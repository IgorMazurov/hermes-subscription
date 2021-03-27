# integration-hermes-subscription

## Сборка и деплой integration-hermes-subscription
### Системные требования

- OpenJDK 8+
- maven 3.6.0+

### Сборка проекта

Собирается из корневой папки командой:

```shell
mvn clean package
```

###Состав контейнера

Для развертывания бэкенда надо добавить файл ```integration/target/integration-hermes-subscription.jar``` в контейнер и пробросить порт 8080.

###Запуск контейнера

Соответственно запуск производится командой


```shell
java -jar -Dspring.profiles.active=openshift integration-hermes-subscription.jar
```


Для проверки запуска можно зайти на http(s)://{host}:{port}/swagger-ui.html (откроется swagger)

## Переменные среды

Для запуска приложения необходимо указать следующие переменные среды:

- `FLUENTD_HOST` - хост fluentd
- `FLUENTD_PORT` - порт fluentd
- `APP_LOGGING_LEVEL` - уровень логгирования
- `SOAP_CONNECTION_TIMEOUT` - soap connection timeout
- `SOAP_READ_TIMEOUT` - soap read timeout

Подразумевается использования config map с названием `integration-hermes-subscription`
