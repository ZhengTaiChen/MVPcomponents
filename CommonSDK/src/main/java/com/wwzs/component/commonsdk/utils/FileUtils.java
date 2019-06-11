package com.wwzs.component.commonsdk.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

/**
 * Created by lipan on 2014/6/7.
 */
public class FileUtils {

    public static final String ROOT_DIR = "lipan";
    public static final String DOWNLOAD_DIR = "download";
    public static final String CACHE_DIR = "cache";
    public static final String ICON_DIR = "icon";


    /**
     * B方法追加文件：使用FileWriter
     */
    public static void appendStringToFile(String fileName, String content) {
        try {
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断SD卡是否挂载
     */
    public static boolean isSDCardAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    //判断文件是否存在
    public static boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 根据文件路径判断是否是一个图片
     *
     * @param path
     * @return
     */
    public static boolean pathIsImage(String path) {
        if (!fileIsExists(path))
            return false;
        return path.endsWith(".jpg") || path.endsWith(".gif") || path.endsWith(".bmp") || path.endsWith(".png");
    }


    /**
     * 根据文件路径判断是否是一个gif图片
     *
     * @param path
     * @return
     */
    public static boolean isGifImg(String path) {
        return path.toUpperCase().endsWith(".GIF");
    }

    /**
     * 把数据写入文件
     *
     * @param is       数据流
     * @param path     文件路径
     * @param recreate 如果文件存在，是否需要删除重建
     * @return 是否写入成功
     */
    public static boolean writeFile(InputStream is, String path, boolean recreate) {
        boolean res = false;
        File f = new File(path);
        FileOutputStream fos = null;
        try {
            if (recreate && f.exists()) {
                f.delete();
            }
            if (!f.exists() && null != is) {
                File parentFile = new File(f.getParent());
                parentFile.mkdirs();
                int count = -1;
                byte[] buffer = new byte[1024];
                fos = new FileOutputStream(f);
                while ((count = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, count);
                }
                res = true;
            }
        } catch (Exception e) {
//            ALog.e(e.toString());
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return res;
    }


    /**
     * 把字符串数据写入文件
     *
     * @param content  需要写入的字符串
     * @param filename 文件路径名称
     * @return 是否写入成功
     */
    public static boolean writeFile(Context context, byte[] content, String filename) {
        String packageResourcePath = context.getApplicationContext().getPackageResourcePath() + "/trading/" + filename;
        boolean res = false;
        File f = new File(packageResourcePath);
        RandomAccessFile raf = null;
        try {
            if (f.exists()) {
                f.delete();
                f.createNewFile();
            } else {
                f.createNewFile();
            }
            if (f.canWrite()) {
                raf = new RandomAccessFile(f, "rw");
                raf.seek(raf.length());
                raf.write(content);
                res = true;
            }
        } catch (Exception e) {
//            ALog.e(e.toString());
        } finally {
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return res;
    }

    /**
     * 获取文件中保存的String字符串
     *
     * @param context
     * @param filename
     * @return
     */
    public static String getFileText(Context context, String id, String filename) {
        String result = "";
        File file = new File(context.getFilesDir(), id + filename);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = "";
            StringBuffer buffer = new StringBuffer();
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }
            result = buffer.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 判断文件是否存在
     *
     * @param context
     * @param filename
     * @return
     */
    public static boolean fileIsExists(Context context, String id, String filename) {
        File file = new File(context.getFilesDir(), id + filename);
        return file.exists();
    }

    public static boolean fileDestory(Context context, String id, String filename) {
        boolean succ = false;
        File file = new File(context.getFilesDir(), id + filename);
        try {
            if (file.exists()) {
                file.delete();
            }
            succ = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return succ;
    }

    /**
     * 向内存文件中写入String字符串
     *
     * @param context
     * @param content
     * @param filename
     * @return
     */
    public static boolean writeFileText(Context context, String content, String id, String filename) {
        boolean succ = false;
        File path = context.getFilesDir();
        File file = new File(path, id + filename);
        try {
            if (!file.exists()) {
                file.createNewFile();
            } else {
                file.delete();
                file.createNewFile();
            }
            FileWriter fileWritter = new FileWriter(file, true);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write(content);
            bufferWritter.close();
            succ = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        //true = append file
        return succ;
    }

    /**
     * @param context     上下文
     * @param filename    文件名
     * @param filecontent 储存的string
     * @return
     */
    public static boolean writefile(Context context, String filename, String filecontent) {
        boolean storeState = false;
        FileOutputStream out = null;
        try {
            out = context.openFileOutput(filename, Context.MODE_PRIVATE);
            out.write(filecontent.getBytes(StandardCharsets.UTF_8));
            storeState = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return storeState;
    }

    public static String readFile(Context context, String filename) {
        String outString = null;
        FileInputStream in = null;
        ByteArrayOutputStream bout = null;
        byte[] buf = new byte[1024];
        bout = new ByteArrayOutputStream();
        int length = 0;
        try {
            in = context.openFileInput(filename); //获得输入流
            while ((length = in.read(buf)) != -1) {
                bout.write(buf, 0, length);
            }
            byte[] content = bout.toByteArray();
            outString = new String(content, StandardCharsets.UTF_8); //设置文本框为读取的内容
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bout != null) {
                try {
                    bout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return outString;

    }

    /**
     * 读取SD卡中文本文件
     *
     * @param fileName
     * @return
     */
    public static String readSDFile(String fileName) {
        String SDPATH = Environment.getExternalStorageDirectory().getPath();
        File file = new File(SDPATH + "//" + fileName);
        if (!file.exists()) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        try {
            FileInputStream fis = new FileInputStream(file);
            int c;
            while ((c = fis.read()) != -1) {
                sb.append((char) c);
            }
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 写入内容到SD卡中的txt文本中
     * str为内容
     */
    public static boolean writeSDFile(String str, String fileName) {
        boolean succ = false;
        String SDPATH = Environment.getExternalStorageDirectory().getPath();
        try {
            FileWriter fw = new FileWriter(SDPATH + "/" + fileName);
            File f = createSDFile(fileName);
            fw.write(str);
            FileOutputStream os = new FileOutputStream(f);
            DataOutputStream out = new DataOutputStream(os);
            out.writeShort(2);
            out.writeUTF("shishisissisisi");
            System.out.println(out);
            fw.flush();
            fw.close();
            succ = true;
            System.out.println(fw);
        } catch (Exception ignored) {
        }
        return succ;
    }

    /**
     * 在SD卡上创建文件
     *
     * @throws IOException
     */
    public static File createSDFile(String fileName) throws IOException {
        String SDPATH = Environment.getExternalStorageDirectory().getPath();
        File file = new File(SDPATH + "//" + fileName);
        if (!file.exists()) {
            file.createNewFile();
        } else {
            deleteSDFile(fileName);
            file.createNewFile();
        }
        return file;
    }

    /**
     * 删除SD卡上的文件
     *
     * @param fileName
     */
    public static boolean deleteSDFile(String fileName) {
        String SDPATH = Environment.getExternalStorageDirectory().getPath();
        File file = new File(SDPATH + "//" + fileName);
        if (file == null || !file.exists() || file.isDirectory())
            return false;
        return file.delete();
    }


    /**
     * 将uri转化为路径
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getPath(Context context, Uri uri) {

        String scheme = uri.getScheme();
//        ALog.i("scheme: " + scheme);

        if ("content".equalsIgnoreCase(scheme)) {
            String[] projection = {"_data"};
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
                e.printStackTrace();
            }
        } else if ("file".equalsIgnoreCase(scheme)) {
            return uri.getPath();
        }
        return "";
    }

    /**
     * 从文件路径中提取名称
     *
     * @param path
     * @return
     */
    public static String getNameFromPath(String path) {
        String temp[] = path.replaceAll("\\\\", "/").split("/");
        String fileName = "";
        if (temp.length > 1) {
            fileName = temp[temp.length - 1];
        }
        return fileName;
    }
}
