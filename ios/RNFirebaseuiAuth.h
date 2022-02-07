
#if __has_include("RCTBridgeModule.h")
#import "RCTBridgeModule.h"
#else
#import <React/RCTBridgeModule.h>
#endif

#import <React/RCTEventEmitter.h>

#import <FirebaseUI/FirebaseUI.h>
#import <FirebaseAuthUI/FirebaseAuthUI.h>
#import <FirebaseGoogleAuthUI/FirebaseGoogleAuthUI.h>
#import <FirebaseFacebookAuthUI/FirebaseFacebookAuthUI.h>
#import <FirebaseEmailAuthUI/FirebaseEmailAuthUI.h>
#import <FirebaseOAuthUI/FirebaseOAuthUI.h>
#import <FirebasePhoneAuthUI/FirebasePhoneAuthUI.h>
#import <FirebaseAnonymousAuthUI/FirebaseAnonymousAuthUI.h>

#import "FUICustomAuthPickerViewController.h"
#import "FUICustomEmailEntryViewController.h"
#import "FUICustomPasswordSignInViewController.h"
#import "FUICustomPasswordSignUpViewController.h"
#import "FUICustomPasswordRecoveryViewController.h"
#import "FUICustomPasswordVerificationViewController.h"

@interface RNFirebaseuiAuth : RCTEventEmitter <RCTBridgeModule, FUIAuthDelegate>

@end
