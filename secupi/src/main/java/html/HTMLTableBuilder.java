package html;

public class HTMLTableBuilder {

    private final StringBuilder table = new StringBuilder();
    public static String HTML_START = "<html>";
    public static String HTML_END = "</html>";
    public static String TABLE_START_BORDER = "<table border=\"1\">";
    public static String TABLE_END = "</table>";
    public static String HEADER_START = "<th>";
    public static String HEADER_END = "</th>";
    public static String ROW_START = "<tr>";
    public static String ROW_END = "</tr>";
    public static String COLUMN_START = "<td>";
    public static String COLUMN_END = "</td>";


    public HTMLTableBuilder() {
        table.append(HTML_START);
        table.append(TABLE_START_BORDER);
        table.append(TABLE_END);
        table.append(HTML_END);
    }

    public void addTableHeader(String... values) {
        int lastIndex = table.lastIndexOf(TABLE_END);
        if (lastIndex > 0) {
            addRowData(lastIndex, HEADER_START, HEADER_END, values);
        }
    }

    public void addRowValues(String... values) {
        int lastIndex = table.lastIndexOf(ROW_END);
        if (lastIndex > 0) {
            int index = lastIndex + ROW_END.length();
            addRowData(index, COLUMN_START, COLUMN_END, values);
        }

    }

    private void addRowData(int index, String columnStart, String columnEnd, String[] values) {
        final StringBuilder sb = new StringBuilder();
        sb.append(ROW_START);
        for (String value : values) {
            sb.append(columnStart);
            sb.append(value);
            sb.append(columnEnd);
        }
        sb.append(ROW_END);
        table.insert(index, sb.toString());
    }

    public String build() {
        return table.toString();
    }

}