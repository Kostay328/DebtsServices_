package ru.ining.debts.sts.get.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class SimplePaysJSON {
    private String document_type;
    private String document_value;
    private List<Accrual> accruals;

    public SimplePaysJSON() {
    }

    public String getDocument_type() {
        return document_type;
    }

    public void setDocument_type(String document_type) {
        this.document_type = document_type;
    }

    public String getDocument_value() {
        return document_value;
    }

    public void setDocument_value(String document_value) {
        this.document_value = document_value;
    }

    public List<Accrual> getAccruals() {
        return accruals;
    }

    public void setAccruals(List<Accrual> accruals) {
        this.accruals = accruals;
    }
}



/*
[
    {
        "document_type": "ctc",
        "document_value": "9929374239",
        "owner_name": null,
        "additional_fields": [],
        "accruals": [
            {
                "uin": "18810577220946654210",
                "account": "18810577220946654210",
                "sum": 5000,
                "date": "2022-09-15 20:59:59",
                "description": "Оплата штрафа по постановлению 18810577220946654210 от 15.09.2022",
                "type_id": "gibdd",
                "left_sum": 5000,
                "mobile_fields": [
                    {
                        "field_name": "Постановление (УИН)",
                        "field_value": "18810577220946654210"
                    },
                    {
                        "field_name": "Дата протокола",
                        "field_value": "15.09.2022"
                    },
                    {
                        "field_name": "Статья",
                        "field_value": "КоАП 12.12.3 - Повторный проезд на запрещ.сигнал светофора (регулировщика)"
                    }
                ],
                "old_payments": [],
                "bank_requisite": {
                    "bank_name": "ГУ Банка России по ЦФО//УФК по г.Москве",
                    "bik": "004525988",
                    "receiver_name": "УФК по г. Москве (Управление ГИБДД ГУ МВД России по г.Москве)",
                    "rs": "03100643000000017300",
                    "inn": "7707089101",
                    "kpp": "770731005",
                    "oktmo": "45379000",
                    "ks": "40102810545370000003"
                },
                "additional_fields": {
                    "koap_code": "12.12.3 - Повторный проезд на запрещ.сигнал светофора (регулировщика)",
                    "violation_place": "ПОЛЯРНАЯ, д.32, СЕВЕРНОЕ МЕДВЕДКОВО (СВАО) Р-Н, МОСКВА Г.",
                    "violation_date": "2022-09-02 13:29:11",
                    "division_code": "1145000",
                    "division_name": "УГИБДД ГУ МВД России по г. Москве",
                    "violation_coords": "NULL",
                    "kbk": "18811601121010001140",
                    "discount_enabled": false,
                    "images_url": false,
                    "fine_status": null,
                    "pay_until": "2022-11-24",
                    "photo_request_allowed": true
                },
                "total_sum": 5000,
                "status": {
                    "id": "new",
                    "name": "Новое начисление"
                }
            }
        ]
    }
]
*/