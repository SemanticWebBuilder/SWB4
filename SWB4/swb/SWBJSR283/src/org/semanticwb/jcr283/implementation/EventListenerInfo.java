/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.jcr283.implementation;

/**
 *
 * @author victor.lorenzana
 */
public class EventListenerInfo {

    private final int eventTypes;
    private final String absPath;
    private final boolean isDeep;
    private final String[] uuid;
    private final String[] nodeTypeName;
    private final boolean noLocal;
    public EventListenerInfo(int eventTypes, String absPath, boolean isDeep, String[] uuid, String[] nodeTypeName, boolean noLocal)
    {
        this.eventTypes=eventTypes;
        this.absPath=absPath;
        this.isDeep=isDeep;
        this.uuid=uuid;
        this.nodeTypeName=nodeTypeName;
        this.noLocal=noLocal;
    }
    public int getEventTypes()
    {
        return eventTypes;
    }
    public String getAbsPath()
    {
        return absPath;
    }
    public boolean isDeep()
    {
        return isDeep;
    }
    public String[] getUuid()
    {
        return uuid;
    }
    public String[] getNodeTypeName()
    {
        return nodeTypeName;
    }
    public boolean getNoLocal()
    {
        return noLocal;
    }
}
