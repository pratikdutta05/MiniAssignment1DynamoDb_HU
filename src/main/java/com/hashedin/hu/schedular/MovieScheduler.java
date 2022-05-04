//package com.hashedin.hu.schedular;
//
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.io.File;
//import java.util.Date;
//
//@Component
//public class MovieScheduler {
//
//    @Scheduled(cron = "30 * * * *")
//    public void cronJobSch() {
//
//        String filePath1 = "src/main/resources/movies.csv";
//        String filePath2 = "src/main/resources/test/movies.csv";
//
//        File userFile = new File(filePath1);
//        File storedFile = new File(filePath2);
//
//
//        long lastModifiedUserFile = userFile.lastModified();
//        long lastModifiedStoredFile = storedFile.lastModified();
//
//        if(lastModifiedUserFile!=lastModifiedStoredFile) {
//            System.out.println("changed");
//        }
//
//    }
//
//}
