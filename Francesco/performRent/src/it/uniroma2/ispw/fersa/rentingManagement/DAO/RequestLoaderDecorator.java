package it.uniroma2.ispw.fersa.rentingManagement.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractRequest;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ContractPeriodException;

import java.sql.SQLException;

public abstract class RequestLoaderDecorator extends RequestLoader {
    private RequestLoader requestLoader;

    public RequestLoaderDecorator(RequestLoader requestLoader) {
        this.requestLoader = requestLoader;
    }

    @Override
    public ContractRequest loadContractRequest() throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException, ContractPeriodException {
        ContractRequest contractRequest = this.requestLoader.loadContractRequest();
        return contractRequest;
    }

}
