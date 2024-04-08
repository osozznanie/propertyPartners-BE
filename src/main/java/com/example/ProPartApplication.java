package com.example;

import com.example.projects.domain.About;
import com.example.projects.domain.FloorPlan;
import com.example.projects.domain.Project;
import com.example.projects.mapper.ProjectMapper;
import com.example.projects.service.ProjectService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@EnableMongoRepositories
public class ProPartApplication implements CommandLineRunner {

    private final ProjectService projectRepository;
    private final ProjectMapper projectMapper;

    @Autowired
    public ProPartApplication(ProjectService projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    public static void main(String[] args) {
        SpringApplication.run(ProPartApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // URL внешнего сайта с проектами
        String url = "https://alnair.ae/app";

        // Получаем HTML-страницу
        Document doc = Jsoup.connect(url).get();

        // Извлекаем данные проектов из HTML
        Elements projectElements = doc.select("div._root_1ru7q_1");
        List<Project> projects = new ArrayList<>();
        for (Element projectElement : projectElements) {
            String name = projectElement.select("div._name_1ru7q_93").text();
            String address = projectElement.select("div._address_1ru7q_100").text();
            String builder = projectElement.select("div._builder_1ru7q_87").text();
            String logoLink = projectElement.select("img._images_1ru7q_23").attr("src");
            String recommendation = projectElement.select("div._content_1ferd_35").text();

            Elements priceElements = projectElement.select("div._priceItem_fv9pn_10");
            Map<String, Double> prices = new HashMap<>();
            for (Element priceElement : priceElements) {
                String label = priceElement.select("span._priceLabel_fv9pn_18").text();
                Double price = Double.parseDouble(priceElement.select("span._priceValue_fv9pn_44").text());
                prices.put(label, price);
            }

            Project project = Project.builder()
                    .name(name)
                    .location(address)
                    .about(new HashMap<>())
                    .build();


            projects.add(project);
        }

//        for (int i = 0; i < projects.size(); i++) {
//            projectRepository.createProject(projectMapper.toDto(projects.get(i), null));
//        }
    }
}
