package com.example.cartest

import android.util.Log
import com.jqk.filelibrary.dir.CarDirectory
import com.jqk.filelibrary.dir.CarFile
import com.jqk.filelibrary.dir.Code
import io.reactivex.ObservableEmitter
import java.io.File
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


object FileUtils {

    lateinit var onFindFileListener: OnFindFileListener

    interface OnFindFileListener {
        fun onFindFile(name: String)
    }

    fun getFileByDir(path: String): CarDirectory? {
        val rootDir = File(path)
        // 判断路径是否存在
        if (!rootDir.exists()) {
            return null
        }

        var dirName = ""
        var dirPath = ""
        var isRoot = false

        if (path.equals("/")) {
            dirPath = ""
            dirName = ""
            isRoot = true
        } else {
            dirName = rootDir.parentFile.name
            dirPath = rootDir.parentFile.absolutePath
            isRoot = false
        }

        val carDir = CarDirectory(
                isRoot,
                dirPath,
                dirName,
                rootDir.name,
                rootDir.absolutePath,
                arrayListOf<CarDirectory>(),
                arrayListOf<CarFile>(),
                Code.FILE_EMPTY)

        scanDir(carDir)

        return carDir
    }

    fun scanDir(carDir: CarDirectory) {
        val rootDir = File(carDir.path)

        val fileList = arrayListOf<CarFile>()
        val dirList = arrayListOf<CarDirectory>()
        rootDir.listFiles()?.let {
            for (subDirFile in it) {
                if (subDirFile.isDirectory) {
                    val carDirectory = CarDirectory(
                            false,
                            carDir.path,
                            carDir.name,
                            subDirFile.name,
                            subDirFile.absolutePath,
                            arrayListOf<CarDirectory>(),
                            arrayListOf<CarFile>(),
                            Code.FILE_EMPTY
                    )
                    // 方便测试用的条件
//                    if (subDirFile.name.equals("媒体") || subDirFile.name.equals("sdcard") ) {
//                        dirList.add(carDirectory)
//                    }

                    dirList.add(carDirectory)
                } else if (subDirFile.isFile) {
                    val dirName = subDirFile.parentFile.name
                    val fileName = subDirFile.name
                    val filePath = subDirFile.absolutePath
                    val carFile = CarFile(dirName, fileName, filePath)

                    fileList.add(carFile)
                }
            }

            carDir.directorys = dirList
            carDir.files = fileList
        }
    }

    fun getFileByFile(path: String): ArrayList<String>? {
        val rootDir = File(path)
        // 判断路径是否存在
        if (!rootDir.exists()) {
            return null
        }

        val arrayList = arrayListOf<String>()

        scanFile(path, arrayList)

        return arrayList
    }

    fun scanFile(path: String, arrayList: ArrayList<String>) {
        val rootDir = File(path)

        rootDir.listFiles()?.let {
            for (subDirFile in it) {
                if (subDirFile.isDirectory) {
                    scanFile(subDirFile.path, arrayList)
                } else {
                    arrayList.add(subDirFile.name)
                }
            }
        }
    }

    class DirRunnable : Runnable {
        var path: String
        var emitter: ObservableEmitter<String>
        var fixedThreadPool: ExecutorService

        constructor(path: String, emitter: ObservableEmitter<String>, fixedThreadPool: ExecutorService) {
            this.path = path
            this.emitter = emitter
            this.fixedThreadPool = fixedThreadPool
        }

        override fun run() {
            val rootDir = File(path)

            rootDir.listFiles()?.let {
                for (subDirFile in it) {
                    if (subDirFile.isDirectory && !subDirFile.name.equals("sys")) {
                        fixedThreadPool.execute(DirRunnable(subDirFile.path, emitter, fixedThreadPool))
                    } else {
//                        if (subDirFile.name.endsWith("mp3")) {
                        Log.d("", "文件：" + subDirFile.name)
                            onFindFileListener?.onFindFile(subDirFile.name)
                            emitter.onNext(subDirFile.name)
//                        }
                    }
                }
            }
        }
    }

    fun scanFiles(path: String, emitter: ObservableEmitter<String>) {
        val rootDir = File(path)
        // 判断路径是否存在
        if (!rootDir.exists()) {
            return
        }

        val arrayList = arrayListOf<String>()

        val fixedThreadPool = Executors.newFixedThreadPool(5)
        fixedThreadPool.execute(DirRunnable(path, emitter, fixedThreadPool))
    }

    fun traverseFolder1(path: String) {
        var fileNum = 0
        var folderNum = 0
        val file = File(path)
        if (file.exists()) {
            val list = LinkedList<File>()
            var files = file.listFiles()
            for (file2 in files!!) {
                if (file2.isDirectory) {
                    println("文件夹:" + file2.absolutePath)
                    list.add(file2)
                    folderNum++
                } else {
                    println("文件:" + file2.absolutePath)
                    fileNum++
                }
            }
            var temp_file: File
            while (!list.isEmpty()) {
                temp_file = list.removeFirst()
                files = temp_file.listFiles()
                for (file2 in files!!) {
                    if (file2.isDirectory) {
                        println("文件夹:" + file2.absolutePath)
                        list.add(file2)
                        folderNum++
                    } else {
                        println("文件:" + file2.absolutePath)
                        fileNum++
                    }
                }
            }
        } else {
            println("文件不存在!")
        }
        println("文件夹共有:$folderNum,文件共有:$fileNum")

    }
}