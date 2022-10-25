package ru.ining.debts.sts.get.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import ru.ining.debts.sts.get.pojo.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    @Value("${json.url}")
    public String jsonUrl;
    private final WebClient url = WebClient.create("https://api.xn--80ajbekothchmme5j.xn--p1ai/search/gibdd");

    public static void main(String[] args) throws Exception {
        new MainController().process("jdbc:sqlserver://192.168.1.22:14333;database=Test_Debts;user=sa;password=89037637168");
    }

    public void process(String mssql) throws Exception {
        int count = 0;
        String s = "";
        for(Vclmst vclmst:getOrdinanceLst(mssql)) {
            System.out.println(count++);
            List<Accrual> al = new MainController().postRequest(vclmst.getTcard());
            if(al!=null) {
                for (Accrual accrual : al) {
                    if(!existOrdinance(accrual.getUin(), mssql)) {
                        System.out.println("УИН: " + accrual.getUin() + ", добавление в базу");
                        insertNewDebt(accrual.getUin(), mssql);
                    }else {
                        System.out.println("Найден в базе поэтому не добавляем");
                    }
                    s+=accrual.getUin()+"\n";
                }
            }else {
                System.out.println("Штраф не найден");
            }
        }
        System.out.println(s);
    }

    private List<Accrual> postRequest(String sts) {
        int code = 0;
        ObjectMapper mapper = new ObjectMapper();
        SimplePaysJSON simplePaysJSON = null;
        while (true) {
            try {
                String body = "[{\"document_type\":\"ctc\",\"document_value\":\"" + sts + "\",\"additional_fields\":{}}]";

                ResponseEntity<SimplePaysJSON[]> res = url.post()
                        .uri(uriBuilder -> uriBuilder.build())
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(body))
                        .header("Authorization", "Bearer C6ujcuF-cSrLfJxjWig5SsZ59YNeLK17")

                        .retrieve()
                        .toEntity(SimplePaysJSON[].class)
                        .block();
                System.out.println(res.getStatusCode());
                simplePaysJSON = res.getBody()[0];
                code = res.getStatusCodeValue();

//                s = mapper.writeValueAsString(simplePaysJSON);
                break;
            } catch (Exception e) {
                //ошибки при запросе json'a из a-3
                System.out.println(e.getMessage());
            }

        }
        return simplePaysJSON.getAccruals();
    }

    public static int insertNewDebt(String ordinance, String url) throws Exception {
        Connection dbConnection = DriverManager.getConnection(url);
        String sql = "INSERT INTO newDebts (Ordinance) VALUES (?);";

        PreparedStatement ps = dbConnection.prepareStatement(sql);
        ps.setString(1,ordinance);

        return ps.executeUpdate();
    }

    public static boolean existOrdinance(String ord, String url) {
        boolean res = false;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(url);
            String sql = "SELECT COUNT(ID) as cnt FROM newDebts WHERE Ordinance = '"+ord+"'";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            rs.next();

            res = rs.getInt("cnt") > 0;
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }

        return res;
    }

    public static List<Vclmst> getOrdinanceLst(String url) {
        List<Vclmst> resLst = new ArrayList<>();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(url);
            String sql = "SELECT Tcard, Vcl, Vclstamp FROM Vclmst WHERE LEN(Tcard) = 10";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            while(rs.next()) {
                Vclmst vclmst = new Vclmst();
                vclmst.setTcard(rs.getString("Tcard"));
                vclmst.setVcl(rs.getString("Vcl"));
                vclmst.setVclstamp(rs.getString("Vclstamp"));
                resLst.add(vclmst);
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }

        return resLst;
    }
}

