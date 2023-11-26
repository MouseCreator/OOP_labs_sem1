package univ.lab.ninjagame1.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import univ.lab.ninjagame1.R;

public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final GameLoop gameLoop;

    public Shuriken shuriken;
    public Game(Context context) {
        super(context);
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        gameLoop = new GameLoop(this, surfaceHolder);

        shuriken = new Shuriken(getContext(), Vector2.get(200, 200), 30);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                shuriken.setPosition(Vector2.get(motionEvent.getX(), motionEvent.getY()));
                return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawFPS(canvas);
        drawUPS(canvas);
        shuriken.draw(canvas);
    }

    private void drawFPS(Canvas canvas) {
        String averageFPS = Double.toString(gameLoop.getAverageFPS());
        Paint paint = logPaint();
        canvas.drawText("FPS: " + averageFPS, 100, 50, paint);
    }

    private void drawUPS(Canvas canvas) {
        String averageFPS = Double.toString(gameLoop.getAverageUPS());
        Paint paint = logPaint();
        canvas.drawText("UPS: " + averageFPS, 100, 100, paint);
    }
    @NonNull
    private Paint logPaint() {
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(24);
        return paint;
    }


    public void update() {
        shuriken.update();
    }
}
