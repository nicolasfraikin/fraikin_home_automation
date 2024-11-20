from fraikin_home_automation.communication.interfaces.danbolig_house_interface import DanboligHouseInterface, \
    DanboligHouseInterfaceDataType
from fraikin_home_automation.communication.interfaces.test_3_interface import Test3Interface, \
    Test3InterfaceDataType

from fraikin_home_automation.common.py_base_classes.i_module import IModule


class DanboligHouseWatcher(IModule):
    def __init__(self):
        self.data = DanboligHouseInterfaceDataType()
        self.test_data = Test3InterfaceDataType()
        # IModule.cycle_time = 60

    def init(self):
        # print("Init function")
        pass

    def step(self):
        self.test_data.test_uint8 -= 1

    def update_interface_subscription(self):
        pass

    def update_interface_publishing(self):
        DanboligHouseInterface.get_instance().set_data(self.data)
        Test3Interface.get_instance().set_data(self.test_data)
