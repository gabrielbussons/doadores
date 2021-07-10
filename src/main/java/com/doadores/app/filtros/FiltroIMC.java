package com.doadores.app.filtros;

import java.util.List;

import com.doadores.app.models.DoadorModel;

public class FiltroIMC {

	private Integer idadeInicial;
	
	private Integer idadeFinal;
	
	private Double imc;
	
	private Double percObesos;
	
	private String sexo;
	
	private List<DoadorModel> doadores;

	public Integer getIdadeInicial() {
		return idadeInicial;
	}

	public void setIdadeInicial(Integer idadeInicial) {
		this.idadeInicial = idadeInicial;
	}

	public Integer getIdadeFinal() {
		return idadeFinal;
	}

	public void setIdadeFinal(Integer idadeFinal) {
		this.idadeFinal = idadeFinal;
	}

	public Double getImc() {
		return imc;
	}

	public void setImc(Double imc) {
		this.imc = imc;
	}

	public Double getPercObesos() {
		return percObesos;
	}

	public void setPercObesos(Double percObesos) {
		this.percObesos = percObesos;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public List<DoadorModel> getDoadores() {
		return doadores;
	}

	public void setDoadores(List<DoadorModel> doadores) {
		this.doadores = doadores;
	}
	
	public void calcularImc() {
		if(this.doadores != null && this.doadores.size() > 0) {
			Double auxiliar = 0.0;
			Integer qtdePessoas = this.doadores.size();
			for (DoadorModel doadorModel : this.doadores) {
				auxiliar += imcPorPessoa(doadorModel);
			}
			
			this.imc = auxiliar / qtdePessoas;
		}
	}
	
	public void calcularObesos() {
		if(this.doadores != null && this.doadores.size() > 0) {
			Double auxiliar = 0.0;
			Integer qtdePessoas = this.doadores.size();
			Integer qtdePessoasObesas = 0;
			for (DoadorModel doadorModel : this.doadores) {
				auxiliar = imcPorPessoa(doadorModel);
				
				if(auxiliar > 30) {
					qtdePessoasObesas++;
				}
			}
			
			this.percObesos = (qtdePessoasObesas / qtdePessoas.doubleValue());
		}
	}
	
	public Double imcPorPessoa(DoadorModel doadorModel) {
		
		return doadorModel.getPeso() / (Math.pow(doadorModel.getAltura(), 2));
	}
	
}
