package com.example.taskmanagement.utils;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;

public class PdfUtils
{
    public static void writePDF(String worker, int hoursWorked, double salary)
    {
        String dest = "example.pdf";
        try
        {
            PdfWriter writer = new PdfWriter(dest);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            // Define column widths; here we create a table with 3 column
            float[] columnWidths = {1, 1, 1}; // Ratio of column widths
            Table table = new Table(UnitValue.createPercentArray(columnWidths));

            ImageData data = ImageDataFactory.create("java_hotel.jpg");
            data.setWidth(250);
            data.setHeight(250);
            Image image = new Image(data);
            document.add(image);

            // Headers of the table
            table.addCell("Worker").addCell("Hours worked").addCell("Salary");

            table.addCell(worker).addCell(String.valueOf(hoursWorked)).addCell(String.format("%.2f€", salary));

            table.setWidth(500);

            document.add(table);
            document.close();
            System.out.println("Table PDF created.");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
