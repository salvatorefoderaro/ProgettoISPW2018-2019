package it.uniroma2.ispw.fersa.control;

import it.uniroma2.ispw.fersa.boundary.PerformContractRequestBoundary;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.DAO.*;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.bean.*;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PerformContractRequestSession {

    private int apartmentId;

    private String tenantNickname;

    private int rentalFeaturesId;

    private RentalFeatures rentalFeatures;

    private PerformContractRequestBoundary controller;

    private ContractRequest contractRequest;



    public PerformContractRequestSession(String tenantNickname, int rentalFeaturesId, int apartmentId) {

        this.apartmentId = apartmentId;
        this.tenantNickname = tenantNickname;
        this.rentalFeaturesId = rentalFeaturesId;




    }

    public RentableInfoBean makeNewRequest() {
        this.rentalFeatures = RentalFeaturesDAO.getInstance().getRentalFeatures(this.rentalFeaturesId);

        List<String> intervalDates = new ArrayList<String>();

        rentalFeatures.getAvaibility().forEach(intervalDate -> intervalDates.add(intervalDate.toString()));

        Rentable rentable = RentableDAO.getInstance().getRentable(this.rentalFeaturesId);

        RentableInfoBean rentableInfoBean = new RentableInfoBean(rentable.getName(), rentable.getImage(), rentable.getDescription(), rentalFeatures.getDescription(), rentalFeatures.getPrice(), rentalFeatures.getDeposit(), intervalDates);

        EquippedApt equippedApt = EquippedAptDAO.getInstance().getEquippedApt(this.apartmentId);

        this.contractRequest = new ContractRequest(equippedApt.getRenterNickname(), this.tenantNickname, rentable, this.rentalFeatures.getPrice(), this.rentalFeatures.getDeposit());

        return rentableInfoBean;
    }

    public ContractTypeBean selectContract(String contractTypeName) {
        ContractType contractType = ContractTypeDAO.getIstance().getContractType(contractTypeName);
        this.contractRequest.setContractType(contractType);

        return new ContractTypeBean(contractType.getContractTypeId(), contractType.getName(), contractType.getDescription(), contractType.getMinDuration(), contractType.getMaxDuration());
    }

    public List<ServiceBean> getAllServices(){
        List<Service> services = ServiceDAO.getInstance().getAllServices(this.apartmentId);
        List<ServiceBean> serviceBeans = new ArrayList<ServiceBean>();
        services.forEach(service -> serviceBeans.add(new ServiceBean(service.getId() ,service.getName(), service.getDescriprion(), service.getPrice())));
        return serviceBeans;
    }

    public ContractNamesBean getAllContractTypes() {
        List<ContractType> contractTypes = ContractTypeDAO.getIstance().getAllContractTypes();

        List<String> contractNames = new ArrayList<>();

        contractTypes.forEach(contractType -> contractNames.add(contractType.getName()));

        return new ContractNamesBean(contractNames);
    }

    public ResponseBean setPeriod(LocalDate start, LocalDate end) {

        if (!this.rentalFeatures.checkPeriod(start, end)) {
            return new ResponseBean(ResponseEnum.ERROR, "Il periodo inserito non è disponibile!");
        }

        try {
            this.contractRequest.insertPeriod(start, end);
        } catch (PeriodException e) {
            return new ResponseBean(ResponseEnum.ERROR, e.toString());
        }

        return new ResponseBean(ResponseEnum.OK, "Periodo inserito correttamente");


    }

    public ResponseBean setServices(List<ServiceBean> serviceBeans) {
        List<Service> services = new ArrayList<>();

        serviceBeans.forEach(serviceBean -> services.add(ServiceDAO.getInstance().getService(serviceBean.getServiceId())));

        this.contractRequest.setServices(services);

        return new ResponseBean(ResponseEnum.OK, "Servizi inseriti correttamente");
    }






}
