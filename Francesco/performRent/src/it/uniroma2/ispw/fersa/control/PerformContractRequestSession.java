package it.uniroma2.ispw.fersa.control;

import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.DAO.*;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.bean.*;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.*;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.exception.ConfigFileException;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.exception.PeriodException;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PerformContractRequestSession {

    private int apartmentId;

    private String tenantNickname;

    private int rentalFeaturesId;

    private RentalFeatures rentalFeatures;

    private ContractRequest contractRequest;



    public PerformContractRequestSession(String tenantNickname, int rentalFeaturesId, int apartmentId) {

        this.apartmentId = apartmentId;
        this.tenantNickname = tenantNickname;
        this.rentalFeaturesId = rentalFeaturesId;

    }

    public RentableInfoBean makeNewRequest() {
        this.rentalFeatures = RentalFeaturesJDBC.getInstance().getRentalFeatures(this.rentalFeaturesId);

        List<String> intervalDates = new ArrayList<>();

        rentalFeatures.getAvaibility().forEach(intervalDate -> intervalDates.add(intervalDate.toString()));

        Rentable rentable = RentableJDBC.getInstance().getRentable(this.rentalFeaturesId);

        RentableInfoBean rentableInfoBean = new RentableInfoBean(rentable.getName(), rentable.getImage(), rentable.getDescription(), rentalFeatures.getDescription(), rentalFeatures.getPrice(), rentalFeatures.getDeposit(), intervalDates);

        EquippedApt equippedApt = EquippedAptJDBC.getInstance().getEquippedApt(this.apartmentId);

        this.contractRequest = new ContractRequest(equippedApt.getRenterNickname(), this.tenantNickname, rentable, this.rentalFeatures.getPrice(), this.rentalFeatures.getDeposit());

        return rentableInfoBean;
    }

    public ContractTypeBean getContractType(String contractTypeName) {
        ContractType contractType = ContractTypeJDBC.getIstance().getContractTypeByName(contractTypeName);
        return new ContractTypeBean(contractType.getContractTypeId(), contractType.getName(), contractType.getDescription(), contractType.getMinDuration(), contractType.getMaxDuration());
    }

    public ResponseBean selectContract(String contractTypeName) {
        ContractType contractType = ContractTypeJDBC.getIstance().getContractTypeByName(contractTypeName);
        this.contractRequest.setContractType(contractType);
        if (this.contractRequest.hasIntervalDate()) {
            LocalDate startDate = contractRequest.getStartDate(); //TODO Verificare la presenza effettiva della data
            LocalDate endDate = contractRequest.getEndDate();
            this.contractRequest.clearPeriod();

            try {
                this.contractRequest.insertPeriod(new IntervalDate(startDate, endDate));
            } catch (PeriodException e) {
                return new ResponseBean(ResponseEnum.ERROR, "Contratto innserito correttamente ma il periodo non soddisfa più i requisiti!");
            }
        }
        return new ResponseBean(ResponseEnum.OK, "Contratto inserito correttamente");


    }

    public List<ServiceBean> getAllServices() throws ConfigFileException, ConfigException, ClassNotFoundException, SQLException {
        List<Service> services = ServiceJDBC.getInstance().getServicesByAptId(this.apartmentId);
        List<ServiceBean> serviceBeans = new ArrayList<>();
        services.forEach(service -> serviceBeans.add(new ServiceBean(service.getId() ,service.getName(), service.getDescriprion(), service.getPrice())));
        return serviceBeans;
    }

    public ContractNamesBean getAllContractTypes() {
        List<ContractType> contractTypes = ContractTypeJDBC.getIstance().getAllContractTypes();

        List<String> contractNames = new ArrayList<>();

        contractTypes.forEach(contractType -> contractNames.add(contractType.getName()));

        return new ContractNamesBean(contractNames);
    }

    public ResponseBean setPeriod(LocalDate start, LocalDate end) {

        if (!this.contractRequest.hasContractType()) return new ResponseBean(ResponseEnum.ERROR, "Inserire prima un contratto!");

        if (!this.rentalFeatures.checkPeriod(start, end)) {
            return new ResponseBean(ResponseEnum.ERROR, "Il periodo inserito non è disponibile!");
        }

        try {
            this.contractRequest.insertPeriod(new IntervalDate(start, end));
        } catch (PeriodException e) {
            return new ResponseBean(ResponseEnum.ERROR, e.toString());
        }

        return new ResponseBean(ResponseEnum.OK, "Periodo inserito correttamente");


    }

    public void setServices(List<ServiceBean> serviceBeans) throws ConfigFileException, ConfigException, ClassNotFoundException, SQLException{
        List<Service> services = new ArrayList<>();


        for (ServiceBean serviceBean : serviceBeans) {
            services.add(ServiceJDBC.getInstance().getServiceById(serviceBean.getServiceId()));
        }

        this.contractRequest.setServices(services);

    }

    public ContractRequestInfoBean getSummary() {

        List<Service> services = this.contractRequest.getServices();

        List<ServiceBean> serviceBeans = new ArrayList<>();

        services.forEach(service -> serviceBeans.add(new ServiceBean(service.getId(), service.getName(),
                service.getDescriprion(), service.getPrice())));

        return new ContractRequestInfoBean(this.contractRequest.getContractName(),
                this.contractRequest.getStartDate(), this.contractRequest.getEndDate(), this.contractRequest.getRentablePrice(),
                this.contractRequest.getDeposit(),serviceBeans, this.contractRequest.getTotal());
    }

    public ResponseBean sendRequest() {
        List<Service> services = this.contractRequest.getServices();

        int length = services.size();

        int serviceIds[] =  new int[length];

        for (int i = 0; i < length; i++) {
            serviceIds[i] = services.get(i).getId();
        }
        ContractRequestBean contractRequestBean = new ContractRequestBean(this.contractRequest.getRenterNickname(), this.contractRequest.getTenantNickname(), this.rentalFeaturesId, this.contractRequest.getContractId(), this.contractRequest.getStartDate(), this.contractRequest.getEndDate(), this.contractRequest.getRentablePrice(), this.contractRequest.getDeposit(), serviceIds);

        ContractRequestJDBC.getInstance().insertNewRequest(contractRequestBean);

        return new ResponseBean(ResponseEnum.OK, "La richiesta è stata inserita correttamene!");
    }






}
