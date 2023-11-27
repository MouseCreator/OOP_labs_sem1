package org.example.model;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MoveableBody implements KeyListener {

    private final boolean[] pressArray = new boolean[6];
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_W) {
            pressArray[0] = true;
        } else if (keyCode == KeyEvent.VK_S) {
            pressArray[1] = true;
        } else if (keyCode == KeyEvent.VK_A) {
            pressArray[2] = true;
        } else if (keyCode == KeyEvent.VK_D) {
            pressArray[3] = true;
        } else if (keyCode == KeyEvent.VK_Q) {
            pressArray[4] = true;
        } else if (keyCode == KeyEvent.VK_E) {
            pressArray[5] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_W) {
            pressArray[0] = false;
        } else if (keyCode == KeyEvent.VK_S) {
            pressArray[1] = false;
        } else if (keyCode == KeyEvent.VK_A) {
            pressArray[2] = false;
        } else if (keyCode == KeyEvent.VK_D) {
            pressArray[3] = false;
        } else if (keyCode == KeyEvent.VK_Q) {
            pressArray[4] = false;
        } else if (keyCode == KeyEvent.VK_E) {
            pressArray[5] = false;
        }
    }

    public Vector3D updatePosition(Vector3D position3d) {
        double moveAmount = 2.0;
        if (pressArray[0]) {
            position3d = position3d.add(new Vector3D(0, 0, moveAmount));
        } else if (pressArray[1]) {
            position3d = position3d.add(new Vector3D(0, 0, -moveAmount));
        } else if (pressArray[2]) {
            position3d = position3d.add(new Vector3D(-moveAmount, 0, 0));
        } else if (pressArray[3]) {
            position3d = position3d.add(new Vector3D(moveAmount, 0, 0));
        } else if (pressArray[4]) {
            position3d = position3d.add(new Vector3D(0, -moveAmount, 0));
        } else if (pressArray[5]) {
            position3d = position3d.add(new Vector3D(0, moveAmount, 0));
        }
        return position3d;
    }
}
