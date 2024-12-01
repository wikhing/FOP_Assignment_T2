/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package topic_2_ledger_system;








import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;

import java.util.List;
import java.util.ArrayList;

import java.util.Scanner;
public class data_visualization extends ApplicationFrame {

    public data_visualization(String title) {
        super(title);
    }
    
    public static String getStrMonth (int month) {
        String monthStr = "";
        switch (month) {
            case 1 -> {
                monthStr = "Jan";
            }
            case 2 -> {
                monthStr = "Feb";
            }
            case 3 -> {
                monthStr = "Mar";
            }
            case 4 -> {
                monthStr = "Apr";
            }
            case 5 -> {
                monthStr = "May";
            }
            case 6 -> {
                monthStr = "Jun";
            }
            case 7 -> {
                monthStr = "Jul";
            }
            case 8 -> {
                monthStr = "Aug";
            }
            case 9 -> {
                monthStr = "Sep";
            }
            case 10 -> {
                monthStr = "Oct";
            }
            case 11 -> {
                monthStr = "Nov";
            }
            case 12 -> {
                monthStr = "Dec";
            } 
        }
        return monthStr;
    }

    public static double getDebitbyMonthYear(int user_id, String monthYear) {
        List<String[]> allTransactions = file.get_transactions_csv(user_id);
        Double debitMonthYear = 0.0;
        
        for (String[] transaction : allTransactions) {
            if (transaction.length < 6){
                continue;
            }
            
            if (transaction[5].endsWith(monthYear) && transaction[2].equalsIgnoreCase("debit")) {
                debitMonthYear += Double.parseDouble(transaction[3]);
            }
        }
        return debitMonthYear;
    }

    public static List<Double> getDebitbyYear(int user_id, int month, int year) {
       
        List<Double> monthlyDebit = new ArrayList<>();
        
        for (int i = 0; i < 12; i++) { 
            String monthYear = String.format("%02d/%4d", month, year);
            monthlyDebit.add(getDebitbyMonthYear(user_id, monthYear));
            month++;
            
            if(month > 12 ){
                month = 1;
                year++;
            }
        }
        return monthlyDebit;
    }
    
    public static double maxMonthlyDebit (List<Double> monthlyDebit){
        double max = monthlyDebit.get(0); 
        for (int i = 1; i < monthlyDebit.size(); i++) { 
            if (monthlyDebit.get(i) > max) { 
                max = monthlyDebit.get(i); 
            } 
        }
        return max;
    }
    
    public static List<String> getXAxisMonthYear (int month, int year) {
        
        List<String> xAxisMonthYear = new ArrayList<>();
        
        
        for (int i = 0; i < 12; i++){
            String monthYear = getStrMonth(month) + " " + year;
            xAxisMonthYear.add(monthYear);
            month++;
            
            if(month > 12 ){
                month = 1;
                year++;
            }
            System.out.println(monthYear);
        }
        return xAxisMonthYear;
        
    }
    
    public void displaySpendingTrends(int user_id, int month, int year) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        
        List<String> xAxisMonthYear = getXAxisMonthYear (month, year);
        List<Double> monthlyDebit = getDebitbyYear(user_id, month, year);

        System.out.println("Monthly Debit: " + monthlyDebit);


        for (int i = 0; i < monthlyDebit.size(); i++) {
            dataset.addValue(monthlyDebit.get(i), "Spending", xAxisMonthYear.get(i));
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Spending Trends Over Time",   // Chart title
                "Month",                       // X-axis label
                "Amount (RM)",                 // Y-axis label
                dataset,                       // Dataset
                org.jfree.chart.plot.PlotOrientation.VERTICAL,
                true,                          // Include legend
                true,                          // Tooltips
                false                          // URLs
        );

        // Get the Category Plot
        CategoryPlot plot = barChart.getCategoryPlot();


        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();

        // Set the Y-axis range (minimum and maximum values)
        rangeAxis.setRange(0, maxMonthlyDebit(monthlyDebit) + 1000);  // Adjust the maximum based on your data range

