package com.yong.keyhash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textview = (TextView)findViewById(R.id.KeyHash);

        textview.setText(getHashKey());
    }

    private String getHashKey()
    {
        PackageInfo packageInfo = null;
        String value=null ;

        try
        {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures)
        {
            try
            {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                value = Base64.encodeToString(md.digest(), Base64.DEFAULT);

                Log.e("KeyHash", value);
            }
            catch (NoSuchAlgorithmException e)
            {
                value = e.toString();
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }

        return value;
    }
}