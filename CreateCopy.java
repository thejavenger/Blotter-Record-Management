
package blotterrecordmanagement;


import java.awt.Color;
import java.awt.Frame;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.table.DefaultTableModel;

public class CreateCopy extends javax.swing.JFrame {

    Connect db = new Connect();
    Connection connection;
    Statement stmt;
    
    ResultSet rs;
    String query;
    
    ResultSet rs_ci;
    String query_ci;
    
    ResultSet rs_pi;
    String query_pi;
    
    ResultSet rs_ofcr;
    String query_ofcr;
    
    ResultSet rs_x;
    String query_x;
    
    CurrentCalendar curdate = new CurrentCalendar();
    NewCalendar dater;
    
    String entryno;
    String dateentry;
    String timeentry;
    String facts;
    
    
    int piid;
    String involvement;
    String lastname;
    String firstname;
    String mi;
    String suffix;
    
    String involved;
    
    int age;
    String civilstatus;
    String occupation;
    String nativeof;
    String address;
    
    int offid;
    String officer;
    String duty;
    
    int pX;
    int pY;
    int dX;
    int dY;
    int d2X;
    int d2Y;
    
    int curRow = 0;
    boolean emptyrecord = true;
    
    DefaultListModel dmodel = new DefaultListModel();
    DefaultListModel omodel = new DefaultListModel();
    DefaultListModel imodel = new DefaultListModel();
    DefaultListModel pmodel = new DefaultListModel();
    DefaultTableModel model;
    
    ArrayList pid = new ArrayList();
    ArrayList oid = new ArrayList();
    
    JSeparator hiddens[];
    
    public CreateCopy() {
        initComponents();
    }
    
    public CreateCopy(String eno) {
        initComponents();
        extraComponents();
        entryno = eno;
        ReadyConnection();
        GetCase();
        GetOfficers();
        GetPersonInvolved();
    }

