package br.com.gobuzz.backend.gobuzzbackend.util;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public class ImageUtils {

    public static byte[] convertMultipartFileToByteArray(MultipartFile file) throws IOException {
        return file.getBytes();
    }
}
