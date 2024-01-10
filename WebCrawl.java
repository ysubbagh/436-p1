/* WebCrawl 
* Author: Yasmine Subbagh
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;

public class WebCrawl{
    public static void main(String[] args){
        String url = "";
        int n = -1;

        //error handling for arguemtns
        if(args.length > 2 || args.length < 2){
            System.out.println("Incorrect arguments passed!");
            System.exit(0);
        }

        //parse the arguments
        for(int i = 0; i < 2; i++){
            if(i == 0){ //get URL
                url = args[i];
            }else if(i == 1){ //get num
                try{ //error handling for non int
                    n = Integer.parseInt(args[i]);
                }catch(NumberFormatException e){
                    System.out.println("Second argument not interger.");
                    System.exit(0);
                }
            }
        }
        
        //start crawling and setup history storage
        Map<String, Integer> history = new HashMap<String, Integer>();
        hop(url, n, history);

    }

    public static void hop(String url, int n, Map<String, Integer> history){
        if(n <= 0){return;} //reached enf of number to crawl

        URL link;
        InputStream buff = null;
        BufferedReader br;
        String line;

        try{
            link = new URL(url);
            buff = link.openStream();
            br = new BufferedReader((new InputStreamReader((buff))));

            while((line = br.readLine()) != null){
                //get links n print
            }
        }catch (MalformedURLException mue){
            mue.printStackTrace();;
        }catch (IOException ioe){
            ioe.printStackTrace();
        }finally{
            try{
                if(buff != null) {buff.close();}
            }catch (IOException ioe){
                ioe.printStackTrace();
            }
        }
        n--;
    }

}