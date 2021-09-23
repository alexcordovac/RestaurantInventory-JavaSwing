/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import formularios.VistaConsultas;
import menu.MenuItem;
import formularios.VistaHome;
import formularios.VistaInventarioAltas;
import formularios.VistaInventarioBajas;
import formularios.VistaInventarioExistencias;
import formularios.VistaInventarioModificar;
import formularios.VistaNominaArea;
import formularios.VistaNominaPersonal;
import formularios.VistaRHAltas;
import formularios.VistaRHBajas;
import formularios.VistaRHModificar;
import formularios.VistaReportesEmpleado;
import formularios.VistaReportesInventario;
import formularios.VistaReportesRH;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Admin
 */
public class Principal extends javax.swing.JFrame implements MouseListener {

    //Los menus pricipales y anidados los declaro global para poder acceder a ellos
    MenuItem menuInventario;
    MenuItem menuRH;
    MenuItem menuNomina;
    MenuItem menuReportes;
    MenuItem menuConsultas;
    MenuItem menuHome;
    MenuItem reportesRH;
    MenuItem reportesInventario;
    MenuItem reportesEmpleado;
    MenuItem nominaArea;
    MenuItem nominaPersonal;
    MenuItem rhModificaciones;
    MenuItem rhBajas;
    MenuItem rhAltas;
    MenuItem inventarioModificaciones;
    MenuItem inventarioBajas;
    MenuItem inventarioAltas;
    MenuItem inventarioExistencias;

    //Declaro un hash para poder guardar el estado del JPanel y su respectivo menu que lo invoca
    HashMap<MenuItem, JPanel> menusEnMemoria;

    //Proporciona cuál menu fue clickeado al método mouseclicked
    MenuItem clicked = null;

    /**
     * Creates new form VistaHome
     */
    public Principal() {
        initComponents();
        initMenuItems();
        initHashMap();
        
        this.setLocationRelativeTo(null);
        panelCuerpo.add(new VistaHome());
        panelCuerpo.repaint();
        panelCuerpo.revalidate();
    }

