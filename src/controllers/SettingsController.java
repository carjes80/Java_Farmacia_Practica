
package controllers;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import views.SystemView;

public class SettingsController implements MouseListener{
private SystemView views;

public SettingsController(SystemView views){
    this.views =views;
    this.views.jLabelProducts.addMouseListener(this);
    this.views.jLabelSales.addMouseListener(this);
    this.views.jLabelCustomers.addMouseListener(this);
    this.views.jLabelEmployees.addMouseListener(this);
    this.views.jLabelSuppliers.addMouseListener(this);
    this.views.jLabelCategories.addMouseListener(this);
    this.views.jLabelReports.addMouseListener(this);
    this.views.jLabelSettings.addMouseListener(this);
    this.views.jLabelPurchases.addMouseListener(this);
}

    @Override
    public void mouseClicked(MouseEvent e) {
       //ELIMINADO throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // ELIMINADO throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      //ELIMINADO  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
     //ELIMINADO   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if(e.getSource()==views.jLabelProducts){
                views.jPanelProducts.setBackground(new Color(152,202,63));
        }else if (e.getSource()==views.jLabelSales){
                   views.jPanelSales.setBackground(new Color(152,202,63));
        }else if (e.getSource()==views.jLabelEmployees){
                   views.jPanelEmployees.setBackground(new Color(152,202,63));
        }else if (e.getSource()==views.jLabelSuppliers){
                   views.jPanelSuppliers.setBackground(new Color(152,202,63));
        }else if (e.getSource()==views.jLabelCategories){
                   views.jPanelCategories.setBackground(new Color(152,202,63));
        }else if (e.getSource()==views.jLabelCustomers){
                   views.jPanelCustomers.setBackground(new Color(152,202,63));
        }else if (e.getSource()==views.jLabelReports){
                   views.jPanelReports.setBackground(new Color(152,202,63));
        }else if (e.getSource()==views.jLabelSettings){
                   views.jPanelSettings.setBackground(new Color(152,202,63));
        }else if (e.getSource()==views.jLabelPurchases){
                   views.jPanelPurchases.setBackground(new Color(152,202,63));
    }
    }
    @Override
    public void mouseExited (MouseEvent e) {
     //ELIMINADO   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.[18,45,61]
             if(e.getSource()==views.jLabelProducts){
                views.jPanelProducts.setBackground(new Color(18,45,61));
        }else if (e.getSource()==views.jLabelSales){
                   views.jPanelSales.setBackground(new Color(18,45,61));
        }else if (e.getSource()==views.jLabelCustomers){
                   views.jPanelCustomers.setBackground(new Color(18,45,61));
        }else if (e.getSource()==views.jLabelEmployees){
                   views.jPanelEmployees.setBackground(new Color(18,45,61));
        }else if (e.getSource()==views.jLabelSuppliers){
                   views.jPanelSuppliers.setBackground(new Color(18,45,61));
        }else if (e.getSource()==views.jLabelCategories){
                   views.jPanelCategories.setBackground(new Color(18,45,61));
        }else if (e.getSource()==views.jLabelReports){
                   views.jPanelReports.setBackground(new Color(18,45,61));
        }else if (e.getSource()==views.jLabelSettings){
                   views.jPanelSettings.setBackground(new Color(18,45,61));
        }else if (e.getSource()==views.jLabelPurchases){
                   views.jPanelPurchases.setBackground(new Color(18,45,61));           
    }
    }   
}
