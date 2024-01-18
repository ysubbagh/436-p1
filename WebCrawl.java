/* WebCrawl -- css436
* Author: Yasmine Subbagh
Date: 1/17/23
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.net.MalformedURLException;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebCrawl{
    public static void main(String[] args){
        String url = "";
        int numHops = -1;

        //error handling for num of arguemtns
        if(args.length > 2 || args.length < 2){
            System.out.println("Incorrect arguments passed!");
            System.exit(0);
        }

        //parse the arguments (error handling for var types)
        try{ 
            url = args[0];
            numHops = Integer.parseInt(args[1]);
        }catch(NumberFormatException e){
            System.out.println("Incorrect argument values passed!");
            System.exit(0);
        }
        
        //setup history storage
        Map<String, Integer> history = new HashMap<String, Integer>();

        //start crawling
        hop(url, numHops, history);
    }

    //download the HTML and store it into history
    public static void hop(String urlString, int numHops, Map<String, Integer> history){
        if(numHops <= 0){return;} //reached end of number to crawl

        //check that the URL has not been visited


        URL link;
        InputStream buff = null;
        BufferedReader br;
        String line;
        Boolean found = false;

        //download new HTML from URL
        try{
            //connect to the url and download the html
            link = new URL(urlString);
            HttpURLConnection connect = (HttpURLConnection) link.openConnection();
            buff = connect.getInputStream();
            br = new BufferedReader(new InputStreamReader(buff));

            //read the html by line
            while( (line = br.readLine()) != null  && !found){

            }

        }catch (MalformedURLException mue){
            System.out.println("Malformed URL: " + urlString);
            return;
        }catch (IOException e){
            System.out.println("Error downloading HTML from: " + urlString);
            return;
        }finally{
            if(buff != null){ //close Input stream
                try{
                    buff.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }


    }

}
