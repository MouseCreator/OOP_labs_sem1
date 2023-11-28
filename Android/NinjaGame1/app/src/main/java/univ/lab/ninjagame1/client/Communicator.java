package univ.lab.ninjagame1.client;

import java.util.List;
import java.util.function.Consumer;

import univ.lab.ninjagame1.dto.DesktopDTO;
import univ.lab.ninjagame1.filtered.Vector3;

public interface Communicator {
    void start();
    void send(MovementParams movementParams);
    void stopConnection();
    void onReceive(Consumer<DesktopDTO> o);
    void sendMessageType(List<Vector3> recordedVector);
    void sendMessageType(int type);
}
