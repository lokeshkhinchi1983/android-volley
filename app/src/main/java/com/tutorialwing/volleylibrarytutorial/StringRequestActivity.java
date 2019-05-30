package com.tutorialwing.volleylibrarytutorial;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class StringRequestActivity extends AppCompatActivity {

    public static final String REQUEST_TAG = "STRING_REQUEST_TAG";
    public static final String JSON_URL = "https://tutorialwing.com/api/tutorialwing_welcome.json";
    public static final String JSON_URL1 = "http://dummy.restapiexample.com/api/v1/employee/1";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.string_request_activity);

        View view = findViewById(R.id.get_request);
        if (view != null)
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendRequest();
                }
            });
    }

    private void sendRequest() {
        StringRequest stringRequest = new StringRequest(JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showResponse(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(StringRequestActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        // Increase Timeout to 15 secs.
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(stringRequest, REQUEST_TAG);
    }

    private void showResponse(String response) {
        TextView txvResponse = (TextView) findViewById(R.id.request_response);
        if (txvResponse != null) {
            txvResponse.setText(response);
        }
    }
}
