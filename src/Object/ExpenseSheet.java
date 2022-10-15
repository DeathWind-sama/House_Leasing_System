package Object;

public class ExpenseSheet {
    private String sheetID;
    private String payerID;
    private String payerName;
    private double payAmount;
    private boolean isPayed;

    public ExpenseSheet(String sheetID,String payerID,String payerName,double payAmount,boolean isPayed){
        this.sheetID=sheetID;
        this.payerID=payerID;
        this.payerName=payerName;
        this.payAmount=payAmount;
        this.isPayed=isPayed;
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

    public double getPayAmount() {
        return payAmount;
    }

    public boolean getIsPayed(){
        return isPayed;
    }

    //set
    public void setIsPayed(boolean isPayed){
        this.isPayed=isPayed;
    }
}
