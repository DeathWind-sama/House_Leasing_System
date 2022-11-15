package object;

import dao.SnowFlake;
import object.enums.HouseTypeEnum;

public class House {//地址、房型（如平房、带阳台的楼房、独立式住宅等）、最多容纳房客数、租金及房屋状态（待租赁、已出租）。
    //important information
    private String houseID;
    private String ownerID;//主人信息
    private boolean isLeased;//是否被租出
    private boolean isAbleSearched;//可否被检索
    //other information
    private String address;
    private HouseTypeEnum houseType;//可能可以改成String
    private int maxTenantsNum;
    private double monthlyRent;

    public House(){}

    public House(String houseID,String ownerID,boolean isLeased,boolean isAbleSearched,
                 String address,HouseTypeEnum houseType,int maxTenantsNum,double monthlyRent){
        this.houseID=houseID;
        this.ownerID=ownerID;
        this.isLeased=isLeased;
        this.isAbleSearched=isAbleSearched;
        this.address=address;
        this.houseType=houseType;
        this.maxTenantsNum=maxTenantsNum;
        this.monthlyRent=monthlyRent;
    }

    public House(String ownerID,boolean isAbleSearched,
                 String address,HouseTypeEnum houseType,int maxTenantsNum,double monthlyRent){
        this.ownerID=ownerID;
        this.isAbleSearched=isAbleSearched;
        this.address=address;
        this.houseType=houseType;
        this.maxTenantsNum=maxTenantsNum;
        this.monthlyRent=monthlyRent;
        this.isLeased=false;
        this.houseID= SnowFlake.snowGenString();
    }



    //get

    public String getHouseID() {
        return houseID;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public boolean getIsLeased(){
        return isLeased;
    }

    public boolean getIsAbleSearched(){
        return isAbleSearched;
    }

    public String getAddress() {
        return address;
    }

    public HouseTypeEnum getHouseType() {
        return houseType;
    }

    public int getMaxTenantsNum() {
        return maxTenantsNum;
    }

    public double getMonthlyRent() {
        return monthlyRent;
    }
}
