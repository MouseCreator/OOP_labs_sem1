package univ.lab.ninjagame1;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import univ.lab.ninjagame1.client.ClientManager;
import univ.lab.ninjagame1.client.Communicator;
import univ.lab.ninjagame1.client.MovementParams;
import univ.lab.ninjagame1.client.mode.ModeManager;
import univ.lab.ninjagame1.client.recording.RecordingManager;
import univ.lab.ninjagame1.dto.DesktopDTO;
import univ.lab.ninjagame1.filtered.OrientationManager;
import univ.lab.ninjagame1.filtered.Vector3;


public class MainActivity extends AppCompatActivity {
    private GestureDetector gestureDetector;
    private Communicator communicator;
    private OrientationManager orientationManager;
    private ModeManager modeManager;
    private RecordingManager recordingManager;
    private Button recordButton;
    private Button pauseButton;
    private SensorManager sensorManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sensorManager = (SensorManager) getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        super.onCreate(savedInstanceState);
        initModeManager();
        switchRecordingActivity();
        initRecordingManager();
        initOrientationManager();
        initCommunicator();
        modeManager.postConstruct(sensorManager, communicator, recordingManager, orientationManager);
    }

    private void initModeManager() {
        modeManager = new ModeManager();
    }

    private void initRecordingManager() {
        recordingManager = new RecordingManager();
    }

    private void initImage() {
        ImageView imageViewShuriken = findViewById(R.id.shurikenIm);
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setDuration(2000);
        imageViewShuriken.startAnimation(rotateAnimation);
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (modeManager.getCurrentMode()==0) {
                    calculateAndDisplaySpeed(velocityY);
                    return true;
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
        imageViewShuriken.setOnTouchListener((v, event) -> {
            gestureDetector.onTouchEvent(event);
            return true;
        });
    }

    private void initRecordButton() {
        recordButton = findViewById(R.id.recordButton);
        recordButton.setOnClickListener(v -> toggleRecording());
    }

    private void toggleRecording() {
        if (recordingManager.isRecording()) {
            List<Vector3> recordedVector = recordingManager.summarize(sensorManager);
            communicator.sendMessageType(recordedVector);
            recordButton.setText(R.string.button_record);
        } else {
            recordingManager.start(sensorManager);
            recordButton.setText(R.string.button_stop);
        }
    }

    private void initCommunicator() {
        ClientManager clientManager = new ClientManager();
        communicator = clientManager.start(this);
        communicator.onReceive(this::onMessageReceive);
    }

    private void initOrientationManager() {
        orientationManager = new OrientationManager(getApplicationContext());
        orientationManager.start();
    }

    private void calculateAndDisplaySpeed(float velocityY) {
        int speed = Math.abs((int) velocityY);
        Vector3 vector = orientationManager.getCurrentVector();
        Log.d("Main", "Speed: " + speed);
        Log.d("Main", "V: " + vector);
        communicator.send(new MovementParams(speed, vector));
    }

    private void onMessageReceive(DesktopDTO desktopDTO) {
        if (desktopDTO.getGameState() == 4) {
            runOnUiThread(this::switchRecordingActivity);
        }
        modeManager.switchToMode(desktopDTO.getGameState());
    }

    private void switchRecordingActivity() {
        if (modeManager.isRecordMode()) {
            setContentView(R.layout.activity_recorder);
            initRecordButton();
        } else {
            setContentView(R.layout.activity_main);
            initImage();
            initPauseButton();
        }
    }

    private void initPauseButton() {
        pauseButton = findViewById(R.id.pauseButton);
        pauseButton.setOnClickListener(v -> togglePause());
    }

    private void togglePause() {
        if (modeManager.getCurrentMode()==3) {
            communicator.sendMessageType(4);
            pauseButton.setText(R.string.continueTxt);
        } else {
            communicator.sendMessageType(3);
            pauseButton.setText(R.string.pauseTxt);
        }
    }
}