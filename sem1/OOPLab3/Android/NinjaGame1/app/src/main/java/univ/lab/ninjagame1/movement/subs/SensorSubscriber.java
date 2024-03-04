package univ.lab.ninjagame1.movement.subs;

import java.util.List;

import univ.lab.ninjagame1.filtered.Vector3;

public interface SensorSubscriber {
    void onUpdate(Topic topic, Vector3 newData);
    List<Topic> getTopics();
}
