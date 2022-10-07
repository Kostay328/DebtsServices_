package ru.ining.debts.sts.merge.mergests.pojo;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

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
        "css_class",
        "value",
        "default",
        "radio"
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
    @JsonProperty("css_class")
    private String cssClass;
    @JsonProperty("value")
    private String value;
    @JsonProperty("default")
    private String _default;
    @JsonProperty("radio")
    private Radio radio;
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

    @JsonProperty("css_class")
    public String getCssClass() {
        return cssClass;
    }

    @JsonProperty("css_class")
    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }

    @JsonProperty("default")
    public String getDefault() {
        return _default;
    }

    @JsonProperty("default")
    public void setDefault(String _default) {
        this._default = _default;
    }

    @JsonProperty("radio")
    public Radio getRadio() {
        return radio;
    }

    @JsonProperty("radio")
    public void setRadio(Radio radio) {
        this.radio = radio;
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
