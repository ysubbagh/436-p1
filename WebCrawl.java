import java.util.*;

public class WebCrawl{
    //global variables
    public static void main(String[] args){
        String url = "";
        int n = -1;
        //parse the arguments
        for(int i = 0; i < 2; i++){
            if(i == 0){ //get URL
                url = args[i];
            }else if(i == 1){ //get num
                n = Integer.parseInt(args[i]);
            }else{ //error handling
                if(!args[i].equals("")){
                    System.out.println("Too many arguemnts passed!");
                    System.exit(0);
                }
            }
        }
        
        Map<String, Integer> history = new HashMap<String, Integer>();
        hop(url, n, history);

    }

    public static void hop(String url, int n, Map<String, Integer> history){
        if(n <= 0){return;}

    }

    public static Boolean print(String url){

        return false; //base case
    }

}