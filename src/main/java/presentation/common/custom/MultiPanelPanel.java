package presentation.common.custom;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.util.HashMap;

import static presentation.common.GuiConstants.H_GAP;
import static presentation.common.GuiConstants.V_GAP;

public abstract class MultiPanelPanel<T, V> extends JPanel{

    protected JButton previousPanelButton;
    protected JButton nextPanelButton;
    protected JLabel currPageLabel;
    protected int numberOfPanels = 0;
    protected int currentPanel = 0;
    protected int oldNumberOfPanels = 0;
    protected int oldCurrentPanel = 0;
    protected CardLayout cardLayout;
    protected JPanel cardPanel;

    public MultiPanelPanel(JFrame frame) {
        this(frame, new int[] { 0, 0 });
    }

    public MultiPanelPanel(JFrame motherFrame, int[] indexes) {
        this.setLayout(new BorderLayout(H_GAP, V_GAP));
        this.setBorder(new EmptyBorder(V_GAP, H_GAP, V_GAP, H_GAP));
        this.add(createButtonPanel(), BorderLayout.SOUTH);
        this.oldCurrentPanel = indexes[0];
        this.oldNumberOfPanels = indexes[1];
        setBehaviour();
    }

    protected JPanel createButtonPanel() {
        JPanel holder = new JPanel(new FlowLayout(FlowLayout.CENTER, H_GAP, V_GAP));
        previousPanelButton = new JButton("<-");
        nextPanelButton = new JButton("->");
        currPageLabel = new JLabel(getCurrPageLabel());
        holder.add(previousPanelButton);
        holder.add(currPageLabel);
        holder.add(nextPanelButton);
        return holder;
    }

    protected String getCurrPageLabel() {
        return "   " + (currentPanel + 1) + "/" + numberOfPanels + "   ";
    }

    public abstract void updateChart(HashMap<T, V> barChartData);

    protected void reset() {
        cardLayout = null;
        cardPanel = null;
        numberOfPanels = 0;
        currentPanel = 0;
    }

    public void setToPreviouslySelectedCard() {

        // there are new panels, we cannot guarantee that the order is the same
        if(oldNumberOfPanels != numberOfPanels) {
            return;
        }

        // it should not happen, but if there are fewer panels now, we cannot go beyond the size obviously
        if(oldCurrentPanel < numberOfPanels) {
            this.cardLayout.show(cardPanel, oldCurrentPanel + "");
            this.currentPanel = oldCurrentPanel;
            currPageLabel.setText(getCurrPageLabel());
        }
    }


    private void setBehaviour() {
        this.nextPanelButton.addActionListener(e -> {
            if(currentPanel < numberOfPanels - 1) {
                currentPanel++;
                cardLayout.show(cardPanel, currentPanel + "");
                currPageLabel.setText(getCurrPageLabel());
            }
        });
        this.previousPanelButton.addActionListener(e -> {
            if(currentPanel > 0) {
                currentPanel--;
                cardLayout.show(cardPanel, currentPanel + "");
                currPageLabel.setText(getCurrPageLabel());
            }
        });
    }

    public int[] getIndexes() {
        return new int[] { currentPanel, numberOfPanels };
    }
}
