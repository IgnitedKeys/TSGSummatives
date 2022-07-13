
package com.sg.vendingmachine;

import com.sg.vendingmachine.controller.VendingMachineController;
import com.sg.vendingmachine.dao.VendingMachineAuditDao;
import com.sg.vendingmachine.dao.VendingMachineAuditDaoImpl;
import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dao.VendingMachineDaoImpl;
import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.service.InsufficientFundsException;
import com.sg.vendingmachine.service.NoItemInventoryException;
import com.sg.vendingmachine.service.VendingMachineService;import com.sg.vendingmachine.service.VendingMachineService;

import com.sg.vendingmachine.service.VendingMachineServiceImpl;
import com.sg.vendingmachine.ui.UserIO;
import com.sg.vendingmachine.ui.UserIOImpl;
import com.sg.vendingmachine.ui.VendingMachineView;

/**
 *
 * @author angeladrees
 */
public class App {
    public static void main(String[] args) throws VendingMachinePersistenceException, InsufficientFundsException, NoItemInventoryException {
        UserIO io = new UserIOImpl();
        VendingMachineDao dao = new VendingMachineDaoImpl();
        VendingMachineAuditDao auditDao = new VendingMachineAuditDaoImpl();
        VendingMachineView view = new VendingMachineView(io);
        VendingMachineService service = new VendingMachineServiceImpl(dao, auditDao);
        VendingMachineController controller = new VendingMachineController(service, view);
        
        controller.run();
    }
}