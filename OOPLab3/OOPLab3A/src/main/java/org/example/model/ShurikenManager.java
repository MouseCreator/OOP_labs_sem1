package org.example.model;

import org.example.engine.ConstUtils;
import org.example.game.drawable.Sprite;
import org.example.game.drawable.SpriteBuffer;
import org.example.game.drawable.SpriteImpl;
import org.example.game.event.CreationEvent;
import org.example.game.event.DeletionEvent;
import org.example.game.helper.GameUtils;
import org.example.vector.Vector3D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class ShurikenManager implements Updatable, Iterable<Shuriken> {
    private final List<Shuriken> shurikenList = new ArrayList<>();
    public static ShurikenManager create() {
        return new ShurikenManager();
    }
    public void each(Consumer<Shuriken> consumer) {
        shurikenList.forEach(consumer);
    }
    @Override
    public void update() {
        each(Shuriken::update);
        removeDestroyed();
    }

    private void removeDestroyed() {
        shurikenList.stream().filter(Shuriken::isDestroyed).forEach(shuriken -> GameUtils.newEvent(new DeletionEvent(shuriken)));
        shurikenList.removeIf(Shuriken::isDestroyed);
    }

    public void spawn(SpriteBuffer spriteBuffer, MovementParams movementParams) {
        Sprite sprite = SpriteImpl.get(spriteBuffer.getShuriken());
        Shuriken shuriken = Shuriken.newInstance();
        shuriken.initFromMovement(movementParams);
        shuriken.initSprite(sprite);
        shurikenList.add(shuriken);
        GameUtils.newEvent(new CreationEvent(shuriken));
    }

    @Override
    public Iterator<Shuriken> iterator() {
        return shurikenList.iterator();
    }

    public void reset() {
        for (Shuriken shuriken : shurikenList) {
            GameUtils.newEvent(new DeletionEvent(shuriken));
        }
        shurikenList.clear();
    }
}
