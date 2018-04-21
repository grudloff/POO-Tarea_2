import javax.swing.*;
import java.awt.event.*;

public class MyTime {
   public MyTime () {
      playIcon = new ImageIcon("play-button.jpg");
      pauseIcon = new ImageIcon("pause.jpg");
      playing=false;
      view = new JButton(playIcon);
      view.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent event) {// EVENTO SI EL BOTON SE PRESIONA O NO
            playing=!playing;
            if (playing)
               view.setIcon(pauseIcon);
            else 
               view.setIcon(playIcon);
      }
      });

   }
   public JButton getView () {
      return view;
   }
   private boolean playing; // object model
   private JButton view;   // object view
   private ImageIcon playIcon, pauseIcon; // used by view

}