package eu.kudan.qrcode.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.kaopiz.kprogresshud.KProgressHUD;

import eu.kudan.qrcode.Utils.Constants;
import eu.kudan.qrcode.R;
import eu.kudan.qrcode.Utils.GpsTracker1;

public class LoginActivity extends AppCompatActivity {
    EditText phone;
    Button login,register;
    public static int loyality_point;
//    public static String phone_number;


    KProgressHUD hud1;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String Name = "nameKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phone = (EditText) findViewById(R.id.phone_number);
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(Name)) {
            phone.setText(sharedpreferences.getString(Name, ""));
        }
        if(getIntent().getStringExtra("content").equals("splash")){
        if (sharedpreferences.contains(Name)) {
            phone.setText(sharedpreferences.getString(Name, ""));
            hud1 = KProgressHUD.create(LoginActivity.this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                    .show();
            Log.d("error is ", "run: error is 11" + sharedpreferences.getString(Name, ""));
            String url = "http://3.218.130.218:8080/nfc/webresources/phone/select_specific_phone/shoura/" + sharedpreferences.getString(Name, "");

            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(final String s) {


                    new Handler(Looper.getMainLooper()).post(new Runnable() { // Tried new Handler(Looper.myLopper()) also
                        @Override
                        public void run() {
                            if (s.equals("errormmmnmnmnmnm") || s.equals("error")) {

                                Toast.makeText(LoginActivity.this, "هذا الرقم غير مسجل انتقل الي شاشه التسجيل ", Toast.LENGTH_LONG).show();
                                hud1.dismiss();
                            } else {

                                Log.d("error is ", "run: error is " + s);
                                final String[] parts1 = s.split("mmmnmnmnmnm");
//                                                                mnmnmnmnmnmansnam
                                Constants.phone_loyality = parts1[0];
                                Constants.phone_id = parts1[1];


                                Constants.phone_name = sharedpreferences.getString(Name, "");
                                Log.d("Constants.phone_name", "Constants.phone_name  onCreate: " + Constants.phone_name);
//                                Constants.phone_loyality=s;
                                Intent i = new Intent(LoginActivity.this, QRActivity.class);
                                startActivity(i);
                                hud1.dismiss();

                            }
                        }
                    });


                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                    System.out.println("volleyError " + error);
//                    Toast.makeText(LoginActivity.this, " Please try again may be aproblem at network", Toast.LENGTH_LONG).show();
                    hud1.dismiss();
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(LoginActivity.this, "خطأ في مهلة الشبكة", Toast.LENGTH_LONG).show();
                    } else if (error instanceof AuthFailureError) {
                        Toast.makeText(LoginActivity.this, "خطأ فشل ", Toast.LENGTH_LONG).show();
                    } else if (error instanceof ServerError) {
                        Toast.makeText(LoginActivity.this, "خطأ في الشبكه", Toast.LENGTH_LONG).show();
                    } else if (error instanceof NetworkError) {
                        Toast.makeText(LoginActivity.this, "خطأ في الشبكه", Toast.LENGTH_LONG).show();
                    } else if (error instanceof ParseError) {
                        Toast.makeText(LoginActivity.this, "خطأ في التحويل ", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "من فضل حاول مره اخري", Toast.LENGTH_LONG).show();
                    }
                }
            });

            RequestQueue rQueue = Volley.newRequestQueue(LoginActivity.this);
            rQueue.add(request);
        }
    }else

    {
    }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                GpsTracker1 gps12 = new GpsTracker1(LoginActivity.this);
////                    if(gps.canGetLocation()){
//                if(gps12.canGetLocation()) {
////            Toast.makeText(TestActivity.this, "من فضلك انتظر ثواني وحاول مره اخري", Toast.LENGTH_LONG).show();
//                    Log.d("refresh", "onCreate: refresh12341");
//                    if (String.valueOf(gps12.getLatitude()).length() < 5) {
////                Toast.makeText(TestActivity.this, "من فضلك انتظر ثواني ", Toast.LENGTH_LONG).show();
//
////                        LoginActivity.this.recreate();
//                    } else {
                        Intent i=new Intent(LoginActivity.this,TestActivity.class);
                        i.putExtra("login","login");
                        startActivity(i);
                        finish();
