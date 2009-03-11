/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.repository;

import java.util.Collection;
import java.util.HashMap;
import org.semanticwb.office.interfaces.PropertyInfo;
import org.semanticwb.office.interfaces.RepositoryInfo;

/**
 *
 * @author victor.lorenzana
 */
public interface OfficeManager
{

    public String getPropertyTitleType();

    public String getCategoryType();

    public String getPropertyDescriptionType();

    public String getPropertyType();

    public String getUserType();

    public HashMap<String, String> getContentTypes();

    public String getPropertyFileType();

    public Collection<RepositoryInfo> getWorkspaces();

    public PropertyInfo[] getContentProperties(String type);

    public void validateContentValues(PropertyInfo[] properties, Object[] values, String type) throws Exception;
}
