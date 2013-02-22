
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package org.semanticwb.social.components.tree;

/**
*
* @author jorge.jimenez @date 10/03/2012
*/
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Locale;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.*;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.social.Childrenable;
import org.semanticwb.social.DeleteControlable;
import org.semanticwb.social.SocialSite;
import org.semanticwb.social.TreeNodePage;
import org.semanticwb.social.utils.SWBSocialResourceUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

/*
* Clase controladora del árbol de navegación
*/
public final class SWBSTreeComposer extends GenericForwardComposer<Component> {

    /**
     *
     */
    private static Logger log = SWBUtils.getLogger(SWBSTreeComposer.class);
    private Tree tree;
    private Include content;
    private AdvancedTreeModel elemenetTreeModel;
    private Menupopup treePopup;
    private Messagebox messageBox;
    WebSite wsiteAdm = null;
    User user = null;
    SWBParamRequest paramRequest = null;
    String ImgAdminPathBase = null;
    //Mensajes
    private String msg_sureOfdelete;
    private String msg_createNew;
    private String msg_delete;
    private String msg_activate;
    private String msg_deactivate;
    private String msg_activeElement;
    private String msg_unActiveElement;
    private String msg_brandDeleted;
    private String msg_elementDeleted;
    private String msg_elementMoved;
    private Tabs tabs_gen;

