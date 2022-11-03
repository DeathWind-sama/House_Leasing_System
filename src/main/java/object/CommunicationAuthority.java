package object;

/**
 * 付费后，生成此项并放入看房申请表中
 */
public class CommunicationAuthority {
    private String homeownerID;
    private String tenantID;

    private String authorityID;

    private String houseID;


    public CommunicationAuthority(String homeownerID,String tenantID,String authorityID,String houseID){
        this.homeownerID=homeownerID;
        this.tenantID=tenantID;
        this.authorityID=authorityID;
        this.houseID=houseID;
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
}
