package mb.ganesh.komalacreationdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;

public class HomeActivity extends AppCompatActivity {

    MaterialCardView cusDetailsBtn, cusOrderCard, rareUpdaterCard, reportCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.primary_color));

//        Initialize variables
        cusDetailsBtn = findViewById(R.id.customerDetailsCard);
        cusOrderCard = findViewById(R.id.orderetailsCard);
        rareUpdaterCard = findViewById(R.id.rateUpdateCard);
        reportCard = findViewById(R.id.reportCard);


//        OnClicks
        cusDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, CustomerDetailsActivity.class));
            }
        });

        cusOrderCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSnackBar();
            }
        });

        rareUpdaterCard.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, RateUpdaterActivity.class));
            }
        });

        reportCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSnackBar();
            }
        });
    }

    private void showSnackBar() {
        View view = findViewById(android.R.id.content);
        Snackbar.make(view, "Under Processing", Snackbar.LENGTH_SHORT)
                .show();
    }
}