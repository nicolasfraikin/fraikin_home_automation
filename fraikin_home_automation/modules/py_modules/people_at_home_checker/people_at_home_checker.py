from fraikin_home_automation.communication.interfaces.people_at_home_interface import \
    PeopleAtHomeInterface, PeopleAtHomeInterfaceDataType

from fraikin_home_automation.common.py_base_classes.i_module import IModule
import subprocess
import datetime

IP_ADDRESSES_MATCHER = {
    "192.168.1.111" : "Nico",
    "192.168.1.103" : "Irrelevant",
    "192.168.1.1" : "Irrelevant",
    "192.168.1.107" : "Irrelevant",
}

class PeopleAtHomeChecker(IModule):
    def __init__(self):
        self.people_at_home = PeopleAtHomeInterfaceDataType()

    def get_cycle_time(self):
        return 1800
    def init(self):
        pass

    def step(self):
        self.update_people_at_home()
    def update_people_at_home(self):
        ip_addresses = self.get_currently_present_ip_adresses()
        at_home_names = []
        for address in ip_addresses:
            if address in IP_ADDRESSES_MATCHER.keys():
                at_home_names.append(IP_ADDRESSES_MATCHER[address])
            else:
                at_home_names.append("Unknown address (" + address + ")")
        self.people_at_home.people_at_home = ";".join(at_home_names)
        self.people_at_home.update_time = datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")

    def get_currently_present_ip_adresses(self):
        ip_addresses = subprocess.check_output('arp -a | grep wlan0', shell=True, text=True)
        ip_addresses = ip_addresses.split("\n")
        ip_addresses = [adress.split("(")[1].split(")")[0] for adress in ip_addresses if len(adress) > 0]
        online_adresses = []
        for address in ip_addresses:
            if (address in IP_ADDRESSES_MATCHER.keys() and not "Irrelevant" in IP_ADDRESSES_MATCHER[address]) or address not in IP_ADDRESSES_MATCHER.keys():
                if self.ping(address):
                    online_adresses.append(address)
        return online_adresses

    def ping(self, host):
        command = ['ping', '-c', '1', host]
        return subprocess.call(command) == 0

    def update_interface_subscription(self):
        pass
    def update_interface_publishing(self):
        PeopleAtHomeInterface.get_instance().set_data(self.people_at_home)
