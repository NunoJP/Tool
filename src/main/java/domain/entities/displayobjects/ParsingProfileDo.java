package domain.entities.displayobjects;

public class ParsingProfileDo {
    private String name;
    private Integer id;
    private String description;

    public ParsingProfileDo(int id, String name) {
        this(id, name, "");
    }

    public ParsingProfileDo(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
