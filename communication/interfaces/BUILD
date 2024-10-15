load(
    "@platformio_rules//platformio:platformio.bzl",
    "platformio_library",
)
load("//fraikin_home_automation/communication/interfaces:interface_definitions.bzl", "INTERFACE_DEFINITIONS")
load("//fraikin_home_automation/utils:helpers.bzl", "get_includes_from_library_deps")
load("//fraikin_home_automation/utils/code_generators:create_interface_cpp.bzl", "create_interface_cpp")

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
]
