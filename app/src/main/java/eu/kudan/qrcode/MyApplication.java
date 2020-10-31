package eu.kudan.qrcode;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.lang.reflect.Type;
import java.util.List;

public class MyApplication extends Application {
    KProgressHUD hud;
    int farmer_id;
//    private static MyApplication mInstance;
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//
//        mInstance = this;
//    }
//
//    public static synchronized MyApplication getInstance() {
//        return mInstance;
//    }
//
//    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
//        ConnectivityReceiver.connectivityReceiverListener = listener;
//    }


    public static boolean isConnected = false;
    /**
     * To receive change in network state
     */
    private BroadcastReceiver NetworkStatusReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("Internet Connection", "Internet Connection use1");
            ConnectivityManager connMgr = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();





            ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            if (mWifi.isConnected()) {
//                Toast noInternetToast = Toast.makeText(getApplicationContext(),
//                        " available1 ", Toast.LENGTH_LONG);
//                noInternetToast.show();
            }



            if (!isConnected) {

                Log.d("Internet Connection", "Internet Connection open1");
            }else{

                final SharedPreferences sharedPreferences = getSharedPreferences("qr", MODE_PRIVATE);


//retailer
            Gson gson = new Gson();
            String json = sharedPreferences.getString("Set", "");
            if (json.isEmpty()) {
//                Toast.makeText(context, "لا يوجد qr مخزنه ", Toast.LENGTH_LONG).show();
            } else {
                Type type = new TypeToken<List<String>>() {
                }.getType();
                List<String> arrPackageData = gson.fromJson(json, type);
                for (String data : arrPackageData) {




//                    Log.d("result  ", "result  "+data);
//                    final String[] parts = data.split(",");
//                    int replace = data.length() - data.replace(",", "").length();
//                    System.out.println("replace = " + replace);
//
//
//                    if(replace==9){
//////GEtCode
//                        String url = "http://3.218.130.218:8080/nfc/webresources/transaction/get_code2/"+parts[1]+"/"+parts[6];
//                        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(final String s) {
//                                new Handler(Looper.getMainLooper()).post(new Runnable() { // Tried new Handler(Looper.myLopper()) also
//                                    @Override
//                                    public void run() {
////                                        Toast.makeText(QRActivity.this,"result is "+s,Toast.LENGTH_LONG).show();
//                                        if(s.equals("erroe")){
//                                            Toast.makeText(getApplicationContext(), "لا يستطيع الحصول علي qr", Toast.LENGTH_LONG).show();
//
////                                            Intent i=new Intent(getApplicationContext(),DisolayQRDetailsActivity.class);
////                                            i.putExtra("link","un known");
////                                            i.putExtra("organization","un known");
////                                            i.putExtra("product","un known");
////                                            i.putExtra("qualified","not qualified");
////                                            i.putExtra("description","un known");
////                                            startActivity(i);
//
//                                        }else if(s.equals("0")){
//                                            ////Update Code
//                                            String url = "http://3.218.130.218:8080/nfc/webresources/transaction/update_code1/"+parts[6]+"/1/2011-11-12s/"+parts[1];
//                                            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//                                                @Override
//                                                public void onResponse(final String s) {
//                                                    new Handler(Looper.getMainLooper()).post(new Runnable() { // Tried new Handler(Looper.myLopper()) also
//                                                        @Override
//                                                        public void run() {
//                                                            if (s.equals("false")) {
////
//                                                                Toast.makeText(getApplicationContext(), "هناك مشكله", Toast.LENGTH_LONG).show();
//
//                                                            } else {
//                                                                Toast.makeText(getApplicationContext(), "تم ", Toast.LENGTH_LONG).show();
//
//
//
//
//
//
//
//
//////GEtCode
//                                                                String url = "http://3.218.130.218:8080/nfc/webresources/transaction/get_loyality/"+parts[1]+"/"+parts[4];
//                                                                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//                                                                    @Override
//                                                                    public void onResponse(final String s1) {
//                                                                        new Handler(Looper.getMainLooper()).post(new Runnable() { // Tried new Handler(Looper.myLopper()) also
//                                                                            @Override
//                                                                            public void run() {
////                        Toast.makeText(QRActivity.this,"result is "+s,Toast.LENGTH_LONG).show();
//                                                                                if(s1.equals("false")){
//                                                                                    Toast.makeText(getApplicationContext(), "لا يستطيع الحصول علي qr", Toast.LENGTH_LONG).show();
//                                                                                }else {
//                                                                                    LoginActivity.loyality_point=LoginActivity.loyality_point+Integer.parseInt(s1);
//
//
//
//                                                                                    final int x=Integer.parseInt(Constants.phone_loyality)+Integer.parseInt(s1);
//
//                                                                                    String url = "http://3.218.130.218:8080/nfc/webresources/phone/insert_loyality/"+parts[1]+"/" + Constants.phone_name+"/"+x;
//                                                                                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//                                                                                        @Override
//                                                                                        public void onResponse(final String s) {
//
//
//                                                                                            new Handler(Looper.getMainLooper()).post(new Runnable() { // Tried new Handler(Looper.myLopper()) also
//                                                                                                @Override
//                                                                                                public void run() {
//                                                                                                    if (s.equals("error")) {
//
//                                                                                                        Toast.makeText(getApplicationContext(), "يوجد خطأ في اضافه  نقاط الاضافه", Toast.LENGTH_LONG).show();
////                                    hud1.dismiss();
//                                                                                                    } else {
//                                                                                                        Constants.phone_loyality=""+x;
//
//                                                                                                    }
//                                                                                                }
//                                                                                            });
//
//
//                                                                                        }
//
//                                                                                    }, new Response.ErrorListener() {
//                                                                                        @Override
//                                                                                        public void onErrorResponse(VolleyError volleyError) {
//                                                                                            System.out.println("volleyError " + volleyError);
//                                                                                            Toast.makeText(getApplicationContext(), "problem1", Toast.LENGTH_LONG).show();
////                        hud1.dismiss();
//
//                                                                                        }
//                                                                                    });
//
//                                                                                    RequestQueue rQueue = Volley.newRequestQueue(getApplicationContext());
//                                                                                    rQueue.add(request);
//                                                                                }
//                                                                            }
//                                                                        });
//                                                                    }
//
//                                                                }, new Response.ErrorListener() {
//                                                                    @Override
//                                                                    public void onErrorResponse(VolleyError volleyError) {
//                                                                        System.out.println("volleyError " + volleyError);
//                                                                        Toast.makeText(getApplicationContext(), "هناك مشكله", Toast.LENGTH_LONG).show();
//
//
////                        hud1.dismiss();
//
//                                                                    }
//                                                                }){
//
//
//
//                                                                };
//
//                                                                RequestQueue rQueue = Volley.newRequestQueue(getApplicationContext());
//                                                                rQueue.add(request);
//
//
//
//
//
//
//
//
////                                                                Intent i=new Intent(getApplicationContext(),DisolayQRDetailsActivity.class);
////                                                                i.putExtra("link",parts[0]);
////                                                                i.putExtra("organization",parts[1]);
////                                                                i.putExtra("product",parts[8]);
////                                                                i.putExtra("qualified","qualified");
////                                                                i.putExtra("description",parts[7]);
////                                                                startActivity(i);
//                                                            }
//                                                        }
//                                                    });
//                                                }
//
//                                            }, new Response.ErrorListener() {
//                                                @Override
//                                                public void onErrorResponse(VolleyError volleyError) {
//                                                    System.out.println("volleyError " + volleyError);
//                                                    Toast.makeText(getApplicationContext(), "هناك مشكله", Toast.LENGTH_LONG).show();
//
//
////                        hud1.dismiss();
//
//                                                }
//                                            }){
//
//
//                                            };
//
//                                            RequestQueue rQueue = Volley.newRequestQueue(getApplicationContext());
//                                            rQueue.add(request);
//
//
//
//
//
//                                        } else if(s.equals("1")){
//                                            Toast.makeText(getApplicationContext(), "مستخدم من قبل", Toast.LENGTH_LONG).show();
//                                            Intent i=new Intent(getApplicationContext(),DisolayQRDetailsActivity.class);
//                                            i.putExtra("link",parts[0]);
//                                            i.putExtra("organization",parts[1]);
//                                            i.putExtra("product",parts[8]);
//                                            i.putExtra("qualified","not qualified");
//                                            i.putExtra("description",parts[7]);
//                                            startActivity(i);
//
//                                        }}
//                                });
//                            }
//
//                        }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError volleyError) {
//                                System.out.println("volleyError " + volleyError);
//                                Toast.makeText(getApplicationContext(), "volleyError", Toast.LENGTH_LONG).show();
////                        hud1.dismiss();
//
//                            }
//                        }){
//
//                        };
//
//                        RequestQueue rQueue = Volley.newRequestQueue(getApplicationContext());
//                        rQueue.add(request);
//                    }else {
////    Toast.makeText(QRActivity.this,"no 2",Toast.LENGTH_LONG).show();
//                        Intent i=new Intent(getApplicationContext(),DisolayQRDetailsActivity.class);
//                        i.putExtra("link","un known");
//                        i.putExtra("organization","un known");
//                        i.putExtra("product","un known");
//                        i.putExtra("qualified","false");
//                        i.putExtra("description","un known");
//                        startActivity(i);
//                    }














                }

//                Constants.qrs.clear();
//                sharedPreferences.edit().remove("Set");

            }


















//



                sharedPreferences.edit().clear().commit();

            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        /** Register for CONNECTIVITY_ACTION broadcasts */
        registerReceiver(NetworkStatusReceiver, new IntentFilter(
                ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterReceiver(NetworkStatusReceiver);
    }
}
