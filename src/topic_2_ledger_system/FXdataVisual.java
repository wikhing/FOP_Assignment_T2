package topic_2_ledger_system;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author 
 */
public class FXdataVisual extends JFrame{
    
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private static final String[] categoryArray = {
        "Salary",
        "Business",
        "Investment",
        "Dividend",
        "Pension",
        "Commission",
        "Passive Income",
        "Active Income",
        "Rent",
        "Food",
        "Healthcare",
        "Transportation",
        "Debt",
        "Housing",
        "Travel",
        "Insurance",
        "Other"
    };
    
    private static int user_id;
    public static void set_user(int user_id){
        FXdataVisual.user_id = user_id;
    }
    
    @FXML
    public void back() throws IOException{
        main_system.setScene("menu");
    }
    
    
    
    
    
    
    
    
    
    
//    public FXdataVisual(String title) {
//        super(title);
//    }
    // x-axis 
    public static String getStrMonth (int month) {
        String monthStr = "";
        switch (month) {
            case 1 -> monthStr = "Jan";
            case 2 -> monthStr = "Feb";
            case 3 -> monthStr = "Mar";
            case 4 -> monthStr = "Apr";
            case 5 -> monthStr = "May";
            case 6 -> monthStr = "Jun";
            case 7 -> monthStr = "Jul";
            case 8 -> monthStr = "Aug";
            case 9 -> monthStr = "Sep";
            case 10 -> monthStr = "Oct";
            case 11 -> monthStr = "Nov";
            case 12 -> monthStr = "Dec";
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
            if (transaction.length < 7){
                continue;
            }
            
            if (transaction[6].endsWith(monthYear) && transaction[2].equalsIgnoreCase("debit")) {
                debitMonthYear += Double.parseDouble(transaction[3]);
            }
        }
        return debitMonthYear;
    }
    
