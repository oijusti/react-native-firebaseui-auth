/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React from 'react';
import {
  SafeAreaView,
  StyleSheet,
  ScrollView,
  View,
  Text,
  StatusBar,
  Button,
} from 'react-native';

import {
  Header,
  LearnMoreLinks,
  Colors,
  DebugInstructions,
  ReloadInstructions,
} from 'react-native/Libraries/NewAppScreen';

import Auth from 'react-native-firebaseui-auth';

const App: () => React$Node = () => {
  return (
    <>
      <StatusBar barStyle="dark-content" />
      <SafeAreaView>
        <ScrollView
          contentInsetAdjustmentBehavior="automatic"
          style={styles.scrollView}>
          <Header />

          <View style={{alignItems: 'center'}}>
            <Text style={{fontWeight: 'bold', fontSize: 24}}>Firebase UI Auth Example</Text>
            <View style={{flexDirection: 'row', alignItems: 'center', flexWrap: 'wrap', justifyContent: 'center', padding: 5}}>
              <Button
                onPress={() => {
                  const config = {
                    providers: ['email', 'phone'],
                    customizations: ['auth_picker', 'theme', 'logo'],
                    tosUrl: 'https://example.com/tos.htm',
                    privacyPolicyUrl: 'https://example.com/privacypolicy.htm',
                  };
                  Auth.signIn(config)
                    .then(user => console.log(user))
                    .catch(err => console.log(err));
                }}
                title="SignIn"
              />
              <Text> / </Text>
              <Button
                onPress={() => {
                  Auth.getCurrentUser().then(user => console.log(user));
                }}
                title="CurrentUser"
              />
              <Text> / </Text>
              <Button
                onPress={() => {
                  Auth.signOut().then(res => console.log(res));
                }}
                title="SignOut"
              />
            </View>
            <View style={{flexDirection: 'row', alignItems: 'center', flexWrap: 'wrap', justifyContent: 'center', padding: 5}}>
              <Button
                onPress={() => {
                  Auth.delete().then(res => console.log(res));
                }}
                title="Delete"
              />
            </View>
            <Text>Check console log</Text>
          </View>

          {global.HermesInternal == null ? null : (
            <View style={styles.engine}>
              <Text style={styles.footer}>Engine: Hermes</Text>
            </View>
          )}
          <View style={styles.body}>
            <View style={styles.sectionContainer}>
              <Text style={styles.sectionTitle}>Step One</Text>
              <Text style={styles.sectionDescription}>
                Edit <Text style={styles.highlight}>App.js</Text> to change this
                screen and then come back to see your edits.
              </Text>
            </View>
            <View style={styles.sectionContainer}>
              <Text style={styles.sectionTitle}>See Your Changes</Text>
              <Text style={styles.sectionDescription}>
                <ReloadInstructions />
              </Text>
            </View>
            <View style={styles.sectionContainer}>
              <Text style={styles.sectionTitle}>Debug</Text>
              <Text style={styles.sectionDescription}>
                <DebugInstructions />
              </Text>
            </View>
            <View style={styles.sectionContainer}>
              <Text style={styles.sectionTitle}>Learn More</Text>
              <Text style={styles.sectionDescription}>
                Read the docs to discover what to do next:
              </Text>
            </View>
            <LearnMoreLinks />
          </View>
        </ScrollView>
      </SafeAreaView>
    </>
  );
};

const styles = StyleSheet.create({
  scrollView: {
    backgroundColor: Colors.lighter,
  },
  engine: {
    position: 'absolute',
    right: 0,
  },
  body: {
    backgroundColor: Colors.white,
  },
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
  sectionTitle: {
    fontSize: 24,
    fontWeight: '600',
    color: Colors.black,
  },
  sectionDescription: {
    marginTop: 8,
    fontSize: 18,
    fontWeight: '400',
    color: Colors.dark,
  },
  highlight: {
    fontWeight: '700',
  },
  footer: {
    color: Colors.dark,
    fontSize: 12,
    fontWeight: '600',
    padding: 4,
    paddingRight: 12,
    textAlign: 'right',
  },
});

export default App;
