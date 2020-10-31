package eu.kudan.qrcode.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import eu.kudan.qrcode.Utils.Constants;
import eu.kudan.qrcode.Utils.GPSTracker;
import eu.kudan.qrcode.Utils.GpsTracker1;
//import eu.kudan.qrcode.LocationTrack;
import eu.kudan.qrcode.Utils.LocationTrack;
import eu.kudan.qrcode.Utils.Loyalty;
import eu.kudan.qrcode.R;
import eu.kudan.qrcode.Storage.SharedPreference;

public class NewFarmActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,LocationListener{
        Button gps,phone,sure_phone,gps1,farm_code;
        KProgressHUD hud1;
        EditText space_number,farm_code_number;
        TextView loyality_point;
        private ArrayList permissionsToRequest;
        private ArrayList permissionsRejected = new ArrayList();
        private ArrayList permissions = new ArrayList();

        private final static int ALL_PERMISSIONS_RESULT = 101;
        LocationTrack locationTrack;
        private static String tableName = "malyan.malyan_farms";
        // jdbc Connection
        private static Connection conn = null;
        private static Statement stmt = null;
        private static PreparedStatement sql_statement;
        private static String dbURL = "jdbc:postgresql://3.220.123.110:30001/pentadata?user=postgres&password=Xysrt[9816Z";

        LocationManager locationManager;

        String locationText = "";
        String locationLatitude = "";
        String locationLongitude = "";
        GoogleMap googleMap;
        GoogleMap mGoogleMap;
        SupportMapFragment mapFrag;
        LocationRequest mLocationRequest;
        GoogleApiClient mGoogleApiClient;
        Location mLastLocation;
        Marker mCurrLocationMarker;

        private int mInterval = 3000; // 3 seconds by default, can be changed later
        private Handler mHandler;
        private FusedLocationProviderClient client;
        SharedPreference sharedPreference;
        List<Loyalty> products;
        double latitude=0;
        double longitude=0;
        String farm_code_result="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_farm);

        client= LocationServices.getFusedLocationProviderClient(this);
        gps=(Button)findViewById(R.id.gps);
        gps1=(Button)findViewById(R.id.gps1);
        phone=(Button)findViewById(R.id.phone);
        sure_phone=(Button)findViewById(R.id.sure_phone);
        space_number=(EditText)findViewById(R.id.space_number);
        farm_code_number=(EditText)findViewById(R.id.farm_code_number);
        farm_code=(Button)findViewById(R.id.farm_code);
        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(NewFarmActivity.this);
        loyality_point=(TextView)findViewById(R.id.loyality_point);

        gps1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getSupportActionBar().setTitle("Map Location Activity");

                mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                mapFrag.getMapAsync(NewFarmActivity.this);

            }
        });
        farm_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new IntentIntegrator(NewFarmActivity.this).setCaptureActivity(ScannerActivity.class).initiateScan();
            }
        });
        gps.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
//                 check if GPS enabled
                GPSTracker gpsTracker = new GPSTracker(NewFarmActivity.this);

                if (gpsTracker.getIsGPSTrackingEnabled()) {
                    String stringLatitude = String.valueOf(gpsTracker.latitude);


                    String stringLongitude = String.valueOf(gpsTracker.longitude);


                    String country = gpsTracker.getCountryName(NewFarmActivity.this);


                    String city = gpsTracker.getLocality(NewFarmActivity.this);


                    String postalCode = gpsTracker.getPostalCode(NewFarmActivity.this);


                    String addressLine = gpsTracker.getAddressLine(NewFarmActivity.this);
//                    Toast.makeText(NewFarmActivity.this, "country is " + country + "  city is  " + city, Toast.LENGTH_LONG).show();
//
                }

                GpsTracker1 gps = new GpsTracker1(NewFarmActivity.this);
//                    if(gps.canGetLocation()){
                if(gps.canGetLocation()) {
                    if (String.valueOf(gps.getLatitude()).length() < 5) {
                        Toast.makeText(NewFarmActivity.this, "Please wait  seconds and click again", Toast.LENGTH_LONG).show();
//
                    } else {
                        Toast.makeText(NewFarmActivity.this, "latitiude1 is " + gps.getLatitude() + "  longitude is  " + gps.getLongitude(), Toast.LENGTH_LONG).show();
//                        Toast.makeText(NewFarmActivity.this, "lati is " + latitude + "  longi is  " + longitude, Toast.LENGTH_LONG).show();
                        phone.setVisibility(View.VISIBLE);
                    }
                }
//                    }
                else
                {
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }
            }
        });
