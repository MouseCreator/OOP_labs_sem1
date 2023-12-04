package univ.lab.ninjagame1.controller;

import android.widget.Button;

import univ.lab.ninjagame1.MainActivity;
import univ.lab.ninjagame1.R;

public class UIManager {
    private Button pauseButton;
    private Button recordButton;
    private final MainActivity activity;
    private void onUI(Runnable r) {
        activity.runOnUiThread(r);
    }
    public UIManager(MainActivity mainActivity) {
        this.activity = mainActivity;
    }

    public void setPauseButton(Button pauseButton) {
        this.pauseButton = pauseButton;
    }
    public void setRecordButton(Button recordButton) {
        this.recordButton = recordButton;
    }
    public void changeActivity(boolean isRecording) {
        onUI(() -> activity.switchRecordingActivity(isRecording));
    }
    public void startRecording() {
        onUI(() -> {
            if (recordButton != null) {
                recordButton.setText(R.string.button_stop);
            }
        });
    }
    public void stopRecording() {
        onUI(() -> {
            if (recordButton != null) {
                recordButton.setText(R.string.button_record);
            }
        });
    }
    public void onPause() {
        onUI(() -> {
            if (pauseButton != null) {
                pauseButton.setText(R.string.continueTxt);
            }
        });
    }
    public void onContinue() {
        onUI(() -> {
            if (pauseButton != null) {
                recordButton.setText(R.string.pauseTxt);
            }
        });
    }
}
