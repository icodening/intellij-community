package com.intellij.ide.starter.community.model

import java.time.LocalDate


data class ReleaseInfo(val date: LocalDate,
                       val type: String,
                       val version: String,
                       val majorVersion: String,
                       val build: String,
                       val downloads: Download)

data class Download(val linux:OperatingSystem?, val mac:OperatingSystem?, val windows:OperatingSystem?)
data class OperatingSystem(val link:String)

