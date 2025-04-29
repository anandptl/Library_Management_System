/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package jFrame;

import com.toedter.calendar.JDateChooser;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.Calendar;
import javax.swing.JOptionPane;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author anand
 */
public class RenewalBook extends javax.swing.JFrame {

    /**
     * Creates new form RenewalBook
     */
    public RenewalBook() {
        initComponents();
        Section();
        demo();
        demo1();
    }

//    get Student and book details
    public void getIssueBookDetails() {
        String AccessionNo = txt_accessionNo.getText();
        String StudentId = txt_StudentId.getText();

        try {
            Connection con = DbConnection.getConnection();
            String sql = "SELECT bi.*, s.student_name AS student_name, s.student_id, b.book_name "
                    + "FROM book_issues bi "
                    + "JOIN students s ON bi.student_id = s.student_id "
                    + "JOIN books b ON bi.isbn = b.isbn "
                    + "WHERE bi.accession_no = ? AND bi.student_id = ? AND bi.status = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, AccessionNo);
            pst.setString(2, StudentId);
            pst.setString(3, "pending");

            ResultSet rs = pst.executeQuery();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            if (rs.next()) {
                lbl_BookName.setText(rs.getString("book_name"));
                lbl_isbn.setText(rs.getString("isbn"));
                lbl_StudentName.setText(rs.getString("student_name"));

                long issueTimestamp = rs.getLong("issue_date");
                lbl_IssueDate.setText(issueTimestamp > 0 ? sdf.format(new Date(issueTimestamp)) : "N/A");

                long dueTimestamp = rs.getLong("due_date");
                lbl_DueDate.setText(dueTimestamp > 0 ? sdf.format(new Date(dueTimestamp)) : "N/A");
                
                lbl_old_fine.setText(rs.getString("fine"));
                
                if (!lbl_BookName.getText().isEmpty()) {
                    lbl_BookError.setText("");
                }
                showb();
                BookQuantity();
                fine();
                totalfine();
            } else {
                lbl_BookError.setText("No Record Found");

                lbl_BookName.setText("");
                lbl_isbn.setText("");
                lbl_StudentName.setText("");
                lbl_IssueDate.setText("");
                lbl_DueDate.setText("");
                lbl_bookQuantity.setText("");
                lbl_Booknum.setText("");
                lbl_old_fine.setText("");

            }
            rs.close();
            pst.close();
            con.close();
        } catch (Exception e) {
            System.out.print(e);
            JOptionPane.showMessageDialog(this, e);
        }
    }

    public void demo() {
        JDateChooser jDateChooser = new JDateChooser();

        // Get the current date
        java.util.Date currentDate = new java.util.Date();

        // Set the current date to the JDateChooser
        Date_Re_IssueDate.setDate(currentDate);
    }

    public void demo1() {
        java.util.Date selectedDate = Date_Re_IssueDate.getDate();
        if (selectedDate != null) {

            // Get the Calendar instance and set it to the selectesssdDate
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(selectedDate);

            // Add 15 days to the selectedDate
            calendar.add(Calendar.DAY_OF_MONTH, 15);

            // Set the new date to jDateChooser2
            Date_Re_DueDate.setDate(calendar.getTime());
        }
    }
//  how many books have the student     

