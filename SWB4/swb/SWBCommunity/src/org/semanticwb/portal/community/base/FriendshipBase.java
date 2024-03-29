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
package org.semanticwb.portal.community.base;


public abstract class FriendshipBase extends org.semanticwb.portal.community.UserRelationship 
{
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_hasFriend=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasFriend");
    public static final org.semanticwb.platform.SemanticClass swbcomm_Friendship=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Friendship");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Friendship");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.portal.community.Friendship> listFriendships(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Friendship>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.portal.community.Friendship> listFriendships()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Friendship>(it, true);
        }

        public static org.semanticwb.portal.community.Friendship createFriendship(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.portal.community.Friendship.ClassMgr.createFriendship(String.valueOf(id), model);
        }

        public static org.semanticwb.portal.community.Friendship getFriendship(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.community.Friendship)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.portal.community.Friendship createFriendship(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.community.Friendship)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeFriendship(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasFriendship(String id, org.semanticwb.model.SWBModel model)
        {
            return (getFriendship(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.portal.community.Friendship> listFriendshipByFriend(org.semanticwb.model.User hasfriend,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Friendship> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_hasFriend, hasfriend.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.Friendship> listFriendshipByFriend(org.semanticwb.model.User hasfriend)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Friendship> it=new org.semanticwb.model.GenericIterator(hasfriend.getSemanticObject().getModel().listSubjects(swbcomm_hasFriend,hasfriend.getSemanticObject()));
            return it;
        }
    }

    public FriendshipBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.User> listFriends()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.User>(getSemanticObject().listObjectProperties(swbcomm_hasFriend));
    }

    public boolean hasFriend(org.semanticwb.model.User user)
    {
        boolean ret=false;
        if(user!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swbcomm_hasFriend,user.getSemanticObject());
        }
        return ret;
    }

    public void addFriend(org.semanticwb.model.User value)
    {
        getSemanticObject().addObjectProperty(swbcomm_hasFriend, value.getSemanticObject());
    }

    public void removeAllFriend()
    {
        getSemanticObject().removeProperty(swbcomm_hasFriend);
    }

    public void removeFriend(org.semanticwb.model.User user)
    {
        getSemanticObject().removeObjectProperty(swbcomm_hasFriend,user.getSemanticObject());
    }

    public org.semanticwb.model.User getFriend()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_hasFriend);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }
}
