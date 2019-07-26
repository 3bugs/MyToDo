package th.ac.dusit.dbizcom.mytodo.etc;

import androidx.room.TypeConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimestampConverter {

    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    @TypeConverter
    public static Date fromTimestamp(String value) {
        if (value != null) {
            try {
                return df.parse(value);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    @TypeConverter
    public static String dateToTimestamp(Date value) {
        return value == null ? null : df.format(value);
    }

    // จัดรูปแบบวันที่ สำหรับแสดงผลบนหน้าจอ
    public static String formatForUi(Date value) {
        SimpleDateFormat monthFormatter = new SimpleDateFormat("MM", Locale.US);
        String month = monthFormatter.format(value);

        SimpleDateFormat yearFormatter = new SimpleDateFormat("yyyy", Locale.US);
        //String yearInBe = String.valueOf(Integer.valueOf(yearFormatter.format(value)));
        String yearInBe = String.valueOf(Integer.valueOf(yearFormatter.format(value)) + 543);

        SimpleDateFormat dayFormatter = new SimpleDateFormat("dd", Locale.US);
        String day = dayFormatter.format(value);

        return String.format(
                Locale.getDefault(),
                "%s/%s/%s",
                day, month, yearInBe
        );
    }
}