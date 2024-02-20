package univ.lab.ninjagame1.controller;




import univ.lab.ninjagame1.event.Event;

public interface InputListener  {
    void putEvent(Event event);
    Event get() throws InterruptedException;
}
