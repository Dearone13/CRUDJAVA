package interfaz;

import controlador.Controlador;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProPanel1 extends JPanel implements ActionListener {

    private JTextField tipoProducto;
    private JLabel titulo;
    private JButton button;
    public ProPanel2 pnlpanel2;

    private Controlador ctrl;

   
    public ProPanel1(String title, ProPanel2 pnlPanel2, Controlador ctrl) throws SQLException {
        this.ctrl = ctrl;
        setBorder(new CompoundBorder(new EmptyBorder(0, 0, 0, 0), new TitledBorder(title)));
        setLayout(null);
        this.pnlpanel2 = pnlPanel2;
   
        tipoProducto = new JTextField();
        tipoProducto.setBounds(10, 30, 200, 25);
        add(tipoProducto);

        titulo = new JLabel("Tipo de producto:");
        titulo.setBounds(10, 10, 200, 20);
        add(titulo);

        button = new JButton("...");
        button.setBounds(220, 30, 25, 25);
        add(button);
        button.addActionListener(this);
        selectF("SELECT DISTINCT nombreTipo FROM Tipo_Producto");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("...")) {
            try {
                insert(getType());
                select("SELECT * FROM Tipo_Producto ORDER BY ID_Codtipo DESC LIMIT 1");
            } catch (SQLException ex) {
                Logger.getLogger(ProPanel1.class.getName()).log(Level.SEVERE, null, ex);
            }
            //this.pnlpanel2.addChoose(getType());
        }
    }

    public String getType() {
        return tipoProducto.getText();
    }

    public void insert(String option) throws SQLException {
        ctrl.update("INSERT INTO Tipo_Producto ( ID_CodTipo, nombreTipo ) VALUES ( " + null + ", '" + option + "')");
    }

    public void select(String sql) throws SQLException {
        ResultSet rst = ctrl.select(sql);
        while (rst.next()) {
            //System.out.println(rst.getString("ultimoTipo"));
            this.pnlpanel2.addChoose(rst.getString("nombreTipo"));
        }
    }
    public void selectF(String sql)throws SQLException{
        ResultSet rst = ctrl.select(sql);
        while (rst.next()) {
            this.pnlpanel2.addChoose(rst.getString("nombreTipo"));
        }
    }

}
