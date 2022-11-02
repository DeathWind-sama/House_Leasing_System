package object;

public class ExpenseSheet {
    private String sheetID="NULL";//由Dao层生成或数据库给予
    private String payerID;
    private String payerName;
    private boolean isHomeowner;
    private double payAmount;
    private boolean isPayed=false;

    private String houseID;

    public ExpenseSheet(String payerID,String payerName,boolean isHomeowner,double payAmount){
        this.payerID=payerID;
        this.payerName=payerName;
        this.isHomeowner =isHomeowner;
        this.payAmount=payAmount;
    }
    public ExpenseSheet(String sheetID,String payerID,String payerName,boolean isHomeowner,double payAmount,boolean isPayed,String houseID){
        this(payerID,payerName,isHomeowner,payAmount);
        this.sheetID=sheetID;
        this.isPayed=isPayed;
        this.houseID=houseID;
    }



    //get

    public String getSheetID() {
        return sheetID;
    }

    public String getPayerID() {
        return payerID;
    }

    public String getPayerName() {
        return payerName;
    }

    public boolean getIsHomeOwner(){
        return isHomeowner;
    }

    public double getPayAmount() {
        return payAmount;
    }

    public boolean getIsPayed(){
        return isPayed;
    }

    public String getHouseID()
    {
        return houseID;
    }

    //set

    public void setSheetID(String sheetID) {
        this.sheetID = sheetID;
    }

    public void setIsPayed(boolean isPayed){
        this.isPayed=isPayed;
    }
}
