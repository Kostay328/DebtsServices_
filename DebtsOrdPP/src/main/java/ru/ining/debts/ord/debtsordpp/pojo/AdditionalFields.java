package ru.ining.debts.ord.debtsordpp.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
