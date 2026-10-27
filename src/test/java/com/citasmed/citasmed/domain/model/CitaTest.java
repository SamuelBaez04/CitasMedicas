package com.citasmed.citasmed.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CitaTest {

    private final Paciente paciente = new Paciente(UUID.randomUUID(), "Ana Gomez", "ana@mail.com", "3001234567");
    private final Medico medico = new Medico(UUID.randomUUID(), "Dr. Ruiz", "Medicina general", "ruiz@mail.com");

    private Cita citaNueva(LocalDateTime fechaHora) {
        return new Cita(UUID.randomUUID(), paciente, medico, fechaHora, "Control");
    }

    private Cita citaConfirmadaEn(LocalDateTime fechaHora) {
        Cita cita = citaNueva(fechaHora);
        cita.confirmar();
        return cita;
    }

    @Test
    void nuevaCitaQuedaPendiente() {
        Cita cita = citaNueva(LocalDateTime.now().plusDays(1));

        assertThat(cita.getEstado()).isEqualTo(EstadoCita.PENDIENTE);
    }

    @Test
    void noPermiteAgendarEnElPasado() {
        assertThatThrownBy(() -> citaNueva(LocalDateTime.now().minusHours(1)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void noPermiteFechaNula() {
        assertThatThrownBy(() -> citaNueva(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void confirmarPasaDePendienteAConfirmada() {
        Cita cita = citaNueva(LocalDateTime.now().plusDays(1));

        cita.confirmar();

        assertThat(cita.getEstado()).isEqualTo(EstadoCita.CONFIRMADA);
    }

    @Test
    void noSePuedeConfirmarDosVeces() {
        Cita cita = citaConfirmadaEn(LocalDateTime.now().plusDays(1));

        assertThatThrownBy(cita::confirmar).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void noSePuedeCompletarUnaCitaPendiente() {
        Cita cita = citaNueva(LocalDateTime.now().plusDays(1));

        assertThatThrownBy(cita::completar).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void noSePuedeCancelarUnaCitaCompletada() {
        Cita cita = Cita.reconstruir(UUID.randomUUID(), paciente, medico,
                LocalDateTime.now().minusDays(1), "Control", EstadoCita.COMPLETADA);

        assertThatThrownBy(cita::cancelar).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void reconstruirPermiteFechasPasadasYConservaElEstado() {
        LocalDateTime ayer = LocalDateTime.now().minusDays(1);

        Cita cita = Cita.reconstruir(UUID.randomUUID(), paciente, medico, ayer, "Control", EstadoCita.CONFIRMADA);

        assertThat(cita.getFechaHora()).isEqualTo(ayer);
        assertThat(cita.getEstado()).isEqualTo(EstadoCita.CONFIRMADA);
    }

    @Test
    void confirmadaEnLasProximas24HorasRequiereRecordatorio() {
        Cita cita = citaConfirmadaEn(LocalDateTime.now().plusHours(5));

        assertThat(cita.requiereRecordatorio()).isTrue();
    }

    @Test
    void pendienteNoRequiereRecordatorio() {
        Cita cita = citaNueva(LocalDateTime.now().plusHours(5));

        assertThat(cita.requiereRecordatorio()).isFalse();
    }

    @Test
    void confirmadaPeroLejanaNoRequiereRecordatorio() {
        Cita cita = citaConfirmadaEn(LocalDateTime.now().plusDays(3));

        assertThat(cita.requiereRecordatorio()).isFalse();
    }

    @Test
    void confirmadaQueYaPasoNoRequiereRecordatorio() {
        Cita cita = Cita.reconstruir(UUID.randomUUID(), paciente, medico,
                LocalDateTime.now().minusHours(2), "Control", EstadoCita.CONFIRMADA);

        assertThat(cita.requiereRecordatorio()).isFalse();
    }

    @Test
    void noRequiereRecordatorioSiYaFueEnviado(){
        Cita cita = Cita.reconstruir(UUID.randomUUID(), paciente, medico,
                LocalDateTime.now().plusHours(5), "Control,", EstadoCita.CONFIRMADA, true);
        assertThat(cita.requiereRecordatorio()).isFalse();
    }

}