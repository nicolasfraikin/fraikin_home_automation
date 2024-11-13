GENERATOR = "//fraikin_home_automation/utils/code_generators:create_data_type_py"
TEMPLATE_PATH = "templates/data_type_py.jinja"

def _generate_data_type_py_impl(ctx):
    args = ctx.actions.args()
    output_file_name = ctx.actions.declare_file(ctx.attr.data_type_name + ".py")

    ctx.actions.run(
        inputs = [],
        outputs = [output_file_name],
        executable = ctx.executable._codegen_script,
        arguments = [output_file_name.path, ctx.attr.template_path, ctx.attr.data_type_name + ".json"],
    )
    return [DefaultInfo(files = depset([output_file_name]))]

def _generate_attrs():
    return {
        "template_path": attr.string(default = TEMPLATE_PATH),
        "data_type_name": attr.string(),
        "_codegen_script": attr.label(
            default = Label(GENERATOR),  # Use the py_binary as the executable
            executable = True,
            cfg = "host",
        ),
    }

generate_data_type_py = rule(
    _generate_data_type_py_impl,
    attrs = _generate_attrs(),
    doc = "Builds the data type class python file",
)
