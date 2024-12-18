GENERATOR = "//fraikin_home_automation/utils/code_generators:create_interface_cpp"
TEMPLATE_PATH = "templates/interface_h.jinja"

def _create_interface_cpp_impl(ctx):
    args = ctx.actions.args()
    output_file_name = ctx.actions.declare_file(ctx.attr.interface_name + ".h")

    ctx.actions.run(
        inputs = [],
        outputs = [output_file_name],
        executable = ctx.executable._codegen_script,
        arguments = [output_file_name.path, ctx.attr.template_path, ctx.attr.interface_name, ctx.attr.interface_short_name, ctx.attr.interface_data_type],
    )
    return [DefaultInfo(files = depset([output_file_name]))]

def _generate_attrs():
    return {
        "template_path": attr.string(default = TEMPLATE_PATH),
        "interface_name": attr.string(),
        "interface_data_type": attr.string(),
        "interface_short_name": attr.string(
            default = "None",
        ),
        "_codegen_script": attr.label(
            default = Label(GENERATOR),  # Use the py_binary as the executable
            executable = True,
            cfg = "host",
        ),
    }

create_interface_cpp = rule(
    _create_interface_cpp_impl,
    attrs = _generate_attrs(),
    doc = "Builds the interface header file",
)
