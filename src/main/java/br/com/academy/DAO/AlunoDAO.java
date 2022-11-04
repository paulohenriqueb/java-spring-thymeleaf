package br.com.academy.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.academy.model.Aluno;

public interface AlunoDAO extends JpaRepository<Aluno, Integer> {
	@Query("SELECT a FROM Aluno a WHERE a.status = 'ATIVO' ")
	public List<Aluno> findStatusAtivo();
	
	@Query("SELECT i FROM Aluno i WHERE i.status = 'INATIVO'")
	public List<Aluno> findStatusInativo();
	
	@Query("SELECT t FROM Aluno t WHERE t.status = 'TRANCADO'")
	public List<Aluno> findStatusTrancado();
	
	@Query("SELECT c from Aluno c WHERE c.status = 'CANCELADO'")
	public List<Aluno> findStatusCancelado();
	
	public List<Aluno> findByNomeContainingIgnoreCase(String nome);
}
