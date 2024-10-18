import json
import os
import sys
from jinja2 import Environment, FileSystemLoader

DATA_TYPE_FOLDER = "communication/data_types"

CPP_TO_PY_TYPE = {
    "uint8_t": "int"
}

CPP_TO_PY_DEFAULT_VALUE = {
    "false": "False",
    "true": "True",
}


def pascal_case(s):
    return s.replace("_", " ").title().replace(" ", "")


def convert_cpp_type_to_py_type(cpp_type):
    return CPP_TO_PY_TYPE[cpp_type] if cpp_type in CPP_TO_PY_TYPE.keys() else cpp_type


def convert_cpp_type_to_py_default_value(cpp_default_value):
    return (CPP_TO_PY_DEFAULT_VALUE[
                cpp_default_value] if cpp_default_value in CPP_TO_PY_DEFAULT_VALUE.keys() else cpp_default_value).replace(
        "::", ".")


def generate_data_type_py(output_file, template_path, data_type_json):
    template_path = os.path.join(os.path.dirname(os.path.abspath(__file__)), template_path)
    env = Environment(loader=FileSystemLoader(searchpath=os.path.dirname(template_path)))
    template = env.get_template(os.path.basename(template_path))

    json_path = os.path.join(os.path.dirname(os.path.dirname(os.path.dirname(os.path.abspath(__file__)))),
                             DATA_TYPE_FOLDER, data_type_json)
    with open(json_path, "r") as file:
        data_type_description = json.load(file)
    file.close()

    data_type_definition = data_type_description["DataTypeDefinition"]
    # Render the template with the given parameters
    res = {}
    res["IncludeStatements"] = data_type_definition["ImportStatementsPy"]
    res["DataTypeName"] = data_type_definition["DataTypeName"]
    res["DataTypeMembers"] = zip(
        [convert_cpp_type_to_py_type(member["Type"]) for member in data_type_definition["DataTypeMembers"]],
        [member["VariableName"] for member in data_type_definition["DataTypeMembers"]],
        [convert_cpp_type_to_py_default_value(member["DefaultValue"]) for member in
         data_type_definition["DataTypeMembers"]],
        ["," for _ in range(0, len(data_type_definition["DataTypeMembers"]) - 1)] + [""])
    res["DataTypeMembers2"] = zip(
        [convert_cpp_type_to_py_type(member["Type"]) for member in data_type_definition["DataTypeMembers"]],
        [member["VariableName"] for member in data_type_definition["DataTypeMembers"]],
        [convert_cpp_type_to_py_default_value(member["DefaultValue"]) for member in
         data_type_definition["DataTypeMembers"]])

    for enum_def_idx in range(0, len(data_type_definition["EnumDefinitions"])):
        enum_fields = data_type_definition["EnumDefinitions"][enum_def_idx]["EnumFields"]
        indizes = range(0, len(enum_fields))
        data_type_definition["EnumDefinitions"][enum_def_idx]["EnumFields"] = [enum_field + " = " + str(index) for
                                                                               enum_field, index in
                                                                               zip(enum_fields, indizes)]

    res["EnumDefinitions"] = zip([member["EnumName"] for member in data_type_definition["EnumDefinitions"]],
                                 [member["EnumFields"] for member in data_type_definition["EnumDefinitions"]])

    rendered_py = template.render(res)

    # Write the rendered content to the output file
    with open(output_file, 'w') as f:
        f.write(rendered_py)


if __name__ == "__main__":
    output_file = sys.argv[1]
    template_path = sys.argv[2]
    data_type_json = sys.argv[3]

    generate_data_type_py(output_file, template_path, data_type_json)
