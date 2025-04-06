/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package jFrame;

import com.toedter.calendar.JDateChooser;
import java.sql.*;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Anand
 */
public class ManageStudents extends javax.swing.JFrame {

    /**
     * Creates new form ManageStudents
     */
    DefaultTableModel model;

    Color mouseEnterColor = new Color(0, 112, 192);
    Color mouseExitColor = new Color(255, 51, 51);

    public ManageStudents() {
        initComponents();
        setStudentDetailsToTable();
        Section();
        NameS.setText(Session.getUserName());
        showDate();
    }
    //to set the student details into the table 

    public void setStudentDetailsToTable() {

        Connection con = null;
        Statement st = null;

        try {
            con = DbConnection.getConnection();
            st = con.createStatement();
            String sql = "select * from students";
            ResultSet rs = st.executeQuery(sql);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            while (rs.next()) {
                String studentName = rs.getString("student_name");
                String phone = rs.getString("phone_number");
                String Address = rs.getString("address");
                String aadhaar = rs.getString("aadhaar");
                String enrollDate = rs.getLong("enroll_date") > 0 ? sdf.format(new Date(rs.getLong("enroll_date"))) : "N/A";
                String expiryDate = rs.getLong("expiry_date") > 0 ? sdf.format(new Date(rs.getLong("expiry_date"))) : "N/A";

                Object[] obj = {studentName, phone, Address, aadhaar, enrollDate, expiryDate};
                model = (DefaultTableModel) tbl_studentTable.getModel();
                model.addRow(obj);
            }
            con.close();
            st.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

//   Show Date on the JDateChooser .....
    public void showDate() {
        JDateChooser jDateChooser = new JDateChooser();
        java.util.Date currentDate = new java.util.Date();
        Date_Enroll.setDate(currentDate);
    }

    public void showDate1() {
        if (Date_Expiry.getDate() != null) {

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(Date_Expiry.getDate());

            int m = Integer.parseInt(jTextMember.getText());
            calendar.add(Calendar.MONTH, m);
            Date_Expiry.setDate(calendar.getTime());
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(Date_Enroll.getDate());

            int m = Integer.parseInt(jTextMember.getText());
            calendar.add(Calendar.MONTH, m);
            Date_Expiry.setDate(calendar.getTime());
        }
    }

// Student Search by Aadhaar.....
    public void searchByE() {
        String name = txt_sbn.getText();

        try {
            Connection con = DbConnection.getConnection();
            String sql = "select * from students where aadhaar = ?";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, name);
//            pst.setString(2, Branch);

            ResultSet rs = pst.executeQuery();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            clearTable();

            while (rs.next()) {
                String studentName = rs.getString("student_name");
                String phone = rs.getString("phone_number");
                String Address = rs.getString("address");
                String aadhaar = rs.getString("aadhaar");
                String enrollDate = rs.getLong("enroll_date") > 0 ? sdf.format(new Date(rs.getLong("enroll_date"))) : "N/A";
                String expiryDate = rs.getLong("expiry_date") > 0 ? sdf.format(new Date(rs.getLong("expiry_date"))) : "N/A";

                Object[] obj = {studentName, phone, Address, aadhaar, enrollDate, expiryDate};
                model = (DefaultTableModel) tbl_studentTable.getModel();
                model.addRow(obj);
            }
            rs.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    //check duplicate Student
    public boolean checkDuplicateStu() {
        String Aadhaar = Txt_Aadhaar.getText();
        boolean isExist = false;
        try {
            Connection con = DbConnection.getConnection();

            PreparedStatement prt = con.prepareStatement("select * from students where aadhaar = ?");
            prt.setString(1, Aadhaar);

            ResultSet rs = (ResultSet) prt.executeQuery();
            if (rs.next()) {
                isExist = true;

            } else {
                isExist = false;
            }
            con.close();
            prt.close();
        } catch (Exception e) {
            System.out.print(e);
        }
        return isExist;

    }

    //to add student to student_details table
    public boolean addStudent() {
        boolean isAdded = false;
        String StuName = txt_stu_name.getText();
        String Stuphone = txt_phone.getText();
        String Address = txt_Address.getText();
        String Aadhaar = Txt_Aadhaar.getText();

        java.util.Date Enroll = Date_Enroll.getDate();
        java.sql.Date sEnroll = new java.sql.Date(Enroll.getTime());

        java.util.Date Expiry = Date_Expiry.getDate();
        java.sql.Date sExpiry = new java.sql.Date(Expiry.getTime());

        Connection con = null;
        PreparedStatement pst = null;

        // Check if enrollment number is empty
        if (StuName.isEmpty() || Stuphone.isEmpty() || Aadhaar.isEmpty()) {
            JOptionPane.showMessageDialog(this, "TextField cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;  // Stop execution if enrollment is empty
        } else {
            try {
                con = (Connection) DbConnection.getConnection();
                String sql = "insert into students(student_name, phone_number, aadhaar, enroll_date, expiry_date, address, issue_book_no) values(?,?,?,?,?,?,?)";
                pst = con.prepareStatement(sql);

                pst.setString(1, StuName);
                pst.setString(2, Stuphone);
                pst.setString(3, Aadhaar);
                pst.setDate(4, sEnroll);
                pst.setDate(5, sExpiry);
                pst.setString(6, Address);
                pst.setString(7, "0");

                int rowCount = pst.executeUpdate();
                if (rowCount > 0) {
                    isAdded = true;
                } else {
                    isAdded = false;
                }
                con.close();
                pst.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e);
            }
        }
        return isAdded;
    }

    // to update student details
    public boolean updateStudent() {
        boolean isUpdated = false;
        String StuName = txt_stu_name.getText();
        String Stuphone = txt_phone.getText();
        String Address = txt_Address.getText();
        String Aadhaar = Txt_Aadhaar.getText();

        java.util.Date Enroll = Date_Enroll.getDate();
        java.sql.Date sEnroll = new java.sql.Date(Enroll.getTime());

        java.util.Date Expiry = Date_Expiry.getDate();
        java.sql.Date sExpiry = new java.sql.Date(Expiry.getTime());

        Connection con = null;
        PreparedStatement pst = null;

        try {
            con = (Connection) DbConnection.getConnection();
            String sql = "update  students set student_name = ?, phone_number = ?, expiry_date = ?, address =? where aadhaar = ?";
            pst = con.prepareStatement(sql);

            pst.setString(1, StuName);
            pst.setString(2, Stuphone);
            pst.setDate(3, sExpiry);
            pst.setString(4, Address);
            pst.setString(5, Aadhaar);

            int rowCount = pst.executeUpdate();
            if (rowCount > 0) {
                isUpdated = true;
            } else {
                isUpdated = false;
            }
            con.close();
            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
        return isUpdated;
    }

//method to delete student detail
    public boolean deleteStudent() {
        boolean isDeleted = false;
        String Name = txt_stu_name.getText();
        String Aadhaar = Txt_Aadhaar.getText();

        Connection con = null;
        PreparedStatement pst = null;

        try {
            con = (Connection) DbConnection.getConnection();
            String sql = "delete from students  where student_name =? and aadhaar = ? ";
            pst = con.prepareStatement(sql);

            pst.setString(1, Name);
            pst.setString(2, Aadhaar);

            int rowCount = pst.executeUpdate();
            if (rowCount > 0) {
                isDeleted = true;
            } else {
                isDeleted = false;
            }
            con.close();
            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
        return isDeleted;
    }
//method to clear table

    public void clearTable() {
        DefaultTableModel model = (DefaultTableModel) tbl_studentTable.getModel();
        model.setRowCount(0);
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
        rSMaterialButtonCircle1 = new rojerusan.RSMaterialButtonCircle();
        txt_stu_name = new app.bolivia.swing.JCTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txt_phone = new app.bolivia.swing.JCTextField();
        jLabel7 = new javax.swing.JLabel();
        rSMaterialButtonCircle2 = new rojerusan.RSMaterialButtonCircle();
        rSMaterialButtonCircle3 = new rojerusan.RSMaterialButtonCircle();
        rSMaterialButtonCircle4 = new rojerusan.RSMaterialButtonCircle();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txt_Address = new app.bolivia.swing.JCTextField();
        Txt_Aadhaar = new app.bolivia.swing.JCTextField();
        jLabel13 = new javax.swing.JLabel();
        Date_Enroll = new com.toedter.calendar.JDateChooser();
        Date_Expiry = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jTextMember = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_studentTable = new rojeru_san.complementos.RSTableMetro();
        kGradientPanel1 = new keeptoo.KGradientPanel();
        rSMaterialButtonCircle5 = new rojerusan.RSMaterialButtonCircle();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        txt_sbn = new app.bolivia.swing.JCTextField();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(102, 102, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(500, 770));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(102, 102, 255));

        rSMaterialButtonCircle1.setText("<<");
        rSMaterialButtonCircle1.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        rSMaterialButtonCircle1.setPreferredSize(new java.awt.Dimension(80, 60));
        rSMaterialButtonCircle1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSMaterialButtonCircle1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(rSMaterialButtonCircle1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(rSMaterialButtonCircle1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        txt_stu_name.setBackground(new java.awt.Color(102, 102, 255));
        txt_stu_name.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));
        txt_stu_name.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_stu_name.setPlaceholder("Enter Student Name....");
        txt_stu_name.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_stu_nameKeyTyped(evt);
            }
        });
        jPanel1.add(txt_stu_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 120, 270, 40));

        jLabel6.setBackground(new java.awt.Color(255, 51, 51));
        jLabel6.setFont(new java.awt.Font("Sylfaen", 0, 17)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/AddNewBookIcons/icons8_Collaborator_Male_26px.png"))); // NOI18N
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, 40, 50));

