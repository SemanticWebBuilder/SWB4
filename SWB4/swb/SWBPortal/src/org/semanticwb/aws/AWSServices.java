/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.aws;

/**
 *
 * @author serch
 */
public interface AWSServices {
    AWSServices getServiceInterface();
    void setCredentials(String access, String secret);
    
}
