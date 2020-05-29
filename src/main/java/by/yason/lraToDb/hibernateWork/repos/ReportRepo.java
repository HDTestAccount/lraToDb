package by.yason.lraToDb.hibernateWork.repos;

import by.yason.lraToDb.report.Report;
import org.springframework.data.repository.CrudRepository;

public interface ReportRepo extends CrudRepository<Report, Long> {
}
