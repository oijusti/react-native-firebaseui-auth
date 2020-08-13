[![oijusti](https://circleci.com/gh/oijusti/react-native-firebaseui-auth.svg?style=svg)](https://circleci.com/gh/oijusti/react-native-firebaseui-auth)

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
import firebaseui from 'react-native-firebaseui-auth';

...

  firebaseui.signIn({
    providers: [
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
  }).then(user => console.log(user));

...

  firebaseui.signOut().then(resp => console.log(resp));

...

  firebaseui.getCurrentUser().then(user => console.log(user));

...
```

### Returns: `user`

Field | Type |
 --- | --- |
 uid |`string`|
 displayName |`string`|
 photoURL |`string`|
 email |`string`|
 phoneNumber |`string`|
 providerId |`string`|
 isNewUser |`bool`|

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

## Sponsors

Become a sponsor to support the further development of this and other libraries.

<div align="left">
<a href="https://oiradio.co" target="_blank"><img src="https://oiradio.co/assets/images/stations/i/i0.png"></a>
</div>

## License

(MIT)
