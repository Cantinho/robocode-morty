package cantinho.utils;

import cantinho.Vehicle;

public class Predictor {

    public static Position predictPosition(final Vehicle vehicle, final long futureTimestamp) {
        final double hypotenuse = vehicle.getVelocity() * futureTimestamp;
        final double futureX = vehicle.getX() + Math.sin(Math.toRadians(vehicle.getHeading())) * hypotenuse;
        final double futureY = vehicle.getY() + Math.sin(Math.toRadians(vehicle.getHeading())) * hypotenuse;
        return new Position(futureX, futureY);
    }

}
