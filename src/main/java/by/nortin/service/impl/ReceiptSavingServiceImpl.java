package by.nortin.service.impl;

import by.nortin.dto.TransactionDto;
import by.nortin.service.ReceiptMessageService;
import by.nortin.service.ReceiptSavingService;
import by.nortin.util.InjectObjectsFactory;
import java.io.File;
import java.io.FileOutputStream;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ReceiptSavingServiceImpl implements ReceiptSavingService {

    private final ReceiptMessageService receiptMessageService;

    {
        receiptMessageService = (ReceiptMessageService) InjectObjectsFactory.getInstance(ReceiptMessageService.class);
    }

    /**
     * Implementation of the method generates and saves the receipt.
     *
     * @param transactionDto TransactionDto, all info
     */
    @Override
    public void saveReceiptTxt(TransactionDto transactionDto) {
        String receiptText = receiptMessageService.formReceipt(transactionDto);
        saveTxtFormat(receiptText, transactionDto.getId());
    }

    /**
     * The method to save the receipt in TXT format.
     *
     * @param receiptText String
     * @param number      Long transactionId
     */
    private void saveTxtFormat(String receiptText, Long number) {
        try {
            String projectPath = System.getProperty("user.dir");
            String filePath = projectPath + File.separator + "check" + File.separator + "check_" + number + ".txt";
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.write(receiptText.getBytes());
            fos.close();
        } catch (Exception e) {
            log.error("Exception saveReceipt()/txt", e);
        }
    }
}
