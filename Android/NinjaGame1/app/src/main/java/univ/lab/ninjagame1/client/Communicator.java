package univ.lab.ninjagame1.client;

import java.util.List;
import java.util.function.Consumer;

import univ.lab.ninjagame1.dto.DesktopDTO;
import univ.lab.ninjagame1.dto.MobileDTO;
import univ.lab.ninjagame1.filtered.Vector3;

public interface Communicator {
    void start();
    void send(MobileDTO mobileDTO);
    void stopConnection();
    void onReceive(Consumer<DesktopDTO> o);
}
