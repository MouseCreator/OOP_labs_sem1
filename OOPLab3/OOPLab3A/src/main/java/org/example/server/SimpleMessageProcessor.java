package org.example.server;

import org.example.model.MovementParams;
import org.example.utils.JSONUtil;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

public class SimpleMessageProcessor {
    private final ConcurrentLinkedQueue<String> messageQueue;
    public SimpleMessageProcessor(ConcurrentLinkedQueue<String> messageQueue) {
        this.messageQueue = messageQueue;
    }
    public MovementParams toMovement(String message) {
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

    public void ifAny(Consumer<MovementParams> consumer) {
        while (!messageQueue.isEmpty()) {
            String message = messageQueue.poll();
            MovementParams movementParams = toMovement(JSONUtil.fromJSON(message).getVectorData());
            consumer.accept(movementParams);
        }
    }
}
