from fraikin_home_automation.communication.interfaces.danbolig_house_interface import DanboligHouseInterface, \
    DanboligHouseInterfaceDataType

from fraikin_home_automation.common.py_base_classes.i_module import IModule


class DanboligHouseWatcher(IModule):
    def __init__(self):
        self.data = DanboligHouseInterfaceDataType()

    def init(self):
        # print("Init function")
        pass

    def step(self):
        pass

    def update_interface_subscription(self):
        pass

    def update_interface_publishing(self):
        DanboligHouseInterface.get_instance().set_data(self.data)
