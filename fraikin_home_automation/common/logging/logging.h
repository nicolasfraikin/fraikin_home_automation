//
// Created by nico on 10/2/24.
//

#ifndef LOGGING_H
#define LOGGING_H

#ifdef NON_TARGET_BUILD
#include <iostream>
#else
#include <Arduino.h>
#endif

#ifdef NON_TARGET_BUILD
inline void Log(const std::string& log_message) { std::cout << log_message << std::endl; }
#else
inline void Log(const String& log_message) { Serial.println(log_message); }
#endif

#endif // LOGGING_H
