/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package jFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author Anand
 */
public final class HomePage extends javax.swing.JFrame {

    /**
     * Creates new form HomePage
     */
    Color mouseEnterColor = new Color(0, 0, 0);
    Color mouseExitColor = new Color(51, 51, 51);

    DefaultTableModel model;
//    private long long1;

    public HomePage() {
        Section();
        initComponents();
        setDatetoCard();
        showPieChart();
        rSTableStudentDetails();
        rSTableBookDetails();
        Name.setText(Session.getUserName());
    }

//    calender box to connect into the database....
    public void setDatetoCard() {
        Statement st = null;
        ResultSet rs = null;
        Connection con = null;

        try {
            con = DbConnection.getConnection();
            st = con.createStatement();

            // Get the number of books
            rs = st.executeQuery("SELECT COUNT(*) AS count FROM books");
            if (rs.next()) {
                lbl_NumOfBook.setText(String.valueOf(rs.getInt("count")));
            }

            // Get the number of students
            rs = st.executeQuery("SELECT COUNT(*) AS count FROM students");
            if (rs.next()) {
                lbl_NumOfStudent.setText(String.valueOf(rs.getInt("count")));
            }

            // Get the number of pending books
            rs = st.executeQuery("SELECT COUNT(*) AS count FROM book_issues WHERE status = 'pending'");
            if (rs.next()) {
                lbl_pendingBook.setText(String.valueOf(rs.getInt("count")));
            }
            con.close();
            st.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    public final void showPieChart() {

        //create dataset
        DefaultPieDataset barDataset = new DefaultPieDataset();
        try {
            Connection con = DbConnection.getConnection();
            String sql = "select  book_name, count(*) as i from books group by book_name";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                barDataset.setValue(rs.getString("book_name"), Double.valueOf(rs.getDouble("i")));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }

        //create chart
        JFreeChart piechart = ChartFactory.createPieChart("books", barDataset, true, true, false);//explain

        PiePlot piePlot = (PiePlot) piechart.getPlot();

        piePlot.setBackgroundPaint(Color.white);

        //create chartPanel to display chart(graph)
        ChartPanel barChartPanel = new ChartPanel(piechart);
        panelPieChart.removeAll();
        panelPieChart.add(barChartPanel, BorderLayout.CENTER);
        panelPieChart.validate();
    }
//    Student record......

    public void rSTableStudentDetails() {
        try {
            Connection con = DbConnection.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from students ";
            ResultSet rs = st.executeQuery(sql);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            while (rs.next()) {

                String StudentName = rs.getString("student_name");
                String StudentPhone = rs.getString("phone_number");
                String EnrollDate = rs.getLong("enroll_date") > 0 ? sdf.format(new Date(rs.getLong("enroll_date"))) : "N/A";
                String Address = rs.getString("address");

                Object[] obj = {StudentName,StudentPhone, EnrollDate, Address};
                model = (DefaultTableModel) table_StudentDetails.getModel();
                model.addRow(obj);
            }
            con.close();
            st.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }
//Book record .......

    public void rSTableBookDetails() {
        try {
            Connection con = DbConnection.getConnection();
            Statement st = con.createStatement();
            String sql = "Select * from books where isbn";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                String isbn = rs.getString("isbn");
                String BookName = rs.getString("book_name");
                String AuthorName = rs.getString("author");
                String publication = rs.getString("publication");
                String Quantity = rs.getString("quantity");

                Object[] obj = {isbn, BookName, AuthorName, publication, Quantity};
                model = (DefaultTableModel) table_BookDetails.getModel();
                model.addRow(obj);
            }
            con.close();
            st.close();
            rs.close();
        } catch (Exception e) {
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
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        rSMaterialButtonCircle3 = new rojerusan.RSMaterialButtonCircle();
        wel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        ManBookPnl = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        ManStudentPnl = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        RetnBookPnl = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        ViedRecordPnl = new javax.swing.JPanel();
        jLabel163 = new javax.swing.JLabel();
        ViewRtnBookPnl = new javax.swing.JPanel();
        jLabel100 = new javax.swing.JLabel();
        logOut = new javax.swing.JPanel();
        jLabel101 = new javax.swing.JLabel();
        PendingBookPnl = new javax.swing.JPanel();
        jLabel102 = new javax.swing.JLabel();
        RenwalBookPnl = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        IssueBookPnl = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        paypanel = new javax.swing.JPanel();
        lbl_RegisterUser = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        lbl_NumOfBook = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        lbl_NumOfStudent = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        usersdcd = new javax.swing.JPanel();
        lbl_pendingBook = new javax.swing.JLabel();
        jPanel28 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        panelPieChart = new javax.swing.JPanel();
        kGradientPanel1 = new keeptoo.KGradientPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        table_StudentDetails = new rojeru_san.complementos.RSTableMetro();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_BookDetails = new rojeru_san.complementos.RSTableMetro();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(100, 50, 0, 0));
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(102, 102, 255));
        jPanel1.setAutoscrolls(true);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/icons8_menu_48px_1.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, -1, 50));

