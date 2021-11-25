package mb.ganesh.komalacreationdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.Key;

public class RateUpdaterActivity extends AppCompatActivity {


    MaterialButton milkUpdateBtn;
    TextInputEditText milkRateFiled;
    TextInputLayout milkRateFieldLayout;
    Toolbar toolbar;

//    Server
    String URL = "http://192.168.1.26:2000/milkRateUpdate";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_updater);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.rate_upd_col));

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        milkRateFiled = findViewById(R.id.milkRateFiled);
        milkUpdateBtn = findViewById(R.id.milkUpdateBtn);
        milkRateFieldLayout = findViewById(R.id.milkRateFieldLayout);

        milkRateFiled.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                milkRateFieldLayout.setError("");
                milkRateFieldLayout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        milkUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cost = milkRateFiled.getText().toString();

                if(TextUtils.isEmpty(cost) && cost.equals("")){
                    milkRateFieldLayout.setError("Please enter milk cost");
                    milkRateFieldLayout.setErrorEnabled(true);
                }else {

                    try{
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("user","admin");
                        jsonObject.put("milkCost" , cost);
                        volleyPost(jsonObject);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    Toast.makeText(RateUpdaterActivity.this, "Price Updated to " + cost, Toast.LENGTH_SHORT).show();
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
                    String message = response.getString("message");

                    Log.e("RES_SUCCESS", success);
                    Log.e("RES_MESSAGE", message);

                    boolean b1 = Boolean.parseBoolean(success);

                    if (b1) {
                        View v = findViewById(android.R.id.content);
                        Snackbar.make(v, "Milk Cost Updated" , BaseTransientBottomBar.LENGTH_LONG)
                                .setBackgroundTint(Color.GREEN)
                                .show();


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



}