package com.oijusti.firebaseuiauth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class RNFirebaseuiAuthModule extends ReactContextBaseJavaModule {
  private static int RC_SIGN_IN = 100;
  private final ReactApplicationContext reactContext;
  private final String AUTH_STATE_CHANGED_EVENT = "AuthStateChanged";
  private Promise signInPromise;
  private boolean isNewUser = false;

  /**
   * Indicates the user cancelled a sign-in flow.
   */
  private final String ERROR_USER_CANCELLED = "ERROR_USER_CANCELLED";

  /**
   * Indicates firebase encountered an error.
   */
  private final String ERROR_FIREBASE = "ERROR_FIREBASE";


  public RNFirebaseuiAuthModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    reactContext.addActivityEventListener(mActivityEventListener);
  }

  @ReactMethod
  public void addListener(String eventName) {
    // Set up any upstream listeners or background tasks as necessary
  }

  @ReactMethod
  public void removeListeners(Integer count) {
    // Remove upstream listeners, stop unnecessary background tasks
  }

  @Override
  public String getName() {
    return "RNFirebaseuiAuth";
  }

  @ReactMethod
  public void signIn(final ReadableMap config, final Promise promise) {
    signInPromise = promise;
    Activity currentActivity = getCurrentActivity();

    final ReadableArray cfgProviders = config.getArray("providers");
    final ReadableArray cfgCustomizations = config.hasKey("customizations") ? config.getArray("customizations") : null;
    final String tosUrl = config.hasKey("tosUrl") ? config.getString("tosUrl") : null;
    final String privacyPolicyUrl = config.hasKey("privacyPolicyUrl") ? config.getString("privacyPolicyUrl") : null;
    final boolean allowNewEmailAccounts = !config.hasKey("allowNewEmailAccounts") || config.getBoolean("allowNewEmailAccounts");
    final boolean requireDisplayName = !config.hasKey("requireDisplayName") || config.getBoolean("requireDisplayName");

    final List<AuthUI.IdpConfig> providers = new ArrayList<>();

    for (int i = 0; i < cfgProviders.size(); i++)
    {
      String provider = cfgProviders.getString(i);
      if (provider.equals("anonymous")) {
        providers.add(new AuthUI.IdpConfig.AnonymousBuilder().build());
        break;
      }
      if (provider.equals("facebook")) {
        providers.add(new AuthUI.IdpConfig.FacebookBuilder().build());
      }
      else if (provider.equals("google")) {
        providers.add(new AuthUI.IdpConfig.GoogleBuilder().build());
      }
      else if (provider.equals("email")) {
        providers.add(new AuthUI.IdpConfig.EmailBuilder()
                .setAllowNewAccounts(allowNewEmailAccounts)
                .setRequireName(requireDisplayName)
                .build());
      }
      else if (provider.equals("phone")) {
        providers.add(new AuthUI.IdpConfig.PhoneBuilder().build());
      }
      else if (provider.equals("apple")) {
        providers.add(new AuthUI.IdpConfig.AppleBuilder().build());
      }
      else if (provider.equals("yahoo")) {
        providers.add(new AuthUI.IdpConfig.YahooBuilder().build());
      }
      else if (provider.equals("github")) {
        providers.add(new AuthUI.IdpConfig.GitHubBuilder().build());
      }
      else if (provider.equals("twitter")) {
        providers.add(new AuthUI.IdpConfig.TwitterBuilder().build());
      }
      else if (provider.equals("microsoft")) {
        providers.add(new AuthUI.IdpConfig.MicrosoftBuilder().build());
      }
    }

    AuthUI.SignInIntentBuilder builder = AuthUI.getInstance().createSignInIntentBuilder();
    if (cfgCustomizations != null) {
      try {
        PackageManager pm = reactContext.getPackageManager();
        String packageName = reactContext.getPackageName();
        Resources resources = pm.getResourcesForApplication(packageName);

        int loginTheme = resources.getIdentifier("AuthTheme", "style", packageName);
        int loginLogo = resources.getIdentifier("auth_logo", "drawable", packageName);

        for (int i = 0; i < cfgCustomizations.size(); i++) {
          String customization = cfgCustomizations.getString(i);
          if (customization.equals("theme")) {
            builder.setTheme(loginTheme);
          }
          else if (customization.equals("logo")) {
            builder.setLogo(loginLogo);
          }
        }
      } catch (PackageManager.NameNotFoundException e) { }
    }

    currentActivity.startActivityForResult(
            builder
                    .setAvailableProviders(providers)
                    .setTosAndPrivacyPolicyUrls(
                            tosUrl,
                            privacyPolicyUrl)
                    .build(),
            RC_SIGN_IN);
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

  @ReactMethod
  public void signOut(final Promise promise) {
    Context context = getReactApplicationContext();
    AuthUI.getInstance()
            .signOut(context)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
              public void onComplete(@NonNull Task<Void> task) {
                WritableMap params = Arguments.createMap();
                params.putMap("user", null);
                sendEvent(reactContext, AUTH_STATE_CHANGED_EVENT, params);
                promise.resolve(true);
              }
            })
            .addOnFailureListener(new OnFailureListener() {
              @Override
              public void onFailure(@NonNull Exception e) {
                promise.reject(ERROR_FIREBASE, e.getMessage(), e);
              }
            });
  }

  @ReactMethod
  public void deleteUser(final Promise promise) {
    Context context = getReactApplicationContext();
    AuthUI.getInstance()
            .delete(context)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
              public void onComplete(@NonNull Task<Void> task) {
                promise.resolve(true);
              }
            })
            .addOnFailureListener(new OnFailureListener() {
              @Override
              public void onFailure(@NonNull Exception e) {
                promise.reject(ERROR_FIREBASE, e.getMessage(), e);
              }
            });
  }

  private final ActivityEventListener mActivityEventListener = new BaseActivityEventListener() {
    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent intent) {
      if (requestCode == RC_SIGN_IN) {
        IdpResponse response = IdpResponse.fromResultIntent(intent);
        if (response == null) {
          signInPromise.reject(ERROR_USER_CANCELLED, "User cancelled the sign-in process");
          return;
        }
        if (resultCode == RESULT_OK) {
          isNewUser = response.isNewUser();
          FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
          WritableMap params = Arguments.createMap();
          params.putMap("user", mapUser(user));
          sendEvent(reactContext, AUTH_STATE_CHANGED_EVENT, params);
          signInPromise.resolve(mapUser(user));
        } else {
          signInPromise.reject(ERROR_FIREBASE, response.getError().getMessage(), response.getError());
        }
      }
    }
  };

  private void sendEvent(ReactContext reactContext, String eventName, @Nullable WritableMap params) {
    reactContext
            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
            .emit(eventName, params);
  }

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
