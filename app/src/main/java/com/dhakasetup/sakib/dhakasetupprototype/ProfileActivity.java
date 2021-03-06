package com.dhakasetup.sakib.dhakasetupprototype;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private Toolbar toolbar;
    Context context;
    TextView name_tv,phone_tv,address_tv,email_tv;
    SharedPreferences setting;
    ProgressBar progressBar;
    LinearLayout login;
    String userid,name, mobile,address,email,customer_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        context = this;
        name_tv = findViewById(R.id.usernameET);
        phone_tv = findViewById(R.id.phoneTextView);
        address_tv = findViewById(R.id.addressET);
        email_tv = findViewById(R.id.emailEditText);
        progressBar = findViewById(R.id.profile_activity_progess);
        login = findViewById(R.id.login);

        login.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        setting = context.getSharedPreferences("customer_app",Context.MODE_PRIVATE);

        userid = setting.getString("userid",null);
        customer_id = setting.getString("customer_id",null);
        name = setting.getString("name",null);
        mobile = setting.getString("mobile",null);
        address = setting.getString("address",null);
        email = setting.getString("email",null);

        setProfile(userid, mobile);

        name_tv.setText(name);
        phone_tv.setText(mobile);
        address_tv.setText(address);
        email_tv.setText(email);

        name_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = LayoutInflater.from(context);
                final View dialog_view = inflater.inflate(R.layout.prompt_dialog,null);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(dialog_view);
                final EditText userinput = dialog_view.findViewById(R.id.dialog_input);
                final TextView userLable = dialog_view.findViewById(R.id.dialog_label);
                userinput.setText(name_tv.getText());
                userLable.setText("Enter your name:");
                builder.setCancelable(false)
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                name_tv.setText(userinput.getText());
                                profilepost(userid,
                                        name_tv.getText().toString(),
                                        phone_tv.getText().toString(),
                                        email_tv.getText().toString(),
                                        address_tv.getText().toString()
                                        ,"1");
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        /*phone_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = LayoutInflater.from(context);
                final View dialog_view = inflater.inflate(R.layout.prompt_dialog,null);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(dialog_view);
                final EditText userinput = dialog_view.findViewById(R.id.dialog_input);
                final TextView userLable = dialog_view.findViewById(R.id.dialog_label);
                userinput.setText(phone_tv.getText());
                userLable.setText("Enter phone number:");
                builder.setCancelable(false)
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                phone_tv.setText(userinput.getText());
                                profilepost(userid,
                                        name_tv.getText().toString(),
                                        phone_tv.getText().toString(),
                                        email_tv.getText().toString(),
                                        address_tv.getText().toString()
                                        ,"0");
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });*/

        address_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = LayoutInflater.from(context);
                final View dialog_view = inflater.inflate(R.layout.prompt_dialog,null);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(dialog_view);
                final EditText userinput = dialog_view.findViewById(R.id.dialog_input);
                final TextView userLable = dialog_view.findViewById(R.id.dialog_label);
                userinput.setText(address_tv.getText());
                userLable.setText("Enter your address:");
                builder.setCancelable(false)
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                address_tv.setText(userinput.getText());
                                profilepost(userid,
                                        name_tv.getText().toString(),
                                        phone_tv.getText().toString(),
                                        email_tv.getText().toString(),
                                        address_tv.getText().toString()
                                        ,"1");
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        email_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = LayoutInflater.from(context);
                final View dialog_view = inflater.inflate(R.layout.prompt_dialog,null);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(dialog_view);
                final EditText userinput = dialog_view.findViewById(R.id.dialog_input);
                final TextView userLable = dialog_view.findViewById(R.id.dialog_label);
                userinput.setText(email_tv.getText());
                userLable.setText("Enter email address:");
                builder.setCancelable(false)
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                email_tv.setText(userinput.getText());
                                profilepost(userid,
                                        name_tv.getText().toString(),
                                        phone_tv.getText().toString(),
                                        email_tv.getText().toString(),
                                        address_tv.getText().toString()
                                        ,"1");
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra("fragmentNumber",1);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void setProfile(final String userid, final String phoneNum){
        //useridTv.setText(userid);
//        phoneTv.setText(mobile);
//        loginLayout.setVisibility(View.VISIBLE);
//        logoutLayout.setVisibility(View.GONE);
        Log.d("20619","profile api pre loaded "+phoneNum);

        StringRequest request = new StringRequest(Request.Method.POST,
                UrlList.profileGet,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("20619",response);
                            JSONArray rootArray = new JSONArray(response);
                            JSONObject result = rootArray.getJSONObject(0);

                            String first_name = result.getString("name");
                            String email = result.getString("email");
                            String address = result.getString("address");
//                                usernameET.setText(first_name);
//                                addressET.setText(address);
//                                emailET.setText(email);
                            SharedPreferences settings = context.getSharedPreferences("customer_app",Context.MODE_PRIVATE);
                            settings.edit().putString("name",first_name).commit();
                            settings.edit().putString("address",address).commit();
                            settings.edit().putString("email",email).commit();

                            login.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);



                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("mobile",phoneNum);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    public void profilepost(final String uid, final String firstname, final String phone, final String email, final String address, final String action){
        StringRequest request = new StringRequest(Request.Method.POST,
                UrlList.profilePost,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("20619",response);
                            setProfile(uid,phone);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("uid",uid);
                params.put("name",firstname);
                params.put("mobile",phone);
                params.put("email",email);
                params.put("address",address);
                params.put("action",action);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }
}
