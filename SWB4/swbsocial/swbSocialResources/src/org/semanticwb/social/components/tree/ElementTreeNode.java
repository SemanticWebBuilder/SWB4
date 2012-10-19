/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.components.tree;

import org.zkoss.zul.DefaultTreeNode;

/**
 *
 * @author jorge.jimenez
 * @date 10/03/2012
*/

/*
 * Clase Implementación de la clase DefaultTreeNode del framework de zkoss
 * Aqui es donde declaramos que nuestra clase element, se comporte como un DefaultTreeNode
 * Al crear una clase de este tipo, estamos creando una clase DefaultTreeNode<Element> para el árbol
 */
public final class ElementTreeNode extends DefaultTreeNode<Element> {
    /**
     *
     */
    private static final long serialVersionUID = -9145887024839938518L;

    private boolean open = false;

   
    /*
     * Metodo donde agrego un elemento al modelo del árbol, ademas de sus hijos
     * @param data Element generado como información del modelo
     * @param children
     */
    public ElementTreeNode(Element data, DefaultTreeNode<Element>[] children) {
        super(data, children);
    }

    /*
     * Metodo donde agrego un elemento al modelo del árbol, ademas de sus hijos y le digo si el nodo se muestre abierto o cerrado
     * @param data Element generado como información del modelo
     * @param children
     * @param open boolean que indica si se mostrará abierto el nodo o cerrado
     */
    public ElementTreeNode(Element data, DefaultTreeNode<Element>[] children, boolean open) {
        super(data, children);
        setOpen(open);
    }

    /*
     * Metodo donde agrego un elemento al modelo del árbol
     * @param data Element generado como información del modelo
     */
    public ElementTreeNode(Element data) {
        super(data);
    }

    /*
     * Metodo donde se obtiene si el elemento aparece abierto o no
     */
    public boolean isOpen() {
        return open;
    }

    /*
     * Metodo que asigna un valor de abierto o cerrado a un determinado al nodo en cuestión.
     */
    public void setOpen(boolean open) {
        this.open = open;
    }

}