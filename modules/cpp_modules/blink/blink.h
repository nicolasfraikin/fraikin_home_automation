#ifndef BLINK_H
#define BLINK_H

#ifdef NON_TARGET_BUILD
#include "fraikin_home_automation/utils/test_stubs/arduino_mock.h"
#else
#include <Arduino.h>
#endif
#include "module_base.h"
#include "test_interface.h"

class Blink : ModuleBase {
  public:
    Blink();
    void Init() final;
    void Step() final;
    void UpdateInterfaceSubscription() final;
    void UpdateInterfacePublishing() final;

  private:
    TestInterface::DataType test_interface_object_;
};
#endif // BLINK_H