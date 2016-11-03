package de.dhbw.backendTasks.party;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import de.dhbw.BackEndCommunication.RestBackendCommunication;
import de.dhbw.app2night.MainActivity;
import de.dhbw.exceptions.BackendCommunicationException;
import de.dhbw.exceptions.NetworkUnavailableException;
import de.dhbw.model.Party;
import de.dhbw.utils.JSONHelper;
import de.dhbw.utils.PropertyUtil;

/**
 * Created by Tobias Berner on 20.10.2016.
 */

public class GetPartyListTask extends AsyncTask<String, Void, String> implements ApiPartyTask {

    //Initialisert von PropertyUtil
    private static String url;
    MainActivity mainActivity;

    public void setUrl(String urlParm) {
        url = urlParm;
    }

    public GetPartyListTask(MainActivity mA) {
        mainActivity = mA;
        prepare(mA);
    }

    private void prepare(Context c) {
        PropertyUtil.getInstance().init(this, c);
        this.execute(url);
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            RestBackendCommunication rbc = RestBackendCommunication.getInstance();
            if (mainActivity != null)
                return rbc.getRequest(params[0], mainActivity);
        } catch (IOException e) {
            return "Unable to retrieve web page. URL may be invalid.";
        } catch (BackendCommunicationException e) {
            e.printStackTrace();
        } catch (NetworkUnavailableException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {

        Party[] parties = new Gson().fromJson(result, Party[].class);
        for (Party p : parties){
            System.out.println(p.getPartId());
            System.out.println(p.getLocation().getCityName());

        }

    }
}

