package com.stawishaloans.manu.stawishaloans;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.stawishaloans.manu.stawishaloans.Api.Network;
import com.stawishaloans.manu.stawishaloans.Api.RequestCode;
import com.stawishaloans.manu.stawishaloans.Api.SharedPrefManager;
import com.stawishaloans.manu.stawishaloans.Api.WebCompleteTask;
import com.stawishaloans.manu.stawishaloans.Api.WebTask;
import com.stawishaloans.manu.stawishaloans.Api.WebUrls;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ForgotPasword extends AppCompatActivity implements WebCompleteTask {

    private InterstitialAd mInterstitialAd;
    @Bind(R.id.send_btn)
    Button send_btn;
    @Bind(R.id.forgot_email)
    EditText forgot_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pasword);

//        MobileAds.initialize(this,"ca-app-pub-4969343742134029~1926324990");
        MobileAds.initialize(this,getResources().getString(R.string.app_banner));
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        ButterKnife.bind(this,this);
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Network.isConnectingToInternet(ForgotPasword.this)) {
                    ForgotPassword();
                    return;
                }else {
                    showInterstitial();
                }

            }
        });

        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.app_interstitial));
        if (!Network.isConnectingToInternet(ForgotPasword.this)) {
            return;
        }else {
            mInterstitialAd.loadAd(new AdRequest.Builder().build());

        }
        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                ForgotPassword();
            }
        });

    }
    public void showInterstitial(){
        if (mInterstitialAd.isLoaded()){
            mInterstitialAd.show();
        }else {
            ForgotPassword();
        }
    }
    public void ForgotPassword(){
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        if (TextUtils.isEmpty(forgot_email.getText().toString())){
            forgot_email.setError("Please enter your email");
            forgot_email.requestFocus();
            mInterstitialAd.loadAd(new AdRequest.Builder().build());

        }else if (!SharedPrefManager.getInstance(ForgotPasword.this).isValidEmail(forgot_email.getText().toString())){
            forgot_email.setError("Email not valid");
            forgot_email.requestFocus();
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
        }else {
            if (!Network.isConnectingToInternet(ForgotPasword.this)) {
                SharedPrefManager.showMessage(ForgotPasword.this, getString(R.string.network_error_msg));
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                return;
            }else {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                HashMap objectNew = new HashMap();
                objectNew.put("email", forgot_email.getText() + "");
                new WebTask(ForgotPasword.this, WebUrls.BASE_URL + WebUrls.forgot_password_api, objectNew, ForgotPasword.this, RequestCode.CODE_forgot_password, 1);
            }
        }


    }

    @Override
    public void onComplete(String response, int taskcode) {
        if (taskcode == RequestCode.CODE_forgot_password){
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.d("create_acc_response",response);
                JSONObject dataObject = jsonObject.optJSONObject("data");

                if (jsonObject.optString("status").compareTo("success")==0){
                    SharedPrefManager.setUserEmail(ForgotPasword.this,WebUrls.User_Email,forgot_email.getText().toString());
                    Toast.makeText(ForgotPasword.this,jsonObject.optString("message"),Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ForgotPasword.this,OptVerify.class));
                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                }else {
                    Toast.makeText(ForgotPasword.this,jsonObject.optString("message"),Toast.LENGTH_SHORT).show();
                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
