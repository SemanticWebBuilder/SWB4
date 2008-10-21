package org.semanticwb.portal;

import java.util.Iterator;
import org.semanticwb.model.DisplayObject;
import org.semanticwb.model.FormElement;
import org.semanticwb.model.GenericFormElement;
import org.semanticwb.platform.*;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class SWBFormMgr 
{
    public static final String MODE_VIEW="view";
    public static final String MODE_EDIT="edit";
    public static final String MODE_CREATE="create";
    public static final String TYPE_XHTML="xhtml";
    public static final String TYPE_IPHONE="iphone";
    
    private SemanticObject m_obj;
    private SemanticClass m_cls;
    private String m_mode="view";
    private String m_action="";
    private String m_method="GET";
    private String m_lang="es";
    private String m_type=TYPE_XHTML;
    
    public SWBFormMgr(SemanticObject obj, String mode)
    {
        this.m_obj=obj;
        this.m_mode=mode;
        this.m_cls=obj.getSemanticClass();
    }
    
    public String getAction() {
        return m_action;
    }

    public void setAction(String action) {
        this.m_action = action;
    }

    public String getMethod() {
        return m_method;
    }

    public void setMethod(String method) {
        this.m_method = method;
    }
    
    public String getLang() {
        return m_lang;
    }

    public void setLang(String lang) {
        this.m_lang = lang;
    }    
    
    public String getType() {
        return m_type;
    }

    public void setType(String type) {
        this.m_type = type;
    }      
    
    public String renderForm()
    {
        String ret="";
        if(m_type.equals(TYPE_XHTML))
        {
            ret=renderXHTMLForm();
        }else if(m_type.equals(TYPE_IPHONE))
        {
            ret=renderIphoneForm();
        }
        return ret;
    }
    
    public String renderXHTMLForm()
    {
        
        StringBuffer ret=new StringBuffer();
        ret.append("<form action=\""+m_action+"\" method=\""+m_method+"\">");
        ret.append("	<fieldset>");
        ret.append("		<legend>General</legend>");
        ret.append("		<ol>");
        Iterator<SemanticProperty> it=m_cls.listProperties();
        while(it.hasNext())
        {
            SemanticProperty prop=it.next();   
            String code=renderElement(prop);
            if(code!=null && code.length()>0)
            {
                ret.append("<li>");
                ret.append(code);
                ret.append("</li>");
            }
        }
        ret.append("		</ol>");
        ret.append("	</fieldset>");
        ret.append("</form>");
        return ret.toString();
    }    
    
    public String renderIphoneForm()
    {
        
        StringBuffer ret=new StringBuffer();
        ret.append("<form action=\""+m_action+"\" method=\""+m_method+"\">");
        ret.append("	<fieldset>");
        ret.append("		<legend>General</legend>");
        ret.append("		<ol>");
        Iterator<SemanticProperty> it=m_cls.listProperties();
        while(it.hasNext())
        {
            SemanticProperty prop=it.next();   
            String code=renderElement(prop);
            if(code!=null && code.length()>0)
            {
                ret.append("<li>");
                ret.append(code);
                ret.append("</li>");
            }
        }
        ret.append("		</ol>");
        ret.append("	</fieldset>");
        ret.append("</form>");
        return ret.toString();
    }        
    

    
    public String renderElement(String propName)
    {
        //TODO
        return null;
    }
    
    public String renderElement(SemanticProperty prop)
    {
        String ret=null;
        SemanticObject obj=prop.getDisplayObject();
        FormElement ele=null;
        if(obj!=null)
        {
            DisplayObject disp=new DisplayObject(obj);
            if(m_mode.equals(MODE_VIEW))
            {
                ele=disp.getViewElement();
            }else if(m_mode.equals(MODE_EDIT))
            {
                ele=disp.getViewElement();
            }else if(m_mode.equals(MODE_CREATE))
            {
                ele=disp.getViewElement();
            }
        }
        if(ele==null)ele=new GenericFormElement();
        ret=ele.render(m_obj, prop, m_type, m_mode, m_lang);
        return ret;
    }
    
    
}
