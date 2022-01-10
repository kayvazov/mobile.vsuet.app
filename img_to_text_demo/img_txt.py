import cv2
import statistics
import easyocr
import sys
import os
import json
import copy

os.chdir(os.path.dirname(__file__))
current_dir = os.getcwd()

os.makedirs(current_dir + "/JSON", exist_ok = True)

DIR = current_dir + '/images/'

reader = easyocr.Reader(['ru'])

main_array = []

def reading_text():
   for filename in os.listdir(current_dir + '/images'):
      dict_for_img = {}
      all_text = []
      all_prob = []
      
      img = cv2.imread(DIR + filename)

      img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
      
      result = reader.readtext(img)
      
      for (bbox, text, prob) in result:
         all_text.append(text)
         all_prob.append(prob)
      
      dict_for_img['path'] = 'images/' + filename
      dict_for_img['text'] = all_text
      dict_for_img['probability'] = statistics.mean(all_prob)
      
      main_array.append(copy.copy(dict_for_img))

def create_json():
    path_for_json = current_dir + "/JSON/text.json"
    with open(path_for_json, "w", encoding='utf-8') as file:
        json.dump(main_array, file, ensure_ascii=False, indent=4)

reading_text()
create_json()
print('end')