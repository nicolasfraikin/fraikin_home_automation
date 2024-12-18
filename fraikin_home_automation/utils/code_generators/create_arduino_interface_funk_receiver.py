import json
import os
import sys
from jinja2 import Environment, FileSystemLoader

DATA_TYPE_FOLDER = "communication/data_types"


def pascal_case(s):
    return s.replace("_", " ").title().replace(" ", "")


def write_file(output_file, template_name, res):
    template_path = os.path.join(os.path.dirname(os.path.abspath(__file__)), template_name)
    env = Environment(loader=FileSystemLoader(searchpath=os.path.dirname(template_path)))
    template = env.get_template(os.path.basename(template_path))
    rendered_file = template.render(res)
    # Write the rendered content to the output file
    with open(output_file, 'w') as f:
        f.write(rendered_file)


def create_arduino_interface_funk_receiver(output_file, template_path, interface_list, interface_short_list, interface_data_type_list):
    interface_data_type_assignments = []
    for interface_data_type in interface_data_type_list:
        data_type_json = interface_data_type
        json_path = os.path.join(os.path.dirname(os.path.dirname(os.path.dirname(os.path.abspath(__file__)))),
                                 DATA_TYPE_FOLDER, data_type_json)
        with open(json_path, "r") as file:
            data_type_description = json.load(file)
        file.close()
        data_type_vars = data_type_description["DataTypeDefinition"]["DataTypeMembers"]
        assignment_statements = []
        for idx in range(0, len(data_type_vars)):
            assignment_statement = "data." + data_type_vars[idx]["VariableName"] + " = static_cast<" + \
                                   data_type_vars[idx]["Type"] + ">(atof(data_fields[" + str(idx) + "].c_str()))"
            assignment_statements.append(assignment_statement)
        interface_data_type_assignments.append(assignment_statements)

    # Render the template with the given parameters
    res = {}
    res["FileNameUpperCase"] = output_file.split("/")[-1].replace(".", "_").upper()
    res["FileNameLowerCase"] = output_file.split("/")[-1].replace(".cpp", "")
    res["InterfaceNamesSnakeCase"] = interface_list
    res["NumOfInterfaces"] = len(interface_list)
    res["InterfaceNamesPascalCase"] = zip([pascal_case(interface) for interface in interface_short_list], [pascal_case(interface) for interface in interface_list])
    interface_indizes = [idx for idx in range(0, len(interface_list))]
    res["InterfaceNamesAndIndex"] = zip(res["InterfaceNamesPascalCase"], interface_indizes)
    res["InterfaceNamesAndIndex1"] = res["InterfaceNamesAndIndex"]
    res["InterfaceNamesAndDataTypes"] = zip([pascal_case(interface) for interface in interface_list], interface_data_type_assignments)

    write_file(output_file, template_path, res)


if __name__ == "__main__":
    output_file = sys.argv[1]
    template_path = sys.argv[2]
    interface_list = sys.argv[3].split(",")
    interface_short_list = sys.argv[4].split(",")
    interface_data_type_list = sys.argv[5].split(",")
    interface_list = [item for item in interface_list if len(item) > 0]
    interface_short_list = [item for item in interface_short_list if len(item) > 0]
    print("##### " + str(interface_list))

    create_arduino_interface_funk_receiver(output_file, template_path, interface_list, interface_short_list,interface_data_type_list)
