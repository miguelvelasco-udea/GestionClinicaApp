package com.clinica.view.dialogs;

import com.clinica.model.Especialidad;
import com.clinica.model.Medico;
import com.clinica.service.MedicoService;

import javax.swing.*;
import java.awt.*;

public class AddDoctorDialog extends JDialog {
    private JTextField documentoField, nombreField, apellidoField, emailField, telefonoField;
    private JComboBox<String> especialidadComboBox;
    private JButton guardarButton, cancelButton;
    private MedicoService medicoService;

    public AddDoctorDialog(Frame owner, MedicoService medicoService) {
        super(owner, "Agregar Médico", true);
        this.medicoService = medicoService;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        setSize(400, 300);
        setLocationRelativeTo(getOwner());

        add(createFormPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Documento:"));
        documentoField = new JTextField();
        formPanel.add(documentoField);

        formPanel.add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        formPanel.add(nombreField);

        formPanel.add(new JLabel("Apellido:"));
        apellidoField = new JTextField();
        formPanel.add(apellidoField);

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);

        formPanel.add(new JLabel("Teléfono:"));
        telefonoField = new JTextField();
        formPanel.add(telefonoField);

        formPanel.add(new JLabel("Especialidad:"));
        especialidadComboBox = new JComboBox<>(new String[]{"Cardiología", "Pediatría", "Dermatología", "Neurología"});
        formPanel.add(especialidadComboBox);

        return formPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        guardarButton = new JButton("Guardar");
        cancelButton = new JButton("Cancelar");

        guardarButton.addActionListener(e -> saveDoctor());
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(guardarButton);
        buttonPanel.add(cancelButton);
        return buttonPanel;
    }

    private void saveDoctor() {
        try {
            String documento = documentoField.getText();
            String nombre = nombreField.getText();
            String apellido = apellidoField.getText();
            String email = emailField.getText();
            String telefono = telefonoField.getText();
            String especialidadNombre = (String) especialidadComboBox.getSelectedItem();
            Especialidad especialidad = new Especialidad(0, especialidadNombre, ""); // ID and description are not used here

            Medico newDoctor = new Medico(documento, nombre, apellido, email, telefono, especialidad);
            medicoService.registrarMedico(newDoctor);

            JOptionPane.showMessageDialog(this, "Médico agregado exitosamente");
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar el médico: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
