package it.uniroma2.ispw.fersa.rentingManagement.bean;

import java.time.LocalDate;
import java.util.List;

public class ContractTextBean {
    private String contractName;
    private String intro;
    private String duration;
    private String transitory;
    private String payment;
    private String deposit;
    private String resolution;
    private String use;
    private String various;

    public ContractTextBean(){}

    public String getContractName() {
        return contractName;
    }

    public String getIntro() {
        return intro;
    }

    public String getDuration() {
        return duration;
    }

    public String getTransitory() {
        return transitory;
    }

    public String getPayment() {
        return payment;
    }

    public String getDeposit() {
        return deposit;
    }

    public String getResolution() {
        return resolution;
    }

    public String getUse() {
        return use;
    }

    public String getVarious() {
        return various;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setTransitory(String transitory) {
        this.transitory = transitory;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public void setUse(String use) {
        this.use = use;
    }

    public void setVarious(String various) {
        this.various = various;
    }
}



