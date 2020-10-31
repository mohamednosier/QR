package eu.kudan.qrcode.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.kaopiz.kprogresshud.KProgressHUD;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import eu.kudan.qrcode.R;

public class SplashActivity extends AppCompatActivity {
    KProgressHUD hud1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        String d1="2020-06-24";
        Date date1 = null;
        try {
            date1 = dateFormat.parse(d1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        Date date2 = dateFormat.parse(d2)
        Date date = new Date();

        String date11 = dateFormat.format(date);
        if ( date.after(date1)) {
//            Log.d("onCreate: 45", "onCreate: 45 " + dateFormat.format(date));
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }
        else{
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

//                hud1 = KProgressHUD.create(SplashActivity.this)
//                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
//                        .setAnimationSpeed(2)
//                        .setDimAmount(0.5f)
//                        .show();
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                i.putExtra("content", "splash");
                startActivity(i);
//                JSONObject jsonBodyObj = new JSONObject();
//                 try{
//
//                    jsonBodyObj.put("username", "01064899720");
//                }catch (JSONException e){
//                    e.printStackTrace();
//                }
//                final String requestBody = jsonBodyObj.toString();
//                String url = "http://192.168.1.110:8080/nfc/webresources/phone/select_specific_phone1";
//
//                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(final String s) {
//
//
//                        new Handler(Looper.getMainLooper()).post(new Runnable() { // Tried new Handler(Looper.myLopper()) also
//                            @Override
//                            public void run() {
//
//
//                                Toast.makeText(SplashActivity.this, "s  "+s, Toast.LENGTH_LONG).show();
//                                Log.d("SplashActivity", "run: SplashActivity  "+s);
//                                hud1.dismiss();
//
//                            }
//                        });
//
//
//                    }
//
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
////                    System.out.println("volleyError " + error);
////                    Toast.makeText(SplashActivity.this, " Please try again may be aproblem at network", Toast.LENGTH_LONG).show();
//                        hud1.dismiss();
//                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                            Toast.makeText(SplashActivity.this, "خطأ في مهلة الشبكة", Toast.LENGTH_LONG).show();
//                        } else if (error instanceof AuthFailureError) {
//                            Toast.makeText(SplashActivity.this, "خطأ فشل ", Toast.LENGTH_LONG).show();
//                        } else if (error instanceof ServerError) {
//                            Toast.makeText(SplashActivity.this, "خطأ في الشبكه", Toast.LENGTH_LONG).show();
//                        } else if (error instanceof NetworkError) {
//                            Toast.makeText(SplashActivity.this, "خطأ في الشبكه", Toast.LENGTH_LONG).show();
//                        } else if (error instanceof ParseError) {
//                            Toast.makeText(SplashActivity.this, "خطأ في التحويل ", Toast.LENGTH_LONG).show();
//                        }else{
//                            Toast.makeText(SplashActivity.this, "من فضل حاول مره اخري", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                }){
//
////                    @Override
////                    protected Map<String, String> getParams()throws AuthFailureError{
////                        Map<String, String>  params = new HashMap<String, String>();
////                        params.put("username", "01064899720");
////
////
////                        return params;
////                    }
//                    @Override
//                    public Map<String, String> getHeaders() throws AuthFailureError {
//                        HashMap<String, String> headers = new HashMap<String, String>();
//                        headers.put("Content-Type", "application/json; charset=utf-8");
//                        return headers;
//                    }
//                    @Override    public byte[] getBody() {
//                        try {
//                            return requestBody == null ? null : requestBody.getBytes("utf-8");
//                        } catch (UnsupportedEncodingException uee) {
//                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
//                                    requestBody, "utf-8");
//                            return null;
//                        }
//                    }
//
//                };
//
//                RequestQueue rQueue = Volley.newRequestQueue(SplashActivity.this);
//                rQueue.add(request);


//                String url = "http://192.168.1.110:8080/nfc/webresources/phone/select_specific_phone/shoura/01064899720";
//
//                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(final String s) {
//
//
//                        new Handler(Looper.getMainLooper()).post(new Runnable() { // Tried new Handler(Looper.myLopper()) also
//                            @Override
//                            public void run() {
//
//
////                                    Toast.makeText(SplashActivity.this, "هذا الرقم غير مسجل انتقل الي شاشه التسجيل ", Toast.LENGTH_LONG).show();
//
//                                    Log.d("SplashActivity", "run: SplashActivity  "+s);
//                                    hud1.dismiss();
//
//                                    hud1.dismiss();
//
//
//                            }
//                        });
//
//
//                    }
//
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
////                    System.out.println("volleyError " + error);
////                    Toast.makeText(SplashActivity.this, " Please try again may be aproblem at network", Toast.LENGTH_LONG).show();
//                        hud1.dismiss();
//                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                            Toast.makeText(SplashActivity.this, "خطأ في مهلة الشبكة", Toast.LENGTH_LONG).show();
//                        } else if (error instanceof AuthFailureError) {
//                            Toast.makeText(SplashActivity.this, "خطأ فشل ", Toast.LENGTH_LONG).show();
//                        } else if (error instanceof ServerError) {
//                            Toast.makeText(SplashActivity.this, "خطأ في الشبكه", Toast.LENGTH_LONG).show();
//                        } else if (error instanceof NetworkError) {
//                            Toast.makeText(SplashActivity.this, "خطأ في الشبكه", Toast.LENGTH_LONG).show();
//                        } else if (error instanceof ParseError) {
//                            Toast.makeText(SplashActivity.this, "خطأ في التحويل ", Toast.LENGTH_LONG).show();
//                        }else{
//                            Toast.makeText(SplashActivity.this, "من فضل حاول مره اخري", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//
//                RequestQueue rQueue = Volley.newRequestQueue(SplashActivity.this);
//                rQueue.add(request);
            }
        }, 3000);


    }

    }
}
