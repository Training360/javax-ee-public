package empapp;

import jakarta.annotation.Resource;
import jakarta.ejb.SessionContext;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@Stateless
public class EmployeeDaoBean {

    @Inject
    private NameTrimmer nameTrimmer;

    @PersistenceContext
    private EntityManager em;

    @Resource
    private SessionContext sessionContext;

    public List<Employee> findEmployees() {
        return em.createQuery("select e from Employee e", Employee.class).getResultList();
    }

    @Transactional
    public void saveEmployee(String name) {
        em.persist(new Employee(name));

        if (name.equals("")) {
            sessionContext.setRollbackOnly();
        }
    }
}
