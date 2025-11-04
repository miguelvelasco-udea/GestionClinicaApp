package com.clinica.view.panels;

import com.clinica.model.Paciente;
import com.clinica.service.PacienteService;
import com.clinica.view.dialogs.AddPatientDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PatientPanel extends JPanel {
    private JTable patientTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton addButton, editButton, deleteButton, searchButton;
    private PacienteService pacienteService;
    
    public PatientPanel(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
        initializePanel();
        loadPatientsData();
    }
    
    private void initializePanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(245, 245, 245));
        
        add(createSearchPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }
    
    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBorder(BorderFactory.createTitledBorder("🔍 Búsqueda"));
        searchPanel.setBackground(Color.WHITE);
        
        searchField = new JTextField(20);
        searchButton = new JButton("Buscar");
        
        searchButton.addActionListener(e -> searchPatients());
        
        searchPanel.add(new JLabel("Buscar paciente:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        
        return searchPanel;
    }
    
    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("📋 Lista de Pacientes"));
        tablePanel.setBackground(Color.WHITE);
        
        String[] columnNames = {"Documento", "Nombre", "Apellido", "Email", "Teléfono", "Dirección", "Fecha Nac."};
        tableModel = new DefaultTableModel(columnNames, 0);
        patientTable = new JTable(tableModel);
        
        // Estilo de tabla
        patientTable.getTableHeader().setBackground(new Color(70, 130, 180));
        patientTable.getTableHeader().setForeground(Color.WHITE);
        patientTable.setRowHeight(25);
        
        JScrollPane scrollPane = new JScrollPane(patientTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        return tablePanel;
    }
    
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(245, 245, 245));
        
        addButton = new JButton("Agregar Paciente");
        editButton = new JButton("Editar Paciente");
        deleteButton = new JButton("Eliminar Paciente");
        
        addButton.addActionListener(e -> addPatient());
        editButton.addActionListener(e -> editPatient());
        deleteButton.addActionListener(e -> deletePatient());
        
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        
        return buttonPanel;
    }
    
    private void loadPatientsData() {
        try {
            List<Paciente> pacientes = pacienteService.listarPacientes();
            updateTable(pacientes);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar pacientes: " + e.getMessage());
        }
    }
    
    private void searchPatients() {
        String searchText = searchField.getText().trim();
        if (searchText.isEmpty()) {
            loadPatientsData();
            return;
        }
        
        try {
            List<Paciente> pacientes = pacienteService.listarPacientes();
            // Filtrar localmente (ya que el servicio no tiene búsqueda)
            pacientes.removeIf(p -> 
                !p.getNombre().toLowerCase().contains(searchText.toLowerCase()) &&
                !p.getApellido().toLowerCase().contains(searchText.toLowerCase()) &&
                !p.getDocumento().contains(searchText)
            );
            updateTable(pacientes);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al buscar pacientes: " + e.getMessage());
        }
    }
    
    private void updateTable(List<Paciente> pacientes) {
        tableModel.setRowCount(0);
        for (Paciente paciente : pacientes) {
            Object[] row = {
                paciente.getDocumento(),
                paciente.getNombre(),
                paciente.getApellido(),
                paciente.getEmail(),
                paciente.getTelefono(),
                paciente.getDireccion(),
                paciente.getFechaNacimiento()
            };
            tableModel.addRow(row);
        }
    }
    
    private void addPatient() {
        AddPatientDialog dialog = new AddPatientDialog((Frame) SwingUtilities.getWindowAncestor(this), pacienteService);
        dialog.setVisible(true);
        loadPatientsData(); // Recargar la tabla
    }
    
    private void editPatient() {
        int selectedRow = patientTable.getSelectedRow();
        if (selectedRow >= 0) {
            String documento = (String) tableModel.getValueAt(selectedRow, 0);
            JOptionPane.showMessageDialog(this, 
                "Editar paciente con documento: " + documento + " - Por implementar");
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un paciente para editar");
        }
    }
    
    private void deletePatient() {
        int selectedRow = patientTable.getSelectedRow();
        if (selectedRow >= 0) {
            String documento = (String) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar al paciente con documento: " + documento + "?",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    pacienteService.eliminarPaciente(documento);
                    loadPatientsData();
                    JOptionPane.showMessageDialog(this, "Paciente eliminado exitosamente");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error al eliminar paciente: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un paciente para eliminar");
        }
    }
}