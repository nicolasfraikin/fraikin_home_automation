//
// Created by nico on 10/5/24.
//

#ifndef ARDUINO_MOCK_H
#define ARDUINO_MOCK_H
#include <stdlib.h>
#include <cstdint>

#define OUTPUT 0x1
#define HIGH 0x1
#define LOW  0x0

void pinMode(uint8_t pin, uint8_t mode) {};
void digitalWrite(uint8_t pin, uint8_t val) {};
void delay(unsigned long ms) {};

#endif //ARDUINO_MOCK_H
