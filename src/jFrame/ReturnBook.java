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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;

/**
 *
 * @author Anand
 */
public class ReturnBook extends javax.swing.JFrame {

    /**
     * Creates new form ReturnBook
     */
    public ReturnBook() {
        initComponents();
        Section();
        demo();

    }

//    to fetch the issue book details from the database and display into pane..
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

            }

            rs.close();
            pst.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

//    return the book 
    public boolean returnBook() {
        boolean IsReturned = false;
        String AccessionNo = txt_accessionNo.getText();
        String StudentId = txt_StudentId.getText();
        java.util.Date ReturnDate = Date_ReturnDate.getDate();

        Long l1 = ReturnDate.getTime();
        java.sql.Date uReturnDate = new java.sql.Date(l1);

        try {
            Connection con = DbConnection.getConnection();
            String sql = "update book_issues set status = ?,return_date = ? where accession_no = ? AND student_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, "returned");
            pst.setDate(2, uReturnDate);
            pst.setString(3, AccessionNo);
            pst.setString(4, StudentId);

            int rowCount = pst.executeUpdate();
            if (rowCount > 0) {
                IsReturned = true;
            } else {
                IsReturned = false;
            }
            pst.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
        return IsReturned;
    }

//    Book quantity text fild code here...
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
            JOptionPane.showMessageDialog(this, e);
        }

    }

    //  Book Quantity update when Issu book ....
    public void updateBookCount() {
        String ISBN_No = lbl_isbn.getText();
        try {
            Connection con = DbConnection.getConnection();
            PreparedStatement pst = con.prepareStatement("update books set quantity = quantity + 1 where isbn = ?");
            pst.setString(1, ISBN_No);

            int rowCount = pst.executeUpdate();

            if (rowCount > 0) {
//                JOptionPane.showMessageDialog(this, "Book count Updated");
                int initialCount = Integer.parseInt(lbl_bookQuantity.getText());
                lbl_bookQuantity.setText(Integer.toString(initialCount + 1));
            } else {
//                JOptionPane.showMessageDialog(this, "Can't Updated count Book Count");
            }
            pst.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    public void showb() {
        String StudentId = txt_StudentId.getText();
        try {
            Connection con = DbConnection.getConnection();
            PreparedStatement pst = con.prepareStatement("Select issue_book_no from students where student_id = ?");

            pst.setString(1, StudentId);

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
            JOptionPane.showMessageDialog(this, e);
        }
    }
// After the returning the book issue book number -1 of student

    public void twoupdate() {
        String StudentId = txt_StudentId.getText();
        try {
            Connection con = DbConnection.getConnection();
            PreparedStatement pst = con.prepareStatement("update students set issue_book_no = issue_book_no - 1 where student_id = ?");
            pst.setString(1, StudentId);

            int rowCount = pst.executeUpdate();

            if (rowCount > 0) {
                int initialCount = Integer.parseInt(lbl_Booknum.getText());
                lbl_Booknum.setText(Integer.toString(initialCount - 1));
            }
            pst.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    private void Section() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("book.png")));
    }

//    return date automatice set...
    public void demo() {
        JDateChooser jDateChooser = new JDateChooser();
        // Get the current date
        java.util.Date currentDate = new java.util.Date();
        // Set the current date to the JDateChooser
        Date_ReturnDate.setDate(currentDate);
    }
//    calculate today fine

    public void fine() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            String date1Str = lbl_DueDate.getText();
            Date date1 = dateFormat.parse(date1Str);
            Date date2 = Date_ReturnDate.getDate();

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

            total_fine.setText(totalFineStr);
        } catch (Exception e) {
//            System.out.println(e);
            JOptionPane.showMessageDialog(this, e);
        }
    }

    public void Pay() {

        String StudentId = txt_StudentId.getText();
        String AccessionNo = txt_accessionNo.getText();

        try {
            Connection con = (Connection) DbConnection.getConnection();
            String Sql = "UPDATE book_issues SET fine = ? WHERE student_id = ? AND accession_no = ?";
            PreparedStatement pst = con.prepareStatement(Sql);

            pst.setString(1, "0");
            pst.setString(2, StudentId);
            pst.setString(3, AccessionNo);

            int rowCount = pst.executeUpdate();

            if (rowCount > 0) {
                JOptionPane.showMessageDialog(this, "Amount Paid");
                Today_fine_calculate.setText("0.0");
                lbl_old_fine.setText("0.0");
                total_fine.setText("0.0");
                
            } else {
                JOptionPane.showMessageDialog(this, "Amount not Paid ");
            }

            pst.close();
            con.close();
        } catch (Exception e) {
            System.out.print(e);
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
        rSMaterialButtonCircle3 = new rojerusan.RSMaterialButtonCircle();
        rSMaterialButtonCircle5 = new rojerusan.RSMaterialButtonCircle();
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
        jLabel1 = new javax.swing.JLabel();
        kGradientPanel1 = new keeptoo.KGradientPanel();
        jLabel14 = new javax.swing.JLabel();
        Date_ReturnDate = new com.toedter.calendar.JDateChooser();
        btn_return = new necesario.RSMaterialButtonCircle();
        rSMaterialButtonCircle4 = new rojerusan.RSMaterialButtonCircle();
        jLabel16 = new javax.swing.JLabel();
        txt_StudentId = new app.bolivia.swing.JCTextField();
        txt_accessionNo = new app.bolivia.swing.JCTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        Today_fine_calculate = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        total_fine = new javax.swing.JLabel();
        lbl_old_fine = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(1400, 770));
        jPanel1.setPreferredSize(new java.awt.Dimension(1400, 770));
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
        jPanel9.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, -1, -1));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Student Name :");
        jPanel9.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, -1, -1));

        lbl_BookName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_BookName.setForeground(new java.awt.Color(255, 255, 255));
        jPanel9.add(lbl_BookName, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 280, 190, 25));

        lbl_StudentName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_StudentName.setForeground(new java.awt.Color(255, 255, 255));
        jPanel9.add(lbl_StudentName, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 400, 190, 25));

        lbl_IssueDate.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_IssueDate.setForeground(new java.awt.Color(255, 255, 255));
        jPanel9.add(lbl_IssueDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 460, 190, 25));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Issue Date :");
        jPanel9.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 460, -1, -1));

        lbl_BookError.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        lbl_BookError.setForeground(new java.awt.Color(255, 204, 0));
        jPanel9.add(lbl_BookError, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 700, 370, 30));

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Due Date :");
        jPanel9.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 520, -1, -1));

        lbl_DueDate.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_DueDate.setForeground(new java.awt.Color(255, 255, 255));
        jPanel9.add(lbl_DueDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 520, 190, 25));

        lbl_bookQuantity.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbl_bookQuantity.setForeground(new java.awt.Color(242, 242, 242));
        jPanel9.add(lbl_bookQuantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 580, 190, 25));

        jLabel1000101.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel1000101.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1000101.setText("Book Quantity");
        jPanel9.add(jLabel1000101, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 580, -1, -1));

        jLabel29.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("ISBN No :");
        jPanel9.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, -1, -1));

        lbl_isbn.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbl_isbn.setForeground(new java.awt.Color(255, 255, 255));
        jPanel9.add(lbl_isbn, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 340, 190, 25));

        lbl_Booknum.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbl_Booknum.setForeground(new java.awt.Color(255, 255, 255));
        jPanel9.add(lbl_Booknum, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 640, 190, 25));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("No of IssueBook :");
        jPanel9.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 640, -1, -1));

        jPanel1.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 0, 440, 768));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons/library-2.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-70, 110, 580, 520));

        kGradientPanel1.setInheritsPopupMenu(true);
        kGradientPanel1.setkEndColor(new java.awt.Color(255, 255, 255));
        kGradientPanel1.setkGradientFocus(250);
        kGradientPanel1.setkStartColor(new java.awt.Color(51, 255, 255));
        kGradientPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setBackground(new java.awt.Color(255, 51, 51));
        jLabel14.setFont(new java.awt.Font("Sylfaen", 0, 20)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 51, 51));
        jLabel14.setText("Return Date :");
        kGradientPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 300, 120, 30));

        Date_ReturnDate.setBackground(new java.awt.Color(255, 51, 51));
        Date_ReturnDate.setDateFormatString("dd/MM/yyyy");
        Date_ReturnDate.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        kGradientPanel1.add(Date_ReturnDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 290, 250, 30));

        btn_return.setBackground(new java.awt.Color(255, 51, 51));
        btn_return.setText("Return Book");
        btn_return.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btn_return.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_returnActionPerformed(evt);
            }
        });
        btn_return.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btn_returnKeyPressed(evt);
            }
        });
        kGradientPanel1.add(btn_return, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 570, 340, 60));

        rSMaterialButtonCircle4.setText("Reset");
        rSMaterialButtonCircle4.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        rSMaterialButtonCircle4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonCircle4ActionPerformed(evt);
            }
        });
        rSMaterialButtonCircle4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rSMaterialButtonCircle4KeyPressed(evt);
            }
        });
        kGradientPanel1.add(rSMaterialButtonCircle4, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 650, 340, 60));

        jLabel16.setBackground(new java.awt.Color(255, 51, 51));
        jLabel16.setFont(new java.awt.Font("Sylfaen", 0, 20)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 51, 51));
        jLabel16.setText("Total Fine :");
        kGradientPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 450, 120, 30));

        txt_StudentId.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 0, 51)));
        txt_StudentId.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_StudentId.setPlaceholder("Student Id..........");
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
        kGradientPanel1.add(txt_StudentId, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 220, 250, 40));

        txt_accessionNo.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 0, 51)));
        txt_accessionNo.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_accessionNo.setPlaceholder("Accession No ..........");
        txt_accessionNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_accessionNoKeyTyped(evt);
            }
        });
        kGradientPanel1.add(txt_accessionNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 150, 250, 40));

        jLabel10.setBackground(new java.awt.Color(255, 51, 51));
        jLabel10.setFont(new java.awt.Font("Sylfaen", 0, 20)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 51, 51));
        jLabel10.setText("Accession  No:");
        kGradientPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 170, 150, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 0, 51));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/AddNewBookIcons/icons8_Books_52px_1.png"))); // NOI18N
        jLabel7.setText("   Return Book");
        kGradientPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 40, 230, 100));

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

        kGradientPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 120, -1, 5));

        jLabel13.setBackground(new java.awt.Color(255, 51, 51));
        jLabel13.setFont(new java.awt.Font("Sylfaen", 0, 20)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 51, 51));
        jLabel13.setText("Student Id");
        kGradientPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 240, 90, 30));

        jLabel15.setBackground(new java.awt.Color(255, 51, 51));
        jLabel15.setFont(new java.awt.Font("Sylfaen", 0, 20)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 51, 51));
        jLabel15.setText("Today Fine :");
        kGradientPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 350, 120, 30));

        Today_fine_calculate.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Today_fine_calculate.setPreferredSize(new java.awt.Dimension(64, 31));
        kGradientPanel1.add(Today_fine_calculate, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 350, 190, 29));

        jLabel17.setBackground(new java.awt.Color(255, 51, 51));
        jLabel17.setFont(new java.awt.Font("Sylfaen", 0, 20)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 51, 51));
        jLabel17.setText("Old Fine :");
        kGradientPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 400, 120, 30));

        total_fine.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        total_fine.setPreferredSize(new java.awt.Dimension(64, 31));
        kGradientPanel1.add(total_fine, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 450, 170, 29));

        lbl_old_fine.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_old_fine.setPreferredSize(new java.awt.Dimension(64, 31));
        kGradientPanel1.add(lbl_old_fine, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 400, 190, 29));

        jPanel1.add(kGradientPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 0, 890, 770));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        setSize(new java.awt.Dimension(1400, 770));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void rSMaterialButtonCircle5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle5MouseClicked
        HomePage home = new HomePage();
        home.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_rSMaterialButtonCircle5MouseClicked

    private void btn_returnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_returnActionPerformed
        int confirmed = JOptionPane.showConfirmDialog(null, "Fine : " + total_fine.getText(), "Do you want to pay fine", JOptionPane.YES_NO_OPTION);
        if (confirmed == JOptionPane.YES_OPTION) {
            if (returnBook() == true) {
                Pay();
                JOptionPane.showMessageDialog(this, "Book Returned Successfully");
                updateBookCount();
                twoupdate();
            } else {
                JOptionPane.showMessageDialog(this, "Book Returned Unsuccessfully");
            }
        } else {
            if (returnBook() == true) {
                JOptionPane.showMessageDialog(this, "Book Returned Successfully");
                updateBookCount();
                twoupdate();
            } else {
                JOptionPane.showMessageDialog(this, "Book Returned Unsuccessfully");
            }
        }
    }//GEN-LAST:event_btn_returnActionPerformed

    private void rSMaterialButtonCircle3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle3MouseClicked
        HomePage home = new HomePage();
        home.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_rSMaterialButtonCircle3MouseClicked

    private void txt_StudentIdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_StudentIdKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            getIssueBookDetails();
