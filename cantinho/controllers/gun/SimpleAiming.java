package cantinho.controllers.gun;

import cantinho.controllers.IAimStrategy;
import cantinho.controllers.IGun;
import robocode.ScannedRobotEvent;

import static robocode.util.Utils.normalAbsoluteAngle;
import static robocode.util.Utils.normalRelativeAngle;

public class SimpleAiming implements IAimStrategy {

    final IGun gun;
    // The coordinate of the radar target (x,y)
    int gunTargetX, gunTargetY = Integer.MIN_VALUE;

    public SimpleAiming(final IGun gun) {
        this.gun = gun;
    }

    @Override
    public void aim(final ScannedRobotEvent event) {
        // Calculate the angle to the scanned robot
        final double angle = Math.toRadians((gun.getHeading() + event.getBearing()) % 360);

        // Calculate the coordinates of the robot
        gunTargetX = (int)(gun.getX() + Math.sin(angle) * event.getDistance());
        gunTargetY = (int)(gun.getY() + Math.cos(angle) * event.getDistance());

        // Turns the gun toward the current aim coordinate (x,y) controlled by
        // the current mouse coordinate
        double newAngle = normalAbsoluteAngle(Math.atan2(gunTargetX - gun.getX(), gunTargetY - gun.getY()));

        gun.setTurnGunRightRadians(normalRelativeAngle(newAngle - gun.getGunHeadingRadians()));
    }
}
