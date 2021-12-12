
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class Shops{
    Scanner s = new Scanner(System.in);


    int shopid=0;

    private Socket connectionSocket = null ;
    private DataInputStream inFromClient = null;
    private DataOutputStream outToClient = null;


    Shops(Socket connectionSocket ,DataInputStream inFromClient ,DataOutputStream outToClient) {
        this.connectionSocket =connectionSocket ;
        this.inFromClient = inFromClient;
        this.outToClient = outToClient;
    }

    public static void view_whole_database(String cat) {
        try {
            ResultSet rs;
            String query = "";
            query = "select * from shop_database where category='" + cat + "';";
            rs = Database.executeQuery(query);
            int f = 0;
            while (rs.next()) {
                f = 1;
                //System.out.println("Product - " + rs.getString(2) + "      Cost - " + rs.getDouble(3) + "      Category - " + rs.getString(4));

            }
            if (f == 0) {
                System.out.println("DATABASE EMPTY!!");
            }
            System.out.println("********************************************");
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void view_database(int shopid,String category){
        try {

            ResultSet rs;
            String query = "select * from shop_database where shopid='" + shopid + "' and category='"+category+"';";
            rs = Database.executeQuery(query);
            int f=0;
            while(rs.next()){
                f=1;
                outToClient.writeInt(f);
                outToClient.writeUTF(rs.getString(2));
                outToClient.writeDouble(rs.getDouble(3));
                outToClient.writeUTF(rs.getString(4));
                //outToClient.writeUTF("Product - "+rs.getString(2)+"      Cost - "+rs.getDouble(3)+"      Category - "+rs.getString(4));
            }
            if(f==0){
                outToClient.writeInt(f);
                //outToClient.writeUTF("DATABASE EMPTY!!");
                return;
            }
            f=0;
            outToClient.writeInt(f);
            //outToClient.writeUTF("-----------------------------------------------------------");
            return;
        }
        catch(Exception e){
            System.out.println(e);
        }
    }



    public void display_custdata(){
        try {

            ResultSet rs;
            String query = "select * from storage_database where shopid=" + shopid + ";";
            rs = Database.executeQuery(query);
            int f=0;
            while (rs.next()) {
                f=1;
                outToClient.writeInt(f);
                outToClient.writeUTF(rs.getString(3));
                outToClient.writeInt(rs.getInt(5));
                outToClient.writeDouble(rs.getDouble(4));
                //outToClient.writeUTF("Customer: " + rs.getString(3) + " | No. of time purchased from the shop : " + rs.getInt(5) + " | Total Amount : " + rs.getDouble(4) );
            }
            if(f==0){
                outToClient.writeInt(f);
                //outToClient.writeUTF("DATABASE EMPTY!!");
                return;
            }
            f=0;
            outToClient.writeInt(f);
            //outToClient.writeUTF("-----------------------------------------------------------");
        }
        catch (Exception e){
            System.out.println(e);
        }
    }


    public void add(int shopid,String product,Double cost,String category){
        try {

            String query = "insert into shop_database values(" + shopid + ",'" + product + "'," + cost + ",'" + category + "');";
            Database.executeUpdate(query);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public boolean remove(int shopid,String product,String category) {
        try{

            String query="SELECT product FROM shop_database WHERE shopid="+shopid+" and product='"+product+"';";
            ResultSet rs = Database.executeQuery(query);
            if(!rs.next()){
                return false;
            }
            query = "delete from shop_database where shopid="+shopid+" and product='"+product+"' and category ='"+category+"';";
            Database.executeUpdate(query);
            return true;
        }
        catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    public boolean modify(int shopid, String product, double new_cost) {
        try{

            String query="SELECT product FROM shop_database WHERE shopid="+shopid+" and product='"+product+"';";
            ResultSet rs = Database.executeQuery(query);
            if(!rs.next()){
                return false;
            }
            query = "update shop_database set cost="+new_cost+" where product='"+product+"' and shopid="+shopid+";";
            Database.executeUpdate(query);
            return true;
        }
        catch (Exception e){
            System.out.println(e);
            return false;
        }
    }


    public void menu(String category) {
        int ch = 0;
        try {
            while (true) {
//                System.out.println("Category Menu - ");
//                System.out.println("1. View Storage database ");
//                System.out.println("2. Add Product ");
//                System.out.println("3. Remove Product ");
//                System.out.println("4. Modify cost of a Product ");
//                System.out.println("5. Quit");
//                System.out.println();
//                System.out.println("Enter your choice - ");
                ch = inFromClient.readInt();
             //System.out.println(ch);
                if (ch == 1) {
                    view_database(shopid,category);
                } else if (ch == 2) {
                    //System.out.println("Product name -");
                    String name = inFromClient.readUTF();
                    name = name.toUpperCase();
                    //System.out.println("Product cost -");
                    double cost = inFromClient.readDouble();
                    add(shopid, name, cost,category);
                } else if (ch == 3) {
//                    System.out.println("Product name -");
                    String name = inFromClient.readUTF();
                    name = name.toUpperCase();
                    boolean f = false;
                    f=remove(shopid, name,category);
                    if(f==false) {
                        outToClient.writeUTF("Item Name NOT FOUND!!");
                    }
                    else
                        outToClient.writeUTF("Product deleted!");
                } else if (ch == 4) {
//                    System.out.println("Product name - ");
                    String name = inFromClient.readUTF();
                    name = name.toUpperCase();
//                    System.out.println("New Product cost -");
                    double cost = inFromClient.readDouble();
                    boolean f = false;
                     f=modify(shopid, name, cost);
                     if(f==false) {
                        outToClient.writeUTF("Item Name NOT FOUND!!");
                    }
                     else
                         outToClient.writeUTF("Item Cost Modified!!");
                } else if (ch == 5) {
                    outToClient.writeInt(0);
                    return;
                }
            }
        } catch (Exception e) {
            System.out.println("In catch of menu of shop");
            System.out.println(e);
        }
    }


    public void main1(int shopid) {
        this.shopid=shopid;
        try {

            System.out.println("Connection from " + connectionSocket);
            String cat="";
            while (true) {
                try {
//                    System.out.println("********WELCOME TO THE STORE*********");
//                    System.out.println();
//                    System.out.println("********WHAT CATEGORY DO YOU WANT TO PERFORM OPERATIONS*********");
//                    System.out.println("1.INSECTICIDES");
//                    System.out.println("2.PESTICIDES");
//                    System.out.println("3.CROP SEEDS");
//                    System.out.println("4.TOOLS");
//                    System.out.println("5.DISPLAY SHOP DATABASE");
//                    System.out.println("6.CUSTOMER DATA");
//                    System.out.println("7.EXIT");
//                    System.out.println();
//                    System.out.println("Enter the Category you want (String) -");
                    //String cat = s.nextLine();
                    cat = inFromClient.readUTF();
                    cat = cat.trim();
                    //System.out.println(cat);
                    if (!(cat.equalsIgnoreCase("INSECTICIDES") || cat.equalsIgnoreCase("CUSTOMER DATA") || cat.equalsIgnoreCase("DISPLAY SHOP DATABASE") || cat.equalsIgnoreCase("PESTICIDES") || cat.equalsIgnoreCase("CROP SEEDS") || cat.equalsIgnoreCase("TOOLS") || cat.equalsIgnoreCase("EXIT"))) {
                        //System.out.println("Option not available!");
                        continue;
                    }
                    else {
                        if (cat.equalsIgnoreCase("INSECTICIDES")) {
                            menu("INSECTICIDES");
                        }
                        else if (cat.equalsIgnoreCase("PESTICIDES")) {
                            menu("PESTICIDES");
                        }
                        else if (cat.equalsIgnoreCase("CROP SEEDS")) {
                            menu("CROP SEEDS");
                        }
                        else if (cat.equalsIgnoreCase("TOOLS")) {
                            menu("TOOLS");
                        }
                        else if (cat.equalsIgnoreCase("DISPLAY SHOP DATABASE")) {
                            //System.out.println("INSECTICIDES - ");
                            //view_hashmap(shop_storage_insecticides);
                            view_database(shopid,"INSECTICIDES");
                            //System.out.println();
                            //System.out.println("PESTICIDES - ");
                            //view_hashmap(shop_storage_pesticides);
                            view_database(shopid,"PESTICIDES");
                            //System.out.println();
                            //System.out.println("CROP SEEDS - ");
                            //view_hashmap(shop_storage_cropseeds);
                            view_database(shopid,"CROP SEEDS");
                            //System.out.println();
                            //System.out.println("TOOLS - ");
                            //view_hashmap(shop_storage_tools);
                            view_database(shopid,"TOOLS");
                            //System.out.println();
                             continue;
                        }
                        else if(cat.equalsIgnoreCase("CUSTOMER DATA")){
                            display_custdata();
                        }
                        //else if (cat.equalsIgnoreCase("EXIT")) {
                        else{
                            return;
                        }
                    }
                }
                catch (Exception ex) {
                    System.out.println("In inner catch of while loop of shops");
                    System.out.println(ex);
                    break;
                }
            }
        }
        catch (Exception e) {
            System.out.println("In outer catch of while loop of shops");
            System.out.println(e);
        }
    }
}

