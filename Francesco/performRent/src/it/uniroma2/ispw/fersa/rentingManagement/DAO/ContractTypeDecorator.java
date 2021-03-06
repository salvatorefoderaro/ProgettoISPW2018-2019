package it.uniroma2.ispw.fersa.rentingManagement.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.entity.*;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ContractPeriodException;

import java.sql.SQLException;

public class ContractTypeDecorator extends ContractLoaderDecorator {
    public ContractTypeDecorator(ContractLoader contractLoader) {
        super(contractLoader);
    }

    public Contract setContractType(Contract contract) throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException {
        ContractId contractId = contract.getContractId();
        ContractType contractType = ContractTypeDAO.getIstance().getContractTypeByContractId(contractId);
        contract.setContractType(contractType);
        return contract;
    }

    @Override
    public Contract loadContract() throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException {
        Contract preliminaryResult = super.loadContract();
        preliminaryResult = setContractType(preliminaryResult);
        return preliminaryResult;
    }
}
