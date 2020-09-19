package br.com.linnik.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.linnik.dto.CotacaoDTO;
import br.com.linnik.dto.CotacoesDTO;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class APITest {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8080/cotacoes";
	}
	
	@Test
	public void verificaDisponibilidade() {
		RestAssured
		.given()
			.log().all()
		.when()
			.get("/data?dataCotacao=01-02-2020")			
		.then()
			.log().all()
			.statusCode(200);
	}
	
	@Test
	public void verificaDadosObrigatoriosEstaoSendoRetornados() throws JsonMappingException, JsonProcessingException {		
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/data?dataCotacao=01-02-2020");		
		ResponseBody body = response.getBody();		
		String bodyAsString = body.asString();
		ObjectMapper objectMapper = new ObjectMapper();
		CotacoesDTO cotacoes = objectMapper.readValue(bodyAsString, CotacoesDTO.class);
		CotacaoDTO cotacao = cotacoes.getValue().get(0);		
		Assert.assertNotNull(cotacao.getCotacaoCompra());
		Assert.assertNotNull(cotacao.getCotacaoVenda());
		Assert.assertNotNull(cotacao.getDataHoraCotacao());		
	}
	
	@Test
	public void verificaSePrecoVendaMaiorPrecoCompra() throws JsonMappingException, JsonProcessingException {		
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/data?dataCotacao=01-02-2020");		
		ResponseBody body = response.getBody();		
		String bodyAsString = body.asString();
		ObjectMapper objectMapper = new ObjectMapper();
		CotacoesDTO cotacoes = objectMapper.readValue(bodyAsString, CotacoesDTO.class);
		CotacaoDTO cotacao = cotacoes.getValue().get(0);		
		Assert.assertTrue(cotacao.getCotacaoVenda() > cotacao.getCotacaoCompra());		
	}	
	
	@Test
	public void validaDataInvalida() {
		RestAssured
		.given()
			.log().all()
		.when()
			.get("/data?dataCotacao=datainvalida")			
		.then()
			.log().all()
			.statusCode(400)
			//Esta é a mensagem padrão retornada pelo serviço. Com mais tempo seria customizada com ProblemDetails.
			.body("message", CoreMatchers.is("Validation failed for object='cotacaoFilter'. Error count: 1"));
	}
	
	
	@Test
	public void validaDataObrigatoria() {
		RestAssured
		.given()
			.log().all()
		.when()
			.get("/data")			
		.then()
			.log().all()
			.statusCode(400)
			//Esta é a mensagem padrão retornada pelo serviço. Com mais tempo seria customizada com ProblemDetails.
			.body("message", CoreMatchers.is("Validation failed for object='cotacaoFilter'. Error count: 1"));
	}	
	
}





