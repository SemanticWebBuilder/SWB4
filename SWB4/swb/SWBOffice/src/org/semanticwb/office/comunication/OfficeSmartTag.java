/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.office.comunication;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import org.semanticwb.model.WebPage;
import org.semanticwb.office.interfaces.IOfficeSmartTag;
import org.semanticwb.office.interfaces.ObjecInfo;
import org.semanticwb.office.interfaces.PropertyObjectInfo;
import org.semanticwb.platform.SemanticLiteral;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.xmlrpc.XmlRpcObject;

/**
 *
 * @author victor.lorenzana
 */
public class OfficeSmartTag extends XmlRpcObject implements IOfficeSmartTag{

    private static final String[] preps={"a","ante","bajo","con","contra","de","desde","para","sin","sobre","tras"};
    public ObjecInfo[] search(String text) throws Exception
    {
        ArrayList<ObjecInfo> search=new ArrayList<ObjecInfo>();
        Iterator<SemanticObject> result=searchObject(text);
        while(result.hasNext())
        {
            SemanticObject obj=result.next();
            ObjecInfo info=new ObjecInfo();
            info.uri=obj.getURI();
            if(obj.getSemanticClass().getLabel("es")==null)
            {
                info.name=obj.getSemanticClass().getName()+":"+obj.getDisplayName();
            }
            else
            {
                info.name=obj.getSemanticClass().getLabel("es")+":"+obj.getDisplayName();
            }
            SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat dateTimeFormat=new SimpleDateFormat("dd/MM/yyyy HH:mm");
            ArrayList<PropertyObjectInfo> properties=new ArrayList<PropertyObjectInfo>();
            Iterator<SemanticProperty> props=obj.getSemanticClass().listProperties();
            while(props.hasNext())
            {
                SemanticProperty prop=props.next();
                if(prop.getDisplayProperty()!=null && prop.isDataTypeProperty())
                {
                    PropertyObjectInfo propInfo=new PropertyObjectInfo();
                    propInfo.uri=prop.getURI();
                    propInfo.title=prop.getLabel();
                    
                    SemanticLiteral literal=obj.getLiteralProperty(prop);
                    if(literal!=null)
                    {
                        if(prop.isDate())
                        {
                            propInfo.value=dateFormat.format(literal.getDate());
                        }
                        else if(prop.isDateTime())
                        {
                            propInfo.value=dateTimeFormat.format(new java.util.Date(literal.getDateTime().getTime()));
                        }
                        else if(prop.isBoolean())
                        {
                            propInfo.value=literal.getBoolean()?"Sí":"No";
                        }
                        else if(prop.isBinary())
                        {
                            
                        }
                        else
                        {
                            propInfo.value=literal.getString();
                        }
                    }
                    properties.add(propInfo);
                }
            }
            info.properties=properties.toArray(new PropertyObjectInfo[properties.size()]);
            search.add(info);
        }
        return search.toArray(new ObjecInfo[search.size()]);
    }
    
    private Iterator<SemanticObject> searchObject(String text)
    {
        ArrayList<SemanticObject> searchObject=new ArrayList<SemanticObject>();
        Iterator<org.semanticwb.model.User> users=org.semanticwb.model.User.ClassMgr.listUsers();
        while(users.hasNext())
        {
            org.semanticwb.model.User ouser=users.next();
            searchObject.add(ouser.getSemanticObject());
        }
        Iterator<WebPage> pages=WebPage.ClassMgr.listWebPages();
        while(pages.hasNext())
        {
            WebPage page=pages.next();
            searchObject.add(page.getSemanticObject());
        }
        return searchObject.iterator();
    }


    public boolean isSmartTag(String text) throws Exception
    {
        return true;
    }
    private boolean isRelevantWord(String token)
    {
        boolean isRelevantWord=true;
        for(String prep : preps)
        {
            if(prep.equalsIgnoreCase(token))
            {
                return false;
            }
        }
        return isRelevantWord;
    }
    private String preProccessToken(String token)
    {
       return token;
    }

    public String[] getTokens(String text) throws Exception
    {
        ArrayList<String> getTokens=new ArrayList<String>();
        StringTokenizer st=new StringTokenizer(text," ");
        while(st.hasMoreTokens())
        {
            String token=st.nextToken();
            token=preProccessToken(token);

            if(!isRelevantWord(token))
            {
                getTokens.add(token);
            }
        }
        return getTokens.toArray(new String[getTokens.size()]);
    }

}
