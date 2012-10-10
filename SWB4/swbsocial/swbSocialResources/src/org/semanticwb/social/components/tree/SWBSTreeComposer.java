/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.components.tree;

/**
 *
 * @author jorge.jimenez
 * @date 10/03/2012
 */

import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.social.TreeNodePage;
import java.net.URLEncoder;
import org.semanticwb.SWBPlatform;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.social.Childrenable;
import org.semanticwb.social.utils.SWBSocialResourceUtils;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.Messagebox;
import org.zkoss.zk.ui.event.DropEvent;


/*
 * Clase controladora del árbol de navegación
 */
public class SWBSTreeComposer extends GenericForwardComposer <Component> {
    /**
     *
     */
    //private static final long serialVersionUID = 3814570327995355261L;

    private static Logger log = SWBUtils.getLogger(SWBSTreeComposer.class);
    private Tree tree;
    private Include content;
    //private Include treeProps;
    private AdvancedTreeModel elemenetTreeModel;
    private Menupopup treePopup;
    private Messagebox messageBox;
    WebSite wsiteAdm=null;
    User user=null;
    SWBParamRequest paramRequest=null;

    /*
    * Metodo implementación de la clase padre, este metodo se ejecuta una vez cargado
    * el componente.
    */
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        try
        {
            paramRequest=(SWBParamRequest)requestScope.get("paramRequest");
            if(paramRequest!=null)
            {
                //this.getPage().getDesktop().setAttribute("paramRequest", paramRequest);
                wsiteAdm=paramRequest.getWebPage().getWebSite();
                if(wsiteAdm!=null)
                {
                    user=paramRequest.getUser();
                    elemenetTreeModel = new AdvancedTreeModel(new ElementList(paramRequest).getRoot());
                    tree.setModel(elemenetTreeModel);
                    tree.setItemRenderer(new ElementTreeRenderer());
                    tree.setZclass("z-dottree");    //Estilo del icono de los nodos abiertos y cerrados"[+]...[-]"
                    tree.setVflex(true);
                    comp.getPage().getDesktop().getSession().setAttribute("elemenetTreeModel", elemenetTreeModel);
                }
            }
        }catch(Exception e)
        {
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
            try
            {
                ElementTreeNode ctn = treeNode;
                Element element = (Element) ctn.getData();
                //Lo siguiente lo hice ya que envíaba error "only one treerow is allowed", cuando tenía solo la línea
                //Treerow dataRow=new Treerow();, pero con esto se corrigió
                Treerow dataRow = treeItem.getTreerow();
                if(dataRow == null) {
                    dataRow = new Treerow();
                } else {
                    dataRow.getChildren().clear();
                }

                dataRow.setParent(treeItem);
                treeItem.setValue(ctn);
                treeItem.setOpen(ctn.isOpen());

                {
                    Hlayout hl = new Hlayout();
                    //hl.appendChild(new Image("/work/models/"+wsiteAdm.getId()+"/admin/img/" + element.getIconElement()));
                    hl.appendChild(new Image(element.getIconElement()));
                    hl.appendChild(new Label(element.getName()));
                    hl.setSclass("h-inline-block");
                    Treecell treeCell = new Treecell();
                    treeCell.appendChild(hl);
                    dataRow.setDraggable("true");
                    dataRow.appendChild(treeCell);
                    //Manejo de click derecho en los elementos del árbol
                    dataRow.addEventListener(Events.ON_RIGHT_CLICK, new EventListener<Event>() {
                        @Override
                        public void onEvent(Event event) throws Exception {
                            final Treeitem selectedTreeItem = tree.getSelectedItem();
                            final ElementTreeNode itemValue = (ElementTreeNode) selectedTreeItem.getValue();
                            treePopup.getChildren().clear();
                            if(isCategory(itemValue.getData())){ //Es una categoría
                                Menuitem mItemNew=new Menuitem();
                                mItemNew.setLabel("Crear nuevo");
                                mItemNew.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
                                @Override
                                public void onEvent(Event event) throws Exception {
                                    content.setSrc(null);
                                    //content.setSrc("/work/models/swbsocial/admin/zul/clearCompose.zul");
                                    WebPage adminWebPage=wsiteAdm.getWebPage(itemValue.getData().getUri());
                                    if(adminWebPage instanceof TreeNodePage)
                                    {
                                        TreeNodePage treeNodePage=(TreeNodePage) adminWebPage;
                                        content.setSrc(treeNodePage.getZulResourcePath());
                                    }
                                    
                                    content.setDynamicProperty("treeItem", itemValue);
                                    content.setDynamicProperty("action", SWBSocialResourceUtils.ACTION_ADD);
                                    content.setDynamicProperty("user", user);

                                    //Se obtiene website del nodo en cuestion, es decir, no es el sitio de admin.
                                    //Tomando en cuenta que el siguiente padre de una categoría es el nodo del sitio
                                    Treeitem wsiteItem=selectedTreeItem.getParentItem();
                                    ElementTreeNode wsiteItemValue = (ElementTreeNode) wsiteItem.getValue();
                                    if(wsiteItemValue.getData().getUri()!=null)
                                    {
                                        SemanticObject semObject = SemanticObject.createSemanticObject(wsiteItemValue.getData().getUri());
                                        if(semObject!=null)
                                        {
                                            WebSite wsite = (WebSite) semObject.createGenericInstance();
                                            content.setDynamicProperty("wsite", wsite);
                                        }
                                    }
                                    content.setDynamicProperty("optionWepPage", adminWebPage);
                                }
                                });
                                treePopup.appendChild(mItemNew);
                                treePopup.open(selectedTreeItem);
                            }else{ //Es un elemento

                                Treeitem parentItem = selectedTreeItem.getParentItem();
                                final ElementTreeNode parentItemValue = (ElementTreeNode) parentItem.getValue();

                                //MENU CONTEXTUAL

                                final SemanticObject semObj=SemanticObject.createSemanticObject(itemValue.getData().getUri());

                                //Opción Crear, para los nodos que sean de tipo Childrenable
                                if(semObj.getSemanticClass().isSubClass(Childrenable.social_Childrenable))
                                {
                                    Menuitem mItemNew=new Menuitem();
                                    mItemNew.setLabel("Crear nuevo");
                                    mItemNew.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
                                    @Override
                                    public void onEvent(Event event) throws Exception {
                                        content.setSrc(null);   //En teoría, esta línea hace lo que hace la línea de abajo (Todo:Probar y Quitar la de abajo)
                                        //content.setSrc("/work/models/swbsocial/admin/zul/clearCompose.zul");
                                        WebPage adminWebPage=wsiteAdm.getWebPage(itemValue.getData().getCategoryID());
                                        if(adminWebPage instanceof TreeNodePage)
                                        {
                                            TreeNodePage treeNodePage=(TreeNodePage) adminWebPage;
                                            content.setSrc(treeNodePage.getZulResourcePath());
                                        }

                                        content.setDynamicProperty("treeItem", itemValue);
                                        content.setDynamicProperty("action", SWBSocialResourceUtils.ACTION_ADD);
                                        content.setDynamicProperty("user", user);
                                        WebSite wsite=(WebSite)semObj.getModel().getModelObject().createGenericInstance();
                                        content.setDynamicProperty("wsite", wsite);
                                        content.setDynamicProperty("optionWepPage", adminWebPage);
                                    }
                                    });
                                    treePopup.appendChild(mItemNew);
                                }



                                //------------Opciones por defecto----------//


                                //Opción Eliminar TODO:Que aparezca mensaje antes de eliminar un nodo:¿Esta seguro de querer eliminar el elemento?
                                Menuitem mItemRemove=new Menuitem();
                                mItemRemove.setLabel("Eliminar");
                                mItemRemove.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
                                @Override
                                public void onEvent(Event event) throws Exception {
                                    messageBox.show("Esta seguro de eliminar este elemento?", "deleteConfirm", Messagebox.YES|Messagebox.NO, Messagebox.QUESTION,
                                     new EventListener() {
                                       public void onEvent(Event evt) {
                                         switch (((Integer)evt.getData()).intValue()) {
                                           case Messagebox.YES:
                                               if(semObj!=null)
                                               {
                                                   if(semObj.getSemanticClass().isSubClass(Childrenable.social_Childrenable))
                                                   {
                                                       if(SWBSocialResourceUtils.Semantic.removeObjChildrenable(semObj))
                                                       {
                                                            semObj.remove();
                                                       }
                                                   }else
                                                   {
                                                        semObj.remove();
                                                   }
                                                   elemenetTreeModel.remove(itemValue);
                                                   break;
                                               }
                                           //case Messagebox.NO: doNo(); break;
                                      }
                                    }
                                   });
                                }
                                });
                                treePopup.appendChild(mItemRemove);

                                //Opción Activar/Desactivar

                                Menuitem mItemActive=new Menuitem();
                                mItemActive.setLabel("Activar");
                                mItemActive.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
                                @Override
                                public void onEvent(Event event) throws Exception {
                                    content.setSrc("/work/models/swbsocial/admin/zul/"+parentItemValue.getData().getZulPage());
                                    content.setDynamicProperty("objUri", itemValue.getData().getUri());
                                    content.setDynamicProperty("paramRequest", paramRequest);;
                                }
                                });
                                treePopup.appendChild(mItemActive);


                                //------------Opciones dependiendo del nodo del árbol----------//

                                WebPage treeCategoryNode=wsiteAdm.getWebPage(itemValue.getData().getCategoryID());
                                Iterator <WebPage> itTreeCategoryNodeChilds=treeCategoryNode.listVisibleChilds(user.getLanguage());
                                while(itTreeCategoryNodeChilds.hasNext())
                                {
                                    final WebPage wpageOption=itTreeCategoryNodeChilds.next();
                                    Menuitem mItemCustomOpt=new Menuitem();
                                    mItemCustomOpt.setLabel(wpageOption.getDisplayName(user.getLanguage()));
                                    mItemCustomOpt.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
                                    @Override
                                    public void onEvent(Event event) throws Exception {
                                        String zulPage=parentItemValue.getData().getZulPage();
                                        String action=SWBSocialResourceUtils.ACTION_EDIT;
                                        if(wpageOption instanceof TreeNodePage)
                                        {
                                            TreeNodePage TreeNodePage=(TreeNodePage)wpageOption;
                                            if(TreeNodePage.getZulResourcePath()!=null)
                                            {
                                                zulPage=TreeNodePage.getZulResourcePath();
                                            }
                                            if(TreeNodePage.getAction()!=null)
                                            {
                                                action=TreeNodePage.getAction();
                                            }
                                        }
                                        content.setSrc(null);//En teoría, esta línea hace lo que hace la línea de abajo (Todo:Probar y Quitar la de abajo)
                                        //content.setSrc("/work/models/swbsocial/admin/zul/clearCompose.zul");
                                        content.setSrc(zulPage);
                                        content.setDynamicProperty("objUri", URLEncoder.encode(itemValue.getData().getUri()));
                                        content.setDynamicProperty("paramRequest", paramRequest);;
                                        content.setDynamicProperty("action", action);
                                        content.setDynamicProperty("optionWepPage", wpageOption);
                                        content.setDynamicProperty("treeItem", itemValue);
                                        WebSite wsite=(WebSite)semObj.getModel().getModelObject().createGenericInstance();
                                        content.setDynamicProperty("wsite", wsite);
                                        content.setDynamicProperty("user", user);
                                    }
                                    });
                                    treePopup.appendChild(mItemCustomOpt);
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
                            final Treeitem selectedTreeItem = tree.getSelectedItem();
                            final ElementTreeNode itemValue = (ElementTreeNode) selectedTreeItem.getValue();                            
                            if(!isCategory(itemValue.getData())){ //Si es una categoría
                                //final Treeitem selectedTreeItemParent = tree.getSelectedItem().getParentItem();
                                //final ElementTreeNode itemValueParent = (ElementTreeNode) selectedTreeItemParent.getValue();
                                WebPage wpage=wsiteAdm.getWebPage(itemValue.getData().getCategoryID());
                                String zulPage="";
                                if(wpage!=null && wpage instanceof TreeNodePage)
                                {
                                    TreeNodePage TreeNodePage=(TreeNodePage)wpage;
                                    if(TreeNodePage.getZulResourcePath()!=null)
                                    {
                                        zulPage=TreeNodePage.getZulResourcePath();
                                    }
                                }
                                content.setSrc(null);//En teoría, esta línea hace lo que hace la línea de abajo (Todo:Probar y Quitar la de abajo)
                                content.setSrc("/work/models/swbsocial/admin/zul/clearCompose.zul");
                                if(zulPage!=null)
                                {
                                    content.setSrc(zulPage);
                                }
                                content.setDynamicProperty("objUri", URLEncoder.encode(itemValue.getData().getUri()));
                                //content.setDynamicProperty("parentItem", itemValueParent);
                                content.setDynamicProperty("treeItem", itemValue);
                                content.setDynamicProperty("action", SWBSocialResourceUtils.ACTION_DOUBLECLICK);
                                content.setDynamicProperty("wsiteAdm", wsiteAdm);
                                content.setDynamicProperty("user", user);

                                WebSite wsite=WebSite.ClassMgr.getWebSite(SemanticObject.getSemanticObject(itemValue.getData().getUri()).getModel().getName());
                                
                                if(wsite!=null)
                                {
                                    content.setDynamicProperty("wsite",wsite);
                                }                                
                                content.setDynamicProperty("optionWepPage", null);
                            }
                        }
                    });
                }

                //Bloque de código para soportar drag&drop. Solo en los casos de que los nodos sean de tipo Childrenable
                final ElementTreeNode itemValue = (ElementTreeNode) treeItem.getValue();
                //System.out.println("itemValue-00:"+itemValue.getData().getUri());
                if(itemValue.getData().getUri()!=null)
                {
                    SemanticObject semObj=SemanticObject.createSemanticObject(itemValue.getData().getUri());
                    if(semObj!=null && semObj.getSemanticClass().isSubClass(Childrenable.social_Childrenable))
                    {
                        //System.out.println("semObj NO ES NULO:"+semObj.getURI());
                        dataRow.setDraggable("true");
                        dataRow.setDroppable("true");
                        dataRow.addEventListener(Events.ON_DROP, new EventListener<Event>()
                        {
                            @SuppressWarnings("unchecked")
                            @Override
                            public void onEvent(Event event) throws Exception
                            {
                                // The dragged target is a TreeRow belongs to an
                                // Treechildren of TreeItem.
                                Treeitem draggedItem = (Treeitem) ((DropEvent) event).getDragged().getParent();
                                ElementTreeNode draggedValue = (ElementTreeNode) draggedItem.getValue();

                                //Treeitem parentItem = treeItem.getParentItem();
                                ElementTreeNode parentData = (ElementTreeNode) treeItem.getValue();
                                //System.out.println("draggedValue-0:"+draggedValue+",parentData-0:"+parentData);
                                //System.out.println("draggedValue:"+draggedValue.getData().getUri()+",parentData:"+parentData.getData().getUri());
                                if(draggedValue.getData().getModelID().equals(parentData.getData().getModelID()))
                                {
                                        elemenetTreeModel.add(parentData, new ElementTreeNode[] { draggedValue });
                                        //elemenetTreeModel.remove(draggedValue);
                               }
                            }
                        });
                    }else{
                        //System.out.println("itemValue-00:"+itemValue.getData().getUri()+",Es NULO");
                        dataRow.setDraggable("false");
                        dataRow.setDroppable("false");
                        //Se revisa si es una sección de la administración y si su semanticClass es de tipo Childrenable
                        if(itemValue.getData().getUri()!=null)
                        {
                            WebPage wpage=wsiteAdm.getWebPage(itemValue.getData().getUri());
                            if(wpage instanceof TreeNodePage)
                            {
                                TreeNodePage TreeNodePage=(TreeNodePage)wpage;
                                if(TreeNodePage.getClassUri()!=null)
                                {
                                    SemanticClass swbClass = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(TreeNodePage.getClassUri());
                                    if(swbClass.isSubClass(Childrenable.social_Childrenable))
                                    {
                                         dataRow.setDroppable("true");
                                         dataRow.addEventListener(Events.ON_DROP, new EventListener<Event>()
                                         {
                                            @SuppressWarnings("unchecked")
                                            @Override
                                            public void onEvent(Event event) throws Exception
                                            {
                                                Treeitem draggedItem = (Treeitem) ((DropEvent) event).getDragged().getParent();
                                                ElementTreeNode draggedValue = (ElementTreeNode) draggedItem.getValue();

                                                //Treeitem parentItem = treeItem.getParentItem();
                                                ElementTreeNode parentData = (ElementTreeNode) treeItem.getValue();
                                                //System.out.println("draggedValue-0:"+draggedValue+",parentData-0:"+parentData);
                                                //System.out.println("draggedValue:"+draggedValue.getData().getUri()+",parentData:"+parentData.getData().getUri());
                                                if(draggedValue.getData().getModelID().equals(parentData.getData().getModelID()))
                                                {
                                                    elemenetTreeModel.add(parentData, new ElementTreeNode[] { draggedValue });
                                                    //elemenetTreeModel.remove(draggedValue);
                                                }
                                            }
                                         });
                                    }
                                }
                            }
                        }
                    }
                }
            }catch(Exception e)
            {
                log.error(e);
            }
        }

        /*
         * Metodo que regresa si un cierto nodo del árbol es de tipo category o no lo es
         * Ejemplos de nodos tipo Category: Redes Sociales, Streams y Temas
         * @param element Element o nodo a revisar si es de tipo Category
         */
        private boolean isCategory(Element element) {
            if(element.getUri()!=null && element.getUri().indexOf("#")==-1)
            {
                return true;
            }
            return false;
        }
    }

    /*
     class MyListener implements org.zkoss.zk.ui.event.EventListener {

        public void onEvent(org.zkoss.zk.ui.event.Event event) {
            try {
                    content.setSrc("/hello.zul");
                    //content.getPage();
            } catch (Exception ex) 
                ex.printStackTrace();
            }
        }
    }*/


    /*
     * Metodo que se suscribe a un evento para refrescar el árbol de navegación
     *
     */

    //@Subscribe(value = "createElement", scope = EventQueues.SESSION)    //La anotación @Subscribe solo se puede utilizar con Zkos EE (Ya Valio!!)
    public void method2(Event event) {
        // this method will be called when EventQueue "queue2" of Session scope is published
    }

}