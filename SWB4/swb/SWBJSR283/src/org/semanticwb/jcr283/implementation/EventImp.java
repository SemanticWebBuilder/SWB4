/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.jcr283.implementation;

import java.util.Map;
import javax.jcr.RepositoryException;
import javax.jcr.observation.Event;

/**
 *
 * @author victor.lorenzana
 */
public class EventImp implements Event {

    private final int type;
    private final String path;
    private final String userid;
    private final String id;
    private final String userData;
    public EventImp(int type,String path,String userid,String id,String userData)
    {
        this.type=type;
        this.path=path;
        this.userid=userid;
        this.id=id;
        this.userData=userData;
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
        return userid;
    }

    public String getIdentifier() throws RepositoryException
    {
        return id;
    }

    public Map getInfo() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getUserData() throws RepositoryException
    {
        return userData;
    }

    public long getDate() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
