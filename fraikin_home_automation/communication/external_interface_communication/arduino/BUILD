load(
    "@platformio_rules//platformio:platformio.bzl",
    "platformio_library",
)
load("//fraikin_home_automation/communication/interfaces:interface_definitions.bzl", "INTERFACE_DEFINITIONS")
load("//fraikin_home_automation/utils:helpers.bzl", "get_includes_from_library_deps")
load("//fraikin_home_automation/utils/code_generators:create_arduino_interface_bluetooth_receiver.bzl", "create_arduino_interface_bluetooth_receiver")
load("//fraikin_home_automation/utils/code_generators:create_arduino_interface_bluetooth_sender.bzl", "create_arduino_interface_bluetooth_sender")
load("//fraikin_home_automation/utils/code_generators:create_arduino_interface_funk_receiver.bzl", "create_arduino_interface_funk_receiver")
load("//fraikin_home_automation/utils/code_generators:create_arduino_interface_funk_sender.bzl", "create_arduino_interface_funk_sender")
load("//fraikin_home_automation/utils/code_generators:create_arduino_interface_receiver.bzl", "create_arduino_interface_receiver")
load("//fraikin_home_automation/utils/code_generators:create_arduino_interface_sender.bzl", "create_arduino_interface_sender")

# SERIAL COMMUNICATION
INTERFACE_LIST_SENDER = [interface["Name"] for interface in INTERFACE_DEFINITIONS if "ArduinoToRaspi" in interface["InterfaceConfig"]]

INTERFACE_LIST_RECEIVER = [interface["Name"] for interface in INTERFACE_DEFINITIONS if "RaspiToArduino" in interface["InterfaceConfig"]]

INTERFACE_DATA_TYPE_LIST_RECEIVER = [interface["DataTypeDefinition"] for interface in INTERFACE_DEFINITIONS if "RaspiToArduino" in interface["InterfaceConfig"]]

# BLUETOOUTH COMMUNICATION
INTERFACE_LIST_BLUETOOTH_SENDER = [interface["Name"] for interface in INTERFACE_DEFINITIONS if "ArduinoToApp" in interface["InterfaceConfig"]]

INTERFACE_LIST_BLUETOOTH_RECEIVER = [interface["Name"] for interface in INTERFACE_DEFINITIONS if "AppToArduino" in interface["InterfaceConfig"]]

INTERFACE_DATA_TYPE_LIST_BLUETOOTH_RECEIVER = [interface["DataTypeDefinition"] for interface in INTERFACE_DEFINITIONS if "AppToArduino" in interface["InterfaceConfig"]]

# FUNK COMMUNICATION
INTERFACE_LIST_FUNK_SENDER = [interface["Name"] for interface in INTERFACE_DEFINITIONS if "ArduinoToArduino" in interface["InterfaceConfig"]]

INTERFACE_LIST_FUNK_RECEIVER = [interface["Name"] for interface in INTERFACE_DEFINITIONS if "ArduinoToArduino" in interface["InterfaceConfig"]]

INTERFACE_DATA_TYPE_LIST_FUNK_RECEIVER = [interface["DataTypeDefinition"] for interface in INTERFACE_DEFINITIONS if "ArduinoToArduino" in interface["InterfaceConfig"]]

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
    {
        "Name": "interface_bluetooth_sender",
        "visibility": [
            "//fraikin_home_automation/deployment:__subpackages__",
        ],
        "deps": ["//fraikin_home_automation/communication/interfaces:{}".format(interface) for interface in INTERFACE_LIST_BLUETOOTH_SENDER],
    },
    {
        "Name": "interface_bluetooth_receiver",
        "visibility": [
            "//fraikin_home_automation/deployment:__subpackages__",
        ],
        "deps": ["//fraikin_home_automation/communication/interfaces:{}".format(interface) for interface in INTERFACE_LIST_BLUETOOTH_RECEIVER],
    },
    {
        "Name": "interface_funk_sender",
        "visibility": [
            "//fraikin_home_automation/deployment:__subpackages__",
        ],
        "deps": ["//fraikin_home_automation/communication/interfaces:{}".format(interface) for interface in INTERFACE_LIST_FUNK_SENDER],
    },
    {
        "Name": "interface_funk_receiver",
        "visibility": [
            "//fraikin_home_automation/deployment:__subpackages__",
        ],
        "deps": ["//fraikin_home_automation/communication/interfaces:{}".format(interface) for interface in INTERFACE_LIST_FUNK_RECEIVER],
    },
]

