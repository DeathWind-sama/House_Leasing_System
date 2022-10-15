package Object;

public abstract class People {
    private String name;
    private String address;
    private String telNumber;

    public People(String name,String address,String telNumber){
        this.name=name;
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
