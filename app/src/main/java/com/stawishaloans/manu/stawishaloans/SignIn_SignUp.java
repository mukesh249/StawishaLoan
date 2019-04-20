package com.stawishaloans.manu.stawishaloans;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.stawishaloans.manu.stawishaloans.Api.Network;
import com.stawishaloans.manu.stawishaloans.Api.RequestCode;
import com.stawishaloans.manu.stawishaloans.Api.SharedPrefManager;
import com.stawishaloans.manu.stawishaloans.Api.WebCompleteTask;
import com.stawishaloans.manu.stawishaloans.Api.WebTask;
import com.stawishaloans.manu.stawishaloans.Api.WebUrls;
import com.stawishaloans.manu.stawishaloans.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SignIn_SignUp extends AppCompatActivity implements WebCompleteTask {

    @Bind(R.id.sign_tv)
    TextView sign_tv;
    @Bind(R.id.registration_tv)
    TextView registration_tv;

    @Bind(R.id.tv_content)
    RelativeLayout tv_content;
    @Bind(R.id.sigin_rel)
    RelativeLayout sigin_rel;
    @Bind(R.id.reg_feilds_layout)
    RelativeLayout reg_feilds_layout;

    @Bind(R.id.logo)
    ImageView logo;

    @Bind(R.id.login_btn)
    ImageButton login_btn;
    @Bind(R.id.regitration_btn)
    ImageButton create_acc_btn;


    @Bind(R.id.sign_in_email)
    EditText sign_in_email;
    @Bind(R.id.sign_in_pass)
    EditText sign_in_pass;

    @Bind(R.id.first_name)
    EditText first_name;
    @Bind(R.id.last_name)
    EditText last_name;
    @Bind(R.id.sign_email_erg)
    EditText sign_email_erg;
    @Bind(R.id.id_passport)
    EditText id_passport;
    @Bind(R.id.mobile)
    EditText mobile;
    @Bind(R.id.sign_pass_reg)
    EditText sign_pass_reg;
    @Bind(R.id.con_pass)
    EditText con_pass;
    @Bind(R.id.forgot_tv)
    TextView forgot_tv;
    @Bind(R.id.scroll_view_signin_reg)
    LinearLayout scroll_view_signin_reg;
    private InterstitialAd mInterstitialAd;
    Boolean sclick = false;
    Boolean rclick = false,f_click=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);  // get rid of dimming
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in__sign_up);

        ButterKnife.bind(this,this);

        sigin_rel.setVisibility(View.VISIBLE);
        reg_feilds_layout.setVisibility(View.GONE);
        login_btn.setVisibility(View.VISIBLE);
        create_acc_btn.setVisibility(View.GONE);
        registration_tv.setBackground(null);
        registration_tv.setTextColor(getResources().getColor(R.color.gray));

        registration_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sign_in_email.setText("");
                sign_in_pass.setText("");
                reg_feilds_layout.setVisibility(View.VISIBLE);
                sigin_rel.setVisibility(View.GONE);
                login_btn.setVisibility(View.GONE);
                create_acc_btn.setVisibility(View.VISIBLE);
                tv_content.setVisibility(View.GONE);

               // LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
               // scroll_view_signin_reg.setLayoutParams(params);
                scroll_view_signin_reg.setBackground(getResources().getDrawable(R.drawable.registrastion_back));

                sign_tv.setBackground(null);
                sign_tv.setTextColor(getResources().getColor(R.color.gray));
                registration_tv.setBackground(getResources().getDrawable(R.drawable.button_regstration));
                registration_tv.setTextColor(getResources().getColor(R.color.white));

            }
        });

        sign_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                first_name.setText("");
                last_name.setText("");
                sign_email_erg.setText("");
                id_passport.setText("");
                mobile.setText("");
                sign_pass_reg.setText("");
                con_pass.setText("");

                sigin_rel.setVisibility(View.VISIBLE);
                reg_feilds_layout.setVisibility(View.GONE);
                login_btn.setVisibility(View.VISIBLE);
                create_acc_btn.setVisibility(View.GONE);

                tv_content.setVisibility(View.VISIBLE);

