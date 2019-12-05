package cantinho.controllers.gun;

import cantinho.controllers.IAimStrategy;
import cantinho.controllers.IGun;
import cantinho.utils.Position;
import cantinho.utils.Predictor;
import robocode.ScannedRobotEvent;

import static robocode.util.Utils.normalAbsoluteAngle;
import static robocode.util.Utils.normalRelativeAngle;

public class PredictiveLinearAiming implements IAimStrategy {

    final IGun gun;
    // The coordinate of the radar target (x,y)
    int gunTargetX, gunTargetY = Integer.MIN_VALUE;
    double firePower = 0;

    public PredictiveLinearAiming(final IGun gun) {
        this.gun = gun;
    }

    public double getFirePower() {
        return firePower;
    }

    public void setFirePower(double firePower) {
        this.firePower = firePower;
    }

    @Override
    public void aim(final ScannedRobotEvent event) {
        // calculate speed of bullet
        final double bulletSpeed = 20 - getFirePower() * 3;
        // distance = rate * time, solved for time
        final long time = (long) (event.getDistance() / bulletSpeed);

        // Calculate the angle to the scanned robot
        double angle = Math.toRadians((gun.getHeading() + event.getBearing()) % 360);

        // Calculate the coordinates of the robot
        gunTargetX = (int)(gun.getX() + Math.sin(angle) * event.getDistance());
        gunTargetY = (int)(gun.getY() + Math.cos(angle) * event.getDistance());

        // calculate gun turn to predicted x, y position
        final double predictedX = gunTargetX + Math.sin(Math.toRadians(event.getHeading())) * event.getVelocity() * time;
        final double predictedY = gunTargetY + Math.cos(Math.toRadians(event.getHeading())) * event.getVelocity() * time;

        // Turns the gun toward the current aim coordinate (x,y) controlled by
        // the current mouse coordinate
        double newAngle = normalAbsoluteAngle(Math.atan2(predictedX - gun.getX(), predictedY - gun.getY()));

        gun.setTurnGunRightRadians(normalRelativeAngle(newAngle - gun.getGunHeadingRadians()));
    }
}
