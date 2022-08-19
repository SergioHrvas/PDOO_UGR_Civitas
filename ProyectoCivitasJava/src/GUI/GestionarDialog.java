/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import civitas.Jugador;
import civitas.TituloPropiedad;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.DefaultListModel;

/**
 *
 * @author sergio
 */
public class GestionarDialog extends javax.swing.JDialog {

    private int gestionElegida;
    private int propiedadElegida;
    /**
     * Creates new form GestionarDialog
     */
    public GestionarDialog(java.awt.Frame parent) {
        super(parent, true);
        initComponents();
        gestionElegida = -1;
        propiedadElegida = -1;
    }

    public void gestionar(Jugador jugador){
        setGestiones();
        setPropiedades(jugador);
        repaint();
    }
    
public void setGestiones(){      
    DefaultListModel gestiones = new DefaultListModel<>(); // datos para la lista
    ArrayList<String> opciones = new ArrayList<> (Arrays.asList("Vender","Hipotecar","Cancelar hipoteca", "Construir casa","Construir hotel","Terminar"));
    for (String s: opciones){
        gestiones.addElement(s);
    }  //se completan los datos 
    listagestiones.setModel(gestiones); //se le dice a la lista cuáles son esos datos
}   

public void setPropiedades(Jugador jugador){      
    DefaultListModel propiedades = new DefaultListModel<>(); // datos para la lista
    for (TituloPropiedad propiedad: jugador.getPropiedades()){
        propiedades.addElement(propiedad.getNombre());
    }  //se completan los datos 
    listapropiedades.setModel(propiedades); //se le dice a la lista cuáles son esos datos
}   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gestionar = new javax.swing.JLabel();
        gestiones = new javax.swing.JLabel();
        propiedades = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listagestiones = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        listapropiedades = new javax.swing.JList<>();
        realizar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        gestionar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        gestionar.setText("GESTIONAR");

        gestiones.setText("GESTIONES");

        propiedades.setText("PROPIEDADES");

        listagestiones.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listagestiones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listagestionesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(listagestiones);

        listapropiedades.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listapropiedades.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listapropiedadesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(listapropiedades);

        realizar.setText("REALIZAR");
        realizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                realizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(gestiones)
                        .addGap(0, 104, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(realizar))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(propiedades)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(gestionar)
                .addGap(46, 46, 46))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(gestionar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(gestiones)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(propiedades)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(realizar)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void listagestionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listagestionesMouseClicked
        gestionElegida = listagestiones.getSelectedIndex();
        // TODO add your handling code here:
    }//GEN-LAST:event_listagestionesMouseClicked

    private void listapropiedadesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listapropiedadesMouseClicked
        propiedadElegida = listapropiedades.getSelectedIndex();
        // TODO add your handling code here:
    }//GEN-LAST:event_listapropiedadesMouseClicked

    private void realizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_realizarActionPerformed
        propiedadElegida = listapropiedades.getSelectedIndex();
        gestionElegida = listagestiones.getSelectedIndex();
        
        if(propiedadElegida != -1 && gestionElegida != -1 || gestionElegida == 5 ){
            dispose();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_realizarActionPerformed

    int getGestion(){
        return gestionElegida;
    }
    
    int getPropiedad(){
        return propiedadElegida;
    }
    /**
     * @param args the command line arguments
     */
   /* public static void main(String args[]) {
         Set the Nimbus look and feel 
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
         If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GestionarDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GestionarDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GestionarDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GestionarDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

         Create and display the dialog 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                GestionarDialog dialog = new GestionarDialog(new javax.swing.JFrame());
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }*/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel gestionar;
    private javax.swing.JLabel gestiones;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> listagestiones;
    private javax.swing.JList<String> listapropiedades;
    private javax.swing.JLabel propiedades;
    private javax.swing.JButton realizar;
    // End of variables declaration//GEN-END:variables
}
