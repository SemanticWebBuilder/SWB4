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
package org.semanticwb.model;

import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


   /**
   * Elemento usado para validar la posibilidad de ser o no activado del elemento, ejemplo: cuando el elmenento esta en flujo no puede ser activado 
   */
public class Activate extends org.semanticwb.model.base.ActivateBase 
{
    public Activate(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName, String type, String mode, String lang) 
    {
        
        if (obj == null) {
            obj = new SemanticObject();
        }

        boolean DOJO   = false;

//        if (type.equals("iphone")) {
//            IPHONE = true;
//        } else if (type.equals("xhtml")) {
//            XHTML = true;
//        } else
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

        String ext = "";
        
        if(obj.instanceOf(Resource.sclass))
        {
            Resource res = (Resource) obj.createGenericInstance();

            boolean isInFlow = isInFlow(res);
            boolean needAuthorization = needAnAuthorization(res);
            
            disabled=true;

            if (!isInFlow && !needAuthorization) {
                disabled = false;
            }
            if (!isInFlow && needAuthorization) {
                disabled = true;
                //send2Flow = true;
            }                    
        }
                        

        if (disabled) {
            ext += " disabled=\"disabled\"";
        }

        if (prop.isDataTypeProperty()) 
        {
            if (prop.isBoolean()) 
            {
                String  checked = "";
                boolean value   = false;
                String  aux     = request.getParameter(propName);

                if (aux != null) {
                    value = true;
                } else {
                    value = obj.getBooleanProperty(prop);
                }

                if (value) {
                    checked = "checked=\"checked\"";
                }

                ret.append("<input type=\"checkbox\" id_=\"" + name + "\" name=\"" + name + "\" " + checked);

                if (DOJO) {
                    ret.append(" dojoType=\"dijit.form.CheckBox\"");
                }

                if (DOJO) {
                    ret.append(" required=\"" + required + "\"");
                }

//              + " propercase=\"true\""
                if (DOJO) {
                    ret.append(" promptMessage=\"" + pmsg + "\"");
                }

                if (DOJO) {
                    ret.append(" invalidMessage=\"" + imsg + "\"");
                }

//              + " trim=\"true\""
                ret.append(ext);

                if (mode.equals("view")) {
                    ret.append(" disabled=\"disabled\"");
                }                

                ret.append("/>");
                
                if (!mode.equals("view")) 
                {
                    if(disabled && value)
                    {
                        ret.append("<input type=\"hidden\" name=\"" + name + "\" value=\"true\"/>");
                    }                    
                }        
                
            } 
        } 

        // System.out.println("ret:"+ret);
        return ret.toString();        
    }
    
    
    /**
     * Checks if is in flow.
     * 
     * @param resource the resource
     * @return true, if is in flow
     */
    public boolean isInFlow(Resource resource)
    {
        if (resource.getPflowInstance() == null)
        {
            return false;
        }
        else
        {
            if(resource.getPflowInstance().getStatus() == 3 && resource.getPflowInstance().getStep() != null)
            {
                return true;
            }
            if (!(resource.getPflowInstance().getStatus() == 3 || resource.getPflowInstance().getStatus() == 2 || resource.getPflowInstance().getStep() == null))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Need an authorization.
     * 
     * @param resource the resource
     * @return true, if successful
     */
    public boolean needAnAuthorization(Resource resource)
    {
        Iterator<Resourceable> resourceables = resource.listResourceables();
        while (resourceables.hasNext())
        {
            Resourceable resourceable = resourceables.next();
            if (resourceable instanceof WebPage)
            {
                WebPage page = (WebPage) resourceable;
                Iterator<PFlowRef> refs = page.listInheritPFlowRefs();
                while (refs.hasNext())
                {
                    PFlowRef ref = refs.next();
                    if (ref.isActive())
                    {
                        PFlow pflow = ref.getPflow();
                        if (pflow != null)
                        {
                            String typeresource = resource.getResourceType().getId();
                            Document docflow = SWBUtils.XML.xmlToDom(pflow.getXml());
                            Element workflow = (Element) docflow.getElementsByTagName("workflow").item(0);
                            NodeList resourceTypes = workflow.getElementsByTagName("resourceType");
                            for (int ires = 0; ires < resourceTypes.getLength(); ires++)
                            {
                                Element eres = (Element) resourceTypes.item(ires);
                                String iresw = eres.getAttribute("id");

                                if (iresw.equals(typeresource))
                                {
                                    if (resource.getPflowInstance() == null)
                                    {
                                        return true;
                                    }
                                    else
                                    {
                                        PFlowInstance instance = resource.getPflowInstance();
                                        switch (instance.getStatus())
                                        {
                                            case -1:
                                            case 0: //no enviado
                                                return true;
                                            case 3: //rechazado
                                                return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }    
    
    
}
