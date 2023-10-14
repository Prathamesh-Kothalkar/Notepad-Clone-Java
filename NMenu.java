import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.undo.UndoManager;
import java.io.*;
import java.util.*;
import java.awt.Toolkit;
import java.awt.datatransfer.*;
import javax.swing.text.Document;



class NMenu extends JMenuBar{
    NotePad parentFrame;
    File currentFile;
    UndoManager undoManager = new UndoManager();
    
    JMenu file = new JMenu("File");
    JMenu edit = new JMenu("Edit");
    JMenu setting = new JMenu("Setting");
    JMenu Help = new JMenu("Help");

///File Menuitem
    JMenuItem New = new JMenuItem("New");
    JMenuItem open = new JMenuItem("Open");
    JMenuItem saveas = new JMenuItem("Save as");
    JMenuItem save = new JMenuItem("Save");
    JMenuItem print = new JMenuItem("Print");
    JMenuItem exit = new JMenuItem("Exit");

/////Edit Menu Item
    JMenuItem undo = new JMenuItem("Undo");
    JMenuItem redo = new JMenuItem("Redo");
    JMenuItem copy = new JMenuItem("Copy");
    JMenuItem cut = new JMenuItem("Cut");
    JMenuItem paste = new JMenuItem("Paste");

/////Setting Menuitem
    JMenuItem font = new JMenuItem("Font Size");
    JMenuItem background = new JMenuItem("Background");

////Help
    JMenuItem info = new JMenuItem("Info");
    JMenu about = new JMenu("About");
    JMenuItem dev = new JMenuItem("About Developers");

    

