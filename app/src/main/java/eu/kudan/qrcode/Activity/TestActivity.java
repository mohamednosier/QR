package eu.kudan.qrcode.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.google.android.gms.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
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

public class TestActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,LocationListener {
    Button phone,sure_phone;
    KProgressHUD hud1;
    EditText phone_number;
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
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String Name = "nameKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        sharedPreference = new SharedPreference();
        client = LocationServices.getFusedLocationProviderClient(this);

        phone = (Button) findViewById(R.id.phone);
        sure_phone = (Button) findViewById(R.id.sure_phone);
        phone_number = (EditText) findViewById(R.id.phone_number);
        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(TestActivity.this);
        loyality_point = (TextView) findViewById(R.id.loyality_point);
        if(mGoogleMap == null){
            GpsTracker1 gps12 = new GpsTracker1(TestActivity.this);

        if (gps12.canGetLocation()) {

            if (String.valueOf(gps12.getLatitude()).length() < 5) {
                this.recreate();
            } else {
                latitude = gps12.getLatitude();
                longitude = gps12.getLongitude();

            }
        }
//                    }
        else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps12.showSettingsAlert();
        }
    }




phone.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        phone_number.setVisibility(View.VISIBLE);
    }
});
sure_phone.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
//        Toast.makeText(TestActivity.this, ""+latitude, Toast.LENGTH_SHORT).show();
//        Log.d("latitude", "onClick: latitude "+latitude);
        if(phone_number.getText().toString().length()!=11) {

            Toast.makeText(TestActivity.this, " الرجاء ادخال رقم المحمول لايتعدي ولا يقل عن 11 رقم ", Toast.LENGTH_SHORT).show();
        } else {
            locationTrack = new LocationTrack(TestActivity.this);
            if (locationTrack.canGetLocation()) {


                longitude = locationTrack.getLongitude();
                latitude = locationTrack.getLatitude();

if(phone_number.getText().toString().substring(0,3).equals("011")||phone_number.getText().toString().substring(0,3).equals("012")||phone_number.getText().toString().substring(0,3).equals("010")||phone_number.getText().toString().substring(0,3).equals("015")){


    Log.d("ddd", "phone_number.getText().toString().substring(0,3)"+phone_number.getText().toString().substring(0,3));






            hud1 = KProgressHUD.create(TestActivity.this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                    .show();

            String url = "http://3.218.130.218:8080/nfc/webresources/phone/register/public/" + phone_number.getText().toString()+"/"+latitude+"/"+longitude;

            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(final String s) {


                    new Handler(Looper.getMainLooper()).post(new Runnable() { // Tried new Handler(Looper.myLopper()) also
                        @Override
                        public void run() {
                            if (!s.equals("error")) {
                                sharedpreferences = getSharedPreferences(mypreference,
                                        Context.MODE_PRIVATE);




                                hud1 = KProgressHUD.create(TestActivity.this)
                                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                        .setAnimationSpeed(2)
                                        .setDimAmount(0.5f)
                                        .show();
                                String url = "http://3.218.130.218:8080/nfc/webresources/phone/select_specific_phone/shoura/" + phone_number.getText().toString();

                                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(final String s) {


                                        new Handler(Looper.getMainLooper()).post(new Runnable() { // Tried new Handler(Looper.myLopper()) also
                                            @Override
                                            public void run() {
                                                if (s.equals("errormmmnmnmnmnm")) {

                                                    Toast.makeText(TestActivity.this, "هذا الرقم غير مسجل انتقل الي شاشه التسجيل ", Toast.LENGTH_LONG).show();
                                                    hud1.dismiss();
                                                } else {
//                                    Log.d("error is ", "run: error is "+s);
//                                    Toast.makeText(TestActivity.this, "error is "+s, Toast.LENGTH_LONG).show();

                                                    Constants.phone_name=phone_number.getText().toString();
                                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                                    editor.putString(Name, phone_number.getText().toString());
                                                    editor.commit();
//                                   Constants.phone_loyality=s;
                                                    final String[] parts1 = s.split("mmmnmnmnmnm");
//                                                                mnmnmnmnmnmansnam
                                                    Constants.phone_loyality=parts1[0];
                                                    Constants.phone_id =parts1[1];
                                                    Log.d("Constants.phone_name", "Constants.phone_name: "+Constants.phone_name);
                                                    Log.d("Constants._loyality", "Constants.phone_loyality: "+Constants.phone_loyality);
                                                    Intent i=new Intent(TestActivity.this,QRActivity.class);
                                                    startActivity(i);

                                                    hud1.dismiss();
                                                }
                                            }
                                        });


                                    }

                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        System.out.println("volleyError " + error);
//                        Toast.makeText(TestActivity.this, "Please Try again", Toast.LENGTH_LONG).show();
                                        hud1.dismiss();
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                            Toast.makeText(TestActivity.this, "خطأ في مهلة الشبكة", Toast.LENGTH_LONG).show();
                                        } else if (error instanceof AuthFailureError) {
                                            Toast.makeText(TestActivity.this, "خطأ فشل ", Toast.LENGTH_LONG).show();
                                        } else if (error instanceof ServerError) {
                                            Toast.makeText(TestActivity.this, "خطأ في الشبكه", Toast.LENGTH_LONG).show();
                                        } else if (error instanceof NetworkError) {
                                            Toast.makeText(TestActivity.this, "خطأ في الشبكه", Toast.LENGTH_LONG).show();
                                        } else if (error instanceof ParseError) {
                                            Toast.makeText(TestActivity.this, "خطأ في التحويل ", Toast.LENGTH_LONG).show();
                                        }else{
                                            Toast.makeText(TestActivity.this, "من فضل حاول مره اخري", Toast.LENGTH_LONG).show();
                                        }

                                    }
                                });

                                RequestQueue rQueue = Volley.newRequestQueue(TestActivity.this);
                                rQueue.add(request);









                                hud1.dismiss();
                            } else {

                                Toast.makeText(TestActivity.this, "هذا الرقم مسجل من قبل", Toast.LENGTH_LONG).show();



                                hud1.dismiss();
                            }
                        }
                    });


                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("volleyError " + error);

                    hud1.dismiss();
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(TestActivity.this, "خطأ في مهلة الشبكة", Toast.LENGTH_LONG).show();
                    } else if (error instanceof AuthFailureError) {
                        Toast.makeText(TestActivity.this, "خطأ فشل ", Toast.LENGTH_LONG).show();
                    } else if (error instanceof ServerError) {
//                        خطأ في الشبكه
                        Toast.makeText(TestActivity.this, "خطأ في الشبكه", Toast.LENGTH_LONG).show();
                    } else if (error instanceof NetworkError) {
                        Toast.makeText(TestActivity.this, "خطأ في الشبكه", Toast.LENGTH_LONG).show();
                    } else if (error instanceof ParseError) {
                        Toast.makeText(TestActivity.this, "خطأ في التحويل ", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(TestActivity.this, "من فضل حاول مره اخري", Toast.LENGTH_LONG).show();
                    }

                }
            });

            RequestQueue rQueue = Volley.newRequestQueue(TestActivity.this);
            rQueue.add(request);
        }else{
    Toast.makeText(TestActivity.this, " الرجاء ادخال رقم المحمول صحيح ", Toast.LENGTH_SHORT).show();

}
            }

        else

    {

        locationTrack.showSettingsAlert();
    }
        }
    }
});
    }








    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
