package com.whitedeveloper.matrix.tags;

public enum MatrixKey
{
    MATRIX_A("key_matrix_a"),
    MATRIX_B("key_matrix_b"),
    MATRIX_RESULT("key_matrix_result"),
    NUMBER_ROWS("key_number_rows"),
    NUMBER_COLUMNS("key_number_columns"),
    INDEX_NAVIGATION_MENU("key_index_navigation_menu");

    private String name;

    MatrixKey(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
