/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package jFrame;

import java.sql.*;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Anand
 */
public class Forget1 extends javax.swing.JFrame {

    /**
     * Creates new form ForgetPassword
     */
    public Forget1() {
        initComponents();
        Section();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    private void Section() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("book.png")));
    }

    public void update() {
        String nam = name1.getText();
        String npass = txt_npass.getText();
        String cpass = txt_cpass.getText();

        // Check if new password and confirm password match
        if (!npass.equals(cpass)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!");
            return;
        }

        Connection con = null;
        PreparedStatement prt = null;
        try {
            con = DbConnection.getConnection();
            prt = con.prepareStatement("UPDATE users SET password_hash = ? WHERE name = ?");

            // Hash the new password before updating
            String hashedPassword = BCrypt.hashpw(cpass, BCrypt.gensalt(12));

            prt.setString(1, hashedPassword);
            prt.setString(2, nam);
            int rowsUpdate = prt.executeUpdate();

            if (rowsUpdate > 0) {
                JOptionPane.showMessageDialog(this, "Your Password Updated");
                LoginPage page = new LoginPage();
                page.setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error updating password. User not found.");
            }
            prt.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txt_npass = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btn_update = new javax.swing.JButton();
        btn_exit = new javax.swing.JButton();
        txt_cpass = new javax.swing.JPasswordField();
        view = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setMinimumSize(new java.awt.Dimension(400, 306));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel2.add(txt_npass, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, 240, -1));

        jLabel2.setText("New Password:");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 90, -1));

        jLabel3.setText("Conform Password:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 110, -1));

        btn_update.setText("Update");
        btn_update.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                btn_updateMouseMoved(evt);
            }
        });
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });
        btn_update.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btn_updateKeyPressed(evt);
            }
        });
        jPanel2.add(btn_update, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 150, 70, 27));

        btn_exit.setText("Exit");
        btn_exit.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                btn_exitMouseMoved(evt);
            }
        });
        btn_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_exitActionPerformed(evt);
            }
        });
        jPanel2.add(btn_exit, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 150, 70, 27));

        txt_cpass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_cpassKeyPressed(evt);
            }
        });
        jPanel2.add(txt_cpass, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 90, 240, -1));

        view.setText("View Password");
        view.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                viewMouseMoved(evt);
            }
        });
        view.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewActionPerformed(evt);
            }
        });
        jPanel2.add(view, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 120, -1, -1));

        name1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jPanel2.add(name1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, 210, 30));

        jLabel4.setText("Username:");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 30));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 63, 420, 190));

        jPanel3.setBackground(new java.awt.Color(241, 196, 15));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Sitka Display", 1, 25)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Forget Password");
        jLabel1.setAlignmentY(0.0F);
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 5, 420, 50));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 420, 60));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 420, 250));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
        update();
    }//GEN-LAST:event_btn_updateActionPerformed

    private void btn_updateMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_updateMouseMoved
        btn_update.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // TODO add your handling code here:
    }//GEN-LAST:event_btn_updateMouseMoved

    private void btn_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_exitActionPerformed
        LoginPage page = new LoginPage();
        page.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_exitActionPerformed

    private void btn_exitMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_exitMouseMoved
        btn_exit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_btn_exitMouseMoved

    private void viewMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewMouseMoved
        view.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_viewMouseMoved

    private void viewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewActionPerformed
        if (view.isSelected()) {
            txt_cpass.setEchoChar((char) 0);
        } else {
            txt_cpass.setEchoChar('*');
        }   // TODO add your handling code here:
    }//GEN-LAST:event_viewActionPerformed

    private void txt_cpassKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cpassKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            update();
        }
    }//GEN-LAST:event_txt_cpassKeyPressed

    private void btn_updateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_updateKeyPressed
        update();        // TODO add your handling code here:
    }//GEN-LAST:event_btn_updateKeyPressed

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
            java.util.logging.Logger.getLogger(Forget1.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Forget1.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Forget1.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Forget1.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Forget1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_exit;
    private javax.swing.JButton btn_update;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    public static final javax.swing.JLabel name1 = new javax.swing.JLabel();
    private javax.swing.JPasswordField txt_cpass;
    private javax.swing.JTextField txt_npass;
    private javax.swing.JCheckBox view;
    // End of variables declaration//GEN-END:variables
}
