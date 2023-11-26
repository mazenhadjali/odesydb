package org.example.gui;

import org.example.dao.PersonneDaoImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.function.Supplier;

public class SingletonJFrame3 extends JFrame {

    int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

    int frameWidth = screenWidth / 2;
    int frameHeight = screenHeight / 2;
    private static SingletonJFrame3 instance;
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

    private SingletonJFrame3() {
        setTitle("تصنيف المعطيات على مستوى الاسر ");
        setSize(frameWidth,frameHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container = getContentPane();
        container.setLayout(new BorderLayout());

        this.createInputPanel(); // Call the method to create and configure the input panel
        populateGouvernoratComboBox();

        this.createCenterPanelButtons();


        setLocation(0, screenHeight - frameHeight);
        setVisible(true);
    }

    // Public method to get the Singleton instance
    public static SingletonJFrame3 getInstance() {
        if (instance == null) {
            instance = new SingletonJFrame3();
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
        createStatsButton("جدول 5 : له مستغلة (رجال و نساء رؤساء العائلات)", () -> new Tab5Frame(selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes));
        createStatsButton("جدول 17 : مساحة الجملية (هك)", () -> new Tab17Frame(this.selectedGouvernorat, this.selectedDelegation, this.selectedSecteur, this.selectedComplexeRes));
        createStatsButton("جدول 18 : توزيع مساحة (هك)", () -> new Tab18Frame(this.selectedGouvernorat, this.selectedDelegation, this.selectedSecteur, this.selectedComplexeRes));
        createStatsButton("جدول 19 : توزيع  المستغلات الفلاحية حسب المساحة", () -> new Tab19Frame(this.selectedGouvernorat, this.selectedDelegation, this.selectedSecteur, this.selectedComplexeRes));
        createStatsButton("جدول 22 : الميكنة الفلاحية", () -> new Tab21Frame(this.selectedGouvernorat, this.selectedDelegation, this.selectedSecteur, this.selectedComplexeRes));
        createStatsButton("جدول 23 : الحراثة", () -> new Tab22Frame(this.selectedGouvernorat, this.selectedDelegation, this.selectedSecteur, this.selectedComplexeRes));

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
