package ru.ining.debts.sts.merge.mergests;

import com.opencsv.CSVWriter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import ru.ining.debts.sts.merge.mergests.pojo.*;
import ru.ining.debts.sts.merge.mergests.pojo.ordinance.SR.SecondRest;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@Controller
public class MainController {
    private WebClient client = WebClient.create("http://localhost:8080/get");
    private WebClient urlSts = WebClient.create("http://localhost:8090/get");

    public static void main(String[] args) throws Exception {
        MainController mc = new MainController();

        List<String> stsList = new ArrayList<>();
        stsList = getStsLst();
        stsList.add("9939888783");


//        List<String> ordList = getOrdinanceLst();
//        FileWriter writer = new FileWriter("ordinance.csv");
        File file = new File("sts.csv");
        try {
            FileWriter outputfile = new FileWriter(file);

            CSVWriter writer = new CSVWriter(outputfile);

            String[] header = { "СТС", "УИН", "Дата", "Получатель", "ИНН", "КПП", "Расчетный счет", "Банк получателя", "БИК", "ОКТМО", "КБК", "Сумма", "Скидка", "Сумма со скидкой" };
            writer.writeNext(header);
            int cnt = 0;
            for (String sts : stsList) {
                System.out.println(++cnt);
                List<Field> f = new ArrayList<>();
                Fields fields = null;

                while (true) {
                    try {
                        fields = mc.get(sts).getItem().getTemplate().getDiv().get(1).getFields();
                        f = fields.getField();
                    }catch (Exception e){}
                    if(fields != null)
                        break;
                }
                if(f != null && !f.isEmpty()) {
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat sdf2 = new SimpleDateFormat("dd.MM.yyyy");
                    List<Item__1> il = new ArrayList<>();
                    il = f.get(0).getRadio().getItem();


                    for (Item__1 item : il) {
                        String ord = item.getAddInfo().getDocnum();
                        //                String date = item.getAddInfo().getDate();
                        //                String sum = item.getAddInfo().getSum();

                        List<ru.ining.debts.sts.merge.mergests.pojo.ordinance.SR.Field> fl = new ArrayList<>();

                        while (true) {
                            try {
                                fl = mc.getOrdinance(ord).getItem().getTemplate().getDiv().get(0).getFields().getField();
                            }catch (Exception e){}
                            break;
                        }
                        String sts_ = "";    //Свидетельство о регистрации
                        String uin = "";    //Постановление
                        String date = "";   //Дата постановления
                        String plat = "";   //Получатель платежа
                        String inn = "";    //ИНН
                        String kpp = "";    //КПП
                        String rasch = "";  //Расчетный счет
                        String bank = "";   //Банк получателя
                        String bik = "";    //БИК
                        String oktmo = "";  //ОКТМО(ОКАТО)
                        String kbk = "";    //КБК
                        String sum = "";    //Сумма штрафа, руб.
                        String hsd = "";    //Скидка 50%
                        String hs = "";     //К оплате (с учетом скидки), руб.


                        for (ru.ining.debts.sts.merge.mergests.pojo.ordinance.SR.Field field: fl) {
                            if (field.getDescription().contains("Номер постановления (УИН)"))
                                uin = field.getValue();
                            if (field.getDescription().contains("Свидетельство о регистрации"))
                                sts_ = field.getValue();
                            if (field.getDescription().contains("Постановление"))
                                uin = field.getValue();
                            if (field.getDescription().contains("Дата постановления"))
                                date = sdf2.format(sdf1.parse(field.getValue()));
                            if (field.getDescription().contains("Получатель платежа"))
                                plat = field.getValue();
                            if (field.getDescription().contains("ИНН"))
                                inn = field.getValue();
                            if (field.getDescription().contains("КПП"))
                                kpp = field.getValue();
                            if (field.getDescription().contains("Расчетный счет"))
                                rasch = field.getValue();
                            if (field.getDescription().contains("Банк получателя"))
                                bank = field.getValue();
                            if (field.getDescription().contains("БИК"))
                                bik = field.getValue();
                            if (field.getDescription().contains("ОКТМО(ОКАТО)"))
                                oktmo = field.getValue();
                            if (field.getDescription().contains("КБК"))
                                kbk = field.getValue();
                            if (field.getDescription().contains("Сумма штрафа, руб."))
                                sum = field.getValue();
                            if (field.getDescription().contains("Скидка") && field.getDescription().contains("%"))
                                hsd = field.getValue();
                            if (field.getDescription().contains("К оплате"))
                                hs = field.getValue();
                        }

                        String[] d = {sts_, uin, date, plat, inn, kpp, rasch, bank, bik, oktmo, kbk, sum, hsd, hs};
                        writer.writeNext(d);
                    }
                }
            }
        writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @RestController
    public class Controller {
        @GetMapping("/get")
        @ResponseBody
        public ResponseEntity<?> get() {
            return ResponseEntity.status(404).body(123);
        }
    }

    public static List<String> getStsLst() {
        List<String> resLst = new ArrayList<>();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://192.168.1.22:14333;database=Autopark_Nizhfarm;user=sa;password=89037637168");
            String sql = "SELECT Tcard FROM Vclmst WHERE Rcdsts < 9";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            while(rs.next()) {
                String tcard = rs.getString("Tcard");
                if(tcard.length() == 10)
                    resLst.add(tcard);
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }

        return resLst;
    }

    /**
     *
     * @param ordinance
     * @return
     */
    SecondRest getOrdinance(String ordinance){
        SecondRest sr = null;
        try {
            var res2 = client.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("ordinance",ordinance)
                            .build())
                    .retrieve()
                    .bodyToMono(SecondRest.class)
                    .block();
            System.out.println(res2);
            sr = res2;

            System.out.println(res2);

        } catch(Exception e){
            e.printStackTrace();
        }

        return sr;
    }

    /**
     *
     * @param sts
     * @return
     */
    Jsonsts get(String sts){
        Jsonsts sr = null;
        try {
            var res2 = urlSts.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("sts",sts)
                            .build())
                    .retrieve()
                    .bodyToMono(Jsonsts.class)
                    .block();
            System.out.println(res2);
            sr = res2;

            System.out.println(res2);

        } catch(Exception e){
            e.printStackTrace();
        }

        return sr;
    }
}