        Name.setFont(new java.awt.Font("Yu Gothic Medium", 1, 20)); // NOI18N
        Name.setForeground(new java.awt.Color(0, 255, 51));
        jPanel1.add(Name, new org.netbeans.lib.awtextra.AbsoluteConstraints(1300, 20, 150, 30));

        jLabel5.setFont(new java.awt.Font("Yu Gothic Medium", 1, 25)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Library Managment System ");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, -1, -1));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/male_user_50px.png"))); // NOI18N
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1140, 0, 50, 60));

        rSMaterialButtonCircle3.setText("X");
        rSMaterialButtonCircle3.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        rSMaterialButtonCircle3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSMaterialButtonCircle3MouseClicked(evt);
            }
        });
        jPanel1.add(rSMaterialButtonCircle3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1460, 0, 80, 70));

        wel.setFont(new java.awt.Font("Yu Gothic Medium", 1, 20)); // NOI18N
        wel.setForeground(new java.awt.Color(255, 255, 255));
        wel.setText("Welcome, ");
        jPanel1.add(wel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1200, 20, 110, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1540, 70));

        jPanel4.setBackground(new java.awt.Color(51, 51, 51));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(255, 51, 51));
        jPanel6.setPreferredSize(new java.awt.Dimension(340, 60));
        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel6MouseEntered(evt);
            }
        });
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setBackground(new java.awt.Color(255, 51, 51));
        jLabel7.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/icons8_Home_26px_2.png"))); // NOI18N
        jLabel7.setText("    Home ");
        jPanel6.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 170, 40));

        jPanel4.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, -1, -1));

        jLabel2.setBackground(new java.awt.Color(255, 51, 51));
        jLabel2.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Features");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 190, 40));

        ManBookPnl.setBackground(new java.awt.Color(51, 51, 51));
        ManBookPnl.setPreferredSize(new java.awt.Dimension(340, 60));
        ManBookPnl.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setBackground(new java.awt.Color(255, 51, 51));
        jLabel14.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(153, 153, 153));
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/icons8_Book_26px.png"))); // NOI18N
        jLabel14.setText("   Manage Book");
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel14MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel14MouseExited(evt);
            }
        });
        ManBookPnl.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 250, 40));

        jPanel4.add(ManBookPnl, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, -1, -1));

        ManStudentPnl.setBackground(new java.awt.Color(51, 51, 51));
        ManStudentPnl.setPreferredSize(new java.awt.Dimension(340, 60));
        ManStudentPnl.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setBackground(new java.awt.Color(255, 51, 51));
        jLabel15.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(153, 153, 153));
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/icons8_Read_Online_26px.png"))); // NOI18N
        jLabel15.setText("   Manage Students");
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel15MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel15MouseExited(evt);
            }
        });
        ManStudentPnl.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 200, 40));

        jPanel4.add(ManStudentPnl, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, -1, -1));

        RetnBookPnl.setBackground(new java.awt.Color(51, 51, 51));
        RetnBookPnl.setPreferredSize(new java.awt.Dimension(340, 60));
        RetnBookPnl.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel27.setBackground(new java.awt.Color(255, 51, 51));
        jLabel27.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(153, 153, 153));
        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/icons8_Return_Purchase_26px.png"))); // NOI18N
        jLabel27.setText("   Return Book");
        jLabel27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel27MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel27MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel27MouseExited(evt);
            }
        });
        RetnBookPnl.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 190, 40));

        jPanel4.add(RetnBookPnl, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 390, -1, -1));

        ViedRecordPnl.setBackground(new java.awt.Color(51, 51, 51));
        ViedRecordPnl.setPreferredSize(new java.awt.Dimension(340, 60));
        ViedRecordPnl.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel163.setBackground(new java.awt.Color(255, 51, 51));
        jLabel163.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel163.setForeground(new java.awt.Color(153, 153, 153));
        jLabel163.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/icons8_View_Details_26px.png"))); // NOI18N
        jLabel163.setText("   View Records");
        jLabel163.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel163MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel163MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel163MouseExited(evt);
            }
        });
        ViedRecordPnl.add(jLabel163, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 210, 40));

        jPanel4.add(ViedRecordPnl, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 450, -1, -1));

        ViewRtnBookPnl.setBackground(new java.awt.Color(51, 51, 51));
        ViewRtnBookPnl.setPreferredSize(new java.awt.Dimension(340, 60));
        ViewRtnBookPnl.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel100.setBackground(new java.awt.Color(255, 51, 51));
        jLabel100.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel100.setForeground(new java.awt.Color(153, 153, 153));
        jLabel100.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/icons8_Books_26px.png"))); // NOI18N
        jLabel100.setText("   View Return Book");
        jLabel100.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel100MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel100MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel100MouseExited(evt);
            }
        });
        ViewRtnBookPnl.add(jLabel100, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel4.add(ViewRtnBookPnl, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 510, -1, -1));

        logOut.setBackground(new java.awt.Color(102, 102, 255));
        logOut.setPreferredSize(new java.awt.Dimension(340, 60));
        logOut.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel101.setBackground(new java.awt.Color(255, 51, 51));
        jLabel101.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel101.setForeground(new java.awt.Color(255, 255, 255));
        jLabel101.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/icons8_Exit_26px_2.png"))); // NOI18N
        jLabel101.setText("   Log Out");
        jLabel101.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel101MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel101MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel101MouseExited(evt);
            }
        });
        logOut.add(jLabel101, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 190, 60));

        jPanel4.add(logOut, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 640, -1, -1));

        PendingBookPnl.setBackground(new java.awt.Color(51, 51, 51));
        PendingBookPnl.setMinimumSize(new java.awt.Dimension(240, 50));
        PendingBookPnl.setPreferredSize(new java.awt.Dimension(340, 60));
        PendingBookPnl.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel102.setBackground(new java.awt.Color(255, 51, 51));
        jLabel102.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel102.setForeground(new java.awt.Color(153, 153, 153));
        jLabel102.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/icons8_Conference_26px.png"))); // NOI18N
        jLabel102.setText("   Pending Book Details");
        jLabel102.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel102MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel102MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel102MouseExited(evt);
            }
        });
        PendingBookPnl.add(jLabel102, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 230, 40));

        jPanel4.add(PendingBookPnl, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 570, -1, -1));

        RenwalBookPnl.setBackground(new java.awt.Color(51, 51, 51));
        RenwalBookPnl.setPreferredSize(new java.awt.Dimension(340, 60));
        RenwalBookPnl.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel20.setBackground(new java.awt.Color(255, 51, 51));
        jLabel20.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(153, 153, 153));
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/icons8_Sell_26px.png"))); // NOI18N
        jLabel20.setText("   Renewal Book");
        jLabel20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel20MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel20MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel20MouseExited(evt);
            }
        });
        RenwalBookPnl.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 190, 40));

        jPanel4.add(RenwalBookPnl, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, -1, -1));

        IssueBookPnl.setBackground(new java.awt.Color(51, 51, 51));
        IssueBookPnl.setPreferredSize(new java.awt.Dimension(340, 60));
        IssueBookPnl.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel23.setBackground(new java.awt.Color(255, 51, 51));
        jLabel23.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(153, 153, 153));
        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/icons8_Sell_26px.png"))); // NOI18N
        jLabel23.setText("   Issue Book");
        jLabel23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel23MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel23MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel23MouseExited(evt);
            }
        });
        IssueBookPnl.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 190, 40));

        jPanel4.add(IssueBookPnl, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, -1, -1));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 340, 760));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        paypanel.setBorder(javax.swing.BorderFactory.createMatteBorder(15, 0, 0, 0, new java.awt.Color(102, 102, 255)));
        paypanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                paypanelMouseMoved(evt);
            }
        });
        paypanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                paypanelMouseClicked(evt);
            }
        });
        paypanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_RegisterUser.setFont(new java.awt.Font("Segoe UI Black", 0, 45)); // NOI18N
        lbl_RegisterUser.setForeground(new java.awt.Color(102, 102, 102));
        lbl_RegisterUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/icons8_List_of_Thumbnails_50px.png"))); // NOI18N
        lbl_RegisterUser.setText("Pay");
        paypanel.add(lbl_RegisterUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 160, -1));

        jPanel23.setBorder(javax.swing.BorderFactory.createMatteBorder(15, 0, 0, 0, new java.awt.Color(255, 51, 51)));
        jPanel23.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel28.setFont(new java.awt.Font("Segoe UI Black", 0, 50)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(102, 102, 102));
        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/icons8_Book_Shelf_50px.png"))); // NOI18N
        jLabel28.setText("18");
        jPanel23.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 160, -1));

        paypanel.add(jPanel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 100, 200, 140));

        jPanel7.add(paypanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 60, 200, 140));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Book Details");
        jPanel7.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 490, 170, -1));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(102, 102, 102));
        jLabel17.setText("No. of Students");
        jPanel7.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 10, 170, 40));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(102, 102, 102));
        jLabel21.setText("Student's Fine Payment");
        jPanel7.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 20, 210, 30));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(102, 102, 102));
        jLabel25.setText("No. of Pending Book ");
        jPanel7.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 20, 190, 30));

        jPanel18.setBorder(javax.swing.BorderFactory.createMatteBorder(15, 0, 0, 0, new java.awt.Color(255, 51, 51)));
        jPanel18.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanel18MouseMoved(evt);
            }
        });
        jPanel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel18MouseClicked(evt);
            }
        });
        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_NumOfBook.setFont(new java.awt.Font("Segoe UI Black", 0, 50)); // NOI18N
        lbl_NumOfBook.setForeground(new java.awt.Color(102, 102, 102));
        lbl_NumOfBook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/icons8_Book_Shelf_50px.png"))); // NOI18N
        lbl_NumOfBook.setText("0");
        jPanel18.add(lbl_NumOfBook, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 160, -1));

        jPanel7.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 60, 200, 140));

        jPanel25.setBorder(javax.swing.BorderFactory.createMatteBorder(15, 0, 0, 0, new java.awt.Color(102, 102, 255)));
        jPanel25.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanel25MouseMoved(evt);
            }
        });
        jPanel25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel25MouseClicked(evt);
            }
        });
        jPanel25.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_NumOfStudent.setFont(new java.awt.Font("Segoe UI Black", 0, 50)); // NOI18N
        lbl_NumOfStudent.setForeground(new java.awt.Color(102, 102, 102));
        lbl_NumOfStudent.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/icons8_People_50px.png"))); // NOI18N
        lbl_NumOfStudent.setText("0");
        jPanel25.add(lbl_NumOfStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 160, -1));

        jPanel26.setBorder(javax.swing.BorderFactory.createMatteBorder(15, 0, 0, 0, new java.awt.Color(255, 51, 51)));
        jPanel26.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel30.setFont(new java.awt.Font("Segoe UI Black", 0, 50)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(102, 102, 102));
        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/icons8_Book_Shelf_50px.png"))); // NOI18N
        jLabel30.setText("18");
        jPanel26.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 160, -1));

        jPanel25.add(jPanel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 100, 200, 140));

        jPanel7.add(jPanel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 60, 200, 140));

        usersdcd.setBorder(javax.swing.BorderFactory.createMatteBorder(15, 0, 0, 0, new java.awt.Color(255, 51, 51)));
        usersdcd.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                usersdcdMouseMoved(evt);
            }
        });
        usersdcd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                usersdcdMouseClicked(evt);
            }
        });
        usersdcd.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_pendingBook.setFont(new java.awt.Font("Segoe UI Black", 0, 50)); // NOI18N
        lbl_pendingBook.setForeground(new java.awt.Color(102, 102, 102));
        lbl_pendingBook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/icons8_Sell_26px.png"))); // NOI18N
        lbl_pendingBook.setText("0");
        usersdcd.add(lbl_pendingBook, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 160, -1));

        jPanel28.setBorder(javax.swing.BorderFactory.createMatteBorder(15, 0, 0, 0, new java.awt.Color(255, 51, 51)));
        jPanel28.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel32.setFont(new java.awt.Font("Segoe UI Black", 0, 50)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(102, 102, 102));
        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/icons8_Book_Shelf_50px.png"))); // NOI18N
        jLabel32.setText("18");
        jPanel28.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 160, -1));

        usersdcd.add(jPanel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 100, 200, 140));

        jPanel7.add(usersdcd, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 60, 200, 140));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(102, 102, 102));
        jLabel16.setText("No. of Books");
        jPanel7.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 170, -1));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(102, 102, 102));
        jLabel22.setText("Student Details");
        jPanel7.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 220, 170, -1));

        panelPieChart.setLayout(new java.awt.BorderLayout());
        jPanel7.add(panelPieChart, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 250, 470, 420));

        kGradientPanel1.setInheritsPopupMenu(true);
        kGradientPanel1.setkEndColor(new java.awt.Color(255, 255, 255));
        kGradientPanel1.setkGradientFocus(125);
        kGradientPanel1.setkStartColor(new java.awt.Color(0, 255, 255));
        kGradientPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        table_StudentDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Phone No.", "Enroll Date", "Address"
            }
        ));
        table_StudentDetails.setColorBackgoundHead(new java.awt.Color(102, 102, 255));
        table_StudentDetails.setDropMode(javax.swing.DropMode.ON);
        table_StudentDetails.setFocusable(false);
        table_StudentDetails.setGridColor(new java.awt.Color(255, 255, 255));
        table_StudentDetails.setInheritsPopupMenu(true);
        table_StudentDetails.setRowHeight(40);
        jScrollPane3.setViewportView(table_StudentDetails);

        kGradientPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 250, 590, 220));

        table_BookDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ISBN No", "Book Name", "Author", "Publication", "Quantity"
            }
        ));
        table_BookDetails.setColorBackgoundHead(new java.awt.Color(102, 102, 255));
        table_BookDetails.setDropMode(javax.swing.DropMode.ON);
        table_BookDetails.setFocusable(false);
        table_BookDetails.setGridColor(new java.awt.Color(255, 255, 255));
        table_BookDetails.setInheritsPopupMenu(true);
        table_BookDetails.setRowHeight(40);
        jScrollPane2.setViewportView(table_BookDetails);

        kGradientPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 520, 590, 220));

        jPanel7.add(kGradientPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 760));

        getContentPane().add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 70, 1200, 760));

        setSize(new java.awt.Dimension(1540, 830));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        try {
            Connection con = DbConnection.getConnection();
            String sql = "select * from users where name = '" + Name.getText() + "' ";

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
//                Name1.setText(rs.getString(2));
                BookSecurity book = new BookSecurity();
                book.setVisible(true);
                this.dispose();
            } else {
                System.out.println("Error");
            }

            con.close();
            st.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
        this.dispose();
    }//GEN-LAST:event_jLabel14MouseClicked

    private void rSMaterialButtonCircle1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle1MouseClicked
        int confirmed = JOptionPane.showConfirmDialog(null, "Exit Program?", "EXIT", JOptionPane.YES_NO_OPTION);
        if (confirmed == JOptionPane.YES_OPTION) {
            this.dispose();
        }         // TODO add your handling code here:
    }//GEN-LAST:event_rSMaterialButtonCircle1MouseClicked

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
//   Student security page open karne ke liye 
        try {
            Connection con = DbConnection.getConnection();
            String sql = "select * from users where name = '" + Name.getText() + "' ";

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                StudentSecurity page = new StudentSecurity();
                page.setVisible(true);
                this.dispose();
            } else {
                System.out.println("Error");
            }

            con.close();
            st.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }

    }//GEN-LAST:event_jLabel15MouseClicked

    private void jLabel14MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseEntered
        ManBookPnl.setBackground(mouseEnterColor);
        jLabel14.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_jLabel14MouseEntered

    private void jLabel14MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseExited
        ManBookPnl.setBackground(mouseExitColor);
    }//GEN-LAST:event_jLabel14MouseExited

    private void jLabel15MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseEntered
        ManStudentPnl.setBackground(mouseEnterColor);
        jLabel15.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_jLabel15MouseEntered

    private void jLabel15MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseExited
        ManStudentPnl.setBackground(mouseExitColor);
    }//GEN-LAST:event_jLabel15MouseExited

    private void jLabel20MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel20MouseEntered
        RenwalBookPnl.setBackground(mouseEnterColor);
        RenwalBookPnl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_jLabel20MouseEntered

    private void jLabel20MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel20MouseExited
        RenwalBookPnl.setBackground(mouseExitColor);
    }//GEN-LAST:event_jLabel20MouseExited

    private void jLabel27MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel27MouseEntered
        RetnBookPnl.setBackground(mouseEnterColor);
        RetnBookPnl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_jLabel27MouseEntered

    private void jLabel27MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel27MouseExited
        RetnBookPnl.setBackground(mouseExitColor);
    }//GEN-LAST:event_jLabel27MouseExited

    private void jLabel163MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel163MouseEntered
        ViedRecordPnl.setBackground(mouseEnterColor);
        ViedRecordPnl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_jLabel163MouseEntered

    private void jLabel163MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel163MouseExited
        ViedRecordPnl.setBackground(mouseExitColor);
    }//GEN-LAST:event_jLabel163MouseExited

    private void jLabel102MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel102MouseEntered
        PendingBookPnl.setBackground(mouseEnterColor);
        PendingBookPnl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_jLabel102MouseEntered

    private void jLabel102MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel102MouseExited
        PendingBookPnl.setBackground(mouseExitColor);
    }//GEN-LAST:event_jLabel102MouseExited

    private void jLabel101MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel101MouseClicked
        JOptionPane.showMessageDialog(this, "you want to logout");
        LoginPage page = new LoginPage();
        page.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel101MouseClicked

    private void jLabel20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel20MouseClicked
        RenewalBook book = new RenewalBook();
        book.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel20MouseClicked

    private void jLabel27MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel27MouseClicked
        try {
            Connection con = DbConnection.getConnection();
            String sql = "select * from users where name = '" + Name.getText() + "' ";

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                new ReturnSecurity().setVisible(true);
                this.dispose();
            } else {
                System.out.println("Error");
            }
            con.close();
            st.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
        this.dispose();
    }//GEN-LAST:event_jLabel27MouseClicked

    private void jLabel163MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel163MouseClicked
        ViewRecords book = new ViewRecords();
        book.setVisible(true);
        this.dispose();  // TODO add your handling code here:
    }//GEN-LAST:event_jLabel163MouseClicked

    private void jLabel102MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel102MouseClicked
        PendingBookList book = new PendingBookList();
        book.setVisible(true);
        this.dispose();  // TODO add your handling code here:
    }//GEN-LAST:event_jLabel102MouseClicked

    private void rSMaterialButtonCircle3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle3MouseClicked
        int confirmed = JOptionPane.showConfirmDialog(null, "Exit Program?", "EXIT", JOptionPane.YES_NO_OPTION);
        if (confirmed == JOptionPane.YES_OPTION) {
            System.exit(0);  // Closes the entire Java application
        }

    }//GEN-LAST:event_rSMaterialButtonCircle3MouseClicked

    private void jLabel101MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel101MouseEntered
        Color mouseEnterColor = new Color(0, 0, 204);
        logOut.setBackground(mouseEnterColor);
        logOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
