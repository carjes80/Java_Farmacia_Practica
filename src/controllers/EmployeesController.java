package controllers;

import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Employees;
import models.EmployeesDao;
import static models.EmployeesDao.address_user;
import static models.EmployeesDao.email_user;
import static models.EmployeesDao.full_name_user;
import static models.EmployeesDao.id_user;
import static models.EmployeesDao.rol_user;
import static models.EmployeesDao.telephone_user;
import views.SystemView;

public class EmployeesController implements ActionListener, MouseListener, KeyListener {//siempre se importan y se agregan los métodos

    private Employees employee;
    private EmployeesDao employee_dao;
    private SystemView views;

    // Rol
    String rol = rol_user;
    DefaultTableModel model = new DefaultTableModel();//para poder trabajar con la tabla

    public EmployeesController(Employees employee, EmployeesDao employee_dao, SystemView views) {
        this.employee = employee;
        this.employee_dao = employee_dao;
        this.views = views;
        //boton de registrar empleado.
        this.views.btn_register_employee.addActionListener(this);

        //boton de modificar empleado
        this.views.btn_update_employee.addActionListener(this);
        //boton de eliminar y cancelar empleado   
        this.views.btn_delete_employee.addActionListener(this);//cuando se agrega el accion listener hay que implementarlo en la clase como se ve arriba.
        this.views.btn_cancel_employee.addActionListener(this);//

        this.views.employees_table.addMouseListener(this);//no se para que pasamos esto, ni lo de arriba (entendí que era para poder hacer clic en la tabla y poder modificar)
        this.views.txt_employee_search.addKeyListener(this);

        //boton de modificar contraseña
        this.views.btn_modify_data.addActionListener(this);
        
        //label de employee en escucha
        this.views.jLabelEmployees.addMouseListener(this);
        Profile();
    }

