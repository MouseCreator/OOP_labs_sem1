package univ.lab.ninjagame1;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import univ.lab.ninjagame1.game.Game;

public class MainActivity extends Activity {
    private static final String DEBUG_TAG = "Velocity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new Game(this));
    }

}