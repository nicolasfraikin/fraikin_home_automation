import os
import sys
from jinja2 import Environment, FileSystemLoader


def pascal_case(s):
    return s.replace("_", " ").title().replace(" ", "")


def generate_cpp(output_file, template_path, deployed_modules, use_serial_communication, use_bluetooth_communication, use_funk_communication):
    deployed_modules = deployed_modules.split(",")
    template_path = os.path.join(os.path.dirname(os.path.abspath(__file__)), template_path)
    env = Environment(loader=FileSystemLoader(searchpath=os.path.dirname(template_path)))
    template = env.get_template(os.path.basename(template_path))

    # Render the template with the given parameters
    res = {}
    deployed_modules = [deployed_module for deployed_module in deployed_modules if len(deployed_module) > 0]
    res["ModuleNameSnakeCase"] = deployed_modules
    res["ModuleNamePascalCase"] = [pascal_case(module) for module in deployed_modules]
    res["ModuleNames"] = zip(res["ModuleNamePascalCase"], res["ModuleNameSnakeCase"])
    res["UseSerialCommunication"] = use_serial_communication
    res["UseBluetoothCommunication"] = use_bluetooth_communication
    res["UseFunkCommunication"] = use_funk_communication
    rendered_cpp = template.render(res)

    # Write the rendered content to the output file
    with open(output_file, 'w') as f:
        f.write(rendered_cpp)


if __name__ == "__main__":
    use_serial_communication = True if "True" in sys.argv[1] else False
    use_bluetooth_communication = True if "True" in sys.argv[2] else False
    use_funk_communication = True if "True" in sys.argv[3] else False
    output_file = sys.argv[4]
    template_path = sys.argv[5]
    deployed_modules = sys.argv[6]

    generate_cpp(output_file, template_path, deployed_modules,use_serial_communication, use_bluetooth_communication, use_funk_communication)
