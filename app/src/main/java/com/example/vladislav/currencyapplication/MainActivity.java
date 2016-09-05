package com.example.vladislav.currencyapplication;

import org.json.*;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.concurrent.Future;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity
{

    private static final String API_URL = "https://openexchangerates.org/api/latest.json?app_id=21924596e90547a9882ccf5531e7738c";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnConvert = (Button) findViewById(R.id.buttonConvert);

        final EditText usdValue = (EditText) findViewById(R.id.editTextUSD);
        final TextView gbpValue = (TextView) findViewById(R.id.textViewGBP);
        final TextView eurValue = (TextView) findViewById(R.id.textViewEUR);

        assert btnConvert != null;
        btnConvert.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (!usdValue.getText().toString().equals("")) {
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.get(API_URL, new AsyncHttpResponseHandler() {


                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                            Log.i("CHACHING", "HTTP Success");
                            try {
                                JSONObject jsonObj = new JSONObject(responseBody.toString());
                                JSONObject ratesObject = jsonObj.getJSONObject("rates");

                                Double gbpRate = ratesObject.getDouble("GBP");
                                Double eurRate = ratesObject.getDouble("EUR");
                                Log.i("CHACHING", "GBP: " + gbpRate);
                                Log.i("CHACHING", "EUR: " + eurRate);

                                Double usds = Double.valueOf(usdValue.getText().toString());

                                Double gbps = usds * gbpRate;
                                Double euros = usds * eurRate;

                                gbpValue.setText("GBP: " + String.valueOf(gbps));
                                eurValue.setText("EURO: " + String.valueOf(euros));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onStart() {
                            // called before request is started
                            super.onStart();
                        }
                    });
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please enter a USD value!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}
