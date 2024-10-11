//
// Created by nico on 10/2/24.
//

#ifndef I_INTERFACE_H
#define I_INTERFACE_H

template <typename Interface, typename DataType>
class InterfaceBase {

  public:
    static InterfaceBase* GetInstance() {
      static InterfaceBase<Interface, DataType>* instance;
      return instance;
    };

    void SetData(const DataType& data) { data_ = data; }
    void GetData(DataType& data) const { data = data_; }

  private:
    InterfaceBase(){};
    InterfaceBase(const InterfaceBase&) = delete;
    InterfaceBase& operator=(const InterfaceBase&) = delete;

    static InterfaceBase* instance;
    DataType data_{};
};

#endif // I_INTERFACE_H
