
package controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import models.Customers;
import models.CustomersDao;
import views.SystemView;


public class ProductsController implements MouseListener {
    private Customers customer;
    private CustomersDao customer_dao;
    private SystemView views;

    public ProductsController(Customers customer, CustomersDao customer_dao, SystemView views) {
        this.customer = customer;
        this.customer_dao = customer_dao;
        this.views = views;
        
        //label
        this.views.jLabelCustomers.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
      
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
}
