import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class adGui {
    public static JFrame jf=new JFrame();
    static Socket clientSocket2=null;
    static DataInputStream inFromServer=null;
    static DataOutputStream outToServer=null;
    static{
        Dimension dimension = new Dimension(800,800);
        jf.setLocation(250,150);
        jf.setSize(dimension);
        //jf.setResizable(false);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    adGui(){
        try {
            clientSocket2 = new Socket("localhost", 5000);
            System.out.println("Connected to Server.");
            inFromServer = new DataInputStream(clientSocket2.getInputStream());
            outToServer = new DataOutputStream(clientSocket2.getOutputStream());
        }
        catch (Exception e){
            System.out.println(e+"in Constructor");
        }
    }

    public static void main(String[]args){
        adGui ob=new adGui();
        jf.setTitle("Main Page");
        jf.setContentPane(new Main_Page());
        jf.setVisible(true);
    }
}


class Main_Page extends JPanel {
    JButton b1,b2;
    JLabel l1, l2;


    Main_Page(){

        setLayout(null);
        initialize_details();
        addActionListener();
    }

    public void initialize_details() {

        l1 = new JLabel("Agro - Mart ");
        l1.setFont(new Font("Eras Bold ITC", Font.ITALIC, 50));
        l1.setBounds(45, 10, 360, 90);
        add(l1);

        JTextField non_editable=new JTextField();
        non_editable.setBounds(45, 112, 700, 600);
        non_editable.setBackground(Color.GRAY);
        non_editable.setText("Welcome to Agro-Mart");

        non_editable.setFont(new Font("Castellar", Font.BOLD, 30));
        non_editable.setHorizontalAlignment(JTextField.CENTER);
        non_editable.setEditable(false);
        add(non_editable);


        b1 = new JButton("Log in");
        b1.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        b1.setBackground(Color.GRAY);
        b1.setBounds(550, 45, 80, 30);

        b2 = new JButton("Register");
        b2.setFont(new Font("Ubuntu Mono", Font.BOLD, 15));
        b2.setBackground(Color.GRAY);
        b2.setBounds(650, 45, 100, 30);

        add(b1);
        add(b2);
        //add(l2);
    }

    public void addActionListener() {

        b1.addActionListener(e -> {
            try {
                adGui.outToServer.writeInt(1);
            }
            catch (Exception ex){
                System.out.println(ex);
            }
            adGui.jf.setTitle("Login");
            adGui.jf.getContentPane().removeAll();
            adGui.jf.setContentPane(new Login());
            adGui.jf.setVisible(true);

        });

        b2.addActionListener(e -> {

            try {
                adGui.outToServer.writeInt(2);
            }
            catch (Exception ex){
                System.out.println(ex);
            }
            adGui.jf.setTitle("Register");
            adGui.jf.getContentPane().removeAll();
            adGui.jf.setContentPane(new Register());
            adGui.jf.setVisible(true);

        });
    }

}