//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//                scroll_view_signin_reg.setLayoutParams(params);
                scroll_view_signin_reg.setBackground(getResources().getDrawable(R.drawable.sign_in_back));

                sign_tv.setBackground(getResources().getDrawable(R.drawable.button_signin));
                sign_tv.setTextColor(getResources().getColor(R.color.white));
                registration_tv.setBackground(null);
                registration_tv.setTextColor(getResources().getColor(R.color.gray));

            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Network.isConnectingToInternet(SignIn_SignUp.this)) {
                    SignIn();
                    return;
                }else {
                    sclick = true;
                    rclick = false;
                    f_click = false;
                    showInterstitial();
                }

//                SignIn();
            }
        });
        create_acc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Network.isConnectingToInternet(SignIn_SignUp.this)) {
                    CreateAccount();
                    return;
                }else {
                    sclick = false;
                    rclick = true;
                    f_click = false;
                    showInterstitial();
                }
            }
        });
        forgot_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    sclick = false;
                    rclick = false;
                    f_click = true;
                if (!Network.isConnectingToInternet(SignIn_SignUp.this)) {
//                    SharedPrefManager.showMessage(SignIn_SignUp.this, getString(R.string.network_error_msg));
                    startActivity(new Intent(SignIn_SignUp.this,ForgotPasword.class));
                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                    return;
                }else {
                    showInterstitial();
                }

            }
        });

        //        MobileAds.initialize(this,"ca-app-pub-4969343742134029~1926324990");
        MobileAds.initialize(this,getResources().getString(R.string.app_banner));
        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.app_interstitial));
        if (!Network.isConnectingToInternet(SignIn_SignUp.this)) {
            return;
        }else {
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
        }
        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                if (rclick){
                    CreateAccount();
                }
                if(sclick){
                    SignIn();
                }
                if (f_click){
                    startActivity(new Intent(SignIn_SignUp.this,ForgotPasword.class));
                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                }
            }
        });
    }
    public void showInterstitial(){
        if (mInterstitialAd.isLoaded()){
            mInterstitialAd.show();
        }else {

            if (rclick){
                CreateAccount();
            }
            if(sclick){
                SignIn();
            }
            if (f_click){
                startActivity(new Intent(SignIn_SignUp.this,ForgotPasword.class));
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        }
    }
    public void SignIn(){
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        if (TextUtils.isEmpty(sign_in_email.getText().toString())){
            sign_in_email.setError("Please enter email");
            sign_in_email.requestFocus();
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
        } else if (TextUtils.isEmpty(sign_in_pass.getText().toString())){
            sign_in_pass.setError("Please enter your password");
            sign_in_pass.requestFocus();
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
        }else if (!SharedPrefManager.getInstance(SignIn_SignUp.this).isValidEmail(sign_in_email.getText().toString())){
            sign_in_email.setError("Email not valid");
            sign_in_email.requestFocus();
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
        }else {
            if (!Network.isConnectingToInternet(SignIn_SignUp.this)) {
                SharedPrefManager.showMessage(SignIn_SignUp.this, getString(R.string.network_error_msg));
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                return;
            }else {
                HashMap objectNew = new HashMap();
                objectNew.put("email", sign_in_email.getText() + "");
                objectNew.put("password", sign_in_pass.getText() + "");
                new WebTask(SignIn_SignUp.this, WebUrls.BASE_URL + WebUrls.login_api, objectNew, SignIn_SignUp.this, RequestCode.CODE_Login, 1);
            }
        }
    }
    public void CreateAccount(){
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        if (TextUtils.isEmpty(first_name.getText().toString())){
            first_name.setError("Please enter the First Name");
            first_name.requestFocus();
        } else if (TextUtils.isEmpty(last_name.getText().toString())){
            last_name.setError("Please enter the Last Name");
            last_name.requestFocus();
        } else if (!SharedPrefManager.getInstance(SignIn_SignUp.this).isValidEmail(sign_email_erg.getText().toString())){
            sign_email_erg.setError("Email not valid");
            sign_email_erg.requestFocus();
        } else if (sign_pass_reg.getText().length()<6){
            sign_pass_reg.setError("Password must have atleast 6 digit");
            sign_pass_reg.requestFocus();
        } else if (TextUtils.isEmpty(con_pass.getText().toString())){
            con_pass.setError("Please enter the confirm Password");
            con_pass.requestFocus();
        }else if (!sign_pass_reg.getText().toString().equals(con_pass.getText().toString())){
            con_pass.setError("Password and confirm Password do not match");
            con_pass.requestFocus();
        }else if (TextUtils.isEmpty(id_passport.getText().toString())){
            id_passport.setError("Please enter your ID number/ Passport NO.");
            id_passport.requestFocus();
        }else if (TextUtils.isEmpty(mobile.getText().toString())){
            mobile.setError("Please your mobile number");
            mobile.requestFocus();
        }else {
            if (!Network.isConnectingToInternet(SignIn_SignUp.this)) {
                SharedPrefManager.showMessage(SignIn_SignUp.this, getString(R.string.network_error_msg));
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                return;
            }else {
//            showInterstitial();
                try {
                    HashMap objectNew = new HashMap();
                    objectNew.put("first_name", first_name.getText() + "");
                    objectNew.put("last_name", last_name.getText() + "");
                    objectNew.put("mobile", mobile.getText() + "");
                    objectNew.put("email", sign_email_erg.getText() + "");
                    objectNew.put("password", sign_pass_reg.getText() + "");
                    objectNew.put("confirm_password", con_pass.getText() + "");
                    objectNew.put("id_proof", id_passport.getText() + "");

                    Log.d("data_obj", objectNew.toString());
                    new WebTask(SignIn_SignUp.this, WebUrls.BASE_URL + WebUrls.acc_api, objectNew, SignIn_SignUp.this, RequestCode.CODE_Signup, 1);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onComplete(String response, int taskcode) {
        if (taskcode == RequestCode.CODE_Signup){
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.d("create_acc_response",response);
                JSONObject dataObject = jsonObject.optJSONObject("data");

                if (jsonObject.optString("status").compareTo("success")==0){
                    SharedPrefManager.setUserID(SignIn_SignUp.this,WebUrls.User_Id,dataObject.getString("id"));
                    SharedPrefManager.setUserEmail(SignIn_SignUp.this,WebUrls.User_Email,dataObject.getString("email"));
                    SharedPrefManager.setIDPassPort(SignIn_SignUp.this,WebUrls.User_Id_Passport,dataObject.getString("id_proof"));
                    SharedPrefManager.setMobile(SignIn_SignUp.this,WebUrls.User_Mobile,dataObject.getString("phone"));
                    Toast.makeText(SignIn_SignUp.this,jsonObject.optString("message"),Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignIn_SignUp.this,PurposeOfLoan.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                }else {
                    Toast.makeText(SignIn_SignUp.this,jsonObject.optString("message"),Toast.LENGTH_SHORT).show();
                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (taskcode == RequestCode.CODE_Login ){
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.d("login_response",response);
                JSONObject dataObject = jsonObject.optJSONObject("data");
                if (jsonObject.optString("status").compareTo("success")==0){
                    SharedPrefManager.setUserID(SignIn_SignUp.this,WebUrls.User_Id,dataObject.getString("id"));
                    SharedPrefManager.setUserEmail(SignIn_SignUp.this,WebUrls.User_Email,dataObject.getString("email"));
                    SharedPrefManager.setIDPassPort(SignIn_SignUp.this,WebUrls.User_Id_Passport,dataObject.getString("id_proof"));
                    SharedPrefManager.setMobile(SignIn_SignUp.this,WebUrls.User_Mobile,dataObject.getString("phone"));
                    Toast.makeText(SignIn_SignUp.this,jsonObject.optString("message"),Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignIn_SignUp.this,PurposeOfLoan.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                }else {
                    Toast.makeText(SignIn_SignUp.this,jsonObject.optString("message"),Toast.LENGTH_SHORT).show();
                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

}
