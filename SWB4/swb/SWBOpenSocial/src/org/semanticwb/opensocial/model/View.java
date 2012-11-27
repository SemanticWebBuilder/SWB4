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
package org.semanticwb.opensocial.model;

import java.net.URL;
import org.json.JSONObject;

/**
 *
 * @author victor.lorenzana
 */
public class View {
    private String name="default";
    private int preferredHeight;
    private String type="html";
    private boolean scrolling;
    private int preferredWidth;
    private String content;
    private URL urlcontent;
    public View(String name)
    {
        this.name=name;
    }
    public void setContent(String  content)
    {
        this.content=content;
    }
    public String getContent()
    {
        return content;
    }
    public URL getUrlcontent()
    {
        return urlcontent;
    }
    public void setType(String  type)
    {
        this.type=type;
    }
    public void setUrlContent(URL  urlcontent)
    {
        this.urlcontent=urlcontent;
    }
    public void setScrolling(boolean scrolling)
    {
        this.scrolling=scrolling;
    }
    public void setPreferredWidth(int preferredWidth)
    {
        this.preferredWidth=preferredWidth;
    }
    public void setPreferredHeight(int preferredHeight)
    {
        this.preferredHeight=preferredHeight;
    }
    public String getName()
    {
        return name;
    }
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final View other = (View) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 89 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }


    public JSONObject toJSONObject()
    {        
        JSONObject content=new JSONObject();
        try
        {
            content.put("preferredHeight", preferredHeight);
            content.put("type", type);
            content.put("quirks", scrolling);
            content.put("preferredWidth", preferredWidth);
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return content;
    }
}
