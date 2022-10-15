package Object;

public abstract class People {
    private String name;
    private String ID;
    private String address;
    private String telNumber;

    public People(String name,String ID,String address,String telNumber){
        this.name=name;
        this.ID=ID;
        this.address=address;
        this.telNumber=telNumber;
    }



    //get

    public String getName(){
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getTelNumber() {
        return telNumber;
    }
}
