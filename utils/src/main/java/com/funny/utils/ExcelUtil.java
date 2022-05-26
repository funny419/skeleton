package com.funny.utils;

import com.alibaba.fastjson.util.TypeUtils;
import com.funny.utils.constants.ExcelEntity;
import com.funny.utils.helper.ExcelData;
import com.funny.utils.helper.ExcelExport;
import com.funny.utils.helper.ExcelImport;
import com.funny.utils.helper.SheetColumn;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.checkerframework.checker.nullness.qual.NonNull;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import static com.funny.utils.constants.ExcelEntity.*;


public class ExcelUtil {
    public static <T> List<T> singleParseToList(InputStream inputStream,@NonNull Class<T> source) throws IOException {
        Workbook workbook = WorkbookFactory.create(inputStream);
        return singleParseToList(workbook,source);
    }


    public static <T> List<T> singleParseToList(@NonNull Workbook workbook,@NonNull Class<T> source) {
        validateObject(workbook,source);

        ExcelImport excelImport = source.getAnnotation(ExcelImport.class);
        if (Objects.isNull(excelImport)) {
            throw new UnsupportedOperationException("NOT SUPPORT EXCEL IMPORT");
        }

        Sheet[] sheets = new Sheet[1];
        String[] sheetNames = excelImport.sheetNames();
        if (ArrayUtils.isNotEmpty(sheetNames)) {
            String sheetName = sheetNames[0];
            sheets[0] = Optional.ofNullable(workbook.getSheet(sheetName))
                            .orElseThrow(() -> new RuntimeException("SHEET CAN NOT BE MATCHED"));
        }

        int numberOfSheetCount = workbook.getNumberOfSheets();
        int[] sheetIndexs = excelImport.sheetIndexes();
        if (ArrayUtils.isEmpty(sheetNames) && ArrayUtils.isNotEmpty(sheetIndexs)) {
            int sheetIndex = sheetIndexs[0];
            if (sheetIndex > numberOfSheetCount - 1) {
                throw new RuntimeException("SHEET IS OUT OF RANGE");
            }

            sheets[0] = workbook.getSheetAt(sheetIndex);
        }

        if (ArrayUtils.isEmpty(sheetNames) && ArrayUtils.isEmpty(sheetIndexs)) {
            sheets[0] = workbook.getSheetAt(0);
        }

        int[] startRowIndexes = excelImport.startRowIndexes();
        startRowIndexes = ArrayUtils.isEmpty(startRowIndexes) ? new int[1] : startRowIndexes;
        return parseSheetToList(sheets,startRowIndexes,source).get(0);
    }


    public static <T> List<List<T>> parseToList(InputStream inputStream,Class<T> source) throws IOException {
        Workbook workbook = WorkbookFactory.create(inputStream);
        return parseToList(workbook,source);
    }


    public static <T> List<List<T>> parseToList(@NonNull Workbook workbook,@NonNull Class<T> source) {
        validateObject(workbook,source);

        ExcelImport excelImport = source.getAnnotation(ExcelImport.class);
        if (Objects.isNull(excelImport)) {
            throw new IllegalArgumentException("CLASS NOT SUPPORT EXCEL");
        }

        Sheet[] sheets = null;
        String[] sheetNames = excelImport.sheetNames();
        if (ArrayUtils.isNotEmpty(sheetNames)) {
            int sheetNamesCount = sheetNames.length;
            sheets = new Sheet[sheetNamesCount];
            for (int i=0;i<sheetNamesCount;i++) {
                String sheetName = sheetNames[i];
                sheets[i] = Optional.ofNullable(workbook.getSheet(sheetName))
                            .orElseThrow(() -> new RuntimeException("SHEET NAME CAN NOT BE MATCHED"));
            }
        }

        int numberOfSheets = workbook.getNumberOfSheets();
        int[] sheetIndexs = excelImport.sheetIndexes();
        if (ArrayUtils.isEmpty(sheets) && ArrayUtils.isNotEmpty(sheetIndexs)) {
            int sheetIndexsCount = sheetIndexs.length;
            sheets = new Sheet[sheetIndexsCount];
            for (int i=0;i<sheetIndexsCount;i++) {
                int sheetIndex = sheetIndexs[i];
                if (sheetIndex > numberOfSheets - 1) {
                    throw new RuntimeException("SHEET INDEX IS OUT OF RANGE");
                }

                sheets[i] = workbook.getSheetAt(sheetIndex);
            }
        }

        if (ArrayUtils.isEmpty(sheets)) {
            sheets = new Sheet[1];
            sheets[0] = workbook.getSheetAt(0);
        }

        int[] startRowIndexs = ConverterUtil.copyNewArray(excelImport.startRowIndexes(),sheets.length);
        return parseSheetToList(sheets,startRowIndexs,source);
    }


