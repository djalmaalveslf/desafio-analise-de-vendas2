package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import entities.Sale;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		List<Sale> list = new ArrayList<>();
		Map<String, Double> totalSellerSale = new HashMap<>();
		
		System.out.print("Entre o caminho do arquivo: ");
		String strPath = sc.nextLine();
		
		try(BufferedReader br = new BufferedReader(new FileReader(strPath))){			
			String line = br.readLine();
			
			while(line != null) {				
				String[] fields = line.split(",");
				
				list.add(new Sale(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), fields[2], 
									Integer.parseInt(fields[3]), Double.parseDouble(fields[4])));
				
				totalSellerSale.put(fields[2], null);
				
				line = br.readLine();
			}
			
			System.out.println();
			
			for(String key : totalSellerSale.keySet()) {
				double totalValue = list.stream()
										.filter(s -> s.getSeller().toUpperCase().equals(key.toUpperCase()))
										.map(s -> s.getTotal())
										.reduce(0.0, (x, y) -> x+y);
				
				totalSellerSale.replace(key, totalValue);
			}
			
			System.out.println("Total de vendas por vendedor:");
			for(String key : totalSellerSale.keySet()) {
				System.out.println(key + " - " + String.format("%.2f", totalSellerSale.get(key)));
			}			
			
		}
		catch(IOException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		
		sc.close();
	}
}