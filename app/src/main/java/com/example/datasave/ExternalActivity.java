package com.example.datasave;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 内部存储空间:[私有]和公有。
 * 外部存储空间:[私有 /sdcard/Android/data/包名/...]和[公有 sdcard/DCIM .....]。
 */
public class ExternalActivity extends AppCompatActivity {

    private static final String TAG = "ExternalActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external);

        /**
         * 内部存储空间的[应用专属目录],卸载时删除文件。别的应用无法访问。空间比较小。
         * Dir:     /data/user/0/com.example.datasave/files
         * CacheDir:/data/user/0/com.example.datasave/cache
         */
        Log.i(TAG, "onCreate: " + getFilesDir().toString());
        Log.i(TAG, "onCreate: " + getCacheDir().toString());

        /**
         * 外部存储空间的[应用专属目录],卸载时删除文件，别的应用无法访问的到。
         * /storage/emulated/0/Android/data/com.example.datasave/files
         * /storage/emulated/0/Android/data/com.example.datasave/cache
         */
        Log.i(TAG, "onCreate: " + getExternalFilesDir(null).toString());
        Log.i(TAG, "onCreate: " + getExternalCacheDir().toString());


        /**
         * TestMethod:
         */

        //createExternalCacheFile();
        //createExterPrivateFile();
        //createExternalPublicFile();
        //testVisteOtherExternalPrivateDir();
        //allExternalPrivateDir();
        //fileTest();

    }

    /**
     * File类使用总结
     */
    private void fileTest() {

        //
        File f1 = new File("absoluteFilePath");
        File f2 = new File("absoluteFileDir","filename");
        File d1 = new File("absoluteDirPath");

        //判断是否存在
        if(!f1.exists()){
            //f1.createNewFile();
        }

        if(!d1.exists()){
            //d1.mkdir();
        }

        File[] dirs = d1.listFiles();
    }

    /**
     * 创建外部私有目录下的文件和目录。
     * /sdcard/data/包名/file
     */
    private void createExterPrivateFile() {

        //1.获取Dir或者File File可以表示二者。没后缀就是dir。
        //null代表外部私有目录/sdcard/Android/data/包名/
        File f = new File(getExternalFilesDir(null) + "/dirname filename");
        File appSpecificExternalDir = new File(getExternalFilesDir(null),"dirname");
        File appSpecificExternalFile = new File(getExternalFilesDir(null),"filename");

        //2.没有的话可以创建。
        if(!f.exists()){
            //f.createNewFile();
            //dir.mkdir();
        }

        //3.获取dir下的所有文件
        File[] dirs = f.listFiles();
    }

    /**
     * 能否访问的到别的包下的文件测试、/sdcard/Android/data/包名/......
     * 结果：无法访问。
     */

    private void testVisteOtherExternalPrivateDir() {

        File f = new File("/storage/emulated/0/Android/data/com.example.filesio/files/data.txt");

        if(f.exists()){
            Log.i(TAG, "onCreate: 访问到JavaIO的data.txt文件");
            try {
                FileInputStream fis = new FileInputStream(f);
                byte[] buffer = new byte[1024];
                fis.read(buffer);
                String s = new String(buffer);
                Log.i(TAG, "data.txt: " + s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Log.i(TAG, "onCreate: 无法访问到JavaIO的data.txt文件");
        }
    }



    /**
     * 外部存储空间可能不只一个。getExternalFileDir()默认使用的是第一个。
     */

    private void allExternalPrivateDir() {
        File[] externalStorageVolumes = ContextCompat.getExternalFilesDirs(getApplicationContext(),null);
        Log.i(TAG, "外部卷有: " + externalStorageVolumes.length + "个");
        for(int i = 0;i < externalStorageVolumes.length; i++){
            Log.i(TAG, "外部卷: " + externalStorageVolumes[i].getAbsolutePath());
        }
    }


    /**
     * 在[外部存储公有目录][DCIM....]下创建文件/文件夹。APP被卸载内容也会被保留。
     * /sdcard/DCIM MUSIC PICTURE....
     */

    private void createExternalPublicFile() {

        File file = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),"myfile.txt");
        if(!file.exists()){
            Log.i(TAG, "createExternalPublicFile: /sdcrad/myfile.txt不存在");
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Log.i(TAG, "createExternalPublicFile: /sdcrad/myfile.txt存在");
        }
    }

    /**
     * [创建]和[删除]外部存储的私有缓存[文件/文件夹]
     */

    private void createExternalCacheFile() {
        File externalCacheFile = new File(getExternalCacheDir(),"cacheFileName");
        if(externalCacheFile.exists()){
            externalCacheFile.delete();
        }
    }


    /**
     * 判断外部存储是否可写。其实是读写。
     * @return
     */
    private boolean isExternalStorageWritable(){
        //可读可写
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED;
    }

    /**
     * 判断是否可读。
     * @return
     */
    private boolean isExternalStorageReadable(){

        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED ||
               Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED_READ_ONLY;
    }
}