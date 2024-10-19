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
]
