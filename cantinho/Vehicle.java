package cantinho;

import robocode.Robot;

public class Vehicle extends Robot {
    private String name = "";
    private double x;
    private double y;
    private double energy;
    private double distance;
    private double speed;
    private double heading;
    private double bearing;

    public void clear() {
        name = "";
        x = 0;
        y = 0;
        energy = 0;
        distance = 0;
        speed = 0;
        heading = 0;
        bearing = 0;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    @Override
    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public double getHeading() {
        return heading;
    }

    public void setHeading(double heading) {
        this.heading = heading;
    }

    public double getBearing() {
        return bearing;
    }

    public void setBearing(double bearing) {
        this.bearing = bearing;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "name='" + name + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", energy=" + energy +
                ", distance=" + distance +
                ", speed=" + speed +
                ", heading=" + heading +
                ", bearing=" + bearing +
                '}';
    }
}
