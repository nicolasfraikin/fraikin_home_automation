load("@rules_python//python:defs.bzl", "py_binary")

py_binary(
    name = "create_arduino_deployment_core",
    srcs = ["create_arduino_deployment_core.py"],
    data = ["//fraikin_home_automation/utils/code_generators/templates:arduino_deployment_core_template"],
    visibility = ["//visibility:public"],
    deps = ["@pip_deps_jinja2//:pkg"],
)
