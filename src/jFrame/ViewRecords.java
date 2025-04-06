/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package jFrame;

import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Anand
 */
public class ViewRecords extends javax.swing.JFrame {

    /**
     * Creates new form ViewRecords
     */
    DefaultTableModel model;

    public ViewRecords() {
        initComponents();
        setIssueBookDetailsToTable();
        Section();

    }

    //to set the book details into the table 
    public void setIssueBookDetailsToTable() {
        DefaultTableModel model = (DefaultTableModel) tbl_bookDetails.getModel();
        model.setRowCount(0); // Clear table before adding new rows

        try {
            Connection con = DbConnection.getConnection();
            Statement st = con.createStatement();
            String sql = "SELECT bi.*, s.student_name AS student_name, s.student_id, b.book_name "
                    + "FROM book_issues bi "
                    + "JOIN students s ON bi.student_id = s.student_id "
                    + "JOIN books b ON bi.isbn = b.isbn ";

            ResultSet rs = st.executeQuery(sql);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // Format for the date

            while (rs.next()) {
                String StudentId = rs.getString("student_id");
                String AccessionNo = rs.getString("accession_no");
                String Isbn = rs.getString("isbn");
                String BookName = rs.getString("book_name");
                String StudentName = rs.getString("student_name");

                // Convert timestamps into formatted date strings (handling null values)
                String IssueDate = rs.getLong("issue_date") > 0 ? sdf.format(new Date(rs.getLong("issue_date"))) : "N/A";
                String DueDate = rs.getLong("due_date") > 0 ? sdf.format(new Date(rs.getLong("due_date"))) : "N/A";
                String ReturnDate = rs.getLong("return_date") > 0 ? sdf.format(new Date(rs.getLong("return_date"))) : "Not Returned";

                String Status = rs.getString("status");

                // Add formatted dates to the table
                Object[] obj = {StudentId, AccessionNo, Isbn, BookName, StudentName, IssueDate, DueDate, ReturnDate, Status};
                model = (DefaultTableModel) tbl_bookDetails.getModel();
                model.addRow(obj);
            }

            // Close resources
            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e);
            System.out.println(e);
        }
    }

    //method to clear table
    public void clearTable() {
        DefaultTableModel model = (DefaultTableModel) tbl_bookDetails.getModel();
        model.setRowCount(0);
    }

//    to fetch the record using data field..
    public void searchbyid() {

        String StudentId = Txt_StudentId.getText();

        try {
            Connection con = DbConnection.getConnection();
            String sql = "SELECT bi.*, s.student_name AS student_name, s.student_id, b.book_name "
                    + "FROM book_issues bi "
                    + "JOIN students s ON bi.student_id = s.student_id "
                    + "JOIN books b ON bi.isbn = b.isbn "
                    + "WHERE bi.student_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, StudentId);
            ResultSet rs = pst.executeQuery();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            while (rs.next()) {
                String acceasonNo = rs.getString("accession_no");
                String Isbn = rs.getString("isbn");
                String BookName = rs.getString("book_name");
                String StudentName = rs.getString("student_name");
                String IssueDate = rs.getLong("issue_date") > 0 ? sdf.format(new Date(rs.getLong("issue_date"))) : "N/A";
                String DueDate = rs.getLong("due_date") > 0 ? sdf.format(new Date(rs.getLong("due_date"))) : "N/A";
                String ReturnDate = rs.getLong("return_date") > 0 ? sdf.format(new Date(rs.getLong("return_date"))) : "Not Returned";
                String Status = rs.getString("status");

                Object[] obj = {StudentId, acceasonNo, Isbn, BookName, StudentName, IssueDate, DueDate, ReturnDate, Status};
                model = (DefaultTableModel) tbl_bookDetails.getModel();
                model.addRow(obj);

            }
            rs.close();
            pst.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e);
            System.out.println(e);
        }
    }

