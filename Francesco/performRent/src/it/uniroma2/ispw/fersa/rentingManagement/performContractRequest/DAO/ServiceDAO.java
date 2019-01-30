package it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.Service;

import java.util.ArrayList;
import java.util.List;

public class ServiceDAO {
    private static ServiceDAO ourInstance = new ServiceDAO();

    private static List<Service> services;  //TODO eliminare dopo l'implementazione del db

    public static ServiceDAO getInstance() {

        return ourInstance;
    }

    protected ServiceDAO() {
        services = new ArrayList<Service>();
        for (int i = 0; i < 10; i++) {
            services.add(new Service("Service "+ i, "Description " + 1, i * 5));
        }
    }

    public List<Service> getServices(int apartmentId) {
        return services;
    }
}
