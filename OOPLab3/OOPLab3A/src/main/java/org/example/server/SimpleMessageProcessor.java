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
