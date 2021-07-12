package uz.app.DataTable;

import java.io.PrintStream;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.LinkedList;
import java.util.Random;

public class TestDataTable {
    static PrintStream log = System.out;

    static Integer abs(Integer a)
    {
        return a > 0 ? a : -a;
    }

    /**
     * Generates list of random integers
     * @param count Count of random integers to generate
     * @param leftBound The integer that random numbers will be greater or equal 
     * @param rightBound The integer that random numbers will be less
     * @param repeated Indicates whether indexes may dublicate 
     * @return
     */
    public static LinkedList<Integer> getRandomIndexList(int count, int leftBound, int rightBound, boolean repeated)
    {
        LinkedList<Integer> randomIndexes = new LinkedList<>();

        Random randomGen = new Random();

        if(repeated)
            for(int i = 0; i < count; i++)
                randomIndexes.add(leftBound + ( abs(randomGen.nextInt()) % (rightBound - leftBound)));
        else
        {
            HashSet<Integer> setOfIndexes = new HashSet<>();
            for(int i = leftBound; i < rightBound; i++)
                setOfIndexes.add(i);
            
            int t = 0;
            while(t < count)
            {
                Integer randomNum = leftBound + (abs(randomGen.nextInt()) % (rightBound - leftBound));
                if(setOfIndexes.contains(randomNum)) 
                {
                    randomIndexes.add(randomNum);
                    setOfIndexes.remove(randomNum);
                    t++;
                }
                    
            }
        }
        return randomIndexes;
    }

    public static Integer getRandomIndex(int leftBound, int rightBound)
    {
        Random randomGen = new Random();
        return leftBound + randomGen.nextInt() % (rightBound - leftBound);
    }

    /**
     * Assume that getRowLength() function works correctly
     * @param args
     */
    public static void test_AddColumn()
    {
        log.println("========== AddColumn() ==========");
        DataTable dt = new DataTable();
        assert dt != null: "datatable is null";

        int COL_NUM = 100;
        long t_start_addColumn = System.currentTimeMillis();
        for(int i = 0; i < COL_NUM; i++)
        {
            dt.addColumn("col_" + i);
        }
        long t_end_addColumn = System.currentTimeMillis();
        log.println(COL_NUM + " columns added in " + ( t_end_addColumn - t_start_addColumn) + "ms");
        assert dt.getRowLength() == COL_NUM : "ERROR: Some columns have not added";

        dt = new DataTable();
        COL_NUM = 1000;
        t_start_addColumn = System.currentTimeMillis();
        for(int i = 0; i < COL_NUM; i++)
        {
            dt.addColumn("col_" + i);
        }
        t_end_addColumn = System.currentTimeMillis();
        log.println(COL_NUM + " columns added in " + ( t_end_addColumn - t_start_addColumn) + "ms");
        assert dt.getRowLength() == COL_NUM : "ERROR: Some columns have not added";

        dt = new DataTable();
        COL_NUM = 10000;
        t_start_addColumn = System.currentTimeMillis();
        for(int i = 0; i < COL_NUM; i++)
        {
            dt.addColumn("col_" + i);
        }
        t_end_addColumn = System.currentTimeMillis();
        log.println(COL_NUM + " columns added in " + ( t_end_addColumn - t_start_addColumn) + "ms");
        assert dt.getRowLength() == COL_NUM : "ERROR: Some columns have not added";

        dt.addColumn(null);

        log.println("AddColumn() test passed!");        
    }

    /**
     * Assume that DataTable.AddColumn() and getRowLength() functions work correctly
     * @param args
     */
    public static void test_removeColumn()
    {
        log.println("========== removeColumn() ==========");
        DataTable dt = new DataTable();
        assert dt != null: "datatable is null";

        int COL_NUM = 10000;
        long t_start;
        long t_end;
        String[] colNames = new String[COL_NUM];

        for(int i = 0; i < COL_NUM; i++)
        {
            colNames[i] = "col_" + i;
            dt.addColumn(colNames[i]);
        }
        
        t_start = System.currentTimeMillis();
        for(int i = 0; i < COL_NUM; i++)
            dt.removeColumn(colNames[i]);
        t_end = System.currentTimeMillis();

        log.println(COL_NUM + " columns removed in " + ( t_end - t_start) + "ms");
        assert dt.getRowLength() == 0 : "ERROR: Some columns have not removed";

        dt.removeColumn(null);
        log.println("removeColumn() test passed!");   
    }

