/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz;
import controlador.Controlador;
import java.awt.Color;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
/**
 *
 * @author RSP-L23-LT-026
 */
public class Consultas extends JFrame{
   
    private ConPanel1 pnlConpanel1;
    private ConPanel2 pnlConpanel2;
    private Controlador ctrl;
    public Consultas(Controlador ctrl) throws SQLException {
        this.ctrl = ctrl;
        
        setTitle( "Taller 5 - Consultas" );  
        getContentPane( ).setLayout( null );
      
        pnlConpanel1 = new ConPanel1("", this.ctrl);
        pnlConpanel1.setBounds(10, 10, 375, 340);
        getContentPane().add( pnlConpanel1);
        
        pnlConpanel2 = new ConPanel2("", this.ctrl);
        pnlConpanel2.setBounds(400, 10, 375, 340);
        getContentPane().add( pnlConpanel2);
        
        setSize(800,400);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
         setResizable( false );
        
    }

}
