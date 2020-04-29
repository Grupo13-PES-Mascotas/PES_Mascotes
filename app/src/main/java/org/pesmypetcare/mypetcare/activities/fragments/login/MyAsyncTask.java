package org.pesmypetcare.mypetcare.activities.fragments.login;

import android.content.Context;
import android.os.AsyncTask;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.Scope;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Enric Hernando
 */
public class MyAsyncTask extends AsyncTask<Void, Void, String> {
    private GoogleSignInAccount acct;
    private Context context;
    public AsyncResponse delegate = null;

    public MyAsyncTask(GoogleSignInAccount acct, Context context) {
        this.acct = acct;
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        StringBuilder scopes = new StringBuilder();
        for (Scope s : acct.getRequestedScopes())
            scopes.append(s.toString()).append(" ");
        scopes = new StringBuilder(scopes.substring(0, scopes.toString().lastIndexOf(' ')));
        try {
            return GoogleAuthUtil.getToken(Objects.requireNonNull(context),
                    Objects.requireNonNull(acct.getAccount()), "oauth2:" + scopes.toString());
        } catch (IOException | GoogleAuthException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(String result) {
        System.out.println(result);
        delegate.processFinish(result);
    }

}
