    import java.util.*;
        
    public class User extends Customer
    {
        Scanner scan=new Scanner(System.in);
        public HashMap<String,Customer> customer=new HashMap<String,Customer>();
        public HashMap<String,Shops> shop=new HashMap<String,Shops>();
        
        /*public HashMap<String, Shops> get_shop_map(){
           return shop;
        }*/
        
        public void main(){
            while(true){
            System.out.println("****** WELCOME TO AGRICULTURE-MARKET MANAGEMENTS ********");
            System.out.println();
            System.out.println("1.Login");
            System.out.println("2.Register");
            int ch=scan.nextInt();
            scan.nextLine();
            String user,password;
            if(ch==1){
                System.out.println("Choose User Type (INTEGER)- ");
                System.out.println("1.Customer");
                System.out.println("2.Shop Member");    
                ch=scan.nextInt();
                scan.nextLine();
                if(ch==1){
                    System.out.println("Enter Username");
                    user=scan.nextLine();
                    if(customer.containsKey(user)){
                       Customer obj=customer.get(user);  
                       System.out.println("Enter Password");
                       password=scan.nextLine();
                       if(obj.password.equals(password)){
                           obj.main(shop);
                       }
                       else{
                          System.out.println("Invalid Password!");
                        }    
                    }
                    else{
                        System.out.println("User not present!!");
                    }
                }
                else if(ch==2){
                    System.out.println("Enter Username");
                    user=scan.nextLine();
                    if(shop.containsKey(user)){
                       Shops obj=shop.get(user);  
                       System.out.println("Enter Password");
                       password=scan.nextLine();
                       if(obj.password.equals(password)){
                           obj.main1();
                       }
                       else{
                          System.out.println("Invalid Password!");
                        }    
                    }
                    else{
                        System.out.println("User not present!!");
                    }
                }
          }
        
        else if(ch==2){
            System.out.println("Choose User Type - ");
                System.out.println("1.Customer");
                System.out.println("2.Shop Member"); 
                ch=scan.nextInt();
                scan.nextLine();
               if(ch==2){    
                while(true){    
                   System.out.println("Enter Username -");
                   user=scan.nextLine();
                    if(shop.containsKey(user)){
                        System.out.println("Username already choosen! Try different Username!");
                    }
                    else{break;}
                }
                
                System.out.println("Enter Shop Name -");
                String shop_name=scan.nextLine();
                System.out.println("Enter Email Id -");
                String email=scan.nextLine();
                System.out.println("Enter Phone Number - ");
                String phone=scan.nextLine();
                System.out.println("Enter Password -");
                password=scan.nextLine();
                Shops obj=new Shops(shop_name,email,phone,password);
                shop.put(user,obj);
                System.out.println("You are ready to go! Please Log in. ");
             }
              
              else if(ch==1){
                     while(true){    
                       System.out.println("Enter Username -");
                       user=scan.nextLine();
                        if(shop.containsKey(user)){
                            System.out.println("Username already choosen! Try different Username!");
                        }
                        else{break;}
                    }
                    
                    System.out.println("Enter Customer Name -");
                    String customer_name=scan.nextLine();
                    System.out.println("Enter Email Id -");
                    String email=scan.nextLine();
                    System.out.println("Enter Phone Number - ");
                    String phone=scan.nextLine();
                    System.out.println("Enter Password -");
                    password=scan.nextLine();
                    Customer obj=new Customer(customer_name,email,phone,password);
                    customer.put(user,obj);
                    System.out.println();
                    System.out.println("You are ready to go! Please Log in. ");
              }
            }
            else{
                break;
            }
        }
        
    }
}
