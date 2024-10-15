//
// Created by nico on 10/2/24.
//

#ifndef MODULE_BASE_H
#define MODULE_BASE_H
#include "i_module.h"
#include "interface_base.h"
#include "logging.h"

class ModuleBase : public IModule {
  public:
    template <typename Interface>
    void ReceiveInterfaceData(typename Interface::DataType& variable) {
      variable = Interface::GetData();
    }
    template <typename Interface>
    void PublishInterfaceData(const typename Interface::DataType& variable) {
      Interface::SetData(variable);
    }
    void StepModule() final {
      UpdateInterfaceSubscription();
      Step();
      UpdateInterfacePublishing(); // TODO: This makes everything crash
    }
};

#endif // MODULE_BASE_H
