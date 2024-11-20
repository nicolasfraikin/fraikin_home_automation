import os

from fraikin_home_automation.deployment.raspberry_pi_home_base.replaceall_symlinks import replace_symlinks_with_files

base_path = "/home/nico/dev/bazel-out/k8-fastbuild/bin/fraikin_home_automation/deployment/raspberry_pi_home_base"

# print("#### DELETE OLD FOLDERS ####")
# os.system('rm -rf ' + base_path + "/*")
# print("#### BUILD TARGET ####")
# os.system(
#     'cd /home/nico/dev && bazel build //fraikin_home_automation/deployment/raspberry_pi_home_base:raspi_deployment')

replace_symlinks_with_files(base_path)

print("#### KILL PROCESS ####")
os.system('ssh nicolasfraikin@raspberrypi.local "killall screen"')
print("#### REMOVE OLD FILES ####")
os.system('ssh nicolasfraikin@raspberrypi.local "cd /home/nicolasfraikin && rm -rf raspi_home/*"')
print("#### TRANSFER DATA ####")
os.system(
    "scp " + os.path.join(base_path,
                          "raspi_deployment.py") + " nicolasfraikin@raspberrypi.local:/home/nicolasfraikin/raspi_home")

os.system(
    "scp -r " + os.path.join(base_path, "deploy_raspi_deployment.runfiles", "_main",
                             "fraikin_home_automation") + " nicolasfraikin@raspberrypi.local:/home/nicolasfraikin/raspi_home")
print("#### RESTART PROCESS ####")
os.system(
    'ssh nicolasfraikin@raspberrypi.local ' + '"screen -L -Logfile ~/raspi_home/event_log.log -S Fraikin_Home_Automation -dm bash -c ' + "'source /etc/profile; cd /home/nicolasfraikin/raspi_home; python3 raspi_deployment.py; sleep 200'" + ' "logfile flush 0^M""')
