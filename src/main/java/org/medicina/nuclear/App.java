package org.medicina.nuclear;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.ResourceBundle;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class App extends JFrame {

    private ResourceBundle bundle;

    // UI Components
    private JLabel instLabel;
    private JTextField instField;
    private JLabel unitLabel;
    private JComboBox<String> unitBox;
    private JLabel activityLabel;
    private JTextField activityField;
    private JLabel volumeLabel;
    private JTextField volumeField;
    private JLabel doseLabel;
    private JTextField doseField;
    private JLabel timeLabel;
    private JTextField timeField;
    private JComboBox<String> amPmBox;
    private JComboBox<String> langBox;
    
    private JButton calculateBtn;
    private JButton exportPdfBtn;
    private JTextArea resultArea;

    public App() {
        setSize(600, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // 1. Input Panel (Now 6 rows for new inputs)
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        instLabel = new JLabel();
        instField = new JTextField();
        inputPanel.add(instLabel);
        inputPanel.add(instField);

        unitLabel = new JLabel();
        unitBox = new JComboBox<>(new String[]{"mCi", "MBq"});
        inputPanel.add(unitLabel);
        inputPanel.add(unitBox);

        activityLabel = new JLabel();
        activityField = new JTextField("88");
        inputPanel.add(activityLabel);
        inputPanel.add(activityField);

        volumeLabel = new JLabel();
        volumeField = new JTextField("4");
        inputPanel.add(volumeLabel);
        inputPanel.add(volumeField);

        doseLabel = new JLabel();
        doseField = new JTextField("30");
        inputPanel.add(doseLabel);
        inputPanel.add(doseField);

        timeLabel = new JLabel();
        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        timeField = new JTextField("08:00", 5);
        amPmBox = new JComboBox<>(new String[]{"AM", "PM"});
        timePanel.add(timeField);
        timePanel.add(Box.createHorizontalStrut(5));
        timePanel.add(amPmBox);
        
        inputPanel.add(timeLabel);
        inputPanel.add(timePanel);

        // 2. Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        calculateBtn = new JButton();
        exportPdfBtn = new JButton();
        
        // Added Portuguese to the dropdown
        langBox = new JComboBox<>(new String[]{"English", "Español", "Français", "Português", "中文", "日本語"});
        
        buttonPanel.add(langBox);
        buttonPanel.add(calculateBtn);
        buttonPanel.add(exportPdfBtn);

        JPanel topWrapper = new JPanel(new BorderLayout());
        topWrapper.add(inputPanel, BorderLayout.CENTER);
        topWrapper.add(buttonPanel, BorderLayout.SOUTH);

        add(topWrapper, BorderLayout.NORTH);

        // 3. Output Area
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        resultArea.setLineWrap(true);       
        resultArea.setWrapStyleWord(true);  
        
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // Actions
        calculateBtn.addActionListener(e -> calculateTable());
        exportPdfBtn.addActionListener(e -> exportToPdf());
        
        langBox.addActionListener(e -> {
            int idx = langBox.getSelectedIndex();
            switch (idx) {
                case 0: setLanguage("en"); break;
                case 1: setLanguage("es"); break;
                case 2: setLanguage("fr"); break;
                case 3: setLanguage("pt"); break;
                case 4: setLanguage("zh"); break;
                case 5: setLanguage("ja"); break;
            }
        });
        
        setLanguage("en"); // Default
    }

    private void setLanguage(String langCode) {
        Locale locale = Locale.of(langCode); 
        bundle = ResourceBundle.getBundle("idioma", locale);
        updateUILanguage();
    }

    private void updateUILanguage() {
        setTitle(bundle.getString("app.title"));
        instLabel.setText(bundle.getString("lbl.inst"));
        unitLabel.setText(bundle.getString("lbl.unit"));
        activityLabel.setText(bundle.getString("lbl.activity"));
        volumeLabel.setText(bundle.getString("lbl.volume"));
        doseLabel.setText(bundle.getString("lbl.dose"));
        timeLabel.setText(bundle.getString("lbl.time"));
        calculateBtn.setText(bundle.getString("btn.calc"));
        exportPdfBtn.setText(bundle.getString("btn.pdf"));
    }

    /**
     * Helper method to validate empty mandatory fields
     */
    private boolean areMandatoryFieldsEmpty() {
        return activityField.getText().trim().isEmpty() || 
               volumeField.getText().trim().isEmpty() || 
               doseField.getText().trim().isEmpty() || 
               timeField.getText().trim().isEmpty();
    }

    private void calculateTable() {
        if (areMandatoryFieldsEmpty()) {
            JOptionPane.showMessageDialog(this, bundle.getString("err.empty"), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double initialActivity = Double.parseDouble(activityField.getText().replace(",", "."));
            double totalVolume = Double.parseDouble(volumeField.getText().replace(",", "."));
            double targetDose = Double.parseDouble(doseField.getText().replace(",", "."));
            String unit = (String) unitBox.getSelectedItem();

            String timeInput = timeField.getText().trim() + " " + amPmBox.getSelectedItem();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.US);
            LocalTime initialTime = LocalTime.parse(timeInput, formatter);

            Isotope tc99m = new Isotope("99mTc", 361.2);
            double v0 = DecayCalculator.calculateInitialVolume(targetDose, initialActivity, totalVolume);
            
            String targetDoseStr = String.format(Locale.US, "%.0f", targetDose);
            String v0Str = String.format(Locale.US, "%.2f", v0);
            
            StringBuilder sb = new StringBuilder();

            // 1. Structured Input Summary
            sb.append(bundle.getString("txt.summary")).append("\n");
            String instName = instField.getText().trim();
            if (!instName.isEmpty()) {
                sb.append("• ").append(bundle.getString("lbl.inst").replace(" (Optional):", "").replace(" (Opcional):", "").replace(" (Optionnel):", "")).append(" ").append(instName).append("\n");
            }
            sb.append("• ").append(bundle.getString("lbl.activity")).append(" ").append(initialActivity).append(" ").append(unit).append("\n");
            sb.append("• ").append(bundle.getString("lbl.volume")).append(" ").append(totalVolume).append(" ml\n");
            sb.append("• ").append(bundle.getString("lbl.dose")).append(" ").append(targetDose).append(" ").append(unit).append("\n");
            sb.append("• ").append(bundle.getString("lbl.time")).append(" ").append(timeInput).append("\n\n");

            // 2. Professional Paragraphs
            String paragraph1 = String.format(bundle.getString("msg.v0"), targetDoseStr, unit, v0Str);
            String paragraph2 = bundle.getString("msg.desc");

            sb.append(paragraph1).append("\n\n");
            sb.append(paragraph2).append("\n\n");
            
            // 3. Table header
            sb.append(String.format("%-20s %s\n", bundle.getString("tbl.vol"), bundle.getString("tbl.time")));
            sb.append("------------------------------------------\n");

            double startVolume = Math.ceil(v0 * 10) / 10.0;
            for (double v = startVolume; v <= totalVolume; v += 0.1) {
                double minutes = DecayCalculator.calculateDecayMinutes(tc99m, initialActivity, targetDose, totalVolume, v);
                
                LocalTime injectionTime = initialTime.plusMinutes((long) minutes);
                String formattedTime = injectionTime.format(formatter);

                sb.append(String.format("%-20.1f %s\n", v, formattedTime));
            }

            resultArea.setText(sb.toString());

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, bundle.getString("err.num"), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, bundle.getString("err.time"), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exportToPdf() {
        if (resultArea.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, bundle.getString("err.calc"), "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(bundle.getString("pdf.save"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("PDF Documents", "pdf"));
        fileChooser.setSelectedFile(new File("Decay_Table.pdf"));

        int userSelection = fileChooser.showSaveDialog(this);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            
            if (!filePath.toLowerCase().endsWith(".pdf")) {
                filePath += ".pdf";
            }

            try {
                Document document = new Document(PageSize.A4);
                PdfWriter.getInstance(document, new FileOutputStream(filePath));
                document.open();

                // 1. Institution Header (if provided)
                String instName = instField.getText().trim();
                if (!instName.isEmpty()) {
                    com.itextpdf.text.Font instFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 16, com.itextpdf.text.Font.BOLD);
                    Paragraph instPara = new Paragraph(instName, instFont);
                    instPara.setAlignment(Element.ALIGN_CENTER);
                    instPara.setSpacingAfter(15f);
                    document.add(instPara);
                }

                // 2. Title
                com.itextpdf.text.Font titleFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 14, com.itextpdf.text.Font.BOLD);
                Paragraph titlePara = new Paragraph(bundle.getString("pdf.title"), titleFont);
                titlePara.setAlignment(Element.ALIGN_CENTER);
                titlePara.setSpacingAfter(20f);
                document.add(titlePara);

                double initialActivity = Double.parseDouble(activityField.getText().replace(",", "."));
                double totalVolume = Double.parseDouble(volumeField.getText().replace(",", "."));
                double targetDose = Double.parseDouble(doseField.getText().replace(",", "."));
                String unit = (String) unitBox.getSelectedItem();
                String timeInput = timeField.getText().trim() + " " + amPmBox.getSelectedItem();
                
                // 3. Input Summary List
                com.itextpdf.text.Font listFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 12, com.itextpdf.text.Font.NORMAL);
                document.add(new Paragraph(bundle.getString("txt.summary"), new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 12, com.itextpdf.text.Font.BOLD)));
                document.add(new Paragraph("• " + bundle.getString("lbl.activity") + " " + initialActivity + " " + unit, listFont));
                document.add(new Paragraph("• " + bundle.getString("lbl.volume") + " " + totalVolume + " ml", listFont));
                document.add(new Paragraph("• " + bundle.getString("lbl.dose") + " " + targetDose + " " + unit, listFont));
                
                Paragraph timePara = new Paragraph("• " + bundle.getString("lbl.time") + " " + timeInput, listFont);
                timePara.setSpacingAfter(15f);
                document.add(timePara);
                
                // 4. Paragraphs
                Isotope tc99m = new Isotope("99mTc", 361.2);
                double v0 = DecayCalculator.calculateInitialVolume(targetDose, initialActivity, totalVolume);
                String targetDoseStr = String.format(Locale.US, "%.0f", targetDose);
                String v0Str = String.format(Locale.US, "%.2f", v0);
                
                Paragraph param1 = new Paragraph(String.format(bundle.getString("msg.v0"), targetDoseStr, unit, v0Str));
                param1.setSpacingAfter(10f); 
                document.add(param1);

                Paragraph param2 = new Paragraph(bundle.getString("msg.desc"));
                param2.setSpacingAfter(20f);
                document.add(param2);

                // 5. Table
                PdfPTable table = new PdfPTable(2);
                table.addCell(new PdfPCell(new Paragraph(bundle.getString("tbl.vol") + " (ml)")));
                table.addCell(new PdfPCell(new Paragraph(bundle.getString("tbl.time"))));
                
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.US);
                LocalTime initialTime = LocalTime.parse(timeInput, formatter);
                
                double startVolume = Math.ceil(v0 * 10) / 10.0;

                for (double v = startVolume; v <= totalVolume; v += 0.1) {
                    double minutes = DecayCalculator.calculateDecayMinutes(tc99m, initialActivity, targetDose, totalVolume, v);
                    LocalTime injectionTime = initialTime.plusMinutes((long) minutes);
                    
                    table.addCell(String.format("%.1f", v));
                    table.addCell(injectionTime.format(formatter));
                }

                document.add(table);
                document.close();

                JOptionPane.showMessageDialog(this, bundle.getString("pdf.success"), "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            App app = new App();
            app.setLocationRelativeTo(null);
            app.setVisible(true);
        });
    }
}