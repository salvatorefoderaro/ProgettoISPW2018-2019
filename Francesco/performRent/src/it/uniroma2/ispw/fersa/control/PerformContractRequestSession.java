package it.uniroma2.ispw.fersa.control;

import it.uniroma2.ispw.fersa.rentingManagement.DAO.*;
import it.uniroma2.ispw.fersa.rentingManagement.bean.*;
import it.uniroma2.ispw.fersa.rentingManagement.entity.*;
import it.uniroma2.ispw.fersa.rentingManagement.exception.*;

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

    public RentableInfoBean makeNewRequest() throws ClassNotFoundException, ConfigException, ConfigFileException,
            SQLException, NotFoundException, IOException {
        this.rentalFeatures = RentalFeaturesDAO.getInstance().getRentalFeatures(this.rentalFeaturesId);

        List<String> intervalDates = new ArrayList<>();

        rentalFeatures.getAvailability().forEach(intervalDate -> intervalDates.add(intervalDate.toString()));

        Property property = PropertyDAO.getInstance().getRentableByRentalFeaturesId(this.rentalFeaturesId);

        if (property == null) throw new NotFoundException("Errore: informazioni sull'immobile selezionato non trovate");

        RentableInfoBean rentableInfoBean = new RentableInfoBean(property.getName(), property.getImage(),
                property.getType(), property.getDescription(), rentalFeatures.getDescription(),
                rentalFeatures.getPrice(), rentalFeatures.getDeposit(), intervalDates);

        EquippedApt equippedApt = EquippedAptDAO.getInstance().getEquippedAptById(this.apartmentId);

        this.contractRequest = new ContractRequest(equippedApt.getRenterNickname(), this.tenantNickname,
                property.getRentableId(), this.rentalFeatures.getPrice(), this.rentalFeatures.getDeposit());

        return rentableInfoBean;
    }

    public ContractTypeBean getContractType(String contractTypeName) throws ClassNotFoundException, SQLException,
            ConfigException, ConfigFileException, NotFoundException {
        ContractType contractType = ContractTypeDAO.getIstance().getContractTypeByName(contractTypeName);
        if (contractType == null) throw new NotFoundException("Errore: contratto selezionato non trovato");
        return new ContractTypeBean(contractType.getContractTypeId(), contractType.getName(),
                contractType.getDescription(), contractType.getMinDuration(), contractType.getMaxDuration());
    }

    public void selectContract(String contractTypeName) throws ClassNotFoundException, SQLException, ConfigException,
            ConfigFileException, NotFoundException, ContractPeriodException {
        ContractType contractType = ContractTypeDAO.getIstance().getContractTypeByName(contractTypeName);
        if (contractType == null) throw new NotFoundException("Errore: contratto selezionato non trovato");
        this.contractRequest.setContractType(contractType);
    }

    public List<ServiceBean> getAllServices() throws ConfigFileException, ConfigException, ClassNotFoundException,
            SQLException {
        List<Service> services = ServiceDAO.getInstance().getServicesByAptId(this.apartmentId);
        List<ServiceBean> serviceBeans = new ArrayList<>();
        services.forEach(service -> serviceBeans.add(new ServiceBean(service.getId() ,service.getName(),
                service.getDescriprion(), service.getPrice())));
        return serviceBeans;
    }

    public ContractNamesBean getAllContractTypes() throws ClassNotFoundException, SQLException, ConfigException,
            ConfigFileException {
        List<ContractType> contractTypes = ContractTypeDAO.getIstance().getAllContractTypes();

        List<String> contractNames = new ArrayList<>();

        contractTypes.forEach(contractType -> contractNames.add(contractType.getName()));

        return new ContractNamesBean(contractNames);
    }

    public void setPeriod(LocalDate start, LocalDate end) throws ContractPeriodException, PeriodException {
        if (!this.rentalFeatures.checkPeriod(start, end)) throw new PeriodException();
        this.contractRequest.insertPeriod(new DateRange(start, end));
    }

    public void setServices(List<ServiceBean> serviceBeans) throws ConfigFileException, ConfigException,
            ClassNotFoundException, SQLException{
        List<Service> services = new ArrayList<>();


        for (ServiceBean serviceBean : serviceBeans) {
            services.add(ServiceDAO.getInstance().getServicesByContractRequestId(serviceBean.getServiceId()));
        }

        this.contractRequest.setServices(services);

    }

    public ContractRequestInfoBean getSummary() throws IncompleteException {

        if (!this.contractRequest.check()) throw new IncompleteException();

        List<Service> services = this.contractRequest.getServices();

        List<ServiceBean> serviceBeans = new ArrayList<>();

        services.forEach(service -> serviceBeans.add(new ServiceBean(service.getId(), service.getName(),
                service.getDescriprion(), service.getPrice())));

        return new ContractRequestInfoBean(this.contractRequest.getContractName(),
                this.contractRequest.getStartDate(), this.contractRequest.getEndDate(),
                this.contractRequest.getPropertyPrice(),
                this.contractRequest.getDeposit(),serviceBeans, this.contractRequest.getTotal());
    }

    public void sendRequest() throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException,
            ContractPeriodException {
        List<Service> services = this.contractRequest.getServices();

        int length = services.size();

        int serviceIds[] =  new int[length];

        for (int i = 0; i < length; i++) {
            serviceIds[i] = services.get(i).getId();
        }
        ContractRequestBean contractRequestBean = new ContractRequestBean(this.contractRequest.getRenterNickname(),
                this.contractRequest.getTenantNickname(), this.apartmentId,this.rentalFeaturesId,
                this.contractRequest.getContractTypeId(), this.contractRequest.getStartDate(),
                this.contractRequest.getEndDate(), this.contractRequest.getPropertyPrice(),
                this.contractRequest.getDeposit(), serviceIds);

        ContractRequestDAO.getInstance().insertNewRequest(contractRequestBean);

    }






}
