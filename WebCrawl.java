/* WebCrawl -- css436
* Author: Yasmine Subbagh
Date: 1/17/23
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
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
    private static Boolean hop(String urlString, int numHops, Map<String, Integer> history){
        if(numHops <= 0){return true;} //reached end of number to crawl

        //remove tailing slashes
        if(urlString.endsWith("/")){
            urlString = urlString.substring(0, urlString.length() - 1);
        }
        //check in history
        if(checkHist(urlString, history)){ return false; }

        URL link;
        InputStream buff = null;
        BufferedReader br;
        String line;

        //download new HTML from URL
        try{
            //connect to the url and download the html
            link = new URL(urlString);
            HttpURLConnection connect = (HttpURLConnection) link.openConnection();

            //handle bad html codes
            int htmlCode = connect.getResponseCode();
            if(htmlCode >= 300 && htmlCode < 400){
                String redirectUrl = connect.getHeaderField("Location");
                if(redirectUrl != null){ return hop(redirectUrl, numHops - 1, history);}
            }else if (htmlCode > 400){ return false; }

            buff = connect.getInputStream();
            br = new BufferedReader(new InputStreamReader(buff));

            //read the html by line
            while( (line = br.readLine()) != null){
                if(line.contains("<a href")){
                    newSource(line);
                }

                // if(true){break;}
            }
            connect.disconnect();;

        }catch (MalformedURLException mue){
            System.out.println("Malformed URL: " + urlString);
            System.exit(1);
        }catch (IOException e){
            System.out.println("Error downloading HTML from: " + urlString);
            System.exit(1);
        }finally{
            if(buff != null){ //close Input stream
                try{
                    buff.close();
                }catch (IOException e){
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }
        return true;
    }

    private static void newSource(String line){
        int start = line.indexOf("<a href>");
        int end = line.indexOf(">", start);

        if(start != -1 && end != -1){
            String href = line.substring(start, end);
            String url = getUrl(href);
        }
    }

    private static String getUrl(String href){
        String regex = "href\\s*=\\s*\"([^\"]*)\"";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(href);
        if(matcher.find()){ return matcher.group(1); }
        return "";
    }

    //check to see if the url has already been accessed
    private static Boolean checkHist(String url, Map<String, Integer> history){
        return history.containsKey(url);
    }

}
