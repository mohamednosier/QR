package eu.kudan.qrcode.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import eu.kudan.qrcode.Utils.Constants;
import eu.kudan.qrcode.R;

public class DisolayQRDetailsActivity extends AppCompatActivity {
TextView link,organization,product,defination,internet,open_malyan,comapny_name,product_name,product_code_url,product_code_description,product_description,product_url;

    Button back;
    int x;
    KProgressHUD hud1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disolay_qrdetails);
        link=(TextView)findViewById(R.id.link);
        product_code_url=(TextView)findViewById(R.id.product_code_url);
        organization=(TextView)findViewById(R.id.organization);
        product=(TextView)findViewById(R.id.product);

        back=(Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                x=0;
                new IntentIntegrator(DisolayQRDetailsActivity.this).setCaptureActivity(ScannerActivity.class).initiateScan();
                
            }
        });


        defination=(TextView)findViewById(R.id.defination);
        internet=(TextView)findViewById(R.id.internet);
        open_malyan=(TextView)findViewById(R.id.open_malyan);
        comapny_name=(TextView)findViewById(R.id.comapny_name);
        product_name=(TextView)findViewById(R.id.product_name);
        product_code_description=(TextView)findViewById(R.id.product_code_description);
        product_description=(TextView)findViewById(R.id.product_description);
        product_url=(TextView)findViewById(R.id.product_url);

        link.setText(getIntent().getStringExtra("link"));
        product_code_url.setText(getIntent().getStringExtra("link"));
        organization.setText(getIntent().getStringExtra("organization"));
        product.setText(getIntent().getStringExtra("product"));
        product_code_description.setText(getIntent().getStringExtra("description"));
        if(getIntent().getStringExtra("qualified").equals("not qualified")){
            internet.setText("برجاء الاتصال برقم 01114442251 للتأكد من سلامه المنتج");
            internet.setTextColor(Color.parseColor("#43B649"));
            open_malyan.setText("كود غير صحيح ");
            open_malyan.setTextColor(Color.parseColor("#43B649"));
            comapny_name.setVisibility(View.GONE);
            organization.setVisibility(View.GONE);
            product_name.setVisibility(View.GONE);
            product.setVisibility(View.GONE);
            product_description.setVisibility(View.GONE);
            product_code_description.setVisibility(View.GONE);
            product_url.setVisibility(View.GONE);
            product_code_url.setVisibility(View.GONE);
        }else if(getIntent().getStringExtra("qualified").equals("qualified")){
//Toast.makeText(DisolayQRDetailsActivity.this,"true",Toast.LENGTH_LONG).show();
//            organization.setText(getIntent().getStringExtra("organization"));
            internet.setVisibility(View.GONE);
            open_malyan.setText("كود أصلي جديد");
            open_malyan.setTextColor(Color.parseColor("#43B649"));

        }else if(getIntent().getStringExtra("qualified").equals("is used before")){
            internet.setText("برجاء الاتصال برقم 01114442251 للتأكد من سلامه المنتج");
            internet.setTextColor(Color.parseColor("#43B649"));

            open_malyan.setTextColor(Color.parseColor("#43B649"));
//            relative2.setVisibility(View.GONE);
//            relative3.setVisibility(View.GONE);
//            relative4.setVisibility(View.GONE);
//            relative5.setVisibility(View.GONE);
            open_malyan.setText("كود سبق استخدامه ");

            comapny_name.setVisibility(View.GONE);
            organization.setVisibility(View.GONE);
            product_name.setVisibility(View.GONE);
            product.setVisibility(View.GONE);
            product_description.setVisibility(View.GONE);
            product_code_description.setVisibility(View.GONE);
            product_url.setVisibility(View.GONE);
            product_code_url.setVisibility(View.GONE);
        }else{
//            organization.setText(getIntent().getStringExtra("product"));
            internet.setText("برجاء الاتصال برقم 01114442251 للتأكد من سلامه المنتج");
            internet.setTextColor(Color.parseColor("#43B649"));
            open_malyan.setText("كود غير صحيح ");
            open_malyan.setTextColor(Color.parseColor("#43B649"));
            comapny_name.setVisibility(View.GONE);
            organization.setVisibility(View.GONE);
            product_name.setVisibility(View.GONE);
            product.setVisibility(View.GONE);
            product_description.setVisibility(View.GONE);
            product_code_description.setVisibility(View.GONE);
            product_url.setVisibility(View.GONE);
            product_code_url.setVisibility(View.GONE);

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
                if(x==1){
//show dialogue with result
//                    showResultDialogue1(result.getContents());
//                    showResultDialogue4(result.getContents());
                }else if(x==0){
                    connect(result.getContents());
                }
                //show dialogue with result
//                showResultDialogue(result.getContents());
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void connect(String result){
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
            hud1 = KProgressHUD.create(DisolayQRDetailsActivity.this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                    .show();

//            String url = "http://3.218.130.218:8080/nfc/webresources/transaction/register_products_purchaced/" + parts[1]+"/"+parts[2].substring(0,parts[2].indexOf('/'))+"/"+formatter.format(date)+"/"+Constants.phone_id;

            JSONObject jsonBodyObj = new JSONObject();
            try{
                jsonBodyObj.put("organization_id", parts[1]);
                jsonBodyObj.put("text", parts[2]);
                jsonBodyObj.put("scanned_date", formatter.format(date));
                jsonBodyObj.put("user_id", Constants.phone_id);
            }catch (JSONException e){
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


                                Intent i = new Intent(DisolayQRDetailsActivity.this, DisolayQRDetailsActivity.class);
                                i.putExtra("link", "un known");
                                i.putExtra("organization", "un known");
                                i.putExtra("product", "un known");
                                i.putExtra("qualified", "not qualified");
                                i.putExtra("description", "un known");
                                startActivity(i);



                                hud1.dismiss();
                            } else if(s.equals("1")){

                                Toast.makeText(DisolayQRDetailsActivity.this, "تم شراء هذا المنتج من قبل", Toast.LENGTH_LONG).show();

                                Intent i = new Intent(DisolayQRDetailsActivity.this, DisolayQRDetailsActivity.class);
                                i.putExtra("link", "");
                                i.putExtra("organization", "");
                                i.putExtra("product", "");
                                i.putExtra("qualified", "is used before");
                                i.putExtra("description", "");
                                startActivity(i);

                                hud1.dismiss();
                            }
                            else{
                                Toast.makeText(DisolayQRDetailsActivity.this, "تم شراء هذا المنتج ", Toast.LENGTH_LONG).show();

                                String s1 = fixEncoding(s);
                                Log.d("results", "run: results1  " + s1);
                                final String[] parts1 = s1.split("mnmnmnmnmnmansnam");
//                                                                mnmnmnmnmnmansnam
                                String loyality_pont = parts1[0];
                                product_name[0] = parts1[1];
                                product_description[0] = parts1[2];
                                product_url[0] = parts1[3];
                                production_profile_id[0] = parts1[4];
                                Intent i = new Intent(DisolayQRDetailsActivity.this, DisolayQRDetailsActivity.class);
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
                        Toast.makeText(DisolayQRDetailsActivity.this, "خطأ في مهلة الشبكة", Toast.LENGTH_LONG).show();
                    } else if (error instanceof AuthFailureError) {
                        Toast.makeText(DisolayQRDetailsActivity.this, "خطأ فشل ", Toast.LENGTH_LONG).show();
                    } else if (error instanceof ServerError) {
//                        خطأ في الشبكه
                        Toast.makeText(DisolayQRDetailsActivity.this, "خطأ في الشبكه", Toast.LENGTH_LONG).show();
                    } else if (error instanceof NetworkError) {
                        Toast.makeText(DisolayQRDetailsActivity.this, "خطأ في الشبكه", Toast.LENGTH_LONG).show();
                    } else if (error instanceof ParseError) {
                        Toast.makeText(DisolayQRDetailsActivity.this, "خطأ في التحويل ", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(DisolayQRDetailsActivity.this, "من فضل حاول مره اخري", Toast.LENGTH_LONG).show();
                    }

                }
            }){

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
                @Override    public byte[] getBody() {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                                requestBody, "utf-8");
                        return null;
                    }
                }

            };

            RequestQueue rQueue = Volley.newRequestQueue(DisolayQRDetailsActivity.this);
            rQueue.add(request);










//        } else {
//            Intent i=new Intent(DisolayQRDetailsActivity.this,DisolayQRDetailsActivity.class);
//            i.putExtra("link","un known");
//            i.putExtra("organization","un known");
//            i.putExtra("product","un known");
//            i.putExtra("qualified","false");
//            i.putExtra("description","un known");
//            startActivity(i);
//        }
        }else {
//    Toast.makeText(DisolayQRDetailsActivity.this,"no 2",Toast.LENGTH_LONG).show();
            Intent i = new Intent(DisolayQRDetailsActivity.this, DisolayQRDetailsActivity.class);
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
    public void onBackPressed(){
       Intent i=new Intent(DisolayQRDetailsActivity.this,QRActivity.class);
       startActivity(i);
//       finish();
    }
}
