package org.semanticwb.model;

import java.util.ArrayList;
import java.util.Iterator;
import org.semanticwb.model.base.*;
import org.semanticwb.platform.SemanticObject;

public class Role extends RoleBase 
{
    public Role(SemanticObject base)
    {
        super(base);
    }

    public Iterator<Role> listAllChilds()
    {
        ArrayList<Role> arr=new ArrayList();
        addChilds(arr);
        return arr.iterator();
    }

    private void addChilds(ArrayList<Role> arr)
    {
        arr.add(this);
        Iterator<Role> it=listChilds();
        while(it.hasNext())
        {
            Role aux=it.next();
            aux.addChilds(arr);
        }
    }


}
