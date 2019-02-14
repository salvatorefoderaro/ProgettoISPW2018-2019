package it.uniroma2.ispw.fersa.control;

import it.uniroma2.ispw.fersa.rentingManagement.DAO.*;
import it.uniroma2.ispw.fersa.rentingManagement.bean.*;
import it.uniroma2.ispw.fersa.rentingManagement.entity.*;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RenterContractHandlerSession {
    private String renter;
    private Contract contract;

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
            ContractsAndRequestLoader contractsAndRequestLoader = new ServiceDecorator(new ContractsAndRequestSimpleLoader(contractId));

            Contract contract = contractsAndRequestLoader.retriveContract();

            contractLabelBeans.add(new ContractLabelBean(contract.getContractId().getContractId(),
                    contract.getTenantNickname(), contract.getCreationDate(), contract.getStipulationDate(),
                    contract.getStartDate(), contract.getEndDate(), contract.getGrossPrice(), contract.getState()));

        }

        return contractLabelBeans;
    }

    public void selectContract(ContractId contractId) throws SQLException, ClassNotFoundException, ConfigException,
            ConfigFileException {
        ContractsAndRequestLoader contractsAndRequestLoader =
                new ContractTypeDecorator(new ServiceDecorator(new ContractsAndRequestSimpleLoader(contractId)));
        this.contract = contractsAndRequestLoader.retriveContract();
    }

    public ContractTextBean getContract() throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException {
        EquippedApt equippedApt = EquippedAptDAO.getInstance().getEquippedAptByContractId(this.contract.getContractId());

        List<ServiceBean> serviceBeans = new ArrayList<>();

        this.contract.getServices().forEach(service -> serviceBeans.add(new ServiceBean(service.getId(),
                service.getName(), service.getDescriprion(), service.getPrice())));

        return new ContractTextBean(this.contract.getContractTypeName(), this.contract.isTransitory(),
                equippedApt.getAddress(), this.contract.getStartDate(), this.contract.getEndDate(),
                this.contract.getTenantName(), this.contract.getTenantSurname(), this.contract.getTenantCF(),
                this.contract.getTenantDateOfBirth(), this.contract.getTenantCityOfBirth(),
                this.contract.getTenantAddress(), this.contract.getRenterName(),
                this.contract.getRenterSurname(), this.contract.getRenterCF(), this.contract.getRenterAddress(),
                this.contract.getNumMonths(), this.contract.getNetPrice(), this.contract.getDeposit(), serviceBeans);

    }

    public PropertyBean getPropertyInfo()  throws SQLException, ClassNotFoundException, ConfigException,
            ConfigFileException, IOException {
        EquippedApt apt = EquippedAptDAO.getInstance().getEquippedAptByContractId(this.contract.getContractId());

        Property property = PropertyDAO.getInstance().getRentableByContractId(
                this.contract.getContractId());
        return new PropertyBean(apt.getAddress(), property.getName(), property.getImage(), property.getType(),
                property.getDescription());
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