//    sreach by the student Name
    public void searchbyAccession() {

        String acceasonNo = txt_accessionNo.getText();
        try {
            Connection con = DbConnection.getConnection();
            String sql = "SELECT bi.*, s.student_name AS student_name, s.student_id, b.book_name "
                    + "FROM book_issues bi "
                    + "JOIN students s ON bi.student_id = s.student_id "
                    + "JOIN books b ON bi.isbn = b.isbn "
                    + "WHERE bi.accession_no = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, acceasonNo);
            ResultSet rs = pst.executeQuery();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            while (rs.next()) {
                String StudentId = rs.getString("student_id");
                String Isbn = rs.getString("isbn");
                String BookName = rs.getString("book_name");
                String StudentName = rs.getString("student_name");
                String IssueDate = rs.getLong("issue_date") > 0 ? sdf.format(new Date(rs.getLong("issue_date"))) : "N/A";
                String DueDate = rs.getLong("due_date") > 0 ? sdf.format(new Date(rs.getLong("due_date"))) : "N/A";
                String ReturnDate = rs.getLong("return_date") > 0 ? sdf.format(new Date(rs.getLong("return_date"))) : "Not Returned";
                String Status = rs.getString("status");

                Object[] obj = {StudentId, acceasonNo, Isbn, BookName, StudentName, IssueDate, DueDate, ReturnDate, Status};
                model = (DefaultTableModel) tbl_bookDetails.getModel();
                model.addRow(obj);

            }
            rs.close();
            pst.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error" + e);
        }
    }

    private void Section() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("book.png")));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        rSMaterialButtonCircle1 = new necesario.RSMaterialButtonCircle();
        rSMaterialButtonCircle5 = new rojerusan.RSMaterialButtonCircle();
        rSMaterialButtonCircle6 = new rojerusan.RSMaterialButtonCircle();
        jButton2 = new javax.swing.JButton();
        rSMaterialButtonCircle2 = new rojerusan.RSMaterialButtonCircle();
        Txt_StudentId = new app.bolivia.swing.JCTextField();
        txt_accessionNo = new app.bolivia.swing.JCTextField();
        jLabel13 = new javax.swing.JLabel();
        label1 = new java.awt.Label();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_bookDetails = new rojeru_san.complementos.RSTableMetro();
        kGradientPanel1 = new keeptoo.KGradientPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(1400, 770));
        jPanel1.setPreferredSize(new java.awt.Dimension(1400, 770));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(102, 102, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel22.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/AddNewBookIcons/icons8_Literature_100px_1.png"))); // NOI18N
        jLabel22.setText("View Book Records");
        jPanel2.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 20, -1, -1));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setPreferredSize(new java.awt.Dimension(340, 5));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 360, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 130, 360, 5));

        jLabel12.setBackground(new java.awt.Color(255, 51, 51));
        jLabel12.setFont(new java.awt.Font("Sylfaen", 0, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Accession No:");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 190, 150, 30));

        rSMaterialButtonCircle1.setBackground(new java.awt.Color(255, 51, 51));
        rSMaterialButtonCircle1.setText("SEARCH");
        rSMaterialButtonCircle1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonCircle1ActionPerformed(evt);
            }
        });
        rSMaterialButtonCircle1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rSMaterialButtonCircle1KeyPressed(evt);
            }
        });
        jPanel2.add(rSMaterialButtonCircle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 160, 170, 50));

        rSMaterialButtonCircle5.setBackground(new java.awt.Color(255, 51, 51));
        rSMaterialButtonCircle5.setText("<<");
        rSMaterialButtonCircle5.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        rSMaterialButtonCircle5.setPreferredSize(new java.awt.Dimension(80, 60));
        rSMaterialButtonCircle5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonCircle5ActionPerformed(evt);
            }
        });
        jPanel2.add(rSMaterialButtonCircle5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        rSMaterialButtonCircle6.setBackground(new java.awt.Color(255, 51, 51));
        rSMaterialButtonCircle6.setText("X");
        rSMaterialButtonCircle6.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        rSMaterialButtonCircle6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSMaterialButtonCircle6MouseClicked(evt);
            }
        });
        jPanel2.add(rSMaterialButtonCircle6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1320, 0, 80, 70));

        jButton2.setBackground(new java.awt.Color(102, 102, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons/Printer.png"))); // NOI18N
        jButton2.setBorder(null);
        jButton2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jButton2MouseMoved(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jButton2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton2KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jButton2KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jButton2KeyTyped(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 60, -1, -1));

        rSMaterialButtonCircle2.setBackground(new java.awt.Color(255, 51, 51));
        rSMaterialButtonCircle2.setText("refresh");
        rSMaterialButtonCircle2.setMaximumSize(new java.awt.Dimension(95, 28));
        rSMaterialButtonCircle2.setMinimumSize(new java.awt.Dimension(95, 28));
        rSMaterialButtonCircle2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSMaterialButtonCircle2MouseClicked(evt);
            }
        });
        rSMaterialButtonCircle2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonCircle2ActionPerformed(evt);
            }
        });
        jPanel2.add(rSMaterialButtonCircle2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1180, 160, 170, 50));

        Txt_StudentId.setBackground(new java.awt.Color(102, 102, 255));
        Txt_StudentId.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));
        Txt_StudentId.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        Txt_StudentId.setPlaceholder("Student Id: ........");
        Txt_StudentId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Txt_StudentIdKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                Txt_StudentIdKeyTyped(evt);
            }
        });
        jPanel2.add(Txt_StudentId, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 170, 240, 40));

        txt_accessionNo.setBackground(new java.awt.Color(102, 102, 255));
        txt_accessionNo.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));
        txt_accessionNo.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_accessionNo.setPlaceholder("Accession No...");
        txt_accessionNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_accessionNoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_accessionNoKeyTyped(evt);
            }
        });
        jPanel2.add(txt_accessionNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 170, 240, 40));

        jLabel13.setBackground(new java.awt.Color(255, 51, 51));
        jLabel13.setFont(new java.awt.Font("Sylfaen", 0, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Student Id:");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 190, 120, 30));

        label1.setFont(new java.awt.Font("Dialog", 3, 20)); // NOI18N
        label1.setForeground(new java.awt.Color(255, 51, 51));
        label1.setText("OR");
        jPanel2.add(label1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 180, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1400, 240));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_bookDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Accession No", "ISBN No", "Book Name", "Student Name", "Issue Date", "Due Date", "Return Date", "Status"
            }
        ));
        tbl_bookDetails.setColorBackgoundHead(new java.awt.Color(102, 102, 255));
        tbl_bookDetails.setColorFilasBackgound2(new java.awt.Color(255, 255, 255));
        tbl_bookDetails.setDropMode(javax.swing.DropMode.ON);
        tbl_bookDetails.setFocusable(false);
        tbl_bookDetails.setInheritsPopupMenu(true);
        tbl_bookDetails.setRowHeight(40);
        jScrollPane2.setViewportView(tbl_bookDetails);

        jPanel6.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 1320, 430));

        kGradientPanel1.setInheritsPopupMenu(true);
        kGradientPanel1.setkEndColor(new java.awt.Color(235, 235, 235));
        kGradientPanel1.setkGradientFocus(55);
        kGradientPanel1.setkStartColor(new java.awt.Color(0, 255, 255));
        kGradientPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel6.add(kGradientPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1400, 530));

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 1400, 530));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void rSMaterialButtonCircle5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle5ActionPerformed
        HomePage home = new HomePage();
        home.setVisible(true);
        dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_rSMaterialButtonCircle5ActionPerformed

    private void rSMaterialButtonCircle6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle6MouseClicked
        HomePage home = new HomePage();
        home.setVisible(true);
        dispose();
    }//GEN-LAST:event_rSMaterialButtonCircle6MouseClicked

    private void rSMaterialButtonCircle1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle1ActionPerformed
        // Search queryon the button ....
        if (Txt_StudentId.getText() != null || txt_accessionNo.getText() != null) {
            clearTable();
            searchbyid();
            searchbyAccession();
        } else {
            JOptionPane.showMessageDialog(this, "Please select anyone feild");
        }
    }//GEN-LAST:event_rSMaterialButtonCircle1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // printer function..
        try {
            tbl_bookDetails.print(JTable.PrintMode.FIT_WIDTH);
        } catch (Exception e) {
            System.out.print(e);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton2KeyReleased

    }//GEN-LAST:event_jButton2KeyReleased

    private void rSMaterialButtonCircle1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (Txt_StudentId.getText() != null || txt_accessionNo.getText() != null) {
                clearTable();
                searchbyid();
                searchbyAccession();
            } else {
                JOptionPane.showMessageDialog(this, "Please select anyone feild");
            }
        }
    }//GEN-LAST:event_rSMaterialButtonCircle1KeyPressed

    private void jButton2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton2KeyTyped

    }//GEN-LAST:event_jButton2KeyTyped

    private void jButton2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton2KeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_P) {
            try {
                tbl_bookDetails.print(JTable.PrintMode.FIT_WIDTH);
            } catch (Exception e) {
                System.out.print(e);
            }
        }// TODO add your handling code here:
    }//GEN-LAST:event_jButton2KeyPressed

    private void rSMaterialButtonCircle2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle2MouseClicked
        // Refresh button  method
        clearTable();
        setIssueBookDetailsToTable();
    }//GEN-LAST:event_rSMaterialButtonCircle2MouseClicked

    private void rSMaterialButtonCircle2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rSMaterialButtonCircle2ActionPerformed

    private void jButton2MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseMoved
        jButton2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2MouseMoved

    private void Txt_StudentIdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Txt_StudentIdKeyTyped
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_Txt_StudentIdKeyTyped

    private void txt_accessionNoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_accessionNoKeyTyped
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txt_accessionNoKeyTyped

    private void Txt_StudentIdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Txt_StudentIdKeyPressed
        // Search queryon the button .... 
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            clearTable();
            searchbyid();
        }// TODO add your handling code here:
    }//GEN-LAST:event_Txt_StudentIdKeyPressed

    private void txt_accessionNoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_accessionNoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            clearTable();
            searchbyAccession();
        }
    }//GEN-LAST:event_txt_accessionNoKeyPressed

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
            java.util.logging.Logger.getLogger(ViewRecords.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewRecords.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewRecords.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewRecords.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewRecords().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private app.bolivia.swing.JCTextField Txt_StudentId;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private keeptoo.KGradientPanel kGradientPanel1;
    private java.awt.Label label1;
    private necesario.RSMaterialButtonCircle rSMaterialButtonCircle1;
    private rojerusan.RSMaterialButtonCircle rSMaterialButtonCircle2;
    private rojerusan.RSMaterialButtonCircle rSMaterialButtonCircle5;
    private rojerusan.RSMaterialButtonCircle rSMaterialButtonCircle6;
    private rojeru_san.complementos.RSTableMetro tbl_bookDetails;
    private app.bolivia.swing.JCTextField txt_accessionNo;
    // End of variables declaration//GEN-END:variables
}
