package Object;

/**
 * 双方确认拜访的时间地点后，生成此项并放入看房记录表中
 */
public class VisitRecord {
    private String houseID;
    private String homeownerID;
    private String tenantID;
    private String payTime;
    private String appointedTime;

    public VisitRecord(String houseID,String homeownerID,String tenantID,String payTime,String appointedTime){
        this.houseID=houseID;
        this.homeownerID=homeownerID;
        this.tenantID=tenantID;
        this.payTime=payTime;
        this.appointedTime=appointedTime;
    }
    //Construct by Communication Authority
    public VisitRecord(String houseID,CommunicationAuthority communicationAuthority,String payTime,String appointedTime){
        this(houseID,communicationAuthority.getHomeownerID(),communicationAuthority.getTenantID(),payTime,appointedTime);
    }



    //get

    public String getHouseID() {
        return houseID;
    }

    public String getHomeownerID() {
        return homeownerID;
    }

    public String getTenantID() {
        return tenantID;
    }

    public String getPayTime() {
        return payTime;
    }

    public String getAppointedTime() {
        return appointedTime;
    }
}
