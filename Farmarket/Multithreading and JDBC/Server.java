import java.net.*;
import java.io.*;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.HashMap;
import java.util.Scanner;

import java.sql.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Server {

    static Map<String, ReentrantLock> lock_status = new HashMap<>();

    public static void main(String args[]) throws Exception {
        ServerSocket welcomeSocket = new ServerSocket(5000);
        ExecutorService exeservice = Executors.newCachedThreadPool();
        while (true) {
            Socket connectionSocket = null;
            try {
                connectionSocket = welcomeSocket.accept();
                System.out.println("New Client Connected:" + connectionSocket);
//                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
//                DataInputStream inFromClient = new DataInputStream(connectionSocket.getInputStream());
//                ObjectOutputStream os=new ObjectOutputStream(connectionSocket.getOutputStream());
//                ObjectInputStream is=new ObjectInputStream(connectionSocket.getInputStream());
                System.out.println("Assigning Thread to Client");
                ThreadServer t = new ThreadServer(connectionSocket);

                exeservice.execute(t);
            } catch (Exception e){
                System.out.println(e);
            }
        }
    }
}


class ThreadServer extends Thread {

    public DataInputStream inFromClient;
    public DataOutputStream outToClient;
    public Socket connectionSocket;
    int port=0;
//    public ThreadServer(){
//        inFromClient=
//        outToClient=null;
//        connectionSocket=null;
//    }

    public ThreadServer(Socket connectionSocket) throws Exception {
        this.connectionSocket = connectionSocket;
        this.outToClient = new DataOutputStream(connectionSocket.getOutputStream());
        this.inFromClient = new DataInputStream(connectionSocket.getInputStream());
//        this.inFromClient = inFromClient;
//        this.outToClient = outToClient;
    }


    static synchronized void add_to_database(String usertype, String user, String name, String email, String phone, String password, String location) throws Exception {
        //Class.forName("com.mysql.cj.jdbc.Driver");
        //Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/farmarket_schema", "root", "$w@teJ1800");
        //Statement st = con.createStatement();
        String query = "insert into server_database values(null,'" + usertype + "','" + user + "','" + password + "','" + name + "','" + email + "','" + phone + "','" + location + "');";
        Database.executeUpdate(query);
    }

    public static String getname(int id) {
        try {
            //Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/farmarket_schema", "root", "$w@teJ1800");
            //Statement st = con.createStatement();
            String query = "select name from server_database where id=" + id + ";";
            ResultSet rs = Database.executeQuery(query);
            rs.next();
            return (rs.getString(1));
        } catch (Exception e) {
            System.out.println(e);
            return "null";
        }
    }


