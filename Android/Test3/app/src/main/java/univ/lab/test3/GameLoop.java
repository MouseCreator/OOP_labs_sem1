package univ.lab.test3;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameLoop extends Thread {

    private boolean isRunning;
    private final SurfaceHolder surfaceHolder;
    private final Game game;
    public GameLoop(Game game, SurfaceHolder surfaceHolder) {
        this.game = game;
        this.surfaceHolder = surfaceHolder;
        isRunning = false;
    }
    public double getAverageFPS() {
        return 0;
    }

    public void startLoop() {
        isRunning = true;
        start();
    }

    @Override
    public void run() {
        super.run();
        Canvas canvas;
        while (isRunning) {
            try {
                canvas = surfaceHolder.lockCanvas();
                game.update();
                game.draw(canvas);
                surfaceHolder.unlockCanvasAndPost(canvas);
            } catch (IllegalAccessError e) {
                e.printStackTrace();
            }
        }
    }
}
