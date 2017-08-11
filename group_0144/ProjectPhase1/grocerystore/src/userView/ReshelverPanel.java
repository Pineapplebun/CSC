package userView;

import grocerystore.buttonManager.ReshelverButtonManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;

/**
 * ReshelverPanel is a subclass of WorkerPanel. It contains working panels for a Reshelver.
 */
public class ReshelverPanel extends WorkerPanel{

  JButton reshelvesCart = new JButton("Reshelf Cart");
  JButton getOrderHist = new JButton("Get Product Order History");
  ReshelverButtonManager reshelverBM;

  /**
   * Creates a ReshelverPanel.
   */
  public ReshelverPanel(){
    super(false);
    this.setPanelTitle("Reshelver's Unique Action Panel:");
    List<JButton> allButtons = Arrays.asList(reshelvesCart, getOrderHist);
    this.addNewButton(allButtons, 0, 7, new ReshelverListener());
    this.addNewButton(allButtons, 0, 8, new ReshelverListener());
    //this.refreshButtonPanel();
  }

  /**
   * Sets reshelverBM of this ReshelverPanel to reshelverBM.
   * @param reshelverBM reshelverBM of ReshelverButtonManager type.
   */
  public void setReshelverBM(ReshelverButtonManager reshelverBM){
    this.reshelverBM = reshelverBM;
    setWorkerBM(reshelverBM);
  }

  /**
   * Handles the button events recorded from the user interface and then take action accordingly.
   */
  private class ReshelverListener implements ActionListener {

    public void actionPerformed(ActionEvent event) {
      Object source = event.getSource();
      String upc = upcInput.getText();
      if (source == reshelvesCart) {
        clearDisplayCart();
        clearDisplayAnswer();
        try {
          addTextDisplayAnswer(reshelverBM.callReshelvesCart());
        } catch (IOException e) {
          e.printStackTrace();
        }
      } else if (source == getOrderHist && reshelverBM.callCheckUPCValid(upc)) {
        clearDisplayAnswer();
        addTextDisplayAnswer(reshelverBM.callGetOrderHistory(upc));
      }
    }
  }
}
