package empapp;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class EmployeeServiceBean {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    private EmployeeDaoBean employeeDaoBean;

    @Inject
    private LogEntryDaoBean logEntryDaoBean;

    @Resource
    private TimerService timerService;

    @Inject
    private JMSContext context;

    @Resource(mappedName = "java:/jms/queue/EmployeeQueue")
    private Queue queue;


    @PostConstruct
    public void init() {
        System.out.println("Employee Service bean has created");
    }

    @Schedule(second = "*/10", minute = "*", hour = "*")
    public void printTime() {
        System.out.println("The time is: " + LocalDateTime.now());
    }

    public List<Employee> findEmployees() {
        logger.info("List employees");
        logger.debug("List employees without parameters");

        return employeeDaoBean.findEmployees();
    }

    @Transactional
    public void saveEmployee(String name) {
        logEntryDaoBean.saveLogEntry("Save employee with name: " + name);

        employeeDaoBean.saveEmployee(name);

        timerService.createTimer(5000, name);

        context.createProducer().send(queue, name);
    }

    @Timeout
    public void logCreateEmployee(Timer timer) {
        String name = (String) timer.getInfo();
        System.out.println("Employee has created: " + name);
    }

}
