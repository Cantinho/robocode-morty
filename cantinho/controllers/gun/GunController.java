package cantinho.controllers.gun;

import cantinho.controllers.IAimStrategy;
import cantinho.controllers.IGun;
import robocode.HitByBulletEvent;
import robocode.ScannedRobotEvent;

public class GunController {

    final IGun gun;
    final IAimStrategy aimStrategy;
    private boolean isTracking = false;

    public GunController(final IGun gun, final IAimStrategy aimStrategy) {
        this.gun = gun;
        this.aimStrategy = aimStrategy;
        assert(gun != null);
        assert(aimStrategy != null);
    }

    public void init() {
        gun.turnGunRight(360);
    }

    public void run() {

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
        aimStrategy.aim(event);
    }

}
