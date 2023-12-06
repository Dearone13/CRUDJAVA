package interfaz;

import controlador.Controlador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class ConPanel2 extends JPanel implements ActionListener {

    private JLabel etiqueta2;
    private JTextArea areaTexto3;
    private JTextArea areaTexto4;
    private JComboBox caja2;
    private Controlador ctrl;

    public ConPanel2(String title, Controlador ctrl) throws SQLException {
        this.ctrl = ctrl;
        setBorder(new CompoundBorder(new EmptyBorder(0, 0, 0, 0), new TitledBorder(title)));
        setLayout(null);

        etiqueta2 = new JLabel("Ventas");
        etiqueta2.setBounds(10, 5, 100, 30);
        add(etiqueta2);

        areaTexto3 = new JTextArea();
        areaTexto3.setBounds(10, 65, 170, 265);
        add(areaTexto3);

        areaTexto4 = new JTextArea();
        areaTexto4.setBounds(193, 65, 170, 265);
        add(areaTexto4);

        caja2 = new JComboBox();
        caja2.setBounds(10, 30, 200, 25);
        add(caja2);
        caja2.addActionListener(this);

        selectF("SELECT DISTINCT CodVenta FROM Ventas");
    }

    public void selectF(String sql) throws SQLException {
        ResultSet rst = ctrl.select(sql);
        while (rst.next()) {
            caja2.addItem(rst.getString("CodVenta"));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == caja2) {
            String imp = "";
            String date = "";
            String Codpro ="";
            String option = caja2.getSelectedItem().toString();
            System.out.println(imp + " " + date);
            areaTexto3.setText("");
            areaTexto3.setText("");
            areaTexto4.setText("");
            try {
                imp = getImport("SELECT * FROM Ventas WHERE CodVenta LIKE '" + option + "%'");
                date = getDate("SELECT * FROM Ventas WHERE CodVenta LIKE '" + option + "%'");
                areaTexto3.append("Importe: " + imp + "\n");
                areaTexto3.append("Fecha: " + date + "\n");
                getProName("SELECT * FROM DetalleVenta WHERE CodVenta LIKE '" + option + "%'");
                //areaTexto4.append(product + "\n");
            } catch (SQLException ex) {
                Logger.getLogger(ConPanel2.class.getName()).log(Level.SEVERE, null, ex);
            }

            //System.out.println(option + " " + ID);
        }

    }

    public String getImport(String sql) throws SQLException {
        ResultSet rst = ctrl.select(sql);
        String imp = "";
        while (rst.next()) {
            imp = String.valueOf(rst.getFloat("importe"));
        }
        return imp;
    }

    public String getDate(String sql) throws SQLException {
        ResultSet rst = ctrl.select(sql);
        String date = "";
        while (rst.next()) {
            date = String.valueOf(rst.getDate("fecha"));
        }
        return date;
    }
    public void getProName(String sql)throws SQLException{
        ResultSet rst = ctrl.select(sql);
        String CodPro = "";
        String ProdName = "";
        while (rst.next()) {
            CodPro = rst.getString("CodProducto");
            ResultSet aux = ctrl.select("SELECT * FROM Productos WHERE CodProducto LIKE '" + CodPro + "%'");
            while(aux.next()){  
                ProdName = aux.getString("Descripción");
                areaTexto4.append(ProdName + "\n");
            }
        }
    }
}
