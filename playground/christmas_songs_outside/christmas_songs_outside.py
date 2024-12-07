from gpiozero import DistanceSensor
ultrasonic = DistanceSensor(echo=17, trigger=4, max_distance=2)
while True:
    print(ultrasonic.distance * 100 + " cm")