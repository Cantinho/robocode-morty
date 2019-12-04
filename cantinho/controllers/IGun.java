package cantinho.controllers;

public interface IGun extends ICore {

    void turnGunRight(final double degrees);

    void setTurnGunRightRadians(final double radians);

    double getGunHeadingRadians();
}
