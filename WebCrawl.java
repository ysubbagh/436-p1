/* 
* WebCrawl -- p1. css436
* Author: Yasmine Subbagh
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
    //global vars
    private static int hopMax = 0;
    private static int numHops = 0;
    private static  Map<String, Integer> history = new HashMap<String, Integer>();
    //error messages
    private static String eArgs = "incorrect arguments passed.";
    private static String eMalURL = "malformed URL.";
    private static String eDownload = "downloading HTML error.";
    private static String eCantCrawl = "no new recheable URLs found.";

    public static void main(String[] args){
        String url = "";

        //error handling for num of arguemtns
        if(args.length > 2 || args.length < 2){ termination(eArgs); }

        //parse the arguments (error handling for var types)
        try{ 
            url = args[0];
            hopMax = Integer.parseInt(args[1]);
            URL test = new URL(url); //make sure string passed is url
        }catch(NumberFormatException e1){
            termination(eArgs);
        }catch(MalformedURLException e2){
            termination(eArgs);
        }

        //start crawling
        hop(url);
    }

    //download the HTML and store it into history
    private static void hop(String urlString){
        if(numHops >= hopMax + 1){ System.exit(0); } //reached user defined end

        //remove tailing slashes
        if(urlString.endsWith("/")){
            urlString = urlString.substring(0, urlString.length() - 1);
        }

        //check in history
        if(checkHist(urlString)){ return; }
    
        URL link;
        InputStream buff = null;
        BufferedReader br;
        String line;

        //download new HTML file
        try{
            print(urlString);
            //connect to the url and download the html
            link = new URL(urlString);
            HttpURLConnection connect = (HttpURLConnection) link.openConnection();

            //get html codes
            int htmlCode = connect.getResponseCode();
            if(htmlCode >= 300 && htmlCode < 400){ //handle redirection
                String redirectString = connect.getHeaderField("Location");
                if(redirectString != null) { hop(redirectString); }
            }else if(htmlCode >= 400) { return; }

            //connect to html file
            buff = connect.getInputStream();
            br = new BufferedReader(new InputStreamReader(buff));

            //read line by line
            while((line = br.readLine()) != null ) {
                if(line.contains("<a href")){ processTag(line, urlString); }
            }
            connect.disconnect();

        }catch (MalformedURLException mue){
            termination(eMalURL);
        }catch (IOException e){
            termination(eDownload);
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
    }

    //process href tag
    private static Boolean processTag(String line, String fromUrl){
        int start = line.indexOf("<a href");
        int end = line.indexOf(">", start);
        if(start > -1 && end > -1){
            String hrefLoc = line.substring(start, end);
            String nextUrl = extractUrl(hrefLoc, fromUrl);
            hop(nextUrl);
            return true;
        }
        return false; //base case
    }

    //get the url from the href
    private static String extractUrl(String hrefString, String fromURL){
        String regex = "href\\s*=\\s*[\"']([^\"']*)[\"']";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(hrefString);
        if (matcher.find()) {
            String newUrl = matcher.group(1);
            try{ // fix issues with malformed URLs
                URL baseUrl = new URL(fromURL);
                URL fixedUrl = new URL(baseUrl, newUrl);
                return fixedUrl.toString();
            }catch (MalformedURLException e){
                return ""; //loop back, dont fault
            }
        }
        return ""; //base case
    }

    //check to see if the url has already been accessed
    private static Boolean checkHist(String url){
        return history.containsKey(url);
    }

    //print and setup for next crawl 
    private static void print(String url){
        if(numHops == 0) {
            System.out.println("Starting link: " + url);
        }else{
            System.out.println("Hop " + numHops + " : " + url);
        }
        history.put(url, numHops);
        numHops++;
    }

    //termination and error printing
    private static void termination(String reason){
        System.out.println("Crawler stopped after " + numHops + " hops.");
        System.out.println("Crawler stopped because of " + reason);
        System.out.println("Remaning hops: " + (hopMax - numHops));
        System.exit(1);
    }
}