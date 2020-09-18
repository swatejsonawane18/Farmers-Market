        import java.io.DataInputStream;
        import java.io.DataOutputStream;
        import java.net.ServerSocket;
        import java.net.Socket;
        import java.util.*;
        
        public class Customer extends  Shops{
            Scanner sc = new Scanner(System.in);
            String customer_name, email, phone, password;
            //ArrayList<Integer> count=new ArrayList<Integer>();
            LinkedList<Integer> cart_matched = new LinkedList<>();
            ArrayList category = new ArrayList(4);

            HashMap<String,Integer> final_cart=new HashMap<String,Integer>();

            HashMap<String, Integer> cart_insecticides = new HashMap<String, Integer>();
            HashMap<String, Integer> cart_pesticides = new HashMap<String, Integer>();
            HashMap<String, Integer> cart_cropseeds = new HashMap<String, Integer>();
            HashMap<String, Integer> cart_tools = new HashMap<String, Integer>();

            private Socket connectionSocket = null;
            private DataInputStream inFromClient = null;
            private DataOutputStream outToClient = null;

            Customer() {
                customer_name = null;
                email = null;
                phone = null;
                password = null;
            }

            Customer(String shop_name, String email, String phone, String password) {
                this.customer_name = shop_name;
                this.email = email;
                this.phone = phone;
                this.password = password;
            }

            public double check(HashMap<String, Integer> customer, HashMap<String, Double> shop) {
                double sum = 0;
                double sum_item = 0;
                int n = 0;

                for (String i : customer.keySet()) {
                    if (shop.containsKey(i)) {
                        ++n;
                        sum_item = shop.get(i) * customer.get(i);
                        sum += sum_item;
                        System.out.println(i + "     " + customer.get(i) + "     " + sum_item);
                        cart_matched.add(1);
                    } else {
                        System.out.println();
                        cart_matched.add(0);
                        System.out.println("Sorry, " + i + " is currently unavailable at the store! ");
                    }
                }
                //count.add(n);
                System.out.println();
                return sum;
            }

            public void insert_final_cart(){
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
            }


            public void Menu(HashMap<String, Shops> shop, int code) {
                if (code == 1) {
                    category.add(code - 1, 1);
                    for (String i : shop.keySet()) {
                        view_hashmap(shop.get(i).shop_storage_insecticides);
                    }
                } else if (code == 2) {
                    category.add(code - 1, 1);
                    for (String i : shop.keySet()) {
                        view_hashmap(shop.get(i).shop_storage_pesticides);
                    }
                } else if (code == 3) {
                    category.add(code - 1, 1);
                    for (String i : shop.keySet()) {
                        view_hashmap(shop.get(i).shop_storage_cropseeds);
                    }
                } else if (code == 4) {
                    category.add(code - 1, 1);
                    for (String i : shop.keySet()) {
                        view_hashmap(shop.get(i).shop_storage_tools);
                    }
                }
            }


            public void add_cart_item(HashMap<String, Integer> cart, String item, int quantity) {
                cart.put(item, quantity);
            }

            public void remove_cart_item(HashMap<String, Integer> cart, String item) {
                cart.remove(item);
            }

            public boolean modify_cart_item(HashMap<String, Integer> cart, String item, int old_quantity, int new_quantity) {
                return cart.replace(item, old_quantity, new_quantity);
            }

            public void view_cart(HashMap<String, Integer> cart) {
                if (!cart.isEmpty()) {
                    for (String i : cart.keySet()) {
                        System.out.println("Item: " + i + " Quantity: " + cart.get(i));
                    }
                } else {
                    System.out.println("CART IS EMPTY!");
                }
            }

            public int count_matched() {
                int count = 0;
                for (int i = 0; i < cart_matched.size(); i++) {
                    if (cart_matched.get(i) == 1) {
                        count++;
                    }
                }
                return count;
            }

            public void functions(HashMap<String, Integer> cart, HashMap<String, Shops> shop, int code) {
                int ch = 0;
                try {
                    while (true) {
                        System.out.println("Category Menu - ");
                        System.out.println("1. View Cart Items ");
                        System.out.println("2. Add Item ");
                        System.out.println("3. Remove Item ");
                        System.out.println("4. Modify cost of a Item ");
                        System.out.println("5. Quit");
                        System.out.println();
                        System.out.println("Enter your choice - ");
                        ch = inFromClient.readInt();

                        if (ch == 1) {
                            view_cart(cart);
                        }
                        else if (ch == 2) {
                            Menu(shop, code);
                            System.out.println("Item name -");
                            String name = inFromClient.readUTF();
                            name = name.toUpperCase();
                            System.out.println("Item quantity -");
                            int quantity = inFromClient.readInt();
                            add_cart_item(cart, name, quantity);
                        }
                        else if (ch == 3) {
                            System.out.println("Item name -");
                            String item = inFromClient.readUTF();
                            item = item.toUpperCase();
                            remove_cart_item(cart, item);
                        }
                        else if (ch == 4) {
                            System.out.println("Item name - ");
                            String name = inFromClient.readUTF();
                            name = name.toUpperCase();
                            System.out.println("New Item quantity -");
                            int quantity = inFromClient.readInt();
                            //s.nextLine();
                            boolean f = false;
                            try {
                                f = modify_cart_item(cart, name, cart.get(name), quantity);
                            } catch (Exception e) {
                                System.out.println("Item Name NOT FOUND!!");
                            }
                        }
                        else if (ch == 5) {
                            outToClient.writeInt(0);
                            break;
                        }
                        else {
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

            /*public void functions(HashMap<String, Integer> cart, HashMap<String, Shops> shop, int code) {
                try {
                    int ch = 0;
                    String item = "";
                    int quantity = 0;
                    while (true) {
                        System.out.println("1.Add Item in Cart");
                        System.out.println("2.Remove Item from Cart");
                        System.out.println("3.Modify Quantity of Item in the Cart");
                        System.out.println("4.View Items in Cart");
                        System.out.println("5.Quit");
                        ch = inFromClient.readInt();
                        //sc.nextLine();
                        if (ch == 1) {
                            Menu(shop, code);
                            System.out.println("Enter the Product you want -");
                            item = inFromClient.readUTF();
                            item = item.toUpperCase();
                            System.out.println("Enter the Quantity -");
                            quantity = inFromClient.readInt();
                            add_cart_item(cart, item, quantity);
                        } else if (ch == 2) {
                            System.out.println("Enter the Product you want to delete -");
                            item = inFromClient.readUTF();
                            item = item.toUpperCase();
                            remove_cart_item(cart, item);
                        } else if (ch == 3) {
                            System.out.println("Enter the Product whose quantity you want to modify -");
                            item = inFromClient.readUTF();
                            item = item.toUpperCase();
                            System.out.println("Enter the new quantity");
                            int new_quantity = inFromClient.readInt();
                            //sc.nextLine();
                            boolean f = false;
                            if (cart.get(item) != null)
                                f = modify_cart_item(cart, item, cart.get(item), new_quantity);
                            else {
                                System.out.println(item + " not found in cart!");
                            }
                        } else if (ch == 4) {
                            view_cart(cart);
                        } else if (ch == 5) {
                            outToClient.writeInt(0);
                            break;
                        } else {
                            System.out.println("Enter the choice from the above Menu!");
                            continue;
                        }

                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }*/

            public int display_fullcart() {
                int ch = 0;
                System.out.println("Your cart items are - ");
                if (!cart_insecticides.isEmpty()) {
                    System.out.println();
                    System.out.println("INSECTICIDES");
                    System.out.println();
                    ch = 1;
                    for (String i : cart_insecticides.keySet()) {
                        System.out.println("Key: " + i + " Quantity: " + cart_insecticides.get(i));
                    }
                    System.out.println();
                }

                if (!cart_pesticides.isEmpty()) {
                    System.out.println();
                    System.out.println("PESTICIDES - ");
                    System.out.println();
                    ch = 1;
                    for (String i : cart_pesticides.keySet()) {
                        System.out.println("Key: " + i + " Quantity: " + cart_pesticides.get(i));
                    }
                    System.out.println();
                }

                if (!cart_cropseeds.isEmpty()) {
                    System.out.println();
                    System.out.println("CROPSEEDS - ");
                    System.out.println();
                    ch = 1;
                    for (String i : cart_cropseeds.keySet()) {
                        System.out.println("Key: " + i + " Quantity: " + cart_cropseeds.get(i));
                    }
                    System.out.println();
                }

                if (!cart_tools.isEmpty()) {
                    System.out.println();
                    System.out.println("TOOLS -");
                    System.out.println();
                    ch = 1;
                    for (String i : cart_tools.keySet()) {
                        System.out.println("Key: " + i + " Quantity: " + cart_tools.get(i));
                    }
                    System.out.println();
                }
                return ch;
            }

            public void main(HashMap<String, Shops> shop) {
                try {
                    //Customer obj=new Customer();
                    ServerSocket welcomeSocket = new ServerSocket(3000);

                    //System.out.println("ServerSocket awaiting connections...");
                    connectionSocket = welcomeSocket.accept();
                    System.out.println("Connection from " + connectionSocket);

                    inFromClient = new DataInputStream(connectionSocket.getInputStream());
                    outToClient = new DataOutputStream(connectionSocket.getOutputStream());

                    int code = 0;
                    double price = 0;
                    int ch;
                    String r, product;

                    double sum=0, min_sum = Double.MAX_VALUE;
                    String best_shop = null;

                    while (true) {
                        System.out.println("********WELCOME TO THE STORE*********");
                        System.out.println("1.INSECTICIDES");
                        System.out.println("2.PESTICIDES");
                        System.out.println("3.CROP SEEDS");
                        System.out.println("4.TOOLS");
                        System.out.println("5.DISPLAY CART");
                        System.out.println("6.EXIT");
                        System.out.println();
                        System.out.println("Enter the Category you want to buy in (STRING)-");
                        product = inFromClient.readUTF();
                        product=product.trim();
                        //sc.nextLine();

                        if (!(product.equalsIgnoreCase("INSECTICIDES") || product.equalsIgnoreCase("PESTICIDES") || product.equalsIgnoreCase("CROP SEEDS") || product.equalsIgnoreCase("TOOLS") || product.equalsIgnoreCase("DISPLAY CART") || product.equalsIgnoreCase("EXIT"))) {
                            System.out.println("Item not available at the store.");
                        } else {
                            if (product.equalsIgnoreCase("INSECTICIDES")) {
                                code = 1;
                                //category.put(product, code);
                                functions(cart_insecticides, shop, code);
                            }
                            if (product.equalsIgnoreCase("PESTICIDES")) {
                                code = 2;
                                // category.put(product, code);
                                functions(cart_pesticides, shop, code);
                            }
                            if (product.equalsIgnoreCase("CROP SEEDS")) {
                                code = 3;
                                //category.put(product, code);
                                functions(cart_cropseeds, shop, code);
                            }
                            if (product.equalsIgnoreCase("TOOLS")) {
                                code = 4;
                                //category.put(product, code);
                                functions(cart_tools, shop, code);
                            }
                            if (product.equalsIgnoreCase("DISPLAY CART")) {
                                ch = display_fullcart();

                                if (ch == 0) {
                                    System.out.println();
                                    System.out.println("CART IS EMPTY!");
                                    System.out.println();
                                }
                            }
                            if (product.equalsIgnoreCase("EXIT")) {

                                System.out.println();
                                ch = 0;


                                ch = display_fullcart();

                                if (ch == 0) {
                                    System.out.println();
                                    System.out.println("CART IS EMPTY!");
                                    System.out.println();
                                    System.out.println("Thanks for visiting!");
                                    System.out.println();
                                    System.out.println();
                                    break;
                                }
                                else {


                                    int max_count = 0;
                                    int count = 0;
                                    for (String i : shop.keySet()) {
                                        System.out.println("Shop UserId - " + i);
                                        System.out.println();
                                        System.out.println("Item    Qty    Cost");
                                        sum = check(cart_insecticides, shop.get(i).shop_storage_insecticides) + check(cart_pesticides, shop.get(i).shop_storage_pesticides) + check(cart_cropseeds, shop.get(i).shop_storage_cropseeds) + check(cart_tools, shop.get(i).shop_storage_tools);
                                        count = count_matched();
                                        System.out.println("Total Amount - " + sum);
                                        System.out.println();

                                        if (count > max_count) {
                                            max_count = count;
                                            min_sum = sum;
                                            best_shop = i;
                                        } else if (sum < min_sum && count == max_count) {
                                            min_sum = sum;
                                            best_shop = i;
                                        }

                                        for (; cart_matched.size() != 0; ) {
                                            cart_matched.removeFirst();
                                        }
                                        System.out.println();
                                    }

                                    if (min_sum == 0 || best_shop == null) {
                                        System.out.println("No Shop has the Products you require!!");
                                        System.out.println("Try Later!");
                                    } else {
                                        System.out.println("BEST OPTION - ");
                                        System.out.println("Shop name - " + best_shop);
                                        System.out.println("Amount - " + min_sum);


                                        System.out.println("Do you want to BUY? ( 1 - Yes / 0 - No )");
                                        ch = inFromClient.readInt();
                                        if (ch == 1) {
                                            //insert_final_cart();
                                            if(shop.containsKey(best_shop)) {
                                                shop.get(best_shop).add_custdata(customer_name, min_sum);
                                                cart_insecticides.clear();
                                                cart_pesticides.clear();
                                                cart_cropseeds.clear();
                                                cart_tools.clear();
                                            }
                                            else{
                                                System.out.println("SHOP NOT FOUND!");
                                                ch=0;
                                            }
                                        }
                                    }

                                    if(ch==1)
                                    System.out.println("Thanks for shopping!");
                                    else
                                        System.out.println("Cart items are restored!");
                                    System.out.println();
                                    System.out.println();
                                }
                                //outToClient.writeInt(0);
                                connectionSocket.close();
                                welcomeSocket.close();
                                break;
                            }
                        }
                    }
                }
                catch (Exception e) {
                    System.out.println(e);
                }
            }
        }