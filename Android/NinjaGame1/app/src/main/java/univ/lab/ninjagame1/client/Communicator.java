package univ.lab.ninjagame1.client;

import java.io.IOException;
import java.util.function.Consumer;

import univ.lab.ninjagame1.dto.DesktopDTO;

public interface Communicator {
    void start();
    void send(MovementParams movementParams);
    void stopConnection();
    void onReceive(Consumer<DesktopDTO> o);
}
