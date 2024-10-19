package ua.foxminded.moldavets.project.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Organization {

    private final Link homepage;

    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    private final String title;
    private final String description;

    Organization(String name, String url, LocalDateTime startDate, LocalDateTime endDate, String title, String description) {
        Objects.requireNonNull(startDate, "startDate must not be null");
        Objects.requireNonNull(endDate, "endDate must not be null");
        Objects.requireNonNull(title, "title must not be null");
        this.homepage = new Link(name, url);
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(homepage, that.homepage) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(title, that.title) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homepage, startDate, endDate, title, description);
    }
}
