package employees;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@CountryAndZip
public class Employee {

    @NotBlank
    @Size(min = 3, max = 200, message = "{employee_name.invalid_value}")
    private String name;

    @Min(100_000)
    @DivBy(number = 10)
    private int salary;


    private String country;

    @ZipCode
    private String zip;

    public Employee() {
    }

    public Employee(String name, int salary, String country, String zip) {
        this.name = name;
        this.salary = salary;
        this.country = country;
        this.zip = zip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
