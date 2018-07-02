package com.vanessaodawo.r_client;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.vanessaodawo.r_client.Fragments.Login;
import com.vanessaodawo.r_client.Fragments.Register;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    Button Google_Sign, FB_Sign, emailSignUp;
    TextView tvLogin, lbl;
    RelativeLayout rlay1, rlay2;

    FirebaseAuth firebaseAuth;
    GoogleSignInClient mGoogleSignInClient;

    CallbackManager callbackManager;

    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

    private static final String F_EMAIL = "email";

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rlay1.setVisibility(View.VISIBLE);
            rlay2.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Google_Sign = findViewById(R.id.Google_SignUp);
        FB_Sign = findViewById(R.id.FB_SignUp);
        emailSignUp = findViewById(R.id.emailSignUp);
        tvLogin = findViewById(R.id.tvLogin);
        lbl = findViewById(R.id.lbl);
        rlay1 = findViewById(R.id.childrlay);
        rlay2 = findViewById(R.id.childrlay2);

        handler.postDelayed(runnable, 15000);

        firebaseAuth = FirebaseAuth.getInstance();

        Google_Sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignUpBtn();
            }
        });

        FacebookSdk.sdkInitialize(this);
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("Success", "Login");
            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this, "Login Process Cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        FB_Sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("public_profile", "user_friends"));
                LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList(F_EMAIL));
            }
        });

        emailSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailS_Up();
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLogin();
            }
        });

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void emailS_Up() {
        Google_Sign.setVisibility(View.INVISIBLE);
        FB_Sign.setVisibility(View.INVISIBLE);
        emailSignUp.setVisibility(View.INVISIBLE);
        lbl.setVisibility(View.INVISIBLE);
        tvLogin.setVisibility(View.INVISIBLE);
        loadFragment(new Register());
    }

    private void toLogin() {
        Google_Sign.setVisibility(View.INVISIBLE);
        FB_Sign.setVisibility(View.INVISIBLE);
        emailSignUp.setVisibility(View.INVISIBLE);
        lbl.setVisibility(View.INVISIBLE);
        tvLogin.setVisibility(View.INVISIBLE);
        loadFragment(new Login());
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }
        }

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
        // [START_EXCLUDE silent]
//        showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Snackbar.make(findViewById(R.id.rlay), "Authentication Failed.", Snackbar.LENGTH_SHORT)
                                    .setAction("RETRY", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                        }
                                    }).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
//                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }

    private void googleSignUpBtn() {
        // [START signin]
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
        Toast.makeText(this, "google signup button clicked", Toast.LENGTH_SHORT).show();
    }

//    private void revokeAccess() {
//        // Firebase sign out
//        firebaseAuth.signOut();
//
//        // Google revoke access
//        mGoogleSignInClient.revokeAccess().addOnCompleteListener(this,
//                new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        updateUI(null);
//                    }
//                });
//    }

    private void updateUI(FirebaseUser currentUser) {
//        hideProgressDialog();
        if (currentUser != null) {
            findViewById(R.id.Google_SignUp).setVisibility(View.GONE);
            startActivity(new Intent(this, HomeActivity.class));
        } else {
            findViewById(R.id.Google_SignUp).setVisibility(View.VISIBLE);
        }
    }

    private void loadFragment(Register register) {
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout, new Register());
        ft.commit();
    }

    private void loadFragment(Login login) {
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout, new Login());
        ft.commit();
    }
}
