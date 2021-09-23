
package reporte;

import conexion.Conexion;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class Reporte {
    
    public void consulta(String matricula) throws JRException  {
        //Ruta del reporte
        String reporte = "src/reporte/reporte.jasper";
            
        //HashMap para guardar la consulta
        Map<String,Object> map = new HashMap();
        map.put("parametro", matricula);
        
        //Conexion
        Connection con = Conexion.conectar();
        
        //"Llenamos el reporte"
        JasperPrint prin = JasperFillManager.fillReport(reporte, map, con);

        //Lo guardamos en pdf
        JasperExportManager.exportReportToPdfFile(prin, "consulta.pdf");
        System.out.println("Reporte exportado.");

        //Mostrar pdf
        JasperViewer ver = new JasperViewer(prin, false);
        ver.setVisible(true);
    }
}
