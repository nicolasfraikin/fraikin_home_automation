GENERATOR = "//fraikin_home_automation/utils/code_generators:create_arduino_interface_receiver"
TEMPLATE_PATH_H = "templates/arduino_interface_receiver_h.jinja"
TEMPLATE_PATH_CPP = "templates/arduino_interface_receiver_cpp.jinja"

def _create_arduino_interface_receiver_impl(ctx):
    # Define output files
    template_path = TEMPLATE_PATH_H if ".h" in ctx.attr.output_file else TEMPLATE_PATH_CPP
    output_file = ctx.actions.declare_file(ctx.attr.output_file)

    # Run the Python code generation script
    ctx.actions.run(
        inputs = [],
        outputs = [output_file],
        executable = ctx.executable._codegen_script,
        arguments = [output_file.path, template_path, ctx.attr.interface_list, ctx.attr.interface_data_type_list],
    )
    return [DefaultInfo(files = depset([output_file]))]

def _generate_attrs():
    return {
        "output_file": attr.string(),
        "interface_list": attr.string(),
        "interface_data_type_list": attr.string(),
        "_codegen_script": attr.label(
            default = Label(GENERATOR),  # Use the py_binary as the executable
            executable = True,
            cfg = "host",
        ),
    }

create_arduino_interface_receiver = rule(
    _create_arduino_interface_receiver_impl,
    attrs = _generate_attrs(),
    doc = "Builds the output communication for arduino",
)
