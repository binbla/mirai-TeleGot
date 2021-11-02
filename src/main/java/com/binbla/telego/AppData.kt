package com.binbla.telego

import net.mamoe.mirai.console.data.AutoSavePluginData
import net.mamoe.mirai.console.data.value

/**
 * >ClassName AppData.java
 * >Description TODO
 * >Author binbla
 * >Version 1.0.0
 * >CreateTime 2021-06-13  10:55
 */
object AppData: AutoSavePluginData("Appdata"){
    var msg_queue: MutableList<List<String>> by value()
}
