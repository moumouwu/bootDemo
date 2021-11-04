package org.azhell.nettyLearn.util;

import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class TestFiles {

    private final static String FILE_STORE_PATH = "UploadFile";
    @Test
    public void test01(){
        //File file = new File("F:\\test.pdf");
        String prefix = "D:\\" + FILE_STORE_PATH;
        String filename = UUID.randomUUID().toString().replace("-", "");
        String suffix = ".pdf";
        filename = filename + suffix;
        Path filePath = Paths.get(prefix, filename);
        try {
            /*File file = new File(prefix+File.separator+filename);
            if (!file.exists()){
                file.createNewFile();
            }*/
            InputStream inputStream = new FileInputStream("F:\\test.pdf");
            Files.copy(inputStream, filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
