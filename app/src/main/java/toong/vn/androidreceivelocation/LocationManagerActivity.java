package toong.vn.androidreceivelocation;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class LocationManagerActivity extends AppCompatActivity {
    int UPDATE_INTERVAL_IN_MILLISECONDS = 2000;
    String TAG = "LocationManagerActivity";
    LocationManager mLocationManager;
    LocationListener mLocationManagerListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Toast.makeText(LocationManagerActivity.this,
                    "location change " + location.getLongitude(), Toast.LENGTH_SHORT).show();
            Log.i(TAG, "location change " + location.getLongitude());
            tvLog.append("location change " + location.getLongitude() + "\n");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
    private TextView tvLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_manager);
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        findViewById(R.id.button_start_location_update).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startLocationUpdate();
                    }
                });
        tvLog = (TextView) findViewById(R.id.text_log);
    }

    private void startLocationUpdate(){
        Log.i(TAG, "startLocationUpdate1");
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(LocationManagerActivity.this,
                    new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                    34);
            return;
        }
        Log.i(TAG, "startLocationUpdate2");
        tvLog.append("startLocationUpdate\n");

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                UPDATE_INTERVAL_IN_MILLISECONDS, 0, mLocationManagerListener);
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                UPDATE_INTERVAL_IN_MILLISECONDS, 0, mLocationManagerListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationManager.removeUpdates(mLocationManagerListener);
    }
}
