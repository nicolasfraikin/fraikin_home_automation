//
// Created by nico on 10/2/24.
//

#ifndef MODULE_BASE_H
#define MODULE_BASE_H
#include "i_module.h"
#include "i_interface.h"

class ModuleBase : public IModule {
  protected:
    //    template <typename T>
    //    void AddSubscriberCallback(const std::function<void(const T&)>& subsriber_callback);
    //    template <typename T>
    //    void AddPublisherCallback(const std::function<T(void)>& publisher_callback);
};

#endif // MODULE_BASE_H
