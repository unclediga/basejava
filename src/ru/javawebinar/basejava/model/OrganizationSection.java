package ru.javawebinar.basejava.model;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class OrganizationSection extends Section {
    private List<OrganizationSectionEntry> content = new ArrayList<>();

    public OrganizationSection(String title) {
        setTitle(title);
    }

    public class OrganizationSectionEntry {
        private Organization organization;
        private YearMonth dateFrom;
        private YearMonth dateTo;
        private String title;
        private String content;

        OrganizationSectionEntry(Organization organization, int yearFrom, int monthFrom, int yearTo, int monthTo, String title, String content) {
            this.organization = organization;
            this.dateFrom = YearMonth.of(yearFrom, monthFrom);
            this.dateTo = YearMonth.of(yearTo, monthTo);
            this.title = title;
            this.content = content;
        }

        @Override
        public String toString() {
            return "OrganizationSectionEntry{" +
                    "organization=" + organization +
                    ", dateFrom='" + dateFrom + '\'' +
                    ", dateTo='" + dateTo + '\'' +
                    ", title='" + title + '\'' +
                    ", content='" + content + '\'' +
                    '}';
        }
    }

    public void addSubsection(String organizationTitle, String organizationLink,
                              int yearFrom, int monthFrom, int yearTo, int monthTo, String workTitle, String subsection) {
        Organization organization = new Organization(organizationTitle, organizationLink);
        OrganizationSectionEntry entry = new OrganizationSectionEntry(organization, yearFrom, monthFrom, yearTo, monthTo, workTitle, subsection);
        content.add(entry);
    }

    @Override
    public String toString() {
        return "OrganizationSection{" +
                "title='" + getTitle() + '\'' +
                ", content=" + content +
                '}';
    }
}