// TODO add your handling code here:
    }//GEN-LAST:event_jLabel101MouseEntered

    private void jLabel101MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel101MouseExited
        Color mouseExitColor = new Color(102, 102, 255);
        logOut.setBackground(mouseExitColor);
    }//GEN-LAST:event_jLabel101MouseExited

    private void jPanel6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseEntered
        jPanel6.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel6MouseEntered

    private void jPanel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel18MouseClicked
        BookPrint page = new BookPrint();
        page.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jPanel18MouseClicked

    private void jPanel18MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel18MouseMoved
        jPanel18.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel18MouseMoved

    private void jPanel25MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel25MouseClicked
        StudentPrint page = new StudentPrint();
        page.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jPanel25MouseClicked

    private void jPanel25MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel25MouseMoved
        jPanel25.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_jPanel25MouseMoved

    private void usersdcdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usersdcdMouseClicked
        PendingBookList page = new PendingBookList();
        page.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_usersdcdMouseClicked

    private void usersdcdMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usersdcdMouseMoved
        usersdcd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_usersdcdMouseMoved

    private void paypanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_paypanelMouseClicked
        try {
            Connection con = DbConnection.getConnection();
            String sql = "select * from users where name = '" + Name.getText() + "' ";

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                new PaySecurity().setVisible(true);
                this.dispose();
            } else {
                System.out.println("Error");
            }
            con.close();
            st.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }

    }//GEN-LAST:event_paypanelMouseClicked

    private void paypanelMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_paypanelMouseMoved
        paypanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_paypanelMouseMoved

    private void jLabel100MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel100MouseExited
        ViewRtnBookPnl.setBackground(mouseExitColor);
    }//GEN-LAST:event_jLabel100MouseExited

    private void jLabel100MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel100MouseEntered
        ViewRtnBookPnl.setBackground(mouseEnterColor);
        ViewRtnBookPnl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_jLabel100MouseEntered

    private void jLabel100MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel100MouseClicked
        ViewReturnBook book = new ViewReturnBook();
        book.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel100MouseClicked

    private void jLabel23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel23MouseClicked
