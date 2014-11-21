package com.dfba.gobus;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.URL;

import static com.dfba.gobus.MyAlarmReceiver.*;

/**
 * Created by claudio on 21/11/14.
 */
public class GeoLocationTask extends AsyncTask<String,Integer,Integer> {

    private Context context;
    private ProgressDialog dialog;

    public GeoLocationTask(Context c){
        context = c;
    }

    @Override
    protected void onPreExecute(){
        dialog = new ProgressDialog(context);
        dialog.setTitle("Waiting");
        dialog.setMessage("Computing path...");
        dialog.show();
    }

    @Override
    protected Integer doInBackground(String... params) {
        String URL = "https://fast-fire-771.appspot.com/init";
        RestTemplate restTemplate = new RestTemplate();
        try{
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            ResponseEntity<String> response = restTemplate.postForEntity(URL + "?destination=" + params[0], String.class, null);
            return response.getStatusCode().value();
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }

    }

    @Override
    protected void onPostExecute(Integer response){
        dialog.dismiss();
        if(response==200){
            AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, MyAlarmReceiver.class);
            final PendingIntent pIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    1000, 50 * 1000, pIntent);
            Toast.makeText(context,"Ora riceverai le notifiche sul tuo Smart Watch!",Toast.LENGTH_LONG).show();
        }else{
            System.out.print("BAD");
        }
    }
}
