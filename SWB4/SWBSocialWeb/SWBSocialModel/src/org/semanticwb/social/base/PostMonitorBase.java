package org.semanticwb.social.base;


public abstract class PostMonitorBase extends org.semanticwb.model.SWBClass 
{
   /**
   * En este objeto se guardara el identificador que es asignado para cada post en cada una de las redes sociales, es decir, si un mismo post se envía hacia mas de una red social, cada una de esas redes sociales daran un identificador unico para ese post en esa red social, este lo tenemos que guardar nosotros en este objeto para fines de monitoreo de estatus del post en esa red social (En Proceso, Revisado, Publicado, etc), como nosotros para un post, independientemente de a cuantas redes sociales se envíe, solo creamos un objeto PostOut (Message, Photo, Video), tuvimos que crear esta clase para guardar el identificador de ese postOut para c/red social.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostOutNet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostOutNet");
   /**
   * Propiedad en la que se agregan instancias de PostOutNet de redes sociales que son de tipo SocialMonitorable. Sa agregan instancias para poderlas estas monitoreando, cuando dicha instancia regresa el valor de "True" al proceso que las esta monitoreando, quiere decir que ya se publicó en dicha red social, en ese momento se eliminara dicha instancia de esta propiedad, es decir del monitoreo. Si sigue enviando false, es porque aun esta el PostOut en proceso de publicación ("En Proceso"), lo cual hara que el proceso siga monitoreando a dicha instancia cada x cantidad de minutos..
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasPostOutNet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasPostOutNet");
    public static final org.semanticwb.platform.SemanticClass social_PostMonitor=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostMonitor");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostMonitor");

    public static class ClassMgr
    {
       /**
       * Returns a list of PostMonitor for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.PostMonitor
       */

        public static java.util.Iterator<org.semanticwb.social.PostMonitor> listPostMonitors(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostMonitor>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.PostMonitor for all models
       * @return Iterator of org.semanticwb.social.PostMonitor
       */

        public static java.util.Iterator<org.semanticwb.social.PostMonitor> listPostMonitors()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostMonitor>(it, true);
        }

        public static org.semanticwb.social.PostMonitor createPostMonitor(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.PostMonitor.ClassMgr.createPostMonitor(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.PostMonitor
       * @param id Identifier for org.semanticwb.social.PostMonitor
       * @param model Model of the org.semanticwb.social.PostMonitor
       * @return A org.semanticwb.social.PostMonitor
       */
        public static org.semanticwb.social.PostMonitor getPostMonitor(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.PostMonitor)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.PostMonitor
       * @param id Identifier for org.semanticwb.social.PostMonitor
       * @param model Model of the org.semanticwb.social.PostMonitor
       * @return A org.semanticwb.social.PostMonitor
       */
        public static org.semanticwb.social.PostMonitor createPostMonitor(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.PostMonitor)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.PostMonitor
       * @param id Identifier for org.semanticwb.social.PostMonitor
       * @param model Model of the org.semanticwb.social.PostMonitor
       */
        public static void removePostMonitor(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.PostMonitor
       * @param id Identifier for org.semanticwb.social.PostMonitor
       * @param model Model of the org.semanticwb.social.PostMonitor
       * @return true if the org.semanticwb.social.PostMonitor exists, false otherwise
       */

        public static boolean hasPostMonitor(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPostMonitor(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.PostMonitor with a determined PostOutNet
       * @param value PostOutNet of the type org.semanticwb.social.PostOutNet
       * @param model Model of the org.semanticwb.social.PostMonitor
       * @return Iterator with all the org.semanticwb.social.PostMonitor
       */

        public static java.util.Iterator<org.semanticwb.social.PostMonitor> listPostMonitorByPostOutNet(org.semanticwb.social.PostOutNet value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostMonitor> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostOutNet, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostMonitor with a determined PostOutNet
       * @param value PostOutNet of the type org.semanticwb.social.PostOutNet
       * @return Iterator with all the org.semanticwb.social.PostMonitor
       */

        public static java.util.Iterator<org.semanticwb.social.PostMonitor> listPostMonitorByPostOutNet(org.semanticwb.social.PostOutNet value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostMonitor> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostOutNet,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static PostMonitorBase.ClassMgr getPostMonitorClassMgr()
    {
        return new PostMonitorBase.ClassMgr();
    }

   /**
   * Constructs a PostMonitorBase with a SemanticObject
   * @param base The SemanticObject with the properties for the PostMonitor
   */
    public PostMonitorBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.social.PostOutNet
   * @return A GenericIterator with all the org.semanticwb.social.PostOutNet
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutNet> listPostOutNets()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutNet>(getSemanticObject().listObjectProperties(social_hasPostOutNet));
    }

   /**
   * Gets true if has a PostOutNet
   * @param value org.semanticwb.social.PostOutNet to verify
   * @return true if the org.semanticwb.social.PostOutNet exists, false otherwise
   */
    public boolean hasPostOutNet(org.semanticwb.social.PostOutNet value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasPostOutNet,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a PostOutNet
   * @param value org.semanticwb.social.PostOutNet to add
   */

    public void addPostOutNet(org.semanticwb.social.PostOutNet value)
    {
        getSemanticObject().addObjectProperty(social_hasPostOutNet, value.getSemanticObject());
    }
   /**
   * Removes all the PostOutNet
   */

    public void removeAllPostOutNet()
    {
        getSemanticObject().removeProperty(social_hasPostOutNet);
    }
   /**
   * Removes a PostOutNet
   * @param value org.semanticwb.social.PostOutNet to remove
   */

    public void removePostOutNet(org.semanticwb.social.PostOutNet value)
    {
        getSemanticObject().removeObjectProperty(social_hasPostOutNet,value.getSemanticObject());
    }

   /**
   * Gets the PostOutNet
   * @return a org.semanticwb.social.PostOutNet
   */
    public org.semanticwb.social.PostOutNet getPostOutNet()
    {
         org.semanticwb.social.PostOutNet ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasPostOutNet);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostOutNet)obj.createGenericInstance();
         }
         return ret;
    }
}
