from bs4 import BeautifulSoup
import requests
import os
import sys
import time
import traceback
import json


all_names = []
name_mini = {}


os.chdir(os.path.dirname(__file__))
currentDir = os.getcwd()

if not os.path.exists(currentDir + "/images"):
    os.makedirs(currentDir + "/images")

if not os.path.exists(currentDir + "/images" + "/teachers"):
    os.makedirs(currentDir + "/images" + "/teachers")

if not os.path.exists(currentDir + "/JSON"):
    os.makedirs(currentDir + "/JSON")


VSUET_SITE = "https://vsuet.ru"
ZADERJKA = 2


def get_all_caf():
    r = requests.get("https://vsuet.ru/obuchenie/chairs")
    soup = BeautifulSoup(r.content, features="html.parser")

    r_api = requests.get("https://vsuet.app/api/schedule/teachers")
    soup_api = BeautifulSoup(r_api.content, features="html.parser")
    api_all_prepod = soup_api.get_text()
    r_api.close()

    div = soup.find('div', {'itemprop': 'articleBody'})

    cafdr = []
    children = div.find_all("a")
    r.close()
    for child in children:
        cafdr.append(VSUET_SITE + child.get("href"))
    
    for i in range(len(cafdr)):
        print("N of cycle - ", i)
        time.sleep(ZADERJKA)

        if i == 2 or i == 10:
            print("По идее тут должен закончиться")
            # DODELAT


            continue
        
        r = requests.get(cafdr[i])
        soup = BeautifulSoup(r.content, features="html.parser")

        a1 = soup.find("ul", {"class": "aside__menu nav flex-column"})
        a2 = a1.select_one(":nth-child(2)")
        a_str = VSUET_SITE + a2.find("a", {"class": "nav-link"}).get("href")
        sotr_k(a_str, api_all_prepod)
        r.close()



def sotr_k(a_str, api_all_prepod):
    print("Ссылка - ", a_str)
    r = requests.get(a_str)
    soup_sotr = BeautifulSoup(r.content, features="html.parser")
    block_w_img = soup_sotr.find('div', {'class': 'content cat_chair staff'})
    alls = soup_sotr.find_all("a", {"class": "person-name"})
    count_for_img = -1

    for b in alls:
        time.sleep(ZADERJKA)
        count_for_img += 1
        b_str = b.get_text()
        name_un = str(' '.join(b_str.split()))
        print("Имя - ", name_un)
        name_for_api = name_un[:]
        name_changer(name_for_api, api_all_prepod, count_for_img, block_w_img)



def find_img(count_for_img, block_w_img):
    a_img = block_w_img.select('img')[count_for_img]
    link = VSUET_SITE + "/" + a_img['src']

    name_ind = str(link).rfind("/")
    name_img = str(link)[name_ind + 1:]


    cr_path = currentDir + "/images" + "/teachers" + str("/" + name_img[:name_img.index(".")])
    cr_pa = "/images" + "/teachers" + str("/" + name_img[:name_img.index(".")]) + "/" + name_img
    papka_w_img = currentDir + "/images" + "/teachers" + str("/" + name_img[:name_img.index(".")]) + "/" + name_img

    if not os.path.exists(cr_path):
        os.makedirs(cr_path)

    with open(papka_w_img, "wb") as file:
        im = requests.get(link)
        file.write(im.content)
        im.close()
    
    name_mini["image"] = cr_pa
    print("Dict - ", name_mini)
    all_names.append(name_mini.copy())

    

def name_changer(name_for_api, api_all_prepod, count_for_img, block_w_img):
    name_for_api = name_for_api.split()

    name_for_api_ch = name_for_api[0].lower().capitalize() + " " + name_for_api[1][0] + "." + name_for_api[2][0] + "."
    print("Измененное - ", name_for_api_ch)


    if name_for_api_ch in api_all_prepod:
        print("Yes")
        name_mini["shortName"] = name_for_api_ch
        name_mini["fullRuName"] = ' '.join(name_for_api[0:3]).lower().title()
        name_mini["fullEnName"] = ' '.join(name_for_api[-3:])

        find_img(count_for_img, block_w_img)
    else:
        print("NO")




get_all_caf()




cr_path = currentDir + "/JSON" + str("/" + "teachers.json")
with open(cr_path, "w", encoding='utf-8') as file:
    json.dump(all_names, file, ensure_ascii=False, indent=4)


print("end")