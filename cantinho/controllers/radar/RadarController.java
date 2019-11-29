package cantinho.controllers.radar;

import cantinho.controllers.IRadar;
import robocode.HitByBulletEvent;
import robocode.ScannedRobotEvent;

import static robocode.util.Utils.normalAbsoluteAngle;
import static robocode.util.Utils.normalRelativeAngle;

public class RadarController {

    final IRadar radar;

    private boolean isTracking = false;

    // The coordinate of the radar target (x,y)
    int radarTargetX, radarTargetY = Integer.MIN_VALUE;

    public RadarController(final IRadar radar) {
        this.radar = radar;
    }

    public void init() {
        radar.turnRadarRight(360);
    }

    public void run() {
        // Turns the radar toward the current radar target coordinate (x,y) controlled by
        // the current mouse coordinate
        double radarTargetAngle = normalAbsoluteAngle(Math.atan2(radarTargetX - radar.getX(), radarTargetY - radar.getY()));

        radar.setTurnRadarRightRadians(normalRelativeAngle(radarTargetAngle - radar.getRadarHeadingRadians()));
    }

    public void onHitByBullet(final HitByBulletEvent event) {
        if(!isTracking) {
            radar.turnRadarRight(360);
        }
    }

    public void onScannedRobot(final ScannedRobotEvent event) {
        if(!isTracking) {
            isTracking = true;
        }
        // Calculate the angle to the scanned robot
        double angle = Math.toRadians((radar.getHeading() + event.getBearing()) % 360);

        // Calculate the coordinates of the robot
        radarTargetX = (int)(radar.getX() + Math.sin(angle) * event.getDistance());
        radarTargetY = (int)(radar.getY() + Math.cos(angle) * event.getDistance());
    }

}
