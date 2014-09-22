package com.yahoo.sundarm.twitterclient;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yahoo.sundarm.twitterclient.models.Tweet;
import com.yahoo.sundarm.twitterclient.models.User;

import java.util.ArrayList;

/**
 * Created by sundarm on 9/21/14.
 */


public class TwitterArrayAdapter extends ArrayAdapter<Tweet> {
    public TwitterArrayAdapter(Context context, ArrayList<Tweet> tweets) {
        super(context, R.layout.tweet_item, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Tweet tweet = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.tweet_item, parent, false);
        }
        // Lookup view for data population
        TextView tvUser = (TextView) convertView.findViewById(R.id.tvUser);
        TextView tvTweetText = (TextView) convertView.findViewById(R.id.tvTweetText);
        TextView tvTimestamp = (TextView)convertView.findViewById(R.id.tvTimestamp);
        ImageView profileImage = (ImageView)convertView.findViewById(R.id.ivProfileView);
        // Populate the data into the template view using the data object
        tvUser.setText(tweet.getUser().getScreenName());
        tvTweetText.setText(tweet.getBody());
        tvTimestamp.setText(tweet.getTimestamp());
        ImageLoader imageLoader = ImageLoader.getInstance();
        Log.d("debug", "--------The image url is " + tweet.getUser().getProfileImageUrl());
        imageLoader.displayImage(tweet.getUser().getProfileImageUrl(), profileImage );
        // Return the completed view to render on screen
        return convertView;
    }
}