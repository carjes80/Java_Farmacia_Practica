package models;

import java.sql.Timestamp;
import java.sql.Connection; // averiguar para que se usa esta libreria
import java.sql.PreparedStatement; //igual que arriba
import java.sql.ResultSet;//igual que arriba
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class EmployeesDao {

    //Instanciar la conexión.
    ConnectionMySQL cn = new ConnectionMySQL();
    Connection conn; //libreria connection, para qué sirve?
    PreparedStatement pst;//igual que arriba
    ResultSet rs;//igual que arriba

    //Variables para enviar datos entre interfases.
    public static int id_user = 0;
    public static String full_name_user = "";
    public static String username_user = "";
    public static String address_user = "";
    public static String rol_user = "";
    public static String email_user = "";
    public static String telephone_user = "";

    //Método de Login
    public Employees loginQuery(String user, String password) {
        String query = "SELECT * FROM employees WHERE username = ? AND password = ? ";
        Employees employee = new Employees();
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            //Enviar parámetros
            pst.setString(1, user);
            pst.setString(2, password);
            rs = pst.executeQuery();
            //System.out.println("la variable rs guardó"+rs);
            //System.out.println("la variable rs guardó antes"+rs.next());
            if (rs.next()) {
                //System.out.println("la variable rs guardó"+rs.next());
                employee.setId(rs.getInt("id"));
                id_user = employee.getId();
                employee.setFull_name(rs.getString("full_name"));
                full_name_user = employee.getFull_name();
                employee.setUsername(rs.getString("username"));
                username_user = employee.getUsername();
                employee.setAddress(rs.getString("address"));
                address_user = employee.getAddress();
                employee.setTelephone(rs.getString("telephone"));
                telephone_user = employee.getTelephone();
                employee.setEmail(rs.getString("email"));
                email_user = employee.getEmail();
                employee.setRol(rs.getString("rol"));
                rol_user = employee.getRol();

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener al empleado " + e);

        }
        return employee;
    }

    //Método para Registrar el empleado
    public boolean registerEmployeeQuery(Employees employee) {
        String query = "INSERT INTO employees (id, full_name, username, address, telephone, email, password, rol, created,"
                + "updated) VALUES (?,?,?,?,?,?,?,?,?,?)";
        Timestamp datetime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, employee.getId());
            pst.setString(2, employee.getFull_name());
            pst.setString(3, employee.getUsername());
            pst.setString(4, employee.getAddress());
            pst.setString(5, employee.getTelephone());
            pst.setString(6, employee.getEmail());
            pst.setString(7, employee.getPassword());
            pst.setString(8, employee.getRol());
            pst.setTimestamp(9, datetime);
            pst.setTimestamp(10, datetime);
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar al empleado" + e);
            return false;

        }
    }

    //Método para listar empleado
    public List listEmployeesQuery(String value) {
        List<Employees> list_employees = new ArrayList();
        String query = "SELECT * FROM employees ORDER BY rol ASC"; //Este query traerá todos los empleados ordenados por rol en ascendente (ASC)
        String query_search_employee = "SELECT * FROM employees WHERE id LIKE '%" + value + "%'"; // Listará el empleado con el nro de identificación ID*
        try {
            conn = cn.getConnection();
            if (value.equalsIgnoreCase("")) {
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();

            } else {
                pst = conn.prepareStatement(query_search_employee);
                rs = pst.executeQuery();
            }

            while (rs.next()) {
                Employees employee = new Employees();
                employee.setId(rs.getInt("id"));
                employee.setFull_name(rs.getString("full_name"));
                employee.setAddress(rs.getString("address"));
                employee.setTelephone(rs.getString("telephone"));
                employee.setUsername(rs.getString("username"));
                employee.setEmail(rs.getString("email"));
                employee.setRol(rs.getString("rol"));

                list_employees.add(employee);

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());//No se que es e.toString, verificar

        }
        return list_employees;
    }

    //Método para modificar el empleado.
    public boolean updateEmployeeQuery(Employees employee) {
        String query = "UPDATE employees SET full_name = ?, username = ?, address = ?, telephone = ?, email = ?, rol = ?, updated = ? WHERE id = ?";
        Timestamp datetime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, employee.getFull_name());
            pst.setString(2, employee.getUsername());
            pst.setString(3, employee.getAddress());
            pst.setString(4, employee.getTelephone());
            pst.setString(5, employee.getEmail());
            pst.setString(6, employee.getRol());
            pst.setTimestamp(7, datetime);
            pst.setInt(8, employee.getId());
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar los datos del empleado" + e);
            return false;

        }
    }

    // Método para eliminar el empleado.
    public boolean deleteEmployeeQuery(int id, String name) {
        //Employees employee_D = new Employees();
        String query = "DELETE FROM employees WHERE id = " + id;
        String name_to_be_deleted = "";
        String query_confirm = "SELECT * FROM employees WHERE id =" + id;

        try {
                           
            if (JOptionPane.showConfirmDialog(null, "Está seguro que desea eliminar el Empleado " + name, " PRECAUCIÓN ",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                conn = cn.getConnection();
                pst = conn.prepareStatement(query);
                pst.execute();

                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se puede eliminar un empleado que tenga relación con otra tabla" + e);
            return false;
        }

    }

    // Método para cambiar la contraseña.
    public boolean updateEmployeePassword(Employees employee) {
        String query = "UPDATE employees SET password = ? WHERE username = '" + username_user + "'";
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, employee.getPassword());
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error al modificar la contraseña " + e);
            return false;
        }
    }
}
