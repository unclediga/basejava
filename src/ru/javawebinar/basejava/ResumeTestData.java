package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.time.Month;

public class ResumeTestData {
    private Resume resume;

    public static void main(String[] args) {

        ResumeTestData resumeTestData = new ResumeTestData();
        Resume resume = resumeTestData.resume;
        ResumeTestData resumeTestData2 = new ResumeTestData();
        Resume resume2 = resumeTestData2.resume;
        OrganizationSection section = (OrganizationSection) resume2.getSection(SectionType.EDUCATION);

        System.out.println(resume);
        System.out.println(resume.hashCode());

        System.out.println(resume2);
        System.out.println(resume2.hashCode());

        System.out.println("equals :" + resume.equals(resume2));
        System.out.println("equals contacts:" + resume.getContacts().equals(resume2.getContacts()));
        System.out.println("equals sections:" + resume.getSections().equals(resume2.getSections()));
        System.out.println("comp sections PERS:" + resume.getSection(SectionType.PERSONAL).equals(resume2.getSection(SectionType.PERSONAL)));
        System.out.println("comp sections EDUC:" + resume.getSection(SectionType.EDUCATION).equals(resume2.getSection(SectionType.EDUCATION)));

        resume2.addContact(ContactType.TELEPHONE, "+7(495)123-45-67");
        section.addSubsection("Siemens AG", "http://www.siemens.ru/",
                2005, Month.JANUARY, 2005, Month.APRIL, "Tester", "3 месяца обучения мобильным IN сетям (Берлин)");

        System.out.println("equals  :" + resume.equals(resume2));
        System.out.println("equals contacts:" + resume.getContacts().equals(resume2.getContacts()));
        System.out.println("equals sections:" + resume.getSections().equals(resume2.getSections()));
        System.out.println("comp sections PERS:" + resume.getSection(SectionType.PERSONAL).equals(resume2.getSection(SectionType.PERSONAL)));
        System.out.println("comp sections EDUC:" + resume.getSection(SectionType.EDUCATION).equals(resume2.getSection(SectionType.EDUCATION)));

    }

