package ua.foxminded.moldavets.project.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends Section {

    private final List<Organization> organizations;

    public OrganizationSection(Organization... items) {
        this(Arrays.asList(items));
    }

    public OrganizationSection(List<Organization> organizations) {
        Objects.requireNonNull(organizations, "organizations must not be null");
        this.organizations = organizations;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    @Override
    public String toString() {
        return "OrganizationSection{" +
                "organizations=" + organizations +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationSection that = (OrganizationSection) o;
        return Objects.equals(organizations, that.organizations);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(organizations);
    }
}
