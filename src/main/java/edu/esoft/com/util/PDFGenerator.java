package edu.esoft.com.util;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import edu.esoft.com.entity.Payment;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PDFGenerator {

    /** yyyy‑MM‑dd HH:mm */
    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static byte[] generateBillPDF(Payment payment) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            Document doc = new Document();
            PdfWriter.getInstance(doc, out);
            doc.open();

            /* ---------- fonts ---------- */
            Font title   = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font bold    = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font regular = FontFactory.getFont(FontFactory.HELVETICA, 12);

            doc.add(new Paragraph("Hotel Payment Receipt", title));
            doc.add(new Paragraph(" "));          // blank line

            PdfPTable t = new PdfPTable(2);
            t.setWidthPercentage(100);
            t.setSpacingBefore(10f);

            t.addCell(new Phrase("Reservation ID:", bold));
            t.addCell(new Phrase(String.valueOf(payment.getReservation().getId()), regular));

            t.addCell(new Phrase("Payment ID:", bold));
            t.addCell(new Phrase(String.valueOf(payment.getId()), regular));

            t.addCell(new Phrase("Payment Method:", bold));
            t.addCell(new Phrase(payment.getPaymentMethod().toString(), regular));

            t.addCell(new Phrase("Amount Paid:", bold));
            t.addCell(new Phrase("Rs. " + payment.getAmountPaid(), regular));

            LocalDateTime paidDateTime = payment.getPaidAt();

            t.addCell(new Phrase("Date:", bold));
            t.addCell(new Phrase(paidDateTime.format(FMT), regular));

            doc.add(t);
            doc.add(new Paragraph("\nThank you for your payment!", bold));

            doc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }
}
