package com.clinica.view.dialogs;

import com.clinica.model.Paciente;
import com.clinica.service.PacienteService;
import java.awt.*;
import java.time.LocalDate;
import java.util.Calendar;
import javax.swing.*;

public class AddPatientDialog extends JDialog {
    private JTextField documentoField, nombreField, apellidoField, emailField, telefonoField, direccionField, historialMedicoField;
    private JComboBox<Integer> diaComboBox, mesComboBox, anioComboBox;
    private JButton guardarButton, cancelButton;
    private PacienteService pacienteService;
    private Runnable onPatientAdded;

    public AddPatientDialog(Frame owner, PacienteService pacienteService, Runnable onPatientAdded) {
        super(owner, "Agregar Paciente", true);
        this.pacienteService = pacienteService;
        this.onPatientAdded = onPatientAdded;
        initializeUI();
    }

    public AddPatientDialog(Frame owner, PacienteService pacienteService) {
        this(owner, pacienteService, null);
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        setSize(400, 500);
        setLocationRelativeTo(getOwner());

        add(createFormPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(9, 2, 5, 5));
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

        formPanel.add(new JLabel("Dirección:"));
        direccionField = new JTextField();
        formPanel.add(direccionField);

        formPanel.add(new JLabel("Fecha de Nacimiento:"));
        JPanel fechaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        diaComboBox = new JComboBox<>();
        mesComboBox = new JComboBox<>();
        anioComboBox = new JComboBox<>();
        for (int i = 1; i <= 31; i++) diaComboBox.addItem(i);
        for (int i = 1; i <= 12; i++) mesComboBox.addItem(i);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = currentYear; i >= currentYear - 100; i--) anioComboBox.addItem(i);
        fechaPanel.add(diaComboBox);
        fechaPanel.add(mesComboBox);
        fechaPanel.add(anioComboBox);
        formPanel.add(fechaPanel);

        formPanel.add(new JLabel("Historial Médico:"));
        historialMedicoField = new JTextField();
        formPanel.add(historialMedicoField);

        return formPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        guardarButton = new JButton("Guardar");
        cancelButton = new JButton("Cancelar");

        guardarButton.addActionListener(e -> savePatient());
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(guardarButton);
        buttonPanel.add(cancelButton);
        return buttonPanel;
    }

    private void savePatient() {
        try {
            String documento = documentoField.getText().trim();
            String nombre = nombreField.getText().trim();
            String apellido = apellidoField.getText().trim();
            String email = emailField.getText().trim();
            String telefono = telefonoField.getText().trim();
            String direccion = direccionField.getText().trim();
            int dia = (int) diaComboBox.getSelectedItem();
            int mes = (int) mesComboBox.getSelectedItem();
            int anio = (int) anioComboBox.getSelectedItem();
            LocalDate fechaNacimiento = LocalDate.of(anio, mes, dia);
            String historialMedico = historialMedicoField.getText().trim();

            // Validaciones
            if (!documento.matches("\\d{8,10}")) {
                JOptionPane.showMessageDialog(this, "Documento inválido (8-10 dígitos numéricos)", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!nombre.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]{3,20}$")) {
                JOptionPane.showMessageDialog(this, "Nombre inválido (solo letras, 3-20 caracteres)", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!apellido.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]{3,20}$")) {
                JOptionPane.showMessageDialog(this, "Apellido inválido (solo letras, 3-20 caracteres)", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!email.matches("^[\\w.-]+@[\\w.-]+\\.com$")) {
                JOptionPane.showMessageDialog(this, "Correo inválido (debe tener @ y terminar en .com)", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!telefono.matches("\\d{8,10}")) {
                JOptionPane.showMessageDialog(this, "Teléfono inválido (8-10 dígitos numéricos)", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Paciente newPatient = new Paciente(documento, nombre, apellido, email, telefono, direccion, fechaNacimiento, historialMedico);
            pacienteService.registrarPaciente(newPatient);

            JOptionPane.showMessageDialog(this, "Paciente agregado exitosamente");

            if (onPatientAdded != null) {
                onPatientAdded.run();
            }

            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar el paciente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
