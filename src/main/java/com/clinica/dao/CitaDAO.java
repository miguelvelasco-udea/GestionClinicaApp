package com.clinica.dao;

import com.clinica.exception.DatosInvalidosException;
import com.clinica.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CitaDAO {

    private final String archivo = "data/citas.txt";

    public CitaDAO() {
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
                System.out.println("Archivo de citas creado: " + f.getAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println("Error al crear archivo de citas: " + e.getMessage());
        }
    }

    // Lee todas las citas del archivo (resistente a líneas corruptas)
    public List<Cita> obtenerCitas() {
        List<Cita> citas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            int lineaNum = 0;
            while ((linea = br.readLine()) != null) {
                lineaNum++;
                if (linea.trim().isEmpty()) continue;
                String[] datos = linea.split(";");
                // Esperamos 16 campos: id;fecha;hora;pacDoc;pacNom;pacApe;pacEmail;pacTel;
                // medDoc;medNom;medApe;medEmail;medTel;espId;espNom;estado
                if (datos.length < 16) {
                    System.err.println("Línea inválida en citas.txt (línea " + lineaNum + "): faltan campos -> " + linea);
                    continue;
                }
                try {
                    int id = Integer.parseInt(datos[0]);
                    LocalDate fecha = LocalDate.parse(datos[1]);
                    LocalTime hora = LocalTime.parse(datos[2]);

                    // Paciente
                    String pacDoc = datos[3];
                    String pacNom = datos[4];
                    String pacApe = datos[5];
                    String pacEmail = datos[6];
                    String pacTel = datos[7];

                    // Médico
                    String medDoc = datos[8];
                    String medNom = datos[9];
                    String medApe = datos[10];
                    String medEmail = datos[11];
                    String medTel = datos[12];

                    int espId = Integer.parseInt(datos[13]);
                    String espNom = datos[14];

                    String estadoStr = datos[15];

                    // Construir objetos dentro de try para capturar DatosInvalidosException
                    Paciente paciente;
                    Medico medico;
                    try {
                        paciente = new Paciente(pacDoc, pacNom, pacApe, pacEmail, pacTel, "N/A", LocalDate.now().minusYears(30), "");
                    } catch (DatosInvalidosException ex) {
                        // Si no puede construirse el paciente, saltar esta cita
                        System.err.println("No se pudo crear Paciente en línea " + lineaNum + ": " + ex.getMessage());
                        continue;
                    }

                    Especialidad esp = new Especialidad(espId, espNom, "");
                    try {
                        medico = new Medico(medDoc, medNom, medApe, medEmail, medTel, esp);
                    } catch (DatosInvalidosException ex) {
                        System.err.println("No se pudo crear Medico en línea " + lineaNum + ": " + ex.getMessage());
                        continue;
                    }

                    EstadoCita estado;
                    try {
                        estado = EstadoCita.valueOf(estadoStr);
                    } catch (Exception ex) {
                        System.err.println("Estado inválido en línea " + lineaNum + ": " + estadoStr + " -> se marcará PROGRAMADA");
                        estado = EstadoCita.PROGRAMADA;
                    }

                    Cita cita = new Cita(id, fecha, hora, paciente, medico);
                    cita.setEstado(estado);
                    citas.add(cita);

                } catch (Exception ex) {
                    System.err.println("Error parseando cita en línea " + lineaNum + ": " + ex.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer citas: " + e.getMessage());
        }

        System.out.println("Citas cargadas: " + citas.size());
        return citas;
    }

    // Agrega una cita (añade al final del archivo)
    public void agregarCita(Cita cita) {
        cita.setId(generarNuevoId());
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
            bw.write(formatearCita(cita));
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error al agregar cita: " + e.getMessage());
        }
    }

    // Actualiza una cita (reescribe todo)
    public void actualizarCita(Cita citaActualizada) {
        List<Cita> citas = obtenerCitas();
        for (int i = 0; i < citas.size(); i++) {
            if (citas.get(i).getId() == citaActualizada.getId()) {
                citas.set(i, citaActualizada);
                break;
            }
        }
        guardarTodas(citas);
    }

    // Elimina por id (reescribe todo)
    public void eliminarCita(int id) {
        List<Cita> citas = obtenerCitas();
        citas.removeIf(c -> c.getId() == id);
        guardarTodas(citas);
    }

    // Reescribe archivo con la lista provista
    private void guardarTodas(List<Cita> citas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (Cita c : citas) {
                bw.write(formatearCita(c));
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar citas: " + e.getMessage());
        }
    }

    // Genera nuevo id (seguro)
    private int generarNuevoId() {
        List<Cita> citas = obtenerCitas();
        int max = 0;
        for (Cita c : citas) {
            if (c.getId() > max) max = c.getId();
        }
        int nuevo = max + 1;
        System.out.println("Generando nuevo ID de cita: " + nuevo);
        return nuevo;
    }

    // Convierte una cita a línea de texto (16 campos)
    private String formatearCita(Cita c) {
        // Asegurarse de no tener nulls
        String pacEmail = safe(c.getPaciente().getEmail());
        String pacTel = safe(c.getPaciente().getTelefono());
        String medEmail = safe(c.getMedico().getEmail());
        String medTel = safe(c.getMedico().getTelefono());
        String espId = String.valueOf(c.getMedico().getEspecialidad().getId());
        String espNom = safe(c.getMedico().getEspecialidad().getNombre());
        String estado = c.getEstado() != null ? c.getEstado().name() : EstadoCita.PROGRAMADA.name();

        return c.getId() + ";" +
               c.getFecha().toString() + ";" +
               c.getHora().toString() + ";" +
               safe(c.getPaciente().getDocumento()) + ";" +
               safe(c.getPaciente().getNombre()) + ";" +
               safe(c.getPaciente().getApellido()) + ";" +
               pacEmail + ";" +
               pacTel + ";" +
               safe(c.getMedico().getDocumento()) + ";" +
               safe(c.getMedico().getNombre()) + ";" +
               safe(c.getMedico().getApellido()) + ";" +
               medEmail + ";" +
               medTel + ";" +
               espId + ";" +
               espNom + ";" +
               estado;
    }

    private String safe(String s) {
        return s == null ? "" : s.replace(";", ""); // evitar ';' en campos
    }
}
