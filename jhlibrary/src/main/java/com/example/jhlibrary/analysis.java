package com.example.jhlibrary;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class analysis {
	public static List<HashMap<String, String>> information = new ArrayList<HashMap<String, String>>();
	public static List<HashMap<String, String>> Catelog = new ArrayList<HashMap<String, String>>();

	public static List<HashMap<String, String>> analysisInformation(
			String result) {
		String myString = new String();
		try {
			Document doc = Jsoup.parse(result);

//			Elements elements = doc.getElementsByClass("information_list_info");
			HashMap<String, String> card = null;

			Elements elements = doc.getElementsByTag("td");
			Log.d("element", elements.toString());

			for (Element e : elements) {
				card = new HashMap<String, String>();
				String informayion = e.getElementsByTag("td").text();
				card.put("Information", informayion);
				information.add(card);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			myString = e.getMessage();
			e.printStackTrace();
		}
		return information;
	}

	public static List<HashMap<String, String>> analysisCatelog(String result) {
		String myString = new String();

		try {
			Document doc = Jsoup.parse(result);

	//		Element element = doc.getElementById("nav_mylib");
			HashMap<String, String> card = null;

			Elements elements = doc.getElementsByTag("li");
			Log.d("element", elements.toString());

			for (Element e : elements) {
				card = new HashMap<String, String>();
				String catelog = e.getElementsByTag("a").text();
                String link   = e.select("a").attr("href").trim();
                String url  = "http://opac.lib.jhun.edu.cn:8080/reader/"+link;
				card.put("Catelog", catelog);
				card.put("Url", url);
				Catelog.add(card);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			myString = e.getMessage();
			e.printStackTrace();
		}
		return Catelog;
	}
}
