package in.hatcube.sacntopay;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class PaymentSuccessActivity extends AppCompatActivity {

    ImageView img;
    TextView txt_title,txt_desc;
    WebView vendingLinkSuccessView;
    private Bundle extras;
    private String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);

        findViewById(R.id.btn_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PaymentSuccessActivity.this, MainActivity.class));
            }
        });

        extras = getIntent().getExtras();
        status = extras.getString("status");

        vendingLinkSuccessView = (WebView) findViewById(R.id.vendingLinkSuccess);
        txt_title = (TextView) findViewById(R.id.pageTitle);
        txt_desc = (TextView) findViewById(R.id.pageDesc);
        img = (ImageView) findViewById(R.id.pageImg);

        if(status.equals("success")) {

            SharedPreferences prefs = getSharedPreferences("APP_CONF", MODE_PRIVATE);
            String vendingUrl = prefs.getString("vending_url", null);
            if (vendingUrl != null) {
                Log.d("AK",vendingUrl);
                vendingLinkSuccessView.setWebViewClient(new WebViewClient());
                vendingLinkSuccessView.loadUrl(vendingUrl);
//                RequestQueue queue = Volley.newRequestQueue(this);
//                StringRequest stringRequest = new StringRequest(Request.Method.GET, vendingUrl,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                // Display the first 500 characters of the response string.
//                                Log.d("AK",response);
//                            }
//                        }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("AK",error.toString());
//                    }
//                });
//                queue.add(stringRequest);
            }

            img.setImageResource(R.drawable.checked);
            txt_title.setText("Payment Successful!");
            txt_desc.setText("Your order will be dispensed immediately");
        } else {
            img.setImageResource(R.drawable.error);
            txt_title.setText("Payment Failed!");
            txt_desc.setText("Please try again..");
        }
    }
}
