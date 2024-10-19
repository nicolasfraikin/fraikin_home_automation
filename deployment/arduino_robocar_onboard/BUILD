load(
    "@platformio_rules//platformio:platformio.bzl",
    "platformio_library",
    "platformio_project",
)
load("//fraikin_home_automation/modules/cpp_modules:modules.bzl", "MODULES")
load("//fraikin_home_automation/utils:helpers.bzl", "get_deployment_deps")
load("//fraikin_home_automation/utils/code_generators:create_arduino_deployment_core.bzl", "generate_arduino_deployment_core")

DEPLOYED_MODULES = [
    "blink",
    "blink2",
]

ADDITIONAL_DEPS = [
    "//fraikin_home_automation/common/cpp_base_classes:i_module",
    "//fraikin_home_automation/communication/external_interface_communication/arduino:interface_sender",
    "//fraikin_home_automation/communication/external_interface_communication/arduino:interface_receiver",
]

generate_arduino_deployment_core(
    name = "arduino_core",
    deployed_modules = DEPLOYED_MODULES,
    file_name = "arduino_core.cc",
)

platformio_project(
    name = "arduino_deployment",
    src = ":arduino_core",
    board = "uno",
    # If you need to you can pass multiple string key-value pairs directly
    # to PlatformIO. All of these will appear under the environment section in
    # the generated platformio.ini file. That would be "[env:megaatmega2560]" in
    # this example. For the available options see:
    # http://docs.platformio.org/en/stable/projectconf.html
    #    environment_kwargs = {
    #        "board_f_cpu": "16000000L",
    #    },
    framework = "arduino",
    platform = "atmelavr",
    deps = get_deployment_deps(DEPLOYED_MODULES, MODULES, "cpp") + ADDITIONAL_DEPS,
)
