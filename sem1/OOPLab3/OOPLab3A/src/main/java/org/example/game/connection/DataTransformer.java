package org.example.game.connection;

import org.example.model.MovementParams;

public class DataTransformer {
    public static MovementParams toMovement(String message) {
        String[] parts = message.split(" ");
        if (parts.length == 4) {
            double x = Double.parseDouble(parts[0]);
            double y = Double.parseDouble(parts[1]);
            double z = Double.parseDouble(parts[2]);
            int speed = Integer.parseInt(parts[3]);
            return new MovementParams(x,y,z,speed);
        } else {
            return new MovementParams(0, 0, 0, 12000);
        }
    }
}
