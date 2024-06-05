package com.example.gsmoa;

import com.example.gsmoa.Contest.entity.Contest;
import com.example.gsmoa.Contest.repository.ContestRepository;
import com.example.gsmoa.Contest.service.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class ContestGenerater {

    @Autowired
    private ContestService contestService;
    @Autowired
    private ContestRepository contestRepository;

    public List<Contest> parseCSV(String filePath) {
        List<Contest> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
            String line;
            if ((line = br.readLine()) != null) {
                if (line.startsWith("\uFEFF")) {
                    line = line.substring(1);
                }
            }

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                Contest contest = new Contest();

                contest.setTitle(values[0]);
                contest.setHostName(values[1]);
                String period = values[3].replaceAll("[^0-9]", "").trim();
                contest.setPeriod(Integer.parseInt(period));
                contest.setTag(values[6]);
                contest.setDetails(values[7]); // Add details from the CSV file

                String imagePath = values[4].replace("\"", ""); // Remove quotes from the image path
                byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
                contest.setImage(imageBytes);

                records.add(contest);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }

    public void printParsedCSVAsContests(String filePath) {
        List<Contest> records = parseCSV(filePath);
        for (Contest contest : records) {
            System.out.println(contest);
        }
    }
    public void saveAllContestsToDB(String filePath) {
        List<Contest> records = parseCSV(filePath);
        Set<String> reportedTitles = new HashSet<>();
        for (Contest contest : records) {
            try {
                contestService.saveContest(contest);
            } catch (Exception e) {
                if (!reportedTitles.contains(contest.getTitle())) {
                    System.out.println("이미 존재하는 공모전: " + contest.getTitle());
                    reportedTitles.add(contest.getTitle());
                }
            }
        }
    }

    public void updateAllContestDetails(String filePath) { // CSV 파일에서 details를 읽어와 모든 공모전의 details를 업데이트
        List<Contest> contestsFromFile = parseCSV(filePath);
        List<Contest> contestsFromDB = contestRepository.findAll();

        for (Contest contestFromDB : contestsFromDB) {
            for (Contest contestFromFile : contestsFromFile) {
                if (contestFromDB.getTitle().equals(contestFromFile.getTitle())) {
                    contestFromDB.setDetails(contestFromFile.getDetails());
                    contestRepository.save(contestFromDB);
                    break;
                }
            }
        }
    }


    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ContestGenerater.class, args);
        ContestGenerater contestGenerater = context.getBean(ContestGenerater.class);
        String filePath = "C:\\GIT\\Capstone\\크롤링\\공모전.csv";
        contestGenerater.saveAllContestsToDB(filePath);
        contestGenerater.updateAllContestDetails(filePath);

    }
}