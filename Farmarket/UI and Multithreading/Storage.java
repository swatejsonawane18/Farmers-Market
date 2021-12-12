import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Storage {
     private String username;
     private int times_purchased=0;
     private double amount=0;


     Storage(){
         username="";
         times_purchased=0;
         amount=0.0;
     }


     public void update_value(int shopid,int custid,double new_cost){
        try{
             amount+=new_cost;
             times_purchased+=1;
             String query = "update storage_database set amount="+(amount)+",count="+(times_purchased)+" where shopid=" + shopid + " and customerid=" + custid + ";";
             Database.executeUpdate(query);
         }
         catch (Exception e){
             System.out.println(e);
         }
    }
     public int show_times_purchased(){
         return this.times_purchased;
     }

    public double show_amount(){
        return this.amount;
    }

    public void add_custdata(int shop_id,int cust_id,String cust_name,double newamount){
        try {
            ResultSet rs;
            String query = "select * from storage_database where shopid=" + shop_id + " and customerid=" + cust_id + ";";
            rs = Database.executeQuery(query);

            if (rs.next()) {
                this.times_purchased=rs.getInt(5);
                this.amount=rs.getDouble(4);
                //update_value(cust_id, shop_id, amount);
                times_purchased+=1;
                amount+=newamount;
                query = "update storage_database set amount="+(amount)+",count="+(times_purchased)+" where shopid=" + shop_id + " and customerid=" + cust_id + ";";
                Database.executeUpdate(query);
            }
            else {
                System.out.println("Inserting details...");
                query = "insert into storage_database values (" + shop_id + ", " + cust_id + ", '" + cust_name + "', " + newamount + ", "+1+");";
                Database.executeUpdate(query);
            }
        }
        catch (Exception e){
            System.out.println("Storage");
            System.out.println(e);
        }
    }

}
