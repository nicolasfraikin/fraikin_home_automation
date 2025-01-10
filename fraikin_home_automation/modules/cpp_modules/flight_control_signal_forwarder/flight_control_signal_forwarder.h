#ifndef FLIGHT_SPEED_CONTROL_SIGNAL_FORWARDER_H
#define FLIGHT_SPEED_CONTROL_SIGNAL_FORWARDER_H

#ifdef NON_TARGET_BUILD
#include "fraikin_home_automation/utils/test_stubs/arduino_mock.h"
#else
#include <Arduino.h>
#endif
#include "flight_altitude_funk_interface.h"
#include "flight_altitude_interface.h"
#include "flight_direction_funk_interface.h"
#include "flight_direction_interface.h"
#include "flight_main_rotor_speed_funk_interface.h"
#include "flight_main_rotor_speed_interface.h"
#include "flight_switches_funk_interface.h"
#include "flight_switches_interface.h"
#include "module_base.h"

class FlightControlSignalForwarder : public ModuleBase {
  public:
    FlightControlSignalForwarder();
    void Init() final;
    void Step() final;
    void UpdateInterfaceSubscription() final;
    void UpdateInterfacePublishing() final;

  private:
    template <typename Interface, typename InterfaceDataType>
    void ReceiveInterface(InterfaceDataType& previous_data, InterfaceDataType& current_data) {
      previous_data = current_data;
      ReceiveInterfaceData<Interface>(current_data);
    }
    void ConfigureInterfaceSendingOnChange();
    void ForwardData();
    void CheckBluetoothConnection();
    // Speed
    FlightMainRotorSpeedInterface::DataType rotor_speed_from_bluetooth_;
    FlightMainRotorSpeedInterface::DataType previous_rotor_speed_from_bluetooth_;
    FlightMainRotorSpeedFunkInterface::DataType rotor_speed_to_plane_;
    // Altitude
    FlightAltitudeInterface::DataType altitude_from_bluetooth_;
    FlightAltitudeInterface::DataType previous_altitude_from_bluetooth_;
    FlightAltitudeFunkInterface::DataType altitude_to_plane_;
    // Direction
    FlightDirectionInterface::DataType direction_from_bluetooth_;
    FlightDirectionInterface::DataType previous_direction_from_bluetooth_;
    FlightDirectionFunkInterface::DataType direction_to_plane_;
    // Switches
    FlightSwitchesInterface::DataType switches_from_bluetooth_;
    FlightSwitchesInterface::DataType previous_switches_from_bluetooth_;
    FlightSwitchesFunkInterface::DataType switches_to_plane_;
    // Bluetooth state
    bool bluetooth_connected_;
    unsigned long bluetooth_check_timer_;
};
#endif // FLIGHT_SPEED_CONTROL_SIGNAL_FORWARDER_H