import serial
import time

ser = serial.Serial('/dev/ttyUSB0', 250000, timeout=0.1)
while True:
    line_read = ser.readline().decode('utf-8').rstrip()
    print(line_read)
    time.sleep(1)
