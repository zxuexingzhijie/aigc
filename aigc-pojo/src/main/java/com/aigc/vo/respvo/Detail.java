package com.aigc.vo.respvo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

// 定义代表details数组中每个对象的类
@Data
public class Detail {
    private int text_number;
    private String text;
    @JsonProperty("AI_probability")
    private double AI_probability;
    private double real_probability;
}