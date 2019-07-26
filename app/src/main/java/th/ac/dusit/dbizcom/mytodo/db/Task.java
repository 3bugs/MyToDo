package th.ac.dusit.dbizcom.mytodo.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;

import th.ac.dusit.dbizcom.mytodo.etc.TimestampConverter;

@Entity(tableName = "task")
public class Task implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "desc")
    private String desc;
    @ColumnInfo(name = "finished")
    private boolean finished;
    @ColumnInfo(name = "dueDate")
    @TypeConverters({TimestampConverter.class})
    private Date dueDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
