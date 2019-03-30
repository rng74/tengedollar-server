package test.test.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.test.domain.Course;
import test.test.repo.CourseRepo;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

@RestController
@RequestMapping("course")
public class CourseController {
    private final CourseRepo courseRepo;

    @Autowired
    public CourseController(CourseRepo courseRepo) {
        this.courseRepo = courseRepo;
    }

    @GetMapping
    public List<Course> list() {
        courseRepo.findAll();
        return courseRepo.findAll();
    }

    private void generateOne() {
        try {
            String link = "https://openexchangerates.org/api/latest.json?app_id=";
            String api_key = "c55713a34c2a42699de30c3d1efeb62f";
            URL url = new URL(link + api_key);

            Scanner in = new Scanner((InputStream) url.getContent());
            String res = "";
            while (in.hasNext()) {
                res += in.nextLine();
            }

            JSONObject obj = new JSONObject(res);
            double value = obj.getJSONObject("rates").getDouble("KZT");

            Course course = new Course();
            course.setValue(value);
            courseRepo.save(course);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(fixedRate = 600000)
    public void run() {
        generateOne();
    }
}
