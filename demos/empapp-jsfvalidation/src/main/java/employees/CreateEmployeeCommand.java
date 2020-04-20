package employees;

import javax.validation.constraints.NotBlank;

public class CreateEmployeeCommand {

    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
