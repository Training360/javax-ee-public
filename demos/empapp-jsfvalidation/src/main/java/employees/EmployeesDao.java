package employees;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Stateless
public class EmployeesDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void createEmployee(String name) {
        entityManager.persist(new Employee(name));
    }

    public List<Employee> listEmployees() {
        return entityManager
            .createQuery("select e from Employee e", Employee.class)
                 .getResultList();
    }
}