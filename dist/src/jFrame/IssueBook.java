/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package jFrame;

import com.toedter.calendar.JDateChooser;
import java.awt.Toolkit;
import java.sql.*;
import java.util.Calendar;
import javax.swing.JOptionPane;

/**
 *
 * @author Anand
 */
public class IssueBook extends javax.swing.JFrame {

    /**
     * Creates new form IssueBook
     */
    public IssueBook() {
        Section();
        initComponents();
        demo();
        demo1();
        NameI.setText(Session.getUserName());
    }
//    to fetch the book detailes from the databasse and display it to book details panel

    public void getBookDetails() {
        String Isbn_No = txt_isbn.getText();

        try {
            Connection con = DbConnection.getConnection();
            PreparedStatement pst = con.prepareStatement("Select * from books where isbn = ?");
            pst.setString(1, Isbn_No);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                lbl_isbn.setText(rs.getString("isbn"));
                lbl_BookName.setText(rs.getString("book_name"));
                lbl_Author.setText(rs.getString("author"));
                lbl_Publication.setText(rs.getString("publication"));
                lbl_bookQuantity.setText(rs.getString("quantity"));
                if (!lbl_isbn.getText().isEmpty()) {
                    lbl_bookError.setText(" ");
                }

            } else {
                lbl_bookError.setText("Invalid ISBN No");

                lbl_isbn.setText("");
                lbl_BookName.setText("");
                lbl_Author.setText("");
                lbl_Publication.setText("");
                lbl_bookQuantity.setText("");
            }
            con.close();
            pst.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }

    }

//    to fetch the Student detailes from the databasse and display it to book details panel    
    public void getStudentDetails() {
        String StudentId = txt_StudentId.getText();

        try {
            Connection con = DbConnection.getConnection();
            PreparedStatement pst = con.prepareStatement("Select * from students where student_id =?");
            pst.setString(1, StudentId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                lbl_StudentId.setText(rs.getString("student_id"));
                lbl_studentName.setText(rs.getString("student_name"));
                lbl_Phone.setText(rs.getString("phone_number"));
                lbl_Enroll.setText(rs.getString("enroll_date"));
                lbl_Booknum.setText(rs.getString("issue_book_no"));
                findFine();
                if (!lbl_StudentId.getText().isEmpty()) {
                    lbl_studentError.setText("");
                }
            } else {
                lbl_studentError.setText("Invalid Student Id");

                lbl_StudentId.setText("");
                lbl_studentName.setText("");
                lbl_Phone.setText("");
                lbl_Enroll.setText("");
                lbl_Booknum.setText("");
                lbl_Fine.setText("");
            }
            con.close();
            pst.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }

    }

    public boolean issueBook() {
        boolean isIssued = false;
        String AccessionNo = txt_accessionNo.getText();
        String StudentId = txt_StudentId.getText();
        String Isbn = txt_isbn.getText();
        double fine = Double.parseDouble(lbl_Fine.getText());

        java.util.Date uIssueDate = Date_IssueDate.getDate();
        java.util.Date uDueDate = Date_DueDate.getDate();

        Long l1 = uIssueDate.getTime();
        Long l2 = uDueDate.getTime();

        java.sql.Date sIssueDate = new java.sql.Date(l1);
        java.sql.Date sDueDate = new java.sql.Date(l2);

        try {

            Connection con = DbConnection.getConnection();
            String sql = "INSERT INTO book_issues (accession_no, isbn, student_id, issue_date, due_date, status, fine) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, AccessionNo);
            pst.setString(2, Isbn);
            pst.setString(3, StudentId);
            pst.setDate(4, sIssueDate);
            pst.setDate(5, sDueDate);
            pst.setString(6, "pending");
            pst.setDouble(7, fine);

            int rowCount = pst.executeUpdate();
            if (rowCount > 0) {
                isIssued = true;
                JOptionPane.showMessageDialog(this, "Book issued successfully");
            }
            pst.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e);
        }
        return isIssued;
    }

//  Cheaking book already allocated or not ...
    public boolean isAlreadyIssued() {

        boolean isAlreadyIssued = false;
        String Isbn_no = lbl_isbn.getText();
        String StudentId = txt_StudentId.getText();
        try {
            Connection con = DbConnection.getConnection();
            String sql = "select * from book_issues where isbn = ? and student_id = ? and status = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, Isbn_no);
            pst.setString(2, StudentId);
            pst.setString(3, "pending");

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                isAlreadyIssued = true;
            } else {
                isAlreadyIssued = false;
            }
            con.close();
            pst.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
        return isAlreadyIssued;
    }

