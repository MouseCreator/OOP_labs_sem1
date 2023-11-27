package univ.lab.ninjagame1.client;

public interface Communicator {
    void start();
    void send(MovementParams movementParams);
    void stopConnection();
}
