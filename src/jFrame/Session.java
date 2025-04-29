/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jFrame;

/**
 *
 * @author anand
 */
public class Session {

    private static int userId;
    private static String userName;

    // Set user details when login is successful
    public static void setUser(int id, String name) {
        userId = id;
        userName = name;
    }

    // Get user ID
    public static int getUserId() {
        return userId;
    }

    // Get username
    public static String getUserName() {
        return userName;
    }

    // Clear session when user logs out
    public static void clearSession() {
        userId = 0;
        userName = null;
    }
}
