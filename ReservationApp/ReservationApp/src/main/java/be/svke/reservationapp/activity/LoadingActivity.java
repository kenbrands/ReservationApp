package be.svke.reservationapp.activity;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.ContentValues;
import android.content.Intent;
import be.svke.reservationapp.R;
import be.svke.reservationapp.db.DB;
import be.svke.reservationapp.db.ReservationContentProvider;
import be.svke.reservationapp.model.Reservation;
import be.svke.reservationapp.model.Room;

public class LoadingActivity extends Activity {
    private Reservation[] reservations;
    private Room[] rooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startrelative);
    }

   class DownloadTask extends AsyncTask<Integer, Integer, String>{


       @Override
       protected String doInBackground(Integer... integers) {
           downloadRooms();
           return null;
       }

       @Override
       protected void onPostExecute(String s) {
           super.onPostExecute(s);
       }
   }

    private void downloadRooms(){
        try{


        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        rooms = restTemplate.getForObject("http://localhost:8081/project/data/rooms", Room[].class);
        getContentResolver().delete(ReservationContentProvider.CONTENT_URI_ROOM, null, null);

        if(rooms != null){
            for(Room room: rooms){
                ContentValues values = new ContentValues();
                values.put(DB.ROOMS.ID, room.getRoomID());
                values.put(DB.ROOMS.ROOMNAME, room.getRoomName());
                getContentResolver().insert(ReservationContentProvider.CONTENT_URI_ROOM, values);

            }
        }
        }catch(Exception e){
            e.printStackTrace();
        }



    }


}
