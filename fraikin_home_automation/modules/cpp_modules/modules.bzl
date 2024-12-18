MODULES = {
    "blink": {
        "hdrs": ["blink/blink.h"],
        "srcs": ["blink/blink.cpp"],
        "visibility": [
            "//fraikin_home_automation/deployment:__subpackages__",
        ],
        "deps": [
            "//fraikin_home_automation/common/cpp_base_classes:module_base",
            "//fraikin_home_automation/utils/test_stubs:Arduino_mock",
            "//fraikin_home_automation/communication/interfaces:test_interface",
        ],
    },
    "blink2": {
        "hdrs": ["blink2/blink2.h"],
        "srcs": ["blink2/blink2.cpp"],
        "visibility": [
            "//fraikin_home_automation/deployment:__subpackages__",
        ],
        "deps": [
            "//fraikin_home_automation/common/cpp_base_classes:module_base",
            "//fraikin_home_automation/utils/test_stubs:Arduino_mock",
            "//fraikin_home_automation/communication/interfaces:test_interface",
        ],
    },
    "flight_control_signal_forwarder": {
        "hdrs": ["flight_control_signal_forwarder/flight_control_signal_forwarder.h"],
        "srcs": ["flight_control_signal_forwarder/flight_control_signal_forwarder.cpp"],
        "visibility": [
            "//fraikin_home_automation/deployment:__subpackages__",
        ],
        "deps": [
            "//fraikin_home_automation/common/cpp_base_classes:module_base",
            "//fraikin_home_automation/utils/test_stubs:Arduino_mock",
        ],
    },
    "flight_speed_control": {
        "hdrs": ["flight_speed_control/flight_speed_control.h"],
        "srcs": ["flight_speed_control/flight_speed_control.cpp"],
        "visibility": [
            "//fraikin_home_automation/deployment:__subpackages__",
        ],
        "deps": [
            "//fraikin_home_automation/common/cpp_base_classes:module_base",
            "//fraikin_home_automation/utils/test_stubs:Arduino_mock",
        ],
    },
    "flight_altitude_control": {
        "hdrs": ["flight_altitude_control/flight_altitude_control.h"],
        "srcs": ["flight_altitude_control/flight_altitude_control.cpp"],
        "visibility": [
            "//fraikin_home_automation/deployment:__subpackages__",
        ],
        "deps": [
            "//fraikin_home_automation/common/cpp_base_classes:module_base",
            "//fraikin_home_automation/utils/test_stubs:Arduino_mock",
        ],
    },
}
