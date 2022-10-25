package ru.ining.debts.sts.get.pojo;

import java.util.ArrayList;
import java.util.List;

public class Body {
    private String document_type;
    private String document_value;
    private AdditionalFields additional_fields;

    private class AdditionalFields{
        public AdditionalFields() {
        }
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

    public AdditionalFields getAdditional_fields() {
        return additional_fields;
    }

    public void setAdditional_fields(AdditionalFields additional_fields) {
        this.additional_fields = additional_fields;
    }

    public Body(String document_value) {
        this.document_type = "ctc";
        this.document_value = document_value;
        this.additional_fields = new AdditionalFields();
    }
}
