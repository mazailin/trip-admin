package com.ulplanet.trip.modules.sys.entity;

import com.ulplanet.trip.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * 编码Entity
 * Created by zhangxd on 15/11/23.
 */
public class Code extends DataEntity<Code> {

	private static final long serialVersionUID = 1L;

    private int modType;
    private String prefix;
    private int useDate;
    private String dateFmt;
    private int numLength;
    private String nextNum;
    private String separator;

    public Code() {
        super();
    }

    public Code(int modType) {
        this.modType = modType;
    }

    public Code(int modType, String nextNum) {
        this.modType = modType;
        this.nextNum = nextNum;
    }

    @NotNull
    public int getModType() {
        return modType;
    }

    public void setModType(int modType) {
        this.modType = modType;
    }

    @Length(min=0, max=10, message="前缀长度必须介于 1 和 10 之间")
    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @NotNull
    public int getUseDate() {
        return useDate;
    }

    public void setUseDate(int useDate) {
        this.useDate = useDate;
    }

    @NotNull
    public String getDateFmt() {
        return dateFmt;
    }

    public void setDateFmt(String dateFmt) {
        this.dateFmt = dateFmt;
    }

    @Range(min = 3, max = 8, message = "数字长度必须介于 3 和 8 之间")
    public int getNumLength() {
        return numLength;
    }

    public void setNumLength(int numLength) {
        this.numLength = numLength;
    }

    public String getNextNum() {
        return nextNum;
    }

    public void setNextNum(String nextNum) {
        this.nextNum = nextNum;
    }

    @Length(min=0, max=2, message="分隔符长度必须介于 1 和 10 之间")
    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }
}