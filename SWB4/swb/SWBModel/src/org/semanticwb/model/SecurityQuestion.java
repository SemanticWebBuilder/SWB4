package org.semanticwb.model;

import java.util.Iterator;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


public class SecurityQuestion extends org.semanticwb.model.base.SecurityQuestionBase 
{
    public SecurityQuestion(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void process(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type, String mode, String lang)
    {
        super.process(request, obj, prop, type, mode, lang);
    }

    @Override
    public String renderElement(SemanticObject obj, SemanticProperty prop, String type, String mode, String lang)
    {
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
//        System.out.println("name:"+name);
//        System.out.println("label:"+label);
//        System.out.println("sobj:"+sobj);
//        System.out.println("m_obj:"+obj.getModel().getName());
//        System.out.println("prop:"+prop);
//        System.out.println("DC:"+prop.getURI());
        if(sobj!=null)
        {
            DisplayProperty dobj=new DisplayProperty(sobj);
            pmsg=dobj.getPromptMessage();
            imsg=dobj.getInvalidMessage();
            selectValues= SWBContext.getUserRepository(obj.getModel().getName()).getUserRepSecurityQuestionList();
                    //dobj.getSelectValues(lang); //TODO:
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
                ret+="<option value=\"\"></option>";
                SemanticClass cls=prop.getRangeClass();
                Iterator<SemanticObject> it=null;

                    if(!obj.isVirtual())it=SWBComparator.sortSermanticObjects(obj.getModel().listInstancesOfClass(cls),lang);

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
                ret+="<option value=\"\"></option>";
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