    public void showb() {
        String enrollment = txt_StudentId.getText();
        try {
            Connection con = DbConnection.getConnection();
            PreparedStatement pst = con.prepareStatement("Select issue_book_no from students where student_id = ?");

            pst.setString(1, enrollment);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                lbl_Booknum.setText(rs.getString("issue_book_no"));
            } else {
                lbl_Booknum.setText("");
            }
            rs.close();
            pst.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(this, e);
        }
    }

    //    return the book 
    public boolean RenewalBook() {
        boolean IsRenewal = false;
        String AccessionNo = txt_accessionNo.getText();
        String StudentId = txt_StudentId.getText();

        java.util.Date uIssueDate = Date_Re_IssueDate.getDate();
        java.util.Date uDueDate = Date_Re_DueDate.getDate();
        double fine = Double.parseDouble(lbl_Totel_fine.getText());

        Long l1 = uIssueDate.getTime();
        Long l2 = uDueDate.getTime();

        java.sql.Date sIssueDate = new java.sql.Date(l1);
        java.sql.Date sDueDate = new java.sql.Date(l2);

        try {
            Connection con = (Connection) DbConnection.getConnection();
            String sql = "update book_issues set  issue_date = ?, due_date = ?, fine = ? where accession_no = ? AND student_id = ? And status = ?";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setDate(1, sIssueDate);
            pst.setDate(2, sDueDate);
            pst.setDouble(3, fine);
            pst.setString(4, AccessionNo);
            pst.setString(5, StudentId);
            pst.setString(6, "pending");

            int rowCount = pst.executeUpdate();
            if (rowCount > 0) {
                IsRenewal = true;
                JOptionPane.showMessageDialog(this, "Book Renewal Successfully");

            } else {
                IsRenewal = false;
                JOptionPane.showMessageDialog(this, "Book Renewal Unsuccessfully");
            }
//            rs.close();
            pst.close();
            con.close();
        } catch (Exception e) {
            System.out.print(e);
            JOptionPane.showMessageDialog(this, e);
        }
        return IsRenewal;
    }

//    If you not submit book time at time .... so you have fine 
    public void BookQuantity() {

        String Isbn_no = lbl_isbn.getText();
        try {
            Connection con = DbConnection.getConnection();
            String sql = "Select * from books where isbn = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, Isbn_no);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                lbl_bookQuantity.setText(rs.getString("quantity"));
            } else {
                lbl_bookQuantity.setText("");
            }
            rs.close();
            pst.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(this, e);
        }

    }

//    Fine calcuate ...
    public void fine() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            String date1Str = lbl_DueDate.getText();
            Date date1 = dateFormat.parse(date1Str);
            Date date2 = Date_Re_IssueDate.getDate();

            long diffInMillies = date2.getTime() - date1.getTime();
            double diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

            // Set fine (5.0 per day)
            double fine = Math.max(diffInDays * 5.0, 0.0);
            // Format fine to 2 decimal places
            DecimalFormat df = new DecimalFormat("#.##");
            String fineStr = df.format(fine);

            Today_fine_calculate.setText(fineStr);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

