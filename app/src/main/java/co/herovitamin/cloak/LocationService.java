package co.herovitamin.cloak;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.github.davidmoten.geo.GeoHash;
import com.github.davidmoten.geo.LatLong;
import com.google.android.gms.location.LocationResult;

import java.util.List;

public class LocationService extends IntentService {

    public static final String ACTION_PROCESS_UPDATES = "co.herovitamin.cloak.PROCESS_UPDATES";
    private static final String TAG = LocationService.class.getSimpleName();

    public LocationService(){
        super(TAG);
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_PROCESS_UPDATES.equals(action)) {
                LocationResult result = LocationResult.extractResult(intent);
                if (result != null) {
                    List<Location> locations = result.getLocations();
                    Log.i(TAG, locations.size() + " locations found.");

                    // loop for batched locations
                    for (Location location : locations) {

                        // 5: for districts
                        // 7: for blocks
                        // 8: for streets
                        int eagleView = 8;
                        String hash = GeoHash.encodeHash(location.getLatitude(), location.getLongitude()).substring(0, eagleView);
                        Log.i(TAG, "hash for: ("+ location.getLatitude()+", "+ location.getLongitude() + "): " + hash);

                        StringBuilder neighbours = new StringBuilder();
                        for (String s : GeoHash.neighbours(hash)) {
                            neighbours.append(s).append("\n");
                        }
                        Log.i(TAG, "neighbours for hash: " + hash + ":\n " + neighbours);

                        //3 decimals right
                        LatLong decodedPosition = GeoHash.decodeHash(hash);
                        Log.i(TAG, "original position: (" + location.getLatitude() + ", " + location.getLongitude()+ ")");
                        Log.i(TAG, "decoded  position: (" + decodedPosition.getLat() + ", " + decodedPosition.getLon()+ ")");
                    }

//                    LocationResultHelper locationResultHelper = new LocationResultHelper(this,
//                            locations);
//                    // Save the location data to SharedPreferences.
//                    locationResultHelper.saveResults();
//                    // Show notification with the location data.
//                    locationResultHelper.showNotification();
//                    Log.i(TAG, LocationResultHelper.getSavedLocationResult(this));
                }
            }
        }
    }
}
