package eu.kudan.qrcode.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.kaopiz.kprogresshud.KProgressHUD;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import eu.kudan.qrcode.Activity.DisplayQRPurchacedUsedActivity;
import eu.kudan.qrcode.Activity.QRActivity;
import eu.kudan.qrcode.Utils.Constants;
import eu.kudan.qrcode.Adapter.MyListAdapter;
import eu.kudan.qrcode.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SecondFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SecondFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecondFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View view1;
    TextView home;
    TextView loyality;
    TextView call;
    KProgressHUD hud1;
    private OnFragmentInteractionListener mListener;

    public SecondFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SecondFragment newInstance(String param1, String param2) {
        SecondFragment fragment = new SecondFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_second, container, false);

        view1= inflater.inflate(R.layout.fragment_second, container, false);

        home = (TextView) view1.findViewById(R.id.home1);
        loyality=(TextView)view1.findViewById(R.id.my_loyality);
        call=(TextView)view1.findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                FirstFragment.ExportDatabaseCSVTask task=new FirstFragment.ExportDatabaseCSVTask();
//                task.execute();
                openWhatsApp("+201114442251","لدى سؤال بخصوص التطبيق");
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
        }); RecyclerView recyclerView = (RecyclerView) view1.findViewById(R.id.recyclerView);
                                MyListAdapter adapter = new MyListAdapter(DisplayQRPurchacedUsedActivity.myListData1);
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                recyclerView.setAdapter(adapter);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//
//
//
////                            final String[] parts1 = s.split("mmmnmnmnmnm");
//////                                                                mnmnmnmnmnmansnam
////                            Constants.phone_loyality=parts1[0];
////                            Constants.phone_id =parts1[1];
////
////
////                            Constants.phone_name=sharedpreferences.getString(Name, "");
////                            Log.d("Constants.phone_name", "Constants.phone_name  onCreate: "+Constants.phone_name);
//////                                Constants.phone_loyality=s;
////                            Intent i=new Intent(getActivity(),QRActivity.class);
////                            startActivity(i);
//                            hud1.dismiss();
//
//                        }
//                    }
//                });
//
//
//            }
//
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                System.out.println("volleyError " + error);
////                    Toast.makeText(getActivity(), " Please try again may be aproblem at network", Toast.LENGTH_LONG).show();
//                hud1.dismiss();
//                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                    Toast.makeText(getActivity(), "خطأ في مهلة الشبكة", Toast.LENGTH_LONG).show();
//                } else if (error instanceof AuthFailureError) {
//                    Toast.makeText(getActivity(), "خطأ فشل ", Toast.LENGTH_LONG).show();
//                } else if (error instanceof ServerError) {
//                    Toast.makeText(getActivity(), "خطأ في الشبكه", Toast.LENGTH_LONG).show();
//                } else if (error instanceof NetworkError) {
//                    Toast.makeText(getActivity(), "خطأ في الشبكه", Toast.LENGTH_LONG).show();
//                } else if (error instanceof ParseError) {
//                    Toast.makeText(getActivity(), "خطأ في التحويل ", Toast.LENGTH_LONG).show();
//                }else{
//                    Toast.makeText(getActivity(), "من فضل حاول مره اخري", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//
//        RequestQueue rQueue = Volley.newRequestQueue(getActivity());
//        rQueue.add(request);

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
    public interface OnFragmentInteractionListener {
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
