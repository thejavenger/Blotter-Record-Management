
package blotterrecordmanagement;

import java.awt.Color;
import java.awt.Frame;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.table.DefaultTableModel;

public class ExistingRecords extends javax.swing.JFrame {

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
    
    String entryno;
    String dateentry;
    String timeentry;
    
    String complainants = "";
    String suspects = "";
    String witnesses = "";
    String victims = "";
    
    String complainantsf = "";
    String suspectsf = "";
    String witnessesf = "";
    String victimsf = "";
    
    String arrestingofficers = "";
    String investigators = "";
    
    String involvement;
    String lastname;
    String firstname;
    String mi;
    String suffix;
    
    String involved;
    
    String officer;
    String duty;
    
    boolean haverecords = false;
            
    int pX;
    int pY;
    int dX;
    int dY;
    
    int curRow = 0;
    boolean emptyrecord = true;
    
    String orderquery = "";
    
    DefaultTableModel model;
    
    JSeparator hiddens[];
    
    public ExistingRecords() {
        initComponents();
        extraComponents();
        ReadyConnection();
        EntryDB();
    }
    
    private void ReadyConnection(){
        try {
            connection = db.doConnection(connection, "", "");
            stmt = connection.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE );
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Cannot open connection!", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void EntryDB(){
        try {
            
            
            
            query_ci = "SELECT * FROM CaseInfo";
            
            if(cSortBy.getSelectedIndex()!=0){
                query_ci += orderquery;
            }
            
            rs_ci = stmt.executeQuery(query_ci);
                    
                    
            ShowItemData();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
    
    private void ClearItemTable(){
        lblShowInfo.setEnabled(false);
        
        while(tblEntries.getRowCount()>0){
            model.removeRow(tblEntries.getRowCount() - 1);
        }
    }
    
    private void ShowItemData(){
        try {
            
            model = (DefaultTableModel) tblEntries.getModel();
            
            ClearItemTable();
            
            int counter = 0;
            
            while(rs_ci.next()){
                
                counter++;
                
                emptyrecord = false;
                
                haverecords = false;
                
                curRow = rs_ci.getRow();
                
                entryno = rs_ci.getString("EntryNo");
                dateentry = rs_ci.getString("DateEntry");
                
                complainants = rs_ci.getString("Complainants");
                victims = rs_ci.getString("Victims");
                suspects = rs_ci.getString("Suspects");
                witnesses = rs_ci.getString("Witnesses");
                
                complainantsf = rs_ci.getString("ComplainantsFormatted");
                victimsf = rs_ci.getString("VictimsFormatted");
                suspectsf = rs_ci.getString("SuspectsFormatted");
                witnessesf = rs_ci.getString("WitnessesFormatted");
    
                arrestingofficers = rs_ci.getString("ArrestingOfficers");
                investigators = rs_ci.getString("Investigators");
                
                model.insertRow(model.getRowCount(), new Object[]{entryno, dateentry,  suspects, complainants, witnesses, arrestingofficers});
                
//                query_ci = "SELECT * FROM CaseInfo";
//                rs_ci = stmt.executeQuery(query_ci);
//                
//                rs_ci.absolute(curRow);
            
            }
            
            lblRecordCount.setText(tblEntries.getRowCount() + " result returned.");
            
            if(emptyrecord)
                JOptionPane.showMessageDialog(rootPane, "Retrieved 0 records!", "Blank records!", JOptionPane.ERROR_MESSAGE);
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
    
    private void SearchCase(){
        try {
            
            String searchquery = "";
            String[] searchtable = new String[]{"EntryNo", "DateEntry", "Complainants", "Suspects", "Witnesses", "ArrestingOfficers"}; 
            
            for(int i=0;i<searchtable.length;i++){
                if(i>0){
                    searchquery += " OR";
                }else{
                    searchquery += " WHERE";
                }
                searchquery += " LOWER(" + searchtable[i] + ") LIKE '%" + txtSearch.getText().toLowerCase() + "%'";
            }
            
            query_ci = "SELECT * FROM CaseInfo" + searchquery;
            
            if(cSortBy.getSelectedIndex()!=0){
                query_ci += orderquery;
            }
            
            rs_ci = stmt.executeQuery(query_ci);
            
            
            
            ShowItemData();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
    
    private void GetInfo(){
        entryno = String.valueOf(model.getValueAt(tblEntries.getSelectedRow(), 0));
        dateentry = String.valueOf(model.getValueAt(tblEntries.getSelectedRow(), 1));
        suspects = String.valueOf(model.getValueAt(tblEntries.getSelectedRow(), 2));
        complainants = String.valueOf(model.getValueAt(tblEntries.getSelectedRow(), 3));
        witnesses = String.valueOf(model.getValueAt(tblEntries.getSelectedRow(), 4));
        arrestingofficers = String.valueOf(model.getValueAt(tblEntries.getSelectedRow(), 5));
        
        lblShowInfo.setEnabled(true);
//        lblDeleteInfo.setEnabled(true);
    }
    
    private void CloseConnections(){
        try {
            
            //ResultSet list rs, rs_emp, rs_itm, rs_sales, rs_pr, rs_c,
            
            if(!stmt.isClosed()){
                stmt.close();
            }
            
            if(null!=rs&&!rs.isClosed()){
                rs.close();
            }
            
            if(null!=rs_ci&&!rs_ci.isClosed()){
                rs_ci.close();
            }
            
            if(null!=rs_pi&&!rs_pi.isClosed()){
                rs_pi.close();
            }
            
            if(null!=rs_ofcr&&!rs_ofcr.isClosed()){
                rs_ofcr.close();
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    private void ExtraHide(){
        hiddens = new JSeparator[]{sSearch};
        
        for (JSeparator hidden : hiddens) {
            hidden.setVisible(false);
        }
    }
    
    private void extraComponents(){
        this.setLocationRelativeTo(null);
        ExtraHide();
        
        cSortBy.add("--Select--");
        cSortBy.add("Date");
        cSortBy.add("Suspects");
        cSortBy.add("Complainants");
        cSortBy.add("Witnesses");
        cSortBy.add("Arresting Officer");
        
    }
    
    private void prompt(String p){
        JOptionPane.showMessageDialog(null, p);
    }
    
    private void prompt(int p){
        JOptionPane.showMessageDialog(null, p);
    }
    
    private void prompt(int p[]){
        JOptionPane.showMessageDialog(null, p);
    }
    
    private void write(String p){
        System.out.println(p);
    }
    
    private void write(int p){
        System.out.println(p);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblMinimized = new javax.swing.JLabel();
        lblExit = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblShowInfo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEntries = new javax.swing.JTable();
        jLabel23 = new javax.swing.JLabel();
        sSearch = new javax.swing.JSeparator();
        txtSearch = new javax.swing.JTextField();
        cSortBy = new java.awt.Choice();
        jLabel13 = new javax.swing.JLabel();
        lblRecordCount = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
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
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
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

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ico/employee_48.png"))); // NOI18N
        jLabel4.setText("Existing Records");

        lblShowInfo.setBackground(new java.awt.Color(0, 0, 0));
        lblShowInfo.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lblShowInfo.setForeground(new java.awt.Color(255, 255, 255));
        lblShowInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblShowInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ico/record_32.png"))); // NOI18N
        lblShowInfo.setText("Show Related Info");
        lblShowInfo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        lblShowInfo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblShowInfo.setEnabled(false);
        lblShowInfo.setOpaque(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblMinimized, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblExit, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(lblShowInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 184, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(331, 331, 331))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMinimized, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblExit, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(35, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblShowInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19))))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 910, 100));

        jPanel2.setBackground(new java.awt.Color(102, 0, 0));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblEntries.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Entry No.", "Date", "Name of Suspect", "Name of Complainant", "Name of Witnesses", "Name of Arresting Officer"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblEntries.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblEntries.setSelectionBackground(new java.awt.Color(0, 0, 0));
        tblEntries.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblEntries.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                tblEntriesMouseDragged(evt);
            }
        });
        tblEntries.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEntriesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblEntries);
        if (tblEntries.getColumnModel().getColumnCount() > 0) {
            tblEntries.getColumnModel().getColumn(2).setPreferredWidth(180);
            tblEntries.getColumnModel().getColumn(3).setPreferredWidth(180);
            tblEntries.getColumnModel().getColumn(4).setPreferredWidth(180);
            tblEntries.getColumnModel().getColumn(5).setPreferredWidth(180);
        }

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 873, 280));

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ico/search_32.png"))); // NOI18N
        jLabel23.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel2.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, 30, 20));
        jPanel2.add(sSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 170, -1));

        txtSearch.setBackground(new java.awt.Color(102, 0, 0));
        txtSearch.setForeground(new java.awt.Color(255, 255, 255));
        txtSearch.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSearch.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        txtSearch.setOpaque(false);
        txtSearch.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        txtSearch.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSearchMouseClicked(evt);
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });
        jPanel2.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 170, 20));

        cSortBy.setBackground(new java.awt.Color(102, 0, 0));
        cSortBy.setForeground(new java.awt.Color(255, 255, 255));
        cSortBy.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cSortByItemStateChanged(evt);
            }
        });
        jPanel2.add(cSortBy, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, 150, -1));

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Sort By:");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 10, -1, -1));

        lblRecordCount.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        lblRecordCount.setForeground(new java.awt.Color(255, 255, 255));
        lblRecordCount.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jPanel2.add(lblRecordCount, new org.netbeans.lib.awtextra.AbsoluteConstraints(714, 10, 180, 20));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 910, 340));

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

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        pX = evt.getX();
        pY = evt.getY();
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        if((evt.getX()>0&&pX!=0)&&(evt.getY()>0&&pY!=0)){
            this.setLocation(this.getLocation().x + evt.getX() - pX, this.getLocation().y + evt.getY() - pY );
        }
    }//GEN-LAST:event_formMouseDragged

    private void txtSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchMouseClicked

    }//GEN-LAST:event_txtSearchMouseClicked

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        SearchCase();
    }//GEN-LAST:event_txtSearchKeyReleased

    private void cSortByItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cSortByItemStateChanged
        
            
        if(cSortBy.getSelectedIndex()!=0){
            if(cSortBy.getSelectedItem().equals("Date")){
                orderquery = " ORDER BY DateFormatted";
            }else if(cSortBy.getSelectedItem().equals("Complainants")){
                orderquery = " ORDER BY Complainants";
            }else if(cSortBy.getSelectedItem().equals("Suspects")){
                orderquery = " ORDER BY Suspects";
            }else if(cSortBy.getSelectedItem().equals("Witnesses")){
                orderquery = " ORDER BY Witnesses";
            }else if(cSortBy.getSelectedItem().equals("Arresting Officer")){
                orderquery = " ORDER BY ArrestingOfficers";
            }
        }
        
        if(!txtSearch.getText().equals("")){
            SearchCase();
        }else{
            EntryDB();
        }
    }//GEN-LAST:event_cSortByItemStateChanged

    private void tblEntriesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEntriesMouseClicked
        GetInfo();
        if (evt.getClickCount() % 2 == 0 && !evt.isConsumed()) {
            evt.consume();
            
            CloseConnections();
            
            new EntryRecords(entryno).setVisible(true);
            this.dispose();
            
        }
    }//GEN-LAST:event_tblEntriesMouseClicked

    private void tblEntriesMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEntriesMouseDragged
        GetInfo();
    }//GEN-LAST:event_tblEntriesMouseDragged

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        CloseConnections();
    }//GEN-LAST:event_formWindowClosed

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
            java.util.logging.Logger.getLogger(ExistingRecords.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ExistingRecords.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ExistingRecords.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ExistingRecords.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ExistingRecords().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Choice cSortBy;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblExit;
    private javax.swing.JLabel lblMinimized;
    private javax.swing.JLabel lblRecordCount;
    private javax.swing.JLabel lblShowInfo;
    private javax.swing.JSeparator sSearch;
    private javax.swing.JTable tblEntries;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