// Total Fine Calculation old + todaty.
    public void totalfine() {
        try {
            double F1 = Double.parseDouble(Today_fine_calculate.getText());
            double F2 = Double.parseDouble(lbl_old_fine.getText());

            double totalFine = F1 + F2;
            DecimalFormat df = new DecimalFormat("#.##");
            String totalFineStr = df.format(totalFine);

            lbl_Totel_fine.setText(totalFineStr);
        } catch (Exception e) {
//            System.out.println(e);
            JOptionPane.showMessageDialog(this, e);
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
        rSMaterialButtonCircle3 = new rojerusan.RSMaterialButtonCircle();
        rSMaterialButtonCircle5 = new rojerusan.RSMaterialButtonCircle();
        kGradientPanel1 = new keeptoo.KGradientPanel();
        btn_renewal = new necesario.RSMaterialButtonCircle();
        jPanel9 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        lbl_BookName = new javax.swing.JLabel();
        lbl_StudentName = new javax.swing.JLabel();
        lbl_IssueDate = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        lbl_BookError = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        lbl_DueDate = new javax.swing.JLabel();
        lbl_bookQuantity = new javax.swing.JLabel();
        jLabel1000101 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        lbl_isbn = new javax.swing.JLabel();
        lbl_Booknum = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txt_accessionNo = new app.bolivia.swing.JCTextField();
        txt_StudentId = new app.bolivia.swing.JCTextField();
        jLabel13 = new javax.swing.JLabel();
        Date_Re_IssueDate = new com.toedter.calendar.JDateChooser();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        Date_Re_DueDate = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        Today_fine_calculate = new javax.swing.JLabel();
        lbl_Totel_fine = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lbl_old_fine = new javax.swing.JLabel();
        btn_renewal1 = new necesario.RSMaterialButtonCircle();
        jPanel3 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        rSMaterialButtonCircle3.setText("<<");
        rSMaterialButtonCircle3.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        rSMaterialButtonCircle3.setPreferredSize(new java.awt.Dimension(80, 60));
        rSMaterialButtonCircle3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSMaterialButtonCircle3MouseClicked(evt);
            }
        });
        jPanel1.add(rSMaterialButtonCircle3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        rSMaterialButtonCircle5.setText("X");
        rSMaterialButtonCircle5.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        rSMaterialButtonCircle5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSMaterialButtonCircle5MouseClicked(evt);
            }
        });
        jPanel1.add(rSMaterialButtonCircle5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1320, 0, 80, 70));

        kGradientPanel1.setInheritsPopupMenu(true);
        kGradientPanel1.setkEndColor(new java.awt.Color(255, 255, 255));
        kGradientPanel1.setkGradientFocus(250);
        kGradientPanel1.setkStartColor(new java.awt.Color(51, 255, 255));
        kGradientPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_renewal.setBackground(new java.awt.Color(255, 51, 51));
        btn_renewal.setText("Renewal Book");
        btn_renewal.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btn_renewal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_renewalActionPerformed(evt);
            }
        });
        btn_renewal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btn_renewalKeyPressed(evt);
            }
        });
        kGradientPanel1.add(btn_renewal, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 580, 340, 70));

        jPanel9.setBackground(new java.awt.Color(255, 0, 51));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel22.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/AddNewBookIcons/icons8_Literature_100px_1.png"))); // NOI18N
        jLabel22.setText("BookDetails");
        jPanel9.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 100, -1, -1));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setPreferredSize(new java.awt.Dimension(340, 5));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 340, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );

        jPanel9.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 340, 5));

        jLabel24.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Book name :");
        jPanel9.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 110, -1));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Student Name :");
        jPanel9.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, 140, -1));

        lbl_BookName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_BookName.setForeground(new java.awt.Color(255, 255, 255));
        jPanel9.add(lbl_BookName, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 250, 190, 25));

        lbl_StudentName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_StudentName.setForeground(new java.awt.Color(255, 255, 255));
        jPanel9.add(lbl_StudentName, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 370, 190, 25));

        lbl_IssueDate.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_IssueDate.setForeground(new java.awt.Color(255, 255, 255));
        jPanel9.add(lbl_IssueDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 430, 190, 25));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Issue Date :");
        jPanel9.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 430, 110, -1));

        lbl_BookError.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        lbl_BookError.setForeground(new java.awt.Color(255, 204, 0));
        jPanel9.add(lbl_BookError, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 670, 380, 30));

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Due Date :");
        jPanel9.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 490, 100, -1));

        lbl_DueDate.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_DueDate.setForeground(new java.awt.Color(255, 255, 255));
        jPanel9.add(lbl_DueDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 490, 190, 25));

        lbl_bookQuantity.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbl_bookQuantity.setForeground(new java.awt.Color(242, 242, 242));
        jPanel9.add(lbl_bookQuantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 550, 190, 25));

        jLabel1000101.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel1000101.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1000101.setText("Book Quantity");
        jPanel9.add(jLabel1000101, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 550, 130, -1));

        jLabel29.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("ISBN No :");
        jPanel9.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, 90, -1));

        lbl_isbn.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbl_isbn.setForeground(new java.awt.Color(255, 255, 255));
        jPanel9.add(lbl_isbn, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 310, 190, 25));

        lbl_Booknum.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbl_Booknum.setForeground(new java.awt.Color(255, 255, 255));
        jPanel9.add(lbl_Booknum, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 610, 190, 25));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("No of IssueBook :");
        jPanel9.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 610, 160, -1));

        kGradientPanel1.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 440, 768));

        jPanel2.setBackground(new java.awt.Color(255, 0, 51));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        kGradientPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 120, -1, 5));

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 0, 51));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/AddNewBookIcons/icons8_Books_52px_1.png"))); // NOI18N
        jLabel7.setText("  Renewal Book");
        kGradientPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 40, 250, 100));

        jLabel10.setBackground(new java.awt.Color(255, 51, 51));
        jLabel10.setFont(new java.awt.Font("Sylfaen", 0, 20)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 51, 51));
        jLabel10.setText("Accession  No:");
        kGradientPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 170, 150, 30));

        txt_accessionNo.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 0, 51)));
        txt_accessionNo.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_accessionNo.setPlaceholder("Accession No : ");
        txt_accessionNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_accessionNoKeyTyped(evt);
            }
        });
        kGradientPanel1.add(txt_accessionNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 160, 250, 40));

        txt_StudentId.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 0, 51)));
        txt_StudentId.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_StudentId.setPlaceholder("Student Id: ........");
        txt_StudentId.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_StudentIdFocusLost(evt);
            }
        });
        txt_StudentId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_StudentIdKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_StudentIdKeyTyped(evt);
            }
        });
        kGradientPanel1.add(txt_StudentId, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 230, 250, 40));

        jLabel13.setBackground(new java.awt.Color(255, 51, 51));
        jLabel13.setFont(new java.awt.Font("Sylfaen", 0, 20)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 51, 51));
        jLabel13.setText("Student Id:");
        kGradientPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 240, 150, 30));

        Date_Re_IssueDate.setBackground(new java.awt.Color(255, 51, 51));
        Date_Re_IssueDate.setDateFormatString("dd/MM/yyyy");
        Date_Re_IssueDate.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        kGradientPanel1.add(Date_Re_IssueDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 450, 260, 30));

        jLabel12.setBackground(new java.awt.Color(255, 51, 51));
        jLabel12.setFont(new java.awt.Font("Sylfaen", 0, 20)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 51, 51));
        jLabel12.setText("Re-Issue Date :");
        kGradientPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 460, 130, 30));

        jLabel14.setBackground(new java.awt.Color(255, 51, 51));
        jLabel14.setFont(new java.awt.Font("Sylfaen", 0, 20)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 51, 51));
        jLabel14.setText("Today Fine :");
        kGradientPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 300, 120, 30));

        Date_Re_DueDate.setBackground(new java.awt.Color(255, 51, 51));
        Date_Re_DueDate.setDateFormatString("dd/MM/yyyy");
        Date_Re_DueDate.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        kGradientPanel1.add(Date_Re_DueDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 510, 260, 30));

        jButton1.setBackground(new java.awt.Color(204, 204, 204));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setText("After 15 Days");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        kGradientPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1170, 510, -1, 30));

        jLabel15.setBackground(new java.awt.Color(255, 51, 51));
        jLabel15.setFont(new java.awt.Font("Sylfaen", 0, 20)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 51, 51));
        jLabel15.setText("Re-Due Date :");
        kGradientPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 520, 130, 30));

        jLabel16.setBackground(new java.awt.Color(255, 51, 51));
        jLabel16.setFont(new java.awt.Font("Sylfaen", 0, 20)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 51, 51));
        jLabel16.setText("Totel Fine :");
        kGradientPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 400, 120, 30));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("=");
        kGradientPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1140, 520, -1, 10));

        Today_fine_calculate.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Today_fine_calculate.setPreferredSize(new java.awt.Dimension(64, 31));
        kGradientPanel1.add(Today_fine_calculate, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 300, 190, 29));

        lbl_Totel_fine.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_Totel_fine.setPreferredSize(new java.awt.Dimension(64, 31));
        kGradientPanel1.add(lbl_Totel_fine, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 400, 190, 29));

        jLabel17.setBackground(new java.awt.Color(255, 51, 51));
        jLabel17.setFont(new java.awt.Font("Sylfaen", 0, 20)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 51, 51));
        jLabel17.setText("Old Fine :");
        kGradientPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 350, 120, 30));

        lbl_old_fine.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_old_fine.setPreferredSize(new java.awt.Dimension(64, 31));
        kGradientPanel1.add(lbl_old_fine, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 350, 190, 29));

        btn_renewal1.setText("RESET");
        btn_renewal1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btn_renewal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_renewal1ActionPerformed(evt);
            }
        });
        btn_renewal1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btn_renewal1KeyPressed(evt);
            }
        });
        kGradientPanel1.add(btn_renewal1, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 670, 340, 70));

        jPanel3.setBackground(new java.awt.Color(255, 0, 51));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        kGradientPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 110, 250, 3));

        jPanel1.add(kGradientPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1400, 770));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        setSize(new java.awt.Dimension(1400, 770));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void rSMaterialButtonCircle3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle3MouseClicked
        HomePage home = new HomePage();
        home.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_rSMaterialButtonCircle3MouseClicked

    private void rSMaterialButtonCircle5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle5MouseClicked
        HomePage home = new HomePage();
        home.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_rSMaterialButtonCircle5MouseClicked

    private void txt_StudentIdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_StudentIdKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            getIssueBookDetails();
        }      // TODO add your handling code here:
    }//GEN-LAST:event_txt_StudentIdKeyPressed

    private void btn_renewalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_renewalActionPerformed
        if (RenewalBook() == true) {
            getIssueBookDetails();
//            showFine();
            BookQuantity();
            fine();
            totalfine();
        }
    }//GEN-LAST:event_btn_renewalActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        demo1();   // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txt_StudentIdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_StudentIdFocusLost
