package object;

import dao.SnowFlake;

/**
 * 付费后，生成此项并放入看房申请表中
 */
public class CommunicationAuthority {
    private String authorityID;

    private String homeownerID;
    private String tenantID;
    private String houseID;

    private String appointedTime="";
    private String appointedPlace="";


    private boolean isHomeownerModifyAvailable=false;//whether it's homeowner's turn to decide


    public CommunicationAuthority(String homeownerID,String tenantID,String houseID){
        this.homeownerID=homeownerID;
        this.tenantID=tenantID;
        this.authorityID = SnowFlake.snowGenString();
        this.houseID=houseID;
    }

    public CommunicationAuthority(String homeownerID,String tenantID,String authorityID,String houseID,String appointedTime,String appointedPlace,boolean isHomeownerModifyAvailable){
        this.homeownerID=homeownerID;
        this.tenantID=tenantID;
        this.authorityID=authorityID;
        this.houseID=houseID;
        this.appointedTime=appointedTime;
        this.appointedPlace=appointedPlace;
        this.isHomeownerModifyAvailable=isHomeownerModifyAvailable;
    }



    //get

    public String getHomeownerID() {
        return homeownerID;
    }

    public String getTenantID() {
        return tenantID;
    }

    public String getAuthorityID(){return authorityID;}

    public String getHouseID(){return houseID;}

    public String getAppointedTime() {
        return appointedTime;
    }

    public String getAppointedPlace() {
        return appointedPlace;
    }

    public boolean getIsHomeownerModifyAvailable(){
        return isHomeownerModifyAvailable;
    }

    //set

    public void setAppointedPlace(String appointedPlace) {
        this.appointedPlace = appointedPlace;
    }

    public void setAppointedTime(String appointedTime) {
        this.appointedTime = appointedTime;
    }

    public void setHomeownerModifyAvailable(boolean homeownerModifyAvailable) {
        isHomeownerModifyAvailable = homeownerModifyAvailable;
    }
}
