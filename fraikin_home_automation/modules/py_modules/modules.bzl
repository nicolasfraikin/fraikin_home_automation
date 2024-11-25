MODULES = {
    "danbolig_house_watcher": {
        "srcs": ["danbolig_house_watcher/danbolig_house_watcher.py"],
        "visibility": [
            "//fraikin_home_automation/deployment:__subpackages__",
        ],
        "deps": [
            "//fraikin_home_automation/common/py_base_classes:i_module",
            "//fraikin_home_automation/communication/interfaces:test_interface_py",
            "//fraikin_home_automation/communication/interfaces:test_1_interface_py",
        ],
    },
    "electricity_price_watcher": {
        "srcs": ["electricity_price_watcher/electricity_price_watcher.py"],
        "visibility": [
            "//fraikin_home_automation/deployment:__subpackages__",
        ],
        "deps": [
            "//fraikin_home_automation/common/py_base_classes:i_module",
            "//fraikin_home_automation/communication/interfaces:electricity_prices_interface_py",
        ],
    },
    "smart_home_device_manager": {
        "srcs": ["smart_home_device_manager/smart_home_device_manager.py"],
        "visibility": [
            "//fraikin_home_automation/deployment:__subpackages__",
        ],
        "deps": [
            "//fraikin_home_automation/common/py_base_classes:i_module",
            "//fraikin_home_automation/communication/interfaces:requested_smart_home_runs_interface_py",
            "//fraikin_home_automation/communication/interfaces:scheduled_smart_home_runs_interface_py",
            "//fraikin_home_automation/modules/py_modules:libraries",
        ],
    },
    "vacuum_robot_manager": {
        "srcs": ["vacuum_robot_manager/vacuum_robot_manager.py"],
        "visibility": [
            "//fraikin_home_automation/deployment:__subpackages__",
        ],
        "deps": [
            "//fraikin_home_automation/common/py_base_classes:i_module",
            "//fraikin_home_automation/communication/interfaces:vacuum_robot_requests_interface_py",
            "//fraikin_home_automation/communication/interfaces:vacuum_robot_status_interface_py",
        ],
    },
    "christmas_light_manager": {
        "srcs": ["christmas_light_manager/christmas_light_manager.py"],
        "visibility": [
            "//fraikin_home_automation/deployment:__subpackages__",
        ],
        "deps": [
            "//fraikin_home_automation/common/py_base_classes:i_module",
            "//fraikin_home_automation/communication/interfaces:christmas_light_request_interface_py",
            "//fraikin_home_automation/communication/interfaces:christmas_light_status_interface_py",
            "//fraikin_home_automation/modules/py_modules:libraries",
        ],
    },
}