//    Ananad find tha all detiles
        getIssueBookDetails();
//TODO add your handling code here:
    }//GEN-LAST:event_txt_StudentIdFocusLost

    private void btn_renewal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_renewal1ActionPerformed
        RenewalBook book = new RenewalBook();
        book.setVisible(true);
        this.dispose(); // TODO add your handling code here:
    }//GEN-LAST:event_btn_renewal1ActionPerformed

    private void txt_accessionNoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_accessionNoKeyTyped
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txt_accessionNoKeyTyped

    private void txt_StudentIdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_StudentIdKeyTyped
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txt_StudentIdKeyTyped

    private void btn_renewalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_renewalKeyPressed
        // TODO Renewal Book code here:
        if (RenewalBook() == true) {
            getIssueBookDetails();
//            showFine();
            BookQuantity();
            fine();
            totalfine();
        }
    }//GEN-LAST:event_btn_renewalKeyPressed

    private void btn_renewal1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_renewal1KeyPressed
        RenewalBook book = new RenewalBook();
        book.setVisible(true);
        this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_btn_renewal1KeyPressed

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
            java.util.logging.Logger.getLogger(RenewalBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RenewalBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RenewalBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RenewalBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RenewalBook().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser Date_Re_DueDate;
    private com.toedter.calendar.JDateChooser Date_Re_IssueDate;
    private javax.swing.JLabel Today_fine_calculate;
    private necesario.RSMaterialButtonCircle btn_renewal;
    private necesario.RSMaterialButtonCircle btn_renewal1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel1000101;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel9;
    private keeptoo.KGradientPanel kGradientPanel1;
    private javax.swing.JLabel lbl_BookError;
    private javax.swing.JLabel lbl_BookName;
    private javax.swing.JLabel lbl_Booknum;
    private javax.swing.JLabel lbl_DueDate;
    private javax.swing.JLabel lbl_IssueDate;
    private javax.swing.JLabel lbl_StudentName;
    private javax.swing.JLabel lbl_Totel_fine;
    private javax.swing.JLabel lbl_bookQuantity;
    private javax.swing.JLabel lbl_isbn;
    private javax.swing.JLabel lbl_old_fine;
    private rojerusan.RSMaterialButtonCircle rSMaterialButtonCircle3;
    private rojerusan.RSMaterialButtonCircle rSMaterialButtonCircle5;
    private app.bolivia.swing.JCTextField txt_StudentId;
    private app.bolivia.swing.JCTextField txt_accessionNo;
    // End of variables declaration//GEN-END:variables
}
