package univ.lab.ninjagame1.client;

import java.io.IOException;

public interface Communicator {
    void start();
    void send(MovementParams movementParams);
    void stopConnection();
}
