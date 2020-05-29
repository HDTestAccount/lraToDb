package by.yason.lraToDb.hibernateWork.service;

import by.yason.lraToDb.hibernateWork.repos.ReportRepo;
import by.yason.lraToDb.report.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;

@Service
public class ReportService {
    @Autowired
    private ReportRepo reportRepo;

    public ReportService() {
    }

    public void saveReport(String absolutePath) throws IOException, ParseException {
        Report report = new Report(absolutePath);
        System.out.println("end create report");
        reportRepo.save(report);
    }

    public Iterable<Report> getListReports() throws IOException, ParseException {

        return reportRepo.findAll();
    }

    public long countReports() throws IOException, ParseException {

        return reportRepo.count();
    }
}
