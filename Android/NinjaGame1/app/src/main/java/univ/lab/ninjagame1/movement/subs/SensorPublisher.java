package univ.lab.ninjagame1.movement.subs;

public interface SensorPublisher {
    void acceptSubscriber(SensorSubscriber sensorSubscriber);
    void deleteSubscriber(SensorSubscriber orientationManager);
}
