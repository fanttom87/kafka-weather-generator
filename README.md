## Задание

Лето! а погода - то ноябрь, то жара. Не всем это нравится. Но страна у нас большая. Маша
работает над приложением для комфортного путешествия и при этом любит интересные новые
места. Ей пришла в голову мысль получать данные о метеорологии в текущем времени и
выбирать направление для путешествия прямо в текущий момент.
Подключаемся к метеорологическим станциям, которые дают информацию о погоде.
1. Создать продюсер - отправляющий сообщение в топик Kafka и рассказывающий про погоду.
Данные о погоде генерировать случайным образом (формат сообщения можно определить
самостоятельно. Например, температура от 0 до 35 градусов и состояние "солнечно", "облачно",
"дождь", город, дата)
Создать класс WeatherProducer, который будет отправлять сообщения в Kafka. Топик (название
можно любое, но допустим "weather") в который пишется эта информация будет иметь несколько
консьюмеров - но это не важно, важно отправить эти данные
Предложение: в топик отправляется информация по разным городам за 1 неделю (сутки можно
"сократить" до пяти минут или одной минуты или пары секунд, писать реальную неделю не
обязательно, можно договориться что в сообщении будет дата - не только таймстемп отправки
сообщения но и дата события - когда была зафиксирована конкретная погода)
Петя был рад такой идее, и он хочет собирать статистику - количество солнечных дней в Магадане
и на Чукотке, количество дождей в Питере, когда можно идти за грибами в Тюмени (несколько
дождливых дней) - по этой аналитике он делает свое приложение. Поэтому он попросил
подключения к топику и собирает информацию
2. Создать класс WeatherConsumer, который будет получать сообщения из Kafka. Будет собирать и
консолидировать данные и как-то их суммировать, и обобщать (здесь жестких ограничений нет,
аналитика в данном случае на ваше усмотрение. Предлагается такой вариант: собирать данные
вида "самое большое количество дождливых дней за
эту неделю в городе Н, самая жаркая погода была 10 июля в городе К, самая низкая средняя
температура в городе Ч.")


3. Создание продюсера:
 – Создать класс WeatherProducer, который будет отправлять сообщения в Kafka.
 – Реализовать метод, который генерирует случайные данные о погоде (например, температура
от 0 до 35 градусов и состояние "солнечно", "облачно", "дождь").
 – Использовать KafkaProducer для отправки сообщений в заданную тему (например, weathertopic).
4. Создание консюмера:
 – Создать класс WeatherConsumer, который будет получать сообщения из Kafka.
 – Реализовать метод, который подписывается на тему weather-topic и выводит полученные
сообщения на экран.
 – Использовать KafkaConsumer для получения сообщений.
5. Запуск приложения:
 – Запустить консюмера, чтобы он начал слушать сообщения.
 – Запустить продюсера, который будет периодически отправлять сообщения о погоде
(например, каждые 2 секунды).

## Установка и запуск
### 1. Скопируйте репозиторий
```bash
git clone https://github.com/fanttom87/kafka-t1-homework.git
```

### 2. Перейдите в корневую папку проекта

### 3. Запустите проект через Docker
```bash
docker compose up
```

## Итог работы
![image](https://github.com/fanttom87/kafka-t1-homework/blob/master/testing.jpg?raw=true)
