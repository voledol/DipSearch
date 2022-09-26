# DipSearch
![Java](https://img.shields.io/badge/-Java-0a0a0a?style=for-the-badge&logo=Java) ![Spring](https://img.shields.io/badge/-Spring-0a0a0a?style=for-the-badge&logo=Spring)
<br/>

>Этот проект предназначен для индексации и поиска по указанным в файле настроек сайте.

## Содержание
* Общая информация
* Технологии
* В будущем
* Статус разработки
* Цели проекта
* Контакты

## Общая информация
Данный проект разработан для осуществления поиска ,по указанным в файле конфигурации, сайтам

Проект включает следующие сервисы:
* полная индексация сайтов указанных в файле конфигурации
* индексация по одной странице в пределах указанных сайтов
* поиск по всем сайтом, или отдельно по выбранному сайту
* вывод статистики по проиндексированным сайтам


Для запуска проекта необходимо с помощью командной строки перейти перейти в дирректорию расположения исполняемого файла, и выполнить команду: 

`java -jar  DipSearch-1.0.jar`
<br/>
Предварительно необходимо отредактировать с помощью Блокнота файл application.yaml. 
В данном файле необходимо указать ссылку для подключения к базе данных, например:
`url: jdbc:mysql://127.0.0.1:3306/search_engine?useSSL=false&serverTimezone=UTC` 
дале следует указать логин и пароль для доступа к базе данных:
`username: *username*
password: *password*`

Далее ниже строки "site:" необходимо указать список желаемых для индексации сайтов в формате:
`- url: *ссылка на главную страницу сайта в формате  https://SiteName.domainName/*
name: "имя сайта. любое по желанию"`
При перечислении соблюдайте стандарт форматирования документа.

Проект запускается через порт 8080, перед запуском убедитесь что порт не занят другим процессом

<br/>

## Технологии
* Java - version 11
* Spring Boot - version 2.1.4
* jsoup 1.15.3
* javax.xml 2.4.0
* hibernate 5.2.1
* lombok 1.18.24
* json version 20220320
* fasterxml 2.13.3
* lucene.morphology 
## Пример кода
### Запрос
```java
GET http://localhost:8080/statistics
@GetMapping ("/statistics")
@ResponseBody
public ResponseEntity<ResultStatisticDto> statistic () {
siteService.saveSitesIfNotExist();
Main.availableSites = siteService.getAllSites();
System.out.println(Main.availableSites.get(0).getId());
return dbStatisticService.getStatistic();
}
```
####При загрузке главной страницы обновляет список сайтов баже данных, а так же выводит на главной странице полную и детальную статистику по сайтам:
 ![MainPage](https://github.com/voledol/DipSearch/blob/9a2bcee30b78fcca3625feb831cc8bfdfdbcb1e8/MainPage.png)

## В будущем
* Разработка авторизации
* Переиндексация по одному сайту
* Возможность поиска по сайту во время переиндексации

## Статус
Project is: _finished_

## Цели проекта
Проект выполнен в целях изучения java 

## Контакт
Created by [Vladimir Galtsev](https://mark1708.github.io/) - feel free to contact me!
#### +7(920)686-15-73 | voledoler@yandex.ru | [github](https://github.com/voledol)

![Readme Card](https://github-readme-stats.vercel.app/api/pin/?username=mark1708&repo=spring-boot-rabbitmq-example&theme=chartreuse-dark&show_icons=true)
