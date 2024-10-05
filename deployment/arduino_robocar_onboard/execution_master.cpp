/*
 * Blink
 * Turns on an LED on for one second,
 * then off for one second, repeatedly.
 */

#include "blink/blink.h"

Blink blink{};

void setup() { blink.Init(); }

void loop() { blink.Step(); }