package com.quancua.cohuedepgai;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


public class MainActivity extends AppCompatActivity {
    private GoogleMap myMap;
    private ProgressDialog myProgress;
    private static final String MYTAG = "MYTAG";
    public static final int REQUEST_ID_ACCESS_COURSE_FINE_LOCATION = 100;
    TextView txtMoist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        txtMoist = (TextView) findViewById(R.id.txtMoist);
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//        myRef.setValue("Hello");
//
//        myProgress = new ProgressDialog(this);
//        myProgress.setTitle("Map Loading ...");
//        myProgress.setMessage("Please wait...");
//        myProgress.setCancelable(true);
//
//        myProgress.show();
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);
//        mapFragment.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(GoogleMap googleMap) {
//                onMyMapReady(googleMap);
//            }
//        });
//


        GraphView graphMoist = (GraphView) findViewById(R.id.graphMoist);
        LineGraphSeries<DataPoint> seriesMoist = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1023),
                new DataPoint(1, 1023),
                new DataPoint(2, 1023),
                new DataPoint(3, 1024),
                new DataPoint(4, 1023),
                new DataPoint(5, 1024),
                new DataPoint(6, 1023),
                new DataPoint(7, 1023),
                new DataPoint(8, 1024),
                new DataPoint(9, 1023)
        });
        graphMoist.addSeries(seriesMoist);

        GraphView graphTemp = (GraphView) findViewById(R.id.graphTemp);
        LineGraphSeries<DataPoint> seriesTemp = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 56.85),
                new DataPoint(1, 56.85),
                new DataPoint(2, 57.85),
                new DataPoint(3, 56.85),
                new DataPoint(4, 55.85),
                new DataPoint(5, 56.85),
                new DataPoint(6, 56.85),
                new DataPoint(7, 56.85),
                new DataPoint(8, 57.85),
                new DataPoint(9, 55.85)
        });
        graphTemp.addSeries(seriesTemp);
    }

//    @SuppressLint("MissingPermission")
//    private void onMyMapReady(GoogleMap googleMap) {
//
//        // Lấy đối tượng Google Map ra:
//        myMap = googleMap;
//
//        // Thiết lập sự kiện đã tải Map thành công
//        myMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
//
//            @Override
//            public void onMapLoaded() {
//
//                // Đã tải thành công thì tắt Dialog Progress đi
//                myProgress.dismiss();
//
//                // Hiển thị vị trí người dùng.
//                askPermissionsAndShowMyLocation();
//            }
//        });
//        myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        myMap.getUiSettings().setZoomControlsEnabled(true);
//        myMap.setMyLocationEnabled(true);
//    }
//
//    private void askPermissionsAndShowMyLocation() {
//
//
//        // Với API >= 23, bạn phải hỏi người dùng cho phép xem vị trí của họ.
//        if (Build.VERSION.SDK_INT >= 23) {
//            int accessCoarsePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
//            int accessFinePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
//
//
//            if (accessCoarsePermission != PackageManager.PERMISSION_GRANTED || accessFinePermission != PackageManager.PERMISSION_GRANTED) {
//                // Các quyền cần người dùng cho phép.
//                String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
//
//                // Hiển thị một Dialog hỏi người dùng cho phép các quyền trên.
//                ActivityCompat.requestPermissions(this, permissions, REQUEST_ID_ACCESS_COURSE_FINE_LOCATION);
//                return;
//            }
//        }
//
//        // Hiển thị vị trí hiện thời trên bản đồ.
//        this.showMyLocation();
//    }
//
//    // Khi người dùng trả lời yêu cầu cấp quyền (cho phép hoặc từ chối).
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        //
//        switch (requestCode) {
//            case REQUEST_ID_ACCESS_COURSE_FINE_LOCATION: {
//
//
//                // Chú ý: Nếu yêu cầu bị bỏ qua, mảng kết quả là rỗng.
//                if (grantResults.length > 1
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
//                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//
//                    Toast.makeText(this, "Permission granted!", Toast.LENGTH_LONG).show();
//
//                    // Hiển thị vị trí hiện thời trên bản đồ.
//                    this.showMyLocation();
//                }
//                // Hủy bỏ hoặc từ chối.
//                else {
//                    Toast.makeText(this, "Permission denied!", Toast.LENGTH_LONG).show();
//                }
//                break;
//            }
//        }
//    }
//
//    private String getEnabledLocationProvider() {
//        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//
//        // Tiêu chí để tìm một nhà cung cấp vị trí.
//        Criteria criteria = new Criteria();
//
//        // Tìm một nhà cung vị trí hiện thời tốt nhất theo tiêu chí trên.
//        // ==> "gps", "network",...
//        String bestProvider = locationManager.getBestProvider(criteria, true);
//
//        boolean enabled = locationManager.isProviderEnabled(bestProvider);
//
//        if (!enabled) {
//            Toast.makeText(this, "No location provider enabled!", Toast.LENGTH_LONG).show();
//            Log.i(MYTAG, "No location provider enabled!");
//            return null;
//        }
//        return bestProvider;
//    }
//
//    // Chỉ gọi phương thức này khi đã có quyền xem vị trí người dùng.
//    private void showMyLocation() {
//
//        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//        String locationProvider = this.getEnabledLocationProvider();
//
//        if (locationProvider == null) {
//            return;
//        }
//
//        // Millisecond
//        final long MIN_TIME_BW_UPDATES = 1000;
//        // Met
//        final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;
//
//        Location myLocation = null;
//        try {
//
//            // Đoạn code nay cần người dùng cho phép (Hỏi ở trên ***).
//            locationManager.requestLocationUpdates(
//                    locationProvider,
//                    MIN_TIME_BW_UPDATES,
//                    MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) this);
//
//            // Lấy ra vị trí.
//            myLocation = locationManager
//                    .getLastKnownLocation(locationProvider);
//        }
//        // Với Android API >= 23 phải catch SecurityException.
//        catch (SecurityException e) {
//            Toast.makeText(this, "Show My Location Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
//            Log.e(MYTAG, "Show My Location Error:" + e.getMessage());
//            e.printStackTrace();
//            return;
//        }
//
//        if (myLocation != null) {
//
//            LatLng latLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
//            myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
//
//            CameraPosition cameraPosition = new CameraPosition.Builder()
//                    .target(latLng)             // Sets the center of the map to location user
//                    .zoom(15)                   // Sets the zoom
//                    .bearing(90)                // Sets the orientation of the camera to east
//                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
//                    .build();                   // Creates a CameraPosition from the builder
//            myMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//
//            // Thêm Marker cho Map:
//            MarkerOptions option = new MarkerOptions();
//            option.title("My Location");
//            option.snippet("....");
//            option.position(latLng);
//            Marker currentMarker = myMap.addMarker(option);
//            currentMarker.showInfoWindow();
//        } else {
//            Toast.makeText(this, "Location not found!", Toast.LENGTH_LONG).show();
//            Log.i(MYTAG, "Location not found");
//        }
//
//
//    }
//
//    @Override
//    public void onLocationChanged(Location location) {
//
//    }
//
//    @Override
//    public void onStatusChanged(String s, int i, Bundle bundle) {
//
//    }
//
//    @Override
//    public void onProviderEnabled(String s) {
//
//    }
//
//    @Override
//    public void onProviderDisabled(String s) {
//
//    }
}
