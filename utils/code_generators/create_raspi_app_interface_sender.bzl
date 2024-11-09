GENERATOR = "//fraikin_home_automation/utils/code_generators:create_raspi_app_interface_sender"
TEMPLATE_PATH = "templates/raspi_app_interface_sender_py.jinja"

def _create_raspi_app_interface_sender_impl(ctx):
    # Define output files
    output_file = ctx.actions.declare_file(ctx.attr.output_file)

    # Run the Python code generation script
    ctx.actions.run(
        inputs = [],
        outputs = [output_file],
        executable = ctx.executable._codegen_script,
        arguments = [output_file.path, ctx.attr.template_path, ctx.attr.interface_list],
    )
    return [DefaultInfo(files = depset([output_file]))]

def _generate_attrs():
    return {
        "template_path": attr.string(default = TEMPLATE_PATH),
        "output_file": attr.string(),
        "interface_list": attr.string(),
        "_codegen_script": attr.label(
            default = Label(GENERATOR),  # Use the py_binary as the executable
            executable = True,
            cfg = "host",
        ),
    }

create_raspi_app_interface_sender = rule(
    _create_raspi_app_interface_sender_impl,
    attrs = _generate_attrs(),
    doc = "Builds the output communication for raspi",
)
