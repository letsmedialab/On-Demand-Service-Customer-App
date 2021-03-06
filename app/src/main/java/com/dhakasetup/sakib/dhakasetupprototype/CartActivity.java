package com.dhakasetup.sakib.dhakasetupprototype;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dhakasetup.sakib.dhakasetupprototype.adapter.CartAdapter;
import com.dhakasetup.sakib.dhakasetupprototype.adapter.SubcatAdapter;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Category;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Data;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Service;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartActivity extends AppCompatActivity {
    Toolbar toolbar;
    Context context;
    ImageButton add_more;
    Button place_order;
    public TextView subtotal,savings;
    public BottomNavigationView bottomNavigationView;
    String fragmentTag;
    List<Category> categories;
    public int cart_counter = 0;
    NumberFormat nf ;
    String orderid;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);

        subtotal = findViewById(R.id.service1_item_subtotal);
        savings = findViewById(R.id.service1_item_savings);
        add_more = findViewById(R.id.cart_add_more);
        place_order = findViewById(R.id.place_order_btn);

        categories = Data.getInstance(this).getData();
        context = this;

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cart");

        RecyclerView recyclerView = findViewById(R.id.recycler_cart);
            /*recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
            int cat_id = 20;
            String cat_name = "ac";
            //load(cat_id,cat_name,recyclerView);*/
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CartAdapter adapter = new CartAdapter(this);
        recyclerView.setAdapter(adapter);
        subtotal.setText(nf.format(Data.getCart(this).total()));

        add_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject obj = Data.place_order(context);
                String uid = null;
                try {
                    uid = obj.getString("oauth_uid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (uid == null){
                    Toast.makeText(getApplicationContext(), "please log in!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (Data.getCart(context).getServices().size() == 0){
                    Toast.makeText(getApplicationContext(), "Please add a service", Toast.LENGTH_LONG).show();
                    return;
                }
                send_cart("http://www.dhakasetup.com/api/order/orderpost.php",obj);
                //Toast.makeText(getApplicationContext(),"Order placed! For details, check ongoing orders",Toast.LENGTH_SHORT).show();



                Toast toast = Toast.makeText(getApplicationContext(), "order placed!", Toast.LENGTH_SHORT);
                TextView vv = (TextView) toast.getView().findViewById(android.R.id.message);
                if( vv != null) vv.setGravity(Gravity.CENTER);
                toast.show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);

        MenuItem menuItem = menu.findItem(R.id.action_cart);
        menuItem.setIcon(MenuPainter.paint(CartActivity.this,Data.getCart(this).size(),R.drawable.ic_action_cart));


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            //
        }
        switch (item.getItemId()) {
            case R.id.action_cart:
                Intent intent = new Intent(this,CartActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_search:
                Intent intent1 = new Intent(this,SearchActivity.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void send_cart(String url, final JSONObject jObject){
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        orderid = response;
                        Data.getCart(CartActivity.this).clear();
                        Log.d("profileres", response);
                        Toast.makeText(CartActivity.this,orderid,Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context,ScheduleActivity.class);
                        intent.putExtra("orderid",orderid);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("orderdata", jObject.toString());

                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(postRequest);
    }


}
