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
package org.semanticwb.opensocial.services;

import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebSite;
import org.semanticwb.opensocial.model.Gadget;
import org.semanticwb.opensocial.model.data.Group;
import org.semanticwb.opensocial.model.data.Person;
import org.semanticwb.opensocial.resources.RPCException;

/**
 *
 * @author victor.lorenzana
 */
public class GroupsService implements Service {

    private static final Logger log = SWBUtils.getLogger(GroupsService.class);
    public JSONObject get(Person person, JSONObject params, WebSite site, Gadget gadget) throws RPCException
    {
        String groupid=null;
        if (params.optString("groupId") != null && !params.optString("groupId").equals(""))
        {
            groupid = params.optString("groupId");
        }
        if(groupid==null) // all
        {
            JSONArray array=new JSONArray();
            JSONObject obj=new JSONObject();
            try
            {
                obj.put("entry", array);

                Iterator<Group> groups=person.listGroups();
                while(groups.hasNext())
                {
                    Group group=groups.next();
                    array.put(group.toJSONObject());
                }
            }
            catch(JSONException e)
            {
                log.debug(e);
            }
            return obj;
        }
        else
        {            
            Group group = Group.createGroup(groupid, person, site);
            if(group!=null)
            {
                return group.toJSONObject();
            }
        }
        return null;
    }

    public JSONObject update(Person person, JSONObject params, WebSite site, Gadget gadget) throws RPCException
    {
        String groupid=null;
        if (params.optString("groupId") != null && !params.optString("groupId").equals(""))
        {
            groupid = params.optString("groupId");
        }
        if(groupid!=null)
        {
            if(Group.getGroup(groupid, person, site)!=null)
            {
                Group group=Group.getGroup(groupid, person, site);
                return group.toJSONObject();
            }
        }
        return null;
    }

    public JSONObject delete(Person person, JSONObject params, WebSite site, Gadget gadget) throws RPCException
    {
        String groupid=null;
        if (params.optString("groupId") != null && !params.optString("groupId").equals(""))
        {
            groupid = params.optString("groupId");
        }
        if(groupid!=null)
        {
            if(Group.getGroup(groupid, person, site)!=null)
            {
                Group.getGroup(groupid, person, site).remove();
            }
        }
        return null;
    }

    public JSONObject create(Person person, JSONObject params, WebSite site, Gadget gadget) throws RPCException
    {
        String groupid=null;
        if (params.optString("groupId") != null && !params.optString("groupId").equals(""))
        {
            groupid = params.optString("groupId");
        }
        if(groupid!=null)
        {
            if(Group.getGroup(groupid, person, site)==null)
            {                
                Group group=Group.createGroup(groupid, person, site);
                person.addGroup(group);
                return group.toJSONObject();
            }
        }
        return null;
    }

    

    

}
