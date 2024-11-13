/*
 * Blink
 * Turns on an LED on for one second,
 * then off for one second, repeatedly.
 */

#include "blink.h"

// Set LED_BUILTIN if it is not defined by Arduino framework
#ifndef LED_BUILTIN
#define LED_BUILTIN 2
#endif

Blink::Blink() : test_interface_object_{} {}

void Blink::UpdateInterfaceSubscription() {
  ReceiveInterfaceData<TestInterface>(test_interface_object_);
  ReceiveInterfaceData<Test1Interface>(test_interface_1_object_);
}
void Blink::UpdateInterfacePublishing() {}

void Blink::Init() { pinMode(LED_BUILTIN, OUTPUT); }

void Blink::Step() {
  Log("Arduino : Uint8 value received from pi is " + String(test_interface_1_object_.test_uint8));
  // turn the LED on (HIGH is the voltage level)
  digitalWrite(LED_BUILTIN, HIGH);
  // wait for a second
  delay(50);
  // turn the LED off by making the voltage LOW
  digitalWrite(LED_BUILTIN, LOW);
  // wait for a second
  delay(50);
}