package de.dhbw.utils;

import android.content.Context;
import android.content.res.AssetManager;
import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;

import de.dhbw.backendTasks.party.ApiPartyTask;
import de.dhbw.backendTasks.party.ChangePartyByIdTask;
import de.dhbw.backendTasks.party.DeletePartyByIdTask;
import de.dhbw.backendTasks.party.GetPartyListTask;
import de.dhbw.backendTasks.party.PostPartyTask;
import de.dhbw.backendTasks.token.GetToken;

/**
 * Created by Tobias Berner on 27.10.2016.
 */

public class PropertyUtil {

    private static PropertyUtil pu = null;

    private PropertyUtil(){   }

    public static PropertyUtil getInstance(){
        if(pu == null)
            pu = new PropertyUtil();
        return pu;

    }

    public void init (GetToken gt, Context c){
        Properties props = getProperties(c);
        gt.setUrl(props.getProperty("app2night.api.url.token.get"));

    }

    public String getBodyOfGetToken (String username, String password, Context c){
        Properties props =  getProperties(c);
        String body = props.getProperty("app2night.token.get.body");
        body = "username=" + username + "&password=" + password + "&" + body;
        return body;
    }

    public void init(SettingsAdministration sa, Context c) {
        Properties props =  getProperties(c);
        sa.setSharedPrefs(props.getProperty("app2night.settings.sharedpref"));
        sa.setDefaultSetting("radius",props.getProperty("app2night.settings.default.radius"));
    }

    public  void init(ApiPartyTask apt, Context c) {
        Properties props =  getProperties(c);
        apt.setUrl(props.getProperty("app2night.api.url.party"));
    }

    public Properties getProperties(Context context){
        Properties properties = new Properties();
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open("app2night.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }


    



}
