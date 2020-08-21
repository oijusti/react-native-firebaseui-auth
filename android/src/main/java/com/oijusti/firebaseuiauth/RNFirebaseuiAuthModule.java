package com.oijusti.firebaseuiauth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class RNFirebaseuiAuthModule extends ReactContextBaseJavaModule {
  private final ReactApplicationContext reactContext;
  private static int RC_SIGN_IN = 100;
  private Promise signInPromise;
  private boolean isNewUser = false;

  /**
   * Indicates that user cancelled the sign-in process.
   */
  private final String ERROR_USER_CANCELLED = "ERROR_USER_CANCELLED";
  /**
   * Indicates that firebase encountered an error.
   */
  private final String ERROR_FIREBASE = "ERROR_FIREBASE";


  public RNFirebaseuiAuthModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    reactContext.addActivityEventListener(mActivityEventListener);
  }

  @Override
  public String getName() {
    return "RNFirebaseuiAuth";
  }

  @ReactMethod
  public void signIn(final ReadableMap config, final Promise promise) {
    signInPromise = promise;
    Activity currentActivity = getCurrentActivity();
    ReadableArray optProviders = config.getArray("providers");

    final String tosUrl = config.hasKey("tosUrl") ? config.getString("tosUrl") : null;
    final String privacyPolicyUrl = config.hasKey("privacyPolicyUrl") ? config.getString("privacyPolicyUrl") : null;
    final List<AuthUI.IdpConfig> providers = new ArrayList<>();

    for (int i = 0; i < optProviders.size(); i++)
    {
      if (optProviders.getString(i).equals("anonymous")) {
        providers.add(new AuthUI.IdpConfig.AnonymousBuilder().build());
        break;
      }
      if (optProviders.getString(i).equals("facebook")) {
        providers.add(new AuthUI.IdpConfig.FacebookBuilder().build());
      }
      else if (optProviders.getString(i).equals("google")) {
        providers.add(new AuthUI.IdpConfig.GoogleBuilder().build());
      }
      else if (optProviders.getString(i).equals("email")) {
        providers.add(new AuthUI.IdpConfig.EmailBuilder().build());
      }
      else if (optProviders.getString(i).equals("phone")) {
        providers.add(new AuthUI.IdpConfig.PhoneBuilder().build());
      }
      else if (optProviders.getString(i).equals("apple")) {
        providers.add(new AuthUI.IdpConfig.AppleBuilder().build());
      }
      else if (optProviders.getString(i).equals("yahoo")) {
        providers.add(new AuthUI.IdpConfig.YahooBuilder().build());
      }
      else if (optProviders.getString(i).equals("github")) {
        providers.add(new AuthUI.IdpConfig.GitHubBuilder().build());
      }
      else if (optProviders.getString(i).equals("twitter")) {
        providers.add(new AuthUI.IdpConfig.TwitterBuilder().build());
      }
      else if (optProviders.getString(i).equals("microsoft")) {
        providers.add(new AuthUI.IdpConfig.MicrosoftBuilder().build());
      }
    }
    currentActivity.startActivityForResult(
            AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .setTosAndPrivacyPolicyUrls(
                            tosUrl,
                            privacyPolicyUrl)
                    .build(),
            RC_SIGN_IN);
  }

  @ReactMethod
  public void signOut(final Promise promise) {
    Context context = getReactApplicationContext();
    AuthUI.getInstance()
            .signOut(context)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
              public void onComplete(@NonNull Task<Void> task) {
                promise.resolve(true);
              }
            });
  }

  @ReactMethod
  public void getCurrentUser(final Promise promise) {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    if (user != null) {
      promise.resolve(mapUser(user));
      return;
    }
    promise.resolve(null);
  }

  private final ActivityEventListener mActivityEventListener = new BaseActivityEventListener() {
    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent intent) {
      if (requestCode == RC_SIGN_IN) {
        IdpResponse response = IdpResponse.fromResultIntent(intent);

        if (resultCode == RESULT_OK) {
          isNewUser = response.isNewUser();
          FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
          signInPromise.resolve(mapUser(user));
        } else if (response == null) {
          signInPromise.reject(ERROR_USER_CANCELLED, "User cancelled the sign-in process");
        }
        else {
          signInPromise.reject(ERROR_FIREBASE, response.getError().getMessage(), response.getError());
        }
      }
    }
  };

  private WritableMap mapUser(FirebaseUser user) {
    WritableMap resultData = new WritableNativeMap();
    resultData.putString("uid", user.getUid());
    resultData.putString("displayName", user.getDisplayName());
    resultData.putString("photoURL", user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : null);
    resultData.putString("email", user.getEmail());
    resultData.putString("phoneNumber", user.getPhoneNumber());
    resultData.putString("providerId", user.getProviderId());
    resultData.putBoolean("isNewUser", isNewUser);
    resultData.putDouble("creationTimestamp", user.getMetadata().getCreationTimestamp());
    resultData.putDouble("lastSignInTimestamp", user.getMetadata().getLastSignInTimestamp());
    return resultData;
  }
}