    public void run() {
        port=connectionSocket.getPort();
        try {

            String user, password, query, address, email, phone;
            int clientlogintype, ch;

            ResultSet rs=null;
            while (true) {
//                System.out.println("****** WELCOME TO AGRICULTURE-MARKET MANAGEMENTS ********");
//                System.out.println();
//                System.out.println("1.Login");
//                System.out.println("2.Register");
//                System.out.println("3.Exit");
                clientlogintype = inFromClient.readInt();
                if (clientlogintype == 1) { //Login

//                    System.out.println("Choose User Type (INTEGER)- ");
//                    System.out.println("1.Customer");
//                    System.out.println("2.Shop Member");
                    ch = inFromClient.readInt();

                    if (ch == 1) { //Customer Login
//                        System.out.println("Enter Username");
                        user = inFromClient.readUTF();

                        query = "SELECT username FROM server_database WHERE username='" + user + "' and usertype='Customer';";
                        rs = Database.executeQuery(query);
                        //System.out.println(rs.next());
                        if (rs.next()) {

                            outToClient.writeInt(1);
//                            System.out.println("Enter Password");
                            password = inFromClient.readUTF();
                            query = "SELECT id,password FROM server_database WHERE username='" + user + "' and usertype='Customer';";
                            rs = Database.executeQuery(query);
                            rs.next();



                            if ((rs.getString(2)).equals(password)) {
                                outToClient.writeInt(1);

                                if(!Server.lock_status.containsKey(user)) {
                                    ReentrantLock rl=new ReentrantLock();
                                    Server.lock_status.put(user,rl);
                                }
                                if(Server.lock_status.containsKey(user)){
                                    boolean check=Locks.checkLock(Server.lock_status.get(user));
                                    if(check==false)
                                        Locks.setLock(Server.lock_status.get(user));
                                    else{
                                        outToClient.writeInt(-1);
                                        continue;
                                    }
                                }

                                Customer obj2 = new Customer(connectionSocket,inFromClient,outToClient);
                                obj2.main(rs.getInt(1));
                                Locks.removeLock(Server.lock_status.get(user));
                                continue;
                            } else {
                                outToClient.writeInt(0);
//                                System.out.println("Invalid Password!");
                            }
                        } else {
                            outToClient.writeInt(0);
//                            System.out.println("User not present!!");
                        }
                    } else if (ch == 2) { //Shops Login
//                        System.out.println("Enter Username");
                        user = inFromClient.readUTF();


                        query = "SELECT username FROM server_database WHERE username='" + user + "' and usertype='Shop';";
                        rs = Database.executeQuery(query);
                        //rs.next();
                        System.out.println(rs);

                        if (rs.next()) {
                            outToClient.writeInt(1);
//                            System.out.println("Enter Password");
                            password = inFromClient.readUTF();
                            query = "SELECT id,password FROM server_database WHERE username='" + user + "' and usertype='Shop';";
                            rs = Database.executeQuery(query);
                            rs.next();

                            if ((rs.getString(2)).equals(password)) {

                                if(!Server.lock_status.containsKey(user)) {
                                    ReentrantLock rl=new ReentrantLock();
                                    Server.lock_status.put(user,rl);
                                }
                                if(Server.lock_status.containsKey(user)){
                                    boolean check=Locks.checkLock(Server.lock_status.get(user));
                                    if(check==false)
                                        Locks.setLock(Server.lock_status.get(user));
                                    else{
                                        //System.out.println("Access not granted : USER ALREADY LOGGED IN!!");
                                        outToClient.writeInt(-1);
                                        continue;
                                    }
                                }
                                outToClient.writeInt(1);
                                Shops obj = new Shops(connectionSocket,inFromClient,outToClient);
                                obj.main1(rs.getInt(1));
                                Locks.removeLock(Server.lock_status.get(user));
                                continue;
                            } else {
                                outToClient.writeInt(0);
//                                System.out.println("Invalid Password!");
                            }
                        } else {
                            outToClient.writeInt(0);
//                            System.out.println("User not present!!");
                        }
                    }

                }
                if (clientlogintype == 2) {  //Register

//                    System.out.println("Choose User Type - ");
//                    System.out.println("1.Customer");
//                    System.out.println("2.Shop Member");
                    ch = inFromClient.readInt();

                    if (ch == 1) {
                        while (true) {
//                            System.out.println("Enter Username -");
                            user = inFromClient.readUTF();
                            query = "SELECT username FROM server_database WHERE username='" + user + "';";
                            rs = Database.executeQuery(query);
                            if (rs.next()) {
//                                System.out.println("Username already choosen! Try different Username!");
                                outToClient.writeInt(0);
                            } else {
                                outToClient.writeInt(1);
                                break;
                            }
                        }

                      //  System.out.println("Enter Customer Name -");
                        String customer_name = inFromClient.readUTF();
                      //  System.out.println(customer_name);
                      //  System.out.println("Enter Email Id -");
                        email = inFromClient.readUTF();
                     //   System.out.println(email);
                     //   System.out.println("Enter Phone Number - ");
                        phone = inFromClient.readUTF();
                     //   System.out.println(phone);
                     //   System.out.println("Enter Address -");
                        address = inFromClient.readUTF();
                     //   System.out.println(address);
                     //   System.out.println("Enter Password -");
                        password = inFromClient.readUTF();
                     //   System.out.println(password);

                        query = "SELECT username FROM server_database WHERE username='" + user + "';";
                        rs = Database.executeQuery(query);
                        if (rs.next()) {
                            System.out.println("Username already choosen! Try different Username!");
                            outToClient.writeInt(0);
                        }
                        else {
                            add_to_database("Customer", user, customer_name, email, phone, password, address);
                            outToClient.writeInt(1);
                        }


//                        System.out.println();
//                        System.out.println("You are ready to go! Please Log in. ");
                    }
                    else if (ch == 2) {
                        while (true) {
//                            System.out.println("Enter Username -");
                            user = inFromClient.readUTF();

                            query = "SELECT username FROM server_database WHERE username='" + user + "';";
                            rs = Database.executeQuery(query);
                            if (rs.next()) {
                              //  System.out.println("Username already choosen! Try different Username!");
                                outToClient.writeInt(0);
                            } else {
                                outToClient.writeInt(1);
                                break;
                            }
                        }

                       // System.out.println("Enter Shop Name -");
                        String shop_name = inFromClient.readUTF();
                       // System.out.println(shop_name);
                       // System.out.println("Enter Email Id -");
                        email = inFromClient.readUTF();
                       // System.out.println(email);
                       // System.out.println("Enter Phone Number - ");
                        phone = inFromClient.readUTF();
                       // System.out.println(phone);
                       // System.out.println("Enter Address -");
                        address = inFromClient.readUTF();
                        //System.out.println(address);
                        //System.out.println("Enter Password -");
                        password = inFromClient.readUTF();
                       // System.out.println(password);

                        query = "SELECT username FROM server_database WHERE username='" + user + "';";
                        rs = Database.executeQuery(query);
                        if (rs.next()) {
                              System.out.println("Username already choosen! Try different Username!");
                              outToClient.writeInt(0);
                        }
                        else {
                            add_to_database("Shop", user, shop_name, email, phone, password, address);
                            outToClient.writeInt(1);
                        }
                       // System.out.println("You are ready to go! Please Log in. ");
                    }
                } else if (clientlogintype == 3) {
                   // outToClient.writeUTF("Thank You!");
                    break;
                }
            }

        } catch (EOFException | SocketException e){
            System.out.println("["+port+"] 499 client closed request");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("closed: "+connectionSocket.getInetAddress()+":"+port);
            try {
                connectionSocket.close();
            }catch (IOException e){ }
        }
    }
}

