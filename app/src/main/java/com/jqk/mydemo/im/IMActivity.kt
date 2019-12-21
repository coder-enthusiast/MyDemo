package com.jqk.mydemo.im

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jqk.mydemo.R
import com.jqk.mydemo.databinding.ActivityImBinding
import com.jqk.commonlibrary.util.L
import com.jqk.commonlibrary.util.ScreenUtil
import android.util.Log
import androidx.annotation.Nullable
import androidx.core.provider.FontRequest
import androidx.emoji.bundled.BundledEmojiCompatConfig
import androidx.emoji.text.EmojiCompat
import androidx.emoji.text.FontRequestEmojiCompatConfig
import androidx.fragment.app.Fragment
import com.jqk.mydemo.im.emojiview.Emoji
import com.jqk.mydemo.im.emojiview.EmojiPageFragment
import com.jqk.mydemo.im.emojiview.EmojiViewPagerAdapter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


// 仿微信输入法，表情布局切换逻辑
// 特别感谢：https://github.com/siebeprojects/samples-keyboardheight
class IMActivity : AppCompatActivity(), KeyboardHeightObserver {
    val EMOTICONTYPE_KEYBOARD = 1
    val EMOTICONTYPE_EMOTICON = 2
    val USE_BUNDLED_EMOJI = true

    lateinit var binding: ActivityImBinding
    // 表情布局是否显示，默认不显示
    var keyboardShow: Boolean = false

    var emoticonType: Int = EMOTICONTYPE_KEYBOARD

    val datas: ArrayList<String> by lazy {
        arrayListOf<String>()
    }
    val iMAdapter: IMAdatper by lazy {
        IMAdatper(this, datas)
    }
    val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(this)
    }
    val itemHeight = 70
    var mKeyboardHeight = 400

    lateinit var keyboardHeightProvider: KeyboardHeightProvider

    val textWatcher: MyTextWatcher by lazy {
        MyTextWatcher()
    }
    var isFind = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 初始化谷歌emoji库
        initEmojiCompat()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_im)
        binding.view = this

        initEmojiView()
        MessageUtil.init()

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        keyboardHeightProvider = KeyboardHeightProvider(this)
        binding.root.post(Runnable { keyboardHeightProvider.start() })

        binding.recyclerView.adapter = iMAdapter
        binding.recyclerView.layoutManager = linearLayoutManager

        binding.message.addTextChangedListener(textWatcher)

