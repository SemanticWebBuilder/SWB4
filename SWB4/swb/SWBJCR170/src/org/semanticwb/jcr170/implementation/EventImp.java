/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.jcr170.implementation;

import javax.jcr.RepositoryException;
import javax.jcr.observation.Event;

/**
 *
 * @author victor.lorenzana
 */
public class EventImp implements Event {

    private int type;
    private String path;
    private String userID;
    public EventImp(int type,String path,String userID)
    {
        this.type=type;
        this.path=path;
    }
    public int getType()
    {
        return type;
    }

    public String getPath() throws RepositoryException
    {
        return path;
    }

    public String getUserID()
    {
        return userID;
    }

}
