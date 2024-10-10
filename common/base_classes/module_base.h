//
// Created by nico on 10/2/24.
//

#ifndef MODULE_BASE_H
#define MODULE_BASE_H
#include "i_module.h"
#include "interface_base.h"
#include <functional>
#include <memory>

class ModuleBase : public IModule {
  protected:
    template <typename T>
    void AddSubscriberCallback(const std::function<void(const typename T::DataType&)> subscriber_callback) {
      T::AddSubscriberCallback(subscriber_callback);
    };
    template <typename T>
    void AddPublisherCallback(const std::function<T(void)> publisher_callback) {};
};

#endif // MODULE_BASE_H
