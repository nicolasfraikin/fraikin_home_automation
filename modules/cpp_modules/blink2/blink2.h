#ifndef BLINK_2_H
#define BLINK_2_H

#ifdef NON_TARGET_BUILD
#include "fraikin_home_automation/utils/test_stubs/arduino_mock.h"
#else
#include <Arduino.h>
#endif
#include "module_base.h"
#include "test_interface.h"

class Blink2 : public ModuleBase {
  public:
    void Init() final;
    void Step() final;
    void UpdateInterfaceSubscription() final;
    void UpdateInterfacePublishing() final;

  private:
    uint8_t local_uint8{0};
    TestInterface::DataType data{};
};
#endif // BLINK_2_H