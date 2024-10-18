import time
from fraikin_home_automation.communication.interfaces.test_interface import TestInterface

from fraikin_home_automation.common.py_base_classes.i_module import IModule


class SerialCommunicationTest(IModule):
    def __init__(self):
        self.data = None
        self.test = 0

    def init(self):
        # print("Init function")
        pass

    def step(self):
        time.sleep(1)
        self.test += 5
        # print("Step function")
        pass

    def update_interface_subscription(self):
        self.data = TestInterface.get_instance().get_data()

    def update_interface_publishing(self):
        data = self.data
        data.test_uint8 = self.test
        TestInterface.get_instance().set_data(data)
