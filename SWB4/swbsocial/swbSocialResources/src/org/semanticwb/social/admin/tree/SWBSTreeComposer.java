/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.admin.tree;

/**
 *
 * @author jorge.jimenez
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

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericForwardComposer;
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
    WebSite wsite=null;
    User user=null;
    SWBParamRequest paramRequest=null;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        try
        {
            paramRequest=(SWBParamRequest)requestScope.get("paramRequest");
            if(paramRequest!=null)
            {
                //this.getPage().getDesktop().setAttribute("paramRequest", paramRequest);
                wsite=paramRequest.getWebPage().getWebSite();
                if(wsite!=null)
                {
                    user=paramRequest.getUser();
                    elemenetTreeModel = new AdvancedTreeModel(new ElementList(paramRequest).getRoot());
                    tree.setModel(elemenetTreeModel);
                    tree.setItemRenderer(new ElementTreeRenderer());
                    tree.setZclass("z-dottree");    //Estilo del icono de los nodos abiertos y cerrados"[+]...[-]"
                    tree.setVflex(true);
                }
            }
        }catch(Exception e)
        {
            log.error(e);
        }
    }

   
    public final class ElementTreeRenderer implements TreeitemRenderer<ElementTreeNode> {
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
                    //hl.appendChild(new Image("/work/models/"+wsite.getId()+"/admin/img/" + element.getIconElement()));
                    hl.appendChild(new Image(element.getIconElement()));
                    hl.appendChild(new Label(element.getName()));
                    hl.setSclass("h-inline-block");
                    Treecell treeCell = new Treecell();
                    treeCell.appendChild(hl);
                    dataRow.setDraggable("true");
                    dataRow.appendChild(treeCell);

                    dataRow.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener<Event>() {
                        @Override
                        public void onEvent(Event event) throws Exception {
                            ElementTreeNode clickedNodeValue = (ElementTreeNode) ((Treeitem) event.getTarget().getParent())
                                    .getValue();
                            content.setSrc("/hello.zul");
                        }
                    });
                    /*
                    //Sección de propiedades de un nodo del árbol
                    dataRow.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
                        @Override
                        public void onEvent(Event event) throws Exception {
                            ElementTreeNode clickedNodeValue = (ElementTreeNode) ((Treeitem) event.getTarget().getParent())
                                    .getValue();
                            treeProps.setSrc("/treeProperties.zul");
                            //content.getPage();
                        }
                    });*/
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
                                    content.setSrc("/work/models/swbsocial/admin/zul/clearCompose.zul");
                                    content.setSrc("/work/models/swbsocial/admin/zul/"+itemValue.getData().getZulPage());
                                    //Tomando en cuenta que el siguiente padre de una categoría es el nodo del sitio
                                    Treeitem wsiteItem=selectedTreeItem.getParentItem();
                                    ElementTreeNode wsiteItemValue = (ElementTreeNode) wsiteItem.getValue();
                                    if(wsiteItemValue.getData().getUri()!=null)
                                    {
                                        SemanticObject semObject = SemanticObject.createSemanticObject(wsiteItemValue.getData().getUri());
                                        WebSite wsite = (WebSite) semObject.createGenericInstance();
                                        content.setDynamicProperty("wsite", wsite);
                                    }
                                    content.setDynamicProperty("parentItem", itemValue);
                                    content.setDynamicProperty("action", "add");
                                }
                                });
                                treePopup.appendChild(mItemNew);
                                treePopup.open(selectedTreeItem);
                            }else{ //Es un elemento

                                Treeitem parentItem = selectedTreeItem.getParentItem();
                                final ElementTreeNode parentItemValue = (ElementTreeNode) parentItem.getValue();

                                //MENU CONTEXTUAL

                                //------------Opciones por defecto----------//

                                //Opción Editar
                                Menuitem mItemEdit=new Menuitem();
                                mItemEdit.setLabel("Editar");
                                mItemEdit.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
                                @Override
                                public void onEvent(Event event) throws Exception {
                                    //El siguiente "clearCompose.zul", no tiene nada, solo es para que zkoss utilize uno que limpie su
                                    //memoría (de zkoss), ya que si se hace algo con un zul (ejemplo:cnf_socialNetwork), si se utiliza
                                    //nuevamente el mismo zul, ya no entraría el metodo doAfterCompose, ya que ya lo tiene cargado, es por eso,
                                    //que primero envio uno vacio, por si ya estaba cargado uno y despues envio el que quiero mandar, esto
                                    //haría que se siempre se cargue el metodo doAfterCompose del elemento que realmente quiero.
                                    //Esto es un truco, hay que ver si existe algo diferente que se pueda hacer.
                                    content.setSrc("/work/models/swbsocial/admin/zul/clearCompose.zul");
                                    content.setSrc("/work/models/swbsocial/admin/zul/"+parentItemValue.getData().getZulPage());
                                    content.setDynamicProperty("item", itemValue);
                                    content.setDynamicProperty("action", "edit");
                                }
                                });
                                treePopup.appendChild(mItemEdit);

                                //Opción Eliminar TODO:Que aparezca mensaje antes de eliminar un nodo:¿Esta seguro de querer eliminar el elemento?
                                Menuitem mItemRemove=new Menuitem();
                                mItemRemove.setLabel("Eliminar");
                                mItemRemove.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
                                @Override
                                public void onEvent(Event event) throws Exception {
                                    SemanticObject semObject = SemanticObject.createSemanticObject(itemValue.getData().getUri());
                                    semObject.remove();
                                    elemenetTreeModel.remove(itemValue);
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
                                    content.setDynamicProperty("uri", itemValue.getData().getUri());
                                    content.setDynamicProperty("paramRequest", paramRequest);;
                                }
                                });
                                treePopup.appendChild(mItemActive);


                                //------------Opciones dependiendo del nodo del árbol----------//

                                WebPage treeCategoryNode=wsite.getWebPage(parentItemValue.getData().getUri());
                                Iterator <WebPage> itTreeCategoryNodeChilds=treeCategoryNode.listVisibleChilds(user.getLanguage());
                                while(itTreeCategoryNodeChilds.hasNext())
                                {
                                    final WebPage wpageOption=itTreeCategoryNodeChilds.next();
                                    Menuitem mItemCustomOpt=new Menuitem();
                                    mItemCustomOpt.setLabel(wpageOption.getDisplayName(user.getLanguage()));
                                    mItemCustomOpt.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
                                    @Override
                                    public void onEvent(Event event) throws Exception {
                                        String zulPage="/work/models/swbsocial/admin/zul/"+parentItemValue.getData().getZulPage();
                                        String action="";
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
                                        content.setSrc("/work/models/swbsocial/admin/zul/clearCompose.zul");
                                        content.setSrc(zulPage);
                                        content.setDynamicProperty("uri", itemValue.getData().getUri());
                                        content.setDynamicProperty("paramRequest", paramRequest);;
                                        content.setDynamicProperty("action", action);
                                    }
                                    });
                                    treePopup.appendChild(mItemCustomOpt);
                                }


                                /*
                                //Manejo de elementos especificos por categoría en menú contextual
                                if(parentItemValue.getData().getUri()!=null && parentItemValue.getData().getUri().equals("cat:streams")) //Cuando el nodo seleccionado es de tipo stream
                                {
                                    Menuitem mItemListen=new Menuitem();
                                    mItemListen.setLabel("Escuchar");
                                    mItemListen.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
                                    @Override
                                    public void onEvent(Event event) throws Exception {
                                        content.setSrc("/work/models/swbsocial/admin/zul/timeline.zul");
                                        content.setDynamicProperty("uri", itemValue.getData().getUri());
                                        content.setDynamicProperty("paramRequest", paramRequest);;
                                    }
                                    });
                                    treePopup.appendChild(mItemListen);
                                }
                                */

                                //Abre el menú contextual inmediatamente abajo del nodo seleccionado
                                treePopup.open(selectedTreeItem);
                            }
                        }
                    });

                }
                //Bloque de código para soportar drag&drop. TODO: Revisar para implementar bien (Si vemos que debe aplicar)
                /*
                dataRow.setDroppable("true");
                dataRow.addEventListener(Events.ON_DROP, new EventListener<Event>() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void onEvent(Event event) throws Exception {

                        // The dragged target is a TreeRow belongs to an
                        // Treechildren of TreeItem.
                        Treeitem draggedItem = (Treeitem) ((DropEvent) event).getDragged().getParent();
                        ElementTreeNode draggedValue = (ElementTreeNode) draggedItem.getValue();
                        Treeitem parentItem = treeItem.getParentItem();
                        elemenetTreeModel.remove(draggedValue);
                        if (isCategory((Element) ((ElementTreeNode) treeItem.getValue()).getData())) {
                            elemenetTreeModel.add((ElementTreeNode) treeItem.getValue(),
                                    new DefaultTreeNode[] { draggedValue });
                        } else {
                            int index = parentItem.getTreechildren().getChildren().indexOf(treeItem);
                            if(parentItem.getValue() instanceof ElementTreeNode) {
                                elemenetTreeModel.insert((ElementTreeNode)parentItem.getValue(), index, index,
                                        new DefaultTreeNode[] { draggedValue });
                            }

                        }

                    }
                });
                 * */
            }catch(Exception e)
            {
                log.error(e);
            }
        }

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
}