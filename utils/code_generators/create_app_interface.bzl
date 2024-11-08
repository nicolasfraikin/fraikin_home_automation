GENERATOR = "//fraikin_home_automation/utils/code_generators:create_app_interface"
TEMPLATE_PATH = "templates/app_interface_java.jinja"

def _pascal_case(s):
    return s.replace("_", " ").title().replace(" ", "")

def _create_app_interface_impl(ctx):
    # Define output files
    output_file_name = ctx.actions.declare_file(_pascal_case(ctx.attr.interface_name) + ".java")

    # Run the Python code generation script
    ctx.actions.run(
        inputs = [],
        outputs = [output_file_name],
        executable = ctx.executable._codegen_script,
        arguments = [output_file_name.path, ctx.attr.template_path, ctx.attr.interface_name, ctx.attr.interface_data_type],
    )
    return [DefaultInfo(files = depset([output_file_name]))]

def _generate_attrs():
    return {
        "template_path": attr.string(default = TEMPLATE_PATH),
        "interface_name": attr.string(),
        "interface_data_type": attr.string(),
        "_codegen_script": attr.label(
            default = Label(GENERATOR),  # Use the py_binary as the executable
            executable = True,
            cfg = "host",
        ),
    }

create_app_interface = rule(
    _create_app_interface_impl,
    attrs = _generate_attrs(),
    doc = "Builds the output communication for app",
)
