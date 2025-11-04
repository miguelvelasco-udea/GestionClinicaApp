package com.clinica.view.panels;

import com.clinica.model.Medico;
import com.clinica.service.MedicoService;
import com.clinica.view.dialogs.AddDoctorDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DoctorPanel extends JPanel {
    private JTable doctorTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> specialityComboBox;
    private MedicoService medicoService;
    
    public DoctorPanel(MedicoService medicoService) {
        this.medicoService = medicoService;
        initializePanel();
        loadDoctorsData();
    }
    
    private void initializePanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(245, 245, 245));
        
        add(createFilterPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }
    
    private JPanel createFilterPanel() {
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBorder(BorderFactory.createTitledBorder("🎯 Filtros"));
        filterPanel.setBackground(Color.WHITE);
        
        specialityComboBox = new JComboBox<>(new String[]{
            "Todas las especialidades", "Cardiología", "Pediatría", "Dermatología", "Neurología"
        });
        
        JButton filterButton = new JButton("Filtrar");
        filterButton.addActionListener(e -> filterDoctors());
        
        filterPanel.add(new JLabel("Especialidad:"));
        filterPanel.add(specialityComboBox);
        filterPanel.add(filterButton);
        
        return filterPanel;
    }
    
    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("👨‍⚕️ Cuerpo Médico"));
        tablePanel.setBackground(Color.WHITE);
        
        String[] columnNames = {"Documento", "Nombre", "Apellido", "Email", "Teléfono", "Especialidad"};
        tableModel = new DefaultTableModel(columnNames, 0);
        doctorTable = new JTable(tableModel);
        
        // Estilo de tabla
        doctorTable.getTableHeader().setBackground(new Color(70, 130, 180));
        doctorTable.getTableHeader().setForeground(Color.WHITE);
        doctorTable.setRowHeight(25);
        
        JScrollPane scrollPane = new JScrollPane(doctorTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        return tablePanel;
    }
    
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(245, 245, 245));
        
        JButton addButton = new JButton("Agregar Médico");
        JButton editButton = new JButton("Editar Médico");
        JButton deleteButton = new JButton("Eliminar Médico");
        
        addButton.addActionListener(e -> addDoctor());
        editButton.addActionListener(e -> editDoctor());
        deleteButton.addActionListener(e -> deleteDoctor());
        
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        
        return buttonPanel;
    }
    
    private void loadDoctorsData() {
        try {
            List<Medico> medicos = medicoService.listarMedicos();
            updateTable(medicos);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar médicos: " + e.getMessage());
        }
    }
    
    private void filterDoctors() {
        String especialidad = (String) specialityComboBox.getSelectedItem();
        if ("Todas las especialidades".equals(especialidad)) {
            loadDoctorsData();
            return;
        }
        
        try {
            List<Medico> medicos = medicoService.listarPorEspecialidad(especialidad);
            updateTable(medicos);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al filtrar médicos: " + e.getMessage());
        }
    }
    
    private void updateTable(List<Medico> medicos) {
        tableModel.setRowCount(0);
        for (Medico medico : medicos) {
            Object[] row = {
                medico.getDocumento(),
                medico.getNombre(),
                medico.getApellido(),
                medico.getEmail(),
                medico.getTelefono(),
                medico.getEspecialidad().getNombre()
            };
            tableModel.addRow(row);
        }
    }
    
    private void addDoctor() {
        AddDoctorDialog dialog = new AddDoctorDialog((Frame) SwingUtilities.getWindowAncestor(this), medicoService);
        dialog.setVisible(true);
        loadDoctorsData(); // Recargar la tabla
    }
    
    private void editDoctor() {
        int selectedRow = doctorTable.getSelectedRow();
        if (selectedRow >= 0) {
            String documento = (String) tableModel.getValueAt(selectedRow, 0);
            JOptionPane.showMessageDialog(this, "Editar médico: " + documento);
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un médico para editar");
        }
    }
    
    private void deleteDoctor() {
        int selectedRow = doctorTable.getSelectedRow();
        if (selectedRow >= 0) {
            String documento = (String) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this,
                "¿Eliminar médico con documento: " + documento + "?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    medicoService.eliminarMedico(documento);
                    loadDoctorsData();
                    JOptionPane.showMessageDialog(this, "Médico eliminado");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un médico");
        }
    }
}