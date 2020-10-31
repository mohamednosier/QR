package eu.kudan.qrcode.Fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfWriter;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import eu.kudan.qrcode.Activity.DisplayQRPurchacedUsedActivity;
import eu.kudan.qrcode.Activity.QRActivity;
//import eu.kudan.qrcode.CSVWriter;
import eu.kudan.qrcode.Utils.CSVWriter;
import eu.kudan.qrcode.Utils.Constants;
import eu.kudan.qrcode.Adapter.MyListAdapter;
import eu.kudan.qrcode.R;

import static com.itextpdf.text.html.HtmlTags.FONT;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FirstFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FirstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View view1;
    TextView home;
    TextView loyality;
    TextView call;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    KProgressHUD hud1;
    private static final String LOG_TAG_EXTERNAL_STORAGE = "EXTERNAL_STORAGE";

    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 1;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private OnFragmentInteractionListener mListener;
    public static String newline = System.getProperty("line.separator");
    private File pdfFile;
    public FirstFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FirstFragment newInstance(String param1, String param2) {
        FirstFragment fragment = new FirstFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view1= inflater.inflate(R.layout.fragment_first, container, false);

        home = (TextView) view1.findViewById(R.id.home1);
            loyality=(TextView)view1.findViewById(R.id.my_loyality);
             call=(TextView)view1.findViewById(R.id.call);
             call.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     openWhatsApp("+201114442251","لدى سؤال بخصوص التطبيق");
//                     try {
//                         if(ExternalStorageUtil.isExternalStorageMounted()) {
//
//                             // Check whether this app has write external storage permission or not.
//                             int writeExternalStoragePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
//                             // If do not grant write external storage permission.
//                             if(writeExternalStoragePermission!= PackageManager.PERMISSION_GRANTED)
//                             {
//                                 // Request user to grant write external storage permission.
//                                 ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);
//                             }else {
//                                 ExportDatabaseCSVTask task = new ExportDatabaseCSVTask();
//                                 task.execute();
//                             }
//                 }




//                     if (Build.VERSION.SDK_INT >= 23) {
//                         if (checkPermission()) {
//                             ExportDatabaseCSVTask task = new ExportDatabaseCSVTask();
//                             task.execute();
//                         } else {
//                             requestPermission(); // Code for permission
//                         }
//                     } else {
//                         ExportDatabaseCSVTask task = new ExportDatabaseCSVTask();
//                         task.execute();
//                     }




             }
             });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(), QRActivity.class);
                startActivity(i);
            }
        });
        loyality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hud1 = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        String url = "http://3.218.130.218:8080/nfc/webresources/phone/get_loyality_based_on_user_id/"+ Constants.phone_id;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(final String s) {


                new Handler(Looper.getMainLooper()).post(new Runnable() { // Tried new Handler(Looper.myLopper()) also
                    @Override
                    public void run() {
                        if (s.equals("error")) {

                            Toast.makeText(getActivity(), "لايستطيع الحصول ع البيانات ", Toast.LENGTH_LONG).show();
                            hud1.dismiss();
                        } else {


                                Toast.makeText(getActivity(),"محفظتك تحتوي علي "+ s +" نقاط",Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getActivity(), "خطأ في مهلة الشبكة", Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(getActivity(), "خطأ فشل ", Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(getActivity(), "خطأ في الشبكه", Toast.LENGTH_LONG).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(getActivity(), "خطأ في الشبكه", Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(getActivity(), "خطأ في التحويل ", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getActivity(), "من فضل حاول مره اخري", Toast.LENGTH_LONG).show();
                }
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(getActivity());
        rQueue.add(request);
            }
        });

                                RecyclerView recyclerView = (RecyclerView) view1.findViewById(R.id.recyclerView);
                                MyListAdapter adapter = new MyListAdapter(DisplayQRPurchacedUsedActivity.myListData2);
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                recyclerView.setAdapter(adapter);

return view1;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
    private class ExportDatabaseCSVTask extends AsyncTask<String ,String, String> {
        private final ProgressDialog dialog = new ProgressDialog(getActivity());
        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Exporting database...");
            this.dialog.show();
        }

        protected String doInBackground(final String... args){
            File exportDir = new File(Environment.getExternalStorageDirectory(), "");
            if (!exportDir.exists()) {
                exportDir.mkdirs();
            }

            File file = new File(exportDir, "ExcelFile.csv");
//            try {
//                Writer out = new BufferedWriter(new OutputStreamWriter(
//                        new FileOutputStream(file), "UTF8"));
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
            try {
//                // Check whether this app has write external storage permission or not.
//                int writeExternalStoragePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
//// If do not grant write external storage permission.
//                if(writeExternalStoragePermission!= PackageManager.PERMISSION_GRANTED)
//                {
//                    // Request user to grant write external storage permission.
//                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);
//                }
                file.createNewFile();
                CSVWriter csvWrite = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                    byte[] u = response.toString().getBytes(
//                            "ISO-8859-1");
//                    response = new String(u, "UTF-8");
                    csvWrite = new CSVWriter(new FileWriter(file), StandardCharsets.UTF_16);
//                    csvWrite = new CSVWriter(new OutputStreamWriter(new FileOutputStream(file), "CP1256"));

                }


                StringBuilder stringBuilder = new StringBuilder();
//                stringBuilder.append(newline);
//                stringBuilder.append(newline);
//                stringBuilder.append(newline);
//                stringBuilder.append("الكميه التاريخ المنتج");
//                stringBuilder.append("الكميه              التاريخ     المنتج");
//String text="الكميه              التاريخ     المنتج";



                for (int i=0;i<DisplayQRPurchacedUsedActivity.myListData2.length;i++){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                        stringBuilder.append("الكميه              التاريخ     المنتج");
                        stringBuilder.append(newline);
                        stringBuilder.append(DisplayQRPurchacedUsedActivity.myListData2[i].getProduct()+"   "+DisplayQRPurchacedUsedActivity.myListData2[i].getDate()+ "   "+DisplayQRPurchacedUsedActivity.myListData2[i].getCode());
//                        stringBuilder.append("الكميه التاريخ المنتج");
                    }
//                    String arrStr[] ={DisplayQRPurchacedUsedActivity.myListData2[i].getProduct(), DisplayQRPurchacedUsedActivity.myListData2[i].getDate(), DisplayQRPurchacedUsedActivity.myListData2[i].getCode()};
//                    stringBuilder.append(DisplayQRPurchacedUsedActivity.myListData2[i].getProduct()+"   "+DisplayQRPurchacedUsedActivity.myListData2[i].getDate()+ "   "+DisplayQRPurchacedUsedActivity.myListData2[i].getCode());
                }
//                createPdf("الكميه              التاريخ     المنتج");


                String finalString = stringBuilder.toString();
                createPdf(finalString);
                createandDisplayPdf(finalString);
                Log.d("file is ", "doInBackground: file is  "+finalString);
//                try {
////                    createPdf1(finalString);
//                } catch (DocumentException e) {
//                    e.printStackTrace();
//                }
                String arrStr1[] ={fixEncoding("المنتج"), fixEncoding("التاريخ"), fixEncoding("التاريخ") };
                csvWrite.writeNext(arrStr1);
for (int i=0;i<DisplayQRPurchacedUsedActivity.myListData2.length;i++){
    String arrStr[] ={DisplayQRPurchacedUsedActivity.myListData2[i].getProduct(), DisplayQRPurchacedUsedActivity.myListData2[i].getDate(), DisplayQRPurchacedUsedActivity.myListData2[i].getCode()};
    csvWrite.writeNext(arrStr);
}


                csvWrite.close();
                return "";
            }
            catch (IOException e){
                Log.e("MainActivity", e.getMessage(), e);
                return "";
            }
        }

        @SuppressLint("NewApi")
        @Override
        protected void onPostExecute(final String success) {

            if (this.dialog.isShowing()){
                this.dialog.dismiss();
            }
            if (success.isEmpty()){
                Toast.makeText(getActivity(), "Export successful!", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getActivity(), "Export failed!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(getActivity(), "Write External Storage permission allows us to save files. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e("value", "Permission Granted, Now you can use local drive .");
            } else {
                Log.e("value", "Permission Denied, You cannot use local drive .");
            }
            break;
        }
    }
    private void createPdf(String sometext){
        // create a new document
        PdfDocument document = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            document = new PdfDocument();
        }
        // crate a page description
        PdfDocument.PageInfo pageInfo = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            pageInfo = new PdfDocument.PageInfo.Builder(300, 700, 1).create();
        }
        // start a page
        PdfDocument.Page page = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            page = document.startPage(pageInfo);
        }
        Canvas canvas = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            canvas = page.getCanvas();
        }
        Paint paint = new Paint();
        paint.setColor(Color.RED);
