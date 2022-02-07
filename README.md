
[![oijusti](https://circleci.com/gh/oijusti/react-native-firebaseui-auth.svg?style=svg)](https://circleci.com/gh/oijusti/react-native-firebaseui-auth)

[![Build Status](https://travis-ci.org/oijusti/react-native-firebaseui-auth.svg?branch=master)](https://travis-ci.org/oijusti/react-native-firebaseui-auth)

[prs]: https://img.shields.io/badge/PRs-welcome-brightgreen.svg
[prs-url]: https://github.com/oijusti/react-native-firebaseui-auth#contributing

[![npm version](https://badge.fury.io/js/react-native-firebaseui-auth.svg)](https://www.npmjs.com/package/react-native-firebaseui-auth) [![PR's welcome][prs]][prs-url]

<a href="https://npmcharts.com/compare/react-native-firebaseui-auth?minimal=true">
  <img  src="https://img.shields.io/npm/dt/react-native-firebaseui-auth?color=46c71f"/> 
</a>

<a href="https://npmcharts.com/compare/react-native-firebaseui-auth?minimal=true">
  <img src="https://img.shields.io/npm/dm/react-native-firebaseui-auth.svg">
</a>

<a href="https://packagephobia.now.sh/result?p=react-native-firebaseui-auth">
  <img src="https://packagephobia.now.sh/badge?p=react-native-firebaseui-auth" alt="install size">
</a>


# react-native-firebaseui-auth

Easily add sign-in to your React Native app with FirebaseUI

<div align="left">
<p float="left">
<img src="https://raw.githubusercontent.com/oijusti/react-native-firebaseui-auth/HEAD/firebaseui-android.png" width="150"/>
<img src="https://raw.githubusercontent.com/oijusti/react-native-firebaseui-auth/HEAD/firebaseui-ios.png" width="150"/>
</p>
</div>

## Getting started

### Add Firebase to your  project

#### Android:

 1. Follow the [Android setup guide](https://firebase.google.com/docs/android/setup)
 2. Follow the [sign-in methods guide](https://firebase.google.com/docs/auth/android/firebaseui#set_up_sign-in_methods)    

#### iOS:
1. Follow the [iOS setup guide](https://firebase.google.com/docs/ios/setup)
    
2.  Add FirebaseUI to your Podfile:
    
    ```
    pod 'FirebaseUI'
    ```
    Run ` $ pod update`
3. Follow the [sign-in methods guide](https://firebase.google.com/docs/auth/ios/firebaseui#set_up_sign-in_methods)    

### Installation 

`$ npm install react-native-firebaseui-auth --save`

or 

`$ yarn add react-native-firebaseui-auth`


### Linking (RN >= 0.60 skip this step)

RN <= 0.59 only

#### Automatic

`$ react-native link react-native-firebaseui-auth`

#### Manual

#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-firebaseui-auth` and add `RNFirebaseuiAuth.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNFirebaseuiAuth.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)

#### Android

1. Open up `android/app/src/main/java/[...]/MainApplication.java`
  - Add `import com.oijusti.firebaseuiauth.RNFirebaseuiAuthPackage;` to the imports at the top of the file
  - Add `new RNFirebaseuiAuthPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
    ```
    include ':react-native-firebaseui-auth'
    project(':react-native-firebaseui-auth').projectDir = new File(rootProject.projectDir,  '../node_modules/react-native-firebaseui-auth/android')
    ```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
    ```
      implementation project(':react-native-firebaseui-auth')
    ```


## Usage

```javascript
import Auth, {AuthEventEmitter, AuthEvents} from 'react-native-firebaseui-auth';

...

  componentDidMount() {
    this.eventListener = AuthEventEmitter.addListener(
      AuthEvents.AUTH_STATE_CHANGED,
      event => {
        console.log('user:', event.user);
      }
    );
  }

  componentWillUnmount() {
    this.eventListener.remove(); //Removes the listener
  }

...

  const config = {
    providers: [
      'anonymous',
      'facebook', 
      'google', 
      'email', 
      'phone', 
      'apple', 
      'yahoo', 
      'github', 
      'twitter', 
      'microsoft'
    ],
    tosUrl: 'https://example.com/tos.htm',
    privacyPolicyUrl: 'https://example.com/privacypolicy.htm',
  };

  Auth.signIn(config)
    .then(user => console.log(user))
    .catch(err => console.log(err));

...

  Auth.getCurrentUser().then(user => console.log(user));

...

  Auth.signOut().then(res => console.log(res));

...

  Auth.deleteUser().then(res => console.log(res));

...
```

### Returns: `user`

Field | Type | Description |
--- | --- | --- |
uid |`string`| The provider's user ID for the user. |
displayName |`string`| The name of the user. |
photoURL |`string`| The URL of the user's profile photo. |
email |`string`| Indicates the email address associated with this user ~~has been verified~~. |
phoneNumber |`string`| A phone number associated with the user. |
providerId |`string`| The provider identifier. |
isNewUser |`boolean`| Indicates whether or not the current user was signed in for the first time. |
creationTimestamp |`number`| Stores the timestamp at which this account was created as dictated by the server clock in milliseconds since epoch. |
lastSignInTimestamp |`number`| Stores the last signin timestamp as dictated by the server clock in milliseconds since epoch. |

### UI Customization
Optionally, you can use the option `customizations` to change the look of the authentication screens. This does not apply to the actual sign-in buttons and their position. What you can change depends on the platform.

<div align="left">
<p float="left">
<img src="https://raw.githubusercontent.com/oijusti/react-native-firebaseui-auth/HEAD/firebaseui-android-custom.png" height="300"/>
<img src="https://raw.githubusercontent.com/oijusti/react-native-firebaseui-auth/HEAD/firebaseui-ios-custom.png" height="300"/>


##### Android
The values available for android customization are as follows,
```javascript
  const config = {
    ...
    customizations: [
      'theme',
      'logo'
    ],
  };
```
First add FirebaseUI in your build.gradle (:app),
```javascript
dependencies {
    implementation 'com.firebaseui:firebase-ui-auth:6.4.0'
    ...
```

For `theme`, add the next style in your `styles.xml`, then copy into the `drawable` folder an image to use for background and name it `auth_background.png`.
```javascript
    <style name="AuthTheme" parent="FirebaseUI">
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>

        <item name="colorControlNormal">@android:color/white</item>
        <item name="colorControlActivated">@android:color/white</item>
        <item name="colorControlHighlight">@android:color/white</item>
        <item name="android:windowBackground">@drawable/auth_background</item>
    </style>
```
For `logo`, copy an image in the `drawable` folder and name it `auth_logo.png`.


##### iOS
The values available for iOS customization correspond to specific screens and are as follows,
```javascript
  const config = {
    ...
    customizations: [
      'auth_picker',
      'email_entry',
      'password_sign_in',
      'password_sign_up',
      'password_recovery',
      'password_verification'
    ],
  };
```
Open your project in `XCode` and add the `.xib` file of the screen you want to customize. The .xib files are located in `./ios/custom-screens/` of this library. Let's say, you want to customize the `auth-picker` screen, add the file `FUICustomAuthPickerViewController.xib` and use the XCode tools to add it labels, images, change colors, and so on.

### Email Password Settings
You can control whether new users can sign in or not by using the option `allowNewEmailAccounts`. Also, if you do not want to require the user name during sign up you can set the option `requireDisplayName` to false.
```javascript
  const config = {
    ...
    allowNewEmailAccounts: false,
    requireDisplayName: false,
  };
```

## Example Project

Create a project in the [Firebase Console](https://console.firebase.google.com) and add apps for Android and iOS. Then enable Email/Password provider in Authentication.

#### Android
Make sure you type `com.example.app` in the `Android package name` field.

Download the file `google-services.json` in the android/app folder.

#### iOS
Make sure you type `com.example.app` in the `iOS bundle ID` field.

Download the file ``GoogleService-Info.plist`` in the ios/example folder and add it into the project using xcode.

Update pods `$ pod install`

## Contributing

Feel free to report bugs, ask questions and submit a PR.

If this is your first open source contribution, please take a look at this [guide](https://egghead.io/courses/how-to-contribute-to-an-open-source-project-on-github) .

## Find this library useful?

Please give me a star ✭ if you like it!

## License

(MIT)
