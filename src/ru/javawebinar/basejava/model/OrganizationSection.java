package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.util.DateUtil;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.basejava.util.DateUtil.NOW;

public class OrganizationSection extends AbstractSection {
    private static final long serialVersionUID = 1L;
    private List<Organization> content = new ArrayList<>();

    public OrganizationSection(String title) {
        setTitle(title);
    }

    public OrganizationSection(String title, Organization... organizations) {
        setTitle(title);
        for (Organization organization : organizations) {
            content.add(organization);
        }
    }

    // Organization ////////////////////////////////////////////////////////////
    public static class Organization implements Serializable {
        private static final long serialVersionUID = 1L;
        private Link link;
        private List<Position> positions = new ArrayList<>();

        public Organization(String organizationTitle, String link, Position... positions) {
            this.link = new Link(organizationTitle, link);
            this.positions = Arrays.asList(positions);
        }

        public Organization(String organizationTitle, String link, List<Position> positions) {
            this.link = new Link(organizationTitle, link);
            this.positions = positions;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Organization)) return false;
            Organization that = (Organization) o;
            return link.equals(that.link) &&
                    positions.equals(that.positions);
        }

        @Override
        public int hashCode() {
            return Objects.hash(link, positions);
        }

        @Override
        public String toString() {
            return "Organization{" +
                    "link=" + link +
                    ", positions=" + positions +
                    '}';
        }
    }

    // Link //////////////////////////////////////////////////////////////
    public static class Link implements Serializable {
        private static final long serialVersionUID = 1L;
        private String title;
        private String homePage;

        public Link(String title, String homePage) {
            Objects.requireNonNull(title, "title must not be null");
            this.title = title;
            this.homePage = homePage;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Link that = (Link) o;
            return title.equals(that.title) &&
                    Objects.equals(homePage, that.homePage);
        }

        @Override
        public int hashCode() {
            return Objects.hash(title, homePage);
        }

        @Override
        public String toString() {
            return "OrganizationLink{" +
                    "title='" + title + '\'' +
                    ", link='" + homePage + '\'' +
                    '}';
        }
    }

    public void addSubsection(String organizationTitle, String organizationLink,
                              int yearFrom, Month monthFrom, String workTitle, String subsection) {
        Organization entry = new Organization(organizationTitle, organizationLink,
                new Position(yearFrom, monthFrom, workTitle, subsection));
        content.add(entry);
    }

    public void addSubsection(String organizationTitle, String organizationLink,
                              int yearFrom, Month monthFrom, int yearTo, Month monthTo, String workTitle, String subsection) {
        Organization entry = new Organization(organizationTitle, organizationLink,
                new Position(yearFrom, monthFrom, yearTo, monthTo, workTitle, subsection));
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

    // Position ////////////////////////////////////////////////////////////
    public static class Position implements Serializable {
        private static final long serialVersionUID = 1L;
        private LocalDate dateFrom;
        private LocalDate dateTo;
        private TextSection content;

        public Position(int yearFrom, Month monthFrom, String workTitle, String subsection) {
            this(DateUtil.of(yearFrom, monthFrom), NOW, workTitle, subsection);
        }

        public Position(int yearFrom, Month monthFrom, int yearTo, Month monthTo, String workTitle, String subsection) {
            this(DateUtil.of(yearFrom, monthFrom), DateUtil.of(yearTo, monthTo), workTitle, subsection);
        }

        public Position(LocalDate dateFrom, LocalDate dateTo, String title, String content) {
            Objects.requireNonNull(dateFrom, "dateFrom must be not null!");
            Objects.requireNonNull(dateTo, "dateTo must be not null!");
            Objects.requireNonNull(title, "title must be not null!");
            this.dateFrom = dateFrom;
            this.dateTo = dateTo;
            TextSection textSection = new TextSection(title);
            textSection.setContent(content);
            this.content = textSection;
        }

        public LocalDate getDateFrom() {
            return dateFrom;
        }

        public void setDateFrom(LocalDate dateFrom) {
            this.dateFrom = dateFrom;
        }

        public LocalDate getDateTo() {
            return dateTo;
        }

        public void setDateTo(LocalDate dateTo) {
            this.dateTo = dateTo;
        }

        public TextSection getContent() {
            return content;
        }

        public void setContent(TextSection content) {
            this.content = content;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Position)) return false;
            Position position = (Position) o;
            return dateFrom.equals(position.dateFrom) &&
                    dateTo.equals(position.dateTo) &&
                    content.equals(position.content);
        }

        @Override
        public int hashCode() {
            return Objects.hash(dateFrom, dateTo, content);
        }

        @Override
        public String toString() {
            return "Position{" +
                    "dateFrom=" + dateFrom +
                    ", dateTo=" + dateTo +
                    ", content=" + content +
                    '}';
        }
    }
}
