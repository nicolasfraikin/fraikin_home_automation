import serial
import time
import sys

ser = serial.Serial('/dev/ttyUSB1', 250000)
while True:
    try:
        line_read = ser.readline().decode('utf-8').rstrip()
        print(line_read)
    except KeyboardInterrupt:
        sys.exit()
    except:
        continue
