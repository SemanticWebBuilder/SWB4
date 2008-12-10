/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.repository;

import java.util.HashMap;

/**
 *
 * @author victor.lorenzana
 */
public interface OfficeManager {
    public String getPropertyTitleType();
    public String getCategoryType();
    public String getPropertyDescriptionType();
    public String getPropertyType();
    public String getPartType();    
    public HashMap<String, String> getContentTypes();
}
