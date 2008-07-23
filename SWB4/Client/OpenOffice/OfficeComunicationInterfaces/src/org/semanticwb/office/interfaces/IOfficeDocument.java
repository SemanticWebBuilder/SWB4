/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.office.interfaces;
/**
 *
 * @author victor.lorenzana
 */
public interface IOfficeDocument
{
    public int publish(String title,String description,String path);
    public String getPath();
    public void setTitle(String title);
    public void setDescription(String description);
    public void setPath(String path);
    public void setActive(boolean active);
    public boolean getActive();
    public void delete();
}
