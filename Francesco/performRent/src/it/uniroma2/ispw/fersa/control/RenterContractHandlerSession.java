package it.uniroma2.ispw.fersa.control;

import it.uniroma2.ispw.fersa.rentingManagement.DAO.*;
import it.uniroma2.ispw.fersa.rentingManagement.bean.*;
import it.uniroma2.ispw.fersa.rentingManagement.entity.*;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ContractPeriodException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RenterContractHandlerSession extends ContractHandlerSession{
    private String renter;


    public RenterContractHandlerSession(String renter) {
        this.renter = renter;
    }

    public List<ContractLabelBean> getAllContracts() throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException {
        List<ContractLabelBean> contractLabelBeans = new ArrayList<>();
        List<ContractId> contractIds;
        try {
            contractIds = ContractDAO.getInstance().getAllContractsIdByRenterNickname(this.renter);
        } catch (SQLException | ConfigException | ConfigFileException | ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        }

        for (ContractId contractId : contractIds) {
            ContractLoader contractLoader = new ContractServiceDecorator(new ContractSimpleLoader(contractId));

            Contract contract = contractLoader.loadContract();

            contractLabelBeans.add(new ContractLabelBean(contract.getContractId().getContractId(),
                    contract.getTenantNickname(), contract.getCreationDate(), contract.getStipulationDate(),
                    contract.getStartDate(), contract.getEndDate(), contract.getGrossPrice(), contract.getState()));

        }

        return contractLabelBeans;
    }

    public ContractInfoBean getContractInfo() {
        List<Service> services = this.contract.getServices();

        List<ServiceBean> serviceBeans = new ArrayList<>();

        services.forEach(service -> serviceBeans.add(new ServiceBean(service.getId(), service.getName(),
                service.getDescriprion(), service.getPrice())));

        return new ContractInfoBean(this.contract.getContractTypeName(), this.contract.getTenantNickname(),
                this.contract.getTenantName(), this.contract.getTenantSurname(), this.contract.getTenantCF(),
                this.contract.getCreationDate(), this.contract.getStipulationDate(), this.contract.getStartDate(),
                this.contract.getEndDate(), this.contract.getPropertyPrice(), this.contract.getDeposit(), serviceBeans,
                this.contract.getGrossPrice(), this.contract.getState());
    }

    public boolean isContractSelected() {
        return this.contract != null;
    }
}
