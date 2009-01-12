
package org.semanticwb.portal.admin.resources.reports.jrresources.data;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.semanticwb.portal.admin.resources.reports.beans.*;
import org.semanticwb.portal.admin.resources.reports.datadetail.LoggedUniqueDataDetail;
import org.semanticwb.portal.admin.resources.reports.datadetail.SWBDataDetail;
import org.semanticwb.portal.admin.resources.reports.jrresources.JRDataSourceable;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Administrador
 */
public class JRLoggedUniqueDataDetail implements JRDataSourceable{
    private SWBDataDetail dataDetail;
    
    public JRLoggedUniqueDataDetail(WBAFilterReportBean filterReportBean){
        dataDetail = new LoggedUniqueDataDetail(filterReportBean);
    }
    
    public JRDataSource orderJRReport() throws IncompleteFilterException{
        List dataList = dataDetail.execute();
        JRDataSource dataSource = null;
        
        if(!dataList.isEmpty())
            dataSource = new JRBeanCollectionDataSource(dataList);
        return dataSource;
    }
    
    public void prepareJRReport() throws JRException{
    }
    
    public void exportReport(HttpServletResponse response) throws java.io.IOException, JRException{
    }
}
