package com.rgb.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.rgb.api.entities.Empresa;
import com.rgb.api.repositories.EmpresaRepository;
import com.rgb.api.utils.SenhaUtils;

@SpringBootApplication
public class MeuPrimeiroProjetoApplication {

	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;

	@Autowired
	private EmpresaRepository empresaRepository;

	public static void main(String[] args) {
		SpringApplication.run(MeuPrimeiroProjetoApplication.class, args);
		System.out.println("Meu primeiro projeto!");
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			String senhaEncoded = SenhaUtils.gerarBCrypt("123456");
			System.out.println("Senha encoded: " + senhaEncoded);

			senhaEncoded = SenhaUtils.gerarBCrypt("123456");
			System.out.println("Senha encoded novamente: " + senhaEncoded);

			System.out.println("Senha valida: " + SenhaUtils.senhaValida("123456", senhaEncoded));

			System.out.println("Senha invalida: " + SenhaUtils.senhaValida("1234567", senhaEncoded));

			System.out.println("##Qtd de elementos por página = " + this.qtdPorPagina);

			Empresa empresa = new Empresa();
			empresa.setRazaoSocial("RGB IT");
			empresa.setCnpj("1234567891236456");

			this.empresaRepository.save(empresa);

			List<Empresa> empresas = empresaRepository.findAll();
			empresas.forEach(System.out::println);

			Empresa empresaDB = empresaRepository.findOne(1L);
			System.out.println("Empresa por ID: " + empresaDB);

			empresaDB.setRazaoSocial("RGB IT WEB");
			this.empresaRepository.save(empresaDB);

			Empresa empresaCnpj = empresaRepository.findByCnpj("1234567891236456");
			System.out.println("Empresa por CNPJ: " + empresaCnpj);

			this.empresaRepository.delete(1L);
			empresas = empresaRepository.findAll();
			System.out.println("Empresas: " + empresas.size());

		};
	}
}