    private static <T> List<List<T>> parseSheetToList(Sheet[] sheets,int[] startRowIndexs,Class<T> source) {
        return IntStream.range(0,sheets.length).mapToObj(sheetIndex -> {
            Sheet sheet = sheets[sheetIndex];
            List<Field> fieldList = Arrays.stream(source.getDeclaredFields())
                                        .filter(field -> field.isAnnotationPresent(SheetColumn.class) && field.getAnnotation(SheetColumn.class).imported())
                                        .collect(Collectors.toList());

            int maxIndex = fieldList.stream().mapToInt(item -> item.getAnnotation(SheetColumn.class).index()).max().getAsInt();

            Row row;
            Cell cell;
            List<T> result = Lists.newArrayList();
            for (int i=startRowIndexs[sheetIndex];i<sheet.getPhysicalNumberOfRows();i++) {
                if (Objects.isNull(row = sheet.getRow(i))) {
                    continue;
                }

                try {
                    T instance = source.getDeclaredConstructor().newInstance();

                    int currentIndex = maxIndex;
                    for (Field field : fieldList) {
                        SheetColumn sheetColumn = field.getAnnotation(SheetColumn.class);
                        int index = sheetColumn.index();
                        int compare = row.getLastCellNum() - 1;
                        if (index > compare || currentIndex > compare) {
                            continue;
                        }

                        field.setAccessible(true);
                        cell = index == -1 ? row.getCell(++currentIndex) : row.getCell(index);
                        Object value = TypeUtils.cast(getCellValue(cell),field.getType(),null);

                        field.set(instance,value);
                    }
                } catch (Exception e) {
                    throw new RuntimeException("NEW INSTANCE FAIL");
                }
            }

            return result;
        }).collect(Collectors.toList());
    }


    private static Object getCellValue(Cell cell) {
        if (Objects.isNull(cell)) {
            return null;
        }

        Object value;
        switch (cell.getCellType()) {
            case NUMERIC:
                value = DateUtil.isCellDateFormatted(cell) ? cell.getDateCellValue() : cell.getNumericCellValue();
            break;

            case STRING:
                value = cell.getStringCellValue();
            break;

            case BOOLEAN:
                value = cell.getBooleanCellValue();
            break;

            case FORMULA:
                value = cell.getCellFormula();
            break;

            case BLANK:

            case ERROR:

            case _NONE:

            default:
                value = null;
            break;
        }

        return value;
    }


    private static <T> void validateObject(Workbook workbook,Class<T> source) {
        if (Objects.isNull(workbook)) {
            throw new IllegalArgumentException("WORKBOOK NULL");
        }

        if (Objects.isNull(source)) {
            throw new IllegalArgumentException("SOURCE CLASS NULL");
        }
    }


    public static <T> void singleListToStream(List<T> dataList,@NonNull OutputStream outputStream,@NonNull Class<T> source) {
        singleListToStream(dataList,outputStream,source,null);
    }


    public static <T> void singleListToStream(List<T> dataList,@NonNull OutputStream outputStream,@NonNull Class<T> source,List<ExcelData> excelDataList) {
        listToStream(Collections.singletonList(dataList),outputStream,source,excelDataList);
    }

    public static <T> Workbook singleListToWorkbook(List<T> dataList,@NonNull Class<T> source) {
        return singleListToWorkbook(dataList,source,null);
    }


    public static <T> Workbook singleListToWorkbook(List<T> dataList,@NonNull Class<T> source,List<ExcelData> excelDataList) {
        return listToWorkbook(Collections.singletonList(dataList),source,excelDataList);
    }


    public static <T> void listToStream(List<List<T>> dataList,@NonNull OutputStream outputStream,@NonNull Class<T> source) {
        listToStream(dataList,outputStream,source,null);
    }


