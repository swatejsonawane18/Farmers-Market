
import java.io.*;
import java.net.*;
import java.util.*;
class test {

    public static void shop_input() {
        int k=0;
        try {
            Scanner sc = new Scanner(System.in);
            Socket clientSocket2 = new Socket("localhost", 4000);
            System.out.println("Connected to Server.");

            DataInputStream inFromServer = new DataInputStream(clientSocket2.getInputStream());
            DataOutputStream outToServer = new DataOutputStream(clientSocket2.getOutputStream());

            while (true) { //Menu for categories
                k=0;
                //System.out.println("Enter category choice - ");
                String ch = sc.nextLine(); //Category Choice
                ch=ch.toUpperCase();
                outToServer.writeUTF(ch);

                if (ch.equalsIgnoreCase("EXIT")) {
                    k=1;
                    clientSocket2.close();
                    return;

                }
                else if(ch.equalsIgnoreCase("DISPLAY SHOP DATABASE") || ch.equalsIgnoreCase("CUSTOMER DATA")){
                    continue;
                }
                else{
                    //Menu for operation
                    while (true) {
                        int choice = sc.nextInt();
                        sc.nextLine();
                        outToServer.writeInt(choice);
                        if (choice == 1) {
                            continue;
                        }
                        else if (choice == 2) {
                            //System.out.println("Item name -");
                            String name = sc.nextLine();
                            outToServer.writeUTF(name);

                            //System.out.println("Item cost -");
                            double cost = sc.nextDouble();
                            sc.nextLine();
                            outToServer.writeDouble(cost);

                        } else if (choice == 3) {
                            System.out.println("Item name -");
                            String name = sc.nextLine();
                            outToServer.writeUTF(name);
                        } else if (choice == 4) {
                            System.out.println("Item name - ");
                            String name = sc.nextLine();
                            outToServer.writeUTF(name);
                            System.out.println("New Item cost -");
                            double cost = sc.nextDouble();
                            sc.nextLine();
                            outToServer.writeDouble(cost);

                        } else if (choice == 5) {
                            choice = inFromServer.readInt();
                            if (choice == 0)
                                break;
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

    public static void customer_input() {
        int k=0;
        try {
            Scanner sc = new Scanner(System.in);
            Socket clientSocket2 = new Socket("localhost", 3000);
            System.out.println("Connected to Server.");

            DataInputStream inFromServer = new DataInputStream(clientSocket2.getInputStream());
            DataOutputStream outToServer = new DataOutputStream(clientSocket2.getOutputStream());

            while (true) { //Menu for categories
                k=0;
                String ch = sc.nextLine(); //Category Choice
                ch=ch.toUpperCase();
                outToServer.writeUTF(ch);

                if (ch.equalsIgnoreCase("EXIT")) {
                    k=sc.nextInt();
                    sc.nextLine();
                    outToServer.writeInt(k);
                    clientSocket2.close();
                    return;

                }
                else if(ch.equalsIgnoreCase("DISPLAY CART")){
                    continue;
                }

                else {
                    //Menu for operation
                    while (true) {
                        int choice = sc.nextInt();
                        sc.nextLine();
                        outToServer.writeInt(choice);

                        if (choice == 2) {
                            //System.out.println("Item name -");
                            String name = sc.nextLine();
                            outToServer.writeUTF(name);

                            //System.out.println("Item cost -");
                            int quantity = sc.nextInt();
                            sc.nextLine();
                            outToServer.writeInt(quantity);

                        } else if (choice == 3) {
                            // System.out.println("Item name -");
                            String name = sc.nextLine();
                            outToServer.writeUTF(name);
                        } else if (choice == 4) {
                            //System.out.println("Item name - ");
                            String name = sc.nextLine();
                            outToServer.writeUTF(name);
                            //System.out.println("New Quantity -");
                            int quantity = sc.nextInt();
                            sc.nextLine();
                            outToServer.writeInt(quantity);

                        } else if (choice == 1) {
                            continue;
                        } else if (choice == 5) {
                            choice = inFromServer.readInt();
                            if (choice == 0)
                                break;
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

        Socket clientSocket = new Socket("localhost",5000);
        System.out.println("Connected to Server.");


        DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());


        while (true) {

            int ch = scan.nextInt();  //login/register choice
            scan.nextLine();
            outToServer.writeInt(ch);

            String user, password;

            if (ch == 1) {   //Login choice

                ch = scan.nextInt(); // Customer/Shop choice
                scan.nextLine();
                outToServer.writeInt(ch);

                if (ch == 1) {  //Customer Login
                    user = scan.nextLine();
                    outToServer.writeUTF(user + '\n');
                    ch = inFromServer.readInt();
                    if (ch == 0) {
                        continue;
                    }
                    password = scan.nextLine();
                    outToServer.writeUTF(password + '\n');
                    ch = inFromServer.readInt();
                    if(ch==1)
                        customer_input();
                }
//
                else if (ch == 2) { //Shop Login
                    user = scan.nextLine();
                    outToServer.writeUTF(user + '\n');
                    ch = inFromServer.readInt();
                    if (ch == 0) {
                        continue;
                    }
                    password = scan.nextLine();
                    outToServer.writeUTF(password + '\n');
                    ch = inFromServer.readInt();
                    if(ch==1){
                        shop_input();
                        continue;
                    }

                }

            }

            if (ch == 2) {    //Register choice

                ch = scan.nextInt();  // Customer/shop choice
                scan.nextLine();
                outToServer.writeInt(ch);

                if (ch == 1) { //Customer registration

                    while (true) {
                        user = scan.nextLine();
                        //System.out.println("Username - "+user);
                        outToServer.writeUTF(user + '\n');
                        ch = inFromServer.readInt();
                        if (ch == 0) {
                            continue;
                        } else {
                            break;
                        }
                    }
                    String shop_name = scan.nextLine();
                    outToServer.writeUTF(shop_name + '\n');
                    String email = scan.nextLine();
                    outToServer.writeUTF(email + '\n');
                    String phone = scan.nextLine();
                    outToServer.writeUTF(phone + '\n');
                    password = scan.nextLine();
                    outToServer.writeUTF(password + '\n');

                }

                else if (ch == 2) { //Shop registration

                    while (true) {
                        user = scan.nextLine();
                        //System.out.println("Username - "+user);
                        outToServer.writeUTF(user + '\n');
                        ch = inFromServer.readInt();
                        if (ch == 0) {
                            continue;
                        }
                        else {
                            break;
                        }
                    }
                    String shop_name = scan.nextLine();
                    outToServer.writeUTF(shop_name + '\n');
                    String email = scan.nextLine();
                    outToServer.writeUTF(email + '\n');
                    String phone = scan.nextLine();
                    outToServer.writeUTF(phone + '\n');
                    password = scan.nextLine();
                    outToServer.writeUTF(password + '\n');

                }

            }
            else if (ch == 3) {
                System.out.println(inFromServer.readUTF());
                clientSocket.close();
            }
        }
    }
}
