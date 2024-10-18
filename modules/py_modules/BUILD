load("//fraikin_home_automation/modules/py_modules:modules.bzl", "MODULES")

[
    (
        py_library(
            name = "{}".format(module_name),
            srcs = module["srcs"],
            visibility = module["visibility"],
            deps = module["deps"],
        ),
    )
    for module_name, module in MODULES.items()
]
