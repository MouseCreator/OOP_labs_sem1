package univ.lab.ninjagame1.client;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class ClientManager {

    public Communicator start(Activity activity) {

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.INTERNET}, 1);
        }
        SocketCommunicator communicator = new SocketCommunicator();
        communicator.start();
        return communicator;
    }

}
