package org.pesmypetcare.mypetcare.activities.fragments.login;

import android.content.Context;
import android.os.AsyncTask;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Enric Hernando
 */
public class MyAsyncTask extends AsyncTask<Void, Void, String> {
    private Context context;
    private String googleEmail;
    private String scopes;
    public AsyncResponse delegate = null;

    public MyAsyncTask(String googleEmail, String scopes, Context context) {
        this.googleEmail = googleEmail;
        this.scopes = scopes;
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            return GoogleAuthUtil.getToken(Objects.requireNonNull(context), googleEmail, "oauth2:" + this.scopes);

        } catch (IOException | GoogleAuthException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(String result) {
        delegate.processFinish(result);
    }

}
