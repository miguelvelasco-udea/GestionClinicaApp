package com.clinica.dao;

import com.clinica.model.Paciente;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {

    private final String archivo = "pacientes.txt";

    public PacienteDAO() {
        crearArchivoSiNoExiste();
    }

    private void crearArchivoSiNoExiste() {
        try {
            File f = new File(archivo);
            if (f.getParentFile() != null && !f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }
            if (!f.exists()) {
                f.createNewFile();
                System.out.println("Archivo de pacientes creado: " + f.getAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println("Error al crear archivo de pacientes: " + e.getMessage());
        }
    }

    // Agregar paciente
    public void agregarPaciente(Paciente p) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
            bw.write(formatoTexto(p));
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Obtener lista de pacientes
    public List<Paciente> obtenerPacientes() {
        List<Paciente> lista = new ArrayList<>();

        File f = new File(archivo);
        if (!f.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                // Validación de campos esperados
                if (datos.length == 8) {
                    String documento = datos[0];
                    String nombre = datos[1];
                    String apellido = datos[2];
                    String email = datos[3];
                    String telefono = datos[4];
                    String direccion = datos[5];
                    LocalDate fechaNacimiento = LocalDate.parse(datos[6]);
                    String historialMedico = datos[7];

                    Paciente p = new Paciente(documento, nombre, apellido, email, telefono,
                                              direccion, fechaNacimiento, historialMedico);
                    lista.add(p);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // Actualizar paciente
    public void actualizarPaciente(Paciente actualizado) {
        List<Paciente> pacientes = obtenerPacientes();

        for (int i = 0; i < pacientes.size(); i++) {
            if (pacientes.get(i).getDocumento().equals(actualizado.getDocumento())) {
                pacientes.set(i, actualizado);
                break;
            }
        }
        guardarTodos(pacientes);
    }

    // Eliminar paciente
    public void eliminarPaciente(String documento) {
        List<Paciente> pacientes = obtenerPacientes();
        pacientes.removeIf(p -> p.getDocumento().equals(documento));
        guardarTodos(pacientes);
    }

    // Guardar toda la lista (reescribir archivo completo)
    private void guardarTodos(List<Paciente> lista) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (Paciente p : lista) {
                bw.write(formatoTexto(p));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Formato estándar para escribir en archivo
    private String formatoTexto(Paciente p) {
        return String.join(";",
                p.getDocumento(),
                p.getNombre(),
                p.getApellido(),
                p.getEmail(),
                p.getTelefono(),
                p.getDireccion(),
                p.getFechaNacimiento().toString(), // formato ISO yyyy-MM-dd
                p.getHistorialMedico()
        );
    }
}