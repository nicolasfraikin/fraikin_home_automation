import json
import os
import sys
from jinja2 import Environment, FileSystemLoader

DATA_TYPE_FOLDER = "communication/data_types"

CPP_TO_JAVA_TYPE = {
    "uint8_t": "int",
    "bool": "boolean",
    "float": "double",
}

CPP_TO_JAVA_CONVERSION_TYPE = {
    "uint8_t": "Integer",
    "bool": "Boolean",
    "float": "Double",
}

CPP_TO_JAVA_DEFAULT_VALUE = {
}


def pascal_case(s):
    return s.replace("_", " ").title().replace(" ", "")


def convert_cpp_type_to_java_type(cpp_type):
    return CPP_TO_JAVA_TYPE[cpp_type] if cpp_type in CPP_TO_JAVA_TYPE.keys() else cpp_type


def convert_cpp_type_to_conversion_type(cpp_type):
    return CPP_TO_JAVA_CONVERSION_TYPE[cpp_type] if cpp_type in CPP_TO_JAVA_CONVERSION_TYPE.keys() else cpp_type


def convert_cpp_to_java_default_value(cpp_default_value):
    return (CPP_TO_JAVA_DEFAULT_VALUE[
                cpp_default_value] if cpp_default_value in CPP_TO_JAVA_DEFAULT_VALUE.keys() else cpp_default_value).replace(
        "::", ".")


def write_file(output_file, template_name, res):
    template_path = os.path.join(os.path.dirname(os.path.abspath(__file__)), template_name)
    env = Environment(loader=FileSystemLoader(searchpath=os.path.dirname(template_path)))
    template = env.get_template(os.path.basename(template_path))
    rendered_file = template.render(res)
    # Write the rendered content to the output file
    with open(output_file, 'w') as f:
        f.write(rendered_file)


def create_app_interface(output_file, template_path, interface_name, interface_data_type):
    data_type_json = interface_data_type + ".json"
    json_path = os.path.join(os.path.dirname(os.path.dirname(os.path.dirname(os.path.abspath(__file__)))),
                             DATA_TYPE_FOLDER, data_type_json)
    with open(json_path, "r") as file:
        data_type_description = json.load(file)
    file.close()

    data_type_definition = data_type_description["DataTypeDefinition"]
    # Render the template with the given parameters
    res = {}
    res["InterfaceNamePascalCase"] = pascal_case(interface_name)
    res["DataTypeType"] = data_type_definition["DataTypeType"]
    res["DataTypeName"] = data_type_definition["DataTypeName"]
    res["DataTypeNameUpperCase"] = data_type_definition["DataTypeName"].upper()
    res["DataTypeMembers"] = zip(
        [convert_cpp_type_to_java_type(member["Type"]) for member in data_type_definition["DataTypeMembers"]],
        [member["VariableName"] for member in data_type_definition["DataTypeMembers"]],
        [convert_cpp_to_java_default_value(member["DefaultValue"]) for member in
         data_type_definition["DataTypeMembers"]])
    res["DataTypeMembers2"] = zip(
        [convert_cpp_type_to_java_type(member["Type"]) for member in data_type_definition["DataTypeMembers"]],
        [member["VariableName"] + (
            "," if member["VariableName"] != data_type_definition["DataTypeMembers"][-1]["VariableName"] else "") for
         member in
         data_type_definition["DataTypeMembers"]],
        [convert_cpp_to_java_default_value(member["DefaultValue"]) for member in
         data_type_definition["DataTypeMembers"]])
    res["DataTypeMembers3"] = zip(
        [convert_cpp_type_to_java_type(member["Type"]) for member in data_type_definition["DataTypeMembers"]],
        [member["VariableName"] for member in data_type_definition["DataTypeMembers"]],
        [convert_cpp_to_java_default_value(member["DefaultValue"]) for member in
         data_type_definition["DataTypeMembers"]])
    res["DataTypeMembers4"] = zip(
        [convert_cpp_type_to_java_type(member["Type"]) for member in data_type_definition["DataTypeMembers"]],
        [member["VariableName"] for member in data_type_definition["DataTypeMembers"]],
        [convert_cpp_to_java_default_value(member["DefaultValue"]) for member in
         data_type_definition["DataTypeMembers"]])
    res["DataTypeMembers5"] = zip(
        [convert_cpp_type_to_conversion_type(member["Type"]) for member in data_type_definition["DataTypeMembers"]],
        [member["VariableName"] for member in data_type_definition["DataTypeMembers"]],
        [i for i in range(0, len(
            data_type_definition["DataTypeMembers"]))])
    res["DataTypeMembers6"] = zip(
        [convert_cpp_type_to_java_type(member["Type"]) for member in data_type_definition["DataTypeMembers"]],
        [member["VariableName"] + (
            "," if member["VariableName"] != data_type_definition["DataTypeMembers"][-1]["VariableName"] else "") for
         member in
         data_type_definition["DataTypeMembers"]],
        [convert_cpp_to_java_default_value(member["DefaultValue"]) for member in
         data_type_definition["DataTypeMembers"]])
    res["DataTypeMembers7"] = zip(
        [convert_cpp_type_to_java_type(member["Type"]) for member in data_type_definition["DataTypeMembers"]],
        [member["VariableName"] + (
            "," if member["VariableName"] != data_type_definition["DataTypeMembers"][-1]["VariableName"] else "") for
         member in
         data_type_definition["DataTypeMembers"]],
        [convert_cpp_to_java_default_value(member["DefaultValue"]) for member in
         data_type_definition["DataTypeMembers"]])
    res["EnumDefinitions"] = zip([member["EnumName"] for member in data_type_definition["EnumDefinitions"]],
                                 [member["EnumFields"] for member in data_type_definition["EnumDefinitions"]])

    write_file(output_file, template_path, res)


if __name__ == "__main__":
    output_file = sys.argv[1]
    template_path = sys.argv[2]
    interface_name = sys.argv[3]
    interface_data_type = sys.argv[4]

    create_app_interface(output_file, template_path, interface_name, interface_data_type)
