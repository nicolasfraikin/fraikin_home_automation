import os
from fraikin_home_automation.communication.data_types.christmas_light_request import ChristmasLightRequest, Request
from fraikin_home_automation.communication.data_types.christmas_light_status import ChristmasLightStatus, Status
from fraikin_home_automation.communication.interfaces.christmas_light_request_interface import \
    ChristmasLightRequestInterface
from fraikin_home_automation.communication.interfaces.christmas_light_status_interface import \
    ChristmasLightStatusInterface

from fraikin_home_automation.common.py_base_classes.i_module import IModule
from fraikin_home_automation.modules.py_modules.libraries.sonoff import Sonoff


class ChristmasLightManager(IModule):
    def __init__(self):
        self.light_request = ChristmasLightRequest()
        self.previous_light_request = ChristmasLightRequest()
        self.light_status = ChristmasLightStatus()
        try:
            self.sonoff_obj = Sonoff(os.environ["EWELINK_EMAIL"], os.environ["EWELINK_PW"], 'eu')
        except:
            self.sonoff_obj = None
            print("ChristmasLightManager: Login to Sonoff failed")

    def init(self):
        pass

    def step(self):
        self.attempt_login()
        self.check_if_light_should_be_turned_on()
        self.check_if_light_should_be_turned_off()
        self.update_status()

    def attempt_login(self):
        if self.sonoff_obj is None:
            try:
                self.sonoff_obj = Sonoff(os.environ["EWELINK_EMAIL"], os.environ["EWELINK_PW"], 'eu')
            except:
                self.sonoff_obj = None
                print("ChristmasLightManager: Login to Sonoff failed")

    def check_if_light_should_be_turned_on(self):
        if (Request(self.previous_light_request.request) != Request.kTurnOn or self.get_status()=='off') and Request(
                self.light_request.request) == Request.kTurnOn:
            print("Turn light on")
            self.turn_light_on()

    def check_if_light_should_be_turned_off(self):
        if (Request(self.previous_light_request.request) != Request.kTurnOff or self.get_status()=='on') and Request(
                self.light_request.request) == Request.kTurnOff:
            print("Turn light off")
            self.turn_light_off()

    def get_status(self):
        self.sonoff_obj.update_devices()
        status = self.sonoff_obj.get_device('1000b0f22f')
        return status['params']['switch']
    def update_status(self):
        match self.get_status():
            case 'on':
                self.light_status.status = Status.kTurnedOn
            case 'off':
                self.light_status.status = Status.kTurnedOff

    def turn_light_on(self):
        self.sonoff_obj.switch('on', '1000b0f22f')

    def turn_light_off(self):
        self.sonoff_obj.switch('off', '1000b0f22f')

    def update_interface_subscription(self):
        self.previous_light_request = self.light_request
        self.light_request = ChristmasLightRequestInterface.get_instance().get_data()

    def update_interface_publishing(self):
        ChristmasLightStatusInterface.get_instance().set_data(self.light_status)
