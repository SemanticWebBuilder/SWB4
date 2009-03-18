
package org.semanticwb.portal.admin.resources.reports.jrresources.data;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.semanticwb.portal.admin.resources.reports.beans.*;
import org.semanticwb.portal.admin.resources.reports.datadetail.SessionDataDetail;
import org.semanticwb.portal.admin.resources.reports.datadetail.SWBDataDetail;
import org.semanticwb.portal.admin.resources.reports.jrresources.JRDataSourceable;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Administrador
 */
public class JRSessionDataDetail implements JRDataSourceable{
    private SWBDataDetail dataDetail;
    
    public JRSessionDataDetail(WBAFilterReportBean filterReportBean){
        dataDetail = new SessionDataDetail(filterReportBean);
    }
    
    public JRDataSource orderJRReport() throws IncompleteFilterException{
        List dataList = dataDetail.execute();
        JRBeanCollectionDataSource dataSource = null;
        
        if(!dataList.isEmpty())
            dataSource = new JRBeanCollectionDataSource(dataList);
        return dataSource;
    }
    
    public void prepareJRReport() throws JRException{
    }
    
    public void exportReport(HttpServletResponse response) throws java.io.IOException, JRException{
    }
}
