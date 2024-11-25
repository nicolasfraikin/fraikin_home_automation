from sonoff import Sonoff
import time

sonoff_obj = Sonoff('nicolas.fraikin3@gmail.com', 'Gangster123!', 'eu')

sonoff_obj.do_login()

sonoff_obj.switch('on', '1000b0f22f')
time.sleep(1)
sonoff_obj.switch('on', '1000a5ffcd')  # Dishwasher
print("DONE")
