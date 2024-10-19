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


def create_arduino_interface_sender(output_file, template_path, interface_list):
    # Render the template with the given parameters
    res = {}
    res["FileNameUpperCase"] = output_file.split("/")[-1].replace(".", "_").upper()
    res["FileNameLowerCase"] = output_file.split("/")[-1].replace(".cpp", "")
    res["InterfaceNameSnakeCase"] = interface_list
    res["InterfaceNamePascalCase"] = [pascal_case(interface) for interface in interface_list]

    write_file(output_file, template_path, res)


if __name__ == "__main__":
    output_file = sys.argv[1]
    template_path = sys.argv[2]
    interface_list = sys.argv[3].split(",")
    interface_list = [item for item in interface_list if len(item) > 0]
    print("##### " + str(interface_list))

    create_arduino_interface_sender(output_file, template_path, interface_list)