//   this book is allready issue an other student 
    public boolean isSameAccession() {

        boolean isSameAccession = false;
        String Accession = txt_accessionNo.getText();
        try {
            Connection con = DbConnection.getConnection();
            String sql = "SELECT * FROM book_issues WHERE accession_no = ? AND status = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, Accession);
            pst.setString(2, "pending");
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                isSameAccession = true;
                JOptionPane.showMessageDialog(this, "This Accession book has been issued to someone else");
            } else {
                isSameAccession = false;
            }
            rs.close();
            pst.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Same Aceesion" + e);
        }
        return isSameAccession;
    }

    public void twoupdate() {
        String StudentId = lbl_StudentId.getText();
        try {
            Connection con = DbConnection.getConnection();
            PreparedStatement pst = con.prepareStatement("update students set issue_book_no = issue_book_no + 1 where student_id = ?");
            pst.setString(1, StudentId);

            int rowCount = pst.executeUpdate();

            if (rowCount > 0) {
                int initialCount = Integer.parseInt(lbl_Booknum.getText());
                lbl_Booknum.setText(Integer.toString(initialCount + 1));
            }
            con.close();
            pst.close();
//            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ibn" + e);
            System.out.println(e);
        }
    }

    //  Book Quantity update when Issu book ....
    public void updateBookCount() {
        String ISBN_No = lbl_isbn.getText();
        try {
            Connection con = DbConnection.getConnection();
            PreparedStatement pst = con.prepareStatement("update books set quantity = quantity - 1 where isbn = ?");
            pst.setString(1, ISBN_No);

            int rowCount = pst.executeUpdate();

            if (rowCount > 0) {
//                JOptionPane.showMessageDialog(this, "Book count Updated");
                int initialCount = Integer.parseInt(lbl_bookQuantity.getText());
                lbl_bookQuantity.setText(Integer.toString(initialCount - 1));
            } else {
//                JOptionPane.showMessageDialog(this, "Can't Updated count Book Count");
            }
            con.close();
            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

//    logo 
    private void Section() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("book.png")));
    }

    public void demo() {
        JDateChooser jDateChooser = new JDateChooser();

        // Get the current date
        java.util.Date currentDate = new java.util.Date();

        // Set the current date to the JDateChooser
        Date_IssueDate.setDate(currentDate);
    }

    public void demo1() {
        java.util.Date selectedDate = Date_IssueDate.getDate();
        if (selectedDate != null) {

            // Get the Calendar instance and set it to the selectesssdDate
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(selectedDate);

            // Add 15 days to the selectedDate
            calendar.add(Calendar.DAY_OF_MONTH, 15);

            // Set the new date to jDateChooser2
            Date_DueDate.setDate(calendar.getTime());
        }
    }

