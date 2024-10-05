load(
    "@platformio_rules//platformio:platformio.bzl",
    "platformio_library",
    "platformio_project",
)

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
    deps = [
        "//fraikin_home_automation/common/base_classes:i_module_arduino",
        "//fraikin_home_automation/common/base_classes:module_base_arduino",
        "//fraikin_home_automation/modules/blink:blink_arduino",
    ],
)
