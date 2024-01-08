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

        System.out.println("URL: " + url + ". n = " + n);

    }

    public static void hop(String url, int n){

    }

    public static Boolean print(String URL){

        return false; //base case
    }

}