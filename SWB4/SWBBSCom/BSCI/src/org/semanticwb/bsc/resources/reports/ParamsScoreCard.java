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

    private String titleDoc;
    private String perspectiveHeader;
    private String themeHeader;
    private String objHeader;
    private String indicHeader;
    private String initHeader;
    private String perspectiveTitle;
    private String themeTitle;
    private String objectiveTitle;
    private String indicatorTitle;
    private String initiativeTitle;
    private String scoreCard;
    private String periodTitle;
    private String msg;
    private InputStream logo;

    public ParamsScoreCard(String titleDoc, String perspectiveHeader, String themeHeader,
            String objHeader, String indicHeader, String initHeader, String perspectiveTitle, String themeTitle,
            String objectiveTitle, String indicatorTitle, String initiativeTitle, String scoreCard,
            String periodTitle, String msg, InputStream logo) {

        this.titleDoc = titleDoc;
        this.perspectiveHeader=perspectiveHeader;
        this.themeHeader=themeHeader;
        this.objHeader=objHeader;
        this.indicHeader=indicHeader;
        this.initHeader=initHeader;
        this.perspectiveTitle = perspectiveTitle;
        this.themeTitle = themeTitle;
        this.objectiveTitle = objectiveTitle;
        this.indicatorTitle = indicatorTitle;
        this.initiativeTitle = initiativeTitle;
        this.scoreCard = scoreCard;
        this.periodTitle = periodTitle;
        this.msg = msg;
        this.logo = logo;
    }

    public String getTitleDoc() {
        return titleDoc;
    }

    public void setTitleDoc(String titleDoc) {
        this.titleDoc = titleDoc;
    }

    public String getPerspectiveHeader() {
        return perspectiveHeader;
    }

    public void setPerspectiveHeader(String perspectiveHeader) {
        this.perspectiveHeader = perspectiveHeader;
    }

    public String getThemeHeader() {
        return themeHeader;
    }

    public void setThemeHeader(String themeHeader) {
        this.themeHeader = themeHeader;
    }

    public String getObjHeader() {
        return objHeader;
    }

    public void setObjHeader(String objHeader) {
        this.objHeader = objHeader;
    }

    public String getIndicHeader() {
        return indicHeader;
    }

    public void setIndicHeader(String indicHeader) {
        this.indicHeader = indicHeader;
    }

    public String getInitHeader() {
        return initHeader;
    }

    public void setInitHeader(String initHeader) {
        this.initHeader = initHeader;
    }

    
    public String getPerspectiveTitle() {
        return perspectiveTitle;
    }

    public void setPerspectiveTitle(String perspectiveTitle) {
        this.perspectiveTitle = perspectiveTitle;
    }

    public String getThemeTitle() {
        return themeTitle;
    }

    public void setThemeTitle(String themeTitle) {
        this.themeTitle = themeTitle;
    }

    public String getObjectiveTitle() {
        return objectiveTitle;
    }

    public void setObjectiveTitle(String objectiveTitle) {
        this.objectiveTitle = objectiveTitle;
    }

    public String getIndicatorTitle() {
        return indicatorTitle;
    }

    public void setIndicatorTitle(String indicatorTitle) {
        this.indicatorTitle = indicatorTitle;
    }

    public String getInitiativeTitle() {
        return initiativeTitle;
    }

    public void setInitiativeTitle(String initiativeTitle) {
        this.initiativeTitle = initiativeTitle;
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
