package eu.kudan.qrcode.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
//import org.apache.commons.io.IOUtils;

import eu.kudan.qrcode.Utils.Constants;
import eu.kudan.qrcode.R;

public class QRActivity extends AppCompatActivity {
    int x;
    Button confirm_product, login_product, testpost, my_wallet, callus, farm_log, new_farm;
    //    KProgressHUD hud1;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    public static ArrayList<String> sub_batch = new ArrayList<String>();
    public static int[] your_loyality = {0};
    static SharedPreferences sharedPreferences;
    KProgressHUD hud1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        sharedPreferences = getSharedPreferences("qr", MODE_PRIVATE);
        confirm_product = (Button) findViewById(R.id.confirm_product);
        login_product = (Button) findViewById(R.id.login_product);
        testpost = (Button) findViewById(R.id.testpost);
        my_wallet = (Button) findViewById(R.id.my_wallet);
        farm_log = (Button) findViewById(R.id.farm_log);
        new_farm = (Button) findViewById(R.id.new_farm);
        TextView my_loyality = (TextView) findViewById(R.id.my_loyality);
        TextView call = (TextView) findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWhatsApp("+201114442251", "لدى سؤال بخصوص التطبيق");
            }
        });
        my_loyality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hud1 = KProgressHUD.create(QRActivity.this)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setAnimationSpeed(2)
                        .setDimAmount(0.5f)
                        .show();
                String url = "http://3.218.130.218:8080/nfc/webresources/phone/get_loyality_based_on_user_id/" + Constants.phone_id;

                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String s) {


                        new Handler(Looper.getMainLooper()).post(new Runnable() { // Tried new Handler(Looper.myLopper()) also
                            @Override
                            public void run() {
                                if (s.equals("error")) {

                                    Toast.makeText(QRActivity.this, "لايستطيع الحصول ع البيانات ", Toast.LENGTH_LONG).show();
                                    hud1.dismiss();
                                } else {


                                    Toast.makeText(QRActivity.this, "محفظتك تحتوي علي " + s + " نقاط", Toast.LENGTH_LONG).show();
//
                                    hud1.dismiss();

                                }
                            }
                        });


                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("volleyError " + error);
//                    Toast.makeText(getActivity(), " Please try again may be aproblem at network", Toast.LENGTH_LONG).show();
                        hud1.dismiss();
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(QRActivity.this, "خطأ في مهلة الشبكة", Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(QRActivity.this, "خطأ فشل ", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(QRActivity.this, "خطأ في الشبكه", Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(QRActivity.this, "خطأ في الشبكه", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(QRActivity.this, "خطأ في التحويل ", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(QRActivity.this, "من فضل حاول مره اخري", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                RequestQueue rQueue = Volley.newRequestQueue(QRActivity.this);
                rQueue.add(request);
            }


        });
        new_farm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(QRActivity.this, NewFarmActivity.class);
                startActivity(i);
            }
        });
        callus = (Button) findViewById(R.id.callus);

//        checkConnection();
        callus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                TelephonyManager tMgr = (TelephonyManager)getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
//                @SuppressLint("MissingPermission") String mPhoneNumber = tMgr.getLine1Number();
                openWhatsApp("+201114442251", "أود الحصول على خدمة الإستشارات الزراعية");
            }
        });
        farm_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(QRActivity.this, DisplayQRPurchacedUsedActivity.class);
                startActivity(i);
            }
        });
        my_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String url = "http://3.218.130.218:8080/nfc/webresources/phone/select_specific_phone/public/" + Constants.phone_name;

                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String s) {


                        new Handler(Looper.getMainLooper()).post(new Runnable() { // Tried new Handler(Looper.myLopper()) also
                            @Override
                            public void run() {
                                if (s.equals("error")) {

                                    Toast.makeText(QRActivity.this, "هذا الرقم غير مسجل انتقل الي شاشه التسجيل ", Toast.LENGTH_LONG).show();

                                } else {
                                    final String[] parts1 = s.split("mmmnmnmnmnm");
//                                                                mnmnmnmnmnmansnam
                                    Constants.phone_loyality = parts1[0];
                                    Constants.phone_id = parts1[1];
                                    Constants.phone_loyality = parts1[0];
                                    Toast.makeText(QRActivity.this, "محفظتك تحتوي علي " + parts1[0] + " نقاط", Toast.LENGTH_LONG).show();

                                }
                            }
                        });


                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("volleyError " + error);
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(QRActivity.this, "خطأ في مهلة الشبكة", Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(QRActivity.this, "خطأ فشل ", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(QRActivity.this, "خطأ في الشبكه", Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(QRActivity.this, "خطأ في الشبكه", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(QRActivity.this, "خطأ في التحويل ", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(QRActivity.this, "من فضل حاول مره اخري", Toast.LENGTH_LONG).show();
                        }


                    }
                });

                RequestQueue rQueue = Volley.newRequestQueue(QRActivity.this);
                rQueue.add(request);


            }
        });
        testpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        login_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                x = 1;
                new IntentIntegrator(QRActivity.this).setCaptureActivity(ScannerActivity.class).initiateScan();
            }
        });
        confirm_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                x = 0;
                new IntentIntegrator(QRActivity.this).setCaptureActivity(ScannerActivity.class).initiateScan();
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
                Toast.makeText(this, "تم الغاء الفحص", Toast.LENGTH_LONG).show();
            } else {
                if (x == 1) {
//show dialogue with result
                    showResultDialogue1(result.getContents());

                } else if (x == 0) {

                    connect(result.getContents());
//                    showResultDialogue(result.getContents());
                }
//                Log.d("showResultDialogue", "onActivityResult:showResultDialogue "+result.getContents());
//                //show dialogue with result
//                showResultDialogue(result.getContents());
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    //method to construct dialogue with scan results
    public void showResultDialogue1(final String result) {
        Log.d("result  ", "result  " + result);
        final String[] parts = result.split("&&");
        int replace = result.length() - result.replace("&&", "").length();
        System.out.println("replace = " + parts.length);
//        Log.d("replace = ", "replace = : "+parts[0]);
//        Log.d("replace = ", "replace = : "+parts[1]);
//        Log.d("replace = ", "replace = : "+parts[2]);
//        Log.d("replace = ", "replace = : "+parts[3]);
//        Log.d("replace = ", "replace = : "+parts[4].substring(1));


        if (parts.length == 3) {
//            if ( parts[1].contains("http://bit.ly/2xnoFZR")) {

//                String url = "http://3.218.130.218:8080/nfc/webresources/phone/select_specific_user_loyality/" + parts[2].substring(1);
            JSONObject jsonBodyObj = new JSONObject();
            try {

                jsonBodyObj.put("text", parts[2]);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            final String requestBody = jsonBodyObj.toString();
            String url = "http://3.218.130.218:8080/nfc/webresources/phone/select_specific_user_loyality1";

            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(final String s) {


                    new Handler(Looper.getMainLooper()).post(new Runnable() { // Tried new Handler(Looper.myLopper()) also
                        @Override
                        public void run() {
                            Log.d("ggggg", "run:ggggg " + s);
//                                Toast.makeText(QRActivity.this, "s " + s, Toast.LENGTH_LONG).show();
                            if (s.equals("error")) {

                                Toast.makeText(QRActivity.this, "هذا QR غير مسجل من قبل ", Toast.LENGTH_LONG).show();

                            } else {

                                if (s.equals("0")) {
                                    Toast.makeText(QRActivity.this, "تم تسجيل ال QR  ", Toast.LENGTH_LONG).show();
                                } else if (s.equals("1")) {
                                    Toast.makeText(QRActivity.this, "هذا QR  مسجل من قبل ", Toast.LENGTH_LONG).show();
                                } else if (s.equals("2")) {
                                    Toast.makeText(QRActivity.this, "هذا QR غير مسجل من قبل ", Toast.LENGTH_LONG).show();
                                }


                            }
                        }
                    });


                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("volleyError " + error);
//                    Toast.makeText(LoginActivity.this, " Please try again may be aproblem at network", Toast.LENGTH_LONG).show();

                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(QRActivity.this, "خطأ في مهلة الشبكة", Toast.LENGTH_LONG).show();
                    } else if (error instanceof AuthFailureError) {
                        Toast.makeText(QRActivity.this, "خطأ فشل ", Toast.LENGTH_LONG).show();
                    } else if (error instanceof ServerError) {
                        Toast.makeText(QRActivity.this, "خطأ في الشبكه", Toast.LENGTH_LONG).show();
                    } else if (error instanceof NetworkError) {
                        Toast.makeText(QRActivity.this, "خطأ في الشبكه", Toast.LENGTH_LONG).show();
                    } else if (error instanceof ParseError) {
                        Toast.makeText(QRActivity.this, "خطأ في التحويل ", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(QRActivity.this, "من فضل حاول مره اخري", Toast.LENGTH_LONG).show();
                    }
                }
            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }

                @Override
                public byte[] getBody() {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                                requestBody, "utf-8");
                        return null;
                    }
                }

            };

            RequestQueue rQueue = Volley.newRequestQueue(QRActivity.this);
            rQueue.add(request);
