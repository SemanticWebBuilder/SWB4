/*
 * Message.java
 *
 * Created on 12 de mayo de 2003, 18:40
 */
package infotec.app.infochat.multiserver;


/**
 *
 * @author  Administrador
 */
public class Message
{
    public String message = null;
    public String community = null;
    
    /** Creates a new instance of Message */
    public Message(String message, String community)
    {
        this.message = message;
        this.community = community;
    }
}
