/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.components.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Activeable;
import org.semanticwb.model.Descriptiveable;
import org.semanticwb.model.DisplayObject;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.social.Childrenable;
import org.semanticwb.social.SocialSite;
import org.semanticwb.social.TreeNodePage;

/**
 *
 * @author jorge.jimenez
 * @date 10/03/2012
 */

 /*
  * Clase controladora del modelo de datos del árbol de navegación
  */
 public final class ElementList {

    private ElementTreeNode root;
    private WebSite modelAdmin=null;
    private User user=null;
    private String ImgAdminPathBase=null;

    /*
     * Constructor de la clase
     * @paramRequest SWBParamRequest que llega desde el recurso que controla la plantilla de la
     * administración de la herramienta de swbsocial
     */
    public ElementList(SWBParamRequest paramRequest) {
        modelAdmin=paramRequest.getWebPage().getWebSite();
        ImgAdminPathBase="/work/models/"+modelAdmin.getId()+"/admin/img/";
        user=paramRequest.getUser();
        root=new ElementTreeNode(new Element("Company Brands","socialBrands",""),getSocialSites(), false);
    }

    /*
     * Obtención de sitios de tipo SocialSite
     */
    private ElementTreeNode[] getSocialSites()
    {
        DisplayObject displayObj=(DisplayObject)SocialSite.sclass.getDisplayObject().createGenericInstance();
        ArrayList<SocialSite> alist=new ArrayList();
        int cont=0;
        Iterator <SocialSite> itSocialSites=SocialSite.ClassMgr.listSocialSites();
        while(itSocialSites.hasNext())
        {
            SocialSite socialSite=itSocialSites.next();
            if(!socialSite.isDeleted())
            {
                alist.add(socialSite);
                cont++;
            }
            
        }
        ElementTreeNode[] elementTreeNode=new ElementTreeNode[cont];
        itSocialSites=alist.iterator();
        int cont2=0;
        while(itSocialSites.hasNext())
        {
            SocialSite socialSite=itSocialSites.next();
            String sIconImg=displayObj.getDoDispatcher();   //Aqui esta el icono para social, en getIconClass esta el icono(swbIconWebSite) para mostrar el sitio en el admin de SWB.
            if(!socialSite.isActive())
            {
                sIconImg="off_"+sIconImg;
            }
            elementTreeNode[cont2]=new ElementTreeNode(new Element(socialSite.getTitle(), socialSite.getURI(), ImgAdminPathBase+sIconImg),getTreeCategoryNodes(socialSite),false);
            cont2++;
        }
        return elementTreeNode;
    }

    /*
     * Obtención de las categorías del árbol, estas descienden del nodo TreeCategoryNodes del sitio
     * de administración
     * @param model SWBModel que es enviado desde el metodo anterior (getSocialSites)
     */
    private ElementTreeNode[] getTreeCategoryNodes(SWBModel model)
    {
        ArrayList<TreeNodePage> alist=new ArrayList();
        WebSite adminWSite=WebSite.ClassMgr.getWebSite(modelAdmin.getId());
        WebPage treePageHome=adminWSite.getWebPage("TreeCategoryNodes");
        int cont=0;
        Iterator <WebPage> itChilds=treePageHome.listVisibleChilds(user.getLanguage());
        while(itChilds.hasNext())
        {
            WebPage page=itChilds.next();
            if(page instanceof TreeNodePage)
            {
                TreeNodePage treeNodePage=(TreeNodePage)page;
                alist.add(treeNodePage);
                cont++;
            }
        }
        ElementTreeNode[] elementTreeNode=new ElementTreeNode[cont];
        Iterator<TreeNodePage> itTreeNodes=alist.iterator();
        int cont2=0;
        while(itTreeNodes.hasNext())
        {
            TreeNodePage treeNode=itTreeNodes.next();
            String iconImgPath=SWBPortal.getWebWorkPath()+treeNode.getWorkPath()+"/"+treeNode.social_wpImg.getName()+"_"+treeNode.getId()+"_"+treeNode.getWpImg();
            elementTreeNode[cont2]=new ElementTreeNode(new Element(treeNode.getTitle(), treeNode.getId(), treeNode.getZulResourcePath(), iconImgPath, null, model.getId()),getTreeNodeElements(treeNode.getClassUri(), model, treeNode.getId()), false);
            cont2++;
        }
        return elementTreeNode;
    }

    /*
     * Obtención de instancias del tipo de clase que contiene la categoría en su propiedad "tree_classUri"
     * @param classUri String uri de la categoría
     * @param model SWBModel de la categoría
     * @param categoryID String id de la categoría
     */
    
    private ElementTreeNode[] getTreeNodeElements(String classUri, SWBModel model, String categoryID)
    {
        if(classUri==null) return new ElementTreeNode[0];
        int cont=0;
        SemanticClass swbClass = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(classUri);
        if(swbClass==null) return new ElementTreeNode[0];
        ArrayList alist=new ArrayList();
        Iterator <GenericObject> itGenObjs=model.listInstancesOfClass(swbClass);
        List tmpList=SWBUtils.Collections.copyIterator(itGenObjs);
        itGenObjs=tmpList.iterator();
        while(itGenObjs.hasNext())
        {
            GenericObject genObj=itGenObjs.next();
            DisplayObject displayObj=(DisplayObject)genObj.getSemanticObject().getSemanticClass().getDisplayObject().createGenericInstance();
            String sIconImg=displayObj.getIconClass();
            //Si el objeto semantico instancia de una clase Activeable, pero no esta activada dicha instancia
            if(genObj.getSemanticObject().getSemanticClass().isSubClass(Activeable.swb_Activeable) && !genObj.getSemanticObject().getBooleanProperty(Activeable.swb_active))
            {
                sIconImg="off_"+sIconImg;
            }
            if(genObj instanceof Childrenable)
            {
                Childrenable child=(Childrenable)genObj;
                //Si no tiene padre, se agrega al principal
                if(child.getParentObj()==null)
                {
                    alist.add(new ElementTreeNode(new Element(genObj.getSemanticObject().getProperty(Descriptiveable.swb_title), genObj.getURI(), null, ImgAdminPathBase+sIconImg, categoryID, model.getId()), getTreeNodeChildrenElements(genObj, model, categoryID), false));
                    cont++;
                }
            }else
            {
                alist.add(new ElementTreeNode(new Element(genObj.getSemanticObject().getProperty(Descriptiveable.swb_title), genObj.getURI(), null,ImgAdminPathBase+sIconImg, categoryID, model.getId())));
                cont++;
            }
        }
        ElementTreeNode[] elementTreeNode=new ElementTreeNode[cont];
        cont=0;
        Iterator<ElementTreeNode> itNodes=alist.iterator();
        while(itNodes.hasNext())
        {
            elementTreeNode[cont]=itNodes.next();
            cont++;
        }
        return elementTreeNode;
    }
   
    

    /*
     * Obtención de nodos o elementos hijos de un GenericObject que es de tipo Childrenable, esto, de manera recursiva
     * @param genObj GenericObject de tipo childrenable a desplegar en el árbol de navegación
     * @param model SWBModel de la categoría
     * @param categoryID String del id de la categoría en cuestión
     */
   private ElementTreeNode[] getTreeNodeChildrenElements(GenericObject genObj, SWBModel model, String categoryID)
    {
        if(genObj instanceof Childrenable)
        {
            int cont=0;
            Childrenable childrenable = (Childrenable) genObj;
            Iterator<Childrenable> itChildren=childrenable.listChildrenObjInvs();
            List tmpList=SWBUtils.Collections.copyIterator(itChildren);
            ElementTreeNode[] elementTreeNode=new ElementTreeNode[tmpList.size()];
            itChildren=tmpList.iterator();
            while(itChildren.hasNext())
            {
                Childrenable child=itChildren.next();
                GenericObject childGenObj=child.getSemanticObject().getGenericInstance();
                DisplayObject displayObj=(DisplayObject)childGenObj.getSemanticObject().getSemanticClass().getDisplayObject().createGenericInstance();
                String sIconImg=displayObj.getIconClass();
                //Si el objeto semantico instancia de una clase Activeable, pero no esta activada dicha instancia
                if(childGenObj.getSemanticObject().getSemanticClass().isSubClass(Activeable.swb_Activeable) && !childGenObj.getSemanticObject().getBooleanProperty(Activeable.swb_active))
                {
                    sIconImg="off_"+sIconImg;
                }
                elementTreeNode[cont]=new ElementTreeNode(new Element(childGenObj.getSemanticObject().getProperty(Descriptiveable.swb_title), childGenObj.getURI(), null, ImgAdminPathBase+sIconImg, categoryID, model.getId()),getTreeNodeChildrenElements(childGenObj, model, categoryID), false);
                cont++;
            }
            return elementTreeNode;
        }
        return null;
    }

    /*
     * Obtención del modelo de datos generado por la clase.
     */
    public ElementTreeNode getRoot() {
        return root;
    }
}