/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.triplestore.virtuoso;

import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.graph.impl.TransactionHandlerBase;
import com.hp.hpl.jena.shared.JenaException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 *
 * @author javier.solis.g
 */
public class SWBVirtTransactionHandler extends TransactionHandlerBase
{
    private static Logger log= SWBUtils.getLogger(SWBVirtTransactionHandler.class);

    private ConcurrentHashMap<Long,SWBTransaction> conmap;
    
    private SWBVirtGraph graph = null;
    private Boolean m_transactionsSupported = null;

    public SWBVirtTransactionHandler(SWBVirtGraph _graph)
    {
        super();
        this.graph = _graph;
        conmap=new ConcurrentHashMap();
    }

    @Override
    public boolean transactionsSupported()
    {
//        if (m_transactionsSupported != null)
//        {
//            return (m_transactionsSupported.booleanValue());
//        }
//
//        try
//        {
//            Connection c = getConnection();
//            if (c != null)
//            {
//                m_transactionsSupported = c.getMetaData().supportsMultipleTransactions();
//                c.close();
//                return (m_transactionsSupported.booleanValue());
//            }
//        } catch (Exception e)
//        {
//            throw new JenaException(e);
//        }
//        return false;
        return true;
    }

    @Override
    public void begin()
    {
        if (transactionsSupported())
        {
            try
            {
                startTransaction();
                Connection c = getConnection();
                if (c.getTransactionIsolation() != Connection.TRANSACTION_READ_COMMITTED)
                {
                    c.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                }
                if (c.getAutoCommit())
                {
                    c.setAutoCommit(false);
                }
                c.close();
            } catch (SQLException e)
            {
                throw new JenaException("Transaction begin failed: ", e);
            }
        } else
        {
            notSupported("begin transaction");
        }
    }

    @Override
    public void abort()
    {
        if (transactionsSupported())
        {
            try
            {
                Connection c = getConnection();
                c.rollback();
                c.commit();
                c.setAutoCommit(true);
                c.close();
                closeTransaction();
            } catch (SQLException e)
            {
                throw new JenaException("Transaction rollback failed: ", e);
            }
        } else
        {
            notSupported("abort transaction");
        }
    }

    @Override
    public void commit()
    {
        if (transactionsSupported())
        {
            try
            {
                endTransaction();
                Connection c = getConnection();
                c.commit();
                c.setAutoCommit(true);
                c.close();
                closeTransaction();
            } catch (SQLException e)
            {
                throw new JenaException("Transaction commit failed: ", e);
            }
        } else
        {
            notSupported("commit transaction");
        }
    }

    private void notSupported(String opName)
    {
        throw new UnsupportedOperationException(opName);
    }

    public Connection getConnection()
    {
        //System.out.print("getConnection");
        Long id=Thread.currentThread().getId();
        Connection con=null;
        SWBTransaction tran=conmap.get(id);
        //System.out.println("trans.getConnection():"+id+" "+con);
        if(tran==null)
        {
            con=SWBUtils.DB.getDefaultConnection();
            //System.out.println("trans2.getConnection():"+id+" "+con);
            //System.out.print(" new:"+id);
        }else
        {
            con=tran.getConnection();
            //System.out.print(" existing:"+id);
        }
        //System.out.println(" "+con);
        return con;
    }
    
    public boolean batchAddTransaction(Triple triple)
    {
        Long id=Thread.currentThread().getId();
        SWBTransaction tran=conmap.get(id);
        if(tran!=null)
        {
            if(tran.getMode()==SWBTransaction.MODE_DELETE)
            {
                if(!tran.getList().isEmpty())
                {
                    graph.delete(tran.getList().iterator(), null);
                    tran.getList().clear();;
                }
            }
            if(tran.getMode()!=SWBTransaction.MODE_ADD)tran.setMode(SWBTransaction.MODE_ADD);
            tran.getList().add(triple);
            if(tran.getList().size()==SWBVirtGraph.BATCH_SIZE)
            {
                graph.add(tran.getList().iterator(), null);
                tran.getList().clear();;
            }
            return true;
        }else
        {
            return false;
        }
    }
    
    public boolean batchDeleteTransaction(Triple triple)
    {
        Long id=Thread.currentThread().getId();
        SWBTransaction tran=conmap.get(id);
        if(tran!=null)
        {
            if(tran.getMode()==SWBTransaction.MODE_ADD)
            {
                if(!tran.getList().isEmpty())
                {
                    graph.add(tran.getList().iterator(), null);
                    tran.getList().clear();;
                }
            }
            if(tran.getMode()!=SWBTransaction.MODE_DELETE)tran.setMode(SWBTransaction.MODE_DELETE);
            tran.getList().add(triple);
            if(tran.getList().size()==SWBVirtGraph.BATCH_SIZE)
            {
                graph.delete(tran.getList().iterator(), null);
                tran.getList().clear();;
            }
            return true;
        }else
        {
            return false;
        }
    }

    public void startTransaction()
    {
        Long id=Thread.currentThread().getId();
        SWBTransaction tran=conmap.get(id);
        //System.out.println("startTransaction:"+id+" "+tran);
        if(tran==null)
        {
            Connection con=new SWBPoolConection(SWBUtils.DB.getDefaultConnection(),false);
            tran=new SWBTransaction(id, con);
            conmap.put(id, tran);
        }
    }

    public void endTransaction()
    {
        Long id=Thread.currentThread().getId();
        SWBTransaction tran=conmap.get(id);
        //System.out.println("endTransaction:"+id+" "+tran);
        try
        {
            if(tran!=null)
            {
                if(!tran.getList().isEmpty())
                {
                    if(tran.getMode()==SWBTransaction.MODE_ADD)
                    {
                        graph.add(tran.getList().iterator(), null);
                    }else if(tran.getMode()==SWBTransaction.MODE_DELETE)
                    {
                        graph.delete(tran.getList().iterator(), null);
                    }
                    tran.getList().clear();;
                }
            }
        }catch(Exception e)
        {
            log.error(e);
        }        
    }
    
    public void closeTransaction()
    {
        Long id=Thread.currentThread().getId();
        SWBTransaction tran=conmap.remove(id);
        //System.out.println("closeTransaction:"+id+" "+tran);
        try
        {
            if(tran!=null)
            {
                if(tran.getConnection() instanceof SWBPoolConection)((SWBPoolConection)tran.getConnection()).sclose();
            }
        }catch(Exception e)
        {
            log.error(e);
        }
    }
    
    public class SWBTransaction
    {
        public final static int MODE_UNDEFINED=0; 
        public final static int MODE_ADD=1; 
        public final static int MODE_DELETE=2; 
        
        private Long id;
        private Connection connection;
        private ArrayList<Triple> list=new ArrayList();
        private int mode;

        public SWBTransaction(Long id, Connection connection)
        {
            this.id = id;
            this.connection = connection;
        }

        public Connection getConnection()
        {
            return connection;
        }

        public Long getId()
        {
            return id;
        }

        public ArrayList<Triple> getList()
        {
            return list;
        }

        public int getMode()
        {
            return mode;
        }

        public void setMode(int mode)
        {
            this.mode = mode;
        }
        
        
        
    }
    
}
