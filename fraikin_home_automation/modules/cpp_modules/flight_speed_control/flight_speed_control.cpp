/*
 * FlightSpeedControl
 */

#include "flight_speed_control.h"

FlightSpeedControl::FlightSpeedControl() : rotor_speed_{}, previous_rotor_speed_{} {}

void FlightSpeedControl::UpdateInterfaceSubscription() {
  previous_rotor_speed_ = rotor_speed_;
  ReceiveInterfaceData<FlightMainRotorSpeedFunkInterface>(rotor_speed_);
}
void FlightSpeedControl::UpdateInterfacePublishing() {}

void FlightSpeedControl::Init() {}

void FlightSpeedControl::Step() {
  if (rotor_speed_.v != previous_rotor_speed_.v) {
    Log("Motor speed = " + String(rotor_speed_.v));
  }
}