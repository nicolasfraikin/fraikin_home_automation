# Fraikin Home Automation

## Overview
This repository includes several smart home functionalities to control e.g. washing machine, dishwasher, vacuum robot, lights at my home.
Smart home functionalities include:
- Schedule a dishwasher, washing machine or dryer run based on chossing the cheapest electricity prices
- Make my vacuum robot start cleaning as soon as all family members are out of the house

The Smart Home framework is based on an Android App and a raspberry pi.

Furthermore, this repository includes code to control a self-built RC plane (still WIP). Therefore an arduino serves as a RF transmitter taking input signals via bluetooth from the Fraikin Home Automation Android App and then forwards the signals to the flight controller arduino.

All source code written in Java, C++ and Python is managed by Bazel as a build system- Code generation is used to ensure communication across all platforms.
