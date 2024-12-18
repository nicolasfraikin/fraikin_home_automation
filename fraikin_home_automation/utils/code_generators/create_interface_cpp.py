import json
import os
import sys
from jinja2 import Environment, FileSystemLoader

DATA_TYPE_FOLDER = "communication/data_types"


def pascal_case(s):
    return s.replace("_", " ").title().replace(" ", "")


def create_interface_h(output_file, template_path, interface_name, interface_short_name, interface_data_type):
    template_path = os.path.join(os.path.dirname(os.path.abspath(__file__)), template_path)
    env = Environment(loader=FileSystemLoader(searchpath=os.path.dirname(template_path)))
    template = env.get_template(os.path.basename(template_path))

    data_type_json = interface_data_type + ".json"
    json_path = os.path.join(os.path.dirname(os.path.dirname(os.path.dirname(os.path.abspath(__file__)))),
                             DATA_TYPE_FOLDER, data_type_json)
    with open(json_path, "r") as file:
        data_type_description = json.load(file)
    file.close()

    # Render the template with the given parameters
    res = {}
    res["DataTypeVariables"] = [member["VariableName"] for member in
                                data_type_description["DataTypeDefinition"]["DataTypeMembers"]]
    res["InterfaceNameUpperCase"] = interface_name.upper()
    res["InterfaceNamePascalCase"] = pascal_case(interface_name)
    res["InterfaceShortNamePascalCase"] = interface_short_name
    res["ShortNameDefined"] = "None" not in interface_short_name
    res["DataTypeNamePascalCase"] = pascal_case(interface_data_type)
    res["IncludeStatementDataType"] = interface_data_type + ".h"

    rendered_cpp = template.render(res)

    # Write the rendered content to the output file
    with open(output_file, 'w') as f:
        f.write(rendered_cpp)


if __name__ == "__main__":
    output_file = sys.argv[1]
    template_path = sys.argv[2]
    interface_name = sys.argv[3]
    interface_short_name = sys.argv[4]
    interface_data_type = sys.argv[5]

    create_interface_h(output_file, template_path, interface_name, interface_short_name, interface_data_type)
