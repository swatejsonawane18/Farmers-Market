
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;
public class Shops {
    Scanner s = new Scanner(System.in);

    HashMap<String, Storage> shop_customerdata=new HashMap<>();
    HashMap<String, Double> shop_storage_insecticides = new HashMap<String, Double>();
    HashMap<String, Double> shop_storage_pesticides = new HashMap<String, Double>();
    HashMap<String, Double> shop_storage_cropseeds = new HashMap<String, Double>();
    HashMap<String, Double> shop_storage_tools = new HashMap<String, Double>();


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

    Shops(String shop_name, String email, String phone, String password) {
        this.shop_name = shop_name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public void view_hashmap(HashMap<String, Double> map) {
        if (!map.isEmpty()) {
            for (String i : map.keySet()) {
                System.out.println("Item: " + i + " Cost: " + map.get(i));
            }
        } else {
            System.out.println("CART IS EMPTY!");
        }
        return;
    }

    public void add_custdata(String cust_name,double cost){
        if(!shop_customerdata.containsKey(cust_name)){
            Storage st=new Storage("name");
            st.update_value(cost);
            shop_customerdata.put(cust_name,st);
        }
        else{
            shop_customerdata.get(cust_name).update_value(cost);
        }
    }

    public void display_custdata(){
        if (!shop_customerdata.isEmpty()) {
            for (String i : shop_customerdata.keySet()) {
                System.out.println("Customer: " + i.trim() + " | No. of time purchased from the shop : " + shop_customerdata.get(i).show_times_purchased() + " | Total Amount : " + shop_customerdata.get(i).show_amount() );
            }
        } else {
            System.out.println("CART IS EMPTY!");
        }
    }

    public void add(HashMap<String, Double> map, String item, double cost) {
        map.put(item, cost);
    }

    public void remove(HashMap<String, Double> map, String item) {
        map.remove(item);
    }

    public boolean modify(HashMap<String, Double> map, String item, double old_cost, double new_cost) {
        return map.replace(item, old_cost, new_cost);
    }


    public void menu(HashMap<String, Double> map) {
        int ch = 0;
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
                ch = inFromClient.readInt();

                if (ch == 1) {
                    view_hashmap(map);
                } else if (ch == 2) {
                    System.out.println("Item name -");
                    String name = inFromClient.readUTF();
                    name = name.toUpperCase();
                    System.out.println("Item cost -");
                    double cost = inFromClient.readDouble();
                    add(map, name, cost);
                } else if (ch == 3) {
                    System.out.println("Item name -");
                    String name = inFromClient.readUTF();
                    name = name.toUpperCase();
                    remove(map, name);
                } else if (ch == 4) {
                    System.out.println("Item name - ");
                    String name = inFromClient.readUTF();
                    name = name.toUpperCase();
                    System.out.println("New Item cost -");
                    double cost = inFromClient.readDouble();
                    //s.nextLine();
                    boolean f = false;
                    try {
                        f = modify(map, name, map.get(name), cost);
                    } catch (Exception e) {
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


    public void main1() {

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
                            menu(shop_storage_insecticides);
                        }
                        else if (cat.equalsIgnoreCase("PESTICIDES")) {
                            menu(shop_storage_pesticides);
                        }
                        else if (cat.equalsIgnoreCase("CROP SEEDS")) {
                            menu(shop_storage_cropseeds);
                        }
                        else if (cat.equalsIgnoreCase("TOOLS")) {
                            menu(shop_storage_tools);
                        }
                        else if (cat.equalsIgnoreCase("DISPLAY SHOP DATABASE")) {
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

