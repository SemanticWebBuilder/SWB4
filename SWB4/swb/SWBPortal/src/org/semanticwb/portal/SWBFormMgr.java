package org.semanticwb.portal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.DisplayProperty;
import org.semanticwb.model.FormElement;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.FormView;
import org.semanticwb.model.GenericFormElement;
import org.semanticwb.model.PropertyGroup;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.SWBContext;
import org.semanticwb.platform.*;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class SWBFormMgr 
{
    private static Logger log = SWBUtils.getLogger(SWBFormMgr.class);

    public static final String MODE_VIEW="view";
    public static final String MODE_EDIT="edit";
    public static final String MODE_CREATE="create";
    public static final String TYPE_XHTML="xhtml";
    public static final String TYPE_DOJO="dojo";
    public static final String TYPE_IPHONE="iphone";

    public static final String PRM_ID="id";
    public static final String PRM_MODE="smode";
    public static final String PRM_REF="sref";
    public static final String PRM_URI="suri";
    public static final String PRM_CLS="scls";
    
    private SemanticObject m_obj;
    private SemanticObject m_ref;
    private SemanticClass m_cls;
    private FormView m_fview=null;
    private Map<SemanticProperty, String> m_propmap=null;
    private String m_mode=MODE_VIEW;
    private String m_action="";
    private String m_method="POST";
    private String m_onsubmit=null;
    private String m_lang="es";
    private String m_type=TYPE_XHTML;
    private PropertyGroup m_general=null;

    private HashMap<String, String> hidden=null;
    private ArrayList hiddenProps=null;
    private ArrayList<Object> buttons=null;

    private boolean filterRequired=true;
    
    private HashMap<PropertyGroup, TreeSet> groups=null;

    private boolean submitByAjax=false;
    
    public SWBFormMgr(SemanticObject obj, String frmview, String mode)
    {
        this.m_obj=obj;
        this.m_mode=mode;
        this.m_cls=obj.getSemanticClass();
        this.m_fview=SWBContext.getFormView(frmview);
        init();
    }

    /**
     * Modo creacion
     * @param cls
     * @param frmview
     */
    public SWBFormMgr(SemanticClass cls, SemanticObject ref, String frmview)
    {
        this.m_mode=MODE_CREATE;
        this.m_cls=cls;
        this.m_ref=ref;
        this.m_fview=SWBContext.getFormView(frmview);
        init();
    }

    
    public void init()
    {
        m_general=(PropertyGroup)SWBPlatform.getSemanticMgr().getOntology().getGenericObject(SemanticVocabulary.SWBXF_URI+"pg_General");
        //System.out.println("********************"+m_general+"***************************");
        if(m_fview!=null)
        {
            m_propmap=m_fview.getPropertyMap(m_mode);
        }
        //System.out.println("m_fview:"+m_fview+" m_propmap:"+m_propmap);
        groups=new HashMap();
        hidden=new HashMap();
        buttons=new ArrayList();
        Iterator<SemanticProperty> it=m_cls.listProperties();
        while(it.hasNext())
        {
            SemanticProperty prop=it.next();
            //System.out.println("add:"+prop);
            addProperty(prop);
        }
    }

    public HashMap<PropertyGroup, TreeSet> getGroups()
    {
        return groups;
    }
    
    public void addProperty(SemanticProperty prop)
    {
        //System.out.println("prop:"+prop);
        boolean createGroup=false;
        boolean addProp=false;
        SemanticObject obj=prop.getDisplayProperty();
        PropertyGroup grp=null;
        boolean hidden=false;
        boolean required=prop.isRequired();
        if(obj!=null)
        {
            DisplayProperty disp=new DisplayProperty(obj);       
            grp=disp.getGroup();
            hidden=disp.isHidden();
        }

        if(grp==null)grp=m_general;
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
            createGroup=true;
        }
        if(m_fview!=null)           //valida si la propiedad se encuentra dentro de la vista
        {
            if(m_propmap.containsKey(prop))
            {
                addProp=true;
            }
        }else
        {
            //System.out.println("filterRequired:"+filterRequired+" m_mode:"+m_mode);
            if(filterRequired && m_mode.equals(MODE_CREATE))      //solo se agregan las requeridas
            {
                if(required)
                {
                    addProp=true;
                }
            }else
            {
                addProp=true;
            }
        }
        if(addProp)
        {
//            if(!hidden)
//            {
            if(obj!=null)
            {
                props.add(prop);
            }
//            }else
//            {
//                hiddenProps.add(prop);
//            }
            //System.out.println("put:"+grp);
            if(createGroup)groups.put(grp, props);
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
    
    public String renderForm(HttpServletRequest request)
    {
        boolean DOJO=false;
        boolean IPHONE=false;
        boolean XHTML=false;
        if(m_type.equals(TYPE_XHTML))XHTML=true;
        if(m_type.equals(TYPE_DOJO))DOJO=true;
        if(m_type.equals(TYPE_IPHONE))IPHONE=true;

        StringBuffer ret=new StringBuffer();
        String frmname=getFormName();

        String onsubmit="";
        if(m_onsubmit!=null)onsubmit=" onsubmit=\""+m_onsubmit+"\"";
        //si es dojo por default se manda por ajax
        if(m_onsubmit==null && submitByAjax)onsubmit="  onsubmit=\"submitForm('"+frmname+"');return false;\"";

        if(DOJO)ret.append("<form id=\""+frmname+"\" dojoType=\"dijit.form.Form\" class=\"swbform\" action=\""+m_action+"\""+onsubmit+" method=\""+m_method.toLowerCase()+"\">\n");
        else ret.append("<form id=\""+frmname+"\" class=\"swbform\" action=\""+m_action+"\""+onsubmit+" method=\""+m_method.toLowerCase()+"\">\n");

        if(m_obj!=null)ret.append("    <input type=\"hidden\" name=\""+PRM_URI+"\" value=\""+m_obj.getURI()+"\"/>\n");
        if(m_cls!=null)ret.append("    <input type=\"hidden\" name=\""+PRM_CLS+"\" value=\""+m_cls.getURI()+"\"/>\n");
        if(m_mode!=null)ret.append("    <input type=\"hidden\" name=\""+PRM_MODE+"\" value=\""+m_mode+"\"/>\n");
        if(m_ref!=null)ret.append("    <input type=\"hidden\" name=\""+PRM_REF+"\" value=\""+m_ref.getURI()+"\"/>\n");
        Iterator<Map.Entry<String,String>> hit=hidden.entrySet().iterator();
        while(hit.hasNext())
        {
            Map.Entry entry=hit.next();
            ret.append("    <input type=\"hidden\" name=\""+entry.getKey()+"\" value=\""+entry.getValue()+"\"/>\n");
        }

        if(!m_mode.equals(MODE_CREATE))
        {
            String sid="Identificador";
            if(m_lang.equals("en"))sid="Identifier";
            ret.append("	<fieldset>\n");
            ret.append("	    <table><tr><td width=\"200px\" align=\"right\">\n");
            ret.append("                <label>"+sid+" &nbsp;</label>\n");
            ret.append("        </td><td>\n");
            ret.append("                <span>"+m_obj.getId()+"</span>\n");
            ret.append("	    </td></tr></table>\n");
            ret.append("	</fieldset>\n");

            Iterator<PropertyGroup> itgp=SWBComparator.sortSortableObject(groups.keySet().iterator());
            while(itgp.hasNext())
            {
                PropertyGroup group=itgp.next();
                ret.append("	<fieldset>\n");
                ret.append("	    <legend>"+group.getSemanticObject().getDisplayName(m_lang)+"</legend>\n");
                ret.append("	    <table>\n");

                Iterator<SemanticProperty> it=groups.get(group).iterator();
                while(it.hasNext())
                {
                    SemanticProperty prop=it.next();
                    FormElement ele=getFormElement(prop);
                    renderProp(request, ret, prop,ele);
                }
                ret.append("	    </table>\n");
                ret.append("	</fieldset>\n");
            }

            ret.append("<fieldset><span align=\"center\">\n");
            //ret.append("<button dojoType='dijit.form.Button' type=\"submit\">Guardar</button>");
            Iterator it=buttons.iterator();
            while(it.hasNext())
            {
                Object aux=it.next();
                ret.append("    ");
                if(aux instanceof SWBFormButton)
                {
                    ret.append(((SWBFormButton)aux).renderButton(request, m_type, m_lang));
                }else
                {
                    ret.append(aux.toString());
                }
                ret.append("\n");
            }
            ret.append("</span></fieldset>\n");

        }else
        {
            ret.append("	<fieldset>\n");
            //ret.append("	    <legend>"+group.getSemanticObject().getDisplayName(m_lang)+"</legend>");
            ret.append("	    <table>\n");
            Iterator<PropertyGroup> itgp=SWBComparator.sortSortableObject(groups.keySet().iterator());
            while(itgp.hasNext())
            {
                PropertyGroup group=itgp.next();

                Iterator<SemanticProperty> it=groups.get(group).iterator();
                while(it.hasNext())
                {
                    SemanticProperty prop=it.next();
                    FormElement ele=getFormElement(prop);
                    if(DOJO && !m_cls.isAutogenId() && prop.equals(m_cls.getDisplayNameProperty()))
                    {
                        ele.setAttribute("onkeyup", "dojo.byId('swb_create_id').value=replaceChars4Id(this.textbox.value);dijit.byId('swb_create_id').validate()");
                    }
                    renderProp(request, ret, prop, ele);
                }
            }
            if(!m_cls.isAutogenId())
            {
                String sid="Identificador";
                if(m_lang.equals("en"))sid="Identifier";
                String model=m_ref.getModel().getName();
                String clsid=m_cls.getClassId();
                ret.append("	    <tr><td align=\"right\">\n");
                ret.append("                <label>"+sid+" <em>*</em></label>\n");
                ret.append("        </td><td>\n");
                if(DOJO)ret.append("                <input type=\"text\" id=\"swb_create_id\" name=\""+PRM_ID+"\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" promptMessage=\"Captura Identificador.\" isValid=\"return canCreateSemanticObject('"+model+"','"+clsid+"',this.textbox.value);\" invalidMessage=\"Identificador invalido.\" style=\"width:300px;\" trim=\"true\"/>\n");
                else ret.append("                <input type=\"text\" id=\"swb_create_id\" style=\"width:300px;\" name=\""+PRM_ID+"\"/>\n");
                ret.append("	    </td></tr>\n");
            }
            //ret.append("        <tr><td align=\"center\" colspan=\"2\"><hr/></td></tr>");
            ret.append("        <tr><td align=\"center\" colspan=\"2\">\n");
            //ret.append("            <button dojoType='dijit.form.Button' type=\"submit\">Guardar</button>");
            //ret.append("            <button dojoType='dijit.form.Button' onclick=\"dijit.byId('swbDialog').hide();\">Cancelar</button>");
            Iterator it=buttons.iterator();
            while(it.hasNext())
            {
                Object aux=it.next();
                ret.append("    ");
                if(aux instanceof SWBFormButton)
                {
                    ret.append(((SWBFormButton)aux).renderButton(request, m_type, m_lang));
                }else
                {
                    ret.append(aux.toString());
                }
                ret.append("\n");
            }
            ret.append("	    </td></tr>\n");
            ret.append("	    </table>\n");
            ret.append("	</fieldset>\n");
        }
        ret.append("</form>\n");
        //ret.append("<div id=\""+frmname+"_loading\">Loading...</div>");
        return ret.toString();
    }

    public SemanticObject validateForm(HttpServletRequest request) throws FormValidateException
    {
        SemanticObject ret=m_obj;
        String smode=request.getParameter(PRM_MODE);
        if(smode!=null)
        {
            Iterator<SemanticProperty> it=m_cls.listProperties();
            while(it.hasNext())
            {
                SemanticProperty prop=it.next();
                validateElement(request, prop);
            }
        }
        return ret;
    } 

    public SemanticObject processForm(HttpServletRequest request) throws FormValidateException
    {
        validateForm(request);
        SemanticObject ret=m_obj;
        String smode=request.getParameter(PRM_MODE);
        if(smode!=null)
        {
            if(smode.equals(MODE_CREATE))
            {
                SemanticModel model=m_ref.getModel();
                if(!m_cls.isAutogenId())
                {
                    String id=request.getParameter(PRM_ID);
                    ret=model.createSemanticObjectById(id, m_cls);
                }else
                {
                    long id=model.getCounter(m_cls);
                    ret=model.createSemanticObjectById(""+id,m_cls);
                }
                m_obj=ret;
            }
            //else
            {
                Iterator<SemanticProperty> it=m_cls.listProperties();
                while(it.hasNext())
                {
                    SemanticProperty prop=it.next();
                    processElement(request, prop);
                }
            }
        }
        return ret;
    }
    
    private void renderProp(HttpServletRequest request, StringBuffer ret, SemanticProperty prop, FormElement ele)
    {
        String label=null;
        String element=null;
        boolean hidden=false;
        SemanticObject dispobj=prop.getDisplayProperty();
        if(dispobj!=null)
        {
            DisplayProperty disp=new DisplayProperty(dispobj);
            hidden=disp.isHidden();
        }
        if(!hidden)
        {
            try
            {
                if(m_propmap!=null)
                {
                    label=ele.renderLabel(request, m_obj, prop, m_type, m_propmap.get(prop), m_lang);
                    element=ele.renderElement(request, m_obj, prop, m_type, m_propmap.get(prop), m_lang);
                }else
                {
                    label=ele.renderLabel(request, m_obj, prop, m_type, m_mode, m_lang);
                    element=ele.renderElement(request, m_obj, prop, m_type, m_mode, m_lang);
                }
            }catch(Exception e){log.error("Element:"+ele,e);}
            if(element!=null && element.length()>0)
            {
                if(!m_mode.equals(MODE_CREATE))
                {
                    ret.append("                <tr><td width=\"200px\" align=\"right\">");
                }else
                {
                    ret.append("                <tr><td align=\"right\">");
                }
                ret.append(label);
                ret.append("</td><td>");
                ret.append(element);
                ret.append("</td></tr>\n");
            }
        }else if(m_mode.equals(MODE_CREATE))
        {
            String name=prop.getName();
            String value=request.getParameter(name);
            //Solo si el valor pasa por parametro se agrega el hidden
//            if(prop.isDataTypeProperty())
//            {
//                value=m_obj.getProperty(prop);
//            }else
//            {
//                SemanticObject aux=m_obj.getObjectProperty(prop);
//                if(aux!=null)
//                {
//                    value=aux.getURI();
//                }
//            }
            if(value!=null)
            {
                ret.append("    <input type=\"hidden\" name=\""+name+"\" value=\""+value+"\"/>");
            }
        }
    }

    public String getFormName()
    {
        String uri;
        String frmname=null;
        if(m_mode.equals(MODE_CREATE))
        {
            uri=m_cls.getURI();
        }else
        {
            uri=m_obj.getURI();
        }
        return frmname=uri+"/form";
    }

    public String renderElement(HttpServletRequest request, String propName, String mode)
    {
        String ret=null;
        if(m_obj!=null)
        {
            SemanticProperty prop=m_obj.getSemanticClass().getProperty(propName);
            FormElement ele=getFormElement(prop);
            ret=ele.renderElement(request, m_obj, prop, m_type, mode, m_lang);
        }
        return ret;
    }

    public String renderElement(HttpServletRequest request, String propName)
    {
        return renderElement(request, propName,m_mode);
    }
    
    public FormElement getFormElement(SemanticProperty prop)
    {
        SemanticObject obj=prop.getDisplayProperty();
        FormElement ele=null;
        if(obj!=null)
        {
            DisplayProperty disp=new DisplayProperty(obj);
            SemanticObject feobj=disp.getFormElement();
            if(feobj!=null)
            {
                ele=(FormElement)feobj.createGenericInstance();
            }
        }
        //System.out.println("obj:"+obj+" prop:"+prop+" ele:"+ele);
        if(ele==null)ele=new GenericFormElement();
        if(ele!=null)
        {
            if(m_obj!=null)
                ele.setModel(m_obj.getModel());
            else if(m_ref!=null)
                ele.setModel(m_ref.getModel());
        }
        return ele;
    }

    public void validateElement(HttpServletRequest request, SemanticProperty prop) throws FormValidateException
    {
        FormElement ele=getFormElement(prop);
        ele.validate(request, m_obj, prop);
    }
    
    public void processElement(HttpServletRequest request, SemanticProperty prop)
    {
        FormElement ele=getFormElement(prop);
        ele.process(request, m_obj, prop);
    }
    
    public String renderLabel(HttpServletRequest request, SemanticProperty prop, String mode)
    {
        String ret=null;
        //System.out.println("prop:"+prop+" mode:"+mode);
        FormElement ele=getFormElement(prop);
        ret=ele.renderLabel(request, m_obj, prop, m_type, mode, m_lang);
        return ret;
    }

    public String renderElement(HttpServletRequest request, SemanticProperty prop, String mode)
    {
        String ret=null;
        //System.out.println("prop:"+prop+" mode:"+mode);
        FormElement ele=getFormElement(prop);
        ret=ele.renderElement(request, m_obj, prop, m_type, mode, m_lang);
        return ret;
    }
    
    public void addHiddenParameter(String key, String value)
    {
        if(key!=null && value!=null)hidden.put(key, value);
    }

    /**
     * Add HTML text for Button
     * Sample: <button dojoType="dijit.form.Button" type="submit">Guardar</button>
     * @param html
     */
    public void addButton(String html)
    {
        buttons.add(html);
    }

    public void addButton(SWBFormButton button)
    {
        buttons.add(button);
    }

    public void setOnSubmit(String onsubmit)
    {
        m_onsubmit=onsubmit;
    }

    public void setSubmitByAjax(boolean submitByAjax)
    {
        this.submitByAjax=submitByAjax;
    }

    /**
     * Filtra solo requeridas en la creacion, por default = true
     * @param onlyRequired
     */
    public void setFilterRequired(boolean onlyRequired)
    {
        filterRequired=onlyRequired;
        init();
    }
}
