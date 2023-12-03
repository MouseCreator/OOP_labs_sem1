package univ.lab.ninjagame1.client;

import java.util.List;
import java.util.Locale;

import univ.lab.ninjagame1.dto.MessageType;
import univ.lab.ninjagame1.dto.MobileDTO;
import univ.lab.ninjagame1.filtered.Vector3;

public class AdvancedCommunicator {

    private final Communicator communicator;

    public AdvancedCommunicator(Communicator communicator) {
        this.communicator = communicator;
    }

    public void sendRecordedData(List<Vector3> vector) {
        MobileDTO mobileDTO = new MobileDTO();
        mobileDTO.setMessageType(MessageType.RECORD_MESSAGE);
        mobileDTO.setVectorData(transformRecords(vector));
        communicator.send(mobileDTO);
    }

    private String transformParams(MovementParams movementParams) {
        return String.format(Locale.ENGLISH, "%f %f %f %d", movementParams.getxAngle(),
                movementParams.getyAngle(), movementParams.getzAngle(), movementParams.getSpeed());
    }

    private String transformRecords(List<Vector3> recordedVector) {
        int size = recordedVector.size();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            Vector3 v =recordedVector.get(i);
            builder.append(v.x()).append(" ").append(v.y()).append(" ").append(v.z());
            if (i != size-1)
                builder.append(",");
        }
        return builder.toString();
    }

    public void sendModeSwitch(int toMode) {
        MobileDTO mobileDTO = new MobileDTO();
        mobileDTO.setMessageType(MessageType.SWITCH_MODE);
        mobileDTO.setGameMode(toMode);
        communicator.send(mobileDTO);
    }

    public void sendMovementParams(MovementParams movementParams) {
        MobileDTO mobileDTO = new MobileDTO();
        mobileDTO.setMessageType(MessageType.SHURIKEN_MESSAGE);
        mobileDTO.setVectorData(transformParams(movementParams));
        communicator.send(mobileDTO);
    }
}
