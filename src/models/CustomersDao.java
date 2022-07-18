package models;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomersDao {
    //Instanciar la conexión.

    ConnectionMySQL cn = new ConnectionMySQL();
    Connection conn; //libreria connection, para qué sirve?
    PreparedStatement pst;//igual que arriba
    ResultSet rs;//igual que arriba

    //Registrar cliente
    public boolean registrerCustomerQuery(Customers customer) {
        String query = "INSERT INTO customers (id, fullname, address, telephone, email, created, updated) "
                + "VALUES (?,?,?,?,?,?,?)";
        Timestamp datetime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, customer.getId());
            pst.setString(2, customer.getFull_name());
            pst.setString(3, customer.getAddress());
            pst.setString(4, customer.getTelephone());
            pst.setString(5, customer.getEmail());
            pst.setTimestamp(6, datetime);
            pst.setTimestamp(7, datetime);
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar al cliente " + e);
            return false;
        }

    }

    //Listar clientes
    public List listCustomerQuery(String value) {
        List<Customers> list_customers = new ArrayList();
        String query = "SELECT * FROM customers";
        String query_search_customer = "SELECT * FROM customers WHERE id LIKE '%" + value + "%'";
        try {
            conn = cn.getConnection();
            if (value.equalsIgnoreCase("")) {
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            } else {
                pst = conn.prepareStatement(query_search_customer);
                rs = pst.executeQuery();

            }
            while (rs.next()) {
                Customers customer = new Customers();
                customer.setId(rs.getInt("id"));
                customer.setFull_name(rs.getString("fullname"));
                customer.setAddress(rs.getString("address"));
                customer.setTelephone(rs.getString("telephone"));
                customer.setEmail(rs.getString("email"));
                list_customers.add(customer);

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list_customers;
    }

    //Modificar clientes
    public boolean updateCustomerQuery(Customers customer) {
        String query = "UPDATE customers SET fullname = ?, address = ?, telephone = ?, email = ?, updated = ? WHERE id"
                + " = ?";

        Timestamp datetime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, customer.getFull_name());
            pst.setString(2, customer.getAddress());
            pst.setString(3, customer.getTelephone());
            pst.setString(4, customer.getEmail());
            pst.setTimestamp(5, datetime);
            pst.setInt(6, customer.getId());
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar los datos del cliente " + e);
            return false;
        }
    }

    //Eliminar cliente
    public boolean deleteCustomerQuery(int id) {

        String query = "DELETE FROM customers WHERE id = " + id;
        // String query_confirm = "SELECT * FROM customers WHERE id =" + id;
        try {

            if (JOptionPane.showConfirmDialog(null, "Está seguro que desea eliminar el Cliente " + id, " PRECAUCIÓN ",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                conn = cn.getConnection();
                pst = conn.prepareStatement(query);
                pst.execute();
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar al cliente" + e);
            return false;
        }
    }

    public Customers searchCustomerById(int id) {
        String query_search_customer = "SELECT * FROM customers WHERE id = " + id;
        Customers customerById = new Customers();
        customerById.setFull_name("");
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query_search_customer);
            rs = pst.executeQuery();
            
            while (rs.next()) {
                
                customerById.setFull_name(rs.getString("fullname"));
            }
            return customerById;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            customerById.setFull_name("");
            return customerById;
        }
        
    }
}
