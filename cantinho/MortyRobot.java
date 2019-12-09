package cantinho;

import cantinho.controllers.IGun;
import cantinho.controllers.IRadar;
import cantinho.controllers.gun.GunController;
import cantinho.controllers.gun.PredictiveLinearAiming;
import cantinho.controllers.radar.RadarController;
import cantinho.samples.Interactive;
import robocode.HitByBulletEvent;
import robocode.ScannedRobotEvent;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import static java.awt.event.KeyEvent.*;
import static java.awt.event.KeyEvent.VK_A;

public class MortyRobot extends Interactive implements IRadar, IGun {

    private RadarController radarController = new RadarController(this);
    final PredictiveLinearAiming predictiveLinearAiming = new PredictiveLinearAiming(this);
    private GunController gunController = new GunController(this, predictiveLinearAiming);


    private void init() {
        // Set radar to turn independent from the gun's turn
        setAdjustRadarForGunTurn(true);
        radarController.init();
        gunController.init();
    }

    // Move direction: 1 = move forward, 0 = stand still, -1 = move backward
    int moveDirection;

    // Turn direction: 1 = turn right, 0 = no turning, -1 = turn left
    int turnDirection;

    // Amount of pixels/units to move
    double moveAmount;

    // The coordinate of the aim (x,y)
    int aimX, aimY = Integer.MIN_VALUE;

    // Fire power, where 0 = don't fire
    int firePower;

    // Called when the robot must run
    public void run() {

        // Sets the colors of the robot
        // body = black, gun = white, radar = red
        setColors(Color.BLACK, Color.WHITE, Color.RED);
        init();
        // Loop forever
        for (;;) {
            // Sets the robot to move forward, backward or stop moving depending
            // on the move direction and amount of pixels to move
            setAhead(moveAmount * moveDirection);

            // Decrement the amount of pixels to move until we reach 0 pixels
            // This way the robot will automatically stop if the mouse wheel
            // has stopped it's rotation
            moveAmount = Math.max(0, moveAmount - 1);

            // Sets the robot to turn right or turn left (at maximum speed) or
            // stop turning depending on the turn direction
            setTurnRight(45 * turnDirection); // degrees



            // Fire the gun with the specified fire power, unless the fire power = 0
//            if (firePower > 0) {
//                setFire(firePower);
//            }

            radarController.run();
            gunController.run();

            // Execute all pending set-statements
            execute();

            // Next turn is processed in this loop..
        }
    }

    // Called when a key has been pressed
    public void onKeyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case VK_UP:
            case VK_W:
                // Arrow up key: move direction = forward (infinitely)
                moveDirection = 1;
                moveAmount = Double.POSITIVE_INFINITY;
                break;

            case VK_DOWN:
            case VK_S:
                // Arrow down key: move direction = backward (infinitely)
                moveDirection = -1;
                moveAmount = Double.POSITIVE_INFINITY;
                break;

            case VK_RIGHT:
            case VK_D:
                // Arrow right key: turn direction = right
                turnDirection = 1;
                break;

            case VK_LEFT:
            case VK_A:
                // Arrow left key: turn direction = left
                turnDirection = -1;
                break;
        }
    }

    // Called when a key has been released (after being pressed)
    public void onKeyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case VK_UP:
            case VK_W:
            case VK_DOWN:
            case VK_S:
                // Arrow up and down keys: move direction = stand still
                moveDirection = 0;
                moveAmount = 0;
                break;

            case VK_RIGHT:
            case VK_D:
            case VK_LEFT:
            case VK_A:
                // Arrow right and left keys: turn direction = stop turning
                turnDirection = 0;
                break;
        }
    }

    // Called when the mouse wheel is rotated
    public void onMouseWheelMoved(MouseWheelEvent e) {
        // If the wheel rotation is negative it means that it is moved forward.
        // Set move direction = forward, if wheel is moved forward.
        // Otherwise, set move direction = backward
        moveDirection = (e.getWheelRotation() < 0) ? 1 : -1;

        // Set the amount to move = absolute wheel rotation amount * 5 (speed)
        // Here 5 means 5 pixels per wheel rotation step. The higher value, the
        // more speed
        moveAmount += Math.abs(e.getWheelRotation()) * 5;
    }

    // Called when a mouse button has been pressed
    public void onMousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            // Button 3: fire power = 3 energy points, bullet color = red
            firePower = 3;
            setBulletColor(Color.RED);
        } else if (e.getButton() == MouseEvent.BUTTON2) {
            // Button 2: fire power = 2 energy points, bullet color = orange
            firePower = 2;
            setBulletColor(Color.ORANGE);
        } else {
            // Button 1 or unknown button:
            // fire power = 1 energy points, bullet color = yellow
            firePower = 1;
            setBulletColor(Color.YELLOW);
        }
        predictiveLinearAiming.setFirePower(firePower);
    }

    // Called when a mouse button has been released (after being pressed)
    public void onMouseReleased(MouseEvent e) {
        // Fire power = 0, which means "don't fire"
        firePower = 0;
        predictiveLinearAiming.setFirePower(firePower);
    }

    // Called in order to paint graphics for this robot.
    // "Paint" button on the robot console window for this robot must be
    // enabled in order to see the paintings.
    public void onPaint(Graphics2D g) {
        // Draw a red cross hair with the center at the current aim
        // coordinate (x,y)
        g.setColor(Color.RED);
        g.drawOval(aimX - 15, aimY - 15, 30, 30);
        g.drawLine(aimX, aimY - 4, aimX, aimY + 4);
        g.drawLine(aimX - 4, aimY, aimX + 4, aimY);
    }

    // Called when the mouse has been moved
    public void onMouseMoved(MouseEvent e) {}

    @Override
    public void onScannedRobot(ScannedRobotEvent event) {
        super.onScannedRobot(event);
        radarController.onScannedRobot(event);
        gunController.onScannedRobot(event);
    }

    @Override
    public void onHitByBullet(HitByBulletEvent event) {
        super.onHitByBullet(event);
        radarController.onHitByBullet(event);
        gunController.onHitByBullet(event);
    }
}
