package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ValidacaoTutorComAdocaoEmAndamentoTest {

    @InjectMocks
    private ValidacaoTutorComAdocaoEmAndamento validador;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private SolicitacaoAdocaoDto dto;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private Tutor tutor;

    @Test
    void naoDeveriaPermitirSolicitacaoDeAdocaoTutorComAdocaoEmAndamento() {
        // Arrange
        var tutorId = 1L;
        var adocao = new Adocao();
        adocao.setTutor(tutor);
        adocao.setStatus(StatusAdocao.AGUARDANDO_AVALIACAO);
        var adocoes = Collections.singletonList(adocao);

        when(dto.idTutor()).thenReturn(tutorId);
        when(tutorRepository.getReferenceById(tutorId)).thenReturn(tutor);
        when(adocaoRepository.findAll()).thenReturn(adocoes);

        // Act + Assert
        assertThrows(ValidacaoException.class, () -> validador.validar(dto));
    }

    @Test
    void deveriaPermitirSolicitacaoDeAdocaoTutorSemAdocaoEmAndamento() {
        // Arrange
        var tutorId = 1L;

        when(dto.idTutor()).thenReturn(tutorId);
        when(tutorRepository.getReferenceById(tutorId)).thenReturn(tutor);
        when(adocaoRepository.findAll()).thenReturn(Collections.emptyList());

        // Act + Assert
        assertDoesNotThrow(() -> validador.validar(dto));
    }
}