package mb.ganesh.komalacreationdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class AddCustomerActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextInputEditText firstName;
    TextInputEditText lastName;
    TextInputEditText primaryPhoneNo;
    TextInputEditText secondaryPhoneNo;
    TextInputEditText address;
    MaterialButton saveBtn;

    //    Server
    String URL = "http://192.168.1.26:2000/addCustomer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.cus_det_col));


        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        primaryPhoneNo = findViewById(R.id.mobNumber);
        secondaryPhoneNo = findViewById(R.id.mobNumber2);
        address = findViewById(R.id.address);
        saveBtn = findViewById(R.id.addCustomerPageBtn);
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


//        SaveBtn onClick
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tempFirstName = firstName.getText().toString().trim();
                String tempLastName = lastName.getText().toString().trim();
                String tempPrimaryNo = primaryPhoneNo.getText().toString().trim();
                String tempSecondaryNo = secondaryPhoneNo.getText().toString().trim();
                String tempAddress = address.getText().toString().trim();

                if (TextUtils.isEmpty(tempFirstName) || TextUtils.isEmpty(tempLastName) || TextUtils.isEmpty(tempPrimaryNo)  || TextUtils.isEmpty(tempAddress)) {
                    Toast.makeText(AddCustomerActivity.this, "Please enter all the details", Toast.LENGTH_SHORT).show();
                } else {
                    storeDB(tempFirstName, tempLastName, tempPrimaryNo, tempSecondaryNo, tempAddress);
                }

            }
        });


    }

    public void volleyPost(JSONObject jsonObject) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String success = response.getString("success");
                    String  message = response.getString("message");


                    Log.e("RES_SUCCESS", success);
                    Log.e("RES_MESSAGE", message);

                    boolean b1 = Boolean.parseBoolean(success);

                    if (b1) {
                        Toast.makeText(AddCustomerActivity.this, message, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddCustomerActivity.this , CustomerDetailsActivity.class));
                            finish();
                    } else {
                        Toast.makeText(AddCustomerActivity.this, message, Toast.LENGTH_SHORT).show();

                    }


                } catch (JSONException e) {
                    Toast.makeText(AddCustomerActivity.this, "Server Down", Toast.LENGTH_SHORT).show();
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

    private void storeDB(String tempFirstName, String tempLastName, String tempPrimaryNo, String tempSecondaryNo, String tempAddress) {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("cusFName", tempFirstName);
            jsonBody.put("cusLName", tempLastName);
            jsonBody.put("MobileNumber", tempPrimaryNo);
            jsonBody.put("AlternateMobileNumber", tempSecondaryNo);
            jsonBody.put("cusAddress", tempAddress);

            Log.e("singleCusData" , jsonBody.toString());

            volleyPost(jsonBody);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}