    public NMenu(NotePad parent){
        parentFrame=parent;
        
        Document doc = parent.textarea.getDocument();
        parent.undoManager = new UndoManager();
        doc.addUndoableEditListener(parent.undoManager);
        //setPreferredSize(new Dimension(150,30));

       
        file.setFont(new Font("Arial",Font.PLAIN,18));

        //New.setFont(new Font("Arial",Font.PLAIN,17));
        open.setFont(new Font("Arial",Font.PLAIN,17));
        save.setFont(new Font("Arial",Font.PLAIN,17));
        saveas.setFont(new Font("Arial",Font.PLAIN,17));
        print.setFont(new Font("Arial",Font.PLAIN,17));
        exit.setFont(new Font("Arial",Font.PLAIN,17));

       // file.add(New);
        file.add(open);
        file.add(saveas);
        file.add(save);
        file.addSeparator();
        file.add(print);
        file.add(exit);
        
        edit.setFont(new Font("Arial",Font.PLAIN,18));
        undo.setFont(new Font("Arial",Font.PLAIN,17));
        redo.setFont(new Font("Arial",Font.PLAIN,17));
        copy.setFont(new Font("Arial",Font.PLAIN,17));
        cut.setFont(new Font("Arial",Font.PLAIN,17));
        paste.setFont(new Font("Arial",Font.PLAIN,17));
        edit.add(undo);
       
        edit.add(redo);
        edit.addSeparator();
        edit.add(copy);
        edit.add(cut);
        edit.add(paste);

        setting.setFont(new Font("Arial",Font.PLAIN,18));
        font.setFont(new Font("Arial",Font.PLAIN,17));
        background.setFont(new Font("Arial",Font.PLAIN,17));
        setting.add(font);
        setting.add(background);

        Help.setFont(new Font("Arial",Font.PLAIN,18));
        info.setFont(new Font("Arial",Font.PLAIN,17));
        dev.setFont(new Font("Arial",Font.PLAIN,17));
        about.setFont(new Font("Arial",Font.PLAIN,17));
        Help.add(info);
        about.add(dev);
        Help.add(about);

        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        saveas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.SHIFT_DOWN_MASK));
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
        redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK));
        print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
        font.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));
        background.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_DOWN_MASK));
        info.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_DOWN_MASK));
        dev.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));

        add(file);
        add(edit);
        add(setting);
        add(Help);

        open.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
				if(!parent.saved){
					int choice=JOptionPane.showConfirmDialog(parent,"Do you want to save the file?","Save file",
							JOptionPane.YES_NO_CANCEL_OPTION);

					if(choice==JOptionPane.CANCEL_OPTION || choice==JOptionPane.CLOSED_OPTION )
						return;

					if(choice==JOptionPane.YES_OPTION){
						saveFile();
						parent.saved=true;
					}
				}
				JFileChooser chooser=new JFileChooser();
				chooser.showOpenDialog(parent);
				chooser.setMultiSelectionEnabled(false);
				parent.textarea.setText("");
				currentFile=chooser.getSelectedFile();
				BufferedReader reader=new BufferedReader(new FileReader(currentFile));
				parent.setTitle("NotePad - "+currentFile);

				String line=reader.readLine();
				while(line!=null){
					parent.textarea.append(line+"\n");

					line=reader.readLine();
				}
				reader.close();
				parentFrame.opened=true;
				saveas.setEnabled(true);
			}
			catch(Exception exc){System.out.println(exc);};
            }
        });///End of open action listener 

        save.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
			saveFile();
            }
		});//end of save actionlistener

        saveas.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
				JFileChooser chooser=new JFileChooser();
				chooser.showSaveDialog(parentFrame);
				File newFile=chooser.getSelectedFile();
				PrintWriter pw=new PrintWriter(newFile);
				pw.print(parentFrame.textarea.getText());
				pw.close();
				JOptionPane.showMessageDialog(parent,"File Saved Successfully ! ! !");
			}
			catch(Exception exc){System.out.println(exc);}
            }
        });//end of saveas actionListener

        print.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                     parent.textarea.print();
                }
                catch(Exception pe){
                    System.out.print(e);
                }
               
            }
        });//end of print actionlistener
       
       font.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
               String size= JOptionPane.showInputDialog(parent,"Enter Font Size");
               int s = Integer.parseInt(size);
               parent.textarea.setFont(new Font("Arial",Font.PLAIN,s));
            }
       });//end of font actionlistener

       info.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            String str = "Features:\n- Create and edit text documents.\n- Save documents to your preferred location.\n- Open and edit existing text files.\n- Customize text font and background color.\n- Undo and redo actions for easy editing.\n- Copy, cut, and paste text content.\n- Keyboard shortcuts for common actions.\nDeveloped by: Prathamesh Kothalkar";
            JOptionPane.showMessageDialog(parent,str,"Information",JOptionPane.INFORMATION_MESSAGE);
        }
       });//end of info actionlistener

        dev.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            JDialog j = new JDialog(parent,"About",true);
            j.setLocation(350,150);
            j.setSize(400,400);
            j.add(new JLabel("<html><h1>NotePad Text Editor</h1><p><strong>Version:Cloned</strong> 1.0</p><h2>Developer Information</h2><p><strong>Developed by:</strong>Prathamesh Kothalkar</p><p><strong>Website:</strong> <a href='https://github.com/prathamesh-kothalkar'>GitHub</a></p></html>"),"Center");
            j.setVisible(true);
        }
       });//end of dev actionlistener

       background.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            Color c = JColorChooser.showDialog(null,"Select Background",new Color(255,0,0));
            parent.textarea.setBackground(c);
        }
       });//end of background actionlistener

        copy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedText = parent.textarea.getText();
                // Copy the text to the clipboard
                StringSelection stringSelection = new StringSelection(selectedText);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
            }
        });
      
       paste.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                Transferable contents = clipboard.getContents(null);

                if (contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                    try {
                        String pastedText = (String) contents.getTransferData(DataFlavor.stringFlavor);
                        parent.textarea.append(pastedText);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

         cut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedText = parent.textarea.getText();
                // Copy the text to the clipboard
                StringSelection stringSelection = new StringSelection(selectedText);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
                parent.textarea.setText("");
            }
        });

        
        undo.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                  if (parent.undoManager.canUndo()) {
                    parent.undoManager.undo();
        }
            }
        });

        redo.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(parent.undoManager.canRedo()){
                    parent.undoManager.redo();
                }
            }
        });

        exit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
			if(!parent.saved){
				int choice=JOptionPane.showConfirmDialog(parent,"Do you want to save the file?","Save file",
							JOptionPane.YES_NO_CANCEL_OPTION);
				if(choice==JOptionPane.YES_OPTION)
					saveFile();
				else if(choice==JOptionPane.NO_OPTION)
					System.exit(0);
				else if(choice==JOptionPane.CANCEL_OPTION)
					return;
			}
			System.exit(0);
            }
		});//


    }

    public boolean canUndo() {
    return undoManager.canUndo();
    }

    public boolean canRedo(){
        return undoManager.canRedo();
    }

    void saveFile() {
		try{
			if(parentFrame.opened){
				PrintWriter pw=new PrintWriter(currentFile);
				pw.print(parentFrame.textarea.getText());
				parentFrame.setTitle("NotePad- "+currentFile);
				pw.close();
				parentFrame.saved=true;
			}
			else{
				JFileChooser chooser=new JFileChooser();
				chooser.showSaveDialog(parentFrame);
				currentFile=chooser.getSelectedFile();
				PrintWriter pw=new PrintWriter(currentFile);
				pw.print(parentFrame.textarea.getText());
				parentFrame.setTitle("NotePad- "+currentFile);
				pw.close();
				parentFrame.opened=true;
				saveas.setEnabled(true);
				JOptionPane.showMessageDialog(parentFrame,"File Saved Successfully ! ! !");
				parentFrame.saved=true;
			}
		}
		catch(Exception ex){System.out.println(ex);}
    }//end of save

}