package univ.lab.ninjagame1.game;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameLoop extends Thread {
    private final Game game;
    private boolean isRunning = false;
    private final SurfaceHolder surfaceHolder;
    private final double MAX_UPS = 60.0;
    private final double UPS_PERIOD = 1000 / MAX_UPS;
    private double averageUPS = 0.0;
    private double averageFPS = 0.0;
    public GameLoop(Game game, SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        this.game = game;
    }
    public double getAverageFPS() {
        return averageFPS;
    }
    public double getAverageUPS() {
        return averageUPS;
    }
    public void startLoop() {
        isRunning = true;
        start();
    }
    @Override
    public void run() {
        super.run();
        Canvas canvas = null;

        long startTime = System.currentTimeMillis();
        long elapsedTime = 0;
        long sleepTime;

        int updateCount = 0;
        int frameCount = 0;

        while (isRunning) {
            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    game.update();
                    updateCount++;
                    game.draw(canvas);
                }

            } catch (IllegalAccessError e) {
                e.printStackTrace();
            } finally {
                try {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                    frameCount++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            sleepTime = (long) (updateCount*UPS_PERIOD - elapsedTime);
            if (sleepTime > 0) {
                try {
                    sleep(sleepTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            while (sleepTime < 0 && updateCount < MAX_UPS - 1) {
                game.update();
                updateCount++;
                elapsedTime = System.currentTimeMillis() - startTime;
                sleepTime =(long) (updateCount * UPS_PERIOD - elapsedTime);
            }

            elapsedTime = System.currentTimeMillis() - startTime;
            if (elapsedTime >= 1000) {
                averageUPS = updateCount / (0.001 * elapsedTime);
                averageFPS = frameCount / (0.001 * elapsedTime);
                updateCount = 0;
                frameCount = 0;
                startTime = System.currentTimeMillis();
            }
        }
    }

}
