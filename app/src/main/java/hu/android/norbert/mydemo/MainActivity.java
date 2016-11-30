package hu.android.norbert.mydemo;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.facebook.FacebookSdk;

public class MainActivity extends Activity {
    MainFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize the SDK before executing any other operations,
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getFragmentManager();
        fragment = (MainFragment) fragmentManager.findFragmentByTag("MainFragment");

        if (fragment == null) {
            fragment = new MainFragment();
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layoutContent, fragment, "MainFragment");
        fragmentTransaction.commit();

    }
}
