package be.svke.reservationapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by jeansmits on 8/04/14.
 */
public class Database extends SQLiteOpenHelper{



    private static final String TAG = "Database";
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "appointment_database";
    private static final String CREATE_TABLE_RESERVATIONS =
            "create table " + DB.RESERVATIONS.TABLE
                    + " ("
                    + DB.RESERVATIONS.ID + " integer primary key autoincrement, "
                    + DB.RESERVATIONS.ROOMID + " integer, "
                    + DB.RESERVATIONS.RESERVATIONSTART + " time, "
                    + DB.RESERVATIONS.RESERVATIONEND + " time, "
                    + DB.RESERVATIONS.RESERVATIONDESCRIPTION + " string, "
                    + DB.RESERVATIONS.USERID + " integer, "
                    + DB.RESERVATIONS.RESERVATIONACTIVE+ " string, "
                    + DB.RESERVATIONS.RESERVATIONVERSION+ " integer, );";

    private static final String CREATE_TABLE_ROOMS =
            "create table " + DB.ROOMS.TABLE
                    + " ("
                    + DB.ROOMS.ID + " integer primary key autoincrement, "
                    + DB.ROOMS.ROOMNAME + " string, "
                    + DB.ROOMS.ROOMACTIVE + " string, "
                    + DB.ROOMS.ROOMVERSION + " integer);";

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_RESERVATIONS);
        db.execSQL(CREATE_TABLE_ROOMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database. Existing contents will be lost. ["
                + oldVersion + "]->[" + newVersion + "]");
        db.execSQL("DROP TABLE IF EXISTS " + DB.RESERVATIONS.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DB.ROOMS.TABLE);
        onCreate(db);
    }



}
