package com.funny.utils;

import com.funny.utils.extend.FileType;
import com.funny.utils.helper.RegHelper;
import com.funny.utils.helper.SysHelper;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.FileNameMap;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class FileUtil {
    private static final Integer BUFFER_SIZE = 1024 * 1024 * 10;
    private static final int KILOBYTE_UNIT = 1024;



    public static boolean exist(String path) {
        return StringUtils.isNotEmpty(path) && new File(path).exists();
    }


    public static boolean exist(File file) {
        return file != null && file.exists();
    }


    public static boolean exist(String directory,String regexp) {
        if (StringUtils.isEmpty(directory)) {
            return false;
        }

        File file = new File(directory);
        if (!exist(file)) {
            return false;
        }

        String[] fileList = file.list();
        if (ArrayUtils.isEmpty(fileList)) {
            return false;
        }

        for (String fileName : fileList) {
            if (StringUtils.isNotEmpty(fileName) && fileName.matches(regexp)) {
                return true;
            }
        }

        return false;
    }


    public static File toFile(URL url) {
        if (url == null) {
            return null;
        }

        if (!"file".equals(url.getProtocol())) {
            return null;
        }

        String path = url.getPath();
        return StringUtils.isNotEmpty(path) ? new File(EscapeUtil.unescapeURL(path)) : null;

    }


    public static boolean isDirectory(String path) {
        return path != null && new File(path).isDirectory();
    }


    public static boolean isDirectory(File file) {
        return file != null && file.isDirectory();
    }


    public static boolean isFile(String path) {
        return path != null && new File(path).isDirectory();
    }


    public static boolean isFile(File file) {
        return file != null && file.isDirectory();
    }


    public static int countLines(File file) throws IOException {
        long fileLength = file.length();
        LineNumberReader reader = new LineNumberReader(new FileReader(file));
        reader.skip(fileLength);
        return reader.getLineNumber();
    }


    public static List<String> lines(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line;
        List<String> result = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            result.add(line);
        }

        return result;
    }


    public static List<String> lines(File file,String encoding) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),encoding));

        String line;
        List<String> result = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            result.add(line);
        }

        return result;
    }


    public static List<String> lines(File file,int lines) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line;
        List<String> result = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            result.add(line);
            if (result.size() == lines) {
                break;
            }
        }

        return result;
    }


    public static List<String> lines(File file,int lines,String encoding) throws IOException {
        String line;
        List<String> result = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),encoding));
        while ((line = reader.readLine()) != null) {
            result.add(line);
            if (result.size() == lines) {
                break;
            }
        }

        return result;
    }


    public static boolean appendLine(File file,String str) throws IOException {
        RandomAccessFile random = new RandomAccessFile(file,"w");
        long fileLength = random.length();
        random.seek(fileLength);
        random.writeBytes(SysHelper.FILE_SEPARATOR + str);
        return true;
    }


    public static boolean appendLine(File file,String str,String encoding) throws IOException {
        String lineSeparator = System.getProperty("line.separator","\n");

        RandomAccessFile random = new RandomAccessFile(file,"w");
        long fileLength = random.length();
        random.seek(fileLength);
        random.write((lineSeparator + str).getBytes(encoding));
        return true;
    }


    public static boolean write(File file,String str) throws IOException {
        RandomAccessFile random = new RandomAccessFile(file,"rw");
        random.writeBytes(str);
        return true;
    }


    public static boolean write(File file,String str,String encoding) throws IOException {
        RandomAccessFile random = new RandomAccessFile(file,"rw");
        long fileLength = random.length();
        random.seek(fileLength);
        random.write(str.getBytes(encoding));
        return true;
    }


    public static boolean writeAppend(File file,String str) throws IOException {
        RandomAccessFile random = new RandomAccessFile(file,"rw");
        long fileLength = random.length();
        random.seek(fileLength);
        random.writeBytes(str);
        return true;
    }


    public static boolean writeAppend(File file,String str,String encoding) throws IOException {
        RandomAccessFile random = new RandomAccessFile(file,"rw");
        long fileLength = random.length();
        random.seek(fileLength);
        random.write(str.getBytes(encoding));
        return true;
    }


    public static boolean cleanFile(File file) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write("");
        return true;
    }


    public static String mimeType(String file) {
        FileNameMap map = URLConnection.getFileNameMap();
        return map.getContentTypeFor(file);
    }


    public static String fileType(File file) throws IOException {
        return FileType.getFileType(file);
    }


    public static String fileType(String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            return "";
        }

        String[] str = fileName.split("\\.");
        if (str.length > 1) {
            return str[str.length-1].trim().toLowerCase();
        }

        return "";
    }


    public static Date modifyTime(File file) {
        return new Date(file.lastModified());
    }


    public static boolean copy(String resource,String target) throws IOException {
        return copy(new File(resource),target);
    }


    public static boolean copy(File file,String targetFile) throws IOException {
        FileInputStream fin = new FileInputStream(file);
        FileOutputStream fout = new FileOutputStream(new File(targetFile));

        FileChannel in = fin.getChannel();
        FileChannel out = fout.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        while (in.read(buffer) != -1) {
            buffer.flip();
            out.write(buffer);
            buffer.clear();
        }

        return true;
    }


    public static boolean createPaths(String paths) {
        File dir = new File(paths);
        return !dir.exists() && dir.mkdir();
    }


    public static boolean createFiles(String filePath) throws IOException {
        File file = new File(filePath);
        if (file.isDirectory()) {
            if (!file.exists()) {
                return file.mkdirs();
            }
        } else {
            File dir = file.getParentFile();
            if (!dir.exists()) {
                if (dir.mkdirs()) {
                    return file.createNewFile();
                }
            }
        }

        return false;
    }


    public static boolean deleteFile(File file) {
        return file.delete();
    }


    public static boolean deleteDir(File file) {
        List<File> files = listFileAll(file);

        if (CheckUtil.valid(files)) {
            for (File f : files) {
                if (f.isDirectory()) {
                    deleteDir(f);
                } else {
                    deleteFile(f);
                }
            }
        }

        return file.delete();
    }


    public static boolean deleteBigFile(File file) throws IOException {
        return cleanFile(file) && file.delete();
    }


    public static void copyDir(String filePath,String targetPath) throws IOException {
        File file = new File(filePath);
        copyDir(file,targetPath);
    }


    public static void copyDir(File filePath,String targetPath) throws IOException {
        File targetFile = new File(targetPath);
        if (!targetFile.exists()) {
            createPaths(targetPath);
        }

        File[] files = filePath.listFiles();
        if (CheckUtil.valid(files)) {
            for (File file : files) {
                String path = file.getName();
                if (file.isDirectory()) {
                    copyDir(file,targetPath + "/" + path);
                } else {
                    copy(file,targetPath + "/" + path);
                }
            }
        }
    }


    public static List<File> listFile(String path) {
        File file = new File(path);
        return listFile(file);
    }


    public static List<File> listFile(String path,boolean child) {
        return listFile(new File(path),child);
    }


    public static List<File> listFile(File path) {
        List<File> list = new ArrayList<>();

        File[] files = path.listFiles();
        if (CheckUtil.valid(files)) {
            for (File file : files) {
                if (file.isDirectory()) {
                    list.addAll(listFile(file));
                } else {
                    list.add(file);
                }
            }
        }

        return list;
    }


    public static List<File> listFile(File path,boolean child) {
        List<File> list = new ArrayList<>();

        File[] files = path.listFiles();
        if (CheckUtil.valid(files)) {
            for (File file : files) {
                if (child && file.isDirectory()) {
                    list.addAll(listFile(file));
                } else {
                    list.add(file);
                }
            }
        }

        return list;
    }


    public static List<File> listFileAll(File path) {
        List<File> list = new ArrayList<>();

        File[] files = path.listFiles();
        if (CheckUtil.valid(files)) {
            for (File file : files) {
                list.add(file);

                if (file.isDirectory()) {
                    list.addAll(listFileAll(file));
                }
            }
        }

        return list;
    }


    public static List<File> listFileFilter(File path,FilenameFilter filter) {
        List<File> list = new ArrayList<>();

        File[] files = path.listFiles();
        if (CheckUtil.valid(files)) {
            for (File file : files) {
                if (file.isDirectory()) {
                    list.addAll(listFileFilter(file,filter));
                } else {
                    if (filter.accept(file.getParentFile(),file.getName())) {
                        list.add(file);
                    }
                }
            }
        }

        return list;
    }


    public static List<File> listFileFilter(File dirPath,String postfixs) {
        List<File> list = new ArrayList<File>();

        File[] files = dirPath.listFiles();
        if (CheckUtil.valid(files)) {
            for (File file : files) {
                if (file.isDirectory()) {
                    list.addAll(listFileFilter(file,postfixs));
                } else {
                    String fileName = file.getName().toLowerCase();
                    if (fileName.endsWith(postfixs.toLowerCase())) {
                        list.add(file);
                    }
                }
            }
        }

        return list;
    }


    public static List<File> searchFile(File dirPath,String fileName) {
        List<File> list = new ArrayList<>();

        File[] files = dirPath.listFiles();
        if (CheckUtil.valid(files)) {
            for (File file : files) {
                if (file.isDirectory()) {
                    list.addAll(searchFile(file,fileName));
                } else {
                    String Name = file.getName();
                    if (Name.equals(fileName)) {
                        list.add(file);
                    }
                }
            }
        }

        return list;
    }


    public static List<File> searchFileReg(File dirPath,String reg) {
        List<File> list = new ArrayList<>();

        File[] files = dirPath.listFiles();
        if (CheckUtil.valid(files)) {
            for (File file : files) {
                if (file.isDirectory()) {
                    list.addAll(searchFile(file,reg));
                } else {
                    String Name = file.getName();
                    if (RegHelper.isMatche(Name,reg)) {
                        list.add(file);
                    }
                }
            }
        }

        return list;
    }


    public static String suffix(File file) {
        String fileName = file.getName();
        return fileName.substring(fileName.indexOf(".") + 1);
    }


    public static long fileSize(File file) throws IOException {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            size = fis.available();
        }

        return size;
    }


    public static String fileSuffix(String file) {
        if (StringUtils.isEmpty(file)) {
            return null;
        }

        int extIndex = file.lastIndexOf(".");
        if (extIndex == -1) {
            return null;
        }

        int folderIndex = file.lastIndexOf("/");
        if (folderIndex > extIndex) {
            return null;
        }

        return file.substring(extIndex+1);
    }


    public static int GB(double giga) {
        return (int) (giga * KILOBYTE_UNIT * KILOBYTE_UNIT * KILOBYTE_UNIT);
    }


    public static int MB(double mega) {
        return (int) (mega * KILOBYTE_UNIT * KILOBYTE_UNIT);
    }


    public static int KB(double kilo) {
        return (int) (kilo * KILOBYTE_UNIT);
    }


    public static String inKB(long bytes) {
        return String.format("%(,.1fKB",(double)(bytes/KILOBYTE_UNIT));
    }


    public static String inMB(long bytes) {
        return String.format("%(,.1fMB",(double)(bytes/KILOBYTE_UNIT/KILOBYTE_UNIT));
    }


    public static String inGB(long bytes) {
        return String.format("%(,.1fGB",(double)(bytes/KILOBYTE_UNIT/KILOBYTE_UNIT/KILOBYTE_UNIT));
    }
}
