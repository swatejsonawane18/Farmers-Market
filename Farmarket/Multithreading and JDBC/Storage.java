import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Storage {
     private String username;
     private int times_purchased=0;
     private double amount=0;

    static Connection con=null;
    static final String url = "jdbc:mysql://localhost:3306/farmarket_schema";
    static final  String name = "root";
    static final String pass = "$w@teJ1800";

    static {
        try {
            con = DriverManager.getConnection(url,name,pass);
        }catch (Exception e){
            System.out.println(e);
        }
    }

     Storage(){
         username="";
         times_purchased=0;
         amount=0.0;
     }


     public void update_value(int shopid,int custid,double new_cost){
        try{
             Statement st = con.createStatement();
             ResultSet rs;
             amount+=new_cost;
             times_purchased+=1;
             String query = "update storage_database set amount="+(amount)+",count="+(times_purchased)+" where shopid=" + shopid + " and customerid=" + custid + ";";
             st.executeQuery(query);
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

    public void add_custdata(int shop_id,int cust_id,String cust_name,double amount){
        /*if(!shop_customerdata.containsKey(cust_name)){
            Storage st=new Storage("name");
            st.update_value(cost);
            shop_customerdata.put(cust_name,st);
        }
        else{
            shop_customerdata.get(cust_name).update_value(cost);
        }*/
        try {
            Statement st = con.createStatement();
            ResultSet rs;
            String query = "select * from storage_database where shopid=" + shop_id + " and customerid=" + cust_id + ";";
            rs = st.executeQuery(query);

            if (rs.next()) {
                this.times_purchased=rs.getInt(5);
                this.amount=rs.getDouble(4);
                update_value(cust_id, shop_id, amount);
            }
            else {
                query = "insert into storage_database values (" + shop_id + "," + cust_id + ",'" + cust_name + "'," + amount + ","+1+");";
                st.executeUpdate(query);
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

}
