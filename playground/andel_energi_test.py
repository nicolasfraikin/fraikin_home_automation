import datetime
from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By

ANDEL_ENERGI_LINK = "https://andelenergi.dk/el/timepris/"

chrome_options = Options()
chrome_options.add_argument("--headless")
# chrome_options.add_argument("--disable-gpu")

driver = webdriver.Chrome(options=chrome_options)

driver.get(ANDEL_ENERGI_LINK)  # Browser goes to google.com

element = driver.find_element(By.ID, "chart-component")
price_info = element.get_attribute("data-chart")

dates = price_info.split('"dates":[')[1].split("]")[0]
days_split = dates.split("day")
days = []
for day_split in days_split[1:]:
    days.append(int(day_split.split("}")[0].replace(":", "").replace("'", "").replace('"', "")))

times = price_info.split('"west":{"labels":[')[1].split("]")[0]
times = [int(t.replace('"', "")) for t in times.split(",")]
values = price_info.split('"west":{"labels":[')[1].split('"values":[')[1].split("]")[0]
values = [float(t.replace('"', "")) for t in values.split(",")]
values_distribution = price_info.split('"valuesDistribution":[')[1].split("]")[0]
values_distribution = [float(t.replace('"', "")) for t in values_distribution.split(",")]
values = [value + distribution for value, distribution in zip(values, values_distribution)]

day_index = 0
days_long = []
first = True
for t in times:
    days_long.append(days[day_index])
    if t == 0 and not first:
        day_index += 1
    first = False

current_time = datetime.datetime.now()

index = -1
for day, t in zip(days_long, times):
    index += 1
    if day == current_time.day and t == current_time.hour:
        break

days_long = days_long[index:]
times = times[index:]
values = values[index:]

print(len(days_long))
print(len(times))
print("ENDE")
temp_list = [[v, d, t] for v, d, t in sorted(zip(values, days_long, times))]
print(temp_list)
values = [x[0] for x in temp_list]
days_long = [x[1] for x in temp_list]
times = [x[2] for x in temp_list]

string_dates = []
current_day = current_time.day
for day, t, price in zip(days_long, times, values):
    string_date = ""
    if day == current_day:
        string_date = "Today, "
    else:
        string_date = "Tomorrow, "
    string_date += str(t) + ":00 - " + str(t + 1 if t != 23 else "0") + ":00 (" + str(price) + " DKK)"
    string_dates.append(string_date)
