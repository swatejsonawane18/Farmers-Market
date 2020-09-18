public class Storage {
     private String username;
     private int times_purchased=0;
     private double cost=0;

     Storage(){
         username="";
         times_purchased=0;
         cost=0.0;
     }
    Storage(String name){
        username=name;
        times_purchased=0;
        cost=0.0;
    }

     public void update_value(double cost){
         this.cost+=cost;
         times_purchased+=1;
    }
     public int show_times_purchased(){
         return times_purchased;
     }

    public double show_amount(){
        return cost;
    }
}
