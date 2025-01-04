
package blotterrecordmanagement;

import java.awt.Color;
import java.awt.Frame;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Home extends javax.swing.JFrame {

    Connect db = new Connect();
    Connection connection;
    Statement stmt;
    
    ResultSet rs;
    String query;
    
    ResultSet rs_c;
    String query_c;
    
    ResultSet rs_t;
    String query_t;
    
    int pX;
    int pY;
    int dX;
    int dY;
    
    public Home() {
        initComponents();
        extraComponents();
    }

    private void extraComponents(){
        this.setLocationRelativeTo(null);
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblMinimized = new javax.swing.JLabel();
        lblExit = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblNewRecord = new javax.swing.JLabel();
        lblExistingRecord = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        lblMinimized.setBackground(new java.awt.Color(255, 255, 255));
        lblMinimized.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        lblMinimized.setForeground(new java.awt.Color(255, 255, 255));
        lblMinimized.setText("-");
        lblMinimized.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMinimizedMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblMinimizedMousePressed(evt);
            }
        });

        lblExit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ico/close-white_32.png"))); // NOI18N
        lblExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblExitMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblExitMousePressed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pnp-logo.png"))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Blotter Record Management");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblMinimized, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblExit, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel1)
                .addGap(46, 46, 46)
                .addComponent(jLabel4)
                .addGap(0, 131, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMinimized, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblExit, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 35, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addGap(83, 83, 83))))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 630, -1));

        jPanel2.setBackground(new java.awt.Color(102, 0, 0));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblNewRecord.setBackground(new java.awt.Color(0, 0, 0));
        lblNewRecord.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lblNewRecord.setForeground(new java.awt.Color(255, 255, 255));
        lblNewRecord.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNewRecord.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ico/edit_32.png"))); // NOI18N
        lblNewRecord.setText("New Record");
        lblNewRecord.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        lblNewRecord.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblNewRecord.setOpaque(true);
        lblNewRecord.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblNewRecordMousePressed(evt);
            }
        });
        jPanel2.add(lblNewRecord, new org.netbeans.lib.awtextra.AbsoluteConstraints(141, 51, 140, 39));

        lblExistingRecord.setBackground(new java.awt.Color(0, 0, 0));
        lblExistingRecord.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lblExistingRecord.setForeground(new java.awt.Color(255, 255, 255));
        lblExistingRecord.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblExistingRecord.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ico/employee_32.png"))); // NOI18N
        lblExistingRecord.setText("Existing Record");
        lblExistingRecord.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        lblExistingRecord.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblExistingRecord.setOpaque(true);
        jPanel2.add(lblExistingRecord, new org.netbeans.lib.awtextra.AbsoluteConstraints(371, 51, 140, 39));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 630, 160));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblMinimizedMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMinimizedMouseClicked

    }//GEN-LAST:event_lblMinimizedMouseClicked

    private void lblMinimizedMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMinimizedMousePressed
        this.setState(Frame.ICONIFIED);
    }//GEN-LAST:event_lblMinimizedMousePressed

    private void lblExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblExitMouseClicked

    }//GEN-LAST:event_lblExitMouseClicked

    private void lblExitMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblExitMousePressed
       this.dispose();
       System.exit(0);
    }//GEN-LAST:event_lblExitMousePressed

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        pX = evt.getX();
        pY = evt.getY();
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        if((evt.getX()>0&&pX!=0)&&(evt.getY()>0&&pY!=0)){
            this.setLocation(this.getLocation().x + evt.getX() - pX, this.getLocation().y + evt.getY() - pY );
        }
    }//GEN-LAST:event_formMouseDragged

    private void lblNewRecordMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNewRecordMousePressed
        new GeneralInfo().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblNewRecordMousePressed

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblExistingRecord;
    private javax.swing.JLabel lblExit;
    private javax.swing.JLabel lblMinimized;
    private javax.swing.JLabel lblNewRecord;
    // End of variables declaration//GEN-END:variables
}
