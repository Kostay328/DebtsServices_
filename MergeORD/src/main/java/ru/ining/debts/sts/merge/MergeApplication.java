package ru.ining.debts.sts.merge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

@SpringBootApplication
@EnableScheduling
public class MergeApplication {

    public static void main(String[] args) {
//        PrintStream out = null;
//        try {
//            out = new PrintStream(new FileOutputStream("/home/konnik/DebtsServices/output.txt"));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        System.setOut(out);
//        System.setErr(out);

        SpringApplication.run(MergeApplication.class, args);
    }

}
