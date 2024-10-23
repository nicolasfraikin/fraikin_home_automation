import serial
from fraikin_home_automation.communication.interfaces.test_1_interface import Test1Interface, Test1InterfaceDataType

from fraikin_home_automation.common.py_base_classes.i_module import IModule


class SerialCommunicationTest(IModule):
    def __init__(self):
        self.ser = serial.Serial('/dev/ttyUSB0', 9600, timeout=0.1)
        self.ser.reset_input_buffer()
        self.data = Test1InterfaceDataType()

    def init(self):
        # print("Init function")
        pass

    def step(self):
        line_read = self.ser.readline().decode('utf-8').rstrip()
        if "Arduino" in line_read:
            print(line_read)

    def update_interface_subscription(self):
        pass

    def update_interface_publishing(self):
        data = self.data
        data.test_uint8 = 243
        Test1Interface.get_instance().set_data(data)
