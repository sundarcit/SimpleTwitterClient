package com.yahoo.sundarm.twitterclient.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sundarm on 9/20/14.
 */
public class Tweet {
    private String body;
    private User user;
    private Long id;
    private String timestamp;

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }



    public String getTimestamp() {
        return timestamp;
    }


    public String getBody() {

        return body;
    }


    public static Tweet fromJSON(JSONObject jsonObject) throws ParseException {
        Tweet tweet = new Tweet();
        try {
            tweet.body  = jsonObject.getString("text");
            tweet.id = jsonObject.getLong("id");
            tweet.timestamp = getTwitterDate(jsonObject.getString("created_at")).toString();
            Log.d("debug", "--------Timestamp is------------" + tweet.timestamp);
            tweet.user =  User.fromJSON((JSONObject) jsonObject.get("user"));

        }
        catch (JSONException e)
        {
            Log.e("error", e.getMessage());
        }
        return tweet;
    }

    public static ArrayList<Tweet> fromJSONArray (JSONArray jsonArray)
    {
        ArrayList<Tweet> tweetList = new ArrayList<Tweet>();
        for (int i=0; i <jsonArray.length();i++)
        {
            JSONObject obj = null;
            try {
                obj = jsonArray.getJSONObject(i);
                Tweet tweet = null;
                try {
                    tweet = fromJSON(obj);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                tweetList.add(tweet);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return  tweetList;
    }

    @Override
    public String toString() {
        return body + "   " + getUser().screenName + "  ";
    }


    public static String getTwitterDate(String date) throws ParseException {

        final String TWITTER="EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(TWITTER);
        sf.setLenient(true);
        Long currentTime = System.currentTimeMillis()/1000;
        Long createdTime = sf.parse(date).getTime() / 1000;

        Long duration = currentTime - createdTime;
        String periodArray[] = {"years", "months", "weeks", "days" , "hours", "minutes", "seconds"};
        long period [] = {60 * 60 * 24 * 365,  60 * 60 * 24 * 30, 60 * 60 * 24 * 7, 60 * 60 * 24, 60 * 60, 60, 1 };
        int number = 0;
        int i = 0;
        for ( i= 0; i < period.length ; i++)
        {
             number = (int) (duration / period[i]);
            if (number > 0) {
                break;
            }
        }

        if (number > 0) {
            return   Integer.toString(number) + " " + periodArray[i] + " Ago";
        }
        return   "Now" ;
    }

}