//            }else{
//                Toast.makeText(QRActivity.this, "هذا QR غير مسجل من قبل ", Toast.LENGTH_LONG).show();
//
//            }
        } else {
            Toast.makeText(QRActivity.this, "هذا QR غير مسجل من قبل ", Toast.LENGTH_LONG).show();
            ;
        }
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
//    public void showResultDialogue2(final String result) {
//        Toast.makeText(QRActivity.this, "هذا المنتج  اصلي", Toast.LENGTH_LONG).show();
//
//    }
//    public void showResultDialogue3(final String results) {
//        Toast.makeText(QRActivity.this, "هذا المنتج   غير اصلي", Toast.LENGTH_LONG).show();
//
//    }
//    public void showResultDialogue4(final String results) {
//        Toast.makeText(QRActivity.this, "Congratulation You earned 5 loyality point", Toast.LENGTH_LONG).show();
//
//    }

    //    public static void Post_JSON() {
//        String query_url = "http://3.218.130.218:8080/nfc/webresources/login/login1";
//        String json = "{\n" +
//                "\"username\" : \"mohamed3\",\n" +
//                "\"password\" : \"123\"\n" +
//                "}";
//        try {
//            URL url = new URL(query_url);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setConnectTimeout(5000);
//            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
//            conn.setRequestProperty("Accept", "application/json; charset=UTF-8");
//            conn.setDoOutput(true);
//            conn.setDoInput(true);
//            conn.setRequestMethod("POST");
//            OutputStream os = conn.getOutputStream();
//            os.write(json.getBytes("UTF-8"));
//            os.close();
//            // read the response
//
//
//            int responseCode1=conn.getResponseCode();
//            System.out.println("responseCode1 Code :: " + responseCode1);
//        InputStream in = new BufferedInputStream(conn.getInputStream());
//            int responseCode = conn.getResponseCode();
//            System.out.println("Response Code :: " + responseCode);
////            if (responseCode == HttpURLConnection.HTTP_OK) { // connection ok
//                BufferedReader in1 = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                String inputLine;
//                StringBuffer response = new StringBuffer();
//
//                while ((inputLine = in1.readLine()) != null) {
//                    response.append(inputLine);
//                }
//                System.out.println("Data Received: " + response.toString());
//            JSONObject myResponse1 = new JSONObject(response.toString());
//            System.out.println("encrypted_key- "+myResponse1.getString("encrypted_key"));
//            System.out.println("page- "+myResponse1.getInt("id"));
////            }
//            String result = IOUtils.toString(in, "UTF-8");
//            System.out.println("result "+result);
//            System.out.println("result after Reading JSON Response");
//            JSONObject myResponse = new JSONObject(result);
//            System.out.println("jsonrpc- "+myResponse.getString("encrypted_key"));
//            System.out.println("page- "+myResponse.getInt("id"));
////            System.out.println("result- "+myResponse.getString("result"));
//            in.close();
//            conn.disconnect();
//        } catch (Exception e) {
//            System.out.println("volleyError " +e);
//
//        }
//    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void openWhatsApp(String numero, String mensaje) {

        try {
            PackageManager packageManager = QRActivity.this.getPackageManager();
            Intent i = new Intent(Intent.ACTION_VIEW);
            String url = "https://api.whatsapp.com/send?phone=" + numero + "&text=" + URLEncoder.encode(mensaje, "UTF-8");
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                startActivity(i);
            } else {
//                KToast.errorToast(getActivity(), getString(R.string.no_whatsapp), Gravity.BOTTOM, KToast.LENGTH_SHORT);
                Toast.makeText(QRActivity.this, "لايوجد واتس اب", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.e("ERROR WHATSAPP", e.toString());
            Toast.makeText(QRActivity.this, "لايوجد واتس اب", Toast.LENGTH_LONG).show();
//            KToast.errorToast(getActivity(), , Gravity.BOTTOM, KToast.LENGTH_SHORT);
        }

    }
    // Method to manually check connection status
