package org.rainjay.util.sql;

import java.util.ArrayList;

import static org.rainjay.util.sql.Column.Col;

/**
 * Created by RainJay on 2016/12/16.
 */
public class SimpleSQLBuilder {
    private String selectColumn = "";
    private ArrayList<Column> cols;
    private ArrayList<Column> tables;
    private StringBuilder joinBuilder = null;
    private StringBuilder whereBuilder = null;
    private ArrayList<Column> groups = null;

    SimpleSQLBuilder(){
        cols = new ArrayList<Column>();
        tables = new ArrayList<Column>();
        joinBuilder = new StringBuilder(100);
        whereBuilder = new StringBuilder(100);
        groups = new ArrayList<>();
    }

    public SimpleSQLBuilder select( Object... cols){
        for( int i = 0; i < cols.length; i++ ){
            if( cols[i] instanceof Column ){
                this.cols.add((Column)cols[i]);
            }else{
                this.cols.add(Col(String.valueOf(cols[i])));
            }
        }
        return this;
    }

    public SimpleSQLBuilder from(Object... tables){
        for( int i = 0; i < tables.length; i++ ){
            if( tables[i] instanceof Column ){
                this.tables.add((Column)tables[i]);
            }else{
                this.tables.add(Col(String.valueOf(tables[i])));
            }
        }
        return this;
    }

    public SimpleSQLBuilder join(String tableName, String expression ){
        return join(tableName,expression,"INNER");
    }

    public SimpleSQLBuilder join( String tableName, String expression, String type ){
        joinBuilder.append(" ")
                .append(type).append(" JOIN ").append(tableName).append(" ON ").append(expression);
        return this;
    }

    public SimpleSQLBuilder where(String exp){
        whereBuilder.append(" WHERE ").append(exp);
        return this;
    }

    public SimpleSQLBuilder groupBy(Object ... cols){
        for( int i = 0; i < cols.length; i++ ){
            if( cols[i] instanceof Column ){
                this.groups.add((Column)cols[i]);
            }else{
                this.groups.add(Col(String.valueOf(cols[i])));
            }
        }
        return this;
    }
    
    public String create(){

        StringBuilder rslt = new StringBuilder(1024);
        
        rslt.append("SELECT ");
        for( int i = 0; i < cols.size(); i++ ){
            rslt.append(cols.get(i));
            if( i != cols.size() -1 ){
                rslt.append(", ");
            }
        }
        
        rslt.append(" FROM ");
        for (int i = 0; i < tables.size(); i++ ){
            rslt.append(tables.get(i));
            if( i != tables.size() -1 ){
                rslt.append(", ");
            }
        }
        
        rslt.append(" ");
        rslt.append(joinBuilder.toString()).append(" ");
        rslt.append(whereBuilder.toString()).append(" ");

        rslt.append(" GROUP BY ");
        for (int i = 0; i < groups.size(); i++ ){
            rslt.append(groups.get(i));
            if( i != groups.size() -1 ){
                rslt.append(", ");
            }
        }

        return rslt.toString();
    }

    public static void main(String[] args) {
        String rslt = new SimpleSQLBuilder()
                .select("col1","col2",Col("col7"))
                .from("table1", "Table2", Col("T3").alias("Table3"))
                .join("rightTable", "a=b", "Right")
                .where("col1=col2")
                .groupBy("col1")
                .create();
        System.out.println(rslt);
    }
}
