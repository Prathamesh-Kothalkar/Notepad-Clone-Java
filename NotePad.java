import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.undo.UndoManager;

class NotePad extends JFrame {
    JTextArea textarea = new JTextArea();
    JScrollPane scrollpane = new JScrollPane(textarea);
    NMenu menu;
    boolean saved=true;
    boolean opened=false;
    UndoManager undoManager=new UndoManager();

    NotePad(){
        Image img = Toolkit.getDefaultToolkit().getImage("logo3.png");
        this.setIconImage(img);
        this.setSize(1000,700);
        this.setTitle("Notepad - Untitled Text");
        menu = new NMenu(this);
        this.setJMenuBar(menu);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(scrollpane,"Center");
        this.textarea.setFont(new Font("Arial",Font.PLAIN,25));  
    }

    public static void main(String args[]){
        new NotePad();
 
    }
}