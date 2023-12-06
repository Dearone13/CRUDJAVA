package interfaz;

import controlador.Controlador;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProPanel2 extends JPanel implements ActionListener {

    private JLabel title1, title2, title3;
    private JComboBox<String> cb;
    private JTextField prodName, prodPrice;
    private JButton button;
    private ProPanel1 pnlPanel1;
    private Controlador ctrl;

    public ProPanel2(String title, ProPanel1 pnlPanel1, Controlador ctrl) throws SQLException {
        this.ctrl = ctrl;
        setBorder(new CompoundBorder(new EmptyBorder(0, 0, 0, 0), new TitledBorder(title)));
        setLayout(null);
        this.pnlPanel1 = pnlPanel1;

        title1 = new JLabel("Tipo de producto: ");
        title1.setBounds(10, 10, 315, 25);
        add(title1);
        cb = new JComboBox<String>();
        cb.setBounds(10, 40, 120, 25);
        add(cb);
        //cb.addItemListener(this);
        cb.setVisible(true);
        title2 = new JLabel("Descripción: ");
        title2.setBounds(140, 10, 315, 25);
        add(title2);
        prodName = new JTextField();
        prodName.setBounds(140, 40, 120, 25);
        add(prodName);
        title3 = new JLabel("Precio de compra: ");
        title3.setBounds(270, 10, 315, 25);
        add(title3);
        prodPrice = new JTextField();
        prodPrice.setBounds(270, 40, 120, 25);
        add(prodPrice);

        button = new JButton("...");
        button.setBounds(410, 40, 25, 25);
        button.addActionListener(this);
        add(button);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("...")) {
            try {
                int ID = getID("SELECT * FROM Tipo_Producto WHERE nombreTipo LIKE '" + addSelected() + "%'");
                String amount = getAmount("SELECT COUNT(*) AS cantidad FROM Productos");
                insert(amount,description(),priceBuy(),ID);
                System.out.println("ID: " + ID+" Cantidad: "+amount);
            } catch (SQLException ex) {
                Logger.getLogger(ProPanel2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public String description(){
        return prodName.getText();
    }

    public float priceBuy() {
        return Float.parseFloat(prodPrice.getText());
    }

    public void addChoose(String order) {
        cb.addItem(order);
    }

    public String addSelected() {
        return (String) cb.getSelectedItem();
    }

    public void insert(String CodProducto,String descripcion,float priceBuy,int ID_CodTipo) throws SQLException {
        //float priceSell = (float)(priceBuy+(priceBuy*0.1));
        ctrl.update("INSERT INTO Productos ( CodProducto, Descripción, precioCompra, precioVenta, ID_CodTipo ) VALUES ( '" + CodProducto + "', '" + descripcion + "', '" + priceBuy + "', '" + (float)(priceBuy+(priceBuy*0.1)) + "', '" + ID_CodTipo + "' )");
    }

    public int getID(String sql) throws SQLException {
        ResultSet rst = ctrl.select(sql);
        int ID = 0;
        while (rst.next()) {
            ID = rst.getInt("ID_CodTipo");
        }
        return ID;
    }

    public String getAmount(String sql) throws SQLException {
        ResultSet rst = ctrl.select(sql);
        int amount = 0;
        while (rst.next()) {
            amount = rst.getInt("cantidad");
        }
        return "C"+String.valueOf(amount+1);
    }
}
