package com.example.jhlibrary;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ThirdActivity {

	public static List<HashMap<String, String>> bookList = new ArrayList<HashMap<String, String>>();

	public static List<HashMap<String, String>> analysis(String html) {
		Document doc = Jsoup.parse(html);

		Elements elements = doc.getElementsByClass("book_list_info");
		HashMap<String, String> book = null;

		for (Element e : elements) {
			book = new HashMap<String, String>();
			Elements bookInfo = e.select("h3");
			String bookName = bookInfo.select("a").text();
			String bookNum = bookInfo.text().trim()
					.substring(bookName.length() + 5);

			book.put("bookName", bookName);
			book.put("bookNum", bookNum);
			
			bookList.add(book);
		}
		return bookList;
	}
}
