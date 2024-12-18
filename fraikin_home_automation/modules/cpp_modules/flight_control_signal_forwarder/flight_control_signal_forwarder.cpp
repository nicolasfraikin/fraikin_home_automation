/*
 * FlightControlSignalForwarder
 */

#include "flight_control_signal_forwarder.h"

FlightControlSignalForwarder::FlightControlSignalForwarder()
    : rotor_speed_from_bluetooth_{}, previous_rotor_speed_from_bluetooth_{}, rotor_speed_to_plane_{},
      altitude_from_bluetooth_{}, previous_altitude_from_bluetooth_{}, altitude_to_plane_{},
      direction_from_bluetooth_{}, previous_direction_from_bluetooth_{}, direction_to_plane_{},
      switches_from_bluetooth_{}, previous_switches_from_bluetooth_{}, switches_to_plane_{} {}

void FlightControlSignalForwarder::UpdateInterfaceSubscription() {
  ReceiveInterface<FlightMainRotorSpeedInterface>(previous_rotor_speed_from_bluetooth_, rotor_speed_from_bluetooth_);
  ReceiveInterface<FlightAltitudeInterface>(previous_altitude_from_bluetooth_, altitude_from_bluetooth_);
  ReceiveInterface<FlightDirectionInterface>(previous_direction_from_bluetooth_, direction_from_bluetooth_);
  ReceiveInterface<FlightSwitchesInterface>(previous_switches_from_bluetooth_, switches_from_bluetooth_);
}
void FlightControlSignalForwarder::UpdateInterfacePublishing() {
  PublishInterfaceData<FlightMainRotorSpeedFunkInterface>(rotor_speed_to_plane_);
  PublishInterfaceData<FlightAltitudeFunkInterface>(altitude_to_plane_);
  PublishInterfaceData<FlightDirectionFunkInterface>(direction_to_plane_);
  PublishInterfaceData<FlightSwitchesFunkInterface>(switches_to_plane_);
}

void FlightControlSignalForwarder::Init() {
  FlightMainRotorSpeedFunkInterface::SendOnChangeData(true);
  FlightAltitudeFunkInterface::SendOnChangeData(true);
  FlightDirectionFunkInterface::SendOnChangeData(true);
  FlightSwitchesFunkInterface::SendOnChangeData(true);
}

void FlightControlSignalForwarder::ConfigureInterfaceSendingOnChange() {
  FlightMainRotorSpeedFunkInterface::SendOnChangeData(rotor_speed_from_bluetooth_.v !=
                                                      previous_rotor_speed_from_bluetooth_.v);
  FlightAltitudeFunkInterface::SendOnChangeData(altitude_from_bluetooth_.v != previous_altitude_from_bluetooth_.v);
  FlightDirectionFunkInterface::SendOnChangeData(direction_from_bluetooth_.v != previous_direction_from_bluetooth_.v);
  FlightSwitchesFunkInterface::SendOnChangeData(switches_from_bluetooth_.v != previous_switches_from_bluetooth_.v);
}

void FlightControlSignalForwarder::ForwardData() {
  rotor_speed_to_plane_.v = rotor_speed_from_bluetooth_.v;
  altitude_to_plane_.v = altitude_from_bluetooth_.v;
  direction_to_plane_.v = direction_from_bluetooth_.v;
  switches_to_plane_.v = switches_from_bluetooth_.v;
}

void FlightControlSignalForwarder::Step() {
  ConfigureInterfaceSendingOnChange();
  ForwardData();
}