    private void initMenuItems() {
        //Iconos
        ImageIcon icoInventario = new ImageIcon(getClass().getResource("/swing/images/IcoInventario.png"));
        ImageIcon icoConsultas = new ImageIcon(getClass().getResource("/swing/images/IcoConsultas.png"));
        ImageIcon icoNomina = new ImageIcon(getClass().getResource("/swing/images/IcoNomina.png"));
        ImageIcon icoReportes = new ImageIcon(getClass().getResource("/swing/images/IcoReportes.png"));
        //ImageIcon icoMantenimiento = new ImageIcon(getClass().getResource("/swing/images/IcoMantenimiento.png"));
        ImageIcon icoAgregar = new ImageIcon(getClass().getResource("/swing/images/IcoAgregar.png"));
        ImageIcon icoRH = new ImageIcon(getClass().getResource("/swing/images/IcoRH.png"));
        ImageIcon icoHome = new ImageIcon(getClass().getResource("/swing/images/IcoHome.png"));
        ImageIcon icoModificar = new ImageIcon(getClass().getResource("/swing/images/IcoModificar.png"));
        ImageIcon icoEliminar = new ImageIcon(getClass().getResource("/swing/images/IcoEliminar.png"));
        ImageIcon icoDisponible = new ImageIcon(getClass().getResource("/swing/images/IcoDisponible.png"));
        ImageIcon icoPersonal = new ImageIcon(getClass().getResource("/swing/images/IcoPersonal.png"));
        ImageIcon icoZona = new ImageIcon(getClass().getResource("/swing/images/IcoZona.png"));

        //Submenu inventario
        inventarioExistencias = new MenuItem(icoDisponible, "Existencias", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelCuerpo.removeAll();
                panelCuerpo.add(new VistaInventarioExistencias());
                panelCuerpo.repaint();
                panelCuerpo.revalidate();
            }
        });
        inventarioAltas = new MenuItem(icoAgregar, "Altas", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelCuerpo.removeAll();
                panelCuerpo.add(new VistaInventarioAltas());
                panelCuerpo.repaint();
                panelCuerpo.revalidate();
            }
        });
        inventarioBajas = new MenuItem(icoEliminar, "Bajas", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelCuerpo.removeAll();
                panelCuerpo.add(new VistaInventarioBajas());
                panelCuerpo.repaint();
                panelCuerpo.revalidate();
            }
        });
        inventarioModificaciones = new MenuItem(icoModificar, "Modificar", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelCuerpo.removeAll();
                panelCuerpo.add(new VistaInventarioModificar());
                panelCuerpo.repaint();
                panelCuerpo.revalidate();
            }
        });

        //submenu recursos humanos
        rhAltas = new MenuItem(icoAgregar, "Altas", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelCuerpo.removeAll();
                panelCuerpo.add(new VistaRHAltas());
                panelCuerpo.repaint();
                panelCuerpo.revalidate();
            }
        });
        rhBajas = new MenuItem(icoEliminar, "Bajas", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelCuerpo.removeAll();
                panelCuerpo.add(new VistaRHBajas());
                panelCuerpo.repaint();
                panelCuerpo.revalidate();
            }
        });
        rhModificaciones = new MenuItem(icoModificar, "Modificar", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelCuerpo.removeAll();
                panelCuerpo.add(new VistaRHModificar());
                panelCuerpo.repaint();
                panelCuerpo.revalidate();
            }
        });

        //submenu nomina
        nominaPersonal = new MenuItem(icoPersonal, "Personal", null);

        nominaArea = new MenuItem(icoZona, "Area", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelCuerpo.removeAll();
                panelCuerpo.add(new VistaNominaArea());
                panelCuerpo.repaint();
                panelCuerpo.revalidate();
            }
        });

        //submenus reportes
        reportesEmpleado = new MenuItem(icoPersonal, "Empleados", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelCuerpo.removeAll();
                panelCuerpo.add(new VistaReportesEmpleado());
                panelCuerpo.repaint();
                panelCuerpo.revalidate();
            }
        });

        reportesInventario = new MenuItem(icoInventario, "Inventario", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelCuerpo.removeAll();
                panelCuerpo.add(new VistaReportesInventario());
                panelCuerpo.repaint();
                panelCuerpo.revalidate();
            }
        });

        reportesRH = new MenuItem(icoRH, "Recursos H.", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelCuerpo.removeAll();
                panelCuerpo.add(new VistaReportesRH());
                panelCuerpo.repaint();
                panelCuerpo.revalidate();
            }
        });

        //Menus principales (modulos)
        menuHome = new MenuItem(icoHome, "Home", null);

        menuConsultas = new MenuItem(icoConsultas, "Consultas", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelCuerpo.removeAll();
                panelCuerpo.add(new VistaConsultas());
                panelCuerpo.repaint();
                panelCuerpo.revalidate();
            }
        });
        
        
        //Asigamos los subemnus a los menus principales
        menuInventario = new MenuItem(icoInventario, "Inventario", null, inventarioExistencias, inventarioAltas, inventarioBajas, inventarioModificaciones);
        menuRH = new MenuItem(icoRH, "Recursos Humanos", null, rhAltas, rhBajas, rhModificaciones);
        menuNomina = new MenuItem(icoNomina, "Nomina", null, nominaPersonal, nominaArea);
        menuReportes = new MenuItem(icoReportes, "Reportes", null, reportesEmpleado, reportesInventario, reportesRH);

        //Pintamos los menus en el JPanel izquierdo, para la nevegación del 
        pintarMenuItems(menuHome, menuInventario, menuRH, menuNomina, menuReportes, menuConsultas);
    }

    private void initHashMap() {
        menusEnMemoria = new HashMap();
        
        //Home
        menusEnMemoria.put(menuHome, new VistaHome());
        
        //Inventario
        menusEnMemoria.put(inventarioExistencias, new VistaInventarioExistencias());
        menusEnMemoria.put(inventarioAltas, new VistaInventarioAltas());
        menusEnMemoria.put(inventarioBajas, new VistaInventarioBajas());
        menusEnMemoria.put(inventarioModificaciones, new VistaInventarioModificar());
        
        //Recursos humanos
        menusEnMemoria.put(rhAltas, new VistaRHAltas());
        menusEnMemoria.put(rhBajas, new VistaRHBajas());
        menusEnMemoria.put(rhModificaciones, new VistaRHModificar());
        
        //Nómina
        menusEnMemoria.put(nominaPersonal, new VistaNominaPersonal());
        menusEnMemoria.put(nominaArea, new VistaNominaArea());
        
        //Reportes
        menusEnMemoria.put(reportesEmpleado, new VistaReportesEmpleado());
        menusEnMemoria.put(reportesInventario, new VistaReportesInventario());
        menusEnMemoria.put(reportesRH, new VistaReportesRH());
        
        //Consultas
        menusEnMemoria.put(menuConsultas, new VistaConsultas());
    }

    private void pintarMenuItems(MenuItem... menu) {
        for (int i = 0; i < menu.length; i++) {
            menus.add(menu[i]);
            menu[i].addMouseListener(this);
            ArrayList<MenuItem> subMenu = menu[i].getSubMenu();
            for (MenuItem m : subMenu) {
                pintarMenuItems(m);
            }
        }
        menus.revalidate();
    }
    
    private void controlarVista(MouseEvent e){
        MenuItem tmp = (MenuItem) e.getSource();
        JPanel hashFound = this.menusEnMemoria.get(tmp);

        if (tmp != this.clicked) {

            if (this.clicked != null) {
                this.clicked.resetColor();
            }

            tmp.setColor();
            this.clicked = tmp;

            //Si el menu ya está registrado en el hashmap con su JPanel lo traemos y lo pintamos
            if (hashFound != null) {
                this.panelCuerpo.removeAll();
                this.panelCuerpo.add(hashFound);
                this.panelCuerpo.repaint();
                this.panelCuerpo.revalidate();
            }
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        controlarVista(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        barraLateral = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        menus = new javax.swing.JPanel();
        barraSuperior = new javax.swing.JPanel();
        btnSalir = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        panelCuerpo = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setLocationByPlatform(true);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        barraLateral.setBackground(new java.awt.Color(23, 35, 51));

        jScrollPane1.setBackground(new java.awt.Color(23, 35, 51));
        jScrollPane1.setBorder(null);

        menus.setBackground(new java.awt.Color(23, 35, 51));
        menus.setLayout(new javax.swing.BoxLayout(menus, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane1.setViewportView(menus);

        javax.swing.GroupLayout barraLateralLayout = new javax.swing.GroupLayout(barraLateral);
        barraLateral.setLayout(barraLateralLayout);
        barraLateralLayout.setHorizontalGroup(
            barraLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
        );
        barraLateralLayout.setVerticalGroup(
            barraLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, barraLateralLayout.createSequentialGroup()
                .addGap(0, 50, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(barraLateral, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 590));

        barraSuperior.setBackground(new java.awt.Color(71, 120, 197));
        barraSuperior.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                barraSuperiorMouseDragged(evt);
            }
        });
        barraSuperior.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                barraSuperiorMousePressed(evt);
            }
        });

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/swing/images/IcoSalirMin.png"))); // NOI18N
        btnSalir.setBorder(null);
        btnSalir.setBorderPainted(false);
        btnSalir.setContentAreaFilled(false);
        btnSalir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalir.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/swing/images/IcoSalirMax.png"))); // NOI18N
        btnSalir.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/swing/images/IcoSalirMax.png"))); // NOI18N
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Sistema de adminitración central - Restaurantes de comida mexicana");

        javax.swing.GroupLayout barraSuperiorLayout = new javax.swing.GroupLayout(barraSuperior);
        barraSuperior.setLayout(barraSuperiorLayout);
        barraSuperiorLayout.setHorizontalGroup(
            barraSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, barraSuperiorLayout.createSequentialGroup()
                .addContainerGap(325, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(109, 109, 109)
                .addComponent(btnSalir)
                .addContainerGap())
        );
        barraSuperiorLayout.setVerticalGroup(
            barraSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, barraSuperiorLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(barraSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSalir)
                    .addComponent(jLabel1))
                .addGap(14, 14, 14))
        );

        getContentPane().add(barraSuperior, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1070, 50));

        panelCuerpo.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                panelCuerpoPropertyChange(evt);
            }
        });
        panelCuerpo.setLayout(new java.awt.BorderLayout());
        getContentPane().add(panelCuerpo, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 60, 860, 520));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    int xx, xy;
    private void barraSuperiorMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_barraSuperiorMousePressed
        // TODO add your handling code here:
        //drag this pane
        xx = evt.getX();
        xy = evt.getY();
    }//GEN-LAST:event_barraSuperiorMousePressed

    private void barraSuperiorMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_barraSuperiorMouseDragged
        // TODO add your handling code here:
        //source to drag
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xx, y - xy);
    }//GEN-LAST:event_barraSuperiorMouseDragged

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnSalirActionPerformed

    private void panelCuerpoPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_panelCuerpoPropertyChange
        System.out.println("event: " + evt);
    }//GEN-LAST:event_panelCuerpoPropertyChange
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel barraLateral;
    private javax.swing.JPanel barraSuperior;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel menus;
    private javax.swing.JPanel panelCuerpo;
    // End of variables declaration//GEN-END:variables
}
