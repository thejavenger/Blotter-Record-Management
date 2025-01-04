
package blotterrecordmanagement;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.table.DefaultTableModel;

public class EntryRecords extends javax.swing.JFrame {

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
    
    public EntryRecords() {
        initComponents();
        extraComponents();
    }
    
    public EntryRecords(String eno) {
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
                
                txtEntryNo.setText(entryno);
                txtDateEntry.setText(dateentry);
                txtTimeEntry.setText(timeentry);
                
                txtFacts.setText(facts);
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
                
        
    }
    
    private void GetOfficers(){
        try {
            
            lstDuty.setModel(dmodel);
            lstOfficer.setModel(omodel);
            
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
            
            lstInvolvement.setModel(imodel);
            lstPersonInvolved.setModel(pmodel);
            
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
    
    private void GetPersonInvolvedInfo(){
        
        try {
            int cur = lstPersonInvolved.getSelectedIndex();
            
            int id = (Integer) pid.get(cur);
            
            query_pi = "SELECT * FROM PersonInvolved WHERE ID = " + id;
            rs_pi = stmt.executeQuery(query_pi);
            
            if(rs_pi.next()){
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
                
                age = rs_pi.getInt("Age");
                civilstatus = rs_pi.getString("CivilStatus");
                occupation = rs_pi.getString("Occupation");
                nativeof = rs_pi.getString("NativeOf");
                address = rs_pi.getString("Address");
                
                lblInvolvement.setText(involvement);
                
                lblFullname.setText(involved);
                lblAge.setText(Integer.toString(age));
                lblCivilStatus.setText(civilstatus);
                lblOccupation.setText(occupation);
                lblNativeOf.setText(nativeof);
                txtAddress.setText(address);
                
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
    
    private void GetOfficerInfo(){
        int cur = lstOfficer.getSelectedIndex();
        
        duty = (String) dmodel.get(cur);
        officer = (String) omodel.get(cur);
        
        lblDuty.setText(duty);
        lblOfficer.setText(officer);
        
    }
    

    class DutyAdjustmentListener implements AdjustmentListener {
      @Override
      public void adjustmentValueChanged(AdjustmentEvent ae) {
        int pos = ae.getValue();
        
        
        if(scrOfficer.getVerticalScrollBar().getValue()!=pos){
            scrOfficer.getVerticalScrollBar().setValue(pos);
        }
        
        if(scrDuty.getVerticalScrollBar().getValue()!=pos){
            scrDuty.getVerticalScrollBar().setValue(pos);
        }
        
      }
    }
    
    class InvolvementAdjustmentListener implements AdjustmentListener {
      @Override
      public void adjustmentValueChanged(AdjustmentEvent ae) {
        int pos = ae.getValue();
        
        
        if(scrInvolvement.getVerticalScrollBar().getValue()!=pos){
            scrInvolvement.getVerticalScrollBar().setValue(pos);
        }
        
        if(scrPersonInvolved.getVerticalScrollBar().getValue()!=pos){
            scrPersonInvolved.getVerticalScrollBar().setValue(pos);
        }
        
      }
    }
    
    
    private void DutyScroller(){
        
        AdjustmentListener adjustmentListener = new DutyAdjustmentListener();
        
        scrOfficer.getHorizontalScrollBar().addAdjustmentListener(adjustmentListener); 
        scrOfficer.getVerticalScrollBar().addAdjustmentListener(adjustmentListener); 
        scrDuty.getHorizontalScrollBar().addAdjustmentListener(adjustmentListener); 
        scrDuty.getVerticalScrollBar().addAdjustmentListener(adjustmentListener); 
    }
    
    private void InvolvementScroller(){
        
        AdjustmentListener adjustmentListener = new InvolvementAdjustmentListener();
        
        scrInvolvement.getHorizontalScrollBar().addAdjustmentListener(adjustmentListener); 
        scrInvolvement.getVerticalScrollBar().addAdjustmentListener(adjustmentListener); 
        scrPersonInvolved.getHorizontalScrollBar().addAdjustmentListener(adjustmentListener); 
        scrPersonInvolved.getVerticalScrollBar().addAdjustmentListener(adjustmentListener);
    }
    
    private void ExtraHide(){
        hiddens = new JSeparator[]{sEntryNo, sDate, sTime};
        
        for (JSeparator hidden : hiddens) {
            hidden.setVisible(false);
        }
    }
    
    private void extraComponents(){
        this.setLocationRelativeTo(null);
        PersonInvolvedDialog.setLocationRelativeTo(null);
        OfficerDialog.setLocationRelativeTo(null);
        ExtraHide();
        DutyScroller();
        InvolvementScroller();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PersonInvolvedDialog = new javax.swing.JDialog();
        jPanel6 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        btnOk = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        lblFullname = new javax.swing.JLabel();
        lblAge = new javax.swing.JLabel();
        lblCivilStatus = new javax.swing.JLabel();
        lblNativeOf = new javax.swing.JLabel();
        lblOccupation = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAddress = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        lblInvolvement = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        lblDialogClose = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        OfficerDialog = new javax.swing.JDialog();
        jPanel7 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        btnOk1 = new javax.swing.JButton();
        lblOfficer = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        lblDuty = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        lblDialogClose1 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
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
        lblCase = new javax.swing.JLabel();
        lblArticle = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        scrOfficer = new javax.swing.JScrollPane();
        lstOfficer = new javax.swing.JList();
        scrDuty = new javax.swing.JScrollPane();
        lstDuty = new javax.swing.JList();
        scrPersonInvolved = new javax.swing.JScrollPane();
        lstPersonInvolved = new javax.swing.JList();
        scrInvolvement = new javax.swing.JScrollPane();
        lstInvolvement = new javax.swing.JList();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtFacts = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        PersonInvolvedDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        PersonInvolvedDialog.setModal(true);
        PersonInvolvedDialog.setUndecorated(true);
        PersonInvolvedDialog.setResizable(false);
        PersonInvolvedDialog.setSize(new java.awt.Dimension(410, 500));
        PersonInvolvedDialog.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                PersonInvolvedDialogMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                PersonInvolvedDialogMouseMoved(evt);
            }
        });
        PersonInvolvedDialog.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                PersonInvolvedDialogMousePressed(evt);
            }
        });
        PersonInvolvedDialog.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                PersonInvolvedDialogWindowActivated(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                PersonInvolvedDialogWindowClosing(evt);
            }
            public void windowDeactivated(java.awt.event.WindowEvent evt) {
                PersonInvolvedDialogWindowDeactivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                PersonInvolvedDialogWindowOpened(evt);
            }
        });
        PersonInvolvedDialog.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel15.setText("Occupation:");
        jPanel6.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, -1));

        jLabel18.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel18.setText("Address:");
        jPanel6.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, -1));

        jLabel22.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel22.setText("Age:");
        jPanel6.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 70, -1, -1));

        jLabel21.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel21.setText("Fullname:");
        jPanel6.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, -1, -1));

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel14.setText("Civil Status:");
        jPanel6.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));

        btnOk.setBackground(new java.awt.Color(102, 0, 0));
        btnOk.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnOk.setForeground(new java.awt.Color(255, 255, 255));
        btnOk.setText("OK");
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });
        jPanel6.add(btnOk, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 340, 120, 31));

        jLabel20.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel20.setText("Native Of:");
        jPanel6.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, -1));

        lblFullname.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblFullname.setText("jLabel8");
        jPanel6.add(lblFullname, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, 250, -1));

        lblAge.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblAge.setText("jLabel9");
        jPanel6.add(lblAge, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, -1, -1));

        lblCivilStatus.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblCivilStatus.setText("jLabel10");
        jPanel6.add(lblCivilStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 100, 150, -1));

        lblNativeOf.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblNativeOf.setText("jLabel11");
        jPanel6.add(lblNativeOf, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 130, 260, -1));

        lblOccupation.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblOccupation.setText("jLabel12");
        jPanel6.add(lblOccupation, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 160, 260, -1));

        txtAddress.setBackground(new java.awt.Color(255, 255, 255));
        txtAddress.setColumns(20);
        txtAddress.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txtAddress.setLineWrap(true);
        txtAddress.setRows(5);
        txtAddress.setWrapStyleWord(true);
        txtAddress.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        txtAddress.setSelectionColor(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(txtAddress);

        jPanel6.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 190, 280, 140));

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel13.setText("Involvement:");
        jPanel6.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        lblInvolvement.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblInvolvement.setText("jLabel17");
        jPanel6.add(lblInvolvement, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 150, -1));

        PersonInvolvedDialog.getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 410, 380));

        jPanel9.setBackground(new java.awt.Color(0, 0, 0));

        lblDialogClose.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDialogClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ico/close-redbg_32.png"))); // NOI18N
        lblDialogClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDialogCloseMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblDialogCloseMousePressed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ico/questionmark_48.png"))); // NOI18N
        jLabel16.setText("Person Involved Info");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(0, 387, Short.MAX_VALUE)
                .addComponent(lblDialogClose, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(lblDialogClose, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(jLabel16)
                .addGap(17, 17, 17))
        );

        PersonInvolvedDialog.getContentPane().add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 410, 120));

        OfficerDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        OfficerDialog.setMinimumSize(new java.awt.Dimension(410, 325));
        OfficerDialog.setModal(true);
        OfficerDialog.setUndecorated(true);
        OfficerDialog.setResizable(false);
        OfficerDialog.setSize(new java.awt.Dimension(410, 325));
        OfficerDialog.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                OfficerDialogMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                OfficerDialogMouseMoved(evt);
            }
        });
        OfficerDialog.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                OfficerDialogMousePressed(evt);
            }
        });
        OfficerDialog.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                OfficerDialogWindowActivated(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                OfficerDialogWindowClosing(evt);
            }
            public void windowDeactivated(java.awt.event.WindowEvent evt) {
                OfficerDialogWindowDeactivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                OfficerDialogWindowOpened(evt);
            }
        });
        OfficerDialog.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel24.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel24.setText("Officer:");
        jPanel7.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, -1, -1));

        btnOk1.setBackground(new java.awt.Color(102, 0, 0));
        btnOk1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnOk1.setForeground(new java.awt.Color(255, 255, 255));
        btnOk1.setText("OK");
        btnOk1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOk1ActionPerformed(evt);
            }
        });
        jPanel7.add(btnOk1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 160, 120, 31));

        lblOfficer.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblOfficer.setText("jLabel8");
        jPanel7.add(lblOfficer, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 90, 250, -1));

        jLabel27.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel27.setText("Duty:");
        jPanel7.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, -1));

        lblDuty.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblDuty.setText("jLabel17");
        jPanel7.add(lblDuty, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 150, -1));

        OfficerDialog.getContentPane().add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 410, 210));

        jPanel10.setBackground(new java.awt.Color(0, 0, 0));

        lblDialogClose1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDialogClose1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ico/close-redbg_32.png"))); // NOI18N
        lblDialogClose1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDialogClose1MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblDialogClose1MousePressed(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ico/questionmark_48.png"))); // NOI18N
        jLabel28.setText("Officer Info");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(0, 387, Short.MAX_VALUE)
                .addComponent(lblDialogClose1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(lblDialogClose1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(jLabel28)
                .addGap(17, 17, 17))
        );

        OfficerDialog.getContentPane().add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 410, 120));

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
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ico/record_48.png"))); // NOI18N
        jLabel4.setText("Case Records");

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

        lblCase.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lblCase.setForeground(new java.awt.Color(255, 255, 255));
        lblCase.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCase.setText("jLabel8");

        lblArticle.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lblArticle.setForeground(new java.awt.Color(255, 255, 255));
        lblArticle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblArticle.setText("jLabel9");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblCase, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
                    .addComponent(lblArticle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(90, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lblMinimized, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(lblExit, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(210, 210, 210))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addComponent(sEntryNo, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(44, 44, 44)
                                .addComponent(sDate, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(54, 54, 54)
                                .addComponent(sTime, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                                .addComponent(txtTimeEntry, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(64, 64, 64))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMinimized, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblExit, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblCase)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblArticle)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 630, 190));

        jPanel3.setBackground(new java.awt.Color(102, 0, 0));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        scrOfficer.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        lstOfficer.setBackground(new java.awt.Color(102, 0, 0));
        lstOfficer.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        lstOfficer.setForeground(new java.awt.Color(255, 255, 255));
        lstOfficer.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        lstOfficer.setSelectionBackground(new java.awt.Color(255, 255, 255));
        lstOfficer.setSelectionForeground(new java.awt.Color(0, 0, 0));
        lstOfficer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstOfficerMouseClicked(evt);
            }
        });
        lstOfficer.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstOfficerValueChanged(evt);
            }
        });
        scrOfficer.setViewportView(lstOfficer);

        jPanel3.add(scrOfficer, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 40, 140, 200));

        scrDuty.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        lstDuty.setBackground(new java.awt.Color(102, 0, 0));
        lstDuty.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        lstDuty.setForeground(new java.awt.Color(255, 255, 255));
        lstDuty.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        lstDuty.setSelectionBackground(new java.awt.Color(255, 255, 255));
        lstDuty.setSelectionForeground(new java.awt.Color(0, 0, 0));
        lstDuty.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstDutyMouseClicked(evt);
            }
        });
        lstDuty.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstDutyValueChanged(evt);
            }
        });
        scrDuty.setViewportView(lstDuty);

        jPanel3.add(scrDuty, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 120, 200));

        scrPersonInvolved.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        lstPersonInvolved.setBackground(new java.awt.Color(102, 0, 0));
        lstPersonInvolved.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        lstPersonInvolved.setForeground(new java.awt.Color(255, 255, 255));
        lstPersonInvolved.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        lstPersonInvolved.setSelectionBackground(new java.awt.Color(255, 255, 255));
        lstPersonInvolved.setSelectionForeground(new java.awt.Color(0, 0, 0));
        lstPersonInvolved.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstPersonInvolvedMouseClicked(evt);
            }
        });
        lstPersonInvolved.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstPersonInvolvedValueChanged(evt);
            }
        });
        scrPersonInvolved.setViewportView(lstPersonInvolved);

        jPanel3.add(scrPersonInvolved, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 40, 140, 200));

        scrInvolvement.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        lstInvolvement.setBackground(new java.awt.Color(102, 0, 0));
        lstInvolvement.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        lstInvolvement.setForeground(new java.awt.Color(255, 255, 255));
        lstInvolvement.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        lstInvolvement.setSelectionBackground(new java.awt.Color(255, 255, 255));
        lstInvolvement.setSelectionForeground(new java.awt.Color(0, 0, 0));
        lstInvolvement.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstInvolvementMouseClicked(evt);
            }
        });
        lstInvolvement.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstInvolvementValueChanged(evt);
            }
        });
        scrInvolvement.setViewportView(lstInvolvement);

        jPanel3.add(scrInvolvement, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 40, 120, 200));

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
        jScrollPane5.setViewportView(txtFacts);

        jPanel3.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 270, 560, 170));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Facts:");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 250, 60, -1));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Officers:");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Person Involved:");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 20, -1, -1));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 630, 460));

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

    private void txtFactsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFactsKeyReleased
        //ValidForFinalization();
    }//GEN-LAST:event_txtFactsKeyReleased

    private void lstDutyValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstDutyValueChanged
        lstOfficer.setSelectedIndex(lstDuty.getSelectedIndex());
        lstOfficer.ensureIndexIsVisible(lstDuty.getSelectedIndex());
    }//GEN-LAST:event_lstDutyValueChanged

    private void lstPersonInvolvedValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstPersonInvolvedValueChanged
        lstInvolvement.setSelectedIndex(lstPersonInvolved.getSelectedIndex());
        lstInvolvement.ensureIndexIsVisible(lstPersonInvolved.getSelectedIndex());
    }//GEN-LAST:event_lstPersonInvolvedValueChanged

    private void lstOfficerValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstOfficerValueChanged
        lstDuty.setSelectedIndex(lstOfficer.getSelectedIndex());
        lstDuty.ensureIndexIsVisible(lstOfficer.getSelectedIndex());
    }//GEN-LAST:event_lstOfficerValueChanged

    private void lstInvolvementValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstInvolvementValueChanged
        lstPersonInvolved.setSelectedIndex(lstInvolvement.getSelectedIndex());
        lstPersonInvolved.ensureIndexIsVisible(lstInvolvement.getSelectedIndex());
    }//GEN-LAST:event_lstInvolvementValueChanged

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        pX = evt.getX();
        pY = evt.getY();
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        if((evt.getX()>0&&pX!=0)&&(evt.getY()>0&&pY!=0)){
            this.setLocation(this.getLocation().x + evt.getX() - pX, this.getLocation().y + evt.getY() - pY );
        }
    }//GEN-LAST:event_formMouseDragged

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
//        SaveNewCustomer();
//        ResetCustomerForm();
        PersonInvolvedDialog.setVisible(false);
        PersonInvolvedDialog.dispose();
    }//GEN-LAST:event_btnOkActionPerformed

    private void lblDialogCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDialogCloseMouseClicked

    }//GEN-LAST:event_lblDialogCloseMouseClicked

    private void lblDialogCloseMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDialogCloseMousePressed
        PersonInvolvedDialog.dispose();
    }//GEN-LAST:event_lblDialogCloseMousePressed

    private void PersonInvolvedDialogMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PersonInvolvedDialogMouseDragged
        if(PersonInvolvedDialog.isVisible()){
            PersonInvolvedDialog.setLocation(PersonInvolvedDialog.getLocation().x + evt.getX() - dX, PersonInvolvedDialog.getLocation().y + evt.getY() - dY );
        }
    }//GEN-LAST:event_PersonInvolvedDialogMouseDragged

    private void PersonInvolvedDialogMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PersonInvolvedDialogMouseMoved

    }//GEN-LAST:event_PersonInvolvedDialogMouseMoved

    private void PersonInvolvedDialogMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PersonInvolvedDialogMousePressed
        if(PersonInvolvedDialog.isVisible()){
            dX = evt.getX();
            dY = evt.getY();
        }
    }//GEN-LAST:event_PersonInvolvedDialogMousePressed

    private void PersonInvolvedDialogWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_PersonInvolvedDialogWindowActivated

    }//GEN-LAST:event_PersonInvolvedDialogWindowActivated

    private void PersonInvolvedDialogWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_PersonInvolvedDialogWindowClosing

    }//GEN-LAST:event_PersonInvolvedDialogWindowClosing

    private void PersonInvolvedDialogWindowDeactivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_PersonInvolvedDialogWindowDeactivated

    }//GEN-LAST:event_PersonInvolvedDialogWindowDeactivated

    private void PersonInvolvedDialogWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_PersonInvolvedDialogWindowOpened

    }//GEN-LAST:event_PersonInvolvedDialogWindowOpened

    private void lstPersonInvolvedMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstPersonInvolvedMouseClicked

        if (evt.getClickCount() % 2 == 0 && !evt.isConsumed()) {
            evt.consume();

            GetPersonInvolvedInfo();

            PersonInvolvedDialog.setVisible(true);
        }
    }//GEN-LAST:event_lstPersonInvolvedMouseClicked

    private void lstInvolvementMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstInvolvementMouseClicked
        if (evt.getClickCount() % 2 == 0 && !evt.isConsumed()) {
            evt.consume();

            GetPersonInvolvedInfo();

            PersonInvolvedDialog.setVisible(true);
        }
    }//GEN-LAST:event_lstInvolvementMouseClicked

    private void btnOk1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOk1ActionPerformed
        OfficerDialog.dispose();
    }//GEN-LAST:event_btnOk1ActionPerformed

    private void lblDialogClose1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDialogClose1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblDialogClose1MouseClicked

    private void lblDialogClose1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDialogClose1MousePressed
        OfficerDialog.dispose();
    }//GEN-LAST:event_lblDialogClose1MousePressed

    private void OfficerDialogMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_OfficerDialogMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_OfficerDialogMouseDragged

    private void OfficerDialogMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_OfficerDialogMouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_OfficerDialogMouseMoved

    private void OfficerDialogMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_OfficerDialogMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_OfficerDialogMousePressed

    private void OfficerDialogWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_OfficerDialogWindowActivated
        // TODO add your handling code here:
    }//GEN-LAST:event_OfficerDialogWindowActivated

    private void OfficerDialogWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_OfficerDialogWindowClosing
        // TODO add your handling code here:
    }//GEN-LAST:event_OfficerDialogWindowClosing

    private void OfficerDialogWindowDeactivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_OfficerDialogWindowDeactivated
        // TODO add your handling code here:
    }//GEN-LAST:event_OfficerDialogWindowDeactivated

    private void OfficerDialogWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_OfficerDialogWindowOpened
        // TODO add your handling code here:
    }//GEN-LAST:event_OfficerDialogWindowOpened

    private void lstOfficerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstOfficerMouseClicked
        
        
        if (evt.getClickCount() % 2 == 0 && !evt.isConsumed()) {
            evt.consume();

            GetOfficerInfo();

            OfficerDialog.setVisible(true);
        }
    }//GEN-LAST:event_lstOfficerMouseClicked

    private void lstDutyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstDutyMouseClicked
        if (evt.getClickCount() % 2 == 0 && !evt.isConsumed()) {
            evt.consume();

            GetOfficerInfo();

            OfficerDialog.setVisible(true);
        }
    }//GEN-LAST:event_lstDutyMouseClicked

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
            java.util.logging.Logger.getLogger(EntryRecords.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EntryRecords.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EntryRecords.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EntryRecords.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EntryRecords().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog OfficerDialog;
    private javax.swing.JDialog PersonInvolvedDialog;
    private javax.swing.JButton btnOk;
    private javax.swing.JButton btnOk1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblAge;
    private javax.swing.JLabel lblArticle;
    private javax.swing.JLabel lblCase;
    private javax.swing.JLabel lblCivilStatus;
    private javax.swing.JLabel lblDialogClose;
    private javax.swing.JLabel lblDialogClose1;
    private javax.swing.JLabel lblDuty;
    private javax.swing.JLabel lblExit;
    private javax.swing.JLabel lblFullname;
    private javax.swing.JLabel lblInvolvement;
    private javax.swing.JLabel lblMinimized;
    private javax.swing.JLabel lblNativeOf;
    private javax.swing.JLabel lblOccupation;
    private javax.swing.JLabel lblOfficer;
    private javax.swing.JList lstDuty;
    private javax.swing.JList lstInvolvement;
    private javax.swing.JList lstOfficer;
    private javax.swing.JList lstPersonInvolved;
    private javax.swing.JSeparator sDate;
    private javax.swing.JSeparator sEntryNo;
    private javax.swing.JSeparator sTime;
    private javax.swing.JScrollPane scrDuty;
    private javax.swing.JScrollPane scrInvolvement;
    private javax.swing.JScrollPane scrOfficer;
    private javax.swing.JScrollPane scrPersonInvolved;
    private javax.swing.JTextArea txtAddress;
    private javax.swing.JTextField txtDateEntry;
    private javax.swing.JTextField txtEntryNo;
    private javax.swing.JTextArea txtFacts;
    private javax.swing.JTextField txtTimeEntry;
    // End of variables declaration//GEN-END:variables
}