    //Registrar empleados
    @Override
    public void actionPerformed(ActionEvent e) {

        //Registrar empleado
        if (e.getSource() == views.btn_register_employee) {
            // verificar si los campos están vacios.
            if (views.txt_employee_id.getText().equals("")
                    || views.txt_employee_fullname.getText().equals("")
                    || views.txt_employee_username.getText().equals("")
                    || views.txt_employee_address.getText().equals("")
                    || views.txt_employee_telephone.getText().equals("")
                    || views.txt_employee_email.getText().equals("")
                    || views.cmb_rol.getSelectedItem().toString().equals("Seleccione")
                    || String.valueOf(views.txt_employee_password.getPassword()).equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                markNullFields();

            } else {
                employee.setId(Integer.parseInt(views.txt_employee_id.getText().trim()));
                employee.setUsername(views.txt_employee_username.getText().trim());
                employee.setFull_name(views.txt_employee_fullname.getText().trim());
                employee.setAddress(views.txt_employee_address.getText().trim());
                employee.setTelephone(views.txt_employee_telephone.getText().trim());
                employee.setEmail(views.txt_employee_email.getText().trim());
                employee.setRol(views.cmb_rol.getSelectedItem().toString().trim());
                employee.setPassword(String.valueOf(views.txt_employee_password.getPassword()));

                if (employee_dao.registerEmployeeQuery(employee)) {
                    cleanTable();
                    cleanFields();
                    ListAllEmployees();
                    JOptionPane.showMessageDialog(null, "Empleado registrado con éxito");

                    views.txt_employee_id.setText("");
                    views.txt_employee_fullname.setText("");
                    views.txt_employee_username.setText("");
                    views.txt_employee_address.setText("");
                    views.txt_employee_telephone.setText("");
                    views.txt_employee_email.setText("");
                    views.cmb_rol.setSelectedIndex(0);
                    views.txt_employee_password.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrar el empleado");
                }
            }
        } // Actualizar empleado
        else if (e.getSource() == views.btn_update_employee) {
            int row = views.employees_table.getSelectedRow(); // con esto estoy seleccionando el número de fila que está seleccionada,si no hay presionado arroja "-1"
            if (row >= 0) {
                if (views.txt_employee_id.getText().equals("")
                        || views.txt_employee_fullname.getText().equals("")
                        || views.cmb_rol.getSelectedItem().toString().equals("Seleccione")) {
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");

                } else {
                    employee.setId(Integer.parseInt(views.txt_employee_id.getText().trim()));
                    employee.setUsername(views.txt_employee_username.getText().trim());
                    employee.setFull_name(views.txt_employee_fullname.getText().trim());
                    employee.setAddress(views.txt_employee_address.getText().trim());
                    employee.setTelephone(views.txt_employee_telephone.getText().trim());
                    employee.setEmail(views.txt_employee_email.getText().trim());
                    employee.setRol(views.cmb_rol.getSelectedItem().toString().trim());
                    employee.setPassword(String.valueOf(views.txt_employee_password.getPassword()));

                    if (employee_dao.updateEmployeeQuery(employee)) {
                        cleanTable();
                        cleanFields();
                        ListAllEmployees();
                        views.btn_update_employee.setEnabled(true);
                        JOptionPane.showMessageDialog(null, "Empleado modificado con éxito");
                        Profile();

                    } else {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error al modificar el empleado");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debe seleccionar en la tabla el empleado que desea modificar");

            }

            //Eliminar empleado
        } else if (e.getSource() == views.btn_delete_employee) {
            int row = views.employees_table.getSelectedRow(); // con esto estoy seleccionando el número de fila que está seleccionada,si no hay presionado arrija "-1"
            if (row >= 0) {
                if (views.employees_table.getValueAt(row, 0).equals(id_user)) {//para verificar que no se haya seleccionado él mismo.
                    JOptionPane.showMessageDialog(null, "No puede eliminar el usuario autenticado");
                } else {
                    int id = Integer.parseInt(views.employees_table.getValueAt(row, 0).toString());
                    String name = views.employees_table.getValueAt(row, 1).toString();
                    if (employee_dao.deleteEmployeeQuery(id, name) != false) {
                        cleanFields();
                        views.btn_register_employee.setEnabled(true);
                        views.txt_employee_password.setEnabled(true);
                        cleanTable();
                        ListAllEmployees();
                        JOptionPane.showMessageDialog(null, "Empleado eliminado con éxito");
                    }

                }
            } else {
                JOptionPane.showMessageDialog(null, "Debe seleccionar en la tabla el empleado que desea eliminar");
            }

        } //Botón cancelar
        else if (e.getSource() == views.btn_cancel_employee) {
            cleanFields();
            initFields();
            cleanTable();
            ListAllEmployees();
            views.btn_register_employee.setEnabled(true);
            views.txt_employee_password.setEnabled(true);
            views.txt_employee_id.setEnabled(true);
            views.txt_employee_id.setEditable(true);
        } // boton de modificar contraseña
        else if (e.getSource() == views.btn_modify_data) {

            //recolectar información de las cajas password.
            String password = String.valueOf(views.txt_profile_password_modify.getPassword());
            String confirm_password = String.valueOf(views.txt_profile_passwor_modify_confirm.getPassword());

            //verificar que coincida la contraseña
            if (!password.equals("") && !confirm_password.equals("")) {
                if (password.equals(confirm_password)) {
                    employee.setPassword(String.valueOf(views.txt_profile_password_modify.getPassword()));

                    if (employee_dao.updateEmployeePassword(employee) != false) {
                        JOptionPane.showMessageDialog(null, "Contraseña modificada con éxito");
                        views.txt_profile_password_modify.setText("");
                        views.txt_profile_passwor_modify_confirm.setText("");
                        
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al modificar la contraseña");
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Contraseñas no coinciden");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Los campos son obligatorios");
            }
        }
    }
    //Listar todos los empleados

    public void ListAllEmployees() {

        if (rol.equals("Administrador")) {
            // System.out.println("El rol es en el if es " + rol);
            List<Employees> list = employee_dao.listEmployeesQuery(views.txt_employee_search.getText());
            model = (DefaultTableModel) views.employees_table.getModel(); //se tuvo que hacer un casteo, no se por qué
            Object[] row = new Object[7]; //aqui definimos la cantidad de columnas, en este caso hay 7
            for (int i = 0; i < list.size(); i++) {   //se usa el list.size para saber cuantas filas tiene la tabla
                row[0] = list.get(i).getId();
                row[1] = list.get(i).getFull_name();
                row[2] = list.get(i).getUsername();
                row[3] = list.get(i).getAddress();
                row[4] = list.get(i).getTelephone();
                row[5] = list.get(i).getEmail();
                row[6] = list.get(i).getRol();
                model.addRow(row);

            }

        } else {
            //System.out.println("El rol es en el else es" + rol);
        }
    }
    
    public void Profile(){
        this.views.txt_profile_id.setText(""+id_user);
        this.views.txt_profile_name.setText(full_name_user);
        this.views.txt_profile_phone.setText(telephone_user);
        this.views.txt_profile_address.setText(address_user);
        this.views.txt_profile_email.setText(email_user);
        
             
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.employees_table) {
            int row = views.employees_table.rowAtPoint(e.getPoint());//es necesario para saber en qué fila se ha hecho click con el mouse.
            cleanFields();
            initFields();
            views.txt_employee_id.setText(views.employees_table.getValueAt(row, 0).toString());
            views.txt_employee_fullname.setText(views.employees_table.getValueAt(row, 1).toString());
            views.txt_employee_username.setText(views.employees_table.getValueAt(row, 2).toString());
            views.txt_employee_address.setText(views.employees_table.getValueAt(row, 3).toString());
            views.txt_employee_telephone.setText(views.employees_table.getValueAt(row, 4).toString());
            views.txt_employee_email.setText(views.employees_table.getValueAt(row, 5).toString());
            views.cmb_rol.setSelectedItem(views.employees_table.getValueAt(row, 6).toString());

            //deshabilitar cajas y botones
            views.txt_employee_id.setEditable(false);
            views.txt_employee_password.setEnabled(false);
            views.btn_register_employee.setEnabled(false);

        } else if (e.getSource() == views.jLabelEmployees){
            if(rol_user.equals("Administrador")){
                views.jTabbedPane1.setSelectedIndex(4);
                // limpiar la tabla
                cleanTable();
                cleanFields();
                ListAllEmployees();
            }else{
                views.jTabbedPane1.setEnabledAt(4, false);
                views.jLabelEmployees.setEnabled(false);
                JOptionPane.showMessageDialog(null,"No tienes privilegios");
                
            }
            //deshabilitar la pestaña empleados
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == views.txt_employee_search) {
            cleanTable();
            ListAllEmployees();//acá estamos pasando el texto para listar
        }
    }

    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }

