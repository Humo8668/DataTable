package Test;

import java.io.PrintStream;

import uz.app.DataTable;

import java.util.LinkedList;
import java.util.Random;

public class TestDataTable {
    static PrintStream log = System.out;

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
        LinkedList<Integer> randomIndexes = new LinkedList<>();
        String[] newNames = new String[RENAME_COUNT];

        Random randomGen = new Random();
        for(int i = 0; i < RENAME_COUNT; i++)
            randomIndexes.add(randomGen.nextInt());

        for(int i = 0; i < 100; i++) 
            dt.addColumn("col_" + i);

        for(int i = 0; i < 100; i++)
        {
            if(randomIndexes.contains(i))
            {
                newNames[i] = "renamed_col_" + i;
                dt.renameColumn("col_" + i, newNames[i]);
                assert dt.getColNames()[i].equals(newNames[i]);
            }
        }
        log.println(RENAME_COUNT + " columns renamed successfully!");   
        log.println("renameColumn() test passed!");   
    }


    /**
     * Assume that getRowsCount() function works correctly
     * @param args
     */
    public static void test_AddRow()
    {
        log.println("========== AddColumn() ==========");
        DataTable dt = new DataTable();
        assert dt != null: "datatable is null";
        assert dt.getRowsCount() == 0 : "Count of rows is not zero.";
        
        int ROW_NUM = 100;
        int COL_NUM = 10;
        LinkedList<Object> row = new LinkedList<>();
        for(int i = 0; i < COL_NUM; i++)
            row.add("value_" + i);

        long t_start = System.currentTimeMillis();
        for(int i = 0; i < ROW_NUM; i++)
            dt.addRow(row);
        long t_end = System.currentTimeMillis();
        log.println(ROW_NUM+" rows with length="+COL_NUM+" added in " + ( t_end - t_start) + "ms");
        assert dt.getRowsCount() == ROW_NUM : "ERROR: Some rows have not added";

        dt = new DataTable();
        ROW_NUM = 1000;
        t_start = System.currentTimeMillis();
        for(int i = 0; i < ROW_NUM; i++)
            dt.addRow(row);
        t_end = System.currentTimeMillis();
        log.println(ROW_NUM+" rows with length="+COL_NUM+" added in " + ( t_end - t_start) + "ms");
        assert dt.getRowsCount() == ROW_NUM : "ERROR: Some rows have not added";

        dt = new DataTable();
        ROW_NUM = 100000;
        t_start = System.currentTimeMillis();
        for(int i = 0; i < ROW_NUM; i++)
            dt.addRow(row);
        t_end = System.currentTimeMillis();
        log.println(ROW_NUM+" rows with length="+COL_NUM+" added in " + ( t_end - t_start) + "ms");
        assert dt.getRowsCount() == ROW_NUM : "ERROR: Some rows have not added";
        log.println("addRow() test passed!");
    }


    public static void main(String[] args) {
        long t_start_test = System.currentTimeMillis();
        test_AddColumn();
        test_removeColumn();
        test_renameColumn();
        test_AddRow();
        long t_end_test = System.currentTimeMillis();
        System.out.println("Test passed: OK | Time: " + (t_end_test - t_start_test) + " ms");
        //assert("id".equals(dt.getColNames()));
        //System.out.println(dt);
    }
}
