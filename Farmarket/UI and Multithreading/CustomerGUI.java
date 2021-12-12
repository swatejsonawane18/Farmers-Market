import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Scanner;


class CustomerGUI extends JPanel {

    JButton b1, b2, b3;
    JLabel l1, l2, l3;
    JRadioButton rb1, rb2, rb3, rb4, rb5;
    HashMap<String, String> cart = new HashMap<String, String>();
    HashMap<String, String> product = new HashMap<String, String>();
    public CustomerGUI(int i) {
        setLayout(null);
        if (i == 1) {
            initialize_details_customer_menu();
            addActionListener1();
        }
    }

    public CustomerGUI(String cat){
        setLayout(null);
        initialize_details_category_menu();
        addActionListener2(cat);
    }



    public void initialize_details_customer_menu() {

        //jp.setBackground(Color.WHITE);

        l1 = new JLabel("Agro - Mart ", SwingConstants.CENTER);
        l1.setFont(new Font("Eras Bold ITC", Font.ITALIC, 50));
        l1.setBounds(45, 10, 700, 90);
        l1.setOpaque(true);
        l1.setBackground(Color.GRAY);
        l1.setAlignmentX(6f);
        add(l1);

        l2 = new JLabel("CATEGORIES",SwingConstants.CENTER);
        //l1.setHorizontalAlignment(SwingConstants.CENTER);
        l2.setFont(new Font("Eras Bold ITC", Font.BOLD, 20));
        l2.setOpaque(true);
        l2.setBackground(Color.GRAY);
        l2.setBounds(250, 112, 300, 30);
        add(l2);

        rb1 = new JRadioButton("INSECTICIDES\n");
        rb1.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        rb1.setBounds(300, 162, 400, 30);

        rb2 = new JRadioButton("PESTICIDES");
        rb2.setBounds(300, 192, 400, 30);
        rb2.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));

        rb3 = new JRadioButton("CROP SEEDS");
        rb3.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        rb3.setBounds(300, 222, 400, 30);

        rb4 = new JRadioButton("TOOLS");
        rb4.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        rb4.setBounds(300, 252, 400, 30);

        rb5 = new JRadioButton("DISPLAY CART");
        rb5.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        rb5.setBounds(300, 282, 400, 30);


        b1 = new JButton("PROCEED");
        b1.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        b1.setBounds(275, 362, 240, 30);
        b1.setBackground(Color.GRAY);

        b2 = new JButton("BUY CART PRODUCTS");
        b2.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        b2.setBounds(250, 422, 300, 30);
        b2.setBackground(Color.GRAY);

        b3 = new JButton("LOGOUT");
        b3.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        b3.setBounds(250, 482, 300, 30);
        b3.setBackground(Color.GRAY);

        l3 = new JLabel("                            ");
        l3.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        l3.setBounds(280, 400, 268, 35);

        ButtonGroup bg = new ButtonGroup();
        bg.add(rb1);
        bg.add(rb2);
        bg.add(rb3);
        bg.add(rb4);
        bg.add(rb5);

        add(rb1);
        add(rb2);
        add(rb3);
        add(rb4);
        add(rb5);

        add(b1);
        add(b2);
        add(b3);
        add(l3);
    }

    public void addActionListener1() {
        try{
            b1.addActionListener(e -> {
                try {
                    if (rb1.isSelected()) {
                        adGui.jf.setTitle("Insecticides Menu");
                        adGui.jf.getContentPane().removeAll();
                        adGui.outToServer.writeUTF("INSECTICIDES");
                        adGui.jf.setContentPane(new CustomerGUI("INSECTICIDES"));
                        adGui.jf.setVisible(true);
                    }

                    if (rb2.isSelected()) {
                        adGui.jf.setTitle("Pesticides Menu");
                        adGui.jf.getContentPane().removeAll();
                        adGui.outToServer.writeUTF("PESTICIDES");
                        adGui.jf.setContentPane(new CustomerGUI("PESTICIDES"));
                        adGui.jf.setVisible(true);
                    }

                    if (rb3.isSelected()) {
                        adGui.jf.setTitle("Crop Seeds Menu");
                        adGui.jf.getContentPane().removeAll();
                        adGui.outToServer.writeUTF("CROP SEEDS");
                        adGui.jf.setContentPane(new CustomerGUI("CROP SEEDS"));
                        adGui.jf.setVisible(true);
                    }

                    if (rb4.isSelected()) {
                        adGui.jf.setTitle("Tools Menu");
                        adGui.outToServer.writeUTF("TOOLS");
                        adGui.jf.getContentPane().removeAll();
                        adGui.jf.setContentPane(new CustomerGUI("TOOLS"));
                        adGui.jf.setVisible(true);
                    }
                    if (rb5.isSelected()){
                        try {
                            adGui.outToServer.writeUTF("DISPLAY CART");
                        }
                        catch (Exception ex){
                            System.out.println(ex+" CART");
                        }
                        JFrame frame=new JFrame();
                        frame.setTitle("Customer Database");

                        DefaultTableModel model = new DefaultTableModel();
                        model.addColumn("Product");
                        model.addColumn("Quantity");
                        model.addColumn("Category");
                        System.out.println("Your Cart Products are - ");
                        print_customer(model);
                        print_customer(model);
                        print_customer(model);
                        print_customer(model);
                        if(adGui.inFromServer.readInt()==0){
                            JFrame fr=new JFrame();
                            JOptionPane.showMessageDialog(fr,"CART IS EMPTY!");
                            fr.dispose();
                        }
                        else{
                            JTable jt=new JTable(model);
                            jt.setLocation(25,25);
                            frame.add(jt);
                            JScrollPane sp=new JScrollPane(jt);
                            frame.add(sp);
                            //fr.setLocation(750, 250);
                            frame.setSize(300,300);
                            frame.setVisible(true);
                        }
                        //continue;
                        adGui.jf.getContentPane().removeAll();
                        adGui.jf.setContentPane(new CustomerGUI(1));
                        adGui.jf.setVisible(true);
                    }
                }
                catch(Exception ex){
                    System.out.println(ex+"Category menu");
                }
            });

            b2.addActionListener(e -> {

                try {
                    adGui.outToServer.writeUTF("BUY CART PRODUCTS");

                JFrame frame1=new JFrame();
                frame1.setTitle("Customer Cart");

                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Product");
                model.addColumn("Quantity");
                model.addColumn("Category");
                System.out.println("Your Cart Products are - ");
                model=print_customer(model);
                    model=print_customer(model);
                    model=print_customer(model);
                    model=print_customer(model);
                if(adGui.inFromServer.readInt()==0){
                    JFrame fr=new JFrame();
                    JOptionPane.showMessageDialog(fr,"CART IS EMPTY!");
                    fr.dispose();
                }
                else{
                    JTable jt=new JTable(model);

                    frame1.add(jt);
                    jt.setLocation(25,25);
//                    JScrollPane sp=new JScrollPane(jt);
//                    frame1.add(sp);
                    //fr.setLocation(750, 250);
                    frame1.setSize(300,300);
                    frame1.setVisible(true);
                }
                //continue;


                        //continue;


                        System.out.println();
                        System.out.println("Options are -");
                        int f = adGui.inFromServer.readInt();

                        if (f == 1) {

                            //Handle obj=new Handle();
                            adGui.jf.getContentPane().removeAll();
                            adGui.jf.setContentPane(new Handle());
                            adGui.jf.setVisible(true);
                            //obj.Client_exitHandler();
                        } else {
                            adGui.jf.getContentPane().removeAll();
                            adGui.jf.setContentPane(new CustomerGUI(1));
                            adGui.jf.setVisible(true);

                        }
//                    adGui.jf.getContentPane().removeAll();
//                    adGui.jf.setContentPane(new CustomerGUI(1));
//                    adGui.jf.setVisible(true);
                    }
                    catch (Exception eg){
                        System.out.println(eg);
                    }
            });

            b3.addActionListener(e -> {
                //l2.setText("Login");
                try {
                    adGui.outToServer.writeUTF("EXIT");
                }
                catch (Exception ex){
                    System.out.println(ex+" Logout");
                }
                adGui.jf.setTitle("Login");
                adGui.jf.getContentPane().removeAll();
                JFrame f=new JFrame();
                JOptionPane.showMessageDialog(f,"Logged Out Successfully!");
                f.dispose();
                adGui.jf.setContentPane(new Main_Page());
                adGui.jf.setVisible(true);
            });
        }
        catch(Exception ex){
            System.out.println(ex+"category menu");
        }
    }


    public void initialize_details_category_menu() {

        //jp.setBackground(Color.WHITE);;

        l1 = new JLabel("Agro - Mart ", SwingConstants.CENTER);
        l1.setFont(new Font("Eras Bold ITC", Font.ITALIC, 50));
        l1.setBounds(45, 10, 700, 90);
        l1.setOpaque(true);
        l1.setBackground(Color.GRAY);
        l1.setAlignmentX(6f);
        add(l1);

        l2 = new JLabel("OPERATIONS",SwingConstants.CENTER);
        l2.setFont(new Font("Eras Bold ITC", Font.BOLD, 20));
        l2.setOpaque(true);
        l2.setBackground(Color.GRAY);
        l2.setBounds(250, 112, 300, 30);
        add(l2);

        rb1 = new JRadioButton("View Cart");
        rb1.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        rb1.setBounds(300, 162, 400, 30);

        rb2 = new JRadioButton("Add Product");
        rb2.setBounds(300, 192, 400, 30);
        rb2.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));

        rb3 = new JRadioButton("Remove Product");
        rb3.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        rb3.setBounds(300, 222, 400, 30);

        rb4 = new JRadioButton("Modify Quantity of a Product");
        rb4.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        rb4.setBounds(300, 252, 400, 30);

        b1 = new JButton("Conduct Operation");
        b1.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        b1.setBounds(275, 302, 240, 30);
        b1.setBackground(Color.GRAY);

        b2 = new JButton("BACK");
        b2.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        b2.setBounds(250, 352, 300, 30);
        b2.setBackground(Color.GRAY);

        ButtonGroup bg = new ButtonGroup();
        bg.add(rb1);
        bg.add(rb2);
        bg.add(rb3);
        bg.add(rb4);
        //bg.add(rb5);


        add(rb1);
        add(rb2);
        add(rb3);
        add(rb4);
        //add(rb5);


        add(b1);
        add(b2);
        add(l2);
    }

    public void addActionListener2(String cat) {
        b1.addActionListener(e -> {

            try {
                Functions obj = new Functions();
                if (rb1.isSelected()) {
                    adGui.outToServer.writeInt(1);
                    JFrame frame=new JFrame();
                    frame.setTitle("Customer Cart");
                    DefaultTableModel model = new DefaultTableModel();
                    model.addColumn("Product");
                    model.addColumn("Quantity");
                    model.addColumn("Category");
                    System.out.println("Your Cart Products are - ");
                    print_customer(model);

                    if(adGui.inFromServer.readInt()==0){
                        JFrame fr=new JFrame();
                        JOptionPane.showMessageDialog(fr,"CART IS EMPTY!");
                        fr.dispose();
                    }
                    else{
                        JTable jt=new JTable(model);
                        jt.setLocation(25,25);
                        frame.add(jt);
                        JScrollPane sp=new JScrollPane(jt);
                        frame.add(sp);
                        //fr.setLocation(750, 250);
                        frame.setSize(300,300);
                        frame.setVisible(true);
                    }
//                    int f=print_customer();
//                    if(f==0){
//                        System.out.println(adGui.inFromServer.readUTF());
//                        System.out.println();
//                        JFrame fr=new JFrame();
//                        JOptionPane.showMessageDialog(fr,"CART IS EMPTY!");
//                        fr.dispose();
//                    }
//                    else{
//
//                    }
                }
                else if (rb2.isSelected()) {
                    adGui.outToServer.writeInt(2);
                    getProducts();
                    obj.insert_product_cust(product,cart,cat);


                } else if (rb3.isSelected()) {
                    adGui.outToServer.writeInt(3);
                    obj.delete_product();
                } else if (rb4.isSelected()) {
                    adGui.outToServer.writeInt(4);
                    obj.modify_product_cust();
                } else
                { l3.setText("Select Option!"); l3.setBounds(175, 400, 268, 20);add(l3);}
                adGui.jf.getContentPane().removeAll();
                adGui.jf.setContentPane(new CustomerGUI(cat));
                //initialize_details_category_menu();
                adGui.jf.setVisible(true);
                //addActionListener2(cat);
            }
            catch (Exception ex){
                System.out.println(ex+"Operation menu");
                //System.out.println(ex.getLocalizedMessage());
            }
        });


        b2.addActionListener(e-> {
            try {
                adGui.outToServer.writeInt(5);
                int choice = adGui.inFromServer.readInt();
                if (choice == 0){
                    adGui.jf.setTitle("Customer Menu");
                    adGui.jf.getContentPane().removeAll();
                    adGui.jf.setContentPane(new CustomerGUI(1));
                    //initialize_details_shop_menu();
                    adGui.jf.setVisible(true);

                }
            }
            catch (Exception ex){
                System.out.println(ex+"operation menu");
            }
        });

    }

    public DefaultTableModel print_customer(DefaultTableModel model){

        try {
            int c=adGui.inFromServer.readInt();
//
            while (c==1) {
                System.out.println("LOOP");
                model.addRow((new Object[] {
                        adGui.inFromServer.readUTF(),
                        adGui.inFromServer.readInt(),
                        adGui.inFromServer.readUTF()}));
                c=adGui.inFromServer.readInt();
            }
            // System.out.println(adGui.inFromServer.readUTF());
        }
        catch (Exception e){
            System.out.println(e+"Print");
        }
        return model;
    }