////                        latitude=gps12.getLatitude();
////                        longitude=gps12.getLongitude();
////                        Toast.makeText(TestActivity.this, "latitiude1 is " + gps.getLatitude() + "  longitude is  " + gps.getLongitude(), Toast.LENGTH_LONG).show();
////                        Toast.makeText(TestActivity.this, "lati is " + latitude + "  longi is  " + longitude, Toast.LENGTH_LONG).show();
////                phone.setVisibility(View.VISIBLE);
////                LatLng latLng = new LatLng(latitude, longitude);
////                MarkerOptions markerOptions = new MarkerOptions();
////                markerOptions.position(latLng);
////                markerOptions.title("Current Position");
////                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
//////                mGoogleMap.clear();
////                mGoogleMap.addMarker(markerOptions);
////                mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
////                Intent
//                    }
//                }
////                    }
//                else
//                {
//                    // can't get location
//                    // GPS or Network is not enabled
//                    // Ask user to enable GPS/network in settings
//                    gps12.showSettingsAlert();
//                }
//
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


if(phone.getText().toString().length()!=11) {
    Toast.makeText(LoginActivity.this, " الرجاء ادخال رقم المحمول لايتعدي ولا يقل عن 11 رقم ", Toast.LENGTH_SHORT).show();
}else{
    hud1 = KProgressHUD.create(LoginActivity.this)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setAnimationSpeed(2)
            .setDimAmount(0.5f)
            .show();
                String url = "http://3.218.130.218:8080/nfc/webresources/phone/select_specific_phone/shoura/" + phone.getText().toString();

                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String s) {


                        new Handler(Looper.getMainLooper()).post(new Runnable() { // Tried new Handler(Looper.myLopper()) also
                            @Override
                            public void run() {
                                if (s.equals("errormmmnmnmnmnm")) {

                                    Toast.makeText(LoginActivity.this, "هذا الرقم غير مسجل انتقل الي شاشه التسجيل ", Toast.LENGTH_LONG).show();
                                    hud1.dismiss();
                                } else {
//                                    Log.d("error is ", "run: error is "+s);
//                                    Toast.makeText(LoginActivity.this, "error is "+s, Toast.LENGTH_LONG).show();

                                    Constants.phone_name=phone.getText().toString();
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString(Name, phone.getText().toString());
                                    editor.commit();
//                                   Constants.phone_loyality=s;
                                    final String[] parts1 = s.split("mmmnmnmnmnm");
//                                                                mnmnmnmnmnmansnam
                                    Constants.phone_loyality=parts1[0];
                                    Constants.phone_id =parts1[1];
                                    Log.d("Constants.phone_name", "Constants.phone_name: "+Constants.phone_name);
                                    Log.d("Constants._loyality", "Constants.phone_loyality: "+Constants.phone_loyality);
                                    Intent i=new Intent(LoginActivity.this,QRActivity.class);
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
//                        Toast.makeText(LoginActivity.this, "Please Try again", Toast.LENGTH_LONG).show();
                        hud1.dismiss();
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(LoginActivity.this, "خطأ في مهلة الشبكة", Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(LoginActivity.this, "خطأ فشل ", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(LoginActivity.this, "خطأ في الشبكه", Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(LoginActivity.this, "خطأ في الشبكه", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(LoginActivity.this, "خطأ في التحويل ", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(LoginActivity.this, "من فضل حاول مره اخري", Toast.LENGTH_LONG).show();
                        }

                    }
                });

                RequestQueue rQueue = Volley.newRequestQueue(LoginActivity.this);
                rQueue.add(request);

            }}
        });
    }
//    @Override
//    protected void onRestart() {
//        super.onRestart();
////        this.recreate();
//        Intent i =new Intent(LoginActivity.this,TestActivity.class);
//        startActivity(i);
//    }
@Override
public void onBackPressed(){
    Intent a = new Intent(Intent.ACTION_MAIN);
    a.addCategory(Intent.CATEGORY_HOME);
    a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(a);
}
}
