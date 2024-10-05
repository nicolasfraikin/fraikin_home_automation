load(
    "@platformio_rules//platformio:platformio.bzl",
    "platformio_library",
    "platformio_project",
)
load("//fraikin_home_automation/modules:modules.bzl", "MODULES")
load("//fraikin_home_automation/utils:helpers.bzl", "get_deployment_deps_for_arduino")

DEPLOYED_MODULES = ["blink"]

ADDITIONAL_DEPS = [
    "//fraikin_home_automation/common/base_classes:i_module_arduino",
]

platformio_project(
    name = "arduino_deployment",
    src = "execution_master.cpp",
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
    deps = get_deployment_deps_for_arduino(DEPLOYED_MODULES, MODULES) + ADDITIONAL_DEPS,
)
