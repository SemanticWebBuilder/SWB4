/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.admin;

/**
 *
 * @author jorge.jimenez
 */

import org.semanticwb.social.admin.data.ContactList;
import org.semanticwb.social.admin.data.pojo.Contact;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;



public class SWBSTreeComposer extends GenericForwardComposer <Component> {
    /**
     *
     */
    //private static final long serialVersionUID = 3814570327995355261L;

    @Wire
    private Tree tree;
    @Wire
    private Include content;
    @Wire
    private Include treeProps;
    @Wire
    private AdvancedTreeModel contactTreeModel;
    @Wire
    private Popup editPopup;

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        contactTreeModel = new AdvancedTreeModel(new ContactList().getRoot());
        tree.setItemRenderer(new ContactTreeRenderer());
        tree.setModel(contactTreeModel);
        //tree.setAutopaging(true);
    }

    /**
     * The structure of tree
     *
     * <pre>
     * &lt;treeitem>
     *   &lt;treerow>
     *     &lt;treecell>...&lt;/treecell>
     *   &lt;/treerow>
     *   &lt;treechildren>
     *     &lt;treeitem>...&lt;/treeitem>
     *   &lt;/treechildren>
     * &lt;/treeitem>
     * </pre>
     */
    private final class ContactTreeRenderer implements TreeitemRenderer<ContactTreeNode> {
        @Override
        public void render(final Treeitem treeItem, ContactTreeNode treeNode, int index) throws Exception {
            ContactTreeNode ctn = treeNode;
            Contact contact = (Contact) ctn.getData();
            final Treerow dataRow = new Treerow();
            dataRow.setParent(treeItem);
            treeItem.setValue(ctn);
            treeItem.setOpen(ctn.isOpen());

            if (!isCategory(contact)) { // Contact Row
                Hlayout hl = new Hlayout();
                hl.appendChild(new Image("/img/tree/" + contact.getProfilepic()));
                hl.appendChild(new Label(contact.getName()));
                hl.setSclass("h-inline-block");
                Treecell treeCell = new Treecell();
                treeCell.appendChild(hl);
                dataRow.setDraggable("true");
                dataRow.appendChild(treeCell);
                //dataRow.addEventListener("onClick", new MyListener());
                
                dataRow.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener<Event>() {
                    @Override
                    public void onEvent(Event event) throws Exception {
                        ContactTreeNode clickedNodeValue = (ContactTreeNode) ((Treeitem) event.getTarget().getParent())
                                .getValue();
                        content.setSrc("/hello.zul");
                        //content.getPage();
                    }
                });
                dataRow.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
                    @Override
                    public void onEvent(Event event) throws Exception {
                        ContactTreeNode clickedNodeValue = (ContactTreeNode) ((Treeitem) event.getTarget().getParent())
                                .getValue();
                        treeProps.setSrc("/treeProperties.zul");
                        //content.getPage();
                    }
                });
                dataRow.addEventListener(Events.ON_RIGHT_CLICK, new EventListener<Event>() {
                    @Override
                    public void onEvent(Event event) throws Exception {
                        System.out.println("RowJ:"+dataRow.getId() +",:"+dataRow.getLabel());
                        dataRow.setContext(editPopup);
                        //content.getPage();
                    }
                });
            } else { // Category Row
                dataRow.appendChild(new Treecell(contact.getCategory()));
            }
            // Both category row and contact row can be item dropped
            dataRow.setDroppable("true");
            dataRow.addEventListener(Events.ON_DROP, new EventListener<Event>() {
                @SuppressWarnings("unchecked")
                @Override
                public void onEvent(Event event) throws Exception {
                    // The dragged target is a TreeRow belongs to an
                    // Treechildren of TreeItem.
                    Treeitem draggedItem = (Treeitem) ((DropEvent) event).getDragged().getParent();
                    ContactTreeNode draggedValue = (ContactTreeNode) draggedItem.getValue();
                    Treeitem parentItem = treeItem.getParentItem();
                    contactTreeModel.remove(draggedValue);
                    if (isCategory((Contact) ((ContactTreeNode) treeItem.getValue()).getData())) {
                        contactTreeModel.add((ContactTreeNode) treeItem.getValue(),
                                new DefaultTreeNode[] { draggedValue });
                    } else {
                        int index = parentItem.getTreechildren().getChildren().indexOf(treeItem);
                        if(parentItem.getValue() instanceof ContactTreeNode) {
                            contactTreeModel.insert((ContactTreeNode)parentItem.getValue(), index, index,
                                    new DefaultTreeNode[] { draggedValue });
                        }

                    }
                }
            });

        }

        private boolean isCategory(Contact contact) {
            return contact.getName() == null;
        }
    }

     class MyListener implements org.zkoss.zk.ui.event.EventListener {

        public void onEvent(org.zkoss.zk.ui.event.Event event) {
            try {
                    System.out.println("Event NameJ:"+event.getName());
                    System.out.println("EntraJorge:"+content);
                    content.setSrc("/hello.zul");

                    //content.getPage();
                    System.out.println("content:"+content);
            } catch (Exception ex) {
                System.out.println("Error:"+ex.getMessage());
                ex.printStackTrace();
            }


        }
    }
}