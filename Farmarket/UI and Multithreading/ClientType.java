//import javax.swing.*;
//        import java.awt.*;
//
//public class ClientType extends JPanel {
//    JButton b1;
//    JLabel l1, l2;
//    JRadioButton rb1, rb2;
//
//
//    ClientType(){
//        setLayout(null);
//        initialize_details();
//        addActionListener();
//    }
//
//    public void initialize_details() {
//
//        l1 = new JLabel("Select User Type");
//        l1.setFont(new Font("Ubuntu Mono", Font.BOLD, 20));
//        l1.setBounds(220, 25, 268, 30);
//        add(l1);
//        rb1 = new JRadioButton("Customer");
//        rb1.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
//        rb1.setBounds(225, 85, 268, 20);
//
//        rb2 = new JRadioButton("Shop Member");
//        rb2.setBounds(225, 105, 268, 20);
//        rb2.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
//
//        b1 = new JButton("Proceed");
//        b1.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
//        b1.setBounds(175, 200, 268, 20);
//
//        l2 = new JLabel("                            ");
//        l2.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
//        l2.setBounds(210, 255, 268, 35);
//
//        ButtonGroup bg = new ButtonGroup();
//        bg.add(rb1);
//        bg.add(rb2);
//
//        add(rb1);
//        add(rb2);
//
//        add(b1);
//        add(l2);
//    }
//
//    public void addActionListener() {
//        b1.addActionListener(e -> {
//            if (rb2.isSelected()) {
//                //l2.setText("Login");
//                l2.setText("Shops page loading......");
//                adGui.jf.setTitle("Shop Dashboard");
//                adGui.jf.getContentPane().removeAll();
//                adGui.jf.setContentPane(new Login());
//                adGui.jf.setVisible(true);
//            } else
//                l2.setText("Customer page loading......");
//        });
//    }
//
//}