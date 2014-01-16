/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.portal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.DisplayObject;
import org.semanticwb.model.DisplayProperty;
import org.semanticwb.model.FormElement;
import org.semanticwb.model.FormElementURL;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.FormView;
import org.semanticwb.model.GenericFormElement;
import org.semanticwb.model.PropertyGroup;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.base.FormElementBase;
import org.semanticwb.platform.*;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBFormMgr.
 * 
 * @author Javier Solis Gonzalez
 */
public class SWBFormMgr implements SWBForms
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(SWBFormMgr.class);

    /** The m_obj. */
    private SemanticObject m_obj;
    
    /** The m_ref. */
    private SemanticObject m_ref;
    
    /** The m_cls. */
    private SemanticClass m_cls;
    
    /** The m_fview. */
    private FormView m_fview=null;
    
    /** The m_propmap. */
    private Map<SemanticProperty, String> m_propmap=null;
    
    /** The m_mode. */
    private String m_mode=MODE_VIEW;
    
    /** The m_action. */
    private String m_action="";
    
    /** The m_method. */
    private String m_method="POST";
    
    /** The m_onsubmit. */
    private String m_onsubmit=null;
    
    /** The m_lang. */
    private String m_lang="es";
    
    /** The m_type. */
    private String m_type=TYPE_XHTML;
    
    /** The m_general. */
    private PropertyGroup m_general=null;

    /** The hidden. */
    private HashMap<String, String> hidden=null;
    
    /** The hidden props. */
    private ArrayList hiddenProps=null;
    
    /** The buttons. */
    private ArrayList<Object> buttons=null;

    /** The filter required. */
    private boolean filterRequired=true;
    
    /** The filter html tags. */
    private boolean filterHTMLTags=true;
    
    /** The groups. */
    private HashMap<PropertyGroup, TreeSet> groups=null;

    /** The submit by ajax. */
    private boolean submitByAjax=false;

    /** The use captcha. */
    private boolean useCaptcha=false;

    /** The removed. */
    private ArrayList<SemanticProperty> removed=null;

    private String varName=null;
    private SemanticObject varRef=null;

    private boolean selectClass=false;
    
    /**
     * Instantiates a new sWB form mgr.
     * 
     * @param obj the obj
     * @param frmview the frmview
     * @param mode the mode
     */
    public SWBFormMgr(SemanticObject obj, String frmview, String mode)
    {
        this.m_obj=obj;
        this.m_mode=mode;
        this.m_cls=obj.getSemanticClass();
        this.m_fview=SWBContext.getFormView(frmview);
        init();
    }

    /**
     * Modo creacion.
     * 
     * @param cls the cls
     * @param ref the ref
     * @param frmview the frmview
     */
    public SWBFormMgr(SemanticClass cls, SemanticObject ref, String frmview)
    {
        this.m_mode=MODE_CREATE;
        this.m_cls=cls;
        this.m_ref=ref;
        this.m_fview=SWBContext.getFormView(frmview);
        init();
    }

    
    /**
     * Inits the.
     */
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
        removed=new ArrayList();
        Iterator<SemanticProperty> it=m_cls.listProperties();
        while(it.hasNext())
        {
            SemanticProperty prop=it.next();
            //System.out.println("add:"+prop);
            addProperty(prop,filterRequired);
        }
    }

    public void setSelectClass(boolean selectClass)
    {
        this.selectClass = selectClass;
    }

    public boolean isSelectClass()
    {
        return selectClass;
    }

    /**
     * Gets the locale string.
     * 
     * @param key the key
     * @param lang the lang
     * @return the locale string
     */
    public String getLocaleString(String key, String lang)
    {
        return SWBUtils.TEXT.getLocaleString("locale_swb_admin", key, new Locale(lang));
    }

    /**
     * Hide property.
     * 
     * @param prop the prop
     */
    public void hideProperty(SemanticProperty prop)
    {
        removed.add(prop);
    }

    /**
     * Gets the groups.
     * 
     * @return the groups
     */
    public HashMap<PropertyGroup, TreeSet> getGroups()
    {
        return groups;
    }

    /**
     * Adds the property.
     * 
     * @param prop the prop
     */
    public void addProperty(SemanticProperty prop)
    {
        addProperty(prop, false);
    }
    
    /**
     * Adds the property.
     * 
     * @param prop the prop
     * @param filterRequired the filter required
     */
    public void addProperty(SemanticProperty prop, boolean filterRequired)
    {
        //System.out.println("prop:"+prop);
        boolean createGroup=false;
        boolean addProp=false;
        SemanticObject obj=prop.getDisplayProperty();
        PropertyGroup grp=null;
        boolean hidden=false;
        boolean required=prop.isRequired();
        DisplayProperty disp=null;
        if(obj!=null)
        {
            disp=new DisplayProperty(obj);
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
            }else if(!filterRequired && m_mode.equals(MODE_CREATE))      //solo se agregan las requeridas
            {
                addProp=true;
                if(prop.isDateTime() || (prop.isObjectProperty() && (disp==null || disp.getFormElement()==null)))
                {
                    addProp=false;
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
    
    /**
     * Gets the action.
     * 
     * @return the action
     */
    public String getAction() {
        return m_action;
    }

    /**
     * Sets the action.
     * 
     * @param action the new action
     */
    public void setAction(String action) {
        this.m_action = action;
    }

    /**
     * Gets the method.
     * 
     * @return the method
     */
    public String getMethod() {
        return m_method;
    }

    /**
     * Sets the method.
     * 
     * @param method the new method
     */
    public void setMethod(String method) {
        this.m_method = method;
    }
    
    /**
     * Gets the lang.
     * 
     * @return the lang
     */
    public String getLang() {
        return m_lang;
    }

    /**
     * Sets the lang.
     * 
     * @param lang the new lang
     */
    public void setLang(String lang) {
        this.m_lang = lang;
    }    
    
    /**
     * Gets the captcha status.
     *
     * @return the lang
     */
    public boolean getCaptchaStatus() {
        return useCaptcha;
    }

    /**
     * Sets the captcha status.
     *
     * @param value the new captcha status
     */
    public void setCaptchaStatus(boolean value) {
        this.useCaptcha = value;
    }

    /**
     * Gets the type.
     * 
     * @return the type
     */
    public String getType() {
        return m_type;
    }

    /**
     * Sets the type.
     * 
     * @param type the new type
     */
    public void setType(String type) {
        this.m_type = type;
    }

    /**
     * Gets the identifier element.
     *
     * @return the identifier element
     */
    private String getSelectClassElement()
    {
        boolean DOJO=false;
        boolean IPHONE=false;
        boolean XHTML=false;
        if(m_type.equals(TYPE_XHTML))XHTML=true;
        if(m_type.equals(TYPE_DOJO))DOJO=true;
        if(m_type.equals(TYPE_IPHONE))IPHONE=true;

        StringBuffer ret=new StringBuffer();

        String imsg="La clase es requerido.";
        if (m_lang.equals("en"))
        {
            imsg = "The class is required.";
        }

        String clase=getLocaleString("class",m_lang);
        Iterator<SemanticClass> it=m_cls.listSubClasses();
        if(it.hasNext())
        {
            ret.append("	    <tr><td align=\"right\">\n");
            ret.append("                <label>"+clase+" <em>*</em></label>\n");
            ret.append("        </td><td>\n");

            ret.append("<select name=\"" + PRM_CLS + "\"");

            if (DOJO) {
                ret.append(" dojoType=\"dijit.form.FilteringSelect\" autoComplete=\"true\" invalidMessage=\""
                           + imsg + "\" >");
            }

            ArrayList<SemanticClass> arr=new ArrayList();
            arr.add(m_cls);
            while (it.hasNext())
            {
                SemanticClass semanticClass = it.next();
                arr.add(semanticClass);
            }

            it=arr.iterator();
            while (it.hasNext())
            {
                SemanticClass semanticClass = it.next();
                //System.out.println("cls:"+semanticClass);
                boolean instanceable=true;
                if(semanticClass.getDisplayObject()!=null)
                {
                    DisplayObject disp=(DisplayObject)semanticClass.getDisplayObject().createGenericInstance();
                    instanceable=!disp.isDoNotInstanceable();
                }
                if (instanceable)
                {
                    //System.out.println("cls2:"+semanticClass);
                    ret.append("<option value=\"" + semanticClass.getURI() + "\" ");
                    ret.append(">" + semanticClass.getDisplayName(m_lang) + "</option>");
                }
            }
            ret.append("</select>");
            ret.append("	    </td></tr>\n");
        }
        return ret.toString();
    }



    /**
     * Gets the identifier element.
     * 
     * @return the identifier element
     */
    public String getIdentifierElement()
    {
        boolean DOJO=false;
        boolean IPHONE=false;
        boolean XHTML=false;
        if(m_type.equals(TYPE_XHTML))XHTML=true;
        if(m_type.equals(TYPE_DOJO))DOJO=true;
        if(m_type.equals(TYPE_IPHONE))IPHONE=true;

        StringBuffer ret=new StringBuffer();
        String sid=getLocaleString("identifier",m_lang);
        String model=m_ref.getModel().getName();
        String clsid=m_cls.getClassId();

        ret.append("	    <tr><td align=\"right\">\n");
        ret.append("                <label>"+sid+" <em>*</em></label>\n");
        ret.append("        </td><td>\n");
        if(DOJO)ret.append("                <input type=\"text\" id=\"swb_create_id\" name=\""+PRM_ID+"\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" promptMessage=\"Captura Identificador.\" isValid=\"return canCreateSemanticObject('"+model+"','"+clsid+"',this.textbox.value);\" invalidMessage=\"Identificador invalido.\" style=\"width:300px;\" trim=\"true\"/>\n");
        else ret.append("                <input type=\"text\" id=\"swb_create_id\" style=\"width:300px;\" name=\""+PRM_ID+"\"/>\n");
        ret.append("	    </td></tr>\n");
        return ret.toString();
    }

    /**
     * Regresa input del tipo hidden requeridos para el processForm.
     * 
     * @return the form hiddens
     * @return
     */
    public String getFormHiddens()
    {
        StringBuffer ret=new StringBuffer();
        if(m_obj!=null)ret.append("    <input type=\"hidden\" name=\""+PRM_URI+"\" value=\""+m_obj.getURI()+"\"/>\n");
        if(m_cls!=null && !selectClass || (selectClass && !m_cls.listSubClasses().hasNext()))
        {
            ret.append("    <input type=\"hidden\" name=\"" + PRM_CLS + "\" value=\"" + m_cls.getURI() + "\"/>\n");
        }
        if(m_mode!=null)ret.append("    <input type=\"hidden\" name=\""+PRM_MODE+"\" value=\""+m_mode+"\"/>\n");
        if(m_ref!=null)ret.append("    <input type=\"hidden\" name=\""+PRM_REF+"\" value=\""+m_ref.getURI()+"\"/>\n");
        Iterator<Map.Entry<String,String>> hit=hidden.entrySet().iterator();
        while(hit.hasNext())
        {
            Map.Entry entry=hit.next();
            ret.append("    <input type=\"hidden\" name=\""+entry.getKey()+"\" value=\""+entry.getValue()+"\"/>\n");
        }
        return ret.toString();
    }

    /**
     * Regresa lista de FormElements a renderear.
     * 
     * @return the properties
     * @return
     */
    public List<SemanticProperty> getProperties()
    {
        ArrayList arr=new ArrayList();

        if(!m_mode.equals(MODE_CREATE))
        {
            Iterator<PropertyGroup> itgp=SWBComparator.sortSortableObject(groups.keySet().iterator());
            while(itgp.hasNext())
            {
                PropertyGroup group=itgp.next();
                Iterator<SemanticProperty> it=groups.get(group).iterator();
                while(it.hasNext())
                {
                    SemanticProperty prop=it.next();
                    arr.add(prop);
                }
            }
        }else
        {
            Iterator<PropertyGroup> itgp=SWBComparator.sortSortableObject(groups.keySet().iterator());
            while(itgp.hasNext())
            {
                PropertyGroup group=itgp.next();
                Iterator<SemanticProperty> it=groups.get(group).iterator();
                while(it.hasNext())
                {
                    SemanticProperty prop=it.next();
                    arr.add(prop);
                }
            }
        }
        return arr;
    }

    /**
     * List properties.
     * 
     * @return the iterator
     */
    private Iterator<SemanticProperty> listProperties()
    {
        ArrayList<SemanticProperty> arr=new ArrayList();
        Iterator<PropertyGroup> itgp=SWBComparator.sortSortableObject(groups.keySet().iterator());
        while(itgp.hasNext())
        {
            PropertyGroup group=itgp.next();
            Iterator<SemanticProperty> it=groups.get(group).iterator();
            while(it.hasNext())
            {
                SemanticProperty prop=it.next();
                arr.add(prop);
            }
        }
        return arr.iterator();
    }

    /**
     * Genera HTML de la forma del tipo de objeto especificado en el constructor.
     * 
     * @param request the request
     * @return the string
     * @return
     */
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

        if(DOJO)ret.append(DOJO_REQUIRED);

        if(DOJO)ret.append("<form id=\""+frmname+"\" dojoType=\"dijit.form.Form\" class=\"swbform\" action=\""+m_action+"\""+onsubmit+" method=\""+m_method.toLowerCase()+"\">\n");
        else ret.append("<form id=\""+frmname+"\" class=\"swbform\" action=\""+m_action+"\""+onsubmit+" method=\""+m_method.toLowerCase()+"\">\n");

        ret.append(getFormHiddens());

        if(!m_mode.equals(MODE_CREATE))
        {
            String sid=getLocaleString("identifier",m_lang);
            ret.append("	<fieldset>\n");
            ret.append("	    <table><tr><td width=\"200px\" align=\"right\">\n");
            ret.append("                <label>"+sid+" &nbsp;</label>\n");
            ret.append("        </td><td>\n");
            ret.append("                <span>"+m_obj.getId()+"</span>\n");
            ret.append("	    </td></tr></table>\n");
            ret.append("	</fieldset>\n");

            boolean first=true;
            Iterator<PropertyGroup> itgp=SWBComparator.sortSortableObject(groups.keySet().iterator());
            while(itgp.hasNext())
            {
                PropertyGroup group=itgp.next();
//                if(DOJO)
//                {
//                    ret.append("	<fieldset dojoType=\"dijit.TitlePane\" title=\""+group.getSemanticObject().getDisplayName(m_lang)+"\" open=\""+first+"\">\n");
//                    first=false;
//                }
//                else
//                {
                    ret.append("	<fieldset >\n");
                    ret.append("	    <legend>"+group.getSemanticObject().getDisplayName(m_lang)+"</legend>\n");
//                }
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
            if (useCaptcha)
            {
                ret.append("<table>");
                ret.append(getCaptchaField(DOJO));
                ret.append("</table>\n");
            }
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
            boolean idinsert=false;
            ret.append("	<fieldset>\n");
            //ret.append("	    <legend>"+group.getSemanticObject().getDisplayName(m_lang)+"</legend>");
            ret.append("	    <table>\n");

            if(selectClass)ret.append(getSelectClassElement());

            Iterator<PropertyGroup> itgp=SWBComparator.sortSortableObject(groups.keySet().iterator());
            while(itgp.hasNext())
            {
                PropertyGroup group=itgp.next();

                Iterator<SemanticProperty> it=groups.get(group).iterator();
                while(it.hasNext())
                {
                    SemanticProperty prop=it.next();

                    if(!m_cls.isAutogenId() && !idinsert && !prop.isRequired())
                    {
                        idinsert=true;
                        ret.append(getIdentifierElement());
                    }

                    FormElement ele=getFormElement(prop);
                    if(DOJO && !m_cls.isAutogenId() && prop.equals(m_cls.getDisplayNameProperty()))
                    {
                        ele.setAttribute("onkeyup", "if(dojo.byId('swb_create_id')!=null)dojo.byId('swb_create_id').value=replaceChars4Id(this.textbox.value);if(dojo.byId('swb_create_id')!=null)dijit.byId('swb_create_id').validate()");
                    }
                    renderProp(request, ret, prop, ele);
                }
            }
            if(!idinsert && !m_cls.isAutogenId())
            {
                ret.append(getIdentifierElement());
            }
            //ret.append("        <tr><td align=\"center\" colspan=\"2\"><hr/></td></tr>");
            if (useCaptcha)
            {
                ret.append(getCaptchaField(DOJO));
            }
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

    /**
     * Validate form.
     * 
     * @param request the request
     * @return the semantic object
     * @throws FormValidateException the form validate exception
     */
    public SemanticObject validateForm(HttpServletRequest request) throws FormValidateException
    {
        if (useCaptcha)
        {
            if ((null!=request.getSession(true).getAttribute("captchaCad") && !((String) request.getSession(true).getAttribute("captchaCad")).equalsIgnoreCase(request.getParameter("frmCaptchaValue"))) ||
                    (null!=request.getSession(true).getAttribute("captchaNum") && !((String) request.getSession(true).getAttribute("captchaNum")).equalsIgnoreCase(request.getParameter("frmCaptchaValue")))){
                throw new FormValidateException("Invalid human submitted form validation");
            }
            request.getSession(true).removeAttribute("captchaCad");
            request.getSession(true).removeAttribute("captchaNum");
        }
        SemanticObject ret=m_obj;
        String smode=request.getParameter(PRM_MODE);
        if(smode!=null)
        {
            Iterator<SemanticProperty> it=listProperties();
            while(it.hasNext())
            {
                SemanticProperty prop=it.next();
                validateElement(request, prop, prop.getName());
            }
        }
        return ret;
    }

    /**
     * Process form.
     *
     * @param request the request
     * @return the semantic object
     * @throws FormValidateException the form validate exception
     */
    public SemanticObject processForm(HttpServletRequest request) throws FormValidateException
    {
        return processForm(request,null);
    }

    /**
     * Process form.
     * 
     * @param request the request
     * @return the semantic object
     * @throws FormValidateException the form validate exception
     */
    public SemanticObject processForm(HttpServletRequest request, String id) throws FormValidateException
    {
        validateForm(request);
        SemanticObject ret=m_obj;
        String smode=request.getParameter(PRM_MODE);
        if(smode!=null)
        {
            if(smode.equals(MODE_CREATE))
            {
                SemanticModel model=m_ref.getModel();
                if(id==null)
                {
                    if(!m_cls.isAutogenId())
                    {
                        id=request.getParameter(PRM_ID);
                    }else
                    {
                        id=""+model.getCounter(m_cls);
                    }
                }
                ret=model.createSemanticObjectById(id, m_cls);
                m_obj=ret;
            }
            //else
            {
                Iterator<SemanticProperty> it=listProperties();
                while(it.hasNext())
                {
                    SemanticProperty prop=it.next();
                    //System.out.println("ProcessElement:"+prop);
                    processElement(request, prop, prop.getName());
                }
            }
        }
        return ret;
    }


    /**
     * Rederea propiedad (metodo interno del SWBFormMgr.
     * 
     * @param request the request
     * @param ret the ret
     * @param prop the prop
     * @param ele the ele
     */
    public void renderProp(HttpServletRequest request, StringBuffer ret, SemanticProperty prop, FormElement ele)
    {
        renderProp(request, ret, prop, prop.getName(), ele, m_mode);
    }

    /**
     * Rederea propiedad (metodo interno del SWBFormMgr.
     *
     * @param request the request
     * @param ret the ret
     * @param prop the prop
     * @param ele the ele
     * @param mode the mode
     */
    public void renderProp(HttpServletRequest request, StringBuffer ret, SemanticProperty prop, FormElement ele, String mode)
    {
        renderProp(request, ret, prop, prop.getName(), ele, mode);
    }

    
    /**
     * Rederea propiedad (metodo interno del SWBFormMgr.
     * 
     * @param request the request
     * @param ret the ret
     * @param prop the prop
     * @param ele the ele
     * @param mode the mode
     */
    public void renderProp(HttpServletRequest request, StringBuffer ret, SemanticProperty prop, String propName, FormElement ele, String mode)
    {
        renderProp(request, ret, prop, propName, ele, mode, null);
    }

    /**
     * Rederea propiedad (metodo interno del SWBFormMgr.
     * 
     * @param request the request
     * @param ret the ret
     * @param prop the prop
     * @param ele the ele
     * @param mode the mode
     */
    public void renderProp(HttpServletRequest request, StringBuffer ret, SemanticProperty prop, String propName, FormElement ele, String mode, String label)
    {
        SemanticObject obj=m_obj;
        if(obj==null)obj=new SemanticObject(m_ref.getModel(),m_cls);
        if(removed.contains(prop))return;
        //String label=null;
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
                    label=ele.renderLabel(request, obj, prop, propName, m_type, m_propmap.get(prop), m_lang,label);
                    element=ele.renderElement(request, obj, prop, propName, m_type, m_propmap.get(prop), m_lang);
                }else
                {
                    label=ele.renderLabel(request, obj, prop, propName, m_type, mode, m_lang,label);
                    element=ele.renderElement(request, obj, prop, propName, m_type, mode, m_lang);
                }
            }catch(Exception e){log.error("Element:"+ele,e);}
            if(element!=null && element.length()>0)
            {
                if(!mode.equals(MODE_CREATE))
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
        }else if(mode.equals(MODE_CREATE))
        {
            String name=prop.getName();
            String value=request.getParameter(name);
            //Solo si el valor pasa por parametro se agrega el hidden
//            if(prop.isDataTypeProperty())
//            {
//                value=obj.getProperty(prop);
//            }else
//            {
//                SemanticObject aux=obj.getObjectProperty(prop);
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

    /**
     * Gets the form name.
     * 
     * @return the form name
     */
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

    /**
     * Render element.
     * 
     * @param request the request
     * @param propName the prop name
     * @param mode the mode
     * @return the string
     */
    public String renderElement(HttpServletRequest request, String propName, String mode)
    {
        String ret=null;
        if(m_obj!=null)
        {
            SemanticProperty prop=m_obj.getSemanticClass().getProperty(propName);
            FormElement ele=getFormElement(prop);
            ret=ele.renderElement(request, m_obj, prop, propName, m_type, mode, m_lang);
        }
        return ret;
    }

    /**
     * Render element.
     * 
     * @param request the request
     * @param propName the prop name
     * @return the string
     */
    public String renderElement(HttpServletRequest request, String propName)
    {
        return renderElement(request, propName, m_mode);
    }
    
    /**
     * Gets the form element.
     * 
     * @param prop the prop
     * @return the form element
     */
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
            ((FormElementBase)ele).setFilterHTMLTags(filterHTMLTags);
        }
        return ele;
    }

    /**
     * Validate element.
     *
     * @param request the request
     * @param prop the prop
     * @throws FormValidateException the form validate exception
     */
    public void validateElement(HttpServletRequest request, SemanticProperty prop) throws FormValidateException
    {
        validateElement(request, prop, prop.getName());
    }

    /**
     * Validate element.
     * 
     * @param request the request
     * @param prop the prop
     * @throws FormValidateException the form validate exception
     */
    public void validateElement(HttpServletRequest request, SemanticProperty prop, String propName) throws FormValidateException
    {
        SemanticObject obj=m_obj;
        if(obj==null)
        {
            obj=new SemanticObject(m_ref.getModel(), m_cls);
        }
        FormElement ele=getFormElement(prop);
        ele.validate(request, obj, prop, propName);
    }

    /**
     * Process element.
     *
     * @param request the request
     * @param prop the prop
     */
    public void processElement(HttpServletRequest request, SemanticProperty prop)
    {
        processElement(request, prop, prop.getName());
    }
    
    /**
     * Process element.
     * 
     * @param request the request
     * @param prop the prop
     */
    public void processElement(HttpServletRequest request, SemanticProperty prop, String propName)
    {
        FormElement ele=getFormElement(prop);
        ele.process(request, m_obj, prop, propName);
    }

    /**
     * Render label.
     *
     * @param request the request
     * @param prop the prop
     * @param mode the mode
     * @return the string
     */
    public String renderLabel(HttpServletRequest request, SemanticProperty prop, String mode)
    {
        return renderLabel(request, prop, prop.getName(), mode);
    }

    /**
     * Render label.
     * 
     * @param request the request
     * @param prop the prop
     * @param mode the mode
     * @return the string
     */
    public String renderLabel(HttpServletRequest request, SemanticProperty prop, String propName, String mode)
    {
        String ret=null;
        //System.out.println("prop:"+prop+" mode:"+mode);
        FormElement ele=getFormElement(prop);
        ret=ele.renderLabel(request, m_obj, prop, propName, m_type, mode, m_lang);
        return ret;
    }

    /**
     * Render element.
     *
     * @param request the request
     * @param prop the prop
     * @param mode the mode
     * @return the string
     */
    public String renderElement(HttpServletRequest request, SemanticProperty prop, String mode)
    {
        return renderElement(request, prop, prop.getName(), mode);
    }

    /**
     * Render element.
     * 
     * @param request the request
     * @param prop the prop
     * @param mode the mode
     * @return the string
     */
    public String renderElement(HttpServletRequest request, SemanticProperty prop, String propName, String mode)
    {
        String ret=null;
        //System.out.println("prop:"+prop+" mode:"+mode);
        FormElement ele=getFormElement(prop);
        ret=ele.renderElement(request, m_obj, prop, propName, m_type, mode, m_lang);
        return ret;
    }
    
    /**
     * Adds the hidden parameter.
     * 
     * @param key the key
     * @param value the value
     */
    public void addHiddenParameter(String key, String value)
    {
        if(key!=null && value!=null)hidden.put(key, value);
    }

    /**
     * Add HTML text for Button
     * Sample: <button dojoType="dijit.form.Button" type="submit">Guardar</button>
     * 
     * @param html the html
     */
    public void addButton(String html)
    {
        buttons.add(html);
    }

    /**
     * Adds the button.
     * 
     * @param button the button
     */
    public void addButton(SWBFormButton button)
    {
        buttons.add(button);
    }

    /**
     * Sets the on submit.
     * 
     * @param onsubmit the new on submit
     */
    public void setOnSubmit(String onsubmit)
    {
        m_onsubmit=onsubmit;
    }

    /**
     * Sets the submit by ajax.
     * 
     * @param submitByAjax the new submit by ajax
     */
    public void setSubmitByAjax(boolean submitByAjax)
    {
        this.submitByAjax=submitByAjax;
    }

    /**
     * Filtra solo requeridas en la creacion, por default = true.
     * 
     * @param onlyRequired the new filter required
     */
    public void setFilterRequired(boolean onlyRequired)
    {
        filterRequired=onlyRequired;
        init();
    }

    /**
     * Sets the mode.
     * 
     * @param mode the new mode
     */
    public void setMode(String mode)
    {
        m_mode=mode;
    }

    /**
     * Gets the mode.
     * 
     * @return the mode
     */
    public String getMode()
    {
        return m_mode;
    }

    /**
     * Sets the semantic object.
     * 
     * @param obj the new semantic object
     */
    public void setSemanticObject(SemanticObject obj)
    {
        m_obj=obj;
    }

    /**
     * Gets the semantic object.
     * 
     * @return the semantic object
     */
    public SemanticObject getSemanticObject()
    {
        return m_obj;
    }

    /**
     * Clear properties.
     */
    public void clearProperties()
    {
        groups=new HashMap();
    }

    /**
     * Checks if is filter html tags.
     * 
     * @return the filterHTMLTags
     */
    public boolean isFilterHTMLTags() {
        return filterHTMLTags;
    }

    /**
     * Sets the filter html tags.
     * 
     * @param filterHTMLTags the filterHTMLTags to set
     */
    public void setFilterHTMLTags(boolean filterHTMLTags) {
        this.filterHTMLTags = filterHTMLTags;
    }

    /**
     * Gets the captcha field.
     * 
     * @param isdojo the isdojo
     * @return the captcha field
     */
    private String getCaptchaField(boolean isdojo){
        StringBuilder ret = new StringBuilder();
        ret.append("<tr><td align=\"right\"><label for=\"frmCaptchaValue\">Verificaci&oacute;n <em>*</em></label></td><td>\n");
                ret.append("<div id=\"captchaSnd\"></div><img src=\""+SWBPlatform.getContextPath()+"/frmprocess/requestCaptcha\" style=\"float:left;margin-left: 5px;margin-right: 30px;\" id=\"captchaimg\" />");
                ret.append("<a onclick=\"document.getElementById('captchaimg').src='"+SWBPlatform.getContextPath()+
                        "/frmprocess/requestCaptcha?'+ Math.random(); document.getElementById('frmCaptchaValue').value=''; return false;\">"+getLocaleString("captchachgImg",m_lang)+"</a> ");
                ret.append("<a onclick=\"jBeep('"+SWBPlatform.getContextPath()+"/frmprocess/requestSoundCaptcha?'+ Math.random())\">"+getLocaleString("scaptchasol",m_lang)+"</a><br/>");
                ret.append("<input name=\"frmCaptchaValue\" id=\"frmCaptchaValue\"  ");

                if (isdojo) {
                    String required=getLocaleString("required",m_lang);
                    String pmsg=getLocaleString("captchapmsg",m_lang);
                    String imsg=getLocaleString("captchaimsg",m_lang);
                    ret.append(" dojoType=\"dijit.form.ValidationTextBox\"");
                    ret.append(" required=\"" + required + "\"");
                    ret.append(" promptMessage=\"" + pmsg + "\"");
                    ret.append(" invalidMessage=\"" + imsg + "\"");
                    ret.append(" isValid=\"return validateElement('frmCaptchaValue','/frmprocess/validCaptcha?none=1',this.textbox.value);\"");
                    ret.append(" trim=\"true\"");
                }

                ret.append(" style=\"width:100px;\"/>");

                ret.append("</td></tr>\n");
                return ret.toString();
    }

    public void setVarName(String varName)
    {
        this.varName = varName;
    }

    public void setVarReference(SemanticObject obj)
    {
        this.varRef = obj;
    }

    public SemanticObject getVarReference()
    {
        return varRef;
    }

    public String getVarName()
    {
        return varName;
    }

    

}
