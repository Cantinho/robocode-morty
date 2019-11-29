package cantinho.controllers;

public interface IRadar {

    double getX();

    double getY();

    double getHeading();

    void turnRadarRight(final double degrees);

    void setTurnRadarRightRadians(final double radians);

    double getRadarHeadingRadians();
}