    /*
     * Metodo implementación de la clase padre, este metodo se ejecuta una vez
     * cargado el componente.
     */
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        try {
            paramRequest = (SWBParamRequest) requestScope.get("paramRequest");
            if (paramRequest != null) {
                wsiteAdm = paramRequest.getWebPage().getWebSite();
                if (wsiteAdm != null) {
                    ImgAdminPathBase = "/work/models/" + wsiteAdm.getId() + "/admin/img/";
                    user = paramRequest.getUser();
                    elemenetTreeModel = new AdvancedTreeModel(new ElementList(paramRequest).getRoot());
                    tree.setModel(elemenetTreeModel);
                    tree.setItemRenderer(new ElementTreeRenderer());
                    tree.setVflex(true);
                    //Guardo el modelo y el root node en la session, TODO: Ver si solo guardo el modelo y saco el root node del mismo modelo.
                    comp.getPage().getDesktop().getSession().setAttribute("elemenetTreeModel", elemenetTreeModel);
                    ElementTreeNode rootTreeNode = (ElementTreeNode) elemenetTreeModel._root;
                    comp.getPage().getDesktop().getSession().setAttribute("rootTreeNode", rootTreeNode);
                }
            }
            //Mensajes
           String sLocale = "org.semanticwb.social.components.locales.genericCompMsgs";
            Locale locale = new Locale(user.getLanguage());
            msg_sureOfdelete = SWBUtils.TEXT.getLocaleString(sLocale, "msg_sureOfdelete", locale);
            msg_createNew = SWBUtils.TEXT.getLocaleString(sLocale, "msg_createNew", locale);
            msg_delete = SWBUtils.TEXT.getLocaleString(sLocale, "msg_delete", locale);
            msg_activate = SWBUtils.TEXT.getLocaleString(sLocale, "msg_activate", locale);
            msg_deactivate = SWBUtils.TEXT.getLocaleString(sLocale, "msg_deactivate", locale);
            msg_activeElement = SWBUtils.TEXT.getLocaleString(sLocale, "msg_activeElement", locale);
            msg_unActiveElement = SWBUtils.TEXT.getLocaleString(sLocale, "msg_unActiveElement", locale);
            msg_brandDeleted = SWBUtils.TEXT.getLocaleString(sLocale, "msg_brandDeleted", locale);
            msg_elementDeleted = SWBUtils.TEXT.getLocaleString(sLocale, "msg_elementDeleted", locale);
            msg_elementMoved = SWBUtils.TEXT.getLocaleString(sLocale, "msg_elementMoved", locale);
        } catch (Exception e) {
            log.error(e);
        }
    }


    /*
     * Metodo que renderea los nodos del árbol de navegación
     */
    public final class ElementTreeRenderer implements TreeitemRenderer<ElementTreeNode> {
        /*
         * Metodo que sobreescribe el metodo render del padre
         */

        @Override
        public void render(final Treeitem treeItem, ElementTreeNode treeNode, int index) throws Exception {
            try {
                ElementTreeNode ctn = treeNode;
                final Element element = (Element) ctn.getData();
                Treerow dataRow = treeItem.getTreerow();
                if (dataRow == null) {
                    dataRow = new Treerow();
                } else {
                    dataRow.getChildren().clear();
                }

                dataRow.setParent(treeItem);
                treeItem.setValue(ctn);
                treeItem.setOpen(ctn.isOpen());
                dataRow.setAttribute("xxx", ctn.getData()); //Charly
                treeItem.setId("xxx" + ctn.getData());    //Charly
                {
                    Treecell treeCell = new Treecell();
                    treeCell.setImage(element.getIconElement());
                    treeCell.setLabel(element.getName());
                    dataRow.setDraggable("true");
                    dataRow.appendChild(treeCell);
                    //Manejo de click derecho en los elementos del árbol
                    dataRow.addEventListener(Events.ON_RIGHT_CLICK, new EventListener<Event>() {

                        @Override
                        public void onEvent(Event event) throws Exception {
                            final Treeitem selectedTreeItem = (Treeitem) event.getTarget().getFellow("xxx" + event.getTarget().getAttribute("xxx"));   //Charly
                            final ElementTreeNode itemValue = (ElementTreeNode) selectedTreeItem.getValue();
                            treePopup.getChildren().clear();
                            if (isCategory(itemValue.getData())) { //Es una categoría
                                Menuitem mItemNew = new Menuitem();
                                mItemNew.setLabel(msg_createNew);
                                mItemNew.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

                                    @Override
                                    public void onEvent(Event event) throws Exception {
                                        String zulPage = null;
                                        WebSite wsite = null;
                                        SemanticObject semObject = null;
                                        WebPage adminWebPage = wsiteAdm.getWebPage(itemValue.getData().getUri());
                                        if (adminWebPage instanceof TreeNodePage) {
                                            TreeNodePage treeNodePage = (TreeNodePage) adminWebPage;
                                            zulPage = treeNodePage.getZulResourcePath();
                                        }
//                                        //Se obtiene website del nodo en cuestion, es decir, no es el sitio de admin.
//                                        //Tomando en cuenta que el siguiente padre de una categoría es el nodo del sitio
                                        Treeitem wsiteItem = selectedTreeItem.getParentItem();
                                        ElementTreeNode wsiteItemValue = (ElementTreeNode) wsiteItem.getValue();
                                        if (wsiteItemValue.getData().getUri() != null) {
                                            semObject = SemanticObject.createSemanticObject(wsiteItemValue.getData().getUri());
                                            if (semObject != null) {
                                                wsite = (WebSite) semObject.createGenericInstance();
                                            }
                                        }
                                        loadTab(element.getIconElement(), wsite.getTitle(), element.getUri() + wsite.getId(), itemValue, zulPage, SWBSocialResourceUtils.ACTION_ADD, adminWebPage, wsite);

                                    }
                                });
                                treePopup.appendChild(mItemNew);
                                treePopup.open(selectedTreeItem);
                            } else //Es un elemento
                            {
                                final SemanticObject semObj = SemanticObject.createSemanticObject(itemValue.getData().getUri());
                                Treeitem parentItem = selectedTreeItem.getParentItem();
                                //Es algún otro nodo diferente al de una marca
                                if (parentItem != null) {
                                    final ElementTreeNode parentItemValue = (ElementTreeNode) parentItem.getValue();
                                    //MENU CONTEXTUAL
                                    final WebPage adminWebPage = wsiteAdm.getWebPage(itemValue.getData().getCategoryID());
                                    TreeNodePage treeNodePageTmp = null;
                                    if (adminWebPage instanceof TreeNodePage) {
                                        treeNodePageTmp = (TreeNodePage) adminWebPage;
                                    }
                                    final TreeNodePage treeNodePage = treeNodePageTmp;
                                    final SemanticClass swbClass = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(treeNodePage.getClassUri());
                                    //Opción Crear, para los nodos que sean de tipo Childrenable
                                    if (semObj.getSemanticClass().isSubClass(Childrenable.social_Childrenable)) {
                                        Menuitem mItemNew = new Menuitem();
                                        mItemNew.setLabel(msg_createNew);
                                        mItemNew.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

                                            @Override
                                            public void onEvent(Event event) throws Exception {

                                                WebSite wsite = (WebSite) semObj.getModel().getModelObject().createGenericInstance();
                                                loadTab(element.getIconElement(), element.getName(), element.getUri() + wsite.getId(), itemValue, treeNodePage.getZulResourcePath(), SWBSocialResourceUtils.ACTION_ADD, adminWebPage, wsite);
                                            }
                                        });
                                        treePopup.appendChild(mItemNew);
                                    }
                                    //------------Opciones dependiendo del nodo del árbol----------//
                                    WebPage treeCategoryNode = wsiteAdm.getWebPage(itemValue.getData().getCategoryID());
                                    Iterator<WebPage> itTreeCategoryNodeChilds = treeCategoryNode.listVisibleChilds(user.getLanguage());
                                    while (itTreeCategoryNodeChilds.hasNext()) {
                                        final WebPage wpageOption = itTreeCategoryNodeChilds.next();
                                        Menuitem mItemCustomOpt = new Menuitem();
                                        mItemCustomOpt.setLabel(wpageOption.getDisplayName(user.getLanguage()));
                                        mItemCustomOpt.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

                                            @Override
                                            public void onEvent(Event event) throws Exception {
                                                String zulPage = parentItemValue.getData().getZulPage();
                                                String action = SWBSocialResourceUtils.ACTION_EDIT;
                                                if (wpageOption instanceof TreeNodePage) {
                                                    TreeNodePage TreeNodePage = (TreeNodePage) wpageOption;
                                                    if (TreeNodePage.getZulResourcePath() != null) {
                                                        zulPage = TreeNodePage.getZulResourcePath();
                                                    }
                                                    if (TreeNodePage.getAction() != null) {
                                                        action = TreeNodePage.getAction();
                                                    }
                                                }
                                                WebSite wsite = (WebSite) semObj.getModel().getModelObject().createGenericInstance();
                                                loadTab(element.getIconElement(), element.getName(), element.getUri() + wsite.getId(), itemValue, zulPage, action, wpageOption, wsite);
                                            }
                                        });
                                        treePopup.appendChild(mItemCustomOpt);
                                    }
                                    //Opción Activar/Desactivar
                                    if (swbClass != null && swbClass.isSubClass(Activeable.swb_Activeable)) {
                                        if (!semObj.getBooleanProperty(Activeable.swb_active)) //Si esta desactivado el objeto semantico
                                        {
                                            Menuitem mItemActive = new Menuitem();
                                            mItemActive.setLabel(msg_activate);
                                            mItemActive.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

                                                @Override
                                                public void onEvent(Event event) throws Exception {
                                                    semObj.setBooleanProperty(Activeable.swb_active, true);
                                                    DisplayObject displayObj = (DisplayObject) semObj.getSemanticClass().getDisplayObject().createGenericInstance();
                                                    Element element = (Element) itemValue.getData();
                                                    element.seticonElement(ImgAdminPathBase + displayObj.getIconClass());
                                                    itemValue.setData(element);
                                                    SWBSocialResourceUtils.Zkoss.setStatusMessage(msg_activeElement + ":" + itemValue.getData().getName());
                                                }
                                            });
                                            treePopup.appendChild(mItemActive);
                                        } else //Si esta activo el objeto semantico
                                        {
                                            Menuitem mItemActive = new Menuitem();
                                            mItemActive.setLabel(msg_deactivate);
                                            mItemActive.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

                                                @Override
                                                public void onEvent(Event event) throws Exception {
                                                    semObj.setBooleanProperty(Activeable.swb_active, false);
                                                    DisplayObject displayObj = (DisplayObject) semObj.getSemanticClass().getDisplayObject().createGenericInstance();
                                                    Element element = (Element) itemValue.getData();
                                                    element.seticonElement(ImgAdminPathBase + "off_" + displayObj.getIconClass());
                                                    itemValue.setData(element);
                                                    SWBSocialResourceUtils.Zkoss.setStatusMessage(msg_unActiveElement + ":" + itemValue.getData().getName());
                                                }
                                            });
                                            treePopup.appendChild(mItemActive);
                                        }
                                    }
                                    //Opción de eliminar
                                    Menuitem mItemRemove = new Menuitem();
                                    mItemRemove.setLabel(msg_delete);
                                    mItemRemove.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

                                        @Override
                                        public void onEvent(Event event) throws Exception {
                                            messageBox.show(msg_sureOfdelete + ":" + itemValue.getData().getName(), "deleteConfirm", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
                                                    //messageBox.show("Esta seguro de eliminar este elemento?", "deleteConfirm", Messagebox.YES|Messagebox.NO, Messagebox.QUESTION,
                                                    new EventListener() {

                                                public void onEvent(Event evt) {
                                                    switch (((Integer) evt.getData()).intValue()) {
                                                        case Messagebox.YES:
                                                            if (semObj != null) {
                                                                WebSite wsite = (WebSite) semObj.getModel().getModelObject().createGenericInstance();
                                                                if (swbClass != null && swbClass.isSubClass(DeleteControlable.social_DeleteControlable)) {
                                                                    //Nuevo Código
                                                                    //loadTab(element.getIconElement(), element.getName(), element.getUri() + semObj.getId(), itemValue, treeNodePage.getZulResourcePath(), SWBSocialResourceUtils.ACTION_REMOVE, adminWebPage, wsite);
                                                                    //Termina nuevo código
                                                                } else {
                                                                    if (semObj.getSemanticClass().isSubClass(Childrenable.social_Childrenable)) {
                                                                        if (SWBSocialResourceUtils.Semantic.removeObjChildrenable(semObj)) {
                                                                            semObj.remove();
                                                                            try {
                                                                                ((Tab) page.getFellow("idTab" + element.getUri() + wsite.getId())).close();
                                                                            } catch (Exception e) {
                                                                                e.printStackTrace();
                                                                            }
                                                                        }
                                                                    } else {
                                                                        semObj.remove();
                                                                        try {
                                                                            ((Tab) page.getFellow("idTab" + element.getUri() + wsite.getId())).close();
                                                                        } catch (Exception e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }
                                                                    elemenetTreeModel.remove(itemValue);
                                                                    SWBSocialResourceUtils.Zkoss.setStatusMessage(msg_elementDeleted + ":" + itemValue.getData().getName());
                                                                    break;   //TODO:Ver si requiero este break;
                                                                }
                                                            }
                                                    }
                                                }
                                            });
                                        }
                                    });
                                    treePopup.appendChild(mItemRemove);
                                    //Opciones para cuando es una marca a la que se le da click derecho
                                } else //No tiene padre, seguramente es una marca
                                {
                                    //Opciones dinamicas de un sitio tipo SocialAdmin, todas bajo la sección con id "siteOptions".
                                    WebPage siteOptsWebPage = wsiteAdm.getWebPage("siteOptions");
                                    Iterator<WebPage> itChildPages = siteOptsWebPage.listVisibleChilds(user.getLanguage());
                                    while (itChildPages.hasNext()) {
                                        final WebPage wpageOption = itChildPages.next();
                                        Menuitem mItemCustomOpt = new Menuitem();
                                        mItemCustomOpt.setLabel(wpageOption.getDisplayName(user.getLanguage()));
                                        mItemCustomOpt.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

                                            @Override
                                            public void onEvent(Event event) throws Exception {
                                                String zulPage = null;
                                                String action = SWBSocialResourceUtils.ACTION_EDIT;
                                                if (wpageOption instanceof TreeNodePage) {
                                                    TreeNodePage TreeNodePage = (TreeNodePage) wpageOption;
                                                    if (TreeNodePage.getZulResourcePath() != null) {
                                                        zulPage = TreeNodePage.getZulResourcePath();
                                                    }
                                                    if (TreeNodePage.getAction() != null) {
                                                        action = TreeNodePage.getAction();
                                                    }
                                                }
                                                WebSite wsite = (WebSite) semObj.getModel().getModelObject().createGenericInstance();
                                                loadTab(element.getIconElement(), element.getName(), element.getUri() + wsite.getId(), itemValue, zulPage, action, wpageOption, wsite);
                                            }
                                        });
                                        treePopup.appendChild(mItemCustomOpt);
                                    }
                                    //Opción Activar/Desactivar
                                    if (!semObj.getBooleanProperty(Activeable.swb_active)) //Si esta desactivado el objeto semantico
                                    {
                                        Menuitem mItemActive = new Menuitem();
                                        mItemActive.setLabel(msg_activate);
                                        mItemActive.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

                                            @Override
                                            public void onEvent(Event event) throws Exception {
                                                semObj.setBooleanProperty(Activeable.swb_active, true);
                                                DisplayObject displayObj = (DisplayObject) semObj.getSemanticClass().getDisplayObject().createGenericInstance();
                                                itemValue.getData().seticonElement(ImgAdminPathBase + displayObj.getDoDispatcher());
                                                elemenetTreeModel.refreshNode(itemValue);
                                                SWBSocialResourceUtils.Zkoss.setStatusMessage(msg_activeElement + ":" + itemValue.getData().getName());
                                            }
                                        });
                                        treePopup.appendChild(mItemActive);
                                    } else //Si esta activo el objeto semantico
                                    {
                                        Menuitem mItemActive = new Menuitem();
                                        mItemActive.setLabel(msg_deactivate);
                                        mItemActive.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

                                            @Override
                                            public void onEvent(Event event) throws Exception {
                                                semObj.setBooleanProperty(Activeable.swb_active, false);
                                                DisplayObject displayObj = (DisplayObject) semObj.getSemanticClass().getDisplayObject().createGenericInstance();
                                                itemValue.getData().seticonElement(ImgAdminPathBase + "off_" + displayObj.getDoDispatcher());
                                                elemenetTreeModel.refreshNode(itemValue);
                                                SWBSocialResourceUtils.Zkoss.setStatusMessage(msg_unActiveElement + ":" + itemValue.getData().getName());
                                            }
                                        });
                                        treePopup.appendChild(mItemActive);
                                    }
                                   //Opción de eliminar para una marca
                                    Menuitem mItemRemove = new Menuitem();
                                    mItemRemove.setLabel(msg_delete);
                                    mItemRemove.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

                                        @Override
                                        public void onEvent(Event event) throws Exception {
                                            messageBox.show(msg_sureOfdelete + ":" + itemValue.getData().getName(), "deleteConfirm", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
                                                    new EventListener() {

                                                        public void onEvent(Event evt) {
                                                            SemanticObject semObj = SemanticObject.createSemanticObject(itemValue.getData().getUri());
                                                            if (semObj != null) {
                                                                {
                                                                    SocialSite socialSite = (SocialSite) semObj.createGenericInstance();
                                                                    socialSite.setDeleted(true);    //Como es un sitio, No lo elimino, solo lo envío a la papelera de reciclaje
                                                                    WebSite wsite = (WebSite) semObj.getModel().getModelObject().createGenericInstance();
                                                                    try {
                                                                        ((Tab) page.getFellow("idTab" + element.getUri() + wsite.getId())).close();
//                                                                        ((Tab) page.getFellow("idTab" + element.getUri() + semObj.getId())).close();
                                                                    } catch (Exception e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                }
                                                                elemenetTreeModel.remove(itemValue);
                                                                SWBSocialResourceUtils.Zkoss.setStatusMessage(msg_brandDeleted + ":" + itemValue.getData().getName());
                                                            }
                                                        }
                                                    });
                                        }
                                    });
                                   treePopup.appendChild(mItemRemove);
                                }
                                //Abre el menú contextual inmediatamente abajo del nodo seleccionado
                                treePopup.open(selectedTreeItem);
                            }
                        }
                    });
                }
                //Manejo de doble click en los nodos del árbol
                {
                    //TODO
                    dataRow.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener<Event>() {

                        @Override
                        public void onEvent(Event event) throws Exception {
                            final Treeitem selectedTreeItem = (Treeitem) event.getTarget().getFellow("xxx" + event.getTarget().getAttribute("xxx"));   //Charly
                            final ElementTreeNode itemValue = (ElementTreeNode) selectedTreeItem.getValue();
                            final SemanticObject semObj = SemanticObject.createSemanticObject(itemValue.getData().getUri());
                            if (!isCategory(itemValue.getData())) { //Si es una categoría
                                if (itemValue.getParent().getData().getUri().equals("socialBrands")) {
                                    WebPage siteEditWebPage = wsiteAdm.getWebPage("siteOpt_Edit");
                                    String zulPage = null;
                                    String action = SWBSocialResourceUtils.ACTION_EDIT;
                                    if (siteEditWebPage instanceof TreeNodePage) {
                                        TreeNodePage TreeNodePage = (TreeNodePage) siteEditWebPage;
                                        if (TreeNodePage.getZulResourcePath() != null) {
                                            zulPage = TreeNodePage.getZulResourcePath();
                                        }
                                        if (TreeNodePage.getAction() != null) {
                                            action = TreeNodePage.getAction();
                                        }
                                    }
                                    WebSite wsite = (WebSite) semObj.getModel().getModelObject().createGenericInstance();
                                    loadTab(element.getIconElement(), element.getName(), element.getUri() + wsite.getId(), itemValue, zulPage, action, siteEditWebPage, wsite);
                                } else {
                                    WebPage wpage = wsiteAdm.getWebPage(itemValue.getData().getCategoryID());
                                    String zulPage = "";
                                    if (wpage != null && wpage instanceof TreeNodePage) {
                                        TreeNodePage TreeNodePage = (TreeNodePage) wpage;
                                        if (TreeNodePage.getZulResourcePath() != null) {
                                            zulPage = TreeNodePage.getZulResourcePath();
                                        }
                                    }
                                    WebSite wsite = WebSite.ClassMgr.getWebSite(SemanticObject.getSemanticObject(itemValue.getData().getUri()).getModel().getName());
                                    loadTab(element.getIconElement(), element.getName(), element.getUri() + wsite.getId(), itemValue, zulPage, SWBSocialResourceUtils.ACTION_DOUBLECLICK, null, wsite);
                                }
                            }
                        }
                    });
                }
                //Bloque de código para soportar drag&drop. Solo en los casos de que los nodos sean de tipo Childrenable
                final ElementTreeNode itemValue = (ElementTreeNode) treeItem.getValue();
                if (itemValue.getData().getUri() != null) {
                    SemanticObject semObj = SemanticObject.createSemanticObject(itemValue.getData().getUri());
                    if (semObj != null && semObj.getSemanticClass().isSubClass(Childrenable.social_Childrenable)) {
                        dataRow.setDraggable("true");
                        dataRow.setDroppable("true");
                        dataRow.addEventListener(Events.ON_DROP, new EventListener<Event>() {

                            @SuppressWarnings("unchecked")
                            @Override
                            public void onEvent(Event event) throws Exception {
                                // The dragged target is a TreeRow belongs to an
                                // Treechildren of TreeItem.
                                Treeitem draggedItem = (Treeitem) ((DropEvent) event).getDragged().getParent();
                                ElementTreeNode draggedValue = (ElementTreeNode) draggedItem.getValue();

                                ElementTreeNode parentData = (ElementTreeNode) treeItem.getValue();
                                if (draggedValue.getData().getModelID().equals(parentData.getData().getModelID())) {
                                    elemenetTreeModel.add(parentData, new ElementTreeNode[]{draggedValue});
                                    SWBSocialResourceUtils.Zkoss.setStatusMessage(msg_elementMoved + ":" + itemValue.getData().getName());
                                }
                            }
                        });
                    } else {
                        dataRow.setDraggable("false");
                        dataRow.setDroppable("false");
                        //Se revisa si es una sección de la administración y si su semanticClass es de tipo Childrenable
                        if (itemValue.getData().getUri() != null) {
                            WebPage wpage = wsiteAdm.getWebPage(itemValue.getData().getUri());
                            if (wpage instanceof TreeNodePage) {
                                TreeNodePage treeNodePage = (TreeNodePage) wpage;
                                if (treeNodePage.getClassUri() != null) {
                                    SemanticClass swbClass = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(treeNodePage.getClassUri());
                                    if (swbClass != null && swbClass.isSubClass(Childrenable.social_Childrenable)) {
                                        dataRow.setDroppable("true");
                                        dataRow.addEventListener(Events.ON_DROP, new EventListener<Event>() {

                                            @SuppressWarnings("unchecked")
                                            @Override
                                            public void onEvent(Event event) throws Exception {
                                                Treeitem draggedItem = (Treeitem) ((DropEvent) event).getDragged().getParent();
                                                ElementTreeNode draggedValue = (ElementTreeNode) draggedItem.getValue();

                                                ElementTreeNode parentData = (ElementTreeNode) treeItem.getValue();
                                                if (draggedValue.getData().getModelID().equals(parentData.getData().getModelID())) {
                                                    elemenetTreeModel.add(parentData, new ElementTreeNode[]{draggedValue});
                                                    SWBSocialResourceUtils.Zkoss.setStatusMessage(msg_elementMoved + ":" + itemValue.getData().getName());
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                log.error(e);
            }
        }
        /*
         * Metodo que regresa si un cierto nodo del árbol es de tipo category o
         * no lo es Ejemplos de nodos tipo Category: Redes Sociales, Streams y
         * Temas @param element Element o nodo a revisar si es de tipo Category
         */

        private boolean isCategory(Element element) {
            if (element.getUri() != null && element.getUri().indexOf("#") == -1) {
                return true;
            }
            return false;
       }
    }

    public void loadTab(final String icon,
            final String name,
            String uri,
            final ElementTreeNode itemValue,
            final String zulPage,
            final String action,
            final WebPage wpageOption,
            final WebSite wsite) {
        content.setSrc(null);
        content.setSrc(zulPage);
        content.setDynamicProperty("objUri", URLEncoder.encode(itemValue.getData().getUri()));
        content.setDynamicProperty("paramRequest", paramRequest);
        content.setDynamicProperty("action", action);
        content.setDynamicProperty("optionWepPage", wpageOption);
        content.setDynamicProperty("treeItem", itemValue);
        content.setDynamicProperty("wsite", wsite);
        content.setDynamicProperty("user", user);
        content.setDynamicProperty("icon", icon);
        content.setDynamicProperty("name", name);
        content.setDynamicProperty("wsiteAdm", wsiteAdm);
        Tab tbGral = new Tab();
        tbGral.setImage(icon);
        tbGral.setLabel(name);
        tbGral.setTooltiptext(name);
        try {
            tbGral.setId("idTab" + uri);
            tbGral.setAttribute("idTab", uri);
            tbGral.setClosable(true);
            tbGral.setParent(tabs_gen);
            tbGral.setSelected(true);
        } catch (Exception e) {
            ((Tab) self.getFellow("idTab" + uri)).setSelected(true);
        }
        tbGral.addEventListener(Events.ON_SELECT, new EventListener<Event>() {

            @Override
            public void onEvent(Event event) throws Exception {
                content.setSrc(null);
                content.setSrc(zulPage);
                content.setDynamicProperty("objUri", URLEncoder.encode(itemValue.getData().getUri()));
                content.setDynamicProperty("paramRequest", paramRequest);
                content.setDynamicProperty("action", action);
                content.setDynamicProperty("optionWepPage", wpageOption);
                content.setDynamicProperty("treeItem", itemValue);
                content.setDynamicProperty("wsite", wsite);
                content.setDynamicProperty("user", user);
                content.setDynamicProperty("icon", icon);
                content.setDynamicProperty("name", name);
                content.setDynamicProperty("wsiteAdm", wsiteAdm);
            }
        });
        tbGral.addEventListener(Events.ON_CLOSE, new EventListener<Event>() {

            @Override
            public void onEvent(Event event) throws Exception {
                content.setSrc(null);
            }
        });
        tbGral.addEventListener(Events.ON_RIGHT_CLICK, new EventListener<Event>() {

            @Override
            public void onEvent(Event event) throws Exception {
                treePopup.getChildren().clear();
                Menuitem mItemNew = new Menuitem();
                mItemNew.setLabel("Cerrar");
                uriTab = event.getTarget().getAttribute("idTab").toString();
                mItemNew.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

                    @Override
                    public void onEvent(Event event) throws Exception {
                        ((Tab) page.getFellow("idTab" + uriTab)).close();
                    }
                });
                Menuitem mItemNewt = new Menuitem();
                mItemNewt.setLabel("Cerrar todos");
                mItemNewt.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

                    @Override
                    public void onEvent(Event event) throws Exception {
                        tabs_gen.getChildren().clear();
                        content.setSrc(null);
                        Tab tbInitial = new Tab();
                        tbInitial.setId("idCommunity" + wsiteAdm.getWebPage("Community").getId());
                        tbInitial.setLabel(wsiteAdm.getWebPage("Community").getDisplayTitle(user.getLanguage()));
                        tbInitial.setTooltiptext(wsiteAdm.getWebPage("Community").getDisplayTitle(user.getLanguage()));
                        tabs_gen.appendChild(tbInitial);
                        content.setSrc(null);
                        content.setSrc("/work/models/swbsocial/admin/zul/cnf_GenericRedirect.zul");
                        content.setDynamicProperty("wsiteAdm", wsiteAdm);
                        content.setDynamicProperty("action", "community");
                        tbInitial.addEventListener(Events.ON_SELECT, new EventListener<Event>() {

                            @Override
                            public void onEvent(Event event) throws Exception {
                                content.setSrc(null);
                                content.setSrc("/work/models/swbsocial/admin/zul/cnf_GenericRedirect.zul");
                                content.setDynamicProperty("wsiteAdm", wsiteAdm);
                                content.setDynamicProperty("action", "community");
                            }
                        });
                    }
                });
                Menuitem mItemNewd = new Menuitem();
                mItemNewd.setLabel("Cerrar los demas");
                mItemNewd.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

                    @Override
                    public void onEvent(Event event) throws Exception {
                        Tab tb = (Tab) page.getFellow("idTab" + uriTab);
                        String tabs = "";
                        String idCommunity = "idCommunity" + wsiteAdm.getWebPage("Community").getId();
                        try {
                            for (int i = 0; i < tabs_gen.getChildren().size(); i++) {
                                if (tabs.trim().length() > 0) {
                                    tabs += "|";
                                }
                                tabs += tabs_gen.getChildren().get(i).getId();
                            }
                            String[] socialTabs = tabs.split("\\|");
                            for (int i = 0; i < socialTabs.length; i++) {
                                String tmp_socialUri = socialTabs[i];
                                if (!tmp_socialUri.equals(tb.getId()) && !tmp_socialUri.equals(idCommunity)) {
                                    ((Tab) page.getFellow(tmp_socialUri)).close();
                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                });
                treePopup.appendChild(mItemNew);
                treePopup.appendChild(mItemNewt);
                treePopup.appendChild(mItemNewd);
                treePopup.open((Tab) event.getTarget().getFellow("idTab" + event.getTarget().getAttribute("idTab").toString()));
            }
        });
    }
    String uriTab = "";
}
