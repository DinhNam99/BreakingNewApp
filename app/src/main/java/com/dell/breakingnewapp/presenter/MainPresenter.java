package com.dell.breakingnewapp.presenter;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.dell.breakingnewapp.model.News;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainPresenter extends AsyncTask<String,Integer,String> {
    LoadDataRSS loadDataRSS;
    ArrayList<News> newsArrayList = new ArrayList<>();

    public MainPresenter(LoadDataRSS loadDataRSS){
        this.loadDataRSS = loadDataRSS;
    }


    @Override
    protected String doInBackground(String... strings) {
        return readInfoInURL(strings[0]);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onPostExecute(String s) {

        XMLDOMParser xmldomParser = new XMLDOMParser();
        Document document = xmldomParser.getDocument(s);
        NodeList nodeList = document.getElementsByTagName("item");
        NodeList nodeListDesc = document.getElementsByTagName("description");
        String title ="",desc="",link="",pubDate="",image="";
        for(int i = 0; i<nodeList.getLength(); i++){
            String cdada = nodeListDesc.item(i+1).getTextContent();
            image = extractImageUrl(cdada);
            desc = getDescFromCDATA(cdada);
            Element element = (Element) nodeList.item(i);
            title = xmldomParser.getValue(element,"title");
            link = xmldomParser.getValue(element,"link");
            pubDate = xmldomParser.getValue(element,"pubDate");
            newsArrayList.add(new News(title,desc,pubDate,link,image));
        }

        loadDataRSS.parseRSS(newsArrayList);
        super.onPostExecute(s);
    }

    private String readInfoInURL(String theUrl){
        StringBuilder content = new StringBuilder();
        try    {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null){
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch(Exception e)    {
            e.printStackTrace();
        }
        return content.toString();
    }

    private String getDescFromCDATA(String link){
        int begin = link.indexOf("</br")+5;
        int end = link.length();
        return link.substring(begin, end);
    }
    private String extractImageUrl(String description) {
        org.jsoup.nodes.Document document = Jsoup.parse(description);
        Elements imgs = document.select("img");
        for (org.jsoup.nodes.Element img : imgs) {
            if (img.hasAttr("src")) {
                if(img.attr("src").startsWith("data:image")){
                    return img.attr("data-original");
                }
                return img.attr("src");
            }
        }

        // no image URL
        return "";
    }

}
