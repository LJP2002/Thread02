package com.thread.pro3_bestMatching;

import java.io.*;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * 读取词汇表
 */
public class WordsLoader {
    /**
     * 读取path指定的词汇表
     * @param path 路径
     * @return
     */
    public static List<String> load(String path) {
        List<String> data = new ArrayList<>();
        Path file = Paths.get(path);
        try (InputStream iis = Files.newInputStream(file);//按字节读
             Reader r = new InputStreamReader(iis);//将字节流转为字符，按字符
             BufferedReader br = new BufferedReader(r);//可以按行读取
        ) {
            String line = null;
            while ((line = br.readLine()) != null) {
                data.add(line);//一行一个单词。
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
