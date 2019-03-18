# TinkoffFintech-HomeWork-2
Дз по лекции 5
***
Программа создает два файла в папке resources и заносит данные пользователей в баху данных, один файл с расширением .xls(excel), другой .pdf. Если нет интернет соединения, то производится проверка наналичие данных в базе данных. Если данные будут, то будет производиться генерация из базы данных, если нет, то генерация на основе данных в папке resources.  
Данные людей полностью случайны, любое совпадение случайно.  
Чтобы запустить программу на операционной системе Windows, необходимо иметь установленную **java** и **maven**, так же необходимо иметь прописанные переменные среды  
Для запуска на других операционных система необходимо иметь тоже самое, что и для Windows, запуск не отличается.  
Конфигурационный файл для подключение к базе данных находится по пути \src\resources\config.properties  
Файл содердит в себе 3 строки:  
db.host - строка подключения к базе данных  
db.login - логин  
db.pass - пароль  
Для запуска:  
1) Откройте командную строку  
2) Введите команду для перехода в папку с проектом  
3) Далее введите команду:  
- **mvn compile**  
4) Дождитесь появление в консоли надписи "Build success"  
5) Введите команду:  
- **mvn exec:java**  
После чего будет произведен запуск программы  
