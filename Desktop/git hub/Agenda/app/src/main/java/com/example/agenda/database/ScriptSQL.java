package com.example.agenda.database;

class ScriptSQL {
    public static String getCreateContato(){
        StringBuilder sqlBuilder=new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS CONTATO( ");
        sqlBuilder.append("_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append("nome VARCHAR(35), ");
        sqlBuilder.append("numero VARCHAR(14), ");
        sqlBuilder.append("apelido VARCHAR(35));");

        return sqlBuilder.toString();
    }
}
