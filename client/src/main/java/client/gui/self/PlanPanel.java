package client.gui.self;

import client.gui.util.RoundPanel;
import lib.enumModel.Plan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlanPanel extends RoundPanel {


    private String title;
    private Plan plan;
    private DashBoard dashBoard;
    private boolean isSelected = false;
    private final Color blue= new Color(0, 158, 224);
    private final Color grey = new Color(230,230,230);

    private JPanel centerPanel;
    private JPanel bottomPanel;
    private JPanel topPanel;
    private JLabel choseLabel;
    private JLabel titleLabel;


    public PlanPanel(String title, Plan plan, DashBoard dashBoard){
        this.title = title;
        this.dashBoard = dashBoard;
        this.plan = plan;

        setBackgroundColor(Color.WHITE);
        setPreferredSize(new Dimension(400,200));
        setLayout(new BorderLayout());

        initTopPanel(title);
        add(topPanel,BorderLayout.NORTH);

        initCenterPanel();
        add(centerPanel, BorderLayout.CENTER);

        initBottomPanel();
        add(bottomPanel, BorderLayout.SOUTH);

        choseLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dashBoard.getPlanPanelList().forEach(PlanPanel::unselectPlan);
                selectPlan();
            }
        });

    }

    private void initBottomPanel() {
        bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);

        bottomPanel.setPreferredSize(new Dimension(400,65));
        choseLabel = new JLabel("Choose Plan");
        choseLabel.setFont(new Font("Segoe UI",Font.BOLD,14));
        choseLabel.setForeground(blue);
        choseLabel.setPreferredSize(new Dimension(380,65));
        choseLabel.setHorizontalAlignment(SwingConstants.CENTER);
        choseLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        bottomPanel.add(choseLabel);
    }

    private void initCenterPanel() {
        centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createMatteBorder(0,0,1,0,grey));

    }

    private void initTopPanel(String title) {
        topPanel = new JPanel();
        topPanel.setOpaque(false);
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI",Font.PLAIN,20));
        topPanel.add(titleLabel);
    }

    public void selectPlan(){
        centerPanel.setOpaque(true);
        dashBoard.getChangePlanPanel().setVisible(true);
        centerPanel.setBackground(blue);
        choseLabel.setText("Selected");
        titleLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/checked20.png"));
        isSelected = true;
    }

    public void unselectPlan(){

        centerPanel.setBackground(null);
        centerPanel.setOpaque(false);
        choseLabel.setText("Choose Plan");
        titleLabel.setIcon(null);
        isSelected = false;
    }

    public Plan getPlan() {
        return plan;
    }
    public boolean isSelected() {
        return isSelected;
    }

}
