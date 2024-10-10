#ifdef NON_TARGET_BUILD
#include "fraikin_home_automation/utils/test_stubs/arduino_mock.h"
#else
#include <Arduino.h>
#endif
#include "module_base.h"

class Blink : ModuleBase {
  public:
    Blink();
    void Init() override;
    void Step() override;

  private:
    bool test_bool_blink_;
};