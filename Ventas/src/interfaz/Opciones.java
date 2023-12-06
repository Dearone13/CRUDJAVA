/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz;

import controlador.Controlador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Opciones extends JFrame implements ActionListener {

    private JButton productos;
    private JButton ventas;
    private JButton consultas;
    private JPanel opPanel;
    private Controlador ctrl;

    public static void main(String[] args) throws SQLException {
        Opciones op = new Opciones(new Controlador());
        op.setVisible(true);

    }

    public Opciones(Controlador ctrl) throws SQLException {
        this.ctrl = ctrl;
        if (ctrl.connect() != null) {

            setSize(290, 350);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setTitle("Principal");
            setLocationRelativeTo(null);
            putPanels();
            addButtons();
        } else {
            JOptionPane.showMessageDialog(null, "Mysql: La conexion no fue establecida...");

        }

    }

    public void putPanels() {
        opPanel = new JPanel();
        opPanel.setLayout(null);
        //panel.setBackground(Color.GRAY);
        this.getContentPane().add(opPanel);
    }

    public void addButtons() {
        productos = new JButton("Productos");
        productos.setBounds(10, 10, 250, 90);
        opPanel.add(productos);
        productos.addActionListener(this);
        ventas = new JButton("Ventas");
        ventas.setBounds(10, 110, 250, 90);
        opPanel.add(ventas);
        ventas.addActionListener(this);
        consultas = new JButton("Consultas");
        consultas.setBounds(10, 210, 250, 90);
        opPanel.add(consultas);
        consultas.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Productos")) {
            Productos prod;
            try {
                prod = new Productos(this.ctrl);
                prod.setVisible(true);
            } catch (SQLException ed) {
                Logger.getLogger(Opciones.class.getName()).log(Level.SEVERE, null, ed);

            }
        } else if (e.getActionCommand().equals("Ventas")) {
            Ventas ven;
            try {
                ven = new Ventas(this.ctrl);
                ven.setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(Opciones.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            Consultas consul;
            try {
                consul = new Consultas(this.ctrl);
                consul.setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(Opciones.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }

}
