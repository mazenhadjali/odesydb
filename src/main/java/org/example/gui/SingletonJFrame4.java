package org.example.gui;

import org.example.dao.PersonneDaoImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.function.Supplier;

public class SingletonJFrame4 extends JFrame {

    int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

    int frameWidth = screenWidth / 2;
    int frameHeight = screenHeight / 2;
    private static SingletonJFrame4 instance;
    private Container container;
    private static final PersonneDaoImpl personneDao = new PersonneDaoImpl();

    private JComboBox<String> comboBoxGouvernorat;
    private JComboBox<String> comboBoxDelegation;
    private JComboBox<String> comboBoxSecteur;

    private JComboBox<String> complexeRes;
    private JButton rechercherButton;
    JPanel inputPanel;

    public static String defaultOption = "الكل";
    String selectedGouvernorat;
    String selectedDelegation;
    String selectedSecteur;
    String selectedComplexeRes;
    JPanel buttonsPanel;

    // table Stats Starters

    private SingletonJFrame4() {
        setTitle("تصنيف المعطيات على مستوى الاسر ");
        setSize(frameWidth, frameHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container = getContentPane();
        container.setLayout(new BorderLayout());

        this.createInputPanel(); // Call the method to create and configure the input panel
        populateGouvernoratComboBox();

        this.createCenterPanelButtons();


        setLocation(screenWidth - frameWidth, screenHeight - frameHeight);
        setVisible(true);
    }

    // Public method to get the Singleton instance
    public static SingletonJFrame4 getInstance() {
        if (instance == null) {
            instance = new SingletonJFrame4();
        }
        return instance;
    }

    // Private method to create and configure the input panel
    private void createInputPanel() {
        // Create a default option for the JComboBoxes

        comboBoxGouvernorat = new JComboBox<>();
        comboBoxGouvernorat.addItem(defaultOption);
        comboBoxDelegation = new JComboBox<>();
        comboBoxDelegation.addItem(defaultOption);
        comboBoxSecteur = new JComboBox<>();
        comboBoxSecteur.addItem(defaultOption);
        complexeRes = new JComboBox<>();
        complexeRes.addItem(defaultOption);

        inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("الولاية: "));
        inputPanel.add(comboBoxGouvernorat);
        inputPanel.add(new JLabel("المعتمدية: "));
        inputPanel.add(comboBoxDelegation);
        inputPanel.add(new JLabel("العمادة: "));
        inputPanel.add(comboBoxSecteur);
        inputPanel.add(new JLabel("التجمّع السكني: "));
        inputPanel.add(complexeRes);
        rechercherButton = new JButton("Rechercher");
        inputPanel.add(rechercherButton);
        container.add(inputPanel, BorderLayout.NORTH);

        rechercherButton.addActionListener(e -> {
            this.selectedGouvernorat = (String) comboBoxGouvernorat.getSelectedItem();
            this.selectedDelegation = (String) comboBoxDelegation.getSelectedItem();
            this.selectedSecteur = (String) comboBoxSecteur.getSelectedItem();
            this.selectedComplexeRes = (String) complexeRes.getSelectedItem();

            System.out.println(selectedGouvernorat);
            System.out.println(selectedDelegation);
            System.out.println(selectedSecteur);
            System.out.println(selectedComplexeRes);
        });
    }

