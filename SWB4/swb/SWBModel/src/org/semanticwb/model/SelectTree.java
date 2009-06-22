package org.semanticwb.model;

import java.util.Iterator;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.SWBPlatform;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


public class SelectTree extends org.semanticwb.model.base.SelectTreeBase 
{
    public SelectTree(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
/*
    private String addPage(WebPage page, String selected, String lang, String separator)
    {
        String ret="<option value=\""+page.getURI()+"\" ";
        if(page.getURI().equals(selected))ret+="selected";
        ret+=">"+separator+page.getDisplayName(lang)+"</option>";

        if(separator.length()==0)separator=">";

        Iterator<WebPage> it=page.listVisibleChilds(lang);
        while(it.hasNext())
        {
            WebPage child=it.next();
            ret+=addPage(child,selected,lang,"--"+separator);
        }
        return ret;
    }
*/
    private String addObject(SemanticObject obj, String selected, String lang, String separator)
    {
        String ret="<option value=\""+obj.getURI()+"\" ";
        if(obj.getURI().equals(selected))ret+="selected";
        ret+=">"+separator+obj.getDisplayName(lang)+"</option>";

        if(separator.length()==0)separator=">";

        Iterator<SemanticObject> it=obj.listHerarquicalChilds();
        while(it.hasNext())
        {
            SemanticObject child=it.next();
            ret+=addObject(child,selected,lang,"--"+separator);
        }
        return ret;
    }
    

    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type, String mode, String lang)
    {
        if(obj==null)obj=new SemanticObject();
        boolean IPHONE=false;
        boolean XHTML=false;
        boolean DOJO=false;
        if(type.equals("iphone"))IPHONE=true;
        else if(type.equals("xhtml"))XHTML=true;
        else if(type.equals("dojo"))DOJO=true;

        StringBuffer ret=new StringBuffer();
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

        if(DOJO)
        {
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
        }

        if(prop.isObjectProperty())
        {
            SemanticObject val=null;
            String aux=request.getParameter(prop.getName());
            if(aux!=null)val=SemanticObject.createSemanticObject(aux);
            else val=obj.getObjectProperty(prop);
            String uri="";
            String value="";
            if(val!=null)
            {
                uri=val.getURI();
                value=obj.getDisplayName(lang);
            }
            if(mode.equals("edit") || mode.equals("create") )
            {
                ret.append("<select name=\""+name+"\"");
                if(DOJO)ret.append(" dojoType=\"dijit.form.FilteringSelect\" autoComplete=\"true\" invalidMessage=\""+imsg+"\">");
                //onChange="dojo.byId('oc1').value=arguments[0]"
                if(isBlankSuport())ret.append("<option value=\"\"></option>");
                SemanticClass cls=prop.getRangeClass();
                Iterator<SemanticObject> it=null;
                if(cls!=WebPage.sclass)
                {
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
                    boolean hp=false;
                    if(cls!=null)hp=cls.hasHerarquicalProperties();
                    //System.out.println("cls:"+cls+" hp:"+hp+" "+cls.hasHerarquicalProperties()+" "+cls.hasInverseHerarquicalProperties());
                    while(it.hasNext())
                    {
                        SemanticObject sob=it.next();
                        if(hp)
                        {
                            if(!sob.hasHerarquicalParents())
                            {
                                ret.append(addObject(sob, uri, lang, ""));
                            }
                        }else
                        {
                            ret.append("<option value=\""+sob.getURI()+"\" ");
                            if(sob.getURI().equals(uri))ret.append("selected");
                            ret.append(">"+sob.getDisplayName(lang)+"</option>");
                        }
                    }
                }
                else
                {
                    WebSite site=SWBContext.getWebSite(obj.getModel().getName());
                    if(site!=null)
                    {
                        WebPage home=site.getHomePage();
                        ret.append(addObject(home.getSemanticObject(), uri, lang, ""));
                    }
                }
                ret.append("</select>");
            }else if(mode.equals("view"))
            {
                ret.append("<span _id=\""+name+"\" name=\""+name+"\">"+value+"</span>");
            }
        }else
        {
            if(selectValues!=null)
            {
                String value=request.getParameter(prop.getName());
                if(value==null)value=obj.getProperty(prop);
                ret.append("<select name=\""+name+"\"");
                if(DOJO)ret.append(" dojoType=\"dijit.form.FilteringSelect\" autoComplete=\"true\" invalidMessage=\""+imsg+"\">");
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
                    ret.append("<option value=\""+id+"\" ");
                    if(id.equals(value))ret.append("selected");
                    ret.append(">"+val+"</option>");
                }
                ret.append("</select>");
            }
        }

        return ret.toString();
    }


}
