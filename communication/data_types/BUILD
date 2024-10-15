load(
    "@platformio_rules//platformio:platformio.bzl",
    "platformio_library",
)
load("//fraikin_home_automation/communication/interfaces:interface_definitions.bzl", "INTERFACE_DEFINITIONS")
load("//fraikin_home_automation/utils:helpers.bzl", "get_all_data_type_names_cc_library", "get_all_data_type_names_py_library")
load("//fraikin_home_automation/utils/code_generators:create_data_type_cpp.bzl", "generate_data_type_cpp")

filegroup(
    name = "data_types",
    srcs = glob(["*.json"]),
    visibility = ["//visibility:public"],
)

DATA_TYPE_DEFINITIONS_CC_LIBRARY = get_all_data_type_names_cc_library(INTERFACE_DEFINITIONS)

[
    (
        generate_data_type_cpp(
            name = data_type_name + "_cpp_file",
            data_type_name = data_type_name,
        ),
        platformio_library(
            name = data_type_name,
            hdr = ":{}_cpp_file".format(data_type_name),
            visibility = ["//fraikin_home_automation/communication/interfaces:__subpackages__"],
            deps = [],
        ),
        cc_library(
            name = "{}_test".format(data_type_name),
            hdrs = [":{}_cpp_file".format(data_type_name)],
            visibility = ["//fraikin_home_automation/communication/interfaces:__subpackages__"],
            deps = [],
        ),
    )
    for data_type_name in DATA_TYPE_DEFINITIONS_CC_LIBRARY
]

DATA_TYPE_DEFINITIONS_PY_LIBRARY = get_all_data_type_names_py_library(INTERFACE_DEFINITIONS)

#[
#    (
#        py_library(
#            name = data_type_name,
#            srcs = [":{}_py_file".format(data_type_name)],
#            visibility = ["//fraikin_home_automation/communication/interfaces:__subpackages__"],
#            deps = [],
#        ),
#    )
#    for data_type_name in DATA_TYPE_DEFINITIONS_PY_LIBRARY
#]
