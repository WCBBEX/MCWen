package com.mcen;

import java.io.*;

public class ResourceExtractor {

    public static String extract(String resourcePath) throws IOException {

        InputStream is = ResourceExtractor.class.getClassLoader().getResourceAsStream(resourcePath);
        if (is == null) {
            throw new IOException("Resource not found: " + resourcePath);
        }


        File tempFile = File.createTempFile("resource_", ".gguf");
        tempFile.deleteOnExit();


        try (OutputStream os = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        }


        return tempFile.getAbsolutePath();
    }
}
