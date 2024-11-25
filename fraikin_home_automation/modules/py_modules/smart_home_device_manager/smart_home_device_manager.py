import datetime
import os
import time
from fraikin_home_automation.communication.interfaces.requested_smart_home_runs_interface import \
    RequestedSmartHomeRunsInterface, RequestedSmartHomeRunsInterfaceDataType
from fraikin_home_automation.communication.interfaces.scheduled_smart_home_runs_interface import \
    ScheduledSmartHomeRunsInterface, ScheduledSmartHomeRunsInterfaceDataType

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
        try:
            self.sonoff_obj = Sonoff(os.environ["EWELINK_EMAIL"], os.environ["EWELINK_PW"], 'eu')
        except:
            self.sonoff_obj = None
            print("SmartHomeDeviceManager: Login to Sonoff failed")
        self.dishwasher_request_turn_off_timer = None

    def init(self):
        pass

    def step(self):
        self.attempt_login()
        self.check_if_run_was_requested()
        self.check_if_dishwasher_run_should_be_triggered()
        self.check_if_dishwasher_should_be_turned_off()

    def attempt_login(self):
        if self.sonoff_obj is None:
            try:
                self.sonoff_obj = Sonoff(os.environ["EWELINK_EMAIL"], os.environ["EWELINK_PW"], 'eu')
            except:
                self.sonoff_obj = None
                print("SmartHomeDeviceManager: Login to Sonoff failed")

    def check_if_dishwasher_should_be_turned_off(self):
        if self.dishwasher_request_turn_off_timer is not None and time.time() - self.dishwasher_request_turn_off_timer > DISHWASHER_TURN_OFF_TIME:
            self.turn_dishwasher_off()
            print("Turn dishwasher off")
            if self.get_status()=='off':
                self.dishwasher_request_turn_off_timer = None
            elif  time.time() - self.dishwasher_request_turn_off_timer > DISHWASHER_TURN_OFF_MAX_TRIES_TIME:
                self.dishwasher_request_turn_off_timer = None

    def check_if_run_was_requested(self):
        if self.requested_runs.dishwasher_run_requested and not self.previous_requested_runs.dishwasher_run_requested:
            self.scheduled_runs.dishwasher_run_scheduled = True
            self.scheduled_runs.scheduled_dishwasher_run_time = self.requested_runs.requested_dishwasher_run_time
            self.convert_to_time()
            self.dishwasher_request_turn_off_timer = time.time()
            print("Dishwasher run was requested")

    def check_if_dishwasher_run_should_be_triggered(self):
        current_time = datetime.datetime.now()
        if (self.scheduled_run_time is not None
                and self.scheduled_run_time.day == current_time.day
                and self.previous_time.hour != self.scheduled_run_time.hour
                and current_time.hour == self.scheduled_run_time.hour):
            self.turn_dishwasher_on()
            self.reset_scheduled_interface_data()

        self.previous_time = current_time

    def turn_dishwasher_on(self):
        self.sonoff_obj.switch('on', '1000a5ffcd')

    def turn_dishwasher_off(self):
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
        self.sonoff_obj.update_devices()
        status = self.sonoff_obj.get_device('1000a5ffcd')
        return status['params']['switch']
    def update_interface_subscription(self):
        self.previous_requested_runs = self.requested_runs
        self.requested_runs = RequestedSmartHomeRunsInterface.get_instance().get_data()

    def update_interface_publishing(self):
        ScheduledSmartHomeRunsInterface.get_instance().set_data(self.scheduled_runs)
