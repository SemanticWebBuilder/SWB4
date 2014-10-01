/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.resources.reports;

import java.io.InputStream;

/**
 *
 * @author ana.garcias
 */
public class ParamsScoreCard {
    private String perspective;
    private String theme;
    private String objective;
    private String indicator;
    private String initiative;
    private String scoreCard;
    private String periodTitle;
    private String msg;
    private InputStream logo;
    
    public ParamsScoreCard(String perspective, String theme, String objective, String indicator, String initiative, String scoreCard, String periodTitle, String msg, InputStream logo){
        this.perspective = perspective;
        this.theme = theme;
        this.objective = objective;
        this.indicator = indicator;
        this.initiative = initiative;
        this.scoreCard = scoreCard;
        this.periodTitle = periodTitle;
        this.msg = msg;
        this.logo = logo;
    }

    public String getPerspective() {
        return perspective;
    }

    public void setPerspective(String perspective) {
        this.perspective = perspective;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getIndicator() {
        return indicator;
    }

    public void setIndicator(String indicator) {
        this.indicator = indicator;
    }

    public String getInitiative() {
        return initiative;
    }

    public void setInitiative(String initiative) {
        this.initiative = initiative;
    }
    

    public String getScoreCard() {
        return scoreCard;
    }

    public void setScoreCard(String scoreCard) {
        this.scoreCard = scoreCard;
    }

    public String getPeriodTitle() {
        return periodTitle;
    }

    public void setPeriodTitle(String periodTitle) {
        this.periodTitle = periodTitle;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public InputStream getLogo() {
        return logo;
    }

    public void setLogo(InputStream logo) {
        this.logo = logo;
    }
    
    
}
