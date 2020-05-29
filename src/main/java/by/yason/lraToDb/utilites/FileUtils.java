package by.yason.lraToDb.utilites;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

public class FileUtils {
    public static Document ReadFile(String fName) throws IOException {
        File input = new File(fName);
        Document document = Jsoup.parse(input, "UTF-8", "http://example.com/");
//        Document document = new Documentment(fName, "UTF-8", "http://example.com/");
        return document;
    }
}
