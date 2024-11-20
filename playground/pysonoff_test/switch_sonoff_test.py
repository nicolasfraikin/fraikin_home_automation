from sonoff import Sonoff

sonoff_obj = Sonoff('nicolas.fraikin3@gmail.com', 'Gangster123!', 'eu')

sonoff_obj.do_login()

sonoff_obj.switch('on', '1000b0f22f')
sonoff_obj.switch('on', '1000a5ffcd')  # Dishwaser
print("DONE")