    private void ReadyConnection(){
        try {
            connection = db.doConnection(connection, "", "");
            stmt = connection.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE );
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Cannot open connection!", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void GetCase(){
        
        try {
            query_ci = "SELECT * FROM CaseInfo WHERE EntryNo = '" +entryno + "'";
            rs_ci = stmt.executeQuery(query_ci);
            
            if(rs_ci.next()){
                dateentry = rs_ci.getString("DateEntry");
                timeentry = rs_ci.getString("TimeEntry");
                
                facts = rs_ci.getString("Facts");
                
//                txtEntryNo.setText(entryno);
//                txtDateEntry.setText(dateentry);
//                txtTimeEntry.setText(timeentry);
                
                lblDate.setText(curdate.getFullDate());
                
                txtFacts.setText(facts);
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
                
        
    }
    
    private void GetOfficers(){
        try {
            
//            lstDuty.setModel(dmodel);
//            lstOfficer.setModel(omodel);
            
            query_ofcr = "SELECT * FROM Officers WHERE EntryNo = '" +entryno + "'";
            rs_ofcr = stmt.executeQuery(query_ofcr);
            
            while(rs_ofcr.next()){
                offid = rs_ofcr.getInt("ID");
                
                officer = rs_ofcr.getString("Name");
                duty = rs_ofcr.getString("Duty");
                
                oid.add(offid);
                
                dmodel.addElement(duty);
                omodel.addElement(officer);
                
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
    
    private void GetPersonInvolved(){
        
        try {
            
//            lstInvolvement.setModel(imodel);
//            lstPersonInvolved.setModel(pmodel);
            
            query_pi = "SELECT * FROM PersonInvolved WHERE EntryNo = '" +entryno + "'";
            rs_pi = stmt.executeQuery(query_pi);
            
            while(rs_pi.next()){
                
                piid = rs_pi.getInt("ID");
                
                involvement = rs_pi.getString("Involvement");
                lastname = rs_pi.getString("Lastname");
                firstname = rs_pi.getString("Firstname");
                mi = rs_pi.getString("MI");
                suffix = rs_pi.getString("Suffix");

                if(suffix.equals("")&&mi.equals("")){
                    involved = firstname + " " + lastname;
                }else if(suffix.equals("")){
                    involved = firstname + " " + mi + ". " + lastname;
                }else if(mi.equals("")){
                    involved = firstname + " " + lastname + " " + suffix;
                }else{
                    involved = firstname + " " + mi + ". " + lastname + " " + suffix;
                }
                
                pid.add(piid);
                
                imodel.addElement(involvement);
                pmodel.addElement(involved);
                
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
        
    }
    
//    private void ExtraHide(){
//        hiddens = new JSeparator[]{sEntryNo, sDate, sTime};
//        
//        for (JSeparator hidden : hiddens) {
//            hidden.setVisible(false);
//        }
//    }
    
    private void extraComponents(){
        this.setLocationRelativeTo(null);
        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblMinimized = new javax.swing.JLabel();
        lblExit = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtComplainant = new javax.swing.JTextArea();
        lblDate = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtSuspect1 = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtExhibit = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtWitness = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtFacts = new javax.swing.JTextArea();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblNext = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        jPanel1.add(lblMinimized, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 0, 20, 20));

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
        jPanel1.add(lblExit, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 0, 20, 20));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ico/edit_48.png"))); // NOI18N
        jLabel4.setText("Case Facts");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 30, -1, 33));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 870, 80));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 8)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("<html>  <center>Republic of the Philippines<br> Department of the Interior and Local Government<br> National Police Commission<br> PHILIPPINE National Police<br> QUEZON CITY POLICE DISTRICT<br> GALAS POLICE STATION (PS-11)<br> Unang Hakbang Street corner Luzon Avenue<br> Galas, Quezon City<br>  </center></html>");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, -1, -1));

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 8)); // NOI18N
        jLabel6.setText("PS-11/SIIB");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 8)); // NOI18N
        jLabel7.setText("<html>The Hon. City Prosecutor<br> Quezon City, Metro Manila </html>");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 8)); // NOI18N
        jLabel8.setText("Sir,");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 60, 20));

        txtComplainant.setColumns(20);
        txtComplainant.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        txtComplainant.setLineWrap(true);
        txtComplainant.setRows(5);
        txtComplainant.setWrapStyleWord(true);
        txtComplainant.setSelectedTextColor(new java.awt.Color(255, 255, 255));
        txtComplainant.setSelectionColor(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(txtComplainant);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 30, 250, 110));

        lblDate.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        lblDate.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblDate.setText("jLabel9");
        jPanel2.add(lblDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, 120, -1));

        txtSuspect1.setColumns(20);
        txtSuspect1.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        txtSuspect1.setLineWrap(true);
        txtSuspect1.setRows(5);
        txtSuspect1.setWrapStyleWord(true);
        txtSuspect1.setSelectedTextColor(new java.awt.Color(255, 255, 255));
        txtSuspect1.setSelectionColor(new java.awt.Color(0, 0, 0));
        jScrollPane2.setViewportView(txtSuspect1);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, 250, 110));

        txtExhibit.setColumns(20);
        txtExhibit.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        txtExhibit.setLineWrap(true);
        txtExhibit.setRows(5);
        txtExhibit.setWrapStyleWord(true);
        txtExhibit.setSelectedTextColor(new java.awt.Color(255, 255, 255));
        txtExhibit.setSelectionColor(new java.awt.Color(0, 0, 0));
        jScrollPane3.setViewportView(txtExhibit);

        jPanel2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 290, 250, 110));

        txtWitness.setColumns(20);
        txtWitness.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        txtWitness.setLineWrap(true);
        txtWitness.setRows(5);
        txtWitness.setWrapStyleWord(true);
        txtWitness.setSelectedTextColor(new java.awt.Color(255, 255, 255));
        txtWitness.setSelectionColor(new java.awt.Color(0, 0, 0));
        jScrollPane4.setViewportView(txtWitness);

        jPanel2.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 160, 250, 110));

        txtFacts.setColumns(20);
        txtFacts.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        txtFacts.setLineWrap(true);
        txtFacts.setRows(5);
        txtFacts.setWrapStyleWord(true);
        txtFacts.setSelectedTextColor(new java.awt.Color(255, 255, 255));
        txtFacts.setSelectionColor(new java.awt.Color(0, 0, 0));
        jScrollPane5.setViewportView(txtFacts);

        jPanel2.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 30, 250, 120));

        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        jTextArea2.setLineWrap(true);
        jTextArea2.setRows(5);
        jTextArea2.setWrapStyleWord(true);
        jTextArea2.setSelectedTextColor(new java.awt.Color(255, 255, 255));
        jTextArea2.setSelectionColor(new java.awt.Color(0, 0, 0));
        jScrollPane6.setViewportView(jTextArea2);

        jPanel2.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 250, 110));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel2.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 20, 10, 370));

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel2.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 20, 20, 380));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel1.setText("this Station.");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 190, -1, -1));

        jTextField1.setSelectedTextColor(new java.awt.Color(255, 255, 255));
        jTextField1.setSelectionColor(new java.awt.Color(0, 0, 0));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 180, 130, -1));

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel2.setText("This case will be presented by ");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 160, -1, -1));

        jLabel3.setText("Very truly yours,");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 240, -1, -1));

        jTextField2.setSelectedTextColor(new java.awt.Color(255, 255, 255));
        jTextField2.setSelectionColor(new java.awt.Color(0, 0, 0));
        jPanel2.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 260, 130, -1));

        jTextField3.setSelectedTextColor(new java.awt.Color(255, 255, 255));
        jTextField3.setSelectionColor(new java.awt.Color(0, 0, 0));
        jPanel2.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 290, 130, -1));

        jLabel10.setText("Complainant");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 10, -1, -1));

        jLabel9.setText("Witness");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 140, -1, -1));

        jLabel11.setText("Exhibit");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 270, -1, -1));

        jLabel12.setText("Facts of the Case:");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, -1, -1));

        lblNext.setBackground(new java.awt.Color(0, 0, 0));
        lblNext.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lblNext.setForeground(new java.awt.Color(255, 255, 255));
        lblNext.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ico/forward_32.png"))); // NOI18N
        lblNext.setText("Next");
        lblNext.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        lblNext.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblNext.setEnabled(false);
        lblNext.setOpaque(true);
        lblNext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblNextMousePressed(evt);
            }
        });
        jPanel2.add(lblNext, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 370, 120, 30));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 870, 420));

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
        //System.exit(0);
    }//GEN-LAST:event_lblExitMousePressed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void lblNextMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNextMousePressed
        if(lblNext.isEnabled()){
            //new CaseFacts(entryno, dateentry, dateformatted, timeentry).setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_lblNextMousePressed

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
            java.util.logging.Logger.getLogger(CreateCopy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CreateCopy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CreateCopy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CreateCopy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CreateCopy().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblExit;
    private javax.swing.JLabel lblMinimized;
    private javax.swing.JLabel lblNext;
    private javax.swing.JTextArea txtComplainant;
    private javax.swing.JTextArea txtExhibit;
    private javax.swing.JTextArea txtFacts;
    private javax.swing.JTextArea txtSuspect1;
    private javax.swing.JTextArea txtWitness;
    // End of variables declaration//GEN-END:variables
}
