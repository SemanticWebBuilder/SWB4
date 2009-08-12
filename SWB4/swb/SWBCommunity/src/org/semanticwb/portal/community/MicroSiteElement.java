package org.semanticwb.portal.community;


public class MicroSiteElement extends org.semanticwb.portal.community.base.MicroSiteElementBase 
{
    public static int VIS_ALL=0;
    public static int VIS_MEMBERS_ONLY=1;
    public static int VIS_FRIENDS=2;
    public static int VIS_JUST_ME=3;

    public MicroSiteElement(org.semanticwb.platform.SemanticObject base)
    {
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
     * Solo Ver el elemento
     * @param ele
     * @param mem
     * @return
     */
    public boolean canView(Member mem)
    {
        return true;
    }

    /**
     * Solo ver y agregar comentarios
     * @param ele
     * @param mem
     * @return
     */
    public boolean canComment(Member mem)
    {
        return true;
    }

    /**
     * Ver, Agregar comentarios y modificar (modificarr, eliminar) el elemento
     * @param ele
     * @param mem
     * @return
     */
    public boolean canModify(Member mem)
    {
        return true;
    }



}
