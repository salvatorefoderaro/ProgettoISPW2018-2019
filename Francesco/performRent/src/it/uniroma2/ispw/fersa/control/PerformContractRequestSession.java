package it.uniroma2.ispw.fersa.control;

import it.uniroma2.ispw.fersa.boundary.PerformContractRequestBoundary;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.DAO.ContractTypeDAO;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.DAO.RentableDAO;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.DAO.RentalFeaturesDAO;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.DAO.ServiceDAO;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.bean.ContractTypeBean;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.bean.RentableInfoBean;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.bean.ServiceBean;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.*;

import java.util.ArrayList;
import java.util.List;

public class PerformContractRequestSession {

    private int apartmentId;

    private String tenantNickname;

    private int rentalFeaturesId;

    private PerformContractRequestBoundary controller;

    private ContractRequest contractRequest;



    public PerformContractRequestSession(String tenantNickname, int rentalFeaturesId, int apartmentId) {

        this.apartmentId = apartmentId;
        this.tenantNickname = tenantNickname;
        this.rentalFeaturesId = rentalFeaturesId;




    }

    public RentableInfoBean makeNewRequest() {
        RentalFeatures rentalFeatures = RentalFeaturesDAO.getInstance().getRentalFeatures(this.rentalFeaturesId);

        List<String> intervalDates = new ArrayList<String>();

        rentalFeatures.getAvaibility().forEach(intervalDate -> intervalDates.add(intervalDate.toString()));

        Rentable rentable = RentableDAO.getInstance().getRentable(this.rentalFeaturesId);

        RentableInfoBean rentableInfoBean = new RentableInfoBean(rentable.getName(), rentable.getImage(), rentable.getDescription(), rentalFeatures.getDescription(), rentalFeatures.getPrice(), rentalFeatures.getDeposit(), intervalDates);

        this.contractRequest = new ContractRequest(this.tenantNickname, "", rentalFeatures.getPrice(), rentalFeatures.getDeposit());

        return rentableInfoBean;
    }

    public ContractTypeBean selectContract(String contractName) {
        ContractType contractType = ContractTypeDAO.getIstance().getContractType(contractName);
        this.contractRequest.setContractType(contractType);

        return new ContractTypeBean(contractType.getName(), contractType.getDescription(), contractType.getMinDuration(), contractType.getMaxDuration());
    }

    public List<ServiceBean> getServices(){
        List<Service> services = ServiceDAO.getInstance().getServices(this.apartmentId);
        List<ServiceBean> serviceBeans = new ArrayList<ServiceBean>();
        services.forEach(service -> serviceBeans.add(new ServiceBean(service.getName(), service.getDescriprion(), service.getPrice())));
        return serviceBeans;
    }

    public List<String> getAllContractNames() {
        return ContractTypeDAO.getIstance().getAllContractNames();
    }






}
