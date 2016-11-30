package hu.android.norbert.mydemo;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

/**
 * Created by Norbert on 2016.11.28..
 */

public class MainFragment extends Fragment {

    public static final String TAG = "MainFragment";

    CallbackManager callbackManager;
    TextView tvName;
    TextView tvEmail;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = View.inflate(getActivity(), R.layout.fragment_main, null);
        if (savedInstanceState != null) {
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvName.setText(savedInstanceState.getString("tvName"));
            tvEmail = (TextView) view.findViewById(R.id.tvEmail);
            tvEmail.setText(savedInstanceState.getString("tvEmail"));
        }
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions( "public_profile", "email");
        loginButton.setFragment(this);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {


                AccessToken accessToken = AccessToken.getCurrentAccessToken();

                GraphRequestAsyncTask request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject user, GraphResponse graphResponse) {
                        tvName = (TextView) view.findViewById(R.id.tvName);
                        tvName.setText(user.optString("name", getString(R.string.anonymus)));

                        TextView tvEmail = (TextView) view.findViewById(R.id.tvEmail);
                        tvEmail.setText(user.optString("email", getString(R.string.anonymus)));


                    }
                }).executeAsync();

            }

            @Override
            public void onCancel() {
                Toast.makeText(getActivity(), R.string.login_cancel, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(getActivity(), R.string.login_error, Toast.LENGTH_LONG).show();
            }

        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        tvName = (TextView) getView().findViewById(R.id.tvName);
        tvEmail = (TextView) getView().findViewById(R.id.tvEmail);
        outState.putString("tvName", tvName.getText().toString());
        outState.putString("tvEmail", tvEmail.getText().toString());
    }
}
