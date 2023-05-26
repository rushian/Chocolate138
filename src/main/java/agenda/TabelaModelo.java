/*

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TabelaModelo extends AbstractTableModel {
    private List<String> columns;
    private List<Object[]> lines;

    public TabelaModelo(JSONObject jsonData) {
        columns = new ArrayList<>();
        lines = new ArrayList<>();

        if (!jsonData.isEmpty()) {
            for (String columnName : jsonData.keySet()) {
                columns.add(columnName);
            }

            JSONArray jsonArray = jsonData.toJSONArray(jsonData.names());

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject dataRow = jsonArray.getJSONObject(i);
                Object[] rowData = new Object[columns.size()];

                for (int j = 0; j < columns.size(); j++) {
                    rowData[j] = dataRow.get(columns.get(j));
                }

                lines.add(rowData);
            }
        }
    }



    @Override
    public Class<?> getColumnClass(final int columnIndex) {
        return Object.class; // Pode ser alterado para o tipo de dados específico da coluna
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public String getColumnName(final int column) {
        return columns.get(column);
    }

    @Override
    public int getRowCount() {
        return lines.size();
    }

    @Override
    public Object getValueAt(final int rowIndex, final int columnIndex) {
        return lines.get(rowIndex)[columnIndex];
    }

    @Override
    public boolean isCellEditable(final int rowIndex, final int columnIndex) {
        return false;
    }
}


/*package agenda;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.table.AbstractTableModel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TabelaModelo extends AbstractTableModel {

    private static class Column {
        public final Class<?> CLASS;
        public final String NAME;

        public Column(final String name, final Class<?> type) {
            NAME = name;
            CLASS = type;
        }
    }

    private static class Row {
        public final Object[] VALUES;

        public Row(final Map<String, Object> data) {
            VALUES = data.values().toArray();
        }
    }

    private List<Column> columns;
    private List<Row> lines;

    public TabelaModelo(final File jsonFile) {
        columns = new ArrayList<>();
        lines = new ArrayList<>();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, Object>> jsonData = objectMapper.readValue(jsonFile, new TypeReference<List<Map<String, Object>>>() {});

            if (!jsonData.isEmpty()) {
                Map<String, Object> firstRow = jsonData.get(0);

                for (Map.Entry<String, Object> entry : firstRow.entrySet()) {
                    String columnName = entry.getKey();
                    Object columnValue = entry.getValue();

                    if (columnValue != null) {
                        columns.add(new Column(columnName, columnValue.getClass()));
                    } else {
                        columns.add(new Column(columnName, Object.class));
                    }
                }

                for (Map<String, Object> dataRow : jsonData) {
                    lines.add(new Row(dataRow));
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo JSON: " + e.getMessage());
        }
    }

    @Override
    public Class<?> getColumnClass(final int columnIndex) {
        return columns.get(columnIndex).CLASS;
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public String getColumnName(final int column) {
        return columns.get(column).NAME;
    }

    @Override
    public int getRowCount() {
        return lines.size();
    }

    @Override
    public Object getValueAt(final int rowIndex, final int columnIndex) {
        return lines.get(rowIndex).VALUES[columnIndex];
    }

    @Override
    public boolean isCellEditable(final int rowIndex, final int columnIndex) {
        return false;
    }
}
*/





/*
package agenda;

import java.sql.*;
import java.util.*;
import javax.swing.table.*;

public class TabelaModelo extends AbstractTableModel {

    private static class Column {
        public final Class<?> CLASS;
        public final String NAME;

        public Column(final String name, final Class<?> type) {
            NAME = name;
            CLASS = type;
        }
    }

    private static class Row {
        public final Object [] VALUES ;
        public Row(final ResultSet rs) throws SQLException {
            final int columns = rs.getMetaData().getColumnCount();
            VALUES = new Object  [columns ];
            for (int i = 1; i <= columns; i++) {
                VALUES [i - 1] = rs.getObject(i);
            }
        }
    }
    private static final long serialVersionUID = 1L;
    private List<Column> columns;
    private List<Row> lines;

    public TabelaModelo(final ResultSet rs) throws SQLException,
            ClassNotFoundException {

        columns = new ArrayList<Column>();
        final ResultSetMetaData md = rs.getMetaData();
        final int count = md.getColumnCount();
        for (int i = 1; i <= count; i++) {
            columns.add(new Column(md.getColumnName(i), Class.forName(md.getColumnClassName(i))));
        }
        lines = new ArrayList<Row>();
        while (rs.next()) {
            lines.add(new Row(rs));
        }
    }
    @Override
    public Class<?> getColumnClass(final int columnIndex) {
        return columns.get(columnIndex).CLASS;
    }
    @Override
    public int getColumnCount() {
        return columns.size();
    }
    @Override
    public String getColumnName(final int column) {
        return columns.get(column).NAME;
    }
    @Override
    public int getRowCount() {
        return lines.size();
    }
    @Override
    public Object getValueAt(final int rowIndex, final int columnIndex) {
        return lines.get(rowIndex).VALUES [columnIndex ];
    }
    @Override
    public boolean isCellEditable(final int rowIndex, final int columnIndex) {
        return false;
    }
}*/