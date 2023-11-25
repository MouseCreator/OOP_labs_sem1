package univ.lab.test3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.core.content.ContextCompat;

public class Game extends SurfaceView implements SurfaceHolder.Callback {

    private final GameLoop gameLoop;

    private final Context context;

    public Game(Context context) {
        super(context);


        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        this.context = context;
        this.gameLoop = new GameLoop(this, surfaceHolder);
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
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawFPS(canvas);
    }

    private void drawFPS(Canvas canvas) {
        String average = Double.toString(gameLoop.getAverageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.magenta);
        paint.setTextSize(20);
        paint.setColor(color);
        canvas.drawText("UPS:" + average, 100, 20, paint);
    }

    public void update() {
    }
}
