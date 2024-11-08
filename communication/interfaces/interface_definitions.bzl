INTERFACE_CONFIGS = ["ArduinoOnly", "ArduinoToRaspi", "RaspiToArduino", "RaspiOnly", "AppToRaspi", "RaspiToApp"]

INTERFACE_DEFINITIONS = [
    {
        "Name": "test_interface",
        "DataTypeDefinition": "test_interface_data.json",
        "InterfaceConfig": "ArduinoToRaspi",
    },
    {
        "Name": "test_1_interface",
        "DataTypeDefinition": "test_interface_data.json",
        "InterfaceConfig": "RaspiToArduino",
    },
    {
        "Name": "test_2_interface",
        "DataTypeDefinition": "test_interface_data.json",
        "InterfaceConfig": "AppToRaspi",
    },
    {
        "Name": "test_3_interface",
        "DataTypeDefinition": "test_interface_data.json",
        "InterfaceConfig": "RaspiToApp",
    },
]
