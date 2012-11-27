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
package org.semanticwb.portal.resources.sem.contact;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.SWBActionResponse;


public class Contact extends org.semanticwb.portal.resources.sem.contact.base.ContactBase 
{
    public Contact(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void edit(HashMap<String, String> params) {
        setOrganization(SWBUtils.TEXT.nullValidate(params.get("org"),""));
        setPosition(SWBUtils.TEXT.nullValidate(params.get("pos"),""));
        if(params.get("photo")!=null)
            setPhoto(params.get("photo"));
        setEmail(SWBUtils.TEXT.nullValidate(params.get("email"),""));
        setEmail2(SWBUtils.TEXT.nullValidate(params.get("email2"),""));
        setEmail3(SWBUtils.TEXT.nullValidate(params.get("email3"),""));
        setWebpage(SWBUtils.TEXT.nullValidate(params.get("wp"),""));
    }

    public String getFullName() {
        StringBuilder sb = new StringBuilder();
        if(getTreatment()!=null)
            sb.append(getTreatment());
        if(getFirstName()!=null)
            sb.append(" ").append(getFirstName());
        if(getLastName()!=null)
            sb.append(" ").append(getLastName());
        if(getSecondLastName()!=null)
            sb.append(" ").append(getSecondLastName());
        return sb.toString();
    }

    void init(WebSite model) {
        setWorkPhone(Phone.ClassMgr.createPhone(model));
        setWorkPhone2(Phone.ClassMgr.createPhone(model));
        setHomePhone(Phone.ClassMgr.createPhone(model));
        setFaxPhone(Phone.ClassMgr.createPhone(model));
        setMobilePhone(Phone.ClassMgr.createPhone(model));
        setEveningPhone(Phone.ClassMgr.createPhone(model));
        setWorkAddress(Address.ClassMgr.createAddress(model));
        setHomeAddress(Address.ClassMgr.createAddress(model));
        setOtherAddress(Address.ClassMgr.createAddress(model));
    }

    @Override
    public String toString() {
        StringBuilder c = new StringBuilder();
        if(getTreatment()!=null)
            c.append(getTreatment()).append(". ");
        if(getFirstName()!=null)
            c.append(getFirstName());
        if(getLastName()!=null)
            c.append(" ").append(getLastName());
        if(getSecondLastName()!=null)
            c.append(" ").append(getSecondLastName());
        if(getOrganization()!=null)
            c.append(",").append(getOrganization());
        if(getPosition()!=null)
            c.append(",").append(getPosition());
        if(getWorkPhone()!=null)
            c.append(",").append(getWorkPhone().toString());
        if(getWorkPhone2()!=null)
            c.append(",").append(getWorkPhone2().toString());
        if(getHomePhone()!=null)
            c.append(",").append(getHomePhone().toString());
        if(getEmail()!=null)
            c.append(",").append(getEmail());
        if(getWorkAddress()!=null)
            c.append(",").append(getWorkAddress().toString());
        return c.toString();
    }
}
