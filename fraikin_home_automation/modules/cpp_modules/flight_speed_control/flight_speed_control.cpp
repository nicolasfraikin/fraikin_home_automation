/*
 * FlightSpeedControl
 */

#include "flight_speed_control.h"

#define PROPELLER_LEFT_PIN1 3
#define PROPELLER_LEFT_PIN2 7
#define PROPELLER_RIGHT_PIN1 11
#define PROPELLER_RIGHT_PIN2 12

FlightSpeedControl::FlightSpeedControl() : rotor_speed_{}, previous_rotor_speed_{}, flight_switches_{} {}

void FlightSpeedControl::UpdateInterfaceSubscription() {
  previous_rotor_speed_ = rotor_speed_;
  ReceiveInterfaceData<FlightMainRotorSpeedFunkInterface>(rotor_speed_);
  ReceiveInterfaceData<FlightSwitchesFunkInterface>(flight_switches_);
}
void FlightSpeedControl::UpdateInterfacePublishing() {}

void FlightSpeedControl::Init() {
  pinMode(PROPELLER_LEFT_PIN1, OUTPUT);
  pinMode(PROPELLER_LEFT_PIN2, OUTPUT);
  pinMode(PROPELLER_RIGHT_PIN1, OUTPUT);
  pinMode(PROPELLER_RIGHT_PIN2, OUTPUT);
}

void FlightSpeedControl::Step() {
  if (rotor_speed_.v != previous_rotor_speed_.v) {
    Log("Motor speed = " + String(rotor_speed_.v));
    SetMotorLeftSpeed(rotor_speed_.v);
    SetMotorRightSpeed(rotor_speed_.v);
  }
}
void FlightSpeedControl::SetMotorLeftSpeed(const uint8_t speed) {
  analogWrite(PROPELLER_LEFT_PIN1, speed);
  digitalWrite(PROPELLER_LEFT_PIN2, LOW);
}

void FlightSpeedControl::SetMotorRightSpeed(const uint8_t speed) {
  analogWrite(PROPELLER_RIGHT_PIN1, speed);
  digitalWrite(PROPELLER_RIGHT_PIN2, LOW);
}