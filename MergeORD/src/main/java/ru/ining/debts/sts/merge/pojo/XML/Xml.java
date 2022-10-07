package ru.ining.debts.sts.merge.pojo.XML;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Xml {
    public final static String sear = "sear";
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Parameter {
        @JacksonXmlProperty(namespace = sear,localName="name")
        public String name;
        @JacksonXmlProperty(namespace = sear,localName="value")
        public String value;
    }

    @JacksonXmlProperty(namespace = sear,localName="parameters")
    public List<Parameter> parameters;
}
