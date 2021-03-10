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
    customizations?: string[];
    tosUrl?: string;
    privacyPolicyUrl?: string;
  }

  export function signIn(config: Config): Promise<User>;
  export function getCurrentUser(): Promise<User|null>;
  export function signOut(): Promise<boolean>;
  export function delete(): Promise<boolean>;
}
