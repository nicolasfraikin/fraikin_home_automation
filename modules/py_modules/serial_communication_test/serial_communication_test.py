import serial

from fraikin_home_automation.common.py_base_classes.i_module import IModule


class SerialCommunicationTest(IModule):
    def __init__(self):
        self.ser = serial.Serial('/dev/ttyUSB0', 9600, timeout=1)
        self.ser.reset_input_buffer()

    def init(self):
        # print("Init function")
        pass

    def step(self):
        # Write
        self.ser.write(b"Test1Interface : test_bool=false | test_uint8=145 | test_enum=2\n")
        # Now read
        line_read = self.ser.readline().decode('utf-8').rstrip()
        print(line_read)

    def update_interface_subscription(self):
        pass

    def update_interface_publishing(self):
        # data = self.data
        # data.test_uint8 = self.test
        # TestInterface1.get_instance().set_data(data)
        pass
