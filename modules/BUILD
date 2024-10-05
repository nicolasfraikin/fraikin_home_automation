load(
    "@platformio_rules//platformio:platformio.bzl",
    "platformio_library",
)
load("//fraikin_home_automation/modules:modules.bzl", "MODULES")
load("//fraikin_home_automation/utils:helpers.bzl", "get_includes_from_library_deps")

[
    (
        platformio_library(
            name = "{}_arduino".format(module_name),
            src = module["srcs"][0] if len(module["srcs"]) > 0 else None,
            add_hdrs = module["hdrs"][1:] if len(module["hdrs"]) > 1 else [],
            add_srcs = module["hdrs"][1:] if len(module["srcs"]) > 1 else [],
            hdr = module["hdrs"][0],
            visibility = module["visibility"],
            deps = [(module_dep + "_arduino") for module_dep in module["deps"] if "test_stubs" not in module_dep],
        ),
        cc_library(
            name = module_name,
            srcs = module["srcs"],
            hdrs = module["hdrs"],
            defines = ["NON_TARGET_BUILD"],
            includes = get_includes_from_library_deps(
                module["deps"],
            ),
            visibility = module["visibility"],
            deps = module["deps"],
        ),
    )
    for module_name, module in MODULES.items()
]
