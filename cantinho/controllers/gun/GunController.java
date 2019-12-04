package cantinho.controllers.gun;

import cantinho.controllers.IGun;
import robocode.HitByBulletEvent;
import robocode.ScannedRobotEvent;

import static robocode.util.Utils.normalAbsoluteAngle;
import static robocode.util.Utils.normalRelativeAngle;

public class GunController {

    final IGun gun;

    private boolean isTracking = false;

    // The coordinate of the radar target (x,y)
    int radarTargetX, radarTargetY = Integer.MIN_VALUE;

    public GunController(final IGun gun) {
        this.gun = gun;
    }

    public void init() {
        gun.turnGunRight(360);
    }

    public void run() {
        // Turns the radar toward the current radar target coordinate (x,y) controlled by
        // the current mouse coordinate
        double gunTargetAngle = normalAbsoluteAngle(Math.atan2(radarTargetX - gun.getX(), radarTargetY - gun.getY()));

        gun.setTurnGunRightRadians(normalRelativeAngle(gunTargetAngle - gun.getGunHeadingRadians()));
    }

    public void onHitByBullet(final HitByBulletEvent event) {
        if(!isTracking) {
            gun.turnGunRight(360);
        }
    }

    public void onScannedRobot(final ScannedRobotEvent event) {
        if(!isTracking) {
            isTracking = true;
        }
        // Calculate the angle to the scanned robot
        double angle = Math.toRadians((gun.getHeading() + event.getBearing()) % 360);

        // Calculate the coordinates of the robot
        radarTargetX = (int)(gun.getX() + Math.sin(angle) * event.getDistance());
        radarTargetY = (int)(gun.getY() + Math.cos(angle) * event.getDistance());
    }

}
