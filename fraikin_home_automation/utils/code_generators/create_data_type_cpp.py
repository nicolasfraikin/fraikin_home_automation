import json
import os
import sys
from jinja2 import Environment, FileSystemLoader

DATA_TYPE_FOLDER = "communication/data_types"


def pascal_case(s):
    return s.replace("_", " ").title().replace(" ", "")


def generate_data_type_h(output_file, template_path, data_type_json):
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
    res["IncludeStatements"] = data_type_definition["IncludeStatementsCpp"]
    res["DataTypeType"] = data_type_definition["DataTypeType"]
    res["DataTypeName"] = data_type_definition["DataTypeName"]
    res["DataTypeNameUpperCase"] = data_type_definition["DataTypeName"].upper()
    res["DataTypeMembers"] = zip([member["Type"] for member in data_type_definition["DataTypeMembers"]],
                                 [member["VariableName"] for member in data_type_definition["DataTypeMembers"]],
                                 [member["DefaultValue"] for member in data_type_definition["DataTypeMembers"]])
    res["EnumDefinitions"] = zip([member["EnumName"] for member in data_type_definition["EnumDefinitions"]],
                                 [member["EnumFields"] for member in data_type_definition["EnumDefinitions"]])

    rendered_cpp = template.render(res)

    # Write the rendered content to the output file
    with open(output_file, 'w') as f:
        f.write(rendered_cpp)


if __name__ == "__main__":
    output_file = sys.argv[1]
    template_path = sys.argv[2]
    data_type_json = sys.argv[3]

    generate_data_type_h(output_file, template_path, data_type_json)
