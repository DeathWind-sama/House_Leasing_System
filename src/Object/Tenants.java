package Object;

public class Tenants extends People{
    private String birthday;
    private GenderEnum gender;

    public Tenants(String name,String ID,String address,String telNumber,String birthday,GenderEnum gender){
        super(name,ID,address,telNumber);
        this.birthday=birthday;
        this.gender=gender;
    }



    //get

    public String getBirthday() {
        return birthday;
    }

    public GenderEnum getGender() {
        return gender;
    }
}
