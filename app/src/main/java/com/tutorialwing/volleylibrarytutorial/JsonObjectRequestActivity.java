package com.tutorialwing.volleylibrarytutorial;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tutorialwing.volleylibrarytutorial.models.Tutorialwing;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JsonObjectRequestActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String REQUEST_TAG = "JSON_OBJECT_REQUEST_TAG";
    public static final String POST_REQUEST_TAG = "JSON_OBJECT_POST_REQUEST_TAG";
    public static final String JSON_URL = "https://tutorialwing.com/api/tutorialwing_details.json";
    public static final String JSON_URL1 = "http://api.geonames.org/citiesJSON?north=44.1&south=-9.9&east=-22.4&west=55.2&lang=de&username=demo";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.json_object_request_activity);

        View view = findViewById(R.id.get_request);
        if (view != null)
            view.setOnClickListener(this);

        view = findViewById(R.id.post_request);
        if (view != null)
            view.setOnClickListener(this);
    }

    private void sendRequest() {
        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(JSON_URL1, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        //   showResponse(response, "Showing GET request response...");

                        Toast.makeText(JsonObjectRequestActivity.this, "Get response : " + response.toString(), Toast.LENGTH_LONG).show();

                        Log.e("Get response", "" + response.toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(JsonObjectRequestActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        jsonObjectReq.setRetryPolicy(new DefaultRetryPolicy(15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Adding JsonObject request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjectReq, REQUEST_TAG);
    }

    private void sendPostRequest() {

        // Change the url of the post request as per your need...This url is just for demo purposes and
        // actually it does not post data...i.e. it will return same response irrespective of the value you
        // as parameters.

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, JSON_URL,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        showResponse(response, "Showing POST request response...");
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(JsonObjectRequestActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {

            // You can send parameters as well with POST request....
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", "Tutorialwing");
                params.put("email", "tutorialwing@gmail.com");
                params.put("password", "1234567");
                return params;



              /*  HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("username", "" + args.get(Global.ARGS_USER_NAME));
                hashMap.put("password", "" + args.get(Global.ARGS_PASSWORD));
                hashMap.put("deviceid", "" + Utilities.getDeviceSerialNumber());

                Gson gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss").serializeNulls()
                        .create();

                String jsonString = gson.toJson(hashMap);
                Log.i(TAG, "json String :: " + jsonString);

                HttpWrapper httpWrapper = new HttpWrapper(Global.HTTP_POST, Global.JSON_RESPONSE, HttpWrapper.CONTENT_TYPE_JSON, args.getString(Global.ARGS_URI), jsonString);

                return new RestLoader<Login>(ActivityLogin.this, httpWrapper, Login.class);

*/

            }
        };

        // Increase Timeout to 15 secs.
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(jsonObjReq, POST_REQUEST_TAG);
    }

    private void sendPostRequestWithHeaders() {

        // Change the url of the post request as per your need...This url is just for demo purposes and
        // actually it does not post data...i.e. it will return same response irrespective of the value you
        // as parameters.

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, JSON_URL,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        showResponse(response, "Showing POST request response...");
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
//						VolleyLog.d(TAG, "Error: " + error.getMessage());
                    }
                }) {

            // You can send parameters as header with POST request....
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("apiKey", "xxxxxxxxxxxxxxx");
                return headers;
            }
        };

        AppController.getInstance().addToRequestQueue(jsonObjReq, POST_REQUEST_TAG);
    }

    private void showResponse(JSONObject response, String extra_info) {

        Tutorialwing gson = new Gson().fromJson(response.toString(), Tutorialwing.class);

        // Tutorialwing gson = new GsonBuilder()
        //       .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        //     .create().fromJson(response, Tutorialwing.class);


        String text = gson.getFacebook().toString();


       /* Tutorialwing tutorialwing = new Tutorialwing(response);
        String text = extra_info
                + "\n\n Website: " + tutorialwing.getWebsite()
                + "\n\n Topics: " + tutorialwing.getTopics()
                + "\n\n Facebook: " + tutorialwing.getFacebook()
                + "\n\n Twitter: " + tutorialwing.getTwitter()
                + "\n\n Pinterest: " + tutorialwing.getPinterest()
                + "\n\n Youtube: " + tutorialwing.getYoutube()
                + "\n\n Message: " + tutorialwing.getMessage();
*/
        TextView txvResponse = (TextView) findViewById(R.id.request_response);
        if (txvResponse != null) {
            txvResponse.setText(text);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_request:
                sendRequest();
                break;
            case R.id.post_request:
                sendPostRequest();
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        AppController.getInstance().getRequestQueue().cancelAll(REQUEST_TAG);
    }
}
