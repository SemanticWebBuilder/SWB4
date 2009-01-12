/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.admin.resources.reports.beans;

import java.util.HashMap;

/**
 *
 * @author Administrador
 */
public class WBAFilterReportBean {
    private int dayI;
    private int monthI;
    private int yearI;
    private int dayF;
    private int monthF;
    private int yearF;
    private String site;
    private String idaux;
    private int type;       
    
    private HashMap userTypes;

    
    public WBAFilterReportBean(){
        dayI   = -1;
        monthI = -1;
        yearI  = -1;
    }

    public int getDayI() {
        return dayI;
    }

    public void setDayI(int dayI) {
        this.dayI = dayI;
    }
    
    public String getIdaux() {
        return idaux;
    }

    public void setIdaux(String idaux) {
        this.idaux = idaux;
    }

    public int getMonthI() {
        return monthI;
    }

    public void setMonthI(int monthI) {
        this.monthI = monthI;
    }

    public int getYearI() {
        return yearI;
    }

    public void setYearI(int yearI) {
        this.yearI = yearI;
    }

    public int getDayF() {
        return dayF;
    }

    public void setDayF(int dayF) {
        this.dayF = dayF;
    }

    public int getMonthF() {
        return monthF;
    }

    public void setMonthF(int monthF) {
        this.monthF = monthF;
    }

    public int getYearF() {
        return yearF;
    }

    public void setYearF(int yearF) {
        this.yearF = yearF;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    
    public HashMap getUserTypes() {
        return userTypes;
    }
    
    public void setUserTypes(HashMap userTypes) {
        this.userTypes = userTypes;
    }
}
