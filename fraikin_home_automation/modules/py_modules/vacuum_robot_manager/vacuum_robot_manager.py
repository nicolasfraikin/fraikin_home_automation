from fraikin_home_automation.communication.data_types.vacuum_robot_requests import VacuumRobotRequests, Request
from fraikin_home_automation.communication.data_types.vacuum_robot_status import VacuumRobotStatus
from fraikin_home_automation.communication.interfaces.vacuum_robot_requests_interface import \
    VacuumRobotRequestsInterface
from fraikin_home_automation.communication.interfaces.vacuum_robot_status_interface import \
    VacuumRobotStatusInterface
from miio import RoborockVacuum

from fraikin_home_automation.common.py_base_classes.i_module import IModule


class VacuumRobotManager(IModule):
    def __init__(self):
        self.robot_requests = VacuumRobotRequests()
        self.previous_robot_requests = VacuumRobotRequests()
        self.robot_status = VacuumRobotStatus()
        self.robot = RoborockVacuum("192.168.1.100", "486f42413378664255546a6145676247")

    def init(self):
        pass

    def step(self):
        self.check_if_robot_should_be_started()
        self.check_if_robot_should_be_stopped()
        self.update_status()

    def check_if_robot_should_be_started(self):
        if Request(self.previous_robot_requests.request) != Request(Request.kStartRobot) and Request(self.robot_requests.request) == Request.kStartRobot:
            print("Start vacuum robot")
            self.robot.start()

    def check_if_robot_should_be_stopped(self):
        if Request(self.previous_robot_requests.request) != Request.kStopRobot and Request(self.robot_requests.request) == Request.kStopRobot:
            print("Stop vacuum robot")
            self.robot.home()

    def update_status(self):
        self.robot_status.status = self.robot.status().state

    def update_interface_subscription(self):
        self.previous_robot_requests = self.robot_requests
        self.robot_requests = VacuumRobotRequestsInterface.get_instance().get_data()

    def update_interface_publishing(self):
        VacuumRobotStatusInterface.get_instance().set_data(self.robot_status)
