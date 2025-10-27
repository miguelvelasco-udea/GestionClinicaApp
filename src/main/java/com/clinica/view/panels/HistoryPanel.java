package com.clinica.view.panels;

import com.clinica.service.HistoriaConsultaService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class HistoryPanel extends JPanel {
    private JTable historyTable;
    private DefaultTableModel tableModel;
    private HistoriaConsultaService historiaService;  // Campo para el servicio
    
    // Constructor que recibe el servicio
    public HistoryPanel(HistoriaConsultaService historiaService) {
        this.historiaService = historiaService;
        initializePanel();
        loadSampleData();
    }
    
    // Constructor vacío por si acaso (opcional)
    public HistoryPanel() {
        initializePanel();
        loadSampleData();
    }
    
    private void initializePanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(245, 245, 245));
        
        add(createFilterPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
        add(createDetailPanel(), BorderLayout.SOUTH);
    }
    
    private JPanel createFilterPanel() {
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBorder(BorderFactory.createTitledBorder("🔍 Filtros"));
        filterPanel.setBackground(Color.WHITE);
        
        JComboBox<String> patientComboBox = new JComboBox<>(new String[]{
            "Todos los pacientes", "Juan Pérez", "María Gómez"
        });
        
        JButton filterButton = new JButton("Filtrar");
        filterButton.addActionListener(e -> filterHistory());
        
        filterPanel.add(new JLabel("Paciente:"));
        filterPanel.add(patientComboBox);
        filterPanel.add(filterButton);
        
        return filterPanel;
    }
    
    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("📊 Historial de Consultas"));
        tablePanel.setBackground(Color.WHITE);
        
        String[] columnNames = {"ID", "Fecha", "Paciente", "Médico", "Diagnóstico"};
        tableModel = new DefaultTableModel(columnNames, 0);
        historyTable = new JTable(tableModel);
        
        // Estilo de tabla
        historyTable.getTableHeader().setBackground(new Color(70, 130, 180));
        historyTable.getTableHeader().setForeground(Color.WHITE);
        historyTable.setRowHeight(25);
        
        JScrollPane scrollPane = new JScrollPane(historyTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        return tablePanel;
    }
    
    private JPanel createDetailPanel() {
        JPanel detailPanel = new JPanel(new BorderLayout());
        detailPanel.setBorder(BorderFactory.createTitledBorder("📝 Detalles de Consulta"));
        detailPanel.setBackground(Color.WHITE);
        
        JTextArea detailArea = new JTextArea(4, 50);
        detailArea.setText("Seleccione una consulta para ver detalles...");
        detailArea.setLineWrap(true);
        detailArea.setWrapStyleWord(true);
        
        JScrollPane scrollPane = new JScrollPane(detailArea);
        detailPanel.add(scrollPane, BorderLayout.CENTER);
        
        return detailPanel;
    }
    
    private void loadSampleData() {
        // Limpiar tabla existente
        tableModel.setRowCount(0);
        
        Object[][] sampleData = {
            {"1", "2024-12-10", "Juan Pérez", "Dr. García", "Hipertensión"},
            {"2", "2024-12-05", "María Gómez", "Dra. Rodríguez", "Control rutina"}
        };
        
        for (Object[] row : sampleData) {
            tableModel.addRow(row);
        }
    }
    
    // Método para filtrar historial
    private void filterHistory() {
        if (historiaService != null) {
            // Usar el servicio para filtrar datos reales
            JOptionPane.showMessageDialog(this, "Filtrando historial usando el servicio...");
        } else {
            // Filtrado con datos de ejemplo
            JOptionPane.showMessageDialog(this, "Filtrando historial con datos de ejemplo...");
        }
    }
}