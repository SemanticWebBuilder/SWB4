/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.resources.reports;



// TODO: Auto-generated Javadoc
/**
 * The Enum JasperTemplate.
 */
public enum JasperTemplates {
    
    /** The RE s_ appeared. */
    SCORECARD("/org/semanticwb/bsc/resources/reports/templates/SC.jasper"),
    LOGO("/org/semanticwb/bsc/resources/reports/templates/swb-logo.jpg");
    //EMPLEADOS("C:\\Users\\ana.garcias\\Documents\\Proyectos5\\SWBBSCom\\BSCI\\build\\classes\\org\\semanticwb\\bsc\\resources\\reports\\templates\\SC.jasper");
   
    /** The jasper file name. */
    private final String jasperFileName;
    
    /**
     * Instantiates a new jasper template.
     * 
     * @param jasperFileName the jasper file name
     */
    JasperTemplates(String jasperFileName){
        this.jasperFileName = jasperFileName;
    }   
    
    /**
     * Gets the template path.
     * 
     * @return the template path
     */
    public String getTemplatePath() {
        return jasperFileName;
    }
}
