package com.example.andy.accountingandcancellationofmedicines.database;

/**
 * Created by Andy on 26.11.16.
 */

public final class MedicineTable {

    private static final String NameMedicineTable= "medicine";

    public static final class ColumnMedicineTable{
        private static final String ID= "Id";
        private static final String NameMedicine= "NameMedicine";
        private static final String LotNumber= "LotNumber";
        private static final String Note= "Note";
        private static final String Amount= "Amount";
        private static final String ArrivalDate= "ArrivalDate";
        private static final String DateOfManufacture= "DateOfManufacture";
        private static final String ShelfLife= "ShelfLife";
    }
}
