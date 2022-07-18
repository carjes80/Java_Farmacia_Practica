package controllers;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import views.SystemView;

public class SettingsController implements MouseListener {

    private SystemView views;
    //String color = "152,202,63";

    public SettingsController(SystemView views) {
        this.views = views;
        this.views.jLabelProducts.addMouseListener(this);
        this.views.jLabelSales.addMouseListener(this);
        this.views.jLabelCustomers.addMouseListener(this);
        this.views.jLabelEmployees.addMouseListener(this);
        this.views.jLabelSuppliers.addMouseListener(this);
        this.views.jLabelCategories.addMouseListener(this);
        this.views.jLabelReports.addMouseListener(this);
        this.views.jLabelSettings.addMouseListener(this);
        this.views.jLabelPurchases.addMouseListener(this);
        //views.jLabelProducts.setForeground(Color.yellow);
        icon();
        

    }

    public void icon(){
        System.out.println("JTABBED "+views.jTabbedPane1.getSelectedIndex());
        switch (views.jTabbedPane1.getSelectedIndex()){
            case 0 ->{
                views.jLabelProducts.setForeground(Color.yellow);
                views.jLabelPurchases.setForeground(Color.white);
                views.jLabelSales.setForeground(Color.white);
                views.jLabelCustomers.setForeground(Color.white);
                views.jLabelEmployees.setForeground(Color.white);
                views.jLabelSuppliers.setForeground(Color.white);
                views.jLabelCategories.setForeground(Color.white);
                views.jLabelReports.setForeground(Color.white);
                views.jLabelSettings.setForeground(Color.white);
            }
            case 1 ->{
                views.jLabelProducts.setForeground(Color.white);
                views.jLabelPurchases.setForeground(Color.yellow);
                views.jLabelSales.setForeground(Color.white);
                views.jLabelCustomers.setForeground(Color.white);
                views.jLabelEmployees.setForeground(Color.white);
                views.jLabelSuppliers.setForeground(Color.white);
                views.jLabelCategories.setForeground(Color.white);
                views.jLabelReports.setForeground(Color.white);
                views.jLabelSettings.setForeground(Color.white);}
            case 2 ->{
                views.jLabelProducts.setForeground(Color.white);
                views.jLabelPurchases.setForeground(Color.white);
                views.jLabelSales.setForeground(Color.yellow);
                views.jLabelCustomers.setForeground(Color.white);
                views.jLabelEmployees.setForeground(Color.white);
                views.jLabelSuppliers.setForeground(Color.white);
                views.jLabelCategories.setForeground(Color.white);
                views.jLabelReports.setForeground(Color.white);
                views.jLabelSettings.setForeground(Color.white);}
            case 3 ->{
                views.jLabelProducts.setForeground(Color.white);
                views.jLabelPurchases.setForeground(Color.white);
                views.jLabelSales.setForeground(Color.white);
                views.jLabelCustomers.setForeground(Color.yellow);
                views.jLabelEmployees.setForeground(Color.white);
                views.jLabelSuppliers.setForeground(Color.white);
                views.jLabelCategories.setForeground(Color.white);
                views.jLabelReports.setForeground(Color.white);
                views.jLabelSettings.setForeground(Color.white);}
            case 4 ->{
                views.jLabelProducts.setForeground(Color.white);
                views.jLabelPurchases.setForeground(Color.white);
                views.jLabelSales.setForeground(Color.white);
                views.jLabelCustomers.setForeground(Color.white);
                views.jLabelEmployees.setForeground(Color.yellow);
                views.jLabelSuppliers.setForeground(Color.white);
                views.jLabelCategories.setForeground(Color.white);
                views.jLabelReports.setForeground(Color.white);
                views.jLabelSettings.setForeground(Color.white);}
            case 5 ->{
                views.jLabelProducts.setForeground(Color.white);
                views.jLabelPurchases.setForeground(Color.white);
                views.jLabelSales.setForeground(Color.white);
                views.jLabelCustomers.setForeground(Color.white);
                views.jLabelEmployees.setForeground(Color.white);
                views.jLabelSuppliers.setForeground(Color.yellow);
                views.jLabelCategories.setForeground(Color.white);
                views.jLabelReports.setForeground(Color.white);
                views.jLabelSettings.setForeground(Color.white);}
            case 6 ->{
                views.jLabelProducts.setForeground(Color.white);
                views.jLabelPurchases.setForeground(Color.white);
                views.jLabelSales.setForeground(Color.white);
                views.jLabelCustomers.setForeground(Color.white);
                views.jLabelEmployees.setForeground(Color.white);
                views.jLabelSuppliers.setForeground(Color.white);
                views.jLabelCategories.setForeground(Color.yellow);
                views.jLabelReports.setForeground(Color.white);
                views.jLabelSettings.setForeground(Color.white);}
            case 7 ->{
                views.jLabelProducts.setForeground(Color.white);
                views.jLabelPurchases.setForeground(Color.white);
                views.jLabelSales.setForeground(Color.white);
                views.jLabelCustomers.setForeground(Color.white);
                views.jLabelEmployees.setForeground(Color.white);
                views.jLabelSuppliers.setForeground(Color.white);
                views.jLabelCategories.setForeground(Color.white);
                views.jLabelReports.setForeground(Color.yellow);
                views.jLabelSettings.setForeground(Color.white);}
            case 8 ->{
                views.jLabelProducts.setForeground(Color.white);
                views.jLabelPurchases.setForeground(Color.white);
                views.jLabelSales.setForeground(Color.white);
                views.jLabelCustomers.setForeground(Color.white);
                views.jLabelEmployees.setForeground(Color.white);
                views.jLabelSuppliers.setForeground(Color.white);
                views.jLabelCategories.setForeground(Color.white);
                views.jLabelReports.setForeground(Color.white);
                views.jLabelSettings.setForeground(Color.yellow);}
            
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        icon();
        /* if (views.jTabbedPane1.getSelectedIndex() == 0) {
            views.jPanelProducts.setBackground(new Color(51, 0, 0));
        } else if (e.getSource() == views.jLabelPurchases && views.jTabbedPane1.getSelectedIndex() == 1) {
            views.jPanelPurchases.setBackground(new Color(51, 0, 0));
        } else if (e.getSource() == views.jLabelSales && views.jTabbedPane1.getSelectedIndex() == 2) {
            views.jPanelSales.setBackground(new Color(51, 0, 0));
        } else if (e.getSource() == views.jLabelCustomers && views.jTabbedPane1.getSelectedIndex() == 3) {
            views.jPanelCustomers.setBackground(new Color(51, 0, 0));
        } else if (e.getSource() == views.jLabelEmployees && views.jTabbedPane1.getSelectedIndex() == 4 ) {
            views.jPanelEmployees.setBackground(new Color(51, 0, 0));
        } else if (e.getSource() == views.jLabelSuppliers && views.jTabbedPane1.getSelectedIndex() == 5) {
            views.jPanelSuppliers.setBackground(new Color(51, 0, 0));
        } else if (e.getSource() == views.jLabelCategories && views.jTabbedPane1.getSelectedIndex() == 6) {
            views.jPanelCategories.setBackground(new Color(51, 0, 0));
        } else if (e.getSource() == views.jLabelReports && views.jTabbedPane1.getSelectedIndex() == 7) {
            views.jPanelReports.setBackground(new Color(51, 0, 0));
        } else if (e.getSource() == views.jLabelSettings && views.jTabbedPane1.getSelectedIndex() == 8) {
            views.jPanelSettings.setBackground(new Color(51, 0, 0));
        }else if (e.getSource() == views.jLabelProducts) {
            views.jPanelProducts.setBackground(new Color(18, 45, 61));
        } else if (e.getSource() == views.jLabelSales) {
            views.jPanelSales.setBackground(new Color(18, 45, 61));
        } else if (e.getSource() == views.jLabelCustomers) {
            views.jPanelCustomers.setBackground(new Color(18, 45, 61));
        } else if (e.getSource() == views.jLabelEmployees) {
            views.jPanelEmployees.setBackground(new Color(18, 45, 61));
        } else if (e.getSource() == views.jLabelSuppliers) {
            views.jPanelSuppliers.setBackground(new Color(18, 45, 61));
        } else if (e.getSource() == views.jLabelCategories) {
            views.jPanelCategories.setBackground(new Color(18, 45, 61));
        } else if (e.getSource() == views.jLabelReports) {
            views.jPanelReports.setBackground(new Color(18, 45, 61));
        } else if (e.getSource() == views.jLabelSettings) {
            views.jPanelSettings.setBackground(new Color(18, 45, 61));
        } else if (e.getSource() == views.jLabelPurchases) {
            views.jPanelPurchases.setBackground(new Color(18, 45, 61));
        }*/
        //ELIMINADO throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        icon();
        // ELIMINADO throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        icon();
        /*//ELIMINADO  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if (e.getSource() == views.jLabelProducts && views.jTabbedPane1.getSelectedIndex() == 0) {
            views.jPanelProducts.setBackground(new Color(51, 0, 0));
        } else if (e.getSource() == views.jLabelPurchases && views.jTabbedPane1.getSelectedIndex() == 1) {
            views.jPanelPurchases.setBackground(new Color(51, 0, 0));
        } else if (e.getSource() == views.jLabelSales && views.jTabbedPane1.getSelectedIndex() == 2) {
            views.jPanelSales.setBackground(new Color(51, 0, 0));
        } else if (e.getSource() == views.jLabelCustomers && views.jTabbedPane1.getSelectedIndex() == 3) {
            views.jPanelCustomers.setBackground(new Color(51, 0, 0));
        } else if (e.getSource() == views.jLabelEmployees && views.jTabbedPane1.getSelectedIndex() == 4) {
            views.jPanelEmployees.setBackground(new Color(51, 0, 0));
        } else if (e.getSource() == views.jLabelSuppliers && views.jTabbedPane1.getSelectedIndex() == 5) {
            views.jPanelSuppliers.setBackground(new Color(51, 0, 0));
        } else if (e.getSource() == views.jLabelCategories && views.jTabbedPane1.getSelectedIndex() == 6) {
            views.jPanelCategories.setBackground(new Color(51, 0, 0));
        } else if (e.getSource() == views.jLabelReports && views.jTabbedPane1.getSelectedIndex() == 7) {
            views.jPanelReports.setBackground(new Color(51, 0, 0));
        } else if (e.getSource() == views.jLabelSettings && views.jTabbedPane1.getSelectedIndex() == 8) {
            views.jPanelSettings.setBackground(new Color(51, 0, 0));
        }*/
    }

    /*public String selected()
    {
        
        switch (views.jTabbedPane1.getSelectedIndex()){
            case 0 -> color = "102,102,0";
            
        }
            return color;*/
    @Override
    public void mouseEntered(MouseEvent e) {
        icon();
        //ELIMINADO   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        //views.jPanelSettings.setBackground(new Color(152, 202, 63));
        if (e.getSource() == views.jLabelProducts ) {
            views.jPanelProducts.setBackground(new Color(152, 202, 63));
        } else if (e.getSource() == views.jLabelPurchases ) {
            views.jPanelPurchases.setBackground(new Color(152, 202, 63));
        } else if (e.getSource() == views.jLabelSales ) {
            views.jPanelSales.setBackground(new Color(152, 202, 63));
        } else if (e.getSource() == views.jLabelCustomers) {
            views.jPanelCustomers.setBackground(new Color(152, 202, 63));
        } else if (e.getSource() == views.jLabelEmployees) {
            views.jPanelEmployees.setBackground(new Color(152, 202, 63));
        } else if (e.getSource() == views.jLabelSuppliers ) {
            views.jPanelSuppliers.setBackground(new Color(152, 202, 63));
        } else if (e.getSource() == views.jLabelCategories ) {
            views.jPanelCategories.setBackground(new Color(152, 202, 63));
        } else if (e.getSource() == views.jLabelReports) {
            views.jPanelReports.setBackground(new Color(152, 202, 63));
        } else if (e.getSource() == views.jLabelSettings) {
            views.jPanelSettings.setBackground(new Color(152, 202, 63));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        icon();
        //ELIMINADO   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.[18,45,61]
        if (e.getSource() == views.jLabelProducts ) {
            views.jPanelProducts.setBackground(new Color(18, 45, 61));
        } else if (e.getSource() == views.jLabelSales ) {
            views.jPanelSales.setBackground(new Color(18, 45, 61));
        } else if (e.getSource() == views.jLabelCustomers ) {
            views.jPanelCustomers.setBackground(new Color(18, 45, 61));
        } else if (e.getSource() == views.jLabelEmployees ) {
            views.jPanelEmployees.setBackground(new Color(18, 45, 61));
        } else if (e.getSource() == views.jLabelSuppliers ) {
            views.jPanelSuppliers.setBackground(new Color(18, 45, 61));
        } else if (e.getSource() == views.jLabelCategories) {
            views.jPanelCategories.setBackground(new Color(18, 45, 61));
        } else if (e.getSource() == views.jLabelReports ) {
            views.jPanelReports.setBackground(new Color(18, 45, 61));
        } else if (e.getSource() == views.jLabelSettings) {
            views.jPanelSettings.setBackground(new Color(18, 45, 61));
        } else if (e.getSource() == views.jLabelPurchases) {
            views.jPanelPurchases.setBackground(new Color(18, 45, 61));
        }
    }
}
