package be.svke.reservationapp.model;

/**
 * Created by jeansmits on 8/04/14.
 */
public class Reservation {

    private String reservationID;
    private String roomID;
    private String reservationSTART;
    private String reservationEND;
    private String reservationDESCRIPTION;
    private String userID;
    private String reservationActive;
    private String reservationVersion;

    public String getReservationID() {
        return reservationID;
    }

    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getReservationSTART() {
        return reservationSTART;
    }

    public void setReservationSTART(String reservationSTART) {
        this.reservationSTART = reservationSTART;
    }

    public String getReservationEND() {
        return reservationEND;
    }

    public void setReservationEND(String reservationEND) {
        this.reservationEND = reservationEND;
    }

    public String getReservationDESCRIPTION() {
        return reservationDESCRIPTION;
    }

    public void setReservationDESCRIPTION(String reservationDESCRIPTION) {
        this.reservationDESCRIPTION = reservationDESCRIPTION;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getReservationActive() {
        return reservationActive;
    }

    public void setReservationActive(String reservationActive) {
        this.reservationActive = reservationActive;
    }

    public String getReservationVersion() {
        return reservationVersion;
    }

    public void setReservationVersion(String reservationVersion) {
        this.reservationVersion = reservationVersion;
    }
}
