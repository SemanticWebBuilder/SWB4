/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.triplestore.virtuoso;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.graph.TripleMatch;
import com.hp.hpl.jena.shared.JenaException;
import com.hp.hpl.jena.util.iterator.NiceIterator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.NoSuchElementException;


/**
 *
 * @author javier.solis.g
 */
public class SWBVirtResSetIter extends NiceIterator<Triple>
{

    protected ResultSet v_resultSet;
    protected Triple v_row;
    protected TripleMatch v_in;
    protected boolean v_finished = false;
    protected boolean v_prefetched = false;
    protected SWBVirtGraph v_graph = null;
    private Statement statement = null;

    public SWBVirtResSetIter()
    {
        //System.out.println("SWBVirtResSetIter()");
        this.v_finished = true;
    }

    public SWBVirtResSetIter(SWBVirtGraph paramVirtGraph, ResultSet paramResultSet, Statement statement, TripleMatch paramTripleMatch)
    {
        //System.out.println("SWBVirtResSetIter4("+paramTripleMatch+")");
        this.v_resultSet = paramResultSet;
        this.v_in = paramTripleMatch;
        this.v_graph = paramVirtGraph;
        this.statement =statement;
    }

    public void reset(ResultSet paramResultSet, PreparedStatement paramPreparedStatement)
    {
        //System.out.println("SWBVirtResSetIter3()");
        this.v_resultSet = paramResultSet;
        this.v_finished = false;
        this.v_prefetched = false;
        this.v_row = null;
    }

    @Override
    public boolean hasNext()
    {
        if ((!this.v_finished) && (!this.v_prefetched))
        {
            moveForward();
        }
        return !this.v_finished;
    }

    @Override
    public Triple removeNext()
    {
        Triple localTriple = next();
        remove();
        return localTriple;
    }

    @Override
    public Triple next()
    {
        if ((!this.v_finished) && (!this.v_prefetched))
        {
            moveForward();
        }
        this.v_prefetched = false;
        if (this.v_finished)
        {
            throw new NoSuchElementException();
        }
        return getRow();
    }

    @Override
    public void remove()
    {
        if ((this.v_row != null) && (this.v_graph != null))
        {
            this.v_graph.delete(this.v_row);
            this.v_row = null;
        }
    }

    protected void moveForward()
    {
        try
        {
            if ((!this.v_finished) && (this.v_resultSet.next()))
            {
                extractRow();
                this.v_prefetched = true;
            } else
            {
                close();
            }
        } catch (Exception localException)
        {
            System.out.println("moveForward error:"+this.v_graph+" "+this.v_in);
            throw new JenaException(localException);
        }
    }

    protected void extractRow()
            throws Exception
    {
        //System.out.print("extractRow:"+this.v_in);
        Node localNode1;
        if (this.v_in.getMatchSubject() != null)
        {
            localNode1 = this.v_in.getMatchSubject();
        } else
        {
            localNode1 = SWBVirtGraph.Object2Node(this.v_resultSet.getObject("s"));
        }
        Node localNode2;
        if (this.v_in.getMatchPredicate() != null)
        {
            localNode2 = this.v_in.getMatchPredicate();
        } else
        {
            localNode2 = SWBVirtGraph.Object2Node(this.v_resultSet.getObject("p"));
        }
        Node localNode3;
        if (this.v_in.getMatchObject() != null)
        {
            localNode3 = this.v_in.getMatchObject();
        } else   
        {
            localNode3 = SWBVirtGraph.Object2Node(this.v_resultSet.getObject("o"));
        }
        //System.out.println(" :"+localNode1+" "+localNode2+" "+localNode3);
        this.v_row = new Triple(localNode1, localNode2, localNode3);
    }

    protected Triple getRow()
    {
        return this.v_row;
    }

    @Override
    public void close()
    {
        if ((!this.v_finished) && (this.v_resultSet != null))
        {
            try
            {
                Connection con = statement.getConnection();
                this.v_resultSet.close();
                this.v_resultSet = null;
                statement.close();
                con.close();                
            } catch (SQLException localSQLException)
            {
                throw new JenaException(localSQLException);
            }
        }
        this.v_finished = true;
    }

    @Override
    protected void finalize()
            throws SQLException
    {
        if ((!this.v_finished) && (this.v_resultSet != null))
        {
            close();
        }
    }
}