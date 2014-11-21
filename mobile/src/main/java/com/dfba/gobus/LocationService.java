package com.dfba.gobus;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.text.format.DateUtils;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;

public class LocationService extends Service implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener {

    private static final String EXTRA_EVENT_ID = "extra_event_id";
    private int notificationId = 0;
    LocationClient mLocationClient;


    @Override
    public IBinder onBind(Intent arg0)
    {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        super.onStartCommand(intent,flags,startId);
        mLocationClient = new LocationClient(this,this,this);
        ArrayList<com.dfba.gobus.Location> coord = new ArrayList<com.dfba.gobus.Location>();
        coord.add(new com.dfba.gobus.Location("41.1272436","16.8656919"));
        coord.add(new com.dfba.gobus.Location("41.1363491","16.8447682"));
        //mLocationClient.connect();
        //new StopsTask().execute("41.1272436","16.8656919");
        Stop s1 = new Stop("10","76","Via dei Mille, 180","19:25");
        Stop s2 = new Stop("21","76","Via dei Mille, 180","18:46");
        Stop s3 = new Stop("21","76","Via Re David, 193/R ang. Via Toma, 180","20:18");

        Intent viewIntent = new Intent(this, MainActivity.class);
        viewIntent.putExtra(EXTRA_EVENT_ID, 17);

        PendingIntent viewPendingIntent = PendingIntent.getActivity(this, 0, viewIntent, 0);
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Line "+s3.getLine()+" at "+s3.getDistance_mt())
                .setContentText("Address:"+s3.getStop_address()+" at "+s3.getTime())
                .setContentIntent(viewPendingIntent)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notificationId++, notification);

        return 0;
    }
    @Override
    public void onCreate(){

    }



    @Override
    public void onConnected(Bundle bundle) {
        Location mCurrentLocation;
        String longitude = "0000";
        String latitude = "0000";
        try{
            mCurrentLocation = mLocationClient.getLastLocation();
            latitude =  String.valueOf(mCurrentLocation.getLatitude());
            longitude = String.valueOf(mCurrentLocation.getLongitude());
            new StopsTask().execute(latitude,longitude);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}