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
    {
        "Name": "danbolig_house_interface",
        "DataTypeDefinition": "danbolig_new_house.json",
        "InterfaceConfig": "RaspiToApp",
    },
    {
        "Name": "electricity_prices_interface",
        "DataTypeDefinition": "electricity_prices.json",
        "InterfaceConfig": "RaspiToApp",
    },
    {
        "Name": "scheduled_smart_home_runs_interface",
        "DataTypeDefinition": "scheduled_smart_home_runs.json",
        "InterfaceConfig": "RaspiToApp",
    },
    {
        "Name": "requested_smart_home_runs_interface",
        "DataTypeDefinition": "requested_smart_home_runs.json",
        "InterfaceConfig": "AppToRaspi",
    },
]
