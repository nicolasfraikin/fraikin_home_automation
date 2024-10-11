//
// Created by nico on 10/2/24.
//

#ifndef MODULE_BASE_H
#define MODULE_BASE_H
#include "i_module.h"
#include "interface_base.h"

class ModuleBase : public IModule {
  public:
    void Step() override {
      UpdateInterfaceSubscription();
      UpdateInterfacePublishing();
      Step();
    }
};

#endif // MODULE_BASE_H
