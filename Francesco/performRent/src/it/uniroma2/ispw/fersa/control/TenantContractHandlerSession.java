package it.uniroma2.ispw.fersa.control;

import it.uniroma2.ispw.fersa.rentingManagement.DAO.*;
import it.uniroma2.ispw.fersa.rentingManagement.bean.*;
import it.uniroma2.ispw.fersa.rentingManagement.entity.*;
import it.uniroma2.ispw.fersa.rentingManagement.exception.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TenantContractHandlerSession extends ContractHandlerSession {
    private String tenant;


    public TenantContractHandlerSession(String tenant) {
        this.tenant = tenant;
    }

    public List<ContractLabelBean> getAllContracts() throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException {
        List<ContractLabelBean> contractLabelBeans = new ArrayList<>();
        List<ContractId> contractIds;
        try {
            contractIds = ContractDAO.getInstance().getAllContractsIdByTenantNickname(this.tenant);
        } catch (SQLException | ConfigException | ConfigFileException | ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        }

        for (ContractId contractId : contractIds) {
            ContractLoader contractLoader = new ContractServiceDecorator(new ContractSimpleLoader(contractId));

            Contract contract = contractLoader.loadContract();

            contractLabelBeans.add(new ContractLabelBean(contract.getContractId().getContractId(),
                    contract.getRenterNickname(), contract.getCreationDate(), contract.getStipulationDate(),
                    contract.getStartDate(), contract.getEndDate(), contract.getGrossPrice(), contract.getState()));

        }

        return contractLabelBeans;
    }

    public ContractInfoBean getContractInfo() {
        List<Service> services = this.contract.getServices();

        List<ServiceBean> serviceBeans = new ArrayList<>();

        services.forEach(service -> serviceBeans.add(new ServiceBean(service.getId(), service.getName(),
                service.getDescriprion(), service.getPrice())));

        return new ContractInfoBean(this.contract.getContractTypeName(), this.contract.getRenterNickname(),
                this.contract.getRenterName(), this.contract.getRenterSurname(), this.contract.getRenterCF(),
                this.contract.getCreationDate(), this.contract.getStipulationDate(), this.contract.getStartDate(),
                this.contract.getEndDate(), this.contract.getPropertyPrice(), this.contract.getDeposit(), serviceBeans,
                this.contract.getGrossPrice(), this.contract.getState());
    }

    public void signContract() throws SQLException, ConfigFileException, ClassNotFoundException, ConfigException, CanceledContractException {
        ContractDAO.getInstance().signContract(this.contract.getContractId());

    }
}
