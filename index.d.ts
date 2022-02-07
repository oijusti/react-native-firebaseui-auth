import { NativeEventEmitter } from "react-native";

export = RNFirebaseuiAuth;

declare namespace RNFirebaseuiAuth {
  type EventType =
    | "AuthStateChanged";

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
    allowNewEmailAccounts?: boolean;
    requireDisplayName?: boolean;
  }

  export function signIn(config: Config): Promise<User>;
  export function getCurrentUser(): Promise<User|null>;
  export function signOut(): Promise<boolean>;
  export function deleteUser(): Promise<boolean>;

  export const AuthEventEmitter: NativeEventEmitter;

  export const AuthEvents: {
    AUTH_STATE_CHANGED: EventType;
  };
}
