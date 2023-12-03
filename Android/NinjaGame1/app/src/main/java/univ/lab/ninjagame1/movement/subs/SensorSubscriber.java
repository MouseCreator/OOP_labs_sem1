package univ.lab.ninjagame1.movement.subs;

import univ.lab.ninjagame1.filtered.Vector3;

public interface SensorSubscriber {
    void onUpdate(Vector3 newData);
    Topic getTopic();
}
