package br.com.gobuzz.backend.gobuzzbackend.service;

import org.springframework.stereotype.Service;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class QRCodeService {

    public byte[] generateQRCode(String content, int width, int height) throws WriterException, IOException {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);

        Writer writer = new QRCodeWriter();
        BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height, hints);

        return writeToByteArray(bitMatrix);
    }

    private byte[] writeToByteArray(BitMatrix matrix) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        for (int y = 0; y < matrix.getHeight(); y++) {
            for (int x = 0; x < matrix.getWidth(); x++) {
                int color = matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF;
                outputStream.write((color >> 16) & 0xFF);
                outputStream.write((color >> 8) & 0xFF);
                outputStream.write(color & 0xFF);
            }
        }
        return outputStream.toByteArray();
    }
}
