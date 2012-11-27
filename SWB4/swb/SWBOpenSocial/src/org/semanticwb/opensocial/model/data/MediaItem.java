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
package org.semanticwb.opensocial.model.data;

import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebSite;


public class MediaItem extends org.semanticwb.opensocial.model.data.base.MediaItemBase 
{
    private static final Logger log = SWBUtils.getLogger(MediaItem.class);
    public MediaItem(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static MediaItem getMediaItem(String id,Person person,WebSite site)
    {
        String id_=id+"@"+person.getId();
        return MediaItem.ClassMgr.getMediaItem(id_, site);
    }
    public static MediaItem createMediaItem(String id,Person person,WebSite site)
    {
        String id_=id+"@"+person.getId();
        return MediaItem.ClassMgr.createMediaItem(id_, site);
    }
    public JSONObject toJSONObject()
    {
        JSONObject person=new JSONObject();
        try
        {
            person.put("id", this.getId());
        }
        catch(JSONException e)
        {
            log.debug(e);
        }
        return person;
    }
}
