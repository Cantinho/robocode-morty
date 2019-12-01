package cantinho.controllers;

public interface IGun extends ICore {

    void turnRadarRight(final double degrees);

    void setTurnRadarRightRadians(final double radians);

    double getRadarHeadingRadians();
}
