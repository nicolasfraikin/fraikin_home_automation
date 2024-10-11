/*
 * Blink
 * Turns on an LED on for one second,
 * then off for one second, repeatedly.
 */

#include "blink2.h"

// Set LED_BUILTIN if it is not defined by Arduino framework
#ifndef LED_BUILTIN
#define LED_BUILTIN 2
#endif

void Blink2::UpdateInterfaceSubscription() {}
void Blink2::UpdateInterfacePublishing() {
  TestInterface::DataType data{};
  data.test_bool = true;
  TestInterface::GetInstance()->SetData(data);
}

void Blink2::Init() { pinMode(LED_BUILTIN, OUTPUT); }

void Blink2::Step() {
  // turn the LED on (HIGH is the voltage level)
  digitalWrite(LED_BUILTIN, HIGH);
  // wait for a second
  delay(1500);
  // turn the LED off by making the voltage LOW
  digitalWrite(LED_BUILTIN, LOW);
  // wait for a second
  delay(1500);
}