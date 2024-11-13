load("@rules_python//python:defs.bzl", "py_binary")

py_binary(
    name = "create_arduino_deployment_core",
    srcs = ["create_arduino_deployment_core.py"],
    data = ["//fraikin_home_automation/utils/code_generators/templates:arduino_deployment_core_template"],
    visibility = ["//visibility:public"],
    deps = ["@pip_deps_jinja2//:pkg"],
)

py_binary(
    name = "create_raspi_deployment_core",
    srcs = ["create_raspi_deployment_core.py"],
    data = ["//fraikin_home_automation/utils/code_generators/templates:raspi_deployment_core_template"],
    visibility = ["//visibility:public"],
    deps = ["@pip_deps_jinja2//:pkg"],
)

py_binary(
    name = "create_data_type_cpp",
    srcs = ["create_data_type_cpp.py"],
    data = [
        "//fraikin_home_automation/communication/data_types",
        "//fraikin_home_automation/utils/code_generators/templates:data_type_h_template",
    ],
    visibility = ["//visibility:public"],
    deps = ["@pip_deps_jinja2//:pkg"],
)

py_binary(
    name = "create_data_type_py",
    srcs = ["create_data_type_py.py"],
    data = [
        "//fraikin_home_automation/communication/data_types",
        "//fraikin_home_automation/utils/code_generators/templates:data_type_py_template",
    ],
    visibility = ["//visibility:public"],
    deps = ["@pip_deps_jinja2//:pkg"],
)

py_binary(
    name = "create_interface_cpp",
    srcs = ["create_interface_cpp.py"],
    data = [
        "//fraikin_home_automation/communication/data_types",
        "//fraikin_home_automation/utils/code_generators/templates:interface_h_template",
    ],
    visibility = ["//visibility:public"],
    deps = ["@pip_deps_jinja2//:pkg"],
)

py_binary(
    name = "create_interface_py",
    srcs = ["create_interface_py.py"],
    data = [
        "//fraikin_home_automation/communication/data_types",
        "//fraikin_home_automation/utils/code_generators/templates:interface_py_template",
    ],
    visibility = ["//visibility:public"],
    deps = ["@pip_deps_jinja2//:pkg"],
)

py_binary(
    name = "create_arduino_interface_sender",
    srcs = ["create_arduino_interface_sender.py"],
    data = [
        "//fraikin_home_automation/utils/code_generators/templates:arduino_interface_sender",
    ],
    visibility = ["//visibility:public"],
    deps = ["@pip_deps_jinja2//:pkg"],
)

py_binary(
    name = "create_arduino_interface_receiver",
    srcs = ["create_arduino_interface_receiver.py"],
    data = [
        "//fraikin_home_automation/communication/data_types",
        "//fraikin_home_automation/utils/code_generators/templates:arduino_interface_receiver",
    ],
    visibility = ["//visibility:public"],
    deps = ["@pip_deps_jinja2//:pkg"],
)

py_binary(
    name = "create_raspi_serial_interface_sender",
    srcs = ["create_raspi_serial_interface_sender.py"],
    data = [
        "//fraikin_home_automation/communication/data_types",
        "//fraikin_home_automation/utils/code_generators/templates:raspi_serial_interface_sender",
    ],
    visibility = ["//visibility:public"],
    deps = ["@pip_deps_jinja2//:pkg"],
)

py_binary(
    name = "create_raspi_serial_interface_receiver",
    srcs = ["create_raspi_serial_interface_receiver.py"],
    data = [
        "//fraikin_home_automation/communication/data_types",
        "//fraikin_home_automation/utils/code_generators/templates:raspi_serial_interface_receiver",
    ],
    visibility = ["//visibility:public"],
    deps = ["@pip_deps_jinja2//:pkg"],
)

py_binary(
    name = "create_raspi_app_interface_sender",
    srcs = ["create_raspi_app_interface_sender.py"],
    data = [
        "//fraikin_home_automation/communication/data_types",
        "//fraikin_home_automation/utils/code_generators/templates:raspi_app_interface_sender",
    ],
    visibility = ["//visibility:public"],
    deps = ["@pip_deps_jinja2//:pkg"],
)

py_binary(
    name = "create_raspi_app_interface_sender_receiver",
    srcs = ["create_raspi_app_interface_sender_receiver.py"],
    data = [
        "//fraikin_home_automation/communication/data_types",
        "//fraikin_home_automation/utils/code_generators/templates:raspi_app_interface_sender_receiver",
    ],
    visibility = ["//visibility:public"],
    deps = ["@pip_deps_jinja2//:pkg"],
)

py_binary(
    name = "create_app_interface",
    srcs = ["create_app_interface.py"],
    data = [
        "//fraikin_home_automation/communication/data_types",
        "//fraikin_home_automation/utils/code_generators/templates:app_interface",
    ],
    visibility = ["//visibility:public"],
    deps = ["@pip_deps_jinja2//:pkg"],
)
