INTERFACE_CONFIGS = [
    "ArduinoOnly",
    "ArduinoToRaspi",
    "AppToArduino",
    "RaspiToArduino",
    "RaspiOnly",
    "AppToRaspi",
    "RaspiToApp",
]

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
    {
        "Name": "vacuum_robot_requests_interface",
        "DataTypeDefinition": "vacuum_robot_requests.json",
        "InterfaceConfig": "AppToRaspi",
    },
    {
        "Name": "vacuum_robot_status_interface",
        "DataTypeDefinition": "vacuum_robot_status.json",
        "InterfaceConfig": "RaspiToApp",
    },
    {
        "Name": "christmas_light_request_interface",
        "DataTypeDefinition": "christmas_light_request.json",
        "InterfaceConfig": "AppToRaspi",
    },
    {
        "Name": "christmas_light_status_interface",
        "DataTypeDefinition": "christmas_light_status.json",
        "InterfaceConfig": "RaspiToApp",
    },
    {
        "Name": "modules_stati_interface",
        "DataTypeDefinition": "module_status.json",
        "InterfaceConfig": "RaspiToApp",
    },
    {
        "Name": "people_at_home_interface",
        "DataTypeDefinition": "people_at_home.json",
        "InterfaceConfig": "RaspiToApp",
    },
    {
        "Name": "light_chain_request_interface",
        "DataTypeDefinition": "light_chain_request.json",
        "InterfaceConfig": "AppToRaspi",
    },
    {
        "Name": "flight_main_rotor_speed_interface",
        "DataTypeDefinition": "flight_main_rotor_speed.json",
        "InterfaceConfig": "AppToArduino",
        "InterfaceShortName": "S",
    },
    {
        "Name": "flight_altitude_interface",
        "DataTypeDefinition": "flight_altitude.json",
        "InterfaceConfig": "AppToArduino",
        "InterfaceShortName": "A",
    },
    {
        "Name": "flight_direction_interface",
        "DataTypeDefinition": "flight_direction.json",
        "InterfaceConfig": "AppToArduino",
        "InterfaceShortName": "D",
    },
    {
        "Name": "flight_switches_interface",
        "DataTypeDefinition": "flight_switches.json",
        "InterfaceConfig": "AppToArduino",
        "InterfaceShortName": "B",
    },
    {
        "Name": "flight_main_rotor_speed_funk_interface",
        "DataTypeDefinition": "flight_main_rotor_speed.json",
        "InterfaceConfig": "ArduinoToArduino",
        "InterfaceShortName": "S",
    },
    {
        "Name": "flight_altitude_funk_interface",
        "DataTypeDefinition": "flight_altitude.json",
        "InterfaceConfig": "ArduinoToArduino",
        "InterfaceShortName": "A",
    },
    {
        "Name": "flight_direction_funk_interface",
        "DataTypeDefinition": "flight_direction.json",
        "InterfaceConfig": "ArduinoToArduino",
        "InterfaceShortName": "D",
    },
    {
        "Name": "flight_switches_funk_interface",
        "DataTypeDefinition": "flight_switches.json",
        "InterfaceConfig": "ArduinoToArduino",
        "InterfaceShortName": "B",
    },
]
