package com.yahoo.sundarm.twitterclient.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sundarm on 9/20/14.
 */
public class User {
    String name;
    Long id;

    public String getName() {
        return name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getScreenName() {
        return screenName;
    }

    public Long getId() {
        return id;
    }

    String screenName;
    String profileImageUrl;

    public static User fromJSON(JSONObject jsonObject)
    {
        User user = new User();
        try {
            user.id =  jsonObject.getLong("id");
            user.name =  jsonObject.getString("name");
            user.screenName = "@" + jsonObject.getString("screen_name");
            user.profileImageUrl = jsonObject.getString("profile_image_url");

        }
        catch (JSONException e)
        {
            Log.e("error", e.getMessage());
        }
        return  user;

    }

}
