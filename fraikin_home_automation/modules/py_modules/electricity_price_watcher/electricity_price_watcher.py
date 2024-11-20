import datetime
import os
from fraikin_home_automation.communication.interfaces.electricity_prices_interface import ElectricityPricesInterface, \
    ElectricityPricesInterfaceDataType
from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.by import By

from fraikin_home_automation.common.py_base_classes.i_module import IModule

ANDEL_ENERGI_LINK = "https://andelenergi.dk/el/timepris/"


class ElectricityPriceWatcher(IModule):
    def __init__(self):
        self.electricity_prices = ElectricityPricesInterfaceDataType()

    def get_cycle_time(self):
        return 1800

    def init(self):
        pass

    def step(self):
        print("STEP ELECTRICITY WATCHER!!!")
        self.update_elctricity_prices()

    def update_elctricity_prices(self):
        current_time = datetime.datetime.now()
        price_info_from_html = self.extract_price_info()
        days, times, values = self.get_days_times_and_prices(price_info_from_html)
        days, times, values = self.get_info_from_current_time_onwards(current_time, days, times, values)
        string_formatted_time_price_info = self.get_string_formatted_time_price_info(current_time, days, times, values)
        self.write_to_interface(string_formatted_time_price_info, current_time)

    def update_interface_subscription(self):
        pass

    def update_interface_publishing(self):
        ElectricityPricesInterface.get_instance().set_data(self.electricity_prices)

    def extract_price_info(self):
        chrome_options = Options()
        chrome_options.add_argument("--headless")
        if "RASPI_DEPLOYMENT" in os.environ.keys():
            service = Service('/usr/bin/chromedriver')
            driver = webdriver.Chrome(service=service, options=chrome_options)
        else:
            driver = webdriver.Chrome(options=chrome_options)

        driver.get(ANDEL_ENERGI_LINK)  # Browser goes to google.com

        element = driver.find_element(By.ID, "chart-component")
        price_info_from_html = element.get_attribute("data-chart")
        print("GOT THE PRICE INFO")
        return price_info_from_html

    def get_days_times_and_prices(self, price_info_from_html):
        dates = price_info_from_html.split('"dates":[')[1].split("]")[0]
        days_split = dates.split("day")
        days = []
        for day_split in days_split[1:]:
            days.append(int(day_split.split("}")[0].replace(":", "").replace("'", "").replace('"', "")))

        times = price_info_from_html.split('"west":{"labels":[')[1].split("]")[0]
        times = [int(t.replace('"', "")) for t in times.split(",")]
        values = price_info_from_html.split('"west":{"labels":[')[1].split('"values":[')[1].split("]")[0]
        values = [float(t.replace('"', "")) for t in values.split(",")]
        values_distribution = price_info_from_html.split('"valuesDistribution":[')[1].split("]")[0]
        values_distribution = [float(t.replace('"', "")) for t in values_distribution.split(",")]
        values = [round(value + distribution, 2) for value, distribution in zip(values, values_distribution)]
        return days, times, values

    def get_info_from_current_time_onwards(self, current_time, days, times, values):
        day_index = 0
        days_long = []
        first = True
        for t in times:
            days_long.append(days[day_index])
            if t == 0 and not first:
                day_index += 1
            first = False

        index = -1
        for day, t in zip(days_long, times):
            index += 1
            if day == current_time.day and t == current_time.hour:
                break

        days_long = days_long[index:]
        times = times[index:]
        values = values[index:]

        temp_list = [[v, d, t] for v, d, t in sorted(zip(values, days_long, times))]
        values = [x[0] for x in temp_list]
        days_long = [x[1] for x in temp_list]
        times = [x[2] for x in temp_list]

        return days_long, times, values

    def get_string_formatted_time_price_info(self, current_time, days, times, values):
        string_dates = []
        current_day = current_time.day
        for day, t, price in zip(days, times, values):
            if day == current_day and t >= 1:
                string_date = "Today, "
            else:
                string_date = "Tomorrow, "
            string_date += str(t) + ":00 - " + str(t + 1 if t != 23 else "0") + ":00 (" + str(price) + " DKK)"
            string_dates.append(string_date)
        return string_dates

    def write_to_interface(self, string_formatted_time_price_info, current_time):
        self.electricity_prices.cheapest_prices = ";".join(string_formatted_time_price_info)
        self.electricity_prices.update_time = current_time.strftime("%Y-%m-%d %H:%M:%S")
        print("CHEAPEST ELECTRICITY PRICES = " + str(self.electricity_prices.cheapest_prices))
