package org.example.server;

import org.example.dto.DesktopDTO;
import org.example.dto.MobileDTO;
import org.example.model.MovementParams;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

public class SimpleMessageProcessor {
    private final ConcurrentLinkedQueue<MobileDTO> messageQueue;
    private final BlockingQueue<DesktopDTO> sendQueue;
    public SimpleMessageProcessor(ConcurrentLinkedQueue<MobileDTO> messageQueue, BlockingQueue<DesktopDTO> sendQueue) {
        this.messageQueue = messageQueue;
        this.sendQueue = sendQueue;
    }
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

    public void ifAny(Consumer<MobileDTO> consumer) {
        while (!messageQueue.isEmpty()) {
            MobileDTO message = messageQueue.poll();
            consumer.accept(message);
        }
    }

    public void send(int state) {
        DesktopDTO desktopDTO = new DesktopDTO();
        desktopDTO.setGameState(state);
        desktopDTO.setDetails("");
        sendQueue.add(desktopDTO);
    }

    public void send(DesktopDTO desktopDTO) {
        sendQueue.add(desktopDTO);
    }
}
