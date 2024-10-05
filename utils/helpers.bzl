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
