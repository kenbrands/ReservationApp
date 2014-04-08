package be.svke.reservationapp.db;

/**
 * Created by jeansmits on 8/04/14.
 */
public interface DB {

    public interface RESERVATIONS {
        public static final String TABLE = "reservations";
        public static final String ID = "reservationID";
        public static final String ROOMID = "roomID";
        public static final String RESERVATIONSTART = "reservationSTART";
        public static final String RESERVATIONEND = "reservationEND";
        public static final String RESERVATIONDESCRIPTION = "reservationDESCRIPTION";
        public static final String USERID = "userID";
        public static final String RESERVATIONACTIVE = "reservationACTIVE";
        public static final String RESERVATIONVERSION = "reservationVERSION";
    }

    public interface ROOMS {
        public static final String TABLE = "rooms";
        public static final String ID = "roomID";
        public static final String ROOMNAME = "roomNAME";
        public static final String ROOMACTIVE = "roomACTIVE";
        public static final String ROOMVERSION = "roomVERSION";

    }

}
