import os
import sys
from jinja2 import Environment, FileSystemLoader


def pascal_case(s):
    return s.replace("_", " ").title().replace(" ", "")


def generate_py(output_file, template_path, deployed_modules, include_serial_communication):
    deployed_modules = deployed_modules.split(",")
    template_path = os.path.join(os.path.dirname(os.path.abspath(__file__)), template_path)
    env = Environment(loader=FileSystemLoader(searchpath=os.path.dirname(template_path)))
    template = env.get_template(os.path.basename(template_path))

    # Render the template with the given parameters
    res = {}
    res["ModuleNameSnakeCase"] = deployed_modules
    res["ModuleNamePascalCase"] = [pascal_case(module) for module in deployed_modules]
    res["ModuleNames"] = zip(res["ModuleNamePascalCase"], res["ModuleNameSnakeCase"])
    res["ModuleNames1"] = zip(res["ModuleNamePascalCase"], res["ModuleNameSnakeCase"])
    res["ModuleNames2"] = zip(res["ModuleNamePascalCase"], res["ModuleNameSnakeCase"])
    res["ModuleNames3"] = zip(res["ModuleNamePascalCase"], res["ModuleNameSnakeCase"])
    res["ModuleNames4"] = zip(res["ModuleNamePascalCase"], res["ModuleNameSnakeCase"])
    res["IncludeSerialCommunication"] = include_serial_communication
    rendered_py = template.render(res)

    # Write the rendered content to the output file
    with open(output_file, 'w') as f:
        f.write(rendered_py)


if __name__ == "__main__":
    output_file = sys.argv[1]
    template_path = sys.argv[2]
    deployed_modules = sys.argv[3]
    include_serial_communication = True if "True" in sys.argv[4] else False

    generate_py(output_file, template_path, deployed_modules, include_serial_communication)
