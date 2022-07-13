package com.sg.flooringmastery;

import com.sg.flooringmastery.controller.FlooringMasteryController;
import com.sg.flooringmastery.dao.FilePersistenceException;
import com.sg.flooringmastery.service.OrderValidationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author adrees
 */
public class App {

    public static void main(String[] args) throws FilePersistenceException, OrderValidationException {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        FlooringMasteryController controller = ctx.getBean("controller", FlooringMasteryController.class);
        controller.run();
    }
}
