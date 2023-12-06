package interfaz;

import controlador.Controlador;
import java.sql.SQLException;
import javax.swing.JFrame;
import util.Util;

public class Ventas extends JFrame
{
    private Panel1Ventas pnlPanel1; 
    private Panel2Ventas pnlPanel2;
    private Controlador ctrl;   
    
    public Ventas(Controlador ctrl) throws SQLException 
    { this.ctrl = ctrl;
      setTitle( "Interfaz gráfica..." );  
      getContentPane( ).setLayout( null );

      
      pnlPanel1 = new Panel1Ventas("",ctrl); 
      pnlPanel1.setBounds(10, 10, 375, 60);
      getContentPane().add(pnlPanel1);

      pnlPanel2 = new Panel2Ventas("",this.ctrl,this.pnlPanel1); 
      pnlPanel2.setBounds(10, 70, 375, 340);
      getContentPane().add(pnlPanel2);
              

   // Propiedades de la interfaz.   
      setSize( 410, 460 );      
      setResizable( false );
      //setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
      
   // Centrar ventana.
      Util.centrarVentana( this );     
    }

//    public static void main(String[] args) 
//    { Ventas frmMain = new Ventas();
//      frmMain.setVisible(true);
//    }
    
}