//            BookQuantity();
//            showFIne();
//            fine();
//            totalfine();
        }      // TODO add your handling code here:
    }//GEN-LAST:event_txt_StudentIdKeyPressed

    private void rSMaterialButtonCircle4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle4ActionPerformed
        // Pagfe reset  code here:
        ReturnBook book = new ReturnBook();
        book.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_rSMaterialButtonCircle4ActionPerformed

    private void txt_StudentIdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_StudentIdFocusLost
        getIssueBookDetails();
    }//GEN-LAST:event_txt_StudentIdFocusLost

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

    private void btn_returnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_returnKeyPressed
        int confirmed = JOptionPane.showConfirmDialog(null, "Fine : " + total_fine.getText(), "Do you want to pay fine", JOptionPane.YES_NO_OPTION);
        if (confirmed == JOptionPane.YES_OPTION) {
            if (returnBook() == true) {
                Pay();
                JOptionPane.showMessageDialog(this, "Book Returned Successfully");
                updateBookCount();
                twoupdate();
            } else {
                JOptionPane.showMessageDialog(this, "Book Returned Unsuccessfully");
            }
        } else {
            if (returnBook() == true) {
                JOptionPane.showMessageDialog(this, "Book Returned Successfully");
                updateBookCount();
                twoupdate();
            } else {
                JOptionPane.showMessageDialog(this, "Book Returned Unsuccessfully");
            }
        }
    }//GEN-LAST:event_btn_returnKeyPressed

    private void rSMaterialButtonCircle4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle4KeyPressed
