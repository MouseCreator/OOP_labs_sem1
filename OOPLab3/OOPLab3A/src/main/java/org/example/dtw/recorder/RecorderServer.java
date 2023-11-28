package org.example.dtw.recorder;

import org.example.dto.DesktopDTO;
import org.example.dto.MobileDTO;
import org.example.gamestate.GameState;
import org.example.utils.JSONUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;

public class RecorderServer {
    private final int port = 6666;
    private final GestureManager gestureManager = new GestureManager();
    private final Recorder recorder = new Recorder();
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            Socket client = serverSocket.accept();
            System.out.println("ACCEPTED");
            startServer(client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startServer(Socket client) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            runServer(in, out);
            client.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void runServer(BufferedReader in, PrintWriter out) {
        sendRecorderMessage(out);
        try {
            String clientMessage;
            while ((clientMessage = in.readLine()) != null) {
                System.out.println(clientMessage);
                toRecording(clientMessage);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void toRecording(String clientMessage) {
        MobileDTO mobileDTO = JSONUtil.fromJSON(clientMessage);
        if (mobileDTO.getMessageType() != 1)
            return;
        Optional<Recording> record = recorder.record(mobileDTO.getVectorData());
        record.ifPresent(gestureManager::appendRecording);
        if (record.isPresent()) {
            System.out.println("ADDED RECORDING!");
        }
    }

    private void sendRecorderMessage(PrintWriter out) {
        DesktopDTO desktopDTO = createRecordingNotification();
        out.write(JSONUtil.toJSON(desktopDTO));
    }

    private DesktopDTO createRecordingNotification() {
        DesktopDTO desktopDTO = new DesktopDTO();
        desktopDTO.setGameState(GameState.RECORDING);
        desktopDTO.setDetails("");
        return desktopDTO;
    }
}
