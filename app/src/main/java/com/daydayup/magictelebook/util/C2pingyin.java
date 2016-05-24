package com.daydayup.magictelebook.util;

import android.graphics.Color;
import android.widget.ImageView;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Jay on 16/5/23.
 */
public class C2pingyin {

    public static String converterToFirstSpell(String chines){
        String pinyinName = "";
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        try {
            String[] str = PinyinHelper.toHanyuPinyinStringArray(nameChar[0],defaultFormat);
            if (str==null){
                return String.valueOf(chines.charAt(0));
            }else{
                return String.valueOf(str[0].toCharArray()[0]);
            }
        } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
            badHanyuPinyinOutputFormatCombination.printStackTrace();
        }

        return pinyinName;
    }


}