    public static double getDebitbyCategory(int user_id, int month, int year, String category) {
        List<String[]> allTransactions = file.get_transactions_csv(user_id);
        Double debitPerCategory = 0.0;
        
        for(int i = 0; i < 12; i++){
            String monthYear = String.format("%02d/%4d", month, year);
            
            for (String[] transaction : allTransactions) {
                if (transaction.length < 7){
                    continue;
                }

                if (transaction[6].endsWith(monthYear) && transaction[2].equalsIgnoreCase("debit")) {

                    if (transaction[4].equalsIgnoreCase(category)) {
                        debitPerCategory += Double.parseDouble(transaction[3]);
//                        System.out.println(category + " equal " + debitPerCategory + transaction[6]);
                    }
    //                for (String c : categoryArray) {
    //                    if (transaction[4].equalsIgnoreCase(category)) {
    //                        debitPerCategory += Double.parseDouble(transaction[3]);
    //                    }
    //                }
                }
            }
            month++;
            
            if(month > 12 ){
                month = 1;
                year++;
            }
        }
        
        
        return debitPerCategory;
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
    
    public static List<Double> getAllDebitCategory(int user_id, int month, int year) {
        List<Double> categoricalDebit = new ArrayList<>();
        
        for (int i = 0; i < 17; i++) {
//            System.out.println("work here counter" + categoryArray[i]);
            categoricalDebit.add(getDebitbyCategory(user_id, month, year, categoryArray[i]));
//            System.out.println("Check " + categoricalDebit.get(i) + categoryArray[i]);
        }
        return categoricalDebit;
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
    
    // Pie Chart for spending
    public void displaySpendingTrendsPie(int user_id, int month, int year) {
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        
        List<String> xAxisMonthYear = getXAxisMonthYear (month, year);
        List<Double> debitCategory = getAllDebitCategory(user_id, month, year);
        
        
        for (int i = 0; i < debitCategory.size(); i++) {
            if (debitCategory.get(i) != 0) {
                pieDataset.setValue(categoryArray[i], debitCategory.get(i)); 
//                System.out.println(categoryArray[i] + " -> " + debitCategory.get(i));
            }
        }
        
        JFreeChart pieChart = ChartFactory.createPieChart("Spending Trends in 12 Months", pieDataset, true, true, false);

        ChartPanel pieChartPanel = new ChartPanel(pieChart);
        pieChartPanel.setPreferredSize(new java.awt.Dimension(1200, 1000));
        setContentPane(pieChartPanel);
    }
    
    //Saving 
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
        boolean isRepayments = false;
        
        for (String[] loanHis: allLoanHis) {
            
            if (loanHis.length < 6){
                continue;
            }
            
            double repayment = Double.parseDouble(loanHis[3]);
            if (loanHis[5].endsWith(monthYear)){
                isRepayments = true;
                if (repayment > repaymentsMonthYear){
                    repaymentsMonthYear = repayment;
                }
            }
        }
        
        if(!isRepayments){
            repaymentsMonthYear = -1;
        }
        return repaymentsMonthYear;
    }
     
    public static List<Double> getRepaymentsTrendFor12Months(int user_id, int month, int year) {

        List<Double> repaymentsTrend = new ArrayList<>();
        
        for (int i = 0; i < 12; i++) { 
            String monthYear = String.format("%02d/%04d", month, year);
            repaymentsTrend.add(getRepaymentsByMonthYear(user_id, monthYear));
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
        boolean isLoan = false;
        
        for (String[] loanHis: allLoanHis) {

            if (loanHis.length < 6){
                continue;
            }
            
            double loan = Double.parseDouble(loanHis[4]);
            if (loanHis[5].endsWith(monthYear)){
                isLoan = true;
                if (loan < loanMonthYear){
                    loanMonthYear = loan;
                }
            }
        }
        if(!isLoan){
            loanMonthYear = -1;
        }
        return loanMonthYear;
    }
   
   public static List<Double> getLoanTrendFor12Months(int user_id, int month, int year) {

        List<Double> loanTrend = new ArrayList<>();
        
        for (int i = 0; i < 12; i++) { 
            String monthYear = String.format("%02d/%04d", month, year);
            loanTrend.add(getLoanByMonthYear(user_id, monthYear));
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
   
    @FXML TextField tfmonthYear = new TextField();
    @FXML ChoiceBox trend_choice = new ChoiceBox();
    
    @FXML Label dataV_info = new Label();

    public void dataVisualization () {
        
        String monthYear;
        monthYear = tfmonthYear.getText();
        if (!monthYear.matches("\\d{2}/\\d{4}")) {
            dataV_info.setText("Invalid format. Enter with format MM/YYYY");
            return;
        }
        
        
        String[] strArrMonthYear = monthYear.split("/");
        int[] arrMonthYear = new int[strArrMonthYear.length];
        for (int i = 0; i < arrMonthYear.length; i++) {
            arrMonthYear[i] = Integer.parseInt(strArrMonthYear[i]);
        }
        int month = arrMonthYear[0];
        int year = arrMonthYear[1];
        
        
        String optionString = trend_choice.getValue().toString();
        switch(optionString) {
            case "Spending" -> {
                FXdataVisual dataVisualization = new FXdataVisual();
                dataVisualization.setTitle("Spending Trends Over 12 Months");
                dataVisualization.displaySpendingTrendsPie(user_id, month, year);
                dataVisualization.pack();        
                dataVisualization.setVisible(true); 
                dataVisualization.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
            case "Savings" -> {
                FXdataVisual dataVisualization = new FXdataVisual();
                dataVisualization.setTitle("Saving Growth Over 12 Months");
                dataVisualization.displaySavingsGrowth(user_id, month, year);
                dataVisualization.pack();        
                dataVisualization.setVisible(true); 
                dataVisualization.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
            
            case "Loan Repayments" -> {
                FXdataVisual dataVisualization = new FXdataVisual();
                dataVisualization.setTitle("Repayments Trends Over 12 Months");
                dataVisualization.displayRepaymentsTrend(user_id, month, year);
                dataVisualization.pack();        
                dataVisualization.setVisible(true);
                dataVisualization.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
            
            case "Loan" -> {
                FXdataVisual dataVisualization = new FXdataVisual();
                dataVisualization.setTitle("Loan Trends Over 12 Months");
                dataVisualization.displayLoanTrend(user_id, month, year);
                dataVisualization.pack();        
                dataVisualization.setVisible(true);
                dataVisualization.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
            
            default -> dataV_info.setText("Choose a Trend and Chart.");
        }
            
    }
}
