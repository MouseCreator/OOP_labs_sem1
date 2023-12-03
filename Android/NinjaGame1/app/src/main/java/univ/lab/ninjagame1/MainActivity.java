package univ.lab.ninjagame1;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import univ.lab.ninjagame1.controller.GameController;
import univ.lab.ninjagame1.controller.InputListener;
import univ.lab.ninjagame1.controller.UIManager;
import univ.lab.ninjagame1.event.PauseEvent;


public class MainActivity extends AppCompatActivity {
    private InputListener inputListener;

    private GameController gameController;

    private ImageView imageViewShuriken;
    private UIManager uiManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUIManager();
        switchRecordingActivity(false);
        initGameController();
    }

    private void initUIManager() {
        uiManager = new UIManager(this);
    }

    private void initGameController() {
        GameController gameController = GameController.create(getApplicationContext(), uiManager);
        gameController.run();
        inputListener = gameController.getInputListener();
    }

    private void initImage() {
        ImageView imageViewShuriken = findViewById(R.id.shurikenIm);
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setDuration(2000);
        imageViewShuriken.startAnimation(rotateAnimation);
    }
    private void initRecordButton() {
        Button recordButton = findViewById(R.id.recordButton);
        recordButton.setOnClickListener(v -> toggleRecording());
        uiManager.setRecordButton(recordButton);
    }
    private void initPauseButton() {
        Button pauseButton = findViewById(R.id.pauseButton);
        pauseButton.setOnClickListener(v -> inputListener.putEvent(new PauseEvent()));
        uiManager.setPauseButton(pauseButton);
    }
    private void toggleRecording() {
        inputListener.putEvent(new PauseEvent());
    }
    public void switchRecordingActivity(boolean isRecording) {
        if (isRecording) {
            setContentView(R.layout.activity_recorder);
            initRecordButton();
        } else {
            setContentView(R.layout.activity_main);
            initImage();
            initPauseButton();
        }
    }


}