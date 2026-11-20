package ar.uba.fi.cb100.tps.tp2.modelos;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class AgendaTest {

    @Test
    void agendaNuevaEstaVacia() {
        Agenda agenda = new Agenda();
        assertTrue(agenda.estaVacia());
        assertEquals(0, agenda.cantidad());
    }

    @Test
    void noEjecutaEventosFuturos() {
        Agenda agenda = new Agenda();
        List<String> registro = new ArrayList<>();
        agenda.programar(10, () -> registro.add("a"));

        agenda.ejecutarHasta(9);

        assertTrue(registro.isEmpty());
        assertEquals(1, agenda.cantidad());
    }

    @Test
    void ejecutaElEventoCuandoLlegaSuPaso() {
        Agenda agenda = new Agenda();
        List<String> registro = new ArrayList<>();
        agenda.programar(10, () -> registro.add("a"));

        agenda.ejecutarHasta(10);

        assertEquals(List.of("a"), registro);
        assertTrue(agenda.estaVacia());
    }

    @Test
    void ejecutaEnOrdenDePasoSinImportarElOrdenDeInsercion() {
        Agenda agenda = new Agenda();
        List<String> registro = new ArrayList<>();
        agenda.programar(30, () -> registro.add("zapatos"));
        agenda.programar(25, () -> registro.add("campana"));

        agenda.ejecutarHasta(40);

        assertEquals(List.of("campana", "zapatos"), registro);
    }

    @Test
    void conMismoPasoRespetaElOrdenDeInsercion() {
        Agenda agenda = new Agenda();
        List<String> registro = new ArrayList<>();
        agenda.programar(5, () -> registro.add("primero"));
        agenda.programar(5, () -> registro.add("segundo"));
        agenda.programar(5, () -> registro.add("tercero"));

        agenda.ejecutarHasta(5);

        assertEquals(List.of("primero", "segundo", "tercero"), registro);
    }

    @Test
    void ejecutarHastaDejaLosEventosPosterioresPendientes() {
        Agenda agenda = new Agenda();
        List<String> registro = new ArrayList<>();
        agenda.programar(5, () -> registro.add("a"));
        agenda.programar(20, () -> registro.add("b"));

        agenda.ejecutarHasta(10);

        assertEquals(List.of("a"), registro);
        assertEquals(1, agenda.cantidad());
        assertFalse(agenda.estaVacia());
    }

    @Test
    void ejecutarHastaSinEventosNoHaceNada() {
        Agenda agenda = new Agenda();
        agenda.ejecutarHasta(100);
        assertTrue(agenda.estaVacia());
    }

    @Test
    void unEventoPuedeProgramarOtroQueTambienYaVencio() {
        Agenda agenda = new Agenda();
        List<String> registro = new ArrayList<>();
        agenda.programar(5, () -> {
            registro.add("a");
            agenda.programar(6, () -> registro.add("b"));
        });

        agenda.ejecutarHasta(10);

        assertEquals(List.of("a", "b"), registro);
    }

    @Test
    void rechazaPasoNegativo() {
        Agenda agenda = new Agenda();
        assertThrows(IllegalArgumentException.class, () -> agenda.programar(-1, () -> { }));
    }

    @Test
    void rechazaAccionNula() {
        Agenda agenda = new Agenda();
        assertThrows(IllegalArgumentException.class, () -> agenda.programar(1, null));
    }
}