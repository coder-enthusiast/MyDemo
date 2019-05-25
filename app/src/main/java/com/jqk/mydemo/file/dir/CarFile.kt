package com.example.cartest

data class CarDirectory(
        var root: Boolean,
        var dirPath: String,
        var dirName: String,
        var name: String,
        var path: String,
        var directorys: List<CarDirectory>,
        var files: List<CarFile>,
        var status: String
) {

}

data class CarFile(
        var dirName: String,
        var name: String,
        var path: String) {
}

