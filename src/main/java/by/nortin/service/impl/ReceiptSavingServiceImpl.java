package by.nortin.service.impl;

import by.nortin.dto.TransactionDto;
import by.nortin.service.ReceiptSavingService;
import java.io.File;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ReceiptSavingServiceImpl implements ReceiptSavingService {

    @Override
    public void saveReceipt(TransactionDto transactionDto) {

        String receiptText = createReceiptText(transactionDto);

        //library with vulnerabilities
//        savePdfFormatItexst(receiptText);
        //apachePDF
//        savePdfFormatApache(receiptText);
        saveTxtFormat(receiptText, transactionDto.getId());
    }

    private String createReceiptText(TransactionDto transactionDto) {
        StringBuilder receipt = new StringBuilder();
        receipt.append("_____________________________\n");
        receipt.append("|         Bank Receipt        |\n");
        receipt.append(String.format("|Receipt:                %05d|\n", 12345));
        receipt.append(String.format("|%s              %s|\n", transactionDto.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), transactionDto.getDate().format(DateTimeFormatter.ofPattern("hh:mm"))));
        receipt.append("_____________________________\n");

        return receipt.toString();
    }

/*//    private void savePdfFormatApache(String receiptText) {
//        try {
//            PDDocument document = new PDDocument();
//            PDPage page = new PDPage();
//            document.addPage(page);
//
//            PDPageContentStream contentStream = new PDPageContentStream(document, page);
//            contentStream.setFont(new PDType1Font(TIMES_ROMAN), 12);
////            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
//            contentStream.beginText();
//            contentStream.newLineAtOffset(100, 700);
//            contentStream.showText(receiptText);
//            contentStream.endText();
//            contentStream.close();
//
//            document.save("check/check.pdf");
//            document.close();
//            System.out.println(getSuccessReceiptSavedMessage("PDF"));
//        } catch (IOException e) {
//            log.error("Exception saveReceipt()/pdf", e);
//        }
//    }*/

//    private void savePdfFormatItexst(String receiptText) {
//        try {
//            Document document = new Document();
//            PdfWriter.getInstance(document, new FileOutputStream("check/check.pdf"));
//            document.open();
//            document.add(new Paragraph(receiptText));
//            document.close();
//            System.out.println(getSuccessReceiptSavedMessage("PDF"));
//        } catch (Exception e) {
//            log.error("Exception saveReceipt()/pdf", e);
//        }
//    }

    private void saveTxtFormat(String receiptText, Long number) {
        try {
            String projectPath = System.getProperty("user.dir");
            String filePath = projectPath + File.separator + "check" + File.separator + "check_" + number + ".txt";
            FileOutputStream fos = new FileOutputStream(filePath);
//            FileOutputStream fos = new FileOutputStream("check\\check.txt");
//            FileOutputStream fos = new FileOutputStream("C:\\Users\\OTK_PC2\\IdeaProjects\\ClevertecBankConsoleApi\\"+"check\\check.txt");
            fos.write(receiptText.getBytes());
            fos.close();
            System.out.println(getSuccessReceiptSavedMessage("TXT"));
        } catch (Exception e) {
            log.error("Exception saveReceipt()/txt", e);

        }
    }

    private String getSuccessReceiptSavedMessage(String receiptFormat) {
        return String.format("The receipt has been successfully saved in %s format.", receiptFormat);
    }
}
