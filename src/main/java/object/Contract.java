package object;

import dao.SnowFlake;

/**
 * 交易双方达成条约后共同申请注册合同，创建此项并加入合同表
 * 租金流通须依靠此项进行
 */
public class Contract {
    private String ContractID;
    private String homeownerID;
    private String tenantID;
    private double monthlyRent;
    private double rentArrears;

    private String houseID;

    public Contract(String ContractID,String homeownerID,String tenantID,double monthlyRent,double rentArrears,String houseID){
        this.ContractID=ContractID;
        this.homeownerID=homeownerID;
        this.tenantID=tenantID;
        this.monthlyRent=monthlyRent;
        this.rentArrears=rentArrears;
        this.houseID=houseID;
    }

    public Contract(String homeownerID, String tenantID, double monthlyRent, double rentArrears, String houseID) {
        this.homeownerID = homeownerID;
        this.tenantID = tenantID;
        this.monthlyRent = monthlyRent;
        this.rentArrears = rentArrears;
        this.houseID = houseID;
        this.ContractID = SnowFlake.snowGenString();
    }

    public String getHomeownerID() {
        return homeownerID;
    }

    public String getTenantID() {
        return tenantID;
    }

    public double getMonthlyRent() {
        return monthlyRent;
    }

    public double getRentArrears() {
        return rentArrears;
    }

    //set

    public void setMonthlyRent(double monthlyRent) {
        this.monthlyRent = monthlyRent;
    }

    public void setRentArrears(double rentArrears) {
        this.rentArrears = rentArrears;
    }

    public String getContractID() {return ContractID;}

    public String getHouseID(){return houseID;}
}
