package ru.ining.debts.sts.merge;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static ru.ining.debts.sts.merge.MainController.checkOrd;

@Component
@RequiredArgsConstructor
public class Timer {
    private final MainController mainController;

    @Value("${user.mssql.url}")
    public String msSqlUrl;

    @Scheduled(cron = "0 5 21 * * ?") //секунда минута час;  https://russianblogs.com/article/11681337088/
    public void task() {
        if(!MainController.checkLaunched)
            System.out.println("Запуск по таймеру, проверено " + checkOrd(msSqlUrl) + " записей");
        else
            System.out.println("Попытка запуска проверки при том что она уже идет!");
    }
}
