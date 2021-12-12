

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.InternationalFormatter;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;


class ShopGUI extends JPanel {

    JButton b1, b2,b3;
    JLabel l1, l2, l3;
    JRadioButton rb1, rb2, rb3, rb4, rb5, rb6;

    public ShopGUI(int i) {
        setLayout(null);
        if (i == 1) {
            initialize_details_shop_menu();
            addActionListener1();
        }
    }

    public ShopGUI(String cat){
        setLayout(null);
        initialize_details_category_menu();
        addActionListener2(cat);
    }



    public void initialize_details_shop_menu() {

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

        rb5 = new JRadioButton("DISPLAY SHOP DATABASE");
        rb5.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        rb5.setBounds(300, 282, 400, 30);

        rb6 = new JRadioButton("CUSTOMER DATA");
        rb6.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        rb6.setBounds(300, 312, 400, 30);


        b1 = new JButton("PROCEED");
        b1.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        b1.setBounds(275, 362, 240, 30);
        b1.setBackground(Color.GRAY);

        b2 = new JButton("LOGOUT");
        b2.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        b2.setBounds(250, 422, 300, 30);
        b2.setBackground(Color.GRAY);


        l3 = new JLabel("                            ");
        l3.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        l3.setBounds(280, 400, 268, 35);

        ButtonGroup bg = new ButtonGroup();
        bg.add(rb1);
        bg.add(rb2);
        bg.add(rb3);
        bg.add(rb4);
        bg.add(rb5);
        bg.add(rb6);
        //bg.add(rb7);

        add(rb1);
        add(rb2);
        add(rb3);
        add(rb4);
        add(rb5);
        add(rb6);
        //add(rb7);

        add(b1);
        add(b2);
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
                    adGui.jf.setContentPane(new ShopGUI("INSECTICIDES"));
                    adGui.jf.setVisible(true);
                }

                if (rb2.isSelected()) {
                    adGui.jf.setTitle("Pesticides Menu");
                    adGui.jf.getContentPane().removeAll();
                    adGui.outToServer.writeUTF("PESTICIDES");
                    adGui.jf.setContentPane(new ShopGUI("PESTICIDES"));
                    adGui.jf.setVisible(true);
                }

                if (rb3.isSelected()) {
                    adGui.jf.setTitle("Crop Seeds Menu");
                    adGui.jf.getContentPane().removeAll();
                    adGui.outToServer.writeUTF("CROP SEEDS");
                    adGui.jf.setContentPane(new ShopGUI("CROP SEEDS"));
                    adGui.jf.setVisible(true);
                }

