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
package org.semanticwb.process.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.*;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


public class ItemAwareMappingElement extends org.semanticwb.process.model.base.ItemAwareMappingElementBase 
{
    public ItemAwareMappingElement(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void process(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName)
    {
        //System.out.println("process:"+obj+" "+prop+" "+propName);
        if (obj == null) {
            obj = new SemanticObject();
        }    
        
        GenericObject gen=obj.createGenericInstance();
        CatchMessageable catchmsg=(CatchMessageable)gen;
        
        String action=((ActionCodeable)gen).getActionCode();
//        ThrowMessageable throwmsg=null;
//        Iterator<SemanticObject> it3=obj.getModel().listSubjects(ActionCodeable.swp_actionCode, action);
//        while (it3.hasNext())
//        {
//            SemanticObject semanticObject = it3.next();
//            if(semanticObject.instanceOf(ThrowMessageable.swp_ThrowMessageable))
//            {
//                throwmsg=(ThrowMessageable)semanticObject.createGenericInstance();
//            }
//        }
        
        List<ItemAwareMapping> arr=SWBUtils.Collections.copyIterator(catchmsg.listItemAwareMappings());

//        if(throwmsg!=null)
        {
            Iterator<ItemAware> it=((FlowNode)catchmsg).getContainer().listHerarquicalRelatedItemAware().iterator();
            while (it.hasNext())
            {
                ItemAware itemAware = it.next();
                
                String var=propName+"-"+itemAware.getId();
                
                String value=request.getParameter(var);
                
                if(value!=null && value.length()>0)
                {
                    //System.out.println(var+" "+value);
                    
                    SemanticObject sobjr=SemanticObject.createSemanticObject(SemanticObject.shortToFullURI(value));
                    if(sobjr!=null)
                    {
                        ItemAware ritemAware=(ItemAware)sobjr.createGenericInstance();
                        //System.out.println(itemAware+" "+ritemAware);
                        ItemAwareMapping mapping=getItemAwareMapping(arr, itemAware);
                        if(mapping!=null)
                        {
                            mapping.setRemoteItemAware(ritemAware);
                            arr.remove(mapping);
                        }else
                        {
                            mapping=ItemAwareMapping.ClassMgr.createItemAwareMapping(itemAware.getProcessSite());
                            mapping.setLocalItemAware(itemAware);
                            mapping.setRemoteItemAware(ritemAware);
                            catchmsg.addItemAwareMapping(mapping);
                            
                        }
                    }
                }
            }
            
            Iterator<ItemAwareMapping> it2=arr.iterator();
            while (it2.hasNext())
            {
                ItemAwareMapping mapping = it2.next();
                //System.out.println("removing:"+mapping);
                mapping.remove();
            }
            
        }
    }
    
    
    private ItemAwareMapping getItemAwareMapping(List arr, ItemAware itemAware)
    {
        Iterator<ItemAwareMapping> it=arr.iterator();
        while (it.hasNext())
        {
            ItemAwareMapping itemAwareMapping = it.next();
            if(itemAwareMapping.getLocalItemAware().equals(itemAware))
            {
                return itemAwareMapping;
            }
        }
        return null;
    }
    
    
    
    /* (non-Javadoc)
     * @see org.semanticwb.model.base.FormElementBase#renderElement(javax.servlet.http.HttpServletRequest, org.semanticwb.platform.SemanticObject, org.semanticwb.platform.SemanticProperty, java.lang.String, java.lang.String, java.lang.String)
     */
    /**
     * Render element.
     * 
     * @param request the request
     * @param obj the obj
     * @param prop the prop
     * @param type the type
     * @param mode the mode
     * @param lang the lang
     * @return the string
     */
    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName, String type,
                                String mode, String lang) {
        if (obj == null) {
            obj = new SemanticObject();
        }

        boolean DOJO   = false;

        if (type.equals("dojo")) {
            DOJO = true;
        }

        StringBuffer   ret          = new StringBuffer();
        String         name         = propName;
        String         label        = prop.getDisplayName(lang);
        SemanticObject sobj         = prop.getDisplayProperty();
        boolean        required     = prop.isRequired();
        String         pmsg         = null;
        String         imsg         = null;
        String         selectValues = null;
        boolean        disabled     = false;

        if (sobj != null) {
            DisplayProperty dobj = new DisplayProperty(sobj);

            pmsg         = dobj.getPromptMessage();
            imsg         = dobj.getInvalidMessage();
            selectValues = dobj.getDisplaySelectValues(lang);
            disabled     = dobj.isDisabled();
        }

        if (DOJO) {
            if (imsg == null) {
                if (required) {
                    imsg = label + " es requerido.";

                    if (lang.equals("en")) {
                        imsg = label + " is required.";
                    }
                } else {
                    imsg = "Dato invalido.";

                    if (lang.equals("en")) {
                        imsg = "Invalid data.";
                    }
                }
            }

            if (pmsg == null) {
                pmsg = "Captura " + label + ".";

                if (lang.equals("en")) {
                    pmsg = "Enter " + label + ".";
                }
            }
        }

        String ext = "";

        if (disabled) {
            ext += " disabled=\"disabled\"";
        }

        if (prop.isObjectProperty()) {
            ArrayList<String> vals   = new ArrayList();
            String            auxs[] = request.getParameterValues(propName);

            if (auxs == null) {
                auxs = new String[0];
            }

            for (int x = 0; x < auxs.length; x++) {
                vals.add(auxs[x]);
            }

            Iterator<SemanticObject> it2 = obj.listObjectProperties(prop);

            while (it2.hasNext()) {
                SemanticObject semanticObject = it2.next();

                // System.out.println("semanticObject:"+semanticObject+" vals:"+vals);
                vals.add(semanticObject.getURI());
            }

            String value = obj.getDisplayName(lang);

            if (mode.equals("edit") || mode.equals("create")) 
            {
                GenericObject gen=obj.createGenericInstance();
                
                if(gen instanceof CatchMessageable)
                {
                    CatchMessageable catchmsg=(CatchMessageable)gen;
                    
                    List<ItemAwareMapping> arr=SWBUtils.Collections.copyIterator(catchmsg.listItemAwareMappings());
                    
                    String action=((ActionCodeable)gen).getActionCode();
                    ThrowMessageable throwmsg=null;
                    
                    Iterator<SemanticObject> it3=obj.getModel().listSubjects(ActionCodeable.swp_actionCode, action==null?"_NULL_":action);
                    while (it3.hasNext())
                    {
                        SemanticObject semanticObject = it3.next();
                        if(semanticObject.instanceOf(ThrowMessageable.swp_ThrowMessageable))
                        {
                            throwmsg=(ThrowMessageable)semanticObject.createGenericInstance();
                        }
                    }
                    
                    if(throwmsg!=null && throwmsg.listMessageItemAwares().hasNext())
                    {
                        ret.append("<table border=\"0\">");
                        Iterator<ItemAware> it=((FlowNode)catchmsg).getContainer().listHerarquicalRelatedItemAware().iterator();
                        while (it.hasNext())
                        {
                            ItemAware itemAware = it.next();
                            if (itemAware.getDataObjectClass() != null) { //Si no lo han configurado no se muestra
                                ret.append("<tr>");
                                ret.append("<td>");

                                String selected="";
                                ItemAwareMapping mapping=getItemAwareMapping(arr, itemAware);
                                if(mapping!=null)selected=mapping.getRemoteItemAware().getShortURI();

                                ret.append("<select name=\""+propName+"-"+itemAware.getId()+"\"");                            
                                if (DOJO) {
                                    ret.append(" dojoType=\"dijit.form.FilteringSelect\" autoComplete=\"true\" invalidMessage=\""
                                            + imsg + "\" value=\""+selected+"\"");
                                }
                                ret.append(">");
                                ret.append("<option value=\"\"></option>");
                                Iterator<ItemAware> it4=throwmsg.listMessageItemAwares();
                                while (it4.hasNext())
                                {
                                    ItemAware ritemAware = it4.next();
                                    if (ritemAware.getDataObjectClass() != null && itemAware.getDataObjectClass().equals(ritemAware.getDataObjectClass())) {
                                        ret.append("<option value=\""+ritemAware.getShortURI()+"\">");
                                        ret.append(ritemAware.getName());
                                        ret.append("</option>");
                                    }
                                }
                                ret.append("</select>");
                                ret.append("</td>");

                                ret.append("<td>");
                                ret.append("->");
                                ret.append(itemAware.getName());
                                ret.append("</td>");                            

                                ret.append("</tr>");
                            }
                        }
                        ret.append("</table>");
                    }
                }
            } else if (mode.equals("view")) 
            {
                
            }
        }

        return ret.toString();
    }    
    
}
