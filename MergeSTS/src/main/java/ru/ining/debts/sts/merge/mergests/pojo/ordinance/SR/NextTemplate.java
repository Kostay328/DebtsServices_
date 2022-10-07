
package ru.ining.debts.sts.merge.mergests.pojo.ordinance.SR;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "source",
    "template_id"
})
@Generated("jsonschema2pojo")
public class NextTemplate {

    @JsonProperty("source")
    private String source;
    @JsonProperty("template_id")
    private Integer templateId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("source")
    public String getSource() {
        return source;
    }

    @JsonProperty("source")
    public void setSource(String source) {
        this.source = source;
    }

    @JsonProperty("template_id")
    public Integer getTemplateId() {
        return templateId;
    }

    @JsonProperty("template_id")
    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
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
