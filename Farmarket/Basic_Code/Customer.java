        import java.util.*;
        
        public class Customer extends Shops{
            Scanner sc=new Scanner(System.in);         
                    String shop_name,email,phone,password;
                    ArrayList<Integer> count=new ArrayList<Integer>();
                    BitSet cart_matched=new BitSet();
                    BitSet category=new BitSet(4);
                 Customer(){
                       shop_name=null;
                       email=null;
                       phone=null;
                       password=null;
                }
                 Customer(String shop_name,String email,String phone,String password){
                     this.shop_name=shop_name;
                     this.email=email;
                     this.phone=phone;
                     this.password=password;
                }
                public double check(HashMap<String,Integer> customer,HashMap<String,Double> shop){
                    double sum=0; 
                    double sum_item=0;
                    int n=0;
                    System.out.println();
                    for(String i :customer.keySet()){
                        if(shop.containsKey(i)){
                           ++n;
                          sum_item=shop.get(i)*customer.get(i);
                          sum+=sum_item;
                          System.out.println(i + "     "+customer.get(i)+"     "+sum_item);
                          cart_matched.set(1);
                         } 
                        else{
                            cart_matched.set(0);
                            System.out.println("Sorry, "+i+" is currently unavailable at the store! ");
                        }               
                    }
                    count.add(n);
                    return sum;                     
                }
                
                
                  public void Menu(HashMap<String,Shops> shop,int code){
                    if(code==1){
                      for(String i:shop.keySet()){
                        view_hashmap(shop.get(i).shop_storage_insecticides);
                      }
                    }
                    else if(code==2){
                       for(String i:shop.keySet()){
                        view_hashmap(shop.get(i).shop_storage_pesticides);
                      }
                    }
                    else if(code==3){
                       for(String i:shop.keySet()){
                        view_hashmap(shop.get(i).shop_storage_cropseeds);
                      }
                    }
                    else if(code==4){
                        for(String i:shop.keySet()){
                        view_hashmap(shop.get(i).shop_storage_tools);
                      }
                    }
               }
              
        
                public void add_cart_item(HashMap<String,Integer> cart,String item,int quantity){
                    cart.put(item,quantity);
                }
            
                public void remove_cart_item(HashMap<String,Integer> cart,String item){
                    cart.remove(item);
                }
                
                public boolean modify_cart_item(HashMap<String,Integer> cart,String item,int old_quantity,int new_quantity){
                    return cart.replace(item,old_quantity,new_quantity);        
                }
            
             public void view_cart(HashMap<String,Integer> cart){
                if(!cart.isEmpty()){
                for(String i :cart.keySet()){
                    System.out.println("Item: "+i+" Quantity: "+cart.get(i));
                }
               }
               else{
                   System.out.println("CART IS EMPTY!");
                }
            }
            
            public void functions(HashMap<String,Integer> cart, HashMap<String,Shops> shop, int code){
                    int ch=0;
                    String item=""; int quantity=0;
                    while(true){
                       System.out.println("1.Add Item in Cart");
                       System.out.println("2.Remove Item from Cart");
                       System.out.println("3.Modify Quantity of Item in the Cart");
                       System.out.println("4.View Items in Cart");
                       System.out.println("5.Quit");
                       ch=sc.nextInt();
                       sc.nextLine();
                        if(ch==1){
                           Menu(shop, code);
                           System.out.println("Enter the Product you want -");
                           item=sc.nextLine();
                           item=item.toUpperCase();
                           System.out.println("Enter the Quantity -");
                           quantity=sc.nextInt();
                           add_cart_item(cart,item,quantity);
                        }
                      
                           else if(ch==2){
                           System.out.println("Enter the Product you want to delete -");
                           item=sc.nextLine();
                           item=item.toUpperCase();
                           remove_cart_item(cart,item);
                           }
                      
                         else if(ch==3){
                           System.out.println("Enter the Product whose quantity you want to modify -");
                           item=sc.nextLine();
                           item=item.toUpperCase();
                           System.out.println("Enter the new quantity");
                           int new_quantity=sc.nextInt();
                           sc.nextLine();
                           boolean f=false;
                           if(cart.get(item)!=null)
                           f=modify_cart_item(cart,item,cart.get(item),new_quantity);
                           else{
                               System.out.println(item+" not found in cart!");
                           }
                         }
                       
                        else if(ch==4){
                           view_cart(cart);
                        }
                       
                        else if(ch==5){
                          break;
                        }
                        
                        else{
                            System.out.println("Enter the choice from the above Menu!");
                            continue;
                        }
                      
                       System.out.println("Do want to perform any other operations?(Enter 1(yes)/ -1(no))");
                       ch = sc.nextInt();
                       sc.nextLine();
                       if (ch == 1)
                        {  continue;}
                        else {
                         break;
                       }
                    }
                }
                
                public void main(HashMap<String,Shops> shop)
                {
                    //Customer obj=new Customer();
                    HashMap<String,Integer> category=new HashMap<String,Integer>();
                    
                     HashMap<String,Integer> cart_insecticides=new HashMap<String,Integer>();
                     HashMap<String,Integer> cart_pesticides=new HashMap<String,Integer>();
                     HashMap<String,Integer> cart_cropseeds=new HashMap<String,Integer>();
                     HashMap<String,Integer> cart_tools=new HashMap<String,Integer>();
             
                    int code=0;
                    double price=0; int ch;
                    String r,product;
                    
                    /*obj1.main1();
                    obj2.main2();
                    obj3.main3();*/
                    //HashMap<String,Shops> shop_temp=shop;
                    
                    
                    while(true) {
                        System.out.println("********WELCOME TO THE STORE*********");
                        System.out.println("1.INSECTICIDES");
                        System.out.println("2.PESTICIDES");
                        System.out.println("3.CROP SEEDS");
                        System.out.println("4.TOOLS");
                        System.out.println();
                        System.out.println("Enter the Category you want to buy in (STRING)-");
                        product = sc.nextLine();
                        //sc.nextLine();
            
                        if (!(product.equalsIgnoreCase("INSECTICIDES") || product.equalsIgnoreCase("PESTICIDES") || product.equalsIgnoreCase("CROP SEEDS") || product.equalsIgnoreCase("TOOLS"))) {
                            System.out.println("Item not available at the store.");
                        }
                        else {
                            if (product.equalsIgnoreCase("INSECTICIDES") ) {
                                code = 1;
                                category.put(product, code);
                                functions(cart_insecticides,shop, code);
                            }
                            if (product.equalsIgnoreCase("PESTICIDES")) {
                                code = 2;
                                category.put(product, code);
                                functions(cart_pesticides,shop, code);
                            }
                            if (product.equalsIgnoreCase("CROP SEEDS")) {
                                code = 3;
                                category.put(product, code);
                                functions(cart_cropseeds,shop, code);
                            }
                            if (product.equalsIgnoreCase("TOOLS")) {
                                code = 4;
                                category.put(product, code);
                                functions(cart_tools,shop, code);
                            }
                            
                        }
                        System.out.println("Do want to add more items?(Enter 1(yes)/ -1(no))");
                        ch = sc.nextInt();
                        sc.nextLine();
                       if (ch == 1)
                        {  continue;}
                        else {
                            System.out.println();
                            ch=0;
                            System.out.println("Your cart items are - ");
                            if(!cart_insecticides.isEmpty()){
                            System.out.println();
                            System.out.println("INSECTICIDES");
                            System.out.println();
                            ch=1;
                            for(String i :cart_insecticides.keySet()){
                                System.out.println("Key: "+i+" Quantity: "+cart_insecticides.get(i));
                            }
                            System.out.println();
                          }
                        
                          if(!cart_pesticides.isEmpty()){
                            System.out.println();
                            System.out.println("PESTICIDES - ");
                            System.out.println();
                            ch=1;
                            for(String i :cart_pesticides.keySet()){
                                System.out.println("Key: "+i+" Quantity: "+cart_pesticides.get(i));
                            }
                            System.out.println();
                          }
                        
                          if(!cart_cropseeds.isEmpty()){
                            System.out.println();
                            System.out.println("CROPSEEDS - ");
                            System.out.println();
                            ch=1;
                            for(String i :cart_cropseeds.keySet()){
                                System.out.println("Key: "+i+" Quantity: "+cart_cropseeds.get(i));
                            }
                            System.out.println();
                          }
                        
                          if(!cart_tools.isEmpty()){
                            System.out.println();
                            System.out.println("TOOLS -");
                            System.out.println();
                            ch=1;
                            for(String i :cart_tools.keySet()){
                                System.out.println("Key: "+i+" Quantity: "+cart_tools.get(i));
                            }
                            System.out.println();
                          }
                          
                          if(ch==0){
                              System.out.println();
                              System.out.println("CART IS EMPTY!");
                              System.out.println();
                              System.out.println("Thanks for visiting!");
                              break;
                            }
                            else{
                             double sum,min_sum=100000;                          
                             String min_shop=null;
                             for(String i:shop.keySet()){
                               System.out.println("Shop Name - "+i);
                               sum=check(cart_insecticides,shop.get(i).shop_storage_insecticides)+check(cart_pesticides,shop.get(i).shop_storage_pesticides)+check(cart_cropseeds,shop.get(i).shop_storage_cropseeds)+check(cart_tools,shop.get(i).shop_storage_tools);
                               System.out.println("Total Amount - "+sum);
                               System.out.println();
                               if(sum<min_sum){
                                   min_sum=sum;
                                   min_shop=i;
                               }
                            }
                            System.out.println("BEST OPTION - ");
                            System.out.println("Shop name - "+min_shop);
                            System.out.println("Amount - "+min_sum);
                            }
                            System.out.println("Thanks for shopping!");
                            break;
                       }
                    }
                }
        }