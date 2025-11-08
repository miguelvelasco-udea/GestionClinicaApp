package com.clinica.view.panels;

import com.clinica.model.Cita;
import com.clinica.model.Paciente;
import com.clinica.service.CitaService;
import com.clinica.service.PacienteService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class HistoryPanel extends JPanel {
    private JTable historyTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> patientComboBox;
    private CitaService citaService;
    private PacienteService pacienteService;

    public HistoryPanel(CitaService citaService, PacienteService pacienteService) {
        this.citaService = citaService;
        this.pacienteService = pacienteService;
        initializePanel();
        loadPatients();
        loadHistory();
    }

    private void initializePanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Paciente:"));
        patientComboBox = new JComboBox<>();
        patientComboBox.addItem("Todos");
        top.add(patientComboBox);
        JButton filter = new JButton("Filtrar");
        filter.addActionListener(e -> filterHistory());
        top.add(filter);

        add(top, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Fecha", "Paciente", "Médico", "Estado"}, 0);
        historyTable = new JTable(tableModel);
        add(new JScrollPane(historyTable), BorderLayout.CENTER);
    }

    private void loadPatients() {
        try {
            List<Paciente> pacientes = pacienteService.listarPacientes();
            for (Paciente p : pacientes) {
                patientComboBox.addItem(p.getNombreCompleto());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando pacientes: " + e.getMessage());
        }
    }

    private void loadHistory() {
        try {
            updateTable(citaService.listarCitas());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando historial: " + e.getMessage());
        }
    }

    private void filterHistory() {
        String sel = (String) patientComboBox.getSelectedItem();
        if (sel.equals("Todos")) {
            loadHistory();
            return;
        }

        try {
            List<Cita> filtradas = citaService.listarCitas().stream()
                    .filter(c -> c.getPaciente().getNombreCompleto().equalsIgnoreCase(sel))
                    .collect(Collectors.toList());
            updateTable(filtradas);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al filtrar: " + e.getMessage());
        }
    }

    private void updateTable(List<Cita> citas) {
        tableModel.setRowCount(0);
        for (Cita c : citas) {
            tableModel.addRow(new Object[]{
                    c.getId(),
                    c.getFecha(),
                    c.getPaciente().getNombreCompleto(),
                    c.getMedico().getNombreCompleto(),
                    c.getEstado()
            });
        }
    }
}

