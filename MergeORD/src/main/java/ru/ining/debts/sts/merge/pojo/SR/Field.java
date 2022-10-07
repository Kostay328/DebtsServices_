
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
    "order",
    "name",
    "type",
    "description",
    "extType",
    "visible",
    "editable",
    "mandatory",
    "value",
    "css_class",
    "validation",
    "prompt",
    "example"
})
@Generated("jsonschema2pojo")
public class Field {

    @JsonProperty("order")
    private Integer order;
    @JsonProperty("name")
    private String name;
    @JsonProperty("type")
    private String type;
    @JsonProperty("description")
    private String description;
    @JsonProperty("extType")
    private String extType;
    @JsonProperty("visible")
    private Boolean visible;
    @JsonProperty("editable")
    private Boolean editable;
    @JsonProperty("mandatory")
    private Boolean mandatory;
    @JsonProperty("value")
    private String value;
    @JsonProperty("css_class")
    private String cssClass;
    @JsonProperty("validation")
    private Validation validation;
    @JsonProperty("prompt")
    private String prompt;
    @JsonProperty("example")
    private String example;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("order")
    public Integer getOrder() {
        return order;
    }

    @JsonProperty("order")
    public void setOrder(Integer order) {
        this.order = order;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("extType")
    public String getExtType() {
        return extType;
    }

    @JsonProperty("extType")
    public void setExtType(String extType) {
        this.extType = extType;
    }

    @JsonProperty("visible")
    public Boolean getVisible() {
        return visible;
    }

    @JsonProperty("visible")
    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    @JsonProperty("editable")
    public Boolean getEditable() {
        return editable;
    }

    @JsonProperty("editable")
    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    @JsonProperty("mandatory")
    public Boolean getMandatory() {
        return mandatory;
    }

    @JsonProperty("mandatory")
    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }

    @JsonProperty("css_class")
    public String getCssClass() {
        return cssClass;
    }

    @JsonProperty("css_class")
    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    @JsonProperty("validation")
    public Validation getValidation() {
        return validation;
    }

    @JsonProperty("validation")
    public void setValidation(Validation validation) {
        this.validation = validation;
    }

    @JsonProperty("prompt")
    public String getPrompt() {
        return prompt;
    }

    @JsonProperty("prompt")
    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    @JsonProperty("example")
    public String getExample() {
        return example;
    }

    @JsonProperty("example")
    public void setExample(String example) {
        this.example = example;
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
