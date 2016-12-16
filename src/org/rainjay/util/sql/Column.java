package org.rainjay.util.sql;

/**
 * Created by RainJay on 2016/12/16.
 */
public final class Column {
    private String val = "";

    private Column() {}

    private Column( String column ){
        this.val = column;
    }

    public static Column Col(String Column){
        return new Column(Column.replace(" ","") );
    }

    public Column alias(String newName ){
//        val += " AS " + newName.replace(" ","");
        val = new StringBuilder(val).append(" AS ").append(newName.replace(" ","")).toString();
        return this;
    }

    @Override
    public String toString() {
        return val;
    }
}