// Pagfe reset  code here:
        ReturnBook book = new ReturnBook();
        book.setVisible(true);
        this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_rSMaterialButtonCircle4KeyPressed

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
            java.util.logging.Logger.getLogger(ReturnBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReturnBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReturnBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReturnBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReturnBook().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser Date_ReturnDate;
    private javax.swing.JLabel Today_fine_calculate;
    private necesario.RSMaterialButtonCircle btn_return;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel1000101;
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
    private javax.swing.JPanel jPanel9;
    private keeptoo.KGradientPanel kGradientPanel1;
    private javax.swing.JLabel lbl_BookError;
    private javax.swing.JLabel lbl_BookName;
    private javax.swing.JLabel lbl_Booknum;
    private javax.swing.JLabel lbl_DueDate;
    private javax.swing.JLabel lbl_IssueDate;
    private javax.swing.JLabel lbl_StudentName;
    private javax.swing.JLabel lbl_bookQuantity;
    private javax.swing.JLabel lbl_isbn;
    private javax.swing.JLabel lbl_old_fine;
    private rojerusan.RSMaterialButtonCircle rSMaterialButtonCircle3;
    private rojerusan.RSMaterialButtonCircle rSMaterialButtonCircle4;
    private rojerusan.RSMaterialButtonCircle rSMaterialButtonCircle5;
    private javax.swing.JLabel total_fine;
    private app.bolivia.swing.JCTextField txt_StudentId;
    private app.bolivia.swing.JCTextField txt_accessionNo;
    // End of variables declaration//GEN-END:variables
}
