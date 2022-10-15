package Object;

/**
 * 付费后，生成此项并放入看房申请表中
 */
public class CommunicationAuthority {
    private String homeownerID;
    private String tenantID;

    public CommunicationAuthority(String homeownerID,String tenantID){
        this.homeownerID=homeownerID;
        this.tenantID=tenantID;
    }



    //get

    public String getHomeownerID() {
        return homeownerID;
    }

    public String getTenantID() {
        return tenantID;
    }
}
