package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class OrganizationSection extends Section {
    private String title;
    private List<OrganizationSectionEntry> content = new ArrayList<>();

    public OrganizationSection(String title) {
        this.title = title;
    }

    public class OrganizationSectionEntry {
        private Organization organization;
        private String dateFrom;
        private String dateTo;
        private String title;
        private String content;

        OrganizationSectionEntry(Organization organization, String dateFrom, String dateTo, String title, String content) {
            this.organization = organization;
            this.dateFrom = dateFrom;
            this.dateTo = dateTo;
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

    public void addSubsection(String organizationTitle, String organizationLink, String dateFrom, String dateTo, String workTitle, String subsection) {
        Organization organization = new Organization(organizationTitle, organizationLink);
        OrganizationSectionEntry entry = new OrganizationSectionEntry(organization, dateFrom, dateTo, workTitle, subsection);
        content.add(entry);
    }

    @Override
    public String toString() {
        return "OrganizationSection{" +
                "title='" + title + '\'' +
                ", content=" + content +
                '}';
    }
}
