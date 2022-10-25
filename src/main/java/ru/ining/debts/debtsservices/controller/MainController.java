package ru.ining.debts.debtsservices.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import ru.ining.debts.debtsservices.pojo.FR;
import ru.ining.debts.debtsservices.pojo.SR.SecondRest;


@Controller
public class MainController {
    private WebClient webClientFirst = WebClient.create("https://www.a-3.ru/front_new/msp/init_step_sequence_obr.do");
    private WebClient webClientSecond = WebClient.create("https://www.a-3.ru/front_new/msp/store_step.do");

    public static void main(String[] args) throws Exception {
        new MainController().post("0321695310122081100012575");
    }

    @RestController
    public class Controller {
        @ResponseBody
        @RequestMapping(
                value = "/get",
                method = RequestMethod.GET,
                produces="application/json"
        )
        public ResponseEntity<?> get(@RequestParam String ordinance) {
            BodyCode bc = post(ordinance);
            String res = bc.body;
            System.out.println(res);

            return ResponseEntity.status(bc.code).body(res);
        }
    }

    /**
     *
     * @param ordinance
     * @return
     */
    BodyCode post(String ordinance){
        BodyCode bodyCode = new BodyCode();
        FR fr;
        String session;
        SecondRest sr;
        try {
            ResponseEntity<FR> res = webClientFirst.post()
                    .uri(uriBuilder -> uriBuilder
                        .queryParam("channel","17")
                        .queryParam("paidservice_id","5711")
                        .queryParam("partner_id","80000246")
                        .build())
                    .retrieve()
                    .toEntity(FR.class)
                    .block();

            fr = res.getBody();
            System.out.println(res.getBody());
            session = res.getHeaders().get("X-A3-Session-Token").toString().replace("[","").replace("]","").trim();
            System.out.println(session);


            FR finalFr = fr;
            ResponseEntity<SecondRest> res2 = webClientSecond.post()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("BillNumber$30$33",ordinance)
                            .queryParam("operation_id", finalFr.item.operation_id)
                            .build())
                    .cookie("SESSION", session)
                    .retrieve()
                    .toEntity(SecondRest.class)
                    .block();
            System.out.println(res2);
            sr = res2.getBody();
            bodyCode.code = res2.getStatusCodeValue();

            ObjectMapper mapper = new ObjectMapper();
            bodyCode.body = mapper.writeValueAsString(sr);
        } catch(WebClientResponseException e){
            //ошибки при запросе json'a из a-3
            System.out.println(e.getRawStatusCode());
            bodyCode.code = e.getRawStatusCode();
        } catch (JsonProcessingException e) {
            //ошибка преобразования из pojo в json
            bodyCode.code = 500;
        } catch (NullPointerException e) {
            //ошибка получения json'a с a-3
            bodyCode.code = 500;
        }

        return bodyCode;
    }

    class BodyCode {
        String body;
        int code;

        public BodyCode() {
        }
    }
}
