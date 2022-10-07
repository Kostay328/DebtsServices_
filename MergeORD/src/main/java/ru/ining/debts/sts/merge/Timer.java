package ru.ining.debts.sts.merge;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static ru.ining.debts.sts.merge.MainController.checkOrd;

@Component
public class Timer {
    @Scheduled(cron = "0 30 23 * * ?") //секунда минута час;  https://russianblogs.com/article/11681337088/
    public void task() {
        if(!MainController.checkLaunched)
            System.out.println("Запуск по таймеру, проверено " + checkOrd(new MainController().msSqlUrl) + " записей");
        else
            System.out.println("Попытка запуска проверки при том что она уже идет!");
    }
}
