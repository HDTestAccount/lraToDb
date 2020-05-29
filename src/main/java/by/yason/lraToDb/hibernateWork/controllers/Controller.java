package by.yason.lraToDb.hibernateWork.controllers;

import by.yason.lraToDb.hibernateWork.service.ReportService;
import by.yason.lraToDb.report.Report;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

@org.springframework.stereotype.Controller
public class Controller {

    private static final Logger log = LogManager.getLogger(Controller.class);
    @Autowired
    private ReportService reportService;
//    private static final Logger logger = LoggerFactory.getLogger(main.class);

    @GetMapping("/")
    public String get_all_page(Map<String, Object> model) throws IOException, ParseException {
        Iterable<Report> messages = reportService.getListReports();
        model.put("messages", messages);
        return "get_all_html";
    }


    @GetMapping("/upload_page")
    public String upload_page(Map<String, Object> model) throws IOException, ParseException {
        Iterable<Report> messages = reportService.getListReports();
        model.put("messages", messages);
        return "upload_html";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam(value = "file", required = false) MultipartFile file1, Map<String, Object> model) throws IOException, ParseException {// имена параметров (тут - "file") - из формы JSP.
        log.info("TEST");
        try {
            String name = null;
            log.info("!file1.isEmpty() = " + !file1.isEmpty());
            if (!file1.isEmpty()) {
                log.info("TEST11");
                byte[] bytes = file1.getBytes();
                name = file1.getOriginalFilename();
                String rootPath = "c:\\tmp1\\";  //try also "C:\path\"
                File dir = new File(rootPath + File.separator + "loadFiles");
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                log.info("TEST12");
                File uploadedFile = new File(dir.getAbsolutePath() + File.separator + name);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));
                stream.write(bytes);
                stream.flush();
                stream.close();
                log.info("TEST13");
                log.info("uploaded: " + uploadedFile.getAbsolutePath());
                reportService.saveReport(uploadedFile.getAbsolutePath());
                log.info("TEST14");
                Iterable<Report> messages = reportService.getListReports();
                log.info("TEST15");
                model.put("messages", messages);
                log.info("TEST16");
                log.info("messages" + messages.toString() + "\n");
                log.info("model" + model.toString() + "\n");

            }
        } catch (Exception e) {
            log.info("TEST2");
            log.error(e.getMessage(), e.getCause());
        }
//        } finally {
//            log.info("TEST3");
//            return "/upload_html";
//        }
        Iterable<Report> messages = reportService.getListReports();
        model.put("messages", messages);
        return "upload_html";
    }
}