//    private void checkConnection() {
//        boolean isConnected = ConnectivityReceiver.isConnected();
//        showSnack(isConnected);
//        my_wallet();
//    }

//    // Showing the status in Snackbar
//    private void showSnack(boolean isConnected) {
//        String message;
//        int color;
//        if (isConnected) {
//            message = "Good! Connected to Internet";
//            color = Color.WHITE;
//        } else {
//            message = "Sorry! Not connected to internet";
//            color = Color.RED;
//        }
//
//        Snackbar snackbar = Snackbar
//                .make(findViewById(R.id.fab), message, Snackbar.LENGTH_LONG);
//
//        View sbView = snackbar.getView();
//        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
//        textView.setTextColor(color);
//        snackbar.show();
//    }

    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
//        MyApplication.getInstance().setConnectivityListener(this);
    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
//    @Override
//    public void onNetworkConnectionChanged(boolean isConnected) {
////        showSnack(isConnected);
//        my_wallet();
//    }
    public void connect(String result) {
        Log.d("result  ", "result  " + result);
        final String[] parts = result.split("&&");
        int replace = result.length() - result.replace("&&", "").length();
        System.out.println("replace = " + replace);
//        Log.d("replace = ", "replace = : " + parts[0]);
//        Log.d("replace = ", "replace = : " + parts[1]);
//        Log.d("replace = ", "replace = : " + parts[2]);
//        Log.d("replace = ", "replace = : " + parts[3]);
//        Log.d("replace = ", "replace = : " + parts[4].substring(1));


        if (parts.length == 3) {
//        if ( parts[1].contains("http://bit.ly/2xnoFZR")) {
////GEtCode
//    3.218.130.218

            final String[] product_description = {""};
            final String[] product_name = {""};
            final String[] product_url = {""};
            final String[] production_profile_id = {""};


            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date();
            hud1 = KProgressHUD.create(QRActivity.this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                    .show();

//            String url = "http://3.218.130.218:8080/nfc/webresources/transaction/register_products_purchaced/" + parts[1]+"/"+parts[2].substring(0,parts[2].indexOf('/'))+"/"+formatter.format(date)+"/"+Constants.phone_id;

            JSONObject jsonBodyObj = new JSONObject();
            try {
                jsonBodyObj.put("organization_id", parts[1]);
                jsonBodyObj.put("text", parts[2]);
                jsonBodyObj.put("scanned_date", formatter.format(date));
                jsonBodyObj.put("user_id", Constants.phone_id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            final String requestBody = jsonBodyObj.toString();
            String url = "http://3.218.130.218:8080/nfc/webresources/transaction/register_products_purchaced1";
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                //            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(final String s) {


                    new Handler(Looper.getMainLooper()).post(new Runnable() { // Tried new Handler(Looper.myLopper()) also
                        @Override
                        public void run() {
                            if (s.equals("error")) {


                                Intent i = new Intent(QRActivity.this, DisolayQRDetailsActivity.class);
                                i.putExtra("link", "un known");
                                i.putExtra("organization", "un known");
                                i.putExtra("product", "un known");
                                i.putExtra("qualified", "not qualified");
                                i.putExtra("description", "un known");
                                startActivity(i);


                                hud1.dismiss();
                            } else if (s.equals("1")) {

                                Toast.makeText(QRActivity.this, "تم شراء هذا المنتج من قبل", Toast.LENGTH_LONG).show();

                                Intent i = new Intent(QRActivity.this, DisolayQRDetailsActivity.class);
                                i.putExtra("link", "");
                                i.putExtra("organization", "");
                                i.putExtra("product", "");
                                i.putExtra("qualified", "is used before");
                                i.putExtra("description", "");
                                startActivity(i);

                                hud1.dismiss();
                            } else {
                                Toast.makeText(QRActivity.this, "تم شراء هذا المنتج ", Toast.LENGTH_LONG).show();

                                String s1 = fixEncoding(s);
                                Log.d("results", "run: results1  " + s1);
                                final String[] parts1 = s1.split("mnmnmnmnmnmansnam");
//                                                                mnmnmnmnmnmansnam
                                String loyality_pont = parts1[0];
                                product_name[0] = parts1[1];
                                product_description[0] = parts1[2];
                                product_url[0] = parts1[3];
                                production_profile_id[0] = parts1[4];
                                Intent i = new Intent(QRActivity.this, DisolayQRDetailsActivity.class);
                                i.putExtra("link", parts1[3]);
                                i.putExtra("organization", parts1[4]);
                                i.putExtra("product", parts1[1]);
                                i.putExtra("qualified", "qualified");
                                i.putExtra("description", parts1[2]);
//
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

                    hud1.dismiss();
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(QRActivity.this, "خطأ في مهلة الشبكة", Toast.LENGTH_LONG).show();
                    } else if (error instanceof AuthFailureError) {
                        Toast.makeText(QRActivity.this, "خطأ فشل ", Toast.LENGTH_LONG).show();
                    } else if (error instanceof ServerError) {
//                        خطأ في الشبكه
                        Toast.makeText(QRActivity.this, "خطأ في الشبكه", Toast.LENGTH_LONG).show();
                    } else if (error instanceof NetworkError) {
                        Toast.makeText(QRActivity.this, "خطأ في الشبكه", Toast.LENGTH_LONG).show();
                    } else if (error instanceof ParseError) {
                        Toast.makeText(QRActivity.this, "خطأ في التحويل ", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(QRActivity.this, "من فضل حاول مره اخري", Toast.LENGTH_LONG).show();
                    }

                }
            }) {

                //                    @Override
//                    protected Map<String, String> getParams()throws AuthFailureError{
//                        Map<String, String>  params = new HashMap<String, String>();
//                        params.put("username", "01064899720");
//
//
//                        return params;
//                    }
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }

                @Override
                public byte[] getBody() {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                                requestBody, "utf-8");
                        return null;
                    }
                }

            };

            RequestQueue rQueue = Volley.newRequestQueue(QRActivity.this);
            rQueue.add(request);


//        } else {
//            Intent i=new Intent(QRActivity.this,DisolayQRDetailsActivity.class);
//            i.putExtra("link","un known");
//            i.putExtra("organization","un known");
//            i.putExtra("product","un known");
//            i.putExtra("qualified","false");
//            i.putExtra("description","un known");
//            startActivity(i);
//        }
        } else {
//    Toast.makeText(QRActivity.this,"no 2",Toast.LENGTH_LONG).show();
            Intent i = new Intent(QRActivity.this, DisolayQRDetailsActivity.class);
            i.putExtra("link", "un known");
            i.putExtra("organization", "un known");
            i.putExtra("product", "un known");
            i.putExtra("qualified", "false");
            i.putExtra("description", "un known");
            startActivity(i);
        }
    }

    public static String fixEncoding(String response) {
        try {
            byte[] u = response.toString().getBytes(
                    "ISO-8859-1");
            response = new String(u, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        return response;
    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(QRActivity.this, LoginActivity.class);
        i.putExtra("content", "qr");
        startActivity(i);
//        Intent a = new Intent(Intent.ACTION_MAIN);
//        a.addCategory(Intent.CATEGORY_HOME);
//        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(a);
    }

    public void connect1() {

        JSONObject jsonBodyObj = new JSONObject();
        try {

            jsonBodyObj.put("username", "01064899720");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = jsonBodyObj.toString();
        String url = "http://192.168.1.14:8080/nfc/webresources/transaction/register_products_purchaced1";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(final String s) {


                new Handler(Looper.getMainLooper()).post(new Runnable() { // Tried new Handler(Looper.myLopper()) also
                    @Override
                    public void run() {


                        Toast.makeText(QRActivity.this, "s  " + s, Toast.LENGTH_LONG).show();
                        Log.d("SplashActivity", "run: SplashActivity  " + s);
                        hud1.dismiss();

                    }
                });


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                    System.out.println("volleyError " + error);
//                    Toast.makeText(SplashActivity.this, " Please try again may be aproblem at network", Toast.LENGTH_LONG).show();
                hud1.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(QRActivity.this, "خطأ في مهلة الشبكة", Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(QRActivity.this, "خطأ فشل ", Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(QRActivity.this, "خطأ في الشبكه", Toast.LENGTH_LONG).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(QRActivity.this, "خطأ في الشبكه", Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(QRActivity.this, "خطأ في التحويل ", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(QRActivity.this, "من فضل حاول مره اخري", Toast.LENGTH_LONG).show();
                }
            }
        }) {

            //                    @Override
//                    protected Map<String, String> getParams()throws AuthFailureError{
//                        Map<String, String>  params = new HashMap<String, String>();
//                        params.put("username", "01064899720");
//
//
//                        return params;
//                    }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

            @Override
            public byte[] getBody() {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                            requestBody, "utf-8");
                    return null;
                }
            }

        };

        RequestQueue rQueue = Volley.newRequestQueue(QRActivity.this);
        rQueue.add(request);
    }
}
