package com.binbla.telego

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.MapValue
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

/**
 * >ClassName Config.java
 * >Description TODO
 * >Author binbla
 * >Version 1.0.0
 * >CreateTime 2021-06-13  11:16
 */
object Config:AutoSavePluginConfig("Config"){
    @ValueDescription("拥有者")
    var Master:Long by value(90590809L)
    @ValueDescription("bot")
    var BotID:Long by value(144942787L)
    @ValueDescription("发送时间间隔")
    var TimeSplit:Int by value(10000)
    @ValueDescription("在Telethon脚本中的目标频道，以及 频道名字 群友称呼 图片称呼")
    var Channel:MutableMap<String,List<String>> by value(mutableMapOf(
        "1167351518" to mutableListOf("转发者","帅哥","看点"),
        "1336617732" to mutableListOf("少女情怀总是诗","老色批","甜点"),
        "1445018107" to mutableListOf("咸鱼的杂货铺","老色批","甜点"),
        "1088679595" to mutableListOf("白丝即正义","腿控教徒","雪糕"),
        "1109579085" to mutableListOf("For world|精选|收集器","老色批","甜品"),
        "1214996122" to mutableListOf("奇闻异录 与 沙雕时刻","铁子","下饭菜")
    ))
    @ValueDescription("unprocessed_list.txt")
    var FilePath: String by value("/tmp/unprocessed_list.txt")
    @ValueDescription("群和需要转发到群的频道")
    var GroupList:MutableMap<Long,List<String>> by value(mutableMapOf(
        221789271L to mutableListOf("1167351518","1336617732","1445018107","1088679595","1109579085","1214996122")))
}
