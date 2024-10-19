package ua.foxminded.moldavets.project.model;

public enum ContactType {
    PHONE("Phone number"),
    EMAIL("E-mail"),
    LINKEDIN("Linkedin"),
    GITHUB("GitHub"),
    HOME_PAGE("Home page"),;

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
