import sys
from tkinter import Tk
from tkinter.filedialog import askopenfilename

import cv2
import numpy as np
from matplotlib import pyplot as plt
from scipy import signal

# вызов проводника для выбора файлов
Tk().withdraw()
# определяем форматы отображаемых файлов для открытия (можно указать и другие)
filename = askopenfilename(
    filetypes=[
        ('image files', '.png'),
        ('image files', '.jpg'),
        ('image files', '*.jpeg'),
        ('image files', '*.webp'),
        ('image files', '*.tiff'),
    ])
# считываем выбранный файл в N-мерный массив
original_photo = cv2.imread(filename)
# проверка на успешность загрузки фотографии
if original_photo is None:
    print("Ошибка загрузки фотографии", file=sys.stderr)
    sys.exit(1)

height, width, channels = original_photo.shape
if height <= width:
    original_photo = original_photo[0:height, 0:height, 2]
else:
    original_photo = original_photo[0:width, 0:width, 2]

# применение пуассоновского шума
poisson_noise_added_photo = np.random.poisson(original_photo)
# если выходит за динамический диапазон, то устанавливаем мин и макс возможное занчение
poisson_noise_added_photo[poisson_noise_added_photo < 0] = 0
poisson_noise_added_photo[poisson_noise_added_photo > 255] = 255

# удаляем помехи при помощи винеровской фильтрации
# wiener_filtered_photo = signal.wiener(poisson_noise_added_photo, (5, 5), 300)
wiener_filtered_photo = signal.wiener(poisson_noise_added_photo, (5,5), 10)

# отображение исходных и обработанных изображений
plt.figure(figsize=(13, 5))
# для расположения в ряд
plt.subplot(131)
plt.imshow(original_photo, cmap=plt.cm.gray)
# убираем оси
plt.axis('off')
plt.title('Оригинальное изображение')

plt.subplot(232)
plt.imshow(poisson_noise_added_photo, cmap=plt.cm.gray)
plt.title('C Пуассоновским шумом')
plt.axis('off')


plt.subplot(333)
plt.imshow(wiener_filtered_photo, cmap=plt.cm.gray)
plt.axis('off')
plt.title('После фильтра Винера')

plt.show()
