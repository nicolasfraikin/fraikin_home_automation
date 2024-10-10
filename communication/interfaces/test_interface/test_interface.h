#include "interface_base.h"

struct Data {
    bool test_bool{};
    uint8_t test_uint8{};
};

class TestInterface : public InterfaceBase<TestInterface, Data> {
  public:
    using DataType = Data;
};