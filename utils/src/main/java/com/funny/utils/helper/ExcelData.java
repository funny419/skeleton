package com.funny.utils.helper;

import org.apache.commons.lang3.ArrayUtils;
import java.util.Arrays;
import java.util.Objects;


public class ExcelData {
    private String dataName;
    private String sheetName;
    private Integer sheetIndex;
    private String formula;
    private String[] values;
    private Integer firstRow;
    private Integer lastRow;
    private Integer firstCol;
    private Integer lastCol;
    private Boolean isShowTips;
    private String tipsTitle;
    private String tipsMessage;
    private String errorTitle;
    private String errorMessage;


    public ExcelData() {
    }

    public ExcelData datName(String dataName) {
        this.dataName = dataName;
        return this;
    }

    public ExcelData sheetName(String sheetName) {
        this.sheetName = sheetName;
        return this;
    }

    public ExcelData sheetIndex(Integer sheetIndex) {
        this.sheetIndex = sheetIndex;
        return this;
    }

    public ExcelData formula(String formula) {
        this.formula = formula;
        return this;
    }

    public ExcelData values(String[] values) {
        this.values = values;
        return this;
    }

    public ExcelData firstRow(Integer firstRow) {
        this.firstRow = firstRow;
        return this;
    }

    public ExcelData lastRow(Integer lastRow) {
        this.lastRow = lastRow;
        return this;
    }

    public ExcelData firstCol(Integer firstCol) {
        this.firstCol = firstCol;
        return this;
    }

    public ExcelData lastCol(Integer lastCol) {
        this.lastCol = lastCol;
        return this;
    }

    public ExcelData showTips(Boolean showTips) {
        isShowTips = showTips;
        return this;
    }

    public ExcelData tipsTitle(String tipsTitle) {
        this.tipsTitle = tipsTitle;
        return this;
    }

    public ExcelData tipsMessage(String tipsMessage) {
        this.tipsMessage = tipsMessage;
        return this;
    }

    public ExcelData errorTitle(String errorTitle) {
        this.errorTitle = errorTitle;
        return this;
    }

    public ExcelData errorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }




    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public Integer getSheetIndex() {
        return sheetIndex;
    }

    public void setSheetIndex(Integer sheetIndex) {
        this.sheetIndex = sheetIndex;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }

    public Integer getFirstRow() {
        return firstRow;
    }

    public void setFirstRow(Integer firstRow) {
        this.firstRow = firstRow;
    }

    public Integer getLastRow() {
        return lastRow;
    }

    public void setLastRow(Integer lastRow) {
        this.lastRow = lastRow;
    }

    public Integer getFirstCol() {
        return firstCol;
    }

    public void setFirstCol(Integer firstCol) {
        this.firstCol = firstCol;
    }

    public Integer getLastCol() {
        return lastCol;
    }

    public void setLastCol(Integer lastCol) {
        this.lastCol = lastCol;
    }

    public Boolean getShowTips() {
        return isShowTips;
    }

    public void setShowTips(Boolean showTips) {
        isShowTips = showTips;
    }

    public String getTipsTitle() {
        return tipsTitle;
    }

    public void setTipsTitle(String tipsTitle) {
        this.tipsTitle = tipsTitle;
    }

    public String getTipsMessage() {
        return tipsMessage;
    }

    public void setTipsMessage(String tipsMessage) {
        this.tipsMessage = tipsMessage;
    }

    public String getErrorTitle() {
        return errorTitle;
    }

    public void setErrorTitle(String errorTitle) {
        this.errorTitle = errorTitle;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExcelData excelData = (ExcelData) o;
        return Objects.equals(dataName,excelData.dataName)
                && Objects.equals(sheetName,excelData.sheetName)
                && Objects.equals(sheetIndex,excelData.sheetIndex)
                && Objects.equals(formula,excelData.formula)
                && Arrays.equals(values,excelData.values)
                && Objects.equals(firstRow,excelData.firstRow)
                && Objects.equals(lastRow,excelData.lastRow)
                && Objects.equals(firstCol,excelData.firstCol)
                && Objects.equals(lastCol,excelData.lastCol)
                && Objects.equals(isShowTips,excelData.isShowTips)
                && Objects.equals(tipsTitle,excelData.tipsTitle)
                && Objects.equals(tipsMessage,excelData.tipsMessage)
                && Objects.equals(errorTitle,excelData.errorTitle)
                && Objects.equals(errorMessage,excelData.errorMessage);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(dataName,sheetName,sheetIndex,formula,firstRow,lastRow,firstCol,lastCol,isShowTips,tipsTitle,tipsMessage,errorTitle,errorMessage);
        result = 31 * result + Arrays.hashCode(values);
        return result;
    }

    @Override
    public String toString() {
        return "ExcelData{" +
                "dataName='" + dataName + '\'' +
                ",sheetName='" + sheetName + '\'' +
                ",sheetIndex=" + sheetIndex +
                ",formula='" + formula + '\'' +
                ",values=" + Arrays.toString(values) +
                ",firstRow=" + firstRow +
                ",lastRow=" + lastRow +
                ",firstCol=" + firstCol +
                ",lastCol=" + lastCol +
                ",isShowTips=" + isShowTips +
                ",tipsTitle='" + tipsTitle + '\'' +
                ",tipsMessage='" + tipsMessage + '\'' +
                ",errorTitle='" + errorTitle + '\'' +
                ",errorMessage='" + errorMessage + '\'' +
                '}';
    }

    public String generateUniqueKey() {
        return dataName + (ArrayUtils.isEmpty(values) ? 0 : values.length) + Arrays.toString(values).length();
    }
}
