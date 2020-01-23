package pdfGeneration;


import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import javafx.collections.ObservableList;
import sample.Order;
import sample.SubOrder;

import javax.swing.text.StyleConstants;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Invoice {

    private Order order;
    private ObservableList<SubOrder> subOrdersList;
    // Set Path to User Desktop
    private String filepath = System.getProperty("user.home").replaceAll("\\\\", "/") + "/Desktop/";

    public Invoice(Order order, ObservableList<SubOrder> subOrdersList) {
        this.order = order;
        this.subOrdersList = subOrdersList;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ObservableList<SubOrder> getSubOrdersList() {
        return subOrdersList;
    }

    public void setSubOrdersList(ObservableList<SubOrder> subOrdersList) {
        this.subOrdersList = subOrdersList;
    }

    public void makeInvoice(){

        filepath = filepath.concat(order.getOrderID()+".pdf");
        System.out.println(filepath);

        try{
            PdfWriter writer = new PdfWriter(filepath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            DeviceRgb topRowColor = new DeviceRgb(169,169,169);

            PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLDOBLIQUE);

            Table orderDetails = new Table(new float[]{100,150});

            // Order details
            orderDetails.addCell(new Cell().add(new Paragraph("Order ID    ")).setBackgroundColor(topRowColor).setBorder(Border.NO_BORDER));
            orderDetails.addCell(new Cell().add(new Paragraph(order.getOrderID())).setBorder(Border.NO_BORDER));
            orderDetails.addCell(new Cell().add(new Paragraph("Order Date  ")).setBackgroundColor(topRowColor).setBorder(Border.NO_BORDER));
            orderDetails.addCell(new Cell().add(new Paragraph(String.valueOf(order.getLocalDateTime()))).setBorder(Border.NO_BORDER));
            orderDetails.addCell(new Cell().add(new Paragraph("Cashier  ")).setBackgroundColor(topRowColor).setBorder(Border.NO_BORDER));
            orderDetails.addCell(new Cell().add(new Paragraph(order.getCashierName())).setBorder(Border.NO_BORDER));
            orderDetails.addCell(new Cell().add(new Paragraph("Store ")).setBackgroundColor(topRowColor).setBorder(Border.NO_BORDER));
            orderDetails.addCell(new Cell().add(new Paragraph(order.getBranch())).setBorder(Border.NO_BORDER));

            orderDetails.setFont(font);

            document.add(orderDetails);

            document.add(new Paragraph("Items:" ));

            // Creating a table
            Table table = new Table(new float[]{20, 200, 20, 300, 100});

            table.addCell(new Cell().add(new Paragraph("#")).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(topRowColor).setFont(font));
            table.addCell(new Cell().add(new Paragraph("Product Name")).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(topRowColor).setFont(font));
            table.addCell(new Cell().add(new Paragraph("Qty")).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(topRowColor).setFont(font));
            table.addCell(new Cell().add(new Paragraph("Description")).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(topRowColor).setFont(font));
            table.addCell(new Cell().add(new Paragraph("Price")).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(topRowColor).setFont(font));

            int itemNo = 1;
            int total = 0;
            // Inserting sub orders into the table
            for(SubOrder subOrder : subOrdersList){
                table.addCell(new Cell().add(new Paragraph(String.valueOf(itemNo))));
                table.addCell(new Cell().add(new Paragraph(subOrder.getProductName())));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(subOrder.getQty()))));
                table.addCell(new Cell().add(new Paragraph(subOrder.getDescription())));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(subOrder.getPrice()))));
                total += subOrder.getQty() * subOrder.getPrice();
                itemNo++;
            }

            int change = order.getCash() - total;

            // Adding Table to document
            document.add(table);

            document.add(new Paragraph("Grand Total: " + total).setFont(font));
            document.add(new Paragraph("Paid: " + order.getCash()).setFont(font));
            document.add(new Paragraph("Change: " + change).setFont(font));

            document.close();

            System.out.println("Invoice made");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void openFile() throws IOException {
        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + filepath);
    }
}