package ru.ining.debts.sts.merge;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import ru.ining.debts.sts.merge.pojo.DB.NewDebts;
import ru.ining.debts.sts.merge.pojo.DB.Vclmst;
import ru.ining.debts.sts.merge.pojo.SR.Field;
import ru.ining.debts.sts.merge.pojo.SR.SecondRest;
import ru.ining.debts.sts.merge.pojo.XML.Xml;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

import static ru.ining.debts.sts.merge.Const.*;


@Controller
public class MainController {
    @Value("${json.url}")
    public String jsonUrl;
//    private final WebClient url = WebClient.create("http://json-api:8080/get");
    private final WebClient url = WebClient.create("http://localhost:8080/get");

    @Value("${user.mssql.url}")
    public String msSqlUrl;

    private static SimpleDateFormat createSdf(String format) {
        SimpleDateFormat res = new SimpleDateFormat(format);
        res.setTimeZone(TIME_ZONE_MSC);
        return res;
    }

    public static SimpleDateFormat sdf1 = createSdf("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat sdf2 = createSdf("yyyy-MM-dd");
    public static SimpleDateFormat sdf3 = createSdf(SQL_DATE_FORMAT);




    public static void main(String[] args) {
        if(!checkLaunched)
            checkOrd("jdbc:sqlserver://192.168.1.22:14333;database=Test_Debts;user=sa;password=89037637168");
        else
            System.out.println("Попытка запуска проверки при том что она уже идет!");
    }

    @RestController
    public class Controller {
        @GetMapping("/start")
        @ResponseBody
        public ResponseEntity<?> get() {
            if(!checkLaunched)
                checkOrd(msSqlUrl);
            else
                System.out.println("Попытка запуска проверки при том что она уже идет!");
            return ResponseEntity.status(200).body("Started");
        }
    }

    public static boolean checkLaunched = false;

    public static int checkOrd(String msSqlUrl){
        checkLaunched = true;
        int cnt = 0;
        int cntAdd = 0;
        int cntUpdate = 0;

        MainController mc = new MainController();

        List<NewDebts> newDebtsList = getOrdinanceLst(msSqlUrl);

        try {
            for (NewDebts nd : newDebtsList) {
                Timestamp now = new Timestamp(new java.util.Date().getTime());

                System.out.println(++cnt);
                SecondRest secondRest = mc.get(nd.getOrdinance());
                List<Field> fl = secondRest.getItem().getTemplate().getDiv().get(0).getFields().getField();

                String sts = "";    //Свидетельство о регистрации
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


                for (Field f:fl) {
                    String v = f.getValue().replace("'","");

                    if(f.getDescription().contains("Номер постановления (УИН)"))
                        uin = v;
                    if(f.getDescription().contains("Свидетельство о регистрации"))
                        sts = v;
                    if(f.getDescription().contains("Постановление"))
                        uin = v;
                    if (f.getDescription().contains("Дата постановления"))
                        date = v;
                    if(f.getDescription().contains("Получатель платежа"))
                        plat = v;
                    if(f.getDescription().contains("ИНН"))
                        inn = v;
                    if(f.getDescription().contains("КПП"))
                        kpp = v;
                    if(f.getDescription().contains("Расчетный счет"))
                        rasch = v;
                    if(f.getDescription().contains("Банк получателя"))
                        bank = v;
                    if(f.getDescription().contains("БИК"))
                        bik = v;
                    if(f.getDescription().contains("ОКТМО(ОКАТО)"))
                        oktmo = v;
                    if(f.getOrder() == 512 || f.getName().contains("iRecipientKBK"))
                        kbk = v;
                    if(f.getDescription().contains("Сумма штрафа, руб."))
                        sum = v;
                    if(f.getDescription().contains("Скидка") && f.getDescription().contains("%"))
                        hsd = v.replace("активна до","").trim();
                    if(f.getDescription().contains("К оплате")) {
                        if(!sum.equals(""))
                            hs = v;
                        else
                            sum = v;
                    }
                }

//                if(nd.getTcard().length() == 0)
                    nd.setTcard(sts);

                if(inn.length() > 0) {
                    try {
                        //TODO
                        String s = secondRest.getItem().getTemplate().getAdditionalProperties().get("parameters").toString();
                        s = s.substring(s.indexOf("parameter=[{name=charge, value=") + 31);
                        s = s.substring(0, s.indexOf("}, {name"));


                        XmlMapper xmlMapper = new XmlMapper();
                        xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
                        Xml xml = xmlMapper.readValue(s, Xml.class);

                        String offencePlace = "";    //Место нарушения
                        String offenceDate = "";     //Дата нарушения
                        String legalAct = "";        //Статья нарушения
                        String DepartmentCode = "";  //?
                        String DepartmentName = "";  //?
                        String SRV_CODE = "";        //?

                        for (Xml.Parameter p : xml.parameters) {
                            String v = p.value.replace("'", "");

                            if (p.name.equals("offencePlace"))
                                offencePlace = v;
                            if (p.name.equals("offenceDate"))
                                offenceDate = v;
                            if (p.name.equals("legalAct"))
                                legalAct = v;
                            if (p.name.equals("DepartmentCode"))
                                DepartmentCode = v;
                            if (p.name.equals("DepartmentName"))
                                DepartmentName = v;
                            if (p.name.equals("SRV_CODE"))
                                SRV_CODE = v;
                        }
                        nd.setPlace(offencePlace);
                        nd.setNaznachenie(legalAct);
                        nd.setOfndte(new Timestamp(sdf1.parse(offenceDate).getTime()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                Vclmst v = getVclmst(nd.getTcard(), msSqlUrl);

                nd.setVcl(v.getVcl());
                nd.setVclstamp(v.getVclstamp());
                nd.setRegnum(v.getRegnum());

                boolean srvCodeZero = nd.getSrvCode().equals("0");
                boolean entdteEmpty = false;

                //Найден на a-3
                if(inn.length() > 0) {
                    System.out.println("Найден на а-3;  Ord: " + uin);
                    nd.setTcard(sts);
                    nd.setInn(inn);
                    nd.setKpp(kpp);
                    nd.setRaschSchet(rasch);
                    nd.setBankPol(bank);
                    nd.setBik(bik);
                    nd.setOktomo(oktmo);
                    nd.setKbk(kbk);
//                nd.setPaytoHalf(hsd);
                    if (hs.length() > 0)
                        nd.setSumHalf(hs);
                    if (sum.length() > 0)
                        nd.setSum(sum);
                    nd.setUfk(plat);
                    nd.setLstchkdte(now);
                    nd.setLstchgdte(now);
                    if(sdf2.format(nd.getEntdte()).equals(DEFAULT_DATE_STRING)) {
                        entdteEmpty = true;
                        nd.setEntdte(now);
                        nd.setEntby(SERVICE_NAME);
                    }
                    nd.setLstchgby(SERVICE_NAME);
                    nd.setSrvCode("5");
                    nd.setDbtdte(new Timestamp(sdf2.parse(date).getTime()));
                    try {
                        nd.setPaytoHalf(new Timestamp(sdf1.parse(hsd).getTime()));
                    }catch (Exception e){
                        System.out.println("Не найдена дата оплаты со скидкой");
                        nd.setPaytoHalf(new Timestamp(sdf2.parse(DEFAULT_DATE_STRING).getTime()));
                    }
                    nd.setPaytodte(new Timestamp(sdf2.parse(date).getTime()+((60000L * 60L * 24L) * 60L)));
                    nd.setBrn(v.getBrn());
                    if(nd.getBrn().equals("1"))
                        nd.setBrnname("АО \"Нижфарм\"");
                    else
                        nd.setBrnname("");


                    System.out.println("Запись нового");
                    if(srvCodeZero || entdteEmpty)
                        updateNewDebts(nd, msSqlUrl);
                    else
                        updateNewDebtsNoEnt(nd, msSqlUrl);
                    cntAdd++;
                } else {
//                    if(!nd.getSrvCode().equals("5")) {
//                        System.out.println("Не найден на а-3 но в базе записан как активный");
//                        if(Integer.parseInt(nd.getSrvCode())<5)
//                            updateNewDebtsCode((Integer.parseInt(nd.getSrvCode()) + 1)+"", nd.getId());
//                        else {
//                            nd.setRcdsts("1");
//                            nd.setSrvCode("9");
//                        }
//                    }
//                    else {
                    System.out.println("Не найден на а-3;  Ord: " + uin);
                    if (Integer.parseInt(nd.getSrvCode()) < 4) {
                        if(sdf2.format(nd.getEntdte()).equals(DEFAULT_DATE_STRING))
                            updateNewDebtsCodeEnt((Integer.parseInt(nd.getSrvCode()) + 1) + "", nd.getId(), msSqlUrl);
                        else
                            updateNewDebtsCode((Integer.parseInt(nd.getSrvCode()) + 1) + "", nd.getId(), msSqlUrl);
                        cntUpdate++;
                    }else {
                        if(sdf2.format(nd.getEntdte()).equals(DEFAULT_DATE_STRING))
                            updateNewDebtsCodeRcdEnt("9", "1", nd.getId(), msSqlUrl);
                        else
                            updateNewDebtsCodeRcd("9", "1", nd.getId(), msSqlUrl);
                        cntUpdate++;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkLaunched = false;
        return cnt;
    }

    public static int updateNewDebts(NewDebts newDebt, String url) throws Exception {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection conn = DriverManager.getConnection(url);
        String sql = "UPDATE newDebts SET Vclstamp = '"+newDebt.getVclstamp()+"', Vcl = "+newDebt.getVcl()+
                ", Reason = '"+newDebt.getNaznachenie()+"', Tcard = '"+newDebt.getTcard()+"', Sum = "
                +newDebt.getSum()+", SumHalf = "+newDebt.getSumHalf()+", Regnum = '"+newDebt.getRegnum()+
                "', place = '"+newDebt.getPlace()+"', inn = '"+newDebt.getInn()+"', ufk = '"+newDebt.getUfk()+
                "', kpp = '"+newDebt.getKpp()+"', kbk = '"+newDebt.getKbk()+"', raschSchet = '"+newDebt.getRaschSchet()+
                "', bik = '"+newDebt.getBik()+"', bankPol = '"+newDebt.getBankPol()+"', oktomo = '"+newDebt.getOktomo()+
                "', naznachenie = '"+newDebt.getNaznachenie()+"', Entby = '"+newDebt.getEntby()+"', Entdte = '"+sdf3.format(newDebt.getEntdte())+
                "', Lstchgby = '"+newDebt.getLstchgby()+
                "', Lstchkdte = '"+sdf3.format(newDebt.getLstchkdte())+
                "', Dbtdte = '"+sdf3.format(newDebt.getDbtdte())+
                "', Ofndte = '"+sdf3.format(newDebt.getOfndte())+
                "', Paytodte = '"+sdf3.format(newDebt.getPaytodte())+
                "', PaytoHalf = '"+sdf3.format(newDebt.getPaytoHalf())+
                "', Brn = "+newDebt.getBrn()+", Brnname = '"+newDebt.getBrnname()+
                "', Lstchgdte = '"+sdf3.format(newDebt.getLstchgdte())+
                "', srvCode = "+newDebt.getSrvCode()+" WHERE ID = "+newDebt.getId();

        return conn.createStatement().executeUpdate(sql);
    }

//    public static boolean updateNewDebts(NewDebts newDebt, String url) {
//        try {
//            Connection dbConnection = DriverManager.getConnection(url);
//            PreparedStatement ps = dbConnection.prepareStatement("UPDATE newDebts SET Vclstamp = ?, Vcl = ?, Reason = ?, Tcard = ?, Sum = ?, " +
//                    "SumHalf = ?, Regnum = ?, place = ?, inn = ?, ufk = ?, kpp = ?, kbk = ?, raschSchet = ?, bik = ?, bankPol = ?, oktomo = ?, " +
//                    "naznachenie = ?, Entby = ?, Entdte = ?, Lstchgby = ?, Lstchkdte = ?, Lstchgdte = ?, Dbtdte = ?, Ofndte = ?, Paytodte = ?, PaytoHalf = ?, " +
//                    "Brn = ?, Brnname = ?, srvCode = ?, Entby = ?, Entdte = ? WHERE ID = ?");
//
//
//            ps.setString(1, newDebt.getVclstamp());
//            ps.setInt(2, Integer.parseInt(newDebt.getVcl()));
//            ps.setString(3, newDebt.getNaznachenie());
//            ps.setString(4, newDebt.getTcard());
//            ps.setDouble(5, Double.parseDouble(newDebt.getSum()));
//            ps.setDouble(6, Double.parseDouble(newDebt.getSumHalf()));
//            ps.setString(7, newDebt.getRegnum());
//            ps.setString(8, newDebt.getPlace());
//            ps.setString(9, newDebt.getInn());
//            ps.setString(10, newDebt.getUfk());
//            ps.setString(11, newDebt.getKpp());
//            ps.setString(12, newDebt.getKbk());
//            ps.setString(13, newDebt.getRaschSchet());
//            ps.setString(14, newDebt.getBik());
//            ps.setString(15, newDebt.getBankPol());
//            ps.setString(16, newDebt.getOktomo());
//            ps.setString(17, newDebt.getNaznachenie());
//            ps.setString(18, newDebt.getEntby());
//            ps.setString(19, newDebt.getEntdte());
//            ps.setString(20, newDebt.getLstchgby());
//            ps.setString(21, newDebt.getLstchkdte());
//            ps.setString(22, newDebt.getLstchgdte());
//            ps.setString(23, newDebt.getDbtdte());
//            ps.setString(24, newDebt.getOfndte());
//            ps.setString(25, newDebt.getPaytodte());
//            ps.setString(26, newDebt.getPaytoHalf());
//            ps.setInt(27, Integer.parseInt(newDebt.getBrn()));
//            ps.setString(28, newDebt.getBrnname());
//            ps.setInt(29, Integer.parseInt(newDebt.getSrvCode()));
//            ps.setString(30, newDebt.getEntby());
//            ps.setString(31, newDebt.getEntdte());
//            ps.setInt(32, Integer.parseInt(newDebt.getId()));
//
//            ps.executeUpdate();
//        }catch (Exception e){
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }


//    public static boolean updateNewDebtsNoEnt(NewDebts newDebt, String url) {
//        try {
//            Connection dbConnection = DriverManager.getConnection(url);
//            PreparedStatement ps = dbConnection.prepareStatement("UPDATE newDebts SET Vclstamp = ?, Vcl = ?, Reason = ?, Tcard = ?, Sum = ?, " +
//                            "SumHalf = ?, Regnum = ?, place = ?, inn = ?, ufk = ?, kpp = ?, kbk = ?, raschSchet = ?, bik = ?, bankPol = ?, oktomo = ?, " +
//                            "naznachenie = ?, Entby = ?, Entdte = ?, Lstchgby = ?, Lstchkdte = ?, Lstchgdte = ?, Dbtdte = ?, Ofndte = ?, Paytodte = ?, PaytoHalf = ?, " +
//                            "Brn = ?, Brnname = ?, srvCode = ? WHERE ID = ?");
//
//
//            ps.setString(1, newDebt.getVclstamp());
//            ps.setInt(2, Integer.parseInt(newDebt.getVcl()));
//            ps.setString(3, newDebt.getNaznachenie());
//            ps.setString(4, newDebt.getTcard());
//            ps.setDouble(5, Double.parseDouble(newDebt.getSum()));
//            ps.setDouble(6, Double.parseDouble(newDebt.getSumHalf()));
//            ps.setString(7, newDebt.getRegnum());
//            ps.setString(8, newDebt.getPlace());
//            ps.setString(9, newDebt.getInn());
//            ps.setString(10, newDebt.getUfk());
//            ps.setString(11, newDebt.getKpp());
//            ps.setString(12, newDebt.getKbk());
//            ps.setString(13, newDebt.getRaschSchet());
//            ps.setString(14, newDebt.getBik());
//            ps.setString(15, newDebt.getBankPol());
//            ps.setString(16, newDebt.getOktomo());
//            ps.setString(17, newDebt.getNaznachenie());
//            ps.setString(18, newDebt.getEntby());
//            ps.setString(19, newDebt.getEntdte());
//            ps.setString(20, newDebt.getLstchgby());
//            ps.setString(21, newDebt.getLstchkdte());
//            ps.setString(22, new Timestamp(sdf3.parse(newDebt.getLstchgdte());
//            ps.setString(23, new Timestamp(sdf3.parse(newDebt.getDbtdte());
//            ps.setString(24, new Timestamp(sdf3.parse(newDebt.getOfndte());
//            ps.setString(25, new Timestamp(sdf3.parse(newDebt.getPaytodte());
//            ps.setTimestamp(26, new Timestamp(sdf3.parse(newDebt.getPaytoHalf()).getTime()));
//            ps.setInt(27, Integer.parseInt(newDebt.getBrn()));
//            ps.setString(28, newDebt.getBrnname());
//            ps.setInt(29, Integer.parseInt(newDebt.getSrvCode()));
//            ps.setInt(30, Integer.parseInt(newDebt.getId()));
//
//            ps.executeUpdate();
//        }catch (Exception e){
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }


    public static int updateNewDebtsNoEnt(NewDebts newDebt, String url) throws Exception {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection conn = DriverManager.getConnection(url);
        String sql = "UPDATE newDebts SET Vclstamp = '"+newDebt.getVclstamp()+"', Vcl = "+newDebt.getVcl()+", Reason = '"+newDebt.getNaznachenie()+
                "', Tcard = '"+newDebt.getTcard()+"', Sum = "+newDebt.getSum()+", SumHalf = "+newDebt.getSumHalf()+", Regnum = '"+newDebt.getRegnum()+
                "', place = '"+newDebt.getPlace()+"', inn = '"+newDebt.getInn()+"', ufk = '"+newDebt.getUfk()+"', kpp = '"+newDebt.getKpp()+
                "', kbk = '"+newDebt.getKbk()+"', raschSchet = '"+newDebt.getRaschSchet()+"', bik = '"+newDebt.getBik()+"', bankPol = '"+newDebt.getBankPol()+
                "', oktomo = '"+newDebt.getOktomo()+"', naznachenie = '"+newDebt.getNaznachenie()+"', Lstchgby = '"+newDebt.getLstchgby()+
                "', Lstchkdte = '"+sdf3.format(newDebt.getLstchkdte())+
                "', Dbtdte = '"+sdf3.format(newDebt.getDbtdte())+
                "', Ofndte = '"+sdf3.format(newDebt.getOfndte())+
                "', Paytodte = '"+sdf3.format(newDebt.getPaytodte())+
                "', PaytoHalf = '"+sdf3.format(newDebt.getPaytoHalf())+
                "', Brn = "+newDebt.getBrn()+", Brnname = '"+newDebt.getBrnname()+
                "', Lstchgdte = '"+sdf3.format(newDebt.getLstchgdte())+
                "', srvCode = "+newDebt.getSrvCode()+" WHERE ID = "+newDebt.getId();

        return conn.createStatement().executeUpdate(sql);
    }

    public static int updateNewDebtsCode(String code, String id, String url) throws Exception {
        Connection dbConnection = DriverManager.getConnection(url);
        PreparedStatement ps = dbConnection.prepareStatement("UPDATE newDebts SET srvCode = ?, Lstchgby = ?, Lstchkdte = ?, Lstchgdte = ? WHERE ID = ?");
        String now = sdf3.format(new java.util.Date().getTime());

        ps.setInt(1, Integer.parseInt(code));
        ps.setString(2, SERVICE_NAME);
        ps.setString(3, now);
        ps.setString(4, now);
        ps.setInt(5, Integer.parseInt(id));

        return ps.executeUpdate();
    }

    public static int updateNewDebtsCodeEnt(String code, String id, String url) throws Exception {
        Connection dbConnection = DriverManager.getConnection(url);
        PreparedStatement ps = dbConnection.prepareStatement("UPDATE newDebts SET srvCode = ?, Lstchgby = ?, Lstchkdte = ?, Lstchgdte = ?, Entby = ?, Entdte = ? WHERE ID = ?");
        String now = sdf3.format(new java.util.Date().getTime());

        ps.setInt(1, Integer.parseInt(code));
        ps.setString(2, SERVICE_NAME);
        ps.setString(3, now);
        ps.setString(4, now);
        ps.setString(6, SERVICE_NAME);
        ps.setString(7, now);
        ps.setInt(5, Integer.parseInt(id));

        return ps.executeUpdate();
    }

    public static int updateNewDebtsCodeRcdEnt(String code, String rcdsts, String id, String url) throws Exception {
        Connection dbConnection = DriverManager.getConnection(url);
        PreparedStatement ps = dbConnection.prepareStatement("UPDATE newDebts SET Rcdsts = ?, srvCode = ?, Lstchgby = ?, Lstchkdte = ?, Lstchgdte = ?, Entby = ?, Entdte = ? WHERE ID = ?");
        String now = sdf3.format(new java.util.Date().getTime());

        ps.setInt(1, Integer.parseInt(rcdsts));
        ps.setInt(2, Integer.parseInt(code));
        ps.setString(3, SERVICE_NAME);
        ps.setString(4, now);
        ps.setString(5, now);
        ps.setString(6, SERVICE_NAME);
        ps.setString(7, now);
        ps.setInt(8, Integer.parseInt(id));


        return ps.executeUpdate();
    }

    public static int updateNewDebtsCodeRcd(String code, String rcdsts, String id, String url) throws Exception {
        Connection dbConnection = DriverManager.getConnection(url);
        PreparedStatement ps = dbConnection.prepareStatement("UPDATE newDebts SET Rcdsts = ?, srvCode = ?, Lstchgby = ?, Lstchkdte = ?, Lstchgdte = ? WHERE ID = ?");
        String now = sdf3.format(new java.util.Date().getTime());

        ps.setInt(1, Integer.parseInt(rcdsts));
        ps.setInt(2, Integer.parseInt(code));
        ps.setString(3, SERVICE_NAME);
        ps.setString(4, now);
        ps.setString(5, now);
        ps.setInt(6, Integer.parseInt(id));


        return ps.executeUpdate();
    }

//    public static Vclmst getVclmst(String Tcard) {
//        Vclmst nd = new Vclmst();
//        try {
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            Connection conn = DriverManager.getConnection(msSqlUrl);
//            String sql = "SELECT Vcl, Vclstamp, Regnum, Tcard FROM Vclmst WHERE Tcard = '" + Tcard + "'";
//            ResultSet rs = conn.createStatement().executeQuery(sql);
//
//            rs.next();
//            nd.setVcl(rs.getString("Vcl"));
//            nd.setVclstamp(rs.getString("Vclstamp"));
//            nd.setRegnum(rs.getString("Regnum"));
//            nd.setTcard(rs.getString("Tcard"));
//
//        } catch (Exception throwables) {
//            throwables.printStackTrace();
//        }
//
//        return nd;
//    }

    public static List<NewDebts> getOrdinanceLst(String url) {
        List<NewDebts> resLst = new ArrayList<>();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(url);
            String sql = "SELECT Ordinance, Tcard, Entby, Entdte, Lstchgby, Lstchkdte, Lstchgdte, ID, Srvcode, inn FROM newDebts WHERE Srvcode < 9";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            while(rs.next()) {
                NewDebts nd = new NewDebts();
                nd.setOrdinance(rs.getString("Ordinance"));
                nd.setTcard(rs.getString("Tcard"));
                nd.setEntby(rs.getString("Entby"));
                nd.setEntdte(rs.getTimestamp("Entdte"));
                nd.setLstchgby(rs.getString("Lstchgby"));
                nd.setLstchkdte(rs.getTimestamp("Lstchkdte"));
                nd.setLstchgdte(rs.getTimestamp("Lstchgdte"));
                nd.setId(rs.getString("ID"));
                nd.setSrvCode(rs.getString("Srvcode"));
                nd.setInn(rs.getString("inn"));
                resLst.add(nd);
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }

        return resLst;
    }



    public static Vclmst getVclmst(String Tcard, String url) {
        Vclmst nd = new Vclmst();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(url);
            String sql = "SELECT Vcl, Vclstamp, Regnum, Tcard, Brn FROM Vclmst WHERE Tcard = '" + Tcard + "'";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            rs.next();
            nd.setVcl(rs.getString("Vcl"));
            nd.setVclstamp(rs.getString("Vclstamp"));
            nd.setRegnum(rs.getString("Regnum"));
            nd.setTcard(rs.getString("Tcard"));
            nd.setBrn(rs.getString("Brn"));

        } catch (Exception throwables) {
            throwables.printStackTrace();
        }

        return nd;
    }

//    public static Vclmst getBrnmst(String Vcl) {
//        Vclmst nd = new Vclmst();
//        try {
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            Connection conn = DriverManager.getConnection(msSqlUrl);
//            String sql = "SELECT Vcl, Vclstamp, Regnum, Tcard FROM Vclmst WHERE Tcard = '" + Tcard + "'";
//            ResultSet rs = conn.createStatement().executeQuery(sql);
//
//            rs.next();
//            nd.setVcl(rs.getString("Vcl"));
//            nd.setVclstamp(rs.getString("Vclstamp"));
//            nd.setRegnum(rs.getString("Regnum"));
//            nd.setTcard(rs.getString("Tcard"));
//
//        } catch (Exception throwables) {
//            throwables.printStackTrace();
//        }
//
//        return nd;
//    }

    /**
     *
     * @param ordinance
     * @return
     */
    SecondRest get(String ordinance) throws InterruptedException {
        SecondRest sr = null;
        while (true) {
            try {
                sr = url.get()
                        .uri(uriBuilder -> uriBuilder
                                .queryParam("ordinance",ordinance)
                                .build())
                        .retrieve()
                        .bodyToMono(SecondRest.class)
                        .block();
            }catch (Exception e){
                Thread.sleep(10000);
                System.out.println(e.getMessage());
            }
                    if(sr != null)
                        break;
        }

        return sr;
    }
}
