package uz.app.DataTable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.Vector;
import java.util.ArrayList;

public class DataTable {
    private LinkedHashMap<String, Integer> columns = null;
    private Vector<LinkedList<Object>> rows = null;

    /**
     * Creates data-table object with not set columns.
     */
    public DataTable()
    {
        columns = new LinkedHashMap<String, Integer>();
        rows = new Vector<LinkedList<Object>>();
    }

    /**
     * Creates data-table object with set of columns.
     * @param columnNames Separator-separated column names.
     * @param separator Separator for parsing column names.
     */
    public DataTable(String columnNames, String separator)
    {
        String[] colNames = columnNames.split("["+separator+"]", 0);
        columns = new LinkedHashMap<String, Integer>();
        rows = new Vector<LinkedList<Object>>();

        for(Integer i = 0; i < colNames.length; i++)
            columns.put(colNames[i], i);
    }

    /**
     * Creates data-table object with set of columns.
     * @param columnNames Comma separated column names. 
     */
    public DataTable(String columnNames)
    {
        String[] colNames = columnNames.split("[,]", 0);
        columns = new LinkedHashMap<String, Integer>();
        rows = new Vector<LinkedList<Object>>();

        for(Integer i = 0; i < colNames.length; i++)
            if(colNames[i].length() > 0)
                columns.put(colNames[i], i);
    }

    /**
     * Returns count of columns (row length).
     * @return Returns count of columns (row length).
     */
    public Integer getRowLength()
    {
        if(rows == null || columns == null) return 0;
        
        return columns.size();
    }


    /**
     * Gets rows count in Data-table.
     * @return
     */
    public Integer getRowsCount()
    {
        return rows.size();
    }


    /**
     * Returns row as array of object.
     * @param rowNum Number of row ordered by inserting.
     * @return Row as array of objects. 
     * @throws IndexOutOfBoundsException 
     */
    public Object[] getRow(Integer rowNum) throws IndexOutOfBoundsException
    {
        if(rowNum == null) return null;
        if(rows == null || columns == null) return null;
        if(rowNum < 0 || rowNum >= rows.size())
            throw new IndexOutOfBoundsException();
        
        return rows.get(rowNum).toArray();
    }

    /**
     * Gets value of cell of table.
     * @param row Row number 
     * @param col Column number
     * @return  Cell value as object
     * @throws IndexOutOfBoundsException
     */
    public Object get(Integer row, Integer col) throws IndexOutOfBoundsException
    {
        if(rows == null || columns == null) return null;
        if(row < 0 || row >= rows.size() ||
            col < 0 || col >= columns.size())
            throw new IndexOutOfBoundsException();
        
        return rows.get(row).get(col);
    }

    /**
     * Gets value of cell of table.
     * @param row Row number 
     * @param col Column name
     * @return  Cell value as object
     * @throws IndexOutOfBoundsException
     */
    public Object get(Integer row, String col) throws IndexOutOfBoundsException
    {
        if(rows == null || columns == null) return null;
        if(row < 0 || row >= rows.size() ||
            col.isEmpty() || !columns.containsKey(col))
            throw new IndexOutOfBoundsException();
        
        return rows.get(row).get(columns.get(col));
    }

    /**
     * Sets the value at position.
     * @param row Row nuber (position)
     * @param col Column nuber (position)
     * @param value The value that must be set.
     * @return This Data-table
     * @throws IndexOutOfBoundsException
     */
    public DataTable set(Integer row, Integer col, Object value) throws IndexOutOfBoundsException
    {
        if(rows == null || columns == null) return null;
        if(row < 0 || row >= rows.size() ||
            col < 0 || col >= columns.size())
            throw new IndexOutOfBoundsException();

        rows.get(row).set(col, value);
        return this;
    }

    /**
     * Sets the value at position.
     * @param row Row nuber (position)
     * @param col Column name (position)
     * @param value The value that must be set.
     * @return This Data-table
     * @throws IndexOutOfBoundsException
     */
    public DataTable set(Integer row, String col, Object value) throws IndexOutOfBoundsException
    {
        if(rows == null || columns == null) return null;
        if(row < 0 || row >= rows.size() ||
            col.isEmpty() || !columns.containsKey(col))
            throw new IndexOutOfBoundsException();

        rows.get(row).set(columns.get(col), value);
        return this;
    }

    /**
     * Adds a new column with specified name. Cells under new column will be empty object.
     * @param colName Name for new column.
     * @return This Data-table
     */
    public DataTable addColumn(String colName)
    {
        if(colName == null || columns.containsKey(colName) || colName.isEmpty())
            return this;
        
        Integer max = -1;
        Collection<Integer> values = columns.values();
        for (Integer val : values) 
            if(val > max)
                max = val;

        columns.put(colName, max + 1);
        for (LinkedList<Object> row : rows)
            row.addLast("");
        
        return this;
    }

