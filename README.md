
<h2>Шаги для установки:</h2><br>
1.Клонирование приложения<br>
`git clone https://github.com/564522as/bank-service`<br>
2.Создание PostgreSQL базы данных<br>
`create database bank-service`<br>
  - выполнить sql код в файле `src/main/resources/bankservice.sql`<br>
3.Заполнить имя пользователя и пароль в PostgreSQL<br>
  - открыть `src/main/resources/application.properties`<br>
  - заполнить spring.datasource.username и spring.datasource.password в соответствии с именем пользователя и его паролем в вашем PostgreSQL<br>
4.Запустить приложение с использованием maven<br>
- mvn spring-boot:run
Приложение запустится на локальном хосте - "http://localhost:8080"<br><br>

<p>Api предполагает аутентификацию с помощью jwt токенов.
Для получения такого токена необходимо отправить post запросом email 
и пароль. Если они валидные, то программа вернет ответ с jwt токеном,
который можно использовать для последующих запросов.</p>

<p>Полное описание api - http://localhost:8080/swagger-ui.html</p>
