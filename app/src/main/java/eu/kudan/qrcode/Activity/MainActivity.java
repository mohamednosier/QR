package eu.kudan.qrcode.Activity;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import eu.kudan.qrcode.R;

public class MainActivity extends AppCompatActivity {
    KProgressHUD hud1;
    protected LocationManager locationManager;
    protected LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//        Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
        //Scan Button
        Button buttonBarCodeScan = findViewById(R.id.buttonScan);
        buttonBarCodeScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //initiate scan with our custom scan activity
                new IntentIntegrator(MainActivity.this).setCaptureActivity(ScannerActivity.class).initiateScan();
            }
        });


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
                showResultDialogue(result.getContents());
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    //method to construct dialogue with scan results
    public void showResultDialogue(final String result) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Scan Result")
                .setMessage("Scanned result is " + result)
                .setPositiveButton("Scan result", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("Scan Result", result);
                        clipboard.setPrimaryClip(clip);
//                        Toast.makeText(MainActivity.this, "Result copied to clipboard", Toast.LENGTH_SHORT).show();


                        hud1 = KProgressHUD.create(MainActivity.this)
                                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                .setAnimationSpeed(2)
                                .setDimAmount(0.5f)
                                .show();

                        String url = "http://192.168.1.148:8080/malyan_nfc/webresources/malyan_nfc.farmer/select_specific_qr/" + result;

                        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(final String s) {


                                new Handler(Looper.getMainLooper()).post(new Runnable() { // Tried new Handler(Looper.myLopper()) also
                                    @Override
                                    public void run() {
                                        if (s.length() < 10) {

                                            Toast.makeText(MainActivity.this, "لا يستطيع الحصول علي qr", Toast.LENGTH_LONG).show();
                                            hud1.dismiss();
                                        } else {

                                            try {

                                                JSONArray jArray = new JSONArray(s);
                                                for (int i = 0; i < 1; i++) {
                                                    JSONObject obj = new JSONObject();
                                                    obj = jArray.getJSONObject(i);
//                                                    String statues = obj.getString("p1");
                                                    Toast.makeText(MainActivity.this, "result " + obj.getString("p1"), Toast.LENGTH_LONG).show();
                                                    if (Integer.parseInt(obj.getString("p1")) == 0) {
//                                                    Toast.makeText(MainActivity.this, "true", Toast.LENGTH_LONG).show();

                                                        String url1 = "http://192.168.1.74:8080/malyan_nfc/webresources/malyan_nfc.farmer/update_qr/" + result + "/1";

                                                        StringRequest request1 = new StringRequest(Request.Method.GET, url1, new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(final String s) {
                                                                new Handler(Looper.getMainLooper()).post(new Runnable() { // Tried new Handler(Looper.myLopper()) also
                                                                    @Override
                                                                    public void run() {
                                                                        if (s.equals("false")) {

                                                                            Toast.makeText(MainActivity.this, "هناك مشكله", Toast.LENGTH_LONG).show();
                                                                            hud1.dismiss();
                                                                        } else {
                                                                            Toast.makeText(MainActivity.this, "تم ", Toast.LENGTH_LONG).show();
                                                                            hud1.dismiss();
                                                                        }
                                                                    }
                                                                });


                                                            }

                                                        }, new Response.ErrorListener() {
                                                            @Override
                                                            public void onErrorResponse(VolleyError volleyError) {
                                                                System.out.println("" + volleyError);
                                                                hud1.dismiss();
                                                            }
                                                        });

                                                        RequestQueue rQueue1 = Volley.newRequestQueue(MainActivity.this);
                                                        rQueue1.add(request1);
                                                    } else if (Integer.parseInt(obj.getString("p1")) == 1) {
                                                        Toast.makeText(MainActivity.this, "مستخدم من قبل", Toast.LENGTH_LONG).show();
                                                        hud1.dismiss();
                                                    }
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }


//                                            hud1.dismiss();
                                        }
                                    }
                                });


                            }

                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                System.out.println("" + volleyError);

                            }
                        });

                        RequestQueue rQueue = Volley.newRequestQueue(MainActivity.this);
                        rQueue.add(request);


                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);

                            // TODO: Consider calling
                            //    Activity#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for Activity#requestPermissions for more details.
                            return;
                        }
                    }

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
