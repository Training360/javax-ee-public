package empapp;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Stateless
public class LogEntryDaoBean {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void saveLogEntry(String message) {
        em.persist(new LogEntry(message));
    }
}
