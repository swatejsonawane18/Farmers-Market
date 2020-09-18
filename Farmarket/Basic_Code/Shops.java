
import java.util.*;
public class Shops {
    Scanner s=new Scanner(System.in);
    HashMap<String,Double> shop_storage_insecticides=new HashMap<String,Double>();
                HashMap<String,Double> shop_storage_pesticides=new HashMap<String,Double>();
                HashMap<String,Double> shop_storage_cropseeds=new HashMap<String,Double>();
               HashMap<String,Double> shop_storage_tools=new HashMap<String,Double>();
    
    String shop_name,email,phone,password;
    Shops(){
        shop_name=null;
        email=null;
        phone=null;
        password=null;
    }
    
    Shops(String shop_name,String email,String phone,String password){
        this.shop_name=shop_name;
        this.email=email;
        this.phone=phone;
        this.password=password;
    }

    public void view_hashmap(HashMap<String,Double> map){
                for(String i :map.keySet()){
            System.out.println("Item: "+i+" Cost: "+map.get(i));
        }
    }
    
    public void add(HashMap<String,Double> map,String item,double cost){
        map.put(item,cost);
    }

    public void remove(HashMap<String,Double> map,String item){
        map.remove(item);
    }
    
    public boolean modify(HashMap<String,Double> map,String item,double old_cost,double new_cost){
        return map.replace(item,old_cost,new_cost);        
    }
    


    public void menu(HashMap<String,Double> map){
        int ch=0;
        while(true) {
            System.out.println("Category Menu - ");
            System.out.println("1. View Storage database ");
            System.out.println("2. Add Item ");
            System.out.println("3. Remove Item ");
            System.out.println("4. Modify cost of a Item ");
            System.out.println("5. Quit");
            System.out.println();
            System.out.println("Enter your choice - ");
            ch = s.nextInt();
            s.nextLine();
            if (ch == 1){
              view_hashmap(map);
            }
            else if (ch == 2){
                System.out.println("Item name -");
                String name=s.nextLine();
                System.out.println("Item cost -");
                double cost=s.nextDouble();
                s.nextLine();
                add(map,name,cost);
            }
            else if (ch ==3){
                System.out.println("Item name -");
                String name=s.nextLine();
                remove(map,name);
            }
            else if (ch == 4){
                System.out.println("Item name - ");
                String name=s.nextLine();
                System.out.println("New Item cost -");
                double cost=s.nextDouble();
                s.nextLine();
                modify(map,name,map.get(name),cost);
            }
            else if(ch==5){
                break;}
            else {
                System.out.println();
                System.out.println("Choose from the given Menu! ");
                System.out.println();
            }
        }
     }

   
    public void main1(){
        
         while(true){
            System.out.println("********WELCOME TO THE STORE*********");
            System.out.println();
            System.out.println("********WHAT CATEGORY DO YOU WANT TO PERFORM OPERATIONS*********");
            System.out.println("1.INSECTICIDES");
            System.out.println("2.PESTICIDES");
            System.out.println("3.CROP SEEDS");
            System.out.println("4.TOOLS");
            System.out.println("5.DISPLAY SHOP DATABASE");
            System.out.println("6.Exit");
            System.out.println();
            System.out.println("Enter the Category you want (String) -");
            String cat = s.nextLine();
            
                if (!(cat.equalsIgnoreCase("INSECTICIDES") || cat.equalsIgnoreCase("DISPLAY SHOP DATABASE")|| cat.equalsIgnoreCase("PESTICIDES") || cat.equalsIgnoreCase("CROP SEEDS") || cat.equalsIgnoreCase("TOOLS") || cat.equalsIgnoreCase("EXIT"))) {
                            System.out.println("Item not available at the store.");
                        }
                        else {
                            if (cat.equalsIgnoreCase("INSECTICIDES") ) {
                                menu(shop_storage_insecticides);
                            }
                            if (cat.equalsIgnoreCase("PESTICIDES")) {
                                menu(shop_storage_pesticides);
                            }
                            if (cat.equalsIgnoreCase("CROP SEEDS")) {
                                menu(shop_storage_cropseeds);
                            }
                            if (cat.equalsIgnoreCase("TOOLS")) {
                               menu(shop_storage_tools);
                            }
                            if (cat.equalsIgnoreCase("DISPLAY SHOP DATABASE")) {
                                System.out.println("INSECTICIDES - ");
                               view_hashmap(shop_storage_insecticides);
                                System.out.println();
                               System.out.println("PESTICIDES - ");
                               view_hashmap(shop_storage_pesticides);
                               System.out.println();
                               System.out.println("CROP SEEDS - ");
                               view_hashmap(shop_storage_cropseeds);
                               System.out.println();
                               System.out.println("TOOLS - ");
                               view_hashmap(shop_storage_tools);
                               System.out.println();
                            }
                            if (cat.equalsIgnoreCase("EXIT")) {
                              break;
                            }
                        }
         }
   }
}

