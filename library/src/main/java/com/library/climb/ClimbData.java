package com.library.climb;

import com.library.pojo.Book;
import com.library.pojo.Category;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClimbData {

    public static List<Category> categories() {
        String url = "http://book.dangdang.com/";
        String html = get(url);
        Document doc = Jsoup.parse(html);
        Elements eles = doc.select(".primary_dl");
        Elements hides = doc.select(".submenu");
        List<String> parent = new ArrayList<>();
        List<String> child = new ArrayList<>();
        List<String> child2 = new ArrayList<>();
        boolean flag = false;
        for(Element element : eles){
            Elements dt = element.select("dt");
            if(dt.html().startsWith("<a")){
                Elements as = dt.select("a");
                StringBuilder sb = new StringBuilder();
                for(Element el : as){
                    sb.append(el.html()+" ");
                }
                parent.add(sb.toString());
            }else{
                parent.add(dt.html());
            }
            Elements dd = element.select("dd");
            Elements as = dd.select("a");
            for(Element element1 : as){
                child.add(element1.html());
            }
            child.add("-");
        }
        int ii = 0;
        for(Element element:hides){
            Elements els = element.select(".inner_dl");
                for(Element element2 : els){
                    Elements dd = element2.select("dd");
                    Elements as = dd.select("a");
                    for(Element element3 : as){
                        child2.add(element3.html());
                    }
                    child2.add("-");
            }
        }
        int count = 0;
        List<Category> categories = new ArrayList<>();
        List<Integer> floors = new ArrayList<>();
        int parentId = 0;
        for(String str : parent){
            if(str.length() == 0){
                continue;
            }
            int floor = (int)(Math.random()*4+1);
            floors.add(floor);
//            Category category = new Category(str,floor,parentId);
//            categories.add(category);
            count++;
        }

        parentId = 1;
        int index = 0;
        for(String str : child){
            if(str.equals("-")){
//                System.out.println("-----------------------");
//                System.out.println();
                parentId++;
                continue;
            }

//            System.out.println(str);
//            Category category = new Category(str,floors.get(parentId-1),parentId);
//            categories.add(category);
            count++;
        }
        int parentId2 = floors.size()+1;
        System.out.println(parentId2);
        parentId = 1;
        for(String str : child2){
            if(str.equals("-")){
                parentId2++;
                continue;
            }
                Category category = new Category(str,floors.get(parentId-1),parentId2);
                categories.add(category);
        }

        return categories;
    }

    public static String get(String url){
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        try {
            HttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        List<Category> list = ClimbData.categories();
        for(Category category : list){
            System.out.println(category);
        }
    }

}
