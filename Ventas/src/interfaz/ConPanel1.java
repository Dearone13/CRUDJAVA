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

public class ConPanel1 extends JPanel implements ActionListener {

    private JLabel etiqueta;
    private JTextArea areaTexto;
    private JTextArea areaTexto2;
    private JComboBox caja;
    private Controlador ctrl;
    private int ID;

    public ConPanel1(String title, Controlador ctrl) throws SQLException {
        this.ctrl = ctrl;
        setBorder(new CompoundBorder(new EmptyBorder(0, 0, 0, 0), new TitledBorder(title)));
        setLayout(null);

        etiqueta = new JLabel("Tipo de producto");
        etiqueta.setBounds(10, 5, 100, 30);
        add(etiqueta);

        areaTexto = new JTextArea();
        areaTexto.setBounds(10, 65, 170, 265);
        add(areaTexto);

        areaTexto2 = new JTextArea();
        areaTexto2.setBounds(193, 65, 170, 265);
        add(areaTexto2);

        caja = new JComboBox();
        caja.setBounds(10, 30, 200, 25);
        add(caja);
        caja.addActionListener(this);
        selectF("SELECT DISTINCT nombreTipo FROM Tipo_Producto");
    }

    public void selectF(String sql) throws SQLException {
        ResultSet rst = ctrl.select(sql);
        while (rst.next()) {
            caja.addItem(rst.getString("nombreTipo"));
        }
    }

    public int getId(String sql) throws SQLException {
        int ID = 0;
        ResultSet rst = ctrl.select(sql);
        while (rst.next()) {
            ID = rst.getInt("ID_CodTipo");
        }
        return ID;
    }

    public void selectType(String sql) throws SQLException {
        ResultSet rst = ctrl.select(sql);
        while (rst.next()) {
            if (rst.getInt("ID_CodTipo") == this.ID) {
                areaTexto.append(rst.getString("Descripción")+"\n");   
                areaTexto2.append(String.valueOf(rst.getFloat("precioVenta")+"\n"));
            }

            System.out.println(rst.getString("Descripción")+" "+String.valueOf(rst.getFloat("precioVenta")+"\n"));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == caja) {
            String option = caja.getSelectedItem().toString();
            try {
                areaTexto.setText("");
                areaTexto2.setText("");
                this.ID = getId("SELECT * FROM Tipo_Producto WHERE nombreTipo LIKE '" + option + "%'");
                selectType("SELECT * FROM Productos WHERE ID_CodTipo LIKE '" + this.ID + "%'");
                System.out.println(option + " " + ID);
            } catch (SQLException ex) {
                Logger.getLogger(ConPanel1.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

}
