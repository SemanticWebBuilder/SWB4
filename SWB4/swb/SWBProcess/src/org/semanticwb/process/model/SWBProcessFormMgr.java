/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.process.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.model.DisplayProperty;
import org.semanticwb.model.FormElement;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.PropertyGroup;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.SWBFormButton;
import org.semanticwb.portal.SWBFormMgr;

/**
 *
 * @author javier.solis
 */
public class SWBProcessFormMgr
{
    private FlowNodeInstance m_pinst=null;
    private HashMap<String, SWBFormMgr> mgrs;
    private HashMap<SemanticClass, HashMap<SemanticProperty, String>> views;


    private Map<SemanticProperty, String> m_propmap=null;
    private String m_mode=SWBFormMgr.MODE_EDIT;
    private String m_action="";
    private String m_method="POST";
    private String m_onsubmit=null;
    private String m_lang="es";
    private String m_type=SWBFormMgr.TYPE_DOJO;
    //private PropertyGroup m_general=null;

    private HashMap<String, String> hidden=null;
    //private ArrayList hiddenProps=null;
    private ArrayList<Object> buttons=null;

    //private HashMap<PropertyGroup, TreeSet> groups=null;

    private boolean submitByAjax=false;

    public SWBProcessFormMgr(FlowNodeInstance inst)
    {
        this.m_pinst=inst;
        mgrs=new HashMap();
        views=new HashMap();
        hidden=new HashMap();
        buttons=new ArrayList();
        Iterator<ProcessObject> objs=inst.listHeraquicalProcessObjects().iterator();
        while (objs.hasNext())
        {
            ProcessObject processObject = objs.next();
            SWBFormMgr mgr=new SWBFormMgr(processObject.getSemanticObject(),null,SWBFormMgr.MODE_EDIT);
            mgr.setType(m_type);
            mgrs.put(processObject.getSemanticObject().getSemanticClass().getURI(),mgr);
        }
    }
    
    public void clearProperties()
    {
        Iterator<SWBFormMgr> it=mgrs.values().iterator();
        while (it.hasNext()) 
        {
            SWBFormMgr mgr = it.next();
            mgr.clearProperties();
        }
        views=new HashMap();
    }

    public void addProperty(SemanticProperty prop, SemanticClass cls)
    {
        addProperty(prop, cls, SWBFormMgr.MODE_EDIT);
    }

    public void addProperty(SemanticProperty prop, SemanticClass cls, String view)
    {
        SWBFormMgr mgr=mgrs.get(cls.getURI());
        mgr.addProperty(prop);

        HashMap map=views.get(cls);
        if(map==null)
        {
            map=new HashMap();
            views.put(cls, map);
        }
        map.put(prop, view);
    }

