package com.yahoo.sundarm.twitterclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.sundarm.twitterclient.models.Tweet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;


public class ComposeActivity extends Activity {

    TextView etCompose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        etCompose = (TextView) findViewById(R.id.etCompose);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.compose, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void doTweet(View v) {

        String text = etCompose.getText().toString();
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();


        TwitterRestClient client = TwitterClientApp.getRestClient();
        client.postTweet(new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(JSONObject json) {
                Log.d("debug", "Post succeeded");
                Log.d("debug", "---------response is " + json.toString());
                Tweet newTweet = null;
                try {
                    newTweet = Tweet.fromJSON(json);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent();
                intent.putExtra("tweet",newTweet);
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                Log.d("debug", "failed to retrieve tweets");
                Log.d("debug", throwable.toString());
            }
        }, text);



    }
}