    /**
     * Assume that DataTable.AddColumn() and getColNames() functions work correctly
     * @param args
     */
    public static void test_renameColumn()
    {
        log.println("========== renameColumn() ==========");
        DataTable dt = new DataTable();
        assert dt != null: "datatable is null";
        int RENAME_COUNT = 10;
        int COL_NUM = 100;
        LinkedList<Integer> randomIndexes = getRandomIndexList(RENAME_COUNT, 0, COL_NUM, false);
        String[] newNames = new String[RENAME_COUNT];

        Random randomGen = new Random();
        for(int i = 0; i < RENAME_COUNT; i++)
            randomIndexes.add(randomGen.nextInt());

        for(int i = 0; i < COL_NUM; i++) 
            dt.addColumn("col_" + i);

        int k = 0;
        for(int i = 0; i < COL_NUM; i++)
        {
            if(randomIndexes.contains(i))
            {
                newNames[k] = "renamed_col_" + i;
                dt.renameColumn("col_" + i, newNames[k]);
                assert (dt.getColNames().contains(newNames[k]));
                k++;
            }
        }
        log.println(RENAME_COUNT + " columns renamed successfully!");   

        dt.renameColumn(newNames[RENAME_COUNT - 1], null);
        dt.renameColumn(null, "A");
        dt.renameColumn("A", null);
        dt.renameColumn(null, null);

        log.println("renameColumn() test passed!");   
    }


    /**
     * Assume that getRowsCount() function works correctly
     * @param args
     */
    public static void test_AddRow()
    {
        log.println("========== AddRow() ==========");
        DataTable dt = new DataTable();
        assert dt != null: "datatable is null";
        assert dt.getRowsCount() == 0 : "Count of rows is not zero.";
        
        int ROW_NUM = 100; // **************************
        int COL_NUM = 100;
        LinkedList<Object> row = new LinkedList<>();
        for(int i = 0; i < COL_NUM; i++)
            row.add("value_" + i);

        long t_start = System.currentTimeMillis();
        for(int i = 0; i < ROW_NUM; i++)
            dt.addRow(row);
        long t_end = System.currentTimeMillis();
        log.println(ROW_NUM+" rows with length="+COL_NUM+" added in " + ( t_end - t_start) + "ms");
        assert dt.getRowsCount() == ROW_NUM : "ERROR: Some rows have not added";

        ROW_NUM = 1000; // **************************
        dt = new DataTable();
        t_start = System.currentTimeMillis();
        for(int i = 0; i < ROW_NUM; i++)
            dt.addRow(row);
        t_end = System.currentTimeMillis();
        log.println(ROW_NUM+" rows with length="+COL_NUM+" added in " + ( t_end - t_start) + "ms");
        assert dt.getRowsCount() == ROW_NUM : "ERROR: Some rows have not added";

        ROW_NUM = 100000; // **************************
        dt = new DataTable();
        t_start = System.currentTimeMillis();
        for(int i = 0; i < ROW_NUM; i++)
            dt.addRow(row);
        t_end = System.currentTimeMillis();
        log.println(ROW_NUM+" rows with length="+COL_NUM+" added in " + ( t_end - t_start) + "ms");
        assert dt.getRowsCount() == ROW_NUM : "ERROR: Some rows have not added";

        // ============= Add longer row ==============================

        dt = new DataTable();
        row = new LinkedList<>();
        for(int i = 0; i < COL_NUM + 1; i++)
            row.add("value_" + i);

        dt.addRow(row);
        assert dt.getRowsCount() == 1 : "ERROR: Row with longer length than datatable's initial rowlength have not added";
        
        // ============ Add shorter row ===============================

        dt = new DataTable();
        row = new LinkedList<>();
        for(int i = 0; i < COL_NUM - 1; i++)
            row.add("value_" + i);

        dt.addRow(row);
        assert dt.getRowsCount() == 1 : "ERROR: Row with shorter length than datatable's initial rowlength have not added";
        
        // ============ Add null instead of row ===============================

        dt = new DataTable();
        row = null;
        dt.addRow(row);
        assert dt.getRowsCount() == 0 : "ERROR: Null added as row";

        log.println("addRow() test passed!");
    }

