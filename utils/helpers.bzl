INCLUDE_PATH_DATA_TYPES = "../..//communication/data_types"

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

    if native.package_name().endswith("modules/cpp_modules"):
        includes.append(INCLUDE_PATH_DATA_TYPES)
    return includes

def get_deployment_deps(deployed_module_names, all_modules, programming_language):
    arduino_deps = []
    for deployed_module_name in deployed_module_names:
        deployed_library = all_modules[deployed_module_name]
        arduino_deps.append("//fraikin_home_automation/modules/{}_modules:".format(programming_language) + deployed_module_name)
        for lib_dep in deployed_library["deps"]:
            if "test_stubs" not in lib_dep and lib_dep not in arduino_deps:
                arduino_deps.append(lib_dep)
    return arduino_deps

def config_qualifies_for_cc_library(interface_config):
    return "ArduinoOnly" in interface_config or "ArduinoAndRaspi" in interface_config

def config_qualifies_for_py_library(interface_config):
    return "ArduinoAndRaspi" in interface_config or "RaspiOnly" in interface_config or "AppAndRaspi" in interface_config

def get_all_data_type_names_cc_library(interface_list):
    data_type_names = []
    for interface in interface_list:
        data_type_name = interface["DataTypeDefinition"].replace(".json", "")
        if config_qualifies_for_cc_library(interface["InterfaceConfig"]) and data_type_name not in data_type_names:
            data_type_names.append(data_type_name)
    return data_type_names

def get_all_data_type_names_py_library(interface_list):
    data_type_names = []
    for interface in interface_list:
        data_type_name = interface["DataTypeDefinition"].replace(".json", "")
        if config_qualifies_for_py_library(interface["InterfaceConfig"]) and data_type_name not in data_type_names:
            data_type_names.append(data_type_name)
    return data_type_names