    /**
     * Genera HTML de la forma del tipo de objeto especificado en el constructor
     * @param request
     * @return
     */
    public String renderForm(HttpServletRequest request)
    {
        boolean DOJO=false;
        boolean IPHONE=false;
        boolean XHTML=false;
        if(m_type.equals(SWBFormMgr.TYPE_XHTML))XHTML=true;
        if(m_type.equals(SWBFormMgr.TYPE_DOJO))DOJO=true;
        if(m_type.equals(SWBFormMgr.TYPE_IPHONE))IPHONE=true;

        StringBuffer ret=new StringBuffer();
        String frmname=getFormName();

        String onsubmit="";
        if(m_onsubmit!=null)onsubmit=" onsubmit=\""+m_onsubmit+"\"";
        //si es dojo por default se manda por ajax
        if(m_onsubmit==null && submitByAjax)onsubmit="  onsubmit=\"submitForm('"+frmname+"');return false;\"";

        if(DOJO)ret.append(SWBFormMgr.DOJO_REQUIRED);

        if(DOJO)ret.append("<form id=\""+frmname+"\" dojoType=\"dijit.form.Form\" class=\"swbform\" action=\""+m_action+"\""+onsubmit+" method=\""+m_method.toLowerCase()+"\">\n");
        else ret.append("<form id=\""+frmname+"\" class=\"swbform\" action=\""+m_action+"\""+onsubmit+" method=\""+m_method.toLowerCase()+"\">\n");

        ret.append(getFormHiddens());

        {
//            String sid="Identificador";
//            if(m_lang.equals("en"))sid="Identifier";
//            ret.append("	<fieldset>\n");
//            ret.append("	    <table><tr><td width=\"200px\" align=\"right\">\n");
//            ret.append("                <label>"+sid+" &nbsp;</label>\n");
//            ret.append("        </td><td>\n");
//            ret.append("                <span>"+m_obj.getId()+"</span>\n");
//            ret.append("	    </td></tr></table>\n");
//            ret.append("	</fieldset>\n");


            //Merge properties
            HashMap<PropertyGroup, TreeSet<SWBProcessProperty>> groups=new HashMap();
            Iterator<SWBFormMgr> mgrit=mgrs.values().iterator();
            while (mgrit.hasNext())
            {
                SWBFormMgr mgr = mgrit.next();
                Iterator<PropertyGroup> grpit=mgr.getGroups().keySet().iterator();
                while (grpit.hasNext())
                {
                    PropertyGroup propertyGroup = grpit.next();
                    TreeSet<SWBProcessProperty> set=groups.get(propertyGroup);
                    if(set==null)
                    {
                        set=new TreeSet(new Comparator()
                        {
                            public int compare(Object o1, Object o2)
                            {
                                SemanticObject sobj1=((SWBProcessProperty)o1).getSemanticProperty().getDisplayProperty();
                                SemanticObject sobj2=((SWBProcessProperty)o2).getSemanticProperty().getDisplayProperty();
                                int v1=999999999;
                                int v2=999999999;
                                if(sobj1!=null)v1=new DisplayProperty(sobj1).getIndex();
                                if(sobj2!=null)v2=new DisplayProperty(sobj2).getIndex();
                                return v1<v2?-1:1;
                            }
                        });
                        groups.put(propertyGroup, set);
                    }
                    Iterator<SemanticProperty> itprop=mgr.getGroups().get(propertyGroup).iterator();
                    while (itprop.hasNext())
                    {
                        SemanticProperty semanticProperty = itprop.next();
                        SemanticClass cls=mgr.getSemanticObject().getSemanticClass();
                        set.add(new SWBProcessProperty(cls,semanticProperty, views.get(cls).get(semanticProperty)));
                    }
                }
            }

            Iterator<PropertyGroup> itgp=SWBComparator.sortSortableObject(groups.keySet().iterator());
            while(itgp.hasNext())
            {
                PropertyGroup group=itgp.next();
                ret.append("	<fieldset>\n");
                ret.append("	    <legend>"+group.getSemanticObject().getDisplayName(m_lang)+"</legend>\n");
                ret.append("	    <table>\n");

                Iterator<SWBProcessProperty> it=groups.get(group).iterator();
                while(it.hasNext())
                {
                    SWBProcessProperty pp=it.next();
                    SWBFormMgr mgr=mgrs.get(pp.getSemanticClass().getURI());
                    SemanticProperty prop=pp.getSemanticProperty();
                    FormElement ele=mgr.getFormElement(prop);
                    mgr.renderProp(request, ret, prop, ele, pp.getMode());
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

        }
        ret.append("</form>\n");
        //ret.append("<div id=\""+frmname+"_loading\">Loading...</div>");
        return ret.toString();
    }

    public void processForm(HttpServletRequest request) throws FormValidateException
    {
        //TODO
        //validateForm(request);
        String smode=request.getParameter(SWBFormMgr.PRM_MODE);
        if(smode!=null)
        {
            Iterator<SemanticClass> itcls=views.keySet().iterator();
            while (itcls.hasNext())
            {
                SemanticClass cls=itcls.next();
                Iterator<SemanticProperty> it=views.get(cls).keySet().iterator();
                while(it.hasNext())
                {
                    SemanticProperty prop=it.next();
                    SWBFormMgr mgr=mgrs.get(cls.getURI());
                    if(SWBFormMgr.MODE_EDIT.equals(views.get(cls).get(prop)))
                    {
                        //System.out.println("ProcessElement:"+prop);
                        mgr.processElement(request, prop);
                    }
                }

            }

        }
    }


    public String getFormName()
    {
        String uri;
        String frmname=null;
        uri=m_pinst.getId();
        return frmname=uri+"/form";
    }

    public void setOnSubmit(String onsubmit)
    {
        m_onsubmit=onsubmit;
    }

    public void addHiddenParameter(String key, String value)
    {
        if(key!=null && value!=null)hidden.put(key, value);
    }

    /**
     * Regresa input del tipo hidden requeridos para el processForm
     * @return
     */
    public String getFormHiddens()
    {
        StringBuffer ret=new StringBuffer();
        if(m_pinst!=null)ret.append("    <input type=\"hidden\" name=\""+SWBFormMgr.PRM_URI+"\" value=\""+m_pinst.getURI()+"\"/>\n");
        //if(m_cls!=null)ret.append("    <input type=\"hidden\" name=\""+PRM_CLS+"\" value=\""+m_cls.getURI()+"\"/>\n");
        if(m_mode!=null)ret.append("    <input type=\"hidden\" name=\""+SWBFormMgr.PRM_MODE+"\" value=\""+m_mode+"\"/>\n");
        //if(m_ref!=null)ret.append("    <input type=\"hidden\" name=\""+PRM_REF+"\" value=\""+m_ref.getURI()+"\"/>\n");
        Iterator<Map.Entry<String,String>> hit=hidden.entrySet().iterator();
        while(hit.hasNext())
        {
            Map.Entry entry=hit.next();
            ret.append("    <input type=\"hidden\" name=\""+entry.getKey()+"\" value=\""+entry.getValue()+"\"/>\n");
        }
        return ret.toString();
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

    public String getAction() {
        return m_action;
    }

    public void setAction(String action) {
        this.m_action = action;
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
        Iterator<SWBFormMgr> it=mgrs.values().iterator();
        SWBFormMgr mgr=null;
        while (it.hasNext())
        {
            SWBFormMgr amgr = it.next();
            if(amgr.getProperties().contains(prop))
            {
                mgr=amgr;
                break;
            }
        }
        return mgr.renderLabel(request, prop, mode);
    }

    /**
     * Render label.
     *
     * @param request the request
     * @param prop the prop
     * @param mode the mode
     * @return the string
     */
    public String renderLabel(HttpServletRequest request, SemanticProperty prop, SemanticClass cls, String mode)
    {
        SWBFormMgr mgr=mgrs.get(cls.getURI());
        return mgr.renderLabel(request, prop, mode);
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
        Iterator<SWBFormMgr> it=mgrs.values().iterator();
        SWBFormMgr mgr=null;
        while (it.hasNext())
        {
            SWBFormMgr amgr = it.next();
            if(amgr.getProperties().contains(prop))
            {
                mgr=amgr;
                break;
            }
        }
        return mgr.renderElement(request, prop, mode);
    }

    /**
     * Render element.
     *
     * @param request the request
     * @param prop the prop
     * @param mode the mode
     * @return the string
     */
    public String renderElement(HttpServletRequest request, SemanticProperty prop, SemanticClass cls, String mode)
    {
        SWBFormMgr mgr=mgrs.get(cls.getURI());
        return mgr.renderElement(request, prop, mode);
    }




    
}
