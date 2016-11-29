package com.example.andy.accountingandcancellationofmedicines.database;

/**
 * Created by Andy on 26.11.16.
 */

public final class MedicineTable {

    public static final String NameMedicineTable= "medicine";

    public static final class ColumnMedicineTable{
        public static final String ID= "Id";
        public static final String NameMedicine= "NameMedicine";
        public static final String LotNumber= "LotNumber";
        public static final String Note= "Note";
        public static final String Amount= "Amount";
        public static final String ArrivalDate= "ArrivalDate";
        public static final String DateOfManufacture= "DateOfManufacture";
        public static final String ShelfLife= "ShelfLife";
    }
}
