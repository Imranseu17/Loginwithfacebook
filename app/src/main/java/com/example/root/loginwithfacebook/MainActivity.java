package com.example.root.loginwithfacebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.jar.Attributes;

import static android.R.attr.id;

public class MainActivity extends FragmentActivity{

    CallbackManager callbackManager;
    LoginButton loginButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(MainActivity.this);
        setContentView(R.layout.activity_main);
        Animation animation = AnimationUtils.loadAnimation(MainActivity.this,
                R.anim.move);
        ImageView image1 = (ImageView)findViewById(R.id.imageView);
        image1.startAnimation(animation);

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email","public_profile","user_friends");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                AccessToken accessToken = loginResult.getAccessToken();
                if (accessToken != null) {
                    Toast.makeText(MainActivity.this, accessToken.getUserId(), Toast.LENGTH_LONG).show();
                    GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(JSONObject object, GraphResponse response) {

                                    try {

                                        String id  = object.getString("id");
                                        String email = object.getString("email");
                                        Toast.makeText(MainActivity.this, id, Toast.LENGTH_LONG).show();
                                        Toast.makeText(MainActivity.this, email, Toast.LENGTH_LONG).show();

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                }
                            });
                    Bundle bundle = new Bundle();
                    bundle.putString("fileds","id,name,email,birthday,gender,friends{email,name,birthday,gender,devices},cover,devices");
                    request.setParameters(bundle);
                    request.executeAsync();
                    Intent i = new Intent(MainActivity.this,Main2Activity.class);
                    i.putExtras(bundle);
                    startActivity(i);
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancel() {
                // App code
                Toast.makeText(MainActivity.this,"cancel",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(MainActivity.this,"error",Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}