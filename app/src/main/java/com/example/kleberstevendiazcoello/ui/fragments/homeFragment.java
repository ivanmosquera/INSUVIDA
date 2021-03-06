package com.example.kleberstevendiazcoello.ui.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kleberstevendiazcoello.ui.Activitys.botton_menu;
import com.example.kleberstevendiazcoello.ui.R;
import com.example.kleberstevendiazcoello.ui.login.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link homeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link homeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class homeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String ID_data = "iduser";
    public static final String Mail_data = "email";
    public static final String User_data = "user";
    public static final String Peso_data = "peso";
    public static final String Altura_data = "altura";
    public static final String Edad_data = "edad";
    public static final String Genero_data = "genero";
    public static final String Telefono_data = "telefono";
    public static final String Ciudad_data = "ciudad";
    TextView user, e, peso,a,nombre,genero,ciudad,telefono ;
    RequestQueue requestQueue;
    ImageView config ,configmod;
    Dialog salirpopup;
    Button salidaapp,cancelarsalida;
    String username;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public homeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment homeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static homeFragment newInstance(String param1, String param2) {
        homeFragment fragment = new homeFragment();
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
       View view  =inflater.inflate(R.layout.fragment_home, container, false);
        requestQueue = Volley.newRequestQueue(getActivity());
        //user = (TextView)view.findViewById(R.id.txtcorreouser);
        a = (TextView)view.findViewById(R.id.txtshowaltura);
        peso= (TextView)view.findViewById(R.id.txtshowpeso);
        e = (TextView)view.findViewById(R.id.txtshowedad);
        nombre = (TextView)view.findViewById(R.id.txtshowusuario);
        genero = (TextView)view.findViewById(R.id.txtshowgenero);
        ciudad = (TextView)view.findViewById(R.id.txtshowciudad);
        telefono = (TextView)view.findViewById(R.id.txtshowtelefono);
        config = (ImageView)view.findViewById(R.id.salirapp);
        configmod = (ImageView)view.findViewById(R.id.modificardatos);
        salirpopup = new Dialog(getActivity());
        salirpopup.setContentView(R.layout.popup_salir);
        salidaapp = (Button)salirpopup.findViewById(R.id.salirapppop);
        cancelarsalida = (Button)salirpopup.findViewById(R.id.cancelarsalida);
        config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                salirpopup.show();
                salidaapp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                                "userinfodata", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.clear();
                        editor.commit();

                        SharedPreferences sharedPrefe = getActivity().getSharedPreferences(
                                "userinfo", Context.MODE_PRIVATE);

                        SharedPreferences.Editor editore = sharedPrefe.edit();
                        editore.clear();
                        editore.commit();
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }
                });

                cancelarsalida.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        salirpopup.dismiss();
                    }
                });



               /*getActivity().finish();
               getActivity().moveTaskToBack(true);*/
            }
        });


        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                "userinfo", Context.MODE_PRIVATE);
        username = sharedPref.getString(Mail_data, "hola");
        SharedPreferences sharedPrefet = getActivity().getSharedPreferences(
                "userinfodata", Context.MODE_PRIVATE);
        String nameob = sharedPrefet.getString(User_data, "hola");
        getdatosuser(username);
       /* if(!nameob.equals("")){
            Log.d("Homefragment", "entre al if");
            String edad = sharedPrefet.getString(Edad_data, "hola");
            String name = sharedPrefet.getString(User_data, "hola");
            String altu = sharedPrefet.getString(Altura_data, "hola");
            String pe = sharedPrefet.getString(Peso_data, "hola");
            String ciu = sharedPrefet.getString(Ciudad_data, "hola");
            String ge = sharedPrefet.getString(Genero_data, "hola");
            String fono = sharedPrefet.getString(Telefono_data, "hola");
            //user.setText(mail);
            e.setText(edad);
            a.setText(altu);
            peso.setText(pe);
            nombre.setText(name);
            genero.setText(ge);
            ciudad.setText(ciu);
            telefono.setText(fono);

        }else{
            getdatosuser(username);
        }*/




        /*SharedPreferences sharedPref = getActivity().getSharedPreferences(
                "userinfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(ID_data,object.getInt("id"));
        editor.putString(User_data,object.getString("Usuario"));
        editor.putString(Peso_data,String.valueOf(object.getString("Peso")));
        editor.putString(Altura_data,String.valueOf(object.getString("Altura")));
        editor.putInt(Edad_data,object.getInt("Edad"));
        editor.commit();*/
        configmod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("Mail",username);
                android.support.v4.app.FragmentManager fragmentManager= getFragmentManager();
                android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
                ModificarPerfilFragment mod = new ModificarPerfilFragment();
                mod.setArguments(bundle);
                transaction.replace(R.id.content2,mod).addToBackStack("").commit();
            }
        });
       return view;
    }


    public void getdatosuser(final String mail){

        String url = "http://www.flexoviteq.com.ec/InsuvidaFolder/datosuser.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jObj = new JSONArray(response);
                    JSONObject object = jObj.getJSONObject(0);
                    SharedPreferences sharedPref = getActivity().getSharedPreferences(
                            "userinfodata", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.clear();
                    editor.putInt(ID_data,object.getInt("id"));
                    editor.putString(User_data,object.getString("Nombre"));
                    editor.putString(Peso_data,String.valueOf(object.getString("Peso")));
                    editor.putString(Altura_data,String.valueOf(object.getString("Altura")));
                    editor.putString(Genero_data,String.valueOf(object.getString("Genero")));
                    editor.putString(Ciudad_data,String.valueOf(object.getString("Ciudad")));
                    editor.putString(Telefono_data,String.valueOf(object.getString("Telefono")));
                    //editor.putInt(Edad_data,object.getInt("Edad"));
                    editor.putString(Edad_data, String.valueOf(object.getString("Edad")));
                    editor.commit();

                    SharedPreferences sharedPrefe = getActivity().getSharedPreferences(
                            "userinfodata", Context.MODE_PRIVATE);
                    //int edad = sharedPrefe.getInt(Edad_data, 0);
                    String edad = sharedPrefe.getString(Edad_data, "hola");
                    String name = sharedPrefe.getString(User_data, "hola");
                    String altu = sharedPrefe.getString(Altura_data, "hola");
                    String pe = sharedPrefe.getString(Peso_data, "hola");
                    String ciu = sharedPrefe.getString(Ciudad_data, "hola");
                    String ge = sharedPrefe.getString(Genero_data, "hola");
                    String fono = sharedPrefe.getString(Telefono_data, "hola");
                    //user.setText(mail);
                    e.setText(edad);
                    a.setText(altu);
                    peso.setText(pe);
                    nombre.setText(name);
                    genero.setText(ge);
                    ciudad.setText(ciu);
                    telefono.setText(fono);



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }){
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("correo",mail);
                return map;
            }

        };
        requestQueue.add(request);


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
            Toast.makeText(context,"Notificacion de Fragmento",Toast.LENGTH_SHORT);
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
}