//        if (mGoogleApiClient != null) {
//            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (com.google.android.gms.location.LocationListener) TestActivity.this);
//        }
    }

    @Override
    public void onMapReady(final GoogleMap googleMap)
    {
//        Log.d("refresh", "onCreate: refresh1234");
        mGoogleMap=googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
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
        LatLng latLng = new LatLng(latitude, longitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mGoogleMap.clear();
        mGoogleMap.addMarker(markerOptions);
        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
//        latitude=location.getLatitude();
//        longitude=location.getLongitude();



        // Setting a click event handler for the map
        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

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
                mGoogleMap.clear();

                // Animating to the touched position
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Placing a marker on the touched position
                mGoogleMap.addMarker(markerOptions);
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
                Manifest.permission.ACCESS_FINE_LOCATION)
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




//
//        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(latLng);
//        markerOptions.title("Current Position");
//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
//        mGoogleMap.clear();
//        mGoogleMap.addMarker(markerOptions);
//        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
//        latitude=location.getLatitude();
//        longitude=location.getLongitude();











//        move map camera
//        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17.0f));
//        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
//        mGoogleMap.animateCamera( CameraUpdateFactory.zoomTo( 17.0f ) );
//        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Overridef
//            public boolean onMarkerClick(Marker marker) {
//                LatLng position = marker.getPosition();
//
//                Toast.makeText(
//                        TestActivity.this,
//                        "Lat " + position.latitude + " "
//                                + "Long " + position.longitude,
//                        Toast.LENGTH_LONG).show();
//                return true;
//            }
//        });
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

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
                                ActivityCompat.requestPermissions(TestActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
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
//                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    @Override
    protected void onRestart() {
        super.onRestart();
//        Log.d("onResume", "onResume: onResume111");
//        this.recreate();
//        Intent i =new Intent(TestActivity.this,TestActivity.class);
//        startActivity(i);
//        if(mGoogleMap != null){ //prevent crashing if the map doesn't exist yet (eg. on starting activity)
////            mGoogleMap.clear();
//
//            // add markers from database to the map
//
//            GpsTracker1 gps12 = new GpsTracker1(TestActivity.this);
//
//            if(gps12.canGetLocation()) {
//
//                if (String.valueOf(gps12.getLatitude()).length() < 5) {
//// this.recreate();
//                } else {
//                    latitude=gps12.getLatitude();
//                    longitude=gps12.getLongitude();
//                    LatLng latLng = new LatLng(latitude, longitude);
//                    MarkerOptions markerOptions = new MarkerOptions();
//                    markerOptions.position(latLng);
//                    markerOptions.title("Current Position");
//                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
//                    mGoogleMap.clear();
//                    mGoogleMap.addMarker(markerOptions);
//                    mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
//                }
//            }
////                    }
//            else
//            {
//                // can't get location
//                // GPS or Network is not enabled
//                // Ask user to enable GPS/network in settings
//                gps12.showSettingsAlert();
//            }
//
//
//
//        }
    }
    @Override
    public void onResume(){
        super.onResume();
        Intent intent = getIntent();

//        if (intent.hasExtra("login")) {
//            Log.d("onResume", "onResume: onResume111");
//        } else {
//            Log.d("onResume", "onResume: onResume112");
//            Intent i=new Intent(TestActivity.this,TestActivity.class);
//            startActivity(i);
//        }
        if(mGoogleMap != null){ //prevent crashing if the map doesn't exist yet (eg. on starting activity)
//            mGoogleMap.clear();

            // add markers from database to the map

            GpsTracker1 gps12 = new GpsTracker1(TestActivity.this);

            if(gps12.canGetLocation()) {

                if (String.valueOf(gps12.getLatitude()).length() < 5) {

//                    Log.d("onResume", "onResume: onResume112");
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                } else {
                    latitude=gps12.getLatitude();
                    longitude=gps12.getLongitude();
                    LatLng latLng = new LatLng(latitude, longitude);
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title("Current Position");
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                    mGoogleMap.clear();
                    mGoogleMap.addMarker(markerOptions);
                    mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
                }
            }
//                    }
            else
            {
                // can't get location
                // GPS or Network is not enabled
                // Ask user to enable GPS/network in settings
                gps12.showSettingsAlert();
            }



        }
    }

    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
