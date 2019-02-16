package it.uniroma2.ispw.fersa.rentingManagement.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.entity.*;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ContractPeriodException;

import java.sql.SQLException;
import java.util.List;

public class ContractServiceDecorator extends ContractLoaderDecorator {
    public ContractServiceDecorator(ContractLoader contractLoader) {
        super(contractLoader);
    }


    public Contract setServices(Contract contract) throws ConfigFileException, ConfigException, ClassNotFoundException, SQLException {
        ContractId contractId = contract.getContractId();
        List<Service> services = ServiceDAO.getInstance().getServicesByContractId(contractId);
        contract.setServices(services);
        return contract;
    }


    @Override
    public Contract loadContract() throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException {
        Contract preliminaryResult = super.loadContract();
        preliminaryResult = setServices(preliminaryResult);
        return preliminaryResult;
    }

}
