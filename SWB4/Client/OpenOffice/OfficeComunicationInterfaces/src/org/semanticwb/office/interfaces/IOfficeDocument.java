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
    public String getPath(int contentID);
    public void setTitle(int contentID,String title);
    public void setDescription(int contentID,String description);
    public void setPath(int contentID,String path);
    public void setActive(int contentID,boolean active);
    public boolean getActive(int contentID);
    public void delete(int contentID);
    public boolean exists(int contentId);
    void updateContent(int contentId);
}
