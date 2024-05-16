package com.example.taskmanagement.utils;

import com.example.taskmanagement.models.Trabajador;
import com.example.taskmanagement.models.Trabajo;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import javafx.concurrent.Worker;

import java.math.BigDecimal;

public class PdfUtils
{
    public static void writePDF(String paycheckName, Trabajador worker)
    {
        String dest = paycheckName+".pdf";
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
            BigDecimal hours=worker.getTrabajos().stream().map(Trabajo::getTiempo).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal salary=worker.getTrabajos().stream().map(Trabajo::getTiempo).reduce(BigDecimal.ZERO, (og,add)->og.add(add.multiply(new BigDecimal(20))));
            table.addCell(worker.getNombre()).addCell(String.valueOf(hours)).addCell(String.format("%.2f€", salary));

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
