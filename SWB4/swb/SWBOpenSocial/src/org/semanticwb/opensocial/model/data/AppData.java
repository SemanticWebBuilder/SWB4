package org.semanticwb.opensocial.model.data;

import org.semanticwb.model.WebSite;


public class AppData extends org.semanticwb.opensocial.model.data.base.AppDataBase 
{
    public AppData(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    public static AppData getAppData(String key,Person person,WebSite site)
    {
        String hey_=key+"@"+person.getId();
        return AppData.ClassMgr.createAppData(hey_, site);
    }
    public static AppData createAppData(String key,Person person,WebSite site)
    {
        String key_=key+"@"+person.getId();
        return AppData.ClassMgr.createAppData(key_, site);
    }
}
