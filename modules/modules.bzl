MODULES = {
    "blink": {
        "hdrs": ["blink/blink.h"],
        "srcs": ["blink/blink.cpp"],
        "visibility": [
            "//fraikin_home_automation/deployment:__subpackages__",
        ],
        "deps": [
            "//fraikin_home_automation/common/base_classes:module_base",
            "//fraikin_home_automation/utils/test_stubs:Arduino_mock",
        ],
    },
}