//    find the fine of the student 
    public void findFine() {
        String studentId = txt_StudentId.getText();
        try {
            Connection con = DbConnection.getConnection();
            String sql = "SELECT fine FROM book_issues WHERE student_id = ? AND status = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, studentId);
            pst.setString(2, "returned");
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                lbl_Fine.setText(rs.getString("fine"));
            } else {
                lbl_Fine.setText("0.0");
            }
            rs.close();
            pst.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Fine" + e);
        }
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
        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lbl_StudentId = new javax.swing.JLabel();
        lbl_studentName = new javax.swing.JLabel();
        lbl_Phone = new javax.swing.JLabel();
        lbl_Enroll = new javax.swing.JLabel();
        lbl_studentError = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lbl_Booknum = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lbl_Fine = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        rSMaterialButtonCircle5 = new rojerusan.RSMaterialButtonCircle();
        kGradientPanel1 = new keeptoo.KGradientPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        lbl_BookName = new javax.swing.JLabel();
        lbl_Author = new javax.swing.JLabel();
        lbl_Publication = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        lbl_bookError = new javax.swing.JLabel();
        rSMaterialButtonCircle3 = new rojerusan.RSMaterialButtonCircle();
        lbl_isbn = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel1000101 = new javax.swing.JLabel();
        lbl_bookQuantity = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txt_accessionNo = new app.bolivia.swing.JCTextField();
        jLabel15 = new javax.swing.JLabel();
        txt_StudentId = new app.bolivia.swing.JCTextField();
        jLabel12 = new javax.swing.JLabel();
        Date_IssueDate = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        Date_DueDate = new com.toedter.calendar.JDateChooser();
        btn_clear = new necesario.RSMaterialButtonCircle();
        jLabel16 = new javax.swing.JLabel();
        txt_isbn = new app.bolivia.swing.JCTextField();
        btn_issue = new necesario.RSMaterialButtonCircle();
        jButton1 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setFocusable(false);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(102, 102, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(340, 5));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 340, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 340, 5));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Student Id :");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 140, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Student name :");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Phone No :");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Enroll Date :");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, -1, -1));

        lbl_StudentId.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbl_StudentId.setForeground(new java.awt.Color(255, 255, 255));
        jPanel4.add(lbl_StudentId, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 270, 190, 25));

        lbl_studentName.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbl_studentName.setForeground(new java.awt.Color(255, 255, 255));
        jPanel4.add(lbl_studentName, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 330, 190, 25));

        lbl_Phone.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbl_Phone.setForeground(new java.awt.Color(255, 255, 255));
        jPanel4.add(lbl_Phone, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 390, 190, 25));

        lbl_Enroll.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbl_Enroll.setForeground(new java.awt.Color(255, 255, 255));
        jPanel4.add(lbl_Enroll, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 450, 190, 25));

        lbl_studentError.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        lbl_studentError.setForeground(new java.awt.Color(255, 204, 0));
        lbl_studentError.setText(" ");
        jPanel4.add(lbl_studentError, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 630, 310, 30));

        jLabel26.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/AddNewBookIcons/icons8_Student_Registration_100px_2.png"))); // NOI18N
        jLabel26.setText(" StudentDetails");
        jPanel4.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 100, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("No of IssueBook :");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 510, -1, -1));

        lbl_Booknum.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbl_Booknum.setForeground(new java.awt.Color(255, 255, 255));
        jPanel4.add(lbl_Booknum, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 510, 190, 25));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Fine :");
        jPanel4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 570, -1, -1));

        lbl_Fine.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbl_Fine.setForeground(new java.awt.Color(255, 255, 255));
        jPanel4.add(lbl_Fine, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 570, 190, 25));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 0, 470, 768));

        jPanel2.setBackground(new java.awt.Color(255, 0, 51));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 325, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 160, 325, 5));

        rSMaterialButtonCircle5.setText("X");
        rSMaterialButtonCircle5.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        rSMaterialButtonCircle5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSMaterialButtonCircle5MouseClicked(evt);
            }
        });
        rSMaterialButtonCircle5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonCircle5ActionPerformed(evt);
            }
        });
        jPanel1.add(rSMaterialButtonCircle5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1320, 0, 80, 69));

        kGradientPanel1.setInheritsPopupMenu(true);
        kGradientPanel1.setkEndColor(new java.awt.Color(255, 255, 255));
        kGradientPanel1.setkGradientFocus(250);
        kGradientPanel1.setkStartColor(new java.awt.Color(51, 255, 255));
        kGradientPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel9.setBackground(new java.awt.Color(255, 0, 51));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel22.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/AddNewBookIcons/icons8_Literature_100px_1.png"))); // NOI18N
        jLabel22.setText("BookDetails");
        jPanel9.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, -1, -1));

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
        jPanel9.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, -1, -1));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Author :");
        jPanel9.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, -1, -1));

        lbl_BookName.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbl_BookName.setForeground(new java.awt.Color(255, 255, 255));
        jPanel9.add(lbl_BookName, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 280, 190, 25));

        lbl_Author.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbl_Author.setForeground(new java.awt.Color(255, 255, 255));
        jPanel9.add(lbl_Author, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 340, 190, 25));

        lbl_Publication.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbl_Publication.setForeground(new java.awt.Color(255, 255, 255));
        jPanel9.add(lbl_Publication, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 400, 190, 25));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Publication :");
        jPanel9.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 400, -1, -1));

        lbl_bookError.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        lbl_bookError.setForeground(new java.awt.Color(255, 204, 0));
        jPanel9.add(lbl_bookError, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 600, 310, 30));

        rSMaterialButtonCircle3.setText("<<");
        rSMaterialButtonCircle3.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        rSMaterialButtonCircle3.setPreferredSize(new java.awt.Dimension(80, 60));
        rSMaterialButtonCircle3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSMaterialButtonCircle3MouseClicked(evt);
            }
        });
        jPanel9.add(rSMaterialButtonCircle3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        lbl_isbn.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbl_isbn.setForeground(new java.awt.Color(255, 255, 255));
        jPanel9.add(lbl_isbn, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 460, 190, 25));

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("ISBN No :");
        jPanel9.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 460, -1, -1));

        jLabel1000101.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel1000101.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1000101.setText("Book Quantity");
        jPanel9.add(jLabel1000101, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 520, -1, -1));

        lbl_bookQuantity.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbl_bookQuantity.setForeground(new java.awt.Color(242, 242, 242));
        jPanel9.add(lbl_bookQuantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 520, 190, 25));

        kGradientPanel1.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 460, 768));

        jLabel13.setBackground(new java.awt.Color(255, 51, 51));
        jLabel13.setFont(new java.awt.Font("Sylfaen", 0, 20)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 51, 51));
        jLabel13.setText("Accession No:");
        kGradientPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 370, 150, 30));

        txt_accessionNo.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 0, 51)));
        txt_accessionNo.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_accessionNo.setPlaceholder("Accession No ....");
        txt_accessionNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_accessionNoKeyTyped(evt);
            }
        });
        kGradientPanel1.add(txt_accessionNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 350, 250, 40));

        jLabel15.setBackground(new java.awt.Color(255, 51, 51));
        jLabel15.setFont(new java.awt.Font("Sylfaen", 0, 20)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 51, 51));
        jLabel15.setText("Student Id :");
        kGradientPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 300, 100, 30));

        txt_StudentId.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 0, 51)));
        txt_StudentId.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_StudentId.setPlaceholder("Student Id ....");
        txt_StudentId.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_StudentIdFocusLost(evt);
            }
        });
        txt_StudentId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_StudentIdKeyTyped(evt);
            }
        });
        kGradientPanel1.add(txt_StudentId, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 280, 250, 40));

        jLabel12.setBackground(new java.awt.Color(255, 51, 51));
        jLabel12.setFont(new java.awt.Font("Sylfaen", 0, 20)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 51, 51));
        jLabel12.setText("Issue Date :");
        kGradientPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 430, 150, 30));

        Date_IssueDate.setBackground(new java.awt.Color(255, 51, 51));
        Date_IssueDate.setDateFormatString("dd/MM/yyyy");
        Date_IssueDate.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        kGradientPanel1.add(Date_IssueDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 420, 260, 30));

        jLabel14.setBackground(new java.awt.Color(255, 51, 51));
        jLabel14.setFont(new java.awt.Font("Sylfaen", 0, 20)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 51, 51));
        jLabel14.setText("Due Date :");
        kGradientPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 520, 150, 30));

        Date_DueDate.setBackground(new java.awt.Color(255, 51, 51));
        Date_DueDate.setDateFormatString("dd/MM/yyyy");
        Date_DueDate.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        kGradientPanel1.add(Date_DueDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 510, 260, 30));

        btn_clear.setText("Reset");
        btn_clear.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btn_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clearActionPerformed(evt);
            }
        });
        btn_clear.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btn_clearKeyPressed(evt);
            }
        });
        kGradientPanel1.add(btn_clear, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 660, 340, 70));

        jLabel16.setBackground(new java.awt.Color(255, 51, 51));
        jLabel16.setFont(new java.awt.Font("Sylfaen", 0, 20)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 51, 51));
        jLabel16.setText("ISBN NO :");
        kGradientPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 230, 150, 30));

        txt_isbn.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 0, 51)));
        txt_isbn.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_isbn.setPlaceholder("ISBN No...");
        txt_isbn.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_isbnFocusLost(evt);
            }
        });
        kGradientPanel1.add(txt_isbn, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 210, 250, 40));

        btn_issue.setBackground(new java.awt.Color(255, 51, 51));
        btn_issue.setText("Issue Book");
        btn_issue.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btn_issue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_issueActionPerformed(evt);
            }
        });
        btn_issue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btn_issueKeyPressed(evt);
            }
        });
        kGradientPanel1.add(btn_issue, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 570, 340, 70));

        jButton1.setBackground(new java.awt.Color(204, 204, 204));
        jButton1.setText("After 15 Days");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        kGradientPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1250, 470, -1, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 36)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 0, 51));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/AddNewBookIcons/icons8_Books_52px_1.png"))); // NOI18N
        jLabel7.setText(" Issue Book");
        kGradientPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 80, 270, 100));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/male_user_50px.png"))); // NOI18N
        kGradientPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 10, 50, -1));

        NameI.setFont(new java.awt.Font("Times New Roman", 1, 22)); // NOI18N
        kGradientPanel1.add(NameI, new org.netbeans.lib.awtextra.AbsoluteConstraints(1170, 20, 130, 30));

        jPanel1.add(kGradientPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1400, 770));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        setSize(new java.awt.Dimension(1400, 768));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void rSMaterialButtonCircle5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle5MouseClicked
        HomePage home = new HomePage();
        home.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_rSMaterialButtonCircle5MouseClicked

    private void rSMaterialButtonCircle5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle5ActionPerformed
        HomePage home = new HomePage();
        home.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_rSMaterialButtonCircle5ActionPerformed

    private void rSMaterialButtonCircle3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle3MouseClicked
        HomePage home = new HomePage();
        home.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_rSMaterialButtonCircle3MouseClicked

    private void txt_StudentIdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_StudentIdFocusLost
        if (!txt_StudentId.getText().equals("")) {
            getStudentDetails();
//            numBookIssue();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txt_StudentIdFocusLost

    private void txt_isbnFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_isbnFocusLost
        if (!txt_isbn.getText().equals("")) {
            getBookDetails();
        }// TODO add your handling code here:
    }//GEN-LAST:event_txt_isbnFocusLost

    private void btn_issueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_issueActionPerformed
        if (lbl_bookQuantity.getText().equals("0")) {
            JOptionPane.showMessageDialog(this, "Book is not available");
        } else {
            if (lbl_Booknum.getText().equals("2")) {
                JOptionPane.showMessageDialog(this, "This Student has already 2 book");
            } else {
                if (Double.parseDouble(lbl_Fine.getText()) > 0) {
                    JOptionPane.showMessageDialog(this, "Student has already fine can't issue the Book");
                } else {
                    if (isAlreadyIssued() == false) {
                        if (isSameAccession() == false) {
                            if (issueBook() == true) {
                                updateBookCount();
                                twoupdate();
                            } else {
                                JOptionPane.showMessageDialog(this, "Can't issue the book");
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "This student already has this Book ");
                    }
                }
            }
        }
    }//GEN-LAST:event_btn_issueActionPerformed

    private void btn_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clearActionPerformed
        IssueBook book = new IssueBook();
        book.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_clearActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        demo1();   // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txt_StudentIdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_StudentIdKeyTyped
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }    // TODO add your handling code here:
    }//GEN-LAST:event_txt_StudentIdKeyTyped

    private void txt_accessionNoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_accessionNoKeyTyped
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txt_accessionNoKeyTyped

    private void btn_issueKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_issueKeyPressed
        if (lbl_bookQuantity.getText().equals("0")) {
            JOptionPane.showMessageDialog(this, "Book is not available");
        } else {
            if (lbl_Booknum.getText().equals("2")) {
                JOptionPane.showMessageDialog(this, "This Student has already 2 book");
            } else {
                if (Double.parseDouble(lbl_Fine.getText()) > 0) {
                    JOptionPane.showMessageDialog(this, "Student has already fine can't issue the Book");
                } else {
                    if (isAlreadyIssued() == false) {
                        if (isSameAccession() == false) {
                            if (issueBook() == true) {
                                updateBookCount();
                                twoupdate();
                            } else {
                                JOptionPane.showMessageDialog(this, "Can't issue the book");
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "This student already has this Book ");
                    }
                }
            }
        }
    }//GEN-LAST:event_btn_issueKeyPressed

    private void btn_clearKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_clearKeyPressed
        IssueBook book = new IssueBook();
        book.setVisible(true);
        this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_btn_clearKeyPressed

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
            java.util.logging.Logger.getLogger(IssueBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IssueBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IssueBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IssueBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IssueBook().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser Date_DueDate;
    private com.toedter.calendar.JDateChooser Date_IssueDate;
    public static final javax.swing.JLabel NameI = new javax.swing.JLabel();
    private necesario.RSMaterialButtonCircle btn_clear;
    private necesario.RSMaterialButtonCircle btn_issue;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1000101;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel9;
    private keeptoo.KGradientPanel kGradientPanel1;
    private javax.swing.JLabel lbl_Author;
    private javax.swing.JLabel lbl_BookName;
    private javax.swing.JLabel lbl_Booknum;
    private javax.swing.JLabel lbl_Enroll;
    private javax.swing.JLabel lbl_Fine;
    private javax.swing.JLabel lbl_Phone;
    private javax.swing.JLabel lbl_Publication;
    private javax.swing.JLabel lbl_StudentId;
    private javax.swing.JLabel lbl_bookError;
    private javax.swing.JLabel lbl_bookQuantity;
    private javax.swing.JLabel lbl_isbn;
    private javax.swing.JLabel lbl_studentError;
    private javax.swing.JLabel lbl_studentName;
    private rojerusan.RSMaterialButtonCircle rSMaterialButtonCircle3;
    private rojerusan.RSMaterialButtonCircle rSMaterialButtonCircle5;
    private app.bolivia.swing.JCTextField txt_StudentId;
    private app.bolivia.swing.JCTextField txt_accessionNo;
    private app.bolivia.swing.JCTextField txt_isbn;
    // End of variables declaration//GEN-END:variables

}
