package br.com.linnik.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;




public class CotacaoDTO {	
	
	private Double cotacaoCompra;
	private Double cotacaoVenda;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")	
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime dataHoraCotacao;
	
	public Double getCotacaoCompra() {
		return cotacaoCompra;
	}
	public void setCotacaoCompra(Double cotacaoCompra) {
		this.cotacaoCompra = cotacaoCompra;
	}
	public Double getCotacaoVenda() {
		return cotacaoVenda;
	}
	public void setCotacaoVenda(Double cotacaoVenda) {
		this.cotacaoVenda = cotacaoVenda;
	}
	public LocalDateTime getDataHoraCotacao() {
		return dataHoraCotacao;
	}
	public void setDataHoraCotacao(LocalDateTime dataHoraCotacao) {
		this.dataHoraCotacao = dataHoraCotacao;
	}
	
	
	
}
