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
package org.semanticwb.portal.community;

import java.io.IOException;
import java.io.Writer;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;


public class MicroSiteElement 
        extends org.semanticwb.portal.community.base.MicroSiteElementBase {
    
    private static Logger log = SWBUtils.getLogger(MicroSiteElement.class);
    public static int VIS_ALL=0;
    public static int VIS_MEMBERS_ONLY=1;
    public static int VIS_FRIENDS=2;
    public static int VIS_JUST_ME=3;

    private long views=0;
    private long timer;                     //valores de sincronizacion de views, hits
    private static long time;               //tiempo en milisegundos por cada actualizacion
    private boolean viewed = false;

    
    static {
        time = 1000L * Long.parseLong(
                (String) SWBPlatform.getEnv("swb/accessLogTime", "600"));
    }

    public MicroSiteElement(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

//    /**
//     * return
//     * 0=no access
//     * 1=view
//     * 2=rank
//     * 3=edit
//     * @param ele
//     * @param mem
//     * @return
//     */
//    public static int getAccessLevel(Member mem)
//    {
//        return 3;
//        /*
//        int ret=0;
//        int vis=ele.getVisibility();
//        if(mem!=null)
//        if(vis==MicroSiteUtils.VIS_ALL)ret=true;
//        else if(vis==MicroSiteUtils.VIS_MEMBERS_ONLY && mem!=null)ret=true;
//        //TODO: Friends
//        else if(vis==MicroSiteUtils.VIS_JUST_ME && mem!=null)
//        {
//            if(ele.getCreator().equals(mem.getUser()))ret=true;
//        }
//        return ret;
//        */
//    }

    /**
     * Indica si el <code>Member</code> recibido puede ver el elemento
     * @param ele
     * @param mem
     * @return <code>true</code> si el miembro tiene derecho a ver el elemento, <code>false</code>
     * de lo contrario.
     */
    public boolean canView(Member mem) {
        
        boolean ret = false;
        int vis = getVisibility();
        if (vis == VIS_ALL) {
            ret = true;
        } else if (vis == VIS_MEMBERS_ONLY && mem.canView()) {
            ret = true;
        } else if (vis == VIS_JUST_ME && getCreator().equals(mem.getUser())) {
            ret = true;
        }
        return ret;
    }

    /**
     * Indica si el <code>Member</code> recibido puede ver y agregar comentarios
     * @param ele
     * @param mem
     * @return <code>true</code> si el miembro tiene derecho a ver y agregar el elemento, 
     * <code>false</code> de lo contrario.
     */
    public boolean canComment(Member mem) {
        
        boolean ret = false;
        if (mem.canView()) {
            ret = true;
        }
        return ret;
    }

    /**
     * Indica si el <code>Member</code> recibido puede ver, agregar comentarios 
     * y modificar (modificar o eliminar) el elemento
     * @param ele
     * @param mem
     * @return <code>true</code> si el miembro tiene derecho a ver, agregar comentarios 
     * y modificar (modificar o eliminar) el elemento
     */
    public boolean canModify(Member mem) {
        
        boolean ret = false;
        if (mem.getAccessLevel() >= Member.LEVEL_ADMIN) {
            ret = true;
        } else if (mem.getUser().equals(getCreator())) {
            ret = true;
        }
        return ret;
    }

    /**
     * Obtiene el n&uacute;mero de veces que este elemento ha sido desplegado.
     * @return el n&uacute;mero de veces que este elemento ha sido desplegado.
     */
    @Override
    public long getViews() {
        
        if (views == 0) {
            views = super.getViews();
        }
        return views;
    }

    /**
     * Incrementa en uno, el n&uacute;mero de veces que ha sido desplegado este elemento.
     * @return <code>true</code> si el elemento fue desplegado por última vez hace <code>time</code>
     * milisegundos; <code>false</code> de lo contrario.
     */
    public boolean incViews() {
        
        //System.out.println("incViews:"+views);
        viewed = true;
        if (views == 0) {
            views = getViews();
        }
        views += 1;
        long t = System.currentTimeMillis() - timer;
        if (t > time || t < -time) {
            //TODO: evalDate4Views();
            return true;
        }
        return false;
    }

    /**
     * Fija el valor de las veces que se ha mostrado este elemento.
     * @param views n&uacute;mero de veces que se ha mostrado el elemento.
     */
    @Override
    public void setViews(long views) {

        //System.out.println("setViews:"+views);
        super.setViews(views);
        this.views = views;
    }

    /**
     * Actualiza el estado del n&uacute;mero de veces que se ha mostrado el elemento. En
     * base al estado de <code>viewed</code>
     */
    public void updateViews() {

        //System.out.println("updateViews:"+views);
        if (viewed) {
            timer = System.currentTimeMillis();
            if (views > 0) {
                setViews(views);
            }
            viewed = false;
        }
    }

    /**
     * Muestra la información utilizada por las herramientas o recursos generales para
     * los elementos de comunidades, como calificar y reporte de abuso sobre el elemento
     * o comentarios relacionados al mismo.
     * @param request petición del usuario a atender
     * @param out para agregar los datos a desplegar
     * @param paramRequest para obtener referencias a objetos internos
     * @throws org.semanticwb.portal.api.SWBResourceException
     * @throws java.io.IOException
     */
    public void renderGenericElements(HttpServletRequest request,
            HttpServletResponse response, SWBParamRequest paramRequest)
            throws SWBResourceException, IOException {
        

        String path="/swbadmin/jsp/microsite/RenderGenericElements/RenderGenericElements.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(path);
        try
        {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("MicroSiteElement", this);
            dis.include(request, response);
        }
        catch (Exception e)
        {
            log.error(e);
        }
    }

    public String getURL()
    {
        return "#";
    }
    
    public WebPage getWebPage()
    {
        return null;
    }
}
