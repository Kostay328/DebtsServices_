package ru.ining.debts.sts.merge.mergests.pojo;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "description",
        "css_class",
        "next_template",
        "div",
        "parameters"
})
@Generated("jsonschema2pojo")
public class Template {

    @JsonProperty("description")
    private String description;
    @JsonProperty("css_class")
    private String cssClass;
    @JsonProperty("next_template")
    private List<NextTemplate> nextTemplate = null;
    @JsonProperty("div")
    private List<Div> div = null;
    @JsonProperty("parameters")
    private Parameters parameters;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("css_class")
    public String getCssClass() {
        return cssClass;
    }

    @JsonProperty("css_class")
    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    @JsonProperty("next_template")
    public List<NextTemplate> getNextTemplate() {
        return nextTemplate;
    }

    @JsonProperty("next_template")
    public void setNextTemplate(List<NextTemplate> nextTemplate) {
        this.nextTemplate = nextTemplate;
    }

    @JsonProperty("div")
    public List<Div> getDiv() {
        return div;
    }

    @JsonProperty("div")
    public void setDiv(List<Div> div) {
        this.div = div;
    }

    @JsonProperty("parameters")
    public Parameters getParameters() {
        return parameters;
    }

    @JsonProperty("parameters")
    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
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
