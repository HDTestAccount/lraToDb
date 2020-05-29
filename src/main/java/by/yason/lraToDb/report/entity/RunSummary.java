package by.yason.lraToDb.report.entity;

import org.jsoup.nodes.Document;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
public class RunSummary implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date startDate;
    private Date endDate;
    private long duration;
    private int maximumRunningVusers;
    private double totalThroughputBytes;
    private int averageThroughputBytesSecond;
    private int totalHits;
    private double averageHitsPerSecond;
    private int totalErrors;

    public RunSummary(Document reportDoc) throws ParseException {

        //get date and duration RUN
        System.out.println("run summary start");
        String list = reportDoc.select("td[class^=header_timerange]").text();
        SimpleDateFormat sdf = new SimpleDateFormat("d.MM.yyyy H:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT +2"));
        String regex = "(\\d+.\\d+.\\d+ \\d+:\\d+:\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(list);
        matcher.find();
        startDate = sdf.parse(matcher.group(0));
        matcher.find();
        endDate = sdf.parse(matcher.group(0));
        duration = startDate.getTime() - endDate.getTime();
        System.out.println("run summary parsing date end");

        maximumRunningVusers = Integer.parseInt(reportDoc.select("td[headers=LraMaximumRunningVusers]").text());
        totalThroughputBytes = Double.parseDouble(reportDoc.select("td[headers=LraTotalThroughput]").text().replaceAll(" ",""));
        averageThroughputBytesSecond = Integer.parseInt(reportDoc.select("td[headers=LraAverageThroughput]").text().replaceAll(" ",""));
        totalHits = Integer.parseInt(reportDoc.select("td[headers=LraTotalHits]").text().replaceAll(" ",""));
        averageHitsPerSecond = Double.parseDouble(reportDoc.select("td[headers=LraAverageHitsPerSecond]").text().replaceAll(" ",""));
        totalErrors = Integer.parseInt(reportDoc.select("td[headers=LraTotalErrors]").text().replaceAll(" ",""));
        System.out.println("run summary end");
    }

    public RunSummary() {

    }
}
