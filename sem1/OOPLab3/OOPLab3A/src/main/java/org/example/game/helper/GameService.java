package org.example.game.helper;

public interface GameService<R, V> {
    R callService(V value);
}