    public void cleanFields() {
        views.txt_employee_id.setText("");
        views.txt_employee_id.setEnabled(true);
        views.btn_register_employee.setEnabled(true);
        views.txt_employee_fullname.setText("");
        views.txt_employee_username.setText("");
        views.txt_employee_address.setText("");
        views.txt_employee_telephone.setText("");
        views.txt_employee_email.setText("");
        views.cmb_rol.setSelectedIndex(0);
        views.txt_employee_password.setText("");
    }

    public void markNullFields() {
        if (views.txt_employee_id.getText().equals("")) {
            views.txt_employee_id.setBackground(new Color(255, 204, 204));
        } else {
            views.txt_employee_id.setBackground(new Color(255, 255, 255));
        }
        if (views.txt_employee_fullname.getText().equals("")) {
            views.txt_employee_fullname.setBackground(new Color(255, 204, 204));
        } else {
            views.txt_employee_fullname.setBackground(new Color(255, 255, 255));
        }
        if (views.txt_employee_username.getText().equals("")) {
            views.txt_employee_username.setBackground(new Color(255, 204, 204));
        } else {
            views.txt_employee_username.setBackground(new Color(255, 255, 255));
        }
        if (views.txt_employee_address.getText().equals("")) {
            views.txt_employee_address.setBackground(new Color(255, 204, 204));
        } else {
            views.txt_employee_address.setBackground(new Color(255, 255, 255));
        }
        if (views.txt_employee_telephone.getText().equals("")) {
            views.txt_employee_telephone.setBackground(new Color(255, 204, 204));
        } else {
            views.txt_employee_telephone.setBackground(new Color(255, 255, 255));
        }
        if (views.txt_employee_email.getText().equals("")) {
            views.txt_employee_email.setBackground(new Color(255, 204, 204));
        } else {
            views.txt_employee_email.setBackground(new Color(255, 255, 255));
        }
        if (views.cmb_rol.getSelectedItem().toString().equals("Seleccione")) {
            views.cmb_rol.setBackground(new Color(255, 204, 204));
        } else {
            views.cmb_rol.setBackground(new Color(255, 255, 255));
        }
        if (String.valueOf(views.txt_employee_password.getPassword()).equals("")) {
            views.txt_employee_password.setBackground(new Color(255, 204, 204));
        } else {
            views.txt_employee_password.setBackground(new Color(255, 255, 255));
        }
    }

    public void initFields() {
        views.txt_employee_id.setBackground(new Color(255, 255, 255));
        views.txt_employee_fullname.setBackground(new Color(255, 255, 255));
        views.txt_employee_username.setBackground(new Color(255, 255, 255));
        views.txt_employee_address.setBackground(new Color(255, 255, 255));
        views.txt_employee_telephone.setBackground(new Color(255, 255, 255));
        views.txt_employee_email.setBackground(new Color(255, 255, 255));
        views.cmb_rol.setBackground(new Color(255, 255, 255));
        views.txt_employee_password.setBackground(new Color(255, 255, 255));

    }
}
