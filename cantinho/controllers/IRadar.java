package cantinho.controllers;

public interface IRadar extends ICore {

    void turnRadarRight(final double degrees);

    void setTurnRadarRightRadians(final double radians);

    double getRadarHeadingRadians();
}
