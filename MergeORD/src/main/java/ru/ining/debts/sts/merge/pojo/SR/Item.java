
package ru.ining.debts.sts.merge.pojo.SR;

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
    "payment_recipient_id",
    "step",
    "session_id",
    "partner_ain",
    "total_sum",
    "ain",
    "template"
})
@Generated("jsonschema2pojo")
public class Item {

    @JsonProperty("payment_recipient_id")
    private String paymentRecipientId;
    @JsonProperty("step")
    private Integer step;
    @JsonProperty("session_id")
    private String sessionId;
    @JsonProperty("partner_ain")
    private Integer partnerAin;
    @JsonProperty("total_sum")
    private Double totalSum;
    @JsonProperty("ain")
    private Integer ain;
    @JsonProperty("template")
    private Template template;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("payment_recipient_id")
    public String getPaymentRecipientId() {
        return paymentRecipientId;
    }

    @JsonProperty("payment_recipient_id")
    public void setPaymentRecipientId(String paymentRecipientId) {
        this.paymentRecipientId = paymentRecipientId;
    }

    @JsonProperty("step")
    public Integer getStep() {
        return step;
    }

    @JsonProperty("step")
    public void setStep(Integer step) {
        this.step = step;
    }

    @JsonProperty("session_id")
    public String getSessionId() {
        return sessionId;
    }

    @JsonProperty("session_id")
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @JsonProperty("partner_ain")
    public Integer getPartnerAin() {
        return partnerAin;
    }

    @JsonProperty("partner_ain")
    public void setPartnerAin(Integer partnerAin) {
        this.partnerAin = partnerAin;
    }

    @JsonProperty("total_sum")
    public Double getTotalSum() {
        return totalSum;
    }

    @JsonProperty("total_sum")
    public void setTotalSum(Double totalSum) {
        this.totalSum = totalSum;
    }

    @JsonProperty("ain")
    public Integer getAin() {
        return ain;
    }

    @JsonProperty("ain")
    public void setAin(Integer ain) {
        this.ain = ain;
    }

    @JsonProperty("template")
    public Template getTemplate() {
        return template;
    }

    @JsonProperty("template")
    public void setTemplate(Template template) {
        this.template = template;
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
