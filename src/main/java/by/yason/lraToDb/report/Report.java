package by.yason.lraToDb.report;

import by.yason.lraToDb.report.entity.HttpCodes;
import by.yason.lraToDb.report.entity.RunSummary;
import by.yason.lraToDb.report.entity.Transactions;
import by.yason.lraToDb.utilites.FileUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Report implements Serializable {

    @Id
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "TransactionsRunId", nullable = false)
    private Set<Transactions> transactions = new HashSet<>();
//    private Map transactions = new HashMap<String, Transaction>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "HttpSummaryId", nullable = false)
    private Set<HttpCodes> httpCodes = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "runSummary", nullable = false)
    RunSummary runSummary = new RunSummary();

    public Report(String reportFileName, long id) throws IOException, ParseException {
        Document reportDoc = FileUtils.ReadFile(reportFileName);
        System.out.println("runHttpCodes");
        CreateListHttpCodes(reportDoc);
        System.out.println("runTransactions");
        CreateLisTransactions(reportDoc);
        System.out.println("runSummary");
        runSummary = new RunSummary(reportDoc);
        name = runSummary.getStartDate().toString();
        this.id = id+1;
    }

    public void CreateListHttpCodes(Document reportDoc) {
        Elements list = reportDoc.select("tr[class$=tabledata_lightrow]");
        list.addAll(reportDoc.select("tr[class$=tabledata_darkrow]"));
        for (Element s : list) {
            if (s.text().contains("HTTP")) {
                httpCodes.add(new HttpCodes(s));
            }
        }
    }

    public Report() {
    }

    public void CreateLisTransactions(Document document) {
        //Ищем в документе по CSSселектору строки содержащие средние значения
        List<String> list = document.select("tr[class$=tabledata_lightrow]").eachText();
        list.addAll(document.select("tr[class$=tabledata_darkrow]").eachText());
        System.out.println("for");
        //Каждую строку разбиваем на массив по пробелу,
        for (String string : list) {
            if (!string.contains("HTTP")) {
                transactions.add(new Transactions(string));
            }
        }
    }
}
