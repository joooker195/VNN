package xml;

import logs.Log;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.charts.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
/**
 * Created by Ксю on 12.09.2017.
 */
public class DataExchange
{
    public static ArrayList<Double> dataout;
    public static ArrayList<Double> datain;
    public static ArrayList<String> date = new ArrayList<String>();
    public static ArrayList<Double> dataNumber = new ArrayList<Double>();


    public static void writeToExcel(String nameFile) throws FileNotFoundException, IOException
    {
        try {
            for (int i = 0; i < dataout.size(); i++) {
                double a = i;
                dataNumber.add(a);
            }
            Workbook wd1 = new XSSFWorkbook();
            Sheet sheet = wd1.createSheet("График обучения");
            FileOutputStream fo = new FileOutputStream(nameFile);

            for (int i = 0; i < dataout.size(); i++) {
                Row row = sheet.createRow(i);
                Cell celld = row.createCell(2);
                Cell cellk = row.createCell(1);
                Cell celln = row.createCell(0);
                celld.setCellValue(dataout.get(i));
                cellk.setCellValue(datain.get(i));
                celln.setCellValue(dataNumber.get(i));

            }
            final int NUM_OF_ROWS = datain.size();//4500
            // Create a row and put some cells in it. Rows are 0 based.


            Drawing drawing = sheet.createDrawingPatriarch();

            ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 4, 7, 25, 26);

            Chart chart = drawing.createChart(anchor);

            ChartLegend legend = chart.getOrCreateLegend();
            legend.setPosition(LegendPosition.TOP_RIGHT);

            LineChartData data1 = chart.getChartDataFactory().createLineChartData();

            // Use a category axis for the bottom axis.
            ChartAxis bottomAxis = chart.getChartAxisFactory().createCategoryAxis(AxisPosition.BOTTOM);
            ValueAxis leftAxis = chart.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
            leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);

            ChartDataSource<Number> xs = DataSources.fromNumericCellRange(sheet, new CellRangeAddress(0, NUM_OF_ROWS - 1, 0, 0));
            ChartDataSource<Number> ys1 = DataSources.fromNumericCellRange(sheet, new CellRangeAddress(0, NUM_OF_ROWS - 1, 1, 1));
            ChartDataSource<Number> ys2 = DataSources.fromNumericCellRange(sheet, new CellRangeAddress(0, NUM_OF_ROWS - 1, 2, 2));


            data1.addSeries(xs, ys1);
            data1.addSeries(xs, ys2);

            chart.plot(data1, bottomAxis, leftAxis);


            wd1.write(fo);
            fo.close();
        }
        catch (IOException e)
        {
            Log.debug("DataExchange#writeToExcel(String nameFile)", e.toString(), e.getStackTrace());
        }

    }

    public static ArrayList readFromExcel(String file, String nameSheet) throws IOException
    {
        HSSFWorkbook myExcelBook = new HSSFWorkbook(new FileInputStream(file));
        HSSFSheet myExcelSheet = myExcelBook.getSheet(nameSheet);
        HSSFRow row = myExcelSheet.getRow(0);
        ArrayList<Double> data = new ArrayList<Double>();
        int i = 0;
        row = myExcelSheet.getRow(i);
        while (row!=null)
        {
            Date date1 = row.getCell(0).getDateCellValue();
            data.add(i, Double.valueOf(row.getCell(1).getStringCellValue()));
            date.add(i,date1.toLocaleString());
            i++;
            row = myExcelSheet.getRow(i);
        }
        myExcelBook.close();
        return data;
    }
}
