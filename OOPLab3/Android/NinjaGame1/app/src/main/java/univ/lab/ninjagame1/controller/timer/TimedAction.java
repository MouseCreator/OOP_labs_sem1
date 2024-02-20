package univ.lab.ninjagame1.controller.timer;

public class TimedAction {

    public static void executeAfter(long millis, Runnable toExecute) {
        Runnable full = () -> {
            try {
                Thread.sleep(millis);
                toExecute.run();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        Thread thread = new Thread(full);
        thread.start();
    }
}