        // Set the Y-axis tick unit (adjust interval between ticks)
        rangeAxis.setTickUnit(new NumberTickUnit(500));  // Adjust interval to 500 (or other value)

        // Create the ChartPanel and set it as the content pane
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1200, 1000));
        setContentPane(chartPanel);
    }
   /* public static double getDebitbyMonthYear(int user_id, String monthYear) {
        List<String[]> allTransactions = file.get_transactions_csv(user_id);
        Double debitMonthYear = 0.0;
        
        for (String[] transaction : allTransactions) {
            if (transaction.length < 6){
                continue;
            }
            
            if (transaction[5].endsWith(monthYear) && transaction[2].equalsIgnoreCase("debit")) {
                debitMonthYear += Double.parseDouble(transaction[3]);
            }
        }
        return debitMonthYear;
    }
    public static List<Double> savingBalances(int user_id, int month, int year) {
        List<String[]> allTransactions = file.get_transactions_csv(user_id);
        
        List<Double> savingBalances = new ArrayList<>();
        
        for (int i = 0; i < 12; i++) { 
            String monthYear = String.format("%02d/%4d", month, year);
        for (String[] transaction : allTransactions) {
            if (transaction.length < 6){
                continue;
            }
            
            if (transaction[5].endsWith(monthYear) && transaction[2].equalsIgnoreCase("debit")) {
                debitMonthYear += Double.parseDouble(transaction[3]);
            }
            monthlyDebit.add(getDebitbyMonthYear(user_id, monthYear));
            month++;
            
            if(month > 12 ){
                month = 1;
                year++;
            }
        }
        return monthlyDebit;
    }
    
    public void displaySavingsGrowth(int user_id, List<Double> savingsBalances, List<String> months) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Populate the dataset with savings balances and corresponding months
        for (int i = 0; i < savingsBalances.size(); i++) {
            dataset.addValue(savingsBalances.get(i), "Savings", months.get(i));
        }

        // Create the line chart
        JFreeChart lineChart = ChartFactory.createLineChart(
                "Savings Growth Over Time",  // Chart title
                "Month",                     // X-axis label
                "Savings Balance (RM)",      // Y-axis label
                dataset                      // Dataset
        );

        // Create a chart panel and set it as the content pane
        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);
    }

  */
    public static void dataVisualization (int user_id) {
        
        Scanner sc = new Scanner(System.in);
        
        System.out.println("\n\n== Data visualization ==");
        System.out.println("Data for the next 12 months will be displayed.");  
        
        String monthYear;
        
        while (true){
            System.out.print("Enter the starting month and year (MM/YYYY):");
            monthYear = sc.nextLine();
            if (monthYear.matches("\\d{2}/\\d{4}")) {
                break;
            } else {
                System.out.println("Invalid format. Hint: DD/MM/YYYY");
            }
        }
        
        String[] strArrMonthYear = monthYear.split("/");
        int[] arrMonthYear = new int[strArrMonthYear.length];
        for (int i = 0; i < arrMonthYear.length; i++) {
            arrMonthYear[i] = Integer.parseInt(strArrMonthYear[i]);
        }
        int month = arrMonthYear[0];
        int year = arrMonthYear[1];
        
        
       
        System.out.println("\nPlease select an option to view the trends:");
        System.out.println("1. Spending trends");
        System.out.println("2. Savings growth");
        System.out.println("3. Loan repayments");
        int optionDataVisual = 0;
        while (true){
            if (sc.hasNextInt()) {
                    System.out.print("Enter your choice(1-3): ");
                    optionDataVisual = sc.nextInt();
                    sc.nextLine(); 

                    if (optionDataVisual >= 1 && optionDataVisual <= 3) {
                        break;
                    } else {
                        System.out.println("Invalid option. Please enter a number between 1 and 3.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid number.");
                    sc.nextLine(); 
            }
        }
        
        switch(optionDataVisual) {
            case 1 -> {
                data_visualization dataVisualization = new data_visualization("Spending Trends Over 12 Months");
                dataVisualization.displaySpendingTrends(user_id, month, year);
                dataVisualization.pack();        
                dataVisualization.setVisible(true); 
            }
        }
    }
}
