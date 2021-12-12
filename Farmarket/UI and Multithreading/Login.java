import javax.swing.*;
import java.awt.*;

public class Login extends JPanel {
    JButton b1,b2;
    JLabel l1, l2, l3;
    JRadioButton rb1, rb2;
    private JLabel lblUser, lblPass;
    private JFormattedTextField usernameField;
    private JPasswordField passwordField;

    Login() {

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

        lblUser = new JLabel("Username: ");
        lblUser.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        lblUser.setBounds(52, 112, 113, 37);
        add(lblUser);

        lblPass = new JLabel("Password: ");
        lblPass.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        lblPass.setBounds(52, 213, 113, 37);
        add(lblPass);

        usernameField = new JFormattedTextField();
        usernameField.setFont(new Font("Ubuntu Mono", Font.PLAIN, 15));
        usernameField.setBounds(52, 152, 300, 37);
        add(usernameField);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Ubuntu Mono", Font.PLAIN, 15));
        passwordField.setBounds(52, 253, 300, 37);
        add(passwordField);

        b1 = new JButton("Login");
        b1.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        b1.setBackground(Color.GRAY);
        b1.setBounds(52, 300, 268, 30);
        add(b1);

        b2 = new JButton("Back");
        b2.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        b2.setBackground(Color.GRAY);
        b2.setBounds(52, 350, 268, 30);
        add(b2);

    }

    public void addActionListener() {

        b1.addActionListener(e -> {
            try {
                String username = usernameField.getText();
                adGui.outToServer.writeUTF(username);
                int ch = adGui.inFromServer.readInt();
                if(ch==1) {
                    String password = passwordField.getText();
                    adGui.outToServer.writeUTF(password);
                    ch = adGui.inFromServer.readInt();

                    if (ch == 1) {
                        String usertype = adGui.inFromServer.readUTF();
                        if (usertype.equals("Customer")) {
                            adGui.jf.getContentPane().removeAll();
                            adGui.jf.setContentPane(new CustomerGUI(1));
                            adGui.jf.setVisible(true);
                        } else if (usertype.equals("Shop")) {
                            adGui.jf.getContentPane().removeAll();
                            adGui.jf.setContentPane(new ShopGUI(1));
                            adGui.jf.setVisible(true);
                        }
                    } else if (ch == -1) {
                        System.out.println("Access not granted : USER ALREADY LOGGED IN FROM ANOTHER DEVICE!!");
                        Functions obj=new Functions();
                        obj.accessmessage();
                        adGui.jf.setTitle("Login");
                        adGui.jf.getContentPane().removeAll();
                        //initialize_details();
                        adGui.jf.setContentPane(new Login());
                        adGui.jf.setVisible(true);
                        //addActionListener();
                    } else {
                        System.out.println("Invalid Password!");
                        Functions obj=new Functions();
                        obj.invalidPassmessage();
                        adGui.jf.getContentPane().removeAll();
                        //initialize_details();
                        adGui.jf.setContentPane(new Login());
                        adGui.jf.setVisible(true);
                        //addActionListener();
                    }
                }
                else{
                    System.out.println("User not present!!");
                    Functions obj=new Functions();
                    obj.usernotpresentmessage();
                }

            } catch (Exception ex) {
                System.out.println("In Login " + ex);
            }

        });
    }
}