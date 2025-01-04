
package blotterrecordmanagement;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JSeparator;

public class GeneralInfo extends javax.swing.JFrame {

    Connect db = new Connect();
    InputManager in = new InputManager();
    Connection connection;
    Statement stmt;
    
    ResultSet rs;
    String query;
    
    ResultSet rs_ci;
    String query_ci;
    
    ResultSet rs_pi;
    String query_pi;
    
    int pX;
    int pY;
    int dX;
    int dY;
    
    String entryno;
    String dateentry;
    String timeentry;
    
    String involvement;
    String lastname;
    String firstname;
    String mi;
    String suffix;
    int age;
    String civilstatus;
    String occupation;
    String nativeof;
    String address;
    
    boolean complainantfilled = false;
    boolean victimfilled = false;
    boolean suspectfilled = false;
    boolean witnessfilled = false;
    
    boolean firstsave = true;
    
    int progress;
    int currentprogress = 0;
    
    String newdatesave;
    String dateformatted;
    
    
    JSeparator hiddens[];
    JLabel progressstep[];
    
    CurrentCalendar curdate = new CurrentCalendar();
    NewCalendar dater;
    
    public GeneralInfo() {
        initComponents();
        ReadyConnection();
        SetEntry();
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
    
//    private void CustomerDB(){
//        try {
//            query_c = "SELECT * FROM Customers";
//            rs_c = stmt.executeQuery(query_c);
//            
//            //ShowItemData();
//            
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(this, ex.getMessage());
//        }
//    }
    
    private void SetEntry(){
        
        entryno = String.format("%06d", GetLastID("CaseInfo") + 1);
        dateentry = curdate.getFullDate();
        timeentry = curdate.getSimpleTime();
        
        dateformatted = curdate.getYear() + " " + String.format("%02d", curdate.getIntMonth(curdate.getStringMonth())) + " " + String.format("%02d", curdate.getDate());
        
        txtEntryNo.setText(entryno);
        txtDateEntry.setText(dateentry);
        txtTimeEntry.setText(timeentry);
        
        
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
    
    private void ValidForConfirmation(){
        
        lblConfirm.setEnabled(false);
        
        if(!txtEntryNo.getText().equals("")&&!txtDateEntry.getText().equals("")&&!txtTimeEntry.getText().equals("")&&
                cInvolvement.getSelectedIndex()!=0&&!txtLastname.getText().equals("")&&!txtFirstname.getText().equals("")&&
                !txtAge.getText().equals("")&&cCivilStatus.getSelectedIndex()!=0&&!txtOccupation.getText().equals("")&&
                !txtNativeOf.getText().equals("")&&!txtAddress.getText().equals("")){
            
            lblConfirm.setEnabled(true);
        }
        
    }
    
    private void CheckProgress(){
        
        progress = 0;
        
        if(complainantfilled){
            progress++;
        }
        
        if(victimfilled){
            progress++;
        }
        
        if(suspectfilled){
            progress++;
        }
        
        if(witnessfilled){
            progress++;
        }
        
        if(currentprogress<progress){
            currentprogress = progress;
            
            progressstep[currentprogress-1].setEnabled(true);
            progressstep[currentprogress-1].setOpaque(true);
            progressstep[currentprogress-1].setText(cInvolvement.getSelectedItem());
            
            if(progressstep.length==currentprogress){
                lblNext.setEnabled(true);
            }
        }
        
    }
    
    private void RecordPersonInvolved(){
        
        try {
            int newid = GetLastID("PersonInvolved") + 1;
            
            query_pi = "SELECT * FROM PersonInvolved";
            rs_pi = stmt.executeQuery(query_pi);
            
            
            
            involvement = cInvolvement.getSelectedItem();
            lastname = txtLastname.getText();
            firstname = txtFirstname.getText();
            mi = txtMI.getText();
            suffix = txtSuffix.getText();
            age = Integer.parseInt(txtAge.getText());
            civilstatus = cCivilStatus.getSelectedItem();
            occupation = txtOccupation.getText();
            nativeof = txtNativeOf.getText();
            address = txtAddress.getText();
            
            rs_pi.moveToInsertRow();
            
            rs_pi.updateInt("ID", newid);
            rs_pi.updateString("EntryNo", entryno);
            rs_pi.updateString("Involvement", involvement);
            rs_pi.updateString("Lastname", lastname);
            rs_pi.updateString("Firstname", firstname);
            rs_pi.updateString("MI", mi);
            rs_pi.updateString("Suffix", suffix);
            rs_pi.updateInt("Age", age);
            rs_pi.updateString("CivilStatus", civilstatus);
            rs_pi.updateString("Occupation", occupation);
            rs_pi.updateString("NativeOf", nativeof);
            rs_pi.updateString("Address", address);
            
            rs_pi.insertRow();
            
            JOptionPane.showMessageDialog(null, "New " + involvement + " added!");
            
            stmt.close();
            rs_pi.close();
            
            ReadyConnection();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Fatal Error", JOptionPane.ERROR_MESSAGE);
        }
    } 
    
    private void ClearFields(){
        
        cInvolvement.select(0);
        txtLastname.setText("");
        txtFirstname.setText("");
        txtMI.setText("");
        txtSuffix.setText("");
        txtAge.setText("");
        cCivilStatus.select(0);
        txtOccupation.setText("");
        txtNativeOf.setText("");
        txtAddress.setText("");
        
        lblConfirm.setText("Confirm?");
        lblConfirm.setEnabled(false);
        
    }
    
    private boolean CorrectDateFormat(String d){
        boolean correct = false;
        String date = d;
        
        String mtmp = "";
        int dtmp = 0;
        int ytmp = 0;
        
        int yearrange = 10;
        
        for(int i=0;i<12;i++){
            if(date.toLowerCase().startsWith(curdate.getStringMonth(i).substring(0, 3).toLowerCase())){
                
                if(date.toLowerCase().startsWith(curdate.getStringMonth(i).toLowerCase())){
                    mtmp = curdate.getStringMonth(i);
                }else if(date.toLowerCase().startsWith(curdate.getStringMonth(i).substring(0, 3).toLowerCase() + " ")){
                    mtmp = curdate.getStringMonth(i).substring(0, 3);
                }
                
                if(!mtmp.equals("")){
                    
                    lblStatus.setText("Correct Month!");
                    lblStatus.setForeground(Color.WHITE);
                    
                    if(date.length()>mtmp.length()+2){
                    
                        int ds = 0;
                        int de = 0;

                        String dtest;

                        if(date.contains(",")){
                            ds = mtmp.length() + 1;
                            de = date.indexOf(',');
                        }else{
                            ds = mtmp.length() + 1;
                            de = date.length();
                        }

                        dtest = date.substring(ds, de).trim();

                        if(in.isNumeric(dtest)){
                            dtmp = Integer.parseInt(dtest);
                        }

                        if(dtmp>0&&dtmp<32){

                            lblStatus.setText("Correct Date!");
                            lblStatus.setForeground(Color.WHITE);

                            String ytest = date.substring(date.length()-4, date.length());

                            if(in.isNumeric(ytest)){
                                ytmp = Integer.parseInt(ytest);

                                lblStatus.setText("Correct Year!");
                                lblStatus.setForeground(Color.WHITE);

                                if(ytmp>curdate.getYear()-yearrange&&ytmp<=curdate.getYear()){
                                    
                                    dater = new NewCalendar(mtmp, 1, ytmp);
                                    
                                    int datelimit = dater.getDaysOfMonth();
                                    
                                    if(dtmp<=datelimit){
                                        
                                        dater = new NewCalendar(mtmp, dtmp, ytmp);
                                                
                                        newdatesave = dater.getFullDate();

                                        lblStatus.setText("Correct format! Date is usable!");
                                        lblStatus.setForeground(Color.WHITE);
                                        
                                        correct = true;
                                        break;
                                    }else{
                                        
                                        lblStatus.setText("Date doesn't exist!");
                                        lblStatus.setForeground(Color.YELLOW);
                                    }
                                    
                                    
                                }else{
                                    lblStatus.setText("Exceed date range!");
                                    lblStatus.setForeground(Color.YELLOW);
                                }

                            }else{
                                lblStatus.setText("Wrong Year!");
                                lblStatus.setForeground(Color.YELLOW);
                            }


                        }else{
                            lblStatus.setText("Wrong Date!");
                            lblStatus.setForeground(Color.YELLOW);
                        }
                    }
                    
                    
                    
                }else{
                    lblStatus.setText("Wrong Month!");
                    lblStatus.setForeground(Color.YELLOW);
                }
                
            }/*else{
                lblNewStatus.setText("Unidentified date!");
                lblNewStatus.setForeground(Color.RED);
                //write(curdate.getStringMonth(i).substring(0, 3));
            }*/
        }
        
        
        
        int mm = 0;
        int dd = 0;
        int yy = 0;
        
        String mstr;
        String dstr;
        String ystr;
        
        String[] seperator = {"/", "-", "\\"};
        
        for(int i=0;i<seperator.length;i++){
            if(date.contains(seperator[i])){
                
                int pos = 0;
                
                int strt = 0;
                int end = 0;
                
                end = date.indexOf(seperator[i], pos);
                
                mstr = date.substring(strt, end);
                
                if(!mstr.equals("")){
                    if(in.isNumeric(mstr)){
                        mm = Integer.parseInt(mstr);

                        if(mm>0&&mm<=12){

                            mm--;

                            lblStatus.setText("Correct Month!");
                            lblStatus.setForeground(Color.WHITE);

                            pos = end + 1;

                            if(date.substring(pos).contains(seperator[i])){
                                end = date.indexOf(seperator[i], pos);
                            }else{
                                end = date.length();
                            }

                            strt = pos;

                            dstr = date.substring(strt, end);

                            if(!dstr.equals("")){
                                if(in.isNumeric(dstr)){
                                    dd = Integer.parseInt(dstr);

                                    if(dd>0&&dd<=31){

                                        lblStatus.setText("Correct Date!");
                                        lblStatus.setForeground(Color.WHITE);

                                        
                                        
                                        if(date.substring(pos).contains(seperator[i])){
                                            pos = end + 1;
                                        }else{
                                            pos = date.length();
                                            //pos = date.indexOf(seperator[i], 0);
                                        }

                                        strt = pos;

                                        end = date.length();

                                        ystr = date.substring(strt, end);

                                        if(!ystr.equals("")){
                                            if(in.isNumeric(ystr)){

                                                String shortyear;
                                                int ytest;

                                                for(int i2=0;i2<=yearrange;i2++){

                                                    ytest = curdate.getYear() - (yearrange - i2); 

                                                    shortyear = Integer.toString(ytest).substring(2, Integer.toString(ytest).length());

                                                    if(ystr.equals(shortyear)||
                                                            ystr.equals(Integer.toString(ytest))){

                                                        yy = ytest;

                                                        lblStatus.setText("Correct Year!");
                                                        lblStatus.setForeground(Color.WHITE);

                                                        dater = new NewCalendar(mm, 1, yy);

                                                        int datelimit = dater.getDaysOfMonth();

                                                        if(dd<=datelimit){

                                                            dater = new NewCalendar(mm, dd, yy);
                                                            newdatesave = dater.getFullDate();

                                                            lblStatus.setText("Correct format! Date is usable!");
                                                            lblStatus.setForeground(Color.WHITE);

                                                            correct = true;
                                                            break;
                                                        }else{
                                                            lblStatus.setText("Date doesn't exist!");
                                                            lblStatus.setForeground(Color.YELLOW);
                                                        }
                                                        
                                                    }else{
                                                        lblStatus.setText("Exceed date range!");
                                                        lblStatus.setForeground(Color.YELLOW);
                                                    }
                                                }

                                            }else{
                                                lblStatus.setText("Unidentified Year!");
                                                lblStatus.setForeground(Color.YELLOW);
                                            }
                                        }

                                    }else{
                                        lblStatus.setText("Wrong Date!");
                                        lblStatus.setForeground(Color.YELLOW);
                                    }

                                }else{
                                    lblStatus.setText("Unidentified Date!");
                                    lblStatus.setForeground(Color.YELLOW);
                                }
                            }


                        }else{
                            lblStatus.setText("Wrong Month!");
                            lblStatus.setForeground(Color.YELLOW);
                        }
                    }else{
                        lblStatus.setText("Unidentified Month!");
                        lblStatus.setForeground(Color.YELLOW);
                    }
                }
            }
        }
        
        if(correct){
            if(dater.rawCalendar().compareTo(curdate.rawCalendar())<0){
                lblStatus.setText("Usable date!");
                lblStatus.setForeground(Color.WHITE);
                correct = true;
            }else{
                lblStatus.setText("Advance date!");
                lblStatus.setForeground(Color.YELLOW);
                
                correct = false;
            }
        }
        
        dateformatted = dater.getYear() + " " + String.format("%02d", dater.getDate()) + " " + String.format("%02d", dater.getIntMonth(dater.getStringMonth()));
        
        return correct;
        
    }
    
    private void GroupProgressBar(){
        progressstep = new JLabel[]{lblProgress1, lblProgress2, lblProgress3, lblProgress4};
    }
    
    private void ExtraHide(){
        hiddens = new JSeparator[]{sEntryNo, sDate, sTime, sLastname, sFirstname,
        sMI, sSuffix, sAge, sOccupation, sNativeOf, sAddress};
        
        for (JSeparator hidden : hiddens) {
            hidden.setVisible(false);
        }
        
        
    }

    
    private void extraComponents(){
        this.setLocationRelativeTo(null);
        
        cInvolvement.add("--Select--");
        cInvolvement.add("Complainant");
        cInvolvement.add("Victim");
        cInvolvement.add("Suspect");
        cInvolvement.add("Witness");
        
        cCivilStatus.add("--Select--");
        cCivilStatus.add("Single");
        cCivilStatus.add("Married");
        cCivilStatus.add("Lived-in");
        cCivilStatus.add("Divorced");
        cCivilStatus.add("Annulled");
        cCivilStatus.add("Widowed");
        
        GroupProgressBar();
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
        jPanel2 = new javax.swing.JPanel();
        cInvolvement = new java.awt.Choice();
        jLabel5 = new javax.swing.JLabel();
        txtLastname = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtFirstname = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtMI = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtSuffix = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtAge = new javax.swing.JTextField();
        cCivilStatus = new java.awt.Choice();
        jLabel10 = new javax.swing.JLabel();
        txtOccupation = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtNativeOf = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        lblNext = new javax.swing.JLabel();
        lblAddMore = new javax.swing.JLabel();
        lblProgress2 = new javax.swing.JLabel();
        lblProgress1 = new javax.swing.JLabel();
        lblProgress4 = new javax.swing.JLabel();
        lblProgress3 = new javax.swing.JLabel();
        lblConfirm = new javax.swing.JLabel();
        sLastname = new javax.swing.JSeparator();
        sFirstname = new javax.swing.JSeparator();
        sMI = new javax.swing.JSeparator();
        sSuffix = new javax.swing.JSeparator();
        sAge = new javax.swing.JSeparator();
        sNativeOf = new javax.swing.JSeparator();
        sOccupation = new javax.swing.JSeparator();
        sAddress = new javax.swing.JSeparator();
        lblStatus = new javax.swing.JLabel();

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
        jLabel4.setText("General Info");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Entry No.:");

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

        txtDateEntry.setBackground(new java.awt.Color(0, 0, 0));
        txtDateEntry.setForeground(new java.awt.Color(255, 255, 255));
        txtDateEntry.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDateEntry.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        txtDateEntry.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        txtDateEntry.setSelectionColor(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Time:");

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
                        .addGap(221, 221, 221))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMinimized, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblExit, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
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

        jPanel2.setBackground(new java.awt.Color(102, 0, 0));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cInvolvement.setBackground(new java.awt.Color(102, 0, 0));
        cInvolvement.setForeground(new java.awt.Color(255, 255, 255));
        cInvolvement.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cInvolvementItemStateChanged(evt);
            }
        });
        jPanel2.add(cInvolvement, new org.netbeans.lib.awtextra.AbsoluteConstraints(36, 60, 100, -1));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 10)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Lastname:");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 80, -1, -1));

        txtLastname.setBackground(new java.awt.Color(102, 0, 0));
        txtLastname.setForeground(new java.awt.Color(255, 255, 255));
        txtLastname.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtLastname.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        txtLastname.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        txtLastname.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtLastname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtLastnameKeyReleased(evt);
            }
        });
        jPanel2.add(txtLastname, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 60, 130, 20));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 10)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Firstname:");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 80, -1, -1));

        txtFirstname.setBackground(new java.awt.Color(102, 0, 0));
        txtFirstname.setForeground(new java.awt.Color(255, 255, 255));
        txtFirstname.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFirstname.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        txtFirstname.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        txtFirstname.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtFirstname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFirstnameKeyReleased(evt);
            }
        });
        jPanel2.add(txtFirstname, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 60, 130, 20));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 10)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("MI:");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 80, -1, -1));

        txtMI.setBackground(new java.awt.Color(102, 0, 0));
        txtMI.setForeground(new java.awt.Color(255, 255, 255));
        txtMI.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtMI.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        txtMI.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        txtMI.setSelectionColor(new java.awt.Color(255, 255, 255));
        jPanel2.add(txtMI, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 60, 40, 20));

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 10)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Suffix:");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 80, -1, -1));

        txtSuffix.setBackground(new java.awt.Color(102, 0, 0));
        txtSuffix.setForeground(new java.awt.Color(255, 255, 255));
        txtSuffix.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSuffix.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        txtSuffix.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        txtSuffix.setSelectionColor(new java.awt.Color(255, 255, 255));
        jPanel2.add(txtSuffix, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 60, 40, 20));

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Fullname:");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 60, -1, -1));

        txtAge.setBackground(new java.awt.Color(102, 0, 0));
        txtAge.setForeground(new java.awt.Color(255, 255, 255));
        txtAge.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtAge.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        txtAge.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        txtAge.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtAge.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAgeKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAgeKeyTyped(evt);
            }
        });
        jPanel2.add(txtAge, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 110, 50, 20));

        cCivilStatus.setBackground(new java.awt.Color(102, 0, 0));
        cCivilStatus.setForeground(new java.awt.Color(255, 255, 255));
        cCivilStatus.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cCivilStatusItemStateChanged(evt);
            }
        });
        jPanel2.add(cCivilStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 110, 110, -1));

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Age:");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, -1, -1));

        txtOccupation.setBackground(new java.awt.Color(102, 0, 0));
        txtOccupation.setForeground(new java.awt.Color(255, 255, 255));
        txtOccupation.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtOccupation.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        txtOccupation.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        txtOccupation.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtOccupation.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtOccupationKeyReleased(evt);
            }
        });
        jPanel2.add(txtOccupation, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 150, 190, 20));

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Occupation:");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, -1, -1));

        txtNativeOf.setBackground(new java.awt.Color(102, 0, 0));
        txtNativeOf.setForeground(new java.awt.Color(255, 255, 255));
        txtNativeOf.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNativeOf.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        txtNativeOf.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        txtNativeOf.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtNativeOf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNativeOfKeyReleased(evt);
            }
        });
        jPanel2.add(txtNativeOf, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 110, 100, 20));

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Native Of:");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 110, -1, -1));

        jLabel16.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Address:");
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, -1, -1));

        txtAddress.setBackground(new java.awt.Color(102, 0, 0));
        txtAddress.setForeground(new java.awt.Color(255, 255, 255));
        txtAddress.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtAddress.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        txtAddress.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        txtAddress.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtAddress.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAddressKeyReleased(evt);
            }
        });
        jPanel2.add(txtAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 190, 490, 20));

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Civil Status:");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 110, -1, -1));

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
        jPanel2.add(lblNext, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 310, 120, 30));

        lblAddMore.setBackground(new java.awt.Color(0, 0, 0));
        lblAddMore.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lblAddMore.setForeground(new java.awt.Color(255, 255, 255));
        lblAddMore.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAddMore.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ico/plus_32.png"))); // NOI18N
        lblAddMore.setText("Add More");
        lblAddMore.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        lblAddMore.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblAddMore.setEnabled(false);
        lblAddMore.setOpaque(true);
        jPanel2.add(lblAddMore, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 310, 130, 30));

        lblProgress2.setBackground(new java.awt.Color(0, 0, 0));
        lblProgress2.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        lblProgress2.setForeground(new java.awt.Color(255, 255, 255));
        lblProgress2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblProgress2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ico/check-green_32.png"))); // NOI18N
        lblProgress2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        lblProgress2.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ico/check_32.png"))); // NOI18N
        lblProgress2.setEnabled(false);
        lblProgress2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel2.add(lblProgress2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 270, 130, 20));

        lblProgress1.setBackground(new java.awt.Color(0, 0, 0));
        lblProgress1.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        lblProgress1.setForeground(new java.awt.Color(255, 255, 255));
        lblProgress1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblProgress1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ico/check-green_32.png"))); // NOI18N
        lblProgress1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        lblProgress1.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ico/check_32.png"))); // NOI18N
        lblProgress1.setEnabled(false);
        lblProgress1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel2.add(lblProgress1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 270, 130, 20));

        lblProgress4.setBackground(new java.awt.Color(0, 0, 0));
        lblProgress4.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        lblProgress4.setForeground(new java.awt.Color(255, 255, 255));
        lblProgress4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblProgress4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ico/check-green_32.png"))); // NOI18N
        lblProgress4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        lblProgress4.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ico/check_32.png"))); // NOI18N
        lblProgress4.setEnabled(false);
        lblProgress4.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel2.add(lblProgress4, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 270, 130, 20));

        lblProgress3.setBackground(new java.awt.Color(0, 0, 0));
        lblProgress3.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        lblProgress3.setForeground(new java.awt.Color(255, 255, 255));
        lblProgress3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblProgress3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ico/check-green_32.png"))); // NOI18N
        lblProgress3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        lblProgress3.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ico/check_32.png"))); // NOI18N
        lblProgress3.setEnabled(false);
        lblProgress3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel2.add(lblProgress3, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 270, 130, 20));

        lblConfirm.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lblConfirm.setForeground(new java.awt.Color(255, 255, 255));
        lblConfirm.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblConfirm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ico/save_32.png"))); // NOI18N
        lblConfirm.setText("Confirm?");
        lblConfirm.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        lblConfirm.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblConfirm.setEnabled(false);
        lblConfirm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblConfirmMousePressed(evt);
            }
        });
        jPanel2.add(lblConfirm, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, 180, 30));

        sLastname.setBackground(new java.awt.Color(102, 0, 0));
        sLastname.setForeground(new java.awt.Color(102, 0, 0));
        sLastname.setOpaque(true);
        jPanel2.add(sLastname, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 80, 130, -1));

        sFirstname.setBackground(new java.awt.Color(102, 0, 0));
        sFirstname.setForeground(new java.awt.Color(102, 0, 0));
        sFirstname.setOpaque(true);
        jPanel2.add(sFirstname, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 80, 130, -1));

        sMI.setBackground(new java.awt.Color(102, 0, 0));
        sMI.setForeground(new java.awt.Color(102, 0, 0));
        sMI.setOpaque(true);
        jPanel2.add(sMI, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 80, 40, -1));

        sSuffix.setBackground(new java.awt.Color(102, 0, 0));
        sSuffix.setForeground(new java.awt.Color(102, 0, 0));
        sSuffix.setOpaque(true);
        jPanel2.add(sSuffix, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 80, 40, -1));

        sAge.setBackground(new java.awt.Color(102, 0, 0));
        sAge.setForeground(new java.awt.Color(102, 0, 0));
        sAge.setOpaque(true);
        jPanel2.add(sAge, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 130, 50, -1));

        sNativeOf.setBackground(new java.awt.Color(102, 0, 0));
        sNativeOf.setForeground(new java.awt.Color(102, 0, 0));
        sNativeOf.setOpaque(true);
        jPanel2.add(sNativeOf, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 130, 100, -1));

        sOccupation.setBackground(new java.awt.Color(102, 0, 0));
        sOccupation.setForeground(new java.awt.Color(102, 0, 0));
        sOccupation.setOpaque(true);
        jPanel2.add(sOccupation, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 170, 190, -1));

        sAddress.setBackground(new java.awt.Color(102, 0, 0));
        sAddress.setForeground(new java.awt.Color(102, 0, 0));
        sAddress.setOpaque(true);
        jPanel2.add(sAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 210, 490, -1));

        lblStatus.setFont(new java.awt.Font("Times New Roman", 0, 10)); // NOI18N
        lblStatus.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(lblStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 260, 20));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 630, 380));

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

    private void cInvolvementItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cInvolvementItemStateChanged
        ValidForConfirmation();
        if(cInvolvement.getSelectedIndex()!=0){
            lblConfirm.setText("Confirm " + cInvolvement.getSelectedItem() + "?");
        }else{
            lblConfirm.setText("Confirm?");
        }
        
    }//GEN-LAST:event_cInvolvementItemStateChanged

    private void txtLastnameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLastnameKeyReleased
        ValidForConfirmation();
    }//GEN-LAST:event_txtLastnameKeyReleased

    private void txtFirstnameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFirstnameKeyReleased
        ValidForConfirmation();
    }//GEN-LAST:event_txtFirstnameKeyReleased

    private void txtAgeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAgeKeyReleased
        ValidForConfirmation();
    }//GEN-LAST:event_txtAgeKeyReleased

    private void txtNativeOfKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNativeOfKeyReleased
        ValidForConfirmation();
    }//GEN-LAST:event_txtNativeOfKeyReleased

    private void txtOccupationKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOccupationKeyReleased
        ValidForConfirmation();
    }//GEN-LAST:event_txtOccupationKeyReleased

    private void txtAddressKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAddressKeyReleased
        ValidForConfirmation();
    }//GEN-LAST:event_txtAddressKeyReleased

    private void lblConfirmMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblConfirmMousePressed
        
        if(lblConfirm.isEnabled()){
            
            String confirmation = "Confirm listing of the  person as involved person? \nOnce confirmed, it can't be reversed.";
            
            int confirmentry = JOptionPane.YES_OPTION;
            
            if(firstsave){
                
                confirmentry = JOptionPane.showConfirmDialog(null, "Confirm " + txtEntryNo.getText() + " as new Entry No.? \nThis will be the Entry No. for other related info.", "Confirm new Entry No.", JOptionPane.YES_NO_OPTION);

                if (confirmentry == JOptionPane.YES_OPTION) {
                    txtEntryNo.setEditable(false);
                    txtDateEntry.setEditable(false);
                    txtTimeEntry.setEditable(false);

                    firstsave = false;
                }
            }
            
            int confirminvolved = JOptionPane.showConfirmDialog(null, confirmation, "Confirm recordings", JOptionPane.YES_NO_OPTION);

            if (confirmentry==JOptionPane.YES_OPTION&&confirminvolved==JOptionPane.YES_OPTION) {
                
                RecordPersonInvolved();
                
                
                String involvement = cInvolvement.getSelectedItem();
        
                if(involvement.equals("Complainant")){
                    complainantfilled = true;
                }else if(involvement.equals("Victim")){
                    victimfilled = true;
                }else if(involvement.equals("Suspect")){
                    suspectfilled = true;
                }else if(involvement.equals("Witness")){
                    witnessfilled = true;
                }
                CheckProgress();
                ClearFields();
            }
        }
    }//GEN-LAST:event_lblConfirmMousePressed

    private void txtAgeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAgeKeyTyped
        char c = evt.getKeyChar();
        
        if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE)) || c==KeyEvent.VK_DELETE){
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtAgeKeyTyped

    private void cCivilStatusItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cCivilStatusItemStateChanged
        ValidForConfirmation();
    }//GEN-LAST:event_cCivilStatusItemStateChanged

    private void lblNextMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNextMousePressed
        if(lblNext.isEnabled()){
            new CaseFacts(entryno, dateentry, dateformatted, timeentry).setVisible(true);
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
            java.util.logging.Logger.getLogger(GeneralInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GeneralInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GeneralInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GeneralInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GeneralInfo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Choice cCivilStatus;
    private java.awt.Choice cInvolvement;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
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
    private javax.swing.JLabel lblAddMore;
    private javax.swing.JLabel lblConfirm;
    private javax.swing.JLabel lblExit;
    private javax.swing.JLabel lblMinimized;
    private javax.swing.JLabel lblNext;
    private javax.swing.JLabel lblProgress1;
    private javax.swing.JLabel lblProgress2;
    private javax.swing.JLabel lblProgress3;
    private javax.swing.JLabel lblProgress4;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JSeparator sAddress;
    private javax.swing.JSeparator sAge;
    private javax.swing.JSeparator sDate;
    private javax.swing.JSeparator sEntryNo;
    private javax.swing.JSeparator sFirstname;
    private javax.swing.JSeparator sLastname;
    private javax.swing.JSeparator sMI;
    private javax.swing.JSeparator sNativeOf;
    private javax.swing.JSeparator sOccupation;
    private javax.swing.JSeparator sSuffix;
    private javax.swing.JSeparator sTime;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtAge;
    private javax.swing.JTextField txtDateEntry;
    private javax.swing.JTextField txtEntryNo;
    private javax.swing.JTextField txtFirstname;
    private javax.swing.JTextField txtLastname;
    private javax.swing.JTextField txtMI;
    private javax.swing.JTextField txtNativeOf;
    private javax.swing.JTextField txtOccupation;
    private javax.swing.JTextField txtSuffix;
    private javax.swing.JTextField txtTimeEntry;
    // End of variables declaration//GEN-END:variables
}
