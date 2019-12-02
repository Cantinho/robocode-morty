package cantinho.utils;

import cantinho.Vehicle;
import robocode.ScannedRobotEvent;

public class VehicleUtils {

    public static void updateVehicleWithScannedRobotEvent(final Vehicle vehicle, final ScannedRobotEvent event) {
        vehicle.setName(event.getName());
        vehicle.setBearing(event.getBearing());
        vehicle.setDistance(event.getDistance());
        vehicle.setEnergy(event.getEnergy());
        vehicle.setHeading(event.getHeading());
        vehicle.setSpeed(event.getVelocity());
        double absoluteBearingAsDegrees = vehicle.getHeading() + event.getBearing();
        if(absoluteBearingAsDegrees < 0) {
            absoluteBearingAsDegrees += 360;
        }
        final double x = vehicle.getX() + Math.sin(Math.toRadians(absoluteBearingAsDegrees)) * event.getDistance();
        vehicle.setX(x);
        final double y = vehicle.getY() + Math.cos(Math.toRadians(absoluteBearingAsDegrees)) * event.getDistance();
        vehicle.setY(y);
    }
/**
 *     private String name = "";
 *     private double x;
 *     private double y;
 *     private double energy;
 *     private double distance;
 *     private double speed;
 *     private double heading;
 *     private double bearing;
 */
}
