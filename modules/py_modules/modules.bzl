MODULES = {
    "serial_communication_test": {
        "srcs": ["serial_communication_test/serial_communication_test.py"],
        "visibility": [
            "//fraikin_home_automation/deployment:__subpackages__",
        ],
        "deps": [
            "//fraikin_home_automation/common/py_base_classes:i_module",
            "//fraikin_home_automation/communication/interfaces:test_interface_py",
            "//fraikin_home_automation/communication/interfaces:test_1_interface_py",
        ],
    },
}
