        import jdk.jfr.Category;

        import java.io.DataInputStream;
        import java.io.DataOutputStream;
        import java.net.ServerSocket;
        import java.net.Socket;
        import java.sql.Connection;
        import java.sql.DriverManager;
        import java.sql.ResultSet;
        import java.sql.Statement;
        import java.util.*;
        
        public class Customer {
            Scanner sc = new Scanner(System.in);

            private Socket connectionSocket =null ;
            private DataInputStream inFromClient = null;
            private DataOutputStream outToClient = null;

            int custid;
            int count_match=0;



            Customer(Socket connectionSocket ,DataInputStream inFromClient ,DataOutputStream outToClient) {
                this.connectionSocket=connectionSocket;
                this.inFromClient=inFromClient;
                this.outToClient=outToClient;
            }
//
//

            public double check(String category,int shopid) {
                try{
                double sum = 0;
                double sum_item = 0;

                String query="select product,quantity from customer_database where category='"+category+"' and customerid="+custid+";";
                ResultSet rs = Database.executeQuery(query);
                ResultSet rs2;
                while(rs.next()){
                    outToClient.writeInt(1);
                      String product=rs.getString(1);
                      int quantity=rs.getInt(2);
                      String query2="select product,cost from shop_database where product='"+product+"' and shopid="+shopid+";";
                      rs2=Database.executeQuery(query2);
                      if(rs2.next()){
                          outToClient.writeInt(1);
                          ++count_match;
                          double cost=rs2.getDouble(2);
                          sum_item= cost*quantity;
                          sum+=sum_item;
                          outToClient.writeUTF(product + "     " + quantity + "     " + sum_item);
                      }
                      else {
                          outToClient.writeInt(0);
                          //System.out.println();
                          outToClient.writeUTF("Sorry, " + product + " is currently unavailable at the store! ");
                      }
                }
                outToClient.writeInt(0);
                //System.out.println();
                return sum;
                }
                catch (Exception e){
                    System.out.println(e);
                    return 0;
                }

            }

            public void delete_cart(){
                try {
                    //Statement st = con.createStatement();
                    String query = "delete from customer_database where customerid="+custid+";";
                    Database.executeUpdate(query);
                }
                catch (Exception e){
                    System.out.println("Delete cart");
                    System.out.println(e);
                }
            }

            /*public void insert_final_cart(){
                for (String i : cart_insecticides.keySet()) {
                    System.out.println("Key: " + i + " Quantity: " + cart_insecticides.get(i));
                    add_cart_item(final_cart,i, cart_insecticides.get(i));
                }
                for (String i : cart_pesticides.keySet()) {
                    System.out.println("Key: " + i + " Quantity: " + cart_pesticides.get(i));
                    add_cart_item(final_cart,i, cart_pesticides.get(i));
                }
                for (String i : cart_cropseeds.keySet()) {
                    System.out.println("Key: " + i + " Quantity: " + cart_cropseeds.get(i));
                    add_cart_item(final_cart,i, cart_cropseeds.get(i));
                }
                for (String i : cart_tools.keySet()) {
                    System.out.println("Key: " + i + " Quantity: " + cart_tools.get(i));
                    add_cart_item(final_cart,i, cart_tools.get(i));
                }
            }*/


            public void Menu(String category) {
                try {
                        Shops.view_whole_database(category);
                }
                catch (Exception e){
                    System.out.println(e);
                }
            }

            public void add_cart_item(int custid,String product,int quantity,String category){
                try {
                    String query = "insert into customer_database values(" + custid + ",'" + product + "'," + quantity + ",'" + category + "');";
                    Database.executeUpdate(query);
                }
                catch (Exception e){
                    System.out.println(e);
                }
            }

            public boolean remove_cart_item(int custid,String product,String category) {
                try{
                    //Statement st= con.createStatement();
                    String query="SELECT product FROM customer_database WHERE customerid="+custid+" and product='"+product+"';";
                    ResultSet rs = Database.executeQuery(query);
                    if(!rs.next()){
                        return false;
                    }
                    query = "delete from customer_database where customerid="+custid+" and product='"+product+"' and category ='"+category+"';";
                    Database.executeUpdate(query);
                    return true;
                }
                catch (Exception e){
                    System.out.println(e);
                    return false;
                }
            }

            public boolean modify_cart_item(int custid, String product, double new_quantity) {
                try{
                    String query="SELECT product FROM customer_database WHERE customerid="+custid+" and product='"+product+"';";
                    ResultSet rs = Database.executeQuery(query);
                    if(!rs.next()){
                        return false;
                    }
                    query = "update customer_database set quantity="+new_quantity+" where product='"+product+"' and customerid="+custid+";";
                    Database.executeUpdate(query);
                    return true;
                }
                catch (Exception e){
                    System.out.println(e);
                    return false;
                }
            }


            public int view_cart(int custid,String category){
                try {
                    ResultSet rs;
                    String query = "select * from customer_database where customerid='" + custid + "' and category='"+category+"';";
                    rs = Database.executeQuery(query);

                    int f=0;

                    while(rs.next()){
                        f=1;
                        outToClient.writeInt(f);
                        outToClient.writeUTF("Category - "+ category+ "      Product - "+rs.getString(2)+"      Quantity - "+rs.getInt(3)+"      Category - "+rs.getString(4));
                    }
                    if(f==0){
                        outToClient.writeInt(f);
                        return 0;
                    }
                    f=0;
                    outToClient.writeInt(f);
                    outToClient.writeUTF("---------------------------------------------------------------------");
                    return 1;
                }
                catch(Exception e){
                    System.out.println(e);
                    return 0;
                }
            }


            public void functions(String category) {
                int ch = 0;
                try {
                    while (true) {
//                        System.out.println("Category Menu - ");
//                        System.out.println("1. View Cart Items ");
//                        System.out.println("2. Add Item ");
//                        System.out.println("3. Remove Item ");
//                        System.out.println("4. Modify cost of a Item ");
//                        System.out.println("5. Quit");
//                        System.out.println();
//                        System.out.println("Enter your choice - ");
                        ch = inFromClient.readInt();

                        if (ch == 1) {
                            ch=view_cart(custid,category);
                            if(ch==0){
                                outToClient.writeUTF("CART IS EMPTY!!");
                                //System.out.println();
                            }
                        }
                        else if (ch == 2) {
                            Menu(category);
                            //System.out.println("Item name -");
                            String name = inFromClient.readUTF();
                            name = name.toUpperCase();
                            //System.out.println("Item quantity -");
                            int quantity = inFromClient.readInt();
                            add_cart_item(custid, name, quantity,category);
                        }
                        else if (ch == 3) {
                            //System.out.println("Product name -");
                            String name = inFromClient.readUTF();
                            name = name.toUpperCase();
                            boolean f = false;
                            f=remove_cart_item(custid, name,category);
                            if(f==false) {
                                outToClient.writeUTF("Item Name NOT FOUND!!");
                            }
                            else
                                outToClient.writeUTF("Product DELETED!!");
                        }
                        else if (ch == 4) {
                            //System.out.println("Item name - ");
                            String name = inFromClient.readUTF();
                            name = name.toUpperCase();
                           // System.out.println("New Item quantity -");
                            int quantity = inFromClient.readInt();
                            //s.nextLine();
                            boolean f = false;
                            f=modify_cart_item(custid, name, quantity);
                            if(f==false) {
                                outToClient.writeUTF("Item Name NOT FOUND!!");
                            }
                            else
                                outToClient.writeUTF("Product Cost MODIFIED!!");
                        }
                        else if (ch == 5) {
                            outToClient.writeInt(0);
                            break;
                        }
//                        else {
//                            System.out.println();
//                            System.out.println("Choose from the given Menu! ");
//                            System.out.println();
//                        }
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }




            public int display_fullcart() {
                int ch = 0;
                int f=0;
                //System.out.println("Your cart items are - ");
//                System.out.println();
                ch=view_cart(custid,"INSECTICIDES");
                if(ch==1)
                {    f=1;}
//                System.out.println();
                ch=view_cart(custid,"PESTICIDES");
                if(ch==1)
                {    f=1;}
//                System.out.println();
                ch=view_cart(custid,"CROPSEEDS");
                if(ch==1)
                {    f=1;}
//                System.out.println();
                ch=view_cart(custid,"TOOLS");
                if(ch==1)
                {    f=1;}
//                System.out.println();

                return f;
            }

            public void main(int custid) {
                this.custid=custid;
                try {

                    int code = 0;
                    double price = 0;
                    int ch;
                    String r, product;

                    double sum=0, min_sum = Double.MAX_VALUE;
                    String best_shop = null;
                    int best_shopid=0;

                    while (true) {
//                        System.out.println("********WELCOME TO THE STORE*********");
//                        System.out.println("1.INSECTICIDES");
//                        System.out.println("2.PESTICIDES");
//                        System.out.println("3.CROP SEEDS");
//                        System.out.println("4.TOOLS");
//                        System.out.println("5.DISPLAY CART");
//                        System.out.println("6.EXIT");
//                        System.out.println();
//                        System.out.println("Enter the Category you want to buy in (STRING)-");
                        product = inFromClient.readUTF();
                        product=product.trim();
                        //sc.nextLine();

                        if (!(product.equalsIgnoreCase("INSECTICIDES") || product.equalsIgnoreCase("PESTICIDES") || product.equalsIgnoreCase("CROP SEEDS") || product.equalsIgnoreCase("TOOLS") || product.equalsIgnoreCase("DISPLAY CART") || product.equalsIgnoreCase("EXIT"))) {
                            System.out.println("Item not available at the store.");
                        } else {
                            if (product.equalsIgnoreCase("INSECTICIDES")) {
                                functions(product);
                            }
                            if (product.equalsIgnoreCase("PESTICIDES")) {
                                functions(product);
                            }
                            if (product.equalsIgnoreCase("CROP SEEDS")) {
                                functions(product);
                            }
                            if (product.equalsIgnoreCase("TOOLS")) {
                                functions(product);
                            }
                            if (product.equalsIgnoreCase("DISPLAY CART")) {
                                ch = display_fullcart();
                                outToClient.writeInt(ch);

//                                if (ch == 0) {
//                                    System.out.println();
//                                    System.out.println("CART IS EMPTY!");
//                                    System.out.println();
//                                }
                            }
                            if (product.equalsIgnoreCase("EXIT")) {

                                System.out.println();
                                ch = 0;

                                ch = display_fullcart();

                                if (ch == 0) {
                                    outToClient.writeInt(0);
                                    break;
                                } else {
                                    outToClient.writeInt(1);
                                    //Statement st = con.createStatement();
                                    ResultSet rs;
                                    String query = "select id,name from server_database where usertype='Shop';";
                                    rs = Database.executeQuery(query);

                                    int max_count = 0;
                                    int count = 0;
                                    int shop_id = 0;
                                    String shop_name="";
                                    while (rs.next()) {
                                        outToClient.writeInt(1);
                                        shop_name= rs.getString(2);
                                        shop_id = rs.getInt(1);
                                        outToClient.writeUTF("Shop Name - " + shop_name);

                                        sum = check("INSECTICIDES", shop_id) + check("PESTICIDES", shop_id) + check("CROPSEEDS", shop_id) + check("TOOLS", shop_id);
                                        count = count_match;
                                        count_match = 0;
                                        outToClient.writeUTF("Total Amount - " + sum);


                                        if (count > max_count) {
                                            max_count = count;
                                            min_sum = sum;
                                            best_shop = shop_name;
                                            best_shopid=shop_id;
                                        } else if (sum < min_sum && count == max_count) {
                                            min_sum = sum;
                                            best_shop = shop_name;
                                            best_shopid=shop_id;
                                        }
                                    }
                                    outToClient.writeInt(0);
                                    if (min_sum == 0 || best_shop == null) {
                                        outToClient.writeInt(0);
//                                        System.out.println("No Shop has the Products you require!!");
//                                        System.out.println("Try Later!");
                                    } else {
                                        outToClient.writeInt(1);
//                                        System.out.println("BEST OPTION - ");
                                        outToClient.writeUTF("Shop name - " + best_shop);
                                        outToClient.writeUTF("Amount - " + min_sum);


//                                        System.out.println("Do you want to BUY? ( 1 - Yes / 0 - No )");
                                        ch = inFromClient.readInt();
                                        if (ch == 1) {
                                            //insert_final_cart();
//                                            if(shop.containsKey(best_shop)) {
//                                                shop.get(best_shop).add_custdata(customer_name, min_sum);
//                                                cart_insecticides.clear();
//                                                cart_pesticides.clear();
//                                                cart_cropseeds.clear();
//                                                cart_tools.clear();
//                                            System.out.println("Buying....");
                                            Storage ob = new Storage();
                                            String customer_name=ThreadServer.getname(custid);
                                            ob.add_custdata(best_shopid, custid, customer_name, min_sum);
                                            delete_cart();

                                        }
                                    }
                                }

//                                if (ch == 1)
//                                    System.out.println("Thanks for shopping!");
//                                else
//                                    System.out.println("Cart items are restored!");
//                                System.out.println();
//                                System.out.println();

                                break;
                             }
                          }
                        }
                    }
                catch (Exception e) {
                    System.out.println("In customer class");
                    System.out.println(e);
                }
            }
        }