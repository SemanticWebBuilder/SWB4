package org.semanticwb.model;

import java.util.Iterator;
import java.util.StringTokenizer;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.base.*;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

public class SelectOne extends SelectOneBase 
{
    public SelectOne(SemanticObject base)
    {
        super(base);
    }

    @Override
    public String renderElement(SemanticObject obj, SemanticProperty prop, String type, String mode, String lang) {
        if(obj==null)obj=new SemanticObject();
        String ret="";

        if(type.endsWith("iphone"))
        {
            ret=renderIphone(obj, prop, type, mode, lang);
        }else
        {
            ret=renderXHTML(obj, prop, type, mode, lang);
        }
        return ret;
    }

    public String renderIphone(SemanticObject obj, SemanticProperty prop, String type, String mode, String lang)
    {
        return "";
    }

    public String renderXHTML(SemanticObject obj, SemanticProperty prop, String type, String mode, String lang)
    {
        String ret="";
        String name=prop.getName();
        String label=prop.getDisplayName(lang);
        SemanticObject sobj=prop.getDisplayProperty();
        boolean required=prop.isRequired();
        String pmsg=null;
        String imsg=null;
        String selectValues=null;
        if(sobj!=null)
        {
            DisplayProperty dobj=new DisplayProperty(sobj);
            pmsg=dobj.getPromptMessage();
            imsg=dobj.getInvalidMessage();
            selectValues=dobj.getSelectValues(lang);
        }

        if(imsg==null)
        {
            if(required)
            {
                imsg=label+" es requerido.";
                if(lang.equals("en"))
                {
                    imsg=label+" is required.";
                }
            }
        }

        if(pmsg==null)
        {
            pmsg="Captura "+label+".";
            if(lang.equals("en"))
            {
                pmsg="Enter "+label+".";
            }
        }

        if(prop.isObjectProperty())
        {
            SemanticObject val=obj.getObjectProperty(prop);
            String uri="";
            String value="";
            if(val!=null)
            {
                uri=val.getURI();
                value=obj.getDisplayName(lang);
            }
            if(mode.equals("edit") || mode.equals("create") )
            {
                ret="<select name=\""+name+"\" dojoType=\"dijit.form.FilteringSelect\" autoComplete=\"true\" invalidMessage=\""+imsg+"\">";
                //onChange="dojo.byId('oc1').value=arguments[0]"
                if(isBlankSuport())ret+="<option value=\"\"></option>";
                SemanticClass cls=prop.getRangeClass();
                Iterator<SemanticObject> it=null;
                if(isGlobalScope())
                {
                    if(cls!=null)
                    {
                        it=SWBComparator.sortSermanticObjects(cls.listInstances(),lang);
                    }else
                    {
                        it=SWBComparator.sortSermanticObjects(SWBPlatform.getSemanticMgr().getVocabulary().listSemanticClassesAsSemanticObjects(),lang);
                    }
                }else
                {
                    if(!obj.isVirtual())it=SWBComparator.sortSermanticObjects(obj.getModel().listInstancesOfClass(cls),lang);
                }
                while(it.hasNext())
                {
                    SemanticObject sob=it.next();
                    ret+="<option value=\""+sob.getURI()+"\" ";
                    if(sob.getURI().equals(uri))ret+="selected";
                    ret+=">"+sob.getDisplayName(lang)+"</option>";
                }
                ret+="</select>";
            }else if(mode.equals("view"))
            {
                ret="<span _id=\""+name+"\" name=\""+name+"\">"+value+"</span>";
            }
        }else
        {
            if(selectValues!=null)
            {
                String value=obj.getProperty(prop);
                ret="<select name=\""+name+"\" dojoType=\"dijit.form.FilteringSelect\" autoComplete=\"true\" invalidMessage=\""+imsg+"\">";
                StringTokenizer st=new StringTokenizer(selectValues,"|");
                while(st.hasMoreTokens())
                {
                    String tok=st.nextToken();
                    int ind=tok.indexOf(':');
                    String id=tok;
                    String val=tok;
                    if(ind>0)
                    {
                        id=tok.substring(0,ind);
                        val=tok.substring(ind+1);
                    }
                    ret+="<option value=\""+id+"\" ";
                    if(id.equals(value))ret+="selected";
                    ret+=">"+val+"</option>";
                }
                ret+="</select>";
            }
        }

        return ret;
    }

}
