package ru.javawebinar.basejava.model;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends AbstractSection {
    private List<OrganizationSectionEntry> content = new ArrayList<>();

    public OrganizationSection(String title) {
        setTitle(title);
    }

    public class OrganizationSectionEntry {
        private String organizationTitle;
        private String organizationLink;
        private YearMonth dateFrom;
        private YearMonth dateTo;
        private String title;
        private String content;

        public OrganizationSectionEntry(String organizationTitle, String organizationLink, int yearFrom, int monthFrom, int yearTo, int monthTo, String title, String content) {
            this.organizationTitle = organizationTitle;
            this.organizationLink = organizationLink;
            this.dateFrom = YearMonth.of(yearFrom, monthFrom);
            this.dateTo = YearMonth.of(yearTo, monthTo);
            this.title = title;
            this.content = content;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            OrganizationSectionEntry entry = (OrganizationSectionEntry) o;
            return Objects.equals(organizationTitle, entry.organizationTitle) &&
                    Objects.equals(organizationLink, entry.organizationLink) &&
                    Objects.equals(dateFrom, entry.dateFrom) &&
                    Objects.equals(dateTo, entry.dateTo) &&
                    Objects.equals(title, entry.title) &&
                    Objects.equals(content, entry.content);
        }

        @Override
        public int hashCode() {
            return Objects.hash(organizationTitle, organizationLink, dateFrom, dateTo, title, content);
        }

        @Override
        public String toString() {
            return "OrganizationSectionEntry{" +
                    "organization=" + organizationTitle + "(" + organizationLink + ")" +
                    ", dateFrom='" + dateFrom + '\'' +
                    ", dateTo='" + dateTo + '\'' +
                    ", title='" + title + '\'' +
                    ", content='" + content + '\'' +
                    '}';
        }
    }

    public void addSubsection(String organizationTitle, String organizationLink,
                              int yearFrom, int monthFrom, int yearTo, int monthTo, String workTitle, String subsection) {
        OrganizationSectionEntry entry = new OrganizationSectionEntry(organizationTitle, organizationLink,
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
