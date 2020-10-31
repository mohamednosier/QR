package eu.kudan.qrcode.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;
//import android.support.v4.view.ViewPager;
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
import com.google.android.material.tabs.TabLayout;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import eu.kudan.qrcode.Utils.Constants;
import eu.kudan.qrcode.Adapter.MyFragmentPagerAdapter;
import eu.kudan.qrcode.Adapter.MyListData;
import eu.kudan.qrcode.R;

public class DisplayQRPurchacedUsedActivity extends AppCompatActivity {
    MyFragmentPagerAdapter myFragmentPagerAdapter;
    ViewPager viewPager;
    TabLayout tabLayout;
    KProgressHUD hud1;
    public static MyListData[] myListData2;
    public static MyListData[] myListData1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_qrpurchaced_used);

//        get_used_codes/{organization_name}

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);


        hud1 = KProgressHUD.create(DisplayQRPurchacedUsedActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        String url = "http://3.218.130.218:8080/nfc/webresources/phone/get_purchaced_used_codes/"+ Constants.phone_id;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(final String s) {


                new Handler(Looper.getMainLooper()).post(new Runnable() { // Tried new Handler(Looper.myLopper()) also
                    @Override
                    public void run() {
                        if (s.equals("error")) {

                            Toast.makeText(DisplayQRPurchacedUsedActivity.this, "لايستطيع الحصول ع البيانات ", Toast.LENGTH_LONG).show();
                            hud1.dismiss();
                        } else {

                            try {
                                int purchaced=0;
                                int used=0;
                                int purchaced1=0;
                                int used1=0;
                                String s1=  fixEncoding(s);
                                JSONArray values = new JSONArray(s1);
//                                MyListData[] myListData = new MyListData[values.length()];
                                for (int i = 0; i < values.length(); i++) {
                                    JSONObject animal = values.getJSONObject(i);
                                    if(animal.getString("used").equals("0")){
                                        purchaced=purchaced+1;
                                    }
                                    else{
                                        used=used+1;
                                    }

                                }
                                 myListData1 = new MyListData[purchaced];
                                 myListData2 = new MyListData[used];
                                for (int i = 0; i < values.length(); i++) {


                                    JSONObject animal = values.getJSONObject(i);
                                    if(animal.getString("used").equals("0")){
                                        purchaced1=purchaced1+1;
                                        String product="";
                                        if(animal.getString("product").length()>10){
                                            product = animal.getString("product").substring(0,10);
                                        }else{
                                            product = animal.getString("product");
                                        }


                                        String date = animal.getString("date");
//                                        String organization_name = animal.getString("organization_name");
                                        String farm_code = "";
                                        if(animal.getString("farm_code").length()>8){
                                            farm_code = animal.getString("farm_code").substring(0,8);
                                        }else{
                                            farm_code = animal.getString("farm_code");
                                        }

//                                    Sprintln(id + ", " + species + ", " + name);
                                        myListData1[purchaced1-1]=new MyListData(product, date,farm_code);
//                                        myListData1[purchaced1-1]=new MyListData(product, organization_name,farm_code);
                                    }else{
                                        used1=used1+1;
                                        String product="";
                                        if(animal.getString("product").length()>10){
                                            product = animal.getString("product").substring(0,10);
                                        }else{
                                            product = animal.getString("product");
                                        }


                                        String date = animal.getString("date");
                                        String farm_code = "";
                                        if(animal.getString("farm_code").length()>8){
                                            farm_code = animal.getString("farm_code").substring(0,8);
                                        }else{
                                            farm_code = animal.getString("farm_code");
                                        }

//                                    Sprintln(id + ", " + species + ", " + name);
                                        myListData2[used1-1]=new MyListData(product, date,farm_code);
                                    }

                                }
                                Log.d("myListData1", "run:myListData1 purchaced "+purchaced);
                                Log.d("myListData1", "run:myListData1 used "+used);
                                for(int i=0;i<myListData1.length;i++){
                                    Log.d("myListData1", "run:myListData1 "+myListData1[i].getProduct());
                                }
                                for(int i=0;i<myListData2.length;i++){
                                    Log.d("myListData2", "run:myListData2 "+myListData2[i].getProduct());
                                }

//                                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
//                                MyListAdapter adapter = new MyListAdapter(myListData);
//                                recyclerView.setHasFixedSize(true);
//                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//                                recyclerView.setAdapter(adapter);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            hud1.dismiss();
                            setPagerAdapter();
                            setTabLayout();
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
                    Toast.makeText(DisplayQRPurchacedUsedActivity.this, "خطأ في مهلة الشبكة", Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(DisplayQRPurchacedUsedActivity.this, "خطأ فشل ", Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(DisplayQRPurchacedUsedActivity.this, "خطأ في الشبكه", Toast.LENGTH_LONG).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(DisplayQRPurchacedUsedActivity.this, "خطأ في الشبكه", Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(DisplayQRPurchacedUsedActivity.this, "خطأ في التحويل ", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(DisplayQRPurchacedUsedActivity.this, "من فضل حاول مره اخري", Toast.LENGTH_LONG).show();
                }
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(DisplayQRPurchacedUsedActivity.this);
        rQueue.add(request);




    }

    private void setPagerAdapter(){
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myFragmentPagerAdapter);
    }

    private void setTabLayout() {
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("المستخدم");
        tabLayout.getTabAt(1).setText("المخزن");
    }
                        public String fixEncoding(String response) {
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
    public void onBackPressed(){
        Intent i=new Intent(DisplayQRPurchacedUsedActivity.this,QRActivity.class);
        startActivity(i);
//       finish();

    }}
