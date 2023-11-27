package org.example.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Drams implements DrawUpdatable {
    private final List<Dram> dramList = new ArrayList<>();
    public static Drams create(Bolts bolts) {
        Drams drams = new Drams();

        bolts.each(bolt->{
                    drams.dramList.add(Dram.withOrigin(bolt.originPosition()));
                }
        );
        return drams;
    }
    public void each(Consumer<Dram> consumer) {
        dramList.forEach(consumer);
    }

    public void draw(Graphics2D g2d) {
        each(d -> d.draw(g2d));
    }

    public void update(){
        each(Dram::update);
    }
}
