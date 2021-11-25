package mb.ganesh.komalacreationdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CustomerDetailsActivity extends AppCompatActivity {

    ImageView addCustomerBtn;
    Toolbar toolbar;

    RecyclerView recyclerView;
    ArrayList<CustomerDetailsModel> list = new ArrayList<>();
    String URL = "http://192.168.1.26:2000/allCustomer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.cus_det_col));

        toolbar = findViewById(R.id.toolBar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        addCustomerBtn = findViewById(R.id.addCustomerBtn);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        //        onClicks
        addCustomerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CustomerDetailsActivity.this, AddCustomerActivity.class));
            }
        });


//        RecyclerView
        recyclerView = findViewById(R.id.customerDetailsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        Create user json
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("user", "admin");
            volleyPost(jsonBody);
            Log.e("allCustomerPOST", jsonBody.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void volleyPost(JSONObject jsonObject) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String success = response.getString("success");
                    String message = response.getString("message");


                    Log.e("RES_SUCCESS", success);
                    Log.e("RES_MESSAGE", message);

                    boolean b1 = Boolean.parseBoolean(success);

                    if (b1) {
                        triggerCustomerList(message);
                    } else {

                        View v = findViewById(android.R.id.content);
                        Snackbar.make(v, "Invalid user", BaseTransientBottomBar.LENGTH_LONG)
                                .setBackgroundTint(Color.RED)
                                .show();
                    }


                } catch (JSONException e) {
                    View v = findViewById(android.R.id.content);
                    Snackbar.make(v, "Server Down", BaseTransientBottomBar.LENGTH_LONG)
                            .setBackgroundTint(Color.RED)
                            .show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                View v = findViewById(android.R.id.content);
                Snackbar.make(v, "Server Down", BaseTransientBottomBar.LENGTH_LONG)
                        .setBackgroundTint(Color.RED)
                        .show();
//                Toast.makeText(LoginActivity.this, "Server Down", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);

    }

    private void triggerCustomerList(String message) {

        try {
            JSONArray array = new JSONArray(message);

            if(array.length() != 0){
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);

                    String cusDate = object.getString("cusDate");
                    String cusFName  = object.getString("cusFName") ;
                    String cusLName =  object.getString("cusLName");
                    String MobileNumber = object.getString("MobileNumber");
                    String AlternateMobileNumber = object.getString("AlternateMobileNumber");
                    String cusAddress = object.getString("cusAddress");
                    String cusId = object.getString("cusId");

                    list.add(new CustomerDetailsModel(cusFName , cusLName , MobileNumber , AlternateMobileNumber ,cusAddress , cusDate , cusId));
                }
                recyclerView.setAdapter(new CustomerDetailsAdapter(CustomerDetailsActivity.this , list));
            }else {
                Toast.makeText(CustomerDetailsActivity.this, "No Data Added.", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}