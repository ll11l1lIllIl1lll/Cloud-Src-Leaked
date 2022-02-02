/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package cn.CX.Cloud.file;

import cn.CX.Cloud.Cloud;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;

public class FileManager {
    protected Minecraft mc = Minecraft.getMinecraft();
    private String fileName;
    private File path;

    public FileManager(String fileName, String NAME) {
        this.fileName = fileName = fileName + ".txt";
        this.path = new File(Cloud.instance.directory.toString());
        if (!this.path.exists()) {
            try {
                this.path.mkdir();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void remove(int line) {
        ArrayList<String> file = this.read();
        if (file.size() < line) {
            return;
        }
        this.clear();
        int loop = 1;
        for (String text : file) {
            if (loop != line) {
                this.write(text);
            }
            ++loop;
        }
    }

    public void clear() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(this.path, this.fileName)));
            bw.write("");
            bw.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void write(String text) {
        this.write(new String[]{text});
    }

    public void write(String[] text) {
        if (text == null || text.length == 0 || text[0].trim() == "") {
            return;
        }
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(this.path, this.fileName), true));
            for (String line : text) {
                bw.write(line);
                bw.write("\r\n");
            }
            bw.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final ArrayList<String> read() {
        ArrayList<String> list = new ArrayList<String>();
        try {
            String text;
            BufferedReader br = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(new File(this.path, this.fileName).getAbsolutePath()))));
            while ((text = br.readLine()) != null) {
                list.add(text.trim());
            }
            br.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}

