import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
/**
 * @author Michael Tuskan & Matthew Kim
 * Group Number: 23
 * Date: 05/31/2023
 * Course: TCSS 445 A - Database System Design
 * Project: Final Project Submission
 */

/**
 * Class: ForagerGUI -> Communication code for Forager GUI
 */
public class ForagerGUI extends JDialog {
    private JPanel MainJPanel;
    private JPanel DatabaseSearchJPanel;
    private JPanel JournalPageJPanel;
    private JLabel JournalPageLabel;
    private JPanel JournalPageLabelJPanel;
    private JLabel DatabaseSearchLabel;
    private JPanel DatabaseSearchLabelJPanel;
    private JLabel MushroomKingdomLabel;
    private JComboBox mushroomSpeciesComboBox;
    private JTextPane JournalPageTextPane;
    private JLabel mushroomSpeciesLabel;
    private JComboBox MushroomNameComboBox;
    private JLabel MushroomCommonNameLabel;
    private JButton MushroomJournalPageSearchButton;
    private JButton LoadForagerDatabaseButton;
    private JLabel kingdomFungiLabel;
    private JLabel ForagerDatabaseLabel;
    private JComboBox UsernameComboBox;
    private JLabel UsernameLabel;
    private JLabel kingdomPlanteLabel;
    private JLabel PlantKingdomLabel;
    private JLabel PlantSpeciesLabel;
    private JLabel plantNameLabel;
    private JComboBox plantNameComboBox;
    private JComboBox plantSpeciesComboBox;
    private JButton PlantJournalPageSearchButton;

    /**
     * ForagerGUI - Constructor for ForagerGUI
     * @param parent - parent
     * @throws IOException - IOException
     */
    public ForagerGUI(JFrame parent) throws IOException {
        super(parent);
        setTitle("Homepage");
        setContentPane(MainJPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        LoadData foragerLoader = new LoadData();
        foragerLoader.fillForager();
        String[] foragerData = foragerLoader.getForager();
        for (String forager : foragerData) {
            setforagerIDcomboBox(forager);
        }

        // getMushroomJournalPageSearchButton - Action Listener
        //
        getMushroomJournalPageSearchButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Object mushroomNameData = MushroomNameComboBox.getSelectedItem();
                String mushroomName = mushroomNameData.toString();
                LoadData journalLoader = new LoadData();
                setJournalPageTextPane(journalLoader.fillMushroomJournalPage(mushroomName));


            }
        });

        // getPlantJournalPageSearchButton - Action Listener
        //
        getPlantJournalPageSearchButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object plantNameData = plantNameComboBox.getSelectedItem();
                String plantName = plantNameData.toString();
                LoadData journalLoader = new LoadData();
                setJournalPageTextPane(journalLoader.fillPlantJournalPage(plantName));
            }
        });

        // getLoadForagerDatabaseButton - Action Listener
        //
        getLoadForagerDatabaseButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                MushroomNameComboBox.removeAllItems();
                plantSpeciesComboBox.removeAllItems();
                mushroomSpeciesComboBox.removeAllItems();
                plantNameComboBox.removeAllItems();

                Object foragerData = UsernameComboBox.getSelectedItem();
                String selectedData = foragerData.toString();
                System.out.print(selectedData+"\n");

                LoadData mushroomSpeciesLoader = new LoadData();
                mushroomSpeciesLoader.fillMushroomSpecies(selectedData);
                String[] mushroomSpeciesData = mushroomSpeciesLoader.getMushroomSpecies();
                for (String species : mushroomSpeciesData) {
                    setMushroomSpeciesComboBox(species);
                }
                LoadData mushroomNameLoader = new LoadData();
                mushroomNameLoader.fillMushroomName(selectedData);
                String[] mushroomNameData = mushroomNameLoader.getName();
                for (String name : mushroomNameData) {
                    setMushroomNameComboBox(name);
                }
                LoadData plantSpeciesLoader = new LoadData();
                plantSpeciesLoader.fillPlantSpecies(selectedData);
                String[] plantSpeciesData = plantSpeciesLoader.getPlantSpecies();
                for (String species : plantSpeciesData) {
                    setPlantSpeciesComboBox(species);
                }
                LoadData plantNameLoader = new LoadData();
                plantNameLoader.fillPlantName(selectedData);
                String[] plantNameData = plantNameLoader.getName();
                for (String name : plantNameData) {
                    setPlantNameComboBox(name);
                }

            }

        });
        }


    /**
     * getMainJPanel - Get Main JPanel
     * @return - MainJPanel
     */
    public JPanel getMainJPanel() {
        return MainJPanel;
    }

    /**
     * getPlantJournalPageSearchButton - Get Plant Journal Page Search Button
     * @return - PlantJournalPageSearchButton
     */
    public JButton getPlantJournalPageSearchButton() {
        return PlantJournalPageSearchButton;
    }

    /**
     * getMushroomJournalPageSearchButton - Get Mushroom Journal Page Search Button
     * @return - MushroomJournalPageSearchButton
     */
    public JButton getMushroomJournalPageSearchButton() {
        return MushroomJournalPageSearchButton;
    }

    /**
     * getLoadForagerDatabaseButton - Get Load Forager Database Button
     * @return LoadForagerDatabaseButton
     */
    public JButton getLoadForagerDatabaseButton() {
        return LoadForagerDatabaseButton;
    }

    /**
     * setJournalPageTextPane - Set Journal Page Text Pane
     * @param JournalPageData
     */
    public void setJournalPageTextPane(String JournalPageData) {
        JournalPageTextPane.setText(JournalPageData);
    }

    /**
     * setMushroomSpeciesComboBox - Set Mushroom Species Combo Box
     * @param speciesName - Species Name
     */
    public void setMushroomSpeciesComboBox(String speciesName) {
        mushroomSpeciesComboBox.addItem(speciesName);
    }

    /**
     * setPlantNameComboBox - Set Plant Name Combo Box
     * @param plantName - Plant Name
     */
    public void setPlantNameComboBox(String plantName) {
        plantNameComboBox.addItem(plantName);
    }

    /**
     * setMushroomNameComboBox - Set Mushroom Name Combo Box
     * @param mushroomName - Mushroom Name
     */
    public void setMushroomNameComboBox(String mushroomName) {
        MushroomNameComboBox.addItem(mushroomName);
    }

    /**
     * setPlantSpeciesComboBox - Set Plant Species Combo Box
     * @param plantSpecies - Plant Species
     */
    public void setPlantSpeciesComboBox(String plantSpecies) {
        plantSpeciesComboBox.addItem(plantSpecies);
    }

    /**
     * setforagerIDcomboBox - Set Forager ID combo Box
     * @param forager - Forager
     */
    public void setforagerIDcomboBox(String forager) {
        UsernameComboBox.addItem(forager);
    }

}
