/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taghandlers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import servlets.eBooksStoreAdminUsersServlet;

/**
 *
 * @author gheor
 */
public class UpdateUser extends SimpleTagSupport {

    private String CNP;
    private String username;
    private String password;
    private String role;

    /**
     * Called by the container to invoke this tag. The implementation of this
     * method is provided by the tag library developer, and handles all tag
     * processing, body iteration, etc.
     */
    @Override
    public void doTag() throws JspException {
        JspWriter out = getJspContext().getOut();
        
        try {
            // TODO: insert code to write html before writing the body content.
            // e.g.:
            //
            // out.println("<strong>" + attribute_1 + "</strong>");
            // out.println("    <blockquote>");
             // declare specific variables
                // declare specific DB info
                String user = "test" ;
                String password = "test";
                String url = "jdbc:derby://localhost:1527/ebooksstore;create=true;";
                String driver = "org.apache.derby.jdbc.ClientDriver";
                ResultSet resultSet = null;
                Statement statement = null;
                PreparedStatement pstmnt = null;
                Connection connection = null;
                try
                { 
                    //check driver and create connection
                    Class driverClass = Class.forName(driver);
                    connection = DriverManager.getConnection(url, user, password);
                    // identify selected checkbox and for each execute the update operation
                    //String[] selectedCheckboxes = request.getParameterValues("admin_users_checkbox");
                    //String username = request.getParameter("admin_users_username");
                    //String user_password = request.getParameter("admin_users_password");
                    //String role = request.getParameter("admin_user_role");
                    // if both username and password are "" do nothing
                    if(!(("".equals(username)) && ("".equals(password)))){
                        // operate updates for all selected rows
                        //for(String s : selectedCheckboxes){
                            if("".equals(username)){ // only password/s should be updated
                                String DML = "UPDATE EBOOKS.USERS SET ssn=?, password=?,role=? WHERE SSN=?";
                                pstmnt = connection.prepareStatement(DML);
                                pstmnt.setString(1, CNP);
                                pstmnt.setString(2, password);
                                pstmnt.setString(3, role);
                                pstmnt.setString(4, CNP);
                            }
                            else if("".equals(password)){// only username should be updated
                                String DML = "UPDATE EBOOKS.USERS SET ssn=?, name=?,role=? WHERE SSN=?";
                                pstmnt = connection.prepareStatement(DML);
                                pstmnt.setString(1, CNP);
                                pstmnt.setString(2, username);
                                pstmnt.setString(3, role);
                                pstmnt.setString(4, CNP);
                            }else{
                                String DML = "UPDATE EBOOKS.USERS SET ssn=?, name=?, password=?,role=? WHERE SSN=?";
                                pstmnt = connection.prepareStatement(DML);
                                pstmnt.setString(1, CNP);
                                pstmnt.setString(2, username);
                                pstmnt.setString(3, password);
                                pstmnt.setString(4, role);
                                pstmnt.setString(5, CNP);
                            }
                            boolean execute = pstmnt.execute();
                        //}
                    }else{ // update one or more roles for one or more users
                        //for(String s : selectedCheckboxes){
                            // realize update of all selected rows
                            String DML = "UPDATE EBOOKS.USERS SET role=? WHERE SSN=?";
                            pstmnt = connection.prepareStatement(DML);
                            pstmnt.setString(1, role);
                            pstmnt.setString(2, CNP);
                            boolean execute = pstmnt.execute();
                        //}                    
                    }
                }
                catch (ClassNotFoundException | SQLException ex)
                {
                    // display a message for NOT OK
                    Logger.getLogger(eBooksStoreAdminUsersServlet.class.getName()).log(Level.SEVERE, null, ex);
                    out.println("Exception in UPDATE:"+ex.getMessage());
                }
                finally
                {
                    if (resultSet != null)
                    {
                        try
                        {
                            resultSet.close();
                        }
                        catch (SQLException ex){Logger.getLogger(eBooksStoreAdminUsersServlet.class.getName()).log(Level.SEVERE, null, ex);}
                    }
                    if (pstmnt != null)
                    {
                        try
                        {
                            pstmnt.close();
                        }
                        catch (SQLException ex){Logger.getLogger(eBooksStoreAdminUsersServlet.class.getName()).log(Level.SEVERE, null, ex);}
                    }	
                    if (connection != null)
                    {
                        try
                        {
                            connection.close();
                        }
                        catch (SQLException ex){Logger.getLogger(eBooksStoreAdminUsersServlet.class.getName()).log(Level.SEVERE, null, ex);}
                    }
                }

            JspFragment f = getJspBody();
            if (f != null) {
                f.invoke(out);
            }

            // TODO: insert code to write html after writing the body content.
            // e.g.:
            //
            // out.println("    </blockquote>");
        } catch (java.io.IOException ex) {
            throw new JspException("Error in UpdateUser tag", ex);
        }
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String id_role) {
        this.role = id_role;
    }
    
}