    private ResumeTestData() {
        resume = new Resume("Григорий Кислин");
        resume.addContact(ContactType.TELEPHONE, "+7(921) 855-0482");
        resume.addContact(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.addContact(ContactType.SKYPE, "grigory.kislin");
        resume.addContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume.addContact(ContactType.GITHUB, "https://github.com/gkislin");
        resume.addContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        resume.addContact(ContactType.WEBLINK, "http://gkislin.ru/");

        addObjective("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");

        addPersonal("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");

        addAchievement("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). " +
                "Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. " +
                "Более 1000 выпускников.");
        addAchievement("Реализация двухфакторной аутентификации для онлайн платформы управления " +
                "проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        addAchievement("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. " +
                "Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: " +
                "Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, " +
                "интеграция CIFS/SMB java сервера.");
        addAchievement("Реализация c нуля Rich Internet Application приложения на стеке технологий " +
                "JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        addAchievement("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных " +
                "сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о " +
                "состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования " +
                "и мониторинга системы по JMX (Jython/ Django).");
        addAchievement("Реализация протоколов по приему платежей всех основных платежных системы России " +
                "(Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");


        addQualification("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        addQualification("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        addQualification("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");
        addQualification("MySQL, SQLite, MS SQL, HSQLDB");
        addQualification("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,");
        addQualification("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,");
        addQualification("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, " +
                "Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, " +
                "ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        addQualification("Python: Django.");
        addQualification("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        addQualification("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        addQualification("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, " +
                "StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, " +
                "LDAP, OAuth1, OAuth2, JWT.");
        addQualification("Инструменты: Maven + plugin development, Gradle, настройка Ngnix,");
        addQualification("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, " +
                "Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.");
        addQualification("Отличное знание и опыт применения концепций ООП, SOA, шаблонов " +
                "проектрирования, архитектурных шаблонов, UML, функционального программирования");
        addQualification("Родной русский, английский \"upper intermediate\"");


        addEducation("Coursera", "https://www.coursera.org/course/progfun",
                Month.MARCH, 2013, Month.MAY, 2013, "\"Functional Programming Principles in Scala\" by " +
                        "Martin Odersky");
        addEducation("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366",
                Month.MARCH, 2011, Month.APRIL, 2011, "Курс \"Объектно-ориентированный анализ ИС. " +
                        "Концептуальное моделирование на UML.\"");
        addEducation("Siemens AG", "http://www.siemens.ru/",
                Month.JANUARY, 2005, Month.APRIL, 2005, "3 месяца обучения мобильным IN сетям (Берлин)");
        addEducation("Alcatel", "http://www.alcatel.ru/",
                Month.SEPTEMBER, 1997, Month.MARCH, 1998, "6 месяцев обучения цифровым телефонным сетям (Москва)");
        addEducation("Санкт-Петербургский национальный исследовательский университет информационных " +
                        "технологий, механики и оптики", "http://www.ifmo.ru/",
                Month.SEPTEMBER, 1993, Month.JULY, 1996, "Аспирантура (программист С, С++)");
        addEducation("Санкт-Петербургский национальный исследовательский университет информационных " +
                        "технологий, механики и оптики", "http://www.school.mipt.ru/",
                Month.SEPTEMBER, 1987, Month.JULY, 1993, "Инженер (программист Fortran, C)");
        addEducation("Заочная физико-техническая школа при МФТИ", "http://www.school.mipt.ru/",
                Month.SEPTEMBER, 1984, Month.JUNE, 1987, "Закончил с отличием");


        addExperience("Java Online Projects", "http://javaops.ru/", Month.OCTOBER, 2013,
                "Автор проекта.",
                "Создание, организация и проведение Java онлайн проектов и стажировок.");
        addExperience("Wrike", "https://www.wrike.com/", Month.OCTOBER, 2014, Month.JANUARY, 2016,
                "Старший разработчик ",
                "(backend) Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, " +
                        "Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация " +
                        "по OAuth1, OAuth2, JWT SSO.");
        addExperience("RIT Center", null, Month.APRIL, 2012, Month.OCTOBER, 2014,
                "Java архитектор ",
                "Организация процесса разработки системы ERP для разных окружений: релизная политика, " +
                        "версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы " +
                        "(pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных " +
                        "сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html)." +
                        " Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin " +
                        "development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, " +
                        "Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python");
        addExperience("Luxoft (Deutsche Bank)", "http://www.luxoft.ru/", Month.DECEMBER, 2010, Month.APRIL, 2012,
                "Ведущий программист",
                "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, " +
                        "Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. " +
                        "Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области " +
                        "алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, " +
                        "Commet, HTML5.");
        addExperience("Yota", "https://www.yota.ru/", Month.JUNE, 2008, Month.DECEMBER, 2010,
                "Ведущий специалист ",
                "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, " +
                        "EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики " +
                        "и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)");
        addExperience("Enkata", "http://enkata.com/", Month.MARCH, 2007, Month.JUNE, 2008,
                "Разработчик ПО ",
                "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей " +
                        "кластерного J2EE приложения (OLAP, Data mining).");
        addExperience("Siemens AG", "https://www.siemens.com/ru/ru/home.html", Month.JANUARY, 2005, Month.FEBRUARY, 2007,
                "Разработчик ПО ",
                "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной " +
                        "IN платформе Siemens @vantage (Java, Unix).");
        addExperience("Alcatel", "http://www.alcatel.ru/", Month.SEPTEMBER, 1997, Month.JANUARY, 2005,
                "Инженер по аппаратному и программному тестированию",
                "Тестирование, отладка, внедрение ПОцифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).");
    }

    private AbstractSection getExistOrCreateSection(SectionType sectionType) {
        AbstractSection section = resume.getSection(sectionType);
        if (section == null) {
            switch (sectionType) {
                case PERSONAL:
                case OBJECTIVE:
                    section = new TextSection(sectionType.getTitle());
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    section = new ListSection(sectionType.getTitle());
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    section = new OrganizationSection(sectionType.getTitle());
                    break;
            }
            resume.addSection(sectionType, section);
        }
        return section;
    }

    private void addObjective(String content) {
        TextSection section = (TextSection) getExistOrCreateSection(SectionType.OBJECTIVE);
        section.setContent(content);
    }

    private void addPersonal(String content) {
        TextSection section = (TextSection) getExistOrCreateSection(SectionType.PERSONAL);
        section.setContent(content);
    }

    private void addAchievement(String content) {
        ListSection section = (ListSection) getExistOrCreateSection(SectionType.ACHIEVEMENT);
        section.addSubsection(content);
    }

    private void addQualification(String content) {
        ListSection section = (ListSection) getExistOrCreateSection(SectionType.QUALIFICATIONS);
        section.addSubsection(content);
    }

    private void addEducation(String organizationTitle, String organizationLink,
                              Month monthFrom, int yearFrom, String workTitle) {
        OrganizationSection section = (OrganizationSection) getExistOrCreateSection(SectionType.EDUCATION);
        section.addSubsection(organizationTitle, organizationLink, yearFrom, monthFrom, workTitle, null);
    }

    private void addEducation(String organizationTitle, String organizationLink,
                              Month monthFrom, int yearFrom, Month monthTo, int yearTo, String workTitle) {
        OrganizationSection section = (OrganizationSection) getExistOrCreateSection(SectionType.EDUCATION);
        section.addSubsection(organizationTitle, organizationLink, yearFrom, monthFrom, yearTo, monthTo, workTitle, null);
    }

    private void addExperience(String organizationTitle, String organizationLink,
                               Month monthFrom, int yearFrom, String workTitle, String content) {
        OrganizationSection section = (OrganizationSection) getExistOrCreateSection(SectionType.EXPERIENCE);
        section.addSubsection(organizationTitle, organizationLink, yearFrom, monthFrom, workTitle, content);
    }

    private void addExperience(String organizationTitle, String organizationLink,
                               Month monthFrom, int yearFrom, Month monthTo, int yearTo, String workTitle, String content) {
        OrganizationSection section = (OrganizationSection) getExistOrCreateSection(SectionType.EXPERIENCE);
        section.addSubsection(organizationTitle, organizationLink, yearFrom, monthFrom, yearTo, monthTo, workTitle, content);
    }

}