//                    GpsTracker1 gps = new GpsTracker1(NewFarmActivity.this);
////                    if(gps.canGetLocation()){
//                    if(gps.canGetLocation()) {
//
////                        latitude = gps.getLatitude();
////                        longitude = gps.getLongitude();
////                        Constants.farm_latitude=gps.getLatitude();
////                        Constants.farm_longitude=gps.getLongitude();
////                        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
////                        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
////                        double longitude = location.getLongitude();
////                        double latitude = location.getLatitude();
//                        Toast.makeText(NewFarmActivity.this, "latitiude1s is " + gps.getLatitude() + "  longitude is  " + gps.getLongitude(), Toast.LENGTH_LONG).show();
////                        Toast.makeText(NewFarmActivity.this, "lati is " + latitude + "  longi is  " + longitude, Toast.LENGTH_LONG).show();
//
//                    }
////                    }
//                else
//                {
//                    // can't get location
//                    // GPS or Network is not enabled
//                    // Ask user to enable GPS/network in settings
//                    gps.showSettingsAlert();
//                }


//                //Alert Dialog
//                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
//                        NewFarmActivity.this);
//
//                // Setting Dialog Title
//                alertDialog2.setTitle("Notification");
//
//                // Setting Dialog Message
//                String string1 = "Give it 10-15 seconds for your coordinates to update. Keep moving around and you will see coordinates update.";
//
//                alertDialog2.setMessage(string1);
//
//                // Setting Icon to Dialog
//                alertDialog2.setIcon(R.drawable.ic_launcher_background);
//
//                // Setting Positive "Yes" Btn
//                alertDialog2.setPositiveButton("Continue",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        });
//
//                // Showing Alert Dialog
//                alertDialog2.show();
//
//                Handler handler2 = new Handler();
//                handler2.postDelayed(new Runnable() {
//                    public void run() {
//                        mHandler = new Handler();
//                        startRepeatingTask();
//                    }
//                }, 5000);   //5 seconds
//
//                if ( ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//                    ActivityCompat.requestPermissions(NewFarmActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
//
//                }
//            }
//
//});
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                space_number.setVisibility(View.VISIBLE);
            }
        });
        sure_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(space_number.getText().toString().length()<1) {
                    Toast.makeText(NewFarmActivity.this, " الرجاء ادخال رقم المساحه ", Toast.LENGTH_SHORT).show();
                }
