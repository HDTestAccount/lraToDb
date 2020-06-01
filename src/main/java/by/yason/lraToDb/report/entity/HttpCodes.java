package by.yason.lraToDb.report.entity;

import lombok.Data;
import org.jsoup.nodes.Element;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
@Data
@Entity
public class HttpCodes implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private int total;
    private double perSecond;

    public HttpCodes() {
    }

    public HttpCodes(Element s) {
//.//td[@headers = 'LraHTTP Responses']/span/text()
        this.name = s.select("td[headers^=LraHTTP Responses] span").text();
        this.total = Integer.parseInt(s.select("td[headers^=LraTotal] span").text().replaceAll(" ",""));
        this.perSecond = Double.parseDouble(s.select("td[headers^=LraPer second] span").text().replaceAll(" ",""));
    }
}
