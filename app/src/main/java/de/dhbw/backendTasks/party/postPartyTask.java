package de.dhbw.backendTasks.party;

import android.content.Context;
import android.app.Activity;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;

import de.dhbw.BackEndCommunication.RestBackendCommunication;
import de.dhbw.app2night.MainActivity;
import de.dhbw.app2night.TestFragment;
import de.dhbw.exceptions.BackendCommunicationException;
import de.dhbw.exceptions.NetworkUnavailableException;
import de.dhbw.exceptions.NoTokenFoundException;
import de.dhbw.model.Party;
import de.dhbw.utils.PropertyUtil;

/**
 * Created by Tobias Berner on 20.10.2016.
 */

public class PostPartyTask extends AsyncTask<String,Void,String> implements ApiPartyTask {
    //Initialisert von PropertyUtil
    private static String url;
    PostParty fragment;
    Party toPost;

    public void setUrl(String urlParm){
        url = urlParm;
    }

    public PostPartyTask(PostParty fr, Party party){
        fragment = fr;
        prepare(party);
    }

    private void prepare(Party party){
        PropertyUtil.getInstance().init(this);
        String jString = new Gson().toJson(party);
        this.execute(url,jString);
    }

    @Override
    protected String doInBackground(String... params) {
        try{
                RestBackendCommunication rbc = new RestBackendCommunication();
                return rbc.postRequest(params[0],params[1]);
        } catch (IOException e) {
            return "Unable to retrieve web page. URL may be invalid.";
        } catch (BackendCommunicationException e) {
            e.printStackTrace();
        } catch (NetworkUnavailableException e) {
            e.printStackTrace();
        } catch (NoTokenFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result){
        result = result.substring(1,result.length()-2);
        toPost.setPartId(result);
        fragment.onFinishPostParty(toPost);
    }
}
