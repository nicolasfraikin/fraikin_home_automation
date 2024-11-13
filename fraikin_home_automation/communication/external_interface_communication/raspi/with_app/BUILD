load("//fraikin_home_automation/communication/interfaces:interface_definitions.bzl", "INTERFACE_DEFINITIONS")
load("//fraikin_home_automation/utils/code_generators:create_raspi_app_interface_sender_receiver.bzl", "create_raspi_app_interface_sender_receiver")

INTERFACE_LIST_SENDER = [interface["Name"] for interface in INTERFACE_DEFINITIONS if "ToApp" in interface["InterfaceConfig"]]

INTERFACE_LIST_RECEIVER = [interface["Name"] for interface in INTERFACE_DEFINITIONS if "AppTo" in interface["InterfaceConfig"]]

create_raspi_app_interface_sender_receiver(
    name = "interface_sender_receiver_py",
    interface_list_receive = ",".join(INTERFACE_LIST_RECEIVER),
    interface_list_send = ",".join(INTERFACE_LIST_SENDER),
    output_file = "app_interface_sender_receiver.py",
)

py_library(
    name = "interface_sender_receiver",
    srcs = [":interface_sender_receiver_py"],
    visibility = ["//fraikin_home_automation/deployment:__subpackages__"],
    deps = ["//fraikin_home_automation/communication/interfaces:{}_py".format(interface) for interface in INTERFACE_LIST_SENDER] + ["//fraikin_home_automation/communication/interfaces:{}_py".format(interface) for interface in INTERFACE_LIST_RECEIVER] + ["@pip_deps_websockets//:pkg"],
)
