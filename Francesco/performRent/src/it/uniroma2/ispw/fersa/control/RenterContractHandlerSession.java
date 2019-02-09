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
            contractIds = ContractJDBC.getInstance().getAllContractsIdByRenterNickname(this.renter);
        } catch (SQLException | ConfigException | ConfigFileException | ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        }

        for (ContractId contractId : contractIds) {
            ContractsAndRequestRetriver contractsAndRequestRetriver = new ServiceDecorator(new ContractsAndRequestSimpleRetriver(contractId));

            Contract contract = contractsAndRequestRetriver.retriveContract();

            contractLabelBeans.add(new ContractLabelBean(contract.getContractId().getContractId(),
                    contract.getTenantNickname(), contract.getCreationDate(), contract.getStipulationDate(),
                    contract.getStartDate(), contract.getEndDate(), contract.getGrossPrice(), contract.getState()));

        }

        return contractLabelBeans;
    }

    public void selectContract(ContractId contractId) throws SQLException, ClassNotFoundException, ConfigException,
            ConfigFileException {
        ContractsAndRequestRetriver contractsAndRequestRetriver =
                new ContractTypeDecorator(new ServiceDecorator(new ContractsAndRequestSimpleRetriver(contractId)));
        this.contract = contractsAndRequestRetriver.retriveContract();
    }

    public ContractTextBean getContract() throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException {
        EquippedApt equippedApt = EquippedAptJDBC.getInstance().getEquippedAptByContractId(this.contract.getContractId());

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
        EquippedApt apt = EquippedAptJDBC.getInstance().getEquippedAptByContractId(this.contract.getContractId());

        Rentable rentable = RentableJDBC.getInstance().getRentableByContractId(
                this.contract.getContractId());
        return new PropertyBean(apt.getAddress(), rentable.getName(), rentable.getImage(), rentable.getType(),
                rentable.getDescription());
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
