package main;

import main.tabs.*;

import javax.swing.*;
import java.awt.*;

public class PigFarmPanel extends JPanel {

    private JTabbedPane mainTabbedPane;

    public PigFarmPanel() {
        super(new GridLayout(1, 1));
        setPreferredSize(new Dimension(600, 600));

        mainTabbedPane = new JTabbedPane();

        Pigs pigs = new Pigs();
        mainTabbedPane.addTab("Pigs", pigs.mainPanel);

        UnhealthyPigs unhealthyPigs = new UnhealthyPigs();
        mainTabbedPane.addTab("Unhealthy Pigs", unhealthyPigs.mainPanel);

        Pigstys pigstys = new Pigstys();
        mainTabbedPane.addTab("Pigstys", pigstys.mainPanel);

        PigstyMapping pigstyMapping = new PigstyMapping();
        mainTabbedPane.addTab("PigstyMapping", pigstyMapping.mainPanel);

        Fodder fodder = new Fodder();
        mainTabbedPane.addTab("Fodder", fodder.mainPanel);

        Employees employees = new Employees();
        mainTabbedPane.addTab("Employees", employees.mainPanel);

        mainTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        mainTabbedPane.addChangeListener(e -> {
            switch (mainTabbedPane.getSelectedIndex()) {
                case 0:
                    pigs.onTabSwitch();
                    break;
                case 1:
                    unhealthyPigs.onTabSwitch();
                    break;
                case 2:
                    pigstys.onTabSwitch();
                    break;
                case 3:
                    pigstyMapping.onTabSwitch();
                    break;
                case 4:
                    fodder.onTabSwitch();
                    break;
                case 5:
                    employees.onTabSwitch();
                    break;
            }
        });

        add(mainTabbedPane);
    }
}
