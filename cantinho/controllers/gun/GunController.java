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
    int gunTargetX, gunTargetY = Integer.MIN_VALUE;

    public GunController(final IGun gun) {
        this.gun = gun;
    }

    public void init() {
        gun.turnGunRight(360);
    }

    public void run() {
        // Turns the gun toward the current aim coordinate (x,y) controlled by
        // the current mouse coordinate
        double angle = normalAbsoluteAngle(Math.atan2(gunTargetX - gun.getX(), gunTargetY - gun.getY()));

        gun.setTurnGunRightRadians(normalRelativeAngle(angle - gun.getGunHeadingRadians()));
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
        gunTargetX = (int)(gun.getX() + Math.sin(angle) * event.getDistance());
        gunTargetY = (int)(gun.getY() + Math.cos(angle) * event.getDistance());
    }

}
