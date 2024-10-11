//
// Created by nico on 10/2/24.
//

#ifndef I_MODULE_H
#define I_MODULE_H

class IModule {
  public:
    virtual void Init() = 0;
    virtual void Step() = 0;
    virtual void UpdateInterfaceSubscription() = 0;
    virtual void UpdateInterfacePublishing() = 0;
};

#endif // I_MODULE_H
