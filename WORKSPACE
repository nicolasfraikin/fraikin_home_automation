workspace(name = "fraikin_home_automation")

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

############################################# TEMP
RULES_JVM_EXTERNAL_TAG = "6.4"

RULES_JVM_EXTERNAL_SHA = "85776be6d8fe64abf26f463a8e12cd4c15be927348397180a01693610da7ec90"

http_archive(
    name = "rules_jvm_external",
    sha256 = RULES_JVM_EXTERNAL_SHA,
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
    url = "https://github.com/bazel-contrib/rules_jvm_external/releases/download/%s/rules_jvm_external-%s.tar.gz" % (RULES_JVM_EXTERNAL_TAG, RULES_JVM_EXTERNAL_TAG),
)

load("@rules_jvm_external//:repositories.bzl", "rules_jvm_external_deps")

rules_jvm_external_deps()

load("@rules_jvm_external//:setup.bzl", "rules_jvm_external_setup")

rules_jvm_external_setup()

load("@rules_jvm_external//:defs.bzl", "maven_install")

# Define maven_install to include AndroidX libraries
maven_install(
    name = "maven",
    artifacts = [
        "androidx.fragment:fragment:1.2.0",  # Update version as needed
        "androidx.appcompat:appcompat:1.4.0",
        "androidx.annotation:annotation:1.8.2",
        "androidx.drawerlayout:drawerlayout:1.1.1",
        "com.google.android.material:material:1.2.0",
        "androidx.core:core:1.7.0",
        "com.squareup.okhttp3:okhttp:4.9.2",
        "org.json:json:20210307",
        "androidx.lifecycle:lifecycle-runtime:2.3.1",
        "androidx.lifecycle:lifecycle-viewmodel:2.3.1",
        "androidx.lifecycle:lifecycle-livedata:2.3.1",
        "androidx.lifecycle:lifecycle-livedata-core:2.3.1",
        "androidx.work:work-runtime:2.7.1",
        "com.squareup.okio:okio:3.2.0",
    ],
    repositories = [
        "https://maven.google.com",
        "https://repo1.maven.org/maven2",
    ],
)

############################## PYTHON

http_archive(
    name = "rules_python",
    sha256 = "ca77768989a7f311186a29747e3e95c936a41dffac779aff6b443db22290d913",
    strip_prefix = "rules_python-0.36.0",
    url = "https://github.com/bazelbuild/rules_python/releases/download/0.36.0/rules_python-0.36.0.tar.gz",
)

load("@rules_python//python:repositories.bzl", "py_repositories")

py_repositories()

load("@rules_python//python:pip.bzl", "pip_parse")

pip_parse(
    name = "pip_deps",
    python_interpreter = "/usr/bin/python3",
    requirements_lock = "//:requirements.txt",
)

# Load the starlark macro, which will define your dependencies.
load("@pip_deps//:requirements.bzl", "install_deps")

# Call it to define repos for your requirements.
install_deps()

############################## Platform IO Library
load("@bazel_tools//tools/build_defs/repo:git.bzl", "git_repository")

git_repository(
    name = "platformio_rules",
    remote = "http://github.com/mum4k/platformio_rules.git",
    tag = "v0.0.14",
)

load("@platformio_rules//bazel:deps.bzl", "platformio_rules_dependencies")

platformio_rules_dependencies()

load("@platformio_rules//bazel:transitive.bzl", "platformio_rules_transitive_dependencies")

platformio_rules_transitive_dependencies()

load("@platformio_rules//bazel:pip_parse.bzl", "platformio_rules_pip_parse_dependencies")

platformio_rules_pip_parse_dependencies()

load("@platformio_rules//bazel:pip_install.bzl", "platformio_rules_pip_install_dependencies")

platformio_rules_pip_install_dependencies()

############################## Android development
# Load Android SDK
android_sdk_repository(
    name = "androidsdk",
    #    api_level = 30,  # Set the appropriate API level
    #    build_tools_version = "30.0.3",
    path = "/home/nico/Android/Sdk",  # Change this to your SDK location
)

# Load Android NDK
android_ndk_repository(
    name = "androidndk",
    api_level = 22,  # Change to the desired NDK API level
    path = "/home/nico/Android/Sdk/ndk/28.0.12433566",  # Change this to your NDK location
)
