package it.uniroma2.ispw.fersa.rentingManagement;

import it.uniroma2.ispw.fersa.rentingManagement.DAO.ContractDAO;
import it.uniroma2.ispw.fersa.rentingManagement.bean.RentingContract;
import it.uniroma2.ispw.fersa.rentingManagement.entity.*;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.UserTypeException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RentingInterface {

    public List<RentingContract> getTerminatedContracts(String nickname, UserType userType)
            throws UserTypeException, UserTypeException {
        List<ContractId> contractIds = new ArrayList<>();
        List<RentingContract> rentingContracts = new ArrayList<>();


        try {
            switch (userType) {
                case TENANT:
                    contractIds = ContractDAO.getInstance().getAllContractsIdByTenantNickname(nickname);
                    break;
                case RENTER:
                    contractIds = ContractDAO.getInstance().getAllContractsIdByRenterNickname(nickname);
                    break;
            }
            for (ContractId contractId: contractIds) {
                Contract contract = ContractDAO.getInstance().getContractById(contractId);
                if (contract.getState() == ContractStateEnum.EXPIRED) {
                    rentingContracts.add(new RentingContract(contract.getContractId(), contract.getPropertyId(),
                            contract.getRenterNickname(), contract.getTenantNickname(), contract.getStipulationDate(),
                            contract.getCreationDate(),contract.getStartDate(), contract.getEndDate(),
                            contract.getPropertyPrice(), contract.getDeposit()));
                }
            }
        } catch (SQLException | ClassNotFoundException | ConfigException | ConfigFileException e) {
            e.printStackTrace();
        }

        return rentingContracts;
    }
}
