load(
    "@platformio_rules//platformio:platformio.bzl",
    "platformio_library",
)
load("//fraikin_home_automation/utils:helpers.bzl", "get_includes_from_library_deps")

LIBRARIES = [
    {
        "Name": "logging",
        "hdrs": ["logging.h"],
        "srcs": [],
        "visibility": [
            "//fraikin_home_automation:__subpackages__",
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
