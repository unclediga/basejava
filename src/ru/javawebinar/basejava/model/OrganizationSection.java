package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.util.DateUtil;
import ru.javawebinar.basejava.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.basejava.util.DateUtil.NOW;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrganizationSection extends AbstractSection {
    private static final long serialVersionUID = 1L;
    private List<Organization> content = new ArrayList<>();

    public OrganizationSection() {
    }

    public OrganizationSection(Organization... organizations) {
        for (Organization organization : organizations) {
            content.add(organization);
        }
    }

    public OrganizationSection(List<Organization> organizations) {
        content.addAll(organizations);
    }

    public List<Organization> getContent() {
        return content;
    }

    public void setContent(List<Organization> content) {
        this.content = content;
    }

    // Organization ////////////////////////////////////////////////////////////
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Organization implements Serializable {
        private static final long serialVersionUID = 1L;
        private Link link;
        private List<Position> positions = new ArrayList<>();
        public static final Organization EMPTY = new Organization("", "", Position.EMPTY);

        public Organization() {
        }

        public Organization(String organizationTitle, String link, Position... positions) {
            this.link = new Link(organizationTitle, link);
            this.positions = Arrays.asList(positions);
        }

        public Organization(String organizationTitle, String link, List<Position> positions) {
            this.link = new Link(organizationTitle, link);
            this.positions = positions;
        }

        public Link getLink() {
            return link;
        }

        public void setLink(Link link) {
            this.link = link;
        }

        public List<Position> getPositions() {
            return positions;
        }

        public void setPositions(List<Position> positions) {
            this.positions = positions;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Organization)) return false;
            Organization that = (Organization) o;
            return Objects.equals(link, that.link) &&
                    Objects.equals(positions, that.positions);
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
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Link implements Serializable {
        private static final long serialVersionUID = 1L;
        private String title;
        private String homePage;

        public Link() {
        }

        public Link(String title, String homePage) {
            Objects.requireNonNull(title, "title must not be null");
            this.title = title;
            this.homePage = homePage;
        }

        public String getTitle() {
            return title;
        }

        public String getHomePage() {
            return homePage;
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
        if (!(o instanceof OrganizationSection)) return false;
        OrganizationSection that = (OrganizationSection) o;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }

    @Override
    public String toString() {
        return "OrganizationSection{" +
                "content=" + content +
                '}';
    }

    // Position ////////////////////////////////////////////////////////////
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Position implements Serializable {
        private static final long serialVersionUID = 1L;
        public static final Position EMPTY = new Position();
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate dateFrom;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate dateTo;
        private String title;
        private String description;


        public Position() {
        }

        public Position(int yearFrom, Month monthFrom, String workTitle, String subsection) {
            this(DateUtil.of(yearFrom, monthFrom), NOW, workTitle, subsection);
        }

        public Position(int yearFrom, Month monthFrom, int yearTo, Month monthTo, String workTitle, String subsection) {
            this(DateUtil.of(yearFrom, monthFrom), DateUtil.of(yearTo, monthTo), workTitle, subsection);
        }

        public Position(LocalDate dateFrom, LocalDate dateTo, String title, String description) {
            Objects.requireNonNull(dateFrom, "dateFrom must be not null!");
            Objects.requireNonNull(dateTo, "dateTo must be not null!");
            Objects.requireNonNull(title, "title must be not null!");
            this.dateFrom = dateFrom;
            this.dateTo = dateTo;
            this.title = title;
            this.description = description;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Position)) return false;
            Position position = (Position) o;
            return Objects.equals(dateFrom, position.dateFrom) &&
                    Objects.equals(dateTo, position.dateTo) &&
                    Objects.equals(title, position.title) &&
                    Objects.equals(description, position.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(dateFrom, dateTo, title, description);
        }

        @Override
        public String toString() {
            return "Position{" +
                    "dateFrom=" + dateFrom +
                    ", dateTo=" + dateTo +
                    ", title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }
}
