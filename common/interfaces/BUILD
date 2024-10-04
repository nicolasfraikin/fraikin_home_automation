load(
    "@platformio_rules//platformio:platformio.bzl",
    "platformio_library",
)

platformio_library(
    name = "i_module",
    hdr = "i_module.h",
    visibility = [
        "//fraikin_home_automation/deployment:__subpackages__",
    ],
)

platformio_library(
    name = "module_base",
    src = "module_base.cpp",
    hdr = "module_base.h",
    visibility = [
        "//fraikin_home_automation/deployment:__subpackages__",
        "//fraikin_home_automation/modules:__subpackages__",
    ],
    deps = [":i_module"],
)
