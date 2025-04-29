/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package jFrame;

import java.awt.Toolkit;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import javax.swing.table.TableModel;

/**
 *
 * @author Anand
 */
public final class ManageBook extends javax.swing.JFrame {

    /**
     * Creates new form ManageBook
     */
    String bookName, publication, author, isbnNo;
    int total_page;
    double price;

    DefaultTableModel model;
//    private boolean isAdded;

    public ManageBook() {

        initComponents();
        setBookDetailsToTable();
        Section();
        NameB.setText(Session.getUserName());
    }

    //to set the book details into the table 
    public void setBookDetailsToTable() {

        try {

            Connection con = DbConnection.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from books";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                String isbnNo = rs.getString("isbn");
                String bookName = rs.getString("book_name");
                String author = rs.getString("author");
                String publication = rs.getString("Publication");
                String totalPage = rs.getString("total_pages");
                String price = rs.getString("price");
                String quantity = rs.getString("quantity");

                Object[] obj = {isbnNo, bookName, author, publication, totalPage, price, quantity};
                model = (DefaultTableModel) tbl_bookDetails.getModel();
                model.addRow(obj);
            }
            con.close();
            st.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    //to add book to book_details table
    public boolean addBook() {
        boolean isAdded = false;
        isbnNo = txt_isbnNo.getText();
        bookName = txt_bookName.getText();
        author = txt_authorName.getText();
        publication = txt_publication.getText();
        total_page = Integer.parseInt(txt_totalPage.getText());
        price = Double.parseDouble(txt_price.getText());
        int quantity = Integer.parseInt(txt_quantity.getText());

//        Declare resources
        Connection con = null;
        PreparedStatement pst = null;

        try {
            if (isbnNo.isEmpty()) {
                return false;
            }
            con = DbConnection.getConnection();
            String sql = "INSERT INTO books (isbn, book_name, author, publication, total_pages, price, quantity) VALUES (?, ?, ?, ?, ?, ?, ?)";
            pst = con.prepareStatement(sql);

            pst.setString(1, isbnNo);
            pst.setString(2, bookName);
            pst.setString(3, author);
            pst.setString(4, publication);
            pst.setInt(5, total_page);
            pst.setDouble(6, price);
            pst.setInt(7, quantity);

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
        return isAdded;
    }
    // to update book details

    public boolean updateBook() {
        boolean isUpdated = false;
        isbnNo = txt_isbnNo.getText();
        bookName = txt_bookName.getText();
        author = txt_authorName.getText();
        publication = txt_publication.getText();
        total_page = Integer.parseInt(txt_totalPage.getText());
        price = Double.parseDouble(txt_price.getText());
        int quantity = Integer.parseInt(txt_quantity.getText());

        Connection con = null;
        PreparedStatement pst = null;
        if (isbnNo.isEmpty()) {
                return false;
            }
        try {
            
            con = (Connection) DbConnection.getConnection();

            String sql = "update books set book_name = ?, author = ?, publication = ?, total_pages = ?, price = ?, quantity = ? where isbn = ?";
            pst = con.prepareStatement(sql);

            pst.setString(1, bookName);
            pst.setString(2, author);
            pst.setString(3, publication);
            pst.setInt(4, total_page);
            pst.setDouble(5, price);
            pst.setInt(6, quantity);
            pst.setString(7, isbnNo);

            int rowCount = pst.executeUpdate();
            if (rowCount > 0) {
                isUpdated = true;
//                JOptionPane.showMessageDialog(this, "Book Upd");
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

//method to delete book detail
    public boolean deleteBook() {
        boolean isDeleted = false;
        String isbn = txt_isbnNo.getText();
        if (isbnNo.isEmpty()) {
                return false;
            }
        try {
            Connection con = (Connection) DbConnection.getConnection();

            String sql = "delete from books  where isbn = ?";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, isbn);

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
        DefaultTableModel model = (DefaultTableModel) tbl_bookDetails.getModel();
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
        rSMaterialButtonCircle5 = new rojerusan.RSMaterialButtonCircle();
        txt_bookName = new app.bolivia.swing.JCTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txt_authorName = new app.bolivia.swing.JCTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txt_publication = new app.bolivia.swing.JCTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        rSMaterialButtonCircle1 = new rojerusan.RSMaterialButtonCircle();
        rSMaterialButtonCircle2 = new rojerusan.RSMaterialButtonCircle();
        rSMaterialButtonCircle3 = new rojerusan.RSMaterialButtonCircle();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txt_totalPage = new app.bolivia.swing.JCTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txt_price = new app.bolivia.swing.JCTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txt_isbnNo = new app.bolivia.swing.JCTextField();
        jLabel20 = new javax.swing.JLabel();
        txt_quantity = new app.bolivia.swing.JCTextField();
        jLabel21 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_bookDetails = new rojeru_san.complementos.RSTableMetro();
        rSMaterialButtonCircle6 = new rojerusan.RSMaterialButtonCircle();
        kGradientPanel1 = new keeptoo.KGradientPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(102, 102, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(500, 770));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(102, 102, 255));

        rSMaterialButtonCircle5.setText("<<");
        rSMaterialButtonCircle5.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        rSMaterialButtonCircle5.setPreferredSize(new java.awt.Dimension(80, 60));
        rSMaterialButtonCircle5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonCircle5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(rSMaterialButtonCircle5, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(rSMaterialButtonCircle5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, 60));

        txt_bookName.setBackground(new java.awt.Color(102, 102, 255));
        txt_bookName.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));
        txt_bookName.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_bookName.setPlaceholder("Enter Book Name....");
        txt_bookName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_bookNameKeyTyped(evt);
            }
        });
        jPanel1.add(txt_bookName, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 210, 340, 30));

        jLabel7.setBackground(new java.awt.Color(255, 51, 51));
        jLabel7.setFont(new java.awt.Font("Sylfaen", 0, 17)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/AddNewBookIcons/icons8_Moleskine_26px.png"))); // NOI18N
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, 50, 50));

        jLabel10.setBackground(new java.awt.Color(255, 51, 51));
        jLabel10.setFont(new java.awt.Font("Sylfaen", 0, 20)); // NOI18N
        jLabel10.setText("Book Name");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 180, 270, 40));

        txt_authorName.setBackground(new java.awt.Color(102, 102, 255));
        txt_authorName.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));
        txt_authorName.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_authorName.setPlaceholder("Enter Author Name....");
        txt_authorName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_authorNameKeyTyped(evt);
            }
        });
        jPanel1.add(txt_authorName, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 280, 340, 30));

        jLabel8.setBackground(new java.awt.Color(255, 51, 51));
        jLabel8.setFont(new java.awt.Font("Sylfaen", 0, 17)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/AddNewBookIcons/icons8_Collaborator_Male_26px.png"))); // NOI18N
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 270, 50, 50));

        jLabel11.setBackground(new java.awt.Color(255, 51, 51));
        jLabel11.setFont(new java.awt.Font("Sylfaen", 0, 20)); // NOI18N
        jLabel11.setText("Author Name");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 250, 270, 40));

        txt_publication.setBackground(new java.awt.Color(102, 102, 255));
        txt_publication.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));
        txt_publication.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_publication.setPlaceholder("Publication ....");
        txt_publication.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_publicationKeyTyped(evt);
            }
        });
        jPanel1.add(txt_publication, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 350, 340, 30));

        jLabel12.setBackground(new java.awt.Color(255, 51, 51));
        jLabel12.setFont(new java.awt.Font("Sylfaen", 0, 17)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/AddNewBookIcons/icons8_Unit_26px.png"))); // NOI18N
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 340, 50, 50));

        jLabel13.setBackground(new java.awt.Color(255, 51, 51));
        jLabel13.setFont(new java.awt.Font("Sylfaen", 0, 20)); // NOI18N
        jLabel13.setText("Published By");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 320, 270, 40));

        rSMaterialButtonCircle1.setBackground(new java.awt.Color(255, 51, 51));
        rSMaterialButtonCircle1.setText("ADD");
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
        jPanel1.add(rSMaterialButtonCircle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 600, 140, 70));

        rSMaterialButtonCircle2.setBackground(new java.awt.Color(255, 51, 51));
        rSMaterialButtonCircle2.setText("UPDATE");
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
        jPanel1.add(rSMaterialButtonCircle2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 600, 140, 70));

        rSMaterialButtonCircle3.setBackground(new java.awt.Color(255, 51, 51));
        rSMaterialButtonCircle3.setText("DELETE");
        rSMaterialButtonCircle3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonCircle3ActionPerformed(evt);
            }
        });
        rSMaterialButtonCircle3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rSMaterialButtonCircle3KeyPressed(evt);
            }
        });
        jPanel1.add(rSMaterialButtonCircle3, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 600, 140, 70));

        jLabel14.setBackground(new java.awt.Color(255, 51, 51));
        jLabel14.setFont(new java.awt.Font("Sylfaen", 0, 20)); // NOI18N
        jLabel14.setText("Total Page");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 390, 270, 40));

        jLabel15.setBackground(new java.awt.Color(255, 51, 51));
        jLabel15.setFont(new java.awt.Font("Sylfaen", 0, 17)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/AddNewBookIcons/icons8_Moleskine_26px.png"))); // NOI18N
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 410, 50, 50));

        txt_totalPage.setBackground(new java.awt.Color(102, 102, 255));
        txt_totalPage.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));
        txt_totalPage.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_totalPage.setPlaceholder("Total Page....");
        txt_totalPage.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_totalPageKeyTyped(evt);
            }
        });
        jPanel1.add(txt_totalPage, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 420, 340, -1));

        jLabel16.setBackground(new java.awt.Color(255, 51, 51));
        jLabel16.setFont(new java.awt.Font("Sylfaen", 0, 20)); // NOI18N
        jLabel16.setText("Price");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 460, 120, 40));

        jLabel17.setBackground(new java.awt.Color(255, 51, 51));
        jLabel17.setFont(new java.awt.Font("Sylfaen", 0, 17)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/AddNewBookIcons/money.png"))); // NOI18N
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 480, 50, 50));

        txt_price.setBackground(new java.awt.Color(102, 102, 255));
        txt_price.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));
        txt_price.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_price.setPlaceholder("Price ....");
        txt_price.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_priceKeyTyped(evt);
            }
        });
        jPanel1.add(txt_price, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 490, 110, -1));

        jLabel18.setBackground(new java.awt.Color(255, 51, 51));
        jLabel18.setFont(new java.awt.Font("Sylfaen", 0, 20)); // NOI18N
        jLabel18.setText("ISBN No");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 110, 270, 40));

        jLabel19.setBackground(new java.awt.Color(255, 51, 51));
        jLabel19.setFont(new java.awt.Font("Sylfaen", 0, 17)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/AddNewBookIcons/Unice.png"))); // NOI18N
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 50, 50));

        txt_isbnNo.setBackground(new java.awt.Color(102, 102, 255));
        txt_isbnNo.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));
        txt_isbnNo.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_isbnNo.setPlaceholder("ISBN No....");
        txt_isbnNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_isbnNoKeyTyped(evt);
            }
        });
        jPanel1.add(txt_isbnNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 140, 340, -1));

        jLabel20.setBackground(new java.awt.Color(255, 51, 51));
        jLabel20.setFont(new java.awt.Font("Sylfaen", 0, 20)); // NOI18N
        jLabel20.setText("Quantity");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 460, 120, 40));

        txt_quantity.setBackground(new java.awt.Color(102, 102, 255));
        txt_quantity.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));
        txt_quantity.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_quantity.setPlaceholder("Quantity....");
        txt_quantity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_quantityKeyTyped(evt);
            }
        });
        jPanel1.add(txt_quantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 490, 120, -1));

        jLabel21.setBackground(new java.awt.Color(255, 51, 51));
        jLabel21.setFont(new java.awt.Font("Sylfaen", 0, 17)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/icons8_Books_26px.png"))); // NOI18N
        jPanel1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 470, 40, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 480, -1));
        jPanel1.getAccessibleContext().setAccessibleDescription("");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(900, 770));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_bookDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ISBN No", "Book Name", "Author ", "Publication", "Total Page", "Price", "Quantity"
            }
        ));
        tbl_bookDetails.setColorBackgoundHead(new java.awt.Color(102, 102, 255));
        tbl_bookDetails.setDropMode(javax.swing.DropMode.ON);
        tbl_bookDetails.setFocusable(false);
        tbl_bookDetails.setGridColor(new java.awt.Color(255, 255, 255));
        tbl_bookDetails.setInheritsPopupMenu(true);
        tbl_bookDetails.setRowHeight(40);
        tbl_bookDetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_bookDetailsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_bookDetails);

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 890, 610));

        rSMaterialButtonCircle6.setText("X");
        rSMaterialButtonCircle6.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        rSMaterialButtonCircle6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSMaterialButtonCircle6MouseClicked(evt);
            }
        });
        jPanel3.add(rSMaterialButtonCircle6, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 0, 80, 70));

        kGradientPanel1.setInheritsPopupMenu(true);
        kGradientPanel1.setkEndColor(new java.awt.Color(204, 204, 204));
        kGradientPanel1.setkGradientFocus(100);
        kGradientPanel1.setkStartColor(new java.awt.Color(0, 255, 255));
        kGradientPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 360, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        kGradientPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 80, 360, 5));

        jLabel1.setFont(new java.awt.Font("Serif", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 51));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/AddNewBookIcons/icons8_Books_52px_1.png"))); // NOI18N
        jLabel1.setText("MANAGE BOOKS");
        kGradientPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, 340, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/male_user_50px.png"))); // NOI18N
        kGradientPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 10, 50, -1));

        NameB.setFont(new java.awt.Font("Times New Roman", 1, 22)); // NOI18N
        kGradientPanel1.add(NameB, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 20, 130, 30));

        jPanel3.add(kGradientPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 920, 770));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(475, 0, 920, -1));

        setSize(new java.awt.Dimension(1390, 770));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tbl_bookDetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_bookDetailsMouseClicked
        int rowNo = tbl_bookDetails.getSelectedRow();
        TableModel model = tbl_bookDetails.getModel();

        txt_isbnNo.setText(model.getValueAt(rowNo, 0).toString());
        txt_bookName.setText(model.getValueAt(rowNo, 1).toString());
        txt_authorName.setText(model.getValueAt(rowNo, 2).toString());
        txt_publication.setText(model.getValueAt(rowNo, 3).toString());
        txt_totalPage.setText(model.getValueAt(rowNo, 4).toString());
        txt_price.setText(model.getValueAt(rowNo, 5).toString());
        txt_quantity.setText(model.getValueAt(rowNo, 6).toString());
    }//GEN-LAST:event_tbl_bookDetailsMouseClicked

    private void rSMaterialButtonCircle3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle3ActionPerformed
        if (deleteBook() == true) {
            JOptionPane.showMessageDialog(this, "Book Deleted");
            clearTable();
            setBookDetailsToTable();
            ManageBook manage = new ManageBook();
            manage.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Book Deletion Failed");
        }
    }//GEN-LAST:event_rSMaterialButtonCircle3ActionPerformed

    private void rSMaterialButtonCircle2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle2ActionPerformed
        if (updateBook() == true) {
            JOptionPane.showMessageDialog(this, "Book Updated");
            clearTable();
            setBookDetailsToTable();
            ManageBook manage = new ManageBook();
            manage.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Book Updation Failed");
        }

    }//GEN-LAST:event_rSMaterialButtonCircle2ActionPerformed

    private void rSMaterialButtonCircle1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle1ActionPerformed
        // TODO add your handling code here:

        if (addBook() == true) {
            JOptionPane.showMessageDialog(this, "Book Added");
            clearTable();
            setBookDetailsToTable();
            ManageBook manage = new ManageBook();
            manage.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Book Addition Failed");
        }
    }//GEN-LAST:event_rSMaterialButtonCircle1ActionPerformed

    private void rSMaterialButtonCircle5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle5ActionPerformed
        HomePage home = new HomePage();
        home.setVisible(true);
        this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_rSMaterialButtonCircle5ActionPerformed

    private void rSMaterialButtonCircle6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle6MouseClicked
        HomePage home = new HomePage();
        home.setVisible(true);
        dispose();
    }//GEN-LAST:event_rSMaterialButtonCircle6MouseClicked

    private void txt_totalPageKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_totalPageKeyTyped
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }       // todo only type the number 
    }//GEN-LAST:event_txt_totalPageKeyTyped

    private void txt_priceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_priceKeyTyped
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        } // TODO only tupe the number here:
    }//GEN-LAST:event_txt_priceKeyTyped

    private void txt_quantityKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_quantityKeyTyped
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txt_quantityKeyTyped

    private void txt_bookNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_bookNameKeyTyped
        if (!(Character.isAlphabetic(evt.getKeyChar()) || Character.isWhitespace(evt.getKeyChar()) || evt.getKeyChar() == '.')) {
            evt.consume();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txt_bookNameKeyTyped

    private void txt_authorNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_authorNameKeyTyped
        if (!(Character.isAlphabetic(evt.getKeyChar()) || Character.isWhitespace(evt.getKeyChar()) || evt.getKeyChar() == '.' || evt.getKeyChar() == ',')) {
            evt.consume();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txt_authorNameKeyTyped

    private void txt_publicationKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_publicationKeyTyped
        if (!(Character.isAlphabetic(evt.getKeyChar()) || Character.isWhitespace(evt.getKeyChar()))) {
            evt.consume();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txt_publicationKeyTyped

    private void rSMaterialButtonCircle1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle1KeyPressed
        if (addBook() == true) {
            JOptionPane.showMessageDialog(this, "Book Added");
            clearTable();
            setBookDetailsToTable();
            ManageBook manage = new ManageBook();
            manage.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Book Addition Failed");
        }       // TODO add your handling code here:
    }//GEN-LAST:event_rSMaterialButtonCircle1KeyPressed

    private void rSMaterialButtonCircle2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle2KeyPressed
        if (updateBook() == true) {
            JOptionPane.showMessageDialog(this, "Book Updated");
            clearTable();
            setBookDetailsToTable();
            ManageBook manage = new ManageBook();
            manage.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Book Updation Failed");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_rSMaterialButtonCircle2KeyPressed

    private void rSMaterialButtonCircle3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle3KeyPressed
        if (deleteBook() == true) {
            JOptionPane.showMessageDialog(this, "Book Deleted");
            clearTable();
            setBookDetailsToTable();
            ManageBook manage = new ManageBook();
            manage.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Book Deletion Failed");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_rSMaterialButtonCircle3KeyPressed

    private void txt_isbnNoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_isbnNoKeyTyped
if (!(Character.isDigit(evt.getKeyChar()) || evt.getKeyChar() == '-')) {
            evt.consume();
        }         // TODO add your handling code here:
    }//GEN-LAST:event_txt_isbnNoKeyTyped

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
            java.util.logging.Logger.getLogger(ManageBook.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManageBook.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManageBook.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManageBook.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ManageBook().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static final javax.swing.JLabel NameB = new javax.swing.JLabel();
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
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private keeptoo.KGradientPanel kGradientPanel1;
    private rojerusan.RSMaterialButtonCircle rSMaterialButtonCircle1;
    private rojerusan.RSMaterialButtonCircle rSMaterialButtonCircle2;
    private rojerusan.RSMaterialButtonCircle rSMaterialButtonCircle3;
    private rojerusan.RSMaterialButtonCircle rSMaterialButtonCircle5;
    private rojerusan.RSMaterialButtonCircle rSMaterialButtonCircle6;
    private rojeru_san.complementos.RSTableMetro tbl_bookDetails;
    private app.bolivia.swing.JCTextField txt_authorName;
    private app.bolivia.swing.JCTextField txt_bookName;
    private app.bolivia.swing.JCTextField txt_isbnNo;
    private app.bolivia.swing.JCTextField txt_price;
    private app.bolivia.swing.JCTextField txt_publication;
    private app.bolivia.swing.JCTextField txt_quantity;
    private app.bolivia.swing.JCTextField txt_totalPage;
    // End of variables declaration//GEN-END:variables
}