//        binding.message.removeTextChangedListener(textWatcher)

        binding.message.setText(String(Character.toChars(128513)))
    }

    override fun onPause() {
        super.onPause()
        keyboardHeightProvider.setKeyboardHeightObserver(null)
    }

    override fun onResume() {
        super.onResume()
        keyboardHeightProvider.setKeyboardHeightObserver(this)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    inner class MyTextWatcher : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            stackFromEnd(itemHeight)
            val add = s?.substring(start, start + count)
//            Toast.makeText(this@IMActivity, "add = " + add.toString(), Toast.LENGTH_SHORT).show()
            com.jqk.commonlibrary.util.L.d("add = " + add.toString())
            com.jqk.commonlibrary.util.L.d("add.length = " + add!!.length)

            if (add == "") {
                binding.send.visibility = View.GONE
                binding.add.visibility = View.VISIBLE
            } else {
                binding.send.visibility = View.VISIBLE
                binding.add.visibility = View.GONE
            }

            val find = MessageUtil.isFind(add, s.toString(), start)

            if (find.isFind) {
                isFind = false
                binding.message.removeTextChangedListener(textWatcher)
                binding.message.setText(MessageUtil.findEmoticon(this@IMActivity, find.result))
                binding.message.setSelection(binding.message.getText().toString().trim().length)//将光标移至文字末尾
                binding.message.addTextChangedListener(textWatcher)
            } else {
                isFind = true
            }

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun haha(event: Emoji) {
        Log.d("jiqingke", "收到消息")

        addMessage(event.name, binding.message)
    }

    fun addMessage(str: String, editText: EditText) {
        binding.message.setText(MessageUtil.findEmoticon(this, binding.message.text.toString() + str))

        editText.setFocusable(true)
        editText.setFocusableInTouchMode(true)
        editText.requestFocus()
        editText.setSelection(editText.getText().toString().trim().length)//将光标移至文字末尾
    }

    private fun initEmojiCompat() {
        val config: EmojiCompat.Config
        if (USE_BUNDLED_EMOJI) {
            // Use the bundled font for EmojiCompat
            config = BundledEmojiCompatConfig(applicationContext)
        } else {
            // Use a downloadable font for EmojiCompat
            val fontRequest = FontRequest(
                    "com.google.android.gms.fonts",
                    "com.google.android.gms",
                    "Noto Color Emoji Compat",
                    R.array.com_google_android_gms_fonts_certs)
            config = FontRequestEmojiCompatConfig(applicationContext, fontRequest)
        }

        config.setReplaceAll(true)
                .registerInitCallback(object : EmojiCompat.InitCallback() {
                    override fun onInitialized() {
                        Log.i("jiqingke", "EmojiCompat initialized")
                    }

                    override fun onFailed(@Nullable throwable: Throwable?) {
                        Log.e("jiqingke", "EmojiCompat initialization failed", throwable)
                    }
                })

        EmojiCompat.init(config)
    }

    fun initEmojiView() {
        val fragments = arrayListOf<Fragment>()
        fragments.add(EmojiPageFragment())
        binding.emojiViewPager.adapter = EmojiViewPagerAdapter(supportFragmentManager, fragments)
    }

    fun showKeyboard(activity: Activity, editText: EditText) {
        editText.setFocusable(true)
        editText.setFocusableInTouchMode(true)
        editText.requestFocus()
        editText.setSelection(editText.getText().toString().trim().length)//将光标移至文字末尾

        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }


    fun hideKeyboard(activity: Activity, father: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.message.getApplicationWindowToken(), 0)
    }

    override fun onKeyboardHeightChanged(height: Int, orientation: Int) {
        if (height > 0) {
            keyboardShow = true
            // 初始化表情布局的高度
            initEmoticonView(height)
            binding.emoticonView.visibility = View.VISIBLE

            binding.emoticon.setImageResource(R.drawable.icon_emoticon)
            showMaskView(true)
            emoticonType = EMOTICONTYPE_EMOTICON
        } else {
            if (emoticonType == EMOTICONTYPE_EMOTICON) {
                binding.emoticonView.visibility = View.GONE
            }
            keyboardShow = false
        }

        stackFromEnd(itemHeight)
        scrollToBottom(binding.recyclerView)
    }

    fun initEmoticonView(keyboardHeight: Int) {
        val layoutParams = binding.emoticonView.layoutParams
        layoutParams.height = keyboardHeight
        binding.emoticonView.layoutParams = layoutParams

//        val layoutParams1 = binding.emoticonViewPager.layoutParams
//        layoutParams1.height = keyboardHeight
//        binding.emoticonViewPager.layoutParams = layoutParams1
    }

    fun scrollToBottom(recyclerview: RecyclerView) {
        recyclerview.scrollToPosition(iMAdapter.getItemCount() - 1)
    }

    fun stackFromEnd(itemHeight: Int) {
        linearLayoutManager.stackFromEnd = !(itemHeight * com.jqk.commonlibrary.util.ScreenUtil.getDensity(this) * datas.size < binding.recyclerView.height)
    }

    fun emoticon(view: View) {
        scrollToBottom(binding.recyclerView)

        if (keyboardShow) { // 输入法弹起时
            binding.emoticonView.visibility = View.VISIBLE
            hideKeyboard(this, binding.root)
            binding.emoticon.setImageResource(R.drawable.icon_keyboard)
            showMaskView(false)
            emoticonType = EMOTICONTYPE_KEYBOARD
        } else {
            if (binding.emoticonView.visibility == View.VISIBLE) {
                showKeyboard(this, binding.message)
                binding.emoticon.setImageResource(R.drawable.icon_emoticon)
                showMaskView(true)
                emoticonType = EMOTICONTYPE_EMOTICON
            } else {
                binding.emoticonView.visibility = View.VISIBLE
                binding.emoticon.setImageResource(R.drawable.icon_keyboard)
                showMaskView(false)
                emoticonType = EMOTICONTYPE_KEYBOARD
            }
        }
    }

    fun send(view: View) {
        if (binding.message.text.toString().equals("")) {
            return
        }

        binding.message.addTextChangedListener(textWatcher)

        stackFromEnd(itemHeight)

        com.jqk.commonlibrary.util.L.d("输入的内容 = " + binding.message.text.toString().trim())

        datas.add(binding.message.text.toString().trim())
        iMAdapter.notifyDataSetChanged()

        scrollToBottom(binding.recyclerView)
    }

    fun showMaskView(bool: Boolean) {
        if (bool) {
            binding.maskView.visibility = View.VISIBLE
        } else {
            binding.maskView.visibility = View.GONE
        }
    }

    override fun onBackPressed() {
        if (binding.emoticonView.visibility == View.VISIBLE) {
            binding.emoticonView.visibility = View.GONE

            binding.emoticon.setImageResource(R.drawable.icon_emoticon)
            showMaskView(true)
            emoticonType = EMOTICONTYPE_EMOTICON
        } else {
            finish()
        }
        return
    }


    override fun onDestroy() {
        super.onDestroy()
        keyboardHeightProvider.close()
    }
}