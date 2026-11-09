package co.edu.uniquindio;

import java.time.LocalDate;
import java.time.Period;

public abstract class Persona {

        private String nombre;
        private LocalDate fechaNacimiento;  // se guarda la fecha, NUNCA la edad

        public Persona(String nombre, LocalDate fechaNacimiento) {
            this.nombre = nombre;
            this.fechaNacimiento = fechaNacimiento;
        }

        public int calcularEdad(LocalDate fecha) {
            return Period.between(fechaNacimiento, fecha).getYears();
        }

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
        public LocalDate getFechaNacimiento() {
            return fechaNacimiento;
        }
    }

