/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.admin.tree;

import java.util.ArrayList;
import java.util.Iterator;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.model.Descriptiveable;
import org.semanticwb.model.DisplayObject;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.social.SocialSite;
import org.semanticwb.social.TreeNodePage;

/**
 *
 * @author jorge.jimenez
 */

 
 public class ElementList {

    private ElementTreeNode root;
    private WebSite modelAdmin=null;
    private User user=null;
    private String ImgAdminPathBase=null;
   
    public ElementList(SWBParamRequest paramRequest) {
        modelAdmin=paramRequest.getWebPage().getWebSite();
        ImgAdminPathBase="/work/models/"+modelAdmin.getId()+"/admin/img/";
        user=paramRequest.getUser();
        root=new ElementTreeNode(new Element("Company Brands","",""),getSocialSites(), true);
    }

    private ElementTreeNode[] getSocialSites()
    {
        DisplayObject displayObj=(DisplayObject)SocialSite.sclass.getDisplayObject().createGenericInstance();
        ArrayList<SocialSite> alist=new ArrayList();
        int cont=0;
        Iterator <SocialSite> itSocialSites=SocialSite.ClassMgr.listSocialSites();
        while(itSocialSites.hasNext())
        {
            SocialSite socialSite=itSocialSites.next();
            alist.add(socialSite);
            cont++;
        }
        ElementTreeNode[] elementTreeNode=new ElementTreeNode[cont];
        itSocialSites=alist.iterator();
        int cont2=0;
        while(itSocialSites.hasNext())
        {
            SocialSite socialSite=itSocialSites.next();
            elementTreeNode[cont2]=new ElementTreeNode(new Element(socialSite.getTitle(), socialSite.getURI(), ImgAdminPathBase+displayObj.getIconClass()),getTreeCategoryNodes(socialSite),true);
            cont2++;
        }
        return elementTreeNode;
    }

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
            elementTreeNode[cont2]=new ElementTreeNode(new Element(treeNode.getTitle(), treeNode.getId(), treeNode.getZulResourcePath(), iconImgPath),getTreeNodeElements(treeNode.getClassUri(), model), true);
            cont2++;
        }
        return elementTreeNode;
    }

    private ElementTreeNode[] getTreeNodeElements(String classUri, SWBModel model)
    {
        if(classUri==null) return new ElementTreeNode[0];
        ArrayList<GenericObject> alist=new ArrayList();
        int cont=0;
        SemanticClass swbClass = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(classUri);
        if(swbClass==null) return new ElementTreeNode[0];
        Iterator <GenericObject> itGenObjs=model.listInstancesOfClass(swbClass);
        while(itGenObjs.hasNext())
        {
            GenericObject genObj=itGenObjs.next();
            alist.add(genObj);
            cont++;
        }
        ElementTreeNode[] elementTreeNode=new ElementTreeNode[cont];
        itGenObjs=alist.iterator();
        int cont2=0;
        while(itGenObjs.hasNext())
        {
            GenericObject genObj=itGenObjs.next();
            DisplayObject displayObj=(DisplayObject)genObj.getSemanticObject().getSemanticClass().getDisplayObject().createGenericInstance();
            elementTreeNode[cont2]=new ElementTreeNode(new Element(genObj.getSemanticObject().getProperty(Descriptiveable.swb_title), genObj.getURI(), ImgAdminPathBase+displayObj.getIconClass()));
            cont2++;
        }
        return elementTreeNode;
    }

    public ElementTreeNode getRoot() {
        return root;
    }
}