package it.uniroma2.ispw.fersa.control;

import it.uniroma2.ispw.fersa.rentingManagement.DAO.ContractDAO;
import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractId;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CheckOldContracts implements Runnable {

    private int period = 1;

    public CheckOldContracts() { }

    public CheckOldContracts(int period) {
        this.period = period;
    }



    @Override
    public void run() {

        System.out.println("Numero di giorni: " + this.period);
        while (true) {
            System.out.println("Start checking...");
            try {
                List<ContractId> contractIds = ContractDAO.getInstance().findNotSignedContract(LocalDate.now().minusDays(7));

                System.out.println("Old contracts found: " + contractIds.size());
                for (ContractId contractId : contractIds) {
                    ContractDAO.getInstance().cancelContract(contractId);
                }

            } catch (SQLException | ClassNotFoundException | ConfigFileException | ConfigException e) {
                e.printStackTrace();
                return;
            }
            try {
                System.out.println("Going to sleep...");
                TimeUnit.DAYS.sleep(this.period);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
