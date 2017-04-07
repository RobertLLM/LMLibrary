package robert.com.baselibrary;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangqingbang on 15/10/9.
 */
public class LSControlEdittext extends EditText {
    public LSControlEdittext(Context context) {
        super(context);
        init();
    }

    public LSControlEdittext(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LSControlEdittext(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //设置EditText文字变化的监听
        this.addTextChangedListener(new TextWatcherImpl());
    }



    //当输入结束后判断输入的数字
    private class TextWatcherImpl implements TextWatcher {
        private CharSequence beforeString = null;
        private int editStart ;
        private int editEnd ;
        @Override
        public void afterTextChanged(Editable s) {

            editStart = LSControlEdittext.this.getSelectionStart();
            editEnd = LSControlEdittext.this.getSelectionEnd();

            try {
                if (!isNumber(s.toString())) {
                    s.delete(editStart - 1, editEnd);
                    int tempSelection = editStart;
                    LSControlEdittext.this.setText(s);
                    LSControlEdittext.this.setSelection(tempSelection);
                }
            } catch (Exception e) {
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            try {
                beforeString = s;
            } catch (Exception e) {

            }

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

    }


    public static boolean isFalse(String rs) {
        String regEx = "^[0][0-9]$";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(rs);
        return rs.startsWith(".",0) || matcher.find();
    }

    /**
     * 判断字符串是否是整数
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是浮点数
     */
    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            if (value.contains("."))
                return true;
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是数字
     */
    public static boolean isNumber(String value) {
        return (!isFalse(value)) && ((isInteger(value) || isDouble(value)));
    }


}
