import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Scanner;



class Server {


    public static void main(String argv[]) throws Exception {
        String user,password;
        int  clientlogintype,ch;
        //String capitalizedSentence;
        //Scanner scan =new Scanner(System.in);
        HashMap<String,Customer> customer=new HashMap<>();
        HashMap<String,Shops> shop=new HashMap<>();

        ServerSocket welcomeSocket = new ServerSocket(5000);


        while (true) {
            System.out.println("ServerSocket awaiting connections...");
            Socket connectionSocket = welcomeSocket.accept();
            System.out.println("Connection from " + connectionSocket);

            DataInputStream inFromClient = new DataInputStream(connectionSocket.getInputStream());
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

            while (true) {
                System.out.println("****** WELCOME TO AGRICULTURE-MARKET MANAGEMENTS ********");
                System.out.println();
                System.out.println("1.Login");
                System.out.println("2.Register");
                System.out.println("3.Exit");
                clientlogintype = inFromClient.readInt();

                if(clientlogintype==1){ //Login

                    System.out.println("Choose User Type (INTEGER)- ");
                    System.out.println("1.Customer");
                    System.out.println("2.Shop Member");
                    ch=inFromClient.readInt();

                    if(ch==1){ //Customer Login
                        System.out.println("Enter Username");
                        user=inFromClient.readUTF();
                        if(customer.containsKey(user)){
                            Customer obj=customer.get(user);
                            outToClient.writeInt(1);
                            System.out.println("Enter Password");
                            password=inFromClient.readUTF();
                            if(obj.password.equals(password)){
                                outToClient.writeInt(1);
                                obj.main(shop);
                            }
                            else{
                                outToClient.writeInt(0);
                                System.out.println("Invalid Password!");
                            }
                        }
                        else{
                            outToClient.writeInt(0);
                            System.out.println("User not present!!");
                        }
                    }

                    else if(ch==2){ //Shops Login
                        System.out.println("Enter Username");
                        user=inFromClient.readUTF();
                        if(shop.containsKey(user)){
                            Shops obj=shop.get(user);
                            outToClient.writeInt(1);
                            System.out.println("Enter Password");
                            password=inFromClient.readUTF();
                            if(obj.password.equals(password)){
                                outToClient.writeInt(1);
                                obj.main1();
                                continue;
                            }
                            else{
                                outToClient.writeInt(0);
                                System.out.println("Invalid Password!");
                            }
                        }
                        else{
                            outToClient.writeInt(0);
                            System.out.println("User not present!!");
                        }
                    }

                }
                if (clientlogintype==2) {  //Register

                    System.out.println("Choose User Type - ");
                    System.out.println("1.Customer");
                    System.out.println("2.Shop Member");
                    ch = inFromClient.readInt();

                    if(ch==1){
                        while(true){
                            System.out.println("Enter Username -");
                            user=inFromClient.readUTF();
                            if (customer.containsKey(user)) {
                                System.out.println("Username already choosen! Try different Username!");
                                outToClient.writeInt(0);
                            } else {
                                outToClient.writeInt(1);
                                break;
                            }
                        }

                        System.out.println("Enter Customer Name -");
                        String customer_name=inFromClient.readUTF();
                        System.out.println(customer_name);
                        System.out.println("Enter Email Id -");
                        String email=inFromClient.readUTF();
                        System.out.println(email);
                        System.out.println("Enter Phone Number - ");
                        String phone=inFromClient.readUTF();
                        System.out.println(phone);
                        System.out.println("Enter Password -");
                        password=inFromClient.readUTF();
                        System.out.println(password);
                        Customer obj=new Customer(customer_name,email,phone,password);
                        customer.put(user,obj);
                        System.out.println();
                        System.out.println("You are ready to go! Please Log in. ");
                    }

                    else if(ch==2 ){
                        while (true) {
                            System.out.println("Enter Username -");
                            user = inFromClient.readUTF();
                            System.out.println(user);
                            if (shop.containsKey(user)) {
                                System.out.println("Username already choosen! Try different Username!");
                                outToClient.writeInt(0);
                            } else {
                                outToClient.writeInt(1);
                                break;
                            }
                        }

                        System.out.println("Enter Shop Name -");
                        String shop_name=inFromClient.readUTF();
                        System.out.println(shop_name);
                        System.out.println("Enter Email Id -");
                        String email=inFromClient.readUTF();
                        System.out.println(email);
                        System.out.println("Enter Phone Number - ");
                        String phone=inFromClient.readUTF();
                        System.out.println(phone);
                        System.out.println("Enter Password -");
                        password=inFromClient.readUTF();
                        System.out.println(password);
                        Shops obj=new Shops(shop_name,email,phone,password);
                        shop.put(user,obj);
                        System.out.println("You are ready to go! Please Log in. ");
                    }
                }
                else if (clientlogintype==3) {
                    outToClient.writeUTF("Thank You!");
                    break;
                }
            }

        }
    }
}