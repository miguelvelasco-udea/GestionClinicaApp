package com.clinica.view.dialogs;

import com.clinica.model.Especialidad;
import com.clinica.model.Medico;
import com.clinica.service.MedicoService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EditDoctorDialog extends JDialog {
    private JTextField documentoField, nombreField, apellidoField, emailField, telefonoField;
    private JComboBox<Especialidad> especialidadComboBox;
    private JButton guardarButton, cancelButton;
    private MedicoService medicoService;
    private Medico medico;
    private List<Especialidad> especialidades;

    public EditDoctorDialog(Frame owner, MedicoService medicoService, Medico medico, List<Especialidad> especialidades) {
        super(owner, "Editar Médico", true);
        this.medicoService = medicoService;
        this.medico = medico;
        this.especialidades = especialidades;
        initializeUI();
        fillData();
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
        documentoField.setEditable(false);
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
        especialidadComboBox = new JComboBox<>(especialidades.toArray(new Especialidad[0]));
        formPanel.add(especialidadComboBox);

        return formPanel;
    }

    private void fillData() {
        documentoField.setText(medico.getDocumento());
        nombreField.setText(medico.getNombre());
        apellidoField.setText(medico.getApellido());
        emailField.setText(medico.getEmail());
        telefonoField.setText(medico.getTelefono());
        especialidadComboBox.setSelectedItem(medico.getEspecialidad());
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
            String nombre = nombreField.getText().trim();
            String apellido = apellidoField.getText().trim();
            String email = emailField.getText().trim();
            String telefono = telefonoField.getText().trim();
            Especialidad especialidad = (Especialidad) especialidadComboBox.getSelectedItem();

            // Validaciones
            if (!nombre.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]{3,20}$")) {
                JOptionPane.showMessageDialog(this, "Nombre inválido (solo letras, 3-20 caracteres)", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!apellido.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]{3,20}$")) {
                JOptionPane.showMessageDialog(this, "Apellido inválido (solo letras, 3-20 caracteres)", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                JOptionPane.showMessageDialog(this, "Correo inválido (formato: usuario@dominio.extensión)", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!telefono.matches("\\d{8,10}")) {
                JOptionPane.showMessageDialog(this, "Teléfono inválido (7-10 dígitos numéricos)", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            medico.setNombre(nombre);
            medico.setApellido(apellido);
            medico.setEmail(email);
            medico.setTelefono(telefono);
            medico.setEspecialidad(especialidad);

            medicoService.actualizarMedico(medico);

            JOptionPane.showMessageDialog(this, "Médico actualizado exitosamente");
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar el médico: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}