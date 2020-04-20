package empapp;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Stateless
public class EmployeeBean {

    @Inject
    private NameTrimmer nameTrimmer;


    @PersistenceContext
    private EntityManager em;

    public List<Employee> findEmployees() {
        return em.createQuery("select e from Employee e", Employee.class).getResultList();
    }

    @Transactional
    public void saveEmployee(String name) {
        em.persist(new Employee(name));
    }
}
