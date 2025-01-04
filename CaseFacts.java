
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

public class CaseFacts extends javax.swing.JFrame {

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
    
    String entryno;
    String dateentry;
    String timeentry;
    String dateformatted;
    
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
    String involvedf;
    
    String officer;
    String duty;
    
    int pX;
    int pY;
    int dX;
    int dY;
    
    boolean investigator1stsave = true;
    boolean arrestingofficer1stsave = true;
    boolean investigatorrecorded;
    boolean arrestingofficerrecorded;
    
    String personinvolved;
    String officers;
    
    JSeparator hiddens[];
    
    public CaseFacts() {
        initComponents();
    }
    
    
    public CaseFacts(String eno, String d, String df, String t) {
        initComponents();
        entryno = eno;
        dateentry = d;
        timeentry = t;
        dateformatted = df;
        SetEntry();
        ReadyConnection();
        extraComponents();
    }
    
    private void ReadyConnection(){
        try {
            connection = db.doConnection(connection, "", "");
            stmt = connection.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE );
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Cannot open connection!", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void ValidForFinalization(){
        
        lblFinalize.setEnabled(false);
        
        if(!txtCaseName.getText().equals("")&&!txtArticle.getText().equals("")&&
                !txtInvestigator.getText().equals("")&&!txtArrestingOfficer.getText().equals("")&&
                !txtFacts.getText().equals("")){
            lblFinalize.setEnabled(true);
        }
        
    }
    
    private void SetEntry(){
        
        txtEntryNo.setText(entryno);
        txtDateEntry.setText(dateentry);
        txtTimeEntry.setText(timeentry);
        
    }
    