    private void createCenterPanelButtons() {
        // Create a panel for the buttons with a vertical layout
        buttonsPanel = new JPanel(new GridLayout(15, 1));
//        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        createStatsButton("جدول 20 : الزراعات الصغرى", () -> new Tab20Frame(this.selectedGouvernorat, this.selectedDelegation, this.selectedSecteur, this.selectedComplexeRes));
        createStatsButton("جدول 21 : الزراعات والغراسات", () -> new Tab20pFrame(this.selectedGouvernorat, this.selectedDelegation, this.selectedSecteur, this.selectedComplexeRes));
        createStatsButton("جدول 24 : الأبقار", () -> new Tab23Frame(this.selectedGouvernorat, this.selectedDelegation, this.selectedSecteur, this.selectedComplexeRes));
        createStatsButton("جدول 25 : الماشية", () -> new Tab24Frame(this.selectedGouvernorat, this.selectedDelegation, this.selectedSecteur, this.selectedComplexeRes));
        createStatsButton("جدول 26 : النحل", () -> new Tab25Frame(this.selectedGouvernorat, this.selectedDelegation, this.selectedSecteur, this.selectedComplexeRes));
        createStatsButton("جدول 27 : الأرانب و الدجاج", () -> new Tab26Frame(this.selectedGouvernorat, this.selectedDelegation, this.selectedSecteur, this.selectedComplexeRes));
        createStatsButton("جدول 29 : استغلال الغابة", () -> new Tab28Frame(this.selectedGouvernorat, this.selectedDelegation, this.selectedSecteur, this.selectedComplexeRes));
        createStatsButton("جدول 30 : تصنيف الماشية", () -> new Tab30Frame(this.selectedGouvernorat, this.selectedDelegation, this.selectedSecteur, this.selectedComplexeRes));

        // Add the button panel to the CENTER of the BorderLayout
        container.add(buttonsPanel, BorderLayout.CENTER);
    }

    private void createStatsButton(String label, Supplier<JFrame> frameSupplier) {
        JButton button = new JButton(label);
        // Set the font size to 16 (you can adjust the size as needed)
        // Set the font size to 16 and make it bold
        Font buttonFont = new Font(button.getFont().getName(), Font.BOLD, 16);
        button.setFont(buttonFont);
        button.addActionListener(e -> {
            JFrame frame = frameSupplier.get();
            frame.setVisible(true);
        });
        buttonsPanel.add(button);
    }

    private void populateGouvernoratComboBox() {
        List<String> gouvernorats = personneDao.getAllGouvernorat();
        comboBoxGouvernorat.removeAllItems();
        comboBoxGouvernorat.addItem("الكل");
        for (String gouvernorat : gouvernorats) {
            comboBoxGouvernorat.addItem(gouvernorat);
        }

        comboBoxGouvernorat.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedWilaya = (String) comboBoxGouvernorat.getSelectedItem();
                    populateDelegationComboBox(selectedWilaya);
                    comboBoxSecteur.removeAllItems();
                    comboBoxSecteur.addItem("الكل");
                    complexeRes.removeAllItems();
                    complexeRes.addItem("الكل");
                }
            }
        });
    }

    private void populateDelegationComboBox(String selectedGouvernorat) {
        List<String> delegations = personneDao.getAllDelegationByGouvernorat(selectedGouvernorat);
        comboBoxDelegation.removeAllItems();
        comboBoxDelegation.addItem("الكل");
        for (String delegation : delegations) {
            comboBoxDelegation.addItem(delegation);
        }

        comboBoxDelegation.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedWilaya = (String) comboBoxGouvernorat.getSelectedItem();
                    String selectedMutamadiya = (String) comboBoxDelegation.getSelectedItem();
                    populateSecteurComboBox(selectedWilaya, selectedMutamadiya);
                    complexeRes.removeAllItems();
                    complexeRes.addItem("الكل");
                }
            }
        });
    }

    private void populateSecteurComboBox(String selectedGouvernorat, String selectedMutamadiya) {
        List<String> secteurs = personneDao.getAllsecteurByDelegationByGouvernorat(selectedGouvernorat, selectedMutamadiya);
        comboBoxSecteur.removeAllItems();
        comboBoxSecteur.addItem("الكل");
        for (String secteur : secteurs) {
            comboBoxSecteur.addItem(secteur);
        }

        comboBoxSecteur.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedWilaya = (String) comboBoxGouvernorat.getSelectedItem();
                    String selectedMutamadiya = (String) comboBoxDelegation.getSelectedItem();
                    String selectedImada = (String) comboBoxSecteur.getSelectedItem();
                    populateComplexeResComboBox(selectedWilaya, selectedMutamadiya, selectedImada);
                }
            }
        });
    }

    private void populateComplexeResComboBox(String selectedWilaya, String selectedMutamadiya, String selectedImada) {
        List<String> complexeResList = personneDao.getAllComplexeResBySecteurByDelegationByGouvernorat(selectedWilaya, selectedMutamadiya, selectedImada);
        complexeRes.removeAllItems();
        complexeRes.addItem("الكل");
        for (String complexe : complexeResList) {
            complexeRes.addItem(complexe);
        }
    }


}
