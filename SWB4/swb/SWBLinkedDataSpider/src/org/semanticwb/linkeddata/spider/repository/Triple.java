/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.linkeddata.spider.repository;

/**
 *
 * @author karen.najera
 */
public class Triple {

    public String suj;
    public String pred;
    public String obj;
    public boolean isOnDatabase;
    public boolean isOnPage;

    public Triple(String suj, String pred, String obj, boolean exist, boolean toKeep) {
        this.suj = suj;
        this.pred = pred;
        this.obj = obj;
        this.isOnDatabase = exist;
        this.isOnPage = toKeep;
    }

    @Override
    public String toString() {
        return "<" + suj + "> <" + pred + "> <" + obj + ">" + " (" + isOnDatabase + ")" + " (" + isOnPage + ")";
    }
}