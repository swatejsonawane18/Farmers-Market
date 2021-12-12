import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;


public class Register extends JPanel {
    JButton b1;
    JLabel l1, l2, l3;
    JRadioButton rb1, rb2;
    private JPasswordField passwordField, rpasswordField;
    private JLabel lblNewLabel,lblName, lblEmail, lblPhone, lblAddress,lblUser;
    private JLabel lblPassword, lblRPassword;
    private JFormattedTextField usernameField,nameField, emailField ,addressField,phoneField;
    private JButton signUp;
    private JButton signIn;
    private JLabel message;
    JLabel msg;

    Register(){

            setLayout(null);
            initialize_details();
            addActionListener();

    }

    public void initialize_details() {

        l1 = new JLabel("Agro - Mart ", SwingConstants.CENTER);
        l1.setFont(new Font("Eras Bold ITC", Font.ITALIC, 50));
        l1.setBounds(45, 10, 700, 90);
        l1.setOpaque(true);
        l1.setBackground(Color.GRAY);
        l1.setAlignmentX(6f);
        add(l1);

        l2 = new JLabel("Registration Details - ");
        l2.setFont(new Font("Ubuntu Mono", Font.BOLD, 17));
        l2.setBounds(52, 100, 268, 30);
        add(l2);

//        System.out.println("Enter Customer Name -");
//        String customer_name = scan.nextLine();
//        outToServer.writeUTF(customer_name);
//        System.out.println("Enter Email Id -");
//        email = scan.nextLine();
//        outToServer.writeUTF(email);
//        System.out.println("Enter Phone Number - ");
//        phone = scan.nextLine();
//        outToServer.writeUTF(phone);
//        System.out.println("Enter Address -");
//        address = scan.nextLine();
//        outToServer.writeUTF(address);
//        System.out.println("Enter Password -");
//        password = scan.nextLine();

        lblUser = new JLabel("User Type : ");
        lblUser.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        lblUser.setBounds(52, 132, 113, 30);
        add(lblUser);


        rb1 = new JRadioButton("Customer");
        rb1.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        rb1.setBounds(150, 132, 100, 30);


        rb2 = new JRadioButton("Shop");
        rb2.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        rb2.setBounds(250 ,132, 268, 30);
        ButtonGroup bg = new ButtonGroup();
        bg.add(rb1);
        bg.add(rb2);

        lblNewLabel = new JLabel("User Name : ");
        lblNewLabel.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        lblNewLabel.setBounds(52, 162, 113, 30);
        add(lblNewLabel);

        usernameField = new JFormattedTextField();
        usernameField.setFont(new Font("Ubuntu Mono", Font.PLAIN, 15));
        usernameField.setBounds(52, 192, 300, 25);
        add(usernameField);


        lblName = new JLabel("Customer Name / Shop Name : ");
        lblName.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        lblName.setBounds(52, 222, 500, 37);
        add(lblName);

        nameField = new JFormattedTextField();
        nameField.setFont(new Font("Ubuntu Mono", Font.PLAIN, 15));
        nameField.setBounds(52, 252, 300, 25);
        add(nameField);

        lblEmail = new JLabel("Email Id : ");
        lblEmail.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        lblEmail.setBounds(52, 282, 113, 37);
        add(lblEmail);

        emailField = new JFormattedTextField();
        emailField.setFont(new Font("Ubuntu Mono", Font.PLAIN, 15));
        emailField.setBounds(52, 312, 300, 25);
        add(emailField);

        lblPhone = new JLabel("Phone Number : ");
        lblPhone.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        lblPhone.setBounds(52, 342, 240, 37);
        add(lblPhone);

        phoneField = new JFormattedTextField();
        phoneField.setFont(new Font("Ubuntu Mono", Font.PLAIN, 15));
        phoneField.setBounds(52, 372, 300, 25);
        add(phoneField);

        lblAddress = new JLabel("Address : ");
        lblAddress.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        lblAddress.setBounds(52, 402, 113, 37);
        add(lblAddress);

        addressField = new JFormattedTextField();
        addressField.setFont(new Font("Ubuntu Mono", Font.PLAIN, 15));
        addressField.setBounds(52, 432, 300, 25);
        add(addressField);


        lblPassword = new JLabel("Password : ");
        lblPassword.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        lblPassword.setBounds(52, 462, 113, 37);
        add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Ubuntu Mono", Font.PLAIN, 15));
        passwordField.setBounds(52, 492, 300, 25);
        add(passwordField);


        lblRPassword = new JLabel("Repeat Password : ");
        lblRPassword.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        lblRPassword.setBounds(52, 522, 240, 37);
        add(lblRPassword);


        rpasswordField = new JPasswordField();
        rpasswordField.setFont(new Font("Ubuntu Mono", Font.PLAIN, 15));
        rpasswordField.setBounds(52, 552, 300, 25);
        add(rpasswordField);

        b1 = new JButton("Register");
        b1.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        b1.setBackground(Color.GRAY);
        b1.setBounds(52, 590, 268, 30);


        l3 = new JLabel("                            ");
        l3.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        l3.setBounds(210, 355, 268, 35);


        add(b1);
        add(l3);
        add(rb1);
        add(rb2);

    }

    public void addActionListener() {

        b1.addActionListener(e -> {
            try {
            if(rb2.isSelected()) {
                adGui.outToServer.writeInt(2);
            }
            if (rb1.isSelected())
            {  adGui.outToServer.writeInt(1);}

                        String username = usernameField.getText();
                        adGui.outToServer.writeUTF(username);
                        int ch = adGui.inFromServer.readInt();
                        //msg.removeAll();
                        if (ch == 0) {
                            System.out.println("Username already choosen!");
                            adGui.jf.getContentPane().removeAll();
                            adGui.jf.setContentPane(new Main_Page());
                            adGui.jf.setVisible(true);
                            Functions obj=new Functions();
                            obj.errormessage();

                        } else {
                            String name = nameField.getText();
                            adGui.outToServer.writeUTF(name);
                            String email = emailField.getText();
                            adGui.outToServer.writeUTF(email);
                            String phone = phoneField.getText();
                            adGui.outToServer.writeUTF(phone);
                            String address = addressField.getText();
                            adGui.outToServer.writeUTF(address);
                            String password = passwordField.getText();
                            adGui.outToServer.writeUTF(password);


                            ch = adGui.inFromServer.readInt();
                            if (ch == 0) {
                                System.out.println("Username already choosen!");
                                Functions obj=new Functions();
                                obj.errormessage();
                                adGui.jf.getContentPane().removeAll();
                                adGui.jf.setContentPane(new Main_Page());
                                adGui.jf.setVisible(true);
                                return;
                            } else {
                                msg.removeAll();
                                msg = new JLabel("You are ready to go! Please Log in.");
                                msg.setFont(new Font("Ubuntu Mono", Font.ITALIC, 15));
                                msg.setBounds(72, 630, 113, 30);
                                add(msg);
                            }
                        }
                }
                catch (Exception ex){
                    System.out.println("In register"+ex);
                }
        });
    }

}