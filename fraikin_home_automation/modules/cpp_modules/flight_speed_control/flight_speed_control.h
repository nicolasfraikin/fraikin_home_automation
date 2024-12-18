#ifndef FLIGHT_SPEED_CONTROL_H
#define FLIGHT_SPEED_CONTROL_H

#ifdef NON_TARGET_BUILD
#include "fraikin_home_automation/utils/test_stubs/arduino_mock.h"
#else
#include <Arduino.h>
#endif
#include "flight_main_rotor_speed_funk_interface.h"
#include "module_base.h"

class FlightSpeedControl : public ModuleBase {
  public:
    FlightSpeedControl();
    void Init() final;
    void Step() final;
    void UpdateInterfaceSubscription() final;
    void UpdateInterfacePublishing() final;

  private:
    FlightMainRotorSpeedFunkInterface::DataType rotor_speed_;
    FlightMainRotorSpeedFunkInterface::DataType previous_rotor_speed_;
};
#endif // FLIGHT_SPEED_CONTROL_H