//        It is opning the IssueSecurity page
        try {
            Connection con = DbConnection.getConnection();
            String sql = "select * from users where name = '" + Name.getText() + "' ";

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                IssueSecurity page = new IssueSecurity();
                page.setVisible(true);
                this.dispose();
            } else {
                System.out.println("Error");
            }
            con.close();
            st.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_jLabel23MouseClicked

    private void jLabel23MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel23MouseEntered
        IssueBookPnl.setBackground(mouseEnterColor);
        IssueBookPnl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_jLabel23MouseEntered

    private void jLabel23MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel23MouseExited
        IssueBookPnl.setBackground(mouseExitColor);
    }//GEN-LAST:event_jLabel23MouseExited

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
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomePage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel IssueBookPnl;
    private javax.swing.JPanel ManBookPnl;
    private javax.swing.JPanel ManStudentPnl;
    public static final javax.swing.JLabel Name = new javax.swing.JLabel();
    private javax.swing.JPanel PendingBookPnl;
    private javax.swing.JPanel RenwalBookPnl;
    private javax.swing.JPanel RetnBookPnl;
    private javax.swing.JPanel ViedRecordPnl;
    private javax.swing.JPanel ViewRtnBookPnl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel163;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private keeptoo.KGradientPanel kGradientPanel1;
    private javax.swing.JLabel lbl_NumOfBook;
    private javax.swing.JLabel lbl_NumOfStudent;
    private javax.swing.JLabel lbl_RegisterUser;
    private javax.swing.JLabel lbl_pendingBook;
    private javax.swing.JPanel logOut;
    private javax.swing.JPanel panelPieChart;
    private javax.swing.JPanel paypanel;
    private rojerusan.RSMaterialButtonCircle rSMaterialButtonCircle3;
    private rojeru_san.complementos.RSTableMetro table_BookDetails;
    private rojeru_san.complementos.RSTableMetro table_StudentDetails;
    private javax.swing.JPanel usersdcd;
    private javax.swing.JLabel wel;
    // End of variables declaration//GEN-END:variables

}
