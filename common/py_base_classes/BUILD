load("@rules_python//python:defs.bzl", "py_binary", "py_library")

py_library(
    name = "interface_base",
    srcs = ["interface_base.py"],
    visibility = [
        "//fraikin_home_automation/communication:__subpackages__",
        "//fraikin_home_automation/deployment:__subpackages__",
    ],
    deps = [],
)

py_library(
    name = "i_module",
    srcs = ["i_module.py"],
    visibility = [
        "//fraikin_home_automation/deployment:__subpackages__",
        "//fraikin_home_automation/modules/py_modules:__subpackages__",
    ],
    deps = [],
)
