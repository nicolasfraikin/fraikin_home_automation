load(
    "@platformio_rules//platformio:platformio.bzl",
    "platformio_library",
)
load("//fraikin_home_automation/utils:helpers.bzl", "get_includes_from_library_deps")

LIBRARIES = [
    {
        "Name": "i_module",
        "hdrs": ["i_module.h"],
        "srcs": [],
        "visibility": [
            "//fraikin_home_automation/deployment:__subpackages__",
        ],
        "deps": [],
    },
    {
        "Name": "module_base",
        "hdrs": ["module_base.h"],
        "srcs": ["module_base.cpp"],
        "visibility": [
            "//fraikin_home_automation/deployment:__subpackages__",
            "//fraikin_home_automation/modules/cpp_modules:__subpackages__",
        ],
        "deps": [
            ":i_module",
            ":interface_base",
            "//fraikin_home_automation/common/logging:logging",
        ],
    },
    {
        "Name": "interface_base",
        "hdrs": ["interface_base.h"],
        "srcs": [],
        "visibility": [
            "//fraikin_home_automation/deployment:__subpackages__",
            "//fraikin_home_automation/communication:__subpackages__",
        ],
        "deps": [],
    },
]

[
    (
        platformio_library(
            name = library["Name"],
            src = library["srcs"][0] if len(library["srcs"]) > 0 else None,
            add_hdrs = library["hdrs"][1:] if len(library["hdrs"]) > 1 else [],
            add_srcs = library["hdrs"][1:] if len(library["srcs"]) > 1 else [],
            hdr = library["hdrs"][0],
            visibility = library["visibility"],
            deps = library["deps"],
        ),
        cc_library(
            name = "{}_test".format(library["Name"]),
            srcs = library["srcs"],
            hdrs = library["hdrs"],
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
