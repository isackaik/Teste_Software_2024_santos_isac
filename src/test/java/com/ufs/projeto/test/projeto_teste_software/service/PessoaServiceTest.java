package com.ufs.projeto.test.projeto_teste_software.service;

import com.ufs.projeto.test.projeto_teste_software.exceptions.BusinessException;
import com.ufs.projeto.test.projeto_teste_software.models.Pessoa;
import com.ufs.projeto.test.projeto_teste_software.repositories.PessoaRepository;
import com.ufs.projeto.test.projeto_teste_software.services.PessoaService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PessoaServiceTest {

    @InjectMocks
    PessoaService service;
    @Mock
    PessoaRepository repository;
    Pessoa pessoa;
    static List<Pessoa> listaPessoas;

    @BeforeAll
    static void setUpAll(){
        System.out.println("Executado BeforeAll antes de todos os métodos de teste.");
        listaPessoas = new ArrayList<>();
    }

    @BeforeEach
    public void setUp(){
        System.out.println("Executado BeforeEach antes de cada método de teste");
        pessoa = Pessoa.builder()
                .nome("Isac")
                .cpf("12358569852")
                .profissao("Desenvolvedor")
                .idade(24)
                .cidade("Maruim").rua("Rua 1").numero(1)
                .build();
    }

    @Test
    void deveBuscarPessoaPorCpfComSucesso(){
        System.out.println("Executado método de teste 'deveBuscarPessoaPorCpfComSucesso'");
        when(repository.findPessoa(pessoa.getCpf())).thenReturn(Collections.singletonList(pessoa));
        List<Pessoa> pessoas = service.buscaPessoasPorCpf(pessoa.getCpf());

        assertEquals(Collections.singletonList(pessoa), pessoas);
        verify(repository).findPessoa(pessoa.getCpf());
        verifyNoMoreInteractions(repository);
        listaPessoas.add(pessoa);
    }

    @Test
    void naoDeveChamarRepositorySeCpfNulo(){
        System.out.println("Executado método de teste 'naoDeveChamarRepositorySeCpfNulo'");
        final BusinessException e = assertThrows(BusinessException.class, () -> {
            service.buscaPessoasPorCpf(null);
        });

        assertThat(e, notNullValue());
        assertThat(e.getMessage(), is("Erro ao buscar pessoas por cpf = null"));
        assertThat(e.getCause(), notNullValue());
        assertThat(e.getCause().getMessage(), is("Cpf é obrigatório!"));
        verifyNoInteractions(repository);
    }

    @Test
    void deveRetornarExceptionSeRepositoryFalhar(){
        System.out.println("Executado método de teste 'deveRetornarExceptionSeRepositoryFalhar");
        when(repository.findPessoa(pessoa.getCpf())).thenThrow(new RuntimeException("Falha ao buscar pessoas por cpf!"));

        final BusinessException e = assertThrows(BusinessException.class, () -> {
            service.buscaPessoasPorCpf(pessoa.getCpf());
        });

        assertThat(e.getMessage(), is(format("Erro ao buscar pessoas por cpf = %s", pessoa.getCpf())));
        assertThat(e.getCause().getClass(), is(RuntimeException.class));
        assertThat(e.getCause().getMessage(), is("Falha ao buscar pessoas por cpf!"));
        verify(repository).findPessoa(pessoa.getCpf());
        verifyNoMoreInteractions(repository);
    }

}
