package com.doadores.app.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.doadores.app.filtros.FiltroIMC;
import com.doadores.app.filtros.FiltroRetorno;
import com.doadores.app.filtros.FiltroTipoSanguineo;
import com.doadores.app.models.DoadorModel;
import com.doadores.app.repository.DoadorRepository;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

@Service
public class DoadorService {
	
	@Autowired
	private DoadorRepository doadorRepository;
	
	public String cadastrarArquivo(@RequestParam("file") MultipartFile multipartFile) throws Exception{
		
		try {
		
			File file = new File("file.txt");
			
			try (OutputStream os = new FileOutputStream(file)) {
			    os.write(multipartFile.getBytes());
			}
			
			
			JsonReader reader = new JsonReader(new FileReader(file));
			
			Gson gson = new GsonBuilder()
					.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
					.create();
			DoadorModel[] lista = gson.fromJson(reader, DoadorModel[].class);
			
			file.delete();
			
			for (DoadorModel doadorModel : lista) {
				doadorRepository.save(doadorModel);
			}
		
		} catch (Exception e) {
			throw new Exception("Não foi possível ler o arquivo.");
		}
				
		return "sucesso";
	}
	
	public List<DoadorModel> getAll(){
		
		List<DoadorModel> lista = doadorRepository.findAllByOrderByNomeAsc();
				
		return lista;
	}
	
	@RequestMapping(value="/deleteAll", method=RequestMethod.GET)
	public String deleteAll(){
		
		doadorRepository.deleteAll();
				
		return "sucesso";
	}
	
	public List<FiltroRetorno> pessoasPorEstado() throws Exception {
		
		List<Object[]> lista = doadorRepository.groupByEstado();
		
		List<FiltroRetorno> listaRetorno = new ArrayList<>();
		
		for (Object[] object : lista) {
			FiltroRetorno filtro = obterFiltroRetorno((String) object[0], ((Number) object[1]).doubleValue());
			listaRetorno.add(filtro);
		}
		
		return listaRetorno;
	}
	
	
	public List<FiltroRetorno> imcMedio() throws Exception{
		
		List<DoadorModel> lista = doadorRepository.findAll();
		
		List<FiltroIMC> listaImcs = new ArrayList<>();
		
		Integer idadeInicial = 0;
		Integer idadeFinal = 10;
		
		for (int index = 0; index < 10; index++) {
			FiltroIMC filtro = new FiltroIMC();
			filtro.setIdadeInicial(idadeInicial);
			filtro.setIdadeFinal(idadeFinal);
			filtro.setDoadores(new ArrayList<>());
			listaImcs.add(filtro);
			
			idadeInicial = idadeFinal + 1;
			idadeFinal = idadeFinal + 10;
		}
		
		for (DoadorModel doador : lista) {		
			Integer idade = doador.getIdade();	
			for (FiltroIMC filtroIMC : listaImcs) {
				if(idade >= filtroIMC.getIdadeInicial() && idade <= filtroIMC.getIdadeFinal()) {
					filtroIMC.getDoadores().add(doador);
				}
			}
		}
		
		List<FiltroRetorno> listaRetorno = new ArrayList<>();
		
		for (FiltroIMC filtroIMC : listaImcs) {
			filtroIMC.calcularImc();
			
			String descricao = filtroIMC.getIdadeInicial() + " a " + filtroIMC.getIdadeFinal() + " anos.";
			FiltroRetorno filtroRetorno = obterFiltroRetorno(descricao, filtroIMC.getImc());
			listaRetorno.add(filtroRetorno);
		}
		
		return listaRetorno;	
		
	}
	
	public List<FiltroRetorno> percentualObesos() throws Exception {
		
		List<DoadorModel> listaHomens = doadorRepository.findAllBySexo("Masculino");
		List<DoadorModel> listaMulheres = doadorRepository.findAllBySexo("Feminino");
		
		List<FiltroIMC> listaFiltro = new ArrayList<>();
		
		FiltroIMC homens = new FiltroIMC();
		homens.setDoadores(listaHomens);
		homens.setSexo("Masculino");
		
		FiltroIMC mulheres = new FiltroIMC();
		mulheres.setDoadores(listaMulheres);
		mulheres.setSexo("Feminino");
		
		listaFiltro.add(homens);
		listaFiltro.add(mulheres);
		
		List<FiltroRetorno> listaRetorno = new ArrayList<>();
		
		for (FiltroIMC filtroIMC : listaFiltro) {
			filtroIMC.calcularObesos();
			
			FiltroRetorno filtroRetorno = obterFiltroRetorno(filtroIMC.getSexo(), filtroIMC.getPercObesos());
			listaRetorno.add(filtroRetorno);
		}
		
		return listaRetorno;
	}
	
	public List<FiltroRetorno> mediaIdadeSanguineo() throws Exception {
		
		List<DoadorModel> lista = doadorRepository.findAll();
				
		Map<Object, Double> listaMap =
				lista.stream().collect(Collectors.groupingBy(w -> w.getTipoSanguineo(), 
										Collectors.averagingInt(a -> a.getIdade())));
		
		Map<Object, Double> treeMap = new TreeMap<>(listaMap);
		
		List<FiltroRetorno> listaRetorno = new ArrayList<>();
		for (Entry<Object, Double> pair : treeMap.entrySet()) {
			FiltroRetorno filtroRetorno = obterFiltroRetorno((String) pair.getKey(), pair.getValue());
			listaRetorno.add(filtroRetorno);
		}
		
		return listaRetorno;
		
	}
	
	public List<FiltroRetorno> possiveisDoadores() throws Exception{
		
		List<DoadorModel> lista = doadorRepository.findAll();
		FiltroTipoSanguineo filtro = new FiltroTipoSanguineo(0, 0, 0, 0, 0, 0, 0, 0);
		
		Integer idadeMaxima = 0;
		for (DoadorModel doadorModel : lista) {

			String tipoSanguineo = doadorModel.getTipoSanguineo();
			Integer idade = doadorModel.getIdade();
			if(idade > idadeMaxima) {idadeMaxima = idade;}
			Double peso = doadorModel.getPeso();
			
			if(idade >= 16 && idade <= 69 && peso > 50) {		
				filtro.add(tipoSanguineo);
			}
		}
		
		List<FiltroRetorno> listaRetorno = new ArrayList<>();
		List<Object[]> tiposSanguineos = doadorRepository.findTiposSanguineos();
		for (Object[] object : tiposSanguineos) {
			Integer valor = filtro.getByTipoSanguineo((String) object[0]);
			FiltroRetorno filtroRetorno = obterFiltroRetorno((String) object[0], valor.doubleValue());
			listaRetorno.add(filtroRetorno);
		}
				
		return listaRetorno;
	}
	
	public FiltroRetorno obterFiltroRetorno(String descricao, Double valor) {
		
		FiltroRetorno filtroRetorno = new FiltroRetorno();
		filtroRetorno.setDescricao(descricao);
		filtroRetorno.setValor(valor);
		
		return filtroRetorno;
	}

}
