package saved;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

/*
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
*/
import crossword.Draw;
import crossword.Draw2;

public class myPDF {

	public void makePDF(String filename, JFrame window) throws DocumentException, IOException {
		   
		   Date date = new Date();
	       filename+="\\"+date.getTime()+"PDF.pdf";
	       try {
		    	File file = new File(filename);
		    	if(file.exists())
		    	file.delete();
		    	Document document = new Document();
		        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
		        document.open();
		        PdfContentByte pcb = writer.getDirectContent();
		        PdfTemplate temp = pcb.createTemplate(PageSize.A4.getWidth(),PageSize.A4.getHeight());
		        pcb.addTemplate(temp, 0, 0);
		        Graphics2D g = temp.createGraphics(PageSize.A4.getWidth(),PageSize.A4.getHeight());
		        g.scale(0.5, 1);
		        for(int i=1; i< window.getContentPane().getComponents().length; i++){
		            Component c = window.getContentPane().getComponent(i);
		            if(c instanceof JButton || c instanceof JPanel){
		                g.translate(c.getBounds().x,c.getBounds().y);
		                c.paintAll(g);
		                c.addNotify();
		                c.revalidate();
		            }
		        }
		        writer.flush();
		        g.dispose();
		        document.close();
		        writer.close();
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
		}	       
}
