/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.model;

import java.util.List;
import org.semanticwb.SWBUtils;

/**
 *
 * @author javier.solis.g
 */
public class TestUsers
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
        List list=SWBUtils.Collections.copyIterator(User.ClassMgr.listUsers());
    }
}
