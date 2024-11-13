load(
    "@platformio_rules//platformio:platformio.bzl",
    "platformio_library",
)
load("//fraikin_home_automation/communication/interfaces:interface_definitions.bzl", "INTERFACE_DEFINITIONS")
load("//fraikin_home_automation/utils:helpers.bzl", "get_includes_from_library_deps")
load("//fraikin_home_automation/utils/code_generators:create_arduino_interface_receiver.bzl", "create_arduino_interface_receiver")
load("//fraikin_home_automation/utils/code_generators:create_arduino_interface_sender.bzl", "create_arduino_interface_sender")

INTERFACE_LIST_SENDER = [interface["Name"] for interface in INTERFACE_DEFINITIONS if "ArduinoTo" in interface["InterfaceConfig"]]

INTERFACE_LIST_RECEIVER = [interface["Name"] for interface in INTERFACE_DEFINITIONS if "ToArduino" in interface["InterfaceConfig"]]

INTERFACE_DATA_TYPE_LIST_RECEIVER = [interface["DataTypeDefinition"] for interface in INTERFACE_DEFINITIONS if "ToArduino" in interface["InterfaceConfig"]]

LIBRARIES = [
    {
        "Name": "interface_sender",
        "visibility": [
            "//fraikin_home_automation/deployment:__subpackages__",
        ],
        "deps": ["//fraikin_home_automation/communication/interfaces:{}".format(interface) for interface in INTERFACE_LIST_SENDER],
    },
    {
        "Name": "interface_receiver",
        "visibility": [
            "//fraikin_home_automation/deployment:__subpackages__",
        ],
        "deps": ["//fraikin_home_automation/communication/interfaces:{}".format(interface) for interface in INTERFACE_LIST_RECEIVER],
    },
]

create_arduino_interface_sender(
    name = "interface_sender_h",
    interface_list = ",".join(INTERFACE_LIST_SENDER),
    output_file = "interface_sender.h",
)

create_arduino_interface_sender(
    name = "interface_sender_cpp",
    interface_list = ",".join(INTERFACE_LIST_SENDER),
    output_file = "interface_sender.cpp",
)

create_arduino_interface_receiver(
    name = "interface_receiver_h",
    interface_data_type_list = ",".join(INTERFACE_DATA_TYPE_LIST_RECEIVER),
    interface_list = ",".join(INTERFACE_LIST_RECEIVER),
    output_file = "interface_receiver.h",
)

create_arduino_interface_receiver(
    name = "interface_receiver_cpp",
    interface_data_type_list = ",".join(INTERFACE_DATA_TYPE_LIST_RECEIVER),
    interface_list = ",".join(INTERFACE_LIST_RECEIVER),
    output_file = "interface_receiver.cpp",
)

[
    (
        platformio_library(
            name = library["Name"],
            src = ":{}_cpp".format(library["Name"]),
            hdr = ":{}_h".format(library["Name"]),
            visibility = library["visibility"],
            deps = library["deps"],
        ),
        cc_library(
            name = "{}_test".format(library["Name"]),
            srcs = [":{}_cpp".format(library["Name"])],
            hdrs = [":{}_h".format(library["Name"])],
            defines = ["NON_TARGET_BUILD"],
            includes = get_includes_from_library_deps(
                library["deps"],
            ),
            visibility = library["visibility"],
            deps = [library_dep + "_test" for library_dep in library["deps"]],
        ),
    )
    for library in LIBRARIES
]