        jLabel9.setBackground(new java.awt.Color(255, 51, 51));
        jLabel9.setFont(new java.awt.Font("Sylfaen", 0, 20)); // NOI18N
        jLabel9.setText("Enter Student Name");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 90, 270, 40));

        txt_phone.setBackground(new java.awt.Color(102, 102, 255));
        txt_phone.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));
        txt_phone.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_phone.setPlaceholder("Enter Phone Number");
        txt_phone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_phoneKeyTyped(evt);
            }
        });
        jPanel1.add(txt_phone, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 200, 270, 40));

        jLabel7.setBackground(new java.awt.Color(255, 51, 51));
        jLabel7.setFont(new java.awt.Font("Sylfaen", 0, 17)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/AddNewBookIcons/calendar-24.png"))); // NOI18N
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 530, 40, 50));

        rSMaterialButtonCircle2.setBackground(new java.awt.Color(255, 51, 51));
        rSMaterialButtonCircle2.setText("DELETE");
        rSMaterialButtonCircle2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSMaterialButtonCircle2MouseClicked(evt);
            }
        });
        rSMaterialButtonCircle2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rSMaterialButtonCircle2KeyPressed(evt);
            }
        });
        jPanel1.add(rSMaterialButtonCircle2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 630, 140, 70));

        rSMaterialButtonCircle3.setBackground(new java.awt.Color(255, 51, 51));
        rSMaterialButtonCircle3.setText("ADD");
        rSMaterialButtonCircle3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSMaterialButtonCircle3MouseClicked(evt);
            }
        });
        rSMaterialButtonCircle3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rSMaterialButtonCircle3KeyPressed(evt);
            }
        });
        jPanel1.add(rSMaterialButtonCircle3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 630, 140, 70));

        rSMaterialButtonCircle4.setBackground(new java.awt.Color(255, 51, 51));
        rSMaterialButtonCircle4.setText("UPDATE");
        rSMaterialButtonCircle4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSMaterialButtonCircle4MouseClicked(evt);
            }
        });
        rSMaterialButtonCircle4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rSMaterialButtonCircle4KeyPressed(evt);
            }
        });
        jPanel1.add(rSMaterialButtonCircle4, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 630, 140, 70));

        jLabel11.setBackground(new java.awt.Color(255, 51, 51));
        jLabel11.setFont(new java.awt.Font("Sylfaen", 0, 20)); // NOI18N
        jLabel11.setText("Enter Phone Number");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 170, 270, 40));

        jLabel12.setBackground(new java.awt.Color(255, 51, 51));
        jLabel12.setFont(new java.awt.Font("Sylfaen", 0, 20)); // NOI18N
        jLabel12.setText("Enter Address");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 250, 270, 40));

        txt_Address.setBackground(new java.awt.Color(102, 102, 255));
        txt_Address.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));
        txt_Address.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_Address.setPlaceholder("Enter Address......");
        jPanel1.add(txt_Address, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 280, 270, 40));

        Txt_Aadhaar.setBackground(new java.awt.Color(102, 102, 255));
        Txt_Aadhaar.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));
        Txt_Aadhaar.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        Txt_Aadhaar.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        Txt_Aadhaar.setPlaceholder("Enter Aadhaar & Cheak Again");
        Txt_Aadhaar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                Txt_AadhaarKeyTyped(evt);
            }
        });
        jPanel1.add(Txt_Aadhaar, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 360, 270, 40));

        jLabel13.setBackground(new java.awt.Color(255, 51, 51));
        jLabel13.setFont(new java.awt.Font("Sylfaen", 0, 20)); // NOI18N
        jLabel13.setText("Expiry Date");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 520, 120, 40));

        Date_Enroll.setBackground(new java.awt.Color(102, 102, 255));
        Date_Enroll.setDateFormatString("dd/MM/yyyy");
        Date_Enroll.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jPanel1.add(Date_Enroll, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 440, 270, 30));

        Date_Expiry.setBackground(new java.awt.Color(102, 102, 255));
        Date_Expiry.setDateFormatString("dd/MM/yyyy");
        Date_Expiry.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jPanel1.add(Date_Expiry, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 550, 270, 30));

        jLabel14.setBackground(new java.awt.Color(255, 51, 51));
        jLabel14.setFont(new java.awt.Font("Sylfaen", 0, 20)); // NOI18N
        jLabel14.setText("Enter Aadhaar Number");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 330, 270, 40));

        jLabel15.setBackground(new java.awt.Color(255, 51, 51));
        jLabel15.setFont(new java.awt.Font("Sylfaen", 0, 20)); // NOI18N
        jLabel15.setText("Membership Months");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 480, 180, 40));

        jLabel8.setBackground(new java.awt.Color(255, 51, 51));
        jLabel8.setFont(new java.awt.Font("Sylfaen", 0, 17)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons/icons8_Google_Mobile_50px.png"))); // NOI18N
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 50, 50));

        jLabel10.setBackground(new java.awt.Color(255, 51, 51));
        jLabel10.setFont(new java.awt.Font("Sylfaen", 0, 17)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/icons8_Home_26px_2.png"))); // NOI18N
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 260, 40, 50));

        jLabel16.setBackground(new java.awt.Color(255, 51, 51));
        jLabel16.setFont(new java.awt.Font("Sylfaen", 0, 17)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/AddNewBookIcons/Unice.png"))); // NOI18N
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 340, 40, 50));

        jLabel17.setBackground(new java.awt.Color(255, 51, 51));
        jLabel17.setFont(new java.awt.Font("Sylfaen", 0, 17)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/AddNewBookIcons/date-to-24.png"))); // NOI18N
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 420, 40, 50));

        jLabel18.setBackground(new java.awt.Color(255, 51, 51));
        jLabel18.setFont(new java.awt.Font("Sylfaen", 0, 20)); // NOI18N
        jLabel18.setText("Enroll Date");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 410, 120, 40));

        jTextMember.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextMember.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextMemberFocusLost(evt);
            }
        });
        jPanel1.add(jTextMember, new org.netbeans.lib.awtextra.AbsoluteConstraints(293, 482, 80, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 470, -1));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(900, 770));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_studentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Phone No", "Address", "Aadhaar", "Enroll Date", "Expiry Date"
            }
        ));
        tbl_studentTable.setColorBackgoundHead(new java.awt.Color(102, 102, 255));
        tbl_studentTable.setDropMode(javax.swing.DropMode.ON);
        tbl_studentTable.setFocusable(false);
        tbl_studentTable.setInheritsPopupMenu(true);
        tbl_studentTable.setRowHeight(40);
        tbl_studentTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_studentTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbl_studentTable);

        jPanel3.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 170, 830, 570));

        kGradientPanel1.setInheritsPopupMenu(true);
        kGradientPanel1.setkEndColor(new java.awt.Color(255, 255, 255));
        kGradientPanel1.setkGradientFocus(250);
        kGradientPanel1.setkStartColor(new java.awt.Color(51, 255, 255));
        kGradientPanel1.setMinimumSize(new java.awt.Dimension(930, 60));
        kGradientPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        rSMaterialButtonCircle5.setText("X");
        rSMaterialButtonCircle5.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        rSMaterialButtonCircle5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSMaterialButtonCircle5MouseClicked(evt);
            }
        });
        kGradientPanel1.add(rSMaterialButtonCircle5, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 0, 80, 69));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/male_user_50px.png"))); // NOI18N
        kGradientPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 10, 50, -1));

        jLabel1.setFont(new java.awt.Font("Serif", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 51));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/AddNewBookIcons/icons8_Student_Male_100px.png"))); // NOI18N
        jLabel1.setText("MANAGE STUDENTS");
        kGradientPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 0, 430, -1));

        jPanel5.setBackground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 340, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        kGradientPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 70, 340, 3));

        NameS.setFont(new java.awt.Font("Times New Roman", 1, 22)); // NOI18N
        kGradientPanel1.add(NameS, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 20, 130, 30));

        txt_sbn.setBackground(new java.awt.Color(51, 255, 255));
        txt_sbn.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(0, 0, 0)));
        txt_sbn.setAlignmentY(1.0F);
        txt_sbn.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txt_sbn.setPlaceholder("Search By Aadhaar...");
        txt_sbn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_sbnKeyTyped(evt);
            }
        });
        kGradientPanel1.add(txt_sbn, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 120, 150, -1));

        jButton2.setBackground(new java.awt.Color(51, 255, 255));
        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton2.setText("Search");
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
        kGradientPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 120, -1, 30));

        jPanel3.add(kGradientPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 770));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(468, 0, 930, -1));

        setSize(new java.awt.Dimension(1400, 770));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void rSMaterialButtonCircle1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle1MouseClicked
        HomePage home = new HomePage();
        home.setVisible(true);
        dispose();
    }//GEN-LAST:event_rSMaterialButtonCircle1MouseClicked

    private void tbl_studentTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_studentTableMouseClicked
        int rowNo = tbl_studentTable.getSelectedRow();
        TableModel model = tbl_studentTable.getModel();

        txt_stu_name.setText(model.getValueAt(rowNo, 0).toString());
        txt_phone.setText(model.getValueAt(rowNo, 1).toString());
        txt_Address.setText(model.getValueAt(rowNo, 2).toString());
        Txt_Aadhaar.setText(model.getValueAt(rowNo, 3).toString());
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//            java.util.Date enrollDate = sdf.parse(model.getValueAt(rowNo, 4).toString());
            java.util.Date expiryDate = sdf.parse(model.getValueAt(rowNo, 5).toString());

