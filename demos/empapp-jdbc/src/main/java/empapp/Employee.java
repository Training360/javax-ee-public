package empapp;

public class Employee {

    private Long id;

    private String name;

    public Employee(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Employee() {
    }

    public Employee(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
