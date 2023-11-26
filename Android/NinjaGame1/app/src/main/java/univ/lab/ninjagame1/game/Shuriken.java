package univ.lab.ninjagame1.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import univ.lab.ninjagame1.R;

public class Shuriken {
    private final Vector2 position;
    private final double radius;
    private final Paint paint;
    public Shuriken(Context context, Vector2 position, double radius) {
        this.position = new Vector2(position);
        paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.shuriken);
        this.radius = radius;
        paint.setColor(color);

    }

    public void setPosition(Vector2 vector) {
        this.position.from(vector);
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle(position.xf(), position.yf(), (float) radius, paint);
    }
    public void update() {

    }
}
