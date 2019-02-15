package it.uniroma2.ispw.fersa.rentingManagement.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.entity.Contract;
import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractId;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;

import java.sql.SQLException;

public class ContractSimpleLoader extends ContractLoader {

    private ContractId contractId;

    public ContractSimpleLoader(ContractId contractId) {
        this.contractId = contractId;
    }

    @Override
    public Contract loadContract() throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException {
        return ContractDAO.getInstance().getContractById(this.contractId);
    }

}

