package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Contact;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;

public class ResumeTestData {
    public static void main(String[] args) {

        Resume resume = new Resume("Григорий Кислин");
        resume.setContact(new Contact(ContactType.TELEPHONE, "+7(921) 855-0482"));
        resume.setContact(new Contact(ContactType.EMAIL, "gkislin@yandex.ru"));
        resume.setContact(new Contact(ContactType.SKYPE, "grigory.kislin"));
        resume.setContact(new Contact(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin"));
        resume.setContact(new Contact(ContactType.GITHUB, "https://github.com/gkislin"));
        resume.setContact(new Contact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473"));
        resume.setContact(new Contact(ContactType.WEBLINK, "http://gkislin.ru/"));

        resume.addObjective("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        resume.addPersonal("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        resume.addAchievement("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). " +
                "Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. " +
                "Более 1000 выпускников.");
        resume.addAchievement("Реализация двухфакторной аутентификации для онлайн платформы управления " +
                "проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        resume.addAchievement("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. " +
                "Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: " +
                "Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, " +
                "интеграция CIFS/SMB java сервера.");
        resume.addAchievement("Реализация c нуля Rich Internet Application приложения на стеке технологий " +
                "JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        resume.addAchievement("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных " +
                "сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о " +
                "состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования " +
                "и мониторинга системы по JMX (Jython/ Django).");
        resume.addAchievement("Реализация протоколов по приему платежей всех основных платежных системы России " +
                "(Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");


        resume.addQualification("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        resume.addQualification("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        resume.addQualification("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");
        resume.addQualification("MySQL, SQLite, MS SQL, HSQLDB");
        resume.addQualification("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,");
        resume.addQualification("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,");
        resume.addQualification("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, " +
                "Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, " +
                "ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        resume.addQualification("Python: Django.");
        resume.addQualification("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        resume.addQualification("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        resume.addQualification("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, " +
                "StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, " +
                "LDAP, OAuth1, OAuth2, JWT.");
        resume.addQualification("Инструменты: Maven + plugin development, Gradle, настройка Ngnix,");
        resume.addQualification("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, " +
                "Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.");
        resume.addQualification("Отличное знание и опыт применения концепций ООП, SOA, шаблонов " +
                "проектрирования, архитектурных шаблонов, UML, функционального программирования");
        resume.addQualification("Родной русский, английский \"upper intermediate\"");


        resume.addEducation("Coursera", "https://www.coursera.org/course/progfun",
                3, 2013, 5, 2013, "\"Functional Programming Principles in Scala\" by " +
                        "Martin Odersky");
        resume.addEducation("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366",
                3, 2011, 4, 2011, "Курс \"Объектно-ориентированный анализ ИС. " +
                        "Концептуальное моделирование на UML.\"");
        resume.addEducation("Siemens AG", "http://www.siemens.ru/",
                1, 2005, 4, 2005, "3 месяца обучения мобильным IN сетям (Берлин)");
        resume.addEducation("Alcatel", "http://www.alcatel.ru/",
                9, 1997, 3, 1998, "6 месяцев обучения цифровым телефонным сетям (Москва)");
        resume.addEducation("Санкт-Петербургский национальный исследовательский университет информационных " +
                        "технологий, механики и оптики", "http://www.ifmo.ru/",
                9, 1993, 7, 1996, "Аспирантура (программист С, С++)");
        resume.addEducation("Санкт-Петербургский национальный исследовательский университет информационных " +
                        "технологий, механики и оптики", "http://www.school.mipt.ru/",
                9, 1987, 7, 1993, "Инженер (программист Fortran, C)");
        resume.addEducation("Заочная физико-техническая школа при МФТИ", "http://www.school.mipt.ru/",
                9, 1984, 6, 1987, "Закончил с отличием");


        resume.addExperience("Java Online Projects", "http://javaops.ru/", 10, 2013, 12, 2099,
                "Автор проекта.",
                "Создание, организация и проведение Java онлайн проектов и стажировок.");
        resume.addExperience("Wrike", "https://www.wrike.com/", 10, 2014, 1, 2016,
                "Старший разработчик ",
                "(backend) Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, " +
                        "Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация " +
                        "по OAuth1, OAuth2, JWT SSO.");
        resume.addExperience("RIT Center", null, 4, 2012, 10, 2014,
                "Java архитектор ",
                "Организация процесса разработки системы ERP для разных окружений: релизная политика, " +
                        "версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы " +
                        "(pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных " +
                        "сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html)." +
                        " Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin " +
                        "development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, " +
                        "Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python");
        resume.addExperience("Luxoft (Deutsche Bank)", "http://www.luxoft.ru/", 12, 2010, 4, 2012,
                "Ведущий программист",
                "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, " +
                        "Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. " +
                        "Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области " +
                        "алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, " +
                        "Commet, HTML5.");
        resume.addExperience("Yota", "https://www.yota.ru/", 6, 2008, 12, 2010,
                "Ведущий специалист ",
                "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, " +
                        "EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики " +
                        "и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)");
        resume.addExperience("Enkata", "http://enkata.com/", 3, 2007, 6, 2008,
                "Разработчик ПО ",
                "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей " +
                        "кластерного J2EE приложения (OLAP, Data mining).");
        resume.addExperience("Siemens AG", "https://www.siemens.com/ru/ru/home.html", 1, 2005, 2, 2007,
                "Разработчик ПО ",
                "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной " +
                        "IN платформе Siemens @vantage (Java, Unix).");
        resume.addExperience("Alcatel", "http://www.alcatel.ru/", 9, 1997, 1, 2005,
                "Инженер по аппаратному и программному тестированию",
                "Тестирование, отладка, внедрение ПОцифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).");

        System.out.println(resume);
    }
}
