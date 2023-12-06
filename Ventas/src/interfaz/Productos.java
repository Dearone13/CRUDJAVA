package interfaz;

import controlador.Controlador;
import java.sql.SQLException;
import javax.swing.*;

import java.util.Scanner;
public class Productos extends JFrame {
    
    private ProPanel2 pnlPanel2;
    private ProPanel1 pnlPanel1;
    private Panel2Ventas pnlVentas2;
    private Controlador ctrl;
    
    
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        Productos frProd = new Productos();
//        frProd.setVisible(true);
//    }
    
    public Productos(Controlador ctrl)throws SQLException{
      this.ctrl = ctrl;
      setTitle( "Taller-Productos" );  
      getContentPane( ).setLayout( null );
      
      pnlPanel2 = new ProPanel2("",pnlPanel1,this.ctrl);
      pnlPanel2.setBounds(10, 80, 460, 80);
      getContentPane().add(pnlPanel2);
      
      pnlPanel1 = new ProPanel1("",pnlPanel2, this.ctrl);
      pnlPanel1.setBounds(10, 10, 460, 60);
      getContentPane().add(pnlPanel1);
      
      setSize( 500, 210 );      
      setResizable( false );
      //setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
      setVisible(true);
    }
    
    
 
}