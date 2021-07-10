package com.doadores.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.doadores.app.models.DoadorModel;

@Repository
public interface DoadorRepository extends JpaRepository<DoadorModel, Long>{

	DoadorModel findById(long id);
	
	List<DoadorModel> findAllBySexo(String sexo);
	
	List<DoadorModel> findAllByTipoSanguineo(String tipo);
	
	List<DoadorModel> findAllByOrderByNomeAsc();
	
	List<DoadorModel> findAllByOrderByTipoSanguineoAsc();
	
	@Query("SELECT estado as e, count(*) as c FROM DoadorModel GROUP BY e ORDER BY c")
	List<Object[]> groupByEstado();
	
	@Query("SELECT distinct tipoSanguineo as t FROM DoadorModel ORDER BY t")
	List<Object[]> findTiposSanguineos();
	

}