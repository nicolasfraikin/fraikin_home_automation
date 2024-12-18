#ifndef FLIGHT_ALTITUDE_CONTROL_H
#define FLIGHT_ALTITUDE_CONTROL_H

#ifdef NON_TARGET_BUILD
#include "fraikin_home_automation/utils/test_stubs/arduino_mock.h"
#else
#include <Arduino.h>
#endif
#include "flight_altitude_funk_interface.h"
#include "module_base.h"

class FlightAltitudeControl : public ModuleBase {
  public:
    FlightAltitudeControl();
    void Init() final;
    void Step() final;
    void UpdateInterfaceSubscription() final;
    void UpdateInterfacePublishing() final;

  private:
    FlightAltitudeFunkInterface::DataType altitude_;
    FlightAltitudeFunkInterface::DataType previous_altitude_;
};
#endif // FLIGHT_ALTITUDE_CONTROL_H