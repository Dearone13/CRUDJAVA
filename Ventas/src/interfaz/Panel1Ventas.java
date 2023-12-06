package interfaz;

import controlador.Controlador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class Panel1Ventas extends JPanel implements ActionListener {

    private JLabel lblCV;
    public JTextField CodVenta;
    private JButton btnCrear;
    private Controlador ctrl;
    
    public Panel1Ventas(String title, Controlador ctrl) throws SQLException {
        this.ctrl = ctrl;
        setBorder(new CompoundBorder(new EmptyBorder(0, 0, 0, 0), new TitledBorder(title)));
        setLayout(null);
        
        lblCV = new JLabel("Codigo de Venta:");
        lblCV.setBounds(10, 5, 100, 25);
        add(lblCV);
        
        CodVenta = new JTextField();
        CodVenta.setBounds(10, 25, 100, 25);
        add(CodVenta);
        
        btnCrear = new JButton("...");
        btnCrear.setBounds(120, 25, 20, 25);
        btnCrear.addActionListener(this);
        add(btnCrear);
        
    }
   
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("...")) {            
            try {
                //System.out.println(getCodVenta());
                insert(getCodVenta());
            } catch (SQLException ex) {
                Logger.getLogger(Panel1Ventas.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    public String getCodVenta() {        
        return CodVenta.getText();        
    }

    public void insert(String CodVenta) throws SQLException {
        //System.out.println("Metodo insert: " + CodVenta);
        ctrl.update("INSERT INTO Ventas (CodVenta) VALUES ('" + CodVenta + "')");
        
    }
}
