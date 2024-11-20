from abc import ABC, abstractmethod


# Define an abstract base class
class IModule(ABC):

    def get_cycle_time(self):
        return -1

    @abstractmethod
    def init(self):
        pass

    @abstractmethod
    def step(self):
        pass

    @abstractmethod
    def update_interface_subscription(self):
        pass

    @abstractmethod
    def update_interface_publishing(self):
        pass
