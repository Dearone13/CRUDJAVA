package interfaz;

import controlador.Controlador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.time.LocalDate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTextArea;
import jdk.vm.ci.code.CodeUtil;

public class Panel2Ventas extends JPanel implements ActionListener {

    private JLabel lblTP;
    private JLabel lblDV;
    private DefaultListModel<String> CodP, CodDT, CodG;
    private JList<String> CodProductos, CodDetVenta, CodGenerar;
    private JComboBox txtTProducto;
    private JButton btnAgregar, btnQuitar, btnGenerarF;
    private Controlador ctrl;
    private Panel1Ventas pnlPanel1Ventas;
    private int ID;

    public Panel2Ventas(String title, Controlador ctrl, Panel1Ventas pnlPanel1Ventas) throws SQLException {
        this.pnlPanel1Ventas = pnlPanel1Ventas;
        this.ctrl = ctrl;
        setBorder(new CompoundBorder(new EmptyBorder(0, 0, 0, 0), new TitledBorder(title)));
        setLayout(null);

        lblTP = new JLabel("Tipo Producto:");
        lblTP.setBounds(10, 5, 100, 25);
        add(lblTP);

        lblDV = new JLabel("Detalle de Venta:");
        lblDV.setBounds(205, 50, 100, 25);
        add(lblDV);

        CodP = new DefaultListModel<>();
        //CodP.addElement("Item1");
        CodProductos = new JList<>(CodP);
        CodProductos.setBounds(10, 80, 125, 250);
        add(CodProductos);

        CodDT = new DefaultListModel<>();
        CodDetVenta = new JList<>(CodDT);
        CodDetVenta.setBounds(205, 80, 125, 250);
        add(CodDetVenta);

        CodG = new DefaultListModel<>();
        CodGenerar = new JList<>(CodG);
        CodGenerar.setBounds(340, 80, 25, 250);
        add(CodGenerar);

        btnAgregar = new JButton(">>>");
        btnAgregar.setBounds(142, 130, 55, 25);
        btnAgregar.addActionListener(this);
        add(btnAgregar);

        btnQuitar = new JButton("<<<");
        btnQuitar.setBounds(142, 240, 55, 25);
        btnQuitar.addActionListener(this);
        add(btnQuitar);

        btnGenerarF = new JButton("...");
        btnGenerarF.setBounds(340, 50, 25, 25);
        btnGenerarF.addActionListener(this);
        add(btnGenerarF);

        txtTProducto = new JComboBox();
        txtTProducto.setBounds(10, 25, 125, 25);
        add(txtTProducto);
        txtTProducto.addActionListener(this);

        selectF("SELECT DISTINCT nombreTipo FROM Tipo_Producto");

        //Items();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == txtTProducto) {
            try {
                CodP.removeAllElements();
                String option = txtTProducto.getSelectedItem().toString();
                this.ID = getId("SELECT * FROM Tipo_Producto WHERE nombreTipo LIKE '" + option + "%'");
                selectType("SELECT * FROM Productos WHERE ID_CodTipo LIKE '" + this.ID + "%'");
                System.out.println(option + " " + ID);
            } catch (SQLException ex) {
                Logger.getLogger(Panel2Ventas.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getActionCommand().equals(">>>")) {
            String Pasar, Recibir;
            if (CodProductos.getSelectedIndex() == -1) {
                Pasar = "";
            } else {
                Pasar = CodProductos.getSelectedValue();
            }
            int N = CodDT.getSize();

            //System.out.println(N);
            boolean eE = false;
            for (int i = 0; i < N; i++) {
                Recibir = CodDT.getElementAt(i);
                // Verificar si el elemento ya existe en la lista
                if (Pasar.equals(Recibir)) {
                    String v = CodG.getElementAt(i);
                    int nV = Integer.parseInt(v);
                    nV++;
                    String vf = Integer.toString(nV);
                    CodG.setElementAt(vf, i);
                    CodGenerar.repaint();
                    // Marcar que el elemento ya existe
                    eE = true;
                    // No es necesario seguir buscando
                    break;
                }
            }
// Si el elemento no existe, añadirlo a la lista
            if (!eE) {
                CodDT.addElement(Pasar);
                CodG.addElement("1");
                CodDetVenta.repaint();
                CodGenerar.repaint();
            }

        } else if (e.getActionCommand().equals("<<<")) {
            if (CodDetVenta.getSelectedIndex() != -1) {
                int select = CodDetVenta.getSelectedIndex();
                CodDT.remove(select);
                CodG.remove(select);

            }

        } else if (e.getActionCommand().equals("...")) {
            int N = CodDT.getSize();
            String CodProd = "";
            float importe = 0;

            try {
                for (int i = 0; i < N; i++) {
                    String product = CodDT.elementAt(i);
                    int cant = Integer.parseInt(CodG.elementAt(i));
                    CodProd = selectProduct("SELECT * FROM Productos WHERE Descripción LIKE '" + product + "%'");
                    importe += getPriceSell("SELECT * FROM Productos WHERE Descripción LIKE '" + product + "%'")*cant;
                    insert(this.pnlPanel1Ventas.getCodVenta(), CodProd, cant);
                }
                //System.out.println("CodProducto: " + CodProd);
                //System.out.println("CodVenta " + this.pnlPanel1Ventas.getCodVenta());
                LocalDate fecha = LocalDate.now(); // Create a date object
                //System.out.println(fecha+" "+importe); // Display the current date
                ctrl.update( "UPDATE VENTAS SET fecha = '" + fecha + "' WHERE CodVenta = '" + this.pnlPanel1Ventas.getCodVenta()+"'");
                ctrl.update( "UPDATE VENTAS SET importe = '" + importe + "' WHERE CodVenta = '" + this.pnlPanel1Ventas.getCodVenta()+"'");
                CodDT.removeAllElements();
                CodG.removeAllElements();
                this.pnlPanel1Ventas.CodVenta.setText(null);
                
            } catch (SQLException ex) {
                Logger.getLogger(Panel2Ventas.class.getName()).log(Level.SEVERE, null, ex);
            }
            

            //select();
        }

    }

    public void selectF(String sql) throws SQLException {
        ResultSet rst = ctrl.select(sql);
        while (rst.next()) {
            txtTProducto.addItem(rst.getString("nombreTipo"));
        }
    }

    public void selectType(String sql) throws SQLException {
        ResultSet rst = ctrl.select(sql);
        while (rst.next()) {
            if (rst.getInt("ID_CodTipo") == this.ID) {
                CodP.addElement(rst.getString("Descripción"));
            }

            //System.out.println(rst.getString("ultimoTipo"));
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

    public String getTProducto() {
        String TProducto;
        return TProducto = txtTProducto.getSelectedItem().toString();
    }

    public float getPriceSell(String sql) throws SQLException {
        ResultSet rst = ctrl.select(sql);
        float price = 0;
        while (rst.next()) {
            price = rst.getFloat("precioVenta");
        }
        return price;
    }

    public void insert(String CodVenta, String CodProducto, int cantidad) throws SQLException {
        ctrl.update("INSERT INTO DetalleVenta ( CodVenta, CodProducto, cantidad ) VALUES ( '" + CodVenta + "', '" + CodProducto + "', '" + cantidad + "')");
    }

    public String selectProduct(String sql) throws SQLException {
        ResultSet rst = ctrl.select(sql);
        String CodProd = "";
        while (rst.next()) {
            CodProd = rst.getString("CodProducto");
        }
        return CodProd;
    }

}
