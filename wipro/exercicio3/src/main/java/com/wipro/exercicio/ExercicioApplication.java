package com.wipro.exercicio;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExercicioApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExercicioApplication.class, args);
		Scanner scan = new Scanner(System.in).useLocale(Locale.US);

		ArrayList<String> candidatos = new ArrayList<String>();
		boolean fim = false;
		int[] votos = new int[4];
		
		

		for (int i = 0; i < 4; i += 1) {
			System.out.println("Nome do candidato nº " + i+1);
			String candidato = scan.nextLine();
			candidatos.add(candidato);
		}
		
		String menu = String.format("---Votação para Presidente da turma----"
				+ "\nCandidato 01: "
				+ "\nCandidato 02: "
				+ "\nCandidato 03: "
				+ "\nCandidato 04: ", candidatos.get(0), candidatos.get(1), candidatos.get(2), candidatos.get(3));
		
		
		do {
			System.out.println("Escolha o número do seu candidato (0 - para Sair)\n"+menu);
			int cadidatoEscolhido = scan.nextInt();
			
			if(cadidatoEscolhido == 0) {
				fim = true;
			} else if(cadidatoEscolhido < 0 || cadidatoEscolhido > 4) {
				System.out.println("Valor inválido! Escolha novamente.\n");
			} else {
				votos[cadidatoEscolhido-1] += 1;
			}				
			
		} while(!fim);
		
		int index = posicaoCandidadoGanhador(votos);
		resultado(votos, candidatos, candidatos.get(index));
	}
		
	
	static void resultado(int[] votos, ArrayList<String> candidatos, String ganhador) {
		System.out.println("\n\n\n\n\n-------RESULTADO---------\n");
		for(int i = 0; i < 4; i++) {
			System.out.println("Candidato " + i+1 + ": " + candidatos.get(i) + " - Total de votos: " + votos[i]);
		}
		System.out.println("VENCEDOR: " + ganhador);
	}
	
	static int posicaoCandidadoGanhador(int[] votos) {
		int maior = votos[0];
		int indexGanhador = 0;
		for(int i = 0; i< votos.length; i+=1) {
			if(votos[i] >=  maior) {
				maior = votos[i];
				indexGanhador= i;
			}
		}
		return indexGanhador;
	}

}