    public static <T> void listToStream(List<List<T>> dataList,@NonNull OutputStream outputStream,@NonNull Class<T> source,List<ExcelData> excelDataList) {
        Optional.of(listToWorkbook(dataList,source,excelDataList)).ifPresent(workbook -> {
            try {
                workbook.write(outputStream);
            } catch (IOException e) {
                throw new RuntimeException("EXCEL WRITE UNKNOWN ERROR");
            }
        });
    }


    public static <T> Workbook listToWorkbook(List<List<T>> dataList,@NonNull Class<T> source) {
        return listToWorkbook(dataList,source,null);
    }


    public static <T> Workbook listToWorkbook(List<List<T>> dataList,@NonNull Class<T> source,List<ExcelData> excelDataList) {
        ExcelExport excelExport = source.getAnnotation(ExcelExport.class);
        if (Objects.isNull(excelExport)) {
            throw new IllegalArgumentException("CLASS TYPE NOT SUPPORT EXCEL EXPORT");
        }

        int excelDataListSize = CollectionUtils.isEmpty(excelDataList) ? 0 : excelDataList.size();
        String[] sheetNames = ConverterUtil.copyNewArray(excelExport.sheetNames(),excelDataListSize);

        boolean[] isHiddenSheets = ConverterUtil.copyNewArray(excelExport.isIncludeHeaders(),excelDataListSize);
        boolean[] inCludeHeaders = ConverterUtil.copyNewArray(excelExport.isIncludeHeaders(),excelDataListSize);

        int[] startRowIndexes = ConverterUtil.copyNewArray(excelExport.startRowIndexes(),excelDataListSize);
        short[] startColumnIndexes = ConverterUtil.copyNewArray(excelExport.startColumnIndexes(),excelDataListSize);

        Workbook workbook = ExcelEntity.EXCEL_XLS.equals(excelExport.fileType()) ? new HSSFWorkbook() : new XSSFWorkbook();

        Row row;
        Cell cell;
        Sheet sheet;

        int i=0,sheetIndex=1;
        List<T> resultList;

        Iterator<List<T>> iterator = dataList.iterator();
        while(iterator.hasNext()) {
            int rowNum = startRowIndexes[i];
            short startColumn = startColumnIndexes[i];

            List<Field> fieldList = Arrays.stream(source.getDeclaredFields())
                                        .filter(field -> field.isAnnotationPresent(SheetColumn.class) && field.getAnnotation(SheetColumn.class).exported())
                                        .collect(Collectors.toList());

            if (CollectionUtils.isEmpty(fieldList)) {
                continue;
            }

            int maxIndex = fieldList.stream().mapToInt(item -> item.getAnnotation(SheetColumn.class).index()).max().getAsInt();

            boolean isHiddenSheet = isHiddenSheets[i];
            if (isHiddenSheet) {
                workbook.setSheetHidden(i,true);
            }

            int currentIndex = maxIndex;
            String sheetName = sheetNames[i];
            sheet = workbook.createSheet(StringUtils.isEmpty(sheetName) ? ExcelEntity.SHEET_PREFIX + sheetIndex++ : sheetName);

            row = null;
            boolean inCludeHeader = inCludeHeaders[i];
            if (inCludeHeader) {
                row = sheet.createRow(rowNum++);
            }

            for (Field field : fieldList) {
                SheetColumn sheetColumn = field.getAnnotation(SheetColumn.class);

                int index = sheetColumn.index();
                index = (index == -1 ? ++currentIndex : index) + startColumn;
                if (Objects.nonNull(row)) {
                    String name = sheetColumn.name();
                    boolean required = sheetColumn.required();
                    cell = row.createCell(index);
                    fillHeaderCell(workbook,cell,name,required);
                }

                int width = sheetColumn.width();
                if (width == -1) {
                    sheet.autoSizeColumn(index);
                } else {
                    sheet.setColumnWidth(index,width*2*256);
                }
            }

            resultList = iterator.next();
            for (T result : resultList) {
                currentIndex = maxIndex;
                for (Field field : fieldList) {
                    SheetColumn sheetColumn = field.getAnnotation(SheetColumn.class);

                    int index = sheetColumn.index();
                    index = (index == -1 ? ++currentIndex:index) + startColumn;

                    cell = row.createCell(index);
                    field.setAccessible(true);

                    Object value = null;
                    try {
                        value = field.get(result);
                    } catch (IllegalAccessException e) {
                        throw new IllegalArgumentException("EXCEL WRITE UNKNOWN ERROR");
                    }

                    fillDataCell(workbook,cell,value,sheetColumn.format());
                }
            }

            i++;
        } // while end

        createDropDownList(workbook,excelDataList);

        return workbook;
    }


