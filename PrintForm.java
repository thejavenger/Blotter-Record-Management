

package blotterrecordmanagement;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.Timer;


public class PrintForm extends javax.swing.JFrame implements Printable, Pageable{

    Timer timer = new Timer(1000, new Listener());
    int secondlater;
    
    String username;
    
    public PrintForm() {
        initComponents();
        
        Calendar rightNow = Calendar.getInstance();
        rightNow.add(Calendar.SECOND, 3);
        
        secondlater = rightNow.get(Calendar.SECOND);
        timer.start();
    }
    
    public PrintForm(String theuser, String getname, String getdailyrate, String getpayperiod, 
    String getregular, String getovertime, String getholiday, 
    String getregularrate, String getovertimerate, String getholidayrate, 
    String getregularamount, String getovertimeamount, String getholidayamount, 
    String getregulartotal, String getadjustmentfrom, String getadjustmentup, 
    String getsss, String getpagibig, String getphilhealth, String gettax, 
    String getregulardeductions, String getotherdeductionsfrom, String getotherdeductions,
    String getgrossincome, String gettotaldeduction, String getnetpay){
        
        initComponents();
        
        username = theuser;
        
        Calendar rightNow = Calendar.getInstance();
        rightNow.add(Calendar.SECOND, 3);
        
        secondlater = rightNow.get(Calendar.SECOND);
        timer.start();
        
        if(getregular.equals("")){
            getregular = "0";
        }
        
        if(getovertime.equals("")){
            getovertime = "0";
        }
        
        if(getholiday.equals("")){
            getholiday = "0";
        }
        
        if(getadjustmentfrom.equals("")||getadjustmentup.equals("")){
            getadjustmentfrom = "None";
            getadjustmentup = "0";
        }
        
        if(getotherdeductionsfrom.equals("")||getotherdeductions.equals("")){
            getotherdeductionsfrom = "None";
            getotherdeductions = "0";
        }
        
        

        lblName.setText(getname);
        
        
        
    }

    @Override
    public int getNumberOfPages() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PageFormat getPageFormat(int pageIndex) throws IndexOutOfBoundsException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Printable getPrintable(int pageIndex) throws IndexOutOfBoundsException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public class Listener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Calendar rightNow = Calendar.getInstance();
            
            int second = rightNow.get(Calendar.SECOND);
            
            if(second==secondlater){
                timer.stop();
                //JOptionPane.showMessageDialog(null, "Five second later!!!", null, JOptionPane.ERROR_MESSAGE);
                printMe();
            }
            
        }
        
    }

     @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        Graphics2D g2 = (Graphics2D) graphics;
        g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
            
        /*int yc = (int) (pageFormat.getImageableY() + 
              	   pageFormat.getImageableHeight()/2);
    		g2.drawString("Widgets, Inc.", 72, yc+36);*/
        
    	Font  f = new Font("Monospaced",Font.PLAIN,12);
    	g2.setFont (f);
	paint (g2);
	    	
        return Printable.PAGE_EXISTS;
    }
    
    public void printMe(){
        PrinterJob printJob = PrinterJob.getPrinterJob();
	/*PageFormat format = printJob.defaultPage();
        
        Book book = new Book();
        
        book.append(this, format);*/
  		    // Get and change default page format settings if necessary.
     
        //printJob.setPageable( book);
        
        printJob.setPrintable( this);
            if (printJob.printDialog()) {
                try {
                    printJob.print();  

                } catch (Exception PrintException) {
                    PrintException.printStackTrace();
                }
                printJob.cancel();	
            }
        
            printJob.cancel();
    }
    
    
    public void getPrintData(String getname, String getdailyrate, String getpayperiod, 
    String getregular, String getovertime, String getholiday, String getadjustmentfrom, 
    String getadjustmentup, String getsss, String getpagibig, String getphilhealth, String gettax, 
    String getregulardeductions, String getotherdeductionsfrom, String getotherdeductions,
    String getgrossincome, String gettotaldeduction, String getnetpay){
        
        
        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel17 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        jLabel101 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowDeactivated(java.awt.event.WindowEvent evt) {
                formWindowDeactivated(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jLabel1.setText("Employee Name");

        lblName.setFont(new java.awt.Font("Tahoma", 1, 9)); // NOI18N
        lblName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblName.setText("jLabel4");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(129, 129, 129)
                        .addComponent(jLabel1)))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel17.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 20, -1, 49));

        jLabel101.setBackground(new java.awt.Color(255, 255, 255));
        jLabel101.setText("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
        jPanel17.add(jLabel101, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 355, 565, -1));

        getContentPane().add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 590, 720));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        //new Menu(username).setVisible(true);
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        //this.dispose();
    }//GEN-LAST:event_formWindowClosing

    private void formWindowDeactivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowDeactivated
        
    }//GEN-LAST:event_formWindowDeactivated

    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged
        
    }//GEN-LAST:event_formWindowStateChanged

    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JLabel lblName;
    // End of variables declaration//GEN-END:variables

   
}
