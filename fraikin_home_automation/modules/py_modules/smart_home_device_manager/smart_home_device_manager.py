import datetime
import os
import time
from fraikin_home_automation.communication.interfaces.requested_smart_home_runs_interface import \
    RequestedSmartHomeRunsInterface, RequestedSmartHomeRunsInterfaceDataType
from fraikin_home_automation.communication.interfaces.scheduled_smart_home_runs_interface import \
    ScheduledSmartHomeRunsInterface, ScheduledSmartHomeRunsInterfaceDataType
# Christmas lights
from fraikin_home_automation.communication.data_types.christmas_light_request import ChristmasLightRequest, Request
from fraikin_home_automation.communication.data_types.christmas_light_status import ChristmasLightStatus, Status
from fraikin_home_automation.communication.interfaces.christmas_light_request_interface import \
    ChristmasLightRequestInterface
from fraikin_home_automation.communication.interfaces.christmas_light_status_interface import \
    ChristmasLightStatusInterface

from fraikin_home_automation.common.py_base_classes.i_module import IModule
from fraikin_home_automation.modules.py_modules.libraries.sonoff import Sonoff

DISHWASHER_TURN_OFF_TIME = 5
DISHWASHER_TURN_OFF_MAX_TRIES_TIME = 60


class SmartHomeDeviceManager(IModule):
    def __init__(self):
        self.requested_runs = RequestedSmartHomeRunsInterfaceDataType()
        self.previous_requested_runs = RequestedSmartHomeRunsInterfaceDataType()
        self.scheduled_runs = ScheduledSmartHomeRunsInterfaceDataType()
        self.scheduled_run_time = None
        self.previous_time = datetime.datetime.now()
        self.login_to_sonoff()
        self.dishwasher_request_turn_off_timer = None
        # Christmas lights
        self.light_request = ChristmasLightRequest()
        self.previous_light_request = ChristmasLightRequest()
        self.light_status = ChristmasLightStatus()

    def init(self):
        pass

    def step(self):
        self.sonoff_obj.update_devices()
        self.attempt_login()
        self.check_if_run_was_requested()
        self.check_if_dishwasher_run_should_be_triggered()
        self.check_if_dishwasher_should_be_turned_off()
        # Christmas lights
        self.check_if_light_should_be_turned_on()
        self.check_if_light_should_be_turned_off()
        self.update_light_status()

    def update_light_status(self):
        match self.get_light_status():
            case 'on':
                self.light_status.status = Status.kTurnedOn
            case 'off':
                self.light_status.status = Status.kTurnedOff

    def check_if_light_should_be_turned_on(self):
        if (Request(self.previous_light_request.request) != Request.kTurnOn or self.get_light_status()=='off') and Request(
                self.light_request.request) == Request.kTurnOn:
            print("Turn light on")
            self.turn_light_on()

    def check_if_light_should_be_turned_off(self):
        if (Request(self.previous_light_request.request) != Request.kTurnOff or self.get_light_status()=='on') and Request(
                self.light_request.request) == Request.kTurnOff:
            print("Turn light off")
            self.turn_light_off()

    def turn_light_on(self):
        self.login_to_sonoff()
        self.sonoff_obj.switch('on', '1000b0f22f')

    def turn_light_off(self):
        self.login_to_sonoff()
        self.sonoff_obj.switch('off', '1000b0f22f')

    def login_to_sonoff(self):
        try:
            self.sonoff_obj = Sonoff(os.environ["EWELINK_EMAIL"], os.environ["EWELINK_PW"], 'eu')
        except:
            self.sonoff_obj = None
            print("SmartHomeDeviceManager: Login to Sonoff failed")

    def attempt_login(self):
        if self.sonoff_obj is None:
            self.login_to_sonoff()

    def check_if_dishwasher_should_be_turned_off(self):
        if self.dishwasher_request_turn_off_timer is not None and time.time() - self.dishwasher_request_turn_off_timer > DISHWASHER_TURN_OFF_TIME:
            self.turn_dishwasher_off()
            print("Turn dishwasher off")
            print("Status is " + self.get_status())
            if self.get_status()=='off':
                print("Status was off, so exit trying")
                self.dishwasher_request_turn_off_timer = None
            elif time.time() - self.dishwasher_request_turn_off_timer > DISHWASHER_TURN_OFF_MAX_TRIES_TIME:
                self.dishwasher_request_turn_off_timer = None
                print("Max numbe of tries time exceeded")
            else:
                print("Dishwasher did not turn off, try again later")

    def check_if_run_was_requested(self):
        if self.requested_runs.dishwasher_run_requested and self.requested_runs.message_id != self.previous_requested_runs.message_id:
            self.scheduled_runs.dishwasher_run_scheduled = True
            self.scheduled_runs.scheduled_dishwasher_run_time = self.requested_runs.requested_dishwasher_run_time
            self.convert_to_time()
            self.dishwasher_request_turn_off_timer = time.time()
            print("Dishwasher run was requested")

    def check_if_dishwasher_run_should_be_triggered(self):
        current_time = datetime.datetime.now()
        if (self.scheduled_run_time is not None
                and self.scheduled_run_time.day == current_time.day
                and current_time.hour == self.scheduled_run_time.hour):
            if self.get_status()!='on':
                self.turn_dishwasher_on()
            else:
                self.reset_scheduled_interface_data()

        self.previous_time = current_time

    def turn_dishwasher_on(self):
        self.login_to_sonoff()
        self.sonoff_obj.switch('on', '1000a5ffcd')

    def turn_dishwasher_off(self):
        self.login_to_sonoff()
        self.sonoff_obj.switch('off', '1000a5ffcd')

    def reset_scheduled_interface_data(self):
        self.scheduled_runs.dishwasher_run_scheduled = False
        self.scheduled_runs.scheduled_dishwasher_run_time = None

    def convert_to_time(self):
        date_today = datetime.datetime.now()
        date_tomorrow = date_today + datetime.timedelta(days=1)
        chosen_date = date_today if "Today" in self.scheduled_runs.scheduled_dishwasher_run_time else date_tomorrow
        chosen_hour = int(self.scheduled_runs.scheduled_dishwasher_run_time.split(",")[1].split(":")[0])
        self.scheduled_run_time = datetime.datetime(chosen_date.year, chosen_date.month, chosen_date.day,
                                                    chosen_hour)

    def get_status(self):
        # self.sonoff_obj.update_devices()
        status = self.sonoff_obj.get_device('1000a5ffcd')
        return status['params']['switch']

    def get_light_status(self):
        # self.sonoff_obj.update_devices()
        status = self.sonoff_obj.get_device('1000b0f22f')
        return status['params']['switch']
    def update_interface_subscription(self):
        self.previous_requested_runs = self.requested_runs
        self.requested_runs = RequestedSmartHomeRunsInterface.get_instance().get_data()
        # Christmas lights
        self.previous_light_request = self.light_request
        self.light_request = ChristmasLightRequestInterface.get_instance().get_data()

    def update_interface_publishing(self):
        ScheduledSmartHomeRunsInterface.get_instance().set_data(self.scheduled_runs)
        # Christmas lights
        ChristmasLightStatusInterface.get_instance().set_data(self.light_status)
