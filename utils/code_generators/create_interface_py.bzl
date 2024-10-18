GENERATOR = "//fraikin_home_automation/utils/code_generators:create_interface_py"
TEMPLATE_PATH = "templates/interface_py.jinja"

def _create_interface_py_impl(ctx):
    args = ctx.actions.args()
    output_file_name = ctx.actions.declare_file(ctx.attr.interface_name + ".py")

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

create_interface_py = rule(
    _create_interface_py_impl,
    attrs = _generate_attrs(),
    doc = "Builds the interface py file",
)
