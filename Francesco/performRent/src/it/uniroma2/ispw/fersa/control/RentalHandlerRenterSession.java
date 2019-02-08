package it.uniroma2.ispw.fersa.control;

import it.uniroma2.ispw.fersa.rentingManagement.DAO.*;
import it.uniroma2.ispw.fersa.rentingManagement.bean.*;
import it.uniroma2.ispw.fersa.rentingManagement.entity.*;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ContractPeriodException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.PeriodException;
import it.uniroma2.ispw.fersa.userProfileAndServices.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RentalHandlerRenterSession extends RentalHandlerSession{


    public RentalHandlerRenterSession(String renterNickname) {
        super(renterNickname);
    }

    @Override
    public List<RequestLabelBean> getAllContractRequest() throws SQLException, ClassNotFoundException,
            ConfigFileException, ConfigException {
        List<RequestLabelBean> requestLabelBeans = new ArrayList<>();

        List<ContractRequestId> contractRequestIds =
                ContractRequestJDBC.getInstance().findContractRequestIdsByRenterNickname(this.nickname);
        for (ContractRequestId requestId : contractRequestIds) {
            try {
                ContractRequestRetriver contractRequestRetriver =
                        new ContractTypeDecorator(new ServiceDecorator(new ContractRequestSimpleRetriver(requestId)));
                ContractRequest contractRequest = contractRequestRetriver.retriveContractRequest();
                requestLabelBeans.add(new RequestLabelBean(contractRequest.getRequestId().getId(),
                        contractRequest.getTenantNickname(), contractRequest.getCreationDate(),
                        contractRequest.getStartDate(), contractRequest.getEndDate(), contractRequest.getTotal(),
                        contractRequest.getState()));
            } catch (ContractPeriodException e) {
                e.printStackTrace();
            }
        }

        return requestLabelBeans;
    }


    public void declineRequest(String declineMotivation) throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException {
        ContractRequestJDBC.getInstance().refuseRequest(this.contractRequest.getContractRequestId(), declineMotivation);
    }

    public void createContract() throws NicknameNotFoundException, SQLException, ClassNotFoundException, ConfigException, ConfigFileException{
        UserProfileInterface userProfile = new UserLoaderFAKE();
        UserInfo tenantInfo, renterInfo;
        ContractType contractType;

        tenantInfo = userProfile.getUserInfo(this.contractRequest.getTenantNickname(), UserInfoType.TENANT);
        renterInfo = userProfile.getUserInfo(this.nickname, UserInfoType.RENTER);
        contractType = ContractTypeJDBC.getIstance().getContractTypeById(this.contractRequest.getContractTypeId());



        this.contract = new Contract(this.contractRequest.getRentableId(), tenantInfo.getNickname(),
                this.contractRequest.getStartDate(), this.contractRequest.getEndDate(), tenantInfo.getName(),
                tenantInfo.getSurname(), tenantInfo.getCF(), tenantInfo.getDateOfBirth(), tenantInfo.getCityOfBirth(),
                tenantInfo.getAddress(), renterInfo.getName(), renterInfo.getSurname(), renterInfo.getCF(),
                renterInfo.getAddress(), this.contractRequest.getRentablePrice(), this.contractRequest.getDeposit(),
                contractType, this.contractRequest.getServices());
    }


    public void signContract() throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException, ContractPeriodException {
        ContractBean contractBean = new ContractBean(this.contractRequest.getContractRequestId(), this.contract.getTenantName(), this.contract.getTenantSurname(), this.contract.getTenantCF(), this.contract.getTenantDateOfBirth(), this.contract.getTenantCityOfBirth(), this.contract.getTenantAddress(), this.contract.getRenterName(), this.contract.getRenterSurname(), this.contract.getRenterCF(), this.contract.getRenterAddress());

        System.out.println("Saving contract");

        ContractJDBC.getInstance().createContract(contractBean);
    }

    @Override
    public List<ContractLabelBean> getAllContracts() {
        return null;
    }
}
