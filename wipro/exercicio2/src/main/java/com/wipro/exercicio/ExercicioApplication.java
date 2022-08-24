package com.wipro.exercicio;

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
		
		System.out.println("Nome: ");
		String nome = scan.nextLine();
		
		System.out.println("Notas: ");
		float nota1 = scan.nextFloat();
		float nota2 = scan.nextFloat();
		float avg = (nota1 + nota2) / 2;
		
		if(avg >= 6) {
			System.out.println("Aprovado com média " + avg);
		} else {
			System.out.println("Reprovado com média " + avg);
		}
		
	}

}
