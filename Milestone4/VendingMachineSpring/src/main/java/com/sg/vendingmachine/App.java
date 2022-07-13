package com.sg.vendingmachine;

import com.sg.vendingmachine.controller.VendingMachineController;
import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.service.InsufficientFundsException;
import com.sg.vendingmachine.service.NoItemInventoryException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author angeladrees
 */
public class App {

    public static void main(String[] args) throws VendingMachinePersistenceException, InsufficientFundsException, NoItemInventoryException {
       ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
       VendingMachineController controller = ctx.getBean("controller", VendingMachineController.class);

        controller.run();
    }
}
