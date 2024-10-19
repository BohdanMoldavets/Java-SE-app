package ua.foxminded.moldavets.project.model;

import java.util.List;
import java.util.Objects;

public class ListSection extends Section {

    private final List<String> items;

    ListSection(List<String> items) {
        Objects.requireNonNull(items, "items must not be null");
        this.items = items;
    }

    public List<String> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return items.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return items != null ? Objects.hashCode(items) : 0;
    }
}
