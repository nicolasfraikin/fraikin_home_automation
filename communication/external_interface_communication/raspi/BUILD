load("//fraikin_home_automation/communication/interfaces:interface_definitions.bzl", "INTERFACE_DEFINITIONS")
load("//fraikin_home_automation/utils/code_generators:create_raspi_interface_receiver.bzl", "create_raspi_interface_receiver")
load("//fraikin_home_automation/utils/code_generators:create_raspi_interface_sender.bzl", "create_raspi_interface_sender")

INTERFACE_LIST_SENDER = [interface["Name"] for interface in INTERFACE_DEFINITIONS if "RaspiTo" in interface["InterfaceConfig"]]

INTERFACE_LIST_RECEIVER = [interface["Name"] for interface in INTERFACE_DEFINITIONS if "ToRaspi" in interface["InterfaceConfig"]]

LIBRARIES = [
    {
        "Name": "interface_sender",
        "visibility": [
            "//fraikin_home_automation/deployment:__subpackages__",
        ],
        "deps": ["//fraikin_home_automation/communication/interfaces:{}_py".format(interface) for interface in INTERFACE_LIST_SENDER],
    },
    {
        "Name": "interface_receiver",
        "visibility": [
            "//fraikin_home_automation/deployment:__subpackages__",
        ],
        "deps": ["//fraikin_home_automation/communication/interfaces:{}_py".format(interface) for interface in INTERFACE_LIST_RECEIVER],
    },
]

create_raspi_interface_sender(
    name = "interface_sender_py",
    interface_list = ",".join(INTERFACE_LIST_SENDER),
    output_file = "interface_sender.py",
)

create_raspi_interface_receiver(
    name = "interface_receiver_py",
    interface_list = ",".join(INTERFACE_LIST_RECEIVER),
    output_file = "interface_receiver.py",
)

[
    (
        py_library(
            name = "{}".format(library["Name"]),
            srcs = [":{}_py".format(library["Name"])],
            visibility = library["visibility"],
            deps = library["deps"],
        ),
    )
    for library in LIBRARIES
]
