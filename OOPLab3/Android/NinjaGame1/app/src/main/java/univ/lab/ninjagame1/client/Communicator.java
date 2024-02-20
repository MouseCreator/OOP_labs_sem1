package univ.lab.ninjagame1.client;

import java.util.function.Consumer;

import univ.lab.ninjagame1.dto.DesktopDTO;
import univ.lab.ninjagame1.dto.MobileDTO;

public interface Communicator {
    void start();
    void send(MobileDTO mobileDTO);
    void stopConnection();
    void onReceive(Consumer<DesktopDTO> o);
}
