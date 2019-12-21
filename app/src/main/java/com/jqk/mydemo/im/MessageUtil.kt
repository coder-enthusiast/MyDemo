package com.jqk.mydemo.im

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import com.jqk.mydemo.R
import com.jqk.mydemo.im.emojiview.Emoji
import com.jqk.commonlibrary.util.L
import java.util.regex.Pattern

object MessageUtil {
    val map = mutableMapOf<String, Int>()
    val mEmojiList = mutableListOf<Emoji>()
    val uMap = mutableMapOf<String, String>()

    fun init() {
        map.put("[xiaolian]", R.drawable.icon_emoji_xiaolian)

        mEmojiList.add(Emoji("[xiaolian]", R.drawable.icon_emoji_xiaolian))
        mEmojiList.add(Emoji("[xiaolian]", R.drawable.icon_emoji_xiaolian))
        mEmojiList.add(Emoji("[xiaolian]", R.drawable.icon_emoji_xiaolian))
        mEmojiList.add(Emoji("[xiaolian]", R.drawable.icon_emoji_xiaolian))
        mEmojiList.add(Emoji("[xiaolian]", R.drawable.icon_emoji_xiaolian))
        mEmojiList.add(Emoji("[xiaolian]", R.drawable.icon_emoji_xiaolian))
        mEmojiList.add(Emoji("[xiaolian]", R.drawable.icon_emoji_xiaolian))
        mEmojiList.add(Emoji("[xiaolian]", R.drawable.icon_emoji_xiaolian))
        mEmojiList.add(Emoji("[xiaolian]", R.drawable.icon_emoji_xiaolian))
        mEmojiList.add(Emoji("[xiaolian]", R.drawable.icon_emoji_xiaolian))

        uMap.put("\uD83D\uDE01", "[xiaolian]")
    }

    fun findEmoticon(context: Context, str: String): SpannableString {
        var message = str
        var allEmojiLength = 0

        //匹配[表情]
        val pattern = "\\[[^\\[](.+?)\\]"
        //用正则式匹配文本获取匹配器
        val matcher = Pattern.compile(pattern).matcher(message)

        val spanString = SpannableString(message)
        while (matcher.find()) { //匹配器进行匹配
            //打印元文本信息
            com.jqk.commonlibrary.util.L.d("matcher.group()" + matcher.group())
            val id = MessageUtil.emojiList().get(matcher.group()) ?: 0
            com.jqk.commonlibrary.util.L.d("id = " + id)
            if (id == 0) {
                continue
            }
//            message.indexOf(matcher.group())
            val start = message.indexOf(matcher.group()) + allEmojiLength
            val end = start + matcher.group().length

            allEmojiLength += matcher.group().length

            com.jqk.commonlibrary.util.L.d("start = " + start)
            com.jqk.commonlibrary.util.L.d("end = " + end)
            com.jqk.commonlibrary.util.L.d("size = " + allEmojiLength)

            com.jqk.commonlibrary.util.L.d("message = " + message)
            message = message.replaceFirst(matcher.group(), "")
            com.jqk.commonlibrary.util.L.d("message2 = " + message)

            val drawable = context.getResources().getDrawable(id)
            drawable.setBounds(0, 0, 70, 70)
            val imageSpan = MyImageSpan(drawable)

            spanString.setSpan(imageSpan, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        }

        return spanString
    }

    fun isFind(add: String, message: String, start: Int): Find {
        var hasEmoji = false
        var result = add
        var str = message
        // 匹配emoji表情
        val pattern = "[^\\u0000-\\uFFFF]"
        //用正则式匹配文本获取匹配器
        val matcher = Pattern.compile(pattern).matcher(add)
        while (matcher.find()) { //匹配器进行匹配
            hasEmoji = true
            com.jqk.commonlibrary.util.L.d("匹配到的数据 = " + matcher.group())
            result = uMap.get(matcher.group()) ?: ""
            com.jqk.commonlibrary.util.L.d("result = " + result)
//            L.d("result = " + result)
            if (!result.equals("")) {
                hasEmoji = true
                str = str.replace(matcher.group(), result)
                com.jqk.commonlibrary.util.L.d("str = " + str)
            } else {
                com.jqk.commonlibrary.util.L.d("为空")
            }
        }

        val stringBuffer = StringBuffer(str)

        stringBuffer.insert(start, result)

        val find = Find(hasEmoji, str)

        return find
    }

    fun emojiList(): Map<String, Int> {
        return map
    }
}