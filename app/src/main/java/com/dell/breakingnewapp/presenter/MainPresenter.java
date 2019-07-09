package com.dell.breakingnewapp.presenter;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.dell.breakingnewapp.model.News;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
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

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    @Override
    protected void onPostExecute(String s) {

        XMLDOMParser xmldomParser = new XMLDOMParser();
        Document document = xmldomParser.getDocument(s);
        NodeList nodeList = document.getElementsByTagName("item");
        NodeList nodeListDesc = document.getElementsByTagName("description");
        String title ="",desc="",link="",pubDate="",image="";
        for(int i = 1; i<nodeList.getLength(); i++){
            String cdada = nodeListDesc.item(i+1).getTextContent();
            image = getImageLinkFromCDATA(cdada);
            desc = getDescFromCDATA(cdada);
            Element element = (Element) nodeList.item(i);
            title = xmldomParser.getValue(element,"title");
            link = xmldomParser.getValue(element,"link");
            pubDate = xmldomParser.getValue(element,"pubDate");
            newsArrayList.add(new News(title,desc,pubDate,link,image));
        }

        //Main
        String cdada = nodeListDesc.item(1).getTextContent();
        String imageM = getImageLinkFromCDATA(cdada);
        String descM = getDescFromCDATA(cdada);
        Element element = (Element) nodeList.item(0);
        String titleM = xmldomParser.getValue(element,"title");
        String linkM = xmldomParser.getValue(element,"link");
        String pubDateM = xmldomParser.getValue(element,"pubDate");
        News news = new News(titleM,descM,pubDateM,linkM,imageM);
        loadDataRSS.parseRSSForMain(news);
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
    private String getImageLinkFromCDATA(String link){
        int begin = link.indexOf("https://i");
        int end = link.indexOf("></a>")-2;
        return link.substring(begin, end);
    }
    private String getDescFromCDATA(String link){
        int begin = link.indexOf("</br")+5;
        int end = link.length();
        return link.substring(begin, end);
    }
}
