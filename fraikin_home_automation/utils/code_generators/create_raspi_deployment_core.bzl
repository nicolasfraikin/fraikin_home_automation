GENERATOR = "//fraikin_home_automation/utils/code_generators:create_raspi_deployment_core"
TEMPLATE_PATH = "templates/raspi_deployment_core_py.jinja"

def _generate_raspi_deployment_core_impl(ctx):
    args = ctx.actions.args()
    output_file = ctx.actions.declare_file(ctx.attr.file_name)

    deployed_modules_sep = ",".join(ctx.attr.deployed_modules)
    ctx.actions.run(
        inputs = [],
        outputs = [output_file],
        executable = ctx.executable._codegen_script,
        arguments = [output_file.path, ctx.attr.template_path, ",".join(ctx.attr.deployed_modules), str(ctx.attr.include_serial_communication)],
    )
    return [DefaultInfo(files = depset([output_file]))]

def _generate_attrs():
    return {
        "deployed_modules": attr.string_list(
            doc = "List of deployed modules",
        ),
        "file_name": attr.string(
            mandatory = True,
        ),
        "include_serial_communication": attr.bool(
            default = True,
        ),
        "template_path": attr.string(default = TEMPLATE_PATH),
        "_codegen_script": attr.label(
            default = Label(GENERATOR),  # Use the py_binary as the executable
            executable = True,
            cfg = "host",
        ),
    }

generate_raspi_deployment_core = rule(
    _generate_raspi_deployment_core_impl,
    attrs = _generate_attrs(),
    doc = "Builds the deployment core for raspi",
)
