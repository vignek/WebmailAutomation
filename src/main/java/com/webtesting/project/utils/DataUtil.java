package com.webtesting.project.utils;

import java.util.Hashtable;

public class DataUtil {
	public static Object[][] getTestData(Xls_Reader xls,String Testcasename){
		String sheetname = "Data";
		int teststartrownum=1;
		while(!xls.getCellData(sheetname, 0, teststartrownum).equals(Testcasename)){
			teststartrownum++;
		}
		System.out.println("test starts from :" + teststartrownum);
		int colstartrownum = teststartrownum + 1;
		int datastartrownum = teststartrownum + 2;

		//calculate rows of data
		int rows =0;
		while(!xls.getCellData(sheetname, 0, datastartrownum+rows).equals("")){
			rows++;
		}
		System.out.println("total rows are" + rows);
		//calculate the columns
		int cols =0;
		while(!xls.getCellData(sheetname, cols, colstartrownum).equals("")){
			cols++;
		}
		System.out.println("total cols are" + cols);
		Object[][] data = new Object[rows][1];
		//print the values
		int datarow=0;
		Hashtable<String,String> table = null;
		for(int rnum=datastartrownum;rnum<datastartrownum+rows;rnum++){
			table = new Hashtable<String,String>();
			for(int cnum=0;cnum<cols;cnum++){
				String key = xls.getCellData(sheetname,cnum,colstartrownum);
				String value= xls.getCellData(sheetname, cnum, rnum);
				table.put(key, value);
			}
			data[datarow][0] = table;
			datarow++;
		}
		return data;
	}
	public static boolean isRunnable(Xls_Reader xls,String Testname){
		String sheetname = "testcases";
		int rows = xls.getRowCount(sheetname);
		for(int r=2;r<=rows;r++){
			String tname = xls.getCellData(sheetname, "TCID", r);
			if(tname.equals(Testname)){
				String runmode = xls.getCellData(sheetname, "Runmode", r); 
				if(runmode.equals("Y"))
					return true;
				else 
					return false;
			}
		}
		return false;
	}
}

