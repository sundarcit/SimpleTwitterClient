package com.yahoo.sundarm.twitterclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.sundarm.twitterclient.models.Tweet;

import org.json.JSONArray;

import java.util.ArrayList;


public class TimelineActivity extends Activity {

    private ArrayList<Tweet> tweetsList;
    private ArrayAdapter<Tweet> adapter ;
    private ListView lvTimeLine ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        TwitterRestClient client = TwitterClientApp.getRestClient();
        tweetsList = new ArrayList<Tweet>();
        populateTimeLine();
        lvTimeLine = (ListView)findViewById(R.id.lvTimeline);
        adapter = new TwitterArrayAdapter(this, tweetsList );

        lvTimeLine.setAdapter(adapter);
        Log.d("debug", String.valueOf(tweetsList.size()) + " -------is the size ");


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timeline, menu);
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


    public void populateTimeLine()
    {
        TwitterRestClient client = TwitterClientApp.getRestClient();
        client.getTimeLine(new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(JSONArray jsonArray) {
                tweetsList.addAll(Tweet.fromJSONArray(jsonArray));
                Log.d("debug", jsonArray.toString());
                Log.d("debug", "Number of tweets is ------ " + jsonArray.length());
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                Log.d("debug", "failed to retrieve tweets");
                Log.d("debug", throwable.toString());
            }
        });
    }

    public void composeTweet( MenuItem mi)
    {
        Toast.makeText(this, "Compose a new Tweet", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(TimelineActivity.this, ComposeActivity.class);
        startActivityForResult(intent, 200);


    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == 200) {
            Log.d("debug", "In timeline Activity========");
            populateTimeLine();
        }
    }
}
