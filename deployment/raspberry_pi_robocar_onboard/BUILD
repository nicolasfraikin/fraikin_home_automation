load("//fraikin_home_automation/modules/py_modules:modules.bzl", "MODULES")
load("//fraikin_home_automation/utils:helpers.bzl", "get_deployment_deps")
load("//fraikin_home_automation/utils/code_generators:create_raspi_deployment_core.bzl", "generate_raspi_deployment_core")

DEPLOYED_MODULES = [
    "serial_communication_test",
]

ADDITIONAL_DEPS = [
    "//fraikin_home_automation/communication/external_interface_communication/raspi:interface_sender",
    "//fraikin_home_automation/communication/external_interface_communication/raspi:interface_receiver",
]

generate_raspi_deployment_core(
    name = "raspi_core",
    deployed_modules = DEPLOYED_MODULES,
    file_name = "raspi_deployment.py",
)

py_binary(
    name = "raspi_deployment",
    srcs = [":raspi_core"],
    deps = get_deployment_deps(DEPLOYED_MODULES, MODULES, "py") + ADDITIONAL_DEPS,
)
