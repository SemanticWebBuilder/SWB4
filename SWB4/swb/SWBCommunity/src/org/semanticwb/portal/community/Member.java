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

import java.util.ArrayList;
import java.util.Iterator;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.platform.SemanticObject;


public class Member extends org.semanticwb.portal.community.base.MemberBase 
{
    public static int LEVEL_NO_ACCESS=0;
    public static int LEVEL_VIEW=1;         //SOLO VER
    public static int LEVEL_COMMENT=2;      //SOLO Comentar, Ranker, Notifi, Abuso
    public static int LEVEL_EDIT=3;         //Agregar, modificar los tuyos
    public static int LEVEL_ADMIN=4;        //Agregar y Modificar todo
    public static int LEVEL_OWNER=5;        //Propietario

    public Member(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static Member getMember(User user, WebPage page)
    {
        ArrayList<Member> membersToRemove=new ArrayList<Member>();
        Member ret=null;
        if(page instanceof MicroSite)
        {
            if(user.isRegistered())
            {
                Iterator<Member> it=Member.ClassMgr.listMemberByUser(user,page.getWebSite());
                while(it.hasNext())
                {
                    Member mem=it.next();
                    if(mem.getMicroSite()==null)
                    {
                        membersToRemove.add(mem);
                    }
                    else
                    {
                        if(mem.getMicroSite().equals(page))
                        {
                           ret=mem;
                        }
                    }
                    
                }
            }
            for(Member member : membersToRemove)
            {
                member.remove();
            }
            if(ret==null)
            {
                ret=new Member(new SemanticObject(page.getSemanticObject().getModel(),Member.sclass));
                ret.setMicroSite((MicroSite)page);
                ret.setUser(user);
                //TODO: Comentar esto cuando se tengan miembros
                //ret.setAccessLevel(LEVEL_ADMIN);
            }
        }else if(page instanceof MicroSiteWebPageUtil)
        {
            MicroSite site=MicroSite.getMicroSite(page);
            ret=getMember(user,site);
        }
        return ret;
    }

    public boolean canView()
    {
        return getAccessLevel()>=LEVEL_VIEW;
    }

    /**
     * Editar
     * @return
     */
    public boolean canAdd()
    {
        return getAccessLevel()>=LEVEL_EDIT;
    }

}
