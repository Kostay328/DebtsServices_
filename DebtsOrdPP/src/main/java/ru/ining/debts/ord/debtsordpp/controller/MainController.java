package ru.ining.debts.ord.debtsordpp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import ru.ining.debts.ord.debtsordpp.pojo.Accrual;
import ru.ining.debts.ord.debtsordpp.pojo.SR.Item;
import ru.ining.debts.ord.debtsordpp.pojo.SR.SecondRest;
import ru.ining.debts.ord.debtsordpp.pojo.SimplePaysJSON;
import ru.ining.debts.ord.debtsordpp.pojo.Vclmst;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    @Value("$mssql.url}")
    public String mssql;
    private final WebClient url = WebClient.create("https://api.xn--80ajbekothchmme5j.xn--p1ai/search/gibdd");


    @RestController
    public class Controller {
        @ResponseBody
        @RequestMapping(
                value = "/get",
                method = RequestMethod.GET,
                produces="application/json"
        )
        public ResponseEntity<?> get(@RequestParam String ordinance) {
            BodyCode bc = postRequest(ordinance);

            SecondRest sr = new SecondRest();

            Item item = new Item();
            item.set

            sr.setItem();
            String res = bc.body;
            System.out.println(res);

            return ResponseEntity.status(bc.code).body(res);
        }
    }

    class BodyCode {
        Accrual body;
        int code;

        public BodyCode() {
        }
    }

    public static void main(String[] args) throws Exception {
        new MainController().process("jdbc:sqlserver://192.168.1.22:14333;database=Test_Debts;user=sa;password=89037637168");
    }

    public void process(String mssql) throws Exception {
        getOrdinanceLst(mssql);

    }

    private BodyCode postRequest(String uin) {
        BodyCode bc = new BodyCode();
        ObjectMapper mapper = new ObjectMapper();
        SimplePaysJSON simplePaysJSON = null;
        while (true) {
            try {
                String body = "[{document_type\":\"uin\",document_value\":\""+uin+"\",additional_fields\":{}}]";

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
                bc.code = res.getStatusCodeValue();


                bc.body = simplePaysJSON.getAccruals().get(0);
                break;
            } catch (Exception e) {
                //ошибки при запросе json'a из a-3
                System.out.println(e.getMessage());
            }

        }
        return bc;
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

