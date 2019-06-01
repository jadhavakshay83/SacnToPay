package in.hatcube.sacntopay;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class ScanResultActivity extends AppCompatActivity implements PaymentResultListener {

    Button btn_pay;
    TextView txt_title,txt_desc,txt_amount;
    private Bundle extras;
    private String title,desc, amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result);
        Checkout.preload(getApplicationContext());

        extras = getIntent().getExtras();
        title = extras.getString("title");
        desc = extras.getString("desc");
        amount = extras.getString("amount");

        btn_pay = (Button) findViewById(R.id.btn_pay);
        txt_title = (TextView) findViewById(R.id.title);
        txt_desc = (TextView) findViewById(R.id.desc);
        txt_amount = (TextView) findViewById(R.id.amount);

        txt_title.setText(title);
        txt_desc.setText(desc);
        txt_amount.setText(amount);

        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Checkout checkout = new Checkout();

                final Activity activity = ScanResultActivity.this;
                try {
                    JSONObject options = new JSONObject();

                    options.put("name", "Scan to Pay");

                    options.put("description", "Order #123456");

                    options.put("currency", "INR");

                    /**
                     * Amount is always passed in PAISE
                     * Eg: "500" = Rs 5.00
                     */
                    options.put("amount", Integer.parseInt(amount) * 100);

                    checkout.open(activity, options);
                } catch(Exception e) {
                    Log.e("AK", "Error in starting Razorpay Checkout", e);
                }
            }
        });
    }

    @Override
    public void onPaymentSuccess(String s) {
        Intent intent = new Intent(ScanResultActivity.this,PaymentSuccessActivity.class);
        intent.putExtra("status", "success");
        startActivity(intent);
    }

    @Override
    public void onPaymentError(int i, String s) {
        Intent intent = new Intent(ScanResultActivity.this,PaymentSuccessActivity.class);
        intent.putExtra("status", "failure");
        startActivity(intent);
    }
}
