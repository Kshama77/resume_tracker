public class ResumeVersion {
    private int id;
    private String name;
    private String description;

    public ResumeVersion(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String toFileLine() {
        return id + "|" + clean(name) + "|" + clean(description);
    }

    private String clean(String value) {
        return value.replace("|", "/").replace("\n", " ");
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Name: " + name + " | Description: " + description;
    }
}
