export = RNFirebaseuiAuth;

declare namespace RNFirebaseuiAuth {
  // TODO: Enum Alternative
  // export enum Providers {
  //   Facebook = 'facebook',
  //   Google = 'google',
  //   Email = 'email',
  //   Phone = 'phone',
  //   Apple = 'apple',
  //   Yahoo = 'yahoo',
  //   Github = 'github',
  //   Twitter = 'twitter',
  //   Microsoft = 'microsoft',
  // }

  type User = {
    uid: string;
    displayName: string;
    photoURL: string;
    email: string;
    phoneNumber: string;
    providerId: string;
    isNewUser: boolean;
    creationTimestamp: number;
    lastSignInTimestamp: number;
  };

  interface Config {
    providers: string[];
    tosUrl?: string;
    privacyPolicyUrl?: string;
  }

  export function signIn(config: Config): Promise<User>;
  export function signOut(): Promise<{}>;
  export function getCurrentUser(): Promise<User>;
}