    /**
     * Assume that getRowsCount(), AddRow() and AddColumn() works correctly
     */
    public static void test_RemoveRow()
    {
        log.println("========== RemoveRow() ==========");
        DataTable dt = new DataTable();
        assert dt != null: "datatable is null";
        assert dt.getRowsCount() == 0 : "Count of rows is not zero.";
        
        int ROW_NUM = 100;
        int COL_NUM = 10;
        LinkedList<Object> row = new LinkedList<>();
        for(int i = 0; i < COL_NUM; i++)
        {
            dt.addColumn("col_" + i);
            row.add("value_" + i);
        }

        for(int i = 0; i < ROW_NUM; i++)
            dt.addRow(row);

        // =============== Removing rows in random order ==========================
        LinkedList<Integer> randomIntegers = getRandomIndexList(ROW_NUM, 0, ROW_NUM, false);
        randomIntegers.sort(new Comparator<Integer>(){
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            };
        });
        long t_start = System.currentTimeMillis();
        for (Integer index : randomIntegers)
            dt.removeRow(index);
        long t_end = System.currentTimeMillis();
        assert dt.getRowsCount() == 0 : "ERROR: Some rows have not removed";
        log.println(ROW_NUM + " rows removed in " + ( t_end - t_start) + "ms");

        // ============== Removing row from empty datatable
        dt.removeRow(getRandomIndex(0, 10));
        assert dt.getRowsCount() == 0 : "ERROR: Rows count must be 0";

        // ============== Testing removeRow() with no arguments ====================
        for(int i = 0; i < 3; i++)
            dt.addRow(row);

        dt.removeRow();
        assert dt.getRowsCount() == 2 : "ERROR: Rows count must be 2";
        dt.removeRow();
        assert dt.getRowsCount() == 1 : "ERROR: Rows count must be 1";
        dt.removeRow();
        assert dt.getRowsCount() == 0 : "ERROR: Rows count must be 0";
        dt.removeRow();
        assert dt.getRowsCount() == 0 : "ERROR: Rows count must be 0";
        
