#import "RNFirebaseuiAuth.h"

@interface RNFirebaseuiAuth ()
@property (nonatomic, retain) FUIAuth *authUI;
@property (nonatomic, retain) FIRAdditionalUserInfo *additionalUserInfo;
@property (nonatomic) RCTPromiseResolveBlock _resolve;
@property (nonatomic) RCTPromiseRejectBlock _reject;
@end

@implementation RNFirebaseuiAuth

/** @const ERROR_USER_CANCELLED
 @brief Indicates the user cancelled a sign-in flow.
 */
NSString* const ERROR_USER_CANCELLED = @"ERROR_USER_CANCELLED";

/** @const ERROR_FIREBASE
 @brief Indicates firebase encountered an error.
 */
NSString* const ERROR_FIREBASE = @"ERROR_FIREBASE";

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

+ (BOOL)requiresMainQueueSetup
{
    return YES;
}

- (instancetype)init {
    self = [super init];
    if (self) {
        self.authUI = [FUIAuth defaultAuthUI];
        self.authUI.delegate = self;
    }
    return self;
}

RCT_EXPORT_MODULE();

RCT_EXPORT_METHOD(signIn:(NSDictionary *)options
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{
    NSMutableArray<id<FUIAuthProvider>> *providers = [[NSMutableArray alloc] init];
    NSArray<NSString *> *optProviders = [options objectForKey:@"providers"];
    
    for (int i = 0; i < [optProviders count]; i++)
    {
        if ([optProviders[i] isEqualToString:@"anonymous"]) {
            [providers addObject:[[FUIAnonymousAuth alloc] init]];
        }
        else if ([optProviders[i] isEqualToString:@"facebook"]) {
            [providers addObject:[[FUIFacebookAuth alloc] init]];
        }
        else if ([optProviders[i] isEqualToString:@"google"]) {
            [providers addObject:[[FUIGoogleAuth alloc] init]];
        }
        else if ([optProviders[i] isEqualToString:@"email"]) {
            [providers addObject:[[FUIEmailAuth alloc] init]];
        }
        else if ([optProviders[i] isEqualToString:@"phone"]) {
            [providers addObject:[[FUIPhoneAuth alloc] initWithAuthUI:[FUIAuth defaultAuthUI]]];
        }
        else if ([optProviders[i] isEqualToString:@"apple"]) {
            if (@available(iOS 13.0, *)) {
                [providers addObject:[FUIOAuth appleAuthProvider]];
            }
        }
        else if ([optProviders[i] isEqualToString:@"yahoo"]) {
            [providers addObject:[FUIOAuth yahooAuthProvider]];
        }
        else if ([optProviders[i] isEqualToString:@"github"]) {
            [providers addObject:[FUIOAuth githubAuthProvider]];
        }
        else if ([optProviders[i] isEqualToString:@"twitter"]) {
            [providers addObject:[FUIOAuth twitterAuthProvider]];
        }
        else if ([optProviders[i] isEqualToString:@"microsoft"]) {
            [providers addObject:[FUIOAuth microsoftAuthProvider]];
        }
    }
    
    self.authUI.providers = providers;
    self.authUI.TOSURL = [NSURL URLWithString:options[@"tosUrl"]];
    self.authUI.privacyPolicyURL = [NSURL URLWithString:options[@"privacyPolicyUrl"]];
    
    UINavigationController *authViewController = [self.authUI authViewController];
    UIViewController *rootVC = UIApplication.sharedApplication.delegate.window.rootViewController;
    [rootVC presentViewController:authViewController animated:YES completion:nil];
    
    self._resolve = resolve;
    self._reject = reject;
}

RCT_EXPORT_METHOD(getCurrentUser:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{
    FIRUser *user = self.authUI.auth.currentUser;
    if (user) {
        NSDictionary *authResultDict = [self mapUser:user];
        resolve(authResultDict);
        return;
    }
    resolve([NSNull null]);
}

RCT_EXPORT_METHOD(signOut:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{
    NSError *error;
    [self.authUI signOutWithError:&error];
    if (error) {
        reject(ERROR_FIREBASE, @"Sign out error", error);
        return;
    }
    resolve(@YES);
}

RCT_EXPORT_METHOD(delete:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{
    FIRUser *user = self.authUI.auth.currentUser;
    [user deleteWithCompletion:^(NSError *_Nullable error) {
        if (error) {
            reject(ERROR_FIREBASE, @"Delete user error", error);
            return;
        }
        resolve(@YES);
    }];
}

- (void)authUI:(FUIAuth *)authUI
didSignInWithAuthDataResult:(nullable FIRAuthDataResult *)authDataResult
         error:(nullable NSError *)error{
    
    if (error) {
        if (error.code == FUIAuthErrorCodeUserCancelledSignIn) {
            self._reject(ERROR_USER_CANCELLED, @"User cancelled the sign-in process", error);
        } else {
            self._reject(ERROR_FIREBASE, error.localizedDescription, error);
        }
        return;
    }
    
    FIRUser *user = authDataResult.user;
    if (user) {
        self.additionalUserInfo = authDataResult.additionalUserInfo;
        NSDictionary *authResultDict = [self mapUser:user];
        self._resolve(authResultDict);
        return;
    }
}

- (NSDictionary*)mapUser:(nullable FIRUser*)user {
    return @{
        @"uid": user.uid ?: [NSNull null],
        @"displayName": user.displayName ?: [NSNull null],
        @"photoURL": user.photoURL ?: [NSNull null],
        @"email": user.email ?: [NSNull null],
        @"phoneNumber": user.phoneNumber ?: [NSNull null],
        @"providerID": user.providerID ?: [NSNull null],
        @"isNewUser": self.additionalUserInfo?
        @(self.additionalUserInfo.isNewUser) : [NSNull null],
        @"creationTimestamp": user.metadata ?
        @(user.metadata.creationDate.timeIntervalSince1970 * 1000) : [NSNull null],
        @"lastSignInTimestamp": user.metadata ?
        @(user.metadata.lastSignInDate.timeIntervalSince1970 * 1000) : [NSNull null],
    };
}

@end
