/*
 * SWBResourceModes.java
 *
 * Created on 8 de junio de 2004, 05:57 PM
 */

package org.semanticwb.portal.api;

/**
 * Interfase que define las constantes requeridas por WBPartameter
 * @author Javier Solis Gonzalez
 */
public interface SWBResourceModes
{
    public static final String WinState_NORMAL="normal";
    public static final String WinState_MAXIMIZED="maximized";
    public static final String WinState_MINIMIZED="minimized";    
    
    public static final String Mode_EDIT="edit";
    public static final String Mode_VIEW="view";
    public static final String Mode_HELP="help";
    public static final String Mode_ADMIN="admin";
    public static final String Mode_ADMHLP="admhlp";
    public static final String Mode_XML="XML";
    public static final String Mode_INDEX="index";
    
    public static final String Action_EDIT="edit";
    public static final String Action_ADD="add";
    public static final String Action_REMOVE="remove";

    public static final int UsrLevel_NONE=0;
    public static final int UsrLevel_SEE=1;
    public static final int UsrLevel_CREATE=2;
    public static final int UsrLevel_MODIFY=3;
    public static final int UsrLevel_REMOVE=4;
    public static final int UsrLevel_ADMIN=5;
    
    public static final int Call_CONTENT=1;
    public static final int Call_STRATEGY=2;
    public static final int Call_DIRECT=3;    
    
    public static final int UrlType_RENDER=0;
    public static final int UrlType_ACTION=1;
}