    private static void fillHeaderCell(Workbook workbook,Cell cell,Object value,boolean required) {
        CellStyle cellStyle = workbook.createCellStyle();

        if (required) {
            cellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.index);
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }

        if (Objects.isNull(value)) {
            cell.setCellStyle(cellStyle);
            return;
        }

        Font font = workbook.createFont();
        font.setBold(true);

        cellStyle.setFont(font);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    }


    private static void fillDataCell(Workbook workbook,Cell cell,Object value,String format) {
        CellStyle cellStyle = workbook.createCellStyle();

        if (Objects.isNull(value)) {
            cell.setCellStyle(cellStyle);
            return;
        }

        fillCellValue(workbook,cell,cellStyle,value,format);
    }


    private static void fillCellValue(Workbook workbook,Cell cell,CellStyle cellStyle,Object value,String format) {
        Class<?> clazz = value.getClass();

        if (String.class == clazz) {
            cell.setCellValue(ConverterUtil.toString(value));
        }
        else if (Integer.class == clazz) {
            cell.setCellValue(ConverterUtil.toInteger(value));
        }
        else if (Long.class == clazz) {
            cell.setCellValue(ConverterUtil.toLong(value));
        }
        else if (Float.class == clazz) {
            cell.setCellValue(ConverterUtil.toFloat(value));
        }
        else if (Double.class == clazz) {
            cell.setCellValue(ConverterUtil.toDouble(value));
        }
        else if (BigDecimal.class == clazz) {
            cell.setCellValue(ConverterUtil.toDouble(value));
        }
        else if (Boolean.class == clazz) {
            cell.setCellValue(ConverterUtil.toBooleanValue(value));
        }
        else if (Date.class == clazz) {
            cell.setCellValue(ConverterUtil.toDate(value));
            DataFormat dataFormat = workbook.createDataFormat();
            cellStyle.setDataFormat(dataFormat.getFormat(format));
        }
        else if (GregorianCalendar.class == clazz) {
            cell.setCellValue(ConverterUtil.toCalendar(value));
        }
        else if (XSSFRichTextString.class == clazz) {
            cell.setCellValue((XSSFRichTextString) value);
        }

        cell.setCellStyle(cellStyle);
    }


    private static void createDropDownList(Workbook workbook,List<ExcelData> excelDataList) {
        if (CollectionUtils.isEmpty(excelDataList)) {
            return;
        }

        int validSize = excelDataList.stream()
                                    .filter(excelData -> ArrayUtils.isNotEmpty(excelData.getValues()))
                                    .map(ExcelData::generateUniqueKey)
                                    .collect(Collectors.toSet()).size();

        if (validSize > 3) {
            dropDownListWithHiddenSheet(workbook,excelDataList);
            return;
        }

        int maxLength = excelDataList.stream().mapToInt(item -> calStringArrayTotalLength(item.getValues())).max().orElse(0);
        if (maxLength > 256 - 1) {
            dropDownListWithHiddenSheet(workbook,excelDataList);
            return;
        }

        dropDownListLessByte(workbook,excelDataList);
    }


    private static void dropDownListLessByte(Workbook workbook,List<ExcelData> excelDataList) {
        excelDataList.stream()
                    .filter(excelData -> ArrayUtils.isNotEmpty(excelData.getValues()) || StringUtils.isNotEmpty(excelData.getFormula()))
                    .forEach(excelData -> createDataValidation(workbook,excelData));
    }


    private static int calStringArrayTotalLength(String[] values) {
        if (ArrayUtils.isEmpty(values)) {
            return 0;
        }

        StringBuilder sb = new StringBuilder();
        for (String value : values) {
            sb.append(value).append(" ");
        }

        return sb.length() - 1;
    }


    private static void dropDownListWithHiddenSheet(Workbook workbook,List<ExcelData> excelDataList) {
        Map<String,String> formulaMap = Maps.newHashMapWithExpectedSize(excelDataList.size());

        Sheet sheet = workbook.createSheet(HIDDEN_SHEET_NAME);
        workbook.setSheetHidden(workbook.getSheetIndex(sheet),true);

        Row row;
        Cell cell;
        int rowIndex;
        int colIndex = 0;
        for (ExcelData excelData : excelDataList) {
            String formula = excelData.getFormula();
            if (StringUtils.isNotEmpty(formula)) {
                createDataValidation(workbook,excelData);
                continue;
            }

            String[] values = excelData.getValues();
            if (ArrayUtils.isEmpty(values)) {
                continue;
            }

            formula = formulaMap.get(excelData.generateUniqueKey());
            if (StringUtils.isNotEmpty(formula)) {
                createDataValidation(workbook,excelData.formula(formula));
                continue;
            }

            rowIndex = 0;
            row = getRow(sheet,rowIndex++);
            cell = getCell(row,colIndex);

            String dataName = Optional.ofNullable(excelData.getDataName()).orElse("");
            fillHeaderCell(workbook,cell,dataName,false);

            for (String value : values) {
                row = getRow(sheet,rowIndex++);
                cell = getCell(row,colIndex);
                cell.setCellValue(value);
            }

            String columnName = ConverterUtil.toString(ConverterUtil.toCharacter(A + colIndex++));
            formula = String.format(FORMULA_FORMAT,HIDDEN_SHEET_NAME,columnName,columnName,values.length+1);
            formulaMap.put(excelData.generateUniqueKey(),formula);

            createDataValidation(workbook,excelData.formula(formula));
        }
    }


    private static Row getRow(Sheet sheet,int rowIndex) {
        return Optional.ofNullable(sheet.getRow(rowIndex)).orElseGet(() -> sheet.createRow(rowIndex));
    }


    private static Cell getCell(Row row,int colIndex) {
        return Optional.ofNullable(row.getCell(colIndex)).orElseGet(() -> row.createCell(colIndex));
    }


    private static void createDataValidation(Workbook workbook,ExcelData excelData) {
        Sheet sheet = null;
        String sheetName = excelData.getSheetName();

        if (StringUtils.isNotEmpty(sheetName)) {
            sheet = Optional.ofNullable(workbook.getSheet(sheetName)).orElseThrow(() -> new RuntimeException("SHEETNAME CAT NOT BE MATCHED A AVAILABLE SHEET"));
        }

        if (Objects.isNull(sheet)) {
            int sheetIndex = excelData.getSheetIndex();
            if (sheetIndex > workbook.getNumberOfSheets() - 1) {
                throw new IllegalArgumentException("SHEET INDEX IS OUT OF RANGE");
            }

            sheet = workbook.getSheetAt(sheetIndex);
        }

        int firstRow = excelData.getFirstRow();
        int lastRow = Optional.ofNullable(excelData.getLastRow()).orElse(65536);

        int firstCol = excelData.getFirstCol();
        int lastCol = Optional.ofNullable(excelData.getLastCol()).orElse(firstCol);

        Boolean isShowTips = Optional.ofNullable(excelData.getShowTips()).orElse(false);
        String tipsTitle = Optional.ofNullable(excelData.getTipsTitle()).orElse(DEFAULT_TIPS_TITLE);
        String tipsMessage = Optional.ofNullable(excelData.getTipsMessage()).orElse(DEFAULT_TIPS_MESSAGE);

        String errorTitle = Optional.ofNullable(excelData.getErrorTitle()).orElse(DEFAULT_ERROR_TITLE);
        String errorMessage = Optional.ofNullable(excelData.getErrorMessage()).orElse(DEFAULT_ERROR_MESSAGE);

        String formula = excelData.getFormula();
        DataValidationHelper helper = sheet.getDataValidationHelper();
        CellRangeAddressList addressList = new CellRangeAddressList(firstRow,lastRow,firstCol,lastCol);

        DataValidationConstraint constraint = StringUtils.isEmpty(formula) ? helper.createExplicitListConstraint(excelData.getValues()) : helper.createFormulaListConstraint(formula);
        DataValidation validation = helper.createValidation(constraint,addressList);

        if (validation instanceof XSSFDataValidation) {
            validation.setSuppressDropDownArrow(true);
            validation.setShowErrorBox(true);
            validation.createErrorBox(errorTitle,errorMessage);
        } else {
            validation.setSuppressDropDownArrow(false);
        }

        validation.setEmptyCellAllowed(true);
        validation.setShowPromptBox(isShowTips);
        validation.createPromptBox(tipsTitle,tipsMessage);
        sheet.addValidationData(validation);
    }




    private ExcelUtil() {
        throw new IllegalStateException("THIS IS A UTILITY CLASS");
    }
}
