import { NativeEventEmitter, NativeModules } from 'react-native';

const { RNFirebaseuiAuth } = NativeModules;

export const AuthEventEmitter = new NativeEventEmitter(NativeModules.RNFirebaseuiAuth);
export const AuthEvents = {
  AUTH_STATE_CHANGED: 'AuthStateChanged'
}

export default RNFirebaseuiAuth;
