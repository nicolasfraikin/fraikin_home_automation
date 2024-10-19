load(
    "@platformio_rules//platformio:platformio.bzl",
    "platformio_library",
)
load("//fraikin_home_automation/communication/interfaces:interface_definitions.bzl", "INTERFACE_DEFINITIONS")
load("//fraikin_home_automation/utils:helpers.bzl", "config_qualifies_for_cc_library", "config_qualifies_for_py_library", "get_includes_from_library_deps")
load("//fraikin_home_automation/utils/code_generators:create_interface_cpp.bzl", "create_interface_cpp")
load("//fraikin_home_automation/utils/code_generators:create_interface_py.bzl", "create_interface_py")

[
    (
        create_interface_cpp(
            name = interface_definition["Name"] + "_h_file",
            interface_data_type = interface_definition["DataTypeDefinition"].replace(".json", ""),
            interface_name = interface_definition["Name"],
        ),
        platformio_library(
            name = interface_definition["Name"],
            hdr = ":{}_h_file".format(interface_definition["Name"]),
            visibility = [
                "//fraikin_home_automation/communication:__subpackages__",
                "//fraikin_home_automation/deployment:__subpackages__",
                "//fraikin_home_automation/modules/cpp_modules:__subpackages__",
            ],
            deps = ["//fraikin_home_automation/communication/data_types:{}".format(interface_definition["DataTypeDefinition"].replace(".json", ""))],
        ),
        cc_library(
            name = "{}_test".format(interface_definition["Name"]),
            hdrs = [":{}_h_file".format(interface_definition["Name"])],
            includes = get_includes_from_library_deps(
                ["//fraikin_home_automation/communication/data_types:{}_test".format(interface_definition["DataTypeDefinition"].replace(".json", ""))],
            ),
            visibility = ["//fraikin_home_automation/modules/cpp_modules:__subpackages__"],
            deps = ["//fraikin_home_automation/communication/data_types:{}_test".format(interface_definition["DataTypeDefinition"].replace(".json", ""))],
        ),
    )
    for interface_definition in INTERFACE_DEFINITIONS
    if config_qualifies_for_cc_library(interface_definition["InterfaceConfig"])
]

[
    (
        create_interface_py(
            name = interface_definition["Name"] + "_py_file",
            interface_data_type = interface_definition["DataTypeDefinition"].replace(".json", ""),
            interface_name = interface_definition["Name"],
        ),
        py_library(
            name = "{}_py".format(interface_definition["Name"]),
            srcs = [":{}_py_file".format(interface_definition["Name"])],
            visibility = [
                "//fraikin_home_automation/communication:__subpackages__",
                "//fraikin_home_automation/deployment:__subpackages__",
                "//fraikin_home_automation/modules/py_modules:__subpackages__",
            ],
            deps = ["//fraikin_home_automation/communication/data_types:{}_py".format(interface_definition["DataTypeDefinition"].replace(".json", ""))] + ["//fraikin_home_automation/common/py_base_classes:interface_base"],
        ),
    )
    for interface_definition in INTERFACE_DEFINITIONS
    if config_qualifies_for_py_library(interface_definition["InterfaceConfig"])
]