# Serial communication
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

# Bluetooth communication
INTERFACE_LIST_BLUETOOTH_SENDER_SHORT = [(interface["InterfaceShortName"] if "InterfaceShortName" in interface.keys() else interface["Name"]) for interface in INTERFACE_DEFINITIONS if "ArduinoToApp" in interface["InterfaceConfig"]]

INTERFACE_LIST_BLUETOOTH_RECEIVER_SHORT = [(interface["InterfaceShortName"] if "InterfaceShortName" in interface.keys() else interface["Name"]) for interface in INTERFACE_DEFINITIONS if "AppToArduino" in interface["InterfaceConfig"]]

create_arduino_interface_bluetooth_sender(
    name = "interface_bluetooth_sender_h",
    interface_list = ",".join(INTERFACE_LIST_BLUETOOTH_SENDER),
    interface_short_list = ",".join(INTERFACE_LIST_BLUETOOTH_SENDER_SHORT),
    output_file = "interface_bluetooth_sender.h",
)

create_arduino_interface_bluetooth_sender(
    name = "interface_bluetooth_sender_cpp",
    interface_list = ",".join(INTERFACE_LIST_BLUETOOTH_SENDER),
    interface_short_list = ",".join(INTERFACE_LIST_BLUETOOTH_SENDER),
    output_file = "interface_bluetooth_sender.cpp",
)

create_arduino_interface_bluetooth_receiver(
    name = "interface_bluetooth_receiver_h",
    interface_data_type_list = ",".join(INTERFACE_DATA_TYPE_LIST_BLUETOOTH_RECEIVER),
    interface_list = ",".join(INTERFACE_LIST_BLUETOOTH_RECEIVER),
    interface_short_list = ",".join(INTERFACE_LIST_BLUETOOTH_RECEIVER_SHORT),
    output_file = "interface_bluetooth_receiver.h",
)

create_arduino_interface_bluetooth_receiver(
    name = "interface_bluetooth_receiver_cpp",
    interface_data_type_list = ",".join(INTERFACE_DATA_TYPE_LIST_BLUETOOTH_RECEIVER),
    interface_list = ",".join(INTERFACE_LIST_BLUETOOTH_RECEIVER),
    interface_short_list = ",".join(INTERFACE_LIST_BLUETOOTH_RECEIVER_SHORT),
    output_file = "interface_bluetooth_receiver.cpp",
)

# Funk communication
INTERFACE_LIST_FUNK_SENDER_SHORT = [(interface["InterfaceShortName"] if "InterfaceShortName" in interface.keys() else interface["Name"]) for interface in INTERFACE_DEFINITIONS if "ArduinoToArduino" in interface["InterfaceConfig"]]

INTERFACE_LIST_FUNK_RECEIVER_SHORT = [(interface["InterfaceShortName"] if "InterfaceShortName" in interface.keys() else interface["Name"]) for interface in INTERFACE_DEFINITIONS if "ArduinoToArduino" in interface["InterfaceConfig"]]

create_arduino_interface_funk_sender(
    name = "interface_funk_sender_h",
    interface_list = ",".join(INTERFACE_LIST_FUNK_SENDER),
    interface_short_list = ",".join(INTERFACE_LIST_FUNK_SENDER_SHORT),
    output_file = "interface_funk_sender.h",
)

create_arduino_interface_funk_sender(
    name = "interface_funk_sender_cpp",
    interface_list = ",".join(INTERFACE_LIST_FUNK_SENDER),
    interface_short_list = ",".join(INTERFACE_LIST_FUNK_SENDER),
    output_file = "interface_funk_sender.cpp",
)

create_arduino_interface_funk_receiver(
    name = "interface_funk_receiver_h",
    interface_data_type_list = ",".join(INTERFACE_DATA_TYPE_LIST_FUNK_RECEIVER),
    interface_list = ",".join(INTERFACE_LIST_FUNK_RECEIVER),
    interface_short_list = ",".join(INTERFACE_LIST_FUNK_RECEIVER_SHORT),
    output_file = "interface_funk_receiver.h",
)

create_arduino_interface_funk_receiver(
    name = "interface_funk_receiver_cpp",
    interface_data_type_list = ",".join(INTERFACE_DATA_TYPE_LIST_FUNK_RECEIVER),
    interface_list = ",".join(INTERFACE_LIST_FUNK_RECEIVER),
    interface_short_list = ",".join(INTERFACE_LIST_FUNK_RECEIVER_SHORT),
    output_file = "interface_funk_receiver.cpp",
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