//        else{
//        Toast.makeText(NewFarmActivity.this,"phone is  "+farm_code_number.getText().toString().length(),Toast.LENGTH_LONG).show();
//        if (((phone_number.getText().toString().length()) != 11)&&latitude == 0.0&&longitude ==0.0) {
//            Toast.makeText(NewFarmActivity.this, " الرجاء ادخال رقم المحمول لايتعدي ولا يقل عن 11 رقم ", Toast.LENGTH_LONG).show();
//        }
//        createConnection();
                else {
                    if((latitude == 0.0)||(longitude ==0.0)){
                        Toast.makeText(NewFarmActivity.this, " الرجاء اختيار ع الخريطه المكان المناسب ", Toast.LENGTH_SHORT).show();

                    }else{

if((farm_code_number.getText().toString().length()<2) && farm_code_result.length()<1) {
//    Toast.makeText(NewFarmActivity.this,"phone is"+farm_code_number.getText().toString().length(),Toast.LENGTH_LONG).show();
    Toast.makeText(NewFarmActivity.this, " الرجاء اصدار الرقم الخاص بالمزرعه ", Toast.LENGTH_SHORT).show();

}else{
    String url="";
    if(farm_code_number.getText().toString().length()>2) {
        url = "http://3.218.130.218:8080/nfc/webresources/phone/insert_farm_code/" + farm_code_number.getText().toString() + "/" + Constants.phone_id;
    }else{
        url = "http://3.218.130.218:8080/nfc/webresources/phone/insert_farm_code/" + farm_code_result + "/" + Constants.phone_id;

    }
    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
        @Override
        public void onResponse(final String s) {


            new Handler(Looper.getMainLooper()).post(new Runnable() { // Tried new Handler(Looper.myLopper()) also
                @Override
                public void run() {
                    if (s.equals("error")) {

                        Toast.makeText(NewFarmActivity.this, "هناك مشكله في الشبكه حاول مره اخري ", Toast.LENGTH_LONG).show();

                    } else {
//                        final String[] parts1 = s.split("mmmnmnmnmnm");
////                                                                mnmnmnmnmnmansnam
//                        Constants.phone_loyality=parts1[0];
//                        Constants.phone_id =parts1[1];
//                        Constants.phone_loyality=parts1[0];
//                        Toast.makeText(NewFarmActivity.this,    "محفظتك تحتوي علي "+ parts1[0] +" نقاط",Toast.LENGTH_LONG).show();
Intent i=new Intent(NewFarmActivity.this,QRActivity.class);
startActivity(i);
                    }
                }
            });


        }

    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            System.out.println("volleyError " + error);
            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                Toast.makeText(NewFarmActivity.this, "خطأ في مهلة الشبكة", Toast.LENGTH_LONG).show();
            } else if (error instanceof AuthFailureError) {
                Toast.makeText(NewFarmActivity.this, "خطأ فشل ", Toast.LENGTH_LONG).show();
            } else if (error instanceof ServerError) {
                Toast.makeText(NewFarmActivity.this, "خطأ في الشبكه", Toast.LENGTH_LONG).show();
            } else if (error instanceof NetworkError) {
                Toast.makeText(NewFarmActivity.this, "خطأ في الشبكه", Toast.LENGTH_LONG).show();
            } else if (error instanceof ParseError) {
                Toast.makeText(NewFarmActivity.this, "خطأ في التحويل ", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(NewFarmActivity.this, "من فضل حاول مره اخري", Toast.LENGTH_LONG).show();
            }


        }
    });

    RequestQueue rQueue = Volley.newRequestQueue(NewFarmActivity.this);
    rQueue.add(request);
}}
            }}
        });
    }
    private static void insert_clients(String name)
    {
        try
        {

            stmt = conn.createStatement();
            stmt.execute("insert into " + tableName + "(image_name,statues) values (" +
                    "'" +name + "',0)");
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    }
    private static void createConnection()
    {
        try

        {
            //            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            //Get a connection
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(dbURL);
        }
        catch (Exception except)
        {
            except.printStackTrace();
        }
    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {

//            final EditText yourlat = (EditText) findViewById(R.id.yourLat);
//            final EditText yourlong = (EditText) findViewById(R.id.yourLong);

            try {
                getLocation(); //this function can change value of mInterval.

                if (locationText.toString() == "") {
                    Toast.makeText(getApplicationContext(), "Trying to retrieve coordinates.", Toast.LENGTH_LONG).show();
                }
                else {

//                    yourlat.setText(locationLatitude.toString());
//                    yourlong.setText(locationLongitude.toString());
                    Toast.makeText(NewFarmActivity.this, "lati is " + locationLatitude.toString() + "  longi is  " + locationLongitude.toString(), Toast.LENGTH_LONG).show();
//
                }
            } finally {

                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }

    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 5, (android.location.LocationListener) this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public void onLocationChanged(Location location) {
//
//        locationText = location.getLatitude() + "," + location.getLongitude();
//        locationLatitude = location.getLatitude() + "";
//        locationLongitude = location.getLongitude() + "";
//    }

//    @Override
//    public void onProviderDisabled(String provider) {
//        Toast.makeText(NewFarmActivity.this, "Please Enable GPS", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onStatusChanged(String provider, int status, Bundle extras) {
//
//    }
//
//    @Override
//    public void onProviderEnabled(String provider) {
//
//
//    }





    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (com.google.android.gms.location.LocationListener) NewFarmActivity.this);
        }
    }

    @Override
    public void onMapReady(final GoogleMap googleMap)
    {
        mGoogleMap=googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        }
        else {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
        }

        // Setting a click event handler for the map
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);

                // Setting the title for the marker.
                // This will be displayed on taping the marker
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                latitude=latLng.latitude;
                longitude=latLng.longitude;

                // Clears the previously touched position
                googleMap.clear();

                // Animating to the touched position
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Placing a marker on the touched position
                googleMap.addMarker(markerOptions);
            }
        });
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {}

    @Override
    public void onLocationChanged(Location location)
    {
        mLastLocation = location;
//        if (mCurrLocationMarker != null) {
//            mCurrLocationMarker.remove();
//        }

//        Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
        latitude=location.getLatitude();
        longitude=location.getLongitude();
//        move map camera
//        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));
//        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
//        mGoogleMap.animateCamera( CameraUpdateFactory.zoomTo( 17.0f ) );
//        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                LatLng position = marker.getPosition();
//
//                Toast.makeText(
//                        NewFarmActivity.this,
//                        "Lat " + position.latitude + " "
//                                + "Long " + position.longitude,
//                        Toast.LENGTH_LONG).show();
//                return true;
//            }
//        });
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(NewFarmActivity.this,
                                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //We will get scan results here
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        //check for null
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Scan Cancelled", Toast.LENGTH_LONG).show();
            } else {

                //show dialogue with result
//                showResultDialogue(result.getContents());
farm_code_result=result.getContents();
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
