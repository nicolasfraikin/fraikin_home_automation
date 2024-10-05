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
            "//fraikin_home_automation/modules:__subpackages__",
        ],
        "deps": [
            ":i_module",
            ":i_interface",
        ],
    },
    {
        "Name": "i_interface",
        "hdrs": ["i_interface.h"],
        "srcs": [],
        "visibility": [
            "//fraikin_home_automation/deployment:__subpackages__",
            "//fraikin_home_automation/modules:__subpackages__",
        ],
        "deps": [],
    },
]

[
    (
        platformio_library(
            name = "{}_arduino".format(library["Name"]),
            src = library["srcs"][0] if len(library["srcs"]) > 0 else None,
            add_hdrs = library["hdrs"][1:] if len(library["hdrs"]) > 1 else [],
            add_srcs = library["hdrs"][1:] if len(library["srcs"]) > 1 else [],
            hdr = library["hdrs"][0],
            visibility = library["visibility"],
            deps = [library_dep + "_arduino" for library_dep in library["deps"]],
        ),
        cc_library(
            name = library["Name"],
            srcs = library["srcs"],
            hdrs = library["hdrs"],
            defines = ["NON_TARGET_BUILD"],
            includes = get_includes_from_library_deps(
                library["deps"],
            ),
            visibility = library["visibility"],
            deps = library["deps"],
        ),
    )
    for library in LIBRARIES
]
