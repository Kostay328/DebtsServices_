package ru.ining.debts.sts.get.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Accrual {
    private String uin;
/*        private String account;
        private Integer sum;
        private String date;
        private String description;
        private String type_id;
        private Integer left_sum;
        private List<MobileField> mobile_fields;
        private List<Object> old_payments;
        private BankRequisite bank_requisite;
        private AdditionalFields additional_fields;
        private Integer total_sum;
        private Status status;*/

    public Accrual() {
    }

    public String getUin() {
        return uin;
    }

    public void setUin(String uin) {
        this.uin = uin;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Status {
        private String id;
        private String name;

        public Status() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class MobileField {
        private String field_name;
        private String field_value;

        public MobileField() {
        }

        public String getField_name() {
            return field_name;
        }

        public void setField_name(String field_name) {
            this.field_name = field_name;
        }

        public String getField_value() {
            return field_value;
        }

        public void setField_value(String field_value) {
            this.field_value = field_value;
        }
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class BankRequisite {
        private String bank_name;
        private String bik;
        private String receiver_name;
        private String rs;
        private String inn;
        private String kpp;
        private String oktmo;
        private String ks;

        public BankRequisite() {
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public String getBik() {
            return bik;
        }

        public void setBik(String bik) {
            this.bik = bik;
        }

        public String getReceiver_name() {
            return receiver_name;
        }

        public void setReceiver_name(String receiver_name) {
            this.receiver_name = receiver_name;
        }

        public String getRs() {
            return rs;
        }

        public void setRs(String rs) {
            this.rs = rs;
        }

        public String getInn() {
            return inn;
        }

        public void setInn(String inn) {
            this.inn = inn;
        }

        public String getKpp() {
            return kpp;
        }

        public void setKpp(String kpp) {
            this.kpp = kpp;
        }

        public String getOktmo() {
            return oktmo;
        }

        public void setOktmo(String oktmo) {
            this.oktmo = oktmo;
        }

        public String getKs() {
            return ks;
        }

        public void setKs(String ks) {
            this.ks = ks;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class AdditionalFields {
        private String koap_code;
        private String violation_place;
        private String violation_date;
        private String division_code;
        private String division_name;
        private String violation_coords;
        private String kbk;
        private Boolean discount_enabled;
        private Boolean images_url;
        private Object fine_status;
        private String pay_until;
        private Boolean photo_request_allowed;

        public AdditionalFields() {
        }

        public String getKoap_code() {
            return koap_code;
        }

        public void setKoap_code(String koap_code) {
            this.koap_code = koap_code;
        }

        public String getViolation_place() {
            return violation_place;
        }

        public void setViolation_place(String violation_place) {
            this.violation_place = violation_place;
        }

        public String getViolation_date() {
            return violation_date;
        }

        public void setViolation_date(String violation_date) {
            this.violation_date = violation_date;
        }

        public String getDivision_code() {
            return division_code;
        }

        public void setDivision_code(String division_code) {
            this.division_code = division_code;
        }

        public String getDivision_name() {
            return division_name;
        }

        public void setDivision_name(String division_name) {
            this.division_name = division_name;
        }

        public String getViolation_coords() {
            return violation_coords;
        }

        public void setViolation_coords(String violation_coords) {
            this.violation_coords = violation_coords;
        }

        public String getKbk() {
            return kbk;
        }

        public void setKbk(String kbk) {
            this.kbk = kbk;
        }

        public Boolean getDiscount_enabled() {
            return discount_enabled;
        }

        public void setDiscount_enabled(Boolean discount_enabled) {
            this.discount_enabled = discount_enabled;
        }

        public Boolean getImages_url() {
            return images_url;
        }

        public void setImages_url(Boolean images_url) {
            this.images_url = images_url;
        }

        public Object getFine_status() {
            return fine_status;
        }

        public void setFine_status(Object fine_status) {
            this.fine_status = fine_status;
        }

        public String getPay_until() {
            return pay_until;
        }

        public void setPay_until(String pay_until) {
            this.pay_until = pay_until;
        }

        public Boolean getPhoto_request_allowed() {
            return photo_request_allowed;
        }

        public void setPhoto_request_allowed(Boolean photo_request_allowed) {
            this.photo_request_allowed = photo_request_allowed;
        }
    }
}
