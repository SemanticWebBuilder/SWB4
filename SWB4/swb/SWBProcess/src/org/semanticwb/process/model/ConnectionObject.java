/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.process.model;

import org.semanticwb.model.User;
import org.semanticwb.process.utils.Point;


public class ConnectionObject extends org.semanticwb.process.model.base.ConnectionObjectBase 
{
    public ConnectionObject(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void execute(FlowNodeInstance source, User user)
    {
        //Implementar en subclases
    }

    public boolean evaluate(FlowNodeInstance source, User user)
    {
        return true;
    }
    
    public Point getSartConnectionPoint(GraphicalElement ini, GraphicalElement end) {
        Point ret = null;
        double retX = 0;
        double retY = 0;
        Point lnode = new Point(end.getX(), end.getY());
        
        if (getConnectionPoints() != null && getConnectionPoints().trim().length() > 0) {
            String [] handles = getConnectionPoints().split("\\|");
            Point p = Point.fromString(handles[0]);
            if (p != null) {
                lnode = new Point(p.getX(), p.getY());
            }
        }
        
        if (ini != null && end != null) {
            double dx = lnode.getX() - ini.getX();
            double dy = lnode.getY() - ini.getY();
            if(Math.abs(dx) >= Math.abs(dy)) {
                if(dx > 0) {
                    retX = ini.getX() + ini.getWidth() / 2 + 2;
                } else {
                    retX = ini.getX() - ini.getWidth() / 2 - 2;
                }
            } else {
                retX = ini.getX();
            }
            
            if(Math.abs(dy) > Math.abs(dx)) {
                if(dy > 0) {
                    retY = ini.getY() + ini.getHeight() / 2 + 2;
                } else {
                    retY = ini.getY() - ini.getHeight() / 2 - 2;
                }
            } else {
                retY = ini.getY();
            }
            ret = new Point(retX, retY);
        }
        
        return ret;
    }
        
    public Point getEndConnectionPoint(GraphicalElement ini, GraphicalElement end) {
        Point ret = null;
        double retX = 0;
        double retY = 0;
        Point lnode = new Point(end.getX(), end.getY());
        
        if (getConnectionPoints() != null && getConnectionPoints().trim().length() > 0) {
            String [] handles = getConnectionPoints().split("\\|");
            Point p = Point.fromString(handles[handles.length-1]);
            if (p != null) {
                lnode = new Point(p.getX(), p.getY());
            }
        }
        
        if (ini != null && end != null) {
            double dx = lnode.getX() - ini.getX();
            double dy = lnode.getY() - ini.getY();
            if(Math.abs(dx) >= Math.abs(dy)) {
                if(dx > 0) {
                    retX = ini.getX() + ini.getWidth() / 2 + 2;
                } else {
                    retX = ini.getX() - ini.getWidth() / 2 - 2;
                }
            } else {
                retX = ini.getX();
            }
            
            if(Math.abs(dy) > Math.abs(dx)) {
                if(dy > 0) {
                    retY = ini.getY() + ini.getHeight() / 2 + 2;
                } else {
                    retY = ini.getY() - ini.getHeight() / 2 - 2;
                }
            } else {
                retY = ini.getY();
            }
            ret = new Point(retX, retY);
        }
        
        return ret;
    }
    
    public double getInter1ConnectionX(GraphicalElement ini, GraphicalElement end, Point pini, Point pend) {
        double ret = 0;
        
        if(end != null) {
            if(ini.getY() != pini.getY()) {
                ret = pini.getX();
            } else {
                ret = pini.getX() + (pend.getX() - pini.getX()) / 2;
            }
        } else {
            ret = pini.getX();
        }
        return ret;
    }
    
    public double getInter1ConnectionY(GraphicalElement ini, GraphicalElement end, Point pini, Point pend) {
        double ret = 0;
        
        if(end != null) {
            if(ini.getY() != pini.getY()) {
                ret = pini.getY()+(pend.getY() - pini.getY())/2;
            } else {
                ret = pini.getY();
            }
        } else {
            ret = pini.getY();
        }
        return ret;
    }
    
    public double getInter2ConnectionX(GraphicalElement ini, GraphicalElement end, Point pini, Point pend) {
        double ret = 0;
        
        if(end != null) {
            if(end.getY() != pend.getY()) {
                ret = pend.getX();
            } else {
                ret = pini.getX() + (pend.getX() - pini.getX()) / 2;
            }
        } else {
            ret = getInter1ConnectionX(ini, end, pini, pend);
        }
        return ret;
    }
    
    public double getInter2ConnectionY(GraphicalElement ini, GraphicalElement end, Point pini, Point pend) {
        double ret = 0;
        
        if(end != null) {
            if(end.getY() != pend.getY()) {
                ret = pini.getY() + (pend.getY() - pini.getY()) / 2;
            } else {
                ret = pend.getY();
            }
        } else {
            ret = getInter1ConnectionY(ini, end, pini, pend);
        }
        return ret;
    }
}
