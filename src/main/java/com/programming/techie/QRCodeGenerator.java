package com.programming.techie;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

//import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import javax.imageio.ImageIO;

public class QRCodeGenerator {

    public static String generateQRCodeImageBase64(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        String base64Image = Base64.getEncoder().encodeToString(byteArray);
        return base64Image;
    }

    public static void decodeBase64ToImage(String base64Image, String filePath) throws IOException {
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);
        BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);

        ImageIO.write(bufferedImage, "png", new File(filePath));
    }

    public static void main(String[] args) {
        String base64QRCode = null;
        try {
            String qrCodeText = "HTruong48";
            int width = 350;
            int height = 350;

            base64QRCode = generateQRCodeImageBase64(qrCodeText, width, height);
            System.out.println("Base64 QR Code: " + base64QRCode);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        try {
            String filePath = "decodeQrCode.png";
            decodeBase64ToImage(base64QRCode, filePath);
            System.out.println("QR Code save to : " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