                if (rb4.isSelected()) {
                    adGui.jf.setTitle("Tools Menu");
                    adGui.outToServer.writeUTF("TOOLS");
                    adGui.jf.getContentPane().removeAll();
                    adGui.jf.setContentPane(new ShopGUI("TOOLS"));
                    adGui.jf.setVisible(true);
                }
                if (rb5.isSelected()){
                    try {
                        adGui.outToServer.writeUTF("DISPLAY SHOP DATABASE");
                    }
                    catch (Exception ex){
                        System.out.println(ex+" Logout");
                    }
                    JFrame fr=new JFrame();
                    fr.setTitle("Shop Database");

                    DefaultTableModel model = new DefaultTableModel();
                    model.addColumn("Product");
                    model.addColumn("Cost");
                    model.addColumn("Category");

                    print_shop(model);
                    print_shop(model);
                    print_shop(model);
                    print_shop(model);
                    JTable jt=new JTable(model);
                    jt.setLocation(25,25);
                    fr.add(jt);
                    JScrollPane sp=new JScrollPane(jt);
                    fr.add(sp);
                    //fr.setLocation(750, 250);
                    fr.setSize(300,300);
                    fr.setVisible(true);

                    //l1.setHorizontalAlignment(SwingConstants.CENTER);

//                    JLabel l1 = new JLabel("Agro - Mart ", SwingConstants.CENTER);
//                    l1.setFont(new Font("Eras Bold ITC", Font.ITALIC, 50));
//                    l1.setBounds(45, 10, 700, 90);
//                    l1.setOpaque(true);
//                    l1.setBackground(Color.GRAY);
//                    l1.setAlignmentX(6f);
//                    fr.add(l1);

                }
                if(rb6.isSelected()){
                    try {
                        adGui.outToServer.writeUTF("CUSTOMER DATA");
                    }
                    catch (Exception ex){
                        System.out.println(ex+" Logout");
                    }
//                    print_shop();
                    JFrame fr=new JFrame();
                    fr.setTitle("Customer Details Database");

                    DefaultTableModel model = new DefaultTableModel();
                    model.addColumn("Customer");
                    model.addColumn("No. of times purchased from the shop");
                    model.addColumn("Total Amount");

                    print_custdata(model);

                    JTable jt=new JTable(model);
                    jt.setLocation(25,25);
                    fr.add(jt);
                    JScrollPane sp=new JScrollPane(jt);
                    fr.add(sp);
                    //fr.setLocation(750, 250);
                    fr.setSize(700,100);
                    fr.setVisible(true);
                    System.out.println();
                }

            }
            catch(Exception ex){
                System.out.println(ex+"Category menu");
            }
        });

        b2.addActionListener(e -> {
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

    public static void print_custdata(DefaultTableModel model){
        try {
            int c=adGui.inFromServer.readInt();

            while (c==1) {
                //System.out.println(adGui.inFromServer.readUTF());
                model.addRow((new Object[] {
                        adGui.inFromServer.readUTF(),
                        adGui.inFromServer.readInt(),
                        adGui.inFromServer.readDouble()}));
                c=adGui.inFromServer.readInt();
            }
            // System.out.println(adGui.inFromServer.readUTF());
        }
        catch (Exception e){
            System.out.println(e+"Print");
        }
    }



    public static void print_shop(DefaultTableModel model){

        try {
            int c=adGui.inFromServer.readInt();
//            if(c==0){
//                System.out.println(adGui.inFromServer.readUTF());
//                System.out.println("-----------------------------------------------------------");
//            }

            while (c==1) {
                //System.out.println(adGui.inFromServer.readUTF());
                model.addRow((new Object[] {
                        adGui.inFromServer.readUTF(),
                        adGui.inFromServer.readDouble(),
                        adGui.inFromServer.readUTF()}));
                c=adGui.inFromServer.readInt();
            }
           // System.out.println(adGui.inFromServer.readUTF());
        }
        catch (Exception e){
            System.out.println(e+"Print");
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

        rb1 = new JRadioButton("View Storage database\n");
        rb1.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        rb1.setBounds(300, 162, 400, 30);

        rb2 = new JRadioButton("Add Product");
        rb2.setBounds(300, 192, 400, 30);
        rb2.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));

        rb3 = new JRadioButton("Remove Product");
        rb3.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        rb3.setBounds(300, 222, 400, 30);

        rb4 = new JRadioButton("Modify cost of a Product");
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
        bg.add(rb5);


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

                    JFrame fr=new JFrame();
                    fr.setTitle(cat+" DATABASE");

                    DefaultTableModel model = new DefaultTableModel();
                    model.addColumn("Product");
                    model.addColumn("Cost");
                    model.addColumn("Category");

                    print_shop(model);

                    JTable jt=new JTable(model);
                    jt.setLocation(25,25);
                    fr.add(jt);
                    JScrollPane sp=new JScrollPane(jt);
                    fr.add(sp);
                    //fr.setLocation(750, 250);
                    fr.setSize(300,300);
                    fr.setVisible(true);
                }
                else if (rb2.isSelected()) {
                    adGui.outToServer.writeInt(2);
                    obj.insert_product();
                } else if (rb3.isSelected()) {
                    adGui.outToServer.writeInt(3);
                    obj.delete_product();
                } else if (rb4.isSelected()) {
                    adGui.outToServer.writeInt(4);
                    obj.modify_product();
                } else
                { l3.setText("Select Option!"); l3.setBounds(175, 400, 268, 20);add(l3);}
                adGui.jf.getContentPane().removeAll();
                adGui.jf.setContentPane(new ShopGUI(cat));
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
                adGui.jf.setTitle("Shop Menu");
                adGui.jf.getContentPane().removeAll();
                adGui.jf.setContentPane(new ShopGUI(1));
                //initialize_details_shop_menu();
                adGui.jf.setVisible(true);

            }
            }
            catch (Exception ex){
                System.out.println(ex+"operation menu");
            }
        });

    }



}

