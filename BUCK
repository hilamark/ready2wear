android_resource(
  name = 'res',
  res = 'res',
  package = 'com.example.hellofacebook',
  deps = [
    '//facebook:android-sdk',
  ],
)

android_library(
  name = 'lib',
  srcs = glob(['src/**/*.java']),
  deps = [
    ':res',
    '//facebook:android-sdk',
    '//libs:android-support-v4',
  ],
)

android_binary(
  name = 'app',
  manifest = 'AndroidManifest.xml',
  keystore = '//keystores:debug',
  package_type = 'debug',
  deps = [
    ':lib',
    '//facebook:android-sdk',
    '//libs:android-support-v4',
  ],
)

project_config(
  src_target = ':app',
  src_roots = ['src'],
)
