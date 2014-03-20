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
    private String scoreCard;
    private InputStream logo;
    
    public ParamsScoreCard(String perspective, String theme, String objective, String indicator, String scoreCard, InputStream logo){
        this.perspective = perspective;
        this.theme = theme;
        this.objective = objective;
        this.indicator = indicator;
        this.scoreCard = scoreCard;
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

    public String getScoreCard() {
        return scoreCard;
    }

    public void setScoreCard(String scoreCard) {
        this.scoreCard = scoreCard;
    }

    public InputStream getLogo() {
        return logo;
    }

    public void setLogo(InputStream logo) {
        this.logo = logo;
    }
    
    
}
