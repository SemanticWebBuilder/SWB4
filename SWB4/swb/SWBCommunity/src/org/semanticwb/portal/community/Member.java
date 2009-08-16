package org.semanticwb.portal.community;

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
        Member ret=null;
        if(page instanceof MicroSite)
        {
            if(user.isRegistered())
            {
                Iterator<Member> it=Member.listMemberByUser(user,page.getWebSite());
                while(it.hasNext())
                {
                    Member mem=it.next();
                    if(mem.getMicroSite().equals(page))
                    {
                       ret=mem;
                    }
                }
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
