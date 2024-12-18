/*
 * FlightAltitudeControl
 */

#include "flight_altitude_control.h"

FlightAltitudeControl::FlightAltitudeControl() : altitude_{}, previous_altitude_{} {}

void FlightAltitudeControl::UpdateInterfaceSubscription() {
  previous_altitude_ = altitude_;
  ReceiveInterfaceData<FlightAltitudeFunkInterface>(altitude_);
}
void FlightAltitudeControl::UpdateInterfacePublishing() {}

void FlightAltitudeControl::Init() {}

void FlightAltitudeControl::Step() {
  if (altitude_.v != previous_altitude_.v) {
    Log("Altitude = " + String(altitude_.v));
  }
}