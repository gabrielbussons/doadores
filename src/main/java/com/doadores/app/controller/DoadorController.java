package com.doadores.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.doadores.app.filtros.FiltroRetorno;
import com.doadores.app.models.DoadorModel;
import com.doadores.app.service.DoadorService;

@RestController
@RequestMapping(value="/api")
@CrossOrigin(origins = "*")
public class DoadorController {

	@Autowired
	private DoadorService doadorService;
	
	@RequestMapping(value="/cadastrarArquivo", consumes="multipart/form-data", method=RequestMethod.POST)
	public String cadastrarArquivo(@RequestParam("file") MultipartFile multipartFile) throws Exception{
		
		return doadorService.cadastrarArquivo(multipartFile);
	}
	
	@RequestMapping(value="/getAll", method=RequestMethod.GET)
	public List<DoadorModel> getAll(){
		
		return doadorService.getAll();
	}
	
	@RequestMapping(value="/deleteAll", method=RequestMethod.GET)
	public String deleteAll(){
		
		return doadorService.deleteAll();
	}
	
	@RequestMapping(value="/pessoasPorEstado", method=RequestMethod.GET)
	public List<FiltroRetorno> pessoasPorEstado() throws Exception {
		
		return doadorService.pessoasPorEstado();
	}
	
	
	@RequestMapping(value="/imcMedio", method=RequestMethod.GET)
	public List<FiltroRetorno> imcMedio() throws Exception{
		
		return doadorService.imcMedio();	
		
	}
	
	@RequestMapping(value="/percentualObesos", method=RequestMethod.GET)
	public List<FiltroRetorno> percentualObesos() throws Exception {
		
		return doadorService.percentualObesos();
	}
	

	@RequestMapping(value="/mediaIdadeSanguineo", method=RequestMethod.GET)
	public List<FiltroRetorno> mediaIdadeSanguineo() throws Exception {
		
		return doadorService.mediaIdadeSanguineo();
		
	}
	
	@RequestMapping(value="/possiveisDoadores", method=RequestMethod.GET)
	public List<FiltroRetorno> possiveisDoadores() throws Exception{
		
		return doadorService.possiveisDoadores();
	}
	
}