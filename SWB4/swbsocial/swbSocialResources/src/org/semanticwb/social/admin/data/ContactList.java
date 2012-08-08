/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.admin.data;

import org.semanticwb.social.admin.ContactTreeNode;
import org.semanticwb.social.admin.data.pojo.Contact;

/**
 *
 * @author jorge.jimenez
 */

/*
public class ContactList {
    public final static String Category = "Category";
    public final static String Contact = "Contact";

    private ContactTreeNode root;
    public ContactList() {
        root = new ContactTreeNode(null,
            new ContactTreeNode[] {
                new ContactTreeNode(new Contact("Entidad 1", "cloud_new.png"),new ContactTreeNode[] {
                    new ContactTreeNode(new Contact("Redes Sociales", "coinstack_check.png"), new ContactTreeNode[] {
                        new ContactTreeNode(new Contact("Twitter", "comments_add.png")),
                        new ContactTreeNode(new Contact("Facebook", "comments_add.png")),
                        new ContactTreeNode(new Contact("LinkedIn", "comments_add.png")),
                        new ContactTreeNode(new Contact("Youtube", "comments_add.png"))
                    },true),
                    new ContactTreeNode(new Contact("Escuchar", "Contact-i.png")),
                    new ContactTreeNode(new Contact("Análisis",  "barchart.pn")),
                    new ContactTreeNode(new Contact("Contactos", "Contact.png")),
                    new ContactTreeNode(new Contact("Reglas"), new ContactTreeNode[] {
                        new ContactTreeNode(new Contact("Regla 1", "Contact.png")),
                        new ContactTreeNode(new Contact("Regla 2", "Contact.png")),
                        new ContactTreeNode(new Contact("Regla 3", "Contact.png"))
                    },true),
                    new ContactTreeNode(new Contact("Calendarios"), new ContactTreeNode[] {
                        new ContactTreeNode(new Contact("Calendario 1", "calendar_check.png")),
                        new ContactTreeNode(new Contact("Calendario 2", "calendar_check.png")),
                        new ContactTreeNode(new Contact("Calendario 3", "calendar_check.png"))
                    },true),
                    new ContactTreeNode(new Contact("Flujos Pub."), new ContactTreeNode[] {
                        new ContactTreeNode(new Contact("Flujo 1", "Contact.png")),
                        new ContactTreeNode(new Contact("Flujo 2", "Contact.png")),
                        new ContactTreeNode(new Contact("Flujo 3", "Contact.png"))
                    },true),
                    new ContactTreeNode(new Contact("Rep. Usuarios", "user.png")),
                    new ContactTreeNode(new Contact("Publicar",  "globe_edit"))
                },true),
                new ContactTreeNode(new Contact("Entidad 2", "cloud_new.png"),new ContactTreeNode[] {
                    new ContactTreeNode(new Contact("Redes Sociales", "coinstack_check.png"), new ContactTreeNode[] {
                        new ContactTreeNode(new Contact("Twitter", "Contact.png")),
                        new ContactTreeNode(new Contact("Facebook", "Contact.png")),
                        new ContactTreeNode(new Contact("LinkedIn", "Contact.png")),
                        new ContactTreeNode(new Contact("Youtube", "Contact.png"))
                    },true),
                    new ContactTreeNode(new Contact("Escuchar", "Contact-i.png")),
                    new ContactTreeNode(new Contact("Análisis",  "Contact.png")),
                    new ContactTreeNode(new Contact("Contactos", "Contact.png")),
                    new ContactTreeNode(new Contact("Reglas"), new ContactTreeNode[] {
                        new ContactTreeNode(new Contact("Regla 1", "Contact.png")),
                        new ContactTreeNode(new Contact("Regla 2", "Contact.png")),
                        new ContactTreeNode(new Contact("Regla 3", "Contact.png"))
                    },true),
                    new ContactTreeNode(new Contact("Calendarios"), new ContactTreeNode[] {
                        new ContactTreeNode(new Contact("Calendario 1", "Contact.png")),
                        new ContactTreeNode(new Contact("Calendario 2", "Contact.png")),
                        new ContactTreeNode(new Contact("Calendario 3", "Contact.png"))
                    },true),
                    new ContactTreeNode(new Contact("Flujos Pub."), new ContactTreeNode[] {
                        new ContactTreeNode(new Contact("Flujo 1", "Contact.png")),
                        new ContactTreeNode(new Contact("Flujo 2", "Contact.png")),
                        new ContactTreeNode(new Contact("Flujo 3", "Contact.png"))
                    },true),
                    new ContactTreeNode(new Contact("Rep. Usuarios", "Contact-i.png")),
                    new ContactTreeNode(new Contact("Publicar",  "Contact.png"))
                })
            },true
        );
    }
 * */

 // A realizar de manera dinámica
    public class ContactList {
    public final static String Category = "Category";
    public final static String Contact = "Contact";

    private ContactTreeNode root;
    public ContactList() {
        root = new ContactTreeNode(null,
            new ContactTreeNode[] {
                new ContactTreeNode(new Contact("Entidad 1", "cloud_new.png"),new ContactTreeNode[] {
                    new ContactTreeNode(new Contact("Redes Sociales", "coinstack_check.png"), new ContactTreeNode[] {
                        new ContactTreeNode(new Contact("Twitter", "comments_add.png")),
                        new ContactTreeNode(new Contact("Facebook", "comments_add.png")),
                        new ContactTreeNode(new Contact("LinkedIn", "comments_add.png")),
                        new ContactTreeNode(new Contact("Youtube", "comments_add.png"))
                    },true),
                    new ContactTreeNode(new Contact("Escuchar", "Contact-i.png")),
                    new ContactTreeNode(new Contact("Análisis",  "barchart.pn")),
                    new ContactTreeNode(new Contact("Contactos", "Contact.png")),
                    new ContactTreeNode(new Contact("Reglas"), new ContactTreeNode[] {
                        new ContactTreeNode(new Contact("Regla 1", "Contact.png")),
                        new ContactTreeNode(new Contact("Regla 2", "Contact.png")),
                        new ContactTreeNode(new Contact("Regla 3", "Contact.png"))
                    },true),
                    new ContactTreeNode(new Contact("Calendarios"), new ContactTreeNode[] {
                        new ContactTreeNode(new Contact("Calendario 1", "calendar_check.png")),
                        new ContactTreeNode(new Contact("Calendario 2", "calendar_check.png")),
                        new ContactTreeNode(new Contact("Calendario 3", "calendar_check.png"))
                    },true),
                    new ContactTreeNode(new Contact("Flujos Pub."), new ContactTreeNode[] {
                        new ContactTreeNode(new Contact("Flujo 1", "Contact.png")),
                        new ContactTreeNode(new Contact("Flujo 2", "Contact.png")),
                        new ContactTreeNode(new Contact("Flujo 3", "Contact.png"))
                    },true),
                    new ContactTreeNode(new Contact("Rep. Usuarios", "user.png")),
                    new ContactTreeNode(new Contact("Publicar",  "globe_edit"))
                },true),
                new ContactTreeNode(new Contact("Entidad 2", "cloud_new.png"),new ContactTreeNode[] {
                    new ContactTreeNode(new Contact("Redes Sociales", "coinstack_check.png"), new ContactTreeNode[] {
                        new ContactTreeNode(new Contact("Twitter", "Contact.png")),
                        new ContactTreeNode(new Contact("Facebook", "Contact.png")),
                        new ContactTreeNode(new Contact("LinkedIn", "Contact.png")),
                        new ContactTreeNode(new Contact("Youtube", "Contact.png"))
                    },true),
                    new ContactTreeNode(new Contact("Escuchar", "Contact-i.png")),
                    new ContactTreeNode(new Contact("Análisis",  "Contact.png")),
                    new ContactTreeNode(new Contact("Contactos", "Contact.png")),
                    new ContactTreeNode(new Contact("Reglas"), new ContactTreeNode[] {
                        new ContactTreeNode(new Contact("Regla 1", "Contact.png")),
                        new ContactTreeNode(new Contact("Regla 2", "Contact.png")),
                        new ContactTreeNode(new Contact("Regla 3", "Contact.png"))
                    },true),
                    new ContactTreeNode(new Contact("Calendarios"), new ContactTreeNode[] {
                        new ContactTreeNode(new Contact("Calendario 1", "Contact.png")),
                        new ContactTreeNode(new Contact("Calendario 2", "Contact.png")),
                        new ContactTreeNode(new Contact("Calendario 3", "Contact.png"))
                    },true),
                    new ContactTreeNode(new Contact("Flujos Pub."), new ContactTreeNode[] {
                        new ContactTreeNode(new Contact("Flujo 1", "Contact.png")),
                        new ContactTreeNode(new Contact("Flujo 2", "Contact.png")),
                        new ContactTreeNode(new Contact("Flujo 3", "Contact.png"))
                    },true),
                    new ContactTreeNode(new Contact("Rep. Usuarios", "Contact-i.png")),
                    new ContactTreeNode(new Contact("Publicar",  "Contact.png"))
                })
            },true
        );
    }

    public ContactTreeNode getRoot() {
        return root;
    }
}