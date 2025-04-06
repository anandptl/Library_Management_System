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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author anand
 */
public class FinePage extends javax.swing.JFrame {

    /**
     * Creates new form FinePage
     */
    DefaultTableModel model;

    public FinePage() {
        initComponents();
        FinedataTable();
        Section();
    }

    public void FinedataTable() {
        try {
            Connection con = DbConnection.getConnection();
            String sql = "SELECT bi.*, s.student_name AS student_name, s.student_id, b.book_name "
                    + "FROM book_issues bi "
                    + "JOIN students s ON bi.student_id = s.student_id "
                    + "JOIN books b ON bi.isbn = b.isbn ";

            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String StudentId = rs.getString("student_id");
                String StudentName = rs.getString("student_name");
                String AccessionNo = rs.getString("accession_no");
                String BookName = rs.getString("book_name");
                String Fine = rs.getString("fine");
                String Status = rs.getString("status");

                Object[] obj = {StudentId, StudentName, AccessionNo, BookName, Fine, Status};

                model = (DefaultTableModel) tbl_user1.getModel();
                model.addRow(obj);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    public void clearTable() {
        DefaultTableModel model = (DefaultTableModel) tbl_user1.getModel();
        model.setRowCount(0);
    }

//    to fetch the record using data field..
    public void searchbyid() {

        String Studentid = txt_StudentId.getText();

        try {
            Connection con = DbConnection.getConnection();
            String sql = "SELECT bi.*, s.student_name AS student_name, s.student_id,b.book_name "
                    + "FROM book_issues bi "
                    + "JOIN students s ON bi.student_id = s.student_id "
                    + "JOIN books b ON bi.isbn = b.isbn "
                    + "WHERE bi.student_id = ?";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, Studentid);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
//                 String EnrollmentNo = rs.getString("enrollment_no");
                String StudentName = rs.getString("student_name");
                String AccessionNo = rs.getString("accession_no");
                String BookName = rs.getString("book_name");
                String Fine = rs.getString("fine");
                String Status = rs.getString("status");

                Object[] obj = {Studentid, StudentName, AccessionNo, BookName, Fine, Status};
                model = (DefaultTableModel) tbl_user1.getModel();
                model.addRow(obj);
            }
            rs.close();
            pst.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

//    sreach by the student Name
    public void Pay() {

        String Studentid = txt_StudentId.getText();
        String AccessionNo = txt_AccessionNo.getText();
        double Fine = Double.parseDouble(txt_Ammount.getText());
        double Ammount = Double.parseDouble(txt_Ammount1.getText());
        double Tfine = Fine - Ammount;
        try {

            Connection con = DbConnection.getConnection();
            String Sql = "UPDATE book_issues SET fine = ? WHERE student_id = ? AND accession_no = ?";

            PreparedStatement pst = con.prepareStatement(Sql);

            pst.setDouble(1, Tfine);
            pst.setString(2, Studentid);
            pst.setString(3, AccessionNo);

            int rowCount = pst.executeUpdate();

            if (rowCount > 0) {
                JOptionPane.showMessageDialog(this, "Amount Paid Successfully");
                new FinePage().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Amount not Paid ");
            }

//            // Close resources
            pst.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

//Page Image ...
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

        kGradientPanel1 = new keeptoo.KGradientPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_user1 = new rojeru_san.complementos.RSTableMetro();
        jPanel2 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        rSMaterialButtonCircle3 = new rojerusan.RSMaterialButtonCircle();
        txt_StudentId = new app.bolivia.swing.JCTextField();
        jLabel12 = new javax.swing.JLabel();
        txt_Ammount = new app.bolivia.swing.JCTextField();
        rSMaterialButtonCircle2 = new necesario.RSMaterialButtonCircle();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        rSMaterialButtonCircle1 = new necesario.RSMaterialButtonCircle();
        txt_AccessionNo = new app.bolivia.swing.JCTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txt_Ammount1 = new app.bolivia.swing.JCTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        kGradientPanel1.setInheritsPopupMenu(true);
        kGradientPanel1.setkEndColor(new java.awt.Color(204, 204, 204));
        kGradientPanel1.setkGradientFocus(120);
        kGradientPanel1.setkStartColor(new java.awt.Color(0, 255, 255));
        kGradientPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_user1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Enrollment No", "Student Name", "Accession No", "Book Name", "Fine", "Status"
            }
        ));
        tbl_user1.setColorBackgoundHead(new java.awt.Color(102, 102, 255));
        tbl_user1.setColorFilasBackgound2(new java.awt.Color(255, 255, 255));
        tbl_user1.setDropMode(javax.swing.DropMode.ON);
        tbl_user1.setFocusable(false);
        tbl_user1.setInheritsPopupMenu(true);
        tbl_user1.setRowHeight(40);
        tbl_user1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_user1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_user1);

        kGradientPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 270, 1170, 430));

        jPanel2.setBackground(new java.awt.Color(102, 102, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 80, -1, -1));

        rSMaterialButtonCircle3.setText("<<");
        rSMaterialButtonCircle3.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        rSMaterialButtonCircle3.setPreferredSize(new java.awt.Dimension(80, 60));
        rSMaterialButtonCircle3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSMaterialButtonCircle3MouseClicked(evt);
            }
        });
        jPanel2.add(rSMaterialButtonCircle3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 70, 60));

        txt_StudentId.setBackground(new java.awt.Color(102, 102, 255));
        txt_StudentId.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));
        txt_StudentId.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_StudentId.setPlaceholder("Search By Student Id");
        txt_StudentId.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                txt_StudentIdMouseMoved(evt);
            }
        });
        txt_StudentId.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_StudentIdFocusLost(evt);
            }
        });
        jPanel2.add(txt_StudentId, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 170, 190, 40));

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("Sylfaen", 0, 20)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Fine :");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 190, 50, 30));

        txt_Ammount.setBackground(new java.awt.Color(102, 102, 255));
        txt_Ammount.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));
        txt_Ammount.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_Ammount.setPlaceholder("Amount..");
        txt_Ammount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_AmmountKeyPressed(evt);
            }
        });
        jPanel2.add(txt_Ammount, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 170, 140, 40));

        rSMaterialButtonCircle2.setBackground(new java.awt.Color(255, 51, 51));
        rSMaterialButtonCircle2.setText("Pay");
        rSMaterialButtonCircle2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonCircle2ActionPerformed(evt);
            }
        });
        rSMaterialButtonCircle2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rSMaterialButtonCircle2KeyPressed(evt);
            }
        });
        jPanel2.add(rSMaterialButtonCircle2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 170, 170, 50));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 90, 430, 5));

        jLabel1.setFont(new java.awt.Font("Sylfaen", 1, 50)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Student Fine List");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 50, -1, -1));

        rSMaterialButtonCircle1.setText("X");
        rSMaterialButtonCircle1.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        rSMaterialButtonCircle1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSMaterialButtonCircle1MouseClicked(evt);
            }
        });
        jPanel2.add(rSMaterialButtonCircle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1230, 0, 70, 60));

        txt_AccessionNo.setBackground(new java.awt.Color(102, 102, 255));
        txt_AccessionNo.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));
        txt_AccessionNo.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_AccessionNo.setPlaceholder("Accession No..");
        jPanel2.add(txt_AccessionNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 170, 150, 40));

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Sylfaen", 0, 20)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Accession No :");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 190, 130, 30));

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setFont(new java.awt.Font("Sylfaen", 0, 20)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Fine :");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 190, 50, 30));

        txt_Ammount1.setBackground(new java.awt.Color(102, 102, 255));
        txt_Ammount1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));
        txt_Ammount1.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_Ammount1.setPlaceholder("Amount..");
        jPanel2.add(txt_Ammount1, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 170, 140, 40));

        kGradientPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1300, 240));

        getContentPane().add(kGradientPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1300, 704));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void rSMaterialButtonCircle3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle3MouseClicked
        HomePage home = new HomePage();
        home.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_rSMaterialButtonCircle3MouseClicked

    private void rSMaterialButtonCircle1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle1MouseClicked
        HomePage home = new HomePage();
        home.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_rSMaterialButtonCircle1MouseClicked

    private void jButton2MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseMoved
        jButton2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2MouseMoved

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // printer function..
        try {
            tbl_user1.print(JTable.PrintMode.FIT_WIDTH);
        } catch (Exception e) {
            System.out.print(e);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton2KeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_P) {
            try {
                tbl_user1.print(JTable.PrintMode.FIT_WIDTH);
            } catch (Exception e) {
                System.out.print(e);
            }
        }// TODO add your handling code here:
    }//GEN-LAST:event_jButton2KeyPressed

    private void rSMaterialButtonCircle2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle2ActionPerformed
        // pay money  ....
        if (txt_Ammount.getText() != null) {
            Pay();
            clearTable();
            searchbyid();
            txt_Ammount.setText("");
            txt_Ammount1.setText("");
        }
    }//GEN-LAST:event_rSMaterialButtonCircle2ActionPerformed

    private void txt_StudentIdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_StudentIdFocusLost
        if (!txt_StudentId.getText().equals("")) {
            clearTable();
            searchbyid();
            txt_Ammount.setText(" ");
            txt_Ammount1.setText(" ");
        }
    }//GEN-LAST:event_txt_StudentIdFocusLost

    private void txt_StudentIdMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_StudentIdMouseMoved
        txt_StudentId.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_txt_StudentIdMouseMoved

    private void tbl_user1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_user1MouseClicked
        int rowNo = tbl_user1.getSelectedRow();
        TableModel model = tbl_user1.getModel();
        txt_StudentId.setText(model.getValueAt(rowNo, 0).toString());
        txt_AccessionNo.setText(model.getValueAt(rowNo, 2).toString());
        txt_Ammount.setText(model.getValueAt(rowNo, 4).toString());

    }//GEN-LAST:event_tbl_user1MouseClicked

    private void txt_AmmountKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_AmmountKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txt_Ammount.getText() != null) {
                Pay();
                clearTable();
                searchbyid();
            } 
        }    // TODO add your handling code here:
    }//GEN-LAST:event_txt_AmmountKeyPressed

    private void rSMaterialButtonCircle2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle2KeyPressed
// pay money  ....
        if (txt_Ammount.getText() != null) {
            Pay();
            clearTable();
            searchbyid();
            txt_Ammount.setText("");
            txt_Ammount1.setText("");
        }         // TODO add your handling code here:
    }//GEN-LAST:event_rSMaterialButtonCircle2KeyPressed

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
            java.util.logging.Logger.getLogger(FinePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FinePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FinePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FinePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FinePage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private keeptoo.KGradientPanel kGradientPanel1;
    private necesario.RSMaterialButtonCircle rSMaterialButtonCircle1;
    private necesario.RSMaterialButtonCircle rSMaterialButtonCircle2;
    private rojerusan.RSMaterialButtonCircle rSMaterialButtonCircle3;
    private rojeru_san.complementos.RSTableMetro tbl_user1;
    private app.bolivia.swing.JCTextField txt_AccessionNo;
    private app.bolivia.swing.JCTextField txt_Ammount;
    private app.bolivia.swing.JCTextField txt_Ammount1;
    private app.bolivia.swing.JCTextField txt_StudentId;
    // End of variables declaration//GEN-END:variables
}
