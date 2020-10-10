
import java.io.*;
import java.net.*;
import java.util.*;
class Client {

    static Socket clientSocket2=null;
    static DataInputStream inFromServer=null;
    static DataOutputStream outToServer=null;



    public static void print_shop(){
        try {
            int c=inFromServer.readInt();
            if(c==0){
                System.out.println(inFromServer.readUTF());
                System.out.println("-----------------------------------------------------------");
                return;
            }
            while (c==1) {
                System.out.println(inFromServer.readUTF());
                c=inFromServer.readInt();
            }
            System.out.println(inFromServer.readUTF());
            return;
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public static void shop_input() {
        int k=0;
        try {
            Scanner sc = new Scanner(System.in);

            while (true) { //Menu for categories
                k=0;
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

                String ch = sc.nextLine(); //Category Choice
                ch=ch.toUpperCase();
                outToServer.writeUTF(ch);

                if (!(ch.equalsIgnoreCase("INSECTICIDES") || ch.equalsIgnoreCase("CUSTOMER DATA") || ch.equalsIgnoreCase("DISPLAY SHOP DATABASE") || ch.equalsIgnoreCase("PESTICIDES") || ch.equalsIgnoreCase("CROP SEEDS") || ch.equalsIgnoreCase("TOOLS") || ch.equalsIgnoreCase("EXIT"))) {
                    System.out.println("Option not available!");
                }

                else if (ch.equalsIgnoreCase("EXIT")) {
                    k=1;
                    //clientSocket2.close();
                    return;
                }
                else if(ch.equalsIgnoreCase("DISPLAY SHOP DATABASE")){
                    System.out.println("-----------------------------------------------------------");
                    System.out.println("INSECTICIDES - ");
                    print_shop();
                    System.out.println();
                    System.out.println("PESTICIDES - ");
                    print_shop();
                    System.out.println();
                    System.out.println("CROP SEEDS - ");
                    print_shop();
                    System.out.println();
                    System.out.println("TOOLS - ");
                    print_shop();
                    System.out.println();
                }
                else if(ch.equalsIgnoreCase("CUSTOMER DATA")){
                    print_shop();
                    System.out.println();
                }
                else{
                    //Menu for operation
                    while (true) {
                        System.out.println("Category Menu - ");
                        System.out.println("1. View Storage database ");
                        System.out.println("2. Add Product ");
                        System.out.println("3. Remove Product ");
                        System.out.println("4. Modify cost of a Product ");
                        System.out.println("5. Quit");
                        System.out.println();
                        System.out.println("Enter your choice - ");
                        int choice = sc.nextInt();
                        sc.nextLine();
                        outToServer.writeInt(choice);
                        System.out.println(choice);
                        if (choice == 1) {
                            System.out.println("-----------------------------------------------------------");
                            print_shop();
                            System.out.println();
                        }
                        else if (choice == 2) {
                            System.out.println("Item name -");
                            String name = sc.nextLine();
                            outToServer.writeUTF(name);

                            System.out.println("Item cost -");
                            double cost = sc.nextDouble();
                            sc.nextLine();
                            outToServer.writeDouble(cost);

                        } else if (choice == 3) {
                            System.out.println("Product name -");
                            String name = sc.nextLine();
                            outToServer.writeUTF(name);
                            System.out.println(inFromServer.readUTF());
                        } else if (choice == 4) {
                            System.out.println("Product name - ");
                            String name = sc.nextLine();
                            outToServer.writeUTF(name);
                            System.out.println("New Product cost -");
                            double cost = sc.nextDouble();
                            sc.nextLine();
                            outToServer.writeDouble(cost);
                            System.out.println(inFromServer.readUTF());
                        } else if (choice == 5) {
                            choice = inFromServer.readInt();
                            if (choice == 0)
                                break;
                            //clientSocket2.close();
                        }
                        else{
                            System.out.println();
                            System.out.println("Choose from the given Menu! ");
                            System.out.println();
                        }
                    }
                }

            }

        } catch (Exception e) {
            if(k==1){
                System.out.println("Exception in shop_input");
                System.out.println(e);
            }
        }

    }

    public static void check(){
        try {
            int c=0;
            while (inFromServer.readInt() == 1) {
                c=inFromServer.readInt();
                if (c==1) {
                    System.out.println(inFromServer.readUTF());
                } else {
                    //System.out.println();
                    System.out.println(inFromServer.readUTF());
                }
            }
            //System.out.println();
            return;
        }
        catch (Exception e){
            e.getStackTrace();
        }
    }

    public static void Client_exitHandler(){
        Scanner sc=new Scanner(System.in);
        try {
            while (inFromServer.readInt()==1) {
                System.out.println(inFromServer.readUTF());
                System.out.println();
                System.out.println("Item    Qty    Cost");
                check();
                check();
                check();
                check();
                System.out.println(inFromServer.readUTF());
                System.out.println();
                System.out.println();
            }
            if(inFromServer.readInt()==1) {
                System.out.println("BEST OPTION - ");
                System.out.println(inFromServer.readUTF());
                System.out.println(inFromServer.readUTF());
                System.out.println();
                System.out.println("Do you want to BUY? ( 1 - Yes / 0 - No )");
                int f = sc.nextInt();
                sc.nextLine();
                outToServer.writeInt(f);
                if (f == 1) {
//                    int k = sc.nextInt();
//                    sc.nextLine();
//                    outToServer.writeInt(k);
                    System.out.println("Buying....");
                    System.out.println("Thanks for shopping!");
                }
                else
                    System.out.println("Cart items are restored!");
                System.out.println();
                System.out.println();
            }
                else{
                System.out.println("No Shop has the Products you require!!");
                System.out.println("Try Later!");
                }
        }
        catch (Exception e){
            e.getStackTrace();
        }
    }

    public static int print_customer(){
        try {
            int c=inFromServer.readInt();
            if(c==0){
                return c;
            }
            while (c==1) {
                System.out.println(inFromServer.readUTF());
                c=inFromServer.readInt();
            }
            System.out.println(inFromServer.readUTF());
            return 1;
        }
        catch (Exception e){
            System.out.println(e);
            return 0;
        }
    }

    public static void customer_input() {
        int k=0;
        try {
            Scanner sc = new Scanner(System.in);
            while (true) { //Menu for categories
                k=0;
                System.out.println("********WELCOME TO THE STORE*********");
                System.out.println("1.INSECTICIDES");
                System.out.println("2.PESTICIDES");
                System.out.println("3.CROP SEEDS");
                System.out.println("4.TOOLS");
                System.out.println("5.DISPLAY CART");
                System.out.println("6.EXIT");
                System.out.println();
                System.out.println("Enter the Category you want to buy in (STRING)-");
                String ch = sc.nextLine(); //Category Choice
                //ch=ch.toUpperCase();
                outToServer.writeUTF(ch);

                if (!(ch.equalsIgnoreCase("INSECTICIDES") || ch.equalsIgnoreCase("DISPLAY CART") || ch.equalsIgnoreCase("PESTICIDES") || ch.equalsIgnoreCase("CROP SEEDS") || ch.equalsIgnoreCase("TOOLS") || ch.equalsIgnoreCase("EXIT"))) {
                    System.out.println("Option not available!");
                }

                else if (ch.equalsIgnoreCase("EXIT")) {
                    System.out.println();
                    System.out.println();
                    System.out.println("Your Cart Products are - ");
                    print_customer();
                    print_customer();
                    print_customer();
                    print_customer();
                    System.out.println();
                    System.out.println("Options are -");
                    int f=inFromServer.readInt();
                    if(f==1) {
                        Client_exitHandler();
                    }
                    else{
                        System.out.println();
                        System.out.println("CART IS EMPTY!");
                        System.out.println();
                        System.out.println("Thanks for visiting!");
                        System.out.println();
                        System.out.println();
                    }
                    return;
                }
                else if(ch.equalsIgnoreCase("DISPLAY CART")){
                    System.out.println("Your Cart Products are - ");
                    print_customer();
                    print_customer();
                    print_customer();
                    print_customer();
                    if(inFromServer.readInt()==0){
                        System.out.println();
                        System.out.println("CART IS EMPTY!");
                        System.out.println();
                    }
                    continue;
                }

                else {
                    //Menu for operation
                    while (true) {
                        System.out.println("Category Menu - ");
                        System.out.println("1. View Cart Items ");
                        System.out.println("2. Add Item ");
                        System.out.println("3. Remove Item ");
                        System.out.println("4. Modify cost of a Item ");
                        System.out.println("5. Quit");
                        System.out.println();
                        System.out.println("Enter your choice - ");
                        int choice = sc.nextInt();
                        sc.nextLine();
                        outToServer.writeInt(choice);

                        if (choice == 2) {
                            System.out.println("Product Name -");
                            String name = sc.nextLine();
                            outToServer.writeUTF(name);

                            System.out.println("Product Quantity -");
                            int quantity = sc.nextInt();
                            sc.nextLine();
                            outToServer.writeInt(quantity);

                        } else if (choice == 3) {
                             System.out.println("Product Name -");
                            String name = sc.nextLine();
                            outToServer.writeUTF(name);
                            System.out.println(inFromServer.readUTF());
                        } else if (choice == 4) {
                            System.out.println("Product name - ");
                            String name = sc.nextLine();
                            outToServer.writeUTF(name);
                            System.out.println("New Quantity -");
                            int quantity = sc.nextInt();
                            sc.nextLine();
                            outToServer.writeInt(quantity);
                            System.out.println(inFromServer.readUTF());
                        } else if (choice == 1) {
                            int f=print_customer();
                            if(f==0){
                                System.out.println(inFromServer.readUTF());
                                System.out.println();
                            }
                        } else if (choice == 5) {
                            choice = inFromServer.readInt();
                            if (choice == 0)
                                break;
                        }
                        else {
                            System.out.println();
                            System.out.println("Choose from the given Menu! ");
                            System.out.println();
                        }
                    }
                }
            }

        }
        catch (Exception e) {
            if(k!=0){
                System.out.println("Exception in customer_input");
                System.out.println(e);
            }
        }

    }


    public static void main(String argv[]) throws IOException {
        Scanner scan = new Scanner(System.in);

        //InetAddress ip = InetAddress.getByName("localhost");

        clientSocket2 = new Socket("localhost",5000);
        System.out.println("Connected to Server.");


        inFromServer = new DataInputStream(clientSocket2.getInputStream());
        outToServer = new DataOutputStream(clientSocket2.getOutputStream());


        while (true) {

            System.out.println("****** WELCOME TO AGRICULTURE-MARKET MANAGEMENTS ********");
            System.out.println();
            System.out.println("1.Login");
            System.out.println("2.Register");
            System.out.println("3.Exit");

            int ch = scan.nextInt();  //login/register choice
            scan.nextLine();
            outToServer.writeInt(ch);

            String user, password,address,phone,email;

            if (ch == 1) {   //Login choice
                System.out.println("Choose User Type (INTEGER)- ");
                System.out.println("1.Customer");
                System.out.println("2.Shop Member");
                ch = scan.nextInt(); // Customer/Shop choice
                scan.nextLine();
                outToServer.writeInt(ch);

                if (ch == 1) {  //Customer Login
                    System.out.println("Enter Username");
                    user = scan.nextLine();
                    outToServer.writeUTF(user);
                    ch = inFromServer.readInt();
                    System.out.println(ch);
                    if (ch == 0) {
                        System.out.println("User not present!!");
                        continue;
                    }
                    System.out.println("Enter Password");
                    password = scan.nextLine();
                    outToServer.writeUTF(password);
                    ch = inFromServer.readInt();
                    if(ch==1)
                        customer_input();
                    else if(ch==-1)
                        System.out.println("Access not granted : USER ALREADY LOGGED IN FROM ANOTHER DEVICE!!");
                    else
                        System.out.println("Invalid Password!");
                }
//
                else if (ch == 2) { //Shop Login
                    System.out.println("Enter Username");
                    user = scan.nextLine();
                    outToServer.writeUTF(user);
                    ch = inFromServer.readInt();
                    System.out.println(ch);
                    if (ch == 0) {
                        System.out.println("User not present!!");
                        continue;
                    }
                    System.out.println("Enter Password");
                    password = scan.nextLine();
                    outToServer.writeUTF(password);
                    ch = inFromServer.readInt();
                    if(ch==1){
                        shop_input();
                        continue;
                    }
                    else if(ch==-1)
                        System.out.println("Access not granted : USER ALREADY LOGGED IN FROM ANOTHER DEVICE!!");
                    else
                        System.out.println("Invalid Password!");
                }

            }

            if (ch == 2) {    //Register choice
                System.out.println("Choose User Type - ");
                System.out.println("1.Customer");
                System.out.println("2.Shop Member");
                ch = scan.nextInt();  // Customer/shop choice
                scan.nextLine();
                outToServer.writeInt(ch);

                if (ch == 1) { //Customer registration

                    while (true) {
                        System.out.println("Enter Username -");
                        user = scan.nextLine();
                        //System.out.println("Username - "+user);
                        outToServer.writeUTF(user);
                        ch = inFromServer.readInt();
                        if (ch == 0) {
                            System.out.println("Username already choosen! Try different Username!");
                            continue;
                        } else {
                            break;
                        }
                    }
                    System.out.println("Enter Customer Name -");
                    String customer_name = scan.nextLine();
                    outToServer.writeUTF(customer_name);
                    System.out.println("Enter Email Id -");
                    email = scan.nextLine();
                    outToServer.writeUTF(email);
                    System.out.println("Enter Phone Number - ");
                    phone = scan.nextLine();
                    outToServer.writeUTF(phone);
                    System.out.println("Enter Address -");
                    address = scan.nextLine();
                    outToServer.writeUTF(address);
                    System.out.println("Enter Password -");
                    password = scan.nextLine();
                    outToServer.writeUTF(password);
                    System.out.println();
                    ch = inFromServer.readInt();
                    if (ch == 0) {
                        System.out.println("Username already choosen! Try different Username!");
                        continue;
                    } else {
                        System.out.println("You are ready to go! Please Log in. ");
                    }
                }

                else if (ch == 2) { //Shop registration

                    while (true) {
                        System.out.println("Enter Username -");
                        user = scan.nextLine();
                        //System.out.println("Username - "+user);
                        outToServer.writeUTF(user);
                        ch = inFromServer.readInt();
                        if (ch == 0) {
                            System.out.println("Username already choosen! Try different Username!");
                            continue;
                        }
                        else {
                            break;
                        }
                    }
                    System.out.println("Enter Shop Name -");
                    String shop_name = scan.nextLine();
                    outToServer.writeUTF(shop_name);
                    System.out.println("Enter Email Id -");
                    email = scan.nextLine();
                    outToServer.writeUTF(email);
                    System.out.println("Enter Phone Number - ");
                    phone = scan.nextLine();
                    outToServer.writeUTF(phone);
                    System.out.println("Enter Address - ");
                    address = scan.nextLine();
                    outToServer.writeUTF(address);
                    System.out.println("Enter Password - ");
                    password = scan.nextLine();
                    outToServer.writeUTF(password);
                    System.out.println();
                    ch = inFromServer.readInt();
                    if (ch == 0) {
                        System.out.println("Username already choosen! Try different Username!");
                        continue;
                    } else {
                        System.out.println("You are ready to go! Please Log in. ");
                    }
                }

            }
            else if (ch == 3) {
                System.out.println("Thank You!!");
                clientSocket2.close();
                return;
            }
        }
    }
}
