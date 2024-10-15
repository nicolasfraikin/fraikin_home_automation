/*
 * Blink
 * Turns on an LED on for one second,
 * then off for one second, repeatedly.
 */

#include "blink2.h"

void Blink2::UpdateInterfaceSubscription() {}
void Blink2::UpdateInterfacePublishing() {
  data.test_uint8 = local_uint8;
  PublishInterfaceData<TestInterface>(data);
}

void Blink2::Init() {}

void Blink2::Step() {
  local_uint8 += 1;
  Log("Uint8 value BLINK2 is " + String(local_uint8));
}