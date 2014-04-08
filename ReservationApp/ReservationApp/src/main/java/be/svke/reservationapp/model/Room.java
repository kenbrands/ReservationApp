package be.svke.reservationapp.model;

/**
 * Created by jeansmits on 8/04/14.
 */
public class Room {
    private String roomID;
    private String roomName;
    private String roomActive;
    private String roomVersion;

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomActive() {
        return roomActive;
    }

    public void setRoomActive(String roomActive) {
        this.roomActive = roomActive;
    }

    public String getRoomVersion() {
        return roomVersion;
    }

    public void setRoomVersion(String roomVersion) {
        this.roomVersion = roomVersion;
    }
}
