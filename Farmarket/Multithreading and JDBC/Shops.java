
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

public class Shops {
    Scanner s = new Scanner(System.in);


    int shopid=0;
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

    private Socket connectionSocket = null;
    private DataInputStream inFromClient = null;
    private DataOutputStream outToClient = null;

    String shop_name, email, phone, password;

    Shops() {
        shop_name = null;
        email = null;
        phone = null;
        password = null;
    }

    public void view_whole_database(String cat) {
        try {
            Statement st = con.createStatement();
            ResultSet rs;
            String query = "";
            query = "select * from shop_database where category='" + cat + "';";
            rs = st.executeQuery(query);
            int f = 0;
            while (rs.next()) {
                f = 1;
                System.out.println("Product - " + rs.getString(2) + "      Cost - " + rs.getDouble(3) + "      Category - " + rs.getString(4));
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

            //Class.forName("com.mysql.cj.jdbc.Driver");
            //Connection con = DriverManager.getConnection(url, name, pass);
            Statement st = con.createStatement();
            ResultSet rs;
            String query = "select * from shop_database where shopid='" + shopid + "' and category='"+category+"';";
            rs = st.executeQuery(query);
            int f=0;
            while(rs.next()){
                f=1;
                System.out.println("Product - "+rs.getString(2)+"      Cost - "+rs.getDouble(3)+"      Category - "+rs.getString(4));
            }
            if(f==0){
                System.out.println("DATABASE EMPTY!!");
            }
            System.out.println("********************************************");
        }
        catch(Exception e){
            System.out.println(e);
        }
    }



    public void display_custdata(){
        /*if (!shop_customerdata.isEmpty()) {
            for (String i : shop_customerdata.keySet()) {
                System.out.println("Customer: " + i.trim() + " | No. of time purchased from the shop : " + shop_customerdata.get(i).show_times_purchased() + " | Total Amount : " + shop_customerdata.get(i).show_amount() );
            }
        } else {
            System.out.println("CART IS EMPTY!");
        }*/

        try {
            Statement st = con.createStatement();
            ResultSet rs;
            String query = "select * from storage_database where shopid=" + shopid + ";";
            rs = st.executeQuery(query);

            while (rs.next()) {
                System.out.println("Customer: " + rs.getString(3) + " | No. of time purchased from the shop : " + rs.getInt(5) + " | Total Amount : " + rs.getDouble(4) );
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }


    public void add(int shopid,String product,Double cost,String category){
        try {
            Statement st = con.createStatement();
            String query = "insert into shop_database values(" + shopid + ",'" + product + "'," + cost + ",'" + category + "');";
            st.executeUpdate(query);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public boolean remove(int shopid,String product,String category) {
        try{
            Statement st= con.createStatement();
            String query="SELECT product FROM shop_database WHERE shopid="+shopid+" and product='"+product+"';";
            ResultSet rs = st.executeQuery(query);
            if(!rs.next()){
                return false;
            }
            query = "delete from shop_database where shopid="+shopid+" and product='"+product+"' and category ='"+category+"';";
            st.executeUpdate(query);
            return true;
        }
        catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    public boolean modify(int shopid, String product, double new_cost) {
        try{
            Statement st= con.createStatement();
            String query="SELECT product FROM shop_database WHERE shopid="+shopid+" and product='"+product+"';";
            ResultSet rs = st.executeQuery(query);
            if(!rs.next()){
                return false;
            }
            query = "update shop_database set cost="+new_cost+" where product='"+product+"' and shopid="+shopid+";";
            st.executeUpdate(query);
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
                System.out.println("Category Menu - ");
                System.out.println("1. View Storage database ");
                System.out.println("2. Add Product ");
                System.out.println("3. Remove Product ");
                System.out.println("4. Modify cost of a Product ");
                System.out.println("5. Quit");
                System.out.println();
                System.out.println("Enter your choice - ");
                ch = inFromClient.readInt();

                if (ch == 1) {
                    view_database(shopid,category);
                } else if (ch == 2) {
                    System.out.println("Product name -");
                    String name = inFromClient.readUTF();
                    name = name.toUpperCase();
                    System.out.println("Product cost -");
                    double cost = inFromClient.readDouble();
                    add(shopid, name, cost,category);
                } else if (ch == 3) {
                    System.out.println("Product name -");
                    String name = inFromClient.readUTF();
                    name = name.toUpperCase();
                    boolean f = false;
                    f=remove(shopid, name,category);
                    if(f==false) {
                        System.out.println("Item Name NOT FOUND!!");
                    }
                } else if (ch == 4) {
                    System.out.println("Product name - ");
                    String name = inFromClient.readUTF();
                    name = name.toUpperCase();
                    System.out.println("New Product cost -");
                    double cost = inFromClient.readDouble();
                    //s.nextLine();
                    boolean f = false;
                     f=modify(shopid, name, cost);
                     if(f==false) {
                        System.out.println("Item Name NOT FOUND!!");
                    }
                } else if (ch == 5) {
                    outToClient.writeInt(0);
                    break;
                } else {
                    System.out.println();
                    System.out.println("Choose from the given Menu! ");
                    System.out.println();
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
            ServerSocket welcomeSocket = new ServerSocket(4000);

            //System.out.println("ServerSocket awaiting connections...");
            connectionSocket = welcomeSocket.accept();
            System.out.println("Connection from " + connectionSocket);

            inFromClient = new DataInputStream(connectionSocket.getInputStream());
            outToClient = new DataOutputStream(connectionSocket.getOutputStream());

            String cat="";
            while (true) {
                try {
                    System.out.println("********WELCOME TO THE STORE*********");
                    System.out.println();
                    System.out.println("********WHAT CATEGORY DO YOU WANT TO PERFORM OPERATIONS*********");
                    System.out.println("1.INSECTICIDES");
                    System.out.println("2.PESTICIDES");
                    System.out.println("3.CROP SEEDS");
                    System.out.println("4.TOOLS");
                    System.out.println("5.DISPLAY SHOP DATABASE");
                    System.out.println("6.CUSTOMER DATA");
                    System.out.println("7.EXIT");
                    System.out.println();
                    System.out.println("Enter the Category you want (String) -");
                    //String cat = s.nextLine();
                    cat = inFromClient.readUTF();
                    cat = cat.trim();
                    System.out.println(cat);
                    if (!(cat.equalsIgnoreCase("INSECTICIDES") || cat.equalsIgnoreCase("CUSTOMER DATA") || cat.equalsIgnoreCase("DISPLAY SHOP DATABASE") || cat.equalsIgnoreCase("PESTICIDES") || cat.equalsIgnoreCase("CROP SEEDS") || cat.equalsIgnoreCase("TOOLS") || cat.equalsIgnoreCase("EXIT"))) {
                        System.out.println("Option not available!");
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
                            System.out.println("INSECTICIDES - ");
                            //view_hashmap(shop_storage_insecticides);
                            view_database(shopid,"INSECTICIDES");
                            System.out.println();
                            System.out.println("PESTICIDES - ");
                            //view_hashmap(shop_storage_pesticides);
                            view_database(shopid,"PESTICIDES");
                            System.out.println();
                            System.out.println("CROP SEEDS - ");
                            //view_hashmap(shop_storage_cropseeds);
                            view_database(shopid,"CROP SEEDS");
                            System.out.println();
                            System.out.println("TOOLS - ");
                            //view_hashmap(shop_storage_tools);
                            view_database(shopid,"TOOLS");
                            System.out.println();
                             continue;
                        }
                        else if(cat.equalsIgnoreCase("CUSTOMER DATA")){
                            display_custdata();
                        }
                        //else if (cat.equalsIgnoreCase("EXIT")) {
                        else{
                            //outToClient.writeInt(0);
                            welcomeSocket.close();
                            connectionSocket.close();
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

/*public class Shops {
    Scanner s=new Scanner(System.in);
    HashMap<String,Double> shop_storage_insecticides=new HashMap<String,Double>();
    HashMap<String,Double> shop_storage_pesticides=new HashMap<String,Double>();
    HashMap<String,Double> shop_storage_cropseeds=new HashMap<String,Double>();
    HashMap<String,Double> shop_storage_tools=new HashMap<String,Double>();


    String shop_name,email,phone,password;
    Shops(){
        shop_name=null;
        email=null;
        phone=null;
        password=null;
    }

    Shops(String shop_name,String email,String phone,String password){
        this.shop_name=shop_name;
        this.email=email;
        this.phone=phone;
        this.password=password;
    }

    public void view_hashmap(HashMap<String,Double> map){
        for(String i :map.keySet()){
            System.out.println("Item: "+i+" Cost: "+map.get(i));
        }
    }

    public void add(HashMap<String,Double> map,String item,double cost){
        map.put(item,cost);
    }

    public void remove(HashMap<String,Double> map,String item){
        map.remove(item);
    }

    public boolean modify(HashMap<String,Double> map,String item,double old_cost,double new_cost){
        return map.replace(item,old_cost,new_cost);
    }



    public void menu(HashMap<String,Double> map){
        int ch=0;
        try {
            while (true) {
                System.out.println("Category Menu - ");
                System.out.println("1. View Storage database ");
                System.out.println("2. Add Item ");
                System.out.println("3. Remove Item ");
                System.out.println("4. Modify cost of a Item ");
                System.out.println("5. Quit");
                System.out.println();
                System.out.println("Enter your choice - ");
                ch = s.nextInt();

                if (ch == 1) {
                    view_hashmap(map);
                }
                else if (ch == 2) {
                    System.out.println("Item name -");
                    String name = s.nextLine();
                    name = name.toUpperCase();
                    System.out.println("Item cost -");
                    double cost = s.nextDouble();
                    s.nextLine();
                    add(map, name, cost);
                }
                else if (ch == 3) {
                    System.out.println("Item name -");
                    String name = s.nextLine();
                    name = name.toUpperCase();
                    remove(map, name);
                } else if (ch == 4) {
                    System.out.println("Item name - ");
                    String name = s.nextLine();
                    name = name.toUpperCase();
                    System.out.println("New Item cost -");
                    double cost = s.nextDouble();
                    s.nextLine();
                    boolean f = modify(map, name, map.get(name), cost);
                    if (f == false) {
                        System.out.println("Item Name NOT FOUND!!");
                    }
                }
                else if (ch == 5) {
                    //outToClient.writeInt(0);
                    break;
                }
                else {
                    System.out.println();
                    System.out.println("Choose from the given Menu! ");
                    System.out.println();
                }
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }


    public void main1(){

            while(true){
                System.out.println("********WELCOME TO THE STORE*********");
                System.out.println();
                System.out.println("********WHAT CATEGORY DO YOU WANT TO PERFORM OPERATIONS*********");
                System.out.println("1.INSECTICIDES");
                System.out.println("2.PESTICIDES");
                System.out.println("3.CROP SEEDS");
                System.out.println("4.TOOLS");
                System.out.println("5.DISPLAY SHOP DATABASE");
                System.out.println("6.Exit");
                System.out.println();
                System.out.println("Enter the Category you want (String) -");
                String cat = s.nextLine();

                if (!(cat.equalsIgnoreCase("INSECTICIDES") || cat.equalsIgnoreCase("DISPLAY SHOP DATABASE")|| cat.equalsIgnoreCase("PESTICIDES") || cat.equalsIgnoreCase("CROP SEEDS") || cat.equalsIgnoreCase("TOOLS") || cat.equalsIgnoreCase("EXIT"))) {
                    System.out.println("Item not available at the store.");
                }
                else {
                    if (cat.equalsIgnoreCase("INSECTICIDES") ) {
                        menu(shop_storage_insecticides);
                    }
                    if (cat.equalsIgnoreCase("PESTICIDES")) {
                        menu(shop_storage_pesticides);
                    }
                    if (cat.equalsIgnoreCase("CROP SEEDS")) {
                        menu(shop_storage_cropseeds);
                    }
                    if (cat.equalsIgnoreCase("TOOLS")) {
                        menu(shop_storage_tools);
                    }
                    if (cat.equalsIgnoreCase("DISPLAY SHOP DATABASE")) {
                        System.out.println("INSECTICIDES - ");
                        view_hashmap(shop_storage_insecticides);
                        System.out.println();
                        System.out.println("PESTICIDES - ");
                        view_hashmap(shop_storage_pesticides);
                        System.out.println();
                        System.out.println("CROP SEEDS - ");
                        view_hashmap(shop_storage_cropseeds);
                        System.out.println();
                        System.out.println("TOOLS - ");
                        view_hashmap(shop_storage_tools);
                        System.out.println();
                    }
                    if (cat.equalsIgnoreCase("EXIT")) {
                        break;
                    }
                }
            }
    }
}*/