//    public static void check(){
//        try {
//            int c=0;
//            while (adGui.inFromServer.readInt() == 1) {
//                c=adGui.inFromServer.readInt();
//                if (c==1) {
//                    System.out.println(adGui.inFromServer.readUTF());
//                } else {
//                    //System.out.println();
//                    System.out.println(adGui.inFromServer.readUTF());
//                }
//            }
//            //System.out.println();
//            return;
//        }
//        catch (Exception e){
//            e.getStackTrace();
//        }
//    }
//
//    public static void Client_exitHandler(){
//        Scanner sc=new Scanner(System.in);
//        try {
//            JFrame fr=new JFrame();
//            fr.setTitle("DATABASE");
//
//
//            while (adGui.inFromServer.readInt()==1) {
//                System.out.println(adGui.inFromServer.readUTF());
//                System.out.println();
//                System.out.println("Item    Qty    Cost");
//                check();
//                check();
//                check();
//                check();
//                System.out.println(adGui.inFromServer.readUTF());
//                System.out.println();
//                System.out.println();
//            }
//            if(adGui.inFromServer.readInt()==1) {
//                System.out.println("BEST OPTION - ");
//                System.out.println(adGui.inFromServer.readUTF());
//                System.out.println(adGui.inFromServer.readUTF());
//                System.out.println();
//                System.out.println("Do you want to BUY? ( 1 - Yes / 0 - No )");
//                int f = sc.nextInt();
//                sc.nextLine();
//                adGui.outToServer.writeInt(f);
//                if (f == 1) {
////                    int k = sc.nextInt();
////                    sc.nextLine();
////                    outToServer.writeInt(k);
//                    System.out.println("Buying....");
//                    System.out.println("Thanks for shopping!");
//                }
//                else
//                    System.out.println("Cart items are restored!");
//                System.out.println();
//                System.out.println();
//            }
//            else{
//                System.out.println("No Shop has the Products you require!!");
//                System.out.println("Try Later!");
//            }
//        }
//        catch (Exception e){
//            e.getStackTrace();
//        }
//    }

    public void getProducts() {

        String query2 = "select product,category from shop_database;";
        ResultSet rs2 = Database.executeQuery(query2);
        try {
            while (rs2.next()) {
                product.put(rs2.getString(1), rs2.getString(2));
            }
            if(!product.isEmpty()){
                for(String i :product.keySet()){
                    System.out.println("Item: "+i+" Category: "+product.get(i));
                }
            }
            else{
                System.out.println("CART IS EMPTY!");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

}

class Handle extends JPanel {
    int y;

    Handle() {
        y = 0;
        setLayout(null);
//        initialize_details_category_menu();
//        addActionListener2(cat);
        Client_exitHandler();

    }

    public void check() {
        try {
            int c = 0;
            JLabel l2;
            while (adGui.inFromServer.readInt() == 1) {
                c = adGui.inFromServer.readInt();
                if (c == 1) {
                    //System.out.println(adGui.inFromServer.readUTF());
                    l2 = new JLabel(adGui.inFromServer.readUTF());
                    //l2=new JLabel("hi");
                    l2.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
                    l2.setBounds(150, y + 30, 300, 30);
                    y = y + 30;
                    add(l2);
                } else {
                    //System.out.println();
                    //System.out.println(adGui.inFromServer.readUTF());
                    l2 = new JLabel(adGui.inFromServer.readUTF());
                    l2.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
                    l2.setBounds(150, y + 30, 800, 30);
                    y = y + 30;
                    add(l2);
                }
            }
            //System.out.println();
            return;
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void Client_exitHandler() {
        Scanner sc = new Scanner(System.in);
        try {
            JLabel l1, l2;
            l1 = new JLabel("Agro - Mart ", SwingConstants.CENTER);
            l1.setFont(new Font("Eras Bold ITC", Font.ITALIC, 50));
            l1.setBounds(45, 10, 700, 90);
            l1.setOpaque(true);
            l1.setBackground(Color.GRAY);
            l1.setAlignmentX(6f);
            add(l1);
            y = 150;
            while (adGui.inFromServer.readInt() == 1) {
                //System.out.println(adGui.inFromServer.readUTF());
                String s = adGui.inFromServer.readUTF();
                l2 = new JLabel(s, SwingConstants.CENTER);
                l2.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
                l2.setBounds(250, y + 30, 300, 30);
                y = y + 30;
                add(l2);
                //System.out.println();
                //System.out.println("Item    Qty    Cost");
                l2 = new JLabel("Item    Qty    Cost");
                l2.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
                l2.setBounds(150, y + 30, 300, 30);
                y = y + 30;
                add(l2);
                check();
                check();
                check();
                check();
                //System.out.println(adGui.inFromServer.readUTF());
                l2 = new JLabel(adGui.inFromServer.readUTF());
                l2.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
                l2.setBounds(150, y + 30, 300, 30);
                y = y + 30;
                add(l2);
                System.out.println();
                System.out.println();
            }
            if (adGui.inFromServer.readInt() == 1) {
                System.out.println("BEST OPTION - ");
                l2 = new JLabel("BEST OPTION - ",SwingConstants.CENTER);
                l2.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
                l2.setBounds(250, y + 30, 300, 30);
                y = y + 30;
                add(l2);
                //System.out.println(adGui.inFromServer.readUTF());
                l2 = new JLabel(adGui.inFromServer.readUTF(),SwingConstants.CENTER);
                l2.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
                l2.setBounds(250, y + 30, 300, 30);
                y = y + 30;
                add(l2);
                //System.out.println(adGui.inFromServer.readUTF());
                l2 = new JLabel(adGui.inFromServer.readUTF(),SwingConstants.CENTER);
                l2.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
                l2.setBounds(250, y + 30, 300, 30);
                y = y + 30;
                add(l2);
//                System.out.println();
//                System.out.println("Do you want to BUY? ( 1 - Yes / 0 - No )");
//                int f = sc.nextInt();
//                sc.nextLine();

//                Functions obj=new Functions();
//                int f=obj.buyconfirm();

                JButton b1 = new JButton("Confirm Order");
                b1.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
                b1.setBounds(102, y + 60, 300, 20);
                add(b1);

                JButton b2 = new JButton("Cancel");
                b2.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
                b2.setBounds(452, y + 60, 300, 20);
                add(b2);

                b1.addActionListener(e -> {
                    try {
                        adGui.outToServer.writeInt(1);
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                    JFrame insert = new JFrame();
                    insert.setLayout(null);

                    insert.setLocation(750, 250);
                    insert.setSize(300, 200);
                    insert.setTitle("");

                    insert.setVisible(true);
                    //jf.setResizable(false);
                    //insert.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

                    JLabel lblError = new JLabel("Thanks for shopping! ");
                    lblError.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
                    lblError.setBounds(32, 52, 400, 37);
                    insert.add(lblError);

                    JButton b3 = new JButton("OK");
                    b3.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
                    b3.setBounds(92, 100, 100, 20);

                    insert.add(b3);

                    b3.addActionListener(eg -> {
                                insert.dispose();
                        adGui.jf.setTitle("Customer Menu");
                        adGui.jf.getContentPane().removeAll();
                        adGui.jf.setContentPane(new CustomerGUI(1));
                        //initialize_details_shop_menu();
                        adGui.jf.setVisible(true);
                            }
                    );

                });


                b2.addActionListener(e -> {
                    try {
                        adGui.outToServer.writeInt(0);
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
//                    System.out.println("Cart items are restored!");
//                    System.out.println();
//                    System.out.println();

                    JFrame insert2 = new JFrame();
                    insert2.setLayout(null);

                    insert2.setLocation(750, 250);
                    insert2.setSize(300, 200);
                    insert2.setTitle("Error!");

                    insert2.setVisible(true);

                    JLabel lblError = new JLabel("Cart items are restored! ");
                    lblError.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
                    lblError.setBounds(32, 52, 400, 37);
                    insert2.add(lblError);

                    JButton b3 = new JButton("OK");
                    b3.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
                    b3.setBounds(92, 100, 100, 20);

                    insert2.add(b3);

                    b3.addActionListener(ec -> {
                                insert2.dispose();
                        adGui.jf.setTitle("Customer Menu");
                        adGui.jf.getContentPane().removeAll();
                        adGui.jf.setContentPane(new CustomerGUI(1));
                        //initialize_details_shop_menu();
                        adGui.jf.setVisible(true);
                            }
                    );

                });
            } else {
                System.out.println("No Shop has the Products you require!!");
                System.out.println("Try Later!");
                JFrame insert = new JFrame();
                insert.setLayout(null);

                insert.setLocation(750, 250);
                insert.setSize(300, 200);
                insert.setTitle("Error!");

                insert.setVisible(true);
                //jf.setResizable(false);
                //insert.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

                JLabel lblError = new JLabel("No Shop has the Products you require!! Try Later! ");
                lblError.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
                lblError.setBounds(32, 52, 400, 37);
                insert.add(lblError);

                JButton b1 = new JButton("OK");
                b1.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
                b1.setBounds(92, 100, 100, 20);

                insert.add(b1);

                b1.addActionListener(e -> {
                            insert.dispose();
                        }
                );
            }
        } catch (Exception e) {
            e.getStackTrace();
        }

    }
}


