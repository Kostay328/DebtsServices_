package ru.ining.debts.sts.debtssts.pojo;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "docnum",
        "docname",
        "description",
        "date",
        "sum",
        "recipient"
})
@Generated("jsonschema2pojo")
public class AddInfo {

    @JsonProperty("docnum")
    private String docnum;
    @JsonProperty("docname")
    private String docname;
    @JsonProperty("description")
    private Object description;
    @JsonProperty("date")
    private String date;
    @JsonProperty("sum")
    private String sum;
    @JsonProperty("recipient")
    private Object recipient;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("docnum")
    public String getDocnum() {
        return docnum;
    }

    @JsonProperty("docnum")
    public void setDocnum(String docnum) {
        this.docnum = docnum;
    }

    @JsonProperty("docname")
    public String getDocname() {
        return docname;
    }

    @JsonProperty("docname")
    public void setDocname(String docname) {
        this.docname = docname;
    }

    @JsonProperty("description")
    public Object getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(Object description) {
        this.description = description;
    }

    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    @JsonProperty("date")
    public void setDate(String date) {
        this.date = date;
    }

    @JsonProperty("sum")
    public String getSum() {
        return sum;
    }

    @JsonProperty("sum")
    public void setSum(String sum) {
        this.sum = sum;
    }

    @JsonProperty("recipient")
    public Object getRecipient() {
        return recipient;
    }

    @JsonProperty("recipient")
    public void setRecipient(Object recipient) {
        this.recipient = recipient;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
