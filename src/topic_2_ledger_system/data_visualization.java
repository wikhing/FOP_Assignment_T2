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
import org.jfree.chart.renderer.category.LineAndShapeRenderer;


import java.awt.geom.Ellipse2D;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.Scanner;

public class data_visualization extends ApplicationFrame {

    public data_visualization(String title) {
        super(title);
    }
   // x-axis 
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


            }
            return xAxisMonthYear;
    }
    
    //Spending Trend

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

    public static List<Double> getDebitfor12Months(int user_id, int month, int year) {
       
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
    
    public void displaySpendingTrends(int user_id, int month, int year) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        
        List<String> xAxisMonthYear = getXAxisMonthYear (month, year);
        List<Double> monthlyDebit = getDebitfor12Months(user_id, month, year);

        
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


        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1200, 1000));
        setContentPane(chartPanel);
    }
   public static double getSavingsByMonthYear(int user_id, String monthYear) {
        List<String[]> allSavingsHis = file.get_savHistory_csv(user_id);
        double SavingsMonthYear = 0.0;
        boolean isSavings = false;
        
        for (String[] savingsHis: allSavingsHis) {

            if (savingsHis.length < 5){
                continue;
            }
            
            
            double savings = Double.parseDouble(savingsHis[3]);
            if (savingsHis[4].endsWith(monthYear)){
                isSavings = true;
                if (savings > SavingsMonthYear){
                    SavingsMonthYear = savings;
                }
                
            }
             
        }
        
        if(!isSavings){
            SavingsMonthYear = -1;
        }
        
        return SavingsMonthYear;
    }
    public static List<Double> getSavingsFor12Months(int user_id, int month, int year) {

        List<Double> Savings = new ArrayList<>();
        
        for (int i = 0; i < 12; i++) { 
            String monthYear = String.format("%02d/%04d", month, year);
            Savings.add(getSavingsByMonthYear(user_id, monthYear));
            month++;
            
            if(month > 12 ){
                month = 1;
                year++;
            }
        }
        return Savings;
    }
    
    
    public void displaySavingsGrowth(int user_id, int month, int year) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        List<String> xAxisMonthYear = getXAxisMonthYear (month, year);
        List<Double> SavingsFor12Months = getSavingsFor12Months(user_id, month, year);
        
        Map<String, Double> savingsData = new HashMap<>();
        
        for (int i = 0; i < xAxisMonthYear.size(); i++){
            if (SavingsFor12Months.get(i) != -1){
                savingsData.put(xAxisMonthYear.get(i), SavingsFor12Months.get(i));
            }
        }
   

        for (String monthYear : xAxisMonthYear) {
            Double savings = savingsData.get(monthYear); 
            if (savings != null) {
                dataset.addValue(savings, "Total Savings", monthYear); 
            } else {
                dataset.addValue(null, "Total Savings", monthYear); 
            }
        }

       

        // Create the line chart
        JFreeChart lineChart = ChartFactory.createLineChart(
                "Savings Growth Over Time",  // Chart title
                "Month",                     // X-axis label
                "Total Savings (RM)",      // Y-axis label
                dataset                      // Dataset
        );
        
        CategoryPlot plot = lineChart.getCategoryPlot(); // Get the plot
        LineAndShapeRenderer renderer = new LineAndShapeRenderer();

        // Enable both lines and shapes (dots)
        renderer.setSeriesLinesVisible(0, true);  // Show line
        renderer.setSeriesShapesVisible(0, true); // Show shapes (dots)

        // Customize dot shape (e.g., small circles)
        renderer.setSeriesShape(0, new Ellipse2D.Double(-3, -3, 6, 6)); // Circle with radius 3
        plot.setRenderer(renderer); // Apply the renderer to the plot


        // Create a chart panel and set it as the content pane
        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1200, 1000));
        setContentPane(chartPanel);
    }
    
     public static double getRepaymentsByMonthYear(int user_id, String monthYear) {
        List<String[]> allLoanHis = file.get_loanHistory_csv(user_id);
        double repaymentsMonthYear = 0.0;
        
        for (String[] loanHis: allLoanHis) {

            if (loanHis.length < 6){
                continue;
            }
            
            double repayment = Double.parseDouble(loanHis[3]);
            if (loanHis[5].endsWith(monthYear)){
                if (repayment > repaymentsMonthYear){
                    repaymentsMonthYear = repayment;
                }
            }
        }
        return repaymentsMonthYear;
    }
     
    public static List<Double> getRepaymentsTrendFor12Months(int user_id, int month, int year) {

        List<Double> repaymentsTrend = new ArrayList<>();
        
        for (int i = 0; i < 12; i++) { 
            String monthYear = String.format("%02d/%04d", month, year);
            repaymentsTrend.add(getRepaymentsByMonthYear(user_id, monthYear));
            System.out.println(getRepaymentsByMonthYear(user_id, monthYear));
            month++;
            
            if(month > 12 ){
                month = 1;
                year++;
            }
        }
        return repaymentsTrend;
    }
    
    

   public void displayRepaymentsTrend(int user_id, int month, int year) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        List<String> xAxisMonthYear = getXAxisMonthYear (month, year);
        List<Double> repaymentsTrendFor12Months = getRepaymentsTrendFor12Months(user_id, month, year);
        
        Map<String, Double> repaymentsData = new HashMap<>();
        
        
        for (int i = 0; i < xAxisMonthYear.size(); i++){
            if (repaymentsTrendFor12Months.get(i) > 0.0 || repaymentsTrendFor12Months.get(i) == 0.0 && repaymentsTrendFor12Months.get(i + 1) != 0.0){
                repaymentsData.put(xAxisMonthYear.get(i), repaymentsTrendFor12Months.get(i));
            }
        }
        
        for (String monthYear : xAxisMonthYear) {
            Double repayments = repaymentsData.get(monthYear); 
            if (repayments != null) {
                dataset.addValue(repayments, "Repayments", monthYear); 
            } else {
                dataset.addValue(null, "Repayments", monthYear); 
            }
        }
        

        // Create the line chart
        JFreeChart lineChart = ChartFactory.createLineChart(
                "Repayments Trend Over Time",  // Chart title
                "Month",                     // X-axis label
                "Amount (RM)",      // Y-axis label
                dataset                      // Dataset
        );
        
        CategoryPlot plot = lineChart.getCategoryPlot(); // Get the plot
        LineAndShapeRenderer renderer = new LineAndShapeRenderer();

        // Enable both lines and shapes (dots)
        renderer.setSeriesLinesVisible(0, true);  // Show line
        renderer.setSeriesShapesVisible(0, true); // Show shapes (dots)

        // Customize dot shape (e.g., small circles)
        renderer.setSeriesShape(0, new Ellipse2D.Double(-3, -3, 6, 6)); // Circle with radius 3
        plot.setRenderer(renderer); // Apply the renderer to the plot

        // Create a chart panel and set it as the content pane
        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1200, 1000));
        setContentPane(chartPanel);
    }
   
   public static double getLoanByMonthYear(int user_id, String monthYear) {
        List<String[]> allLoanHis = file.get_loanHistory_csv(user_id);
        double loanMonthYear = Double.MAX_VALUE;
        
        for (String[] loanHis: allLoanHis) {

            if (loanHis.length < 6){
                continue;
            }
            
            double loan = Double.parseDouble(loanHis[4]);
            if (loanHis[5].endsWith(monthYear)){
                if (loan < loanMonthYear){
                    loanMonthYear = loan;
                }
            }
        }
        return loanMonthYear;
    }
   
   public static List<Double> getLoanTrendFor12Months(int user_id, int month, int year) {

        List<Double> loanTrend = new ArrayList<>();
        
        for (int i = 0; i < 12; i++) { 
            String monthYear = String.format("%02d/%04d", month, year);
            loanTrend.add(getLoanByMonthYear(user_id, monthYear));
            System.out.println(getLoanByMonthYear(user_id, monthYear));
            month++;
            
            if(month > 12 ){
                month = 1;
                year++;
            }
        }
        return loanTrend;
    }
   
   public void displayLoanTrend(int user_id, int month, int year) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        List<String> xAxisMonthYear = getXAxisMonthYear (month, year);
        List<Double> loanTrendFor12Months = getLoanTrendFor12Months(user_id, month, year);
        
        Map<String, Double> loanData = new HashMap<>();
        
        for (int i = 0; i < xAxisMonthYear.size(); i++){
            if (loanTrendFor12Months.get(i) > 0 || loanTrendFor12Months.get(i) == 0.0 && loanTrendFor12Months.get(i - 1) != 0.0 ){
                loanData.put(xAxisMonthYear.get(i), loanTrendFor12Months.get(i));
            }
        }
        
        for (String monthYear : xAxisMonthYear) {
            Double loan = loanData.get(monthYear); 
            if (loan != null) {
                dataset.addValue(loan, "Loan", monthYear); 
            } else {
                dataset.addValue(null, "Loan", monthYear); 
            }
        }
        
         // Create the line chart
        JFreeChart lineChart = ChartFactory.createLineChart(
                "Loan Trend Over Time",  // Chart title
                "Month",                     // X-axis label
                "Amount (RM)",      // Y-axis label
                dataset                      // Dataset
        );
        
        
        CategoryPlot plot = lineChart.getCategoryPlot(); // Get the plot
        LineAndShapeRenderer renderer = new LineAndShapeRenderer();

        // Enable both lines and shapes (dots)
        renderer.setSeriesLinesVisible(0, true);  // Show line
        renderer.setSeriesShapesVisible(0, true); // Show shapes (dots)

        // Customize dot shape (e.g., small circles)
        renderer.setSeriesShape(0, new Ellipse2D.Double(-3, -3, 6, 6)); // Circle with radius 3
        plot.setRenderer(renderer); // Apply the renderer to the plot

        // Create a chart panel and set it as the content pane
        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1200, 1000));
        setContentPane(chartPanel);
   }

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
                System.out.println("Invalid format. Hint: DD/MM/YYYY\n");
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
        System.out.println("3. Repayments trend");
        System.out.println("4. Loan trend");
        int optionDataVisual = 0;
        while (true){
            System.out.print("\nEnter your choice(1-4): ");
            if (sc.hasNextInt()) {
                    optionDataVisual = sc.nextInt();
                    sc.nextLine(); 

                    if (optionDataVisual >= 1 && optionDataVisual <= 4) {
                        break;
                    } else {
                        System.out.println("Invalid option. Please enter a number between 1 and 4.");
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
            case 2 -> {
                data_visualization dataVisualization = new data_visualization("Saving Growth Over 12 Months");
                dataVisualization.displaySavingsGrowth(user_id, month, year);
                dataVisualization.pack();        
                dataVisualization.setVisible(true); 
            }
            
            case 3 -> {
                data_visualization dataVisualization = new data_visualization("Repayments Trends Over 12 Months");
                dataVisualization.displayRepaymentsTrend(user_id, month, year);
                dataVisualization.pack();        
                dataVisualization.setVisible(true);
            }
            
            case 4 -> {
                data_visualization dataVisualization = new data_visualization("Loan Trends Over 12 Months");
                dataVisualization.displayLoanTrend(user_id, month, year);
                dataVisualization.pack();        
                dataVisualization.setVisible(true);
                
            }
        }
            
    }
}
