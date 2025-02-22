package com.ngdat.cukcuklite.data.service;

import static com.ngdat.cukcuklite.data.service.FirebaseManager.USERS;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ngdat.cukcuklite.R;
import com.ngdat.cukcuklite.base.activity.BaseActivity;
import com.ngdat.cukcuklite.base.animation.ScreenAnimation;
import com.ngdat.cukcuklite.base.firebase.BaseFireBase;
import com.ngdat.cukcuklite.base.fragment.BaseFragment;
import com.ngdat.cukcuklite.data.models.User;
import com.ngdat.cukcuklite.screen.start.LoginFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class FacebookLoginServices extends BaseFireBase {

    private static final String TAG = FacebookLoginServices.class.getSimpleName();

    private FirebaseAuth mAuth;
    private User mUser;

    public FacebookLoginServices() {
        mAuth = getFirebaseAuth();
    }

    public void loginAccountFacebook(final Activity activity, CallbackManager callbackManager) {
        LoginManager.getInstance()
                .logInWithReadPermissions(activity, Arrays.asList("public_profile", "email"));
        LoginManager.getInstance()
                .registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        FragmentManager manager = ((BaseActivity) activity).getSupportFragmentManager();
                        BaseFragment.hideFragment(
                                manager,
                                manager.beginTransaction(),
                                LoginFragment.class,
                                ScreenAnimation.NONE, true, true);
                        mUser = new User();
                        GraphRequest graphRequest
                                = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        try {
                                            mUser.setUserId(object.getString("id"));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id");
                        graphRequest.setParameters(parameters);
                        graphRequest.executeAsync();
                        handleFacebookAccessToken(loginResult.getAccessToken());
                        activity.findViewById(R.id.txt_hello).startAnimation(
                                AnimationUtils.loadAnimation(activity, R.anim.anim_txt));
                        activity.findViewById(R.id.img_logo).startAnimation(
                                AnimationUtils.loadAnimation(activity, R.anim.anim_img));
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException error) {

                    }
                });
    }

    private void handleFacebookAccessToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            final FirebaseUser firebaseUser = task.getResult().getUser();
                            mUser.setName(firebaseUser.getDisplayName());
                            mUser.setEmail(firebaseUser.getEmail());
                            getDatabaseReference().child(USERS).child(firebaseUser.getUid()).setValue(mUser);
                        }
                    }
                });
    }
}