load(
    "@platformio_rules//platformio:platformio.bzl",
    "platformio_library",
)
#cc_library(
#    name = "blink",
#    srcs = ["blink.cpp"],
#    hdrs = ["blink.h"],
#    deps = [
#        "//fraikin_home_automation/common/interfaces:module_base",
#        "//fraikin_home_automation/common/utils:arduino",
#    ],
#)

platformio_library(
    name = "blink",
    src = "blink.cpp",
    hdr = "blink.h",
    visibility = ["//fraikin_home_automation/deployment:__subpackages__"],
    deps = [
        "//fraikin_home_automation/common/interfaces:module_base",
    ],
)
