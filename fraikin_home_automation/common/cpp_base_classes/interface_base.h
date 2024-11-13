//
// Created by nico on 10/2/24.
//

#ifndef I_INTERFACE_H
#define I_INTERFACE_H

template <typename Interface, typename DataType>
class InterfaceBase {

  public:
    static void SetData(const DataType& data) { GetDataRaw() = data; }
    static const DataType& GetData() { return GetDataRaw(); }

  private:
    InterfaceBase(){};
    InterfaceBase(const InterfaceBase&) = delete;
    InterfaceBase& operator=(const InterfaceBase&) = delete;

    static DataType& GetDataRaw() {
      static DataType data{};
      return data;
    }
};

#endif // I_INTERFACE_H
