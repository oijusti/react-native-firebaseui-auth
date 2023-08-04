require 'json'

package = JSON.parse(File.read(File.join(__dir__, 'package.json')))
version = package['version']

Pod::Spec.new do |s|
  s.name                   = 'react-native-firebaseui-auth'
  s.version                = version
  s.summary                = 'Easy login with FirebaseUi Auth'
  s.homepage               = 'https://github.com/oijusti/react-native-firebaseui-auth'
  s.license                = package['license']
  s.author                 = 'Oscar Justi <oijustisoft@gmail.com>'
  s.platforms              = { :ios => '9.0' }
  s.source                 = { :git => 'https://github.com/oijusti/react-native-firebaseui-auth.git', :tag => "v#{version}" }
  s.source_files           = 'ios/*.{h,m}'
  s.dependency 'React'
  s.dependency 'FirebaseDatabaseUI'
  s.dependency 'FirebaseAuthUI'
  s.dependency 'FirebaseFirestoreUI'
  s.dependency 'FirebaseOAuthUI'
  s.dependency 'FirebasePhoneAuthUI'
  s.dependency 'FirebaseAnonymousAuthUI'
  s.dependency 'FirebaseEmailAuthUI'
  s.dependency 'FirebaseFacebookAuthUI'
  s.dependency 'FirebaseGoogleAuthUI'
  s.dependency 'FirebaseStorageUI'
end