        log.println("removeRow() test passed!");
    }

    /**
     * Assume that getRowsCount() and AddColumn()  works correctly
     */
    public static void test_AddAll()
    {
        log.println("========== AddAll() ==========");
        DataTable dt = new DataTable();
        assert dt != null: "datatable is null";
        assert dt.getRowsCount() == 0 : "Count of rows is not zero.";

        
        int COL_NUM = 100;
        for(int i = 0; i < COL_NUM; i++)
            dt.addColumn("col_" + i);

        // =============== Adding rows ================================
        int ROWS_COUNT = 10_000;
        LinkedList< LinkedList<Object> > listOfRows = new LinkedList<>();
        LinkedList<Object> row;
        for(int i = 0; i < ROWS_COUNT; i++)
        {
            row = new LinkedList<>();
            for(int j = 0; j < COL_NUM; j++)
                row.add("value_" + j);

            listOfRows.add(row);
        }

        long t_start = System.currentTimeMillis();
        dt.addAll(listOfRows);
        long t_end = System.currentTimeMillis();
        assert (dt.getRowsCount() == ROWS_COUNT) : "ERROR: Some rows have not added.";
        log.println(ROWS_COUNT + " rows added in " + ( t_end - t_start) + "ms");

        dt.addAll(null);
        assert (dt.getRowsCount() == ROWS_COUNT) : "ERROR: Undefined behavior for null.";
        log.println("addAll() test passed!");
    }

    /**
     * Assume that getRowsCount() and addColumn() works correctly
     */
    public static void test_AddEmptyRows()
    {
        log.println("========== AddEmptyRows() ==========");
        DataTable dt = new DataTable();
        assert dt != null: "datatable is null";
        assert dt.getRowsCount() == 0 : "Count of rows is not zero.";

        int ROWS_COUNT = 10;
        // ======== Add rows before adding columns ========
        dt.addEmptyRows(ROWS_COUNT);
        
        int COL_NUM = 100;
        for(int i = 0; i < COL_NUM; i++)
            dt.addColumn("col_" + i);

        // =======================
        ROWS_COUNT = 100_000;
        dt = new DataTable();
        long t_start = System.currentTimeMillis();
        dt.addEmptyRows(ROWS_COUNT);
        long t_end = System.currentTimeMillis();
        assert (dt.getRowsCount() == ROWS_COUNT) : "ERROR: Some rows have not added.";
        log.println(ROWS_COUNT + " rows added in " + ( t_end - t_start) + "ms");
        
        log.println("AddEmptyRows() test passed!");
    }

    public static void test_addColumnAfterRowAdded()
    {   
        log.println("========== Adding column when some rows already added ==========");
        DataTable dt = new DataTable();
        assert dt != null: "datatable is null";
        assert dt.getRowsCount() == 0 : "Count of rows is not zero.";

        int ROWS_COUNT = 5;
        int COL_NUM = 5;
        for(int i = 0; i < COL_NUM; i++)
            dt.addColumn("col_" + i);

        LinkedList<Object> row = new LinkedList<>();
        for(int i = 0; i < COL_NUM; i++)
            row.add("value_" + i);
        
        for(int i = 0; i < ROWS_COUNT; i++)
            dt.addRow(row);

        dt.addColumn("Extra column");

        log.println(dt);
        
        log.println("<Adding column when some rows already added> test passed!");
    }

    /**
     * Assume that constructor, getRowsCount(), addColumn() works correctly
     */
    public static void test_getSet()
    {
        log.println("========== get(<int, string>)|get(<int, int>)|set(<int, string>)|set(<int, int>) ==========");
        DataTable dt = new DataTable();

        int COL_NUM = 5;
        for(int i = 0; i < COL_NUM; i++)
            dt.addColumn("col_" + i);

        
        LinkedList<Object> row = new LinkedList<>();
        for(int i = 0; i < COL_NUM; i++)
            row.add("value_" + i);

        int ROWS_COUNT = 10;
        for(int j = 0; j < ROWS_COUNT; j++)
        {
            for(int i = 0; i < COL_NUM; i++)
            {
                row.set(i, "value_" + ( i + j*COL_NUM ) );
            }
            dt.addRow(row);
        }
        log.println(dt);

        assert dt.get(7, "col_0").equals("value_35");   // 7 * COL_NUM + 0
        assert dt.get(3, 4).equals("value_19");   // 3 * COL_NUM + 4
        
        boolean passed = false;
        try{
            dt.get(ROWS_COUNT + 1, COL_NUM + 1);
            passed = false;
        } catch(IndexOutOfBoundsException ex) {
            passed = true;
        }
        assert passed : "Not throwing exception when indexes are out of bound.";
        
        passed = false;
        try{
            dt.get(0, "bla-bla_not_a_column");
            passed = false;
        } catch(IndexOutOfBoundsException ex) {
            passed = true;
        }
        assert passed : "Not throwing exception when indexes are out of bound.";

        dt.set(0, 0, null);
        assert (dt.get(0, 0) == null) : "set(int, int, value) not set the value";

        dt.set(7, "col_2", "plumbus");
        assert dt.get(7, "col_2").equals("plumbus") : "set(int, string, value) not set the value";

        passed = false;
        try {
            dt.set(ROWS_COUNT + 1, 0, null);
        } catch(IndexOutOfBoundsException ex) {
            passed = true;
        }
        assert passed : "Not throwing exception when indexes are out of bound.";
        
        passed = false;
        try {
            dt.set(ROWS_COUNT + 1, "foo-bar", null);
        } catch(IndexOutOfBoundsException ex) {
            passed = true;
        }
        assert passed : "Not throwing exception when indexes are out of bound.";


        log.println("get(<int, string>)|get(<int, int>)|set(<int, string>)|set(<int, int>) test passed!");
    }

    /**
     * Assume that getColNames() works correctly
     */
    public static void test_constructors()
    {
        DataTable dt = new DataTable();
        assert dt != null: "datatable is null";
        assert dt.getRowsCount() == 0 : "Count of rows is not zero.";

        LinkedList<String> colNames = new LinkedList<>();
        colNames.add("foo");
        colNames.add("bar");
        colNames.add("test");

        // ============== DataTable(String) ========================
        String columnStr = "";
        for (String col : colNames) {
            columnStr += col;
            columnStr += ",";
        }
        columnStr = columnStr.substring(0, columnStr.length() - 1);
        dt = new DataTable(columnStr);
        Set<String> colNamesArray = dt.getColNames();
        for (String names : colNames) {
            assert colNamesArray.contains(names) : "Column have not added";
        }
        
        // ============== DataTable(String, String) ========================
        columnStr = "";
        for (String col : colNames) {
            columnStr += col;
            columnStr += "|";
        }
        columnStr = columnStr.substring(0, columnStr.length() - 1);
        dt = new DataTable(columnStr, "|");
        colNamesArray = dt.getColNames();
        for (String names : colNames) {
            assert colNamesArray.contains(names) : "Column have not added";
        }
    }

    public static void main(String[] args) {
        long t_start_test = System.currentTimeMillis();
        test_AddColumn();
        test_removeColumn();
        test_renameColumn();
        test_AddRow();
        test_RemoveRow();
        test_AddAll();
        test_AddEmptyRows();

        test_addColumnAfterRowAdded();
        test_getSet();
        test_constructors();
        long t_end_test = System.currentTimeMillis();
        
        log.println("==============================================================");
        log.println("Test passed: OK | Time: " + (t_end_test - t_start_test) + " ms");
    }
}
