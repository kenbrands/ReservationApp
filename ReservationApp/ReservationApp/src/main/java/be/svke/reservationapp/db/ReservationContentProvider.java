package be.svke.reservationapp.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import be.svke.reservationapp.db.DB;

/**
 * Created by jeansmits on 8/04/14.
 */
public class ReservationContentProvider extends ContentProvider{

    private static final String AUTHORITY = "be.svke.reservationAPP.AppointmentsContentProvider";

    public static final int RESERVATIONS = 100;
    public static final int RESERVATION_ID = 110;

    public static final int ROOMS = 200;
    public static final int ROOM_ID = 210;


    private static final String RESERVATION_BASE_PATH = "reservation";
    private static final String ROOM_BASE_PATH = "room";

    public static final Uri CONTENT_URI_APPOINTMENT = Uri.parse("content://"
            + AUTHORITY + "/" + RESERVATION_BASE_PATH);

    public static final Uri CONTENT_URI_DOCTOR = Uri.parse("content://"
            + AUTHORITY + "/" + ROOM_BASE_PATH);

    private static final UriMatcher uRIMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);
    static {
        uRIMatcher.addURI(AUTHORITY, RESERVATION_BASE_PATH, RESERVATIONS);
        uRIMatcher.addURI(AUTHORITY, RESERVATION_BASE_PATH + "/#", RESERVATION_ID);
        uRIMatcher.addURI(AUTHORITY, ROOM_BASE_PATH, ROOMS);
        uRIMatcher.addURI(AUTHORITY, ROOM_BASE_PATH + "/#", ROOM_ID);
    }

    private Database db;

    @Override
    public boolean onCreate() {
        db = new Database(getContext());
        return true;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = uRIMatcher.match(uri);
        if (uriType != RESERVATIONS && uriType != RESERVATION_ID && uriType != ROOMS && uriType != ROOM_ID) {
            throw new IllegalArgumentException("Invalid URI for delete");
        }
        SQLiteDatabase sqlDB = db.getWritableDatabase();
        int rowsAffected = 0;
        String id;
        switch (uriType) {
            case RESERVATIONS:
                rowsAffected = sqlDB.delete(DB.RESERVATIONS.TABLE,
                        selection, selectionArgs);
                break;
            case RESERVATION_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsAffected = sqlDB.delete(DB.RESERVATIONS.TABLE,
                            DB.RESERVATIONS.ID + "=" + id, null);
                } else {
                    rowsAffected = sqlDB.delete(DB.RESERVATIONS.TABLE,
                            selection + " and " + DB.RESERVATIONS.ID + "=" + id,
                            selectionArgs);
                }
                break;
            case ROOMS:
                rowsAffected = sqlDB.delete(DB.ROOMS.TABLE,
                        selection, selectionArgs);
                break;
            case ROOM_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsAffected = sqlDB.delete(DB.ROOMS.TABLE,
                            DB.ROOMS.ID + "=" + id, null);
                } else {
                    rowsAffected = sqlDB.delete(DB.ROOMS.TABLE,
                            selection + " and " + DB.ROOMS.ID + "=" + id,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown or Invalid URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsAffected;
    }

    @Override
    public String getType(Uri uri) {
        // TODO Auto-generated method stub
        return null;
    }

    public Uri insert(Uri uri, ContentValues values) {
        int uriType = uRIMatcher.match(uri);
        if (uriType != RESERVATIONS && uriType != ROOMS) {
            throw new IllegalArgumentException("Invalid URI for insert");
        }
        SQLiteDatabase sqlDB = db.getWritableDatabase();
        long newID = 0;
        if (uriType == RESERVATIONS) {
            newID = sqlDB.insertWithOnConflict(DB.RESERVATIONS.TABLE, null, values,
                    SQLiteDatabase.CONFLICT_REPLACE);
        }
        if (uriType == ROOMS) {
            newID = sqlDB.insertWithOnConflict(DB.ROOMS.TABLE, null, values,
                    SQLiteDatabase.CONFLICT_REPLACE);
        }
        if (newID > 0) {
            Uri newUri = ContentUris.withAppendedId(uri, newID);
            getContext().getContentResolver().notifyChange(uri, null);
            return newUri;
        } else {
            throw new SQLException("Failed to insert row into " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        int uriType = uRIMatcher.match(uri);
        switch (uriType) {
            case RESERVATION_ID:
                queryBuilder.setTables(DB.RESERVATIONS.TABLE);
                queryBuilder.appendWhere(DB.RESERVATIONS.ID + "="
                        + uri.getLastPathSegment());
                break;
            case RESERVATIONS:
                queryBuilder.setTables(DB.RESERVATIONS.TABLE);
                // no filter
                break;
            case ROOM_ID:
                queryBuilder.setTables(DB.ROOMS.TABLE);
                queryBuilder.appendWhere(DB.ROOMS.ID + "="
                        + uri.getLastPathSegment());
                break;
            case ROOMS:
                queryBuilder.setTables(DB.ROOMS.TABLE);
                // no filter
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }
        Cursor cursor = queryBuilder.query(db.getReadableDatabase(),
                projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int count = 0;
        SQLiteDatabase sqlDB = db.getWritableDatabase();
        switch (uRIMatcher.match(uri)) {
            case RESERVATION_ID:
                count = sqlDB.update(DB.RESERVATIONS.TABLE, values,
                        DB.RESERVATIONS.ID
                                + " = "
                                + uri.getPathSegments().get(1)
                                + (!TextUtils.isEmpty(selection) ? " AND ("
                                + selection + ')' : ""), selectionArgs);
                break;
            case RESERVATIONS:
                count = sqlDB.update(DB.RESERVATIONS.TABLE, values, selection,
                        selectionArgs);
                break;
            case ROOM_ID:
                count = sqlDB.update(DB.ROOMS.TABLE, values,
                        DB.ROOMS.ID
                                + " = "
                                + uri.getPathSegments().get(1)
                                + (!TextUtils.isEmpty(selection) ? " AND ("
                                + selection + ')' : ""), selectionArgs);
                break;
            case ROOMS:
                count = sqlDB.update(DB.ROOMS.TABLE, values, selection,
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

}
