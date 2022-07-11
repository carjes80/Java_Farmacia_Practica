package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;
import models.Employees;
import models.EmployeesDao;
import static models.EmployeesDao.rol_user;
import views.LoginView;
import views.SystemView;

public class LoginController implements ActionListener, KeyListener {

    private Employees employee;
    private EmployeesDao employees_dao;
    private LoginView login_view;
    private SystemView views;

    public LoginController(Employees employee, EmployeesDao employees_dao, LoginView login_view) {
        this.employee = employee;
        this.employees_dao = employees_dao;
        this.login_view = login_view;
        this.login_view.btn_enter.addActionListener(this);
        this.login_view.txt_user_null.setVisible(false);
        this.login_view.txt_wrong_password.setVisible(false);
        this.login_view.txt_password.addKeyListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // este se elimina  ---->  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        String user = login_view.txt_username.getText().trim();
        String pass = String.valueOf(login_view.txt_password.getPassword());

        if (e.getSource() == login_view.btn_enter) {
            this.login_view.txt_user_null.setVisible(false);
            this.login_view.txt_wrong_password.setVisible(false);
            // validar que los campos no estén vacios.
            if (!user.equals("") || !pass.equals("")) {
                //pasar los parámetros al método lógin
                employee = employees_dao.loginQuery(user, pass);
                //verificar la existencia del usuario
                if (employee.getUsername() != null) {
                    if (employee.getRol().equals("Administrador")) {
                        SystemView admin = new SystemView();
                        admin.setVisible(true);
                   


                    } else {
                        
                       
                        SystemView aux = new SystemView();
                        aux.setVisible(true);
                        
                    }
                    this.login_view.dispose();
                } else {
                    login_view.txt_wrong_password.setVisible(true);
                }

            } else {

                login_view.txt_user_null.setVisible(true);

                //JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrecto");
            }
        } else {

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
   
    }

    @Override
    public void keyPressed(KeyEvent e) {
      /*  if (e.getKeyCode() == KeyEvent.VK_ENTER){
            keyReleased(e);
        }*/
      
    }

    @Override
    public void keyReleased(KeyEvent e) {
       if (e.getKeyCode() == KeyEvent.VK_ENTER){

             String user = login_view.txt_username.getText().trim();
        String pass = String.valueOf(login_view.txt_password.getPassword());

            this.login_view.txt_user_null.setVisible(false);
            this.login_view.txt_wrong_password.setVisible(false);
            // validar que los campos no estén vacios.
            if (!user.equals("") || !pass.equals("")) {
                //pasar los parámetros al método lógin
                employee = employees_dao.loginQuery(user, pass);
                //verificar la existencia del usuario
                if (employee.getUsername() != null) {
                    if (employee.getRol().equals("Administrador")) {
                        SystemView admin = new SystemView();
                        admin.setVisible(true);
                   


                    } else {
                        
                       
                        SystemView aux = new SystemView();
                        aux.setVisible(true);
                        
                    }
                    this.login_view.dispose();
                } else {
                    login_view.txt_wrong_password.setVisible(true);
                }

            } else {

                login_view.txt_user_null.setVisible(true);

                //JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrecto");
            }
        } else {

        }
       }
    }


