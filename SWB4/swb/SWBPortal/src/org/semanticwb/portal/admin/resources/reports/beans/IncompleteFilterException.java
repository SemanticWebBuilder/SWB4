/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.admin.resources.reports.beans;

/**
 *
 * @author Administrador
 */
public class IncompleteFilterException extends Exception {
    public IncompleteFilterException(){
    }
    
    public IncompleteFilterException(String msg){
        super(msg);
    }
}