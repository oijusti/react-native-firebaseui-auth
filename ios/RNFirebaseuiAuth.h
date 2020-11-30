
#if __has_include("RCTBridgeModule.h")
#import "RCTBridgeModule.h"
#else
#import <React/RCTBridgeModule.h>
#endif

#import <FirebaseUI/FirebaseAuthUI.h>
#import <FirebaseUI/FirebaseGoogleAuthUI.h>
#import <FirebaseUI/FirebaseFacebookAuthUI.h>
#import <FirebaseUI/FirebaseEmailAuthUI.h>
#import <FirebaseUI/FirebaseOAuthUI.h>
#import <FirebaseUI/FirebasePhoneAuthUI.h>
#import <FirebaseUI/FirebaseAnonymousAuthUI.h>

@interface RNFirebaseuiAuth : NSObject <RCTBridgeModule, FUIAuthDelegate>

@end
  
