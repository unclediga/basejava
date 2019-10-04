package ru.javawebinar.basejava.model;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends AbstractSection {
    private List<Organization> content = new ArrayList<>();

    public OrganizationSection(String title) {
        setTitle(title);
    }

    public class Organization {
        private String title;
        private String link;
        private YearMonth dateFrom;
        private YearMonth dateTo;
        private TextSection content;

        public Organization(String organizationTitle, String link, int yearFrom, int monthFrom, int yearTo, int monthTo, String title, String content) {
            this.title = organizationTitle;
            this.link = link;
            this.dateFrom = YearMonth.of(yearFrom, monthFrom);
            this.dateTo = YearMonth.of(yearTo, monthTo);
            TextSection textSection = new TextSection(title);
            textSection.setContent(content);
            this.content = textSection;

        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Organization entry = (Organization) o;
            return Objects.equals(title, entry.title) &&
                    Objects.equals(link, entry.link) &&
                    Objects.equals(dateFrom, entry.dateFrom) &&
                    Objects.equals(dateTo, entry.dateTo) &&
                    Objects.equals(content, entry.content);
        }

        @Override
        public int hashCode() {
            return Objects.hash(title, link, dateFrom, dateTo, content);
        }

        @Override
        public String toString() {
            return "OrganizationSectionEntry{" +
                    "organization=" + title + "(" + link + ")" +
                    ", dateFrom='" + dateFrom + '\'' +
                    ", dateTo='" + dateTo + '\'' +
                    ", content='" + content + '\'' +
                    '}';
        }
    }

    public void addSubsection(String organizationTitle, String organizationLink,
                              int yearFrom, int monthFrom, int yearTo, int monthTo, String workTitle, String subsection) {
        Organization entry = new Organization(organizationTitle, organizationLink,
                yearFrom, monthFrom, yearTo, monthTo, workTitle, subsection);
        content.add(entry);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationSection that = (OrganizationSection) o;
        return Objects.equals(content, that.content) && Objects.equals(getTitle(), that.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, getTitle());
    }

    @Override
    public String toString() {
        return "OrganizationSection{" +
                "title='" + getTitle() + '\'' +
                ", content=" + content +
                '}';
    }
}
