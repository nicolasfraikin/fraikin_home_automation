import requests
import time

BASE_URL = "https://eu-api.coolkit.cc:8080/api/user/device/"
AUTH_TOKEN = "2f6ca70e-c943-4903-8eef-95049c8e8aab"
DEVICE_ID = "1000b0f22f"


# Toggle device state
def toggle_device(state):
    url = f"{BASE_URL}{DEVICE_ID}/status"
    headers = {"Authorization": f"Bearer {AUTH_TOKEN}"}
    data = {"switch": state}  # "on" or "off"
    response = requests.post(url, json=data, headers=headers)
    print(response.json())


# Example usage
toggle_device("on")
time.sleep(2)
toggle_device("off")
