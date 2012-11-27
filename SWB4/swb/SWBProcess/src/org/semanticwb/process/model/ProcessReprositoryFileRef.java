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

import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.model.DisplayProperty;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


public class ProcessReprositoryFileRef extends org.semanticwb.process.model.base.ProcessReprositoryFileRefBase 
{
    public ProcessReprositoryFileRef(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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

        //boolean IPHONE = false;
        //boolean XHTML  = false;
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

        if (prop.isObjectProperty()) {
//            if (!name.startsWith("has"))
//            {
//                SemanticObject value = null;
//                String         aux   = request.getParameter(propName);
//
//                if (aux != null) {
//                    value = SemanticObject.createSemanticObject(aux);
//                } else {
//                    value = obj.getObjectProperty(prop);
//                }
//                
//                if(value!=null && value.instanceOf(RepositoryFile.sclass))
//                {
//                    RepositoryFile file=(RepositoryFile)value.createGenericInstance();
//                    
//                    String pageurl=file.getRepositoryDirectory().getUrl();
//                    //System.out.println("file:"+file+" "+file.getRepositoryDirectory()+" "+file.getRepositoryDirectory().getResource());
//                    String fileurl=file.getRepositoryDirectory().getUrl()+"/_rid/"+file.getRepositoryDirectory().getResource().getId()+"/_mto/3/_mod/getFile?fid="+file.getId()+"&verNum="+file.getLastVersion().getVersionNumber();
//                                        
//                    //http://localhost:8080/es/process/GP-Direccion/_rid/162/_mto/3/_mod/getFile?fid=22&verNum=2
//                    
//                    ret.append("<span>");
//
//                    if (value != null) {
//                        ret.append("<a href=\""+ pageurl + "\" target=\"_new\">"
//                                   + file.getRepositoryDirectory().getDisplayName(lang) + "</a>");
//                        ret.append(" / ");
//                        ret.append("<a href=\"" + fileurl + "\">"
//                                   + value.getDisplayName(lang) + "</a>");
//                    }
//                    ret.append("</span>");
//                }else
//                {
//                    ret.append("<span>");
//                    ret.append("-");
//                    ret.append("</span>");                    
//                }
//            }else
            {
                SemanticObject value = null;
                String         aux   = request.getParameter(propName);

                if (aux != null) {
                    value = SemanticObject.createSemanticObject(aux);
                }
                
                Iterator<SemanticObject> it=obj.listObjectProperties(prop);
                
                while(it.hasNext())
                {
                    value=it.next();
                
                    if(value!=null && value.instanceOf(RepositoryFile.sclass))
                    {
                        RepositoryFile file=(RepositoryFile)value.createGenericInstance();

                        String pageurl=file.getRepositoryDirectory().getUrl();
                        //System.out.println("file:"+file+" "+file.getRepositoryDirectory()+" "+file.getRepositoryDirectory().getResource());
                        String fileurl=file.getRepositoryDirectory().getUrl()+"/_rid/"+file.getRepositoryDirectory().getResource().getId()+"/_mto/3/_mod/getFile?fid="+file.getId()+"&verNum="+file.getLastVersion().getVersionNumber();

                        //http://localhost:8080/es/process/GP-Direccion/_rid/162/_mto/3/_mod/getFile?fid=22&verNum=2

                        ret.append("<span>");

                        if (value != null) {
                            ret.append("<a href=\""+ pageurl + "\" target=\"_new\">"
                                       + file.getRepositoryDirectory().getDisplayName(lang) + "</a>");
                            ret.append(" / ");
                            ret.append("<a href=\"" + fileurl + "\">"
                                       + value.getDisplayName(lang) + "</a>");
                        }
                        ret.append("</span><br/>");
                    }else
                    {
                        ret.append("<span>");
                        ret.append("-");
                        ret.append("</span>");                    
                    }                
                }
            }
        }

        // System.out.println("ret:"+ret);
        return ret.toString();
    }    
    
}
