package org.semanticwb.portal;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.model.DisplayProperty;
import org.semanticwb.model.FormElement;
import org.semanticwb.model.FormView;
import org.semanticwb.model.GenericFormElement;
import org.semanticwb.model.SWBContext;
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
    private FormView m_fview=null;
    private Map<SemanticProperty, String> m_propmap=null;
    private String m_mode="view";
    private String m_action="";
    private String m_method="POST";
    private String m_lang="es";
    private String m_type=TYPE_XHTML;
    
    private HashMap<String, TreeSet> groups=null;
    
    public SWBFormMgr(SemanticObject obj, String frmview, String mode)
    {
        this.m_obj=obj;
        this.m_mode=mode;
        this.m_cls=obj.getSemanticClass();
        this.m_fview=SWBContext.getFormView(frmview);
        init();
    }
    
    public void init()
    {
        if(m_fview!=null)
        {
            m_propmap=m_fview.getPropertyMap(m_mode);
        }
        //System.out.println("m_fview:"+m_fview+" m_propmap:"+m_propmap);
        groups=new HashMap();
        Iterator<SemanticProperty> it=m_cls.listProperties();
        while(it.hasNext())
        {
            SemanticProperty prop=it.next();
            addProperty(prop);
        }        
    }
    
    public void addProperty(SemanticProperty prop)
    {
        SemanticObject obj=prop.getDisplayProperty();
        String grp=null;
        boolean hidden=false;
        if(obj!=null)
        {
            DisplayProperty disp=new DisplayProperty(obj);       
            grp=disp.getGroup();
            hidden=disp.isHidden();
        }
        if(grp==null)grp="General";
        TreeSet<SemanticProperty> props=groups.get(grp);
        if(props==null)
        {
            props=new TreeSet(new Comparator(){

                public int compare(Object o1, Object o2){
                    SemanticObject sobj1=((SemanticProperty)o1).getDisplayProperty();
                    SemanticObject sobj2=((SemanticProperty)o2).getDisplayProperty();
                    int v1=999999999;
                    int v2=999999999;
                    if(sobj1!=null)v1=new DisplayProperty(sobj1).getIndex();
                    if(sobj2!=null)v2=new DisplayProperty(sobj2).getIndex();
                    return v1<v2?-1:1;
                }
            });
            groups.put(grp, props);
        }
        if(m_fview!=null)           //valida si la propiedad se encuentra dentro de la vista
        {
            if(m_propmap.containsKey(prop))
            {
                props.add(prop);
            }
        }else
        {
            if(!hidden)props.add(prop);
        }
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
    
    public void processForm(HttpServletRequest request, HttpServletResponse response)
    {
        String smode=request.getParameter("smode");
        if(smode!=null)
        {
            Iterator<SemanticProperty> it=m_cls.listProperties();
            while(it.hasNext())
            {
                SemanticProperty prop=it.next();   
                processElement(request, prop, smode);
            }
        }
    }
    
    public String renderXHTMLForm()
    {        
        String frmname=m_obj.getURI()+"/form";
        StringBuffer ret=new StringBuffer();
        ret.append("<form id=\""+frmname+"\" dojoType=\"dijit.form.Form\" class=\"swbform\" action=\""+m_action+"\" method=\""+m_method+"\">");
        ret.append("    <input type=\"hidden\" name=\"suri\" value=\""+m_obj.getURI()+"\">");
        ret.append("    <input type=\"hidden\" name=\"smode\" value=\""+m_mode+"\">");
        ret.append("	<fieldset>");
        ret.append("	    <ol>");
        ret.append("            <li>");
        ret.append("                <label>Identificador &nbsp;</label> <span>"+m_obj.getId()+"</span>");
        ret.append("            </li>");
        ret.append("	    </ol>");
        ret.append("	</fieldset>");
        
        Iterator<String> itgp=groups.keySet().iterator();
        while(itgp.hasNext())
        {
            String group=itgp.next();
            ret.append("	<fieldset>");
            ret.append("	    <legend>"+group+"</legend>");
            ret.append("	    <ol>");
//            ret.append("	    <table border=\"1\" width=\"100%\">");
            Iterator<SemanticProperty> it=groups.get(group).iterator();
            while(it.hasNext())
            {
                SemanticProperty prop=it.next();   
                String code=null;
                if(m_propmap!=null)
                {
                    code=renderElement(prop,m_propmap.get(prop));
                }else
                {
                    code=renderElement(prop,m_mode);
                }
                if(code!=null && code.length()>0)
                {
                    ret.append("<li>");
//                    ret.append("<tr><td>");
                    ret.append(code);
//                    ret.append("</td></tr>");
                    ret.append("</li>");
                }
            }
            //ret.append("	    </table>");
            ret.append("	    </ol>");
            ret.append("	</fieldset>");
        }
        ret.append("    <p><input type=\"button\" onclick=\"submitForm('"+frmname+"');\" value=\"Actualizar\"/></p>");
        ret.append("</form>");
        return ret.toString();
    }    
    
    public String renderIphoneForm()
    {
        //TODO
        return "";
    }        
    

    
    public String renderElement(String propName)
    {
        //TODO
        return null;
    }
    
    private FormElement getFormElement(SemanticProperty prop)
    {
        SemanticObject obj=prop.getDisplayProperty();
        FormElement ele=null;
        if(obj!=null)
        {
            DisplayProperty disp=new DisplayProperty(obj);
            ele=disp.getFormElementInstance();
        }
        if(ele==null)ele=new GenericFormElement();
        return ele;
    }
    
    public void processElement(HttpServletRequest request, SemanticProperty prop, String mode)
    {
        FormElement ele=getFormElement(prop);
        ele.process(request, m_obj, prop, m_type, mode, m_lang);
    }
    
    public String renderElement(SemanticProperty prop, String mode)
    {
        String ret=null;
        //System.out.println("prop:"+prop+" mode:"+mode);
        FormElement ele=getFormElement(prop);
        ret=ele.render(m_obj, prop, m_type, mode, m_lang);
        return ret;
    }
    
    
}
