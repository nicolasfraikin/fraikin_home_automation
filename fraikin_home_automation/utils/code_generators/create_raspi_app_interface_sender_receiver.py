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


def create_raspi_serial_interface_receiver(output_file, template_path, interface_list_receive, interface_list_send):
    # Render the template with the given parameters
    res = {}
    # Receive
    res["InterfaceNameSnakeCase"] = interface_list_receive
    res["InterfaceNamePascalCase1"] = [pascal_case(interface) for interface in interface_list_receive]
    res["InterfaceNamePascalCase2"] = res["InterfaceNamePascalCase1"]
    res["InterfaceNamePascalCase3"] = res["InterfaceNamePascalCase2"]
    res["InterfaceNameSnakePascalCase"] = zip(res["InterfaceNameSnakeCase"], res["InterfaceNamePascalCase1"])
    # Send
    res["InterfaceNameSnakeCaseSend"] = interface_list_send
    res["InterfaceNamePascalCaseSend"] = [pascal_case(interface) for interface in interface_list_send]
    res["InterfaceNameSnakePascalCaseSend"] = zip(res["InterfaceNameSnakeCaseSend"], res["InterfaceNamePascalCaseSend"])

    write_file(output_file, template_path, res)


if __name__ == "__main__":
    output_file = sys.argv[1]
    template_path = sys.argv[2]
    interface_list_receive = sys.argv[3].split(",")
    interface_list_receive = [item for item in interface_list_receive if len(item) > 0]
    interface_list_send = sys.argv[4].split(",")
    interface_list_send = [item for item in interface_list_send if len(item) > 0]

create_raspi_serial_interface_receiver(output_file, template_path, interface_list_receive, interface_list_send)