//            Date_Enroll.setDate(enrollDate);
            Date_Expiry.setDate(expiryDate);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_tbl_studentTableMouseClicked

    private void rSMaterialButtonCircle3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle3MouseClicked
        int confirmed = JOptionPane.showConfirmDialog(null, "Aadhaar: " + Txt_Aadhaar.getText(), "Check Your Aadhaar Number", JOptionPane.YES_NO_OPTION);
        if (checkDuplicateStu() == false) {
            if (confirmed == JOptionPane.YES_OPTION) {
                if (addStudent() == true) {
//                JOptionPane.showMessageDialog(this, "Student Added");
                    clearTable();
                    setStudentDetailsToTable();
                    ManageStudents manage = new ManageStudents();
                    manage.setVisible(true);
                    this.dispose();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Username Already Exist ");
        }
    }//GEN-LAST:event_rSMaterialButtonCircle3MouseClicked

    private void rSMaterialButtonCircle4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle4MouseClicked
        if (updateStudent() == true) {
            JOptionPane.showMessageDialog(this, "Student Updated");
            clearTable();
            setStudentDetailsToTable();
            ManageStudents manage = new ManageStudents();
            manage.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Student Updation Failed");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_rSMaterialButtonCircle4MouseClicked

    private void rSMaterialButtonCircle2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle2MouseClicked
        if (deleteStudent() == true) {
            JOptionPane.showMessageDialog(this, "Student Deleted");
            clearTable();
            setStudentDetailsToTable();
            ManageStudents manage = new ManageStudents();
            manage.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Student Deletion Failed");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_rSMaterialButtonCircle2MouseClicked

    private void rSMaterialButtonCircle4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle4KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (updateStudent() == true) {
                JOptionPane.showMessageDialog(this, "Student Updated");
                clearTable();
                setStudentDetailsToTable();
            } else {
                JOptionPane.showMessageDialog(this, "Student Updation Failed");
            }
        }
    }//GEN-LAST:event_rSMaterialButtonCircle4KeyPressed

    private void rSMaterialButtonCircle2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (deleteStudent() == true) {
                JOptionPane.showMessageDialog(this, "Student Deleted");
                clearTable();
                setStudentDetailsToTable();
            } else {
                JOptionPane.showMessageDialog(this, "Student Deletion Failed");
            }
        }   // TODO add your handling code here:
    }//GEN-LAST:event_rSMaterialButtonCircle2KeyPressed

    private void rSMaterialButtonCircle5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle5MouseClicked
        HomePage home = new HomePage();
        home.setVisible(true);
        dispose();
    }//GEN-LAST:event_rSMaterialButtonCircle5MouseClicked

    private void txt_stu_nameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_stu_nameKeyTyped
        // only insert or get the text value
        if (!(Character.isAlphabetic(evt.getKeyChar()) || Character.isWhitespace(evt.getKeyChar()) || evt.getKeyChar() == '.')) {
            evt.consume();
        }
    }//GEN-LAST:event_txt_stu_nameKeyTyped

    private void txt_phoneKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_phoneKeyTyped
        if (!Character.isDigit(evt.getKeyChar()) || txt_phone.getText().length() >= 10) {
            evt.consume();
        }

    }//GEN-LAST:event_txt_phoneKeyTyped

    private void rSMaterialButtonCircle3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle3KeyPressed
        int confirmed = JOptionPane.showConfirmDialog(null, "Aadhaar: " + Txt_Aadhaar.getText(), "Check Your Aadhaar Number", JOptionPane.YES_NO_OPTION);
        if (checkDuplicateStu() == false) {
            if (confirmed == JOptionPane.YES_OPTION) {
                if (addStudent() == true) {
//                JOptionPane.showMessageDialog(this, "Student Added");
                    clearTable();
                    setStudentDetailsToTable();
                    ManageStudents manage = new ManageStudents();
                    manage.setVisible(true);
                    this.dispose();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Username Already Exist ");
        }
    }//GEN-LAST:event_rSMaterialButtonCircle3KeyPressed

    private void Txt_AadhaarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Txt_AadhaarKeyTyped
        // TODO add your handling code here:
        if (!Character.isDigit(evt.getKeyChar()) || Txt_Aadhaar.getText().length() >= 12) {
            evt.consume();
        }
    }//GEN-LAST:event_Txt_AadhaarKeyTyped

    private void jTextMemberFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextMemberFocusLost
        String date = jTextMember.getText();
        if (!date.isEmpty()) {
            showDate1();
        } else {
            JOptionPane.showMessageDialog(this, "Check the Membership Field");
        }
    }//GEN-LAST:event_jTextMemberFocusLost

    private void jButton2MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseMoved
        jButton2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));   // TODO add your handling code here:
    }//GEN-LAST:event_jButton2MouseMoved

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        searchByE();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton2KeyPressed
        searchByE(); // TODO add your handling code here:
    }//GEN-LAST:event_jButton2KeyPressed

    private void txt_sbnKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_sbnKeyTyped
        if (!Character.isDigit(evt.getKeyChar()) || Txt_Aadhaar.getText().length() >= 12) {
            evt.consume();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txt_sbnKeyTyped

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
            java.util.logging.Logger.getLogger(ManageStudents.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManageStudents.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManageStudents.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManageStudents.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ManageStudents().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser Date_Enroll;
    private com.toedter.calendar.JDateChooser Date_Expiry;
    public static final javax.swing.JLabel NameS = new javax.swing.JLabel();
    private app.bolivia.swing.JCTextField Txt_Aadhaar;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextMember;
    private keeptoo.KGradientPanel kGradientPanel1;
    private rojerusan.RSMaterialButtonCircle rSMaterialButtonCircle1;
    private rojerusan.RSMaterialButtonCircle rSMaterialButtonCircle2;
    private rojerusan.RSMaterialButtonCircle rSMaterialButtonCircle3;
    private rojerusan.RSMaterialButtonCircle rSMaterialButtonCircle4;
    private rojerusan.RSMaterialButtonCircle rSMaterialButtonCircle5;
    private rojeru_san.complementos.RSTableMetro tbl_studentTable;
    private app.bolivia.swing.JCTextField txt_Address;
    private app.bolivia.swing.JCTextField txt_phone;
    private app.bolivia.swing.JCTextField txt_sbn;
    private app.bolivia.swing.JCTextField txt_stu_name;
    // End of variables declaration//GEN-END:variables
}