    /**
     * Removes specified column with cells and datas under this column.
     * @param colName Name of the removing column.
     * @return This Data-table
     */
    public DataTable removeColumn(String colName)
    {
        if(!columns.containsKey(colName) || colName.isEmpty())
            return this;
        
        for(LinkedList<Object> row: rows)
            row.remove(columns.get(colName));
        
        columns.remove(colName);

        return this;
    }

    /**
     * Changes the name of column.
     * @param oldName The name of column that will be changed.
     * @param newName   New name for column.
     * @return This Data-table
     */
    public DataTable renameColumn(String oldName, String newName)
    {
        if(oldName == null || newName == null)
            return this;
        if(!columns.containsKey(oldName) || columns.containsKey(newName) || oldName.isEmpty() || newName.isEmpty())
            return this;
        
        columns.put(newName, columns.get(oldName));
        columns.remove(oldName);

        return this;
    }

    /**
     * Gets all columns names.
     * @return Array of columns names.
     */
    public Set<String> getColNames()
    {
        LinkedHashSet<String> res = new LinkedHashSet<>();
        for(String colName: columns.keySet())
            res.add(colName);

        return res;
    }

    /**
     * Adds a row to end of table.
     * @param row Array of cell values of new row.
     * @return  This Data-table
     */
    public DataTable addRow(Collection<Object> row)
    {
        if(row == null)
            return this;
        Object[] newRow = new Object[this.getRowLength()];
        Iterator<Object> it = row.iterator();
        for(Integer i = 0; i < this.getRowLength(); i++)
        {
            if(it.hasNext())
                newRow[i] = it.next();
            else
                newRow[i] = "";
        }

        rows.add(new LinkedList<Object>(Arrays.asList(newRow)));
        return this;
    }

    /**
     * Adds list of rows to the end of datatable.
     * @param listOfRows 
     * @return  This Data-table
     */
    public DataTable addAll(Collection<? extends Collection<Object>> listOfRows)
    {
        if(listOfRows == null)
            return this;
        LinkedList<LinkedList<Object>> rowsList = new LinkedList<LinkedList<Object>>();
        ArrayList<Object> row = new ArrayList<Object>(this.getRowLength());
        for(int i = 0; i < this.getRowLength(); i++)
            row.add("");

        for (Collection<Object> rw : listOfRows) 
        {
            Iterator<Object> it = rw.iterator();
            for(int i = 0; i < this.getRowLength(); i++)
            {
                if(i < rw.size())
                    row.set(i, it.next());
                else
                    row.set(i, "");
            }
            rowsList.add(new LinkedList<Object>(row));
        }

        this.rows.addAll(rowsList);
        return this;
    }

    
    /**
     * Adds empty rows to the end of table.
     * @param count Number of empty rows.
     * @return This Data-table
     */
    public DataTable addEmptyRows(int count)
    {
        if(count <= 0) return this;

        LinkedList<Object> emptyRow = new LinkedList<Object>();
        LinkedList<LinkedList<Object>> listOfEmptyRows = new LinkedList<LinkedList<Object>>(); 
        for(int i = 0; i < this.getRowLength(); i++)
            emptyRow.add("");

        for(int i = 0; i < count; i++)
        {
            listOfEmptyRows.add(new LinkedList<Object>(emptyRow));
        }

        this.rows.addAll(listOfEmptyRows);

        return this;
    }
    
    /**
     * Removes the row with given index. 
     */
    public DataTable removeRow(Integer index)
    {
        if(index < 0 || index >= getRowsCount())
            return this;

        try
        {
            rows.remove((int)index);
        } 
        catch (Exception e) { }

        return this;
    }

    /**
     * Removes the last row.
     */
    public DataTable removeRow()
    {
        int index = this.getRowsCount() - 1;
        this.removeRow(index);
        return this;
    }

    @Override
    public String toString() 
    { 
        String header = "";
        String table = "";
        String currRow = "";
        int colWidth = 15;
        int NUMERATION = 10;
        header += String.format("|%" + NUMERATION + "." + NUMERATION + "s|", "#");
        for(String colName: columns.keySet())
        {
            header += String.format("|%" + colWidth + "." + colWidth + "s|", colName);
        }
        table += header;
        table += '\n';
        char divider[] = new char[header.length()];
        for(Integer i = 0; i < header.length(); i++)
            divider[i] = '-';
        
        table += new String(divider);
        table += '\n';
        
        
        long idx = 1;
        for(LinkedList<Object> row: rows)
        {
            currRow = "";
            currRow += String.format("|%" + NUMERATION + "." + NUMERATION + "s|", idx);
            for(String colName: columns.keySet())
            {
                currRow += String.format("|%" + colWidth + "." + colWidth + "s|", row.get(columns.get(colName)));
            }
            
            table += currRow;
            table += '\n';
            idx++;
        }

        return table;
    }
}