    private void RecordOfficer(String off, String d){
        
        try {
            int newid = GetLastID("Officers") + 1;
            
            String name = off;
            String duty = d;
            
            query_ofcr = "SELECT * FROM Officers";
            rs_ofcr = stmt.executeQuery(query_ofcr);
            
            rs_ofcr.moveToInsertRow();
            
            rs_ofcr.updateInt("ID", newid);
            rs_ofcr.updateString("EntryNo", entryno);
            rs_ofcr.updateString("Name", name);
            rs_ofcr.updateString("Duty", duty);
            
            rs_ofcr.insertRow();
            
            stmt.close();
            rs_ofcr.close();
            
            ReadyConnection();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Fatal Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void SaveCaseInfo(){
        
        try {
            int newid = GetLastID("CaseInfo") + 1;
            
            query_ci = "SELECT * FROM CaseInfo";
            rs_ci = stmt.executeQuery(query_ci);
            
            rs_ci.moveToInsertRow();
            
            rs_ci.updateInt("ID", newid);
            rs_ci.updateString("EntryNo", entryno);
            rs_ci.updateString("DateEntry", dateentry);
            rs_ci.updateString("TimeEntry", timeentry);
            
            rs_ci.updateString("CaseName", txtCaseName.getText());
            rs_ci.updateString("Article", txtArticle.getText());
            rs_ci.updateString("Facts", txtFacts.getText());
            rs_ci.updateString("Complainants", complainants);
            rs_ci.updateString("Victims", victims);
            rs_ci.updateString("Suspects", suspects);
            rs_ci.updateString("Witnesses",witnesses);
            rs_ci.updateString("ComplainantsFormatted", complainantsf);
            rs_ci.updateString("VictimsFormatted", victimsf);
            rs_ci.updateString("SuspectsFormatted", suspectsf);
            rs_ci.updateString("WitnessesFormatted",witnessesf);
            rs_ci.updateString("Investigators", investigators);
            rs_ci.updateString("ArrestingOfficers", arrestingofficers);
            rs_ci.updateString("DateFormatted", dateformatted);
            
            rs_ci.insertRow();
            
            JOptionPane.showMessageDialog(null, "New case recorded!");
            
            stmt.close();
            rs_ci.close();
            
            ReadyConnection();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Fatal Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void CancelRecords(){
        try {
            int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel all recordings related to Entry: " + entryno + "?", "Confirm deletion", JOptionPane.YES_NO_OPTION);

            if (reply == JOptionPane.YES_OPTION) {
                
                query_pi = "SELECT * FROM PersonInvolved WHERE EntryNo = '" + entryno + "'";
                rs_pi = stmt.executeQuery(query_pi);

                while(rs_pi.next()){
                    rs_pi.deleteRow();
                }
                
                query_ofcr = "SELECT * FROM Officers WHERE EntryNo = '" + entryno + "'";
                rs_ofcr = stmt.executeQuery(query_ofcr);

                while(rs_ofcr.next()){
                    rs_ofcr.deleteRow();
                }
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Fatal Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private int GetLastID(String tbl){
        
        int lastid = 0;
        
        try {
            query = "SELECT MAX(ID) AS max_id FROM " + tbl;
            rs = stmt.executeQuery(query);
            
            if(rs.next()){
                lastid = rs.getInt("max_id");
            }
            
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Fatal Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return lastid;
    }
    
    private void RetrieveAllOfficers(){
        try {
            query_ofcr = "SELECT * FROM Officers WHERE EntryNo = '" + entryno + "'";
            rs_ofcr = stmt.executeQuery(query_ofcr);
            
            while(rs_ofcr.next()){
                officer = rs_ofcr.getString("Name");
                duty = rs_ofcr.getString("Duty");
                
                if(duty.equals("Arresting Officer")){
                    if(!arrestingofficers.equals("")){
                        arrestingofficers += "; ";
                    }
                    
                    arrestingofficers += officer;
                }else if(duty.equals("Investigators")){
                    if(!investigators.equals("")){
                        investigators += "; ";
                    }
                    
                    investigators += officer;
                }
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Fatal Error", JOptionPane.ERROR_MESSAGE);
        }
        
        
    }
    
    private void RetrieveAllPersonInvolved(){
        try {
            query_pi = "SELECT * FROM PersonInvolved WHERE EntryNo = '" + entryno + "'";
            rs_pi = stmt.executeQuery(query_pi);
            
            while(rs_pi.next()){
                    
                    involvement = rs_pi.getString("Involvement");
                    lastname = rs_pi.getString("Lastname");
                    firstname = rs_pi.getString("Firstname");
                    mi = rs_pi.getString("MI");
                    suffix = rs_pi.getString("Suffix");
                    
                    if(suffix.equals("")&&mi.equals("")){
                        involvedf = lastname + ", " + firstname;
                        involved = firstname + " " + lastname;
                    }else if(suffix.equals("")){
                        involvedf = lastname + ", " + firstname + " " + mi + ".";
                        involved = firstname + " " + mi + ". " + lastname;
                    }else if(mi.equals("")){
                        involvedf = lastname + " " + suffix + ", " + firstname;
                        involved = firstname + " " + lastname + " " + suffix;
                    }else{
                        involvedf = lastname + " " + suffix + ", " + firstname + " " + mi + ".";
                        involved = firstname + " " + mi + ". " + lastname + " " + suffix;
                    }
                    
                    if(involvement.equals("Complainant")){
                        
                        if(!complainants.equals("")){
                            complainants += "; ";
                        }
                        
                        complainants += involved;
                        complainantsf += involvedf;
                        
                    }else if(involvement.equals("Witness")){
                        
                        if(!witnesses.equals("")){
                            witnesses += "; ";
                        }
                        
                        witnesses += involved;
                        witnessesf += involvedf;
                        
                    }else if(involvement.equals("Suspect")){
                        
                        if(!suspects.equals("")){
                            suspects += "; ";
                        }
                        
                        suspects += involved;
                        suspectsf += involvedf;
                        
                    }else if(involvement.equals("Victim")){
                        if(!victims.equals("")){
                            victims += "; ";
                        }
                        
                        victims += involved;
                        victimsf += involvedf;
                        
                    }
                }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Fatal Error", JOptionPane.ERROR_MESSAGE);
        }
        
        
    }
    
    private void ExtraHide(){
        hiddens = new JSeparator[]{sCaseName, sEntryNo, sDate, sTime, sInvestigator, sArrestingOfficer};
        
        for (JSeparator hidden : hiddens) {
            hidden.setVisible(false);
        }
    }

    private void extraComponents(){
        this.setLocationRelativeTo(null);
        ExtraHide();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblMinimized = new javax.swing.JLabel();
        lblExit = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtEntryNo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtDateEntry = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtTimeEntry = new javax.swing.JTextField();
        sEntryNo = new javax.swing.JSeparator();
        sDate = new javax.swing.JSeparator();
        sTime = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        sInvestigator = new javax.swing.JSeparator();
        sArrestingOfficer = new javax.swing.JSeparator();
        txtArrestingOfficer = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        lblAddInvestigator = new javax.swing.JLabel();
        lblAddArrestingOfficer = new javax.swing.JLabel();
        txtInvestigator = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtFacts = new javax.swing.JTextArea();
        lblFinalize = new javax.swing.JLabel();
        lblCancel = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        sCaseName = new javax.swing.JSeparator();
        txtCaseName = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        sArticle = new javax.swing.JSeparator();
        txtArticle = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        sArrestingOfficer1 = new javax.swing.JSeparator();
        txtArrestingOfficer1 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        lblAddArrestingOfficer1 = new javax.swing.JLabel();

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

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ico/edit_48.png"))); // NOI18N
        jLabel4.setText("Case Facts");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Entry No.:");

        txtEntryNo.setEditable(false);
        txtEntryNo.setBackground(new java.awt.Color(0, 0, 0));
        txtEntryNo.setForeground(new java.awt.Color(255, 255, 255));
        txtEntryNo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEntryNo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        txtEntryNo.setMargin(new java.awt.Insets(0, 1, 0, 0));
        txtEntryNo.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        txtEntryNo.setSelectionColor(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Date:");

        txtDateEntry.setEditable(false);
        txtDateEntry.setBackground(new java.awt.Color(0, 0, 0));
        txtDateEntry.setForeground(new java.awt.Color(255, 255, 255));
        txtDateEntry.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDateEntry.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        txtDateEntry.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        txtDateEntry.setSelectionColor(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Time:");

        txtTimeEntry.setEditable(false);
        txtTimeEntry.setBackground(new java.awt.Color(0, 0, 0));
        txtTimeEntry.setForeground(new java.awt.Color(255, 255, 255));
        txtTimeEntry.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTimeEntry.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        txtTimeEntry.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        txtTimeEntry.setSelectionColor(new java.awt.Color(255, 255, 255));

        sEntryNo.setBackground(new java.awt.Color(0, 0, 0));
        sEntryNo.setForeground(new java.awt.Color(102, 0, 0));
        sEntryNo.setOpaque(true);

        sDate.setBackground(new java.awt.Color(0, 0, 0));
        sDate.setForeground(new java.awt.Color(102, 0, 0));
        sDate.setOpaque(true);

        sTime.setBackground(new java.awt.Color(0, 0, 0));
        sTime.setForeground(new java.awt.Color(102, 0, 0));
        sTime.setOpaque(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(6, 6, 6)
                        .addComponent(txtEntryNo, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addComponent(jLabel2)
                        .addGap(1, 1, 1)
                        .addComponent(txtDateEntry, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addComponent(jLabel3)
                        .addGap(8, 8, 8)
                        .addComponent(txtTimeEntry, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(sEntryNo, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(sDate, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54)
                        .addComponent(sTime, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 82, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lblMinimized, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(lblExit, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(232, 232, 232))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMinimized, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblExit, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(txtEntryNo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtDateEntry, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtTimeEntry, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sEntryNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 630, 140));

        jPanel3.setBackground(new java.awt.Color(102, 0, 0));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sInvestigator.setBackground(new java.awt.Color(102, 0, 0));
        sInvestigator.setForeground(new java.awt.Color(102, 0, 0));
        sInvestigator.setOpaque(true);
        jPanel3.add(sInvestigator, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 110, 190, -1));

        sArrestingOfficer.setBackground(new java.awt.Color(102, 0, 0));
        sArrestingOfficer.setForeground(new java.awt.Color(102, 0, 0));
        sArrestingOfficer.setOpaque(true);
        jPanel3.add(sArrestingOfficer, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 140, 190, -1));

        txtArrestingOfficer.setBackground(new java.awt.Color(102, 0, 0));
        txtArrestingOfficer.setForeground(new java.awt.Color(255, 255, 255));
        txtArrestingOfficer.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtArrestingOfficer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        txtArrestingOfficer.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        txtArrestingOfficer.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtArrestingOfficer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtArrestingOfficerKeyReleased(evt);
            }
        });
        jPanel3.add(txtArrestingOfficer, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 120, 190, 20));

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Arresting Officer:");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, -1, -1));

        lblAddInvestigator.setBackground(new java.awt.Color(0, 0, 0));
        lblAddInvestigator.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lblAddInvestigator.setForeground(new java.awt.Color(255, 255, 255));
        lblAddInvestigator.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAddInvestigator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ico/plus_32.png"))); // NOI18N
        lblAddInvestigator.setText("Add More");
        lblAddInvestigator.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        lblAddInvestigator.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblAddInvestigator.setEnabled(false);
        lblAddInvestigator.setOpaque(true);
        lblAddInvestigator.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblAddInvestigatorMousePressed(evt);
            }
        });
        jPanel3.add(lblAddInvestigator, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 90, 130, 20));

        lblAddArrestingOfficer.setBackground(new java.awt.Color(0, 0, 0));
        lblAddArrestingOfficer.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lblAddArrestingOfficer.setForeground(new java.awt.Color(255, 255, 255));
        lblAddArrestingOfficer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAddArrestingOfficer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ico/plus_32.png"))); // NOI18N
        lblAddArrestingOfficer.setText("Add More");
        lblAddArrestingOfficer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        lblAddArrestingOfficer.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblAddArrestingOfficer.setEnabled(false);
        lblAddArrestingOfficer.setOpaque(true);
        lblAddArrestingOfficer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblAddArrestingOfficerMousePressed(evt);
            }
        });
        jPanel3.add(lblAddArrestingOfficer, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 120, 130, 20));

        txtInvestigator.setBackground(new java.awt.Color(102, 0, 0));
        txtInvestigator.setForeground(new java.awt.Color(255, 255, 255));
        txtInvestigator.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtInvestigator.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        txtInvestigator.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        txtInvestigator.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtInvestigator.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtInvestigatorKeyReleased(evt);
            }
        });
        jPanel3.add(txtInvestigator, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 90, 190, 20));

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Article:");
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, -1, -1));

        txtFacts.setBackground(new java.awt.Color(102, 0, 0));
        txtFacts.setColumns(20);
        txtFacts.setForeground(new java.awt.Color(255, 255, 255));
        txtFacts.setLineWrap(true);
        txtFacts.setRows(5);
        txtFacts.setWrapStyleWord(true);
        txtFacts.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        txtFacts.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        txtFacts.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtFacts.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFactsKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(txtFacts);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 190, 437, 167));

        lblFinalize.setBackground(new java.awt.Color(0, 0, 0));
        lblFinalize.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lblFinalize.setForeground(new java.awt.Color(255, 255, 255));
        lblFinalize.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFinalize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ico/save_32.png"))); // NOI18N
        lblFinalize.setText("Finalize");
        lblFinalize.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        lblFinalize.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblFinalize.setEnabled(false);
        lblFinalize.setOpaque(true);
        lblFinalize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblFinalizeMousePressed(evt);
            }
        });
        jPanel3.add(lblFinalize, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 370, 120, 30));

        lblCancel.setBackground(new java.awt.Color(0, 0, 0));
        lblCancel.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lblCancel.setForeground(new java.awt.Color(255, 255, 255));
        lblCancel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ico/close-white_32.png"))); // NOI18N
        lblCancel.setText("Cancel");
        lblCancel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        lblCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblCancel.setOpaque(true);
        lblCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblCancelMousePressed(evt);
            }
        });
        jPanel3.add(lblCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 370, 130, 30));

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Facts:");
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 170, -1, -1));

        sCaseName.setBackground(new java.awt.Color(102, 0, 0));
        sCaseName.setForeground(new java.awt.Color(102, 0, 0));
        sCaseName.setOpaque(true);
        jPanel3.add(sCaseName, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 40, 330, -1));

        txtCaseName.setBackground(new java.awt.Color(102, 0, 0));
        txtCaseName.setForeground(new java.awt.Color(255, 255, 255));
        txtCaseName.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCaseName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        txtCaseName.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        txtCaseName.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtCaseName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCaseNameKeyReleased(evt);
            }
        });
        jPanel3.add(txtCaseName, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 20, 330, 20));

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Investigator:");
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 90, -1, -1));

        sArticle.setBackground(new java.awt.Color(102, 0, 0));
        sArticle.setForeground(new java.awt.Color(102, 0, 0));
        sArticle.setOpaque(true);
        jPanel3.add(sArticle, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 70, 330, -1));

        txtArticle.setBackground(new java.awt.Color(102, 0, 0));
        txtArticle.setForeground(new java.awt.Color(255, 255, 255));
        txtArticle.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtArticle.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        txtArticle.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        txtArticle.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtArticle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtArticleKeyReleased(evt);
            }
        });
        jPanel3.add(txtArticle, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 50, 330, 20));

        jLabel15.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Case Name:");
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 20, -1, -1));

        sArrestingOfficer1.setBackground(new java.awt.Color(102, 0, 0));
        sArrestingOfficer1.setForeground(new java.awt.Color(102, 0, 0));
        sArrestingOfficer1.setOpaque(true);
        jPanel3.add(sArrestingOfficer1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 140, 190, -1));

        txtArrestingOfficer1.setBackground(new java.awt.Color(102, 0, 0));
        txtArrestingOfficer1.setForeground(new java.awt.Color(255, 255, 255));
        txtArrestingOfficer1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtArrestingOfficer1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        txtArrestingOfficer1.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        txtArrestingOfficer1.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtArrestingOfficer1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtArrestingOfficer1KeyReleased(evt);
            }
        });
        jPanel3.add(txtArrestingOfficer1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 120, 190, 20));

        jLabel16.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Arresting Officer:");
        jPanel3.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, -1, -1));

        lblAddArrestingOfficer1.setBackground(new java.awt.Color(0, 0, 0));
        lblAddArrestingOfficer1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lblAddArrestingOfficer1.setForeground(new java.awt.Color(255, 255, 255));
        lblAddArrestingOfficer1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAddArrestingOfficer1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ico/plus_32.png"))); // NOI18N
        lblAddArrestingOfficer1.setText("Add More");
        lblAddArrestingOfficer1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        lblAddArrestingOfficer1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblAddArrestingOfficer1.setEnabled(false);
        lblAddArrestingOfficer1.setOpaque(true);
        lblAddArrestingOfficer1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblAddArrestingOfficer1MousePressed(evt);
            }
        });
        jPanel3.add(lblAddArrestingOfficer1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 120, 130, 20));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 630, 440));

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

    private void txtInvestigatorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInvestigatorKeyReleased
        if(!txtInvestigator.getText().equals("")){
            lblAddInvestigator.setEnabled(true);
        }else{
            lblAddInvestigator.setEnabled(false);
        }
        ValidForFinalization();
    }//GEN-LAST:event_txtInvestigatorKeyReleased

    private void txtArrestingOfficerKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtArrestingOfficerKeyReleased
        if(!txtArrestingOfficer.getText().equals("")){
            lblAddArrestingOfficer.setEnabled(true);
        }else{
            lblAddArrestingOfficer.setEnabled(false);
        }
        ValidForFinalization();
    }//GEN-LAST:event_txtArrestingOfficerKeyReleased

    private void txtFactsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFactsKeyReleased
        ValidForFinalization();
    }//GEN-LAST:event_txtFactsKeyReleased

    private void lblAddInvestigatorMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAddInvestigatorMousePressed
        if(lblAddInvestigator.isEnabled()){
            
            String onfields = txtInvestigator.getText();
            
            if(investigator1stsave){
                RecordOfficer(onfields, "Investigator");
                investigator1stsave = false;
            }
            
            String newofficer = JOptionPane.showInputDialog(rootPane, "Type new Investigator.");
            
            if(null!=newofficer&&!"".equals(newofficer)){
                RecordOfficer(newofficer, "Investigator");

                txtInvestigator.setText(onfields + ", " + newofficer);

            }
            
        }
    }//GEN-LAST:event_lblAddInvestigatorMousePressed

    private void lblAddArrestingOfficerMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAddArrestingOfficerMousePressed
        if(lblAddArrestingOfficer.isEnabled()){
            
            String onfields = txtArrestingOfficer.getText();
            
            if(arrestingofficer1stsave){
                RecordOfficer(onfields, "Arresting Officer");
                arrestingofficer1stsave = false;
            }
            
            String newofficer = JOptionPane.showInputDialog(rootPane, "Type new Arresting Officer.");
            
            if(null!=newofficer&&!"".equals(newofficer)){
                RecordOfficer(newofficer, "Arresting Officer");

                txtArrestingOfficer.setText(onfields + ", " + newofficer);
            }
            
        }
    }//GEN-LAST:event_lblAddArrestingOfficerMousePressed

    private void lblFinalizeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblFinalizeMousePressed
        if(lblFinalize.isEnabled()){
            
            if(investigator1stsave){
                RecordOfficer(txtInvestigator.getText(), "Investigator");
            }
            
            if(arrestingofficer1stsave){
                RecordOfficer(txtArrestingOfficer.getText(), "Arresting Officer");
            }
            
            RetrieveAllPersonInvolved();
            RetrieveAllOfficers();
            
            SaveCaseInfo();
            
            int reply = JOptionPane.showConfirmDialog(null, "Do you want print a copy of this report?", "Print a copy?", JOptionPane.YES_NO_OPTION);

            if (reply == JOptionPane.YES_OPTION) {
                new CreateCopy(entryno).setVisible(true);
            }
            
            this.dispose();
        }
    }//GEN-LAST:event_lblFinalizeMousePressed

    private void lblCancelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCancelMousePressed
        if(lblCancel.isEnabled()){
            CancelRecords();
            
            this.dispose();
        }
    }//GEN-LAST:event_lblCancelMousePressed

    private void txtCaseNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCaseNameKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCaseNameKeyReleased

    private void txtArticleKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtArticleKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtArticleKeyReleased

    private void txtArrestingOfficer1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtArrestingOfficer1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtArrestingOfficer1KeyReleased

    private void lblAddArrestingOfficer1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAddArrestingOfficer1MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblAddArrestingOfficer1MousePressed

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
            java.util.logging.Logger.getLogger(CaseFacts.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CaseFacts.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CaseFacts.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CaseFacts.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CaseFacts().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAddArrestingOfficer;
    private javax.swing.JLabel lblAddArrestingOfficer1;
    private javax.swing.JLabel lblAddInvestigator;
    private javax.swing.JLabel lblCancel;
    private javax.swing.JLabel lblExit;
    private javax.swing.JLabel lblFinalize;
    private javax.swing.JLabel lblMinimized;
    private javax.swing.JSeparator sArrestingOfficer;
    private javax.swing.JSeparator sArrestingOfficer1;
    private javax.swing.JSeparator sArticle;
    private javax.swing.JSeparator sCaseName;
    private javax.swing.JSeparator sDate;
    private javax.swing.JSeparator sEntryNo;
    private javax.swing.JSeparator sInvestigator;
    private javax.swing.JSeparator sTime;
    private javax.swing.JTextField txtArrestingOfficer;
    private javax.swing.JTextField txtArrestingOfficer1;
    private javax.swing.JTextField txtArticle;
    private javax.swing.JTextField txtCaseName;
    private javax.swing.JTextField txtDateEntry;
    private javax.swing.JTextField txtEntryNo;
    private javax.swing.JTextArea txtFacts;
    private javax.swing.JTextField txtInvestigator;
    private javax.swing.JTextField txtTimeEntry;
    // End of variables declaration//GEN-END:variables
}
