#include <Arduino.h>
#include <module_base.h>

class Blink : ModuleBase {
  public:
    void Init() final;
    void Step() final;

  private:
};