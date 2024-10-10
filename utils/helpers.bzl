def get_includes_from_library_deps(libray_deps):
    number_of_subfolders_from_current_dir = len(native.package_name().split("/")) - 1

    includes = []
    for dep in libray_deps:
        if "//fraikin_home_automation" not in dep and dep.count("/") == 0 and dep.count(":") == 1:
            includes.append(".")
        split_folders = dep.replace("//fraikin_home_automation", "").split("/")
        include = "../" * number_of_subfolders_from_current_dir
        include += "/".join(split_folders).split(":")[0]
        includes.append(include)
    return includes

def get_deployment_deps_for_arduino(deployed_module_names, all_modules):
    arduino_deps = []
    for deployed_module_name in deployed_module_names:
        deployed_library = all_modules[deployed_module_name]
        arduino_deps.append("//fraikin_home_automation/cpp_modules:" + deployed_module_name)
        print(deployed_library)
        for lib_dep in deployed_library["deps"]:
            if "test_stubs" not in lib_dep and lib_dep not in arduino_deps:
                arduino_deps.append(lib_dep)
    return arduino_deps