class Functions extends JPanel{

    public void insert_product_cust(HashMap<String,String> prod,HashMap<String,String> cart,String cat) {
        JFrame insert = new JFrame();
        insert.setLayout(null);

        insert.setLocation(750, 250);
        insert.setSize(400,400);
        insert.setTitle("Insert");

        insert.setVisible(true);
        //jf.setResizable(false);
       // insert.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      String options[]=new String[prod.size()];
      int j=0;
        for(String i :prod.keySet()){
            if(prod.get(i).equals(cat)){
                 options[j]=i;
                 j++;
            }
            System.out.println("Item: "+i+" Category: "+prod.get(i));
        }

        JLabel lblItem = new JLabel("Item Name : ");
        lblItem.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        lblItem.setBounds(52, 52, 113, 37);
        insert.add(lblItem);

        JLabel lblCost = new JLabel("Quantity : ");
        lblCost.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        lblCost.setBounds(52, 113, 113, 37);
        insert.add(lblCost);

       // String options[]={"A","B","C"};
        //JFormattedTextField itemField = new JFormattedTextField();
        JComboBox itemField=new JComboBox(options);
        itemField.setFont(new Font("Ubuntu Mono", Font.PLAIN, 15));
        itemField.setBounds(52, 82, 300, 37);
        insert.add(itemField);



        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);

        final JFormattedTextField textField1 = new JFormattedTextField(formatter);

        textField1.setBounds(52, 142, 300, 37);
        insert.add(textField1);

        JButton b1 = new JButton("Insert");
        b1.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        b1.setBounds(52, 250, 268, 20);

        insert.add(b1);

