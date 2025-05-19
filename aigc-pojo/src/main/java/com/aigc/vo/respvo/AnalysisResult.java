package com.aigc.vo.respvo;

import java.util.List;

// 定义代表整个JSON数据的类
public class AnalysisResult {
    private double whole_ai_prob;
    private double whole_real_prob;
    private List<Detail> details;

    // getters and setters
    public double getWhole_ai_prob() {
        return whole_ai_prob;
    }

    public void setWhole_ai_prob(double whole_ai_prob) {
        this.whole_ai_prob = whole_ai_prob;
    }

    public double getWhole_real_prob() {
        return whole_real_prob;
    }

    public void setWhole_real_prob(double whole_real_prob) {
        this.whole_real_prob = whole_real_prob;
    }

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }
}