//        canvas.drawCircle(50, 50, 30, paint);
        paint.setColor(Color.BLACK);
        canvas.drawText(sometext, 80, 50, paint);
//        canvas.drawt
        // finish the page
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            document.finishPage(page);
        }

        // write the document content
        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/mypdf/";
        File file = new File(directory_path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String targetPdf = directory_path+"test-2.pdf";
        File filePath = new File(targetPdf);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                document.writeTo(new FileOutputStream(filePath));
            }
//            Toast.makeText(getActivity(), "Done", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e("main", "error "+e.toString());
            Toast.makeText(getActivity(), "Something wrong: " + e.toString(),  Toast.LENGTH_LONG).show();
        }
        // close the document
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            document.close();
        }
    }
//    // Method for creating a pdf file from text, saving it then opening it for display
//    public void createandDisplayPdf(String text) {
//
//        Document doc = new Document();
//
//        try {
//            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Dir";
//
//            File dir = new File(path);
//            if(!dir.exists())
//                dir.mkdirs();
//
//            File file = new File(dir, "newFile.pdf");
//            FileOutputStream fOut = new FileOutputStream(file);
//
//            PdfWriter.getInstance(doc, fOut);
//
//            //open the document
//            doc.open();
//
//            Paragraph p1 = new Paragraph(text);
//            Font paraFont= new Font(Font.COURIER);
//            p1.setAlignment(Paragraph.ALIGN_CENTER);
//            p1.setFont(paraFont);
//
//            //add paragraph to document
//            doc.add(p1);
//
//        } catch (DocumentException de) {
//            Log.e("PDFCreator", "DocumentException:" + de);
//        } catch (IOException e) {
//            Log.e("PDFCreator", "ioException:" + e);
//        }
//        finally {
//            doc.close();
//        }
//
//        viewPdf("newFile.pdf", "Dir");
//    }
//
//    // Method for opening a pdf file
//    private void viewPdf(String file, String directory) {
//
//        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/" + directory + "/" + file);
//        Uri path = Uri.fromFile(pdfFile);
//
//        // Setting the intent for pdf reader
//        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
//        pdfIntent.setDataAndType(path, "application/pdf");
//        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        try {
//            startActivity(pdfIntent);
//        } catch (ActivityNotFoundException e) {
//            Toast.makeText(TableActivity.this, "Can't read pdf file", Toast.LENGTH_SHORT).show();
//        }
//    }


    // Method for creating a pdf file from text, saving it then opening it for display
    public void createandDisplayPdf(String text) {

        Document doc = new Document();

        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Dir";

            File dir = new File(path);
            if(!dir.exists())
                dir.mkdirs();

            File file = new File(dir, "newFile1.pdf");
            FileOutputStream fOut = new FileOutputStream(file);

            PdfWriter writer =PdfWriter.getInstance(doc, fOut);

            //open the document
            doc.open();

            Paragraph p1 = new Paragraph(text);
            Font f = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Phrase p = new Phrase("This is correct: ");
            p.add(new Chunk(text, f));
            p.add(new Phrase(": 50.00"));
            ColumnText canvas = new ColumnText(writer.getDirectContent());
            canvas.setSimpleColumn(36, 750, 559, 780);
            canvas.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
            canvas.addElement(p);
            canvas.go();



//            Font paraFont= new Font();
            p1.setAlignment(Paragraph.ALIGN_CENTER);
//            p1.setFont(paraFont);

            //add paragraph to document
            doc.add(p1);

        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        }
        finally {
            doc.close();
        }


    }

    private void createPdf1(String text) throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i("file", "Created a new directory for PDF");
        }

        pdfFile = new File(docsFolder.getAbsolutePath(),"HelloWorld.pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();
        PdfWriter.getInstance(document, output);
        document.open();
        document.add(new Paragraph(text));

        document.close();
        previewPdf();

    }
    private void previewPdf() {

        PackageManager packageManager = getActivity().getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() > 0) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(pdfFile);
            intent.setDataAndType(uri, "application/pdf");

            startActivity(intent);
        }else{
            Toast.makeText(getActivity(),"Download a PDF Viewer to see the generated PDF",Toast.LENGTH_SHORT).show();
        }
    }
    private void openWhatsApp(String numero,String mensaje){

        try{
            PackageManager packageManager = getActivity().getPackageManager();
            Intent i = new Intent(Intent.ACTION_VIEW);
            String url = "https://api.whatsapp.com/send?phone="+ numero +"&text=" + URLEncoder.encode(mensaje, "UTF-8");
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                startActivity(i);
            }else {
//                KToast.errorToast(getActivity(), getString(R.string.no_whatsapp), Gravity.BOTTOM, KToast.LENGTH_SHORT);
                Toast.makeText(getActivity(),"لايوجد واتس اب",Toast.LENGTH_LONG).show();
            }
        } catch(Exception e) {
            Log.e("ERROR WHATSAPP",e.toString());
            Toast.makeText(getActivity(),"لايوجد واتس اب",Toast.LENGTH_LONG).show();
//            KToast.errorToast(getActivity(), , Gravity.BOTTOM, KToast.LENGTH_SHORT);
        }

    }
}
