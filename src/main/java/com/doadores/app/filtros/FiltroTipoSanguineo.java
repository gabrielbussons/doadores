package com.doadores.app.filtros;

public class FiltroTipoSanguineo {

	private Integer positivo_A;
	
	private Integer positivo_B;
	
	private Integer positivo_AB;
	
	private Integer positivo_O;
	
	private Integer negativo_A;
	
	private Integer negativo_B;
	
	private Integer negativo_O;
	
	private Integer negativo_AB;

	public FiltroTipoSanguineo(Integer positivo_A, Integer positivo_B, Integer positivo_AB, Integer positivo_O,
			Integer negativo_A, Integer negativo_B, Integer negativo_O, Integer negativo_AB) {
		super();
		this.positivo_A = positivo_A;
		this.positivo_B = positivo_B;
		this.positivo_AB = positivo_AB;
		this.positivo_O = positivo_O;
		this.negativo_A = negativo_A;
		this.negativo_B = negativo_B;
		this.negativo_O = negativo_O;
		this.negativo_AB = negativo_AB;
	}

	public Integer getPositivo_A() {
		return positivo_A;
	}

	public void setPositivo_A(Integer positivo_A) {
		this.positivo_A = positivo_A;
	}

	public Integer getPositivo_B() {
		return positivo_B;
	}

	public void setPositivo_B(Integer positivo_B) {
		this.positivo_B = positivo_B;
	}

	public Integer getPositivo_AB() {
		return positivo_AB;
	}

	public void setPositivo_AB(Integer positivo_AB) {
		this.positivo_AB = positivo_AB;
	}

	public Integer getPositivo_O() {
		return positivo_O;
	}

	public void setPositivo_O(Integer positivo_O) {
		this.positivo_O = positivo_O;
	}

	public Integer getNegativo_A() {
		return negativo_A;
	}

	public void setNegativo_A(Integer negativo_A) {
		this.negativo_A = negativo_A;
	}

	public Integer getNegativo_B() {
		return negativo_B;
	}

	public void setNegativo_B(Integer negativo_B) {
		this.negativo_B = negativo_B;
	}

	public Integer getNegativo_O() {
		return negativo_O;
	}

	public void setNegativo_O(Integer negativo_O) {
		this.negativo_O = negativo_O;
	}

	public Integer getNegativo_AB() {
		return negativo_AB;
	}

	public void setNegativo_AB(Integer negativo_AB) {
		this.negativo_AB = negativo_AB;
	}
	
	public void add(String tipoSanguineo) {
		
		switch (tipoSanguineo) {
			case "AB+":
				this.positivo_AB++;
				break;
			case "A+":
				this.positivo_A++;
				this.positivo_AB++;
				break;
			case "B+":
				this.positivo_B++;
				this.positivo_AB++;
				break;
			case "O+":
				this.positivo_A++;
				this.positivo_B++;
				this.negativo_AB++;
				this.positivo_O++;
				break;
			case "AB-":
				this.positivo_AB++;
				this.negativo_AB++;
				break;
			case "A-":
				this.positivo_A++;
				this.positivo_AB++;
				this.negativo_A++;
				this.negativo_AB++;
				break;
			case "B-":
				this.positivo_B++;
				this.positivo_AB++;
				this.negativo_B++;
				this.negativo_AB++;
				break;
			case "O-":
				this.positivo_A++;
				this.positivo_B++;
				this.negativo_AB++;
				this.positivo_O++;
				this.negativo_A++;
				this.negativo_B++;
				this.negativo_AB++;
				this.negativo_O++;
				break;
			default:
				break;
		}
	}	
	
	public Integer getByTipoSanguineo(String tipoSanguineo) {
		
		Integer quantidade = 0;
		switch (tipoSanguineo) {
			case "AB+":
				quantidade = getPositivo_AB();
				break;
			case "A+":
				quantidade = getPositivo_A();
				break;
			case "B+":
				quantidade = getPositivo_B();
				break;
			case "O+":
				quantidade = getPositivo_O();
				break;
			case "AB-":
				quantidade = getNegativo_AB();
				break;
			case "A-":
				quantidade = getNegativo_A();
				break;
			case "B-":
				quantidade = getNegativo_B();
				break;
			case "O-":
				quantidade = getNegativo_O();
				break;
			default:
				break;
		}
		return quantidade;
	}
	
}
