package it.uniroma2.ispw.fersa.control;

import it.uniroma2.ispw.fersa.rentingManagement.DAO.*;
import it.uniroma2.ispw.fersa.rentingManagement.bean.ContractTextBean;
import it.uniroma2.ispw.fersa.rentingManagement.bean.PropertyBean;
import it.uniroma2.ispw.fersa.rentingManagement.bean.ServiceBean;
import it.uniroma2.ispw.fersa.rentingManagement.entity.Contract;
import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractId;
import it.uniroma2.ispw.fersa.rentingManagement.entity.EquippedApt;
import it.uniroma2.ispw.fersa.rentingManagement.entity.Property;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public abstract class ContractHandlerSession {
    protected Contract contract;

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

        ContractTextBean contractTextBean = new ContractTextBean();

        contractTextBean.setContractName(this.contract.getContractTypeName());

        contractTextBean.setIntro("Il/la Sig./Sig.ra " + this.contract.getRenterSurname() + " " +
                this.contract.getRenterName() + " denominato/a locatore concede in locazione al Sig./Sig.ra " +
                this.contract.getTenantSurname() + " " + this.contract.getTenantName() + ", nato/a a "
                + this.contract.getTenantCityOfBirth() + " e residente a " +
                this.contract.getTenantAddress() + " C.F. " + this.contract.getTenantCF() + " di seguito denominato/a" +
                " conduttore, che accetta di per sè, una porzione dell'unità immobiliare posta in " +
                equippedApt.getAddress() + ". Il locatore dichiara che gli impianti sono a normza con le vigenti " +
                "normative in materia.\nLa locazione è regolata da: ");

        contractTextBean.setDuration("Il contratto è stipulato per la durata di " + this.contract.getNumMonths() +
                " mesi, a decorrere dal " + this.contract.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                + " e fino al " + this.contract.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ".");

        String transitory = null;

        if (this.contract.isTransitory()) {
            transitory = "Il contratto è stipulato per la durata di " + this.contract.getNumMonths() +
                    " mesi, a decorrere dal " + this.contract.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    + " e fino al " + this.contract.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ".";
        }

        contractTextBean.setTransitory(transitory);

        contractTextBean.setPayment("Il canone di locazione è convenuto complessivamente in euro "
                + this.contract.getNetPrice() + " che il conduttore si obbliga a corrispondere, rispettivamente, in "
                + this.contract.getNumMonths() + " rate eguali mensili entro i primi di ogni mese, ciascuna di euro "
                + (int) this.contract.getNetPrice() / this.contract.getNumMonths() + ".");

        contractTextBean.setDeposit("A garanzia delle obbligazioni assunte col presente contratto, " +
                "il conduttore versa al locatore (che con la firma del contratto ne rilascia, in caso, quietanza) " +
                "una somma di euro " + this.contract.getDeposit() + ".");

        contractTextBean.setResolution("Le obbligazioni di pagamento scaturenti dal presente contratto " +
                "costituiscono obbligazioni parziarie e divisibili ai sensi dell'art. 1314 C.C. e ciascuno dei " +
                "debitori non è tenuto a pagare il debito che per la sua parle. Le spese sono escluse dal canone " +
                "di locazione mensilmente corrisposto. 11 pagamento del canone o di quant'altro dovuto anche per" +
                " oneri accessori non può venire sospeso o ritardato da pretese o eccezioni del conduttore, quale ne" +
                " sia il titolo. Il mancato puntuale pagamento, per qualsiasi causa, anche di una sola rata del canone" +
                " (nonché di quant'altro dovuto, ove di imporlo pari almeno ad una mensilità del canone), " +
                "costituisce in mora il conduttore inadempiente, fatto salvo quanto previsto dall'articolo 55 " +
                "della Legge n. 392/78. La vendita dell'unità immobiliare locata - in relazione alla quale " +
                "non viene concessa la prelazione al conduttore - non costituisce motivo di risoluzione del contratto.");

        contractTextBean.setUse("L'immobile deve ssere destinato esclusivamente ad uso di civile abitazione dei " +
                "conduttori . È fatto divieto di sublocare o dare in comodato, int tutto o in parte, l'unità immobiliare, " +
                "pena la risoluzione i diritto del contratto. Il conduttore dovrà riconsegnare l ímmobile pulito ed integro, " +
                "in ogni sua componente anche mobiliare; nel caso in cui si dovessero riscontrare es. muri sporchi, mobili, " +
                "oggetti rotti o quant'altro, il conduttore si impegna a ripristinare il tutto a proprie spese.");

        contractTextBean.setVarious("Per quanto non previsto dal presente contratto le parti rinviano a quanto disposto in " +
                "materia dal Codice Civile, dalle Leggi n. 392/78 e n. 431/98 o ccomunque dalle normi vigenti, " +
                "dagli usi locali e dagli Accordi Territoriali.");

        return contractTextBean;

    }

    public PropertyBean getPropertyInfo()  throws SQLException, ClassNotFoundException, ConfigException,
            ConfigFileException, IOException {
        EquippedApt apt = EquippedAptDAO.getInstance().getEquippedAptByContractId(this.contract.getContractId());

        Property property = PropertyDAO.getInstance().getRentableByContractId(
                this.contract.getContractId());
        return new PropertyBean(apt.getAddress(), property.getName(), property.getImage(), property.getType(),
                property.getDescription());
    }



}
