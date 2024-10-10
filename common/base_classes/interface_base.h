//
// Created by nico on 10/2/24.
//

#ifndef I_INTERFACE_H
#define I_INTERFACE_H
#include <functional>
#include <vector>

template <typename Interface, typename DataType>
class InterfaceBase {
    using SubsriberCallback = std::function<void(const DataType&)>;
    using PublisherCallback = std::function<DataType(void)>;

  public:
    static DataType GetData() {
      DataType data_{};
      return data_;
    }

    static void AddSubscriberCallback(const SubsriberCallback& subsriber_callback) {
      GetSubscriberCallbacks().emplace_back(subsriber_callback);
    }

    static void AddPublisherCallback(const PublisherCallback& publisher_callback) {
      GetPublisherCallback() = publisher_callback;
    }

    static void CallSubscriberCallbacks() {
      for (auto& callback : GetSubscriberCallbacks()) {
        callback(GetData());
      }
    }

  private:
    static std::vector<SubsriberCallback>& GetSubscriberCallbacks() {
      static std::vector<SubsriberCallback> subscriber_callbacks{};
      return subscriber_callbacks;
    }
    static PublisherCallback& GetPublisherCallback() {
      static std::function<PublisherCallback> publisher_callback{};
      return publisher_callback;
    }
};

#endif // I_INTERFACE_H