        b1.addActionListener(e-> {
            try {
               //String name = itemField.getText();
                String name = String.valueOf(itemField.getSelectedItem());
                adGui.outToServer.writeUTF(name);
                int quantity=((Number)textField1.getValue()).intValue();
                adGui.outToServer.writeInt(quantity);
                cart.put(name,cat);

                JFrame f=new JFrame();
                JOptionPane.showMessageDialog(f,"Inserted Successfully!");
                f.dispose();
                insert.dispose();
            } catch (Exception ex) {
                System.out.println(ex+"Insert");
            }
        });

    }

    public void insert_product() {
        JFrame insert = new JFrame();
        insert.setLayout(null);

        insert.setLocation(750, 250);
        insert.setSize(400,400);
        insert.setTitle("Insert");

        insert.setVisible(true);
        //jf.setResizable(false);
        insert.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JLabel lblItem = new JLabel("Item Name : ");
        lblItem.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        lblItem.setBounds(52, 52, 113, 37);
        insert.add(lblItem);

        JLabel lblCost = new JLabel("Cost : ");
        lblCost.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        lblCost.setBounds(52, 113, 113, 37);
        insert.add(lblCost);


        JFormattedTextField itemField = new JFormattedTextField();
        itemField.setFont(new Font("Ubuntu Mono", Font.PLAIN, 15));
        itemField.setBounds(52, 82, 300, 37);
        insert.add(itemField);

        final JFormattedTextField textField1 = new JFormattedTextField();
        textField1.setFormatterFactory(new JFormattedTextField.AbstractFormatterFactory() {

            @Override
            public JFormattedTextField.AbstractFormatter getFormatter(JFormattedTextField tf) {
                NumberFormat format = DecimalFormat.getInstance();
                format.setMinimumFractionDigits(2);
                format.setMaximumFractionDigits(2);
                format.setRoundingMode(RoundingMode.HALF_UP);
                InternationalFormatter formatter = new InternationalFormatter(format);
                formatter.setAllowsInvalid(false);
                formatter.setMinimum(0.0);
                formatter.setMaximum(1000000.00);
                return formatter;
            }
        });
        textField1.setBounds(52, 142, 300, 37);
        insert.add(textField1);

        JButton b1 = new JButton("Insert");
        b1.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        b1.setBounds(52, 250, 268, 20);

        insert.add(b1);

        b1.addActionListener(e-> {
            try {
                String name = itemField.getText();
                adGui.outToServer.writeUTF(name);
                String c = textField1.getText();
                float cost=Float.parseFloat(c);
                adGui.outToServer.writeDouble(cost);
                JFrame f=new JFrame();
                JOptionPane.showMessageDialog(f,"Inserted Successfully!");
                f.dispose();
                insert.dispose();
            } catch (Exception ex) {
                System.out.println(ex+"Insert");
            }
        });

    }

    public void delete_product() {
        JFrame insert = new JFrame();
        insert.setLayout(null);

        insert.setLocation(750, 250);
        insert.setSize(400,400);
        insert.setTitle("Insert");

        insert.setVisible(true);
        //jf.setResizable(false);
        insert.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JLabel lblItem = new JLabel("Item Name : ");
        lblItem.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        lblItem.setBounds(52, 52, 113, 37);
        insert.add(lblItem);

        JFormattedTextField itemField = new JFormattedTextField();
        itemField.setFont(new Font("Ubuntu Mono", Font.PLAIN, 15));
        itemField.setBounds(52, 82, 300, 37);
        insert.add(itemField);


        JButton b1 = new JButton("Delete");
        b1.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        b1.setBounds(52, 150, 268, 20);

        insert.add(b1);

        b1.addActionListener(e-> {
            try {
                String name = itemField.getText();
                adGui.outToServer.writeUTF(name);
                String msg=adGui.inFromServer.readUTF();
                JFrame f=new JFrame();
                JOptionPane.showMessageDialog(f,msg);
                f.dispose();
                insert.dispose();
            }
            catch (Exception ex){
                System.out.println(ex+"Delete");
            }
        });


    }


    public void modify_product_cust() {
        JFrame insert = new JFrame();
        insert.setLayout(null);

        insert.setLocation(750, 250);
        insert.setSize(400,400);
        insert.setTitle("Insert");

        insert.setVisible(true);
        //jf.setResizable(false);
        insert.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JLabel lblItem = new JLabel("Item Name : ");
        lblItem.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        lblItem.setBounds(52, 52, 113, 37);
        insert.add(lblItem);

        JLabel lblCost = new JLabel("New Quantity : ");
        lblCost.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        lblCost.setBounds(52, 113, 113, 37);
        insert.add(lblCost);

        JFormattedTextField itemField = new JFormattedTextField();
        itemField.setFont(new Font("Ubuntu Mono", Font.PLAIN, 15));
        itemField.setBounds(52, 82, 300, 37);
        insert.add(itemField);


        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);

        final JFormattedTextField textField1 = new JFormattedTextField(formatter);

        textField1.setBounds(52, 142, 300, 37);
        insert.add(textField1);

        JButton b1 = new JButton("Modify");
        b1.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        b1.setBounds(52, 250, 268, 20);

        insert.add(b1);

        b1.addActionListener(e-> {
            try {
                String name = itemField.getText();
                adGui.outToServer.writeUTF(name);
                int quantity=((Number)textField1.getValue()).intValue();
                adGui.outToServer.writeInt(quantity);
                String msg=adGui.inFromServer.readUTF();
                JFrame f=new JFrame();
                JOptionPane.showMessageDialog(f,msg);
                f.dispose();
                insert.dispose();
            } catch (Exception ex) {
                System.out.println(ex+"Modify");
            }
        });
    }



    public void modify_product() {
        JFrame insert = new JFrame();
        insert.setLayout(null);

        insert.setLocation(750, 250);
        insert.setSize(400,400);
        insert.setTitle("Insert");

        insert.setVisible(true);
        //jf.setResizable(false);
        insert.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JLabel lblItem = new JLabel("Item Name : ");
        lblItem.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        lblItem.setBounds(52, 52, 113, 37);
        insert.add(lblItem);

        JLabel lblCost = new JLabel("New Cost : ");
        lblCost.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        lblCost.setBounds(52, 113, 113, 37);
        insert.add(lblCost);


        JFormattedTextField itemField = new JFormattedTextField();
        itemField.setFont(new Font("Ubuntu Mono", Font.PLAIN, 15));
        itemField.setBounds(52, 82, 300, 37);
        insert.add(itemField);

        final JFormattedTextField textField1 = new JFormattedTextField();
        textField1.setFormatterFactory(new JFormattedTextField.AbstractFormatterFactory() {

            @Override
            public JFormattedTextField.AbstractFormatter getFormatter(JFormattedTextField tf) {
                NumberFormat format = DecimalFormat.getInstance();
                format.setMinimumFractionDigits(2);
                format.setMaximumFractionDigits(2);
                format.setRoundingMode(RoundingMode.HALF_UP);
                InternationalFormatter formatter = new InternationalFormatter(format);
                formatter.setAllowsInvalid(false);
                formatter.setMinimum(0.0);
                formatter.setMaximum(1000000.00);
                return formatter;
            }
        });
        textField1.setBounds(52, 142, 300, 37);
        insert.add(textField1);

        JButton b1 = new JButton("Modify");
        b1.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        b1.setBounds(52, 250, 268, 20);

        insert.add(b1);

        b1.addActionListener(e-> {
            try {
                String name = itemField.getText();
                adGui.outToServer.writeUTF(name);
                String c = textField1.getText();
                float cost=Float.parseFloat(c);
                adGui.outToServer.writeDouble(cost);
                String msg=adGui.inFromServer.readUTF();
                JFrame f=new JFrame();
                JOptionPane.showMessageDialog(f,msg);
                f.dispose();
                insert.dispose();
            } catch (Exception ex) {
                System.out.println(ex+"Modify");
            }
        });
    }

    public void errormessage() {
        JFrame insert = new JFrame();
        insert.setLayout(null);

        insert.setLocation(750, 250);
        insert.setSize(300,200);
        insert.setTitle("Error!");

        insert.setVisible(true);
        //jf.setResizable(false);
        //insert.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JLabel lblError = new JLabel("USERNAME ALREADY USED! ");
        lblError.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        lblError.setBounds(32, 52, 400, 37);
        insert.add(lblError);

        JButton b1 = new JButton("OK");
        b1.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        b1.setBounds(92, 100, 100, 20);

        insert.add(b1);

        b1.addActionListener(e-> {
                    insert.dispose();
                }
        );
    }


    public void usernotpresentmessage() {
        JFrame insert = new JFrame();
        insert.setLayout(null);

        insert.setLocation(750, 250);
        insert.setSize(300,200);
        insert.setTitle("Error!");

        insert.setVisible(true);
        //jf.setResizable(false);
        //insert.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JLabel lblError = new JLabel("USERNAME NOT FOUND! ");
        lblError.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        lblError.setBounds(32, 52, 400, 37);
        insert.add(lblError);

        JButton b1 = new JButton("OK");
        b1.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        b1.setBounds(92, 100, 100, 20);

        insert.add(b1);

        b1.addActionListener(e-> {
                    insert.dispose();
                }
        );
    }

    public void invalidPassmessage() {
        JFrame insert = new JFrame();
        insert.setLayout(null);

        insert.setLocation(750, 250);
        insert.setSize(300,200);
        insert.setTitle("Error!");

        insert.setVisible(true);
        //jf.setResizable(false);
        //insert.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JLabel lblError = new JLabel("INVALID PASSWORD! ");
        lblError.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        lblError.setBounds(32, 52, 400, 37);
        insert.add(lblError);

        JButton b1 = new JButton("OK");
        b1.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        b1.setBounds(92, 100, 100, 20);

        insert.add(b1);

        b1.addActionListener(e-> {
                    insert.dispose();
                }
        );
    }

    public void accessmessage() {
        JFrame insert = new JFrame();
        insert.setLayout(null);

        insert.setLocation(750, 250);
        insert.setSize(600,150);
        insert.setTitle("Error!");

        insert.setVisible(true);
        //jf.setResizable(false);
        //insert.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JLabel lblError = new JLabel("Access not granted : USER ALREADY LOGGED IN FROM ANOTHER DEVICE!! ");
        lblError.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        lblError.setBounds(12, 22, 700, 37);
        insert.add(lblError);

        JButton b1 = new JButton("OK");
        b1.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        b1.setBounds(252, 70, 100, 20);

        insert.add(b1);

        b1.addActionListener(e-> {
                    insert.dispose();
                }
        );
    }

}











