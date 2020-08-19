export = RNFirebaseuiAuth;

declare namespace RNFirebaseuiAuth {
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
