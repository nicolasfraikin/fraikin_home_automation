load("//fraikin_home_automation/modules/py_modules:modules.bzl", "MODULES")
load("//fraikin_home_automation/utils:helpers.bzl", "get_deployment_deps")
load("//fraikin_home_automation/utils/code_generators:create_raspi_deployment_core.bzl", "generate_raspi_deployment_core")

DEPLOYED_MODULES = [
    "danbolig_house_watcher",
]

ADDITIONAL_DEPS = [
    "//fraikin_home_automation/communication/external_interface_communication/raspi/with_app:interface_sender_receiver",
]

generate_raspi_deployment_core(
    name = "raspi_core",
    deployed_modules = DEPLOYED_MODULES,
    file_name = "raspi_deployment.py",
    include_serial_communication = False,
)

py_binary(
    name = "raspi_deployment",
    srcs = [":raspi_core"],
    deps = get_deployment_deps(DEPLOYED_MODULES, MODULES, "py") + ADDITIONAL_DEPS,
)

py_binary(
    name = "deploy_raspi_deployment",
    srcs = [
        "deploy_raspi_deployment.py",
        "replaceall_symlinks.py",
    ],
    deps = [":raspi_deployment"],
)
